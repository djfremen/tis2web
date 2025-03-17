/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DBAdapter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Brand;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableData;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.util.LocaleTransformer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NavigationTableDataStore
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(NavigationTableDataStore.class);
/*     */   
/*     */   private static NavigationTableDataStore instance;
/*     */   
/*  33 */   private IDatabaseLink dblink = null;
/*  34 */   private DBAdapter dbAdapter = null;
/*     */   
/*  36 */   private final Object SYNC = new Object();
/*     */   
/*  38 */   private Map navTables = null;
/*     */   
/*     */   private NavigationTableDataStore() {
/*     */     try {
/*  42 */       ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  43 */       this.dblink = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)applicationContext, "component.sps.navigation.table.db");
/*  44 */       this.dbAdapter = DBAdapter.getInstance(this.dblink);
/*  45 */       getNavTables();
/*  46 */     } catch (Exception e) {
/*  47 */       log.error("failed to initialize NavigationTableDataStore", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map getNavTables() {
/*  53 */     synchronized (this.SYNC) {
/*  54 */       if (this.navTables == null) {
/*     */         try {
/*  56 */           this.navTables = loadNavTables();
/*  57 */         } catch (Exception e) {
/*  58 */           log.error("unable to load navigation tables, returning empty map - exception: " + e, e);
/*  59 */           this.navTables = Collections.EMPTY_MAP;
/*     */         } 
/*     */       }
/*  62 */       return this.navTables;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*  67 */     synchronized (this.SYNC) {
/*  68 */       this.navTables = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCacheSize() {
/*  73 */     synchronized (this.SYNC) {
/*  74 */       if (this.navTables == null) {
/*  75 */         return 0;
/*     */       }
/*  77 */       return this.navTables.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String trim(String string) {
/*  83 */     return (string != null) ? string.trim() : null;
/*     */   }
/*     */   
/*     */   private Map loadNavTables() throws Exception {
/*  87 */     log.debug("loading navigation table files");
/*  88 */     Map<Object, Object> ret = new HashMap<Object, Object>();
/*  89 */     Connection connection = this.dblink.requestConnection();
/*     */     try {
/*  91 */       connection.setReadOnly(true);
/*  92 */       connection.setAutoCommit(false);
/*  93 */       PreparedStatement stmt = connection.prepareStatement("SELECT ID,DATA,CHECKSUM FROM NAVTABLES ");
/*     */       try {
/*  95 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/*  97 */           while (rs.next()) {
/*  98 */             String identifier = trim(rs.getString(1));
/*     */             
/* 100 */             byte[] data = this.dbAdapter.getBinary(rs, 2);
/*     */             
/* 102 */             byte[] checksum = null;
/* 103 */             String hex = rs.getString(3);
/* 104 */             if (hex != null) {
/* 105 */               checksum = StringUtilities.hexToBytes(hex);
/*     */             }
/* 107 */             log.debug("retrieved navigation table data identified by: " + identifier);
/* 108 */             ret.put(identifier, new NavigationTableDataImpl(identifier, data, checksum));
/*     */           } 
/* 110 */           return ret;
/*     */         } finally {
/* 112 */           rs.close();
/*     */         } 
/*     */       } finally {
/*     */         
/* 116 */         stmt.close();
/*     */       } 
/*     */     } finally {
/* 119 */       this.dblink.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized NavigationTableDataStore getInstance() {
/* 125 */     if (instance == null) {
/* 126 */       instance = new NavigationTableDataStore();
/*     */     }
/* 128 */     return instance;
/*     */   }
/*     */   
/*     */   private NavigationTableData getNTD(String id) throws Exception {
/* 132 */     return (NavigationTableData)getNavTables().get(id);
/*     */   }
/*     */   
/*     */   private NavigationTableData getResource(String prefix, Locale locale) throws Exception {
/* 136 */     StringBuffer name = new StringBuffer(prefix);
/* 137 */     if (locale != null) {
/* 138 */       name.append("_" + locale.toString() + ".properties");
/*     */     } else {
/* 140 */       name.append(".properties");
/*     */     } 
/* 142 */     NavigationTableData retValue = getNTD(name.toString());
/* 143 */     if (retValue == null && locale != null) {
/* 144 */       return getResource(prefix, LocaleTransformer.transform(locale));
/*     */     }
/* 146 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getData(Brand brand, Locale locale) throws Exception {
/* 151 */     log.debug("retrieving navigation table data for brand: " + String.valueOf(brand));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     Collection<NavigationTableData> retValue = new HashSet();
/*     */     
/* 158 */     String tableName = "VCS_" + brand.getIdentifier().toUpperCase(Locale.ENGLISH) + "_NAV.table";
/* 159 */     NavigationTableData navTable = getNTD(tableName);
/* 160 */     if (navTable != null) {
/* 161 */       retValue.add(navTable);
/*     */     } else {
/* 163 */       log.warn("unable to load navigation table with identifier: " + tableName);
/*     */     } 
/*     */     
/* 166 */     String resourceNamePrefix = "VCS_" + brand.getIdentifier().toUpperCase(Locale.ENGLISH) + "_NAV_RES";
/* 167 */     NavigationTableData resource = getResource(resourceNamePrefix, locale);
/* 168 */     if (resource != null) {
/* 169 */       retValue.add(resource);
/*     */     } else {
/* 171 */       log.warn("unable to load navigation table resource for locale: " + String.valueOf(locale));
/*     */     } 
/* 173 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\NavigationTableDataStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */