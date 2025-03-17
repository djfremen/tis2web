/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*    */ 
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*    */ public class FeedbackLinkElement
/*    */   extends LinkElement
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(FeedbackLinkElement.class);
/*    */   
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public FeedbackLinkElement(final ClientContext context) {
/* 30 */     super(context.createID(), null);
/* 31 */     this.context = context;
/* 32 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 34 */           return "pic/common/feedback.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 38 */           return context.getLabel("tooltip.feedback");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 42 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 50 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 54 */     return "feedback";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/*    */     try {
/* 59 */       FeedbackService service = FeedbackServiceProvider.getInstance().getService();
/* 60 */       return service.getUI(this.context.getSessionID(), "", null, submitParams);
/* 61 */     } catch (Exception e) {
/* 62 */       log.error("loading feedback - error:" + e, e);
/* 63 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\FeedbackLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */