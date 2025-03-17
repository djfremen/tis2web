/*     */ package com.eoos.gm.tis2web.admin.info.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.cache.AbstractCache;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Panel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(Panel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  28 */       template = ApplicationContext.getInstance().loadFile(Panel.class, "panel.html", null).toString();
/*  29 */     } catch (Exception e) {
/*  30 */       log.error("error loading template - error:" + e, e);
/*  31 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private ClientContext context;
/*     */   private ClickButtonElement buttonAllocate;
/*     */   private ClickButtonElement buttonClearCaches;
/*     */   
/*     */   private Panel(final ClientContext context) {
/*  40 */     this.context = context;
/*  41 */     this.buttonAllocate = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams)
/*     */         {
/*     */           try {
/*  46 */             Util.StringOutput out = new Util.StringOutput()
/*     */               {
/*     */                 public void write(String string) {
/*  49 */                   Panel.log.debug(string);
/*     */                 }
/*     */               };
/*     */             
/*  53 */             Util.burstMemory(104857600, out);
/*  54 */             Util.burstMemory(10485760, out);
/*  55 */             Util.burstMemory(1048576, out);
/*  56 */           } catch (OutOfMemoryError t) {
/*  57 */             Panel.log.debug("out of memory reached, releasing memory");
/*     */           } 
/*     */           
/*  60 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/*  65 */           return context.getLabel("burst.memory");
/*     */         }
/*     */       };
/*  68 */     addElement((HtmlElement)this.buttonAllocate);
/*     */     
/*  70 */     this.buttonClearCaches = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  73 */           Page page = (Page)getTopLevelContainer();
/*     */           try {
/*  75 */             AbstractCache.ClearanceResult result = AbstractCache.clearAllCaches();
/*  76 */             return page.getInfoPopup("Cleared " + result.getCacheCount() + " caches (" + result.getEntryCount() + " entries)", null);
/*  77 */           } catch (Exception e) {
/*  78 */             return page.getErrorPopup("Unable to clear !<br>Exception: " + e, null);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/*  84 */           return "Clear all caches";
/*     */         }
/*     */       };
/*  87 */     addElement((HtmlElement)this.buttonClearCaches);
/*     */   }
/*     */   
/*     */   public static Panel getInstance(ClientContext context) {
/*  91 */     synchronized (context.getLockObject()) {
/*  92 */       Panel ret = (Panel)context.getObject(Panel.class);
/*  93 */       if (ret == null) {
/*  94 */         ret = new Panel(context);
/*  95 */         context.storeObject(Panel.class, ret);
/*     */       } 
/*  97 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getApplicationVersion() {
/*     */     try {
/* 103 */       StringBuffer buffer = new StringBuffer();
/* 104 */       buffer.append(ConfigurationServiceProvider.getService().getProperty("frame.application.description"));
/* 105 */       buffer.append(" (").append(ConfigurationServiceProvider.getService().getProperty("frame.application.build"));
/* 106 */       buffer.append("/").append(ApplicationContext.getInstance().getBuildNumber()).append(")");
/* 107 */       return buffer.toString();
/* 108 */     } catch (Exception e) {
/* 109 */       log.error("unable to create application version string, ignoring - exception: " + e, e);
/* 110 */       return "n/a";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 116 */     StringBuffer buffer = new StringBuffer(template);
/* 117 */     Util.replace(buffer, "{LABEL_IP}", this.context.getLabel("server.ip"));
/* 118 */     Util.replace(buffer, "{IP}", ApplicationContext.getInstance().getIPAddr());
/* 119 */     Util.replace(buffer, "{LABEL_HOSTNAME}", this.context.getLabel("hostname"));
/* 120 */     Util.replace(buffer, "{HOSTNAME}", ApplicationContext.getInstance().getHostName());
/* 121 */     Util.replace(buffer, "{LABEL_PORT}", this.context.getLabel("port"));
/* 122 */     Util.replace(buffer, "{PORT}", String.valueOf(ApplicationContext.getInstance().getPort()));
/*     */     
/* 124 */     Util.replace(buffer, "{LABEL_STARTUP}", this.context.getLabel("server.startup"));
/* 125 */     Util.replace(buffer, "{STARTUP}", DateFormat.getDateTimeInstance(3, 1).format(new Date(ApplicationContext.getInstance().getStartupTimestamp())) + " (" + Util.formatDate(ApplicationContext.getInstance().getStartupTimestamp()) + ")");
/*     */     
/* 127 */     Util.replace(buffer, "{LABEL_TIME}", this.context.getLabel("time"));
/* 128 */     Util.replace(buffer, "{TIME}", DateFormat.getDateTimeInstance(3, 1).format(new Date()) + " (" + Util.formatDate(System.currentTimeMillis()) + ")");
/* 129 */     Util.replace(buffer, "{LABEL_VERSION}", this.context.getLabel("version"));
/* 130 */     Util.replace(buffer, "{VERSION}", getApplicationVersion());
/*     */     
/* 132 */     if (this.context.isSpecialAccess()) {
/* 133 */       Util.replace(buffer, "{LABEL_SESSIONCOUNT}", this.context.getLabel("session.count"));
/* 134 */       StringBuilder tmp2 = new StringBuilder();
/* 135 */       tmp2.append(ClientContextProvider.getInstance().currentSessionCount());
/* 136 */       tmp2.append(" (");
/* 137 */       tmp2.append("public: ");
/* 138 */       tmp2.append(ClientContextProvider.getInstance().currentPublicAccessCount(null));
/* 139 */       tmp2.append(")");
/*     */       try {
/* 141 */         CTSessionCount.Result result = CTSessionCount.execute(this.context);
/* 142 */         tmp2.append(" / ");
/* 143 */         tmp2.append(result.getSessionCount());
/* 144 */         tmp2.append(" (");
/* 145 */         tmp2.append("public: ");
/* 146 */         tmp2.append(result.getPublicCount());
/* 147 */         tmp2.append(")");
/* 148 */       } catch (Exception e) {
/* 149 */         log.warn("unable to retrieve total session count, ignoring - exception:", e);
/*     */       } 
/*     */       
/* 152 */       Util.replace(buffer, "{SESSIONCOUNT}", tmp2.toString());
/*     */     } else {
/* 154 */       Util.replace(buffer, "{LABEL_SESSIONCOUNT}", "");
/* 155 */       Util.replace(buffer, "{SESSIONCOUNT}", "");
/*     */     } 
/*     */ 
/*     */     
/* 159 */     Util.replace(buffer, "{LABEL_MEMORY}", this.context.getLabel("memory"));
/* 160 */     final StringBuffer tmp = new StringBuffer();
/* 161 */     Util.createMemoryMonitor(new Util.StringOutput()
/*     */         {
/*     */           public void write(String string) {
/* 164 */             tmp.append(string);
/*     */           }
/*     */         }).run();
/* 167 */     Util.replace(buffer, "{MEMORY}", tmp.toString());
/*     */     
/* 169 */     if (this.context.isSpecialAccess()) {
/* 170 */       Util.replace(buffer, "{BUTTON_ALLOCATE}", this.buttonAllocate.getHtmlCode(params));
/*     */     } else {
/* 172 */       Util.replace(buffer, "{BUTTON_ALLOCATE}", "");
/*     */     } 
/*     */ 
/*     */     
/* 176 */     if (this.context.isSpecialAccess()) {
/* 177 */       Util.replace(buffer, "{BUTTON_CLEARCACHES}", this.buttonClearCaches.getHtmlCode(params));
/*     */     } else {
/* 179 */       Util.replace(buffer, "{BUTTON_CLEARCACHES}", "");
/*     */     } 
/*     */     
/* 182 */     if (!this.context.isSpecialAccess()) {
/* 183 */       Util.replace(buffer, Pattern.compile("(?s)<!-- SPECIAL -->(.*?)<!-- /SPECIAL -->"), new Util.ReplacementCallback()
/*     */           {
/*     */             public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 186 */               return "";
/*     */             }
/*     */           });
/*     */     }
/* 190 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\inf\\ui\Panel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */