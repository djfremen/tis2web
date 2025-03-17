/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs;
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
/*    */ public class RefDocIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   DocumentPage wdIFramelPage;
/* 26 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   public RefDocIFrame(ClientContext context, SIO node) {
/* 30 */     super("DialogIFrame");
/* 31 */     this.context = context;
/* 32 */     this.wdIFramelPage = new DocumentPage(context);
/* 33 */     this.wdIFramelPage.setLinkTargetFrame(this);
/* 34 */     this.wdIFramelPage.setIsMainFrame(false);
/* 35 */     this.wdIFramelPage.setPage(node);
/* 36 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 37 */     this.height = scp.getDisplayHeight();
/* 38 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 40 */             RefDocIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 46 */     return this.wdIFramelPage;
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 50 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 54 */     return this.wdIFramelPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 58 */     return "100%";
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 62 */     this.context.unregisterDispatchable((Dispatchable)this.wdIFramelPage);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\RefDocIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */