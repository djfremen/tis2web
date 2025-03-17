/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content;
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
/*    */   
/*    */   private DocumentPage docPage;
/*    */   
/* 26 */   private int sioID = 0;
/*    */   
/*    */   private DocumentContainer container;
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   public SIODocLinkElement(String parameterName, String targetBookmark, DocumentPage docPage, DocumentContainer container, ClientContext context) {
/* 33 */     super(parameterName, targetBookmark);
/* 34 */     this.docPage = docPage;
/* 35 */     this.container = container;
/* 36 */     this.context = context;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 44 */     SIO sio = SIDataAdapterFacade.getInstance(this.context).getSI().getSIO(this.sioID);
/* 45 */     DocumentPage dp = this.docPage;
/* 46 */     AsyncMethodCallback callback = new AsyncMethodCallback() {
/*    */         public void onFinished(Object result) {
/* 48 */           DocumentContainer dc = (DocumentContainer)result;
/* 49 */           dc.setPredecessor(SIODocLinkElement.this.container);
/*    */         }
/*    */       };
/*    */     
/* 53 */     dp.setPage(sio, callback);
/* 54 */     return dp.getPage(params);
/*    */   }
/*    */   
/*    */   public String getParameterName() {
/* 58 */     return this.parameterName;
/*    */   }
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 62 */     this.clicked = false;
/* 63 */     Iterator<String> iter = submitParams.keySet().iterator();
/* 64 */     while (iter.hasNext()) {
/*    */       try {
/* 66 */         String key = iter.next();
/* 67 */         if (key.indexOf(this.parameterName) == 0) {
/* 68 */           this.clicked = true;
/*    */           try {
/* 70 */             this.sioID = Integer.parseInt(key.substring(this.parameterName.length()));
/* 71 */           } catch (NumberFormatException e) {
/* 72 */             throw new RuntimeException();
/*    */           } 
/*    */         } 
/* 75 */       } catch (Exception e) {
/* 76 */         log.error("error - " + e, e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 83 */     return this.renderingCallback.getLink();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\content\SIODocLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */