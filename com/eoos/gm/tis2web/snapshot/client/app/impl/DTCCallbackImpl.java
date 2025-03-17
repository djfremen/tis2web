/*    */ package com.eoos.gm.tis2web.snapshot.client.app.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.snapshot.client.app.DTCCallback;
/*    */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContextProvider;
/*    */ import java.util.Collections;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DTCCallbackImpl
/*    */   implements DTCCallback
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(DTCCallbackImpl.class);
/*    */   
/* 14 */   private static DTCCallbackImpl instance = null;
/*    */   
/*    */   public static DTCCallback getInstance() {
/* 17 */     synchronized (DTCCallbackImpl.class) {
/* 18 */       if (instance == null) {
/* 19 */         instance = new DTCCallbackImpl();
/*    */       }
/* 21 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getBACCode() {
/* 26 */     return ClientAppContextProvider.getClientAppContext().getBACCode();
/*    */   }
/*    */   
/*    */   public void onReadDTC(byte[] dtcData) {
/* 30 */     log.debug("onReadDTC - delegate to DTCAutoTransfer");
/*    */     try {
/* 32 */       DTCUploadProvider.getDTCUpload().upload(Collections.singleton(dtcData));
/* 33 */     } catch (Exception e) {
/* 34 */       throw new RuntimeException("unable to upload dtc", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\impl\DTCCallbackImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */