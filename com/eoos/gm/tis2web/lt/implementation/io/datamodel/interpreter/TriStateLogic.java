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
/*    */ 
/*    */ 
/*    */ public class TriStateLogic
/*    */ {
/*    */   public static final int TFALSE = 0;
/*    */   public static final int TTRUE = 1;
/*    */   public static final int TUNKNOWN = -1;
/*    */   private int m_value;
/*    */   
/*    */   public TriStateLogic(TriStateLogic t) {
/* 23 */     this.m_value = t.m_value;
/*    */   }
/*    */   
/*    */   public TriStateLogic() {
/* 27 */     this.m_value = -1;
/*    */   }
/*    */   
/*    */   public TriStateLogic(int value) {
/* 31 */     this.m_value = value;
/*    */   }
/*    */   
/* 34 */   public static final TriStateLogic FALSE = new TriStateLogic(0);
/*    */   
/* 36 */   public static final TriStateLogic TRUE = new TriStateLogic(1);
/*    */   
/* 38 */   public static final TriStateLogic UNKNOWN = new TriStateLogic(-1);
/*    */   
/*    */   public boolean equals(TriStateLogic op1) {
/* 41 */     return (this.m_value == op1.m_value);
/*    */   }
/*    */   
/*    */   public boolean equals(int op1) {
/* 45 */     return (this.m_value == op1);
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 49 */     return this.m_value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TriStateLogic and(TriStateLogic op1, TriStateLogic op2) {
/* 54 */     if (op1.getValue() == -1 && op2.getValue() == -1)
/* 55 */       return UNKNOWN; 
/* 56 */     if ((op1.getValue() == 1 && op2.getValue() == 1) || (op1.getValue() == 1 && op2.getValue() == -1) || (op1.getValue() == -1 && op2.getValue() == 1)) {
/* 57 */       return TRUE;
/*    */     }
/* 59 */     return FALSE;
/*    */   }
/*    */   
/*    */   public static TriStateLogic or(TriStateLogic op1, TriStateLogic op2) {
/* 63 */     if (op1.getValue() == -1 || op2.getValue() == -1) {
/* 64 */       return UNKNOWN;
/*    */     }
/* 66 */     if (op1.getValue() == 1 || op2.getValue() == 1)
/* 67 */       return TRUE; 
/* 68 */     return FALSE;
/*    */   }
/*    */   
/*    */   public static TriStateLogic xor(TriStateLogic op1, TriStateLogic op2) {
/* 72 */     if (op1.getValue() == -1 || op2.getValue() == -1) {
/* 73 */       return UNKNOWN;
/*    */     }
/* 75 */     if (op1.getValue() == 1 && op2.getValue() == 0) {
/* 76 */       return TRUE;
/*    */     }
/* 78 */     if (op1.getValue() == 0 && op2.getValue() == 1)
/* 79 */       return TRUE; 
/* 80 */     return FALSE;
/*    */   }
/*    */   
/*    */   public TriStateLogic not() {
/* 84 */     if (this.m_value == -1) {
/* 85 */       return UNKNOWN;
/*    */     }
/*    */     
/* 88 */     if (this.m_value == 0) {
/* 89 */       return TRUE;
/*    */     }
/*    */     
/* 92 */     return FALSE;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\interpreter\TriStateLogic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */