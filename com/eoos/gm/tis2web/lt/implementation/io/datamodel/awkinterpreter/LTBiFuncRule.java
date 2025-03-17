/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKQual;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Interpreter;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Solver;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
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
/*    */ public abstract class LTBiFuncRule
/*    */   extends LTRule
/*    */ {
/*    */   char operator;
/*    */   
/*    */   public LTBiFuncRule(char op) {
/* 26 */     this.operator = op;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract LTSolver createSolver(Solver paramSolver1, Solver paramSolver2, AWKInterpreter paramAWKInterpreter);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Solver canApply(List<?> quallist, Interpreter oI) {
/* 39 */     if (quallist.size() < 2) {
/* 40 */       return null;
/*    */     }
/*    */     
/* 43 */     ListIterator<LTAWKQual> it = quallist.listIterator(quallist.size());
/* 44 */     LTAWKQual o1 = null;
/*    */     
/* 46 */     while (it.hasPrevious()) {
/* 47 */       o1 = it.previous();
/* 48 */       if (o1.getOperator() == this.operator) {
/*    */         break;
/*    */       }
/*    */     } 
/* 52 */     if (o1.getOperator() != this.operator) {
/* 53 */       return null;
/*    */     }
/* 55 */     it.next();
/*    */     
/* 57 */     List<LTAWKQual> quallist_left = null;
/* 58 */     if (it.nextIndex() > 1) {
/* 59 */       quallist_left = new ArrayList(quallist.subList(0, it.nextIndex() - 1));
/* 60 */       o1 = (LTAWKQual)o1.clone();
/*    */       
/* 62 */       o1.setOperator('.');
/* 63 */       quallist_left.add(o1);
/*    */     } 
/* 65 */     List<?> quallist_right = null;
/* 66 */     if (quallist.size() - it.nextIndex() != 1) {
/* 67 */       quallist_right = quallist.subList(it.nextIndex(), quallist.size());
/*    */     }
/*    */ 
/*    */     
/* 71 */     Solver left = (quallist_left == null) ? new LTNOPSolver(o1, (AWKInterpreter)oI) : oI.Interpret(quallist_left);
/*    */ 
/*    */     
/* 74 */     Solver right = (quallist_right == null) ? new LTNOPSolver(it.next(), (AWKInterpreter)oI) : oI.Interpret(quallist_right);
/*    */ 
/*    */ 
/*    */     
/* 78 */     if (left == null || right == null) {
/* 79 */       return null;
/*    */     }
/*    */     
/* 82 */     return createSolver(left, right, (AWKInterpreter)oI);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTBiFuncRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */