/*    */ package com.eoos.gm.tis2web.kdr.ui.html.main;
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
/* 47 */     return "kdr";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 51 */     return new MainHook((ClientContext)this.context);
/*    */   }
/*    */   
/*    */   protected Object onClick_Feedback(Map params) {
/*    */     try {
/* 56 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/* 57 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), null, params);
/* 58 */     } catch (Exception e) {
/* 59 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 60 */       return super.onClick_Feedback(params);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_Help(Map submitParams) {
/*    */     try {
/* 66 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*    */       
/* 68 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 69 */       ci.put("moduleid", "kdr");
/* 70 */       ci.put("pageid", "KDR_PAGE");
/*    */       
/* 72 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 73 */     } catch (Exception e) {
/* 74 */       log.error("unable to retrieve help ui -error:" + e, e);
/* 75 */       return super.onClick_Help(submitParams);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick_News(Map submitParams) {
/*    */     try {
/* 81 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*    */       
/* 83 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 84 */       ci.put("moduleid", "kdr");
/*    */       
/* 86 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 87 */     } catch (Exception e) {
/* 88 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 89 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kd\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */