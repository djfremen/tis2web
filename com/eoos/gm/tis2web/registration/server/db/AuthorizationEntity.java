/*    */ package com.eoos.gm.tis2web.registration.server.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AuthorizationEntity
/*    */   implements Subscription
/*    */ {
/*    */   private String authorizationID;
/*    */   private SalesOrganization organization;
/* 14 */   private Map descriptions = new HashMap<Object, Object>();
/*    */   
/*    */   public AuthorizationEntity(String authorizationID) {
/* 17 */     this.authorizationID = authorizationID.trim();
/*    */   }
/*    */   
/*    */   public String getAuthorizationID() {
/* 21 */     return this.authorizationID;
/*    */   }
/*    */   
/*    */   public String getSubscriptionID() {
/* 25 */     return this.authorizationID;
/*    */   }
/*    */   
/*    */   public SalesOrganization getOrganization() {
/* 29 */     return this.organization;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 33 */     String label = (String)this.descriptions.get(locale.toString().toLowerCase(Locale.ENGLISH));
/* 34 */     if (label != null) {
/* 35 */       return label;
/*    */     }
/* 37 */     label = (String)this.descriptions.get(locale.getLanguage());
/* 38 */     if (label != null) {
/* 39 */       return label;
/*    */     }
/* 41 */     label = (String)this.descriptions.get(Locale.US.toString().toLowerCase(Locale.ENGLISH));
/* 42 */     if (label != null) {
/* 43 */       return label;
/*    */     }
/* 45 */     label = (String)this.descriptions.get(Locale.ENGLISH.getLanguage());
/* 46 */     if (label != null) {
/* 47 */       return label;
/*    */     }
/* 49 */     return label;
/*    */   }
/*    */   
/*    */   public void setDescription(Locale locale, String description) {
/* 53 */     this.descriptions.put(locale.toString().toLowerCase(Locale.ENGLISH), description);
/* 54 */     this.descriptions.put(locale.getLanguage().substring(0, 2), description);
/*    */   }
/*    */   
/*    */   public void setOrganization(SalesOrganization organization) {
/* 58 */     this.organization = organization;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     return this.authorizationID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\AuthorizationEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */