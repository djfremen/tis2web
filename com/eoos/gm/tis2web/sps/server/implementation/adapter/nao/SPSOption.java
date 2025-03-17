/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSOption;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVehicle;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SPSOption
/*     */   implements SPSOption
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String PROM_CONTROLLER = "pluggable";
/*     */   public static final String VCI_CONTROLLER = "reprogrammable";
/*  27 */   private static Logger log = Logger.getLogger(SPSOption.class);
/*     */   protected static final String OPTION_GROUP_PREFIX = "#";
/*     */   protected static final String CONTROLLER_GROUP_PREFIX = "$";
/*     */   protected transient SPSDescription descriptions;
/*     */   protected String id;
/*     */   protected String description;
/*     */   protected SPSOption type;
/*     */   
/*     */   public static final class StaticData { private Map groups;
/*     */     private Map options;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  39 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  40 */       this.options = new HashMap<Object, Object>();
/*  41 */       Connection conn = null;
/*  42 */       DBMS.PreparedStatement stmt = null;
/*  43 */       ResultSet rs = null;
/*     */       try {
/*  45 */         conn = db.requestConnection();
/*  46 */         String sql = DBMS.getSQL(db, "SELECT RPO_Code, Language_Code, Description FROM RPO_Description");
/*  47 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  48 */         rs = stmt.executeQuery();
/*  49 */         while (rs.next()) {
/*  50 */           String id = rs.getString(1).trim();
/*  51 */           if (id != null)
/*  52 */             id = id.toUpperCase(Locale.ENGLISH); 
/*  53 */           SPSOption rpo = (SPSOption)this.options.get(id);
/*  54 */           if (rpo == null) {
/*  55 */             rpo = new SPSOption(id);
/*  56 */             this.options.put(id, rpo);
/*     */           } 
/*  58 */           if (rpo.descriptions == null) {
/*  59 */             rpo.descriptions = new SPSDescription();
/*     */           }
/*  61 */           String lg = rs.getString(2);
/*     */           
/*  63 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  64 */           rpo.descriptions.add(locale, DBMS.getString(db, locale, rs, 3));
/*     */         } 
/*  66 */       } catch (Exception e) {
/*  67 */         this.options = null;
/*  68 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  71 */           if (rs != null) {
/*  72 */             rs.close();
/*     */           }
/*  74 */           if (stmt != null) {
/*  75 */             stmt.close();
/*     */           }
/*  77 */           if (this.options == null && conn != null) {
/*  78 */             db.releaseConnection(conn);
/*     */           }
/*  80 */         } catch (Exception x) {
/*  81 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*  84 */       this.groups = new HashMap<Object, Object>();
/*     */       try {
/*  86 */         String sql = DBMS.getSQL(db, "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels");
/*  87 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  88 */         rs = stmt.executeQuery();
/*  89 */         while (rs.next()) {
/*  90 */           String id = "#" + rs.getInt(1);
/*  91 */           if (id != null)
/*  92 */             id = id.toUpperCase(Locale.ENGLISH); 
/*  93 */           SPSOption group = (SPSOption)this.groups.get(id);
/*  94 */           if (group == null) {
/*  95 */             group = new SPSOption(id);
/*  96 */             this.groups.put(id, group);
/*     */           } 
/*  98 */           if (group.descriptions == null) {
/*  99 */             group.descriptions = new SPSDescription();
/*     */           }
/* 101 */           String lg = rs.getString(2);
/*     */           
/* 103 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/* 104 */           group.descriptions.add(locale, DBMS.getString(db, locale, rs, 3));
/*     */         } 
/* 106 */       } catch (Exception e) {
/* 107 */         this.options = null;
/* 108 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 111 */           if (rs != null) {
/* 112 */             rs.close();
/*     */           }
/* 114 */           if (stmt != null) {
/* 115 */             stmt.close();
/*     */           }
/* 117 */           if (this.options == null && conn != null) {
/* 118 */             db.releaseConnection(conn);
/*     */           }
/* 120 */         } catch (Exception x) {
/* 121 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */       try {
/* 125 */         String sql = DBMS.getSQL(db, "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids");
/* 126 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 127 */         rs = stmt.executeQuery();
/* 128 */         while (rs.next()) {
/* 129 */           String id = rs.getString(1).trim();
/* 130 */           if (id != null)
/* 131 */             id = id.toUpperCase(Locale.ENGLISH); 
/* 132 */           SPSOption rpo = (SPSOption)this.options.get(id);
/* 133 */           if (rpo == null) {
/* 134 */             if (db.getDBMS() == 3) {
/* 135 */               SPSOption.log.warn("unknown post-option '" + id + "'.");
/*     */               continue;
/*     */             } 
/* 138 */             throw new Exception("unknown post-option '" + id + "'.");
/*     */           } 
/*     */           
/* 141 */           id = "#" + rs.getInt(2);
/* 142 */           if (id != null)
/* 143 */             id = id.toUpperCase(Locale.ENGLISH); 
/* 144 */           SPSOption group = (SPSOption)this.groups.get(id);
/* 145 */           if (group == null) {
/* 146 */             if (db.getDBMS() == 3) {
/* 147 */               SPSOption.log.warn("unknown post-option group " + id + ".");
/*     */               continue;
/*     */             } 
/* 150 */             throw new Exception("unknown post-option group " + id + ".");
/*     */           } 
/*     */           
/* 153 */           rpo.setType(group);
/*     */         } 
/* 155 */       } catch (Exception e) {
/* 156 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 159 */           if (rs != null) {
/* 160 */             rs.close();
/*     */           }
/* 162 */           if (stmt != null) {
/* 163 */             stmt.close();
/*     */           }
/* 165 */           if (conn != null) {
/* 166 */             db.releaseConnection(conn);
/*     */           }
/* 168 */         } catch (Exception x) {
/* 169 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getGroups() {
/* 179 */       return this.groups;
/*     */     }
/*     */     
/*     */     public Map getOptions() {
/* 183 */       return this.options;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 187 */       synchronized (adapter.getSyncObject()) {
/* 188 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 189 */         if (instance == null) {
/* 190 */           instance = new StaticData(adapter);
/* 191 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 193 */         return instance;
/*     */       } 
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
/*     */   SPSOption(String id) {
/* 207 */     this.id = id;
/*     */   }
/*     */   
/*     */   SPSOption(String id, String description, SPSOption type) {
/* 211 */     this.id = id;
/* 212 */     this.description = description;
/* 213 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SPSOption(String id, String description) {
/* 219 */     this.id = id;
/* 220 */     this.description = description;
/*     */   }
/*     */   
/*     */   void setType(SPSOption type) {
/* 224 */     this.type = type;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 228 */     return this.id.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 232 */     return (object != null && object instanceof SPSOption && ((SPSOption)object).getID().equals(this.id));
/*     */   }
/*     */   
/*     */   public String getID() {
/* 236 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 240 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 244 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getHelp(SPSVehicle vehicle) {
/* 248 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 252 */     return this.id + ":" + this.description;
/*     */   }
/*     */   
/*     */   public SPSOption getType() {
/* 256 */     return this.type;
/*     */   }
/*     */   
/*     */   static boolean isControllerOption(SPSOption option) {
/* 260 */     return (option.getType() == null) ? false : option.getType().getID().equals("$");
/*     */   }
/*     */   
/*     */   static SPSOption getControllerOption(SPSLanguage language, String id) {
/* 264 */     String label = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.controller-type");
/* 265 */     SPSOption group = new SPSOption("$", label);
/*     */     
/* 267 */     label = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.controller-type." + id);
/* 268 */     SPSOption option = new SPSOption("$" + id, label);
/* 269 */     option.setType(group);
/* 270 */     return option;
/*     */   }
/*     */   
/*     */   static SPSOption getRPO(SPSLanguage language, String id, SPSSchemaAdapterNAO adapter) {
/* 274 */     if (id != null)
/* 275 */       id = id.toUpperCase(Locale.ENGLISH); 
/* 276 */     SPSOption option = (SPSOption)StaticData.getInstance(adapter).getOptions().get(id);
/* 277 */     if (option != null)
/*     */     {
/*     */       
/* 280 */       option = new SPSOption(id, option.descriptions.get(language), (SPSOption)option.getType());
/*     */     }
/* 282 */     return option;
/*     */   }
/*     */   
/*     */   static SPSOption getOptionGroup(SPSLanguage language, int id, SPSSchemaAdapterNAO adapter) {
/* 286 */     return getOptionGroup(language, "#" + id, adapter);
/*     */   }
/*     */   
/*     */   static SPSOption getOptionGroup(SPSLanguage language, String id, SPSSchemaAdapterNAO adapter) {
/* 290 */     if (id != null)
/* 291 */       id = id.toUpperCase(Locale.ENGLISH); 
/* 292 */     SPSOption option = (SPSOption)StaticData.getInstance(adapter).getGroups().get(id);
/* 293 */     if (option != null) {
/* 294 */       option = new SPSOption(id, option.descriptions.get(language), (SPSOption)option.getType());
/*     */     }
/* 296 */     return option;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 300 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static List filter(List<SPSOption> options, List list) {
/* 306 */     if (list == null) {
/* 307 */       return null;
/*     */     }
/* 309 */     if (options == null) {
/* 310 */       return list;
/*     */     }
/* 312 */     Set<String> selections = new HashSet();
/* 313 */     for (int i = 0; i < options.size(); i++) {
/* 314 */       SPSOption selection = options.get(i);
/* 315 */       if (selection.getType() != null) {
/* 316 */         selections.add(selection.getType().getID());
/*     */       }
/*     */     } 
/* 319 */     Iterator<SPSOption> it = list.iterator();
/* 320 */     while (it.hasNext()) {
/* 321 */       SPSOption option = it.next();
/* 322 */       if (option.getType() != null && selections.contains(option.getType().getID())) {
/* 323 */         it.remove(); continue;
/* 324 */       }  if (contains(options, option)) {
/* 325 */         it.remove();
/*     */       }
/*     */     } 
/* 328 */     return (list.size() > 0) ? list : null;
/*     */   }
/*     */   
/*     */   static boolean contains(List<SPSOption> options, SPSOption option) {
/* 332 */     for (int i = 0; i < options.size(); i++) {
/* 333 */       SPSOption selection = options.get(i);
/* 334 */       if (selection.equals(option)) {
/* 335 */         return true;
/*     */       }
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean contains(List<SPSOption> options, List list) {
/* 343 */     for (int i = 0; i < options.size(); i++) {
/* 344 */       SPSOption selection = options.get(i);
/* 345 */       if (!isControllerOption(selection))
/*     */       {
/*     */         
/* 348 */         if (!contains(list, selection))
/* 349 */           return false; 
/*     */       }
/*     */     } 
/* 352 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean match(List options, List<SPSOption> list) {
/* 357 */     if (list == null) {
/* 358 */       return true;
/*     */     }
/* 360 */     if (options == null) {
/* 361 */       return true;
/*     */     }
/* 363 */     for (int i = 0; i < list.size(); i++) {
/* 364 */       SPSOption option = list.get(i);
/* 365 */       if (isSelectiveOption(list, option))
/*     */       {
/*     */         
/* 368 */         if (!(option instanceof SPSSpecialOption))
/*     */         {
/*     */           
/* 371 */           if (conflicts(options, option))
/* 372 */             return false;  } 
/*     */       }
/*     */     } 
/* 375 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isSelectiveOption(List<SPSOption> options, SPSOption option) {
/* 379 */     SPSOption group = (SPSOption)option.getType();
/* 380 */     if (group.getID().startsWith("#16") || group.getID().startsWith("#3")) {
/* 381 */       for (int i = 0; i < options.size(); i++) {
/* 382 */         SPSOption alternative = options.get(i);
/* 383 */         if (group.equals(alternative.getType()) && !alternative.equals(option)) {
/* 384 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 388 */     return true;
/*     */   }
/*     */   
/*     */   static boolean conflicts(List<SPSOption> options, SPSOption option) {
/* 392 */     for (int i = 0; i < options.size(); i++) {
/* 393 */       SPSOption selection = options.get(i);
/* 394 */       if (selection.getType().equals(option.getType())) {
/* 395 */         return !selection.equals(option);
/*     */       }
/*     */     } 
/* 398 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */