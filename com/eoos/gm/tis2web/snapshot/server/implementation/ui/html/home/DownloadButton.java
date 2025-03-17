/*     */ package com.eoos.gm.tis2web.snapshot.server.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.SecurityToken;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WindowsLanguageMap;
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
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  44 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/snapshot/client/snapshot.jnlp", null);
/*  45 */     } catch (Exception e) {
/*  46 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  55 */     super(context.createID(), null);
/*  56 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  60 */     return this.context.getLabel("snapshot.start.download");
/*     */   }
/*     */   
/*     */   private String getConfigurationProperty(String key, String defaultValue) {
/*  64 */     ConfigurationService cs = ConfigurationServiceProvider.getService();
/*  65 */     String ret = cs.getProperty(key);
/*  66 */     if (ret == null) {
/*  67 */       ret = defaultValue;
/*     */     }
/*  69 */     return ret;
/*     */   }
/*     */   public Object onClick(Map submitParams) {
/*     */     URL codebase;
/*  73 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  74 */     String cookie = request.getHeader("Cookie");
/*  75 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  77 */       this.cookiesDisabled = true;
/*     */       
/*  79 */       HtmlElementContainer container = getContainer();
/*  80 */       while (container.getContainer() != null) {
/*  81 */         container = container.getContainer();
/*     */       }
/*  83 */       return container;
/*     */     } 
/*  85 */     this.cookiesDisabled = false;
/*     */ 
/*     */     
/*  88 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  89 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.snapshot.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/*  93 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/*  94 */     } catch (MalformedURLException e) {
/*  95 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/* 101 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 102 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 104 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/* 105 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 107 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 108 */     StringUtilities.replace(jnlp, "{LOCALE}", String.valueOf(this.context.getLocale()));
/* 109 */     StringUtilities.replace(jnlp, "{COUNTRYCODE}", SharedContext.getInstance(this.context).getCountry());
/* 110 */     StringUtilities.replace(jnlp, "{SECTOKEN}", SecurityToken.getInstance(this.context).createToken());
/* 111 */     StringUtilities.replace(jnlp, "{DTCUPLOAD}", getConfigurationProperty("frame.dtc.upload.disabled", "false"));
/*     */     
/* 113 */     String bacCode = retrieveBACCode();
/* 114 */     if (bacCode != null && bacCode.trim().compareTo("") != 0) {
/* 115 */       StringUtilities.replace(jnlp, "{BACCODE}", bacCode);
/*     */     }
/* 117 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/* 118 */     StringUtilities.replace(jnlp, "{NATIVE_LAN}", WindowsLanguageMap.getInstance().get(this.context.getLocale()));
/*     */     
/* 120 */     String mailHost = ApplicationContext.getInstance().getProperty("frame.mail.service.host");
/* 121 */     if (mailHost != null && mailHost.length() != 0) {
/* 122 */       StringUtilities.replace(jnlp, "{MAIL_ENABLED}", "true");
/*     */     } else {
/* 124 */       StringUtilities.replace(jnlp, "{MAIL_ENABLED}", "false");
/*     */     } 
/*     */     
/* 127 */     String installationType = ApplicationContext.getInstance().getProperty("frame.installation.type");
/* 128 */     if (installationType != null && installationType.length() != 0) {
/* 129 */       StringUtilities.replace(jnlp, "{INSTALLATION_TYPE}", installationType);
/*     */     } else {
/* 131 */       StringUtilities.replace(jnlp, "{INSTALLATION_TYPE}", "standalone");
/*     */     } 
/*     */ 
/*     */     
/* 135 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 137 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 141 */     String code = super.getHtmlCode(parameters);
/* 142 */     if (this.cookiesDisabled) {
/* 143 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 144 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("snapshot.notify.cookies.disabled"));
/* 145 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 146 */       code = tmp.toString();
/*     */     } 
/* 148 */     return code;
/*     */   }
/*     */   
/*     */   public String retrieveBACCode() {
/* 152 */     String dcStr = null;
/*     */     try {
/* 154 */       SharedContext sc = this.context.getSharedContext();
/* 155 */       DealerCode testDC = (DealerCode)sc.getObject(DealerCode.class);
/* 156 */       dcStr = testDC.getDealerCode();
/* 157 */       log.debug("Dealercode from Shared Context: " + dcStr);
/* 158 */     } catch (Exception e) {
/* 159 */       log.error("unable to retrieve DealerCode from Shared Context: " + e, e);
/*     */     } 
/* 161 */     return dcStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\server\implementatio\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */