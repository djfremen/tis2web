/*    */ package com.eoos.gm.tis2web.sas.common.system;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*    */ import com.eoos.util.Util;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SASServerProvider
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(SASServerProvider.class);
/*    */   
/*    */   private static final String PROXY_IMPL = "com.eoos.gm.tis2web.sas.server.implementation.clientside.SASServerRemoteProxy";
/*    */   
/*    */   private static final String IMPL = "com.eoos.gm.tis2web.sas.server.implementation.serverside.SASServer";
/*    */   
/*    */   private static final String CLIENTCL = "com.eoos.gm.tis2web.sas.client.system.ClientClassLoader";
/*    */   
/* 23 */   private static SASServerProvider instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized SASServerProvider getInstance() {
/* 29 */     if (instance == null) {
/* 30 */       instance = new SASServerProvider();
/*    */     }
/* 32 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized ISASServer getServer(String sessionID) throws InvalidSessionException {
/* 37 */     ISASServer instance = null;
/*    */     try {
/* 39 */       ClassLoader cl = getClass().getClassLoader();
/* 40 */       if (SystemUtil.isClientVM())
/*    */       {
/* 42 */         cl = (ClassLoader)Class.forName("com.eoos.gm.tis2web.sas.client.system.ClientClassLoader").newInstance();
/*    */       }
/*    */       
/*    */       try {
/* 46 */         instance = (ISASServer)cl.loadClass(SystemUtil.isClientVM() ? "com.eoos.gm.tis2web.sas.server.implementation.clientside.SASServerRemoteProxy" : "com.eoos.gm.tis2web.sas.server.implementation.serverside.SASServer").getMethod("getInstance", new Class[] { String.class }).invoke(null, new Object[] { sessionID });
/* 47 */       } catch (InvocationTargetException e) {
/* 48 */         Util.rethrow(e.getTargetException());
/*    */       }
/*    */     
/* 51 */     } catch (InvalidSessionException e) {
/* 52 */       throw e;
/* 53 */     } catch (Exception e) {
/* 54 */       log.error("unable to instantiate SASServer instance - exception:" + e, e);
/* 55 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 57 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\system\SASServerProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */