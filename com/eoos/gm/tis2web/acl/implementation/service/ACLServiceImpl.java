/*     */ package com.eoos.gm.tis2web.acl.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.implementation.io.Database;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.Group;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.ref.v3.IReference;
/*     */ import com.eoos.ref.v3.TimedMutationReference;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ACLServiceImpl
/*     */   implements ACLService
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(ACLServiceImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/*  40 */     return "acl";
/*     */   }
/*     */   
/*     */   public Boolean isActive(String any) {
/*  44 */     return new Boolean(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getAuthorizedResources(String resGroup, Set resValues, Map usrGroup2Manuf, String country) {
/*  52 */     Set authRes = new HashSet();
/*  53 */     Set authResources = new HashSet();
/*     */     
/*  55 */     if (country != null && usrGroup2Manuf.size() > 0) {
/*  56 */       Set authCountryRes = getAuthorizedResources(resGroup, "Country", country);
/*  57 */       Set authUsrGrpMFRes = getGroupManufResources(resGroup, usrGroup2Manuf);
/*  58 */       authRes.addAll(OperatorAnd(authCountryRes, authUsrGrpMFRes));
/*     */     } 
/*     */     
/*  61 */     if (country == null && usrGroup2Manuf.size() > 0) {
/*  62 */       authRes.addAll(getGroupManufResources(resGroup, usrGroup2Manuf));
/*     */     }
/*     */     
/*  65 */     if (country != null && usrGroup2Manuf.size() == 0) {
/*  66 */       authRes.addAll(getAuthorizedResources(resGroup, "Country", country));
/*     */     }
/*     */     
/*  69 */     if (resValues == null || resValues == ALL_RESOURCE_VALUES) {
/*  70 */       authResources.addAll(authRes);
/*     */     } else {
/*  72 */       authResources.addAll(OperatorAnd(resValues, authRes));
/*     */     } 
/*  74 */     return authResources;
/*     */   }
/*     */   
/*     */   public Set getAuthorizedResources(String resGroup, Map usrGroup2Manuf, String country) {
/*  78 */     Set authRes = new HashSet();
/*  79 */     if (country != null && usrGroup2Manuf.size() > 0) {
/*  80 */       Set authCountryRes = getAuthorizedResources(resGroup, "Country", country);
/*  81 */       Set authUsrGrpMFRes = getGroupManufResources(resGroup, usrGroup2Manuf);
/*  82 */       authRes.addAll(OperatorAnd(authCountryRes, authUsrGrpMFRes));
/*     */     } 
/*  84 */     return authRes;
/*     */   }
/*     */   
/*     */   protected Set getGroupManufResources(String resGroup, Map usrGroup2Manuf) {
/*  88 */     Set authRes = new HashSet();
/*  89 */     Iterator<String> it = usrGroup2Manuf.keySet().iterator();
/*  90 */     while (it.hasNext()) {
/*  91 */       String usrGroup = it.next();
/*  92 */       Set authResMFs = new HashSet();
/*  93 */       Set authResUserGroup = getAuthorizedResources(resGroup, "User Group", usrGroup);
/*  94 */       Iterator<String> itMF = ((Set)usrGroup2Manuf.get(usrGroup)).iterator();
/*  95 */       while (itMF.hasNext()) {
/*  96 */         String mf = itMF.next();
/*  97 */         authResMFs.addAll(getAuthorizedResources(resGroup, "Manufacturer", mf));
/*     */       } 
/*  99 */       authRes.addAll(OperatorAnd(authResMFs, authResUserGroup));
/*     */     } 
/* 101 */     return authRes;
/*     */   }
/*     */   
/*     */   protected Set OperatorAnd(Set op1, Set op2) {
/* 105 */     Set<String> res = new HashSet();
/* 106 */     Iterator<String> it = op1.iterator();
/* 107 */     while (it.hasNext()) {
/* 108 */       String strEl = it.next();
/* 109 */       if (op2.contains(strEl))
/* 110 */         res.add(strEl); 
/*     */     } 
/* 112 */     return res;
/*     */   }
/*     */   
/* 115 */   static Map cache = new ConcurrentHashMap<Object, Object>();
/*     */   
/*     */   private String makeKey(String resGroup, String criteria, String resVal) {
/* 118 */     return resGroup + "#" + criteria + "#" + resVal;
/*     */   }
/*     */   
/*     */   public Set getAllResources(String resGroup) {
/* 122 */     String key = "ALL_RESOURCES_" + resGroup;
/* 123 */     Object lock = this.lockObjectProvider.getLockObject(key);
/* 124 */     synchronized (lock) {
/* 125 */       Set<String> ret = (Set)cache.get(key);
/* 126 */       if (ret == null) {
/*     */         try {
/* 128 */           Connection connection = Database.getInstance().requestConnection();
/*     */           try {
/* 130 */             PreparedStatement stmt = connection.prepareStatement("select distinct f.RESOURCE_VALUE from AUTHORIZATION_CRITERIA a, AUTHORIZATION_DOMAIN b, AUTHORIZATION_VALUE c, AUTHORIZATION d, AUTHORIZATION_RESOURCE e, RESOURCE_TABLE f, RESOURCE_DOMAIN g, RESOURCE_GROUP h where a.CRITERIA_ID=b.CRITERIA_ID and b.VALUE_ID=c.VALUE_ID and c.VALUE_ID=d.VALUE_ID and d.AUTHORIZATION_ID=e.AUTHORIZATION_ID and e.RESOURCE_ID=f.RESOURCE_ID and e.RESOURCE_ID=g.RESOURCE_ID and g.RESOURCE_GROUP_ID=h.RESOURCE_GROUP_ID and h.RESOURCE_GROUP=?");
/*     */             try {
/* 132 */               stmt.setString(1, resGroup);
/* 133 */               ResultSet rs = stmt.executeQuery();
/*     */               try {
/* 135 */                 ret = new HashSet();
/* 136 */                 while (rs.next()) {
/* 137 */                   String rv = rs.getString("RESOURCE_VALUE");
/* 138 */                   if (rv != null) {
/* 139 */                     ret.add(Util.toThreadLocalMultiton(rv.trim()));
/*     */                   }
/*     */                 } 
/*     */               } finally {
/* 143 */                 JDBCUtil.close(rs, log);
/*     */               } 
/*     */             } finally {
/*     */               
/* 147 */               JDBCUtil.close(stmt, log);
/*     */             } 
/*     */           } finally {
/*     */             
/* 151 */             Database.getInstance().releaseConnection(connection);
/*     */           } 
/* 153 */         } catch (Exception e) {
/* 154 */           log.error("...unable to retrieve resource values, returning empty set - exception:" + e, e);
/* 155 */           ret = Collections.EMPTY_SET;
/*     */         } 
/* 157 */         cache.put(key, ret);
/*     */       } 
/* 159 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/* 163 */   private LockObjectProvider lockObjectProvider = new LockObjectProvider();
/*     */   
/*     */   protected Set getAuthorizedResources(String resGroup, String criteria, String resVal) {
/* 166 */     if (ApplicationContext.getInstance().developMode()) {
/* 167 */       log.info("!!!!DEVELOP MODE - returning all resources values for group: " + resGroup);
/* 168 */       Set ret = getAllResources(resGroup);
/* 169 */       if (log.isDebugEnabled()) {
/* 170 */         log.debug("...resource values: " + ret);
/*     */       }
/* 172 */       return ret;
/*     */     } 
/* 174 */     String key = makeKey(resGroup, criteria, resVal);
/* 175 */     Object lock = this.lockObjectProvider.getLockObject(key);
/* 176 */     synchronized (lock) {
/* 177 */       Set<String> authRes = (Set)cache.get(key);
/* 178 */       if (authRes != null) {
/* 179 */         return authRes;
/*     */       }
/* 181 */       authRes = new HashSet();
/* 182 */       Database DB = Database.getInstance();
/* 183 */       PreparedStatement stmt = null;
/* 184 */       ResultSet rs = null;
/* 185 */       Connection connection = null;
/*     */       
/*     */       try {
/* 188 */         connection = DB.requestConnection();
/*     */         
/* 190 */         stmt = connection.prepareStatement("select distinct f.RESOURCE_VALUE from AUTHORIZATION_CRITERIA a, AUTHORIZATION_DOMAIN b, AUTHORIZATION_VALUE c, AUTHORIZATION d, AUTHORIZATION_RESOURCE e, RESOURCE_TABLE f, RESOURCE_DOMAIN g, RESOURCE_GROUP h where a.CRITERIA_ID=b.CRITERIA_ID and b.VALUE_ID=c.VALUE_ID and a.CRITERIA=? and c.Value=? and c.VALUE_ID=d.VALUE_ID and d.AUTHORIZATION_ID=e.AUTHORIZATION_ID and e.RESOURCE_ID=f.RESOURCE_ID and e.RESOURCE_ID=g.RESOURCE_ID and g.RESOURCE_GROUP_ID=h.RESOURCE_GROUP_ID and h.RESOURCE_GROUP=?");
/*     */         
/* 192 */         stmt.setString(1, criteria);
/* 193 */         stmt.setString(2, resVal);
/* 194 */         stmt.setString(3, resGroup);
/*     */         
/* 196 */         rs = stmt.executeQuery();
/* 197 */         while (rs.next()) {
/* 198 */           String rv = rs.getString("RESOURCE_VALUE");
/* 199 */           if (rv != null) {
/* 200 */             rv = Util.toThreadLocalMultiton(rv.trim());
/*     */           }
/* 202 */           authRes.add(rv);
/*     */         }
/*     */       
/* 205 */       } catch (Exception e) {
/* 206 */         log.error("failed to execute the query - exception:" + e, e);
/*     */       } finally {
/* 208 */         if (rs != null) {
/*     */           try {
/* 210 */             rs.close();
/*     */           }
/* 212 */           catch (Exception e) {
/* 213 */             log.error("unable to close ResultSet - error:" + e, e);
/*     */           } 
/*     */         }
/* 216 */         if (stmt != null) {
/*     */           try {
/* 218 */             stmt.close();
/* 219 */           } catch (Exception e) {
/* 220 */             log.error("unable to close Statement - error:" + e, e);
/*     */           } 
/*     */         }
/* 223 */         if (connection != null) {
/*     */           try {
/* 225 */             DB.releaseConnection(connection);
/*     */           
/*     */           }
/* 228 */           catch (Exception e) {
/* 229 */             log.error("unable to close Connection - error:" + e, e);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 234 */       cache.put(key, authRes);
/* 235 */       return authRes;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/* 241 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 249 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 253 */     return "aclservice";
/*     */   }
/*     */   
/* 256 */   private static final Object SYNC_GROUPS = new Object();
/*     */   
/* 258 */   private static IReference ttRefGroups = null;
/*     */   
/*     */   public Set getUserGroups() {
/* 261 */     synchronized (SYNC_GROUPS) {
/* 262 */       Set groups = null;
/* 263 */       if (ttRefGroups == null || (groups = (Set)ttRefGroups.get()) == null) {
/*     */         try {
/* 265 */           groups = readUserGroups();
/* 266 */         } catch (Exception e) {
/* 267 */           log.error("unable to read user groups, returning empty set - exception: " + e, e);
/* 268 */           groups = Collections.EMPTY_SET;
/*     */         } 
/* 270 */         ttRefGroups = (IReference)new TimedMutationReference(groups, TimedMutationReference.Type.HARD, TimedMutationReference.Type.NULL, 300000L, false) {
/*     */             protected void onMutation(Object referent) {
/* 272 */               ACLServiceImpl.log.debug("resetting user groups");
/*     */             }
/*     */           };
/*     */       } 
/* 276 */       return groups;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Set readUserGroups() throws Exception {
/* 282 */     Set<Group> ret = new HashSet();
/* 283 */     Connection connection = Database.getInstance().requestConnection();
/*     */     try {
/* 285 */       connection.setReadOnly(true);
/* 286 */       Statement stmt = connection.createStatement();
/*     */       try {
/* 288 */         ResultSet rs = stmt.executeQuery("select distinct b.value from authorization_domain a, authorization_value b  where a.criteria_id=2 and b.value_id=a.value_id");
/*     */         try {
/* 290 */           while (rs.next()) {
/* 291 */             ret.add(Group.getInstance(rs.getString(1)));
/*     */           }
/* 293 */           return ret;
/*     */         } finally {
/*     */           
/* 296 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 299 */         JDBCUtil.close(stmt);
/*     */       } 
/*     */     } finally {
/*     */       
/* 303 */       Database.getInstance().releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\implementation\service\ACLServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */