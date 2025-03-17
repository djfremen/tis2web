/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class RegistrationFlag
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static RegistrationFlag first = null;
/*     */   
/*  21 */   private static RegistrationFlag last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private RegistrationFlag prev;
/*     */   
/*     */   private RegistrationFlag next;
/*     */   
/*  31 */   public static final RegistrationFlag REVOKED = new RegistrationFlag("REVOKED", 0);
/*     */   
/*  33 */   public static final RegistrationFlag MIGRATION = new RegistrationFlag("MIGRATION", 1);
/*     */   
/*  35 */   public static final RegistrationFlag AUTHORIZED = new RegistrationFlag("AUTHORIZED", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RegistrationFlag(String label) {
/*  42 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RegistrationFlag(String label, int ord) {
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
/*  73 */     return this.ord - ((RegistrationFlag)obj).ord;
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
/*  98 */     RegistrationFlag s = get(this.label);
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
/*     */   public static RegistrationFlag get(String label) {
/* 113 */     return (RegistrationFlag)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationFlag get(int ordinal) {
/* 122 */     Iterator<RegistrationFlag> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       RegistrationFlag current = it.next();
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
/* 155 */         private RegistrationFlag current = RegistrationFlag.first;
/*     */         
/*     */         public boolean hasNext() {
/* 158 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 162 */           RegistrationFlag s = this.current;
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
/*     */   public static RegistrationFlag first() {
/* 191 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationFlag last() {
/* 198 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistrationFlag prev() {
/* 206 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistrationFlag next() {
/* 214 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RegistrationFlag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */