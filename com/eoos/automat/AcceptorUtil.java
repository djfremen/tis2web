/*    */ package com.eoos.automat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AcceptorUtil
/*    */ {
/*    */   public static Acceptor invertAcceptor(final Acceptor acceptor) {
/*  9 */     return new Acceptor()
/*    */       {
/*    */         public boolean accept(Object object) {
/* 12 */           return !acceptor.accept(object);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\AcceptorUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */