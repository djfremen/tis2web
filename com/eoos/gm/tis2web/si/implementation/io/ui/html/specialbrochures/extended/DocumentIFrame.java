/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.extended;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.DocumentPageRetrieval;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.DocumentPage;
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
/*    */ public class DocumentIFrame
/*    */   extends IFrameElement
/*    */   implements DocumentPageRetrieval
/*    */ {
/* 23 */   protected Integer height = null;
/*    */   
/* 25 */   protected DocumentPage docPage = null;
/*    */ 
/*    */   
/*    */   public DocumentIFrame(ClientContext context) {
/* 29 */     super("documentiframe");
/*    */     
/* 31 */     this.docPage = new DocumentPage(context);
/*    */     
/* 33 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 34 */     this.height = scp.getDisplayHeight();
/* 35 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 37 */             DocumentIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 43 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 47 */     return this.docPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 51 */     return "100%";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onChange(Integer newHeight) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 63 */     return this.docPage;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\extended\DocumentIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */