/*    */ package com.jw.swing.layout;
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
/*    */ public class SCLayout
/*    */   extends SGLayout
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SCLayout(int rows) {
/* 26 */     super(rows, 1);
/*    */   }
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
/*    */   public SCLayout(int rows, int gap) {
/* 40 */     super(rows, 1, FILL, FILL, gap, 0);
/*    */   }
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
/*    */   public SCLayout(int rows, int hAlignment, int vAlignment, int gap) {
/* 59 */     super(rows, 1, hAlignment, vAlignment, 0, gap);
/*    */   }
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
/*    */   public void setAlignment(int index, int hAlignment, int vAlignment) {
/* 74 */     setRowAlignment(index, hAlignment, vAlignment);
/*    */   }
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
/*    */   public void setScale(int cell, double scale) {
/* 87 */     setRowScale(cell, scale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\jw\swing\layout\SCLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */