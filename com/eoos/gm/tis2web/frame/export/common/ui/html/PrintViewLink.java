/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
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
/*    */ public abstract class PrintViewLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public PrintViewLink(final ClientContext context) {
/* 23 */     super(context.createID(), null);
/* 24 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 26 */           return "pic/common/print.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 30 */           return context.getLabel("help.tooltip.printer.friendly");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 34 */           map.put("border", "0");
/* 35 */           map.put("width", "40");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 44 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 48 */     return "printer-friendly";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\PrintViewLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */