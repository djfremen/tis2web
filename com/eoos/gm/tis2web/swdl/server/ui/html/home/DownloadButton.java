/*     */ package com.eoos.gm.tis2web.swdl.server.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.LCI;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.ApplicationRegistry;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
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
/*  43 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  51 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/swdl/download/swdl.jnlp", null);
/*  52 */     } catch (Exception e) {
/*  53 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  62 */     super(context.createID(), null);
/*  63 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  67 */     return this.context.getLabel("swdl.start.download");
/*     */   }
/*     */   
/*     */   private String getConfigurationProperty(String key, String defaultValue) {
/*  71 */     ConfigurationService cs = ConfigurationServiceProvider.getService();
/*  72 */     String ret = cs.getProperty(key);
/*  73 */     if (ret == null) {
/*  74 */       ret = defaultValue;
/*     */     }
/*  76 */     return ret;
/*     */   }
/*     */   public Object onClick(Map submitParams) {
/*     */     URL codebase;
/*  80 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  81 */     String cookie = request.getHeader("Cookie");
/*  82 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  84 */       this.cookiesDisabled = true;
/*     */       
/*  86 */       HtmlElementContainer container = getContainer();
/*  87 */       while (container.getContainer() != null) {
/*  88 */         container = container.getContainer();
/*     */       }
/*  90 */       return container;
/*     */     } 
/*  92 */     this.cookiesDisabled = false;
/*     */ 
/*     */     
/*  95 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  96 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.swdl.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/* 100 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/* 101 */     } catch (MalformedURLException e) {
/* 102 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 105 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.swdl.url.server"), request);
/*     */     
/* 107 */     String dispatchURL_Task = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/* 109 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 110 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 112 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/* 113 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 115 */     StringUtilities.replace(jnlp, "{DISPATCHURL_TASK}", dispatchURL_Task);
/* 116 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 117 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/* 118 */     StringUtilities.replace(jnlp, "{COUNTRYCODE}", SharedContext.getInstance(this.context).getCountry());
/* 119 */     StringUtilities.replace(jnlp, "{DTCUPLOAD}", getConfigurationProperty("frame.dtc.upload.disabled", "false"));
/*     */     
/*     */     try {
/* 122 */       StringUtilities.replace(jnlp, "{LCI}", Util.toExternal(getLCI()));
/* 123 */     } catch (IOException e) {
/* 124 */       throw new RuntimeException(e);
/*     */     } 
/* 126 */     StringUtilities.replace(jnlp, "{TS}", String.valueOf(System.currentTimeMillis()));
/*     */     
/* 128 */     String bacCode = retrieveBACCode();
/* 129 */     if (bacCode != null && bacCode.trim().compareTo("") != 0) {
/* 130 */       StringUtilities.replace(jnlp, "{BACCODE}", bacCode);
/*     */     }
/* 132 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */     
/* 134 */     if (ApplicationContext.getInstance().isStandalone()) {
/* 135 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "local");
/*     */     } else {
/* 137 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "central");
/*     */     } 
/*     */ 
/*     */     
/* 141 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */ 
/*     */     
/* 144 */     ApplicationRegistry.newInstance(this.context);
/*     */     
/* 146 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 150 */     String code = super.getHtmlCode(parameters);
/* 151 */     if (this.cookiesDisabled) {
/* 152 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 153 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("swdl.notify.cookies.disabled"));
/* 154 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 155 */       code = tmp.toString();
/*     */     } 
/* 157 */     return code;
/*     */   }
/*     */   
/*     */   public String retrieveBACCode() {
/* 161 */     String dcStr = null;
/*     */     try {
/* 163 */       SharedContext sc = this.context.getSharedContext();
/* 164 */       DealerCode testDC = (DealerCode)sc.getObject(DealerCode.class);
/* 165 */       dcStr = testDC.getDealerCode();
/* 166 */       log.debug("Dealercode from Shared Context: " + dcStr);
/* 167 */     } catch (Exception e) {
/* 168 */       log.error("unable to retrieve DealerCode from Shared Context: " + e, e);
/*     */     } 
/* 170 */     return dcStr;
/*     */   }
/*     */ 
/*     */   
/*     */   private LCI getLCI() {
/* 175 */     ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/* 176 */     String group = (String)CollectionUtil.getFirst(SharedContext.getInstance(this.context).getUserGroups());
/*     */     
/* 178 */     String _expiration = configurationService.getProperty("component.swdl.license.expiration.delay." + Util.toLowerCase(group));
/* 179 */     if (Util.isNullOrEmpty(_expiration)) {
/* 180 */       _expiration = configurationService.getProperty("component.swdl.license.expiration.delay");
/*     */     }
/* 182 */     long duration = Util.ONE_DAY;
/*     */     try {
/* 184 */       duration = Util.parseDuration(_expiration).getAsMillis();
/* 185 */     } catch (IllegalArgumentException e) {
/*     */       try {
/* 187 */         duration = Integer.parseInt(_expiration) * Util.ONE_DAY;
/* 188 */       } catch (NumberFormatException ee) {
/* 189 */         log.warn("unable to evaluate configuration entry for license expiration delay, using 1 day");
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     long sessionDuration = SharedContext.getInstance(this.context).getLoginInfo().getMaximumSessionDuration();
/* 194 */     if (sessionDuration != -1L) {
/* 195 */       duration = Math.max(Util.ONE_DAY, Math.min(duration, sessionDuration));
/*     */     }
/*     */     
/* 198 */     long maxDiff = Util.ONE_HOUR;
/*     */     try {
/* 200 */       maxDiff = Util.parseDuration(configurationService.getProperty("component.swdl.max.clock.diff")).getAsMillis();
/* 201 */     } catch (IllegalArgumentException e) {
/* 202 */       log.warn("unable to evaluate configuration entry for max clock difference, using 1 hour");
/*     */     } 
/*     */     
/* 205 */     return new LCI(System.currentTimeMillis(), maxDiff, duration);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\serve\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */