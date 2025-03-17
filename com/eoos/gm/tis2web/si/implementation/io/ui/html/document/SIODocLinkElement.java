/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.util.AsyncMethodCallback;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SIODocLinkElement
/*    */   extends LinkElement
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(SIODocLinkElement.class);
/*    */   private DocumentPage docPage;
/* 24 */   private int sioID = 0;
/*    */   private DocumentContainer container;
/*    */   private ClientContext context;
/*    */   
/*    */   public SIODocLinkElement(ClientContext context, String parameterName, String targetBookmark, DocumentPage docPage, DocumentContainer container) {
/* 29 */     super(parameterName, targetBookmark);
/* 30 */     this.context = context;
/* 31 */     this.docPage = docPage;
/* 32 */     this.container = container;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 40 */     SIO sio = SIDataAdapterFacade.getInstance(this.context).getSI().getSIO(this.sioID);
/* 41 */     DocumentPage dp = this.docPage;
/* 42 */     AsyncMethodCallback callback = new AsyncMethodCallback() {
/*    */         public void onFinished(Object result) {
/* 44 */           DocumentContainer dc = (DocumentContainer)result;
/* 45 */           dc.setPredecessor(SIODocLinkElement.this.container);
/*    */         }
/*    */       };
/*    */     
/* 49 */     dp.setPage(sio, callback);
/*    */ 
/*    */     
/* 52 */     return dp.getMainPage();
/*    */   }
/*    */   
/*    */   public String getParameterName() {
/* 56 */     return this.parameterName;
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 60 */     return "_top";
/*    */   }
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 64 */     this.clicked = false;
/* 65 */     Iterator<String> iter = submitParams.keySet().iterator();
/* 66 */     while (iter.hasNext()) {
/*    */       try {
/* 68 */         String key = iter.next();
/* 69 */         if (key.indexOf(this.parameterName) == 0) {
/* 70 */           this.clicked = true;
/*    */           try {
/* 72 */             this.sioID = Integer.parseInt(key.substring(this.parameterName.length()));
/* 73 */           } catch (NumberFormatException e) {
/* 74 */             throw new RuntimeException();
/*    */           } 
/*    */         } 
/* 77 */       } catch (Exception e) {
/* 78 */         log.error("error - " + e, e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 85 */     return this.renderingCallback.getLink();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\SIODocLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */