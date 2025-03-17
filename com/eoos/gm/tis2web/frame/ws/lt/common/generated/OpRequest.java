/*     */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
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
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"opNumber", "qualifier", "vehDesc"})
/*     */ @XmlRootElement(name = "opRequest")
/*     */ public class OpRequest
/*     */   extends BaseRequest
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected String opNumber;
/*     */   @XmlElement(required = true)
/*     */   protected Qualifier qualifier;
/*     */   @XmlElement(name = "veh_desc", required = true)
/*     */   protected VehDesc vehDesc;
/*     */   @XmlAttribute
/*     */   protected Boolean enforceHours;
/*     */   
/*     */   public String getOpNumber() {
/*  63 */     return this.opNumber;
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
/*     */   public void setOpNumber(String value) {
/*  75 */     this.opNumber = value;
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
/*     */   public Qualifier getQualifier() {
/*  87 */     return this.qualifier;
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
/*     */   public void setQualifier(Qualifier value) {
/*  99 */     this.qualifier = value;
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
/*     */   public VehDesc getVehDesc() {
/* 111 */     return this.vehDesc;
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
/*     */   public void setVehDesc(VehDesc value) {
/* 123 */     this.vehDesc = value;
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
/*     */   public boolean isEnforceHours() {
/* 135 */     if (this.enforceHours == null) {
/* 136 */       return false;
/*     */     }
/* 138 */     return this.enforceHours.booleanValue();
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
/*     */   
/*     */   public void setEnforceHours(Boolean value) {
/* 151 */     this.enforceHours = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\OpRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */