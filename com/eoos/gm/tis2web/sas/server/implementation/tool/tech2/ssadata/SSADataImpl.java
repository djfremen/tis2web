/*     */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SSADataImpl
/*     */   implements SSAData, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static class VINTupleImpl
/*     */     implements SSAData.VINTuple, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*  16 */     private VIN vin = null;
/*     */     
/*  18 */     private String immoSecCode = null;
/*     */     
/*  20 */     private String infoSecCode = null;
/*     */     
/*     */     public VINTupleImpl() {
/*  23 */       this.vin = null;
/*  24 */       this.immoSecCode = null;
/*  25 */       this.infoSecCode = null;
/*     */     }
/*     */     
/*     */     public VIN getVIN() {
/*  29 */       return this.vin;
/*     */     }
/*     */     
/*     */     public void setVIN(VIN vin) {
/*  33 */       this.vin = vin;
/*     */     }
/*     */     
/*     */     public String getImmobilizerSecurityCode() {
/*  37 */       return this.immoSecCode;
/*     */     }
/*     */     
/*     */     public void setImmobilizerSecurityCode(String secCodeImmo) {
/*  41 */       this.immoSecCode = secCodeImmo;
/*     */     }
/*     */     
/*     */     public String getInfotainmentSecurityCode() {
/*  45 */       return this.infoSecCode;
/*     */     }
/*     */     
/*     */     public void setInfotainmentSecurityCode(String secCodeInfo) {
/*  49 */       this.infoSecCode = secCodeInfo;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SeedTupleImpl
/*     */     implements SSAData.SeedTuple, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*  56 */     private Integer seedStatus = null;
/*     */     
/*  58 */     private Integer algorithm = null;
/*     */     
/*  60 */     private Integer seed = null;
/*     */     
/*  62 */     private Integer key = null;
/*     */     
/*     */     public SeedTupleImpl() {
/*  65 */       this.seedStatus = null;
/*  66 */       this.algorithm = null;
/*  67 */       this.seed = null;
/*  68 */       this.key = null;
/*     */     }
/*     */     
/*     */     public void setSeedStatus(Integer seedStatus) {
/*  72 */       this.seedStatus = seedStatus;
/*     */     }
/*     */     
/*     */     public Integer getSeedStatus() {
/*  76 */       return this.seedStatus;
/*     */     }
/*     */     
/*     */     public void setAlgorithm(Integer algorithm) {
/*  80 */       this.algorithm = algorithm;
/*     */     }
/*     */     
/*     */     public Integer getAlgorithm() {
/*  84 */       return this.algorithm;
/*     */     }
/*     */     
/*     */     public void setSeed(Integer seed) {
/*  88 */       this.seed = seed;
/*     */     }
/*     */     
/*     */     public Integer getSeed() {
/*  92 */       return this.seed;
/*     */     }
/*     */     
/*     */     public void setKey(Integer key) {
/*  96 */       this.key = key;
/*     */     }
/*     */     
/*     */     public Integer getKey() {
/* 100 */       return this.key;
/*     */     }
/*     */   }
/*     */   
/* 104 */   private Byte idByte = null;
/*     */   
/* 106 */   private Byte status = null;
/*     */   
/* 108 */   private HardwareKey hwk = null;
/*     */   
/* 110 */   private Integer version = null;
/*     */   
/* 112 */   private Integer freeShots = null;
/*     */   
/* 114 */   private Integer hardwareGroupID = null;
/*     */   
/* 116 */   private SSAData.VINTuple[] vinTuples = null;
/*     */   
/* 118 */   private SSAData.SeedTuple[] seedTuples = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIDByte(Byte idByte) {
/* 125 */     this.idByte = idByte;
/*     */   }
/*     */   
/*     */   public Byte getIDByte() {
/* 129 */     return this.idByte;
/*     */   }
/*     */   
/*     */   public void setStatus(Byte status) {
/* 133 */     this.status = status;
/*     */   }
/*     */   
/*     */   public Byte getStatus() {
/* 137 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setHardwareKey(HardwareKey hwk) {
/* 141 */     this.hwk = hwk;
/*     */   }
/*     */   
/*     */   public HardwareKey getHardwareKey() {
/* 145 */     return this.hwk;
/*     */   }
/*     */   
/*     */   public void setVersion(Integer version) {
/* 149 */     this.version = version;
/*     */   }
/*     */   
/*     */   public Integer getVersion() {
/* 153 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setFreeShots(Integer freeShots) {
/* 157 */     this.freeShots = freeShots;
/*     */   }
/*     */   
/*     */   public Integer getFreeShots() {
/* 161 */     return this.freeShots;
/*     */   }
/*     */   
/*     */   public void setHardwareGroupID(Integer hardwareGroupID) {
/* 165 */     this.hardwareGroupID = hardwareGroupID;
/*     */   }
/*     */   
/*     */   public Integer getHardwareGroupID() {
/* 169 */     return this.hardwareGroupID;
/*     */   }
/*     */   
/*     */   public void setVINTuples(SSAData.VINTuple[] vinTuples) {
/* 173 */     this.vinTuples = vinTuples;
/*     */   }
/*     */   
/*     */   public SSAData.VINTuple[] getVINTuples() {
/* 177 */     return this.vinTuples;
/*     */   }
/*     */   
/*     */   public void setSeedTuples(SSAData.SeedTuple[] seedTuples) {
/* 181 */     this.seedTuples = seedTuples;
/*     */   }
/*     */   
/*     */   public SSAData.SeedTuple[] getSeedTuples() {
/* 185 */     return this.seedTuples;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 189 */     StringBuffer retValue = new StringBuffer("SSADataImpl[\n\rid: {ID}\n\rstatus: {STATUS}\n\rhwkid: {HWKID}\n\rversion: {VERSION}\n\rfreeshot: {FREESHOT}\n\rhwkgrp: {HWKGRP}\n\r\n\r{VINTUPLES}\n\r\n\r{SKTUPLES}");
/* 190 */     StringUtilities.replace(retValue, "{ID}", toHexString(this.idByte));
/* 191 */     StringUtilities.replace(retValue, "{STATUS}", toHexString(this.status));
/* 192 */     StringUtilities.replace(retValue, "{HWKID}", String.valueOf(this.hwk));
/* 193 */     StringUtilities.replace(retValue, "{VERSION}", String.valueOf(this.version));
/* 194 */     StringUtilities.replace(retValue, "{FREESHOT}", toHexString(this.freeShots));
/* 195 */     StringUtilities.replace(retValue, "{HWKGRP}", String.valueOf(this.hardwareGroupID));
/*     */     int i;
/* 197 */     for (i = 0; i < this.vinTuples.length; i++) {
/* 198 */       String tupleName = "Tuple" + i;
/* 199 */       StringBuffer tmp = new StringBuffer(tupleName + " vin: {VIN}\n\r" + tupleName + " SCImmo: {SCImmo}\n\r" + tupleName + " SCInfo: {SCInfo}\n\r");
/* 200 */       VINTupleImpl tuple = (VINTupleImpl)this.vinTuples[i];
/* 201 */       if (tuple != null) {
/* 202 */         StringUtilities.replace(tmp, "{VIN}", String.valueOf(tuple.vin));
/* 203 */         StringUtilities.replace(tmp, "{SCImmo}", String.valueOf(tuple.immoSecCode));
/* 204 */         StringUtilities.replace(tmp, "{SCInfo}", String.valueOf(tuple.infoSecCode));
/*     */         
/* 206 */         StringUtilities.replace(retValue, "{VINTUPLES}", tmp.toString() + "{VINTUPLES}");
/*     */       } 
/*     */     } 
/* 209 */     StringUtilities.replace(retValue, "{VINTUPLES}", "");
/*     */     
/* 211 */     for (i = 0; i < this.seedTuples.length; i++) {
/* 212 */       String tupleName = "SK" + i;
/* 213 */       StringBuffer tmp = new StringBuffer(tupleName + " status: {STATUS}\n" + tupleName + " algo: {ALGO}\n\r" + tupleName + " seed: {SEED}\n\r" + tupleName + " key: {KEY}\n\r");
/* 214 */       SeedTupleImpl tuple = (SeedTupleImpl)this.seedTuples[i];
/* 215 */       if (tuple != null) {
/* 216 */         StringUtilities.replace(tmp, "{STATUS}", toHexString(tuple.seedStatus));
/* 217 */         StringUtilities.replace(tmp, "{ALGO}", toHexString(tuple.algorithm));
/* 218 */         StringUtilities.replace(tmp, "{SEED}", toHexString(tuple.seed));
/* 219 */         StringUtilities.replace(tmp, "{KEY}", toHexString(tuple.key));
/*     */         
/* 221 */         StringUtilities.replace(retValue, "{SKTUPLES}", tmp.toString() + "{SKTUPLES}");
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     StringUtilities.replace(retValue, "{SKTUPLES}", "");
/* 226 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   private String toHexString(Integer integer) {
/* 230 */     if (integer == null) {
/* 231 */       return "null";
/*     */     }
/* 233 */     return "0x" + Integer.toHexString(integer.intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private String toHexString(Byte b) {
/* 238 */     if (b == null) {
/* 239 */       return "null";
/*     */     }
/* 241 */     return "0x" + Integer.toHexString(b.intValue());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\ssadata\SSADataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */