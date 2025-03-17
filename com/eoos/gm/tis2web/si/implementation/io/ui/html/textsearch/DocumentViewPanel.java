/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.IFrameElement;
/*    */ import com.eoos.util.AsyncMethodCallback;
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
/*    */ public class DocumentViewPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private IFrameElement iframe;
/*    */   private DocumentPage page;
/*    */   private Integer height;
/*    */   
/*    */   public DocumentViewPanel(ClientContext context) {
/* 31 */     this.page = new DocumentPage(context);
/*    */     
/* 33 */     this.iframe = new IFrameElement("docviewiframepanel") {
/*    */         protected String getWidth() {
/* 35 */           return "100%";
/*    */         }
/*    */         
/*    */         protected String getHeight() {
/* 39 */           return String.valueOf(DocumentViewPanel.this.height);
/*    */         }
/*    */         
/*    */         protected String getSourceURL(Map params) {
/* 43 */           return DocumentViewPanel.this.page.getURL(params);
/*    */         }
/*    */       };
/*    */     
/* 47 */     addElement((HtmlElement)this.iframe);
/*    */     
/* 49 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 50 */     this.height = scp.getDisplayHeight();
/* 51 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 53 */             DocumentViewPanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 60 */     return this.page;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 64 */     return this.iframe.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public synchronized void setPage(Object _node, AsyncMethodCallback callback) {
/* 68 */     this.page.setPage(_node, callback);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\DocumentViewPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */