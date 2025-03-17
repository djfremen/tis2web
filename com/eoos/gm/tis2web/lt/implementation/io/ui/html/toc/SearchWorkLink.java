/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTreeWorkNrNavigator;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchWorkLink
/*    */   extends LinkElement
/*    */ {
/* 22 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private TextInputElement input;
/*    */   
/*    */   public static final int SEARCH_STATUS_FOUND = 0;
/*    */   
/*    */   public static final int SEARCH_STATUS_NOT_FOUND = 1;
/*    */   
/*    */   public static final int SEARCH_STATUS_NOT_SET = 2;
/*    */   
/* 34 */   private int status = 2;
/*    */ 
/*    */   
/*    */   public SearchWorkLink(TextInputElement input, final ClientContext context) {
/* 38 */     super(context.createID(), "selectednode");
/* 39 */     this.input = input;
/* 40 */     this.context = context;
/*    */     
/* 42 */     if (this.imgRendererCallback == null) {
/* 43 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*    */         {
/*    */           public String getImageSource() {
/* 46 */             return "pic/lt/goto.gif";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getAlternativeText() {
/* 51 */             return context.getLabel("lt.search.goto");
/*    */           }
/*    */           
/*    */           public void getAdditionalAttributes(Map<String, String> map) {
/* 55 */             map.put("border", "0");
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 64 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTargetFrame() {
/* 69 */     return "_top";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 74 */     String val = (String)this.input.getValue();
/* 75 */     if (val != null && (val = val.trim()).length() > 0) {
/* 76 */       LTUIContext.getInstance(this.context).setSearchNr(val);
/* 77 */       TocTreeWorkNrNavigator nav = new TocTreeWorkNrNavigator(TocTree.getInstance(this.context));
/* 78 */       if (!nav.navigateToMainWork(val)) {
/* 79 */         this.status = 1;
/*    */       } else {
/* 81 */         this.status = 0;
/*    */       } 
/*    */     } 
/*    */     
/* 85 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int popStatus() {
/* 94 */     int retValue = this.status;
/* 95 */     this.status = 2;
/* 96 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\SearchWorkLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */