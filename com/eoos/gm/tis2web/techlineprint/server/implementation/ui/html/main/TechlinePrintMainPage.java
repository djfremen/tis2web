/*    */ package com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.main;
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
/*    */ public class TechlinePrintMainPage
/*    */   extends MainPage
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(TechlinePrintMainPage.class);
/*    */ 
/*    */   
/*    */   public TechlinePrintMainPage(ClientContext context) {
/* 30 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized TechlinePrintMainPage getInstance(ClientContext context) {
/* 35 */     TechlinePrintMainPage instance = (TechlinePrintMainPage)context.getObject(TechlinePrintMainPage.class);
/* 36 */     if (instance == null) {
/* 37 */       instance = new TechlinePrintMainPage(context);
/* 38 */       context.storeObject(TechlinePrintMainPage.class, instance);
/*    */     } 
/* 40 */     return instance;
/*    */   }
/*    */   
/*    */   public String getModuleType() {
/* 44 */     return "techlineprint";
/*    */   }
/*    */   
/*    */   protected HtmlElementHook createMainHook() {
/* 48 */     return new TechlinePrintMainHook((ClientContext)this.context);
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
/* 66 */       ci.put("moduleid", "techlineprint");
/* 67 */       ci.put("pageid", "TECHLINEPRINT_DISPLAY");
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
/* 81 */       ci.put("moduleid", "techlineprint");
/*    */       
/* 83 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 84 */     } catch (Exception e) {
/* 85 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 86 */       return super.onClick_News(submitParams);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\server\implementatio\\ui\html\main\TechlinePrintMainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */