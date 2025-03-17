/*     */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ProgrammingResult;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolCallback;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder.VITBuilderProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ProgrammingStatusImpl;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReProgrammer
/*     */ {
/*  26 */   private static Logger log = Logger.getLogger(ReProgrammer.class);
/*     */   public ProgrammingResult reprogram(ToolCallback cb, MessageCallback mcb, Object programmingData, Object dataUnits, String securityCode, String securityCodeCtrl) throws Exception {
/*     */     ProgrammingResult progResult;
/*  29 */     ProgrammingData progData = (ProgrammingData)programmingData;
/*  30 */     List<SPSBlob> blobInfos = progData.getCalibrationFiles();
/*  31 */     VIT1 vit1 = cb.getVIT1(progData.getDeviceID());
/*  32 */     ProgrammingStatus toolResult = null;
/*  33 */     ToolUtils toolUtils = new ToolUtils();
/*  34 */     int callbackCount = 0;
/*  35 */     if (toolUtils.extendedDebugEnabled()) {
/*  36 */       toolUtils.createDebugDir();
/*     */     }
/*     */     try {
/*  39 */       List lstAttrs = vit1.collectAttributes(progData.getDeviceID());
/*  40 */       if (!lstAttrs.isEmpty()) {
/*  41 */         VITBuilder builder = VITBuilderProvider.getBuilder("VIT2 Builder", lstAttrs, progData);
/*  42 */         VIT2 vit2 = (VIT2)builder.build();
/*  43 */         log.debug("VIT2 successfully built.");
/*  44 */         boolean needsSecurityCode = (securityCode != null);
/*  45 */         boolean needsSecurityCodeCtrl = (securityCodeCtrl != null);
/*  46 */         int countSecurityCodeEntries = (needsSecurityCode ? 1 : 0) + (needsSecurityCodeCtrl ? 1 : 0);
/*  47 */         List<Pair> vit2Attributes = vit2.getAttributes();
/*  48 */         Pair[] vit2Pairs = new Pair[vit2Attributes.size() + countSecurityCodeEntries];
/*  49 */         for (int i = 0; i < vit2Pairs.length - countSecurityCodeEntries; i++) {
/*  50 */           vit2Pairs[i] = vit2Attributes.get(i);
/*     */         }
/*  52 */         if (needsSecurityCodeCtrl) {
/*  53 */           vit2Pairs[vit2Pairs.length - 2] = (Pair)new PairImpl("SecCodeCtrl", securityCodeCtrl);
/*     */         }
/*  55 */         if (needsSecurityCode) {
/*  56 */           vit2Pairs[vit2Pairs.length - 1] = (Pair)new PairImpl("SecCodeVeh", securityCode);
/*     */         }
/*  58 */         if (toolUtils.extendedDebugEnabled()) {
/*  59 */           toolUtils.writeDebugVIT("VIT2", vit2Pairs);
/*     */         }
/*  61 */         SPSBlob[] spsBlobs = new SPSBlob[blobInfos.size()];
/*  62 */         int numDataUnits = ((List)dataUnits).size();
/*  63 */         if (spsBlobs.length != numDataUnits) {
/*  64 */           log.error("Different number of programmingDataInfo's and blobs: " + spsBlobs.length + ", " + numDataUnits);
/*  65 */           throw new Exception();
/*     */         } 
/*  67 */         for (int j = 0; j < spsBlobs.length; j++) {
/*  68 */           spsBlobs[j] = blobInfos.get(j);
/*  69 */           byte[] theBlob = ((List<byte[]>)dataUnits).get(j);
/*     */           
/*  71 */           spsBlobs[j].setData(theBlob);
/*  72 */           if (toolUtils.extendedDebugEnabled()) {
/*  73 */             toolUtils.writeDebugBlob(spsBlobs[j].getBlobName(), theBlob);
/*     */           }
/*     */         } 
/*  76 */         String rsc = progData.getRepairShopCode();
/*  77 */         if (rsc == null) {
/*  78 */           rsc = "";
/*     */         }
/*  80 */         ISPSTool spsTool = cb.getSPSTool();
/*  81 */         toolUtils.installNativeToolCallback(spsTool);
/*  82 */         callbackCount++;
/*  83 */         Pair[] toolProperties = new Pair[1];
/*  84 */         toolProperties[0] = cb.getToolContext().getErrorFlag();
/*  85 */         if (log.isDebugEnabled()) {
/*  86 */           for (int k = 0; k < toolProperties.length; k++) {
/*  87 */             log.debug("Tool property: " + toolProperties[k].getFirst() + ", " + toolProperties[k].getSecond());
/*     */           }
/*     */         }
/*  90 */         spsTool.setToolProperties(toolProperties);
/*  91 */         toolUtils.installNativeToolCallback(spsTool);
/*  92 */         callbackCount++;
/*  93 */         if (ClientAppContextProvider.getClientAppContext().testMode()) {
/*  94 */           ProgrammingStatusImpl programmingStatusImpl = new ProgrammingStatusImpl(true, null, null, null);
/*  95 */           log.debug("No programming in test mode");
/*     */         } else {
/*  97 */           toolResult = spsTool.reprogECU(vit2Pairs, spsBlobs, rsc, mcb);
/*  98 */           log.info("Native subsystem programming response: " + toolResult.getStatus());
/*     */         } 
/*     */       } 
/* 101 */     } catch (Exception e) {
/* 102 */       log.error("ECU programming failed: " + e);
/* 103 */       throw e;
/*     */     } finally {
/* 105 */       toolUtils.uninstallNativeToolCallback(cb.getSPSTool(), callbackCount);
/*     */     } 
/*     */     
/* 108 */     if (toolResult != null) {
/* 109 */       progResult = new ProgrammingResultImpl(toolResult.getStatus(), toolResult);
/*     */     } else {
/* 111 */       progResult = new ProgrammingResultImpl(false, null);
/*     */     } 
/* 113 */     return progResult;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ReProgrammer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */