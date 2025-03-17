/*     */ package com.eoos.gm.tis2web.si.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class SIOReference
/*     */   implements SITOCProperty, Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  14 */   private static int size = 0;
/*     */   
/*  16 */   private static int nextOrd = 0;
/*     */   
/*  18 */   private static Map nameMap = new HashMap<Object, Object>(9);
/*     */   
/*  20 */   private static SIOReference first = null;
/*     */   
/*  22 */   private static SIOReference last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private SIOReference prev;
/*     */   
/*     */   private SIOReference next;
/*     */   
/*  32 */   public static final SIOReference CPR = new SIOReference("CPR", 1);
/*     */   
/*  34 */   public static final SIOReference WD = new SIOReference("WD", 2);
/*     */   
/*  36 */   public static final SIOReference CPRChapter = new SIOReference("CPRChapter", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOReference(String label) {
/*  43 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOReference(String label, int ord) {
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
/*  74 */     return this.ord - ((SIOReference)obj).ord;
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
/*  99 */     SIOReference s = get(this.label);
/*     */     
/* 101 */     if (s != null) {
/* 102 */       return s;
/*     */     }
/* 104 */     String msg = "invalid deserialized object:  label = ";
/* 105 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOReference get(String label) {
/* 113 */     return (SIOReference)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOReference get(int ordinal) {
/* 122 */     Iterator<SIOReference> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       SIOReference current = it.next();
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
/* 155 */         private SIOReference current = SIOReference.first;
/*     */         
/*     */         public boolean hasNext() {
/* 158 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 162 */           SIOReference s = this.current;
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
/*     */   public static SIOReference first() {
/* 191 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOReference last() {
/* 198 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOReference prev() {
/* 206 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOReference next() {
/* 214 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */