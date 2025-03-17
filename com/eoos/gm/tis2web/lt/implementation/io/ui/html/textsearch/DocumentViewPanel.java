/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
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
/*    */   private LTListPage page;
/*    */   private Integer height;
/* 30 */   protected SIOLT currentElement = null;
/*    */ 
/*    */   
/*    */   public DocumentViewPanel(ClientContext context) {
/* 34 */     LTListPage.Callback callback = new LTListPage.Callback()
/*    */       {
/*    */         public SIOLT getCurrentElement() {
/* 37 */           return DocumentViewPanel.this.currentElement;
/*    */         }
/*    */         
/*    */         public boolean enableBookmarks() {
/* 41 */           return true;
/*    */         }
/*    */       };
/* 44 */     this.page = new LTListPage(context, callback);
/*    */     
/* 46 */     this.iframe = new IFrameElement("docviewiframepanel") {
/*    */         protected String getWidth() {
/* 48 */           return "100%";
/*    */         }
/*    */         
/*    */         protected String getHeight() {
/* 52 */           return String.valueOf(DocumentViewPanel.this.height);
/*    */         }
/*    */         
/*    */         protected String getSourceURL(Map params) {
/* 56 */           return DocumentViewPanel.this.page.getURL(params);
/*    */         }
/*    */       };
/*    */     
/* 60 */     addElement((HtmlElement)this.iframe);
/*    */     
/* 62 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 63 */     this.height = scp.getDisplayHeight();
/* 64 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 66 */             DocumentViewPanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public LTListPage getListPage() {
/* 73 */     return this.page;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 77 */     return this.iframe.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public synchronized void setPage(Object _node, AsyncMethodCallback callback) {
/* 81 */     this.currentElement = (SIOLT)_node;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\DocumentViewPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */