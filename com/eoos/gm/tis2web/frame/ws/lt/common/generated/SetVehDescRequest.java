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
/*     */ @XmlType(name = "", propOrder = {"reqId", "qualifier", "vehDesc"})
/*     */ @XmlRootElement(name = "setVehDescRequest")
/*     */ public class SetVehDescRequest
/*     */   extends BaseRequest
/*     */ {
/*     */   @XmlElement(name = "req_id", required = true)
/*     */   protected String reqId;
/*     */   @XmlElement(required = true)
/*     */   protected Qualifier qualifier;
/*     */   @XmlElement(name = "veh_desc", required = true)
/*     */   protected VehDesc vehDesc;
/*     */   
/*     */   public String getReqId() {
/*  59 */     return this.reqId;
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
/*     */   public void setReqId(String value) {
/*  71 */     this.reqId = value;
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
/*  83 */     return this.qualifier;
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
/*  95 */     this.qualifier = value;
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
/* 107 */     return this.vehDesc;
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
/* 119 */     this.vehDesc = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\SetVehDescRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */