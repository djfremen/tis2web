/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataDownloadRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonRequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgrammingDataDownloadRequestImpl
/*    */   implements ProgrammingDataDownloadRequest, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private List blobs;
/*    */   
/*    */   public ProgrammingDataDownloadRequestImpl(List blobs) {
/* 24 */     this.blobs = blobs;
/*    */   }
/*    */ 
/*    */   
/*    */   public List getCalibrationFiles() {
/* 29 */     return this.blobs;
/*    */   }
/*    */   
/*    */   public Value getConfirmationValue() {
/* 33 */     return CommonValue.OK;
/*    */   }
/*    */   
/*    */   public RequestGroup getRequestGroup() {
/* 37 */     return CommonRequestGroup.DOWNLOAD_REPROGRAM;
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 41 */     return CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRequestGroup(RequestGroup requestGroup) {}
/*    */   
/*    */   public boolean autoSubmit() {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public void setAutoSubmit(boolean autoSubmit) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\ProgrammingDataDownloadRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */