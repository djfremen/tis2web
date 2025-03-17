/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.xml;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSControllerVCI;
/*    */ 
/*    */ public class XMLProgrammingTypes
/*    */ {
/*  7 */   public static final Integer PROGRAMMING = Integer.valueOf(1);
/*  8 */   public static final Integer CONFIGURATION = Integer.valueOf(2);
/*  9 */   public static final Integer TYPE4 = Integer.valueOf(3);
/*    */   
/*    */   public boolean isXMLProgramming(SPSControllerVCI controller) {
/* 12 */     return controller.isXMLProgramming();
/*    */   }
/*    */   
/*    */   public boolean isXMLConfiguration(SPSControllerVCI controller) {
/* 16 */     return controller.isXMLConfiguration();
/*    */   }
/*    */   
/*    */   public boolean isType4(SPSControllerVCI controller) {
/* 20 */     return controller.isType4();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\xml\XMLProgrammingTypes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */