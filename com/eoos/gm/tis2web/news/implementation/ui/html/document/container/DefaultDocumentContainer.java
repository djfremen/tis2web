/*    */ package com.eoos.gm.tis2web.news.implementation.ui.html.document.container;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
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
/*    */ public class DefaultDocumentContainer
/*    */   extends DocumentContainer
/*    */ {
/*    */   protected String message;
/*    */   
/*    */   public DefaultDocumentContainer(ClientContext context, String message) {
/* 22 */     super(context);
/* 23 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStyleSheet() {
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 32 */     return "";
/*    */   }
/*    */   
/*    */   public String getEncoding() {
/* 36 */     return "utf-8";
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 40 */     return "<table align=\"center\"><tr><td>" + this.message + "</td></tr></table>";
/*    */   }
/*    */ 
/*    */   
/*    */   public SIO getSIO() {
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\document\container\DefaultDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */