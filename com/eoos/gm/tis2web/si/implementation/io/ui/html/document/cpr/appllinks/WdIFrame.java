/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.element.IFrameElement;
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
/*    */ public class WdIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   DocumentPage wdIFramelPage;
/* 26 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   public WdIFrame(ClientContext context, SIO node, DocumentPage wdIFramelPage) {
/* 30 */     super("ProtocolIFrame");
/* 31 */     this.context = context;
/* 32 */     this.wdIFramelPage = wdIFramelPage;
/* 33 */     wdIFramelPage.setIsMainFrame(false);
/* 34 */     wdIFramelPage.setPage(node);
/* 35 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 36 */     this.height = scp.getDisplayHeight();
/* 37 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 39 */             WdIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 45 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 49 */     return this.wdIFramelPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 53 */     return "100%";
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 57 */     this.context.unregisterDispatchable((Dispatchable)this.wdIFramelPage);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\WdIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */