/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.document.page.DocumentPage;
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
/*    */ public class DocumentIFramePanel
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public DocumentIFramePanel(ClientContext context) {
/* 25 */     super("documentiframepanel");
/* 26 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 30 */     return LTUIContext.getInstance(this.context).getDisplayHeight().toString();
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 34 */     return DocumentPage.getInstance(this.context).getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 38 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\document\DocumentIFramePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */