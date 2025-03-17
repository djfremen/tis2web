/*    */ package com.eoos.gm.tis2web.vc.implementation.io.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
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
/*    */   public static final String PAGEID_HOME = "VEHICLE";
/* 19 */   protected static Map instances = new HashMap<Object, Object>();
/*    */   
/*    */   protected String sessionID;
/*    */   
/* 23 */   protected String pageIdentifier = null;
/*    */ 
/*    */   
/*    */   public ModuleContext(String sessionID) {
/* 27 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public static synchronized ModuleContext getInstance(String sessionID) {
/* 31 */     ModuleContext instance = (ModuleContext)instances.get(sessionID);
/* 32 */     if (instance == null) {
/* 33 */       instance = new ModuleContext(sessionID);
/* 34 */       instances.put(sessionID, instance);
/*    */     } 
/* 36 */     return instance;
/*    */   }
/*    */   
/*    */   public void update() {
/* 40 */     SharedContextProxy.getInstance(getClientContext()).update();
/*    */   }
/*    */   
/*    */   public ClientContext getClientContext() {
/* 44 */     return ClientContextProvider.getInstance().getContext(this.sessionID);
/*    */   }
/*    */   
/*    */   public SharedContextProxy getSharedContextProxy() {
/* 48 */     return SharedContextProxy.getInstance(getClientContext());
/*    */   }
/*    */   
/*    */   public void setPageIdentifier(String identifier) {
/* 52 */     this.pageIdentifier = identifier;
/*    */   }
/*    */   
/*    */   public String getPageIdentifier() {
/* 56 */     return (this.pageIdentifier != null) ? this.pageIdentifier : "VEHICLE";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\datamodel\ModuleContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */