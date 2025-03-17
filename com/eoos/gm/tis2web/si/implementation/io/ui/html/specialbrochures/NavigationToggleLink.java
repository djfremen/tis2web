/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures;
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
/*    */ 
/*    */ public class NavigationToggleLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public NavigationToggleLink(final ClientContext context) {
/* 26 */     super(context.createID(), null);
/* 27 */     this.context = context;
/* 28 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 30 */           return "pic/si/navswitch.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 34 */           return context.getLabel("si.tooltip.nav.toggle");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 38 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 46 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 50 */     SpecialBrochuresPanel.getInstance(this.context).toggleMode();
/*    */     
/* 52 */     HtmlElementContainer container = getContainer();
/* 53 */     while (container.getContainer() != null) {
/* 54 */       container = container.getContainer();
/*    */     }
/* 56 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\NavigationToggleLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */