/*     */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
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
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"vehDesc", "modelDesignator", "vehicleNumber", "rpoList"})
/*     */ @XmlRootElement(name = "vehDetails")
/*     */ public class VehDetails
/*     */ {
/*     */   @XmlElement(name = "veh_desc", required = true)
/*     */   protected VehDesc vehDesc;
/*     */   @XmlElement(name = "model_designator")
/*     */   protected String modelDesignator;
/*     */   @XmlElement(name = "vehicle_number")
/*     */   protected String vehicleNumber;
/*     */   @XmlElement(name = "rpo_list")
/*     */   protected RpoList rpoList;
/*     */   
/*     */   public VehDesc getVehDesc() {
/*  61 */     return this.vehDesc;
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
/*  73 */     this.vehDesc = value;
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
/*     */   public String getModelDesignator() {
/*  85 */     return this.modelDesignator;
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
/*     */   public void setModelDesignator(String value) {
/*  97 */     this.modelDesignator = value;
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
/*     */   public String getVehicleNumber() {
/* 109 */     return this.vehicleNumber;
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
/*     */   public void setVehicleNumber(String value) {
/* 121 */     this.vehicleNumber = value;
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
/*     */   public RpoList getRpoList() {
/* 133 */     return this.rpoList;
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
/*     */   public void setRpoList(RpoList value) {
/* 145 */     this.rpoList = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\VehDetails.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */