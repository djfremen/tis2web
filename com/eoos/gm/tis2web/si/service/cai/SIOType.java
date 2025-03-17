/*     */ package com.eoos.gm.tis2web.si.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class SIOType
/*     */   implements SITOCType, Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  14 */   private static int size = 0;
/*     */   
/*  16 */   private static int nextOrd = 0;
/*     */   
/*  18 */   private static Map nameMap = new HashMap<Object, Object>(7);
/*     */   
/*  20 */   private static SIOType first = null;
/*     */   
/*  22 */   private static SIOType last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private SIOType prev;
/*     */   
/*     */   private SIOType next;
/*     */   
/*  32 */   public static final SIOType SI = new SIOType("SI", 8);
/*     */   
/*  34 */   public static final SIOType WIS = new SIOType("WIS", 9);
/*     */   
/*  36 */   public static final SIOType CPR = new SIOType("CPR", 16);
/*     */   
/*  38 */   public static final SIOType WD = new SIOType("WD", 32);
/*     */   
/*  40 */   public static final SIOType MajorOperation = new SIOType("MO", 64);
/*     */   
/*  42 */   public static final SIOType HELP = new SIOType("HELP", 128);
/*     */   
/*  44 */   public static final SIOType NEWS = new SIOType("NEWS", 256);
/*     */   
/*  46 */   public static final SIOType Graphic = new SIOType("G", 512);
/*     */   
/*  48 */   public static final SIOType VERSION = new SIOType("VERSION", 1024);
/*     */   
/*  50 */   public static final SIOType DDBINFO = new SIOType("DDB-SUBGROUP", 999);
/*     */ 
/*     */   
/*  53 */   public static final SIOType WIS_DTC = new SIOType("WIS-DTC", 11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOType(String label) {
/*  60 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOType(String label, int ord) {
/*  67 */     this.label = label;
/*  68 */     this.ord = ord;
/*     */     
/*  70 */     size++;
/*  71 */     nextOrd = ord + 1;
/*     */     
/*  73 */     nameMap.put(label, this);
/*     */     
/*  75 */     if (first == null) {
/*  76 */       first = this;
/*     */     }
/*  78 */     if (last != null) {
/*  79 */       this.prev = last;
/*  80 */       last.next = this;
/*     */     } 
/*     */     
/*  83 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/*  91 */     return this.ord - ((SIOType)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  99 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 116 */     SIOType s = get(this.label);
/*     */     
/* 118 */     if (s != null) {
/* 119 */       return s;
/*     */     }
/* 121 */     String msg = "invalid deserialized object:  label = ";
/* 122 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOType get(String label) {
/* 130 */     return (SIOType)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOType get(int ordinal) {
/* 138 */     Iterator<SIOType> it = iterator();
/* 139 */     while (it.hasNext()) {
/* 140 */       SIOType current = it.next();
/* 141 */       if (current.ord() == ordinal) {
/* 142 */         return current;
/*     */       }
/*     */     } 
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 162 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 170 */     return new Iterator() {
/* 171 */         private SIOType current = SIOType.first;
/*     */         
/*     */         public boolean hasNext() {
/* 174 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 178 */           SIOType s = this.current;
/* 179 */           this.current = this.current.next();
/* 180 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 184 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 193 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 200 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOType first() {
/* 207 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOType last() {
/* 214 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOType prev() {
/* 222 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOType next() {
/* 230 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */