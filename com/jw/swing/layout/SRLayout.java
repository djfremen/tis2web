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
/*    */ public class SRLayout
/*    */   extends SGLayout
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SRLayout(int columns) {
/* 26 */     super(1, columns);
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
/*    */   public SRLayout(int columns, int gap) {
/* 41 */     super(1, columns, FILL, FILL, gap, 0);
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
/*    */   public SRLayout(int columns, int hAlignment, int vAlignment, int gap) {
/* 57 */     super(1, columns, hAlignment, vAlignment, gap, 0);
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
/*    */   public void setAlignment(int column, int hAlignment, int vAlignment) {
/* 72 */     setColumnAlignment(column, hAlignment, vAlignment);
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
/*    */   public void setScale(int column, double scale) {
/* 85 */     setColumnScale(column, scale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\jw\swing\layout\SRLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */