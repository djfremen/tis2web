/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks;
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
/*    */ 
/*    */ public class DocumentViewPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private IFrameElement iframe;
/*    */   private LTListPage page;
/*    */   private Integer height;
/* 31 */   protected SIOLT currentElement = null;
/*    */ 
/*    */   
/*    */   public DocumentViewPanel(ClientContext context) {
/* 35 */     LTListPage.Callback callback = new LTListPage.Callback()
/*    */       {
/*    */         public SIOLT getCurrentElement() {
/* 38 */           return DocumentViewPanel.this.currentElement;
/*    */         }
/*    */         
/*    */         public boolean enableBookmarks() {
/* 42 */           return false;
/*    */         }
/*    */       };
/*    */     
/* 46 */     this.page = new LTListPage(context, callback);
/*    */     
/* 48 */     this.iframe = new IFrameElement(context.createID()) {
/*    */         protected String getWidth() {
/* 50 */           return "100%";
/*    */         }
/*    */         
/*    */         protected String getHeight() {
/* 54 */           return String.valueOf(DocumentViewPanel.this.height);
/*    */         }
/*    */         
/*    */         protected String getSourceURL(Map params) {
/* 58 */           return DocumentViewPanel.this.page.getURL(params);
/*    */         }
/*    */       };
/*    */     
/* 62 */     addElement((HtmlElement)this.iframe);
/*    */     
/* 64 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 65 */     this.height = scp.getDisplayHeight();
/* 66 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 68 */             DocumentViewPanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public LTListPage getLTListPage() {
/* 74 */     return this.page;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 79 */     return this.iframe.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public synchronized void setPage(Object _node, AsyncMethodCallback callback) {
/* 83 */     this.currentElement = (SIOLT)_node;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\bookmarks\DocumentViewPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */