/*     */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container.version;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class VersionDocument
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  25 */   private static Logger log = Logger.getLogger(VersionDocument.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  30 */       template = ApplicationContext.getInstance().loadFile(VersionDocument.class, "versiondocument.html", null).toString();
/*  31 */     } catch (Exception e) {
/*  32 */       log.error("unable to load template - error:" + e, e);
/*  33 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   protected String title;
/*     */   
/*     */   protected VersionListElement body;
/*     */ 
/*     */   
/*     */   public VersionDocument(ClientContext context, String title) {
/*  46 */     this.context = context;
/*  47 */     this.title = title;
/*  48 */     this.body = new VersionListElement(context, getModuleInformationList(context));
/*     */   }
/*     */   
/*     */   protected List getModuleInformationList(ClientContext context) {
/*  52 */     List<ModuleInformation> ret = new ArrayList();
/*     */     
/*  54 */     Collection modules = ConfiguredServiceProvider.getInstance().getServices(VisualModule.class);
/*  55 */     for (Iterator<VisualModule> iter = modules.iterator(); iter.hasNext(); ) {
/*  56 */       VisualModule vm = iter.next();
/*     */       try {
/*  58 */         ModuleInformation moduleInfo = vm.getModuleInformation();
/*  59 */         if (moduleInfo != null) {
/*  60 */           ret.add(moduleInfo);
/*     */         }
/*  62 */         if (vm instanceof ModuleInformation.ClientInfoRetrieval) {
/*  63 */           ModuleInformation moduleInformation = ((ModuleInformation.ClientInfoRetrieval)vm).getClientModuleInformation();
/*  64 */           if (moduleInformation != null) {
/*  65 */             ret.add(moduleInformation);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*  70 */       catch (Exception e) {
/*  71 */         log.warn("unable to retrieve module information from module " + String.valueOf(vm) + " - exception:" + e, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  76 */     ModuleInformation moduleInfoClient = HelpServiceProvider.getInstance().getService().getModuleInformation();
/*  77 */     if (moduleInfoClient != null) {
/*  78 */       ret.add(moduleInfoClient);
/*     */     }
/*  80 */     moduleInfoClient = NewsServiceProvider.getInstance().getService().getModuleInformation();
/*  81 */     if (moduleInfoClient != null) {
/*  82 */       ret.add(moduleInfoClient);
/*     */     }
/*     */     
/*  85 */     return ret;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  89 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  91 */     StringUtilities.replace(code, "{TITLE}", this.title);
/*     */     
/*  93 */     String buildNumber = ApplicationContext.getInstance().getBuildNumber();
/*     */     
/*  95 */     StringBuffer platformInfo = new StringBuffer();
/*  96 */     platformInfo.append(ConfigurationServiceProvider.getService().getProperty("frame.application.description"));
/*  97 */     platformInfo.append(" (" + ConfigurationServiceProvider.getService().getProperty("frame.application.build"));
/*  98 */     if (!Util.isNullOrEmpty(buildNumber)) {
/*  99 */       platformInfo.append("/").append(buildNumber);
/*     */     }
/* 101 */     platformInfo.append(")");
/*     */     
/* 103 */     StringUtilities.replace(code, "{PLATFORM_INFO}", platformInfo.toString());
/* 104 */     StringUtilities.replace(code, "{VERSION_INFORMATION}", this.body.getHtmlCode(params));
/* 105 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\version\VersionDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */