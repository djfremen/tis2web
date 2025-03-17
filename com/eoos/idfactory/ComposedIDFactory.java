/*    */ package com.eoos.idfactory;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.HashCalc;
/*    */ import java.io.Serializable;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComposedIDFactory
/*    */   implements IDFactory, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private IDFactory[] factories;
/*    */   private String separator;
/*    */   
/*    */   public static class ComposedIdentifier
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private Object[] identifiers;
/*    */     private Object separator;
/* 23 */     private transient Integer hash = null;
/*    */     
/*    */     public ComposedIdentifier(Object[] identifiers, Object separator) {
/* 26 */       this.identifiers = identifiers;
/* 27 */       this.separator = separator;
/*    */     }
/*    */     
/*    */     public int hashCode() {
/* 31 */       if (this.hash != null) {
/* 32 */         int value = 41;
/* 33 */         for (int i = 0; i < this.identifiers.length; i++) {
/* 34 */           value = HashCalc.addHashCode(value, this.identifiers[i]);
/*    */         }
/* 36 */         this.hash = Integer.valueOf(value);
/*    */       } 
/* 38 */       return this.hash.intValue();
/*    */     }
/*    */     
/*    */     public boolean equals(Object obj) {
/* 42 */       boolean retValue = false;
/* 43 */       if (this == obj) {
/* 44 */         retValue = true;
/* 45 */       } else if (obj instanceof ComposedIdentifier) {
/* 46 */         ComposedIdentifier ci = (ComposedIdentifier)obj;
/* 47 */         retValue = Arrays.equals(this.identifiers, ci.identifiers);
/*    */       } 
/* 49 */       return retValue;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuffer retvalue = StringBufferPool.getThreadInstance().get();
/*    */       try {
/* 55 */         for (int i = 0; i < this.identifiers.length - 1; i++) {
/* 56 */           retvalue.append(this.identifiers[i]);
/* 57 */           retvalue.append(this.separator);
/*    */         } 
/* 59 */         retvalue.append(this.identifiers[this.identifiers.length - 1]);
/* 60 */         return retvalue.toString();
/*    */       } finally {
/*    */         
/* 63 */         StringBufferPool.getThreadInstance().free(retvalue);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ComposedIDFactory(IDFactory[] idfactories, String sepatator) {
/* 74 */     this.factories = idfactories;
/* 75 */     this.separator = sepatator;
/*    */   }
/*    */   
/*    */   public ComposedIDFactory(IDFactory[] idfactories) {
/* 79 */     this(idfactories, "-");
/*    */   }
/*    */   
/*    */   public Object createID() {
/* 83 */     Object[] ids = new Object[this.factories.length];
/* 84 */     for (int i = 0; i < this.factories.length; i++) {
/* 85 */       ids[i] = this.factories[i].createID();
/*    */     }
/* 87 */     return new ComposedIdentifier(ids, this.separator);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\idfactory\ComposedIDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */