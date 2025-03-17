/*    */ package com.eoos.html.element.gtwo;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.PagerElement;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.Pager;
/*    */ import com.eoos.util.StringUtilities;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PagedElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private static final String template = "<table width=\"100%\"><tr><td width=\"100%\">{ELEMENT}</td></tr><tr><td>{PAGER_ELEMENT}</td></tr></table>";
/*    */   protected String parameterName;
/*    */   protected Pager pager;
/*    */   protected PagerElement pagerElement;
/*    */   protected HtmlElement element;
/*    */   
/*    */   public PagedElement(String parameterName, HtmlElement element, int pageSize, int pageLinkCount) {
/* 34 */     this.parameterName = parameterName;
/*    */     
/* 36 */     if (!(element instanceof DataRetrievalAbstraction)) {
/* 37 */       throw new IllegalArgumentException("element has to implement DataRetrievalAbstraction");
/*    */     }
/* 39 */     this.element = element;
/* 40 */     if (element.getContainer() == null) {
/* 41 */       addElement(element);
/*    */     }
/*    */     
/* 44 */     final DataRetrievalAbstraction.DataCallback dataCallback = ((DataRetrievalAbstraction)this.element).getDataCallback();
/* 45 */     this.pager = new Pager(pageSize) {
/*    */         protected List getList() {
/* 47 */           return dataCallback.getData();
/*    */         }
/*    */       };
/*    */     
/* 51 */     ((DataRetrievalAbstraction)this.element).setDataCallback(new DataRetrievalAbstraction.DataCallback() {
/*    */           public List getData() {
/* 53 */             return PagedElement.this.pager.getCurrentPage();
/*    */           }
/*    */         });
/*    */     
/* 57 */     this.pagerElement = new PagerElement(parameterName, this.pager, pageLinkCount);
/* 58 */     addElement((HtmlElement)this.pagerElement);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 62 */     StringBuffer code = StringBufferPool.getThreadInstance().get("<table width=\"100%\"><tr><td width=\"100%\">{ELEMENT}</td></tr><tr><td>{PAGER_ELEMENT}</td></tr></table>");
/*    */     
/*    */     try {
/* 65 */       StringUtilities.replace(code, "{ELEMENT}", this.element.getHtmlCode(params));
/* 66 */       StringUtilities.replace(code, "{PAGER_ELEMENT}", this.pagerElement.getHtmlCode(params));
/* 67 */       return code.toString();
/*    */     } finally {
/* 69 */       StringBufferPool.getThreadInstance().free(code);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\PagedElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */