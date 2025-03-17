/*    */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "baseRequest")
/*    */ @XmlSeeAlso({QualifierValuesRequest.class, GuiRequest.class, SetVehDescRequest.class, GetVehRequest.class, LogoutRequest.class, ResetTalRequest.class, IclRequest.class, TalRequest.class, VehValRequest.class, OpNumValRequest.class, OpRequest.class, QualifierValidateRequest.class})
/*    */ public class BaseRequest
/*    */ {
/*    */   @XmlAttribute
/*    */   protected String uid;
/*    */   
/*    */   public String getUid() {
/* 58 */     if (this.uid == null) {
/* 59 */       return "defaultID";
/*    */     }
/* 61 */     return this.uid;
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
/* 74 */     this.uid = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\BaseRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */