/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SPSQualification {
/*  6 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*    */   
/*    */   public static final char WILDCARD_CHAR = '~';
/*    */   
/*    */   public static final String WILDCARD_STRING = "~";
/*    */   
/*    */   public static final String VIN_MIN_SEQUENCE = "0";
/*    */   
/*    */   public static final String VIN_MAX_SEQUENCE = "999999";
/*    */   
/*    */   protected int VINPos;
/*    */   
/*    */   protected char VINValue;
/*    */   
/*    */   protected char VINPos4or6;
/*    */   
/*    */   protected String VINBeginningSequence;
/*    */   
/*    */   protected String VINEndingSequence;
/*    */ 
/*    */   
/*    */   SPSQualification(SPSControllerData data) throws Exception {
/* 28 */     this.VINPos = data.getVINPos();
/* 29 */     this.VINValue = data.getVINValue();
/* 30 */     this.VINPos4or6 = data.getVINPos4or6();
/* 31 */     this.VINBeginningSequence = data.getVINBeginningSequence();
/* 32 */     if (this.VINBeginningSequence.length() < 6) {
/* 33 */       this.VINBeginningSequence = padVINSequence(this.VINBeginningSequence);
/*    */     }
/* 35 */     this.VINEndingSequence = data.getVINEndingSequence();
/* 36 */     if (this.VINEndingSequence.length() < 6) {
/* 37 */       this.VINEndingSequence = padVINSequence(this.VINEndingSequence);
/*    */     }
/*    */   }
/*    */   
/*    */   protected String padVINSequence(String sequence) {
/* 42 */     if (sequence.equals("0")) {
/* 43 */       return sequence;
/*    */     }
/* 45 */     while (sequence.length() < 6) {
/* 46 */       sequence = "0" + sequence;
/*    */     }
/* 48 */     return sequence;
/*    */   }
/*    */   
/*    */   boolean qualifies(SPSVehicle vehicle) {
/* 52 */     if (this.VINPos > 0 && 
/* 53 */       this.VINValue != vehicle.getVIN().getPosition(this.VINPos)) {
/* 54 */       log.debug("vehicle qualification fails (pos=" + this.VINPos + "): " + this.VINValue + " <> " + vehicle.getVIN().getPosition(this.VINPos));
/* 55 */       return false;
/*    */     } 
/*    */     
/* 58 */     if (this.VINPos4or6 != '~' && 
/* 59 */       this.VINPos4or6 != ((SPSVIN)vehicle.getVIN()).getPosition4or6()) {
/* 60 */       log.debug("vehicle qualification fails (pos4/6): " + this.VINPos4or6 + " <> " + ((SPSVIN)vehicle.getVIN()).getPosition4or6());
/* 61 */       return false;
/*    */     } 
/*    */     
/* 64 */     if (!this.VINBeginningSequence.equals("0") && 
/* 65 */       this.VINBeginningSequence.compareTo(vehicle.getVIN().getSequence()) > 0) {
/* 66 */       log.debug("vehicle qualification fails: " + this.VINBeginningSequence + " > " + vehicle.getVIN().getSequence());
/* 67 */       return false;
/*    */     } 
/*    */     
/* 70 */     if (!this.VINEndingSequence.equals("999999") && 
/* 71 */       this.VINEndingSequence.compareTo(vehicle.getVIN().getSequence()) < 0) {
/* 72 */       log.debug("vehicle qualification fails: " + this.VINEndingSequence + " < " + vehicle.getVIN().getSequence());
/* 73 */       return false;
/*    */     } 
/*    */     
/* 76 */     return true;
/*    */   }
/*    */   
/*    */   boolean qualifiesPS(SPSVehicle vehicle) {
/* 80 */     if (!this.VINEndingSequence.equals("0")) {
/* 81 */       log.debug("vehicle qualification fails: " + this.VINEndingSequence + " <> " + "0");
/* 82 */       return false;
/*    */     } 
/* 84 */     if (this.VINPos > 0 && 
/* 85 */       this.VINValue != vehicle.getVIN().getPosition(this.VINPos)) {
/* 86 */       log.debug("vehicle qualification fails (pos=" + this.VINPos + "): " + this.VINValue + " <> " + vehicle.getVIN().getPosition(this.VINPos));
/* 87 */       return false;
/*    */     } 
/*    */     
/* 90 */     if (this.VINPos4or6 != '~' && 
/* 91 */       this.VINPos4or6 != ((SPSVIN)vehicle.getVIN()).getPosition4or6()) {
/* 92 */       log.debug("vehicle qualification fails (pos4/6): " + this.VINPos4or6 + " <> " + ((SPSVIN)vehicle.getVIN()).getPosition4or6());
/* 93 */       return false;
/*    */     } 
/*    */     
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSQualification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */