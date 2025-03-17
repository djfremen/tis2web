/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BulletinDisplayPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private IFrameElement iframe;
/*    */   private DocumentPage page;
/*    */   private Integer height;
/*    */   
/*    */   public BulletinDisplayPanel(ClientContext context) {
/* 33 */     this.page = new DocumentPage(context);
/*    */     
/* 35 */     this.iframe = new IFrameElement(context.createID()) {
/*    */         protected String getWidth() {
/* 37 */           return "100%";
/*    */         }
/*    */         
/*    */         protected String getHeight() {
/* 41 */           return String.valueOf(BulletinDisplayPanel.this.height);
/*    */         }
/*    */         
/*    */         protected String getSourceURL(Map params) {
/* 45 */           return BulletinDisplayPanel.this.page.getURL(params);
/*    */         }
/*    */       };
/*    */     
/* 49 */     addElement((HtmlElement)this.iframe);
/*    */     
/* 51 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 52 */     this.height = scp.getDisplayHeight();
/* 53 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 55 */             BulletinDisplayPanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 61 */     return this.page;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 65 */     return this.iframe.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public synchronized void setPage(SIOTSB tsb) {
/* 69 */     this.page.setPage(tsb);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\document\BulletinDisplayPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */