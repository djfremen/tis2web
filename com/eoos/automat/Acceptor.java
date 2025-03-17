/*    */ package com.eoos.automat;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Acceptor
/*    */ {
/*  7 */   public static final Acceptor ACCEPT_ALL = new Acceptor()
/*    */     {
/*    */       public boolean accept(Object object) {
/* 10 */         return true;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 15 */   public static final Acceptor ACCEPT_NONE = new Acceptor()
/*    */     {
/*    */       public boolean accept(Object object) {
/* 18 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean accept(Object paramObject);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\Acceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */