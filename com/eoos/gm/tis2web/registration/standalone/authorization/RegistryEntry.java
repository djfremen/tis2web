/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFlag;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*    */ 
/*    */ public class RegistryEntry
/*    */ {
/*    */   private String subscriberID;
/*    */   private RegistrationFlag flag;
/*    */   private Authorization authorization;
/*    */   
/*    */   public RegistryEntry(String subscriberID, RegistrationFlag flag, Authorization authorization) {
/* 13 */     this.subscriberID = subscriberID;
/* 14 */     this.flag = flag;
/* 15 */     this.authorization = authorization;
/*    */   }
/*    */   
/*    */   public Authorization getAuthorization() {
/* 19 */     return this.authorization;
/*    */   }
/*    */   
/*    */   public RegistrationFlag getFlag() {
/* 23 */     return this.flag;
/*    */   }
/*    */   
/*    */   public String getSubscriberID() {
/* 27 */     return this.subscriberID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\RegistryEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */