/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.nao;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ECUDataReadException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.DataReadDelegate;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.SimpleVIT;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataReadDelegate_NAO
/*     */   extends DataReadDelegate
/*     */ {
/*     */   private static final String REQUESTTYPE = "requesttype";
/*     */   private static final String READDATAREQUEST = "dr-request";
/*  28 */   private static Logger log = Logger.getLogger(DataReadDelegate_NAO.class);
/*     */   
/*     */   public SimpleVIT getECUData(DeviceCommunicationCallback dcb, Object requestData, AttributeValueMap context, PtTool ptTool) throws Exception {
/*  31 */     SimpleVIT result = null;
/*     */ 
/*     */ 
/*     */     
/*  35 */     if (!isReplacedController(context)) {
/*  36 */       log.debug("Assumption: Controller was not replaced (yet).");
/*  37 */       if (isTableDrivenRequest(ptTool.getVINVITData())) {
/*  38 */         result = readTableDriven(dcb, requestData, ptTool.getToolContext(), ptTool.getSPSTool(), 0);
/*     */       } else {
/*  40 */         result = ptTool.getVINVITData();
/*     */       } 
/*     */     } else {
/*  43 */       log.debug("Assumption: Controller has already been replaced.");
/*  44 */       if (isTableDrivenRequest(ptTool.getVINVITData())) {
/*  45 */         result = readTableDriven(dcb, requestData, ptTool.getToolContext(), ptTool.getSPSTool(), 1);
/*     */       } else {
/*  47 */         result = readLegacy(dcb, requestData, ptTool.getToolContext(), ptTool.getSPSTool(), 1);
/*     */       } 
/*     */       
/*  50 */       boolean elementsReplaced = false;
/*  51 */       String prefix = "";
/*  52 */       String ecuAdr = result.getEcuAddr();
/*  53 */       SimpleVIT oldVIT = null;
/*     */       try {
/*  55 */         oldVIT = ptTool.getEcuVIT1Data(ecuAdr).getClone();
/*  56 */       } catch (Exception e) {}
/*     */       
/*  58 */       if (oldVIT != null && oldVIT.isSaturn()) {
/*     */         
/*  60 */         result.updateSaturn(oldVIT);
/*  61 */         prefix = "Saturn ";
/*  62 */         elementsReplaced = true;
/*     */       
/*     */       }
/*  65 */       else if (hasVIT1Transfer(context)) {
/*  66 */         result.update(oldVIT, context);
/*  67 */         prefix = "NAO ";
/*  68 */         elementsReplaced = true;
/*     */       } 
/*     */       
/*  71 */       log.debug(prefix + "VIT data replaced: " + elementsReplaced);
/*     */     } 
/*  73 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTableDrivenRequest(SimpleVIT vinVITData) {
/*  78 */     boolean result = false;
/*  79 */     String requestType = vinVITData.getElementValue("requesttype");
/*  80 */     if (requestType != null && requestType.compareTo("dr-request") == 0) {
/*  81 */       result = true;
/*     */     }
/*  83 */     return result;
/*     */   }
/*     */   
/*     */   private SimpleVIT readTableDriven(DeviceCommunicationCallback dcb, Object requestData, ToolContext toolContext, ISPSTool spsTool, int step) throws Exception {
/*  87 */     SimpleVIT result = null;
/*  88 */     int callbackCount = 0;
/*  89 */     ToolUtils toolUtils = new ToolUtils();
/*  90 */     int nController = 0;
/*     */ 
/*     */     
/*     */     try {
/*  94 */       nController = ((List)requestData).size();
/*  95 */       log.debug("Request_Method_Data count: " + nController);
/*  96 */       ((List)requestData).get(0);
/*  97 */       if (nController < 1) {
/*  98 */         throw new ECUDataReadException();
/*     */       }
/* 100 */     } catch (Exception e) {
/* 101 */       log.error("Cannot retrieve Request_Method_Data: " + e);
/* 102 */       throw new ECUDataReadException();
/*     */     } 
/*     */     
/* 105 */     toolUtils.installNativeToolCallback(spsTool);
/* 106 */     callbackCount++;
/* 107 */     RequestMethodData[] rmd = new RequestMethodData[nController];
/* 108 */     for (int i = 0; i < nController; i++) {
/* 109 */       rmd[i] = ((List<RequestMethodData>)requestData).get(i);
/*     */     }
/*     */     
/*     */     try {
/* 113 */       int propCnt = (nController == 1) ? 4 : 5;
/* 114 */       Pair[] toolProperties = new Pair[propCnt];
/* 115 */       toolProperties[0] = toolContext.getProgrammingMode();
/* 116 */       toolProperties[1] = toolContext.getErrorFlag();
/* 117 */       toolProperties[2] = toolContext.getSalesOrg();
/* 118 */       toolProperties[3] = toolContext.getEventType();
/*     */       
/* 120 */       if (nController > 1)
/*     */       {
/* 122 */         toolProperties[4] = toolContext.getReplaceInfo();
/*     */       }
/* 124 */       if (log.isDebugEnabled()) {
/* 125 */         for (int j = 0; j < toolProperties.length; j++) {
/* 126 */           log.debug("Trying to set tool property: " + toolProperties[j].getFirst() + ", " + toolProperties[j].getSecond());
/*     */         }
/*     */       }
/* 129 */       boolean success = spsTool.setToolProperties(toolProperties);
/* 130 */       log.debug("setToolProperties returned: " + success);
/* 131 */       success = spsTool.setReqMthdProperties(rmd);
/* 132 */       log.debug("setRequestMethodProperties returned: " + success);
/* 133 */       Pair[] rawData = performRead(dcb, toolUtils, spsTool, step);
/* 134 */       result = new SimpleVIT(rawData);
/* 135 */     } catch (Exception e) {
/* 136 */       log.error("Cannot set ToolProperties or RequestMethodProperties or data reading failed: " + e);
/* 137 */       throw new ECUDataReadException();
/*     */     } finally {
/* 139 */       toolUtils.uninstallNativeToolCallback(spsTool, callbackCount--);
/*     */     } 
/*     */     
/* 142 */     return result;
/*     */   }
/*     */   
/*     */   private SimpleVIT readLegacy(DeviceCommunicationCallback dcb, Object requestData, ToolContext toolContext, ISPSTool spsTool, int step) throws Exception {
/* 146 */     SimpleVIT result = null;
/* 147 */     int callbackCount = 0;
/* 148 */     ToolUtils toolUtils = new ToolUtils();
/*     */     
/*     */     try {
/* 151 */       Pair[] toolProperties = new Pair[5];
/* 152 */       toolProperties[0] = toolContext.getProgrammingMode();
/* 153 */       toolProperties[1] = toolContext.getErrorFlag();
/* 154 */       toolProperties[2] = toolContext.getSalesOrg();
/* 155 */       toolProperties[3] = toolContext.getEventType();
/* 156 */       toolProperties[4] = toolContext.getMethodGroup();
/* 157 */       if (log.isDebugEnabled()) {
/* 158 */         for (int i = 0; i < toolProperties.length; i++) {
/* 159 */           log.debug("Trying to set tool property: " + toolProperties[i].getFirst() + ", " + toolProperties[i].getSecond());
/*     */         }
/*     */       }
/* 162 */       toolUtils.installNativeToolCallback(spsTool);
/* 163 */       callbackCount++;
/* 164 */       boolean success = spsTool.setToolProperties(toolProperties);
/* 165 */       log.debug("setToolProperties returned: " + success);
/* 166 */       Pair[] rawData = performRead(dcb, toolUtils, spsTool, step);
/* 167 */       result = new SimpleVIT(rawData);
/* 168 */     } catch (Exception e) {
/* 169 */       log.error("Cannot set NAO legacy-mode specific tool properties or data reading failed: " + e);
/* 170 */       throw new ECUDataReadException();
/*     */     } finally {
/* 172 */       toolUtils.uninstallNativeToolCallback(spsTool, callbackCount--);
/*     */     } 
/*     */     
/* 175 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isReplacedController(AttributeValueMap context) {
/* 179 */     boolean result = false;
/* 180 */     if (context.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) != null) {
/* 181 */       result = true;
/*     */     }
/* 183 */     return result;
/*     */   }
/*     */   
/*     */   private boolean hasVIT1Transfer(AttributeValueMap context) {
/* 187 */     boolean result = false;
/* 188 */     String msg = "No VIT1 data transfer.";
/* 189 */     Value transferElements = context.getValue(CommonAttribute.VIT1_TRANSFER);
/* 190 */     if (transferElements != null) {
/* 191 */       msg = "VIT1 data transfer required.";
/* 192 */       result = true;
/*     */     } 
/* 194 */     log.debug(msg);
/* 195 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\nao\DataReadDelegate_NAO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */