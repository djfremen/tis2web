/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSLanguage
/*     */   extends SPSLanguage {
/*     */   public static class StaticData {
/*  19 */     private Map localesLCID = new HashMap<Object, Object>();
/*     */     
/*     */     private boolean hasLCID = false;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  24 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  25 */       if (db.getDBMS() != 2 && db.getDBMS() != 3) {
/*     */         return;
/*     */       }
/*  28 */       Connection conn = null;
/*  29 */       Statement stmt = null;
/*  30 */       ResultSet rs = null;
/*     */       try {
/*  32 */         conn = db.requestConnection();
/*  33 */         stmt = conn.createStatement();
/*  34 */         rs = stmt.executeQuery("SELECT DISTINCT Language_Acronym, ACP, LCID FROM Win_Languages");
/*  35 */         while (rs.next()) {
/*  36 */           SPSLanguage l = (SPSLanguage)SPSLanguage.getLocalesTLA().get(rs.getString("Language_Acronym").trim());
/*  37 */           if (l != null) {
/*  38 */             int lcid = rs.getInt("LCID");
/*  39 */             SPSLanguage lg = new SPSLanguage(l, Integer.toString(lcid), null);
/*  40 */             lg.setLCID(lcid);
/*  41 */             this.localesLCID.put(lg.getID(), lg);
/*  42 */             lg.setCP(rs.getInt("ACP"));
/*     */           } 
/*     */         } 
/*  45 */         this.hasLCID = true;
/*  46 */       } catch (Exception e) {
/*  47 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  50 */           if (rs != null) {
/*  51 */             rs.close();
/*     */           }
/*  53 */           if (stmt != null) {
/*  54 */             stmt.close();
/*     */           }
/*  56 */           if (conn != null) {
/*  57 */             db.releaseConnection(conn);
/*     */           }
/*  59 */         } catch (Exception x) {
/*  60 */           SPSLanguage.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getLocalesLCID() {
/*  71 */       return this.localesLCID;
/*     */     }
/*     */     
/*     */     public boolean hasLCID() {
/*  75 */       return this.hasLCID;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  79 */       synchronized (adapter.getSyncObject()) {
/*  80 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  81 */         if (instance == null) {
/*  82 */           instance = new StaticData(adapter);
/*  83 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  85 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*  90 */   private static Logger log = Logger.getLogger(SPSLanguage.class);
/*     */   
/*     */   protected int lcid;
/*     */   protected int cp;
/*     */   
/*     */   protected void setLCID(int lcid) {
/*  96 */     this.lcid = lcid;
/*     */   }
/*     */   
/*     */   public int getLocaleLCID() {
/* 100 */     return this.lcid;
/*     */   }
/*     */   
/*     */   protected void setCP(int cp) {
/* 104 */     this.cp = cp;
/*     */   }
/*     */   
/*     */   public int getCP() {
/* 108 */     return this.cp;
/*     */   }
/*     */   
/*     */   public static SPSLanguage getLanguage(String language, SPSSchemaAdapterGlobal adapter) {
/* 112 */     SPSLanguage lg = (SPSLanguage)getLocales().get(language);
/* 113 */     if (lg == null) {
/* 114 */       lg = (SPSLanguage)StaticData.getInstance(adapter).getLocalesLCID().get(language);
/*     */     }
/* 116 */     return (lg == null) ? null : new SPSLanguage(lg, language, adapter);
/*     */   }
/*     */   
/*     */   public static SPSLanguage getLanguage(Locale locale, SPSSchemaAdapterGlobal adapter) {
/* 120 */     String localeCode = lookupLocale(locale);
/* 121 */     SPSLanguage lg = (SPSLanguage)getLocales().get(localeCode);
/* 122 */     return new SPSLanguage(lg, StaticData.getInstance(adapter).hasLCID() ? "1033" : "en_GB", adapter);
/*     */   }
/*     */   
/*     */   public static SPSLanguage getLanguagePROM(SPSLanguage language, SPSSchemaAdapterGlobal adapter) {
/* 126 */     if (language.getLocale().startsWith("fr_")) {
/* 127 */       return language;
/*     */     }
/* 129 */     return getLanguage(Locale.US, adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSLanguage(SPSLanguage language, String dbid, SPSSchemaAdapterGlobal adapter) {
/* 134 */     super(language, dbid);
/*     */     
/* 136 */     if (adapter != null && StaticData.getInstance(adapter).hasLCID()) {
/* 137 */       Iterator<Map.Entry> it = StaticData.getInstance(adapter).getLocalesLCID().entrySet().iterator();
/* 138 */       while (it.hasNext()) {
/* 139 */         Map.Entry entry = it.next();
/* 140 */         SPSLanguage dblang = (SPSLanguage)entry.getValue();
/* 141 */         if (dblang.getLocale().equals(this.locale)) {
/* 142 */           this.dbid = Integer.toString(dblang.lcid);
/* 143 */           this.cp = dblang.cp;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 152 */     SPSLanguage.init();
/* 153 */     StaticData.getInstance(adapter);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSLanguage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */