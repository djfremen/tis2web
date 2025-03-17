/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.resultlist;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.PagedElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class SearchResultPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 25 */   private static Logger log = Logger.getLogger(SearchResultPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 30 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanel.class, "resultpanel.html", null).toString();
/* 31 */     } catch (Exception e) {
/* 32 */       log.error("unable to load template - error:" + e, e);
/* 33 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/* 39 */   protected PagedElement ieList = null;
/*    */   
/*    */   public SearchResultPanel(ClientContext context) {
/* 42 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setResultList(List elementList) {
/* 47 */     removeElement((HtmlElement)this.ieList);
/* 48 */     this.ieList = new PagedElement(this.context.createID(), (HtmlElement)new SIOLTListElement(this.context, elementList), 10, 20);
/* 49 */     addElement((HtmlElement)this.ieList);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 53 */     StringBuffer code = new StringBuffer(template);
/* 54 */     if (this.ieList != null) {
/* 55 */       StringUtilities.replace(code, "{LIST_ELEMENT}", this.ieList.getHtmlCode(params));
/*    */     } else {
/* 57 */       StringUtilities.replace(code, "{LIST_ELEMENT}", "");
/*    */     } 
/* 59 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\resultlist\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */