/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import java.sql.ResultSet;
/*     */ 
/*     */ public class SPSControllerData
/*     */ {
/*     */   protected int controllerID;
/*     */   protected String j2534;
/*     */   protected String preRPOCode;
/*     */   protected String postRPOCode;
/*     */   protected int deviceID;
/*     */   protected int requestMethodID;
/*     */   protected int programmingMethods;
/*     */   protected int vci;
/*     */   protected String preProgrammingInstructions;
/*     */   protected String postProgrammingInstructions;
/*     */   protected int VINPos;
/*     */   protected char VINValue;
/*     */   protected char VINPos4or6;
/*     */   protected String VINBeginningSequence;
/*     */   protected String VINEndingSequence;
/*     */   protected String afterMarketFlag;
/*     */   protected int replaceInstruction;
/*     */   protected int replacer;
/*     */   protected int descriptionIndex;
/*     */   protected String securityID;
/*     */   protected String engine;
/*     */   protected String series;
/*     */   protected String line;
/*     */   protected String suppressSequenceController;
/*     */   protected boolean securityCodeRequired;
/*     */   
/*     */   public String getAfterMarketFlag() {
/*  35 */     return this.afterMarketFlag;
/*     */   }
/*     */   
/*     */   public int getControllerID() {
/*  39 */     return this.controllerID;
/*     */   }
/*     */   
/*     */   public int getDescriptionIndex() {
/*  43 */     return this.descriptionIndex;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  47 */     return this.deviceID;
/*     */   }
/*     */   
/*     */   public String getJ2534() {
/*  51 */     return this.j2534;
/*     */   }
/*     */   
/*     */   public String getPostProgrammingInstructions() {
/*  55 */     return this.postProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public String getPostRPOCode() {
/*  59 */     return this.postRPOCode;
/*     */   }
/*     */   
/*     */   public String getPreProgrammingInstructions() {
/*  63 */     return this.preProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public String getPreRPOCode() {
/*  67 */     return this.preRPOCode;
/*     */   }
/*     */   
/*     */   public int getProgrammingMethods() {
/*  71 */     return this.programmingMethods;
/*     */   }
/*     */   
/*     */   public int getReplaceInstruction() {
/*  75 */     return this.replaceInstruction;
/*     */   }
/*     */   
/*     */   public int getReplacer() {
/*  79 */     return this.replacer;
/*     */   }
/*     */   
/*     */   public int getRequestMethodID() {
/*  83 */     return this.requestMethodID;
/*     */   }
/*     */   
/*     */   public int getVCI() {
/*  87 */     return this.vci;
/*     */   }
/*     */   
/*     */   public int getProgrammingSequenceID() {
/*  91 */     return this.vci;
/*     */   }
/*     */   
/*     */   public String getVINBeginningSequence() {
/*  95 */     return this.VINBeginningSequence;
/*     */   }
/*     */   
/*     */   public String getVINEndingSequence() {
/*  99 */     return this.VINEndingSequence;
/*     */   }
/*     */   
/*     */   public int getVINPos() {
/* 103 */     return this.VINPos;
/*     */   }
/*     */   
/*     */   public char getVINPos4or6() {
/* 107 */     return this.VINPos4or6;
/*     */   }
/*     */   
/*     */   public char getVINValue() {
/* 111 */     return this.VINValue;
/*     */   }
/*     */   
/*     */   public String getSecurityID() {
/* 115 */     return this.securityID;
/*     */   }
/*     */   
/*     */   public String getEngine() {
/* 119 */     return this.engine;
/*     */   }
/*     */   
/*     */   public String getLine() {
/* 123 */     return this.line;
/*     */   }
/*     */   
/*     */   public String getSeries() {
/* 127 */     return this.series;
/*     */   }
/*     */   
/*     */   public String getSuppressSequenceControllerFlag() {
/* 131 */     return this.suppressSequenceController;
/*     */   }
/*     */   
/*     */   public boolean isSecurityCodeRequired() {
/* 135 */     return this.securityCodeRequired;
/*     */   }
/*     */   
/*     */   public String getControllerIdentity() {
/* 139 */     return this.controllerID + ":" + this.j2534 + ":" + this.preRPOCode + ":" + this.postRPOCode;
/*     */   }
/*     */   
/*     */   public SPSControllerData(IDatabaseLink db, ResultSet rs) throws Exception {
/* 143 */     this.controllerID = rs.getInt(1);
/* 144 */     this.j2534 = rs.getString(2).trim();
/* 145 */     this.preRPOCode = rs.getString(3).trim();
/* 146 */     this.postRPOCode = rs.getString(4).trim();
/* 147 */     this.deviceID = rs.getInt(5);
/* 148 */     this.requestMethodID = rs.getInt(6);
/* 149 */     this.programmingMethods = rs.getInt(7);
/* 150 */     this.vci = rs.getInt(8);
/* 151 */     this.preProgrammingInstructions = rs.getString(9);
/* 152 */     this.postProgrammingInstructions = rs.getString(10);
/* 153 */     this.VINPos = rs.getInt(11);
/* 154 */     this.VINValue = rs.getString(12).charAt(0);
/* 155 */     this.VINPos4or6 = rs.getString(13).charAt(0);
/* 156 */     this.VINBeginningSequence = rs.getString(14).trim();
/* 157 */     this.VINEndingSequence = rs.getString(15).trim();
/* 158 */     if (db.getDBMS() == 3) {
/* 159 */       this.afterMarketFlag = rs.getString("After_Market");
/*     */     }
/*     */     try {
/* 162 */       this.replaceInstruction = rs.getInt("REPLACE_MESSAGE_ID");
/* 163 */       this.replacer = rs.getInt("REPLACE_ID");
/* 164 */     } catch (Exception x) {}
/*     */     
/* 166 */     this.descriptionIndex = rs.getInt("Desc_Indx");
/* 167 */     this.securityID = rs.getString("Sec_Id");
/* 168 */     this.engine = rs.getString("Engine").trim();
/* 169 */     this.series = rs.getString("Series").trim();
/* 170 */     this.line = rs.getString("Line").trim();
/*     */     try {
/* 172 */       this.suppressSequenceController = rs.getString("Suppress_Sequence_Controller").trim();
/* 173 */     } catch (Exception x) {}
/*     */     
/*     */     try {
/* 176 */       this.securityCodeRequired = rs.getBoolean("SecCode_Required");
/* 177 */     } catch (Exception x) {}
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */