/*     */ package com.eoos.gm.tis2web.sas.server.implementation.tool;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SCASKAResult;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SSAData;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolBridgeImpl
/*     */   implements ToolBridge, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3258417244009935672L;
/*  42 */   private static final Logger log = Logger.getLogger(ToolBridgeImpl.class);
/*     */   
/*     */   private static final String LIBRARY = "SASBridge";
/*     */   public static final String INSTANCE_NAME_TECH2 = "TECH2";
/*     */   public static final String INSTANCE_NAME_SCAN100 = "SCAN100";
/*  47 */   public static Map<String, ToolBridge> id2Instance = new HashMap<String, ToolBridge>();
/*     */   
/*  49 */   private String instanceID = null;
/*     */   
/*     */   private native String nativeGetInstance(String paramString);
/*     */   
/*     */   private native boolean nativeDiscardInstance(String paramString);
/*     */   
/*     */   private native String nativeInitialize(String paramString1, String paramString2);
/*     */   
/*     */   private native boolean nativeSetDriverProperties(String paramString, Pair[] paramArrayOfPair);
/*     */   
/*     */   private native String nativeGetSalesOrganization(String paramString);
/*     */   
/*     */   private native boolean nativeSetSSAData(String paramString, SSAData paramSSAData);
/*     */   
/*     */   private native SSAData nativeGetSSAData(String paramString);
/*     */   
/*     */   private native SCASKAResult nativeSCASKAEnableECU(String paramString1, String paramString2);
/*     */   
/*     */   private ToolBridgeImpl() {
/*  68 */     this.instanceID = null;
/*     */   }
/*     */   
/*     */   public static ToolBridge getInstance(String name) {
/*  72 */     synchronized (ToolBridgeImpl.class) {
/*  73 */       ToolBridge instance = id2Instance.get(name);
/*  74 */       if (instance == null) {
/*     */         try {
/*  76 */           System.loadLibrary("SASBridge");
/*  77 */           ToolBridgeImpl newInstance = new ToolBridgeImpl();
/*  78 */           if ((newInstance.instanceID = newInstance.nativeGetInstance(name)) != null) {
/*  79 */             id2Instance.put(newInstance.instanceID, newInstance);
/*  80 */             instance = newInstance;
/*     */           } else {
/*  82 */             newInstance = null;
/*  83 */             log.error("Invalid instance object");
/*     */           } 
/*  85 */         } catch (Exception e) {
/*  86 */           log.error("Could not load security bridge library. " + e);
/*     */         } 
/*     */       }
/*  89 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean discardInstance(String instanceID) {
/*  94 */     synchronized (ToolBridgeImpl.class) {
/*  95 */       boolean ret = false;
/*  96 */       ToolBridge instance = id2Instance.get(instanceID);
/*  97 */       if (instance != null && 
/*  98 */         instance instanceof ToolBridgeImpl) {
/*  99 */         ToolBridgeImpl toolInstance = (ToolBridgeImpl)instance;
/* 100 */         ret = toolInstance.nativeDiscardInstance(instanceID);
/* 101 */         id2Instance.remove(toolInstance.instanceID);
/* 102 */         toolInstance = null;
/*     */       } 
/*     */       
/* 105 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String initialize(String initParams) {
/* 110 */     synchronized (ToolBridgeImpl.class) {
/* 111 */       return nativeInitialize(this.instanceID, initParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setDriverProperties(Pair[] properties) {
/* 116 */     synchronized (ToolBridgeImpl.class) {
/* 117 */       return nativeSetDriverProperties(this.instanceID, properties);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSalesOrganization() {
/* 122 */     synchronized (ToolBridgeImpl.class) {
/* 123 */       return nativeGetSalesOrganization(this.instanceID);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setSSAData(SSAData ssaData, ExceptionCallback callback) {
/* 128 */     synchronized (ToolBridgeImpl.class) {
/* 129 */       boolean retValue = false;
/* 130 */       retValue = nativeSetSSAData(this.instanceID, ssaData);
/* 131 */       return retValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SSAData getSSAData() {
/* 136 */     synchronized (ToolBridgeImpl.class) {
/* 137 */       return nativeGetSSAData(this.instanceID);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SCASKAResult scaEnableECU(HardwareKey hwk) {
/* 142 */     synchronized (ToolBridgeImpl.class) {
/* 143 */       return nativeSCASKAEnableECU(this.instanceID, hwk.getDecoded());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ExceptionCallback {
/*     */     boolean onException(Exception param1Exception);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\ToolBridgeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */