/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
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
/*    */ public class DocumentIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/* 24 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   public DocumentIFrame(ClientContext context) {
/* 28 */     super("documentiframepanel");
/* 29 */     this.context = context;
/*    */     
/* 31 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 32 */     this.height = scp.getDisplayHeight();
/* 33 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 35 */             DocumentIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 41 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 45 */     return DocumentPage.getInstance(this.context).getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 49 */     return "100%";
/*    */   }
/*    */   
/*    */   public void onChange(Integer newHeight) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\DocumentIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */