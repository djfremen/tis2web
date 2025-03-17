/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.extended;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.TOCPage;
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
/*    */ 
/*    */ public class TOCIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   public static final int NAVIGATION_MODE_BASIC = 0;
/*    */   public static final int NAVIGATION_MODE_EXTENDED = 1;
/*    */   protected ClientContext context;
/* 29 */   protected Integer height = null;
/*    */   
/*    */   private TOCPage tocPage;
/*    */ 
/*    */   
/*    */   public TOCIFrame(ClientContext context, int navigationMode) {
/* 35 */     super("tociframe");
/* 36 */     this.context = context;
/*    */     
/* 38 */     this.tocPage = new TOCPage(context, navigationMode);
/*    */     
/* 40 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 41 */     this.height = scp.getDisplayHeight();
/* 42 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 44 */             TOCIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getHeight() {
/* 51 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 55 */     return this.tocPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 59 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\extended\TOCIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */