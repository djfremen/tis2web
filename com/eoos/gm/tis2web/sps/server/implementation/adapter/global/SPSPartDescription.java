/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSPartDescription
/*     */ {
/*  14 */   private static Logger log = Logger.getLogger(SPSPartDescription.class);
/*     */   
/*     */   public static final class StaticData {
/*  17 */     private Map descriptions = new HashMap<Object, Object>();
/*     */     
/*  19 */     private Map labels = new HashMap<Object, Object>();
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  22 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  23 */       DBMS.PreparedStatement stmt = null;
/*  24 */       ResultSet rs = null;
/*  25 */       Connection conn = null;
/*     */       try {
/*  27 */         conn = dblink.requestConnection();
/*  28 */         String sql = DBMS.getSQL(dblink, "SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description");
/*  29 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  30 */         rs = stmt.executeQuery();
/*  31 */         while (rs.next()) {
/*  32 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  33 */           String lg = rs.getString(2);
/*  34 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  35 */           if (description == null) {
/*  36 */             description = new SPSDescription();
/*  37 */             this.descriptions.put(id, description);
/*     */           } 
/*  39 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  40 */           description.add(locale, DBMS.getString(dblink, locale, rs, 3));
/*  41 */           String display = DBMS.getString(dblink, locale, rs, 4);
/*  42 */           if (display != null && display.trim().length() > 0) {
/*  43 */             SPSDescription label = (SPSDescription)this.labels.get(id);
/*  44 */             if (label == null) {
/*  45 */               label = new SPSDescription();
/*  46 */               this.labels.put(id, label);
/*     */             } 
/*  48 */             label.add(locale, display);
/*     */           } 
/*     */         } 
/*  51 */       } catch (Exception e) {
/*  52 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  55 */           if (rs != null) {
/*  56 */             rs.close();
/*     */           }
/*  58 */           if (stmt != null) {
/*  59 */             stmt.close();
/*     */           }
/*  61 */           if (conn != null) {
/*  62 */             dblink.releaseConnection(conn);
/*     */           }
/*  64 */         } catch (Exception x) {
/*  65 */           SPSPartDescription.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getDescriptions() {
/*  75 */       return this.descriptions;
/*     */     }
/*     */     
/*     */     public Map getLabels() {
/*  79 */       return this.labels;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  83 */       synchronized (adapter.getSyncObject()) {
/*  84 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  85 */         if (instance == null) {
/*  86 */           instance = new StaticData(adapter);
/*  87 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  89 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static String getDescription(SPSLanguage language, int id, SPSSchemaAdapterGlobal adapter) {
/*  95 */     String label = null;
/*  96 */     SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(Integer.valueOf(id));
/*  97 */     if (description != null) {
/*  98 */       label = description.get(language);
/*  99 */       if (label == null && language.getLocale().compareTo("en_GB") != 0) {
/* 100 */         label = description.get(SPSLanguage.getLanguage("en_GB", adapter));
/*     */       }
/*     */     } 
/* 103 */     return label;
/*     */   }
/*     */   
/*     */   static String getLabel(SPSLanguage language, int id, SPSSchemaAdapterGlobal adapter) {
/* 107 */     String label = null;
/* 108 */     SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getLabels().get(Integer.valueOf(id));
/* 109 */     if (description != null) {
/* 110 */       label = description.get(language);
/* 111 */       if (label == null && language.getLocale().compareTo("en_GB") != 0) {
/* 112 */         label = description.get(SPSLanguage.getLanguage("en_GB", adapter));
/*     */       }
/*     */     } 
/* 115 */     return label;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 119 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSPartDescription.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */