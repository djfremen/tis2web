/*     */ package com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.home;
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
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  40 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("tech2view/client/tech2view.jnlp", null);
/*  41 */     } catch (Exception e) {
/*  42 */       log.error(e);
/*  43 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  52 */     super(context.createID(), null);
/*  53 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  57 */     return this.context.getLabel("tech2view.start.download");
/*     */   }
/*     */   public Object onClick(Map submitParams) {
/*     */     URL codebase;
/*  61 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  62 */     String cookie = request.getHeader("Cookie");
/*  63 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  65 */       this.cookiesDisabled = true;
/*     */       
/*  67 */       HtmlElementContainer container = getContainer();
/*  68 */       while (container.getContainer() != null) {
/*  69 */         container = container.getContainer();
/*     */       }
/*  71 */       return container;
/*     */     } 
/*  73 */     this.cookiesDisabled = false;
/*     */ 
/*     */     
/*  76 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  77 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.tech2view.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/*  81 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/*  82 */     } catch (MalformedURLException e) {
/*  83 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  86 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/*  88 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/*  89 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/*  91 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/*  92 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/*  94 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/*  95 */     StringUtilities.replace(jnlp, "{LOCALE}", String.valueOf(this.context.getLocale()));
/*  96 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*  97 */     StringUtilities.replace(jnlp, "{NATIVE_LAN}", WindowsLanguageMap.getInstance().get(this.context.getLocale()));
/*     */ 
/*     */     
/* 100 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 102 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 106 */     String code = super.getHtmlCode(parameters);
/* 107 */     if (this.cookiesDisabled) {
/* 108 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 109 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("tech2view.notify.cookies.disabled"));
/* 110 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 111 */       code = tmp.toString();
/*     */     } 
/* 113 */     return code;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\server\implementatio\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */