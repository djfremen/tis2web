/*     */ package com.eoos.gm.tis2web.frame.dialog.plugincheck;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.server.FileDwnldUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PluginInstallDialog
/*     */   extends Page
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(PluginInstallDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(PluginInstallDialog.class, "plugininstall.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection plugins;
/*     */   
/*     */   private ClickButtonElement beContinue;
/*     */   
/*     */   private CheckBoxElement cbEnableChecking;
/*     */   
/*     */   private boolean checkingError;
/*     */   
/*     */   private HtmlElementContainerBase dynamicLinksContainer;
/*     */ 
/*     */   
/*     */   public PluginInstallDialog(final ClientContext context, Collection plugins, boolean checkingError) {
/*  53 */     super(context);
/*  54 */     this.plugins = plugins;
/*  55 */     this.checkingError = checkingError;
/*     */     
/*  57 */     this.cbEnableChecking = new CheckBoxElement(context.createID());
/*     */     try {
/*  59 */       Boolean value = context.getSharedContext().checkPlugins();
/*  60 */       this.cbEnableChecking.setValue((value != null) ? value : Boolean.TRUE);
/*  61 */     } catch (Exception e) {
/*  62 */       log.warn("unable to determine default value for plugin check enabling state, ignoring - exception: " + e, e);
/*     */     } 
/*     */ 
/*     */     
/*  66 */     addElement((HtmlElement)this.cbEnableChecking);
/*     */     
/*  68 */     this.beContinue = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  71 */           return context.getLabel("continue");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  75 */           PluginInstallDialog.this.onContinue();
/*  76 */           return PluginInstallDialog.this.onFinished();
/*     */         }
/*     */       };
/*  79 */     addElement((HtmlElement)this.beContinue);
/*     */     
/*  81 */     this.dynamicLinksContainer = new HtmlElementContainerBase()
/*     */       {
/*     */         public String getHtmlCode(Map params) {
/*  84 */           return null;
/*     */         }
/*     */       };
/*  87 */     addElement((HtmlElement)this.dynamicLinksContainer);
/*     */   }
/*     */   
/*     */   private void onContinue() {
/*  91 */     Boolean enableChecking = (Boolean)this.cbEnableChecking.getValue();
/*  92 */     ((ClientContext)this.context).getSharedContext().setCheckPlugins(enableChecking);
/*     */   }
/*     */   
/*     */   private String getPluginLinkCode() {
/*  96 */     this.dynamicLinksContainer.removeAllElements();
/*  97 */     StringBuffer tmp = new StringBuffer("<table cellpadding=\"10\">{PLUGINS}</table>");
/*  98 */     for (Iterator<Plugin> iter = this.plugins.iterator(); iter.hasNext(); ) {
/*  99 */       Plugin plugin = iter.next();
/*     */       
/* 101 */       StringBuffer pluginCode = new StringBuffer("<tr><td>{LINK}</td><td>:</td><td>{DESCRIPTION}</tr>");
/* 102 */       String executableName = plugin.getInstallationExecutableName();
/* 103 */       final String displayName = plugin.getDisplayName();
/*     */       
/* 105 */       if (executableName != null && executableName.startsWith("http")) {
/* 106 */         log.debug("...found http(s) protocol in executable name, rendering direct link");
/* 107 */         StringUtilities.replace(pluginCode, "{LINK}", "<a href=\"{HREF}\">{NAME}</a>");
/* 108 */         StringUtilities.replace(pluginCode, "{HREF}", executableName);
/* 109 */         StringUtilities.replace(pluginCode, "{NAME}", displayName);
/*     */       } else {
/* 111 */         final String filename = executableName;
/* 112 */         LinkElement link = new LinkElement(this.context.createID(), null)
/*     */           {
/*     */             public Object onClick(Map submitParams)
/*     */             {
/* 116 */               HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*     */               
/* 118 */               return FileDwnldUtil.getJNLP((ClientContext)PluginInstallDialog.this.context, request, filename);
/*     */             }
/*     */ 
/*     */             
/*     */             protected String getLabel() {
/* 123 */               return displayName;
/*     */             }
/*     */           };
/* 126 */         this.dynamicLinksContainer.addElement((HtmlElement)link);
/* 127 */         StringUtilities.replace(pluginCode, "{LINK}", link.getHtmlCode(null));
/*     */       } 
/* 129 */       StringUtilities.replace(pluginCode, "{DESCRIPTION}", plugin.getDescription(this.context.getLocale()));
/* 130 */       StringUtilities.replace(tmp, "{PLUGINS}", pluginCode + "{PLUGINS}");
/*     */     } 
/*     */     
/* 133 */     StringUtilities.replace(tmp, "{PLUGINS}", "");
/* 134 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 141 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 143 */     if (this.checkingError) {
/* 144 */       StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.plugin.install.dialog.checking.error"));
/*     */     } else {
/* 146 */       StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.plugin.install.dialog"));
/*     */     } 
/* 148 */     StringUtilities.replace(code, "{PLUGINLIST}", getPluginLinkCode());
/* 149 */     StringUtilities.replace(code, "{INPUT_CHECK_ENABLE}", this.cbEnableChecking.getHtmlCode(params));
/* 150 */     StringUtilities.replace(code, "{LABEL_CHECK_ENABLE}", this.context.getMessage("frame.plugin.install.check.enable"));
/* 151 */     StringUtilities.replace(code, "{BUTTON_CONTINUE}", this.beContinue.getHtmlCode(params));
/* 152 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected abstract ResultObject onFinished();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dialog\plugincheck\PluginInstallDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */