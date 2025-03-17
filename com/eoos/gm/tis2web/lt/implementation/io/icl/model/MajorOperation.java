/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class MajorOperation
/*     */ {
/*     */   private Integer ID;
/*     */   private Map descriptions;
/*     */   private Integer serviceType;
/*     */   private Integer driverType;
/*     */   private String majorOpNr;
/*     */   
/*     */   public MajorOperation(Integer ID, Integer serviceType, Integer driverType, String majorOpNr) {
/*  25 */     this.ID = ID;
/*  26 */     this.descriptions = new HashMap<Object, Object>();
/*  27 */     this.serviceType = serviceType;
/*  28 */     this.driverType = driverType;
/*  29 */     this.majorOpNr = majorOpNr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getID() {
/*  38 */     return this.ID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setID(Integer ID) {
/*  48 */     this.ID = ID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription(int iLanID) {
/*  57 */     return (String)this.descriptions.get(Integer.valueOf(iLanID));
/*     */   }
/*     */   
/*     */   public String getDescription(Integer iLanID) {
/*  61 */     return (String)this.descriptions.get(iLanID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(int iLanID, String description) {
/*  71 */     this.descriptions.put(Integer.valueOf(iLanID), description);
/*     */   }
/*     */   
/*     */   public void setDescription(Integer iLanID, String description) {
/*  75 */     this.descriptions.put(iLanID, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getServiceType() {
/*  84 */     return this.serviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceType(Integer serviceType) {
/*  94 */     this.serviceType = serviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getDriverType() {
/* 103 */     return this.driverType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDriverType(Integer driverType) {
/* 113 */     this.driverType = driverType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMajorOpNr() {
/* 122 */     return this.majorOpNr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMajorOpNr(String majorOpNr) {
/* 132 */     this.majorOpNr = majorOpNr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\MajorOperation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */