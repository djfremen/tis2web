/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.ListElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SIOLinkElement extends ListElement {
/*    */   private ClientContext context;
/*    */   private SIOLUDocumentContainer container;
/*    */   
/*    */   public SIOLinkElement(ClientContext context, SIOLUDocumentContainer container, List links) {
/* 20 */     super(links);
/* 21 */     this.context = context;
/* 22 */     this.container = container;
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 26 */     return 2;
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 30 */     if (columnIndex == 0) {
/* 31 */       return (HtmlElement)new HtmlLabel("-");
/*    */     }
/* 33 */     final Replacer.SIOLink reference = (Replacer.SIOLink)data;
/* 34 */     LinkElement link = new LinkElement(this.context.createID(), reference.getBookmark()) {
/*    */         protected String getLabel() {
/* 36 */           return SIOLinkElement.this.context.getLabel("vc.attributename.modelyear") + " " + reference.getQualifier();
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 40 */           SIO sio = null;
/*    */           try {
/* 42 */             sio = SIDataAdapterFacade.getInstance(SIOLinkElement.this.context).getSI().getSIO(reference.getSIO());
/* 43 */           } catch (IllegalArgumentException e) {}
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 48 */           DocumentPage dp = SIOLinkElement.this.container.getDocumentPage();
/* 49 */           DocumentContainer dc = dp.setPage(sio);
/* 50 */           dc.setPredecessor(SIOLinkElement.this.container);
/* 51 */           return dp.getPage(params);
/*    */         }
/*    */       };
/* 54 */     return (HtmlElement)link;
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement getHeader(int columnIndex) {
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   protected boolean enableHeader() {
/* 63 */     return false;
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 67 */     if (columnIndex == 0) {
/* 68 */       map.put("colspan", "2");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 73 */     return super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\SIOLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */