/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ECUDataReadException;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.DTCUploadProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import java.util.Collections;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DataReadDelegate
/*    */ {
/* 21 */   private static Logger log = Logger.getLogger(DataReadDelegate.class);
/*    */   
/*    */   public abstract SimpleVIT getECUData(DeviceCommunicationCallback paramDeviceCommunicationCallback, Object paramObject, AttributeValueMap paramAttributeValueMap, PtTool paramPtTool) throws Exception;
/*    */   
/*    */   public Pair[] performRead(DeviceCommunicationCallback dcb, ToolUtils toolUtils, ISPSTool spsTool, int step) throws Exception {
/* 26 */     Pair[] result = null;
/* 27 */     int callbackCount = 0;
/* 28 */     boolean hasReadDialog = false;
/*    */     try {
/* 30 */       toolUtils.installNativeToolCallback(spsTool);
/* 31 */       callbackCount++;
/* 32 */       if (dcb != null) {
/* 33 */         dcb.start();
/* 34 */         hasReadDialog = true;
/*    */       } 
/* 36 */       if (ClientAppContextProvider.getClientAppContext().testMode()) {
/* 37 */         if (ClientAppContextProvider.getClientAppContext().DTCTestMode()) {
/* 38 */           byte[] dtcData = toolUtils.getTestDTC();
/* 39 */           if (dtcData != null) {
/* 40 */             DTCUploadProvider.getDTCUpload().upload(Collections.singleton(dtcData));
/*    */           }
/*    */         } 
/* 43 */         result = toolUtils.getFileVIT1("ECU-VIT1");
/*    */       } else {
/* 45 */         result = spsTool.getECUData();
/*    */       } 
/* 47 */       if (hasReadDialog) {
/* 48 */         dcb.stop();
/*    */       }
/* 50 */       hasReadDialog = false;
/* 51 */       if (toolUtils.extendedDebugEnabled()) {
/* 52 */         toolUtils.writeDebugVIT("VIT1-PT", result, step);
/*    */       }
/* 54 */     } catch (Exception e) {
/* 55 */       if (hasReadDialog && dcb != null) {
/* 56 */         dcb.stop();
/*    */       }
/* 58 */       log.error("Cannot read ECU Data: " + e);
/* 59 */       throw new ECUDataReadException();
/*    */     } finally {
/* 61 */       toolUtils.uninstallNativeToolCallback(spsTool, callbackCount--);
/*    */     } 
/*    */     
/* 64 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\common\DataReadDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */