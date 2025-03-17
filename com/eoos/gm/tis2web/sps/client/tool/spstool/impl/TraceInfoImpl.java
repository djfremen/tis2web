/*    */ package com.eoos.gm.tis2web.sps.client.tool.spstool.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ITraceInfo;
/*    */ 
/*    */ public class TraceInfoImpl
/*    */   implements ITraceInfo {
/*  7 */   private Integer step = null;
/*  8 */   private Integer opCode = null;
/*  9 */   private Integer respCode = null;
/*    */   
/*    */   TraceInfoImpl(int step, int opCode, int respCode) {
/* 12 */     this.step = Integer.valueOf(step);
/* 13 */     this.opCode = Integer.valueOf(opCode);
/* 14 */     this.respCode = Integer.valueOf(respCode);
/*    */   }
/*    */   
/*    */   public Integer getStep() {
/* 18 */     return this.step;
/*    */   }
/*    */   
/*    */   public Integer getOperationCode() {
/* 22 */     return this.opCode;
/*    */   }
/*    */   
/*    */   public Integer getResponseCode() {
/* 26 */     return this.respCode;
/*    */   }
/*    */   
/*    */   public void setStep(Integer step) {
/* 30 */     this.step = step;
/*    */   }
/*    */   
/*    */   public void setOperationCode(Integer opCode) {
/* 34 */     this.opCode = opCode;
/*    */   }
/*    */   
/*    */   public void setResponseCode(Integer respCode) {
/* 38 */     this.respCode = respCode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return "Step = " + Integer.toHexString(this.step.intValue()) + " OpCode = " + Integer.toHexString(this.step.intValue()) + " RespCode = " + Integer.toHexString(this.step.intValue());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\spstool\impl\TraceInfoImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */