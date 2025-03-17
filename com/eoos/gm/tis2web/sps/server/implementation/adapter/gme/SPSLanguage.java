/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSLanguage extends SPSLanguage {
/*  15 */   private static final Logger log = Logger.getLogger(SPSLanguage.class);
/*     */   
/*  17 */   protected static HashMap localescpMap = new HashMap<Object, Object>();
/*  18 */   protected static HashMap localesTlaMap = new HashMap<Object, Object>();
/*     */   
/*     */   public static final class StaticData
/*     */   {
/*     */     private boolean hasTLA;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGME adapter) {
/*  25 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  26 */       if (db.getDBMS() != 2 && db.getDBMS() != 3) {
/*     */         return;
/*     */       }
/*  29 */       Connection conn = null;
/*  30 */       Statement stmt = null;
/*  31 */       ResultSet rs = null;
/*     */       try {
/*  33 */         conn = db.requestConnection();
/*  34 */         stmt = conn.createStatement();
/*  35 */         String sql = DBMS.getSQL(db, "SELECT DISTINCT LanguageAcronym, ACP, LCID FROM WinLanguages");
/*  36 */         rs = stmt.executeQuery(sql);
/*  37 */         while (rs.next()) {
/*  38 */           String languageAcronym = rs.getString(1).trim();
/*  39 */           SPSLanguage l = (SPSLanguage)SPSLanguage.getLocales().get(languageAcronym);
/*     */           
/*  41 */           if (l == null) {
/*  42 */             l = (SPSLanguage)SPSLanguage.getLocalesTLA().get(languageAcronym);
/*     */           }
/*     */           
/*  45 */           if (l != null) {
/*  46 */             String cp = Integer.valueOf(rs.getInt(2)).toString();
/*  47 */             SPSLanguage.localescpMap.put(l.getLocale(), cp);
/*  48 */             SPSLanguage.localesTlaMap.put(l.getLocale(), l.getLocaleTLA());
/*     */           } 
/*     */           
/*  51 */           if (languageAcronym.equalsIgnoreCase("en_GB")) {
/*  52 */             DBMS.setTransbaseVersion(DBMS.TRANSBASE_VERSION_NEW); continue;
/*  53 */           }  if (languageAcronym.equalsIgnoreCase("ENU")) {
/*  54 */             DBMS.setTransbaseVersion(DBMS.TRANSBASE_VERSION_OLD);
/*     */           }
/*     */         }
/*     */       
/*  58 */       } catch (Exception e) {
/*  59 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  62 */           if (rs != null) {
/*  63 */             rs.close();
/*     */           }
/*  65 */           if (stmt != null) {
/*  66 */             stmt.close();
/*     */           }
/*  68 */           if (conn != null) {
/*  69 */             db.releaseConnection(conn);
/*     */           }
/*  71 */         } catch (Exception e) {
/*  72 */           SPSLanguage.log.warn("unable to clean up, ignoring  - exception: " + e, e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGME adapter) {
/*  83 */       synchronized (adapter.getSyncObject()) {
/*  84 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  85 */         if (instance == null) {
/*  86 */           instance = new StaticData(adapter);
/*  87 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  89 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasTLA() {
/*  94 */       return this.hasTLA;
/*     */     }
/*     */   }
/*     */   
/*     */   public static SPSLanguage getLanguage(String language, SPSSchemaAdapterGME adapter) {
/*  99 */     boolean hasTLA = (adapter == null) ? false : StaticData.getInstance(adapter).hasTLA();
/*     */     
/* 101 */     SPSLanguage lg = (SPSLanguage)getLocales().get(language);
/* 102 */     if (lg == null) {
/* 103 */       lg = (SPSLanguage)getLocalesTLA().get(language);
/*     */     }
/* 105 */     return (lg == null) ? null : new SPSLanguage(lg, hasTLA ? lg.getLocaleTLA() : language);
/*     */   }
/*     */   
/*     */   public static SPSLanguage getLanguage(Locale locale, SPSSchemaAdapterGME adapter) {
/* 109 */     boolean hasTLA = (adapter == null) ? false : StaticData.getInstance(adapter).hasTLA();
/* 110 */     String localeCode = lookupLocale(locale);
/* 111 */     SPSLanguage lg = (SPSLanguage)getLocales().get(localeCode);
/* 112 */     return new SPSLanguage(lg, hasTLA ? "ENU" : "en_GB");
/*     */   }
/*     */   
/*     */   protected SPSLanguage(SPSLanguage language, String dbid) {
/* 116 */     super(language, dbid);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 121 */     SPSLanguage.init();
/* 122 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCP(String locale) {
/* 127 */     return (String)localescpMap.get(locale);
/*     */   }
/*     */   
/*     */   public static String getLocaleTLA(String locale) {
/* 131 */     return (String)localesTlaMap.get(locale);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSLanguage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */