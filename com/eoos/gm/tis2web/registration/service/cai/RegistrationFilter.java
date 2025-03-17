/*    */ package com.eoos.gm.tis2web.registration.service.cai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationFilter
/*    */ {
/*    */   private RegistrationAttribute attribute;
/*    */   private String value;
/*    */   private long fromTimeStamp;
/*    */   private long toTimeStamp;
/*    */   
/*    */   public RegistrationAttribute getAttribute() {
/* 14 */     return this.attribute;
/*    */   }
/*    */   
/*    */   public void setAttribute(RegistrationAttribute attribute) {
/* 18 */     this.attribute = attribute;
/*    */   }
/*    */   
/*    */   public long getFromTimeStamp() {
/* 22 */     return this.fromTimeStamp;
/*    */   }
/*    */   
/*    */   public void setFromTimeStamp(long fromTimeStamp) {
/* 26 */     this.fromTimeStamp = fromTimeStamp;
/*    */   }
/*    */   
/*    */   public long getToTimeStamp() {
/* 30 */     return this.toTimeStamp;
/*    */   }
/*    */   
/*    */   public void setToTimeStamp(long toTimeStamp) {
/* 34 */     this.toTimeStamp = toTimeStamp;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 38 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RegistrationFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */