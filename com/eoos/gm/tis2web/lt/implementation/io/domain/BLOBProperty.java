/*     */ package com.eoos.gm.tis2web.lt.implementation.io.domain;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class BLOBProperty
/*     */   implements Comparable, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*  12 */   private static int size = 0;
/*     */   
/*  14 */   private static int nextOrd = 0;
/*     */   
/*  16 */   private static Map nameMap = new HashMap<Object, Object>(5);
/*     */   
/*  18 */   private static BLOBProperty first = null;
/*     */   
/*  20 */   private static BLOBProperty last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private BLOBProperty prev;
/*     */   
/*     */   private BLOBProperty next;
/*     */   
/*  30 */   public static final BLOBProperty BLOB = new BLOBProperty("BLOB", 2);
/*     */   
/*  32 */   public static final BLOBProperty MIMETYPE = new BLOBProperty("MIMETYPE", 4);
/*     */   
/*  34 */   public static final BLOBProperty ID = new BLOBProperty("ID", 8);
/*     */   
/*  36 */   public static final BLOBProperty LANGUAGE = new BLOBProperty("LANGUAGE", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BLOBProperty(String label) {
/*  43 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BLOBProperty(String label, int ord) {
/*  50 */     this.label = label;
/*  51 */     this.ord = ord;
/*     */     
/*  53 */     size++;
/*  54 */     nextOrd = ord + 1;
/*     */     
/*  56 */     nameMap.put(label, this);
/*     */     
/*  58 */     if (first == null) {
/*  59 */       first = this;
/*     */     }
/*  61 */     if (last != null) {
/*  62 */       this.prev = last;
/*  63 */       last.next = this;
/*     */     } 
/*     */     
/*  66 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  74 */     return this.ord - ((BLOBProperty)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  82 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  89 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/*  99 */     BLOBProperty b = get(this.label);
/*     */     
/* 101 */     if (b != null) {
/* 102 */       return b;
/*     */     }
/* 104 */     String msg = "invalid deserialized object:  label = ";
/* 105 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BLOBProperty get(String label) {
/* 113 */     return (BLOBProperty)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BLOBProperty get(int ordinal) {
/* 122 */     Iterator<BLOBProperty> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       BLOBProperty current = it.next();
/* 125 */       if (current.ord() == ordinal) {
/* 126 */         return current;
/*     */       }
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 136 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 146 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 154 */     return new Iterator() {
/* 155 */         private BLOBProperty current = BLOBProperty.first;
/*     */         
/*     */         public boolean hasNext() {
/* 158 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 162 */           BLOBProperty b = this.current;
/* 163 */           this.current = this.current.next();
/* 164 */           return b;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 168 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 177 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 184 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BLOBProperty first() {
/* 191 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BLOBProperty last() {
/* 198 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BLOBProperty prev() {
/* 206 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BLOBProperty next() {
/* 214 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\domain\BLOBProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */