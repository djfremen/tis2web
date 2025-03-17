/*     */ package com.eoos.gm.tis2web.registration.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class RegistrationAttribute
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private static int size = 0;
/*     */   
/*  15 */   private static int nextOrd = 0;
/*     */   
/*  17 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  19 */   private static RegistrationAttribute first = null;
/*     */   
/*  21 */   private static RegistrationAttribute last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private RegistrationAttribute prev;
/*     */   
/*     */   private RegistrationAttribute next;
/*     */   
/*  31 */   public static final RegistrationAttribute REQUEST_ID = new RegistrationAttribute("REQUEST-ID", 1);
/*     */   
/*  33 */   public static final RegistrationAttribute DEALERSHIP_ID = new RegistrationAttribute("DEALERSHIP-ID", 2);
/*     */   
/*  35 */   public static final RegistrationAttribute DEALERSHIP_NAME = new RegistrationAttribute("DEALERSHIP-NAME", 4);
/*     */   
/*  37 */   public static final RegistrationAttribute REQUEST_DATE = new RegistrationAttribute("REQUEST-DATE", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RegistrationAttribute(String label) {
/*  44 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RegistrationAttribute(String label, int ord) {
/*  51 */     this.label = label;
/*  52 */     this.ord = ord;
/*     */     
/*  54 */     size++;
/*  55 */     nextOrd = ord + 1;
/*     */     
/*  57 */     nameMap.put(label, this);
/*     */     
/*  59 */     if (first == null) {
/*  60 */       first = this;
/*     */     }
/*  62 */     if (last != null) {
/*  63 */       this.prev = last;
/*  64 */       last.next = this;
/*     */     } 
/*     */     
/*  67 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  75 */     return this.ord - ((RegistrationAttribute)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  83 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 100 */     RegistrationAttribute s = get(this.label);
/*     */     
/* 102 */     if (s != null) {
/* 103 */       return s;
/*     */     }
/* 105 */     String msg = "invalid deserialized object:  label = ";
/* 106 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationAttribute get(String label) {
/* 114 */     return (RegistrationAttribute)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationAttribute get(int ordinal) {
/* 123 */     Iterator<RegistrationAttribute> it = iterator();
/* 124 */     while (it.hasNext()) {
/* 125 */       RegistrationAttribute current = it.next();
/* 126 */       if (current.ord() == ordinal) {
/* 127 */         return current;
/*     */       }
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 147 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 155 */     return new Iterator() {
/* 156 */         private RegistrationAttribute current = RegistrationAttribute.first;
/*     */         
/*     */         public boolean hasNext() {
/* 159 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 163 */           RegistrationAttribute s = this.current;
/* 164 */           this.current = this.current.next();
/* 165 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 169 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 178 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 185 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationAttribute first() {
/* 192 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistrationAttribute last() {
/* 199 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistrationAttribute prev() {
/* 207 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistrationAttribute next() {
/* 215 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RegistrationAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */