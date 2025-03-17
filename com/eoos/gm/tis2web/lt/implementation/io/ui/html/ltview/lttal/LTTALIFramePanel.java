/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
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
/*    */ public class LTTALIFramePanel
/*    */   extends IFrameElement
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public LTTALIFramePanel(ClientContext context) {
/* 24 */     super("ltlistiframepanel");
/* 25 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 29 */     return LTUIContext.getInstance(this.context).getDisplayHeight().toString();
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 33 */     return LTListPage.getInstance(this.context).getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 37 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTTALIFramePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */