/*    */ package com.eoos.gm.tis2web.sps.client.vci.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.vci.VCICalculation;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VCICalculationImpl
/*    */   implements VCICalculation
/*    */ {
/*    */   private static final String LIBRARY = "VCIBridge";
/*    */   private static final int BITMASK = 1398162249;
/* 24 */   private static VCICalculation instance = null;
/*    */ 
/*    */ 
/*    */   
/* 28 */   private static Logger log = Logger.getLogger(VCICalculationImpl.class);
/*    */ 
/*    */   
/*    */   private native Integer nativeCalculateVCI(String paramString);
/*    */   
/*    */   public static VCICalculation getInstance() {
/* 34 */     synchronized (VCICalculationImpl.class) {
/* 35 */       if (instance == null) {
/*    */         try {
/* 37 */           System.loadLibrary("VCIBridge");
/* 38 */           instance = new VCICalculationImpl();
/* 39 */         } catch (UnsatisfiedLinkError e) {
/* 40 */           log.error("Could not load VCIBridge library", e);
/*    */         } 
/*    */       }
/* 43 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Integer calculateVCI(String pathToVCIArchive) {
/* 48 */     synchronized (VCICalculationImpl.class) {
/* 49 */       Integer vciCode = nativeCalculateVCI(pathToVCIArchive);
/* 50 */       return Integer.valueOf(vciCode.intValue() ^ 0x53564349);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\vci\impl\VCICalculationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */