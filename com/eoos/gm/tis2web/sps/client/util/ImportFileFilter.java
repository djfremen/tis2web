/*    */ package com.eoos.gm.tis2web.sps.client.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class ImportFileFilter
/*    */   extends FileFilter
/*    */ {
/*    */   protected String description;
/*    */   protected List fileTypes;
/*    */   
/*    */   public ImportFileFilter(String description, List fileTypes) {
/* 15 */     this.description = description;
/* 16 */     this.fileTypes = fileTypes;
/*    */   }
/*    */   
/*    */   public boolean accept(File f) {
/* 20 */     if (f.isDirectory()) {
/* 21 */       return true;
/*    */     }
/* 23 */     String extension = getExtension(f);
/* 24 */     for (int i = 0; i < this.fileTypes.size(); i++) {
/* 25 */       if (this.fileTypes.get(i).equals(extension)) {
/* 26 */         return true;
/*    */       }
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   protected String getExtension(File f) {
/* 33 */     String s = f.getName();
/* 34 */     int i = s.lastIndexOf('.');
/* 35 */     if (i > 0 && i < s.length() - 1) {
/* 36 */       return s.substring(i).toLowerCase(Locale.ENGLISH);
/*    */     }
/* 38 */     return "";
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 42 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\ImportFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */