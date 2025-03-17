/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class SalesOrganization
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static SalesOrganization first = null;
/*     */   
/*  21 */   private static SalesOrganization last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private SalesOrganization prev;
/*     */   
/*     */   private SalesOrganization next;
/*     */   
/*  31 */   public static final SalesOrganization GME = new SalesOrganization("GME", 1);
/*     */   
/*  33 */   public static final SalesOrganization NAO = new SalesOrganization("NAO", 2);
/*     */   
/*  35 */   public static final SalesOrganization GM = new SalesOrganization("GM", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SalesOrganization(String label) {
/*  42 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SalesOrganization(String label, int ord) {
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
/*  73 */     return this.ord - ((SalesOrganization)obj).ord;
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
/*  98 */     SalesOrganization s = get(this.label);
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
/*     */   public static SalesOrganization get(String label) {
/* 113 */     return (SalesOrganization)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SalesOrganization get(int ordinal) {
/* 122 */     Iterator<SalesOrganization> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       SalesOrganization current = it.next();
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
/* 155 */         private SalesOrganization current = SalesOrganization.first;
/*     */         
/*     */         public boolean hasNext() {
/* 158 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 162 */           SalesOrganization s = this.current;
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
/*     */   public static SalesOrganization first() {
/* 191 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SalesOrganization last() {
/* 198 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SalesOrganization prev() {
/* 206 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SalesOrganization next() {
/* 214 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\SalesOrganization.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */