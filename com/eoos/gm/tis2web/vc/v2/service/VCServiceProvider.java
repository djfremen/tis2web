/*    */ package com.eoos.gm.tis2web.vc.v2.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ 
/*    */ public class VCServiceProvider {
/*  6 */   private static final VCServiceProvider instance = new VCServiceProvider();
/*    */   
/*    */   public static VCServiceProvider getInstance() {
/*  9 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VCService getService(ClientContext context) {
/* 17 */     return VCServiceImpl.getInstance(context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VCServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */