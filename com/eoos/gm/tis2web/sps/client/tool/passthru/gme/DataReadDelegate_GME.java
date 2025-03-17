/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.gme;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ECUDataReadException;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.DataReadDelegate;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.SimpleVIT;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataReadDelegate_GME
/*    */   extends DataReadDelegate
/*    */ {
/* 24 */   private static Logger log = Logger.getLogger(DataReadDelegate_GME.class);
/*    */   
/*    */   public SimpleVIT getECUData(DeviceCommunicationCallback dcb, Object requestData, AttributeValueMap context, PtTool ptTool) throws Exception {
/* 27 */     SimpleVIT result = null;
/* 28 */     ISPSTool spsTool = ptTool.getSPSTool();
/* 29 */     ToolContext toolContext = ptTool.getToolContext();
/* 30 */     log.info("Requesting ECU Data: (" + ptTool.getId() + ", " + ptTool.getType() + ")");
/* 31 */     int callbackCount = 0;
/* 32 */     ToolUtils toolUtils = new ToolUtils();
/* 33 */     int nController = 0;
/*    */ 
/*    */     
/*    */     try {
/* 37 */       nController = ((List)requestData).size();
/* 38 */       log.debug("Request_Method_Data count: " + nController);
/* 39 */       ((List)requestData).get(0);
/* 40 */       if (nController < 1) {
/* 41 */         throw new ECUDataReadException();
/*    */       }
/* 43 */     } catch (Exception e) {
/* 44 */       log.debug("Cannot retrieve Request_Method_Data: " + e);
/* 45 */       throw new ECUDataReadException();
/*    */     } 
/*    */     
/* 48 */     toolUtils.installNativeToolCallback(spsTool);
/* 49 */     callbackCount++;
/* 50 */     RequestMethodData[] rmd = new RequestMethodData[nController];
/* 51 */     for (int i = 0; i < nController; i++) {
/* 52 */       rmd[i] = ((List<RequestMethodData>)requestData).get(i);
/*    */     }
/*    */     try {
/* 55 */       Pair[] toolProperties = new Pair[5];
/* 56 */       toolProperties[0] = toolContext.getProgrammingMode();
/* 57 */       toolProperties[1] = toolContext.getErrorFlag();
/* 58 */       toolProperties[2] = toolContext.getSalesOrg();
/* 59 */       toolProperties[3] = toolContext.getEventType();
/* 60 */       toolContext.setMethodGroupValue(Integer.valueOf(rmd[0].getRequestMethodID()));
/* 61 */       toolProperties[4] = toolContext.getMethodGroup();
/* 62 */       if (log.isDebugEnabled()) {
/* 63 */         for (int j = 0; j < toolProperties.length; j++) {
/* 64 */           log.debug("Trying to set tool property: " + toolProperties[j].getFirst() + ", " + toolProperties[j].getSecond());
/*    */         }
/*    */       }
/* 67 */       boolean success = spsTool.setToolProperties(toolProperties);
/* 68 */       log.debug("setToolProperties returned: " + success);
/* 69 */       Pair[] rawData = performRead(dcb, toolUtils, spsTool, 0);
/* 70 */       result = new SimpleVIT(rawData);
/* 71 */     } catch (Exception e) {
/* 72 */       log.error("Cannot set GME specific tool properties or data read failed: " + e);
/* 73 */       throw new ECUDataReadException();
/*    */     } finally {
/* 75 */       toolUtils.uninstallNativeToolCallback(spsTool, callbackCount--);
/*    */     } 
/* 77 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\gme\DataReadDelegate_GME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */