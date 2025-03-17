/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*    */ import com.eoos.util.v2.Util;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface Subscription {
/*    */   String getDenotation(Locale paramLocale);
/*    */   
/*    */   String getSubscriptionID();
/*    */   
/*    */   public static class Comparator implements java.util.Comparator {
/*    */     public Comparator(Locale locale) {
/* 12 */       this.locale = locale;
/*    */     }
/*    */     private Locale locale;
/*    */     public int compare(Object arg0, Object arg1) {
/*    */       try {
/* 17 */         String s1 = ((Subscription)arg0).getDenotation(this.locale);
/* 18 */         String s2 = ((Subscription)arg1).getDenotation(this.locale);
/* 19 */         return Util.compare(s1, s2);
/* 20 */       } catch (Exception e) {
/* 21 */         return 0;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\Subscription.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */