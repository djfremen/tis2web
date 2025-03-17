/*     */ package com.eoos.gm.tis2web.swdl.client.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SDFileInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  14 */   private int fileID = 0;
/*  15 */   private String fileName = null;
/*  16 */   private int fileType = 0;
/*  17 */   private int pageNo = 0;
/*  18 */   private int blockNo = 0;
/*  19 */   private int memAddr = 0;
/*  20 */   private int noBlocks = 0;
/*  21 */   private int blobSize = 0;
/*  22 */   private int zipSize = 0;
/*  23 */   private short status = 0;
/*  24 */   private String revision = null;
/*     */ 
/*     */   
/*     */   public SDFileInfo(int fID, String fName, int fType, int pNo, int bNo, int addr, int blocks, int bSize, short st, String rev) {
/*  28 */     this.fileID = fID;
/*  29 */     this.fileName = fName;
/*  30 */     this.fileType = fType;
/*  31 */     this.pageNo = pNo;
/*  32 */     this.blockNo = bNo;
/*  33 */     this.memAddr = addr;
/*  34 */     this.noBlocks = blocks;
/*  35 */     this.blobSize = bSize;
/*  36 */     this.status = st;
/*  37 */     this.revision = rev;
/*     */   }
/*     */   
/*     */   public SDFileInfo(String[] params) throws Exception {
/*     */     try {
/*  42 */       this.fileID = Integer.parseInt(params[0]);
/*  43 */       this.fileName = params[1];
/*  44 */       this.fileType = Integer.parseInt(params[2]);
/*  45 */       this.pageNo = Integer.parseInt(params[3]);
/*  46 */       this.blockNo = Integer.parseInt(params[4]);
/*  47 */       this.memAddr = Integer.parseInt(params[5]);
/*  48 */       this.noBlocks = Integer.parseInt(params[6]);
/*  49 */       this.blobSize = Integer.parseInt(params[7]);
/*  50 */       this.status = Short.parseShort(params[8]);
/*  51 */       this.revision = params[9];
/*  52 */     } catch (Exception e) {
/*  53 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFileID() {
/*  58 */     return this.fileID;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/*  62 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public int getFileType() {
/*  66 */     return this.fileType;
/*     */   }
/*     */   
/*     */   public int getPageNo() {
/*  70 */     return this.pageNo;
/*     */   }
/*     */   
/*     */   public int getBlockNo() {
/*  74 */     return this.blockNo;
/*     */   }
/*     */   
/*     */   public int getMemAddr() {
/*  78 */     return this.memAddr;
/*     */   }
/*     */   
/*     */   public int getNoBlocks() {
/*  82 */     return this.noBlocks;
/*     */   }
/*     */   
/*     */   public int getBlobSize() {
/*  86 */     return this.blobSize;
/*     */   }
/*     */   
/*     */   public short getStatus() {
/*  90 */     return this.status;
/*     */   }
/*     */   
/*     */   public String getRevision() {
/*  94 */     return this.revision;
/*     */   }
/*     */   
/*     */   public int getZipSize() {
/*  98 */     return this.zipSize;
/*     */   }
/*     */   
/*     */   public void setZipSize(int zipSize) {
/* 102 */     this.zipSize = zipSize;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\model\SDFileInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */