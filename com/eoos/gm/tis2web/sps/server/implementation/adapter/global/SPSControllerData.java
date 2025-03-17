/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
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
/*     */   protected int optionListID;
/*     */   
/*     */   public String getAfterMarketFlag() {
/*  36 */     return this.afterMarketFlag;
/*     */   }
/*     */   
/*     */   public int getControllerID() {
/*  40 */     return this.controllerID;
/*     */   }
/*     */   
/*     */   public int getDescriptionIndex() {
/*  44 */     return this.descriptionIndex;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  48 */     return this.deviceID;
/*     */   }
/*     */   
/*     */   public String getJ2534() {
/*  52 */     return this.j2534;
/*     */   }
/*     */   
/*     */   public String getPostProgrammingInstructions() {
/*  56 */     return this.postProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public String getPostRPOCode() {
/*  60 */     return this.postRPOCode;
/*     */   }
/*     */   
/*     */   public String getPreProgrammingInstructions() {
/*  64 */     return this.preProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public String getPreRPOCode() {
/*  68 */     return this.preRPOCode;
/*     */   }
/*     */   
/*     */   public int getProgrammingMethods() {
/*  72 */     return this.programmingMethods;
/*     */   }
/*     */   
/*     */   public int getReplaceInstruction() {
/*  76 */     return this.replaceInstruction;
/*     */   }
/*     */   
/*     */   public int getReplacer() {
/*  80 */     return this.replacer;
/*     */   }
/*     */   
/*     */   public int getRequestMethodID() {
/*  84 */     return this.requestMethodID;
/*     */   }
/*     */   
/*     */   public int getVCI() {
/*  88 */     return this.vci;
/*     */   }
/*     */   
/*     */   public int getProgrammingSequenceID() {
/*  92 */     return this.vci;
/*     */   }
/*     */   
/*     */   public String getVINBeginningSequence() {
/*  96 */     return this.VINBeginningSequence;
/*     */   }
/*     */   
/*     */   public String getVINEndingSequence() {
/* 100 */     return this.VINEndingSequence;
/*     */   }
/*     */   
/*     */   public int getVINPos() {
/* 104 */     return this.VINPos;
/*     */   }
/*     */   
/*     */   public char getVINPos4or6() {
/* 108 */     return this.VINPos4or6;
/*     */   }
/*     */   
/*     */   public char getVINValue() {
/* 112 */     return this.VINValue;
/*     */   }
/*     */   
/*     */   public String getSecurityID() {
/* 116 */     return this.securityID;
/*     */   }
/*     */   
/*     */   public String getEngine() {
/* 120 */     return this.engine;
/*     */   }
/*     */   
/*     */   public String getLine() {
/* 124 */     return this.line;
/*     */   }
/*     */   
/*     */   public String getSeries() {
/* 128 */     return this.series;
/*     */   }
/*     */   
/*     */   public String getSuppressSequenceControllerFlag() {
/* 132 */     return this.suppressSequenceController;
/*     */   }
/*     */   
/*     */   public boolean isSecurityCodeRequired() {
/* 136 */     return this.securityCodeRequired;
/*     */   }
/*     */   
/*     */   public int getOptionListID() {
/* 140 */     return this.optionListID;
/*     */   }
/*     */   
/*     */   public String getControllerIdentity() {
/* 144 */     return this.controllerID + ":" + this.j2534 + ":" + this.preRPOCode + ":" + this.postRPOCode;
/*     */   }
/*     */   
/*     */   public SPSControllerData(IDatabaseLink db, ResultSet rs) throws Exception {
/* 148 */     this.controllerID = rs.getInt(1);
/* 149 */     this.j2534 = rs.getString(2).trim();
/* 150 */     this.preRPOCode = rs.getString(3).trim();
/* 151 */     this.postRPOCode = rs.getString(4).trim();
/* 152 */     this.deviceID = rs.getInt(5);
/* 153 */     this.requestMethodID = rs.getInt(6);
/* 154 */     this.programmingMethods = rs.getInt(7);
/* 155 */     this.vci = rs.getInt(8);
/* 156 */     this.preProgrammingInstructions = rs.getString(9);
/* 157 */     this.postProgrammingInstructions = rs.getString(10);
/* 158 */     this.VINPos = rs.getInt(11);
/* 159 */     this.VINValue = rs.getString(12).charAt(0);
/* 160 */     this.VINPos4or6 = rs.getString(13).charAt(0);
/* 161 */     this.VINBeginningSequence = rs.getString(14).trim();
/* 162 */     this.VINEndingSequence = rs.getString(15).trim();
/* 163 */     if (db.getDBMS() == 3) {
/* 164 */       this.afterMarketFlag = rs.getString("After_Market");
/*     */     }
/*     */     try {
/* 167 */       this.replaceInstruction = rs.getInt("REPLACE_MESSAGE_ID");
/* 168 */       this.replacer = rs.getInt("REPLACE_ID");
/* 169 */     } catch (Exception x) {}
/*     */     
/* 171 */     this.descriptionIndex = rs.getInt("Desc_Indx");
/* 172 */     this.securityID = rs.getString("Sec_Id");
/* 173 */     this.engine = rs.getString("Engine").trim();
/* 174 */     this.series = rs.getString("Series").trim();
/* 175 */     this.line = rs.getString("Line").trim();
/*     */     try {
/* 177 */       this.suppressSequenceController = rs.getString("Suppress_Sequence_Controller").trim();
/* 178 */     } catch (Exception x) {}
/*     */     
/*     */     try {
/* 181 */       this.securityCodeRequired = rs.getBoolean("SecCode_required");
/* 182 */     } catch (Exception x) {}
/*     */     
/*     */     try {
/* 185 */       this.optionListID = rs.getInt("Option_List_Id");
/* 186 */     } catch (Exception x) {}
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSControllerData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */