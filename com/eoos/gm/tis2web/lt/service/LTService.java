/*    */ package com.eoos.gm.tis2web.lt.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*    */ 
/*    */ public interface LTService extends Service, VisualModule, CfgProviderRetrieval {
/*    */   void setMainWork(String paramString1, String paramString2) throws LTServiceException;
/*    */   
/*    */   boolean isMainWorkValid(String paramString1, String paramString2);
/*    */   
/*    */   public static class LTServiceException extends Exception {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public LTServiceException(Throwable cause) {
/* 16 */       super(cause);
/*    */     }
/*    */ 
/*    */     
/*    */     public LTServiceException() {}
/*    */ 
/*    */     
/*    */     public LTServiceException(String message, Throwable cause) {
/* 24 */       super(message, cause);
/*    */     }
/*    */     
/*    */     public LTServiceException(String message) {
/* 28 */       super(message);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\service\LTService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */