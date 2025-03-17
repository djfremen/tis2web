/*     */ package com.eoos.gm.tis2web.frame.export.common.locale;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LGSIT
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   private static int size = 0;
/*     */   
/*  18 */   private static int nextOrd = 0;
/*     */   
/*  20 */   private static Map nameMap = new HashMap<Object, Object>(9);
/*     */   
/*  22 */   private static LGSIT first = null;
/*     */   
/*  24 */   private static LGSIT last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private LGSIT prev;
/*     */   
/*     */   private LGSIT next;
/*     */   
/*  34 */   public static final LGSIT DEFAULT = new LGSIT("*", 0);
/*     */   
/*  36 */   public static final LGSIT VC = new LGSIT("VC", 2);
/*     */   
/*  38 */   public static final LGSIT SI = new LGSIT("SI", 4);
/*     */   
/*  40 */   public static final LGSIT CPR = new LGSIT("CPR", 8);
/*     */   
/*  42 */   public static final LGSIT WD = new LGSIT("WD", 16);
/*     */   
/*  44 */   public static final LGSIT LT = new LGSIT("LT", 32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LGSIT(String label) {
/*  51 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LGSIT(String label, int ord) {
/*  58 */     this.label = label;
/*  59 */     this.ord = ord;
/*     */     
/*  61 */     size++;
/*  62 */     nextOrd = ord + 1;
/*     */     
/*  64 */     nameMap.put(label, this);
/*     */     
/*  66 */     if (first == null) {
/*  67 */       first = this;
/*     */     }
/*  69 */     if (last != null) {
/*  70 */       this.prev = last;
/*  71 */       last.next = this;
/*     */     } 
/*     */     
/*  74 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  82 */     return this.ord - ((LGSIT)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  90 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 107 */     LGSIT l = get(this.label);
/*     */     
/* 109 */     if (l != null) {
/* 110 */       return l;
/*     */     }
/* 112 */     String msg = "invalid deserialized object:  label = ";
/* 113 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LGSIT get(String label) {
/* 121 */     return (LGSIT)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 138 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 146 */     return new Iterator() {
/* 147 */         private LGSIT current = LGSIT.first;
/*     */         
/*     */         public boolean hasNext() {
/* 150 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 154 */           LGSIT l = this.current;
/* 155 */           this.current = this.current.next();
/* 156 */           return l;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 160 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 169 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 176 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LGSIT first() {
/* 183 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LGSIT last() {
/* 190 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LGSIT prev() {
/* 198 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LGSIT next() {
/* 206 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\locale\LGSIT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */