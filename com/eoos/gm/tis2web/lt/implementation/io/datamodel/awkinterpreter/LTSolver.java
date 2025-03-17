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
/*    */ 
/*    */ public abstract class LTSolver
/*    */   extends Solver
/*    */ {
/*    */   protected AWKInterpreter inter;
/*    */   
/*    */   protected LTSolver(AWKInterpreter c) {
/* 20 */     super(1);
/* 21 */     this.inter = c;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */