/*    */ package com.eoos.gm.tis2web.news.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.page.DocumentPage;
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
/*    */ public class DocumentIFrameElement
/*    */   extends IFrameElement
/*    */ {
/* 22 */   private Integer height = null;
/*    */   
/*    */   protected DocumentPage embeddedPage;
/*    */ 
/*    */   
/*    */   public DocumentIFrameElement(ClientContext context) {
/* 28 */     super("documentiframe");
/*    */     
/* 30 */     this.embeddedPage = new DocumentPage(context);
/*    */     
/* 32 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 33 */     this.height = scp.getDisplayHeight();
/* 34 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 36 */             DocumentIFrameElement.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 42 */     return this.embeddedPage;
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 46 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 50 */     return this.embeddedPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 54 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\home\DocumentIFrameElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */