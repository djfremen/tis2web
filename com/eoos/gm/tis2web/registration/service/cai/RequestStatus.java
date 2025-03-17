/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class RequestStatus
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static RequestStatus first = null;
/*     */   
/*  21 */   private static RequestStatus last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private RequestStatus prev;
/*     */   
/*     */   private RequestStatus next;
/*     */   
/*  31 */   public static final RequestStatus PENDING = new RequestStatus("PENDING", 1);
/*     */   
/*  33 */   public static final RequestStatus AUTHORIZED = new RequestStatus("AUTHORIZED", 2);
/*     */   
/*  35 */   public static final RequestStatus REVOKED = new RequestStatus("REVOKED", 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestStatus(String label) {
/*  42 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestStatus(String label, int ord) {
/*  49 */     this.label = label;
/*  50 */     this.ord = ord;
/*     */     
/*  52 */     size++;
/*  53 */     nextOrd = ord + 1;
/*     */     
/*  55 */     nameMap.put(label, this);
/*     */     
/*  57 */     if (first == null) {
/*  58 */       first = this;
/*     */     }
/*  60 */     if (last != null) {
/*  61 */       this.prev = last;
/*  62 */       last.next = this;
/*     */     } 
/*     */     
/*  65 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  73 */     return this.ord - ((RequestStatus)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  81 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/*  98 */     RequestStatus s = get(this.label);
/*     */     
/* 100 */     if (s != null) {
/* 101 */       return s;
/*     */     }
/* 103 */     String msg = "invalid deserialized object:  label = ";
/* 104 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestStatus get(String label) {
/* 113 */     return (RequestStatus)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestStatus get(int ordinal) {
/* 122 */     Iterator<RequestStatus> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       RequestStatus current = it.next();
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
/* 155 */         private RequestStatus current = RequestStatus.first;
/*     */         
/*     */         public boolean hasNext() {
/* 158 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 162 */           RequestStatus s = this.current;
/* 163 */           this.current = this.current.next();
/* 164 */           return s;
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
/*     */   public static RequestStatus first() {
/* 191 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestStatus last() {
/* 198 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestStatus prev() {
/* 206 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestStatus next() {
/* 214 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RequestStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */