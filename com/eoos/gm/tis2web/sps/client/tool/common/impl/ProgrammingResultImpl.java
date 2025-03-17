/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ProgrammingResult;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class ProgrammingResultImpl
/*    */   implements ProgrammingResult, Serializable
/*    */ {
/* 10 */   private Boolean status = new Boolean(false);
/*    */   
/*    */   private Object data;
/*    */ 
/*    */   
/*    */   private ProgrammingResultImpl() {}
/*    */ 
/*    */   
/*    */   public ProgrammingResultImpl(boolean result, Object data) {
/* 19 */     this.status = new Boolean(result);
/* 20 */     this.data = data;
/*    */   }
/*    */   
/*    */   public Boolean getStatus() {
/* 24 */     return this.status;
/*    */   }
/*    */   
/*    */   public Object getData() {
/* 28 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ProgrammingResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */