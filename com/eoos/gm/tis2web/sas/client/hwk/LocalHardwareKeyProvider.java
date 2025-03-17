/*    */ package com.eoos.gm.tis2web.sas.client.hwk;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*    */ import com.eoos.gm.tis2web.sas.common.system.SASServerProvider;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class LocalHardwareKeyProvider
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(HardwareKeyProvider.class);
/*    */   
/* 15 */   private static LocalHardwareKeyProvider instance = null;
/*    */   
/* 17 */   private final Object SYNC_HWK = new Object();
/*    */   
/* 19 */   private HardwareKey hwk = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized LocalHardwareKeyProvider getInstance() {
/* 26 */     if (instance == null) {
/* 27 */       instance = new LocalHardwareKeyProvider();
/*    */     }
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public HardwareKey getHardwareKey() throws UnavailableHardwareKeyException {
/* 33 */     synchronized (this.SYNC_HWK) {
/* 34 */       if (this.hwk == null) {
/* 35 */         log.debug("creating hardware key");
/*    */         try {
/* 37 */           String sessionID = SASClientContextProvider.getInstance().getContext().getSessionID();
/* 38 */           ISASServer server = SASServerProvider.getInstance().getServer(sessionID);
/* 39 */           String hwid = server.getHardwareID();
/* 40 */           this.hwk = new HardwareKey(hwid, hwid);
/* 41 */           log.debug("...successfully created hardware key");
/* 42 */         } catch (Exception e) {
/* 43 */           log.error("failed to acquire hardware-id");
/* 44 */           throw new UnavailableHardwareKeyException();
/*    */         } 
/*    */       } 
/* 47 */       return this.hwk;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\hwk\LocalHardwareKeyProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */