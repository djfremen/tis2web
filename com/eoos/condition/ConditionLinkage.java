/*    */ package com.eoos.condition;
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
/*    */ public class ConditionLinkage
/*    */   implements Condition
/*    */ {
/*    */   public static final int OR = 1;
/*    */   public static final int AND = 2;
/*    */   public static final int XOR = 3;
/*    */   protected Condition condition1;
/*    */   protected Condition condition2;
/*    */   protected int nLinkage;
/*    */   
/*    */   public ConditionLinkage(Condition condition1, Condition condition2, int nLinkage) {
/* 34 */     if (condition1 == null || condition2 == null) {
/* 35 */       throw new RuntimeException();
/*    */     }
/*    */     
/* 38 */     this.condition1 = condition1;
/* 39 */     this.condition2 = condition2;
/*    */     
/* 41 */     this.nLinkage = nLinkage;
/*    */   }
/*    */   
/*    */   public boolean check(Object obj) {
/* 45 */     boolean bResult = false;
/*    */     
/* 47 */     switch (this.nLinkage) {
/*    */       case 1:
/* 49 */         bResult = (this.condition1.check(obj) || this.condition2.check(obj));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 70 */         return bResult;case 2: bResult = (this.condition1.check(obj) && this.condition2.check(obj)); return bResult;case 3: bResult = ((this.condition1.check(obj) && !this.condition2.check(obj)) || (this.condition2.check(obj) && !this.condition1.check(obj))); return bResult;
/*    */     } 
/*    */     throw new RuntimeException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\condition\ConditionLinkage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */