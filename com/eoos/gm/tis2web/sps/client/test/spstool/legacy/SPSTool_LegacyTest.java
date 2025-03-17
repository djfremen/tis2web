/*     */ package com.eoos.gm.tis2web.sps.client.test.spstool.legacy;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.RIMParams;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.SPSTool;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSTool_LegacyTest
/*     */ {
/*     */   public static void main(String[] args) {
/*     */     try {
/*  25 */       ISPSTool spsTool = SPSTool.getInstance("TECH2");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  33 */       String commIFace = spsTool.initialize("COM1,115200");
/*  34 */       System.out.println("Communication interface: " + commIFace);
/*     */ 
/*     */ 
/*     */       
/*  38 */       boolean callbackInstalled = spsTool.installCallback();
/*  39 */       System.out.println("Install callback: " + (callbackInstalled ? "OK" : "FAILED"));
/*     */ 
/*     */ 
/*     */       
/*  43 */       RIMParams rimParams = new RIMParams(17, 3, 4);
/*  44 */       spsTool.setReqMthdProperties(rimParams);
/*     */ 
/*     */ 
/*     */       
/*  48 */       Pair[] properties = new Pair[4];
/*     */       
/*  50 */       properties[0] = (Pair)new PairImpl("REPROG", Integer.valueOf(4));
/*  51 */       properties[0] = (Pair)new PairImpl("MethodGroup", Integer.valueOf(32));
/*  52 */       properties[1] = (Pair)new PairImpl("EventType", Integer.valueOf(3));
/*  53 */       properties[2] = (Pair)new PairImpl("SalesOrg,NAO", Integer.valueOf(0));
/*  54 */       properties[3] = (Pair)new PairImpl("Error_Not", Integer.valueOf(0));
/*     */       
/*  56 */       spsTool.setToolProperties(properties);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  61 */       String value = spsTool.getToolProperty("sps_event_time");
/*     */       
/*  63 */       System.out.println("Tool property: sps_event_time = " + value);
/*     */ 
/*     */ 
/*     */       
/*  67 */       Pair[] ecuData = spsTool.getECUData();
/*     */       
/*  69 */       if (ecuData != null) {
/*  70 */         for (int k = 0; k < ecuData.length; k++) {
/*  71 */           System.out.println("**************************************************************");
/*  72 */           System.out.println("Attribute: " + ecuData[k].getFirst().toString());
/*  73 */           System.out.println("Value: " + ecuData[k].getSecond().toString());
/*     */         } 
/*  75 */         System.out.println("**************************************************************");
/*     */       } else {
/*  77 */         System.out.println("Invalid ECU Data: array is NULL");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       Pair[] vit2 = new Pair[10];
/*     */       
/*  87 */       for (int index = 0; index < vit2.length; index++) {
/*  88 */         vit2[index] = (Pair)new PairImpl("attribut" + index, "value" + index);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  93 */       byte[] blob = new byte[10];
/*     */       
/*  95 */       for (int i = 0; i < blob.length; i++) {
/*  96 */         blob[i] = (byte)i;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 101 */       SPSBlob[] blobs = new SPSBlob[10];
/*     */       
/* 103 */       for (int j = 0; j < blobs.length; j++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       TestRequestMethodData[] arrayOfTestRequestMethodData = new TestRequestMethodData[1];
/*     */       
/* 112 */       arrayOfTestRequestMethodData[0] = new TestRequestMethodData();
/*     */       
/* 114 */       spsTool.setReqMthdProperties((RequestMethodData[])arrayOfTestRequestMethodData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       boolean callbackUninstalled = spsTool.uninstallCallback();
/* 127 */       System.out.println("Uninstall callback: " + (callbackUninstalled ? "OK" : "FAILED"));
/* 128 */     } catch (Exception e) {
/* 129 */       System.out.println("Exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\spstool\legacy\SPSTool_LegacyTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */