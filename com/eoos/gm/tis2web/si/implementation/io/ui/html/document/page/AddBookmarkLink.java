/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ 
/*    */ public abstract class AddBookmarkLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public AddBookmarkLink(final ClientContext context) {
/* 23 */     super(context.createID(), null);
/* 24 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 26 */           return "pic/si/add-bookmark.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 30 */           return context.getLabel("si.tooltip.add.bookmark");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 34 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 42 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\page\AddBookmarkLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */