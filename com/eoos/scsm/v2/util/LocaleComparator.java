/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LocaleComparator
/*    */   implements Comparator {
/*  9 */   private static final Logger log = Logger.getLogger(LocaleComparator.class);
/*    */   
/*    */   private Locale clientLocale;
/*    */   
/*    */   public LocaleComparator(Locale clientLocale) {
/* 14 */     this.clientLocale = clientLocale;
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 19 */       Locale l1 = (Locale)o1;
/* 20 */       Locale l2 = (Locale)o2;
/* 21 */       return l1.getDisplayName(this.clientLocale).compareTo(l2.getDisplayName(this.clientLocale));
/* 22 */     } catch (Exception e) {
/* 23 */       log.warn("unable to compare locales, returning 0 - exception: " + e, e);
/* 24 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\LocaleComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */