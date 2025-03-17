/*     */ package com.eoos.gm.tis2web.sps.client.test.spstool.legacy;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class TestRequestMethodData
/*     */   implements RequestMethodData
/*     */ {
/*     */   private static final long serialVersionUID = 3257572819113030963L;
/*  11 */   protected int requestMethodID = 10;
/*  12 */   protected int deviceID = 11;
/*  13 */   protected int protocol = 12;
/*  14 */   protected int pinLink = 13;
/*  15 */   protected int readVIN = 14;
/*  16 */   protected int vinReadType = 15;
/*  17 */   protected String vinAddresses = "16";
/*  18 */   protected int readParts = 17;
/*  19 */   protected int partReadType = 18;
/*  20 */   protected String partAddresses = "19";
/*  21 */   protected String partFormat = "20";
/*  22 */   protected String partLength = "21";
/*  23 */   protected String partStartByte = "22";
/*  24 */   protected int readHWID = 23;
/*  25 */   protected int hwidReadType = 24;
/*  26 */   protected String hwidAddresses = "25";
/*  27 */   protected int hwidFormat = 26;
/*  28 */   protected int hwidLength = 27;
/*  29 */   protected int hwidStartByte = 28;
/*  30 */   protected int readSecuritySeed = 29;
/*  31 */   protected int specialReqType = 30;
/*  32 */   protected int readCVN = 31;
/*     */   
/*     */   public int getRequestMethodID() {
/*  35 */     return this.requestMethodID;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  39 */     return this.deviceID;
/*     */   }
/*     */   
/*     */   public int getProtocol() {
/*  43 */     return this.protocol;
/*     */   }
/*     */   
/*     */   public int getPINLink() {
/*  47 */     return this.pinLink;
/*     */   }
/*     */   
/*     */   public int getReadVIN() {
/*  51 */     return this.readVIN;
/*     */   }
/*     */   
/*     */   public int getVINReadType() {
/*  55 */     return this.vinReadType;
/*     */   }
/*     */   
/*     */   public String getVINAddresses() {
/*  59 */     return this.vinAddresses;
/*     */   }
/*     */   
/*     */   public int getReadParts() {
/*  63 */     return this.readParts;
/*     */   }
/*     */   
/*     */   public int getPartReadType() {
/*  67 */     return this.partReadType;
/*     */   }
/*     */   
/*     */   public String getPartAddresses() {
/*  71 */     return this.partAddresses;
/*     */   }
/*     */   
/*     */   public String getPartFormat() {
/*  75 */     return this.partFormat;
/*     */   }
/*     */   
/*     */   public String getPartLength() {
/*  79 */     return this.partLength;
/*     */   }
/*     */   
/*     */   public String getPartStartByte() {
/*  83 */     return this.partStartByte;
/*     */   }
/*     */   
/*     */   public int getReadHWID() {
/*  87 */     return this.readHWID;
/*     */   }
/*     */   
/*     */   public int getHWIDReadType() {
/*  91 */     return this.hwidReadType;
/*     */   }
/*     */   
/*     */   public String getHWIDAddresses() {
/*  95 */     return this.hwidAddresses;
/*     */   }
/*     */   
/*     */   public int getHWIDFormat() {
/*  99 */     return this.hwidFormat;
/*     */   }
/*     */   
/*     */   public int getHWIDLength() {
/* 103 */     return this.hwidLength;
/*     */   }
/*     */   
/*     */   public int getHWIDStartByte() {
/* 107 */     return this.hwidStartByte;
/*     */   }
/*     */   
/*     */   public int getReadSecuritySeed() {
/* 111 */     return this.readSecuritySeed;
/*     */   }
/*     */   
/*     */   public int getSpecialReqType() {
/* 115 */     return this.specialReqType;
/*     */   }
/*     */   
/*     */   public int getReadCVN() {
/* 119 */     return this.readCVN;
/*     */   }
/*     */   
/*     */   public Set getAttributes() {
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public Object getValue(Object attribute) {
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\spstool\legacy\TestRequestMethodData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */