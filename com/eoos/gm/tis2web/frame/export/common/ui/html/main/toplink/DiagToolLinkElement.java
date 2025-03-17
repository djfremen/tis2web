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
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ 
/*     */ public class DiagToolLinkElement
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*     */   protected boolean cookiesDisabled = false;
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static String jnlpTemplate;
/*     */   
/*     */   static {
/*     */     try {
/*  39 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/diag/client/diag.jnlp", null);
/*  40 */     } catch (Exception e) {
/*  41 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DiagToolLinkElement(final ClientContext context, HtmlElement page) {
/*  47 */     super(context.createID(), null);
/*  48 */     this.context = context;
/*  49 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  51 */           return "pic/common/diagclient.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  55 */           return context.getLabel("tooltip.toplink.diagtool");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  59 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  67 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  71 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  72 */     String jnlp = getJNLPDescriptor(request);
/*  73 */     if (jnlp == null) {
/*  74 */       HtmlElementContainer container = getContainer();
/*  75 */       while (container.getContainer() != null) {
/*  76 */         container = container.getContainer();
/*     */       }
/*  78 */       return container;
/*     */     } 
/*  80 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.getBytes()));
/*     */   }
/*     */   
/*     */   public String getJNLPDescriptor(HttpServletRequest request) {
/*     */     URL codebase;
/*  85 */     String cookie = request.getHeader("Cookie");
/*  86 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/*  88 */       this.cookiesDisabled = true;
/*     */       
/*  90 */       return null;
/*     */     } 
/*  92 */     this.cookiesDisabled = false;
/*     */     
/*  94 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*  95 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.diag.tool.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/*  99 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/* 100 */     } catch (MalformedURLException e) {
/* 101 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 104 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/* 106 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 107 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 109 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/* 110 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 112 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 113 */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*     */     
/* 115 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/* 116 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */ 
/*     */     
/* 119 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 121 */     return jnlp.toString();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 125 */     String code = super.getHtmlCode(parameters);
/* 126 */     if (this.cookiesDisabled) {
/* 127 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 128 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("notify.cookies.disabled"));
/* 129 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 130 */       code = tmp.toString();
/*     */     } 
/* 132 */     return code;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\DiagToolLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */