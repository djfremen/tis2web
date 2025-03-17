/*    */ package com.eoos.gm.tis2web.sas.server.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.help.service.HelpService;
/*    */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*    */ import com.eoos.gm.tis2web.news.service.NewsService;
/*    */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ import java.util.HashMap;
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
/*    */ public class SASMainPage
/*    */   extends MainPage
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(SASMainPage.class);
/*    */ 
/*    */   
/*    */   public SASMainPage(ClientContext context) {
/* 30 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static SASMainPage getInstance(ClientContext context) {
/* 35 */     synchronized (context.getLockObject()) {
/* 36 */       SASMainPage instance = (SASMainPage)context.getObject(SASMainPage.class);
/* 37 */       if (instance == null) {
/* 38 */         instance = new SASMainPage(context);
/* 39 */         context.storeObject(SASMainPage.class, instance);
/*    */       } 
/* 41 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 46 */     return "sas";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 50 */     return new SASMainHook((ClientContext)this.context);
/*    */   }
/*    */   
/*    */   protected Object onClick_Feedback(Map params) {
/*    */     try {
/* 55 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/* 56 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/* 57 */     } catch (Exception e) {
/* 58 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 59 */       return super.onClick_Feedback(params);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_Help(Map submitParams) {
/*    */     try {
/* 65 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*    */       
/* 67 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 68 */       ci.put("moduleid", "sas");
/* 69 */       ci.put("pageid", "SAS_DISPLAY");
/*    */       
/* 71 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 72 */     } catch (Exception e) {
/* 73 */       log.error("unable to retrieve help ui -error:" + e, e);
/* 74 */       return super.onClick_Help(submitParams);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_News(Map submitParams) {
/*    */     try {
/* 80 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*    */       
/* 82 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 83 */       ci.put("moduleid", "sas");
/*    */       
/* 85 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 86 */     } catch (Exception e) {
/* 87 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 88 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementatio\\ui\html\main\SASMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */