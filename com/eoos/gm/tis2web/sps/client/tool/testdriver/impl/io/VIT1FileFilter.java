/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT1FileFilter
/*    */   implements FileFilter
/*    */ {
/*    */   public boolean accept(File pathname) {
/* 16 */     if (pathname.isFile() && pathname.getName().toUpperCase(Locale.ENGLISH).endsWith(".VIT1")) {
/* 17 */       return true;
/*    */     }
/* 19 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\VIT1FileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */