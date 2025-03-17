/*    */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "", propOrder = {"locale", "familyId"})
/*    */ @XmlRootElement(name = "getFamilyDetails")
/*    */ public class GetFamilyDetails
/*    */   extends BaseRequest
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected String locale;
/*    */   @XmlElement(name = "family_id", required = true)
/*    */   protected List<String> familyId;
/*    */   
/*    */   public String getLocale() {
/* 57 */     return this.locale;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocale(String value) {
/* 69 */     this.locale = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getFamilyId() {
/* 95 */     if (this.familyId == null) {
/* 96 */       this.familyId = new ArrayList<String>();
/*    */     }
/* 98 */     return this.familyId;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\GetFamilyDetails.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */