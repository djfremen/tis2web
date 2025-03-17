/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.panel.std;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.gtwo.RootDispatcher;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.SIServiceOverlay;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.Data;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system.StatContContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system.StatContRequestHandler;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.IFrameElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RECompiler;
/*     */ import org.apache.regexp.REProgram;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(StandardPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  41 */       template = ApplicationContext.getInstance().loadFile(StandardPanel.class, "standardpanel.html", null).toString();
/*  42 */     } catch (Exception e) {
/*  43 */       log.error("could not load template - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*  47 */   private static REProgram RE_SRC_BEGIN = null;
/*     */   
/*  49 */   private static REProgram RE_HREF_BEGIN = null;
/*     */   
/*  51 */   private static REProgram RE_SRC_END = null; private ClientContext context; private ModuleMenu moduleMenu;
/*     */   static {
/*     */     try {
/*  54 */       RECompiler comp = new RECompiler();
/*  55 */       RE_SRC_BEGIN = comp.compile("<[^>]*?src\\s*=\\s*\"");
/*  56 */       RE_HREF_BEGIN = comp.compile("<[^>]*?href\\s*=\\s*\"");
/*     */ 
/*     */ 
/*     */       
/*  60 */       RE_SRC_END = comp.compile("\"");
/*     */     }
/*  62 */     catch (Exception e) {
/*  63 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IFrameElement iframe;
/*     */ 
/*     */   
/*     */   private Integer iframeHeight;
/*     */ 
/*     */   
/*  75 */   private String startDocument = null;
/*     */ 
/*     */   
/*     */   private StandardPanel(final ClientContext context) {
/*  79 */     this.context = context;
/*  80 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  83 */             StandardPanel.this.startDocument = null;
/*     */           }
/*     */         });
/*     */     
/*  87 */     ModuleMenu.Connector connector = new ModuleMenu.Connector()
/*     */       {
/*     */         public String getPageCode(Map params) {
/*     */           try {
/*  91 */             synchronized (context.getLockObject()) {
/*  92 */               String contextPath = RootDispatcher.getInstance().getContextPath();
/*  93 */               String servletPath = RootDispatcher.getInstance().getServletPath();
/*     */               
/*  95 */               Data data = StatContContext.getInstance(context).getCurrent();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 100 */               StringBuffer tmp = new StringBuffer(new String(data.toByteArray(), "UTF-8"));
/*     */               
/* 102 */               SectionIndex index = null;
/* 103 */               RE srcBeg = new RE(StandardPanel.RE_SRC_BEGIN, 1);
/* 104 */               RE hRef = new RE(StandardPanel.RE_HREF_BEGIN, 1);
/* 105 */               RE srcEnd = new RE(StandardPanel.RE_SRC_END); while (true) {
/* 106 */                 if ((index = StringUtilities.getSectionIndex(tmp.toString(), srcBeg, srcEnd, (index != null) ? index.end : 0, false, false)) != null) {
/* 107 */                   String url = StringUtilities.getSectionContent(tmp, index);
/* 108 */                   url = contextPath + servletPath + StatContRequestHandler.getInstance(context).getPath() + data.getPath() + "/" + url;
/* 109 */                   StringUtilities.replaceSectionContent(tmp, index, url); continue;
/*     */                 }  break;
/* 111 */               }  while (true) { if ((index = StringUtilities.getSectionIndex(tmp.toString(), hRef, srcEnd, (index != null) ? index.end : 0, false, false)) != null) {
/* 112 */                   String url = StringUtilities.getSectionContent(tmp, index);
/* 113 */                   url = contextPath + servletPath + StatContRequestHandler.getInstance(context).getPath() + data.getPath() + "/" + url;
/* 114 */                   StringUtilities.replaceSectionContent(tmp, index, url); continue;
/*     */                 }  break; }
/*     */               
/* 117 */               return tmp.toString();
/*     */             }
/*     */           
/* 120 */           } catch (Exception e) {
/* 121 */             StandardPanel.log.error("unable to create print view - exception: " + e, e);
/* 122 */             return "<html><body><span style=\"text-align:center;width:100%\"><b>" + context.getMessage("si.print.view.error") + "</b></span></body></html>";
/*     */           } 
/*     */         }
/*     */         
/*     */         public void back(Map submitParams) {
/* 127 */           synchronized (context.getLockObject()) {
/* 128 */             StatContContext.getInstance(context).back();
/*     */           } 
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/* 133 */           return true;
/*     */         }
/*     */       };
/*     */     
/* 137 */     this.moduleMenu = new ModuleMenu(context, connector);
/* 138 */     addElement((HtmlElement)this.moduleMenu);
/*     */     
/* 140 */     this.iframe = new IFrameElement("statcont_top")
/*     */       {
/*     */         protected String getWidth() {
/* 143 */           return "100%";
/*     */         }
/*     */         
/*     */         protected String getHeight() {
/* 147 */           return String.valueOf(StandardPanel.this.iframeHeight);
/*     */         }
/*     */         
/*     */         protected String getSourceURL(Map params) {
/* 151 */           return context.getRequestURL() + "/se" + StatContRequestHandler.getInstance(context).getPath() + StandardPanel.this.getStartDocument();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 156 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 157 */     this.iframeHeight = scp.getDisplayHeight();
/* 158 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*     */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 160 */             StandardPanel.this.iframeHeight = newHeight;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static StandardPanel getInstance(ClientContext context) {
/* 167 */     synchronized (context.getLockObject()) {
/* 168 */       StandardPanel instance = (StandardPanel)context.getObject(StandardPanel.class);
/* 169 */       if (instance == null) {
/* 170 */         instance = new StandardPanel(context);
/* 171 */         context.storeObject(StandardPanel.class, instance);
/*     */       } 
/* 173 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 178 */     StringBuffer code = new StringBuffer(template);
/* 179 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/* 180 */     StringUtilities.replace(code, "{IFRAME}", this.iframe.getHtmlCode(params));
/* 181 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStartDocument() {
/* 186 */     if (this.startDocument == null) {
/* 187 */       this.startDocument = SIServiceOverlay.getInstance().getStartDocument(this.context);
/*     */     }
/* 189 */     return this.startDocument;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\panel\std\StandardPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */