/*     */ package com.eoos.gm.tis2web.kdr.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.SecurityToken;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
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
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  47 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/kdr/client/kdr.jnlp", null);
/*  48 */     } catch (Exception e) {
/*  49 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  58 */     super(context.createID(), null);
/*  59 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  63 */     return this.context.getLabel("kdr.start.download");
/*     */   }
/*     */   
/*     */   private String getConfigurationProperty(String key, String defaultValue) {
/*  67 */     ConfigurationService cs = ConfigurationServiceProvider.getService();
/*  68 */     String ret = cs.getProperty(key);
/*  69 */     if (ret == null) {
/*  70 */       ret = defaultValue;
/*     */     }
/*  72 */     return ret;
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  76 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  77 */     String jnlp = getJNLPDescriptor(request);
/*  78 */     if (jnlp == null) {
/*  79 */       HtmlElementContainer container = getContainer();
/*  80 */       while (container.getContainer() != null) {
/*  81 */         container = container.getContainer();
/*     */       }
/*  83 */       return container;
/*     */     } 
/*  85 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.getBytes()));
/*     */   }
/*     */   
/*     */   public String getJNLPDescriptor(HttpServletRequest request) {
/*     */     URL codebase;
/*  90 */     String cookie = request.getHeader("Cookie");
/*  91 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  93 */       this.cookiesDisabled = true;
/*     */       
/*  95 */       return null;
/*     */     } 
/*  97 */     this.cookiesDisabled = false;
/*     */     
/*  99 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/* 100 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.kdr.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/* 104 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/* 105 */     } catch (MalformedURLException e) {
/* 106 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 109 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/* 111 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 112 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 114 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("tis2web.server.url"), request);
/* 115 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 117 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 118 */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*     */     
/* 120 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/* 121 */     StringUtilities.replace(jnlp, "{SECTOKEN}", SecurityToken.getInstance(this.context).createToken());
/* 122 */     String bacCode = retrieveBACCode();
/* 123 */     if (bacCode != null && bacCode.trim().compareTo("") != 0) {
/* 124 */       StringUtilities.replace(jnlp, "{BACCODE}", bacCode);
/*     */     }
/* 126 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/* 127 */     StringUtilities.replace(jnlp, "{COUNTRYCODE}", SharedContext.getInstance(this.context).getCountry());
/* 128 */     StringUtilities.replace(jnlp, "{DTCUPLOAD}", getConfigurationProperty("frame.dtc.upload.disabled", "false"));
/* 129 */     StringUtilities.replace(jnlp, "{EXCLDIRVISTA}", getConfigurationProperty("component.kdr.excluded.dirs.vista", ""));
/* 130 */     StringUtilities.replace(jnlp, "{REQDISKSPACE}", getConfigurationProperty("component.kdr.required.disk.space", "150"));
/*     */     
/* 132 */     String smartStartVersion = ApplicationContext.getInstance().getProperty("component.kdr.smart.start.version");
/* 133 */     if (Util.isNullOrEmpty(smartStartVersion)) {
/* 134 */       StringUtilities.replace(jnlp, "{SMARTSTARTVERSION}", "");
/*     */     } else {
/* 136 */       StringUtilities.replace(jnlp, "{SMARTSTARTVERSION}", "<property name=\"smart.start.version\" value=\"" + smartStartVersion + "\" />");
/*     */     } 
/*     */     
/* 139 */     String appServerDwnld = ApplicationContext.getInstance().getProperty("frame.dwnld.include.appserver");
/* 140 */     if (Util.isNullOrEmpty(appServerDwnld)) {
/* 141 */       appServerDwnld = "true";
/*     */     }
/* 143 */     StringUtilities.replace(jnlp, "{APPSERVER_DOWNLOAD}", appServerDwnld);
/*     */ 
/*     */     
/* 146 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 148 */     return jnlp.toString();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 152 */     String code = super.getHtmlCode(parameters);
/* 153 */     if (this.cookiesDisabled) {
/* 154 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 155 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("kdr.notify.cookies.disabled"));
/* 156 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 157 */       code = tmp.toString();
/*     */     } 
/* 159 */     return code;
/*     */   }
/*     */   
/*     */   public String retrieveBACCode() {
/* 163 */     String dcStr = null;
/*     */     try {
/* 165 */       SharedContext sc = this.context.getSharedContext();
/* 166 */       DealerCode testDC = (DealerCode)sc.getObject(DealerCode.class);
/* 167 */       dcStr = testDC.getDealerCode();
/* 168 */       log.debug("Dealercode from Shared Context: " + dcStr);
/* 169 */     } catch (Exception e) {
/* 170 */       log.error("unable to retrieve DealerCode from Shared Context: " + e, e);
/*     */     } 
/* 172 */     return dcStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kd\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */