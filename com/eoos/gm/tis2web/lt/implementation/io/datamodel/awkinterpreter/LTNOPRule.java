/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKQual;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Interpreter;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Solver;
/*    */ import java.util.List;
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
/*    */ public class LTNOPRule
/*    */   extends LTRule
/*    */ {
/* 20 */   public static char NOP_OP = '.';
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int Law() {
/* 28 */     return LTRule.NOPLAW;
/*    */   }
/*    */   
/*    */   public Solver canApply(List<LTAWKQual> quallist, Interpreter oI) {
/* 32 */     if (quallist.size() == 0 || quallist.size() > 1) {
/* 33 */       return null;
/*    */     }
/* 35 */     if (((LTAWKQual)quallist.get(0)).getOperator() != NOP_OP) {
/* 36 */       return null;
/*    */     }
/* 38 */     return new LTNOPSolver(quallist.get(0), (AWKInterpreter)oI);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTNOPRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */