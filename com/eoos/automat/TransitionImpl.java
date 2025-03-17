/*    */ package com.eoos.automat;
/*    */ 
/*    */ import com.eoos.condition.Condition;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransitionImpl
/*    */   implements Transition
/*    */ {
/*    */   private State from;
/*    */   private State to;
/*    */   private Condition condition;
/*    */   
/*    */   public TransitionImpl(State from, State to, Condition condition) {
/* 15 */     this.from = from;
/* 16 */     this.to = to;
/* 17 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public State transit(State state, Object token) {
/* 25 */     if (this.from.equals(state) && this.condition.check(token)) {
/* 26 */       return this.to;
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\TransitionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */