/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSOptionCategory
/*     */   extends SPSOption
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String RNG_OPTION = "RNG";
/*     */   public static final String VIN_OPTION = "VIN";
/*     */   public static final String V10_OPTION = "V10";
/*     */   public static final String ENG_OPTION = "ENG";
/*     */   public static final String SYS_OPTION = "SYS";
/*     */   public static final String HWO_OPTION = "HWO";
/*     */   protected transient List options;
/*     */   protected transient String vit1type;
/*     */   protected transient String parameter;
/*     */   protected transient int position;
/*     */   protected transient int order;
/*     */   
/*     */   public static final class Categories {
/*     */     private Map categories;
/*     */     
/*     */     private Categories(SPSSchemaAdapterGME adapter) {
/*  34 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  35 */       boolean error = false;
/*  36 */       this.categories = new HashMap<Object, Object>();
/*  37 */       Connection conn = null;
/*  38 */       DBMS.PreparedStatement stmt = null;
/*  39 */       ResultSet rs = null;
/*     */       try {
/*  41 */         conn = db.requestConnection();
/*  42 */         String sql = DBMS.getSQL(db, "SELECT CategoryCode, LanguageID, Description FROM SPS_Categories");
/*  43 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  44 */         rs = stmt.executeQuery();
/*  45 */         while (rs.next()) {
/*  46 */           String id = rs.getString(1).trim();
/*  47 */           String lg = rs.getString(2).trim();
/*  48 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*  49 */           String description = DBMS.getString(db, language, rs, 3);
/*  50 */           SPSOption category = (SPSOption)this.categories.get(id);
/*  51 */           if (category == null) {
/*  52 */             category = new SPSOptionCategory(id);
/*  53 */             this.categories.put(id, category);
/*     */           } 
/*  55 */           category.descriptions.add(language, description);
/*     */         } 
/*  57 */       } catch (Exception e) {
/*  58 */         error = true;
/*  59 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  62 */           if (rs != null) {
/*  63 */             JDBCUtil.close(rs);
/*     */           }
/*  65 */           if (stmt != null) {
/*     */             try {
/*  67 */               stmt.close();
/*  68 */             } catch (Exception xx) {}
/*     */           }
/*     */           
/*  71 */           if (error && conn != null) {
/*  72 */             db.releaseConnection(conn);
/*     */           }
/*  74 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/*  78 */         String sql = DBMS.getSQL(db, "SELECT CategoryCode, VITType, CatValue, Position FROM SPS_VITCategories");
/*  79 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  80 */         rs = stmt.executeQuery();
/*  81 */         while (rs.next()) {
/*  82 */           String id = rs.getString(1).trim();
/*  83 */           String vit1type = rs.getString(2).trim();
/*  84 */           String parameter = rs.getString(3).trim();
/*  85 */           int position = rs.getInt(4);
/*  86 */           SPSOption category = (SPSOption)this.categories.get(id);
/*  87 */           if (category == null) {
/*  88 */             category = new SPSOptionCategory(id, vit1type, parameter, position);
/*  89 */             this.categories.put(id, category);
/*     */           } 
/*     */         } 
/*  92 */       } catch (Exception e) {
/*     */       
/*     */       } finally {
/*     */         try {
/*  96 */           if (rs != null) {
/*  97 */             JDBCUtil.close(rs);
/*     */           }
/*  99 */           if (stmt != null) {
/*     */             try {
/* 101 */               stmt.close();
/* 102 */             } catch (Exception xx) {}
/*     */           }
/*     */           
/* 105 */           if (conn != null) {
/* 106 */             db.releaseConnection(conn);
/*     */           }
/* 108 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static Categories getInstance(SPSSchemaAdapterGME adapter) {
/* 118 */       synchronized (adapter.getSyncObject()) {
/* 119 */         Categories instance = (Categories)adapter.getObject(Categories.class);
/* 120 */         if (instance == null) {
/* 121 */           instance = new Categories(adapter);
/* 122 */           adapter.storeObject(Categories.class, instance);
/*     */         } 
/* 124 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getCategories() {
/* 129 */       return this.categories;
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
/*     */   void setOrder(int order) {
/* 145 */     this.order = order;
/*     */   }
/*     */   
/*     */   int getOrder() {
/* 149 */     return this.order;
/*     */   }
/*     */   
/*     */   public SPSOptionCategory(String id) {
/* 153 */     super(id);
/* 154 */     this.options = new ArrayList();
/*     */   }
/*     */   
/*     */   public SPSOptionCategory(String id, String description) {
/* 158 */     super(id, description);
/* 159 */     this.options = new ArrayList();
/*     */   }
/*     */   
/*     */   public SPSOptionCategory(String id, String vit1type, String parameter, int position) {
/* 163 */     this(id);
/* 164 */     this.vit1type = parameter;
/* 165 */     this.parameter = parameter;
/* 166 */     this.position = position;
/*     */   }
/*     */   
/*     */   protected void add(SPSOption option) {
/* 170 */     this.options.add(option);
/*     */   }
/*     */   
/*     */   protected void add(List<SPSOption> others) {
/* 174 */     for (int i = 0; i < others.size(); i++) {
/* 175 */       SPSOption option = others.get(i);
/* 176 */       if (!this.options.contains(option)) {
/* 177 */         this.options.add(option);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   List getOptions() {
/* 183 */     return this.options;
/*     */   }
/*     */   
/*     */   boolean match(SPSOption selection) {
/* 187 */     for (int i = 0; i < this.options.size(); i++) {
/* 188 */       SPSOption option = this.options.get(i);
/* 189 */       if (option.equals(selection)) {
/* 190 */         return true;
/*     */       }
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   boolean isValid() {
/* 197 */     return (this.description != null && this.options != null && this.options.size() > 0);
/*     */   }
/*     */   
/*     */   boolean isOptionCategory() {
/* 201 */     return (!isHWOCategory() && !this.id.equals("RNG") && !this.id.equals("VIN") && !this.id.equals("V10") && this.vit1type == null);
/*     */   }
/*     */   
/*     */   boolean isHWOCategory() {
/* 205 */     return this.id.equals("HWO");
/*     */   }
/*     */ 
/*     */   
/*     */   static SPSOptionCategory createOptionCategory(String id, SPSSchemaAdapterGME adapter) {
/* 210 */     SPSOptionCategory category = new SPSOptionCategory(id);
/* 211 */     Categories.getInstance(adapter).getCategories().put(id, category);
/* 212 */     return category;
/*     */   }
/*     */   
/*     */   static SPSOption getOptionCategory(SPSOptionCategory master, SPSOptionCategory category) {
/* 216 */     SPSOptionCategory merge = new SPSOptionCategory(master.id, master.description);
/* 217 */     merge.options.addAll(master.options);
/* 218 */     merge.add(category.options);
/* 219 */     return merge;
/*     */   }
/*     */   
/*     */   static SPSOptionCategory getOptionCategory(SPSLanguage language, SPSOptionCategory category, SPSOptionGroup group) {
/* 223 */     String description = null;
/* 224 */     if (category.descriptions == null) {
/* 225 */       description = "internal";
/*     */     }
/*     */     else {
/*     */       
/* 229 */       description = (language == null) ? null : category.descriptions.get(language);
/*     */     } 
/* 231 */     SPSOptionCategory copy = new SPSOptionCategory(category.id, description);
/* 232 */     List<SPSOption> options = category.getOptions();
/* 233 */     if (options != null) {
/* 234 */       for (int i = 0; i < options.size(); i++) {
/* 235 */         SPSOption option = options.get(i);
/* 236 */         option = SPSOption.getOption(language, option);
/* 237 */         if (option.isValid() && (group == null || group.accept(option))) {
/* 238 */           option.setType(copy);
/* 239 */           copy.add(option);
/*     */         } 
/*     */       } 
/*     */     }
/* 243 */     return copy;
/*     */   }
/*     */   
/*     */   static SPSOptionCategory getOptionCategory(String id, SPSSchemaAdapterGME adapter) {
/* 247 */     SPSOptionCategory category = (SPSOptionCategory)Categories.getInstance(adapter).getCategories().get(id);
/* 248 */     return (category != null) ? category : new SPSOptionCategory(id, "internal");
/*     */   }
/*     */ 
/*     */   
/*     */   static SPSOptionCategory getOptionCategory(SPSLanguage language, String attribute, SPSSchemaAdapterGME adapter) {
/* 253 */     Iterator<SPSOptionCategory> it = Categories.getInstance(adapter).getCategories().values().iterator();
/* 254 */     while (it.hasNext()) {
/* 255 */       SPSOptionCategory category = it.next();
/* 256 */       if (category.descriptions != null && attribute.equals(category.descriptions.get(language))) {
/* 257 */         return category;
/*     */       }
/*     */     } 
/* 260 */     return null;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 264 */     Categories.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionCategory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */