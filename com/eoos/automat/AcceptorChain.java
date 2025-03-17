/*    */ package com.eoos.automat;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AcceptorChain
/*    */   implements Acceptor
/*    */ {
/*    */   public static class Type
/*    */   {
/*    */     private Type() {}
/*    */   }
/*    */   
/* 20 */   public static final Type TYPE_AND = new Type();
/*    */   
/* 22 */   public static final Type TYPE_OR = new Type();
/*    */   
/*    */   private List acceptors;
/*    */   
/*    */   private Type type;
/*    */   
/*    */   public AcceptorChain(List acceptors, Type type) {
/* 29 */     this.acceptors = acceptors;
/* 30 */     this.type = type;
/*    */   }
/*    */   
/*    */   public AcceptorChain(Acceptor[] acceptors, Type type) {
/* 34 */     this(Arrays.asList(acceptors), type);
/*    */   }
/*    */   
/*    */   public boolean accept(Object object) {
/*    */     boolean retValue;
/* 39 */     if (this.type == TYPE_AND) {
/* 40 */       retValue = true;
/* 41 */       for (Iterator<Acceptor> iter = this.acceptors.iterator(); iter.hasNext() && retValue;) {
/* 42 */         retValue = (retValue && ((Acceptor)iter.next()).accept(object));
/*    */       }
/*    */     } else {
/* 45 */       retValue = false;
/* 46 */       for (Iterator<Acceptor> iter = this.acceptors.iterator(); iter.hasNext() && !retValue; ) {
/* 47 */         Acceptor acceptor = iter.next();
/* 48 */         retValue = (retValue || acceptor.accept(object));
/*    */       } 
/*    */     } 
/* 51 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*    */     try {
/* 58 */       for (Iterator<Acceptor> iter = this.acceptors.iterator(); iter.hasNext(); ) {
/* 59 */         Acceptor acceptor = iter.next();
/* 60 */         tmp.append(acceptor);
/* 61 */         if (iter.hasNext()) {
/* 62 */           tmp.append((this.type == TYPE_AND) ? " AND " : " OR ");
/*    */         }
/*    */       } 
/* 65 */       return tmp.toString();
/*    */     } finally {
/*    */       
/* 68 */       StringBufferPool.getThreadInstance().free(tmp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 73 */     Acceptor acceptor = StringAcceptor.create("*.sav", true);
/* 74 */     Acceptor acceptor2 = StringAcceptor.create("*/settings/*", true);
/* 75 */     acceptor = new AcceptorChain(Arrays.asList(new Acceptor[] { acceptor, acceptor2 }, ), TYPE_AND);
/* 76 */     System.out.println(acceptor.accept("d:\\log\\tis2web\\Copy (12) of user.sav"));
/* 77 */     System.out.println(acceptor.accept("d:/settings/myconf.sav"));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\AcceptorChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */