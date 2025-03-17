/*     */ package com.eoos.gm.tis2web.frame.export.common.locale;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleInfoProvider
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(LocaleInfoProvider.class);
/*     */   
/*     */   protected HashMap locales;
/*     */   
/*     */   protected ArrayList localesID;
/*     */   
/*     */   protected HashMap localesTLA;
/*     */   
/*     */   protected HashMap localesSCDS;
/*     */ 
/*     */   
/*     */   public LocaleInfoProvider() {
/*     */     DatabaseLink databaseLink;
/*  36 */     Connection conn = null;
/*  37 */     IDatabaseLink db = null;
/*     */     try {
/*  39 */       databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "frame.adapter.lg.db");
/*  40 */       conn = databaseLink.requestConnection();
/*  41 */       load(conn);
/*  42 */     } catch (Exception e) {
/*  43 */       log.error("unable to init LocaleInfo - error:" + e, e);
/*  44 */       throw new ExceptionWrapper(e);
/*     */     } finally {
/*  46 */       if (databaseLink != null && conn != null) {
/*     */         try {
/*  48 */           databaseLink.releaseConnection(conn);
/*  49 */         } catch (Exception xx) {}
/*     */       }
/*     */       
/*  52 */       if (databaseLink != null) {
/*     */         try {
/*  54 */           databaseLink.close();
/*  55 */         } catch (Exception xx) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized LocaleInfoProvider getInstance() {
/*  62 */     LocaleInfoProvider instance = (LocaleInfoProvider)ApplicationContext.getInstance().getObject(LocaleInfoProvider.class);
/*  63 */     if (instance == null) {
/*  64 */       instance = new LocaleInfoProvider();
/*  65 */       ApplicationContext.getInstance().storeObject(LocaleInfoProvider.class, instance);
/*     */     } 
/*  67 */     return instance;
/*     */   }
/*     */   
/*     */   public Collection getLocales() {
/*  71 */     return this.locales.values();
/*     */   }
/*     */   
/*     */   public Collection getLocalesAsStrings() {
/*  75 */     return this.locales.keySet();
/*     */   }
/*     */   
/*     */   public LocaleInfo getLocale(int locale) {
/*  79 */     return this.localesID.get(locale - 1);
/*     */   }
/*     */   
/*     */   public LocaleInfo getLocale(Integer locale) {
/*  83 */     return getLocale(locale.intValue());
/*     */   }
/*     */   
/*     */   public LocaleInfo getLocale(String locale) {
/*  87 */     return (LocaleInfo)this.locales.get(locale);
/*     */   }
/*     */   
/*     */   public LocaleInfo getLocale(Locale locale) {
/*  91 */     LocaleInfo result = getLocale(locale.getLanguage() + "_" + locale.getCountry());
/*  92 */     if (result == null) {
/*  93 */       Iterator<LocaleInfo> it = getLocales().iterator();
/*  94 */       while (it.hasNext()) {
/*  95 */         LocaleInfo li = it.next();
/*  96 */         if (li.getLanguage().equalsIgnoreCase(locale.getLanguage())) {
/*  97 */           result = li;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocaleInfo getLocaleTLA(String tla) {
/* 107 */     return (LocaleInfo)this.localesTLA.get(tla);
/*     */   }
/*     */   
/*     */   public LocaleInfo getLocaleSCDS(String scds) {
/* 111 */     return (LocaleInfo)this.localesSCDS.get(scds);
/*     */   }
/*     */ 
/*     */   
/*     */   public LocaleInfo getLocaleESI(String esi) {
/* 116 */     Iterator<LocaleInfo> it = getLocales().iterator();
/* 117 */     while (it.hasNext()) {
/* 118 */       LocaleInfo li = it.next();
/* 119 */       if (li.getLocaleTLA() != null && li.getLocaleTLA().startsWith(esi)) {
/* 120 */         return li;
/*     */       }
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public void load(Connection lg) throws Exception {
/* 127 */     this.locales = new HashMap<Object, Object>();
/* 128 */     this.localesID = new ArrayList();
/* 129 */     this.localesTLA = new HashMap<Object, Object>();
/* 130 */     this.localesSCDS = new HashMap<Object, Object>();
/*     */     try {
/* 132 */       init(lg);
/* 133 */       loadFLC(lg);
/* 134 */     } catch (Exception e) {
/* 135 */       log.error("failed to query locale information.");
/* 136 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void init(Connection db) throws Exception {
/* 141 */     Statement stmt = null;
/* 142 */     ResultSet rs = null;
/*     */     try {
/* 144 */       stmt = db.createStatement();
/* 145 */       rs = stmt.executeQuery("SELECT * FROM LOCALE ORDER BY LOCALE_ID");
/* 146 */       while (rs.next()) {
/* 147 */         LocaleInfo l = new LocaleInfo(rs.getInt("LOCALE_ID"), rs.getString("LOCALE"), rs.getString("TLA_ENCODING"), rs.getString("SCDS_ENCODING"), rs.getString("LOCALE_DESCRIPTION"));
/* 148 */         this.locales.put(l.getLocale(), l);
/* 149 */         this.localesID.add(l);
/* 150 */         this.localesTLA.put(l.getLocaleTLA(), l);
/* 151 */         this.localesSCDS.put(l.getLocaleSCDS(), l);
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 155 */         if (rs != null) {
/* 156 */           rs.close();
/*     */         }
/* 158 */         if (stmt != null) {
/* 159 */           stmt.close();
/*     */         }
/* 161 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadFLC(Connection db) throws Exception {
/* 167 */     Statement stmt = null;
/* 168 */     ResultSet rs = null;
/*     */     try {
/* 170 */       LocaleInfo li = null;
/* 171 */       stmt = db.createStatement();
/* 172 */       rs = stmt.executeQuery("SELECT * FROM FALLBACK_LANGUAGE_CHAIN ORDER BY LOCALE_ID, SIT, FALLBACK_POSITION");
/* 173 */       while (rs.next()) {
/* 174 */         int l = rs.getInt("LOCALE_ID");
/* 175 */         if (li == null || li.getLocaleID().intValue() != l) {
/* 176 */           li = getLocale(l);
/*     */         }
/* 178 */         li.addFLC(rs.getInt("SIT"), rs.getInt("FALLBACK_LOCALE_ID"));
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 182 */         if (rs != null) {
/* 183 */           rs.close();
/*     */         }
/* 185 */         if (stmt != null) {
/* 186 */           stmt.close();
/*     */         }
/* 188 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\locale\LocaleInfoProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */