/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.ListElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RelatedLUListElement
/*    */   extends ListElement
/*    */ {
/*    */   protected SIOLUDocumentContainer documentContainer;
/*    */   protected ClientContext context;
/*    */   
/*    */   public RelatedLUListElement(SIOLUDocumentContainer container, List relatedLUs) {
/* 31 */     super(relatedLUs);
/* 32 */     this.context = container.getContext();
/* 33 */     this.documentContainer = container;
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 37 */     return 2;
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 41 */     if (columnIndex == 0) {
/* 42 */       return (HtmlElement)new HtmlLabel("-");
/*    */     }
/* 44 */     final SIOLU lu = (SIOLU)data;
/* 45 */     LinkElement link = new LinkElement(this.context.createID(), null) {
/*    */         protected String getLabel() {
/* 47 */           return lu.getLabel(LocaleInfoProvider.getInstance().getLocale(RelatedLUListElement.this.context.getLocale()));
/*    */         }
/*    */         
/*    */         protected String getTargetFrame() {
/* 51 */           return "_top";
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 55 */           RelatedLUListElement.this.documentContainer.getDocumentPage();
/*    */           
/* 57 */           SpecialBrochuresContext.getInstance(RelatedLUListElement.this.context).setSelectedSIO((SIO)lu, false);
/* 58 */           return MainPage.getInstance(RelatedLUListElement.this.context);
/*    */         }
/*    */       };
/* 61 */     return (HtmlElement)link;
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement getHeader(int columnIndex) {
/* 66 */     if (columnIndex == 0) {
/* 67 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("si.related.literature.units"));
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 74 */     if (columnIndex == 0) {
/* 75 */       map.put("colspan", "2");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getBookmark() {
/* 80 */     return "relatedlus";
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 84 */     return HtmlAnchorRenderer.getInstance().getHtmlCode(getBookmark()) + super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\content\RelatedLUListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */