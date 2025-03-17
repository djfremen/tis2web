/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.LTBackLink;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.LTDownloadTALLink;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.LTTALList;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLayout;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTTALListPage
/*     */   extends DialogBase
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(LTTALListPage.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       template = ApplicationContext.getInstance().loadFile(LTTALListPage.class, "talpage.html", null).toString();
/*  32 */     } catch (Exception e) {
/*  33 */       log.error("unable to load template - error:" + e, e);
/*  34 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static DialogLayout layout = new DialogLayout() {
/*     */       public String getHorizontalAlignment() {
/*  42 */         return "center";
/*     */       }
/*     */       
/*     */       public String getWidth() {
/*  46 */         return "80%";
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  51 */   private LTTALList list = null;
/*     */   
/*  53 */   private LTBackLink back = null;
/*     */   
/*  55 */   private LTDownloadTALLink down = null;
/*     */ 
/*     */   
/*     */   private LTTALListPage(ClientContext context) {
/*  59 */     super(context);
/*     */     
/*  61 */     this.list = new LTTALList(context);
/*  62 */     addElement((HtmlElement)this.list);
/*     */     
/*  64 */     this.down = new LTDownloadTALLink(context);
/*  65 */     addElement((HtmlElement)this.down);
/*     */     
/*  67 */     this.back = new LTBackLink(context);
/*  68 */     addElement((HtmlElement)this.back);
/*     */   }
/*     */   
/*     */   public static LTTALListPage getInstance(ClientContext context) {
/*  72 */     synchronized (context.getLockObject()) {
/*  73 */       LTTALListPage instance = (LTTALListPage)context.getObject(LTTALListPage.class);
/*  74 */       if (instance == null) {
/*  75 */         instance = new LTTALListPage(context);
/*  76 */         context.storeObject(LTTALListPage.class, instance);
/*     */       } 
/*  78 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getURL(Map params) {
/*  83 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*  84 */     String bookmark = (params != null) ? (String)params.get("bm") : null;
/*  85 */     if (bookmark != null) {
/*  86 */       url = url + "#" + bookmark;
/*     */     }
/*  88 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/*  92 */     return new ResultObject(0, getHtmlCode(params));
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  96 */     StringBuffer code = new StringBuffer(template);
/*  97 */     this.list.init();
/*  98 */     StringUtilities.replace(code, "{LT_LIST}", this.list.getHtmlCode(params));
/*  99 */     StringUtilities.replace(code, "{LT_TALDOWN}", this.down.getHtmlCode(params));
/* 100 */     StringUtilities.replace(code, "{LT_BACK}", this.back.getHtmlCode(params));
/* 101 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 105 */     return this.context.getLabel("lt.talhead");
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/* 109 */     return (HtmlLayout)layout;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\page\LTTALListPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */