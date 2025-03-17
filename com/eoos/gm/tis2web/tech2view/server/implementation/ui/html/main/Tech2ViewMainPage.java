/*    */ package com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.main;
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
/*    */ public class Tech2ViewMainPage
/*    */   extends MainPage
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(Tech2ViewMainPage.class);
/*    */ 
/*    */   
/*    */   public Tech2ViewMainPage(ClientContext context) {
/* 30 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized Tech2ViewMainPage getInstance(ClientContext context) {
/* 35 */     Tech2ViewMainPage instance = (Tech2ViewMainPage)context.getObject(Tech2ViewMainPage.class);
/* 36 */     if (instance == null) {
/* 37 */       instance = new Tech2ViewMainPage(context);
/* 38 */       context.storeObject(Tech2ViewMainPage.class, instance);
/*    */     } 
/* 40 */     return instance;
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 44 */     return "tech2view";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 48 */     return new Tech2ViewMainHook((ClientContext)this.context);
/*    */   }
/*    */   
/*    */   protected Object onClick_Feedback(Map params) {
/*    */     try {
/* 53 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/* 54 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/* 55 */     } catch (Exception e) {
/* 56 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 57 */       return super.onClick_Feedback(params);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_Help(Map submitParams) {
/*    */     try {
/* 63 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*    */       
/* 65 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 66 */       ci.put("moduleid", "tech2view");
/* 67 */       ci.put("pageid", "TECH2VIEW_DISPLAY");
/*    */       
/* 69 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 70 */     } catch (Exception e) {
/* 71 */       log.error("unable to retrieve help ui -error:" + e, e);
/* 72 */       return super.onClick_Help(submitParams);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_News(Map submitParams) {
/*    */     try {
/* 78 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*    */       
/* 80 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 81 */       ci.put("moduleid", "tech2view");
/*    */       
/* 83 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 84 */     } catch (Exception e) {
/* 85 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 86 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\server\implementatio\\ui\html\main\Tech2ViewMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */