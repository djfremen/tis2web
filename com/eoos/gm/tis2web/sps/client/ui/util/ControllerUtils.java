/*    */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*    */ 
/*    */ public class ControllerUtils
/*    */ {
/*    */   public static String getLongNameController(String denotation) {
/*  6 */     String longName = null;
/*  7 */     int indexTab = denotation.indexOf("\t");
/*  8 */     if (indexTab == -1) {
/*  9 */       longName = denotation;
/*    */     } else {
/*    */       
/* 12 */       longName = denotation.substring(indexTab, denotation.length());
/*    */     } 
/* 14 */     return longName;
/*    */   }
/*    */   
/*    */   public static String getShortNameController(String denotation) {
/* 18 */     String shortName = null;
/* 19 */     int indexTab = denotation.indexOf("\t");
/* 20 */     if (indexTab == -1) {
/* 21 */       shortName = denotation;
/*    */     } else {
/*    */       
/* 24 */       shortName = denotation.substring(0, indexTab);
/*    */     } 
/*    */     
/* 27 */     return shortName;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\ControllerUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */