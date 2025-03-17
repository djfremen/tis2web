/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class VINVerifier
/*    */ {
/*  9 */   private static final Logger log = Logger.getLogger(VINVerifier.class);
/*    */ 
/*    */   
/* 12 */   private static int[] charValues = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9 };
/*    */ 
/*    */   
/* 15 */   private static int[] posWeights = new int[] { 8, 7, 6, 5, 4, 3, 2, 10, -1, 9, 8, 7, 6, 5, 4, 3, 2 };
/*    */   
/*    */   protected boolean isCorrect(String _vin) {
/* 18 */     boolean result = false;
/* 19 */     if (_vin.length() == 17 && 
/* 20 */       _vin.charAt(8) == calculateCheckDigit(_vin.toUpperCase(Locale.ENGLISH))) {
/* 21 */       result = true;
/*    */     }
/*    */     
/* 24 */     log.debug("VIN " + _vin.toUpperCase(Locale.ENGLISH) + ": " + (result ? "valid" : "invalid"));
/* 25 */     return result;
/*    */   }
/*    */   
/*    */   protected Object splitVIN(String _vin) {
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   private char calculateCheckDigit(String _vin) {
/* 33 */     char[] chars = _vin.toCharArray();
/* 34 */     int posBaseValue = -1;
/* 35 */     int sum = 0;
/* 36 */     for (int i = 0; i < 17; i++) {
/* 37 */       if (Character.isLetter(chars[i])) {
/* 38 */         posBaseValue = charValues[Character.getNumericValue(chars[i]) - 10];
/* 39 */       } else if (Character.isDigit(chars[i])) {
/* 40 */         posBaseValue = Character.getNumericValue(chars[i]);
/*    */       } 
/* 42 */       int posWeight = posWeights[i];
/* 43 */       int posValue = posBaseValue * posWeight;
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 48 */       if (i != 8) {
/* 49 */         sum += posValue;
/*    */       }
/*    */     } 
/* 52 */     char result = (sum % 11 == 10) ? 'X' : Integer.valueOf(sum % 11).toString().charAt(0);
/* 53 */     log.debug("VIN: " + _vin + "\tCheck Value: " + (sum % 11) + "\tCheck Digit: " + result);
/* 54 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\VINVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */