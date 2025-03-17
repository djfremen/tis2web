/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramSequenceRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReprogramSequenceRequestImpl
/*    */   implements ReprogramSequenceRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private List controllers;
/*    */   protected List list;
/*    */   protected List type4;
/*    */   protected List instructions;
/*    */   protected List reaction;
/*    */   
/*    */   public ReprogramSequenceRequestImpl(List controllers, List list, List type4, List instructions, List reaction) {
/* 16 */     this.controllers = controllers;
/* 17 */     this.list = list;
/* 18 */     this.type4 = type4;
/* 19 */     this.instructions = instructions;
/* 20 */     this.reaction = reaction;
/*    */   }
/*    */   
/*    */   public List getProgrammingDataList() {
/* 24 */     return this.list;
/*    */   }
/*    */   
/*    */   public List getControllers() {
/* 28 */     return this.controllers;
/*    */   }
/*    */   
/*    */   public List getType4Data() {
/* 32 */     return this.type4;
/*    */   }
/*    */   
/*    */   public List getInstructions() {
/* 36 */     return this.instructions;
/*    */   }
/*    */   
/*    */   public List getFailureHandling() {
/* 40 */     return this.reaction;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ReprogramSequenceRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */