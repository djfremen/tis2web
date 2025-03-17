/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTBackLink
/*    */   extends LinkElement
/*    */ {
/* 19 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public LTBackLink(ClientContext context) {
/* 25 */     super(context.createID(), null);
/*    */     
/* 27 */     this.context = context;
/*    */     
/* 29 */     if (this.imgRendererCallback == null) {
/* 30 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */           public String getImageSource() {
/* 32 */             return "pic/lt/back.gif";
/*    */           }
/*    */           
/*    */           public String getAlternativeText() {
/* 36 */             return "";
/*    */           }
/*    */           
/*    */           public void getAdditionalAttributes(Map<String, String> map) {
/* 40 */             map.put("border", "0");
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 48 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 54 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTBackLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */