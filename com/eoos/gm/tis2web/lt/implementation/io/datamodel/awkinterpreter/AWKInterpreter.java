/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTVCContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Interpreter;
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
/*    */ public class AWKInterpreter
/*    */   extends Interpreter
/*    */ {
/*    */   private LTVCContext vc;
/*    */   
/*    */   public AWKInterpreter(LTVCContext ovc) {
/* 21 */     this.vc = ovc;
/* 22 */     addRule(new LTORRule());
/* 23 */     addRule(new LTANDRule());
/* 24 */     addRule(new LTPORRule());
/* 25 */     addRule(new LTNOPRule());
/*    */   }
/*    */   
/*    */   public LTVCContext getVC() {
/* 29 */     return this.vc;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\AWKInterpreter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */