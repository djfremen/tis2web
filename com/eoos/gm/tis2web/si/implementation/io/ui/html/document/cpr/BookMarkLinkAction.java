/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElementContainer;
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
/*    */ public class BookMarkLinkAction
/*    */   extends LinkAction
/*    */ {
/*    */   public Object onClick(Map submitParams, HistoryLink hs, StepResults stepResults) {
/* 21 */     return onClick(submitParams, hs);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams, HistoryLink hs) {
/* 25 */     HtmlElementContainer container = hs.getContainer();
/* 26 */     while (container.getContainer() != null) {
/* 27 */       container = container.getContainer();
/*    */     }
/* 29 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\BookMarkLinkAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */