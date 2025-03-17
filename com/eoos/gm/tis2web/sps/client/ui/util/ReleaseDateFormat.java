/*    */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReleaseDateFormat
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(ReleaseDateFormat.class);
/*    */   
/*    */   public static String getReleaseDateFormat(String dateString) {
/* 20 */     if (dateString == null) {
/* 21 */       return null;
/*    */     }
/* 23 */     Date relDate = null;
/*    */     
/*    */     try {
/* 26 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 27 */       relDate = sdf.parse(dateString);
/* 28 */     } catch (ParseException e) {
/* 29 */       log.error("unable to retrieve release date - error:" + e, e);
/* 30 */       return null;
/*    */     } 
/*    */     
/* 33 */     SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
/* 34 */     String newDate = formatter.format(relDate);
/* 35 */     return newDate;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\ReleaseDateFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */