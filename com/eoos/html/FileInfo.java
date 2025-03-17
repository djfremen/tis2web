/*     */ package com.eoos.html;
/*     */ 
/*     */ import java.io.File;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileInfo
/*     */ {
/*  31 */   private File file = null;
/*  32 */   private String clientFileName = null;
/*  33 */   private String fileContentType = null;
/*  34 */   private String name = null;
/*  35 */   private StringBuffer sb = new StringBuffer(100);
/*  36 */   private byte[] fileContents = null;
/*     */   private boolean complete = false;
/*  38 */   private int maxSize = 0;
/*     */   
/*     */   public void setClientFileName(String aClientFileName) {
/*  41 */     this.clientFileName = aClientFileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClientFileName() {
/*  46 */     return this.clientFileName;
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/*  50 */     return this.complete;
/*     */   }
/*     */   
/*     */   public void setFileContentType(String aContentType) {
/*  54 */     this.fileContentType = aContentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFileContentType() {
/*  59 */     return this.fileContentType;
/*     */   }
/*     */   
/*     */   public void setFileContents(byte[] aByteArray) {
/*  63 */     this.fileContents = new byte[aByteArray.length];
/*  64 */     System.arraycopy(aByteArray, 0, this.fileContents, 0, aByteArray.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getFileContents() {
/*  69 */     return this.fileContents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocalFile(File aFile) {
/*  74 */     this.file = aFile;
/*     */   }
/*     */   
/*     */   public File getLocalFile() {
/*  78 */     return this.file;
/*     */   }
/*     */   
/*     */   public int getMaxSize() {
/*  82 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String aName) {
/*  87 */     this.name = aName;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  95 */     this.sb.setLength(0);
/*  96 */     this.sb.append("               name = " + this.name + "\n");
/*  97 */     this.sb.append("     clientFileName = " + this.clientFileName + "\n");
/*     */     
/*  99 */     if (this.file != null) {
/* 100 */       this.sb.append("      File.toString = " + this.file + " (size=" + this.file.length() + ")\n");
/*     */     } else {
/* 102 */       this.sb.append("fileContents.length = " + this.fileContents.length + "\n");
/*     */     } 
/*     */     
/* 105 */     return this.sb.toString();
/*     */   }
/*     */   
/*     */   protected void setComplete() {
/* 109 */     this.complete = true;
/*     */   }
/*     */   
/*     */   protected void setMaxSize(int n) {
/* 113 */     this.maxSize = n;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\FileInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */