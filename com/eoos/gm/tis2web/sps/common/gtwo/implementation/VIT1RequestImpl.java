/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VIT1Request;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VIT1RequestImpl
/*    */   implements VIT1Request {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected List rdata;
/*    */   
/*    */   public VIT1RequestImpl(List rdata) {
/* 12 */     this.rdata = rdata;
/*    */   }
/*    */   
/*    */   public List getRequestMethodData() {
/* 16 */     return this.rdata;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\VIT1RequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */