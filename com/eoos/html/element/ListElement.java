/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.html.renderer.HtmlTagRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class ListElement extends HtmlElementContainerBase {
/*     */   private RendererCallback rendererCallback;
/*     */   protected List data;
/*     */   
/*     */   private class RendererCallback extends HtmlTableRenderer.CallbackAdapter {
/*     */     private List data;
/*     */     private boolean header;
/*     */     
/*     */     private RendererCallback() {}
/*     */     
/*     */     public void init(Map params) {
/*  20 */       ListElement.this.removeAllElements();
/*  21 */       this.data = ListElement.this.getData();
/*  22 */       this.header = ListElement.this.enableHeader();
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/*  26 */       int count = ListElement.this.enableHeader() ? 1 : 0;
/*  27 */       return this.data.size() + count;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/*  31 */       return ListElement.this.getColumnCount();
/*     */     }
/*     */     
/*     */     public String getContent(int rowIndex, int columnIndex) {
/*  35 */       HtmlElement element = null;
/*  36 */       if (rowIndex == 0 && this.header) {
/*  37 */         element = ListElement.this.getHeader(columnIndex);
/*     */       } else {
/*  39 */         element = ListElement.this.getContent(this.data.get(this.header ? (rowIndex - 1) : rowIndex), columnIndex);
/*     */       } 
/*  41 */       if (element != null) {
/*  42 */         ListElement.this.addElement(element);
/*  43 */         return element.getHtmlCode(null);
/*     */       } 
/*  45 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(final int rowIndex, final int columnIndex) {
/*  50 */       if (rowIndex == 0 && this.header) {
/*  51 */         return new HtmlTagRenderer.AdditionalAttributes() {
/*     */             public void getAdditionalAttributes(Map map) {
/*  53 */               ListElement.this.getAdditionalAttributesHeader(columnIndex, map);
/*     */             }
/*     */           };
/*     */       }
/*  57 */       return new HtmlTagRenderer.AdditionalAttributes() {
/*     */           public void getAdditionalAttributes(Map map) {
/*  59 */             ListElement.this.getAdditionalAttributesContent(rowIndex - 1, columnIndex, map);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesRow(int rowIndex) {
/*  66 */       return null;
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map map) {
/*  70 */       ListElement.this.getAdditionalAttributesTable(map);
/*     */     }
/*     */     
/*     */     public boolean isHeader(int rowIndex, int columnIndex) {
/*  74 */       if (rowIndex == 0 && this.header) {
/*  75 */         return true;
/*     */       }
/*  77 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListElement(List<?> data) {
/*  89 */     this.rendererCallback = new RendererCallback();
/*  90 */     this.data = (data != null) ? new ArrayList(data) : null;
/*     */   }
/*     */   
/*     */   public ListElement() {
/*  94 */     this((List)null);
/*     */   }
/*     */   
/*     */   public void setData(List<?> data) {
/*  98 */     this.data = new ArrayList(data);
/*     */   }
/*     */   
/*     */   protected List getData() {
/* 102 */     return this.data;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 106 */     return HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)this.rendererCallback);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getColumnCount();
/*     */ 
/*     */   
/*     */   protected abstract HtmlElement getHeader(int paramInt);
/*     */ 
/*     */   
/*     */   protected abstract HtmlElement getContent(Object paramObject, int paramInt);
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int colomnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {}
/*     */   
/*     */   protected boolean enableHeader() {
/* 128 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\ListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */