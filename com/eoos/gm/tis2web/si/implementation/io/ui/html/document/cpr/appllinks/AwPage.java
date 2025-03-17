/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
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
/*    */ public class AwPage
/*    */   extends DialogBase
/*    */ {
/*    */   private ClientContext context;
/*    */   protected ClickButtonElement buttonClose;
/*    */   private AwList awList;
/*    */   
/*    */   public AwPage(ClientContext context, AwList awList) {
/* 31 */     super(context);
/* 32 */     this.context = context;
/* 33 */     this.awList = awList;
/* 34 */     awList.setPage(this);
/* 35 */     addElement((HtmlElement)awList);
/* 36 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 38 */           return AwPage.this.context.getLabel("close");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 42 */           AwPage.this.context.unregisterDispatchable((Dispatchable)AwPage.this);
/* 43 */           return MainPage.getInstance(AwPage.this.context);
/*    */         }
/*    */       };
/* 46 */     addElement((HtmlElement)this.buttonClose);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getContent(Map params) {
/* 54 */     StringBuffer code = new StringBuffer("<table><tr><td>{DOCUMENT}</td></tr><tr><td>{BUTTON}</td></tr></table>");
/*    */     
/* 56 */     StringUtilities.replace(code, "{BUTTON}", this.buttonClose.getHtmlCode(params));
/* 57 */     StringUtilities.replace(code, "{DOCUMENT}", this.awList.getHtmlCode(params));
/* 58 */     return code.toString();
/*    */   }
/*    */   
/*    */   public String getURL(Map params) {
/* 62 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 63 */     return url;
/*    */   }
/*    */   
/*    */   public ResultObject getPage(Map params) {
/* 67 */     return new ResultObject(0, getHtmlCode(params));
/*    */   }
/*    */   
/*    */   protected String getTitle(Map params) {
/* 71 */     return this.context.getLabel("module.type.lt");
/*    */   }
/*    */   
/*    */   public List getLinks() {
/* 75 */     return this.awList.getData();
/*    */   }
/*    */   
/*    */   public Object getResult(Object obj) {
/* 79 */     return this.awList.switchLT((SIOLT)obj);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\AwPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */