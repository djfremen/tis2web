/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.StyleSheetController;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.waitpage.WaitPage;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.html.element.input.IDLinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class NewsLinkElement
/*    */   extends IDLinkElement
/*    */ {
/* 27 */   private static final Logger log = Logger.getLogger(NewsLinkElement.class);
/*    */   private ClientContext context;
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   private abstract class RedirectPageCallback implements WaitPage.Callback { public String getTitle() {
/* 32 */       return NewsLinkElement.this.context.getLabel("news");
/*    */     }
/*    */     private RedirectPageCallback() {}
/*    */     public String getStylesheet() {
/* 36 */       return StyleSheetController.getInstance((ClientContextBase)NewsLinkElement.this.context).getURL();
/*    */     }
/*    */     
/*    */     public long getReload() {
/* 40 */       return 0L;
/*    */     }
/*    */     
/*    */     public String getMessage() {
/* 44 */       return NewsLinkElement.this.context.getMessage("wait");
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NewsLinkElement(final ClientContext context) {
/* 54 */     super(context.createID(), null, "frame.news");
/* 55 */     this.context = context;
/* 56 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 58 */           return "pic/common/news.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 62 */           return context.getLabel("tooltip.news");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 66 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 74 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 78 */     return "news";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 82 */     Locale locale = this.context.getLocale();
/* 83 */     StringBuffer path = new StringBuffer((locale != null) ? (locale.toString() + "/") : "");
/* 84 */     StringUtilities.replace(path, "_", "/");
/*    */     
/* 86 */     path.insert(0, this.context.getRequestURL() + "/news/");
/* 87 */     path.append("index.htm");
/*    */     
/* 89 */     final String url = path.toString();
/* 90 */     log.debug("redirecting to url:" + url);
/* 91 */     WaitPage wp = new WaitPage(new RedirectPageCallback() {
/*    */           public String getURL() {
/* 93 */             return url;
/*    */           }
/*    */         });
/*    */     
/* 97 */     return new ResultObject(0, wp.getHtmlCode(submitParams));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\NewsLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */