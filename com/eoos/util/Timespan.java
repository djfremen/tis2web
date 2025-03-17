/*     */ package com.eoos.util;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timespan
/*     */ {
/*  12 */   public static final Timespan _1_MINUTE = new Timespan(1L, 4);
/*     */   
/*  14 */   public static final Timespan _1_HOUR = new Timespan(1L, 3);
/*     */   
/*  16 */   public static final Timespan _1_SECOND = new Timespan(1L, 5); public static final int DAYS = 2; public static final int HOURS = 3; public static final int MINUTES = 4; public static final int SECONDS = 5;
/*     */   public static final int MILLISECOND = 6;
/*  18 */   public static final Timespan _1_DAY = new Timespan(1L, 2);
/*     */   public static final int MONTHS = 7;
/*     */   public static final int YEARS = 8;
/*     */   private int unit;
/*     */   private long span;
/*     */   
/*     */   public static class RelativeTimespan
/*     */   {
/*     */     public RelativeTimespan(long span, int unit) {
/*  27 */       this(span, unit, -1L);
/*     */     }
/*     */ 
/*     */     
/*  31 */     private Calendar relationTime = Calendar.getInstance(); private Calendar targetTime;
/*     */     public RelativeTimespan(long span, int unit, long relationTime) {
/*  33 */       if (relationTime != -1L) {
/*  34 */         this.relationTime.setTime(new Date(relationTime));
/*     */       }
/*     */       
/*  37 */       this.targetTime = Calendar.getInstance();
/*  38 */       this.targetTime.setTime(this.relationTime.getTime());
/*     */       
/*  40 */       switch (unit) {
/*     */         case 8:
/*  42 */           this.targetTime.set(1, this.targetTime.get(1) + (int)span);
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 7:
/*  48 */           this.targetTime.set(2, this.targetTime.get(2) + (int)span);
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/*  54 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 24L * 60L * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/*  60 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 60L * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/*  66 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/*  72 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 6:
/*  78 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span));
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  84 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getAsDays() {
/*  89 */       return getAsHours() / 24L;
/*     */     }
/*     */     
/*     */     public long getAsHours() {
/*  93 */       return getAsMinutes() / 60L;
/*     */     }
/*     */     
/*     */     public long getAsMillis() {
/*  97 */       return this.targetTime.getTime().getTime() - this.relationTime.getTime().getTime();
/*     */     }
/*     */     
/*     */     public long getAsMinutes() {
/* 101 */       return getAsSeconds() / 60L;
/*     */     }
/*     */     
/*     */     public long getAsMonths() {
/* 105 */       long remainder = (this.relationTime.getActualMaximum(5) - this.relationTime.get(5));
/* 106 */       long days = getAsDays() - remainder;
/* 107 */       Calendar tmp = Calendar.getInstance();
/*     */       
/* 109 */       tmp.setTime(new Date(this.relationTime.getTime().getTime()));
/* 110 */       tmp.set(2, tmp.get(2) + 1);
/*     */       
/* 112 */       long month = 0L;
/*     */       
/* 114 */       while (days > tmp.getActualMaximum(5)) {
/* 115 */         month++;
/* 116 */         days -= tmp.getActualMaximum(5);
/* 117 */         tmp.set(2, tmp.get(2) + 1);
/*     */       } 
/*     */       
/* 120 */       if (month > 0L && tmp.get(5) >= days) {
/* 121 */         month++;
/*     */       }
/*     */       
/* 124 */       return month;
/*     */     }
/*     */     
/*     */     public long getAsSeconds() {
/* 128 */       return getAsMillis() / 1000L;
/*     */     }
/*     */     
/*     */     public long getAsYears() {
/* 132 */       return (this.targetTime.get(1) - this.relationTime.get(1));
/*     */     }
/*     */     
/*     */     public long getDays() {
/* 136 */       Calendar tmp = Calendar.getInstance();
/*     */       
/* 138 */       tmp.setTime(this.relationTime.getTime());
/*     */       
/* 140 */       long days = 0L;
/*     */       
/* 142 */       for (int i = 0; i < getAsMonths(); i++) {
/* 143 */         days += tmp.getActualMaximum(5);
/* 144 */         tmp.set(2, tmp.get(2) + 1);
/*     */       } 
/*     */       
/* 147 */       return getAsDays() - days;
/*     */     }
/*     */     
/*     */     public long getHours() {
/* 151 */       return getAsHours() - getAsDays() * 24L;
/*     */     }
/*     */     
/*     */     public long getMillis() {
/* 155 */       return getAsMillis() - getAsSeconds() * 1000L;
/*     */     }
/*     */     
/*     */     public long getMinutes() {
/* 159 */       return getAsMinutes() - getAsHours() * 60L;
/*     */     }
/*     */     
/*     */     public long getMonths() {
/* 163 */       return getAsMonths() - getAsYears() * 12L;
/*     */     }
/*     */     
/*     */     public long getSeconds() {
/* 167 */       return getAsSeconds() - getAsMinutes() * 60L;
/*     */     }
/*     */     
/*     */     public long getYears() {
/* 171 */       return getAsYears();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 175 */       StringBuffer result = StringBufferPool.getThreadInstance().get();
/*     */       
/*     */       try {
/* 178 */         if (getYears() > 0L || result.length() > 0) {
/* 179 */           result.append(", " + getYears() + " years");
/*     */         }
/*     */         
/* 182 */         if (getMonths() > 0L || result.length() > 0) {
/* 183 */           result.append(", " + getMonths() + " months");
/*     */         }
/*     */         
/* 186 */         if (getDays() > 0L || result.length() > 0) {
/* 187 */           result.append(", " + getDays() + " days");
/*     */         }
/*     */         
/* 190 */         if (getHours() > 0L || result.length() > 0) {
/* 191 */           result.append(", " + getHours() + " hours");
/*     */         }
/*     */         
/* 194 */         if (getMinutes() > 0L || result.length() > 0) {
/* 195 */           result.append(", " + getMinutes() + " minutes");
/*     */         }
/*     */         
/* 198 */         if (getSeconds() > 0L || result.length() > 0) {
/* 199 */           result.append(", " + getSeconds() + " seconds");
/*     */         }
/*     */         
/* 202 */         result.append(", " + getMillis() + " millis");
/*     */         
/* 204 */         result.delete(0, 2);
/*     */         
/* 206 */         return result.toString();
/*     */       } finally {
/*     */         
/* 209 */         StringBufferPool.getThreadInstance().free(result);
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timespan(long span) {
/* 233 */     this.span = span;
/* 234 */     this.unit = 6;
/*     */   }
/*     */   
/*     */   public Timespan(long span, int unit) {
/* 238 */     this.span = span;
/* 239 */     this.unit = unit;
/*     */   }
/*     */   
/*     */   public Timespan(Timespan timespan, int factor) {
/* 243 */     this(factor * timespan.getAsMillis());
/*     */   }
/*     */   
/*     */   public long getAsMillis() {
/* 247 */     switch (this.unit) {
/*     */       case 6:
/* 249 */         return this.span;
/*     */       
/*     */       case 5:
/* 252 */         return this.span * 1000L;
/*     */       
/*     */       case 4:
/* 255 */         return this.span * 60L * 1000L;
/*     */       
/*     */       case 3:
/* 258 */         return this.span * 60L * 60L * 1000L;
/*     */       
/*     */       case 2:
/* 261 */         return this.span * 24L * 60L * 60L * 1000L;
/*     */     } 
/*     */     
/* 264 */     return (new RelativeTimespan(this.span, this.unit)).getAsMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public RelativeTimespan getRelativeTimespan() {
/* 269 */     return new RelativeTimespan(this.span, this.unit);
/*     */   }
/*     */   
/*     */   public RelativeTimespan getRelativeTimespan(long relationTime) {
/* 273 */     return new RelativeTimespan(this.span, this.unit, relationTime);
/*     */   }
/*     */   
/*     */   public long getSpan() {
/* 277 */     return this.span;
/*     */   }
/*     */   
/*     */   public int getUnit() {
/* 281 */     return this.unit;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 285 */     Calendar c = Calendar.getInstance();
/*     */     
/* 287 */     c.set(1, 2004);
/* 288 */     c.set(6, c.get(6) + 1);
/* 289 */     System.out.println(c.getTime());
/*     */     
/* 291 */     Timespan sp = new Timespan(c.getTime().getTime() - System.currentTimeMillis(), 6);
/*     */     
/* 293 */     RelativeTimespan rts = sp.getRelativeTimespan();
/*     */     
/* 295 */     System.out.println(rts.getYears());
/* 296 */     System.out.println(rts.getMonths());
/* 297 */     System.out.println(rts.getDays());
/* 298 */     System.out.println(rts.getHours());
/* 299 */     System.out.println(rts.getMinutes());
/* 300 */     System.out.println(rts.getSeconds());
/* 301 */     System.out.println(rts.getMillis());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 305 */     RelativeTimespan rt = new RelativeTimespan(this.span, this.unit);
/* 306 */     return rt.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Timespan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */