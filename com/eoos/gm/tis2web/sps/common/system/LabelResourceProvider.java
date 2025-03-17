/*    */ package com.eoos.gm.tis2web.sps.common.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResourceWrapper;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LabelResourceProvider
/*    */ {
/* 13 */   private static LabelResourceProvider instance = null;
/* 14 */   private static Logger log = Logger.getLogger(LabelResourceProvider.class);
/*    */   
/*    */   private static final String CLIENTPROVIDER = "com.eoos.gm.tis2web.sps.client.system.LabelResourceImpl";
/*    */   
/*    */   private static final String SERVERPROVIDER = "com.eoos.gm.tis2web.sps.server.implementation.system.LabelResourceImpl";
/*    */   
/*    */   public static synchronized LabelResourceProvider getInstance() {
/* 21 */     if (instance == null) {
/* 22 */       instance = new LabelResourceProvider();
/*    */     }
/* 24 */     return instance;
/*    */   }
/*    */   
/*    */   public LabelResource getLabelResource() {
/* 28 */     LabelResource result = null;
/* 29 */     boolean onClient = false;
/* 30 */     onClient = (System.getProperty("com.eoos.gm.tis2web.sps.client") != null);
/*    */     try {
/* 32 */       if (onClient) {
/* 33 */         result = (LabelResource)Class.forName("com.eoos.gm.tis2web.sps.client.system.LabelResourceImpl").newInstance();
/*    */       } else {
/* 35 */         result = (LabelResource)Class.forName("com.eoos.gm.tis2web.sps.server.implementation.system.LabelResourceImpl").newInstance();
/*    */       } 
/* 37 */     } catch (Exception e) {
/* 38 */       log.error("Cannot get LabelResource: " + e);
/*    */     } 
/* 40 */     return (LabelResource)new LabelResourceWrapper(result);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\system\LabelResourceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */