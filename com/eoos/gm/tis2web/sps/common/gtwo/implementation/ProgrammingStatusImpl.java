/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ProgrammingStatusImpl
/*    */   implements ProgrammingStatus, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected boolean status;
/*    */   protected Integer stepNumber;
/*    */   protected Integer errorCode;
/*    */   protected String message;
/*    */   
/*    */   public ProgrammingStatusImpl(boolean status, Integer stepNumber, Integer errorCode, String message) {
/* 15 */     this.status = status;
/* 16 */     this.stepNumber = stepNumber;
/* 17 */     this.errorCode = errorCode;
/* 18 */     this.message = message;
/*    */   }
/*    */   
/*    */   public boolean getStatus() {
/* 22 */     return this.status;
/*    */   }
/*    */   
/*    */   public Integer getStepNumber() {
/* 26 */     return this.stepNumber;
/*    */   }
/*    */   
/*    */   public Integer getErrorCode() {
/* 30 */     return this.errorCode;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 34 */     return this.message;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ProgrammingStatusImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */