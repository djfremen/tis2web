/*    */ package com.eoos.automat;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateImpl
/*    */   implements State
/*    */ {
/*  8 */   protected State superstate = null;
/*    */ 
/*    */   
/*    */   public StateImpl() {}
/*    */   
/*    */   public StateImpl(State superstate) {
/* 14 */     this.superstate = superstate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean superstateOf(State state) {
/* 22 */     return (!equals(state) && state.substateOf(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean substateOf(State state) {
/* 30 */     boolean retValue = false;
/* 31 */     if (this.superstate == null) {
/* 32 */       retValue = false;
/*    */     } else {
/* 34 */       retValue = (this.superstate.equals(state) || this.superstate.substateOf(state));
/*    */     } 
/* 36 */     return retValue;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 40 */     State[] states = new State[9];
/* 41 */     states[0] = new StateImpl();
/* 42 */     states[1] = new StateImpl();
/* 43 */     states[2] = new StateImpl(states[1]);
/* 44 */     states[3] = new StateImpl(states[2]);
/* 45 */     states[4] = new StateImpl(states[3]);
/* 46 */     states[5] = new StateImpl();
/* 47 */     states[6] = new StateImpl(states[5]);
/* 48 */     states[7] = new StateImpl(states[1]);
/* 49 */     states[8] = new StateImpl(states[2]);
/*    */     
/* 51 */     for (int i = 0; i < 9; i++) {
/* 52 */       for (int j = 0; j < 9; j++) {
/*    */         
/* 54 */         System.out.println("\nstate " + i + " equals state " + j + " : " + states[i].equals(states[j]));
/* 55 */         System.out.println("state " + i + " is substate of state " + j + " : " + states[i].substateOf(states[j]));
/* 56 */         System.out.println("state " + i + " is superstate of state " + j + " : " + states[i].superstateOf(states[j]));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\StateImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */