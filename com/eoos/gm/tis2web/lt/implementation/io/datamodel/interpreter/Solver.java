/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter;
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
/*    */ 
/*    */ public abstract class Solver
/*    */ {
/*    */   private int m_parsingpos;
/*    */   
/*    */   public abstract TriStateLogic apply();
/*    */   
/*    */   public int appliedPosition() {
/* 20 */     return this.m_parsingpos;
/*    */   }
/*    */   
/*    */   protected Solver(int parsingpos) {
/* 24 */     this.m_parsingpos = parsingpos;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\interpreter\Solver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */