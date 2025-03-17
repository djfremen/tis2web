/*    */ package com.eoos.gm.tis2web.frame.implementation.fallback.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FallbackUIService;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class MainPage
/*    */   extends MainPage
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(MainPage.class);
/*    */   
/*    */   private String moduleType;
/*    */   
/*    */   private FallbackUIService.Callback callback;
/*    */   
/*    */   public MainPage(ClientContext context, FallbackUIService.Callback callback) {
/* 32 */     super(context);
/* 33 */     this.moduleType = callback.getModuleType();
/* 34 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModuleType() {
/* 39 */     return this.moduleType;
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 43 */     return new MainHook(getContext());
/*    */   }
/*    */   
/*    */   protected Object onClick_Feedback(Map params) {
/*    */     try {
/* 48 */       FeedbackService service = FeedbackServiceProvider.getInstance().getService();
/* 49 */       return service.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/* 50 */     } catch (Exception e) {
/* 51 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 52 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected VCLinkElement createVCLink() {
/* 57 */     return new VCLinkElement((ClientContext)this.context, new VCLinkElement.Callback()
/*    */         {
/*    */           public boolean isReadonly(Object key) {
/* 60 */             return MainPage.this.callback.getVCLinkCallback().isReadonly(key);
/*    */           }
/*    */           
/*    */           public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 64 */             return MainPage.this.callback.getVCLinkCallback().isMandatory(key, currentCfg);
/*    */           }
/*    */           
/*    */           public Object getReturnUI() {
/* 68 */             return MainPage.this.callback.getVCLinkCallback().getReturnUI();
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\fallbac\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */