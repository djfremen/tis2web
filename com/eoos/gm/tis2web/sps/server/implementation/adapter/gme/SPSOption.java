/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSOption;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVehicle;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSOption implements SPSOption, Comparable {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   private static final Logger log = Logger.getLogger(SPSOption.class);
/*  23 */   public static transient SPSOption REJECT_OPTION = new SPSOption("##reject##", "none of the above");
/*     */   protected transient SPSDescription descriptions;
/*     */   protected Integer key;
/*     */   protected String id;
/*     */   protected String description;
/*     */   protected SPSOption type;
/*     */   
/*     */   public static final class FreeOptions
/*     */   {
/*     */     private Map options;
/*     */     private Map optionsRNG;
/*     */     private Map optionsVIN;
/*     */     private Map optionsV10;
/*     */     private Map optionsVIT1;
/*     */     private Map optionsHWO;
/*     */     
/*     */     private FreeOptions(SPSSchemaAdapterGME adapter) {
/*  40 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  41 */       boolean error = false;
/*  42 */       this.options = new HashMap<Object, Object>();
/*  43 */       this.optionsRNG = new HashMap<Object, Object>();
/*  44 */       this.optionsVIN = new HashMap<Object, Object>();
/*  45 */       this.optionsV10 = new HashMap<Object, Object>();
/*  46 */       this.optionsVIT1 = new HashMap<Object, Object>();
/*  47 */       this.optionsHWO = new HashMap<Object, Object>();
/*  48 */       Connection conn = null;
/*  49 */       DBMS.PreparedStatement stmt = null;
/*  50 */       ResultSet rs = null;
/*     */       try {
/*  52 */         conn = db.requestConnection();
/*  53 */         String sql = DBMS.getSQL(db, "SELECT OptionCode, LanguageID, Description FROM SPS_FreeOptions");
/*  54 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  55 */         rs = stmt.executeQuery();
/*  56 */         while (rs.next()) {
/*  57 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  58 */           String lg = rs.getString(2).trim();
/*  59 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*  60 */           String description = DBMS.getString(db, language, rs, 3);
/*  61 */           SPSOption option = (SPSOption)this.options.get(id);
/*  62 */           if (option == null) {
/*  63 */             option = new SPSOption(id);
/*  64 */             this.options.put(id, option);
/*     */           } 
/*  66 */           option.descriptions.add(language, description);
/*     */         } 
/*  68 */       } catch (Exception e) {
/*  69 */         error = true;
/*  70 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  73 */           if (rs != null) {
/*  74 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/*  76 */           if (stmt != null) {
/*  77 */             close(stmt);
/*     */           }
/*  79 */           if (error && conn != null) {
/*  80 */             db.releaseConnection(conn);
/*     */           }
/*  82 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/*  86 */         String sql = DBMS.getSQL(db, "SELECT CategoryCode, OptionCode FROM SPS_FreeOptionSet");
/*  87 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  88 */         rs = stmt.executeQuery();
/*  89 */         while (rs.next()) {
/*  90 */           String categoryCode = rs.getString(1).trim();
/*  91 */           SPSOptionCategory category = SPSOptionCategory.getOptionCategory(categoryCode, adapter);
/*  92 */           SPSOption option = (SPSOption)this.options.get(Integer.valueOf(rs.getInt(2)));
/*  93 */           if (category == null) {
/*  94 */             category = SPSOptionCategory.createOptionCategory(categoryCode, adapter);
/*     */           }
/*  96 */           category.add(option);
/*     */         } 
/*  98 */       } catch (Exception e) {
/*  99 */         error = true;
/* 100 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 103 */           if (rs != null) {
/* 104 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 106 */           if (stmt != null) {
/* 107 */             close(stmt);
/*     */           }
/* 109 */           if (error && conn != null) {
/* 110 */             db.releaseConnection(conn);
/*     */           }
/* 112 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/* 116 */         String sql = DBMS.getSQL(db, "SELECT DISTINCT g.OptionCode, h.HWName FROM SPS_Option s, SPS_OptionGroup g, SPS_HWDescription h WHERE s.CategoryCode = 'HWO' AND s.OptionGroup = g.OptionGroup AND g.OptionCode = h.HWID");
/* 117 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 118 */         rs = stmt.executeQuery();
/* 119 */         while (rs.next()) {
/* 120 */           Integer id = Integer.valueOf(rs.getInt(1));
/* 121 */           String hwName = rs.getString(2).trim();
/* 122 */           SPSOption option = new SPSOptionHWO(id, hwName);
/* 123 */           this.optionsHWO.put(id, option);
/*     */         } 
/* 125 */         rs.close();
/* 126 */         stmt.close();
/* 127 */         stmt = DBMS.prepareSQLStatement(conn, DBMS.getSQL(db, "SELECT DISTINCT l.HWLocID, l.IdentOrder, l.VITType, l.VIT1Ident, l.VITPos FROM SPS_HWDescription h, SPS_HWLocation l WHERE h.HWID = ? AND h.HWLocID = l.HWLocID ORDER BY 1,2"));
/* 128 */         Iterator<Integer> it = this.optionsHWO.keySet().iterator();
/* 129 */         while (it.hasNext()) {
/* 130 */           Integer id = it.next();
/* 131 */           SPSOptionHWO option = (SPSOptionHWO)this.optionsHWO.get(id);
/* 132 */           Map<Object, Object> hwlocations = new HashMap<Object, Object>();
/* 133 */           option.setHWLocations(hwlocations);
/* 134 */           stmt.setInt(1, id.intValue());
/* 135 */           rs = stmt.executeQuery();
/* 136 */           while (rs.next()) {
/* 137 */             id = Integer.valueOf(rs.getInt(1));
/* 138 */             rs.getInt(2);
/* 139 */             String vit1Type = DBMS.trimString(rs.getString(3));
/* 140 */             String vit1Ident = DBMS.trimString(rs.getString(4));
/* 141 */             Integer vit1Pos = Integer.valueOf(rs.getInt(5));
/* 142 */             if (rs.wasNull()) {
/* 143 */               vit1Pos = null;
/*     */             }
/* 145 */             String key = id + ":" + vit1Type;
/* 146 */             List<SPSIdentType> sequence = (List)hwlocations.get(key);
/* 147 */             if (sequence == null) {
/* 148 */               sequence = new ArrayList();
/* 149 */               hwlocations.put(key, sequence);
/*     */             } 
/* 151 */             sequence.add(new SPSIdentType(id, vit1Ident, vit1Pos, null));
/*     */           } 
/* 153 */           rs.close();
/* 154 */           rs = null;
/*     */         } 
/* 156 */       } catch (Exception e) {
/* 157 */         error = true;
/* 158 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 161 */           if (rs != null) {
/* 162 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 164 */           if (stmt != null) {
/* 165 */             close(stmt);
/*     */           }
/* 167 */           if (error && conn != null) {
/* 168 */             db.releaseConnection(conn);
/*     */           }
/* 170 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/* 174 */         String sql = DBMS.getSQL(db, "SELECT RangeCode, FromSN, ToSN FROM SPS_VINRange");
/* 175 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 176 */         rs = stmt.executeQuery();
/* 177 */         while (rs.next()) {
/* 178 */           Integer id = Integer.valueOf(rs.getInt(1));
/* 179 */           SPSVINRange range = new SPSVINRange(rs.getString(2).trim(), rs.getString(3).trim());
/*     */           
/* 181 */           SPSOption option = new SPSOptionRNG(id, range);
/* 182 */           this.optionsRNG.put(id, option);
/*     */         } 
/* 184 */       } catch (Exception e) {
/* 185 */         error = true;
/* 186 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 189 */           if (rs != null) {
/* 190 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 192 */           if (stmt != null) {
/* 193 */             close(stmt);
/*     */           }
/* 195 */           if (error && conn != null) {
/* 196 */             db.releaseConnection(conn);
/*     */           }
/* 198 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/* 202 */         String sql = DBMS.getSQL(db, "SELECT VINCode, WMI, VINPattern FROM SPS_VehicleCode");
/* 203 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 204 */         rs = stmt.executeQuery();
/* 205 */         while (rs.next()) {
/* 206 */           Integer id = Integer.valueOf(rs.getInt(1));
/* 207 */           SPSVINCode vcode = new SPSVINCode(rs.getString(2).trim(), rs.getString(3).trim());
/*     */           
/* 209 */           SPSOption option = new SPSOptionVIN(id, vcode);
/* 210 */           this.optionsVIN.put(id, option);
/*     */         } 
/* 212 */       } catch (Exception e) {
/* 213 */         error = true;
/* 214 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 217 */           if (rs != null) {
/* 218 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 220 */           if (stmt != null) {
/* 221 */             close(stmt);
/*     */           }
/* 223 */           if (error && conn != null) {
/* 224 */             db.releaseConnection(conn);
/*     */           }
/* 226 */         } catch (Exception x) {}
/*     */       } 
/*     */ 
/*     */       
/* 230 */       try { String sql = DBMS.getSQL(db, "SELECT Pos10Code, Pos10Value FROM SPS_VINPos10");
/* 231 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 232 */         rs = stmt.executeQuery();
/* 233 */         while (rs.next()) {
/* 234 */           Integer id = Integer.valueOf(rs.getInt(1));
/* 235 */           SPSOption option = new SPSOptionV10(id, rs.getString(2).trim());
/* 236 */           this.optionsV10.put(id, option);
/*     */         }  }
/* 238 */       catch (Exception e) {  }
/*     */       finally
/*     */       { try {
/* 241 */           if (rs != null) {
/* 242 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 244 */           if (stmt != null) {
/* 245 */             close(stmt);
/*     */           }
/* 247 */         } catch (Exception x) {} }
/*     */ 
/*     */       
/*     */       try {
/*     */         try {
/* 252 */           String sql = DBMS.getSQL(db, "SELECT OptionCode, OptValue FROM SPS_VITOptions");
/* 253 */           stmt = DBMS.prepareSQLStatement(conn, sql);
/* 254 */           rs = stmt.executeQuery();
/* 255 */           while (rs.next()) {
/* 256 */             Integer id = Integer.valueOf(rs.getInt(1));
/* 257 */             String value = rs.getString(2).trim();
/* 258 */             SPSOption option = new SPSOptionVIT1(id, value);
/* 259 */             this.optionsVIT1.put(id, option);
/*     */           } 
/* 261 */         } catch (SQLException e) {
/*     */           
/* 263 */           if (e.getErrorCode() != 942 && e.getErrorCode() != 9082) {
/* 264 */             throw e;
/*     */           }
/* 266 */           SPSOption.log.debug("VIT options table does not exist");
/*     */         }
/*     */       
/* 269 */       } catch (Exception e) {
/* 270 */         SPSOption.log.warn("unable to retrieve options, ignoring - exception: " + e, e);
/*     */       } finally {
/*     */         try {
/* 273 */           if (rs != null) {
/* 274 */             JDBCUtil.close(rs, SPSOption.log);
/*     */           }
/* 276 */           if (stmt != null) {
/* 277 */             close(stmt);
/*     */           }
/* 279 */           if (conn != null) {
/* 280 */             db.releaseConnection(conn);
/*     */           }
/* 282 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void close(DBMS.PreparedStatement stmt) {
/*     */       try {
/* 289 */         stmt.close();
/* 290 */       } catch (Exception x) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static FreeOptions getInstance(SPSSchemaAdapterGME adapter) {
/* 299 */       synchronized (adapter.getSyncObject()) {
/* 300 */         FreeOptions instance = (FreeOptions)adapter.getObject(FreeOptions.class);
/* 301 */         if (instance == null) {
/* 302 */           instance = new FreeOptions(adapter);
/* 303 */           adapter.storeObject(FreeOptions.class, instance);
/*     */         } 
/* 305 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getOptions() {
/* 310 */       return this.options;
/*     */     }
/*     */     
/*     */     public Map getOptionsRNG() {
/* 314 */       return this.optionsRNG;
/*     */     }
/*     */     
/*     */     public Map getOptionsVIN() {
/* 318 */       return this.optionsVIN;
/*     */     }
/*     */     
/*     */     public Map getOptionsV10() {
/* 322 */       return this.optionsV10;
/*     */     }
/*     */     
/*     */     public Map getOptionsVIT1() {
/* 326 */       return this.optionsVIT1;
/*     */     }
/*     */     
/*     */     public Map getOptionsHWO() {
/* 330 */       return this.optionsHWO;
/*     */     }
/*     */   }
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
/*     */   SPSOption(Integer id) {
/* 347 */     this.key = id;
/* 348 */     this.id = id.toString();
/* 349 */     this.descriptions = new SPSDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSOption(String id) {
/* 354 */     this.key = null;
/* 355 */     this.id = id;
/* 356 */     this.descriptions = new SPSDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSOption(String id, String description) {
/* 361 */     this.id = id;
/* 362 */     this.description = description;
/*     */   }
/*     */   
/*     */   void setType(SPSOption type) {
/* 366 */     this.type = type;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 370 */     return this.id.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 374 */     return (object != null && object instanceof SPSOption && ((SPSOption)object).getID().equals(this.id));
/*     */   }
/*     */   
/*     */   public Integer getKey() {
/* 378 */     return this.key;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 382 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 386 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 390 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getHelp(SPSVehicle vehicle) {
/* 394 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 398 */     return this.id + ":" + ((this.description != null) ? this.description : this.descriptions.get(SPSLanguage.getLanguage(Locale.ENGLISH, (SPSSchemaAdapterGME)null)));
/*     */   }
/*     */   
/*     */   public SPSOption getType() {
/* 402 */     return this.type;
/*     */   }
/*     */   
/*     */   boolean isValid() {
/* 406 */     return (this.description != null);
/*     */   }
/*     */   
/*     */   boolean isValid(SPSLanguage language) {
/* 410 */     return (this.descriptions.get(language) != null);
/*     */   }
/*     */   
/*     */   public int compareTo(Object o) {
/* 414 */     if (o instanceof SPSOption && ((SPSOption)o).getDescription() != null && this.description != null) {
/* 415 */       return this.description.compareTo(((SPSOption)o).getDescription());
/*     */     }
/* 417 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   static SPSOption getOption(SPSLanguage language, SPSOption option) {
/* 422 */     String description = (language == null) ? null : option.descriptions.get(language);
/* 423 */     return new SPSOption(option.id, description);
/*     */   }
/*     */   
/*     */   static SPSOption getOption(String category, Integer id, SPSSchemaAdapterGME adapter) {
/* 427 */     if (category.equals("VIN"))
/* 428 */       return (SPSOption)FreeOptions.getInstance(adapter).getOptionsVIN().get(id); 
/* 429 */     if (category.equals("V10"))
/* 430 */       return (SPSOption)FreeOptions.getInstance(adapter).getOptionsV10().get(id); 
/* 431 */     if (category.equals("RNG"))
/* 432 */       return (SPSOption)FreeOptions.getInstance(adapter).getOptionsRNG().get(id); 
/* 433 */     if (category.equals("HWO")) {
/* 434 */       return (SPSOption)FreeOptions.getInstance(adapter).getOptionsHWO().get(id);
/*     */     }
/* 436 */     return (SPSOption)FreeOptions.getInstance(adapter).getOptions().get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   static SPSOption getEngineOption(SPSLanguage language, String engine, SPSSchemaAdapterGME adapter) {
/* 441 */     engine = SPSVehicle.normalize(engine);
/* 442 */     SPSOption option = null;
/* 443 */     for (Iterator<SPSOption> iter = FreeOptions.getInstance(adapter).getOptions().values().iterator(); iter.hasNext(); ) {
/* 444 */       option = iter.next();
/* 445 */       if (option.descriptions == null) {
/*     */         continue;
/*     */       }
/* 448 */       String description = option.descriptions.get(language);
/* 449 */       if (description.charAt(0) == engine.charAt(0) && engine.equals(SPSVehicle.normalize(description))) {
/* 450 */         option = getOption(language, option);
/* 451 */         SPSOptionCategory category = SPSOptionCategory.getOptionCategory("ENG", adapter);
/* 452 */         if (category == null || (category.description != null && category.description.equals("internal"))) {
/* 453 */           category = SPSOptionCategory.getOptionCategory("SYS", adapter);
/*     */         }
/* 455 */         category = SPSOptionCategory.getOptionCategory(language, category, (SPSOptionGroup)null);
/* 456 */         option.setType(category);
/*     */         break;
/*     */       } 
/* 459 */       option = null;
/*     */     } 
/* 461 */     return option;
/*     */   }
/*     */   
/*     */   static SPSOption getOption(SPSLanguage language, String attribute, String value, SPSSchemaAdapterGME adapter) {
/* 465 */     SPSOptionCategory category = SPSOptionCategory.getOptionCategory(language, attribute, adapter);
/* 466 */     if (category != null) {
/* 467 */       List<SPSOption> options = category.getOptions();
/* 468 */       for (int i = 0; i < options.size(); i++) {
/* 469 */         SPSOption option = options.get(i);
/* 470 */         if (option.descriptions != null && value.equals(option.descriptions.get(language))) {
/* 471 */           option = getOption(language, option);
/* 472 */           category = SPSOptionCategory.getOptionCategory(language, category, (SPSOptionGroup)null);
/* 473 */           option.setType(category);
/* 474 */           return option;
/*     */         } 
/*     */       } 
/*     */     } 
/* 478 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static void initFreeOptions(IDatabaseLink db) throws Exception {}
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 486 */     SPSOptionCategory.init(adapter);
/* 487 */     FreeOptions.getInstance(adapter).init();
/* 488 */     SPSOptionGroup.init(adapter);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */