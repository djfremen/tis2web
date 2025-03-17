/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class InstallationType
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static InstallationType first = null;
/*     */   
/*  21 */   private static InstallationType last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private InstallationType prev;
/*     */   
/*     */   private InstallationType next;
/*     */   
/*  31 */   public static final InstallationType STANDALONE = new InstallationType("STANDALONE", 1);
/*     */   
/*  33 */   public static final InstallationType SERVER = new InstallationType("SERVER", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InstallationType(String label) {
/*  40 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InstallationType(String label, int ord) {
/*  47 */     this.label = label;
/*  48 */     this.ord = ord;
/*     */     
/*  50 */     size++;
/*  51 */     nextOrd = ord + 1;
/*     */     
/*  53 */     nameMap.put(label, this);
/*     */     
/*  55 */     if (first == null) {
/*  56 */       first = this;
/*     */     }
/*  58 */     if (last != null) {
/*  59 */       this.prev = last;
/*  60 */       last.next = this;
/*     */     } 
/*     */     
/*  63 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  71 */     return this.ord - ((InstallationType)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  79 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  86 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/*  96 */     InstallationType s = get(this.label);
/*     */     
/*  98 */     if (s != null) {
/*  99 */       return s;
/*     */     }
/* 101 */     String msg = "invalid deserialized object:  label = ";
/* 102 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InstallationType get(String label) {
/* 111 */     return (InstallationType)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InstallationType get(int ordinal) {
/* 120 */     Iterator<InstallationType> it = iterator();
/* 121 */     while (it.hasNext()) {
/* 122 */       InstallationType current = it.next();
/* 123 */       if (current.ord() == ordinal) {
/* 124 */         return current;
/*     */       }
/*     */     } 
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 144 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 152 */     return new Iterator() {
/* 153 */         private InstallationType current = InstallationType.first;
/*     */         
/*     */         public boolean hasNext() {
/* 156 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 160 */           InstallationType s = this.current;
/* 161 */           this.current = this.current.next();
/* 162 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 166 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 175 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 182 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InstallationType first() {
/* 189 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InstallationType last() {
/* 196 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstallationType prev() {
/* 204 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstallationType next() {
/* 212 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\InstallationType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */