/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc.page.TocPage;
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
/*    */ public class TocIFramePanel
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   private TocPage tocPage;
/*    */   
/*    */   public TocIFramePanel(ClientContext context, int tocMode) {
/* 26 */     super("tociframepanel");
/* 27 */     this.context = context;
/*    */     
/* 29 */     this.tocPage = new TocPage(context, tocMode);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getHeight() {
/* 34 */     return LTUIContext.getInstance(this.context).getDisplayHeight().toString();
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 38 */     return this.tocPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 42 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\TocIFramePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */