/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
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
/*     */ public class SPSOption
/*     */   implements SPSOption
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String VCI_CONTROLLER = "reprogrammable";
/*  25 */   private static Logger log = Logger.getLogger(SPSOption.class);
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
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  37 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  38 */       this.options = new HashMap<Object, Object>();
/*  39 */       Connection conn = null;
/*  40 */       DBMS.PreparedStatement stmt = null;
/*  41 */       ResultSet rs = null;
/*     */       try {
/*  43 */         conn = db.requestConnection();
/*  44 */         String sql = DBMS.getSQL(db, "SELECT RPO_Code, Language_Code, Description FROM RPO_Description");
/*  45 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  46 */         rs = stmt.executeQuery();
/*  47 */         while (rs.next()) {
/*  48 */           String id = rs.getString(1).trim();
/*  49 */           if (id != null)
/*  50 */             id = id.toUpperCase(Locale.ENGLISH); 
/*  51 */           SPSOption rpo = (SPSOption)this.options.get(id);
/*  52 */           if (rpo == null) {
/*  53 */             rpo = new SPSOption(id);
/*  54 */             this.options.put(id, rpo);
/*     */           } 
/*  56 */           if (rpo.descriptions == null) {
/*  57 */             rpo.descriptions = new SPSDescription();
/*     */           }
/*  59 */           String lg = rs.getString(2);
/*     */           
/*  61 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  62 */           rpo.descriptions.add(locale, DBMS.getString(db, locale, rs, 3));
/*     */         } 
/*  64 */       } catch (Exception e) {
/*  65 */         this.options = null;
/*  66 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  69 */           if (rs != null) {
/*  70 */             rs.close();
/*     */           }
/*  72 */           if (stmt != null) {
/*  73 */             stmt.close();
/*     */           }
/*  75 */           if (this.options == null && conn != null) {
/*  76 */             db.releaseConnection(conn);
/*     */           }
/*  78 */         } catch (Exception x) {
/*  79 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*  82 */       this.groups = new HashMap<Object, Object>();
/*     */       try {
/*  84 */         String sql = DBMS.getSQL(db, "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels");
/*  85 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  86 */         rs = stmt.executeQuery();
/*  87 */         while (rs.next()) {
/*  88 */           String id = "#" + rs.getInt(1);
/*  89 */           if (id != null)
/*  90 */             id = id.toUpperCase(Locale.ENGLISH); 
/*  91 */           SPSOption group = (SPSOption)this.groups.get(id);
/*  92 */           if (group == null) {
/*  93 */             group = new SPSOption(id);
/*  94 */             this.groups.put(id, group);
/*     */           } 
/*  96 */           if (group.descriptions == null) {
/*  97 */             group.descriptions = new SPSDescription();
/*     */           }
/*  99 */           String lg = rs.getString(2);
/*     */           
/* 101 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/* 102 */           group.descriptions.add(locale, DBMS.getString(db, locale, rs, 3));
/*     */         } 
/* 104 */       } catch (Exception e) {
/* 105 */         this.options = null;
/* 106 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 109 */           if (rs != null) {
/* 110 */             rs.close();
/*     */           }
/* 112 */           if (stmt != null) {
/* 113 */             stmt.close();
/*     */           }
/* 115 */           if (this.options == null && conn != null) {
/* 116 */             db.releaseConnection(conn);
/*     */           }
/* 118 */         } catch (Exception x) {
/* 119 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */       try {
/* 123 */         String sql = DBMS.getSQL(db, "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids");
/* 124 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 125 */         rs = stmt.executeQuery();
/* 126 */         while (rs.next()) {
/* 127 */           String id = rs.getString(1).trim();
/* 128 */           if (id != null)
/* 129 */             id = id.toUpperCase(Locale.ENGLISH); 
/* 130 */           SPSOption rpo = (SPSOption)this.options.get(id);
/* 131 */           if (rpo == null) {
/* 132 */             if (db.getDBMS() == 3) {
/* 133 */               SPSOption.log.warn("unknown post-option '" + id + "'.");
/*     */               continue;
/*     */             } 
/* 136 */             throw new Exception("unknown post-option '" + id + "'.");
/*     */           } 
/*     */           
/* 139 */           id = "#" + rs.getInt(2);
/* 140 */           if (id != null)
/* 141 */             id = id.toUpperCase(Locale.ENGLISH); 
/* 142 */           SPSOption group = (SPSOption)this.groups.get(id);
/* 143 */           if (group == null) {
/* 144 */             if (db.getDBMS() == 3) {
/* 145 */               SPSOption.log.warn("unknown post-option group " + id + ".");
/*     */               continue;
/*     */             } 
/* 148 */             throw new Exception("unknown post-option group " + id + ".");
/*     */           } 
/*     */           
/* 151 */           rpo.setType(group);
/*     */         } 
/* 153 */       } catch (Exception e) {
/* 154 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 157 */           if (rs != null) {
/* 158 */             rs.close();
/*     */           }
/* 160 */           if (stmt != null) {
/* 161 */             stmt.close();
/*     */           }
/* 163 */           if (conn != null) {
/* 164 */             db.releaseConnection(conn);
/*     */           }
/* 166 */         } catch (Exception x) {
/* 167 */           SPSOption.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getGroups() {
/* 177 */       return this.groups;
/*     */     }
/*     */     
/*     */     public Map getOptions() {
/* 181 */       return this.options;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/* 185 */       synchronized (adapter.getSyncObject()) {
/* 186 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 187 */         if (instance == null) {
/* 188 */           instance = new StaticData(adapter);
/* 189 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 191 */         return instance;
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
/* 205 */     this.id = id;
/*     */   }
/*     */   
/*     */   SPSOption(String id, String description, SPSOption type) {
/* 209 */     this.id = id;
/* 210 */     this.description = description;
/* 211 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SPSOption(String id, String description) {
/* 217 */     this.id = id;
/* 218 */     this.description = description;
/*     */   }
/*     */   
/*     */   void setType(SPSOption type) {
/* 222 */     this.type = type;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 226 */     return this.id.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 230 */     return (object != null && object instanceof SPSOption && ((SPSOption)object).getID().equals(this.id));
/*     */   }
/*     */   
/*     */   public String getID() {
/* 234 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 238 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 242 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getHelp(SPSVehicle vehicle) {
/* 246 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 250 */     return this.id + ":" + this.description;
/*     */   }
/*     */   
/*     */   public SPSOption getType() {
/* 254 */     return this.type;
/*     */   }
/*     */   
/*     */   static boolean isControllerOption(SPSOption option) {
/* 258 */     return (option.getType() == null) ? false : option.getType().getID().equals("$");
/*     */   }
/*     */   
/*     */   static SPSOption getControllerOption(SPSLanguage language, String id) {
/* 262 */     String label = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.controller-type");
/* 263 */     SPSOption group = new SPSOption("$", label);
/*     */     
/* 265 */     label = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.controller-type." + id);
/* 266 */     SPSOption option = new SPSOption("$" + id, label);
/* 267 */     option.setType(group);
/* 268 */     return option;
/*     */   }
/*     */   
/*     */   static SPSOption getRPO(SPSLanguage language, String id, SPSSchemaAdapterGlobal adapter) {
/* 272 */     if (id != null)
/* 273 */       id = id.toUpperCase(Locale.ENGLISH); 
/* 274 */     SPSOption option = (SPSOption)StaticData.getInstance(adapter).getOptions().get(id);
/* 275 */     if (option != null)
/*     */     {
/*     */       
/* 278 */       option = new SPSOption(id, option.descriptions.get(language), (SPSOption)option.getType());
/*     */     }
/* 280 */     return option;
/*     */   }
/*     */   
/*     */   static SPSOption getOptionGroup(SPSLanguage language, int id, SPSSchemaAdapterGlobal adapter) {
/* 284 */     return getOptionGroup(language, "#" + id, adapter);
/*     */   }
/*     */   
/*     */   static SPSOption getOptionGroup(SPSLanguage language, String id, SPSSchemaAdapterGlobal adapter) {
/* 288 */     if (id != null)
/* 289 */       id = id.toUpperCase(Locale.ENGLISH); 
/* 290 */     SPSOption option = (SPSOption)StaticData.getInstance(adapter).getGroups().get(id);
/* 291 */     if (option != null) {
/* 292 */       option = new SPSOption(id, option.descriptions.get(language), (SPSOption)option.getType());
/*     */     }
/* 294 */     return option;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 298 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static List filter(List<SPSOption> options, List list) {
/* 304 */     if (list == null) {
/* 305 */       return null;
/*     */     }
/* 307 */     if (options == null) {
/* 308 */       return list;
/*     */     }
/* 310 */     Set<String> selections = new HashSet();
/* 311 */     for (int i = 0; i < options.size(); i++) {
/* 312 */       SPSOption selection = options.get(i);
/* 313 */       if (selection.getType() != null) {
/* 314 */         selections.add(selection.getType().getID());
/*     */       }
/*     */     } 
/* 317 */     Iterator<SPSOption> it = list.iterator();
/* 318 */     while (it.hasNext()) {
/* 319 */       SPSOption option = it.next();
/* 320 */       if (option.getType() != null && selections.contains(option.getType().getID())) {
/* 321 */         it.remove(); continue;
/* 322 */       }  if (contains(options, option)) {
/* 323 */         it.remove();
/*     */       }
/*     */     } 
/* 326 */     return (list.size() > 0) ? list : null;
/*     */   }
/*     */   
/*     */   static boolean contains(List<SPSOption> options, SPSOption option) {
/* 330 */     for (int i = 0; i < options.size(); i++) {
/* 331 */       SPSOption selection = options.get(i);
/* 332 */       if (selection.equals(option)) {
/* 333 */         return true;
/*     */       }
/*     */     } 
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean contains(List<SPSOption> options, List list) {
/* 341 */     for (int i = 0; i < options.size(); i++) {
/* 342 */       SPSOption selection = options.get(i);
/* 343 */       if (!isControllerOption(selection))
/*     */       {
/*     */         
/* 346 */         if (!contains(list, selection))
/* 347 */           return false; 
/*     */       }
/*     */     } 
/* 350 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean match(List options, List<SPSOption> list) {
/* 355 */     if (list == null) {
/* 356 */       return true;
/*     */     }
/* 358 */     if (options == null) {
/* 359 */       return true;
/*     */     }
/* 361 */     for (int i = 0; i < list.size(); i++) {
/* 362 */       SPSOption option = list.get(i);
/* 363 */       if (conflicts(options, option)) {
/* 364 */         return false;
/*     */       }
/*     */     } 
/* 367 */     return true;
/*     */   }
/*     */   
/*     */   static boolean conflicts(List<SPSOption> options, SPSOption option) {
/* 371 */     for (int i = 0; i < options.size(); i++) {
/* 372 */       SPSOption selection = options.get(i);
/* 373 */       if (selection.getType().equals(option.getType())) {
/* 374 */         return !selection.equals(option);
/*     */       }
/*     */     } 
/* 377 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */