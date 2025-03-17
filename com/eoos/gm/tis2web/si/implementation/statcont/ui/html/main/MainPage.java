/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.gm.tis2web.si.service.SIService;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class MainPage
/*     */   extends MainPage
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(MainPage.class);
/*     */ 
/*     */   
/*     */   public MainPage(ClientContext context) {
/*  36 */     super(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPage getInstance(ClientContext context) {
/*  41 */     synchronized (context.getLockObject()) {
/*  42 */       MainPage instance = (MainPage)context.getObject(MainPage.class);
/*  43 */       if (instance == null) {
/*  44 */         instance = new MainPage(context);
/*  45 */         context.storeObject(MainPage.class, instance);
/*     */       } 
/*  47 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getModuleType() {
/*  52 */     return "si";
/*     */   }
/*     */   
/*     */   protected HtmlElementHook createMainHook() {
/*  56 */     return MainHook.getInstance((ClientContext)this.context);
/*     */   }
/*     */   
/*     */   protected Object onUnhandledSubmit(Map params) {
/*  60 */     Object result = null;
/*  61 */     if (this.mainHook instanceof UnhandledSubmit) {
/*  62 */       result = ((UnhandledSubmit)this.mainHook).handleSubmit(params);
/*     */     }
/*  64 */     return (result != null) ? result : this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onClick_Help(Map submitParams) {
/*  69 */     return "help is integrated in application - see help link in navigation bar";
/*     */   }
/*     */   
/*     */   protected Object onClick_Feedback(Map params) {
/*     */     try {
/*  74 */       HashMap<Object, Object> moduleParams = new HashMap<Object, Object>();
/*     */       
/*  76 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/*  77 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), moduleParams, params);
/*  78 */     } catch (Exception e) {
/*  79 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/*  80 */       return super.onClick_Feedback(params);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onClick_News(Map submitParams) {
/*     */     try {
/*  87 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*     */       
/*  89 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/*  90 */       ci.put("moduleid", "si");
/*     */       
/*  92 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/*  93 */     } catch (Exception e) {
/*  94 */       log.error("unable to retrieve news ui -error:" + e, e);
/*  95 */       return super.onClick_News(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCLinkElement createVCLink() {
/* 100 */     return new VCLinkElement((ClientContext)this.context, new VCLinkElement.Callback() {
/*     */           public Object getReturnUI() {
/* 102 */             SIService service = (SIService)ConfiguredServiceProvider.getInstance().getService(SIService.class);
/* 103 */             return MainPage.this.switchModule((VisualModule)service);
/*     */           }
/*     */           
/*     */           public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 107 */             return (key == VehicleConfigurationUtil.KEY_MAKE);
/*     */           }
/*     */           
/*     */           public boolean isReadonly(Object key) {
/* 111 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */