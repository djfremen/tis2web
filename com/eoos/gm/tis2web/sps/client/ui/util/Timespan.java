/*     */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Timespan {
/*     */   public static final int DAYS = 2;
/*     */   public static final int HOURS = 3;
/*     */   public static final int MINUTES = 4;
/*     */   public static final int SECONDS = 5;
/*     */   public static final int MILLISECOND = 6;
/*     */   public static final int MONTHS = 7;
/*     */   public static final int YEARS = 8;
/*     */   private int unit;
/*     */   private long span;
/*     */   
/*     */   public static class RelativeTimespan {
/*     */     public RelativeTimespan(long span, int unit) {
/*  18 */       this(span, unit, -1L);
/*     */     }
/*     */ 
/*     */     
/*  22 */     private Calendar relationTime = Calendar.getInstance(); private Calendar targetTime;
/*     */     public RelativeTimespan(long span, int unit, long relationTime) {
/*  24 */       if (relationTime != -1L) {
/*  25 */         this.relationTime.setTime(new Date(relationTime));
/*     */       }
/*     */       
/*  28 */       this.targetTime = Calendar.getInstance();
/*  29 */       this.targetTime.setTime(this.relationTime.getTime());
/*     */       
/*  31 */       switch (unit) {
/*     */         case 8:
/*  33 */           this.targetTime.set(1, this.targetTime.get(1) + (int)span);
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 7:
/*  39 */           this.targetTime.set(2, this.targetTime.get(2) + (int)span);
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/*  45 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 24L * 60L * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/*  51 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 60L * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/*  57 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 60L * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/*  63 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span * 1000L));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 6:
/*  69 */           this.targetTime.setTime(new Date(this.targetTime.getTime().getTime() + span));
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  75 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getAsDays() {
/*  80 */       return getAsHours() / 24L;
/*     */     }
/*     */     
/*     */     public long getAsHours() {
/*  84 */       return getAsMinutes() / 60L;
/*     */     }
/*     */     
/*     */     public long getAsMillis() {
/*  88 */       return this.targetTime.getTime().getTime() - this.relationTime.getTime().getTime();
/*     */     }
/*     */     
/*     */     public long getAsMinutes() {
/*  92 */       return getAsSeconds() / 60L;
/*     */     }
/*     */     
/*     */     public long getAsMonths() {
/*  96 */       long remainder = (this.relationTime.getActualMaximum(5) - this.relationTime.get(5));
/*  97 */       long days = getAsDays() - remainder;
/*  98 */       Calendar tmp = Calendar.getInstance();
/*     */       
/* 100 */       tmp.setTime(new Date(this.relationTime.getTime().getTime()));
/* 101 */       tmp.set(2, tmp.get(2) + 1);
/*     */       
/* 103 */       long month = 0L;
/*     */       
/* 105 */       while (days > tmp.getActualMaximum(5)) {
/* 106 */         month++;
/* 107 */         days -= tmp.getActualMaximum(5);
/* 108 */         tmp.set(2, tmp.get(2) + 1);
/*     */       } 
/*     */       
/* 111 */       if (month > 0L && tmp.get(5) >= days) {
/* 112 */         month++;
/*     */       }
/*     */       
/* 115 */       return month;
/*     */     }
/*     */     
/*     */     public long getAsSeconds() {
/* 119 */       return getAsMillis() / 1000L;
/*     */     }
/*     */     
/*     */     public long getAsYears() {
/* 123 */       return (this.targetTime.get(1) - this.relationTime.get(1));
/*     */     }
/*     */     
/*     */     public long getDays() {
/* 127 */       Calendar tmp = Calendar.getInstance();
/*     */       
/* 129 */       tmp.setTime(this.relationTime.getTime());
/*     */       
/* 131 */       long days = 0L;
/*     */       
/* 133 */       for (int i = 0; i < getAsMonths(); i++) {
/* 134 */         days += tmp.getActualMaximum(5);
/* 135 */         tmp.set(2, tmp.get(2) + 1);
/*     */       } 
/*     */       
/* 138 */       return getAsDays() - days;
/*     */     }
/*     */     
/*     */     public long getHours() {
/* 142 */       return getAsHours() - getAsDays() * 24L;
/*     */     }
/*     */     
/*     */     public long getMillis() {
/* 146 */       return getAsMillis() - getAsSeconds() * 1000L;
/*     */     }
/*     */     
/*     */     public long getMinutes() {
/* 150 */       return getAsMinutes() - getAsHours() * 60L;
/*     */     }
/*     */     
/*     */     public long getMonths() {
/* 154 */       return getAsMonths() - getAsYears() * 12L;
/*     */     }
/*     */     
/*     */     public long getSeconds() {
/* 158 */       return getAsSeconds() - getAsMinutes() * 60L;
/*     */     }
/*     */     
/*     */     public long getYears() {
/* 162 */       return getAsYears();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 166 */       StringBuffer result = new StringBuffer();
/*     */       
/* 168 */       DecimalFormat nrFormat = new DecimalFormat();
/* 169 */       nrFormat.setMinimumIntegerDigits(2);
/* 170 */       result.append(nrFormat.format(getHours()));
/* 171 */       result.append(":" + nrFormat.format(getMinutes()));
/* 172 */       result.append(":" + nrFormat.format(getSeconds()));
/*     */ 
/*     */       
/* 175 */       return result.toString();
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
/*     */   public Timespan(long from, long to) {
/* 194 */     this.span = to - from;
/* 195 */     this.unit = 6;
/*     */   }
/*     */   
/*     */   public Timespan(long span) {
/* 199 */     this.span = span;
/* 200 */     this.unit = 6;
/*     */   }
/*     */   
/*     */   public Timespan(long span, int unit) {
/* 204 */     this.span = span;
/* 205 */     this.unit = unit;
/*     */   }
/*     */   
/*     */   public long getAsMillis() {
/* 209 */     switch (this.unit) {
/*     */       case 6:
/* 211 */         return this.span;
/*     */       
/*     */       case 5:
/* 214 */         return this.span * 1000L;
/*     */       
/*     */       case 4:
/* 217 */         return this.span * 60L * 1000L;
/*     */       
/*     */       case 3:
/* 220 */         return this.span * 60L * 60L * 1000L;
/*     */       
/*     */       case 2:
/* 223 */         return this.span * 24L * 60L * 60L * 1000L;
/*     */     } 
/*     */     
/* 226 */     return (new RelativeTimespan(this.span, this.unit)).getAsMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public RelativeTimespan getRelativeTimespan() {
/* 231 */     return new RelativeTimespan(this.span, this.unit);
/*     */   }
/*     */   
/*     */   public RelativeTimespan getRelativeTimespan(long relationTime) {
/* 235 */     return new RelativeTimespan(this.span, this.unit, relationTime);
/*     */   }
/*     */   
/*     */   public long getSpan() {
/* 239 */     return this.span;
/*     */   }
/*     */   
/*     */   public int getUnit() {
/* 243 */     return this.unit;
/*     */   }
/*     */   
/*     */   public void test() {
/* 247 */     Calendar c = Calendar.getInstance();
/*     */     
/* 249 */     c.set(1, 2004);
/* 250 */     c.set(6, c.get(6) + 1);
/* 251 */     System.out.println(c.getTime());
/*     */     
/* 253 */     Timespan sp = new Timespan(c.getTime().getTime() - System.currentTimeMillis(), 6);
/*     */     
/* 255 */     RelativeTimespan rts = sp.getRelativeTimespan();
/*     */     
/* 257 */     System.out.println(rts.getYears());
/* 258 */     System.out.println(rts.getMonths());
/* 259 */     System.out.println(rts.getDays());
/* 260 */     System.out.println(rts.getHours());
/* 261 */     System.out.println(rts.getMinutes());
/* 262 */     System.out.println(rts.getSeconds());
/* 263 */     System.out.println(rts.getMillis());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 267 */     RelativeTimespan rt = new RelativeTimespan(this.span, this.unit);
/* 268 */     return rt.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\Timespan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */