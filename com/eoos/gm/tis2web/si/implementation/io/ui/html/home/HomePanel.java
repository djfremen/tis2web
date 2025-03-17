/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BookmarkUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BrochuresUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BulletinsUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.FaultDiagUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.NumberSearchUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.StandardUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.TextSearchUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagPanel;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HomePanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/*  37 */     } catch (Exception e) {
/*  38 */       log.error("could not load template - error:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   private StandardUIIconLink linkStandardInfo;
/*     */   private BulletinsUIIconLink linkBulletins;
/*     */   private BrochuresUIIconLink linkBrochures;
/*     */   private NumberSearchUIIconLink linkNumberSearch;
/*     */   private TextSearchUIIconLink linkTextSearch;
/*     */   private BookmarkUIIconLink linkBookmarks;
/*     */   private FaultDiagUIIconLink faultDiag;
/*     */   
/*     */   private HomePanel(ClientContext context) {
/*  53 */     this.context = context;
/*     */     
/*  55 */     this.linkStandardInfo = new StandardUIIconLink(context);
/*  56 */     addElement((HtmlElement)this.linkStandardInfo);
/*     */     
/*  58 */     this.faultDiag = new FaultDiagUIIconLink(context);
/*  59 */     addElement((HtmlElement)this.faultDiag);
/*     */     
/*  61 */     this.linkBulletins = new BulletinsUIIconLink(context);
/*  62 */     addElement((HtmlElement)this.linkBulletins);
/*     */     
/*  64 */     this.linkBrochures = new BrochuresUIIconLink(context);
/*  65 */     addElement((HtmlElement)this.linkBrochures);
/*     */     
/*  67 */     this.linkNumberSearch = new NumberSearchUIIconLink(context);
/*  68 */     addElement((HtmlElement)this.linkNumberSearch);
/*     */     
/*  70 */     this.linkTextSearch = new TextSearchUIIconLink(context);
/*  71 */     addElement((HtmlElement)this.linkTextSearch);
/*     */     
/*  73 */     this.linkBookmarks = new BookmarkUIIconLink(context);
/*  74 */     addElement((HtmlElement)this.linkBookmarks);
/*     */   }
/*     */   
/*     */   public static HomePanel getInstance(ClientContext context) {
/*  78 */     synchronized (context.getLockObject()) {
/*  79 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/*  80 */       if (instance == null) {
/*  81 */         instance = new HomePanel(context);
/*  82 */         context.storeObject(HomePanel.class, instance);
/*     */       } 
/*  84 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  89 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  91 */     StringUtilities.replace(code, "{TEXT_HOME_TITLE}", this.context.getMessage("si.home.title"));
/*  92 */     StringUtilities.replace(code, "{LINK_STANDARD}", this.linkStandardInfo.getHtmlCode(params));
/*  93 */     StringUtilities.replace(code, "{TEXT_STANDARD}", this.context.getMessage("si.home.stdinfo"));
/*  94 */     StringUtilities.replace(code, "{LINK_BOOKMARKS}", this.linkBookmarks.getHtmlCode(params));
/*  95 */     StringUtilities.replace(code, "{TEXT_BOOKMARKS}", this.context.getMessage("si.home.bookmarks"));
/*     */     
/*  97 */     boolean showBrochuresLink = true;
/*  98 */     boolean showNumberSearchLink = true;
/*  99 */     boolean showBulletinLink = ModuleContext.getInstance(this.context.getSessionID()).allowBulletinAccess();
/* 100 */     boolean showFaultDiag = false;
/*     */     
/* 102 */     if (WIS.hasSaabData(this.context)) {
/*     */       
/* 104 */       showBrochuresLink = false;
/* 105 */       showFaultDiag = FaultDiagPanel.getInstance(this.context).hasData();
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (!showFaultDiag) {
/* 110 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!-- FAULTDIAGNOSIS -->", "<!-- /FAULTDIAGNOSIS -->", 0, true, false);
/* 111 */       if (index != null) {
/* 112 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } else {
/* 115 */       StringUtilities.replace(code, "{TEXT_FAULTDIAG}", this.context.getMessage("si.home.faultDiag"));
/* 116 */       StringUtilities.replace(code, "{LINK_FAULTDIAG}", this.faultDiag.getHtmlCode(params));
/*     */     } 
/*     */ 
/*     */     
/* 120 */     if (!showBulletinLink) {
/* 121 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!-- BULLETINS -->", "<!-- /BULLETINS -->", 0, true, false);
/* 122 */       if (index != null) {
/* 123 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } else {
/* 126 */       StringUtilities.replace(code, "{LINK_BULLETINS}", this.linkBulletins.getHtmlCode(params));
/* 127 */       StringUtilities.replace(code, "{TEXT_BULLETINS}", this.context.getMessage("si.home.bulletins"));
/*     */     } 
/*     */     
/* 130 */     if (!showBrochuresLink) {
/* 131 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!-- BROCHURES -->", "<!-- /BROCHURES -->", 0, true, false);
/* 132 */       if (index != null) {
/* 133 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } else {
/* 136 */       StringUtilities.replace(code, "{LINK_BROCHURES}", this.linkBrochures.getHtmlCode(params));
/* 137 */       StringUtilities.replace(code, "{TEXT_BROCHURES}", this.context.getMessage("si.home.brochures"));
/*     */     } 
/*     */     
/* 140 */     if (!showNumberSearchLink) {
/* 141 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!-- NUMBER_SEARCH -->", "<!-- /NUMBER_SEARCH -->", 0, true, false);
/* 142 */       if (index != null) {
/* 143 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } else {
/* 146 */       StringUtilities.replace(code, "{LINK_NUMBER_SEARCH}", this.linkNumberSearch.getHtmlCode(params));
/* 147 */       StringUtilities.replace(code, "{TEXT_NUMBER_SEARCH}", this.context.getMessage("si.home.number.search"));
/*     */     } 
/*     */     
/* 150 */     StringUtilities.replace(code, "{LINK_FULL_SEARCH}", this.linkTextSearch.getHtmlCode(params));
/* 151 */     StringUtilities.replace(code, "{TEXT_FULL_SEARCH}", this.context.getMessage("si.home.text.search"));
/*     */     
/* 153 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */