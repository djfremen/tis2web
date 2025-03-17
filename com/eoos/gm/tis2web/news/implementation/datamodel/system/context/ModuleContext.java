/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.system.context;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
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
/* 17 */   protected static Map instances = new HashMap<Object, Object>();
/*    */   
/*    */   protected String sessionID;
/*    */ 
/*    */   
/*    */   public ModuleContext(String sessionID) {
/* 23 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public static synchronized ModuleContext getInstance(String sessionID) {
/* 27 */     ModuleContext instance = (ModuleContext)instances.get(sessionID);
/* 28 */     if (instance == null) {
/* 29 */       instance = new ModuleContext(sessionID);
/* 30 */       instances.put(sessionID, instance);
/*    */     } 
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public ClientContext getClientContext() {
/* 36 */     return ClientContextProvider.getInstance().getContext(this.sessionID);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\system\context\ModuleContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */