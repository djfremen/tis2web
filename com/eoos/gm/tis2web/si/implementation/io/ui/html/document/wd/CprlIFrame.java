/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd;
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
/*    */ public class CprlIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   DocumentPage cprIFramelPage;
/* 26 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   public CprlIFrame(ClientContext context, SIO docCont, DocumentPage docPage) {
/* 30 */     super("ProtocolIFrame");
/* 31 */     this.context = context;
/* 32 */     this.cprIFramelPage = docPage;
/* 33 */     this.cprIFramelPage.setIsMainFrame(false);
/* 34 */     this.cprIFramelPage.setCprHistory(DocumentPage.getInstance(context).getCprHistory());
/*    */     
/* 36 */     this.cprIFramelPage.setPage(docCont);
/* 37 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 38 */     this.height = scp.getDisplayHeight();
/* 39 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 41 */             CprlIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 47 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 51 */     return this.cprIFramelPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 55 */     return "100%";
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 59 */     this.context.unregisterDispatchable((Dispatchable)this.cprIFramelPage);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\wd\CprlIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */