/*    */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*    */ 
/*    */ public interface ButtonHandle
/*    */ {
/*    */   public static class Type {
/*    */     private Type() {}
/*    */   }
/*    */   
/*  9 */   public static final Type BACK = new Type();
/*    */   
/* 11 */   public static final Type NEXT = new Type();
/*    */   
/* 13 */   public static final Type CANCEL = new Type();
/*    */   
/*    */   void setEnabled(Type paramType, boolean paramBoolean);
/*    */   
/*    */   void setLabel(Type paramType, String paramString);
/*    */   
/*    */   void setVisible(Type paramType, boolean paramBoolean);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\ButtonHandle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */