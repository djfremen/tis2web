/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.util.SubscriptionUtil;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class SubscriptionAdapter
/*    */   implements Subscription {
/*    */   private Subscription ae;
/*    */   
/*    */   public SubscriptionAdapter(Subscription ae) {
/* 13 */     this.ae = ae;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 17 */     return this.ae.getDescription(locale);
/*    */   }
/*    */   
/*    */   public String getSubscriptionID() {
/* 21 */     return this.ae.getSubscriptionID();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 26 */     if (this == obj)
/* 27 */       return true; 
/* 28 */     if (obj instanceof SubscriptionAdapter) {
/* 29 */       SubscriptionAdapter other = (SubscriptionAdapter)obj;
/* 30 */       boolean ret = SubscriptionUtil.equals(this, other);
/* 31 */       return ret;
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     return SubscriptionUtil.hashCode(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\adapter\SubscriptionAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */