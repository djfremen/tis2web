/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTUIContext
/*    */ {
/* 16 */   Integer displayHeight = Integer.valueOf(500);
/*    */ 
/*    */   
/* 19 */   private String searchNr = new String();
/*    */ 
/*    */   
/*    */   public LTUIContext(ClientContext context) {
/* 23 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 24 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 26 */             if (newHeight != null) {
/* 27 */               LTUIContext.this.displayHeight = newHeight;
/*    */             }
/*    */           }
/*    */         });
/*    */     
/* 32 */     if (scp.getDisplayHeight() != null) {
/* 33 */       this.displayHeight = scp.getDisplayHeight();
/*    */     }
/*    */   }
/*    */   
/*    */   public static LTUIContext getInstance(ClientContext context) {
/* 38 */     synchronized (context.getLockObject()) {
/* 39 */       LTUIContext instance = (LTUIContext)context.getObject(LTUIContext.class);
/* 40 */       if (instance == null) {
/* 41 */         instance = new LTUIContext(context);
/* 42 */         context.storeObject(LTUIContext.class, instance);
/*    */       } 
/* 44 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Integer getDisplayHeight() {
/* 49 */     return this.displayHeight;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSearchNr() {
/* 58 */     return this.searchNr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSearchNr(String searchNr) {
/* 68 */     this.searchNr = searchNr;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\LTUIContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */