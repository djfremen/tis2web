/*    */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
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
/*    */ @XmlType(name = "base_request")
/*    */ @XmlSeeAlso({GetFamilyDetails.class, GetLocales.class, ResolveVin.class, GetDocument.class, GetMakes.class})
/*    */ public class BaseRequest
/*    */ {
/*    */   @XmlAttribute
/*    */   protected String uid;
/*    */   @XmlAttribute
/*    */   protected String token;
/*    */   
/*    */   public String getUid() {
/* 54 */     if (this.uid == null) {
/* 55 */       return "defaultID";
/*    */     }
/* 57 */     return this.uid;
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
/*    */   public void setUid(String value) {
/* 70 */     this.uid = value;
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
/*    */   public String getToken() {
/* 82 */     return this.token;
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
/*    */   public void setToken(String value) {
/* 94 */     this.token = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\BaseRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */