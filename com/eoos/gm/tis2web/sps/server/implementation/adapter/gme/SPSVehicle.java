/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSOption;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSVehicle extends SPSVehicle {
/*     */   protected String make;
/*     */   protected String model;
/*     */   protected String year;
/*     */   protected String engine;
/*     */   protected String transmission;
/*  21 */   private static Logger log = Logger.getLogger(SPSVehicle.class); protected List defaults; protected String hardware; protected List wildcardControllerListRNG;
/*     */   private SPSSchemaAdapterGME adapter;
/*     */   
/*  24 */   public static final class StaticData { private Map makes = new HashMap<Object, Object>();
/*     */     
/*  26 */     private Map models = new HashMap<Object, Object>();
/*     */     
/*  28 */     private Map years = new HashMap<Object, Object>();
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGME adapter) {
/*  31 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  32 */       Connection conn = null;
/*     */       try {
/*  34 */         conn = db.requestConnection();
/*  35 */         load(conn, DBMS.getSQL(db, "SELECT SalesMakeCode, SalesMake FROM SPS_SalesMake"), this.makes);
/*  36 */         load(conn, DBMS.getSQL(db, "SELECT DISTINCT ModelCode, Description FROM SPS_ModelDescription"), this.models);
/*  37 */         load(conn, DBMS.getSQL(db, "SELECT ModelYearCode, ModelYear FROM SPS_ModelYear"), this.years);
/*  38 */       } catch (RuntimeException e) {
/*  39 */         throw e;
/*  40 */       } catch (Exception e) {
/*  41 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  44 */           if (conn != null) {
/*  45 */             db.releaseConnection(conn);
/*     */           }
/*  47 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private static void load(Connection conn, String sql, Map<String, Integer> map) throws Exception {
/*  58 */       DBMS.PreparedStatement stmt = null;
/*  59 */       ResultSet rs = null;
/*     */       try {
/*  61 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  62 */         rs = stmt.executeQuery();
/*  63 */         while (rs.next()) {
/*  64 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  65 */           String label = rs.getString(2).trim();
/*  66 */           map.put(label.toLowerCase(Locale.ENGLISH), id);
/*     */         } 
/*  68 */       } catch (Exception e) {
/*  69 */         throw e;
/*     */       } finally {
/*     */         try {
/*  72 */           if (rs != null) {
/*  73 */             rs.close();
/*     */           }
/*  75 */           if (stmt != null) {
/*  76 */             stmt.close();
/*     */           }
/*  78 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGME adapter) {
/*  84 */       synchronized (adapter.getSyncObject()) {
/*  85 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  86 */         if (instance == null) {
/*  87 */           instance = new StaticData(adapter);
/*  88 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  90 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getMakes() {
/*  95 */       return this.makes;
/*     */     }
/*     */     
/*     */     public Map getModels() {
/*  99 */       return this.models;
/*     */     }
/*     */     
/*     */     public Map getYears() {
/* 103 */       return this.years;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSVehicle(SPSVIN vin, SPSSchemaAdapterGME adapter) {
/* 127 */     super(vin);
/* 128 */     this.adapter = adapter;
/* 129 */     this.make = vin.getSalesMakeVC();
/* 130 */     this.model = vin.getModelVC();
/* 131 */     this.year = vin.getModelYearVC();
/* 132 */     this.engine = vin.getEngineVC();
/* 133 */     this.transmission = vin.getTransmissionVC();
/* 134 */     log.debug("make=" + this.make + " (" + getSalesMakeID() + ")");
/* 135 */     log.debug("model=" + this.model + " (" + getModelID() + ")");
/* 136 */     log.debug("model year=" + this.year + " (" + getModelYearID() + ")");
/* 137 */     if (this.engine != null) {
/* 138 */       log.debug("engine=" + this.engine);
/*     */     }
/*     */   }
/*     */   
/*     */   public List getWildcardControllerRNG() {
/* 143 */     return this.wildcardControllerListRNG;
/*     */   }
/*     */   
/*     */   public void setWildcardControllerRNG(SPSController wildcardControllerRNG) {
/* 147 */     if (wildcardControllerRNG == null) {
/* 148 */       this.wildcardControllerListRNG = null;
/*     */     } else {
/* 150 */       if (this.wildcardControllerListRNG == null) {
/* 151 */         this.wildcardControllerListRNG = new ArrayList();
/*     */       }
/* 153 */       this.wildcardControllerListRNG.add(wildcardControllerRNG);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHardware() {
/* 158 */     return this.hardware;
/*     */   }
/*     */   
/*     */   public void setHardware(String hardware) {
/* 162 */     this.hardware = hardware;
/*     */   }
/*     */   
/*     */   public Integer getSalesMakeID() {
/* 166 */     return (Integer)StaticData.getInstance(this.adapter).getMakes().get(this.make.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   public Integer getModelID() {
/* 170 */     return (Integer)StaticData.getInstance(this.adapter).getModels().get(this.model.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   public Integer getModelYearID() {
/* 174 */     return (Integer)StaticData.getInstance(this.adapter).getYears().get(this.year);
/*     */   }
/*     */   
/*     */   public String getSalesMake() {
/* 178 */     return this.make;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 182 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getModelYear() {
/* 186 */     return this.year;
/*     */   }
/*     */   
/*     */   void setEngine(String engine) {
/* 190 */     if (this.engine == null && ((SPSVIN)this.vin).confirm(engine))
/*     */     {
/*     */ 
/*     */       
/* 194 */       this.engine = engine;
/*     */     }
/*     */   }
/*     */   
/*     */   void setTransmission(String transmission) {
/* 199 */     this.transmission = transmission;
/*     */   }
/*     */   
/*     */   String getEngine() {
/* 203 */     return this.engine;
/*     */   }
/*     */   
/*     */   String getTransmission() {
/* 207 */     return this.transmission;
/*     */   }
/*     */   
/*     */   void setEngineTransmission(String snoet) {
/* 211 */     StringTokenizer tokenizer = new StringTokenizer(snoet, " ");
/* 212 */     String ddi = null;
/* 213 */     while (tokenizer.hasMoreTokens()) {
/* 214 */       String token = tokenizer.nextToken();
/* 215 */       if (this.engine == null) {
/* 216 */         setEngine(token); continue;
/* 217 */       }  if (this.transmission == null) {
/* 218 */         if (token.length() > 2) {
/* 219 */           setTransmission(token); continue;
/*     */         } 
/* 221 */         ddi = token; continue;
/*     */       } 
/* 223 */       if (ddi == null && token.length() <= 2) {
/* 224 */         ddi = token; continue;
/*     */       } 
/* 226 */       this.engine = null;
/* 227 */       this.transmission = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   List getOptionsVIT1() {
/* 233 */     return this.defaults;
/*     */   }
/*     */ 
/*     */   
/*     */   void setDefaultOption(SPSOption option) {
/* 238 */     if (this.defaults == null) {
/* 239 */       this.defaults = new ArrayList();
/*     */     }
/* 241 */     if (!this.defaults.contains(option)) {
/* 242 */       this.defaults.add(option);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void removeDefaultOption(SPSOption option) {
/* 249 */     if (this.defaults != null) {
/* 250 */       this.defaults.remove(option);
/*     */     }
/*     */   }
/*     */   
/*     */   SPSOption getOption(String id) {
/* 255 */     if (this.defaults == null) {
/* 256 */       return null;
/*     */     }
/* 258 */     for (int i = 0; i < this.defaults.size(); i++) {
/* 259 */       SPSOption option = this.defaults.get(i);
/* 260 */       if (option.getType() != null && option.getType().getID().equals(id)) {
/* 261 */         return option;
/*     */       }
/*     */     } 
/* 264 */     return null;
/*     */   }
/*     */   
/*     */   static String normalize(String label) {
/* 268 */     if (label == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     StringBuffer buffer = new StringBuffer();
/* 272 */     for (int i = 0; i < label.length(); i++) {
/* 273 */       char c = label.charAt(i);
/* 274 */       if (!Character.isWhitespace(c)) {
/* 275 */         buffer.append(c);
/*     */       }
/*     */     } 
/* 278 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   static String getSalesMake(Integer make, SPSSchemaAdapterGME adapter) {
/* 282 */     Iterator<String> it = StaticData.getInstance(adapter).getMakes().keySet().iterator();
/* 283 */     while (it.hasNext()) {
/* 284 */       String key = it.next();
/* 285 */       Integer id = (Integer)StaticData.getInstance(adapter).getMakes().get(key.toLowerCase(Locale.ENGLISH));
/* 286 */       if (id != null && id.equals(make)) {
/* 287 */         return key;
/*     */       }
/*     */     } 
/* 290 */     return null;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 294 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSVehicle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */