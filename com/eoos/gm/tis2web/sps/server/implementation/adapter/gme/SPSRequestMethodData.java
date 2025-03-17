/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSRequestMethodData
/*     */   implements RequestMethodData
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int requestMethodID;
/*     */   protected int deviceID;
/*  18 */   protected Map data = new HashMap<Object, Object>();
/*     */   
/*     */   public int getRequestMethodID() {
/*  21 */     return this.requestMethodID;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  25 */     return this.deviceID;
/*     */   }
/*     */   
/*     */   public int getProtocol() {
/*  29 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getPINLink() {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getReadVIN() {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getVINReadType() {
/*  41 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getVINAddresses() {
/*  45 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getReadParts() {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getPartReadType() {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPartAddresses() {
/*  57 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPartFormat() {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPartLength() {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPartStartByte() {
/*  69 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getReadHWID() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getHWIDReadType() {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getHWIDAddresses() {
/*  81 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getHWIDFormat() {
/*  85 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getHWIDLength() {
/*  89 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getHWIDStartByte() {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getReadSecuritySeed() {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getSpecialReqType() {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getReadCVN() {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set getAttributes() {
/* 109 */     return this.data.keySet();
/*     */   }
/*     */   
/*     */   public Object getValue(Object attribute) {
/* 113 */     return this.data.get(attribute);
/*     */   }
/*     */   
/*     */   public SPSRequestMethodData(Integer rmg, Integer system) throws Exception {
/* 117 */     this.requestMethodID = rmg.intValue();
/* 118 */     this.data.put("RequestMethodID".toLowerCase(Locale.ENGLISH), rmg);
/* 119 */     this.deviceID = system.intValue();
/* 120 */     this.data.put("DeviceID".toLowerCase(Locale.ENGLISH), system);
/*     */   }
/*     */   
/*     */   public void setDeviceList(List devices) {
/* 124 */     this.data.put("DeviceIDs".toLowerCase(Locale.ENGLISH), devices);
/*     */   }
/*     */   
/*     */   public SPSRequestMethodData(Integer system) throws Exception {
/* 128 */     this.requestMethodID = -1;
/* 129 */     this.deviceID = system.intValue();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSRequestMethodData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */