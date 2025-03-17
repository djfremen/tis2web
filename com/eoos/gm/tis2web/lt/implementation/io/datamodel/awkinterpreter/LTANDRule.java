/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Solver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTANDRule
/*    */   extends LTBiFuncRule
/*    */ {
/* 16 */   public static char AND_OP = ':';
/*    */ 
/*    */   
/*    */   public LTANDRule() {
/* 20 */     super(AND_OP);
/*    */   }
/*    */ 
/*    */   
/*    */   public int Law() {
/* 25 */     return LTRule.ANDLAW;
/*    */   }
/*    */   
/*    */   protected LTSolver createSolver(Solver left, Solver right, AWKInterpreter inter) {
/* 29 */     return new LTANDSolver(left, right, inter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTANDRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */