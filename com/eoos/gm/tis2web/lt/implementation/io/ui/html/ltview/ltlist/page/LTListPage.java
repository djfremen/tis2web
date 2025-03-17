/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.LTOpList;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class LTListPage
/*     */   extends Page
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(LTListPage.class);
/*     */   
/*     */   private static boolean partsEnabled = false;
/*     */   private static String template;
/*     */   
/*     */   static {
/*  38 */     String enabled = ApplicationContext.getInstance().getProperty("component.lt.part.links.enabled");
/*  39 */     if (enabled != null && Boolean.valueOf(enabled).booleanValue()) {
/*  40 */       partsEnabled = true;
/*     */     }
/*     */     
/*     */     try {
/*  44 */       template = ApplicationContext.getInstance().loadFile(LTListPage.class, "listpage.html", null).toString();
/*  45 */     } catch (Exception e) {
/*  46 */       log.error("unable to load template - error:" + e, e);
/*  47 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  51 */   private LTOpList mlist = null;
/*     */   
/*  53 */   private LTOpList alist = null;
/*     */   
/*  55 */   private PartLink linkPart = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   protected OnLoadHandler onLoadHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Callback callback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LTListPage(ClientContext context, Callback callback) {
/*  77 */     super(context);
/*  78 */     this.callback = callback;
/*  79 */     this.mlist = new LTOpList(context);
/*  80 */     this.alist = new LTOpList(context);
/*  81 */     addElement((HtmlElement)this.mlist);
/*  82 */     addElement((HtmlElement)this.alist);
/*     */     
/*  84 */     if (partsEnabled) {
/*  85 */       this.linkPart = new PartLink(context);
/*  86 */       addElement((HtmlElement)this.linkPart);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static LTListPage getInstance(final ClientContext context) {
/*  92 */     synchronized (context.getLockObject()) {
/*  93 */       LTListPage instance = (LTListPage)context.getObject(LTListPage.class);
/*  94 */       if (instance == null) {
/*  95 */         Callback callback = new Callback()
/*     */           {
/*     */             public SIOLT getCurrentElement() {
/*  98 */               return (SIOLT)Util.prepareCast(TocTree.getInstance(context).getSelectedNode(), SIOLT.class);
/*     */             }
/*     */             
/*     */             public boolean enableBookmarks() {
/* 102 */               return true;
/*     */             }
/*     */           };
/* 105 */         instance = new LTListPage(context, callback);
/* 106 */         context.storeObject(LTListPage.class, instance);
/*     */       } 
/* 108 */       return instance;
/*     */     } 
/*     */   } public static interface Callback {
/*     */     SIOLT getCurrentElement(); boolean enableBookmarks(); }
/*     */   public String getURL(Map params) {
/* 113 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 114 */     String bookmark = (params != null) ? (String)params.get("bm") : null;
/* 115 */     if (bookmark != null) {
/* 116 */       url = url + "#" + bookmark;
/*     */     }
/* 118 */     return url;
/*     */   } public static interface OnLoadHandler {
/*     */     String getCode(Map param1Map); boolean isTransient(); }
/*     */   public void setOnLoadHandler(OnLoadHandler handler) {
/* 122 */     this.onLoadHandler = handler;
/*     */   }
/*     */   
/*     */   protected String getOnLoadHandlerCode(Map params) {
/* 126 */     String retValue = null;
/* 127 */     if (this.onLoadHandler != null) {
/* 128 */       retValue = this.onLoadHandler.getCode(params);
/* 129 */       if (this.onLoadHandler.isTransient()) {
/* 130 */         this.onLoadHandler = null;
/*     */       }
/*     */     } 
/* 133 */     return retValue;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 137 */     return new ResultObject(0, getHtmlCode(params));
/*     */   }
/*     */   
/*     */   public SIOLT getCurrentElement() {
/* 141 */     return this.callback.getCurrentElement();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 146 */     SIOLT oElement = getCurrentElement();
/*     */     
/* 148 */     LTClientContext oC = LTClientContext.getInstance((ClientContext)this.context);
/*     */ 
/*     */     
/* 151 */     List<LTDataWork> oL = new LinkedList();
/*     */     LTDataWork work;
/* 153 */     oL.add(work = oC.getMainWork(oElement.getMajorOperationNumber(), true));
/* 154 */     if (work == null)
/*     */     {
/*     */       
/* 157 */       return "";
/*     */     }
/* 159 */     if (work.getTasktype() == 1) {
/* 160 */       List<? extends LTDataWork> ladd = oC.getMainWorks(work.getNr());
/* 161 */       if (ladd != null) {
/* 162 */         oL.addAll(ladd);
/*     */       }
/*     */     } 
/* 165 */     this.mlist.setData(oL);
/* 166 */     this.alist.setData(oL = oC.getAddOnWorks(oElement.getMajorOperationNumber()));
/*     */     
/* 168 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 170 */     StringUtilities.replace(code, "{LT_MAIN}", this.context.getLabel("lt.mworks"));
/* 171 */     StringUtilities.replace(code, "{LT_MAINOP}", this.mlist.getHtmlCode(params));
/*     */     
/* 173 */     if (oL != null && oL.size() > 0) {
/* 174 */       StringUtilities.replace(code, "{LT_ADD}", this.context.getLabel("lt.aworks"));
/* 175 */       StringUtilities.replace(code, "{LT_ADDOP}", this.alist.getHtmlCode(params));
/*     */     } else {
/* 177 */       StringUtilities.replace(code, "<h2>{LT_ADD}</h2>", " ");
/* 178 */       StringUtilities.replace(code, "{LT_ADDOP}", " ");
/*     */     } 
/*     */     
/* 181 */     if (partsEnabled && this.linkPart.isAvailable((CTOCNode)oElement)) {
/*     */ 
/*     */       
/* 184 */       StringUtilities.replace(code, "{LINK_PART}", this.linkPart.getHtmlCode(params));
/*     */     } else {
/* 186 */       StringUtilities.replace(code, "{LINK_PART}", "");
/*     */     } 
/*     */     
/* 189 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\page\LTListPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */