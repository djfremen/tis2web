/*     */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class CTOCType
/*     */   implements SITOCType, Comparable, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*  12 */   private static int size = 0;
/*     */   
/*  14 */   private static int nextOrd = 0;
/*     */   
/*  16 */   private static Map nameMap = new HashMap<Object, Object>(9);
/*     */   
/*  18 */   private static CTOCType first = null;
/*     */   
/*  20 */   private static CTOCType last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private CTOCType prev;
/*     */   
/*     */   private CTOCType next;
/*     */   
/*  30 */   public static final CTOCType CTOC = new CTOCType("CTOCImpl", 2);
/*     */   
/*  32 */   public static final CTOCType STRUCTURE = new CTOCType("NODE", 4);
/*     */   
/*  34 */   public static final CTOCType SI = new CTOCType("SI", 8);
/*     */   
/*  36 */   public static final CTOCType WIS = new CTOCType("WIS", 9);
/*  37 */   public static final CTOCType WIS_ECM = new CTOCType("WIS-ECM", 10);
/*  38 */   public static final CTOCType WIS_DTC = new CTOCType("WIS-DTC", 11);
/*  39 */   public static final CTOCType WIS_COMPONENT = new CTOCType("WIS-CMPNT", 12);
/*     */   
/*  41 */   public static final CTOCType CPR = new CTOCType("CPR", 16);
/*     */   
/*  43 */   public static final CTOCType WD = new CTOCType("WD", 32);
/*     */   
/*  45 */   public static final CTOCType MajorOperation = new CTOCType("MO", 64);
/*     */   
/*  47 */   public static final CTOCType HELP = new CTOCType("HELP", 128);
/*     */   
/*  49 */   public static final CTOCType NEWS = new CTOCType("NEWS", 256);
/*     */   
/*  51 */   public static final CTOCType VERSION = new CTOCType("VERSION", 1024);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCType(String label) {
/*  58 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCType(String label, int ord) {
/*  65 */     this.label = label;
/*  66 */     this.ord = ord;
/*     */     
/*  68 */     size++;
/*  69 */     nextOrd = ord + 1;
/*     */     
/*  71 */     nameMap.put(label, this);
/*     */     
/*  73 */     if (first == null) {
/*  74 */       first = this;
/*     */     }
/*  76 */     if (last != null) {
/*  77 */       this.prev = last;
/*  78 */       last.next = this;
/*     */     } 
/*     */     
/*  81 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  89 */     return this.ord - ((CTOCType)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  97 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 114 */     CTOCType c = get(this.label);
/*     */     
/* 116 */     if (c != null) {
/* 117 */       return c;
/*     */     }
/* 119 */     String msg = "invalid deserialized object:  label = ";
/* 120 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCType get(String label) {
/* 128 */     return (CTOCType)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCType get(int ordinal) {
/* 136 */     Iterator<CTOCType> it = iterator();
/* 137 */     while (it.hasNext()) {
/* 138 */       CTOCType current = it.next();
/* 139 */       if (current.ord() == ordinal) {
/* 140 */         return current;
/*     */       }
/*     */     } 
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 160 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 168 */     return new Iterator() {
/* 169 */         private CTOCType current = CTOCType.first;
/*     */         
/*     */         public boolean hasNext() {
/* 172 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 176 */           CTOCType c = this.current;
/* 177 */           this.current = this.current.next();
/* 178 */           return c;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 182 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 191 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 198 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCType first() {
/* 205 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCType last() {
/* 212 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCType prev() {
/* 220 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCType next() {
/* 228 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */