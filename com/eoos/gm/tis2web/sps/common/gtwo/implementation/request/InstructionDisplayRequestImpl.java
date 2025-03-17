/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class InstructionDisplayRequestImpl
/*    */   extends ConfirmationRequestImpl
/*    */   implements InstructionDisplayRequest, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String content;
/*    */   protected List instructions;
/*    */   
/*    */   public InstructionDisplayRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, String content, List instructions) {
/* 19 */     super(requestGroup, attribute, confirmationValue);
/* 20 */     this.content = content;
/* 21 */     this.instructions = instructions;
/*    */   }
/*    */   
/*    */   public String getContent() {
/* 25 */     return this.content;
/*    */   }
/*    */   
/*    */   public List getInstructions() {
/* 29 */     return this.instructions;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\InstructionDisplayRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */