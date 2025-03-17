/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.SecurityToken;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WindowsLanguageMap;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.QualifierData;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ICOPClientLink
/*     */   extends LinkElement
/*     */ {
/*  48 */   private static final Logger log = Logger.getLogger(ICOPClientLink.class); private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*     */   private static String jnlpTemplate;
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private List optionsMake;
/*     */   private Map<Locale, String> optionsLanguage;
/*     */   
/*  55 */   public ICOPClientLink(final ClientContext context) { super(context.createID(), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     this.optionsMake = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     this.optionsLanguage = null; this.context = context; this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() { String image = "lt/icop.gif"; if (ICOPClientLink.this.isDisabled()) image = "lt/icop-disabled.gif";  return "pic/" + image; }
/*     */         public String getAlternativeText() { return context.getLabel("lt.tooltip.icop"); } public void getAdditionalAttributes(Map<String, String> map) { map.put("border", "0"); }
/* 190 */       }; } private synchronized Map<Locale, String> getLanguageOptions() { if (this.optionsLanguage == null) {
/* 191 */       log.debug("determining options for language selection...");
/* 192 */       Set<String> supported = LTDataAdapterFacade.getInstance().getLT().getSupportedWinLangs();
/* 193 */       this.optionsLanguage = WindowsLanguageMap.getMap();
/* 194 */       for (Iterator<Map.Entry<Locale, String>> iter = this.optionsLanguage.entrySet().iterator(); iter.hasNext(); ) {
/* 195 */         Map.Entry<Locale, String> entry = iter.next();
/* 196 */         if (!supported.contains(entry.getValue()) || "default".equalsIgnoreCase(((Locale)entry.getKey()).toString())) {
/* 197 */           log.debug("...removing unsupported language '" + (String)entry.getValue() + "'");
/* 198 */           iter.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return this.optionsLanguage; }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*     */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/*     */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/lt/icop/cfgclient.jnlp", null);
/*     */     } catch (Exception e) {
/*     */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*     */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*     */     String jnlp = getJNLPDescriptor(request);
/*     */     if (jnlp == null) {
/*     */       HtmlElementContainer container = getContainer();
/*     */       while (container.getContainer() != null)
/*     */         container = container.getContainer(); 
/*     */       return container;
/*     */     } 
/*     */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.getBytes()));
/*     */   }
/*     */   
/*     */   public String getJNLPDescriptor(HttpServletRequest request) {
/*     */     URL codebase;
/*     */     String cookie = request.getHeader("Cookie");
/*     */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/*     */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.lt.url.download.server"), request);
/*     */     try {
/*     */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/*     */     } catch (MalformedURLException e) {
/*     */       throw new RuntimeException(e);
/*     */     } 
/*     */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/*     */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/*     */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/*     */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*     */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/*     */     StringUtilities.replace(jnlp, "{SECTOKEN}", SecurityToken.getInstance(this.context).createToken());
/*     */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */     String icopClientVersion = ApplicationContext.getInstance().getProperty("component.lt.icop.client.version");
/*     */     if (Util.isNullOrEmpty(icopClientVersion)) {
/*     */       StringUtilities.replace(jnlp, "{ICOPCLIENTVERSION}", "");
/*     */     } else {
/*     */       StringUtilities.replace(jnlp, "{ICOPCLIENTVERSION}", "<property name=\"target.version\" value=\"" + icopClientVersion + "\" />");
/*     */     } 
/*     */     String appServerDwnld = ApplicationContext.getInstance().getProperty("frame.dwnld.include.appserver");
/*     */     if (Util.isNullOrEmpty(appServerDwnld))
/*     */       appServerDwnld = "true"; 
/*     */     StringUtilities.replace(jnlp, "{APPSERVER_DOWNLOAD}", appServerDwnld);
/*     */     try {
/*     */       StringUtilities.replace(jnlp, "{SERVERS}", Util.toExternal(getServerOptions()));
/*     */       StringUtilities.replace(jnlp, "{MAKES}", Util.toExternal(getMakeOptions()));
/*     */       StringUtilities.replace(jnlp, "{COUNTRIES}", Util.toExternal(getCountryOptions()));
/*     */       StringUtilities.replace(jnlp, "{LANGUAGES}", Util.toExternal(getLanguageOptions()));
/*     */     } catch (IOException e) {
/*     */       throw new RuntimeException(e);
/*     */     } 
/*     */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     String timeout = ApplicationContext.getInstance().getProperty("component.lt.icop.client.communication.timeout");
/*     */     StringUtilities.replace(jnlp, "{TIMEOUT}", (timeout != null) ? timeout : "");
/*     */     return jnlp.toString();
/*     */   }
/*     */   
/*     */   private List<String> getServerOptions() {
/*     */     return ICOPClientParams.getInstance().getServerList();
/*     */   }
/*     */   
/*     */   private synchronized List<String> getMakeOptions() {
/*     */     if (this.optionsMake == null) {
/*     */       log.debug("determining options for default make selection...");
/*     */       this.optionsMake = ConfigurationUtil.getValueList((Configuration)ConfigurationServiceProvider.getService(), "component.lt.salesmake");
/*     */       Collections.sort(this.optionsMake, Util.COMPARATOR_TOSTRING);
/*     */       log.debug("...done");
/*     */     } 
/*     */     return this.optionsMake;
/*     */   }
/*     */   
/*     */   private synchronized Map<Locale, String> getCountryOptions() {
/*     */     return QualifierData.getInstance().getLocaleCountryMap();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\ICOPClientLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */