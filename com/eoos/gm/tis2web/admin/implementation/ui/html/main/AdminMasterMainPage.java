/*     */ package com.eoos.gm.tis2web.admin.implementation.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InconsistentSystemException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.InconsistentSystemNotification;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.help.service.HelpService;
/*     */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.scsm.v2.util.Util;
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
/*     */ 
/*     */ public class AdminMasterMainPage
/*     */   extends MainPage
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(AdminMasterMainPage.class);
/*     */ 
/*     */   
/*     */   public AdminMasterMainPage(ClientContext context) {
/*  33 */     super(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdminMasterMainPage getInstance(ClientContext context) {
/*  38 */     synchronized (context.getLockObject()) {
/*  39 */       AdminMasterMainPage instance = (AdminMasterMainPage)context.getObject(AdminMasterMainPage.class);
/*  40 */       if (instance == null) {
/*  41 */         instance = new AdminMasterMainPage(context);
/*  42 */         context.storeObject(AdminMasterMainPage.class, instance);
/*     */       } 
/*  44 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getModuleType() {
/*  49 */     return "admin";
/*     */   }
/*     */   
/*     */   protected HtmlElementHook createMainHook() {
/*  53 */     return new AdminMasterMainHook((ClientContext)this.context);
/*     */   }
/*     */   
/*     */   protected Object onClick_Feedback(Map params) {
/*     */     try {
/*  58 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/*  59 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/*  60 */     } catch (Exception e) {
/*  61 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/*  62 */       return super.onClick_Feedback(params);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object onClick_Help(Map submitParams) {
/*     */     try {
/*  68 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*     */       
/*  70 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/*  71 */       ci.put("moduleid", "admin");
/*  72 */       ci.put("pageid", "ADMIN_DISPLAY");
/*     */       
/*  74 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/*  75 */     } catch (Exception e) {
/*  76 */       log.error("unable to retrieve help ui -error:" + e, e);
/*  77 */       return super.onClick_Help(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object onClick_News(Map submitParams) {
/*     */     try {
/*  83 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*     */       
/*  85 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/*  86 */       ci.put("moduleid", "admin");
/*     */       
/*  88 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/*  89 */     } catch (Exception e) {
/*  90 */       log.error("unable to retrieve news ui -error:" + e, e);
/*  91 */       return super.onClick_News(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*     */     try {
/*  97 */       return super.getHtmlCode(params);
/*  98 */     } catch (RuntimeException e) {
/*  99 */       InconsistentSystemException ise = (InconsistentSystemException)Util.getCause(e, InconsistentSystemException.class);
/* 100 */       if (ise != null) {
/* 101 */         return (new InconsistentSystemNotification((ClientContext)this.context, null, ise.getMessage())).getHtmlCode(params);
/*     */       }
/* 103 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\implementatio\\ui\html\main\AdminMasterMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */