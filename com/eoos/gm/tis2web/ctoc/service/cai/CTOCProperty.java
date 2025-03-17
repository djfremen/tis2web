/*     */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class CTOCProperty
/*     */   implements SITOCProperty, Comparable, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*  12 */   private static int size = 0;
/*     */   
/*  14 */   private static int nextOrd = 0;
/*     */   
/*  16 */   private static Map nameMap = new HashMap<Object, Object>(10);
/*     */   
/*  18 */   private static CTOCProperty first = null;
/*     */   
/*  20 */   private static CTOCProperty last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private CTOCProperty prev;
/*     */   
/*     */   private CTOCProperty next;
/*     */   
/*  30 */   public static final CTOCProperty AssemblyGroup = new CTOCProperty("AG", 2);
/*     */   
/*  32 */   public static final CTOCProperty SIT = new CTOCProperty("SIT", 4);
/*     */   
/*  34 */   public static final CTOCProperty SITQ = new CTOCProperty("SITQ", 8);
/*     */   
/*  36 */   public static final CTOCProperty LU = new CTOCProperty("LU", 16);
/*     */   
/*  38 */   public static final CTOCProperty CPR = new CTOCProperty("CPR", 32);
/*     */   
/*  40 */   public static final CTOCProperty WD = new CTOCProperty("WD", 64);
/*     */   
/*  42 */   public static final CTOCProperty LT = new CTOCProperty("LT", 128);
/*     */   
/*  44 */   public static final CTOCProperty MajorOperation = new CTOCProperty("MO", 256);
/*     */   
/*  46 */   public static final CTOCProperty PaintingStage = new CTOCProperty("PS", 512);
/*     */   
/*  48 */   public static final CTOCProperty ChangeFlag = new CTOCProperty("CF", 1024);
/*     */   
/*  50 */   public static final CTOCProperty ElectronicSystem = new CTOCProperty("ES", 2048);
/*     */   
/*  52 */   public static final CTOCProperty SCDS = new CTOCProperty("SCDS", 2097152);
/*     */ 
/*     */   
/*  55 */   public static final CTOCProperty ID = new CTOCProperty("Node", 4096);
/*     */   
/*  57 */   public static final CTOCProperty Page = new CTOCProperty("PAGE", 67108864);
/*     */ 
/*     */   
/*  60 */   public static final CTOCProperty SCDS2GT_GROUP = new CTOCProperty("SCDS2GT_GROUP", 19);
/*     */   
/*  62 */   public static final CTOCProperty VERSION = new CTOCProperty("VERSION", 268435456);
/*     */   
/*  64 */   public static final CTOCProperty PublicationID = new CTOCProperty("PUBID", 524288);
/*     */   
/*  66 */   public static final CTOCProperty LINKS = new CTOCProperty("LINKS", 4194304);
/*     */   
/*  68 */   public static final CTOCProperty ECM_LIST = new CTOCProperty("ECM-LIST", 999);
/*  69 */   public static final CTOCProperty ECM_LABEL = new CTOCProperty("ECM-LABEL", 998);
/*  70 */   public static final CTOCProperty DTC_LIST = new CTOCProperty("DTC-LIST", 997);
/*  71 */   public static final CTOCProperty COMPONENT_LIST = new CTOCProperty("COMPONENT-LIST", 996);
/*  72 */   public static final CTOCProperty COMPONENT_ID = new CTOCProperty("COMPONENT-ID", 995);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCProperty(String label) {
/*  79 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CTOCProperty(String label, int ord) {
/*  86 */     this.label = label;
/*  87 */     this.ord = ord;
/*     */     
/*  89 */     size++;
/*  90 */     nextOrd = ord + 1;
/*     */     
/*  92 */     nameMap.put(label, this);
/*     */     
/*  94 */     if (first == null) {
/*  95 */       first = this;
/*     */     }
/*  97 */     if (last != null) {
/*  98 */       this.prev = last;
/*  99 */       last.next = this;
/*     */     } 
/*     */     
/* 102 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/* 110 */     return this.ord - ((CTOCProperty)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 118 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 135 */     CTOCProperty c = get(this.label);
/*     */     
/* 137 */     if (c != null) {
/* 138 */       return c;
/*     */     }
/* 140 */     String msg = "invalid deserialized object:  label = ";
/* 141 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCProperty get(String label) {
/* 149 */     return (CTOCProperty)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCProperty get(int ordinal) {
/* 158 */     Iterator<CTOCProperty> it = iterator();
/* 159 */     while (it.hasNext()) {
/* 160 */       CTOCProperty current = it.next();
/* 161 */       if (current.ord() == ordinal) {
/* 162 */         return current;
/*     */       }
/*     */     } 
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 172 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 182 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 190 */     return new Iterator() {
/* 191 */         private CTOCProperty current = CTOCProperty.first;
/*     */         
/*     */         public boolean hasNext() {
/* 194 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 198 */           CTOCProperty c = this.current;
/* 199 */           this.current = this.current.next();
/* 200 */           return c;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 204 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 213 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 220 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCProperty first() {
/* 227 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CTOCProperty last() {
/* 234 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCProperty prev() {
/* 242 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCProperty next() {
/* 250 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */