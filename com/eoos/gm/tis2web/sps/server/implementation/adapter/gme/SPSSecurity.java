/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSSecurity
/*     */ {
/*  17 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   
/*  19 */   protected Map ecus = new HashMap<Object, Object>();
/*     */   
/*  21 */   protected Map exgrps = new HashMap<Object, Object>();
/*     */   
/*  23 */   protected Map options = new HashMap<Object, Object>();
/*     */   
/*     */   private SPSSchemaAdapterGME adapter;
/*     */   
/*     */   private SPSSecurity(SPSSchemaAdapterGME adapter) {
/*  28 */     this.adapter = adapter;
/*     */     
/*  30 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/*  31 */     Connection conn = null;
/*  32 */     DBMS.PreparedStatement stmt = null;
/*  33 */     ResultSet rs = null;
/*     */     try {
/*  35 */       conn = dblink.requestConnection();
/*  36 */       String sql = DBMS.getSQL(dblink, "SELECT SecGroupID, ECUID FROM SPS_Security");
/*  37 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  38 */       rs = stmt.executeQuery();
/*  39 */       while (rs.next()) {
/*  40 */         Integer id = Integer.valueOf(rs.getInt(1));
/*  41 */         Integer ecu = Integer.valueOf(rs.getInt(2));
/*  42 */         List<Integer> groups = (List)this.ecus.get(ecu);
/*  43 */         if (groups == null) {
/*  44 */           groups = new ArrayList();
/*  45 */           this.ecus.put(ecu, groups);
/*     */         } 
/*  47 */         groups.add(id);
/*     */       } 
/*  49 */     } catch (Exception e) {
/*  50 */       throw new RuntimeException(e);
/*     */     } finally {
/*     */       try {
/*  53 */         if (rs != null) {
/*  54 */           rs.close();
/*     */         }
/*  56 */         if (stmt != null) {
/*  57 */           stmt.close();
/*     */         }
/*  59 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*     */     try {
/*  63 */       String sql = DBMS.getSQL(dblink, "SELECT SecGroupID, OptionCode FROM SPS_SecOption");
/*  64 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  65 */       rs = stmt.executeQuery();
/*  66 */       while (rs.next()) {
/*  67 */         Integer id = Integer.valueOf(rs.getInt(1));
/*  68 */         Integer option = Integer.valueOf(rs.getInt(2));
/*  69 */         List<Integer> groups = (List)this.options.get(option);
/*  70 */         if (groups == null) {
/*  71 */           groups = new ArrayList();
/*  72 */           this.options.put(option, groups);
/*     */         } 
/*  74 */         groups.add(id);
/*     */       } 
/*  76 */     } catch (Exception e) {
/*  77 */       throw new RuntimeException(e);
/*     */     } finally {
/*     */       try {
/*  80 */         if (rs != null) {
/*  81 */           rs.close();
/*     */         }
/*  83 */         if (stmt != null) {
/*  84 */           stmt.close();
/*     */         }
/*  86 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*     */     try {
/*  90 */       String sql = DBMS.getSQL(dblink, "SELECT SecGroupID, ECUID FROM SPS_ExclSecurity");
/*  91 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  92 */       rs = stmt.executeQuery();
/*  93 */       while (rs.next()) {
/*  94 */         Integer id = Integer.valueOf(rs.getInt(1));
/*  95 */         Integer ecu = Integer.valueOf(rs.getInt(2));
/*  96 */         Set<Integer> execus = (Set)this.exgrps.get(id);
/*  97 */         if (execus == null) {
/*  98 */           execus = new HashSet();
/*  99 */           this.exgrps.put(id, execus);
/*     */         } 
/* 101 */         execus.add(ecu);
/*     */       } 
/* 103 */     } catch (Exception e) {
/* 104 */       log.info("failed to pre-load 'SPS_ExclSecurity' (" + e.getMessage() + ").");
/*     */     } finally {
/*     */       try {
/* 107 */         if (rs != null) {
/* 108 */           rs.close();
/*     */         }
/* 110 */         if (stmt != null) {
/* 111 */           stmt.close();
/*     */         }
/* 113 */         if (conn != null) {
/* 114 */           dblink.releaseConnection(conn);
/*     */         }
/* 116 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static SPSSecurity getInstance(SPSSchemaAdapterGME adapter) {
/* 127 */     synchronized (adapter.getSyncObject()) {
/* 128 */       SPSSecurity instance = (SPSSecurity)adapter.getObject(SPSSecurity.class);
/* 129 */       if (instance == null) {
/* 130 */         instance = new SPSSecurity(adapter);
/* 131 */         adapter.storeObject(SPSSecurity.class, instance);
/*     */       } 
/* 133 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean check(String hwKey, int ecu) throws Exception {
/* 138 */     List authorizations = getSecurityList(ecu);
/* 139 */     return check(hwKey, authorizations);
/*     */   }
/*     */   
/*     */   public boolean checkExclusiveSecurity(String hwKey, int ecu) throws Exception {
/* 143 */     List<Integer> groups = checkSecurityList(hwKey);
/* 144 */     if (groups == null || groups.size() == 0) {
/* 145 */       return true;
/*     */     }
/* 147 */     boolean result = true;
/* 148 */     for (int i = 0; i < groups.size(); i++) {
/* 149 */       Integer group = groups.get(i);
/* 150 */       if (this.exgrps.get(group) != null) {
/* 151 */         if (checkExclusiveGroup(group, ecu)) {
/* 152 */           return true;
/*     */         }
/* 154 */         result = false;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return result;
/*     */   }
/*     */   
/*     */   public boolean checkExclusiveGroup(Integer group, int ecu) throws Exception {
/* 162 */     Set execus = (Set)this.exgrps.get(group);
/* 163 */     return (execus == null) ? true : execus.contains(Integer.valueOf(ecu));
/*     */   }
/*     */   
/*     */   public boolean checkOptions(String hwKey, SPSVehicle vehicle) throws Exception {
/* 167 */     List options = vehicle.getOptions();
/* 168 */     List defaults = vehicle.getOptionsVIT1();
/*     */     
/* 170 */     if (options == null) {
/* 171 */       return true;
/*     */     }
/* 173 */     for (int i = 0; i < options.size(); i++) {
/* 174 */       Object option = options.get(i);
/* 175 */       if (option instanceof SPSOption && (
/* 176 */         defaults == null || !defaults.contains(option)))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 181 */         if (!check(hwKey, (SPSOption)option))
/*     */         {
/* 183 */           return false;
/*     */         }
/*     */       }
/*     */     } 
/* 187 */     return true;
/*     */   }
/*     */   
/*     */   public boolean check(String hwKey, SPSOption option) throws Exception {
/* 191 */     int key = -1;
/* 192 */     if (option.getKey() != null) {
/* 193 */       key = option.getKey().intValue();
/*     */     } else {
/*     */       try {
/* 196 */         key = Integer.parseInt(option.getID());
/* 197 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 200 */     List authorizations = getOptionSecurityList(key);
/* 201 */     return check(hwKey, authorizations);
/*     */   }
/*     */   
/*     */   protected boolean check(String hwKey, List<String> authorizations) throws Exception {
/* 205 */     if (authorizations != null && authorizations.size() > 1) {
/* 206 */       if (hwKey == null) {
/* 207 */         return false;
/*     */       }
/* 209 */       for (int i = 0; i < authorizations.size(); i++) {
/* 210 */         String authorization = authorizations.get(i);
/* 211 */         if (authorization.equals(hwKey)) {
/* 212 */           return true;
/*     */         }
/*     */       } 
/* 215 */       return false;
/*     */     } 
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List getSecurityList(int ecu) throws Exception {
/* 222 */     List groups = (List)this.ecus.get(Integer.valueOf(ecu));
/* 223 */     if (groups != null) {
/* 224 */       return loadSecurityList(groups);
/*     */     }
/* 226 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List loadSecurityList(List<Integer> groups) throws Exception {
/* 231 */     List result = null;
/* 232 */     for (int i = 0; i < groups.size(); i++) {
/* 233 */       Integer id = groups.get(i);
/* 234 */       List list = loadSecurityList(id);
/* 235 */       if (list != null) {
/* 236 */         if (result == null) {
/* 237 */           result = list;
/*     */         } else {
/* 239 */           result.addAll(list);
/*     */         } 
/*     */       }
/*     */     } 
/* 243 */     return result;
/*     */   }
/*     */   
/*     */   protected List getOptionSecurityList(int option) throws Exception {
/* 247 */     List groups = (List)this.options.get(Integer.valueOf(option));
/* 248 */     if (groups != null) {
/* 249 */       return loadSecurityList(groups);
/*     */     }
/* 251 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List loadSecurityList(Integer id) throws Exception {
/* 256 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/* 257 */     List<String> security = new ArrayList();
/* 258 */     Connection conn = null;
/* 259 */     DBMS.PreparedStatement stmt = null;
/* 260 */     ResultSet rs = null;
/*     */     try {
/* 262 */       conn = dblink.requestConnection();
/* 263 */       String sql = DBMS.getSQL(dblink, "SELECT SecurityID FROM SPS_SecGroups WHERE SecGroupID = ?");
/* 264 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 265 */       stmt.setInt(1, id.intValue());
/* 266 */       rs = stmt.executeQuery();
/* 267 */       while (rs.next()) {
/* 268 */         security.add(rs.getString(1).trim());
/*     */       }
/* 270 */     } catch (Exception e) {
/* 271 */       throw e;
/*     */     } finally {
/*     */       try {
/* 274 */         if (rs != null) {
/* 275 */           rs.close();
/*     */         }
/* 277 */         if (stmt != null) {
/* 278 */           stmt.close();
/*     */         }
/* 280 */         if (conn != null) {
/* 281 */           dblink.releaseConnection(conn);
/*     */         }
/* 283 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 286 */     return security;
/*     */   }
/*     */   
/*     */   protected List checkSecurityList(String hwk) throws Exception {
/* 290 */     if (hwk == null) {
/* 291 */       return null;
/*     */     }
/* 293 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/* 294 */     List<Integer> security = new ArrayList();
/* 295 */     Connection conn = null;
/* 296 */     DBMS.PreparedStatement stmt = null;
/* 297 */     ResultSet rs = null;
/*     */     try {
/* 299 */       conn = dblink.requestConnection();
/* 300 */       String sql = DBMS.getSQL(dblink, "SELECT SecGroupID FROM SPS_SecGroups WHERE SecurityID = ?");
/* 301 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 302 */       stmt.setString(1, hwk);
/* 303 */       rs = stmt.executeQuery();
/* 304 */       while (rs.next()) {
/* 305 */         security.add(Integer.valueOf(rs.getInt(1)));
/*     */       }
/* 307 */     } catch (Exception e) {
/* 308 */       throw e;
/*     */     } finally {
/*     */       try {
/* 311 */         if (rs != null) {
/* 312 */           rs.close();
/*     */         }
/* 314 */         if (stmt != null) {
/* 315 */           stmt.close();
/*     */         }
/* 317 */         if (conn != null) {
/* 318 */           dblink.releaseConnection(conn);
/*     */         }
/* 320 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 323 */     return security;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 327 */     getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSecurity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */