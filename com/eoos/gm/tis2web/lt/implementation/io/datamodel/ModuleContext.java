/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
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
/*    */ 
/*    */ 
/*    */ public class ModuleContext
/*    */ {
/*    */   public static final String PAGEID_HOME = "LT_DISPLAY";
/*    */   public static final String PAGEID_BOOKMARKS = "LT_BOOKMARK";
/*    */   public static final String PAGEID_TEXT_SEARCH = "LT_TEXT_SEARCH";
/* 23 */   protected static Map instances = new HashMap<Object, Object>();
/*    */   
/*    */   protected String sessionID;
/*    */   
/* 27 */   protected String pageIdentifier = null;
/*    */   
/* 29 */   protected String sit = null;
/*    */ 
/*    */   
/*    */   public ModuleContext(String sessionID) {
/* 33 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public static synchronized ModuleContext getInstance(String sessionID) {
/* 37 */     ModuleContext instance = (ModuleContext)instances.get(sessionID);
/* 38 */     if (instance == null) {
/* 39 */       instance = new ModuleContext(sessionID);
/* 40 */       instances.put(sessionID, instance);
/*    */     } 
/* 42 */     return instance;
/*    */   }
/*    */   
/*    */   public void update() {
/* 46 */     SharedContextProxy.getInstance(getClientContext()).update();
/*    */   }
/*    */   
/*    */   public ClientContext getClientContext() {
/* 50 */     return ClientContextProvider.getInstance().getContext(this.sessionID);
/*    */   }
/*    */   
/*    */   public SharedContextProxy getSharedContextProxy() {
/* 54 */     return SharedContextProxy.getInstance(getClientContext());
/*    */   }
/*    */   
/*    */   public void setPageIdentifier(String identifier) {
/* 58 */     this.pageIdentifier = identifier;
/*    */   }
/*    */   
/*    */   public String getPageIdentifier() {
/* 62 */     return (this.pageIdentifier != null) ? this.pageIdentifier : "LT_DISPLAY";
/*    */   }
/*    */   
/*    */   public void setSIT(String sit) {
/* 66 */     this.sit = sit;
/*    */   }
/*    */   
/*    */   public String getSIT() {
/* 70 */     return this.sit;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\ModuleContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */