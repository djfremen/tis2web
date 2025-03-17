/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class DealershipContactInfo
/*    */   implements DealershipContact, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String contactName;
/*    */   private String contactLanguage;
/*    */   
/*    */   public DealershipContactInfo() {}
/*    */   
/*    */   public DealershipContactInfo(String contactName, String contactLanguage) {
/* 17 */     this.contactName = contactName;
/* 18 */     this.contactLanguage = contactLanguage;
/*    */   }
/*    */   
/*    */   public void setContactName(String contactName) {
/* 22 */     this.contactName = contactName;
/*    */   }
/*    */   
/*    */   public String getContactName() {
/* 26 */     return this.contactName;
/*    */   }
/*    */   
/*    */   public void setContactLanguage(String contactLanguage) {
/* 30 */     this.contactLanguage = contactLanguage;
/*    */   }
/*    */   
/*    */   public String getContactLanguage() {
/* 34 */     return this.contactLanguage;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\DealershipContactInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */