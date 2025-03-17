/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.LinkElement;
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
/*    */ public class AwLink
/*    */   extends LinkElement
/*    */ {
/*    */   private AwList awList;
/*    */   ClientContext context;
/*    */   
/*    */   public AwLink(ClientContext context, AwList awList) {
/* 25 */     super(context.createID(), null);
/* 26 */     this.context = context;
/* 27 */     this.awList = awList;
/*    */   }
/*    */   
/*    */   public List getLinks() {
/* 31 */     return this.awList.getData();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 36 */     AwPage page = new AwPage(this.context, this.awList);
/* 37 */     List links = page.getLinks();
/* 38 */     if (links.size() == 1) {
/* 39 */       return page.getResult(links.get(0));
/*    */     }
/* 41 */     return new ResultObject(0, page.getHtmlCode(submitParams));
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 46 */     return this.context.getLabel("module.type.lt");
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 50 */     return "_top";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\AwLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */