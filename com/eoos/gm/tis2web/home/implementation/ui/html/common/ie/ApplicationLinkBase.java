/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.IDLinkElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import com.eoos.html.renderer.HtmlLinkRenderer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ApplicationLinkBase
/*    */   extends IDLinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   protected ClientContext context;
/*    */   
/*    */   public ApplicationLinkBase(final ClientContext context, final String imageName, final String tooltipKey, String idPref) {
/* 24 */     super(context.createID(), null, idPref);
/* 25 */     this.context = context;
/* 26 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 28 */           return "pic/home/" + imageName;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 32 */           return context.getLabel(tooltipKey);
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 36 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 50 */     StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\">{LINK_IMAGE}</td></tr><tr><td align=\"center\">{LINK_TEXT}</td></tr></table>");
/*    */     
/* 52 */     LinkElement.RenderingCallback rd = new LinkElement.RenderingCallback()
/*    */       {
/*    */         public String getLabel() {
/* 55 */           return HtmlImgRenderer.getInstance().getHtmlCode(ApplicationLinkBase.this.imgRendererCallback);
/*    */         }
/*    */ 
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 60 */           super.getAdditionalAttributes(map);
/* 61 */           String id = (String)map.get("id");
/* 62 */           if (id != null) {
/* 63 */             map.put("id", id + ".img");
/*    */           }
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 69 */     StringUtilities.replace(tmp, "{LINK_IMAGE}", HtmlLinkRenderer.getInstance().getHtmlCode((HtmlLinkRenderer.Callback)rd));
/*    */     
/* 71 */     rd = new LinkElement.RenderingCallback()
/*    */       {
/*    */         public String getLabel() {
/* 74 */           return ApplicationLinkBase.this.imgRendererCallback.getAlternativeText();
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 79 */     StringUtilities.replace(tmp, "{LINK_TEXT}", HtmlLinkRenderer.getInstance().getHtmlCode((HtmlLinkRenderer.Callback)rd));
/*    */     
/* 81 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public abstract Object onClick(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\ApplicationLinkBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */