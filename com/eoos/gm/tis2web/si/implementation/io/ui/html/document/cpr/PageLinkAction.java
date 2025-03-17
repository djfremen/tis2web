/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DefaultDocumentContainer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
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
/*    */ public class PageLinkAction
/*    */   extends LinkAction
/*    */ {
/* 21 */   protected String sioId = null;
/*    */   
/* 23 */   protected ClientContext context = null;
/*    */   
/* 25 */   protected DocumentPage docPage = null;
/*    */   
/*    */   public PageLinkAction(ClientContext context, String sioId, DocumentPage docPage) {
/* 28 */     this.context = context;
/* 29 */     this.docPage = docPage;
/* 30 */     this.sioId = sioId;
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams, HistoryLink hs) {
/* 34 */     hs.getHistory().pushBack();
/*    */     
/* 36 */     SIO sio = null;
/*    */     try {
/* 38 */       sio = SIDataAdapterFacade.getInstance(this.context).getSI().getSIO(Integer.parseInt(this.sioId));
/* 39 */     } catch (IllegalArgumentException e) {
/* 40 */       return new DefaultDocumentContainer(this.context, this.context.getMessage("si.document.not.available"));
/*    */     } 
/* 42 */     this.docPage.setLinkFrom(hs);
/* 43 */     this.docPage.setCPR(sio);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     return this.docPage.getPage(submitParams);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\PageLinkAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */