/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CTOCLabelCache
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(CTOCLabelCache.class); private CachedRetrievalSupportV3 cachedRetrievalSupport;
/*     */   public static interface ConnectionCallback {
/*     */     Connection getReadOnlyConnection() throws Exception;
/*     */     void releaseConnection(Connection param1Connection); }
/*     */   
/*     */   private final class CRSCallback implements CachedRetrievalSupportV3.Callback, CachedRetrievalSupportV3.Callback.EmptyResultFallback { private final Collection requestedLocales;
/*     */     
/*     */     private CRSCallback(Collection requestedLocales, LocaleInfo locale, boolean useFallbackLocales) {
/*  36 */       this.requestedLocales = requestedLocales;
/*  37 */       this.locale = locale;
/*  38 */       this.useFallbackLocales = useFallbackLocales;
/*     */     }
/*     */     private final LocaleInfo locale; private final boolean useFallbackLocales;
/*     */     public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/*  42 */       stmt.setInt(2, ((Integer)identifier).intValue());
/*     */     }
/*     */     
/*     */     public String getQuery() {
/*  46 */       return "SELECT LABEL FROM TOC_LABEL WHERE LOCALE =? AND LABEL_ID=?";
/*     */     }
/*     */     
/*     */     public void initStatement(PreparedStatement stmt) throws SQLException {
/*  50 */       stmt.setInt(1, this.locale.getLocaleID().intValue());
/*     */     }
/*     */     
/*     */     public Object createObject(Object identifier, ResultSet rs) throws Exception {
/*  54 */       String label = rs.getString("LABEL");
/*     */       
/*  56 */       if (Util.isNullOrEmpty(label)) {
/*  57 */         return createObject(identifier);
/*     */       }
/*  59 */       return label;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object createObject(Object identifier) throws Exception {
/*  64 */       String label = null;
/*  65 */       if (this.useFallbackLocales) {
/*  66 */         for (Iterator<Integer> iterLocales = this.locale.getLocaleFLC(LGSIT.DEFAULT).iterator(); iterLocales.hasNext() && Util.isNullOrEmpty(label); ) {
/*  67 */           Integer lcid = iterLocales.next();
/*  68 */           LocaleInfo fallbackLocal = LocaleInfoProvider.getInstance().getLocale(lcid);
/*  69 */           label = (String)CTOCLabelCache.this.getLabels(Collections.singletonList(identifier), fallbackLocal, this.requestedLocales, true).get(identifier);
/*     */         } 
/*     */       }
/*  72 */       return label;
/*     */     }
/*     */     
/*     */     public Object createKey(Object identifier) {
/*  76 */       return String.valueOf(identifier) + "#" + this.locale.getLocaleID();
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
/*     */   public CTOCLabelCache(final ConnectionCallback connectionCallback, DBMS dbms) {
/*  90 */     ConnectionProvider connectionProvider = new ConnectionProvider()
/*     */       {
/*     */         public void releaseConnection(Connection connection) {
/*  93 */           connectionCallback.releaseConnection(connection);
/*     */         }
/*     */         
/*     */         public Connection getConnection() {
/*     */           try {
/*  98 */             return connectionCallback.getReadOnlyConnection();
/*  99 */           } catch (Exception e) {
/* 100 */             throw new RuntimeException(e);
/*     */           } 
/*     */         }
/*     */       };
/* 104 */     this.cachedRetrievalSupport = new CachedRetrievalSupportV3("CTOC-LABEL", connectionProvider, Tis2webUtil.createStdCache());
/*     */   }
/*     */   
/*     */   public String getLabel(Integer labelID, LocaleInfo locale) {
/* 108 */     return getLabel(labelID, locale, true);
/*     */   }
/*     */   
/*     */   public String getLabel(Integer labelID, LocaleInfo locale, boolean useFallbackLocales) {
/*     */     try {
/* 113 */       return (String)getLabels(Collections.singletonList(labelID), locale, new HashSet(), useFallbackLocales).get(labelID);
/* 114 */     } catch (Exception e) {
/* 115 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getLabels(final Collection ctocs, LocaleInfo locale) {
/* 121 */     final Transforming transforming = new Transforming()
/*     */       {
/*     */         public Object transform(Object object) {
/* 124 */           if (object instanceof CTOCElement)
/* 125 */             return ((CTOCElement)object).getLabelID(); 
/* 126 */           if (object instanceof Integer) {
/* 127 */             return object;
/*     */           }
/* 129 */           return Integer.valueOf(String.valueOf(object));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     try {
/* 135 */       return getLabels(new AbstractCollection()
/*     */           {
/*     */             public Iterator iterator() {
/* 138 */               return Util.createTransformingIterator(ctocs.iterator(), transforming);
/*     */             }
/*     */ 
/*     */             
/*     */             public int size() {
/* 143 */               return 0;
/*     */             }
/*     */           }locale, new HashSet(), true);
/* 146 */     } catch (Exception e) {
/* 147 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map getLabels(Collection ctocs, LocaleInfo locale, Collection<LocaleInfo> requestedLocales, boolean useFallbackLocales) throws Exception {
/* 152 */     if (Util.isNullOrEmpty(ctocs) || requestedLocales.contains(locale)) {
/* 153 */       return Collections.EMPTY_MAP;
/*     */     }
/*     */     
/* 156 */     requestedLocales.add(locale);
/*     */     
/* 158 */     return this.cachedRetrievalSupport.getObjects(ctocs, new CRSCallback(requestedLocales, locale, useFallbackLocales));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\CTOCLabelCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */