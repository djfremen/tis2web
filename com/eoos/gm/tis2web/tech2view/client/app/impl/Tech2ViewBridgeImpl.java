/*    */ package com.eoos.gm.tis2web.tech2view.client.app.impl;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.tech2view.client.app.Tech2ViewBridge;
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
/*    */ public class Tech2ViewBridgeImpl
/*    */   implements Tech2ViewBridge
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(Tech2ViewBridgeImpl.class);
/*    */   
/*    */   private static final String LIBRARY = "Tech2ViewBridge";
/*    */   
/* 23 */   private static Tech2ViewBridgeImpl instance = null;
/*    */   
/*    */   private native boolean nativeSetProperties(String paramString, Pair[] paramArrayOfPair);
/*    */   
/*    */   private native boolean nativeSetLanguage(String paramString1, String paramString2);
/*    */   
/*    */   private native boolean nativeStartUI(String paramString1, String paramString2);
/*    */   
/*    */   private Tech2ViewBridgeImpl() {
/*    */     try {
/* 33 */       System.loadLibrary("Tech2ViewBridge");
/* 34 */     } catch (Exception e) {
/* 35 */       log.fatal("unable to load Tech2ViewBridge library - exception: " + e);
/* 36 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized Tech2ViewBridge getInstance() {
/* 42 */     synchronized (Tech2ViewBridgeImpl.class) {
/* 43 */       if (instance == null) {
/* 44 */         instance = new Tech2ViewBridgeImpl();
/*    */       }
/* 46 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setProperties(String appID, Pair[] properties) {
/* 51 */     synchronized (Tech2ViewBridgeImpl.class) {
/* 52 */       return nativeSetProperties(appID, properties);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startUI(String appID, String toolID) {
/* 57 */     synchronized (Tech2ViewBridgeImpl.class) {
/* 58 */       return nativeStartUI(appID, toolID);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setLanguage(String appID, String language) {
/* 63 */     synchronized (Tech2ViewBridgeImpl.class) {
/* 64 */       return nativeSetLanguage(appID, language);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\app\impl\Tech2ViewBridgeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */