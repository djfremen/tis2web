/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIContext
/*    */ {
/*    */   private boolean displayDealerVCI = false;
/*    */   private boolean displaySecurityCode = false;
/*    */   
/*    */   private UIContext(ClientContext context) {}
/*    */   
/*    */   public static UIContext getInstance(ClientContext context) {
/* 17 */     synchronized (context.getLockObject()) {
/* 18 */       UIContext instance = (UIContext)context.getObject(UIContext.class);
/* 19 */       if (instance == null) {
/* 20 */         instance = new UIContext(context);
/* 21 */         context.storeObject(UIContext.class, instance);
/*    */       } 
/* 23 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setDisplayDealerVCI(boolean display) {
/* 28 */     this.displayDealerVCI = display;
/*    */   }
/*    */   
/*    */   public boolean displayDealerVCI() {
/* 32 */     return this.displayDealerVCI;
/*    */   }
/*    */   
/*    */   public boolean displaySecurityCode() {
/* 36 */     return this.displaySecurityCode;
/*    */   }
/*    */   
/*    */   public void setDisplaySecurityCode(boolean display) {
/* 40 */     this.displaySecurityCode = display;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\UIContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */