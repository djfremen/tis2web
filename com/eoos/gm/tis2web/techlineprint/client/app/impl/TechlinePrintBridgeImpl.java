/*    */ package com.eoos.gm.tis2web.techlineprint.client.app.impl;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.app.TechlinePrintBridge;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TechlinePrintBridgeImpl
/*    */   implements TechlinePrintBridge
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(TechlinePrintBridgeImpl.class);
/*    */   
/*    */   private static final String LIBRARY = "techlineprintbridge";
/*    */   
/* 23 */   private static TechlinePrintBridgeImpl instance = null;
/*    */   
/*    */   private native boolean nativeSetProperties(String paramString, Pair[] paramArrayOfPair);
/*    */   
/*    */   private native boolean nativeSetLanguage(String paramString1, String paramString2);
/*    */   
/*    */   private native boolean nativeStartUI(String paramString, String[] paramArrayOfString);
/*    */   
/*    */   private TechlinePrintBridgeImpl() {
/*    */     try {
/* 33 */       System.loadLibrary("techlineprintbridge");
/* 34 */     } catch (Exception e) {
/* 35 */       log.fatal("unable to load TechlinePrintBridge library - exception: " + e);
/* 36 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized TechlinePrintBridge getInstance() {
/* 42 */     synchronized (TechlinePrintBridgeImpl.class) {
/* 43 */       if (instance == null) {
/* 44 */         instance = new TechlinePrintBridgeImpl();
/*    */       }
/* 46 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setProperties(String appID, Pair[] properties) {
/* 51 */     synchronized (TechlinePrintBridgeImpl.class) {
/* 52 */       return nativeSetProperties(appID, properties);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startUI(String appID, String[] devices) {
/* 57 */     synchronized (TechlinePrintBridgeImpl.class) {
/* 58 */       return nativeStartUI(appID, devices);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setLanguage(String appID, String language) {
/* 63 */     synchronized (TechlinePrintBridgeImpl.class) {
/* 64 */       return nativeSetLanguage(appID, language);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\app\impl\TechlinePrintBridgeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */