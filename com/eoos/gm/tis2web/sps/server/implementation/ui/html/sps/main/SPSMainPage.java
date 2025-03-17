/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.sps.main;
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
/*    */ public class SPSMainPage
/*    */   extends MainPage
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(SPSMainPage.class);
/*    */ 
/*    */   
/*    */   public SPSMainPage(ClientContext context) {
/* 30 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static SPSMainPage getInstance(ClientContext context) {
/* 35 */     synchronized (context.getLockObject()) {
/* 36 */       SPSMainPage instance = (SPSMainPage)context.getObject(SPSMainPage.class);
/* 37 */       if (instance == null) {
/* 38 */         instance = new SPSMainPage(context);
/* 39 */         context.storeObject(SPSMainPage.class, instance);
/*    */       } 
/* 41 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 46 */     return "sps";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 50 */     return new SPSMainHook((ClientContext)this.context);
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
/* 68 */       ci.put("moduleid", "sps");
/* 69 */       ci.put("pageid", "SPS_DISPLAY");
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
/* 83 */       ci.put("moduleid", "sps");
/*    */       
/* 85 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 86 */     } catch (Exception e) {
/* 87 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 88 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\sps\main\SPSMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */