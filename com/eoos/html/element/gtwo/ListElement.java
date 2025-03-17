/*     */ package com.eoos.html.element.gtwo;
/*     */ 
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.html.renderer.HtmlTagRenderer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class ListElement
/*     */   extends HtmlElementContainerBase implements DataRetrievalAbstraction {
/*     */   private RendererCallback rendererCallback;
/*     */   protected List data;
/*     */   protected DataRetrievalAbstraction.DataCallback dataCallback;
/*     */   
/*     */   private class RendererCallback extends HtmlTableRenderer.CallbackAdapter {
/*     */     private List data;
/*     */     private boolean header;
/*     */     
/*     */     private RendererCallback() {}
/*     */     
/*     */     public void init(Map params) {
/*  23 */       ListElement.this.removeAllElements();
/*  24 */       this.data = ListElement.this.dataCallback.getData();
/*  25 */       this.header = ListElement.this.enableHeader();
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/*  29 */       int count = ListElement.this.enableHeader() ? 1 : 0;
/*  30 */       return this.data.size() + count;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/*  34 */       return ListElement.this.getColumnCount();
/*     */     }
/*     */     
/*     */     public String getContent(int rowIndex, int columnIndex) {
/*  38 */       HtmlElement element = null;
/*  39 */       if (rowIndex == 0 && this.header) {
/*  40 */         element = ListElement.this.getHeader(columnIndex);
/*     */       } else {
/*  42 */         element = ListElement.this.getContent(this.data.get(this.header ? (rowIndex - 1) : rowIndex), columnIndex);
/*     */       } 
/*  44 */       if (element != null) {
/*  45 */         ListElement.this.addElement(element);
/*  46 */         return element.getHtmlCode(null);
/*     */       } 
/*  48 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(final int rowIndex, final int columnIndex) {
/*  53 */       if (rowIndex == 0 && this.header) {
/*  54 */         return new HtmlTagRenderer.AdditionalAttributes() {
/*     */             public void getAdditionalAttributes(Map map) {
/*  56 */               ListElement.this.getAdditionalAttributesHeader(columnIndex, map);
/*     */             }
/*     */           };
/*     */       }
/*  60 */       return new HtmlTagRenderer.AdditionalAttributes() {
/*     */           public void getAdditionalAttributes(Map map) {
/*  62 */             ListElement.this.getAdditionalAttributesContent(rowIndex - 1, columnIndex, map);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesRow(final int rowIndex) {
/*  69 */       return new HtmlTagRenderer.AdditionalAttributes() {
/*     */           public void getAdditionalAttributes(Map map) {
/*  71 */             ListElement.this.getAdditionalAttributesRow(rowIndex, map);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public void getAdditionalAttributes(Map map) {
/*  78 */       ListElement.this.getAdditionalAttributesTable(map);
/*     */     }
/*     */     
/*     */     public boolean isHeader(int rowIndex, int columnIndex) {
/*  82 */       if (rowIndex == 0 && this.header) {
/*  83 */         return true;
/*     */       }
/*  85 */       return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ListElement(DataRetrievalAbstraction.DataCallback dataCallback) {
/* 100 */     this.rendererCallback = new RendererCallback();
/* 101 */     this.dataCallback = dataCallback;
/*     */   }
/*     */   
/*     */   public ListElement() {
/* 105 */     this((DataRetrievalAbstraction.DataCallback)null);
/*     */   }
/*     */   
/*     */   public void setDataCallback(DataRetrievalAbstraction.DataCallback dataCallback) {
/* 109 */     this.dataCallback = dataCallback;
/*     */   }
/*     */   
/*     */   public DataRetrievalAbstraction.DataCallback getDataCallback() {
/* 113 */     return this.dataCallback;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 117 */     return HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)this.rendererCallback);
/*     */   }
/*     */   
/*     */   protected abstract int getColumnCount();
/*     */   
/*     */   protected abstract HtmlElement getHeader(int paramInt);
/*     */   
/*     */   protected abstract HtmlElement getContent(Object paramObject, int paramInt);
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map<String, String> map) {
/* 127 */     map.put("width", "100%");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected boolean enableHeader() {
/* 143 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\ListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */