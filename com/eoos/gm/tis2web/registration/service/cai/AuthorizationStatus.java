/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class AuthorizationStatus
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  17 */   private static AuthorizationStatus first = null;
/*     */   
/*  19 */   private static AuthorizationStatus last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private AuthorizationStatus prev;
/*     */   
/*     */   private AuthorizationStatus next;
/*     */   
/*  29 */   public static final AuthorizationStatus TEMPORARY = new AuthorizationStatus("TEMPORARY", 1);
/*     */   
/*  31 */   public static final AuthorizationStatus PENDING = new AuthorizationStatus("PENDING", 2);
/*     */   
/*  33 */   public static final AuthorizationStatus MIGRATED = new AuthorizationStatus("MIGRATED", 4);
/*     */   
/*  35 */   public static final AuthorizationStatus AUTHORIZED = new AuthorizationStatus("AUTHORIZED", 8);
/*     */   
/*  37 */   public static final AuthorizationStatus REVOKED = new AuthorizationStatus("REVOKED", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthorizationStatus(String label, int ord) {
/*  43 */     this.label = label;
/*  44 */     this.ord = ord;
/*     */     
/*  46 */     size++;
/*     */     
/*  48 */     nameMap.put(label, this);
/*     */     
/*  50 */     if (first == null) {
/*  51 */       first = this;
/*     */     }
/*  53 */     if (last != null) {
/*  54 */       this.prev = last;
/*  55 */       last.next = this;
/*     */     } 
/*     */     
/*  58 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  66 */     return this.ord - ((AuthorizationStatus)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  74 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  81 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/*  91 */     AuthorizationStatus s = get(this.label);
/*     */     
/*  93 */     if (s != null) {
/*  94 */       return s;
/*     */     }
/*  96 */     String msg = "invalid deserialized object:  label = ";
/*  97 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationStatus get(String label) {
/* 106 */     return (AuthorizationStatus)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationStatus get(int ordinal) {
/* 115 */     Iterator<AuthorizationStatus> it = iterator();
/* 116 */     while (it.hasNext()) {
/* 117 */       AuthorizationStatus current = it.next();
/* 118 */       if (current.ord() == ordinal) {
/* 119 */         return current;
/*     */       }
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 139 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 148 */     return new Iterator() {
/* 149 */         private AuthorizationStatus current = AuthorizationStatus.first;
/*     */         
/*     */         public boolean hasNext() {
/* 152 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 156 */           AuthorizationStatus s = this.current;
/* 157 */           this.current = this.current.next();
/* 158 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 162 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 171 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 178 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationStatus first() {
/* 185 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationStatus last() {
/* 192 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationStatus prev() {
/* 200 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationStatus next() {
/* 208 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\AuthorizationStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */