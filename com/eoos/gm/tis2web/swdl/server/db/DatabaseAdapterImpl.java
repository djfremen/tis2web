/*     */ package com.eoos.gm.tis2web.swdl.server.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.File;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.FileProxy;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.LanguageRegistry;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseAdapterImpl
/*     */   extends Database
/*     */   implements DatabaseAdapter
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(DatabaseAdapterImpl.class);
/*     */   
/*  38 */   private SQLSelector sqlSelector = null;
/*     */   
/*  40 */   private Set resourceIDs = null;
/*     */   
/*  42 */   private DBVersionInformation dbversion = null;
/*     */ 
/*     */   
/*     */   public DatabaseAdapterImpl(String device, IDatabaseLink dblink, Set resourceIDs) {
/*  46 */     super(dblink);
/*  47 */     Class<?> cls = null;
/*     */     try {
/*  49 */       cls = Class.forName(getClass().getPackage().getName() + ".SQLSelector" + StringUtilities.replace(device, " ", ""));
/*  50 */       this.sqlSelector = (SQLSelector)cls.newInstance();
/*  51 */     } catch (Exception e) {
/*  52 */       log.error("Exception when get the SQLSelector: \"" + cls.getName() + "\"; " + e, e);
/*     */     } 
/*  54 */     this.resourceIDs = resourceIDs;
/*     */   }
/*     */   
/*     */   public DatabaseAdapterImpl(String device, IDatabaseLink dblink, String description, String version, Set resourceIDs) {
/*  58 */     this(device, dblink, resourceIDs);
/*  59 */     this.dbversion = new DBVersionInformation(null, "", new Date(), description, version);
/*     */   }
/*     */   
/*     */   public Set getApplications() {
/*  63 */     Set<Application> apps = new HashSet();
/*  64 */     Connection conn = null;
/*  65 */     PreparedStatement stmt = null;
/*  66 */     ResultSet rs = null;
/*     */     try {
/*  68 */       conn = requestConnection();
/*  69 */       stmt = conn.prepareStatement(this.sqlSelector.getSQL(1));
/*  70 */       rs = stmt.executeQuery();
/*  71 */       while (rs.next()) {
/*  72 */         int appID = rs.getInt("APPLICATION_ID");
/*  73 */         String appDesc = rs.getString("APPLICATION_DESC").trim();
/*  74 */         Application app = new Application(Integer.toString(appID) + "-" + appDesc, appDesc, this.resourceIDs);
/*  75 */         apps.add(app);
/*     */       } 
/*  77 */     } catch (Exception e) {
/*  78 */       log.error("Exception when get applications for database " + toString() + "; " + e, e);
/*     */     } finally {
/*     */       try {
/*  81 */         if (rs != null)
/*  82 */           rs.close(); 
/*  83 */         if (stmt != null)
/*  84 */           stmt.close(); 
/*  85 */         if (conn != null)
/*  86 */           releaseConnection(conn); 
/*  87 */       } catch (Exception x) {
/*  88 */         log.error("Exception when close the statement " + toString() + "; " + x, x);
/*     */       } 
/*     */     } 
/*  91 */     Iterator<Application> itApp = apps.iterator();
/*  92 */     while (itApp.hasNext()) {
/*  93 */       getVersions(itApp.next());
/*     */     }
/*  95 */     return apps;
/*     */   }
/*     */   
/*     */   public File getFile(String fileID) {
/*  99 */     log.debug("::getFile - fileID:" + fileID);
/* 100 */     File file = null;
/* 101 */     Connection conn = null;
/* 102 */     PreparedStatement stmt = null;
/* 103 */     ResultSet rs = null;
/*     */     try {
/* 105 */       conn = requestConnection();
/* 106 */       stmt = conn.prepareStatement(this.sqlSelector.getSQL(4));
/* 107 */       stmt.setInt(1, Integer.parseInt(fileID));
/* 108 */       rs = stmt.executeQuery();
/* 109 */       if (rs.next()) {
/* 110 */         String fileName = rs.getString("FILE_NAME").trim();
/* 111 */         int fileType = rs.getInt("FILE_TYPE");
/* 112 */         int fileStatus = rs.getInt("STATUS");
/* 113 */         String fileRevision = rs.getString("REVISION").trim();
/* 114 */         int pageNo = rs.getInt("PAGE_NO");
/* 115 */         int blockNo = rs.getInt("BLOCK_NO");
/* 116 */         String memoryAddr = rs.getString("MEMORY_ADDRESS").trim();
/* 117 */         int blockCount = rs.getInt("NUMBER_OF_BLOCKS");
/* 118 */         long length = 0L;
/* 119 */         byte[] content = null;
/* 120 */         if (this.dblink.getDBMS() == 2) {
/* 121 */           content = rs.getBytes("FILE_CONTENT");
/* 122 */           length = content.length;
/*     */         } else {
/* 124 */           Blob blob = rs.getBlob("FILE_CONTENT");
/* 125 */           length = blob.length();
/* 126 */           content = blob.getBytes(1L, (int)length);
/*     */         } 
/* 128 */         String fingerprint = rs.getString("MD5");
/* 129 */         FileProxy fileProxy = new FileProxy(fileID, fileName, fileRevision, Integer.valueOf(content.length), Integer.valueOf(fileStatus), Integer.valueOf(fileType), fingerprint, null);
/* 130 */         file = new File(Integer.valueOf(pageNo), Integer.valueOf(blockNo), memoryAddr, Integer.valueOf(blockCount), content, fileProxy);
/*     */       } 
/* 132 */     } catch (Exception e) {
/* 133 */       log.error("Exception when get versions for database " + toString() + "; " + e, e);
/*     */     } finally {
/*     */       try {
/* 136 */         if (rs != null)
/* 137 */           rs.close(); 
/* 138 */         if (stmt != null)
/* 139 */           stmt.close(); 
/* 140 */         if (conn != null)
/* 141 */           releaseConnection(conn); 
/* 142 */       } catch (Exception x) {
/* 143 */         log.error("Exception when close the statement " + toString() + "; " + x, x);
/*     */       } 
/*     */     } 
/* 146 */     return file;
/*     */   }
/*     */   
/*     */   private void getVersions(Application app) {
/* 150 */     Set<Version> versions = new HashSet();
/* 151 */     Connection conn = null;
/* 152 */     PreparedStatement stmt = null;
/* 153 */     ResultSet rs = null;
/*     */     try {
/* 155 */       conn = requestConnection();
/* 156 */       stmt = conn.prepareStatement(this.sqlSelector.getSQL(2));
/* 157 */       String applicationID = (String)app.getIdentifier();
/* 158 */       int index = applicationID.indexOf("-");
/* 159 */       applicationID = applicationID.substring(0, index);
/* 160 */       stmt.setInt(1, Integer.parseInt(applicationID));
/* 161 */       rs = stmt.executeQuery();
/* 162 */       while (rs.next()) {
/* 163 */         int versID = rs.getInt("VERSION_ID");
/* 164 */         String versNo = rs.getString("VERSION_NO").trim();
/* 165 */         Date versDate = rs.getDate("VERSION_DATE");
/* 166 */         int versSize = rs.getInt("VERSION_SIZE");
/* 167 */         String additionalInformation = null;
/* 168 */         if (this.sqlSelector instanceof SQLSelectorTech2) {
/* 169 */           additionalInformation = rs.getString("ADDITIONAL_INFORMATION");
/*     */         }
/* 171 */         Version version = new Version(Integer.toString(versID), versNo, Long.valueOf(versDate.getTime()), Long.valueOf(versSize), app, additionalInformation);
/* 172 */         versions.add(version);
/*     */       } 
/* 174 */     } catch (Exception e) {
/* 175 */       log.error("Exception when get versions for database " + toString() + "; " + e, e);
/*     */     } finally {
/*     */       try {
/* 178 */         if (rs != null)
/* 179 */           rs.close(); 
/* 180 */         if (stmt != null)
/* 181 */           stmt.close(); 
/* 182 */         if (conn != null)
/* 183 */           releaseConnection(conn); 
/* 184 */       } catch (Exception x) {
/* 185 */         log.error("Exception when close the statement " + toString() + "; " + x, x);
/*     */       } 
/*     */     } 
/* 188 */     Iterator<Version> itVers = versions.iterator();
/* 189 */     while (itVers.hasNext()) {
/* 190 */       getFiles(itVers.next());
/*     */     }
/* 192 */     app.setVersions(versions);
/*     */   }
/*     */   
/*     */   private void getFiles(Version version) {
/* 196 */     Set<FileProxy> neutralFiles = new HashSet();
/* 197 */     Map<Object, Object> lan2files = new Hashtable<Object, Object>();
/* 198 */     Connection conn = null;
/* 199 */     PreparedStatement stmt = null;
/* 200 */     ResultSet rs = null;
/*     */     try {
/* 202 */       conn = requestConnection();
/* 203 */       stmt = conn.prepareStatement(this.sqlSelector.getSQL(3));
/* 204 */       stmt.setInt(1, Integer.parseInt((String)version.getIdentifier()));
/* 205 */       rs = stmt.executeQuery();
/* 206 */       while (rs.next()) {
/* 207 */         int fileID = rs.getInt("FILE_ID");
/* 208 */         String fileName = rs.getString("FILE_NAME").trim();
/* 209 */         int fileType = rs.getInt("FILE_TYPE");
/* 210 */         int fileStatus = rs.getInt("STATUS");
/* 211 */         String fileRevision = rs.getString("REVISION").trim();
/* 212 */         int fileSize = 0;
/* 213 */         if (this.dblink.getDBMS() == 2) {
/* 214 */           byte[] content = rs.getBytes("FILE_CONTENT");
/* 215 */           fileSize = content.length;
/*     */         } else {
/* 217 */           Blob blob = rs.getBlob("FILE_CONTENT");
/* 218 */           fileSize = (int)blob.length();
/*     */         } 
/* 220 */         String fingerprint = rs.getString("MD5");
/* 221 */         String lanID = rs.getString("LANGUAGE_ID").trim();
/* 222 */         FileProxy fileProxy = new FileProxy(Integer.toString(fileID), fileName, fileRevision, Integer.valueOf(fileSize), Integer.valueOf(fileStatus), Integer.valueOf(fileType), fingerprint, version);
/* 223 */         if (lanID.compareTo("") == 0) {
/* 224 */           neutralFiles.add(fileProxy); continue;
/*     */         } 
/* 226 */         Language lang = LanguageRegistry.getInstance().getLanguage(lanID);
/* 227 */         if (lang != null) {
/* 228 */           Set<FileProxy> files = null;
/* 229 */           if (lan2files.containsKey(lang)) {
/* 230 */             files = (Set)lan2files.get(lang);
/*     */           } else {
/* 232 */             files = new HashSet();
/* 233 */           }  files.add(fileProxy);
/* 234 */           lan2files.put(lang, files); continue;
/*     */         } 
/* 236 */         log.error("ERROR in SWDL: SWDL DB contains an unknown language. Please check Language and SWDL DBs for language: " + lanID);
/*     */       }
/*     */     
/*     */     }
/* 240 */     catch (Exception e) {
/* 241 */       log.error("Exception when get files from database " + toString() + "; " + e, e);
/*     */     } finally {
/*     */       try {
/* 244 */         if (rs != null) {
/* 245 */           rs.close();
/*     */         }
/* 247 */         if (stmt != null) {
/* 248 */           stmt.close();
/*     */         }
/* 250 */         if (conn != null) {
/* 251 */           releaseConnection(conn);
/*     */         }
/* 253 */       } catch (Exception x) {
/* 254 */         log.error("Exception when close the statement " + toString() + "; " + x, x);
/*     */       } 
/*     */     } 
/* 257 */     version.setLanNeutralFiles(neutralFiles);
/* 258 */     version.setLan2Files(lan2files);
/*     */   }
/*     */   
/*     */   public DBVersionInformation getVersionInfo() {
/* 262 */     if (this.dbversion != null) {
/* 263 */       return this.dbversion;
/*     */     }
/* 265 */     Connection con = null;
/* 266 */     Statement stmt = null;
/* 267 */     ResultSet rs = null;
/*     */     try {
/* 269 */       con = requestConnection();
/* 270 */       stmt = con.createStatement();
/* 271 */       rs = stmt.executeQuery("SELECT * FROM RELEASE");
/* 272 */       if (rs.next()) {
/* 273 */         return new DBVersionInformation(rs.getString("CREATOR"), rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*     */       }
/* 275 */       return null;
/*     */     }
/* 277 */     catch (Exception e) {
/* 278 */       log.error("loading swdl-db version information failed (" + toString() + ").");
/* 279 */       return null;
/*     */     } finally {
/*     */       try {
/* 282 */         if (rs != null) {
/* 283 */           rs.close();
/*     */         }
/* 285 */         if (stmt != null) {
/* 286 */           stmt.close();
/*     */         }
/* 288 */         if (con != null) {
/* 289 */           releaseConnection(con);
/*     */         }
/* 291 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\DatabaseAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */