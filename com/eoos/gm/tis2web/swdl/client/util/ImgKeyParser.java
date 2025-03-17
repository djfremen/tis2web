/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImgKeyParser
/*    */ {
/*    */   public static String getImgKey(String className, String dev) {
/* 19 */     int indx = className.lastIndexOf('.');
/* 20 */     if (indx == -1)
/* 21 */       return className + "." + dev + ".img"; 
/* 22 */     String name = className.substring(indx + 1) + "." + dev + ".img";
/* 23 */     return name;
/*    */   }
/*    */   
/*    */   public static String getImgKey(String className) {
/* 27 */     int indx = className.lastIndexOf('.');
/* 28 */     if (indx == -1)
/* 29 */       return className + ".img"; 
/* 30 */     String name = className.substring(indx + 1) + ".img";
/* 31 */     return name;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\ImgKeyParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */