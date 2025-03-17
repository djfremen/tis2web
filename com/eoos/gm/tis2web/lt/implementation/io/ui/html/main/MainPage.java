/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*     */ import com.eoos.gm.tis2web.help.service.HelpService;
/*     */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.ModuleContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.html.element.HtmlElementHook;
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
/*  31 */   private static final Logger log = Logger.getLogger(MainPage.class);
/*     */ 
/*     */   
/*     */   public MainPage(ClientContext context) {
/*  35 */     super(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPage getInstance(ClientContext context) {
/*  40 */     synchronized (context.getLockObject()) {
/*  41 */       MainPage instance = (MainPage)context.getObject(MainPage.class);
/*  42 */       if (instance == null) {
/*  43 */         instance = new MainPage(context);
/*  44 */         context.storeObject(MainPage.class, instance);
/*     */       } 
/*  46 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getModuleType() {
/*  51 */     return "lt";
/*     */   }
/*     */   
/*     */   protected HtmlElementHook createMainHook() {
/*  55 */     return MainHook.getInstance((ClientContext)this.context);
/*     */   }
/*     */   
/*     */   public Object onClick_Help(Map submitParams) {
/*     */     try {
/*  60 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*     */       
/*  62 */       ModuleContext mcontext = ModuleContext.getInstance(((ClientContext)this.context).getSessionID());
/*     */       
/*  64 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/*  65 */       ci.put("moduleid", "lt");
/*  66 */       ci.put("pageid", mcontext.getPageIdentifier());
/*  67 */       ci.put("sit", mcontext.getSIT());
/*  68 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/*  69 */     } catch (Exception e) {
/*  70 */       log.error("unable to retrieve help ui -error:" + e, e);
/*  71 */       return super.onClick_Help(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object onClick_Feedback(Map params) {
/*     */     try {
/*  77 */       HashMap<Object, Object> moduleParams = new HashMap<Object, Object>();
/*  78 */       TocTree ctocTree = TocTree.getInstance((ClientContext)this.context);
/*  79 */       if (ctocTree != null) {
/*  80 */         Object node = ctocTree.getSelectedNode();
/*  81 */         if (node != null && 
/*  82 */           ctocTree.isLeaf(node) && node instanceof SIOLT) {
/*  83 */           SIOLT ltNode = (SIOLT)node;
/*  84 */           moduleParams.put("MajorOperation", ltNode.getMajorOperationNumber());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/*  92 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), moduleParams, params);
/*  93 */     } catch (Exception e) {
/*  94 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/*  95 */       return super.onClick_Feedback(params);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object onClick_News(Map submitParams) {
/*     */     try {
/* 101 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*     */       
/* 103 */       ModuleContext mcontext = ModuleContext.getInstance(((ClientContext)this.context).getSessionID());
/*     */       
/* 105 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 106 */       ci.put("moduleid", "lt");
/* 107 */       ci.put("pageid", mcontext.getPageIdentifier());
/* 108 */       ci.put("sit", mcontext.getSIT());
/* 109 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 110 */     } catch (Exception e) {
/* 111 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 112 */       return super.onClick_News(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCLinkElement createVCLink() {
/* 117 */     return new VCLinkElement((ClientContext)this.context, new VCLinkCallback((ClientContext)this.context));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */