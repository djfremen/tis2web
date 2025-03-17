/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DateConvert
/*    */ {
/* 13 */   private static final DateFormat DF_ISO_0 = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Date toDate(long date) {
/* 20 */     Calendar c = Calendar.getInstance();
/* 21 */     c.setTime(new Date(date));
/* 22 */     return c.getTime();
/*    */   }
/*    */   
/*    */   public static String toDateString(long date) {
/* 26 */     Date d = toDate(date);
/* 27 */     return d.toString();
/*    */   }
/*    */   
/*    */   public static String toDateString(long date, DateFormat df) {
/* 31 */     Date d = toDate(date);
/* 32 */     return df.format(d);
/*    */   }
/*    */   
/*    */   public static String toISOFormat(Date date) {
/* 36 */     synchronized (DF_ISO_0) {
/* 37 */       return DF_ISO_0.format(date);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String toISOFormat(long date) {
/* 42 */     return toISOFormat(toDate(date));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\DateConvert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */