/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramControllerRequest;
/*    */ 
/*    */ public class ReprogramRequestImpl
/*    */   implements ReprogramControllerRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected ProgrammingData data;
/*    */   
/*    */   public ReprogramRequestImpl(ProgrammingData data) {
/* 12 */     this.data = data;
/*    */   }
/*    */   
/*    */   public ProgrammingData getProgrammingData() {
/* 16 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ReprogramRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */