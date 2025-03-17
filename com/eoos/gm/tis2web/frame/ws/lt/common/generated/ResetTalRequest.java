/*    */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
/*    */ 
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "", propOrder = {"reqId"})
/*    */ @XmlRootElement(name = "resetTalRequest")
/*    */ public class ResetTalRequest
/*    */   extends BaseRequest
/*    */ {
/*    */   @XmlElement(name = "req_id", required = true)
/*    */   protected String reqId;
/*    */   
/*    */   public String getReqId() {
/* 51 */     return this.reqId;
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
/*    */   public void setReqId(String value) {
/* 63 */     this.reqId = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\ResetTalRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */