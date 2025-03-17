/*    */ package com.eoos.gm.tis2web.sas.common.model.reqres.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SecurityCodesImpl
/*    */   implements SSAResponse.SecurityCodes, Serializable
/*    */ {
/*    */   protected static final long serialVersionUID = 1L;
/*    */   private String codeImmobilizer;
/*    */   private String codeInfotainment;
/*    */   
/*    */   public SecurityCodesImpl(String codeImmobilizer, String codeInfotainment) {
/* 17 */     this.codeImmobilizer = codeImmobilizer;
/* 18 */     this.codeInfotainment = codeInfotainment;
/*    */   }
/*    */   
/*    */   public String getImmobilizerSecurityCode() {
/* 22 */     return this.codeImmobilizer;
/*    */   }
/*    */   
/*    */   public String getInfotainmentSecurityCode() {
/* 26 */     return this.codeInfotainment;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\impl\SecurityCodesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */