/*     */ package com.eoos.gm.tis2web.sas.server.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/sas/client/sas.jnlp", null);
/*  46 */     } catch (Exception e) {
/*  47 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  56 */     super(context.createID(), null);
/*  57 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  61 */     return this.context.getLabel("sas.start.download");
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
/*  81 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.sas.url.download.server"), request);
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
/*     */     
/* 102 */     if (ApplicationContext.getInstance().isStandalone()) {
/* 103 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "local");
/*     */     } else {
/* 105 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "central");
/*     */     } 
/*     */ 
/*     */     
/* 109 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 111 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 115 */     String code = super.getHtmlCode(parameters);
/* 116 */     if (this.cookiesDisabled) {
/* 117 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 118 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("swdl.notify.cookies.disabled"));
/* 119 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 120 */       code = tmp.toString();
/*     */     } 
/* 122 */     return code;
/*     */   }
/*     */   
/*     */   public String retrieveBACCode() {
/* 126 */     String dcStr = null;
/*     */     try {
/* 128 */       SharedContext sc = this.context.getSharedContext();
/* 129 */       DealerCode testDC = (DealerCode)sc.getObject(DealerCode.class);
/* 130 */       dcStr = testDC.getDealerCode();
/* 131 */       log.debug("Dealercode from Shared Context: " + dcStr);
/* 132 */     } catch (Exception e) {
/* 133 */       log.error("unable to retrieve DealerCode from Shared Context: " + e, e);
/*     */     } 
/* 135 */     return dcStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementatio\\ui\html\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */