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
/*    */ public class LTORRule
/*    */   extends LTBiFuncRule
/*    */ {
/* 16 */   public static char OR_OP = ',';
/*    */ 
/*    */   
/*    */   public LTORRule() {
/* 20 */     super(OR_OP);
/*    */   }
/*    */ 
/*    */   
/*    */   public int Law() {
/* 25 */     return LTRule.ORLAW;
/*    */   }
/*    */   
/*    */   protected LTSolver createSolver(Solver left, Solver right, AWKInterpreter inter) {
/* 29 */     return new LTORSolver(left, right, inter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTORRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */