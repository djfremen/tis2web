/*    */ package com.eoos.gm.tis2web.ctoc.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*    */ 
/*    */ public interface SICTOCService
/*    */   extends CTOCService, CfgProvider {
/*    */   boolean supports(IConfiguration paramIConfiguration);
/*    */   
/*    */   public static interface Retrieval {
/*    */     SICTOCService getSICTOCService();
/*    */     
/*    */     public static final class RI implements Retrieval {
/*    */       public RI(SICTOCService siCTOCService) {
/* 15 */         this.siCTOCService = siCTOCService;
/*    */       }
/*    */       private SICTOCService siCTOCService;
/*    */       public SICTOCService getSICTOCService() {
/* 19 */         return this.siCTOCService;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\SICTOCService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */