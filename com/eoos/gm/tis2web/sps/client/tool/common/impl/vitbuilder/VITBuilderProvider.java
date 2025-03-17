/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VITBuilderProvider
/*    */ {
/*    */   public static final String VIT1_BUILDER = "VIT1 Builder";
/*    */   public static final String VIT2_BUILDER = "VIT2 Builder";
/*    */   
/*    */   public static VITBuilder getBuilder(String builderType, Object param1, Object param2) {
/* 17 */     if (builderType.equals("VIT1 Builder")) {
/* 18 */       return new VIT1BuilderImpl(param1);
/*    */     }
/* 20 */     if (builderType.equals("VIT2 Builder")) {
/* 21 */       return new VIT2BuilderImpl(param1, param2);
/*    */     }
/*    */     
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\vitbuilder\VITBuilderProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */