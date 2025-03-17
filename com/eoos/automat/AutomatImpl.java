/*    */ package com.eoos.automat;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutomatImpl
/*    */   implements Automat
/*    */ {
/*    */   private Callback callback;
/* 19 */   private Set currentStates = new HashSet();
/*    */   
/*    */   public AutomatImpl(Callback callback) {
/* 22 */     this.callback = callback;
/* 23 */     reset();
/*    */   }
/*    */   
/*    */   public void process(Object token) {
/* 27 */     Set<State> nextStates = new HashSet();
/* 28 */     for (Iterator<Transition> iterTrans = this.callback.getTransitions().iterator(); iterTrans.hasNext(); ) {
/* 29 */       Transition transition = iterTrans.next();
/*    */       
/* 31 */       for (Iterator<State> iterStates = this.currentStates.iterator(); iterStates.hasNext(); ) {
/* 32 */         State state = iterStates.next();
/* 33 */         State next = transition.transit(state, token);
/* 34 */         if (next != null) {
/* 35 */           nextStates.add(next);
/*    */         }
/*    */       } 
/*    */     } 
/* 39 */     this.currentStates = nextStates;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getCurrentStates() {
/* 44 */     return Collections.unmodifiableSet(this.currentStates);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 48 */     this.currentStates.clear();
/* 49 */     this.currentStates.add(this.callback.getStartState());
/*    */   }
/*    */   
/*    */   public State getStartState() {
/* 53 */     return this.callback.getStartState();
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     Set getTransitions();
/*    */     
/*    */     State getStartState();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\AutomatImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */