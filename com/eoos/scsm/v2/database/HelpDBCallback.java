/*    */ package com.eoos.scsm.v2.database;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.sql.Blob;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class HelpDBCallback
/*    */   implements BlobExtraction.Callback
/*    */ {
/*    */   public Blob getBlob(ResultSet rs) throws Exception {
/* 18 */     return rs.getBlob(3);
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream(File outputDir, ResultSet rs) throws Exception {
/* 22 */     Locale locale = Util.parseLocale(rs.getString(1));
/* 23 */     String name = rs.getString(2);
/*    */     
/* 25 */     if (locale != null) {
/* 26 */       outputDir = new File(outputDir, locale.getLanguage());
/* 27 */       if (!outputDir.mkdirs() && !outputDir.exists()) {
/* 28 */         throw new IllegalStateException("unable to create output directory: " + outputDir);
/*    */       }
/* 30 */       if (!Util.isNullOrEmpty(locale.getCountry())) {
/* 31 */         outputDir = new File(outputDir, locale.getCountry());
/* 32 */         if (!outputDir.mkdirs() && !outputDir.exists()) {
/* 33 */           throw new IllegalStateException("unable to create output directory: " + outputDir);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     File destFile = new File(outputDir, name);
/* 39 */     return new BufferedOutputStream(new FileOutputStream(destFile), 4096);
/*    */   }
/*    */   
/*    */   public void setParameters(PreparedStatement stmt) throws Exception {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\database\HelpDBCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */