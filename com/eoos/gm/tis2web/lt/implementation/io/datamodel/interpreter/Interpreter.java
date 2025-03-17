/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter;
/*    */ 
/*    */ import java.util.LinkedList;
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
/*    */ public class Interpreter
/*    */ {
/* 19 */   protected List rules = new LinkedList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRule(Rule rule) {
/* 27 */     if (this.rules.size() == 0) {
/* 28 */       this.rules.add(rule);
/*    */       return;
/*    */     } 
/* 31 */     ListIterator it = this.rules.listIterator();
/*    */     do {
/*    */     
/* 34 */     } while (it.hasNext() && (
/* 35 */       (Rule)it.next()).Law() > rule.Law());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.rules.add(it.nextIndex(), rule);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Solver Interpret(List what) {
/* 47 */     Solver takensolution = null, solution = null;
/* 48 */     Rule takenlaw = null;
/* 49 */     Rule oCurRule = null;
/* 50 */     boolean bNoNext = false;
/* 51 */     for (ListIterator<Rule> it = this.rules.listIterator(); it.hasNext(); ) {
/* 52 */       if (!bNoNext) {
/*    */         
/* 54 */         oCurRule = it.next();
/*    */       } else {
/* 56 */         bNoNext = false;
/*    */       } 
/*    */       
/* 59 */       if ((solution = oCurRule.canApply(what, this)) != null) {
/* 60 */         if (takensolution == null) {
/* 61 */           takenlaw = oCurRule;
/* 62 */           takensolution = solution;
/*    */           
/* 64 */           Rule oNextRule = null;
/* 65 */           if (it.hasNext()) {
/* 66 */             oNextRule = it.next();
/*    */           }
/*    */ 
/*    */           
/* 70 */           if (oNextRule == null || takenlaw.Law() != oNextRule.Law()) {
/* 71 */             return takensolution;
/*    */           }
/* 73 */           oCurRule = it.previous();
/* 74 */           bNoNext = true;
/*    */           continue;
/*    */         } 
/* 77 */         if (takensolution.appliedPosition() > solution.appliedPosition()) {
/* 78 */           takensolution = solution;
/* 79 */           takenlaw = oCurRule;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 86 */     return takensolution;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\interpreter\Interpreter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */