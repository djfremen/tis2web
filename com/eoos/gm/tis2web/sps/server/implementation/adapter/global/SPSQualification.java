/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSQualification
/*    */ {
/*    */   public static final char WILDCARD_CHAR = '~';
/*    */   public static final String WILDCARD_STRING = "~";
/*    */   public static final String VIN_MIN_SEQUENCE = "0";
/*    */   public static final String VIN_MAX_SEQUENCE = "999999";
/*    */   protected int VINPos;
/*    */   protected char VINValue;
/*    */   protected char VINPos4or6;
/*    */   protected String VINBeginningSequence;
/*    */   protected String VINEndingSequence;
/*    */   
/*    */   SPSQualification(SPSControllerData data) throws Exception {
/* 24 */     this.VINPos = data.getVINPos();
/* 25 */     this.VINValue = data.getVINValue();
/* 26 */     this.VINPos4or6 = data.getVINPos4or6();
/* 27 */     this.VINBeginningSequence = data.getVINBeginningSequence();
/* 28 */     if (this.VINBeginningSequence.length() < 6) {
/* 29 */       this.VINBeginningSequence = padVINSequence(this.VINBeginningSequence);
/*    */     }
/* 31 */     this.VINEndingSequence = data.getVINEndingSequence();
/* 32 */     if (this.VINEndingSequence.length() < 6) {
/* 33 */       this.VINEndingSequence = padVINSequence(this.VINEndingSequence);
/*    */     }
/*    */   }
/*    */   
/*    */   protected String padVINSequence(String sequence) {
/* 38 */     if (sequence.equals("0")) {
/* 39 */       return sequence;
/*    */     }
/* 41 */     while (sequence.length() < 6) {
/* 42 */       sequence = "0" + sequence;
/*    */     }
/* 44 */     return sequence;
/*    */   }
/*    */   
/*    */   boolean qualifies(SPSVehicle vehicle) {
/* 48 */     if (this.VINPos > 0 && 
/* 49 */       this.VINValue != vehicle.getVIN().getPosition(this.VINPos)) {
/* 50 */       return false;
/*    */     }
/*    */     
/* 53 */     if (this.VINPos4or6 != '~' && 
/* 54 */       this.VINPos4or6 != ((SPSVIN)vehicle.getVIN()).getPosition4or6()) {
/* 55 */       return false;
/*    */     }
/*    */     
/* 58 */     if (!this.VINBeginningSequence.equals("0") && 
/* 59 */       this.VINBeginningSequence.compareTo(vehicle.getVIN().getSequence()) > 0) {
/* 60 */       return false;
/*    */     }
/*    */     
/* 63 */     if (!this.VINEndingSequence.equals("999999") && 
/* 64 */       this.VINEndingSequence.compareTo(vehicle.getVIN().getSequence()) < 0) {
/* 65 */       return false;
/*    */     }
/*    */     
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   boolean qualifiesPS(SPSVehicle vehicle) {
/* 72 */     if (!this.VINEndingSequence.equals("0")) {
/* 73 */       return false;
/*    */     }
/* 75 */     if (this.VINPos > 0 && 
/* 76 */       this.VINValue != vehicle.getVIN().getPosition(this.VINPos)) {
/* 77 */       return false;
/*    */     }
/*    */     
/* 80 */     if (this.VINPos4or6 != '~' && 
/* 81 */       this.VINPos4or6 != ((SPSVIN)vehicle.getVIN()).getPosition4or6()) {
/* 82 */       return false;
/*    */     }
/*    */     
/* 85 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSQualification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */