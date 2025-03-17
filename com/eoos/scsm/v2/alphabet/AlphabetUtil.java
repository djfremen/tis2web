/*    */ package com.eoos.scsm.v2.alphabet;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.SetPool;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlphabetUtil
/*    */ {
/* 16 */   public static final AbstractAlphabet EMTPY_ALPHABET = new AbstractAlphabet()
/*    */     {
/*    */       public Comparator getComparator() {
/* 19 */         return null;
/*    */       }
/*    */       
/*    */       public Set getElements() {
/* 23 */         return Collections.EMPTY_SET;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public static Alphabet create(final Set elements, final Comparator c) {
/* 29 */     return new AbstractAlphabet()
/*    */       {
/*    */         public Comparator getComparator() {
/* 32 */           return c;
/*    */         }
/*    */         
/*    */         public Set getElements() {
/* 36 */           return elements;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static Alphabet create(CharSequence string) {
/* 43 */     Set<Character> elements = new HashSet();
/* 44 */     for (int i = 0; i < string.length(); i++) {
/* 45 */       elements.add(new Character(string.charAt(i)));
/*    */     }
/* 47 */     return create(elements, Util.NATURAL_COMPARATOR);
/*    */   }
/*    */   
/*    */   public static int diffCount(Alphabet a1, Alphabet a2) {
/* 51 */     Set set = SetPool.getThreadInstance().get();
/*    */     try {
/* 53 */       set.addAll(a1.getElements());
/* 54 */       set.removeAll(a2.getElements());
/* 55 */       int ret = set.size();
/* 56 */       set.addAll(a2.getElements());
/* 57 */       set.removeAll(a1.getElements());
/* 58 */       ret += set.size();
/* 59 */       return ret;
/*    */     } finally {
/*    */       
/* 62 */       SetPool.getThreadInstance().free(set);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int intersectionCount(Alphabet a1, Alphabet a2) {
/* 67 */     Set set = SetPool.getThreadInstance().get();
/*    */     try {
/* 69 */       set.addAll(a1.getElements());
/* 70 */       set.retainAll(a2.getElements());
/* 71 */       return set.size();
/*    */     } finally {
/*    */       
/* 74 */       SetPool.getThreadInstance().free(set);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\alphabet\AlphabetUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */