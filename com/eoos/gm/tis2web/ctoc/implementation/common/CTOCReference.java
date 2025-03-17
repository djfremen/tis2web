/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CTOCReference
/*     */   implements SITOCProperty, Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   private static int size = 0;
/*     */   
/*  19 */   private static int nextOrd = 0;
/*     */   
/*  21 */   private static Map nameMap = new HashMap<Object, Object>(9);
/*     */   
/*  23 */   private static CTOCReference first = null;
/*     */   
/*  25 */   private static CTOCReference last = null;
/*     */ 
/*     */   
/*     */   private final int ord;
/*     */ 
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private CTOCReference prev;
/*     */   
/*     */   private CTOCReference next;
/*     */   
/*  37 */   public static final CTOCReference CTOC_ES = new CTOCReference("SICTOC_ES", 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCReference(String label) {
/*  44 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCReference(String label, int ord) {
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
/*  75 */     return this.ord - ((CTOCReference)obj).ord;
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
/* 100 */     CTOCReference s = get(this.label);
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
/*     */   
/*     */   public static CTOCReference get(String label) {
/* 115 */     return (CTOCReference)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCReference get(int ordinal) {
/* 124 */     Iterator<CTOCReference> it = iterator();
/* 125 */     while (it.hasNext()) {
/* 126 */       CTOCReference current = it.next();
/* 127 */       if (current.ord() == ordinal) {
/* 128 */         return current;
/*     */       }
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 138 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 148 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 156 */     return new Iterator() {
/* 157 */         private CTOCReference current = CTOCReference.first;
/*     */         
/*     */         public boolean hasNext() {
/* 160 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 164 */           CTOCReference s = this.current;
/* 165 */           this.current = this.current.next();
/* 166 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 170 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 179 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 186 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCReference first() {
/* 193 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCReference last() {
/* 200 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCReference prev() {
/* 208 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCReference next() {
/* 216 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\CTOCReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */