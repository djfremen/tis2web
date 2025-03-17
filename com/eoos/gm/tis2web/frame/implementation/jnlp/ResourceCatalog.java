/*     */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.net.URL;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import jnlp.sample.servlet.DownloadRequest;
/*     */ import jnlp.sample.servlet.ErrorResponseException;
/*     */ import jnlp.sample.servlet.JnlpResource;
/*     */ import jnlp.sample.servlet.ResourceCatalog;
/*     */ import jnlp.sample.util.VersionID;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class ResourceCatalog extends ResourceCatalog {
/*  28 */   private static final Logger log = Logger.getLogger(ResourceCatalog.class);
/*     */   
/*  30 */   private static ResourceCatalog instance = null;
/*     */   
/*     */   protected ResourceCatalog catalog;
/*     */   
/*     */   protected ServletContext servletContext;
/*     */   
/*     */   protected IDatabaseLink db;
/*     */   
/*     */   protected boolean poll;
/*     */   
/*     */   protected Map resources;
/*     */   
/*     */   public static synchronized ResourceCatalog createInstance(ServletContext servletContext, boolean poll, ResourceCatalog catalog) throws Exception {
/*  43 */     if (instance != null) {
/*  44 */       throw new IllegalStateException();
/*     */     }
/*  46 */     instance = new ResourceCatalog(servletContext, poll, catalog);
/*  47 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized ResourceCatalog getInstance() {
/*  52 */     if (instance == null) {
/*  53 */       throw new IllegalStateException();
/*     */     }
/*  55 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Object[] getResources() throws Exception {
/*  60 */     if (this.poll) {
/*  61 */       Connection conn = null;
/*     */       try {
/*  63 */         conn = this.db.requestConnection();
/*  64 */         Map updates = checkResources(conn);
/*  65 */         if (updates != null) {
/*  66 */           loadResources(conn, updates);
/*     */         }
/*  68 */       } catch (Exception e) {
/*  69 */         log.error("failed to check/update jnlp resource catalog - error:" + e, e);
/*     */       } finally {
/*  71 */         if (conn != null) {
/*     */           try {
/*  73 */             this.db.releaseConnection(conn);
/*  74 */           } catch (Exception xx) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return this.resources.values().toArray();
/*     */   }
/*     */   
/*     */   public synchronized JnlpDatabaseResource lookupResource(String name, String version, boolean pack200) {
/*  83 */     JnlpDatabaseResource resource = (JnlpDatabaseResource)this.resources.get(name.toLowerCase(Locale.ENGLISH));
/*  84 */     if (resource != null) {
/*  85 */       resource = resource.getResourceVersion(version);
/*  86 */       if (resource != null && pack200 && supportsPack200(resource)) {
/*  87 */         return new JnlpDatabaseResourcePack200(resource);
/*     */       }
/*     */     } 
/*  90 */     return resource;
/*     */   }
/*     */   
/*     */   private static boolean supportsPack200(JnlpDatabaseResource resource) {
/*  94 */     return resource.supportsPack200();
/*     */   }
/*     */   
/*     */   public synchronized void reload() throws Exception {
/*  98 */     this.resources.clear();
/*  99 */     loadResources();
/*     */   }
/*     */   
/*     */   private ResourceCatalog(ServletContext servletContext, boolean poll, ResourceCatalog catalog) throws Exception {
/* 103 */     super(servletContext, null);
/* 104 */     this.servletContext = servletContext;
/* 105 */     this.catalog = catalog;
/* 106 */     this.resources = new HashMap<Object, Object>();
/* 107 */     this.poll = poll;
/* 108 */     init();
/*     */   }
/*     */   
/*     */   protected void init() throws Exception {
/*     */     try {
/* 113 */       this.db = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.jnlp.table.db");
/* 114 */       loadResources();
/* 115 */     } catch (Exception e) {
/* 116 */       if (ApplicationContext.getInstance().developMode()) {
/* 117 */         log.warn("unable to init resource catalog from database, using war-included resources");
/*     */       } else {
/* 119 */         log.error("unable to init resource catalog - error:" + e, e);
/* 120 */         if (this.db != null) {
/*     */           try {
/* 122 */             this.db.close();
/* 123 */           } catch (Exception xx) {}
/*     */         }
/*     */         
/* 126 */         throw e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public JnlpResource lookupResource(DownloadRequest dreq) throws ErrorResponseException {
/* 132 */     String path = dreq.getPath();
/* 133 */     int idx = path.lastIndexOf('/');
/* 134 */     String name = (idx == -1) ? path : path.substring(idx + 1);
/* 135 */     String version = dreq.getVersion();
/* 136 */     final boolean pack200 = (dreq.getEncoding() != null && dreq.getEncoding().indexOf("pack200") >= 0);
/* 137 */     final JnlpDatabaseResource dbresource = lookupResource(name, version, pack200);
/* 138 */     if (dbresource == null) {
/* 139 */       return this.catalog.lookupResource(dreq);
/*     */     }
/* 141 */     String returnVersionId = version;
/* 142 */     long timestamp = dbresource.getTimeStamp();
/* 143 */     JnlpResource resource = new JnlpResource(this.servletContext, name, version, dreq.getOS(), dreq.getArch(), dreq.getLocale(), path, returnVersionId, timestamp, dreq.getEncoding(), pack200) {
/*     */         public boolean isJnlpFile() {
/* 145 */           return false;
/*     */         }
/*     */         
/*     */         public boolean isJarFile() {
/* 149 */           return true;
/*     */         }
/*     */         
/*     */         public long getLastModified() {
/* 153 */           return this.timestamp;
/*     */         }
/*     */         
/*     */         public String getPath() {
/* 157 */           return DatabaseResourceURL.makeDatabaseResourceURL(dbresource, false).toString();
/*     */         }
/*     */         
/*     */         public URL getResource() {
/* 161 */           return DatabaseResourceURL.makeDatabaseResourceURL(dbresource, pack200);
/*     */         }
/*     */         
/*     */         public boolean exists() {
/* 165 */           return true;
/*     */         }
/*     */       };
/* 168 */     return resource;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JnlpDatabaseResource loadResource(Connection conn, String name, String version) throws Exception {
/* 173 */     PreparedStatement stmt = null;
/* 174 */     ResultSet rs = null;
/*     */     try {
/* 176 */       stmt = conn.prepareStatement("SELECT JNLP_TYPE, JNLP_RESOURCE, JNLP_PACK200 FROM SPS_JNLP WHERE JNLP_NAME = ? and JNLP_VERSION = ?");
/* 177 */       stmt.setString(1, name);
/* 178 */       stmt.setString(2, version);
/* 179 */       rs = stmt.executeQuery();
/* 180 */       if (rs.next()) {
/* 181 */         String type = rs.getString(1);
/* 182 */         byte[] data = extractResource(rs);
/* 183 */         byte[] data200 = extractResource200(rs);
/* 184 */         long timestamp = System.currentTimeMillis();
/* 185 */         return new JnlpDatabaseResource(type.trim(), name.toLowerCase(Locale.ENGLISH), version, new VersionID(version), timestamp, data, data200);
/*     */       } 
/* 187 */       return null;
/*     */     } finally {
/*     */       try {
/* 190 */         if (rs != null) {
/* 191 */           rs.close();
/*     */         }
/* 193 */         if (stmt != null) {
/* 194 */           stmt.close();
/*     */         }
/* 196 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadResources(Connection conn, Map updates) throws Exception {
/* 202 */     if (updates == null) {
/*     */       return;
/*     */     }
/* 205 */     Iterator<String> it = updates.keySet().iterator();
/* 206 */     while (it.hasNext()) {
/* 207 */       String name = it.next();
/* 208 */       String version = (String)updates.get(name);
/* 209 */       JnlpDatabaseResource resource = loadResource(conn, name, version);
/* 210 */       if (resource != null) {
/* 211 */         JnlpDatabaseResource history = (JnlpDatabaseResource)this.resources.get(name);
/* 212 */         this.resources.put(name, resource);
/* 213 */         if (supportsPack200(resource)) {
/* 214 */           this.resources.put(name + ".pack.gz", resource);
/*     */         }
/* 216 */         if (history != null)
/* 217 */           resource.addHistory(history); 
/*     */         continue;
/*     */       } 
/* 220 */       log.warn("failed to update jnlp resource '" + name + "' (version " + version + ")");
/*     */     } 
/*     */     
/* 223 */     log.info("jnlp resource catalog updated: loaded " + updates.size() + " resource(s)");
/*     */   }
/*     */   
/*     */   protected Map checkResources(Connection conn) throws Exception {
/* 227 */     Map<Object, Object> updates = null;
/* 228 */     Statement stmt = null;
/* 229 */     ResultSet rs = null;
/*     */     try {
/* 231 */       conn = this.db.requestConnection();
/* 232 */       stmt = conn.createStatement();
/* 233 */       rs = stmt.executeQuery("SELECT JNLP_NAME, JNLP_VERSION FROM SPS_JNLP");
/* 234 */       while (rs.next()) {
/* 235 */         String name = rs.getString(1);
/* 236 */         String version = rs.getString(2);
/* 237 */         VersionID vid = new VersionID(version.trim());
/* 238 */         JnlpDatabaseResource resource = (JnlpDatabaseResource)this.resources.get(name.toLowerCase(Locale.ENGLISH).trim());
/* 239 */         if (resource == null || vid.isGreaterThan(resource.getVersionID())) {
/* 240 */           if (updates == null) {
/* 241 */             updates = new HashMap<Object, Object>();
/*     */           }
/* 243 */           updates.put(name, version);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 248 */         if (rs != null) {
/* 249 */           rs.close();
/*     */         }
/* 251 */         if (stmt != null) {
/* 252 */           stmt.close();
/*     */         }
/* 254 */         if (conn != null) {
/* 255 */           this.db.releaseConnection(conn);
/*     */         }
/* 257 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 260 */     return updates;
/*     */   }
/*     */   
/*     */   protected void loadResources() throws Exception {
/* 264 */     Connection conn = null;
/* 265 */     Statement stmt = null;
/* 266 */     ResultSet rs = null;
/*     */     try {
/* 268 */       conn = this.db.requestConnection();
/* 269 */       stmt = conn.createStatement();
/* 270 */       rs = stmt.executeQuery("SELECT JNLP_NAME, JNLP_VERSION, JNLP_TYPE, JNLP_RESOURCE, JNLP_PACK200 FROM SPS_JNLP ORDER BY JNLP_VERSION");
/* 271 */       while (rs.next()) {
/* 272 */         String name = rs.getString(1).toLowerCase(Locale.ENGLISH).trim();
/* 273 */         String version = rs.getString(2).trim();
/* 274 */         String type = rs.getString(3).trim();
/* 275 */         VersionID vid = new VersionID(version);
/* 276 */         byte[] data = extractResource(rs);
/* 277 */         byte[] data200 = extractResource200(rs);
/* 278 */         long timestamp = System.currentTimeMillis();
/* 279 */         JnlpDatabaseResource resource = new JnlpDatabaseResource(type, name, version, vid, timestamp, data, data200);
/* 280 */         JnlpDatabaseResource history = (JnlpDatabaseResource)this.resources.get(name);
/* 281 */         if (history == null || vid.isGreaterThan(history.getVersionID())) {
/* 282 */           this.resources.put(name, resource);
/* 283 */           if (supportsPack200(resource)) {
/* 284 */             this.resources.put(name + ".pack.gz", resource);
/*     */           }
/* 286 */           if (history != null) {
/* 287 */             resource.addHistory(history);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 293 */         if (rs != null) {
/* 294 */           rs.close();
/*     */         }
/* 296 */         if (stmt != null) {
/* 297 */           stmt.close();
/*     */         }
/* 299 */         if (conn != null) {
/* 300 */           this.db.releaseConnection(conn);
/*     */         }
/* 302 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 305 */     log.info("jnlp resource catalog loaded " + this.resources.size() + " resource(s)");
/*     */   }
/*     */   
/*     */   protected byte[] extractResource(ResultSet rs) throws Exception {
/* 309 */     if (this.db.getDBMS() == 1) {
/* 310 */       Blob b = rs.getBlob("jnlp_resource");
/* 311 */       return b.getBytes(1L, (int)b.length());
/*     */     } 
/* 313 */     return (byte[])rs.getObject("JNLP_RESOURCE");
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] extractResource200(ResultSet rs) throws Exception {
/* 318 */     if (this.db.getDBMS() == 1) {
/* 319 */       Blob b = rs.getBlob("jnlp_pack200");
/* 320 */       return (b == null) ? null : b.getBytes(1L, (int)b.length());
/*     */     } 
/* 322 */     return (byte[])rs.getObject("JNLP_PACK200");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\ResourceCatalog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */