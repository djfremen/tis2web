/*     */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.download.server.DatabaseAdapterSupport;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassifierRI;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ExecutableFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ExecutionMode;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.FileNameFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.RegistryVersionLookup;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.RegistryVersionLookupContainer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.VersionFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.VersionIDFilter;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.cache.util.BulkStoreFacade;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.reflect.IHExecutionTime;
/*     */ import com.eoos.scsm.v2.util.I15dText;
/*     */ import com.eoos.scsm.v2.util.I15dTextSupport;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DatabaseAdapter
/*     */   implements IDatabaseAdapter
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger(DatabaseAdapter.class);
/*     */   
/*  70 */   private static final Logger logPerformance = Logger.getLogger("performance." + DatabaseAdapter.class.getName());
/*     */   private static final IDownloadServer DWNLDSERVER_APPSERVER;
/*     */   
/*     */   static {
/*     */     try {
/*  75 */       DWNLDSERVER_APPSERVER = (IDownloadServer)new DownloadServer(new URL("http://appserver"), false, "Application server");
/*  76 */     } catch (Exception e) {
/*  77 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*  81 */   private static IDatabaseAdapter instance = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*  85 */   private final Object SYNC_UNIT_RETRIEVAL = new Object();
/*     */   
/*  87 */   private Cache unitCache = Tis2webUtil.createStdCache();
/*     */   
/*  89 */   private BulkStoreFacade bsCacheFacade = new BulkStoreFacade(this.unitCache);
/*     */   
/*  91 */   private final Object SYNC_DOWNLOAD_URIS = new Object();
/*     */   
/*  93 */   private Collection downloadURIs = null;
/*     */   
/*     */   private DatabaseAdapterSupport support;
/*     */   
/*     */   private DatabaseAdapter(Configuration dbConfiguration) {
/*  98 */     final IDatabaseLink backend = DatabaseLink.openDatabase(dbConfiguration);
/*  99 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/* 102 */             backend.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/* 107 */               return backend.requestConnection();
/* 108 */             } catch (Exception e) {
/* 109 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */     
/* 114 */     this.support = DatabaseAdapterSupport.create(this.connectionProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/* 119 */     Connection con = this.connectionProvider.getConnection();
/* 120 */     con.setReadOnly(true);
/* 121 */     con.setAutoCommit(false);
/* 122 */     return con;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 126 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   public static synchronized IDatabaseAdapter getInstance() {
/* 130 */     if (instance == null) {
/* 131 */       log.info("creating instance");
/* 132 */       final SubConfigurationWrapper config = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.dwnld.db.");
/* 133 */       final int hash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/* 134 */       instance = new DatabaseAdapter((Configuration)subConfigurationWrapper);
/* 135 */       ConfigurationServiceProvider.getService().addObserver(new ConfigurationService.Observer()
/*     */           {
/*     */             public void onModification() {
/* 138 */               if (ConfigurationUtil.configurationHash(config) != hash) {
/* 139 */                 DatabaseAdapter.log.info("configuration changed, resetting instance");
/* 140 */                 ConfigurationServiceProvider.getService().removeObserver(this);
/* 141 */                 synchronized (DatabaseAdapter.class) {
/* 142 */                   DatabaseAdapter.instance = null;
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/* 148 */       if (ApplicationContext.getInstance().developMode()) {
/* 149 */         instance = (IDatabaseAdapter)IHExecutionTime.createProxy(instance, (IHExecutionTime.Callback)new IHExecutionTime.CallbackAdapter(logPerformance));
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return instance;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/* 157 */     log.debug("resetting instance...");
/* 158 */     instance = null;
/* 159 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   private Collection getACLList(Connection connection, long unitID) throws Exception {
/* 163 */     PreparedStatement stmt = connection.prepareStatement("select a.acl_resource_value from cdl_acl_lists a, cdl_versions b where b.ref_acl_list_id=a.acl_list_id and b.version_id=?");
/*     */     try {
/* 165 */       stmt.setLong(1, unitID);
/* 166 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 168 */         Collection<String> ret = new LinkedList();
/* 169 */         while (rs.next()) {
/* 170 */           String resourceValue = rs.getString(1);
/* 171 */           ret.add(resourceValue);
/*     */         } 
/* 173 */         return ret;
/*     */       } finally {
/*     */         
/* 176 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 179 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private I15dText getDescriptions(Connection connection, long unitID) throws Exception {
/* 184 */     PreparedStatement stmt = connection.prepareStatement("select a.locale, a.text from cdl_descriptions a, cdl_versions b where b.ref_description_id=a.description_id and b.version_id=? ");
/*     */     try {
/* 186 */       stmt.setLong(1, unitID);
/* 187 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 189 */         Map<Object, Object> map = new HashMap<Object, Object>();
/* 190 */         while (rs.next()) {
/* 191 */           Locale locale = Util.parseLocale(rs.getString(1));
/* 192 */           String description = rs.getString(2);
/* 193 */           map.put(locale, description);
/*     */         } 
/* 195 */         return (I15dText)new I15dTextSupport(Locale.ENGLISH, map);
/*     */       } finally {
/*     */         
/* 198 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 201 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private RegistryVersionLookupContainer getVersionLookup(Connection connection, long unitID) throws Exception {
/* 206 */     PreparedStatement stmt = connection.prepareStatement("select a.reg_key, a.reg_name, a.conversion from cdl_version_lookup a, cdl_rel_classifier_vlookup b where a.version_lookup_id=b.ref_version_lookup_id and b.ref_classifier_id in (select distinct c.ref_classifier_id from cdl_classification c where c.ref_version_id=?)");
/*     */     try {
/* 208 */       stmt.setLong(1, unitID);
/* 209 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 211 */         Collection<RegistryVersionLookup> c = new LinkedList();
/* 212 */         while (rs.next()) {
/* 213 */           String key = rs.getString(1);
/* 214 */           String name = rs.getString(2);
/* 215 */           String conversion = rs.getString(3);
/*     */           
/* 217 */           c.add(new RegistryVersionLookup(key, name, conversion));
/*     */         } 
/* 219 */         return new RegistryVersionLookupContainer(c);
/*     */       } finally {
/*     */         
/* 222 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 225 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean blobsAvailable(Connection connection, long unitID) throws Exception {
/* 230 */     PreparedStatement stmt = connection.prepareStatement("select count(*) from cdl_version_files a join cdl_rel_versions_files b on a.file_id=b.ref_file_id left outer join  cdl_blobs c on a.file_id=c.ref_file_id where b.ref_version_id=? and c.ref_file_id is null");
/*     */     try {
/* 232 */       stmt.setLong(1, unitID);
/* 233 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 235 */         if (rs.next() && 
/* 236 */           rs.getInt(1) == 0) {
/* 237 */           return true;
/*     */         }
/*     */         
/* 240 */         return false;
/*     */       } finally {
/*     */         
/* 243 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 246 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection getDownloadServers(Connection connection, long unitID) throws Exception {
/* 252 */     Collection<DownloadServer> c = new LinkedList();
/*     */     
/* 254 */     PreparedStatement stmt = connection.prepareStatement("select b.base_url,b.is_akamai, b.description from cdl_rel_version_cluster a, cdl_cluster b where a.ref_version_id=? and a.ref_cluster_id=b.cluster_id order by b.priority asc");
/*     */     try {
/* 256 */       stmt.setLong(1, unitID);
/* 257 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 259 */         while (rs.next()) {
/* 260 */           String _url = rs.getString(1);
/*     */           try {
/* 262 */             URL url = new URL(_url);
/* 263 */             boolean isAkamai = (rs.getInt(2) == 1);
/* 264 */             String description = Util.trim(rs.getString(3));
/* 265 */             c.add(new DownloadServer(url, isAkamai, description));
/* 266 */           } catch (MalformedURLException e) {
/* 267 */             log.warn("ignoring malformed cluster url: " + String.valueOf(_url));
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 271 */         JDBCUtil.close(rs);
/*     */       } 
/* 273 */       if (blobsAvailable(connection, unitID)) {
/* 274 */         log.debug("...adding application server to list of download servers for unit: " + unitID + " (all files found) ");
/* 275 */         c.add(DWNLDSERVER_APPSERVER);
/*     */       } else {
/* 277 */         log.debug("...the application server does not host any/all files for unit: " + unitID + ", therefor he is not added to server list");
/*     */       } 
/* 279 */       return c;
/*     */     } finally {
/*     */       
/* 282 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ExecutableFile.ExecutionInfo getExecutionInfo(Connection connection, long fileID) throws Exception {
/* 287 */     PreparedStatement stmt = connection.prepareStatement("select a.starter_type, a.cmd_line_params, a.success_codes, a.failure_codes, a.wait_for_completion, b.starter_path from cdl_starter_info a, cdl_rel_vfiles_sinfo b where a.starter_info_id=b.ref_starter_info_id and b.ref_file_id=? ");
/*     */     try {
/* 289 */       stmt.setLong(1, fileID);
/* 290 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 292 */         ExecutableFile.ExecutionInfo ret = null;
/* 293 */         if (rs.next()) {
/* 294 */           ret = new ExecutableFile.ExecutionInfo();
/* 295 */           ret.starterType = rs.getString(1);
/* 296 */           ret.cmdLineParams = rs.getString(2);
/* 297 */           ret.successCodes = rs.getString(3);
/* 298 */           ret.failureCodes = rs.getString(4);
/* 299 */           if (rs.getInt(5) == 1) {
/* 300 */             ret.execMode = ExecutionMode.SYNC;
/*     */           } else {
/* 302 */             ret.execMode = ExecutionMode.ASYNC;
/*     */           } 
/* 304 */           ret.path = rs.getString(6);
/*     */         } 
/*     */         
/* 307 */         return ret;
/*     */       } finally {
/*     */         
/* 310 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 313 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getFiles(Connection connection, long unitID) throws Exception {
/* 318 */     PreparedStatement stmt = connection.prepareStatement("select a.file_id, a.file_name, a.chksum, a.file_size, a.path, a.compression,a.compressed_file_size from cdl_version_files a, cdl_rel_versions_files b where b.ref_file_id=a.file_id and b.ref_version_id=?");
/*     */     try {
/* 320 */       stmt.setLong(1, unitID);
/* 321 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 323 */         Collection<ExecutableFile> c = new LinkedList();
/* 324 */         while (rs.next()) {
/* 325 */           ExecutableFile executableFile; long fileID = rs.getLong(1);
/* 326 */           String name = rs.getString(2);
/* 327 */           byte[] chksum = Util.parseBytes(rs.getString(3));
/* 328 */           long size = rs.getLong(4);
/* 329 */           String path = rs.getString(5);
/* 330 */           String compression = rs.getString(6);
/* 331 */           long compressedSize = rs.getLong(7);
/* 332 */           ExecutableFile.ExecutionInfo execInfo = getExecutionInfo(connection, fileID);
/* 333 */           DownloadFile dfile = null;
/* 334 */           if (execInfo == null) {
/* 335 */             dfile = new DownloadFile(fileID, name, size, chksum, path, compression, compressedSize);
/*     */           } else {
/* 337 */             executableFile = new ExecutableFile(fileID, name, size, chksum, path, compression, compressedSize, execInfo);
/*     */           } 
/* 339 */           c.add(executableFile);
/*     */         } 
/*     */         
/* 342 */         return c;
/*     */       } finally {
/*     */         
/* 345 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 348 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getClassifiers(Connection connection, long unitID) throws Exception {
/* 353 */     Collection<ClassifierRI> ret = new LinkedHashSet();
/* 354 */     PreparedStatement stmt = connection.prepareStatement("select b.classifier from cdl_classification a, cdl_classifiers b where a.ref_classifier_id=b.classifier_id and a.ref_version_id=?");
/*     */     try {
/* 356 */       stmt.setLong(1, unitID);
/* 357 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 359 */         while (rs.next()) {
/* 360 */           rs.getString(1);
/* 361 */           ret.add(new ClassifierRI(rs.getString(1)));
/*     */         } 
/*     */         
/* 364 */         return ret;
/*     */       } finally {
/*     */         
/* 367 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/* 370 */       JDBCUtil.close(stmt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map readDownloadUnits(Connection connection, Collection identifiers) throws Exception {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ldc 'select a.version_string, a.release_date from cdl_versions a where a.version_id=?'
/*     */     //   3: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   8: astore_3
/*     */     //   9: new java/util/HashMap
/*     */     //   12: dup
/*     */     //   13: aload_2
/*     */     //   14: invokeinterface size : ()I
/*     */     //   19: invokespecial <init> : (I)V
/*     */     //   22: astore #4
/*     */     //   24: aload_2
/*     */     //   25: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   30: astore #5
/*     */     //   32: aload #5
/*     */     //   34: invokeinterface hasNext : ()Z
/*     */     //   39: ifeq -> 295
/*     */     //   42: aload #5
/*     */     //   44: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   49: checkcast java/lang/Long
/*     */     //   52: astore #6
/*     */     //   54: aload_3
/*     */     //   55: iconst_1
/*     */     //   56: aload #6
/*     */     //   58: invokevirtual longValue : ()J
/*     */     //   61: invokeinterface setLong : (IJ)V
/*     */     //   66: aload_3
/*     */     //   67: invokeinterface executeQuery : ()Ljava/sql/ResultSet;
/*     */     //   72: astore #7
/*     */     //   74: aload #7
/*     */     //   76: invokeinterface next : ()Z
/*     */     //   81: ifeq -> 269
/*     */     //   84: aconst_null
/*     */     //   85: astore #8
/*     */     //   87: aload #7
/*     */     //   89: iconst_1
/*     */     //   90: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   95: invokestatic parse : (Ljava/lang/String;)Lcom/eoos/datatype/VersionNumber;
/*     */     //   98: astore #8
/*     */     //   100: goto -> 145
/*     */     //   103: astore #9
/*     */     //   105: getstatic com/eoos/gm/tis2web/frame/dwnld/server/DatabaseAdapter.log : Lorg/apache/log4j/Logger;
/*     */     //   108: new java/lang/StringBuilder
/*     */     //   111: dup
/*     */     //   112: invokespecial <init> : ()V
/*     */     //   115: ldc 'unable to parse version number string:'
/*     */     //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   120: aload #7
/*     */     //   122: iconst_1
/*     */     //   123: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   128: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   134: ldc ' , continuing with <null> value '
/*     */     //   136: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   139: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   142: invokevirtual warn : (Ljava/lang/Object;)V
/*     */     //   145: aload #7
/*     */     //   147: iconst_2
/*     */     //   148: invokeinterface getLong : (I)J
/*     */     //   153: lstore #9
/*     */     //   155: aload_0
/*     */     //   156: aload_1
/*     */     //   157: aload #6
/*     */     //   159: invokevirtual longValue : ()J
/*     */     //   162: invokespecial getACLList : (Ljava/sql/Connection;J)Ljava/util/Collection;
/*     */     //   165: astore #11
/*     */     //   167: aload_0
/*     */     //   168: aload_1
/*     */     //   169: aload #6
/*     */     //   171: invokevirtual longValue : ()J
/*     */     //   174: invokespecial getDescriptions : (Ljava/sql/Connection;J)Lcom/eoos/scsm/v2/util/I15dText;
/*     */     //   177: astore #12
/*     */     //   179: aload_0
/*     */     //   180: aload_1
/*     */     //   181: aload #6
/*     */     //   183: invokevirtual longValue : ()J
/*     */     //   186: invokespecial getDownloadServers : (Ljava/sql/Connection;J)Ljava/util/Collection;
/*     */     //   189: astore #13
/*     */     //   191: aload_0
/*     */     //   192: aload_1
/*     */     //   193: aload #6
/*     */     //   195: invokevirtual longValue : ()J
/*     */     //   198: invokespecial getFiles : (Ljava/sql/Connection;J)Ljava/util/Collection;
/*     */     //   201: astore #14
/*     */     //   203: aload_0
/*     */     //   204: aload_1
/*     */     //   205: aload #6
/*     */     //   207: invokevirtual longValue : ()J
/*     */     //   210: invokespecial getVersionLookup : (Ljava/sql/Connection;J)Lcom/eoos/gm/tis2web/frame/dwnld/common/RegistryVersionLookupContainer;
/*     */     //   213: astore #15
/*     */     //   215: aload_0
/*     */     //   216: aload_1
/*     */     //   217: aload #6
/*     */     //   219: invokevirtual longValue : ()J
/*     */     //   222: invokespecial getClassifiers : (Ljava/sql/Connection;J)Ljava/util/Collection;
/*     */     //   225: astore #16
/*     */     //   227: new com/eoos/gm/tis2web/frame/dwnld/common/DownloadUnit
/*     */     //   230: dup
/*     */     //   231: aload #6
/*     */     //   233: invokevirtual longValue : ()J
/*     */     //   236: aload #8
/*     */     //   238: lload #9
/*     */     //   240: aload #11
/*     */     //   242: aload #12
/*     */     //   244: aload #13
/*     */     //   246: aload #14
/*     */     //   248: aload #15
/*     */     //   250: aload #16
/*     */     //   252: invokespecial <init> : (JLcom/eoos/datatype/VersionNumber;JLjava/util/Collection;Lcom/eoos/scsm/v2/util/I15dText;Ljava/util/Collection;Ljava/util/Collection;Lcom/eoos/gm/tis2web/frame/dwnld/common/RegistryVersionLookupContainer;Ljava/util/Collection;)V
/*     */     //   255: astore #17
/*     */     //   257: aload #4
/*     */     //   259: aload #6
/*     */     //   261: aload #17
/*     */     //   263: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   268: pop
/*     */     //   269: jsr -> 283
/*     */     //   272: goto -> 292
/*     */     //   275: astore #18
/*     */     //   277: jsr -> 283
/*     */     //   280: aload #18
/*     */     //   282: athrow
/*     */     //   283: astore #19
/*     */     //   285: aload #7
/*     */     //   287: invokestatic close : (Ljava/sql/ResultSet;)V
/*     */     //   290: ret #19
/*     */     //   292: goto -> 32
/*     */     //   295: aload #4
/*     */     //   297: astore #5
/*     */     //   299: jsr -> 313
/*     */     //   302: aload #5
/*     */     //   304: areturn
/*     */     //   305: astore #20
/*     */     //   307: jsr -> 313
/*     */     //   310: aload #20
/*     */     //   312: athrow
/*     */     //   313: astore #21
/*     */     //   315: aload_3
/*     */     //   316: invokestatic close : (Ljava/sql/Statement;)V
/*     */     //   319: ret #21
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #375	-> 0
/*     */     //   #377	-> 9
/*     */     //   #378	-> 24
/*     */     //   #379	-> 42
/*     */     //   #380	-> 54
/*     */     //   #381	-> 66
/*     */     //   #383	-> 74
/*     */     //   #384	-> 84
/*     */     //   #386	-> 87
/*     */     //   #389	-> 100
/*     */     //   #387	-> 103
/*     */     //   #388	-> 105
/*     */     //   #390	-> 145
/*     */     //   #392	-> 155
/*     */     //   #393	-> 167
/*     */     //   #394	-> 179
/*     */     //   #395	-> 191
/*     */     //   #396	-> 203
/*     */     //   #397	-> 215
/*     */     //   #399	-> 227
/*     */     //   #400	-> 257
/*     */     //   #403	-> 269
/*     */     //   #405	-> 272
/*     */     //   #404	-> 275
/*     */     //   #406	-> 292
/*     */     //   #407	-> 295
/*     */     //   #409	-> 305
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   105	40	9	e	Ljava/lang/Exception;
/*     */     //   87	182	8	versionNumber	Lcom/eoos/datatype/VersionNumber;
/*     */     //   155	114	9	releaseDate	J
/*     */     //   167	102	11	aclTags	Ljava/util/Collection;
/*     */     //   179	90	12	descriptions	Lcom/eoos/scsm/v2/util/I15dText;
/*     */     //   191	78	13	downloadServers	Ljava/util/Collection;
/*     */     //   203	66	14	files	Ljava/util/Collection;
/*     */     //   215	54	15	versionLookup	Lcom/eoos/gm/tis2web/frame/dwnld/common/RegistryVersionLookupContainer;
/*     */     //   227	42	16	classifiers	Ljava/util/Collection;
/*     */     //   257	12	17	unit	Lcom/eoos/gm/tis2web/frame/dwnld/common/DownloadUnit;
/*     */     //   54	238	6	id	Ljava/lang/Long;
/*     */     //   74	218	7	rs	Ljava/sql/ResultSet;
/*     */     //   32	263	5	iter	Ljava/util/Iterator;
/*     */     //   24	281	4	ret	Ljava/util/Map;
/*     */     //   0	321	0	this	Lcom/eoos/gm/tis2web/frame/dwnld/server/DatabaseAdapter;
/*     */     //   0	321	1	connection	Ljava/sql/Connection;
/*     */     //   0	321	2	identifiers	Ljava/util/Collection;
/*     */     //   9	312	3	stmt	Ljava/sql/PreparedStatement;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   9	302	305	finally
/*     */     //   74	272	275	finally
/*     */     //   87	100	103	java/lang/Exception
/*     */     //   275	280	275	finally
/*     */     //   305	310	305	finally
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map getDownloadUnits(Connection connection, Collection identifiers) throws Exception {
/* 414 */     log.debug("retrieving download units for ids: " + String.valueOf(identifiers));
/* 415 */     synchronized (this.SYNC_UNIT_RETRIEVAL) {
/* 416 */       Map<Object, Object> ret = new HashMap<Object, Object>();
/* 417 */       List<Long> unresolved = new LinkedList();
/* 418 */       for (Iterator<Long> iter = identifiers.iterator(); iter.hasNext(); ) {
/* 419 */         Long id = iter.next();
/* 420 */         DownloadUnit unit = (DownloadUnit)this.unitCache.lookup(id);
/* 421 */         if (unit != null) {
/* 422 */           ret.put(id, unit); continue;
/*     */         } 
/* 424 */         unresolved.add(id);
/*     */       } 
/*     */       
/* 427 */       if (unresolved.size() > 0) {
/* 428 */         log.debug("....resolving " + unresolved.size() + " identifiers");
/* 429 */         Map<?, ?> resolved = readDownloadUnits(connection, unresolved);
/* 430 */         this.bsCacheFacade.storeAll(resolved);
/* 431 */         ret.putAll(resolved);
/*     */       } 
/*     */       
/* 434 */       log.debug("...done retrieving download units");
/* 435 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"})
/*     */   private Collection getIdentifiers(Connection connection, Collection filters) throws Exception {
/* 441 */     log.debug("...retrieving identifiers for filters: " + String.valueOf(filters));
/* 442 */     String statement = "select distinct a.version_id from cdl_versions a {JOINTABLE} where {JOINCONDITION} and {FILTERCONDITION}";
/* 443 */     Collection filteredVersionIDs = null;
/* 444 */     if (Util.isNullOrEmpty(filters)) {
/* 445 */       statement = statement.substring(0, statement.indexOf("{JOINTABLE}"));
/*     */     } else {
/* 447 */       int i = 1;
/* 448 */       for (Iterator<DwnldFilter> iter = filters.iterator(); iter.hasNext(); i++) {
/* 449 */         DwnldFilter filter = iter.next();
/* 450 */         if (filter instanceof ClassificationFilter) {
/* 451 */           ClassificationFilter cf = (ClassificationFilter)filter;
/* 452 */           statement = StringUtilities.replace(statement, "{JOINTABLE}", ", cdl_classification b" + i + ", cdl_classifiers c" + i + " {JOINTABLE}");
/* 453 */           statement = StringUtilities.replace(statement, "{JOINCONDITION}", " a.version_id=b" + i + ".ref_version_id and b" + i + ".ref_classifier_id=c" + i + ".classifier_id and {JOINCONDITION}");
/* 454 */           statement = StringUtilities.replace(statement, "{FILTERCONDITION}", "LOWER(c" + i + ".classifier) like '" + cf.getSQLPattern().toLowerCase() + "' and {FILTERCONDITION}");
/* 455 */         } else if (filter instanceof VersionFilter) {
/* 456 */           VersionFilter vf = (VersionFilter)filter;
/* 457 */           statement = StringUtilities.replace(statement, "{FILTERCONDITION}", "LOWER(a.version_string)='" + vf.getVersion().toLowerCase() + "' and {FILTERCONDITION}");
/* 458 */         } else if (filter instanceof VersionIDFilter) {
/* 459 */           Collection<?> ids = ((VersionIDFilter)filter).getVersions();
/* 460 */           if (filteredVersionIDs == null) {
/* 461 */             filteredVersionIDs = new HashSet(ids);
/*     */           } else {
/* 463 */             filteredVersionIDs.retainAll(ids);
/*     */           } 
/* 465 */         } else if (filter instanceof FileNameFilter) {
/* 466 */           FileNameFilter fnf = (FileNameFilter)filter;
/* 467 */           statement = StringUtilities.replace(statement, "{JOINTABLE}", ", cdl_version_files cvf" + i + ", cdl_rel_versions_files crvf" + i + " {JOINTABLE}");
/* 468 */           statement = StringUtilities.replace(statement, "{JOINCONDITION}", " a.version_id=crvf" + i + ".ref_version_id and crvf" + i + ".ref_file_id=cvf" + i + ".file_id and {JOINCONDITION}");
/* 469 */           statement = StringUtilities.replace(statement, "{FILTERCONDITION}", "LOWER(cvf" + i + ".file_name) like '" + fnf.getSQLPattern().toLowerCase() + "' and {FILTERCONDITION}");
/*     */         } 
/*     */       } 
/*     */       
/* 473 */       statement = this.support.checkStatement(statement);
/*     */       
/* 475 */       statement = StringUtilities.replace(statement, "{JOINTABLE}", "");
/* 476 */       statement = StringUtilities.replace(statement, "and {JOINCONDITION}", "");
/* 477 */       statement = StringUtilities.replace(statement, "{JOINCONDITION}", "");
/* 478 */       statement = StringUtilities.replace(statement, "and {FILTERCONDITION}", "");
/* 479 */       statement = StringUtilities.replace(statement, " where  and", "where");
/*     */       
/* 481 */       if (statement.trim().endsWith("where")) {
/* 482 */         statement = statement.substring(0, statement.lastIndexOf("where"));
/*     */       }
/*     */     } 
/* 485 */     log.debug("...using query: " + statement);
/* 486 */     PreparedStatement stmt = connection.prepareStatement(statement);
/*     */     try {
/* 488 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 490 */         Collection<Long> ids = new HashSet();
/* 491 */         while (rs.next()) {
/* 492 */           Long id = Long.valueOf(rs.getLong(1));
/* 493 */           if (filteredVersionIDs == null || filteredVersionIDs.contains(id)) {
/* 494 */             ids.add(Long.valueOf(rs.getLong(1)));
/*     */           }
/*     */         } 
/* 497 */         log.debug("...done retrieving ids, result: " + String.valueOf(ids));
/* 498 */         return ids;
/*     */       } finally {
/* 500 */         JDBCUtil.close(rs, log);
/*     */       } 
/*     */     } finally {
/*     */       
/* 504 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toString(Object obj) {
/* 509 */     return String.valueOf(obj);
/*     */   }
/*     */   
/*     */   public Collection getDownloadUnits(Collection filters) {
/* 513 */     log.debug("retrieving download units for filter: " + toString(filters));
/*     */     try {
/* 515 */       Connection connection = getConnection();
/*     */       try {
/* 517 */         Collection ret = new ArrayList(getDownloadUnits(connection, getIdentifiers(connection, filters)).values());
/* 518 */         return ret;
/*     */       } finally {
/* 520 */         releaseConnection(connection);
/*     */       } 
/* 522 */     } catch (Exception e) {
/* 523 */       log.error("unable to provide download units, returning empty set - exception: " + e, e);
/* 524 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getData(DownloadFile file, OutputStream os) throws IOException {
/* 529 */     log.debug("retrieving file data for file :" + file);
/*     */     try {
/* 531 */       Connection connection = getConnection();
/*     */       try {
/* 533 */         PreparedStatement stmt = connection.prepareStatement("select a.data from cdl_blobs a where a.ref_file_id=?");
/*     */         try {
/* 535 */           stmt.setLong(1, file.getIdentifier());
/* 536 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 538 */             if (rs.next()) {
/* 539 */               InputStream is = rs.getBlob(1).getBinaryStream();
/*     */               try {
/* 541 */                 StreamUtil.transfer(is, os);
/*     */               } finally {
/* 543 */                 is.close();
/*     */               } 
/*     */             } else {
/* 546 */               log.warn("no data for file :" + file);
/*     */             } 
/* 548 */             log.debug("...done retrieving file data");
/*     */           } finally {
/* 550 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/* 553 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/* 556 */         releaseConnection(connection);
/*     */       } 
/* 558 */     } catch (IOException e) {
/* 559 */       throw e;
/* 560 */     } catch (Exception e) {
/* 561 */       log.error("unable to retrieve data for file:" + String.valueOf(file) + ", throwing new IOException - exception: " + e, e);
/* 562 */       throw new IOException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getRelatedUnits(DownloadUnit downloadUnit) {
/* 568 */     log.debug("retrieving related units of : " + toString(downloadUnit));
/*     */     try {
/* 570 */       Connection connection = getConnection();
/*     */       
/*     */       try {
/* 573 */         Collection<Long> relatedIDs = new HashSet();
/* 574 */         PreparedStatement stmt = connection.prepareStatement("select a.ref_version_id1, a.ref_version_id2 from cdl_version_relations a where a.ref_version_id1=? or a.ref_version_id2=?");
/*     */         try {
/* 576 */           stmt.setLong(1, downloadUnit.getIdentifier());
/* 577 */           stmt.setLong(2, downloadUnit.getIdentifier());
/* 578 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 580 */             while (rs.next()) {
/* 581 */               long refID1 = rs.getLong(1);
/* 582 */               long refID2 = rs.getLong(2);
/*     */               
/* 584 */               if (refID1 != downloadUnit.getIdentifier()) {
/* 585 */                 relatedIDs.add(Long.valueOf(refID1)); continue;
/* 586 */               }  if (refID2 != downloadUnit.getIdentifier()) {
/* 587 */                 relatedIDs.add(Long.valueOf(refID2));
/*     */               }
/*     */             } 
/*     */           } finally {
/*     */             
/* 592 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 596 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */         
/* 599 */         return new ArrayList(getDownloadUnits(connection, relatedIDs).values());
/*     */       } finally {
/* 601 */         releaseConnection(connection);
/*     */       } 
/* 603 */     } catch (Exception e) {
/* 604 */       log.error("unable to retrieve related download units for : " + toString(downloadUnit) + ", returing empty set - exception: " + e, e);
/* 605 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DBVersionInformation getDBVersionInfo() {
/*     */     try {
/* 611 */       Connection connection = getConnection();
/*     */       try {
/* 613 */         PreparedStatement stmt = connection.prepareStatement("select release_id,release_date,description,version from release");
/*     */         try {
/* 615 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 617 */             if (rs.next()) {
/* 618 */               String release = rs.getString(1);
/* 619 */               Date date = rs.getDate(2);
/* 620 */               String description = rs.getString(3);
/* 621 */               String version = rs.getString(4);
/*     */               
/* 623 */               return new DBVersionInformation(release, date, description, version);
/*     */             } 
/* 625 */             return null;
/*     */           } finally {
/*     */             
/* 628 */             rs.close();
/*     */           } 
/*     */         } finally {
/* 631 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 634 */         releaseConnection(connection);
/*     */       } 
/* 636 */     } catch (Exception e) {
/* 637 */       log.error("unable to retrieve version information, returning null - exception: " + e, e);
/* 638 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getAllDownloadURIs() {
/* 643 */     synchronized (this.SYNC_DOWNLOAD_URIS) {
/* 644 */       if (this.downloadURIs == null) {
/*     */         try {
/* 646 */           Collection<URI> uris = new HashSet();
/* 647 */           Connection connection = getConnection();
/*     */           try {
/* 649 */             PreparedStatement stmt = connection.prepareStatement("select base_url from cdl_cluster");
/*     */             try {
/* 651 */               ResultSet rs = stmt.executeQuery();
/*     */               try {
/* 653 */                 while (rs.next()) {
/* 654 */                   String tmp = rs.getString(1);
/*     */                   try {
/* 656 */                     uris.add(new URI(tmp));
/* 657 */                   } catch (Exception e) {
/* 658 */                     log.warn("unable to create URI for: " + String.valueOf(tmp) + ", ignoring - exception: " + e, e);
/*     */                   } 
/*     */                 } 
/*     */               } finally {
/*     */                 
/* 663 */                 rs.close();
/*     */               } 
/*     */             } finally {
/* 666 */               stmt.close();
/*     */             } 
/*     */           } finally {
/*     */             
/* 670 */             releaseConnection(connection);
/*     */           } 
/*     */           
/* 673 */           this.downloadURIs = uris;
/*     */         }
/* 675 */         catch (Exception e) {
/* 676 */           log.error("unable to retrieve download uris, returning empty set - exceptiion: " + e, e);
/* 677 */           return Collections.EMPTY_SET;
/*     */         } 
/*     */       }
/* 680 */       return this.downloadURIs;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */