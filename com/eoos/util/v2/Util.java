/*     */ package com.eoos.util.v2;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.regexp.RE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static boolean equals(Object obj, Object obj2) {
/*  24 */     return (obj == null) ? ((obj2 == null)) : obj.equals(obj2);
/*     */   }
/*     */   
/*     */   public static long createRandom(long min, long max) {
/*  28 */     long offset = (long)(Math.random() * (max - min));
/*  29 */     return min + offset;
/*     */   }
/*     */   
/*     */   public static void sleepRandom(long min, long max) throws InterruptedException {
/*  33 */     Thread.sleep(createRandom(min, max));
/*     */   }
/*     */   
/*     */   public static CharSequence getClassName(Class c) {
/*  37 */     String tmp = c.getName();
/*  38 */     Package p = c.getPackage();
/*  39 */     return (p == null) ? tmp : tmp.substring(p.getName().length() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Object obj, Map innerState) {
/*  48 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get();
/*     */     try {
/*  50 */       retValue.append(getClassName(obj.getClass()));
/*  51 */       retValue.append("@");
/*  52 */       retValue.append(obj.hashCode());
/*  53 */       retValue.append("[");
/*  54 */       if (innerState != null) {
/*  55 */         for (Iterator<Map.Entry> iter = innerState.entrySet().iterator(); iter.hasNext(); ) {
/*  56 */           Map.Entry entry = iter.next();
/*  57 */           retValue.append(String.valueOf(entry.getKey()));
/*  58 */           retValue.append(": ");
/*  59 */           retValue.append(String.valueOf(entry.getValue()));
/*  60 */           if (iter.hasNext()) {
/*  61 */             retValue.append(", ");
/*     */           }
/*     */         } 
/*     */       }
/*  65 */       retValue.append("]");
/*  66 */       return retValue.toString();
/*     */     } finally {
/*     */       
/*  69 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class ReverseComparator
/*     */     implements Comparator {
/*     */     private Comparator backend;
/*     */     
/*     */     private ReverseComparator(Comparator backend) {
/*  78 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/*  82 */       return this.backend.compare(o1, o2) * -1;
/*     */     }
/*     */     
/*     */     public Comparator getBackend() {
/*  86 */       return this.backend;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Comparator reverseComparator(Comparator comparator) {
/*  92 */     if (comparator instanceof ReverseComparator) {
/*  93 */       return ((ReverseComparator)comparator).getBackend();
/*     */     }
/*  95 */     return new ReverseComparator(comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int compare(long long1, long long2) {
/* 100 */     long diff = long1 - long2;
/* 101 */     return (diff == 0L) ? 0 : ((diff < 0L) ? -1 : 1);
/*     */   }
/*     */   
/*     */   public static int compare(boolean b1, boolean b2) {
/* 105 */     return (b1 == b2) ? 0 : (b1 ? -11 : 1);
/*     */   }
/*     */   
/*     */   public static int compare(Object obj1, Object obj2) {
/* 109 */     if (obj1 == null && obj2 == null)
/* 110 */       return 0; 
/* 111 */     if (obj1 == null && obj2 != null)
/* 112 */       return 1; 
/* 113 */     if (obj1 != null && obj2 == null)
/* 114 */       return -1; 
/* 115 */     if (obj1 instanceof Comparable)
/* 116 */       return ((Comparable<Object>)obj1).compareTo(obj2); 
/* 117 */     if (obj2 instanceof Comparable) {
/* 118 */       return ((Comparable<Object>)obj2).compareTo(obj1);
/*     */     }
/* 120 */     return obj1.hashCode() - obj2.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Throwable getRootCause(Throwable t) {
/* 125 */     Throwable ret = t;
/* 126 */     while (ret.getCause() != null) {
/* 127 */       ret = ret.getCause();
/*     */     }
/* 129 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseMillis(String period) throws Exception {
/* 143 */     if (period == null || period.trim().length() == 0) {
/* 144 */       throw new IllegalArgumentException();
/*     */     }
/* 146 */     long ret = 0L;
/* 147 */     RE reDays = new RE("d(ay(s)?)?", 1);
/* 148 */     RE reHours = new RE("h(our(s)?)?", 1);
/* 149 */     RE reMinutes = new RE("m(in(ute(s)?)?)?", 1);
/* 150 */     RE reSeconds = new RE("s(ec(ond(s)?)?)?", 1);
/* 151 */     RE reMillis = new RE("mi(lli(s(econd(s)?)?)?)?", 1);
/* 152 */     RE[] reUnits = { reDays, reHours, reMinutes, reSeconds, reMillis };
/* 153 */     RE reSplit = new RE("\\s*,\\s*");
/* 154 */     RE rePart = new RE("(\\d*)\\s*([a-z]*)");
/*     */     
/* 156 */     String[] parts = reSplit.split(period);
/* 157 */     for (int i = 0; i < parts.length; i++) {
/* 158 */       String part = parts[i].trim();
/* 159 */       if (!rePart.match(part)) {
/* 160 */         throw new Exception("unable to parse part :" + String.valueOf(part));
/*     */       }
/* 162 */       long count = Long.parseLong(rePart.getParen(1));
/* 163 */       RE unitMatchingRE = null;
/* 164 */       String unit = rePart.getParen(2);
/* 165 */       for (int j = 0; j < reUnits.length && unitMatchingRE == null; j++) {
/* 166 */         if (reUnits[j].match(unit) && reUnits[j].getParenLength(0) == unit.length()) {
/* 167 */           unitMatchingRE = reUnits[j];
/*     */           
/* 169 */           List tmp = new LinkedList(Arrays.asList((Object[])reUnits));
/* 170 */           tmp.remove(unitMatchingRE);
/* 171 */           reUnits = (RE[])tmp.toArray((Object[])new RE[reUnits.length - 1]);
/*     */         } 
/*     */       } 
/* 174 */       if (reDays == unitMatchingRE) {
/* 175 */         ret += count * 24L * 60L * 60L * 1000L;
/* 176 */       } else if (reHours == unitMatchingRE) {
/* 177 */         ret += count * 60L * 60L * 1000L;
/* 178 */       } else if (reMinutes == unitMatchingRE) {
/* 179 */         ret += count * 60L * 1000L;
/* 180 */       } else if (reSeconds == unitMatchingRE) {
/* 181 */         ret += count * 1000L;
/* 182 */       } else if (reMillis == unitMatchingRE) {
/* 183 */         ret += count;
/*     */       } else {
/* 185 */         throw new Exception("unable to match unit :" + String.valueOf(unit));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 190 */     return ret;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 194 */     String[] test = { "1h", "1hour", "1 hours", "2hs", "1h,    1h", "1d", "1 days", "1 day", "1 day, 1 hour", "1m", "1minute", "1 minute", "2 minutes", "1s", "1sec", "1\tsecond", "2   seconds", "1 min,   2 seconds", "1milli", "2 millis", "2 mi", "1 minute, 1 sec, 10millis" };
/* 195 */     for (int i = 0; i < test.length; i++) {
/* 196 */       System.out.print("parsing: " + test[i] + " --> ");
/*     */       try {
/* 198 */         System.out.println(parseMillis(test[i]) + " ms");
/* 199 */       } catch (Exception e) {
/* 200 */         System.out.println("error: " + e.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface InnerStateDisplayCallback {
/*     */     String getInnerStateDisplay();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */