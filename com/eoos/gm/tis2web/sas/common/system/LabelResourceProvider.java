/*    */ package com.eoos.gm.tis2web.sas.common.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.DummyResourceImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResourceWrapper;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class LabelResourceProvider
/*    */ {
/* 11 */   private static LabelResourceProvider instance = null;
/*    */   
/* 13 */   private static Logger log = Logger.getLogger(LabelResourceProvider.class);
/*    */ 
/*    */   
/*    */   private static final String CLIENT_IMPL = "com.eoos.gm.tis2web.sas.client.system.LabelResourceImpl";
/*    */ 
/*    */   
/*    */   private static final String SERVER_IMPL = "com.eoos.gm.tis2web.frame.export.common.i18n.LabelResourceImpl";
/*    */ 
/*    */   
/*    */   public static synchronized LabelResourceProvider getInstance() {
/* 23 */     if (instance == null) {
/* 24 */       instance = new LabelResourceProvider();
/*    */     }
/* 26 */     return instance;
/*    */   }
/*    */   public LabelResource getLabelResource() {
/*    */     DummyResourceImpl dummyResourceImpl;
/* 30 */     LabelResource result = null;
/*    */     
/* 32 */     String clazzName = null;
/* 33 */     if (SystemUtil.isClientVM()) {
/* 34 */       clazzName = "com.eoos.gm.tis2web.sas.client.system.LabelResourceImpl";
/*    */     } else {
/* 36 */       clazzName = "com.eoos.gm.tis2web.frame.export.common.i18n.LabelResourceImpl";
/*    */     } 
/*    */     
/*    */     try {
/* 40 */       result = (LabelResource)Class.forName(clazzName).newInstance();
/* 41 */     } catch (Exception e) {
/* 42 */       log.error("unable to provide implementation of LabelResource - exception:" + e, e);
/* 43 */       log.error("returning dummy implementation");
/* 44 */       dummyResourceImpl = new DummyResourceImpl();
/*    */     } 
/* 46 */     return (LabelResource)new LabelResourceWrapper((LabelResource)dummyResourceImpl);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\system\LabelResourceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */