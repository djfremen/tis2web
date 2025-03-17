/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.toc.page.TocPage;
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
/*    */ public class TocIFramePanel
/*    */   extends IFrameElement
/*    */ {
/* 23 */   private Integer height = null;
/*    */   
/*    */   private TocPage tocPage;
/*    */ 
/*    */   
/*    */   public TocIFramePanel(ClientContext context, int tocMode) {
/* 29 */     super("tociframepanel");
/* 30 */     this.tocPage = new TocPage(context, tocMode);
/*    */     
/* 32 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 33 */     this.height = scp.getDisplayHeight();
/* 34 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 36 */             TocIFramePanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getHeight() {
/* 43 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 47 */     return this.tocPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 51 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\TocIFramePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */