/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*    */ import com.eoos.util.HashCalc;
/*    */ import com.eoos.util.v2.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubscriptionUtil
/*    */ {
/*    */   public static boolean equals(Subscription s1, Subscription s2) {
/* 13 */     return Util.equals(s1.getSubscriptionID(), s2.getSubscriptionID());
/*    */   }
/*    */   
/*    */   public static int hashCode(Subscription s) {
/* 17 */     int ret = Subscription.class.hashCode();
/* 18 */     ret = HashCalc.addHashCode(ret, s.getSubscriptionID());
/* 19 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamode\\util\SubscriptionUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */