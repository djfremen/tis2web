/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModule;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSModule
/*     */   implements SPSModule
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected String id;
/*     */   protected int order;
/*     */   protected String name;
/*     */   protected String description;
/*     */   protected int type;
/*     */   protected SPSPart origin;
/*     */   
/*     */   SPSModule(int id, int order, String name, int type, SPSPart origin) {
/*  35 */     this.id = Integer.toString(id);
/*  36 */     this.order = order;
/*  37 */     this.name = name;
/*  38 */     this.type = type;
/*  39 */     this.origin = origin;
/*     */   }
/*     */   
/*     */   void setDescription(String description) {
/*  43 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/*  47 */     return this.description;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  51 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getID() {
/*  55 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  59 */     return this.order;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  63 */     return this.description;
/*     */   }
/*     */   
/*     */   public boolean isPROM() {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSelectablePart(Part part) {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModuleInfo(SPSLanguage language, SPSSchemaAdapterGME adapter) throws Exception {
/*  78 */     if (this.order != 1 || this.type == 0) {
/*  79 */       return null;
/*     */     }
/*  81 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/*  82 */     Connection conn = null;
/*  83 */     DBMS.PreparedStatement stmt = null;
/*  84 */     ResultSet rs = null;
/*     */     try {
/*  86 */       conn = dblink.requestConnection();
/*  87 */       String sql = DBMS.getSQL(dblink, "SELECT d.Description FROM SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = ?  AND d.LanguageID = ?");
/*  88 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  89 */       stmt.setInt(1, Integer.parseInt(this.id));
/*  90 */       String languageID = null;
/*  91 */       if (dblink.getDBMS() == 2) {
/*  92 */         languageID = language.getLocale();
/*     */       } else {
/*  94 */         languageID = language.getID();
/*     */       } 
/*     */       
/*  97 */       stmt.setString(2, languageID);
/*     */       
/*  99 */       rs = stmt.executeQuery();
/* 100 */       if (rs.next()) {
/* 101 */         String description = DBMS.getString(dblink, language, rs, 1);
/* 102 */         return description;
/*     */       } 
/* 104 */     } catch (Exception e) {
/* 105 */       throw e;
/*     */     } finally {
/*     */       try {
/* 108 */         if (rs != null) {
/* 109 */           rs.close();
/*     */         }
/* 111 */         if (stmt != null) {
/* 112 */           stmt.close();
/*     */         }
/* 114 */         if (conn != null) {
/* 115 */           dblink.releaseConnection(conn);
/*     */         }
/* 117 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String strip11567(String sql) {
/* 125 */     return StringUtilities.replace(sql, ", DOWNLOAD_SITE", "");
/*     */   }
/*     */   
/*     */   public ProgrammingDataUnit getCalibrationFileInfo(SPSPart part, SPSSchemaAdapter adapter, boolean onlyNAO) throws Exception {
/* 129 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 130 */     Connection conn = null;
/* 131 */     DBMS.PreparedStatement stmt = null;
/* 132 */     ResultSet rs = null;
/*     */     try {
/* 134 */       conn = dblink.requestConnection();
/* 135 */       String sql = DBMS.getSQL(dblink, "SELECT BLOBSIZE, BLOBCHECKSUM, DOWNLOAD_SITE FROM SPS_Blobs WHERE ModuleID = ?");
/* 136 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 137 */       stmt.setInt(1, part.getID());
/*     */       try {
/* 139 */         rs = stmt.executeQuery();
/* 140 */       } catch (Exception s) {
/* 141 */         stmt.close();
/* 142 */         stmt = DBMS.prepareSQLStatement(conn, strip11567(sql));
/* 143 */         stmt.setInt(1, part.getID());
/* 144 */         rs = stmt.executeQuery();
/*     */       } 
/* 146 */       if (rs.next()) {
/* 147 */         String name = this.name;
/* 148 */         Integer identifier = Integer.valueOf(getOrder());
/* 149 */         Integer size = Integer.valueOf(rs.getInt(1));
/* 150 */         String site = null;
/*     */         try {
/* 152 */           site = rs.getString(3);
/* 153 */         } catch (Exception x) {}
/*     */         
/* 155 */         DownloadServer dwnldServer = Util.isNullOrEmpty(site) ? null : adapter.getCalibrationDataDownloadSite();
/* 156 */         String downloadID = (dwnldServer == null) ? null : Integer.toString(part.getID());
/* 157 */         if (dblink.getDBMS() == 2) {
/* 158 */           return (ProgrammingDataUnit)new SPSBlobImpl(name, identifier, size, null, dwnldServer, downloadID);
/*     */         }
/* 160 */         String checksum = DBMS.trimString(rs.getString(2));
/* 161 */         return (ProgrammingDataUnit)new SPSBlobImpl(name, identifier, size, StringUtilities.hexToBytes(checksum), dwnldServer, downloadID);
/*     */       } 
/*     */       
/* 164 */       return null;
/*     */     }
/* 166 */     catch (Exception e) {
/* 167 */       throw e;
/*     */     } finally {
/*     */       try {
/* 170 */         if (rs != null) {
/* 171 */           rs.close();
/*     */         }
/* 173 */         if (stmt != null) {
/* 174 */           stmt.close();
/*     */         }
/* 176 */         if (conn != null) {
/* 177 */           dblink.releaseConnection(conn);
/*     */         }
/* 179 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getCalibrationFile(SPSPart part, SPSSchemaAdapter adapter, boolean onlyNAO) throws Exception {
/* 185 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 186 */     Connection conn = null;
/* 187 */     DBMS.PreparedStatement stmt = null;
/* 188 */     ResultSet rs = null;
/*     */     try {
/* 190 */       conn = dblink.requestConnection();
/* 191 */       String sql = DBMS.getSQL(dblink, "SELECT ModuleBlob FROM SPS_Blobs WHERE ModuleID = ?");
/* 192 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 193 */       stmt.setInt(1, part.getID());
/* 194 */       rs = stmt.executeQuery();
/* 195 */       if (rs.next()) {
/* 196 */         if (dblink.getDBMS() == 2) {
/* 197 */           return rs.getBytes(1);
/*     */         }
/* 199 */         Blob blob = rs.getBlob(1);
/* 200 */         return blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 203 */       return null;
/*     */     }
/* 205 */     catch (Exception e) {
/* 206 */       throw e;
/*     */     } finally {
/*     */       try {
/* 209 */         if (rs != null) {
/* 210 */           rs.close();
/*     */         }
/* 212 */         if (stmt != null) {
/* 213 */           stmt.close();
/*     */         }
/* 215 */         if (conn != null) {
/* 216 */           dblink.releaseConnection(conn);
/*     */         }
/* 218 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Part getOriginPart() {
/* 224 */     return (Part)this.origin;
/*     */   }
/*     */   
/*     */   public Part getSelectedPart() {
/* 228 */     return (Part)this.origin;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedPart(Part part) {}
/*     */   
/*     */   public String getCurrentCalibration() {
/* 235 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */