/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VINRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ 
/*    */ 
/*    */ public class VINRequestImpl
/*    */   extends AssignmentRequestImpl
/*    */   implements VINRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public VINRequestImpl(RequestGroup requestGroup) {
/* 15 */     super(requestGroup, CommonAttribute.VIN);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\VINRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */