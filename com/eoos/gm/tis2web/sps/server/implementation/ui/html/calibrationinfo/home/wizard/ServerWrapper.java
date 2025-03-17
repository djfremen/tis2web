/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.TypeSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerWrapper
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(ServerWrapper.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private ServerWrapper(ClientContext context) {
/* 24 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static ServerWrapper getInstance(ClientContext context) {
/* 28 */     synchronized (context.getLockObject()) {
/* 29 */       ServerWrapper instance = (ServerWrapper)context.getObject(ServerWrapper.class);
/* 30 */       if (instance == null) {
/* 31 */         instance = new ServerWrapper(context);
/* 32 */         context.storeObject(ServerWrapper.class, instance);
/*    */       } 
/* 34 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object execute(AttributeValueMap map) throws Exception {
/*    */     try {
/* 40 */       return SPSServer.getInstance(this.context.getSessionID()).execute(map);
/* 41 */     } catch (RequestException e) {
/* 42 */       if (e.getRequest() instanceof TypeSelectionRequest) {
/* 43 */         TypeSelectionRequest request = (TypeSelectionRequest)e.getRequest();
/*    */         
/* 45 */         for (Iterator iter = request.getOptions().iterator(); iter.hasNext(); ) {
/* 46 */           Object type = iter.next();
/* 47 */           if (type instanceof SPSProgrammingType && (
/* 48 */             (SPSProgrammingType)type).getID().equals(SPSProgrammingType.NORMAL)) {
/* 49 */             log.debug("automatically selected programming type \"normal\" ");
/* 50 */             map.set(request.getAttribute(), (Value)type);
/* 51 */             return execute(map);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 57 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\ServerWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */