/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VCExtRequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.RequestGroupImpl;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class RequestGroupBuilderImpl
/*    */ {
/*    */   public RequestGroup makeRequestGroup() {
/* 23 */     RequestGroup reqGroup = RequestGroupImpl.createInstance();
/* 24 */     return reqGroup;
/*    */   }
/*    */   
/*    */   public VCExtRequestGroup makeVCExtRequestGroup(String vinID, List attrs, Map pairAttrValue) {
/* 28 */     return new VCExtRequestGroupImpl(vinID, attrs, pairAttrValue);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\RequestGroupBuilderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */