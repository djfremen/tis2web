/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ public class NavigationToggleLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public NavigationToggleLink(final ClientContext context) {
/* 25 */     super(context.createID(), null);
/* 26 */     this.context = context;
/* 27 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 29 */           return "pic/lt/navswitch.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 33 */           return context.getLabel("si.tooltip.nav.toggle");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 37 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 45 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 49 */     StandardInfoPanel.getInstance(this.context).toggleNavigationMode();
/*    */     
/* 51 */     HtmlElementContainer container = getContainer();
/* 52 */     while (container.getContainer() != null) {
/* 53 */       container = container.getContainer();
/*    */     }
/* 55 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\NavigationToggleLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */