/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
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
/*    */ public class PageLinkForwardAction
/*    */   extends PageLinkAction
/*    */ {
/*    */   public PageLinkForwardAction(ClientContext context, String sioId, DocumentPage docPage) {
/* 22 */     super(context, sioId, docPage);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams, HistoryLink hs, StepResults stepResults) {
/* 26 */     hs.getHistory().push();
/* 27 */     Object ret = onClick(submitParams, hs);
/* 28 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\PageLinkForwardAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */