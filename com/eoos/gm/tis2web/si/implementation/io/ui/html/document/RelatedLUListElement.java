/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.html.renderer.HtmlTagRenderer;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelatedLUListElement
/*     */   extends ListElement
/*     */ {
/*     */   private SIOLUDocumentContainer documentContainer;
/*     */   private ClientContext context;
/*     */   protected RendererCallbackRLU rendererCallbackRLU;
/*     */   
/*     */   public RelatedLUListElement(SIOLUDocumentContainer container, List relatedLUs) {
/*  35 */     super(relatedLUs);
/*  36 */     this.rendererCallbackRLU = new RendererCallbackRLU();
/*  37 */     this.context = container.getContext();
/*  38 */     this.documentContainer = container;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  42 */     return 2;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*  46 */     if (columnIndex == 0) {
/*  47 */       return (HtmlElement)new HtmlLabel("-");
/*     */     }
/*  49 */     final SIOLU lu = (SIOLU)data;
/*  50 */     List elements = getElements();
/*  51 */     if (elements != null) {
/*  52 */       Iterator it = elements.iterator();
/*  53 */       while (it.hasNext()) {
/*  54 */         Object element = it.next();
/*  55 */         if (element instanceof LinkElement) {
/*  56 */           Object reference = ((LinkElement)element).getValue();
/*  57 */           if (reference instanceof SIOLU && reference.equals(lu)) {
/*  58 */             return (HtmlElement)element;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  63 */     LinkElement link = new LinkElement(this.context.createID(), null)
/*     */       {
/*     */         Object value;
/*     */         
/*     */         protected String getLabel() {
/*  68 */           return lu.getLabel(LocaleInfoProvider.getInstance().getLocale(RelatedLUListElement.this.context.getLocale()));
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/*  72 */           return "_top";
/*     */         }
/*     */         
/*     */         public void setValue(Object value) {
/*  76 */           this.value = value;
/*     */         }
/*     */ 
/*     */         
/*     */         public Object getValue() {
/*  81 */           return this.value;
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  85 */           DocumentPage dp = RelatedLUListElement.this.documentContainer.getDocumentPage();
/*  86 */           DocumentContainer dc = dp.setPage(lu);
/*  87 */           dc.setPredecessor(RelatedLUListElement.this.documentContainer);
/*  88 */           return MainPage.getInstance(RelatedLUListElement.this.context).getHtmlCode(params);
/*     */         }
/*     */       };
/*  91 */     link.setValue(lu);
/*  92 */     return (HtmlElement)link;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  97 */     if (columnIndex == 0) {
/*  98 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("si.related.literature.units"));
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 105 */     if (columnIndex == 0) {
/* 106 */       map.put("colspan", "2");
/*     */     }
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/* 111 */     return "relatedlus";
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 115 */     return HtmlAnchorRenderer.getInstance().getHtmlCode(getBookmark()) + getHtmlCode();
/*     */   }
/*     */   
/*     */   protected String getHtmlCode() {
/* 119 */     String html = HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)this.rendererCallbackRLU);
/* 120 */     return html;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class RendererCallbackRLU
/*     */     extends HtmlTableRenderer.CallbackAdapter
/*     */   {
/* 132 */     private List data = RelatedLUListElement.this.getData();
/* 133 */     private boolean header = RelatedLUListElement.this.enableHeader();
/*     */ 
/*     */     
/*     */     public void init(Map params) {}
/*     */ 
/*     */     
/*     */     public int getRowCount() {
/* 140 */       int count = RelatedLUListElement.this.enableHeader() ? 1 : 0;
/* 141 */       return this.data.size() + count;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 145 */       return RelatedLUListElement.this.getColumnCount();
/*     */     }
/*     */     
/*     */     public String getContent(int rowIndex, int columnIndex) {
/* 149 */       HtmlElement element = null;
/* 150 */       if (rowIndex == 0 && this.header) {
/* 151 */         element = RelatedLUListElement.this.getHeader(columnIndex);
/*     */       } else {
/* 153 */         element = RelatedLUListElement.this.getContent(this.data.get(this.header ? (rowIndex - 1) : rowIndex), columnIndex);
/*     */       } 
/* 155 */       if (element != null) {
/* 156 */         RelatedLUListElement.this.addElement(element);
/* 157 */         return element.getHtmlCode(null);
/*     */       } 
/* 159 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(final int rowIndex, final int columnIndex) {
/* 164 */       if (rowIndex == 0 && this.header) {
/* 165 */         return new HtmlTagRenderer.AdditionalAttributes() {
/*     */             public void getAdditionalAttributes(Map map) {
/* 167 */               RelatedLUListElement.this.getAdditionalAttributesHeader(columnIndex, map);
/*     */             }
/*     */           };
/*     */       }
/* 171 */       return new HtmlTagRenderer.AdditionalAttributes() {
/*     */           public void getAdditionalAttributes(Map map) {
/* 173 */             RelatedLUListElement.this.getAdditionalAttributesContent(rowIndex - 1, columnIndex, map);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesRow(int rowIndex) {
/* 180 */       return null;
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map map) {
/* 184 */       RelatedLUListElement.this.getAdditionalAttributesTable(map);
/*     */     }
/*     */     
/*     */     public boolean isHeader(int rowIndex, int columnIndex) {
/* 188 */       if (rowIndex == 0 && this.header) {
/* 189 */         return true;
/*     */       }
/* 191 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\RelatedLUListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */