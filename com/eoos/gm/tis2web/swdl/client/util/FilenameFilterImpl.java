/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilenameFilterImpl
/*    */   implements FilenameFilter
/*    */ {
/* 15 */   private String mask = null;
/*    */ 
/*    */   
/*    */   public FilenameFilterImpl(String mask) {
/* 19 */     this.mask = mask;
/*    */   }
/*    */   
/*    */   public boolean accept(File dir, String name) {
/* 23 */     int indx = this.mask.indexOf('*');
/* 24 */     if (indx > -1) {
/* 25 */       String mask1 = this.mask.substring(0, indx);
/* 26 */       String mask2 = this.mask.substring(indx + 1);
/* 27 */       return (name.startsWith(mask1) && name.endsWith(mask2));
/*    */     } 
/* 29 */     return name.startsWith(this.mask);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\FilenameFilterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */