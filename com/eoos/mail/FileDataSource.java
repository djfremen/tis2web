/*    */ package com.eoos.mail;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public abstract class FileDataSource
/*    */   implements DataSource {
/*    */   protected File file;
/*    */   
/*    */   public FileDataSource(File file) {
/* 14 */     this.file = file;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 18 */     return new BufferedInputStream(new FileInputStream(this.file));
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.file.getName();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\FileDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */