/*    */ package com.eoos.gm.tis2web.sps.client.tool.spstool;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.ReprogramProgressTool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.RIMParams;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*    */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISPSTool
/*    */   extends ReprogramProgressTool
/*    */ {
/* 32 */   public static final Integer DRIVER_PROPERTY_CATEGORY_DTCUPLOAD = Integer.valueOf(1001);
/*    */   
/*    */   String initialize(String paramString);
/*    */   
/*    */   boolean installCallback();
/*    */   
/*    */   boolean uninstallCallback();
/*    */   
/*    */   boolean setToolProperties(Pair[] paramArrayOfPair);
/*    */   
/*    */   String getToolProperty(String paramString);
/*    */   
/*    */   boolean setReqMthdProperties(RIMParams paramRIMParams);
/*    */   
/*    */   boolean setReqMthdProperties(RequestMethodData[] paramArrayOfRequestMethodData);
/*    */   
/*    */   Pair[] getECUData();
/*    */   
/*    */   ProgrammingStatus reprogECU(Pair[] paramArrayOfPair, SPSBlob[] paramArrayOfSPSBlob, String paramString, MessageCallback paramMessageCallback);
/*    */   
/*    */   boolean clearVehicleDTCs(Integer paramInteger);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\spstool\ISPSTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */