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
/*    */ public class HelpLinkElement
/*    */   extends IDLinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/* 26 */   private static final Logger log = Logger.getLogger(HelpLinkElement.class);
/*    */   private ClientContext context;
/*    */   
/*    */   public abstract class RedirectPageCallback implements WaitPage.Callback {
/*    */     public String getTitle() {
/* 31 */       return HelpLinkElement.this.context.getLabel("help");
/*    */     }
/*    */     
/*    */     public String getStylesheet() {
/* 35 */       return StyleSheetController.getInstance((ClientContextBase)HelpLinkElement.this.context).getURL();
/*    */     }
/*    */     
/*    */     public long getReload() {
/* 39 */       return 0L;
/*    */     }
/*    */     
/*    */     public String getMessage() {
/* 43 */       return HelpLinkElement.this.context.getMessage("wait");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HelpLinkElement(final ClientContext context) {
/* 53 */     super(context.createID(), null, "frame.help");
/* 54 */     this.context = context;
/* 55 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 57 */           return "pic/common/help.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 61 */           return context.getLabel("tooltip.help");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 65 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 73 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 77 */     return "help";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 81 */     Locale locale = this.context.getLocale();
/* 82 */     StringBuffer path = new StringBuffer((locale != null) ? (locale.toString() + "/") : "");
/* 83 */     StringUtilities.replace(path, "_", "/");
/*    */     
/* 85 */     path.insert(0, this.context.getRequestURL() + "/help/");
/* 86 */     path.append("index.htm");
/*    */     
/* 88 */     final String url = path.toString();
/* 89 */     log.debug("redirecting to url:" + url);
/* 90 */     WaitPage wp = new WaitPage(new RedirectPageCallback() {
/*    */           public String getURL() {
/* 92 */             return url;
/*    */           }
/*    */         });
/* 95 */     return new ResultObject(0, wp.getHtmlCode(submitParams));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\HelpLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */