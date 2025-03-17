/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class SPSRequestMethodData
/*     */   implements RequestMethodData
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int requestMethodID;
/*     */   protected int deviceID;
/*     */   protected int protocol;
/*     */   protected int pinLink;
/*     */   protected int readVIN;
/*     */   protected int vinReadType;
/*     */   protected String vinAddresses;
/*     */   protected int readParts;
/*     */   protected int partReadType;
/*     */   protected String partAddresses;
/*     */   protected String partFormat;
/*     */   protected String partLength;
/*     */   protected String partStartByte;
/*     */   protected int readHWID;
/*     */   protected int hwidReadType;
/*     */   protected String hwidAddresses;
/*     */   protected int hwidFormat;
/*     */   protected int hwidLength;
/*     */   protected int hwidStartByte;
/*     */   protected int readSecuritySeed;
/*     */   protected int specialReqType;
/*     */   protected int readCVN;
/*  59 */   protected Map data = new HashMap<Object, Object>();
/*     */   
/*     */   public int getRequestMethodID() {
/*  62 */     return this.requestMethodID;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  66 */     return this.deviceID;
/*     */   }
/*     */   
/*     */   public int getProtocol() {
/*  70 */     return this.protocol;
/*     */   }
/*     */   
/*     */   public int getPINLink() {
/*  74 */     return this.pinLink;
/*     */   }
/*     */   
/*     */   public int getReadVIN() {
/*  78 */     return this.readVIN;
/*     */   }
/*     */   
/*     */   public int getVINReadType() {
/*  82 */     return this.vinReadType;
/*     */   }
/*     */   
/*     */   public String getVINAddresses() {
/*  86 */     return this.vinAddresses;
/*     */   }
/*     */   
/*     */   public int getReadParts() {
/*  90 */     return this.readParts;
/*     */   }
/*     */   
/*     */   public int getPartReadType() {
/*  94 */     return this.partReadType;
/*     */   }
/*     */   
/*     */   public String getPartAddresses() {
/*  98 */     return this.partAddresses;
/*     */   }
/*     */   
/*     */   public String getPartFormat() {
/* 102 */     return this.partFormat;
/*     */   }
/*     */   
/*     */   public String getPartLength() {
/* 106 */     return this.partLength;
/*     */   }
/*     */   
/*     */   public String getPartStartByte() {
/* 110 */     return this.partStartByte;
/*     */   }
/*     */   
/*     */   public int getReadHWID() {
/* 114 */     return this.readHWID;
/*     */   }
/*     */   
/*     */   public int getHWIDReadType() {
/* 118 */     return this.hwidReadType;
/*     */   }
/*     */   
/*     */   public String getHWIDAddresses() {
/* 122 */     return this.hwidAddresses;
/*     */   }
/*     */   
/*     */   public int getHWIDFormat() {
/* 126 */     return this.hwidFormat;
/*     */   }
/*     */   
/*     */   public int getHWIDLength() {
/* 130 */     return this.hwidLength;
/*     */   }
/*     */   
/*     */   public int getHWIDStartByte() {
/* 134 */     return this.hwidStartByte;
/*     */   }
/*     */   
/*     */   public int getReadSecuritySeed() {
/* 138 */     return this.readSecuritySeed;
/*     */   }
/*     */   
/*     */   public int getSpecialReqType() {
/* 142 */     return this.specialReqType;
/*     */   }
/*     */   
/*     */   public int getReadCVN() {
/* 146 */     return this.readCVN;
/*     */   }
/*     */   
/*     */   public Set getAttributes() {
/* 150 */     return this.data.keySet();
/*     */   }
/*     */   
/*     */   public Object getValue(Object attribute) {
/* 154 */     return this.data.get(attribute);
/*     */   }
/*     */   
/*     */   public SPSRequestMethodData(int deviceID) throws Exception {
/* 158 */     this.requestMethodID = -1;
/* 159 */     this.deviceID = deviceID;
/*     */   }
/*     */   
/*     */   public SPSRequestMethodData(ResultSet rs) throws Exception {
/* 163 */     this.requestMethodID = rs.getInt(1);
/* 164 */     this.deviceID = rs.getInt(2);
/* 165 */     this.protocol = rs.getInt(3);
/* 166 */     this.pinLink = rs.getInt(4);
/* 167 */     this.readVIN = rs.getInt(5);
/* 168 */     this.vinReadType = rs.getInt(6);
/* 169 */     this.vinAddresses = rs.getString(7);
/* 170 */     this.readParts = rs.getInt(8);
/* 171 */     this.partReadType = rs.getInt(9);
/* 172 */     this.partAddresses = rs.getString(10);
/* 173 */     this.partFormat = rs.getString(11);
/* 174 */     this.partLength = rs.getString(12);
/* 175 */     this.partStartByte = rs.getString(13);
/* 176 */     this.readHWID = rs.getInt(14);
/* 177 */     this.hwidReadType = rs.getInt(15);
/* 178 */     this.hwidAddresses = rs.getString(16);
/* 179 */     this.hwidFormat = rs.getInt(17);
/* 180 */     this.hwidLength = rs.getInt(18);
/* 181 */     this.hwidStartByte = rs.getInt(19);
/* 182 */     this.readSecuritySeed = rs.getInt(20);
/* 183 */     this.specialReqType = rs.getInt(21);
/* 184 */     this.readCVN = rs.getInt(22);
/* 185 */     ResultSetMetaData meta = rs.getMetaData();
/* 186 */     for (int i = 1; i <= 21; i++)
/* 187 */       this.data.put(meta.getColumnName(i).toLowerCase(Locale.ENGLISH), rs.getObject(i)); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSRequestMethodData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */