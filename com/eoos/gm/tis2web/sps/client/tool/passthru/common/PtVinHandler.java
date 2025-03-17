/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.common;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.VINReadException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.VCSNavigator;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.RIMParams;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PtVinHandler
/*     */ {
/*  26 */   private static Logger log = Logger.getLogger(PtVinHandler.class);
/*     */   public SimpleVIT getVinVITData(DeviceCommunicationCallback dcb, Object requestData, ISPSTool spsTool, VCSNavigator navigator, ToolContext toolContext) throws Exception {
/*     */     Integer methodGroupId;
/*  29 */     SimpleVIT simpleVIT = null;
/*  30 */     ToolUtils toolUtils = new ToolUtils();
/*  31 */     int callbackCount = 0;
/*  32 */     Pair[] ecuData = null;
/*     */     
/*  34 */     int id = -1;
/*  35 */     RIMParams rimParams = null;
/*     */     
/*     */     try {
/*  38 */       methodGroupId = navigator.getMethodGroupID(requestData);
/*  39 */       if (methodGroupId == null || methodGroupId.intValue() < 0) {
/*  40 */         log.error("Invalid methodGroupID: " + methodGroupId);
/*  41 */         throw new VINReadException();
/*     */       } 
/*  43 */       toolContext.setSalesOrg(navigator.getMethodGroupProviderID());
/*  44 */       id = methodGroupId.intValue();
/*  45 */       toolContext.setMethodGroupValue(methodGroupId);
/*  46 */       if (id == 0) {
/*  47 */         rimParams = navigator.getRIMParams();
/*     */       }
/*     */     }
/*  50 */     catch (Exception e) {
/*  51 */       if (e instanceof RequestException) {
/*  52 */         SelectionRequest request = (SelectionRequest)((RequestException)e).getRequest();
/*  53 */         DisplayableAttribute attr = (DisplayableAttribute)request.getAttribute();
/*  54 */         List values = request.getOptions();
/*  55 */         Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  56 */         List<String> displayValues = new ArrayList();
/*  57 */         Iterator<ToolValue> it = values.iterator();
/*  58 */         while (it.hasNext()) {
/*  59 */           ToolValue navValue = it.next();
/*  60 */           displayValues.add(navValue.getDenotation(locale));
/*     */         } 
/*  62 */         log.debug("Navigation exception received: " + attr.getDenotation(locale) + ", " + displayValues);
/*     */       } 
/*  64 */       throw e;
/*     */     } 
/*  66 */     toolUtils.installNativeToolCallback(spsTool);
/*  67 */     callbackCount++;
/*  68 */     Pair[] toolProperties = null;
/*  69 */     if (id == 0) {
/*  70 */       toolProperties = new Pair[4];
/*  71 */       toolContext.setEventTypeValue(ToolContext.GEN_REQUEST_EVENT);
/*  72 */       toolProperties[3] = toolContext.getEventType();
/*  73 */       spsTool.setReqMthdProperties(rimParams);
/*     */     } else {
/*  75 */       toolProperties = new Pair[5];
/*  76 */       toolContext.setEventTypeValue(ToolContext.REQUEST_EVENT);
/*  77 */       toolProperties[3] = toolContext.getEventType();
/*  78 */       toolContext.setMethodGroupValue(Integer.valueOf(id));
/*  79 */       toolProperties[4] = toolContext.getMethodGroup();
/*     */     } 
/*     */     
/*  82 */     toolProperties[0] = toolContext.getProgrammingMode();
/*  83 */     toolProperties[1] = toolContext.getErrorFlag();
/*  84 */     toolProperties[2] = toolContext.getSalesOrg();
/*     */     
/*  86 */     if (log.isDebugEnabled()) {
/*  87 */       for (int i = 0; i < toolProperties.length; i++) {
/*  88 */         log.debug("Tool property: " + toolProperties[i].getFirst() + ", " + toolProperties[i].getSecond());
/*     */       }
/*     */     }
/*     */     
/*  92 */     spsTool.setToolProperties(toolProperties);
/*  93 */     toolUtils.installNativeToolCallback(spsTool);
/*  94 */     callbackCount++;
/*     */     
/*  96 */     if (dcb != null) {
/*  97 */       dcb.start();
/*     */     }
/*  99 */     if (ClientAppContextProvider.getClientAppContext().testMode()) {
/* 100 */       ecuData = toolUtils.getFileVIT1("VIN-VIT1");
/*     */     } else {
/* 102 */       ecuData = spsTool.getECUData();
/*     */     } 
/* 104 */     if (dcb != null) {
/* 105 */       dcb.stop();
/*     */     }
/*     */     
/* 108 */     toolUtils.uninstallNativeToolCallback(spsTool, callbackCount);
/*     */     
/* 110 */     if (toolUtils.extendedDebugEnabled()) {
/* 111 */       toolUtils.writeDebugVIT("VIT1-PT-VIN", ecuData);
/*     */     }
/*     */     
/* 114 */     if (ecuData != null) {
/* 115 */       simpleVIT = new SimpleVIT(ecuData);
/* 116 */       simpleVIT.fixElements();
/*     */     } else {
/* 118 */       log.error("Could not read ECU data. Method group id: " + methodGroupId);
/* 119 */       throw new VINReadException();
/*     */     } 
/*     */     
/* 122 */     return simpleVIT;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\common\PtVinHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */