/*    */ package com.eoos.scsm.v2.alphabet;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class AbstractAlphabet
/*    */   implements Alphabet
/*    */ {
/* 13 */   private final Object SYNC = new Object();
/*    */   
/* 15 */   private SoftReference srOrdered = null;
/*    */   
/*    */   public List getElementList() {
/* 18 */     synchronized (this.SYNC) {
/* 19 */       List<?> ret = null;
/* 20 */       if (this.srOrdered == null || (ret = this.srOrdered.get()) == null) {
/* 21 */         ret = new ArrayList(getElements());
/* 22 */         Collections.sort(ret, getComparator());
/* 23 */         this.srOrdered = new SoftReference<List<?>>(ret);
/*    */       } 
/* 25 */       return ret;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int hasCode() {
/* 30 */     int ret = AbstractAlphabet.class.hashCode();
/* 31 */     ret = HashCalc.addHashCode(ret, getElementList());
/* 32 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 36 */     if (this == obj)
/* 37 */       return true; 
/* 38 */     if (obj instanceof AbstractAlphabet) {
/* 39 */       AbstractAlphabet other = (AbstractAlphabet)obj;
/* 40 */       boolean ret = Util.equals(getElementList(), other.getElementList());
/* 41 */       return ret;
/*    */     } 
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return String.valueOf(getElementList());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\alphabet\AbstractAlphabet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */