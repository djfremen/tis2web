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
/*    */ public class PointLayout
/*    */   extends SGLayout
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public PointLayout() {
/* 17 */     super(1, 1);
/*    */   }
/*    */   
/*    */   public PointLayout(int hAlignment, int vAlignment) {
/* 21 */     super(1, 1, hAlignment, vAlignment, 0, 0);
/*    */   }
/*    */   
/*    */   public void setAlignment(int h, int v) {
/* 25 */     setAlignment(h, v, 0, 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\jw\swing\layout\PointLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */