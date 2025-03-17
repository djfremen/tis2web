/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.IDLinkElement;
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
/*    */ 
/*    */ 
/*    */ public class BulletinsUIIconLink
/*    */   extends IDLinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public BulletinsUIIconLink(final ClientContext context) {
/* 29 */     super("smenu:" + context.createID(), null, "Bulletins");
/* 30 */     this.context = context;
/* 31 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 33 */           String image = "si/bulletins.gif";
/* 34 */           if (BulletinsUIIconLink.this.isDisabled()) {
/* 35 */             image = "si/bulletins-disabled.gif";
/*    */           }
/* 37 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 41 */           return context.getLabel("si.tooltip.bulletins");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 45 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 53 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 57 */     MainHook.getInstance(this.context).switchUI(5);
/* 58 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 62 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\BulletinsUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */