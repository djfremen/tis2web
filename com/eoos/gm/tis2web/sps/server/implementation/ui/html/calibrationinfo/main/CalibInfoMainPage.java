/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.main;
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
/*    */ public class CalibInfoMainPage
/*    */   extends MainPage
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(CalibInfoMainPage.class);
/*    */ 
/*    */   
/*    */   public CalibInfoMainPage(ClientContext context) {
/* 30 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static CalibInfoMainPage getInstance(ClientContext context) {
/* 35 */     synchronized (context.getLockObject()) {
/* 36 */       CalibInfoMainPage instance = (CalibInfoMainPage)context.getObject(CalibInfoMainPage.class);
/* 37 */       if (instance == null) {
/* 38 */         instance = new CalibInfoMainPage(context);
/* 39 */         context.storeObject(CalibInfoMainPage.class, instance);
/*    */       } 
/* 41 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 46 */     return "sps_ci";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 50 */     return new CalibInfoMainHook((ClientContext)this.context);
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
/* 68 */       ci.put("moduleid", "sps_ci");
/* 69 */       ci.put("pageid", "SPS_CALIBRATION_INFO");
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
/* 83 */       ci.put("moduleid", "sps_ci");
/*    */       
/* 85 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 86 */     } catch (Exception e) {
/* 87 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 88 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\main\CalibInfoMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */