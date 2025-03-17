/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtocolSaveLink
/*    */   extends LinkElement
/*    */ {
/*    */   ProtocolPage page;
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public ProtocolSaveLink(final ClientContext context, ProtocolPage page) {
/* 31 */     super(context.createID(), null);
/* 32 */     this.page = page;
/* 33 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*    */       {
/*    */ 
/*    */         
/*    */         public String getImageSource()
/*    */         {
/* 39 */           return "pic/lt/lt-download.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 43 */           return context.getLabel("cpr.save");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 47 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 55 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 59 */     this.page.setDisabled(new Boolean(true));
/* 60 */     byte[] styleBt = ApplicationContext.getInstance().loadResource("si/styles/cpr-style.css");
/* 61 */     String result = this.page.getHtmlCode(submitParams);
/*    */     try {
/* 63 */       String style = "<style type=\"text/css\"><!--" + new String(styleBt, "UTF-8") + "--></style>";
/* 64 */       int beg = result.indexOf("<link ");
/* 65 */       if (beg >= 0) {
/* 66 */         int end = result.indexOf(">", beg + 4);
/* 67 */         if (end >= 0) {
/* 68 */           result = result.substring(0, beg) + style + result.substring(end + 1, result.length());
/*    */         }
/*    */       } 
/* 71 */     } catch (Exception e) {}
/*    */     
/* 73 */     ResultObject res = new ResultObject(0, result);
/* 74 */     this.page.setDisabled(new Boolean(false));
/* 75 */     return res;
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 79 */     return "newone";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\protocol\ProtocolSaveLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */