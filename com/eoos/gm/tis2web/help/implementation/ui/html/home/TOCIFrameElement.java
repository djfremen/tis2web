/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.page.TOCPage;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.page.DocumentPage;
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
/*    */ public class TOCIFrameElement
/*    */   extends IFrameElement
/*    */ {
/* 24 */   private Integer height = null;
/*    */   
/*    */   protected TOCPage embeddedPage;
/*    */ 
/*    */   
/*    */   public TOCIFrameElement(ClientContext context, Map parameter, DocumentPage documentPage) {
/* 30 */     super("tociframe");
/* 31 */     this.embeddedPage = new TOCPage(context, parameter, documentPage);
/*    */     
/* 33 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 34 */     this.height = scp.getDisplayHeight();
/* 35 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 37 */             TOCIFrameElement.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getHeight() {
/* 44 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 48 */     return this.embeddedPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 52 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\home\TOCIFrameElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */