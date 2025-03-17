/*     */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"validAttrList", "invalidAttrList", "invalidAttrCode"})
/*     */ @XmlRootElement(name = "invalidVehicleDescription")
/*     */ public class InvalidVehicleDescription
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected StringValueList validAttrList;
/*     */   @XmlElement(required = true)
/*     */   protected StringValueList invalidAttrList;
/*     */   protected int invalidAttrCode;
/*     */   
/*     */   public StringValueList getValidAttrList() {
/*  56 */     return this.validAttrList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidAttrList(StringValueList value) {
/*  68 */     this.validAttrList = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringValueList getInvalidAttrList() {
/*  80 */     return this.invalidAttrList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvalidAttrList(StringValueList value) {
/*  92 */     this.invalidAttrList = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInvalidAttrCode() {
/* 100 */     return this.invalidAttrCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvalidAttrCode(int value) {
/* 108 */     this.invalidAttrCode = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\InvalidVehicleDescription.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */