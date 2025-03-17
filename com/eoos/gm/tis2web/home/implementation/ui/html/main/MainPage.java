/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.main;
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
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
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
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class MainPage
/*    */   extends MainPage
/*    */ {
/* 27 */   private static final Logger log = Logger.getLogger(MainPage.class);
/*    */ 
/*    */   
/*    */   public MainPage(ClientContext context) {
/* 31 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPage getInstance(ClientContext context) {
/* 36 */     synchronized (context.getLockObject()) {
/* 37 */       MainPage instance = (MainPage)context.getObject(MainPage.class);
/* 38 */       if (instance == null) {
/* 39 */         instance = new MainPage(context);
/* 40 */         context.storeObject(MainPage.class, instance);
/*    */       } 
/* 42 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 47 */     return "home";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 51 */     return new MainHook((ClientContext)this.context);
/*    */   }
/*    */   
/*    */   public Object onClick_Help(Map submitParams) {
/*    */     try {
/* 56 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*    */       
/* 58 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 59 */       ci.put("moduleid", "home");
/* 60 */       ci.put("pageid", "home");
/* 61 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 62 */     } catch (Exception e) {
/* 63 */       log.error("unable to retrieve help ui -error:" + e, e);
/* 64 */       return super.onClick_Help(submitParams);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected Object onClick_Feedback(Map params) {
/*    */     try {
/* 70 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/* 71 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/* 72 */     } catch (Exception e) {
/* 73 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 74 */       return super.onClick_Feedback(params);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_News(Map submitParams) {
/*    */     try {
/* 80 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*    */       
/* 82 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 83 */       ci.put("moduleid", "home");
/* 84 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 85 */     } catch (Exception e) {
/* 86 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 87 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */