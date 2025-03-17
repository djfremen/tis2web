/*     */ package com.eoos.datatype;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.tokenizer.v2.CSTokenizer;
/*     */ import com.eoos.util.HashCalc;
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Time
/*     */   implements Serializable, Comparable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int HOURS_A_DAY = 24;
/*     */   public static final int MINUTES_A_DAY = 1440;
/*     */   public static final int SECONDS_A_DAY = 86400;
/*     */   public static final int MILLIS_A_DAY = 86400000;
/*     */   public static final int MILLIS_A_SECOND = 1000;
/*     */   public static final int MILLIS_A_MINUTE = 60000;
/*     */   public static final int MILLIS_AN_HOUR = 3600000;
/*     */   private final int millis;
/*     */   
/*     */   public Time(int millis) {
/*  31 */     if (millis < 0 || millis > 86400000) {
/*  32 */       throw new IllegalArgumentException();
/*     */     }
/*  34 */     this.millis = millis % 86400000;
/*     */   }
/*     */   
/*     */   public int getHour() {
/*  38 */     return this.millis / 3600000 % 24;
/*     */   }
/*     */   
/*     */   public int getMinute() {
/*  42 */     return this.millis / 60000 % 60;
/*     */   }
/*     */   
/*     */   public int getSecond() {
/*  46 */     return this.millis / 1000 % 60;
/*     */   }
/*     */   
/*     */   public int getMillisecond() {
/*  50 */     return this.millis % 1000;
/*     */   }
/*     */   
/*     */   public Date toDate(Date day) {
/*  54 */     Calendar c = Calendar.getInstance();
/*  55 */     c.setTime(day);
/*  56 */     c.set(11, getHour());
/*  57 */     c.set(12, getMinute());
/*  58 */     c.set(13, getSecond());
/*  59 */     c.set(14, getMillisecond());
/*  60 */     return c.getTime();
/*     */   }
/*     */   
/*     */   public static Time forDate(Date date) {
/*  64 */     Calendar c = Calendar.getInstance();
/*  65 */     c.setTime(date);
/*  66 */     int hour = c.get(11);
/*  67 */     int minute = c.get(12);
/*  68 */     int second = c.get(13);
/*  69 */     int milli = c.get(14);
/*  70 */     return new Time(hour * 3600000 + minute * 60000 + second * 1000 + milli);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Time currentTime() {
/*  75 */     return forDate(new Date());
/*     */   }
/*     */   
/*     */   public Time add(int millis) {
/*  79 */     return new Time(this.millis + millis);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  83 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get();
/*     */     try {
/*  85 */       int[] count = { getHour(), getMinute(), getSecond() };
/*  86 */       for (int i = 0; i < 3; i++) {
/*  87 */         retValue.append((count[i] < 10) ? ("0" + count[i]) : String.valueOf(count[i]));
/*  88 */         if (i == 2) {
/*  89 */           retValue.append(",");
/*     */         } else {
/*  91 */           retValue.append(":");
/*     */         } 
/*     */       } 
/*  94 */       int millis = getMillisecond();
/*  95 */       retValue.append((millis > 100) ? String.valueOf(millis) : ((millis > 10) ? ("0" + millis) : ("00" + millis)));
/*  96 */       return retValue.toString();
/*     */     } finally {
/*     */       
/*  99 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int compareTo(Object o) {
/* 104 */     return this.millis - ((Time)o).millis;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 108 */     int retValue = Time.class.hashCode();
/* 109 */     retValue = HashCalc.addHashCode(retValue, this.millis);
/* 110 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 115 */     if (this == obj)
/* 116 */       return true; 
/* 117 */     if (obj instanceof Time) {
/* 118 */       Time other = (Time)obj;
/* 119 */       boolean ret = (compareTo(other) == 0);
/* 120 */       return ret;
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Time parse(String time) throws NumberFormatException, IllegalArgumentException {
/* 127 */     if (time == null || time.indexOf(":") == -1) {
/* 128 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 131 */     int millis = 0;
/* 132 */     CSTokenizer tokenizer = new CSTokenizer(time, new String[] { ":", "-", ",", " " });
/* 133 */     int[] factors = { 3600000, 60000, 1000, 1 };
/* 134 */     for (int i = 0; i < 4 && tokenizer.hasNext(); i++) {
/* 135 */       millis += Integer.parseInt(String.valueOf(tokenizer.next())) * factors[i];
/*     */     }
/* 137 */     return new Time(millis);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 142 */     System.out.println(parse("12:18"));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\Time.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */