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
/*    */ public class LTORSolver
/*    */   extends LTSolver
/*    */ {
/*    */   private Solver left;
/*    */   private Solver right;
/*    */   
/*    */   public LTORSolver(Solver left, Solver right, AWKInterpreter i) {
/* 21 */     super(i);
/* 22 */     this.left = left;
/* 23 */     this.right = right;
/*    */   }
/*    */   
/*    */   public TriStateLogic apply() {
/* 27 */     return TriStateLogic.or(this.left.apply(), this.right.apply());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTORSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */