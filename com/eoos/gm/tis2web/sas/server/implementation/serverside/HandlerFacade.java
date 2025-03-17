/*    */ package com.eoos.gm.tis2web.sas.server.implementation.serverside;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.util.ClassUtil;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandlerFacade
/*    */   implements SecurityRequestHandler
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger(HandlerFacade.class);
/*    */   
/* 25 */   private static HandlerFacade instance = null;
/*    */   
/* 27 */   private List handlers = new LinkedList();
/*    */   
/*    */   private HandlerFacade() {
/* 30 */     Configuration configuration = (Configuration)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 31 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(configuration, "component.sas.adapter.");
/*    */     
/* 33 */     for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 34 */       String key = iter.next();
/* 35 */       if (key.endsWith(".implementation.class")) {
/* 36 */         String handlerID = key.substring(0, key.length() - ".implementation.class".length());
/*    */         
/*    */         try {
/* 39 */           SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "component.sas.adapter." + handlerID + ".");
/* 40 */           String className = subConfigurationWrapper1.getProperty("implementation.class");
/*    */           
/* 42 */           SecurityRequestHandler handler = null;
/*    */           
/* 44 */           Class<?> clazz = Class.forName(className);
/* 45 */           if (ClassUtil.getAllInterfaces(clazz).contains(Configurable.class)) {
/* 46 */             handler = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*    */           } else {
/* 48 */             handler = (SecurityRequestHandler)clazz.newInstance();
/*    */           } 
/*    */           
/* 51 */           this.handlers.add(handler);
/* 52 */           log.debug("created and added SecurityRequestHandler: " + String.valueOf(handler));
/* 53 */         } catch (Exception e) {
/* 54 */           log.warn("unable to create SecurityRequestHandler with id " + handlerID + " - exception: " + e);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized HandlerFacade getInstance() {
/* 62 */     if (instance == null) {
/* 63 */       instance = new HandlerFacade();
/*    */     }
/* 65 */     return instance;
/*    */   }
/*    */   
/*    */   public SecurityAccessResponse handle(SecurityAccessRequest request, String sessionID) throws SecurityRequestHandler.Exception {
/* 69 */     SecurityAccessResponse response = null;
/* 70 */     for (Iterator<SecurityRequestHandler> iter = this.handlers.iterator(); iter.hasNext() && response == null; ) {
/* 71 */       SecurityRequestHandler handler = iter.next();
/* 72 */       response = handler.handle(request, sessionID);
/*    */     } 
/* 74 */     return response;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 78 */     return super.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\HandlerFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */