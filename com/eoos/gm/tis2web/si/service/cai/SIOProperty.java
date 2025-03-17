/*     */ package com.eoos.gm.tis2web.si.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class SIOProperty
/*     */   implements SITOCProperty, Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  14 */   private static int size = 0;
/*     */   
/*  16 */   private static int nextOrd = 0;
/*     */   
/*  18 */   private static Map nameMap = new HashMap<Object, Object>(9);
/*     */   
/*  20 */   private static SIOProperty first = null;
/*     */   
/*  22 */   private static SIOProperty last = null;
/*     */   
/*     */   private final int ord;
/*     */   
/*     */   private final String label;
/*     */   
/*     */   private SIOProperty prev;
/*     */   
/*     */   private SIOProperty next;
/*     */   
/*  32 */   public static final SIOProperty AssemblyGoups = new SIOProperty("ASSEMBLYGROUPS", 1);
/*     */   
/*  34 */   public static final SIOProperty SIT = new SIOProperty("SIT", 4);
/*     */   
/*  36 */   public static final SIOProperty LU = new SIOProperty("LU", 16);
/*     */   
/*  38 */   public static final SIOProperty CPR = new SIOProperty("CPR", 32);
/*     */   
/*  40 */   public static final SIOProperty WD = new SIOProperty("WD", 64);
/*     */   
/*  42 */   public static final SIOProperty LT = new SIOProperty("LT", 128);
/*     */   
/*  44 */   public static final SIOProperty MajorOperation = new SIOProperty("MO", 256);
/*     */   
/*  46 */   public static final SIOProperty PaintingStage = new SIOProperty("PS", 512);
/*     */   
/*  48 */   public static final SIOProperty ElectronicSystem = new SIOProperty("ES", 2048);
/*     */   
/*  50 */   public static final SIOProperty CPRSection = new SIOProperty("CPR-ACL", 4096);
/*     */   
/*  52 */   public static final SIOProperty Subject = new SIOProperty("SUBJ", 8192);
/*     */   
/*  54 */   public static final SIOProperty RelatedLU = new SIOProperty("RLU", 16384);
/*     */   
/*  56 */   public static final SIOProperty Publication = new SIOProperty("PUB", 32768);
/*     */   
/*  58 */   public static final SIOProperty PublicationYear = new SIOProperty("PUBYEAR", 65536);
/*     */   
/*  60 */   public static final SIOProperty NonMarketConstraint = new SIOProperty("NON-MARKET", 131072);
/*     */   
/*  62 */   public static final SIOProperty PublicationDate = new SIOProperty("PUBDATE", 262144);
/*     */   
/*  64 */   public static final SIOProperty PublicationID = new SIOProperty("PUBID", 524288);
/*     */   
/*  66 */   public static final SIOProperty CircuitFile = new SIOProperty("CIRCUIT", 1048576);
/*     */   
/*  68 */   public static final SIOProperty IO = new SIOProperty("IO", 8388608);
/*     */   
/*  70 */   public static final SIOProperty Page = new SIOProperty("PAGE", 67108864);
/*     */   
/*  72 */   public static final SIOProperty File = new SIOProperty("FILE", 134217728);
/*     */   
/*  74 */   public static final SIOProperty SCDS = new SIOProperty("SCDS", 2097152);
/*     */ 
/*     */   
/*  77 */   public static final SIOProperty LINKS = new SIOProperty("LINKS", 4194304);
/*     */ 
/*     */   
/*  80 */   public static final SIOProperty DTC = new SIOProperty("DTC", 16777216);
/*     */ 
/*     */   
/*  83 */   public static final SIOProperty COMPLAINT = new SIOProperty("SYMPTOM", 33554432);
/*     */ 
/*     */   
/*  86 */   public static final SIOProperty VERSION = new SIOProperty("VERSION", 268435456);
/*     */   
/*  88 */   public static final SIOProperty CPRChapter = new SIOProperty("CPR-Chapter", 536870912);
/*     */   
/*  90 */   public static final SIOProperty WIS = new SIOProperty("WIS", 1073741824);
/*  91 */   public static final SIOProperty WIS_DTC = new SIOProperty("WIS-DTC", -2147483648);
/*  92 */   public static final SIOProperty WIS_FAULTFINDER = new SIOProperty("WIS-FF", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final SIOProperty VCR = WIS;
/*  98 */   public static final SIOProperty RELEASE = WIS_DTC;
/*  99 */   public static final SIOProperty BULLETIN = WIS_FAULTFINDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOProperty(String label) {
/* 106 */     this(label, nextOrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOProperty(String label, int ord) {
/* 113 */     this.label = label;
/* 114 */     this.ord = ord;
/*     */     
/* 116 */     size++;
/* 117 */     nextOrd = ord + 1;
/*     */     
/* 119 */     nameMap.put(label, this);
/*     */     
/* 121 */     if (first == null) {
/* 122 */       first = this;
/*     */     }
/* 124 */     if (last != null) {
/* 125 */       this.prev = last;
/* 126 */       last.next = this;
/*     */     } 
/*     */     
/* 129 */     last = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/* 137 */     return this.ord - ((SIOProperty)obj).ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 145 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 152 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws InvalidObjectException {
/* 162 */     SIOProperty s = get(this.label);
/*     */     
/* 164 */     if (s != null) {
/* 165 */       return s;
/*     */     }
/* 167 */     String msg = "invalid deserialized object:  label = ";
/* 168 */     throw new InvalidObjectException(msg + this.label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOProperty get(String label) {
/* 176 */     return (SIOProperty)nameMap.get(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOProperty get(int ordinal) {
/* 185 */     Iterator<SIOProperty> it = iterator();
/* 186 */     while (it.hasNext()) {
/* 187 */       SIOProperty current = it.next();
/* 188 */       if (current.ord() == ordinal) {
/* 189 */         return current;
/*     */       }
/*     */     } 
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 199 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 209 */     throw new CloneNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator iterator() {
/* 217 */     return new Iterator() {
/* 218 */         private SIOProperty current = SIOProperty.first;
/*     */         
/*     */         public boolean hasNext() {
/* 221 */           return (this.current != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 225 */           SIOProperty s = this.current;
/* 226 */           this.current = this.current.next();
/* 227 */           return s;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 231 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ord() {
/* 240 */     return this.ord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/* 247 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOProperty first() {
/* 254 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIOProperty last() {
/* 261 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOProperty prev() {
/* 269 */     return this.prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOProperty next() {
/* 277 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */