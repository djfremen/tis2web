/*    */ package com.eoos.gm.tis2web.ctoc.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CTOCServiceProvider
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(CTOCServiceProvider.class);
/*    */ 
/*    */   
/*    */   public static class Type
/*    */   {
/*    */     private Type() {}
/*    */   }
/*    */   
/* 23 */   public static final Type HELP = new Type();
/*    */   
/* 25 */   public static final Type NEWS = new Type();
/*    */   
/* 27 */   public static final Type BASIC = new Type();
/*    */   
/* 29 */   private Map services = new HashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized CTOCServiceProvider getInstance() {
/* 36 */     CTOCServiceProvider instance = (CTOCServiceProvider)ApplicationContext.getInstance().getObject(CTOCServiceProvider.class);
/* 37 */     if (instance == null) {
/* 38 */       instance = new CTOCServiceProvider();
/* 39 */       ApplicationContext.getInstance().storeObject(CTOCServiceProvider.class, instance);
/*    */     } 
/* 41 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized CTOCService getService(Type type) {
/* 45 */     CTOCService service = (CTOCService)this.services.get(type);
/* 46 */     if (service == null) {
/* 47 */       service = createService(type);
/* 48 */       this.services.put(type, service);
/*    */     } 
/* 50 */     return service;
/*    */   }
/*    */   
/*    */   private CTOCService createService(Type type) {
/* 54 */     CTOCService service = null;
/* 55 */     String className = null;
/* 56 */     if (type == HELP) {
/* 57 */       className = "com.eoos.gm.tis2web.ctoc.implementation.service.HelpCTOCServiceImpl";
/* 58 */     } else if (type == NEWS) {
/* 59 */       className = "com.eoos.gm.tis2web.ctoc.implementation.service.NewsCTOCServiceImpl";
/* 60 */     } else if (type == BASIC) {
/* 61 */       className = "com.eoos.gm.tis2web.ctoc.implementation.service.CTOCServiceImpl";
/*    */     } else {
/* 63 */       throw new IllegalArgumentException("unknown type");
/*    */     } 
/*    */     
/*    */     try {
/* 67 */       Class<?> clazz = Class.forName(className);
/* 68 */       service = (CTOCService)clazz.newInstance();
/* 69 */     } catch (Exception e) {
/* 70 */       log.error("unable to instantiate CTOCService (" + className + ") - exception:" + e);
/* 71 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 73 */     return service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\CTOCServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */