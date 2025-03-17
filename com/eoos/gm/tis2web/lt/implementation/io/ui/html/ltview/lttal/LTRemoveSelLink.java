/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*    */ import com.eoos.html.element.HtmlElementContainer;
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
/*    */ public class LTRemoveSelLink
/*    */   extends LinkElement
/*    */ {
/* 21 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private String work;
/*    */   
/*    */   private int iType;
/*    */ 
/*    */   
/*    */   public LTRemoveSelLink(String onr, int iT, ClientContext context) {
/* 31 */     super(context.createID(), null);
/* 32 */     this.work = onr;
/* 33 */     this.iType = iT;
/* 34 */     this.context = context;
/*    */     
/* 36 */     if (this.imgRendererCallback == null) {
/* 37 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */           public String getImageSource() {
/* 39 */             return "pic/lt/ltrem.gif";
/*    */           }
/*    */           
/*    */           public String getAlternativeText() {
/* 43 */             return "";
/*    */           }
/*    */           
/*    */           public void getAdditionalAttributes(Map<String, String> map) {
/* 47 */             map.put("border", "0");
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 55 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 61 */     LTClientContext.getInstance(this.context).getSelection().removeWork(this.work, this.iType);
/* 62 */     HtmlElementContainer container = getContainer();
/* 63 */     while (container.getContainer() != null) {
/* 64 */       container = container.getContainer();
/*    */     }
/* 66 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTRemoveSelLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */