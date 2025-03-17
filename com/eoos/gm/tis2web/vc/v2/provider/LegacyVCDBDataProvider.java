/*     */ package com.eoos.gm.tis2web.vc.v2.provider;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.EngineImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.MakeImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.ModelImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.ModelyearImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.TransmissionImpl;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegacyVCDBDataProvider
/*     */   implements CfgProvider
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(LegacyVCDBDataProvider.class);
/*     */   
/*  47 */   private static LegacyVCDBDataProvider instance = null;
/*     */   
/*     */   private IDatabaseLink dbLink;
/*     */   
/*  51 */   private final Object SYNC_RESOLVE_MAP = new Object();
/*     */   
/*  53 */   private Map resolveMap = null;
/*     */   
/*     */   public static final int DOMAINID_MAKE = 2;
/*     */   
/*     */   public static final int DOMAINID_MODEL = 6;
/*     */   
/*     */   public static final int DOMAINID_MODELYEAR = 3;
/*     */   
/*     */   public static final int DOMAINID_ENGINE = 7;
/*     */   
/*     */   public static final int DOMAINID_TRANSMISSION = 8;
/*     */   
/*  65 */   private final Object SYNC_CFGS = new Object();
/*     */   
/*  67 */   private Set cfgs = null;
/*     */   
/*     */   public LegacyVCDBDataProvider(IDatabaseLink dblink) {
/*  70 */     this.dbLink = dblink;
/*     */   }
/*     */   
/*     */   public static synchronized LegacyVCDBDataProvider getInstance() {
/*  74 */     if (instance == null) {
/*  75 */       IDatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.vc.db."));
/*     */       
/*  77 */       instance = new LegacyVCDBDataProvider(dbLink);
/*     */     } 
/*  79 */     return instance;
/*     */   }
/*     */   
/*     */   public Object getKey(int domainID) {
/*  83 */     switch (domainID) {
/*     */       case 2:
/*  85 */         return VehicleConfigurationUtil.KEY_MAKE;
/*     */       case 6:
/*  87 */         return VehicleConfigurationUtil.KEY_MODEL;
/*     */       case 3:
/*  89 */         return VehicleConfigurationUtil.KEY_MODELYEAR;
/*     */       case 7:
/*  91 */         return VehicleConfigurationUtil.KEY_ENGINE;
/*     */       case 8:
/*  93 */         return VehicleConfigurationUtil.KEY_TRANSMISSION;
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDomainID(Object key) {
/* 100 */     if (VehicleConfigurationUtil.KEY_MAKE == key)
/* 101 */       return 2; 
/* 102 */     if (VehicleConfigurationUtil.KEY_MODEL == key)
/* 103 */       return 6; 
/* 104 */     if (VehicleConfigurationUtil.KEY_MODELYEAR == key)
/* 105 */       return 3; 
/* 106 */     if (VehicleConfigurationUtil.KEY_ENGINE == key)
/* 107 */       return 7; 
/* 108 */     if (VehicleConfigurationUtil.KEY_TRANSMISSION == key) {
/* 109 */       return 8;
/*     */     }
/* 111 */     throw new IllegalArgumentException("unknown key");
/*     */   }
/*     */ 
/*     */   
/*     */   private Map createResolveMap() throws Exception {
/* 116 */     StopWatch sw = StopWatch.getInstance().start();
/*     */     try {
/* 118 */       Connection connection = this.dbLink.requestConnection();
/*     */       try {
/* 120 */         connection.setReadOnly(true);
/* 121 */         connection.setAutoCommit(false);
/* 122 */         String query = "select a.domain_id, a.value_id, b.label, c.label from vcrvalue a left outer join  (select * from vcrlabel where language_id=0) b  on a.value_label=b.label_id  left outer join (select * from vcrlabel where language_id=5) c on a.value_label=c.label_id where  a.domain_id in (2,3,6,7,8)";
/* 123 */         PreparedStatement stmt = connection.prepareStatement(query);
/*     */         try {
/* 125 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 127 */             Map<Object, Object> ret = new HashMap<Object, Object>();
/* 128 */             while (rs.next()) {
/* 129 */               int domainID = rs.getInt(1);
/* 130 */               int valueID = rs.getInt(2);
/* 131 */               String label = rs.getString(3);
/* 132 */               if (label == null) {
/* 133 */                 label = rs.getString(4);
/*     */               }
/* 135 */               Object key = new PairImpl(Integer.valueOf(domainID), Integer.valueOf(valueID));
/* 136 */               ret.put(key, label);
/*     */             } 
/* 138 */             if (log.isDebugEnabled()) {
/* 139 */               log.debug("created resolve map in : " + sw.getElapsedTime() + " ms");
/*     */             }
/* 141 */             return ret;
/*     */           } finally {
/* 143 */             JDBCUtil.close(rs);
/*     */           } 
/*     */         } finally {
/*     */           
/* 147 */           JDBCUtil.close(stmt);
/*     */         } 
/*     */       } finally {
/* 150 */         this.dbLink.releaseConnection(connection);
/*     */       } 
/*     */     } finally {
/* 153 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map getResolveMap() {
/* 158 */     synchronized (this.SYNC_RESOLVE_MAP) {
/* 159 */       if (this.resolveMap == null) {
/*     */         try {
/* 161 */           this.resolveMap = Collections.unmodifiableMap(createResolveMap());
/* 162 */         } catch (Exception e) {
/* 163 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/* 166 */       return this.resolveMap;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String resolve(int domainID, int valueID) throws Exception {
/* 171 */     PairImpl pairImpl = new PairImpl(Integer.valueOf(domainID), Integer.valueOf(valueID));
/* 172 */     return (String)getResolveMap().get(pairImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object resolveToObject(int domainID, int valueID) throws Exception {
/* 177 */     if (domainID == 2)
/* 178 */       return resolveMake(valueID); 
/* 179 */     if (domainID == 6)
/* 180 */       return resolveModel(valueID); 
/* 181 */     if (domainID == 3)
/* 182 */       return resolveModelyear(valueID); 
/* 183 */     if (domainID == 7)
/* 184 */       return resolveEngine(valueID); 
/* 185 */     if (domainID == 8) {
/* 186 */       return resolveTransmission(valueID);
/*     */     }
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Make resolveMake(int valueID) throws Exception {
/* 193 */     return VehicleConfigurationUtil.toMake(resolve(2, valueID));
/*     */   }
/*     */   
/*     */   public Model resolveModel(int valueID) throws Exception {
/* 197 */     return VehicleConfigurationUtil.toModel(resolve(6, valueID));
/*     */   }
/*     */   
/*     */   public Modelyear resolveModelyear(int valueID) throws Exception {
/* 201 */     return VehicleConfigurationUtil.toModelyear(resolve(3, valueID));
/*     */   }
/*     */   
/*     */   public Engine resolveEngine(int valueID) throws Exception {
/* 205 */     return VehicleConfigurationUtil.toEngine(resolve(7, valueID));
/*     */   }
/*     */   
/*     */   public Transmission resolveTransmission(int valueID) throws Exception {
/* 209 */     return VehicleConfigurationUtil.toTransmission(resolve(8, valueID));
/*     */   }
/*     */   
/*     */   public Set getConfigurations() {
/* 213 */     synchronized (this.SYNC_CFGS) {
/* 214 */       if (this.cfgs == null) {
/* 215 */         this.cfgs = Collections.unmodifiableSet(createConfigurations());
/*     */       }
/* 217 */       return this.cfgs;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Set createConfigurations() {
/* 222 */     StopWatch sw = StopWatch.getInstance().start();
/*     */     try {
/* 224 */       log.debug("reading vehicle configurations from database");
/*     */       try {
/* 226 */         Connection connection = this.dbLink.requestConnection();
/*     */         try {
/* 228 */           connection.setReadOnly(true);
/* 229 */           connection.setAutoCommit(false);
/*     */           
/* 231 */           PreparedStatement stmt = connection.prepareStatement("select a.value_id, b.value_id, c.value_id, d.value_id, e.value_id, a.config_id from vcrconfig a, vcrconfig b, vcrconfig c, vcrconstraint d, vcrconstraint e, vcrconstraint f where a.domain_id=2 and b.domain_id=6 and c.domain_id=3 and a.config_id=b.config_id and a.config_id=c.config_id and d.domain_id=7 and e.domain_id=8 and f.domain_id=9 and f.value_id=a.config_id and f.constraint_id=d.constraint_id and f.constraint_id=e.constraint_id");
/*     */           
/*     */           try {
/* 234 */             Set<IConfiguration> ret = new HashSet();
/* 235 */             ResultSet rs = stmt.executeQuery();
/*     */             try {
/* 237 */               while (rs.next()) {
/* 238 */                 MakeImpl makeImpl; ModelImpl modelImpl; ModelyearImpl modelyearImpl; EngineImpl engineImpl; TransmissionImpl transmissionImpl; Make make = null;
/* 239 */                 int makeID = rs.getInt(1);
/* 240 */                 if (makeID != 0) {
/* 241 */                   makeImpl = MakeImpl.getInstance(resolve(2, makeID));
/*     */                 }
/*     */                 
/* 244 */                 Model model = null;
/* 245 */                 int modelID = rs.getInt(2);
/* 246 */                 if (modelID != 0) {
/* 247 */                   modelImpl = ModelImpl.getInstance(resolve(6, modelID));
/*     */                 }
/*     */                 
/* 250 */                 Modelyear modelyear = null;
/* 251 */                 int modelyearID = rs.getInt(3);
/* 252 */                 if (modelyearID != 0) {
/* 253 */                   modelyearImpl = ModelyearImpl.getInstance(resolve(3, modelyearID));
/*     */                 }
/*     */                 
/* 256 */                 Engine engine = null;
/* 257 */                 int engineID = rs.getInt(4);
/* 258 */                 if (engineID != 0) {
/* 259 */                   engineImpl = EngineImpl.getInstance(resolve(7, engineID));
/*     */                 }
/*     */                 
/* 262 */                 Transmission transmission = null;
/* 263 */                 int transmissionID = rs.getInt(5);
/* 264 */                 if (transmissionID != 0) {
/* 265 */                   transmissionImpl = TransmissionImpl.getInstance(resolve(8, transmissionID));
/*     */                 }
/* 267 */                 IConfiguration cfg = VehicleConfigurationUtil.createCfg(makeImpl, modelImpl, modelyearImpl, engineImpl, transmissionImpl);
/* 268 */                 ret.add(cfg);
/*     */               } 
/*     */             } finally {
/*     */               
/* 272 */               JDBCUtil.close(rs);
/*     */             } 
/*     */             
/* 275 */             if (log.isDebugEnabled()) {
/* 276 */               log.debug("read " + ret.size() + " configurations in: " + sw.getElapsedTime() + " ms");
/*     */             }
/* 278 */             return ret;
/*     */           } finally {
/*     */             
/* 281 */             JDBCUtil.close(stmt);
/*     */           } 
/*     */         } finally {
/*     */           
/* 285 */           this.dbLink.releaseConnection(connection);
/*     */         } 
/* 287 */       } catch (Exception e) {
/* 288 */         log.error("unable to provide configurations,returning empty set - exception:" + e, e);
/* 289 */         return Collections.EMPTY_SET;
/*     */       } 
/*     */     } finally {
/* 292 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 297 */     return 0L;
/*     */   }
/*     */   
/*     */   public static String normalize(String string) {
/* 301 */     StringBuffer ret = new StringBuffer();
/* 302 */     char[] charArray = string.toLowerCase(Locale.ENGLISH).toCharArray();
/* 303 */     for (int i = 0; i < charArray.length; i++) {
/* 304 */       char c = charArray[i];
/* 305 */       if (Character.isLetterOrDigit(c)) {
/* 306 */         ret.append(c);
/*     */       }
/*     */     } 
/* 309 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public static class ValueNotFoundException
/*     */     extends Exception {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String value;
/*     */     
/*     */     public ValueNotFoundException(String value) {
/* 318 */       super("unable to retrieve valueID for value: " + value);
/* 319 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 323 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getValueID(String _value, int domainID) throws ValueNotFoundException {
/* 329 */     String lookup = normalize(_value);
/*     */     
/* 331 */     Map resolveMap = getResolveMap();
/* 332 */     for (Iterator<Map.Entry> iter = resolveMap.entrySet().iterator(); iter.hasNext(); ) {
/* 333 */       Map.Entry entry = iter.next();
/* 334 */       String value = normalize((String)entry.getValue());
/* 335 */       if (value.equals(lookup)) {
/* 336 */         Pair pair = (Pair)entry.getKey();
/* 337 */         if (domainID == ((Integer)pair.getFirst()).intValue()) {
/* 338 */           return ((Integer)pair.getSecond()).intValue();
/*     */         }
/*     */       } 
/*     */     } 
/* 342 */     throw new ValueNotFoundException(_value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getKeys() {
/* 347 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\LegacyVCDBDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */