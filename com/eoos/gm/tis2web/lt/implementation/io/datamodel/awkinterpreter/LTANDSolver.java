/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Solver;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.TriStateLogic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTANDSolver
/*    */   extends LTSolver
/*    */ {
/*    */   private Solver left;
/*    */   private Solver right;
/*    */   
/*    */   public LTANDSolver(Solver left, Solver right, AWKInterpreter i) {
/* 21 */     super(i);
/* 22 */     this.left = left;
/* 23 */     this.right = right;
/*    */   }
/*    */   
/*    */   public TriStateLogic apply() {
/* 27 */     return TriStateLogic.and(this.left.apply(), this.right.apply());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTANDSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */