/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.numbersearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.IconElement;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.ListElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchResultListElement
/*    */   extends ListElement
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public SearchResultListElement(ClientContext context, DataRetrievalAbstraction.DataCallback dataCallback) {
/* 30 */     super(dataCallback);
/* 31 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 35 */     return 2;
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 39 */     final SITOCElement node = (SITOCElement)data;
/*    */     
/* 41 */     if (columnIndex == 0) {
/* 42 */       String imageUrl = "pic/common/leaf-icon.gif";
/* 43 */       String tooltip = "";
/*    */       try {
/* 45 */         if (node.isSIO()) {
/* 46 */           SIO sio = (SIO)node;
/* 47 */           if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 48 */             tooltip = (String)sio.getProperty((SITOCProperty)SIOProperty.LU);
/*    */           }
/*    */         } 
/* 51 */       } catch (Exception e) {}
/*    */       
/* 53 */       return (HtmlElement)new IconElement(imageUrl, tooltip);
/*    */     } 
/* 55 */     LinkElement link = new LinkElement(this.context.createID(), null) {
/*    */         protected String getLabel() {
/* 57 */           return node.getLabel(LocaleInfoProvider.getInstance().getLocale(SearchResultListElement.this.context.getLocale()));
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 61 */           NumberSearchPanel nsp = NumberSearchPanel.getInstance(SearchResultListElement.this.context);
/* 62 */           nsp.showDocument(node);
/*    */           
/* 64 */           HtmlElementContainer container = getContainer();
/* 65 */           while (container.getContainer() != null) {
/* 66 */             container = container.getContainer();
/*    */           }
/* 68 */           return container;
/*    */         }
/*    */       };
/* 71 */     return (HtmlElement)link;
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement getHeader(int columnIndex) {
/* 76 */     if (columnIndex == 0) {
/* 77 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("si.search.result"));
/*    */     }
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 84 */     if (columnIndex == 0)
/* 85 */       map.put("colspan", "2"); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\numbersearch\SearchResultListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */