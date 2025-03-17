/*    */ package com.eoos.gm.tis2web.help.implementation.datamodel.system.context;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModuleContext
/*    */ {
/* 18 */   protected static Map instances = new HashMap<Object, Object>();
/*    */   
/*    */   protected String sessionID;
/*    */   
/* 22 */   protected SIO selectedDocument = null;
/*    */ 
/*    */   
/*    */   public ModuleContext(String sessionID) {
/* 26 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public static synchronized ModuleContext getInstance(String sessionID) {
/* 30 */     ModuleContext instance = (ModuleContext)instances.get(sessionID);
/* 31 */     if (instance == null) {
/* 32 */       instance = new ModuleContext(sessionID);
/* 33 */       instances.put(sessionID, instance);
/*    */     } 
/* 35 */     return instance;
/*    */   }
/*    */   
/*    */   public ClientContext getClientContext() {
/* 39 */     return ClientContextProvider.getInstance().getContext(this.sessionID);
/*    */   }
/*    */   
/*    */   public SIO getSelectedDocument() {
/* 43 */     return this.selectedDocument;
/*    */   }
/*    */   
/*    */   public void setSelectedDocument(SIO document) {
/* 47 */     this.selectedDocument = document;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\system\context\ModuleContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */