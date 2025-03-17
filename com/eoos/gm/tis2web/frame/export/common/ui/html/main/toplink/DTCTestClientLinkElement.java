/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTCTestClientLinkElement
/*     */   extends LinkElement
/*     */ {
/*     */   private ClientContext context;
/*     */   protected boolean cookiesDisabled = false;
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/tc/dtc/dtc-tc.jnlp", null);
/*  37 */     } catch (Exception e) {
/*  38 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DTCTestClientLinkElement(ClientContext context, HtmlElement page) {
/*  44 */     super(context.createID(), null);
/*  45 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  50 */     return "DTC Upload";
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  54 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  55 */     String jnlp = getJNLPDescriptor(request);
/*  56 */     if (jnlp == null) {
/*  57 */       HtmlElementContainer container = getContainer();
/*  58 */       while (container.getContainer() != null) {
/*  59 */         container = container.getContainer();
/*     */       }
/*  61 */       return container;
/*     */     } 
/*  63 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.getBytes()));
/*     */   }
/*     */   
/*     */   public String getJNLPDescriptor(HttpServletRequest request) {
/*     */     URL codebase;
/*  68 */     String cookie = request.getHeader("Cookie");
/*  69 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  71 */       this.cookiesDisabled = true;
/*     */       
/*  73 */       return null;
/*     */     } 
/*  75 */     this.cookiesDisabled = false;
/*     */     
/*  77 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  78 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.dtc.upload.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/*  82 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/*  83 */     } catch (MalformedURLException e) {
/*  84 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  87 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/*  89 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/*  90 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/*  92 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/*  93 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/*  95 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/*  96 */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*     */     
/*  98 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/*  99 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */ 
/*     */     
/* 102 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 104 */     return jnlp.toString();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 108 */     String code = super.getHtmlCode(parameters);
/* 109 */     if (this.cookiesDisabled) {
/* 110 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 111 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("notify.cookies.disabled"));
/* 112 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 113 */       code = tmp.toString();
/*     */     } 
/* 115 */     return code;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\DTCTestClientLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */