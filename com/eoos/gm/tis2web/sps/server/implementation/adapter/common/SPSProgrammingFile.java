/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSProgrammingFile
/*     */ {
/*     */   protected long id;
/*     */   protected String ftype;
/*     */   protected Object fdata;
/*     */   protected boolean needsSecurityCode;
/*     */   
/*     */   protected SPSProgrammingFile() {}
/*     */   
/*     */   protected SPSProgrammingFile(long id, String ftype, byte[] bytes, boolean needsSecurityCode) throws Exception {
/*  26 */     this(id, ftype, bytes);
/*  27 */     this.needsSecurityCode = needsSecurityCode;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingFile(long id, String ftype, byte[] bytes) throws Exception {
/*  31 */     this.id = id;
/*  32 */     this.ftype = ftype;
/*  33 */     if (ftype.equalsIgnoreCase("prt")) {
/*  34 */       this.fdata = bytes;
/*     */     } else {
/*  36 */       this.fdata = createTemporaryFile(id, bytes);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static SPSProgrammingFile load(IDatabaseLink dblink, long vci) throws Exception {
/*  41 */     return load(dblink, false, vci);
/*     */   }
/*     */   
/*     */   protected static SPSProgrammingFile load(IDatabaseLink dblink, boolean isGME, long vci) throws Exception {
/*  45 */     Connection conn = null;
/*  46 */     PreparedStatement stmt = null;
/*  47 */     ResultSet rs = null;
/*     */     try {
/*  49 */       conn = dblink.requestConnection();
/*  50 */       String sql = null;
/*  51 */       if (isGME) {
/*  52 */         sql = "SELECT FILE_TYPE, FILE_DATA, SecCodeFlag FROM PROGRAMMING_FILES WHERE FILE_ID = ?";
/*     */       } else {
/*  54 */         sql = "SELECT FILE_TYPE, FILE_DATA FROM PROGRAMMING_FILES WHERE FILE_ID = ?";
/*     */       } 
/*  56 */       stmt = conn.prepareStatement(sql);
/*  57 */       stmt.setLong(1, vci);
/*  58 */       rs = stmt.executeQuery();
/*  59 */       if (rs.next()) {
/*  60 */         if (dblink.getDBMS() == 2) {
/*  61 */           return null;
/*     */         }
/*  63 */         boolean needsSecurityCode = false;
/*  64 */         String ftype = rs.getString("FILE_TYPE");
/*  65 */         Blob blob = rs.getBlob("FILE_DATA");
/*  66 */         byte[] bytes = blob.getBytes(1L, (int)blob.length());
/*  67 */         if (isGME) {
/*     */           try {
/*  69 */             needsSecurityCode = rs.getBoolean("SecCodeFlag");
/*  70 */           } catch (Exception x) {}
/*     */           
/*  72 */           return new SPSProgrammingFile(vci, ftype, bytes, needsSecurityCode);
/*     */         } 
/*  74 */         return new SPSProgrammingFile(vci, ftype, bytes);
/*     */       } 
/*     */ 
/*     */       
/*  78 */       return null;
/*     */     }
/*  80 */     catch (Exception e) {
/*  81 */       throw e;
/*     */     } finally {
/*     */       try {
/*  84 */         if (rs != null) {
/*  85 */           rs.close();
/*     */         }
/*  87 */         if (stmt != null) {
/*  88 */           stmt.close();
/*     */         }
/*  90 */         if (conn != null) {
/*  91 */           dblink.releaseConnection(conn);
/*     */         }
/*  93 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected File createTemporaryFile(long vci, byte[] bytes) throws Exception {
/*  99 */     File temp = File.createTempFile("sps-" + vci + "-" + System.currentTimeMillis(), "zip");
/* 100 */     temp.deleteOnExit();
/* 101 */     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(temp));
/* 102 */     bos.write(bytes);
/* 103 */     bos.close();
/* 104 */     return temp;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 108 */     return this.ftype;
/*     */   }
/*     */   
/*     */   public Object getFile() {
/* 112 */     return this.fdata;
/*     */   }
/*     */   
/*     */   public boolean getSecurityCodeFlag() {
/* 116 */     return this.needsSecurityCode;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSProgrammingFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */