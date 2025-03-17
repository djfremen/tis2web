/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.profile.service.ProfileService;
/*    */ import com.eoos.gm.tis2web.profile.service.ProfileServiceProvider;
/*    */ import com.eoos.html.element.HtmlElement;
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
/*    */ public class ProfileLinkElement
/*    */   extends LinkElement
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(ProfileLinkElement.class);
/*    */   
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private HtmlElement page;
/*    */   
/*    */   public ProfileLinkElement(final ClientContext context, HtmlElement page) {
/* 33 */     super(context.createID(), null);
/* 34 */     this.context = context;
/* 35 */     this.page = page;
/* 36 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 38 */           return "pic/common/profile.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 42 */           return context.getLabel("tooltip.toplink.profile");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 46 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 54 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/*    */     try {
/* 59 */       ProfileService profile = ProfileServiceProvider.getInstance().getService();
/*    */       
/* 61 */       ProfileService.ReturnHandler returnHandler = new ProfileService.ReturnHandler()
/*    */         {
/*    */           
/*    */           public Object onReturn(Map params)
/*    */           {
/* 66 */             SharedContextProxy.getInstance(ProfileLinkElement.this.context).update();
/* 67 */             return ProfileLinkElement.this.page.getHtmlCode(params);
/*    */           }
/*    */         };
/* 70 */       return profile.getUI(this.context.getSessionID(), submitParams, returnHandler);
/* 71 */     } catch (Exception e) {
/* 72 */       log.error("unable to switch to profile module - exception:" + e, e);
/* 73 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\ProfileLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */