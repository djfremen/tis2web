/*     */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest2;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import java.io.Serializable;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SSARequestResponseAdapter
/*     */   implements SSARequest2, SSAResponse, Serializable
/*     */ {
/*     */   protected static final long serialVersionUID = 1L;
/*     */   private SSAData ssaData;
/*     */   protected boolean incomplete = true;
/*     */   
/*     */   public SSARequestResponseAdapter(SSAData ssaData) {
/*  24 */     this.ssaData = ssaData;
/*     */   }
/*     */   
/*     */   public SSAData getSSAData() {
/*  28 */     return this.ssaData;
/*     */   }
/*     */   
/*     */   public List getVINs() {
/*  32 */     List<VIN> retValue = new LinkedList();
/*  33 */     for (int i = 0; i < (this.ssaData.getVINTuples()).length; i++) {
/*  34 */       SSAData.VINTuple vinTuple = this.ssaData.getVINTuples()[i];
/*  35 */       if (vinTuple != null) {
/*  36 */         retValue.add(vinTuple.getVIN());
/*     */       } else {
/*  38 */         retValue.add(null);
/*     */       } 
/*     */     } 
/*  41 */     return retValue;
/*     */   }
/*     */   
/*     */   public HardwareKey getHardwareKey() {
/*  45 */     return this.ssaData.getHardwareKey();
/*     */   }
/*     */   
/*     */   public SSAResponse createResponse(Integer dbVersion, Integer freeshot, List<SSAResponse.SecurityCodes> secCodes) {
/*  49 */     this.ssaData.setVersion(dbVersion);
/*  50 */     this.ssaData.setFreeShots(freeshot);
/*  51 */     boolean incomplete = false;
/*  52 */     for (int i = 0; i < secCodes.size(); i++) {
/*  53 */       SSAData.VINTuple vinTuple = this.ssaData.getVINTuples()[i];
/*  54 */       if (vinTuple != null) {
/*  55 */         SSAResponse.SecurityCodes codes = secCodes.get(i);
/*  56 */         if (codes == SSAResponse.SecurityCodes.NULL_CODES) {
/*  57 */           incomplete = true;
/*     */         }
/*  59 */         vinTuple.setImmobilizerSecurityCode(codes.getImmobilizerSecurityCode());
/*  60 */         vinTuple.setInfotainmentSecurityCode(codes.getInfotainmentSecurityCode());
/*     */       } 
/*     */     } 
/*  63 */     this.incomplete = incomplete;
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public Integer getDBVersion() {
/*  68 */     return this.ssaData.getVersion();
/*     */   }
/*     */   
/*     */   public Integer getFreeShots() {
/*  72 */     return this.ssaData.getFreeShots();
/*     */   }
/*     */   
/*     */   public SSAResponse.SecurityCodes getSecCodes(VIN vin) {
/*  76 */     for (int i = 0; i < (this.ssaData.getVINTuples()).length; i++) {
/*  77 */       final SSAData.VINTuple tuple = this.ssaData.getVINTuples()[i];
/*  78 */       if (tuple.getVIN() == vin) {
/*  79 */         return new SSAResponse.SecurityCodes() {
/*     */             public String getImmobilizerSecurityCode() {
/*  81 */               return tuple.getImmobilizerSecurityCode();
/*     */             }
/*     */             
/*     */             public String getInfotainmentSecurityCode() {
/*  85 */               return tuple.getInfotainmentSecurityCode();
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public SSARequest2 setHardwareKey(HardwareKey key) {
/*  95 */     this.ssaData.setHardwareKey(key);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public SecurityAccessRequest getRequest() {
/* 100 */     return (SecurityAccessRequest)this;
/*     */   }
/*     */   
/*     */   public boolean isIncomplete() {
/* 104 */     return this.incomplete;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 108 */     return "SSARequestResponseAdapter[" + String.valueOf(this.ssaData) + "]";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\ssadata\SSARequestResponseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */