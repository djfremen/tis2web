/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ReprogramFunctionSequenceRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReprogramFunctionSequenceRequestImpl
/*    */   implements ReprogramFunctionSequenceRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private List functions;
/*    */   protected List list;
/*    */   protected List preInstructions;
/*    */   protected List postInstructions;
/*    */   
/*    */   public ReprogramFunctionSequenceRequestImpl(List functions, List list, List preInstructions, List postInstructions) {
/* 15 */     this.functions = functions;
/* 16 */     this.list = list;
/* 17 */     this.preInstructions = preInstructions;
/* 18 */     this.postInstructions = postInstructions;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List getFunctionLabels() {
/* 24 */     return this.functions;
/*    */   }
/*    */   
/*    */   public List getPostProgrammingInstructions() {
/* 28 */     return this.postInstructions;
/*    */   }
/*    */   
/*    */   public List getPreProgrammingInstructions() {
/* 32 */     return this.preInstructions;
/*    */   }
/*    */   
/*    */   public List getProgrammingDataList() {
/* 36 */     return this.list;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ReprogramFunctionSequenceRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */