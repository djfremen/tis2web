/*    */ package com.eoos.gm.tis2web.sas.client.hwk;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HWKProviderFacade
/*    */   implements IHardwareKeyProvider {
/* 10 */   private static final Logger log = Logger.getLogger(HWKProviderFacade.class);
/*    */   
/* 12 */   private static HWKProviderFacade instance = null;
/*    */   
/* 14 */   private IHardwareKeyProvider hwkProvider = null;
/*    */   
/*    */   private HWKProviderFacade() {
/* 17 */     log.debug("initializing hwk provider facade");
/* 18 */     if (SASClientContextProvider.getInstance().getContext().isStandaloneInstallation()) {
/* 19 */       log.debug("...local server installation mode, installing adapter for LocalHardwareKeyProvider");
/* 20 */       this.hwkProvider = new IHardwareKeyProvider()
/*    */         {
/*    */           public HardwareKey getHardwareKey() throws UnavailableHardwareKeyException {
/* 23 */             return LocalHardwareKeyProvider.getInstance().getHardwareKey();
/*    */           }
/*    */         };
/*    */     } else {
/*    */       
/* 28 */       log.debug("...normal mode, installing default provider");
/* 29 */       this.hwkProvider = new IHardwareKeyProvider()
/*    */         {
/*    */           public HardwareKey getHardwareKey() throws UnavailableHardwareKeyException {
/* 32 */             return HardwareKeyProvider.getInstance().getHardwareKey();
/*    */           }
/*    */         };
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HWKProviderFacade getInstance() {
/* 41 */     if (instance == null) {
/* 42 */       instance = new HWKProviderFacade();
/*    */     }
/* 44 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public HardwareKey getHardwareKey() throws UnavailableHardwareKeyException {
/* 49 */     return this.hwkProvider.getHardwareKey();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\hwk\HWKProviderFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */