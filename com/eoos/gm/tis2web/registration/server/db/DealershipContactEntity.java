/*    */ package com.eoos.gm.tis2web.registration.server.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*    */ 
/*    */ public class DealershipContactEntity
/*    */   implements DealershipContact {
/*    */   private String contactName;
/*    */   private String contactLanguage;
/*    */   
/*    */   public DealershipContactEntity(String contactName, String contactLanguage) {
/* 11 */     this.contactName = contactName;
/* 12 */     this.contactLanguage = contactLanguage;
/*    */   }
/*    */   
/*    */   public void setContactName(String contactName) {
/* 16 */     this.contactName = contactName;
/*    */   }
/*    */   
/*    */   public String getContactName() {
/* 20 */     return this.contactName;
/*    */   }
/*    */   
/*    */   public void setContactLanguage(String contactLanguage) {
/* 24 */     this.contactLanguage = contactLanguage;
/*    */   }
/*    */   
/*    */   public String getContactLanguage() {
/* 28 */     return this.contactLanguage;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\DealershipContactEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */