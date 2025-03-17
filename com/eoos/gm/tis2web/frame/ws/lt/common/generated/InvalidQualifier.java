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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "", propOrder = {"invalidAttrList", "invalidAttrCode"})
/*    */ @XmlRootElement(name = "invalidQualifier")
/*    */ public class InvalidQualifier
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected StringValueList invalidAttrList;
/*    */   protected int invalidAttrCode;
/*    */   
/*    */   public StringValueList getInvalidAttrList() {
/* 52 */     return this.invalidAttrList;
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
/*    */   public void setInvalidAttrList(StringValueList value) {
/* 64 */     this.invalidAttrList = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInvalidAttrCode() {
/* 72 */     return this.invalidAttrCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInvalidAttrCode(int value) {
/* 80 */     this.invalidAttrCode = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\InvalidQualifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */