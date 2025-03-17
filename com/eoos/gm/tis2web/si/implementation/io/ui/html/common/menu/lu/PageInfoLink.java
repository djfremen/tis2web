/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.lu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.SIOPageInfoDialog;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.ResultObject;
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
/*    */ public abstract class PageInfoLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public PageInfoLink(final ClientContext context) {
/* 28 */     super(context.createID(), null);
/* 29 */     this.context = context;
/* 30 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 32 */           String image = "si/page-info.gif";
/* 33 */           if (PageInfoLink.this.isDisabled()) {
/* 34 */             image = "si/page-info-disabled.gif";
/*    */           }
/* 36 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 40 */           return context.getLabel("si.pageinfo");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 44 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 52 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 56 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 60 */     SIO sio = getSIO();
/* 61 */     if (sio != null) {
/* 62 */       SIOPageInfoDialog dialog = new SIOPageInfoDialog(this.context, sio);
/* 63 */       return new ResultObject(0, dialog.getHtmlCode(submitParams));
/*    */     } 
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getTargetFrame() {
/* 70 */     return "_top";
/*    */   }
/*    */   
/*    */   public abstract SIO getSIO();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\lu\PageInfoLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */