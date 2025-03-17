/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTreeObserver;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.StyleSheetController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.waitpage.WaitPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.CPRDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DefaultDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.SIOLUDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.WDDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.HistoryLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo.StandardInfoPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.log.SIEventLogFacade;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProxy;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.IFrameElement;
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.AsyncMethodCallback;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DocumentPage
/*     */   extends Page
/*     */   implements CTOCTreeObserver {
/*     */   private static final class HitEvaluationTask
/*     */     implements Runnable {
/*     */     private HitEvaluationTask() {}
/*     */     
/*     */     public void run() {
/*  64 */       synchronized (DocumentPage.documentHits) {
/*  65 */         List<?> entries = new ArrayList(DocumentPage.documentHits.entrySet());
/*  66 */         Collections.sort(entries, new Comparator() {
/*     */               public int compare(Object obj1, Object obj2) {
/*     */                 try {
/*  69 */                   Map.Entry e1 = (Map.Entry)obj1;
/*  70 */                   Map.Entry e2 = (Map.Entry)obj2;
/*  71 */                   return ((Long)e1.getValue()).compareTo((Long)e2.getValue()) * -1;
/*  72 */                 } catch (Exception e) {
/*  73 */                   return 0;
/*     */                 } 
/*     */               }
/*     */             });
/*  77 */         if (entries.size() > 0) {
/*  78 */           DocumentPage.documentHitLog.info("SI Document Hits ----------------");
/*  79 */           String template = new String("rank: {RANK}  document: {DOCID}  hits: {HITS}");
/*     */           
/*  81 */           for (int i = 0; i < Math.min(50, entries.size()); i++) {
/*  82 */             Map.Entry entry = (Map.Entry)entries.get(i);
/*  83 */             String key = (String)entry.getKey();
/*  84 */             Long hits = (Long)entry.getValue();
/*  85 */             StringBuffer tmp = new StringBuffer(template);
/*  86 */             StringUtilities.replace(tmp, "{RANK}", String.valueOf(i + 1));
/*  87 */             StringUtilities.replace(tmp, "{HITS}", String.valueOf(hits));
/*     */             
/*  89 */             StringUtilities.replace(tmp, "{DOCID}", key);
/*  90 */             DocumentPage.documentHitLog.info(tmp);
/*     */           } 
/*     */         } 
/*  93 */         DocumentPage.documentHits.clear();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final Logger log = Logger.getLogger(DocumentPage.class);
/*     */   
/*     */   protected SIOLUContainerCreator sioluCreator;
/*     */   
/* 107 */   protected static final Logger documentHitLog = Logger.getLogger("si.document.hits");
/*     */ 
/*     */ 
/*     */   
/*     */   protected DocumentContainer documentContainer;
/*     */ 
/*     */   
/*     */   protected Future future;
/*     */ 
/*     */   
/*     */   private CustomRunnable task;
/*     */ 
/*     */   
/* 120 */   private WaitPage waitPage = null;
/*     */   
/* 122 */   private AverageCalculator averageCalculator = new AverageCalculator(0);
/*     */   
/* 124 */   protected static Map documentHits = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/* 126 */   protected static PeriodicTask ptHitEvaluation = null;
/*     */   
/*     */   private History cprHistory;
/*     */   
/*     */   public static final String TIS2WEB_FRAME = "_top";
/* 131 */   private IFrameElement linkTargetFrame = null;
/*     */   
/*     */   public void setLinkTargetFrame(IFrameElement linkTargetFrame) {
/* 134 */     this.linkTargetFrame = linkTargetFrame;
/*     */   }
/*     */   
/*     */   public IFrameElement getLinkTargetFrame() {
/* 138 */     return this.linkTargetFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMainFrame = true;
/* 143 */   private HistoryLink linkFrom = null;
/*     */ 
/*     */   
/* 146 */   private HistoryLink clean = null;
/*     */   
/*     */   private boolean useClean = false;
/*     */   
/* 150 */   private String lastBookmark = null;
/*     */ 
/*     */   
/*     */   public DocumentPage(ClientContext context) {
/* 154 */     super(context);
/* 155 */     TocTree.getInstance(context).setDocPage(this);
/* 156 */     this.cprHistory = new History(context);
/* 157 */     this.sioluCreator = new SIOLUContainerCreator() {
/*     */         public DocumentContainer create(ClientContext context, SIOLU sio, DocumentPage page) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 159 */           return (DocumentContainer)new SIOLUDocumentContainer((ClientContext)DocumentPage.this.context, sio, DocumentPage.this);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 165 */     synchronized (DocumentPage.class) {
/* 166 */       if (ptHitEvaluation == null) {
/* 167 */         long interval = 0L;
/* 168 */         String intervalProperty = ApplicationContext.getInstance().getProperty("component.si.document.hit.evaluation.interval");
/*     */         try {
/* 170 */           interval = Long.parseLong(intervalProperty) * 60L * 1000L;
/* 171 */         } catch (NumberFormatException e) {
/* 172 */           interval = 86400000L;
/*     */         } 
/*     */         
/* 175 */         Runnable r = new HitEvaluationTask();
/*     */         
/* 177 */         ptHitEvaluation = new PeriodicTask(r, interval);
/* 178 */         ptHitEvaluation.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean backEnabled() {
/*     */     try {
/* 186 */       return (this.documentContainer.getPredecessor() != null);
/* 187 */     } catch (Exception e) {
/* 188 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBack() {
/* 193 */     boolean ret = false;
/*     */     try {
/* 195 */       DocumentContainer dc = this.documentContainer.getPredecessor();
/* 196 */       if (dc != null) {
/* 197 */         setPage(dc);
/* 198 */         ret = true;
/*     */       } 
/* 200 */     } catch (Exception e) {
/* 201 */       log.debug("unable to retrieve prdecessor, ignoring - exception: " + e, e);
/*     */     } 
/*     */     
/* 204 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DocumentPage getInstance(ClientContext context) {
/* 209 */     synchronized (context.getLockObject()) {
/* 210 */       DocumentPage instance = (DocumentPage)context.getObject(DocumentPage.class);
/* 211 */       if (instance == null) {
/* 212 */         instance = new DocumentPage(context);
/* 213 */         TocTree.getInstance(context).addObserver(instance);
/* 214 */         context.storeObject(DocumentPage.class, instance);
/*     */       } 
/* 216 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getURL(Map params) {
/* 221 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 222 */     String bookmark = (params != null) ? (String)params.get("bm") : null;
/* 223 */     if (bookmark != null) {
/* 224 */       url = url + "#" + bookmark;
/* 225 */       this.lastBookmark = bookmark;
/* 226 */     } else if (this.lastBookmark != null) {
/* 227 */       url = url + "#" + this.lastBookmark;
/*     */     } 
/* 229 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(final Map params) {
/* 233 */     if (this.future != null && !this.future.isDone()) {
/*     */       
/* 235 */       try { this.future.get(500L, TimeUnit.MILLISECONDS); }
/* 236 */       catch (ExecutionException e)
/* 237 */       { log.error("unable to retrieve page - exception:", e.getCause());
/* 238 */         throw Util.toRuntimeException(e.getCause()); }
/* 239 */       catch (TimeoutException e) {  }
/* 240 */       catch (InterruptedException e)
/* 241 */       { Thread.currentThread().interrupt(); }
/*     */     
/*     */     }
/*     */     
/* 245 */     if (this.future != null && !this.future.isDone()) {
/* 246 */       if (this.waitPage == null) {
/* 247 */         this.waitPage = new WaitPage(new WaitPage.Callback() {
/*     */               public String getTitle() {
/* 249 */                 return "";
/*     */               }
/*     */               
/*     */               public String getStylesheet() {
/* 253 */                 return StyleSheetController.getInstance(DocumentPage.this.context).getURL();
/*     */               }
/*     */               
/*     */               public long getReload() {
/* 257 */                 return Math.min(2000L, DocumentPage.this.getAverageLoadTime());
/*     */               }
/*     */ 
/*     */               
/*     */               public String getURL() {
/* 262 */                 String ret = DocumentPage.this.getURL(params);
/* 263 */                 ret = Util.unescapeReservedHTMLChars(ret);
/* 264 */                 return ret;
/*     */               }
/*     */               
/*     */               public String getMessage() {
/* 268 */                 return DocumentPage.this.context.getMessage("si.document.page.wait");
/*     */               }
/*     */             });
/*     */       }
/* 272 */       return new ResultObject(0, this.waitPage.getHtmlCode(params));
/*     */     } 
/*     */     
/* 275 */     synchronized (this.context) {
/* 276 */       return new ResultObject(0, getHtmlCode(params));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 282 */     String html = super.getHtmlCode(params);
/* 283 */     if (this.documentContainer instanceof SIOLUDocumentContainer) {
/* 284 */       String styleTag = ((SIOLUDocumentContainer)this.documentContainer).getStyleTag();
/* 285 */       if (styleTag != null) {
/* 286 */         int styleSheetLinkStart = html.indexOf("<link rel=\"stylesheet\"");
/* 287 */         if (styleSheetLinkStart > 0) {
/* 288 */           int styleSheetLinkEnd = html.indexOf(">", styleSheetLinkStart);
/* 289 */           if (styleSheetLinkEnd > 0) {
/* 290 */             StringBuffer tmp = new StringBuffer(html.length() + styleTag.length());
/* 291 */             tmp.append(html.substring(0, styleSheetLinkStart - 1));
/* 292 */             tmp.append(styleTag);
/* 293 */             tmp.append(html.substring(styleSheetLinkEnd + 1));
/* 294 */             html = tmp.toString();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 301 */     return html;
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentContainer setPage(Object _node) {
/* 306 */     if (_node instanceof SIOCPR) {
/* 307 */       this.cprHistory.clearDocumentStack();
/*     */     }
/*     */     
/* 310 */     return setPageDontClear(_node);
/*     */   }
/*     */   
/*     */   public DocumentContainer setCPR(SIO sio) {
/* 314 */     DocumentContainer ret = setPageDontClear(sio);
/* 315 */     onDocChange(sio);
/* 316 */     return ret;
/*     */   }
/*     */   
/*     */   public void onDocChange(Object _node) {
/* 320 */     StandardInfoPanel.getInstance((ClientContext)this.context).setNode(_node, this.cprHistory);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getKey(SITOCElement sio) {
/* 325 */     String ret = "";
/*     */     
/*     */     try {
/* 328 */       if (sio instanceof SIOLU) {
/* 329 */         ret = "LU-" + ((SIOLU)sio).getProperty((SITOCProperty)SIOProperty.LU);
/*     */       }
/* 331 */       else if (sio instanceof SIOWD) {
/* 332 */         Object wd = ((SIOWD)sio).getProperty((SITOCProperty)SIOProperty.WD);
/* 333 */         if (wd != null) {
/* 334 */           ret = "WD-" + wd;
/*     */         } else {
/* 336 */           ret = "WD-" + ((SIOWD)sio).getProperty((SITOCProperty)SIOProperty.LU);
/*     */         }
/*     */       
/* 339 */       } else if (sio instanceof SIOCPR) {
/* 340 */         Object cpr = ((SIOCPR)sio).getProperty((SITOCProperty)SIOProperty.ElectronicSystem);
/* 341 */         if (cpr != null) {
/* 342 */           ret = "CPR-" + cpr;
/*     */         } else {
/* 344 */           ret = "CPR-" + ((SIOCPR)sio).getProperty((SITOCProperty)SIOProperty.LU);
/*     */         } 
/*     */       } else {
/*     */         
/* 348 */         ret = "SIO-" + sio.getID();
/*     */       } 
/* 350 */     } catch (Exception ex) {
/* 351 */       log.error("unable to get Key for sio '" + sio.getID() + "'" + " -exception:" + ex);
/* 352 */       ret = "SIO-" + sio.getID();
/*     */     } 
/* 354 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentContainer setPageDontClear(Object _node) {
/* 359 */     StopWatch sw = StopWatch.getInstance().start(); try {
/*     */       DefaultDocumentContainer defaultDocumentContainer;
/* 361 */       DocumentContainer retValue = null;
/* 362 */       SITOCElement node = (SITOCElement)_node;
/* 363 */       String key = getKey(node);
/*     */       try {
/* 365 */         if (node instanceof SIOLU) {
/* 366 */           retValue = this.sioluCreator.create((ClientContext)this.context, (SIOLU)node, this);
/* 367 */         } else if (node instanceof SIOProxy) {
/* 368 */           SIOLUDocumentContainer sIOLUDocumentContainer = new SIOLUDocumentContainer((ClientContext)this.context, (SIOLU)((SIOProxy)node).getSIO(), this);
/* 369 */         } else if (node instanceof SIOWD) {
/*     */           
/* 371 */           WDDocumentContainer wDDocumentContainer = new WDDocumentContainer((ClientContext)this.context, (SIOWD)node, this);
/* 372 */         } else if (node instanceof SIOCPR) {
/* 373 */           CPRDocumentContainer cprCont = new CPRDocumentContainer((ClientContext)this.context, (SIOCPR)node, this, this.cprHistory, this.linkFrom);
/* 374 */           cprCont.setClean(this.clean, this.useClean);
/* 375 */           this.linkFrom = null;
/* 376 */           this.useClean = false;
/* 377 */           if (cprCont.init()) {
/* 378 */             CPRDocumentContainer cPRDocumentContainer = cprCont;
/*     */           }
/* 380 */           this.clean = null;
/*     */         } else {
/* 382 */           defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.available"));
/*     */         } 
/*     */         
/* 385 */         synchronized (documentHits) {
/* 386 */           Long hits = (Long)documentHits.get(key);
/* 387 */           documentHits.put(key, (hits != null) ? Long.valueOf(hits.longValue() + 1L) : Long.valueOf(1L));
/*     */         }
/*     */       
/* 390 */       } catch (DocumentNotFoundException e) {
/* 391 */         defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.found"));
/* 392 */       } catch (DocumentContainerConstructionException e) {
/* 393 */         defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.available"));
/*     */       } 
/* 395 */       if (defaultDocumentContainer != null) {
/* 396 */         key = key.replace("[", "");
/* 397 */         key = key.replace("]", "");
/* 398 */         SIEventLogFacade.getInstance().createEntry(key.replace("-", ","), (ClientContext)this.context);
/* 399 */         setPage((DocumentContainer)defaultDocumentContainer);
/*     */ 
/*     */         
/* 402 */         long responseTime = sw.stop();
/* 403 */         log.debug("set page finished after:" + responseTime + " ms");
/* 404 */         synchronized (this.averageCalculator) {
/* 405 */           this.averageCalculator.add(BigDecimal.valueOf(responseTime));
/* 406 */           log.debug("new average document load time:" + this.averageCalculator.getCurrentAverage() + " ms");
/*     */         } 
/*     */       } 
/* 409 */       return (DocumentContainer)defaultDocumentContainer;
/*     */     } finally {
/* 411 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAverageLoadTime() {
/* 417 */     return this.averageCalculator.getCurrentAverage().longValue();
/*     */   }
/*     */   
/*     */   public void setPage(Object _node, AsyncMethodCallback callback) {
/*     */     try {
/* 422 */       if (this.isMainFrame) {
/* 423 */         onDocChange(_node);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 428 */       this.task = new CustomRunnable();
/* 429 */       if (_node instanceof SIO) {
/* 430 */         this.task.sio = (SIO)_node;
/*     */       }
/* 432 */       this.task._node = _node;
/* 433 */       this.task.callback = callback;
/* 434 */       this.future = Util.getExecutorService().submit(this.task);
/*     */     }
/* 436 */     catch (Exception e) {
/* 437 */       log.error("unable to setPage - exception" + e.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SIO getSIO() {
/* 443 */     if (this.future != null && !this.future.isDone()) {
/* 444 */       return this.task.sio;
/*     */     }
/*     */     try {
/* 447 */       return this.documentContainer.getSIO();
/* 448 */     } catch (NullPointerException e) {
/* 449 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void setPage(DocumentContainer document) {
/* 455 */     if (this.documentContainer != null && this.documentContainer instanceof CPRDocumentContainer) {
/* 456 */       ((CPRDocumentContainer)this.documentContainer).unregister();
/*     */     }
/* 458 */     removeElement((HtmlElement)this.documentContainer);
/* 459 */     this.documentContainer = document;
/* 460 */     addElement((HtmlElement)this.documentContainer);
/*     */   }
/*     */   
/*     */   public synchronized DocumentContainer getDocContainer() {
/* 464 */     return this.documentContainer;
/*     */   }
/*     */   
/*     */   public void onOptionsChanged() {
/* 468 */     DocumentContainer cont = getDocContainer();
/* 469 */     if (cont instanceof CPRDocumentContainer) {
/* 470 */       ((CPRDocumentContainer)cont).init();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 476 */     if (this.documentContainer != null) {
/* 477 */       return this.documentContainer.getHtmlCode(params);
/*     */     }
/* 479 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 484 */     String styleSheet = null;
/*     */     try {
/* 486 */       styleSheet = this.documentContainer.getStyleSheet();
/* 487 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 490 */     styleSheet = (styleSheet != null) ? ("si/styles/" + styleSheet) : "common/style.css";
/* 491 */     return "res/" + styleSheet;
/*     */   }
/*     */   
/*     */   protected String getTitle() {
/* 495 */     return "";
/*     */   }
/*     */   
/*     */   public synchronized void onSelectionChange(CTOCTree.NodeWrapper selectedNode) {
/* 499 */     Object node = null;
/* 500 */     if (selectedNode == null) {
/* 501 */       removeElement((HtmlElement)this.documentContainer);
/* 502 */       this.documentContainer = null;
/* 503 */       if (this.isMainFrame) {
/* 504 */         onDocChange(node);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 511 */       node = selectedNode.node;
/*     */     } 
/*     */   }
/*     */   
/*     */   public History getCprHistory() {
/* 516 */     return this.cprHistory;
/*     */   }
/*     */   
/*     */   public void setCprHistory(History cprHistory) {
/* 520 */     this.cprHistory = cprHistory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsMainFrame(boolean isMainFrame) {
/* 530 */     this.isMainFrame = isMainFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkFrom(HistoryLink linkFrom) {
/* 539 */     this.linkFrom = linkFrom;
/*     */   }
/*     */   
/*     */   public void setClean(HistoryLink clean, boolean useClean) {
/* 543 */     this.clean = clean;
/* 544 */     this.useClean = useClean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSioluCreator(SIOLUContainerCreator sioluCreator) {
/* 552 */     this.sioluCreator = sioluCreator;
/*     */   }
/*     */   
/*     */   protected String getBodyStyleAttribute(Map params) {
/* 556 */     if (this.documentContainer != null) {
/* 557 */       return this.documentContainer.getBodyStyleAttribute();
/*     */     }
/* 559 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Page getMainPage() {
/* 564 */     return (Page)MainPage.getInstance((ClientContext)this.context);
/*     */   }
/*     */   
/*     */   protected class CustomRunnable
/*     */     implements Runnable {
/* 569 */     public AsyncMethodCallback callback = null;
/* 570 */     public SIO sio = null;
/* 571 */     public Object _node = null;
/*     */     
/*     */     public void run() {
/*     */       try {
/* 575 */         DocumentPage.log.debug("starting asynchronous setPage thread for the sioId:" + this.sio.getID());
/* 576 */         DocumentContainer retValue = DocumentPage.this.setPage(this._node);
/* 577 */         if (this.callback != null) {
/* 578 */           this.callback.onFinished(retValue);
/*     */         }
/* 580 */         DocumentPage.log.debug("finished asynchronous setPage thread");
/* 581 */       } catch (UncheckedInterruptedException e) {
/* 582 */         DocumentPage.log.debug("...thread has been interrupted");
/* 583 */         Thread.currentThread().interrupt();
/* 584 */       } catch (Exception e) {
/* 585 */         DocumentPage.log.warn("...unable to run asynchronous setPage - exception: " + e, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface SIOLUContainerCreator {
/*     */     DocumentContainer create(ClientContext param1ClientContext, SIOLU param1SIOLU, DocumentPage param1DocumentPage) throws DocumentNotFoundException, DocumentContainerConstructionException;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\page\DocumentPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */