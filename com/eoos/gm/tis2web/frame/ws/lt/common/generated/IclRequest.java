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
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"qualifier", "vehDesc", "opNumber"})
/*     */ @XmlRootElement(name = "iclRequest")
/*     */ public class IclRequest
/*     */   extends BaseRequest
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected Qualifier qualifier;
/*     */   @XmlElement(name = "veh_desc", required = true)
/*     */   protected VehDesc vehDesc;
/*     */   @XmlElement(required = true)
/*     */   protected String opNumber;
/*     */   
/*     */   public Qualifier getQualifier() {
/*  59 */     return this.qualifier;
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
/*  71 */     this.qualifier = value;
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
/*  83 */     return this.vehDesc;
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
/*  95 */     this.vehDesc = value;
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
/*     */   public String getOpNumber() {
/* 107 */     return this.opNumber;
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
/* 119 */     this.opNumber = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\IclRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */