/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class RequestType
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static RequestType first = null;
/*     */   
/*  21 */   private static RequestType last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private RequestType prev;
/*     */   
/*     */   private RequestType next;
/*     */   
/*  31 */   public static final RequestType HWKLOCAL = new RequestType("HWKLOCAL", 1);
/*     */   
/*  33 */   public static final RequestType STANDARD = new RequestType("STANDARD", 2);
/*     */   
/*  35 */   public static final RequestType HWKMIGRATION = new RequestType("HWKMIGRATION", 4);
/*     */   
/*  37 */   public static final RequestType EXTENSION = new RequestType("EXTENSION", 8);
/*     */   
/*  39 */   public static final RequestType REPEAT = new RequestType("REPEAT", 16);
/*     */   
/*  41 */   public static final RequestType TEMPORARY = new RequestType("TEMPORARY", 32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestType(String label) {
/*  48 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestType(String label, int ord) {
/*  55 */     this.label = label;
/*  56 */     this.ord = ord;
/*     */     
/*  58 */     size++;
/*  59 */     nextOrd = ord + 1;
/*     */     
/*  61 */     nameMap.put(label, this);
/*     */     
/*  63 */     if (first == null) {
/*  64 */       first = this;
/*     */     }
/*  66 */     if (last != null) {
/*  67 */       this.prev = last;
/*  68 */       last.next = this;
/*     */     } 
/*     */     
/*  71 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  79 */     return this.ord - ((RequestType)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  87 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  94 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 104 */     RequestType s = get(this.label);
/*     */     
/* 106 */     if (s != null) {
/* 107 */       return s;
/*     */     }
/* 109 */     String msg = "invalid deserialized object:  label = ";
/* 110 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestType get(String label) {
/* 118 */     return (RequestType)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestType get(int ordinal) {
/* 127 */     Iterator<RequestType> it = iterator();
/* 128 */     while (it.hasNext()) {
/* 129 */       RequestType current = it.next();
/* 130 */       if (current.ord() == ordinal) {
/* 131 */         return current;
/*     */       }
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 151 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 159 */     return new Iterator() {
/* 160 */         private RequestType current = RequestType.first;
/*     */         
/*     */         public boolean hasNext() {
/* 163 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 167 */           RequestType s = this.current;
/* 168 */           this.current = this.current.next();
/* 169 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 173 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 182 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 189 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestType first() {
/* 196 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestType last() {
/* 203 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestType prev() {
/* 211 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestType next() {
/* 219 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RequestType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */