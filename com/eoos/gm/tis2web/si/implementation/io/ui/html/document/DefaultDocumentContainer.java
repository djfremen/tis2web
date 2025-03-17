/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
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
/*    */ 
/*    */ public class DefaultDocumentContainer
/*    */   extends DocumentContainer
/*    */ {
/*    */   protected String message;
/*    */   protected Page errorPage;
/*    */   
/*    */   public DefaultDocumentContainer(ClientContext context, String message) {
/* 25 */     super(context);
/* 26 */     this.message = message;
/* 27 */     this.errorPage = new Page(context)
/*    */       {
/*    */         protected String getFormContent(Map params) {
/* 30 */           return "<table align=\"center\"><tr><td>" + DefaultDocumentContainer.this.message + "</td></tr></table>";
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStyleSheet() {
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 52 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getEncoding() {
/* 61 */     return "utf-8";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 70 */     return this.errorPage.getHtmlCode(params);
/*    */   }
/*    */ 
/*    */   
/*    */   public SIO getSIO() {
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\DefaultDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */