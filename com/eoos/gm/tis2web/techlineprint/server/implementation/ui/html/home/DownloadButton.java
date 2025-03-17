/*     */ package com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WindowsLanguageMap;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*     */   private static String jnlpTemplate;
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*  41 */   private static String devices = null;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/techlineprint/client/techlineprint.jnlp", null);
/*  46 */     } catch (Exception e) {
/*  47 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */   protected boolean cookiesDisabled = false;
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  56 */     super(context.createID(), null);
/*  57 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  61 */     return this.context.getLabel("techlineprint.start.download");
/*     */   }
/*     */   public Object onClick(Map submitParams) {
/*     */     URL codebase;
/*  65 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  66 */     String cookie = request.getHeader("Cookie");
/*  67 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  69 */       this.cookiesDisabled = true;
/*     */       
/*  71 */       HtmlElementContainer container = getContainer();
/*  72 */       while (container.getContainer() != null) {
/*  73 */         container = container.getContainer();
/*     */       }
/*  75 */       return container;
/*     */     } 
/*  77 */     this.cookiesDisabled = false;
/*     */ 
/*     */     
/*  80 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  81 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.techlineprint.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/*  85 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/*  86 */     } catch (MalformedURLException e) {
/*  87 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  90 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/*  92 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/*  93 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/*  95 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/*  96 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/*  98 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/*  99 */     StringUtilities.replace(jnlp, "{LOCALE}", String.valueOf(this.context.getLocale()));
/* 100 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/* 101 */     StringUtilities.replace(jnlp, "{NATIVE_LAN}", WindowsLanguageMap.getInstance().get(this.context.getLocale()));
/* 102 */     StringUtilities.replace(jnlp, "{DEVICES}", getDevices());
/*     */ 
/*     */     
/* 105 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 107 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 111 */     String code = super.getHtmlCode(parameters);
/* 112 */     if (this.cookiesDisabled) {
/* 113 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 114 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("techlineprint.notify.cookies.disabled"));
/* 115 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 116 */       code = tmp.toString();
/*     */     } 
/* 118 */     return code;
/*     */   }
/*     */   
/*     */   private static synchronized String getDevices() {
/* 122 */     if (devices == null) {
/* 123 */       devices = new String();
/* 124 */       List<String> list = new ArrayList();
/* 125 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.techlineprint.device.");
/* 126 */       for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 127 */         String device = subConfigurationWrapper.getProperty(iter.next());
/* 128 */         list.add(device);
/*     */       } 
/* 130 */       Collections.sort(list);
/* 131 */       for (int i = 0; i < list.size(); i++) {
/* 132 */         if (i > 0) {
/* 133 */           devices += ", ";
/*     */         }
/* 135 */         devices += list.get(i);
/*     */       } 
/*     */     } 
/* 138 */     return devices;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\server\implementatio\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */