/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.SPSProgrammingData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class SPSProgrammingDataImpl
/*    */   implements SPSProgrammingData
/*    */ {
/*    */   private List blobs;
/* 20 */   private String postProgMsg = null;
/* 21 */   private String vmecuhn = null;
/* 22 */   private String ssecusvn = null;
/* 23 */   private String ecuadr = null;
/* 24 */   private String odometer = null;
/* 25 */   private String keycode = null;
/*    */ 
/*    */   
/*    */   public SPSProgrammingDataImpl(String ecuadr, String vmecuhn, String ssecusvn, String postProgMsg) {
/* 29 */     this.blobs = new ArrayList();
/* 30 */     this.ecuadr = ecuadr;
/* 31 */     this.vmecuhn = vmecuhn;
/* 32 */     this.ssecusvn = ssecusvn;
/* 33 */     this.postProgMsg = postProgMsg;
/*    */   }
/*    */   
/*    */   public List getBlobs() {
/* 37 */     return this.blobs;
/*    */   }
/*    */   
/*    */   public void setBlobs(List blobs) {
/* 41 */     this.blobs = blobs;
/*    */   }
/*    */   
/*    */   public String getPostProgMsg() {
/* 45 */     return this.postProgMsg;
/*    */   }
/*    */   
/*    */   public void setPostProgMsg(String postProgMsg) {
/* 49 */     this.postProgMsg = postProgMsg;
/*    */   }
/*    */   
/*    */   public String getVMECUHN() {
/* 53 */     return this.vmecuhn;
/*    */   }
/*    */   
/*    */   public void setVMECUHN(String vmecuhn) {
/* 57 */     this.vmecuhn = vmecuhn;
/*    */   }
/*    */   
/*    */   public String getSSECUSVN() {
/* 61 */     return this.ssecusvn;
/*    */   }
/*    */   
/*    */   public void setSSECUSVN(String ssecusvn) {
/* 65 */     this.ssecusvn = ssecusvn;
/*    */   }
/*    */   
/*    */   public String getEcuadr() {
/* 69 */     return this.ecuadr;
/*    */   }
/*    */   
/*    */   public void setEcuadr(String ecuadr) {
/* 73 */     this.ecuadr = ecuadr;
/*    */   }
/*    */   
/*    */   public String getKeycode() {
/* 77 */     return this.keycode;
/*    */   }
/*    */   
/*    */   public String getOdometer() {
/* 81 */     return this.odometer;
/*    */   }
/*    */   
/*    */   public void setKeycode(String keycode) {
/* 85 */     this.keycode = keycode;
/*    */   }
/*    */   
/*    */   public void setOdometer(String odometer) {
/* 89 */     this.odometer = odometer;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\SPSProgrammingDataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */