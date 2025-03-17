/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.Pager;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PagedListElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private static final String template = "<table width=\"100%\"><tr><td>{LIST_ELEMENT}</td></tr><tr><td>{PAGER_ELEMENT}</td></tr></table>";
/*     */   protected String parameterName;
/*     */   protected Pager pager;
/*     */   protected PagerElement pagerElement;
/*     */   protected ListElement listElement;
/*     */   protected List data;
/*     */   
/*     */   public PagedListElement(String parameterName, List data, int pageSize, int pageLinkCount) {
/*  31 */     this.parameterName = parameterName;
/*  32 */     this.data = data;
/*  33 */     this.pager = new Pager(pageSize) {
/*     */         protected List getList() {
/*  35 */           return PagedListElement.this.getData();
/*     */         }
/*     */       };
/*     */     
/*  39 */     this.listElement = new ListElement() {
/*     */         protected List getData() {
/*  41 */           return PagedListElement.this.pager.getCurrentPage();
/*     */         }
/*     */         
/*     */         protected int getColumnCount() {
/*  45 */           return PagedListElement.this.getColumnCount();
/*     */         }
/*     */         
/*     */         protected HtmlElement getHeader(int columnIndex) {
/*  49 */           return PagedListElement.this.getHeader(columnIndex);
/*     */         }
/*     */         
/*     */         protected HtmlElement getContent(Object data, int columnIndex) {
/*  53 */           return PagedListElement.this.getContent(data, columnIndex);
/*     */         }
/*     */         
/*     */         protected void getAdditionalAttributesTable(Map map) {
/*  57 */           PagedListElement.this.getAdditionalAttributesTable(map);
/*     */         }
/*     */         
/*     */         protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/*  61 */           PagedListElement.this.getAdditionalAttributesContent(dataIndex, columnIndex, map);
/*     */         }
/*     */         
/*     */         protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/*  65 */           PagedListElement.this.getAdditionalAttributesHeader(columnIndex, map);
/*     */         }
/*     */         
/*     */         protected boolean enableHeader() {
/*  69 */           return PagedListElement.this.enableHeader();
/*     */         }
/*     */       };
/*  72 */     addElement((HtmlElement)this.listElement);
/*     */     
/*  74 */     this.pagerElement = new PagerElement(parameterName, this.pager, pageLinkCount);
/*  75 */     addElement((HtmlElement)this.pagerElement);
/*     */   }
/*     */   
/*     */   public void setData(List data) {
/*  79 */     this.data = data;
/*     */   }
/*     */   
/*     */   protected List getData() {
/*  83 */     return this.data;
/*     */   }
/*     */   
/*     */   protected abstract int getColumnCount();
/*     */   
/*     */   protected abstract HtmlElement getHeader(int paramInt);
/*     */   
/*     */   protected abstract HtmlElement getContent(Object paramObject, int paramInt);
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map<String, String> map) {
/*  93 */     map.put("width", "100%");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int colomnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected boolean enableHeader() {
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 108 */     StringBuffer code = StringBufferPool.getThreadInstance().get("<table width=\"100%\"><tr><td>{LIST_ELEMENT}</td></tr><tr><td>{PAGER_ELEMENT}</td></tr></table>");
/*     */     
/*     */     try {
/* 111 */       StringUtilities.replace(code, "{LIST_ELEMENT}", this.listElement.getHtmlCode(params));
/* 112 */       StringUtilities.replace(code, "{PAGER_ELEMENT}", this.pagerElement.getHtmlCode(params));
/*     */       
/* 114 */       return code.toString();
/*     */     } finally {
/*     */       
/* 117 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\PagedListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */