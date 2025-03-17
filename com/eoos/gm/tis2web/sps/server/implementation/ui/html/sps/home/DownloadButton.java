/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.sps.home;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
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
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDatabaseResource;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.ResourceCatalog;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Brand;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableFilter;
/*     */ import com.eoos.gm.tis2web.sps.common.navtables.NavTableValidationMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.brand.BrandProvider;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.NavigationTableFilterProvider;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*     */ import com.eoos.gm.tis2web.sps.service.SPSServiceProvider;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DownloadButton
/*     */   extends ClickButtonElement
/*     */ {
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*  58 */   private static final Logger log = Logger.getLogger(DownloadButton.class);
/*     */   private static String jnlpTemplate;
/*     */   protected ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/sps/download/sps.jnlp", null);
/*  65 */     } catch (Exception e) {
/*  66 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cookiesDisabled = false;
/*     */ 
/*     */   
/*     */   public DownloadButton(ClientContext context) {
/*  75 */     super(context.createID(), null);
/*  76 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  80 */     return this.context.getLabel("sps.start.download");
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  84 */     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*  85 */     String jnlp = getJNLPDescriptor(request);
/*  86 */     if (jnlp == null) {
/*  87 */       HtmlElementContainer container = getContainer();
/*  88 */       while (container.getContainer() != null) {
/*  89 */         container = container.getContainer();
/*     */       }
/*  91 */       return container;
/*     */     } 
/*  93 */     return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", jnlp.getBytes()));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getConfigurationProperty(String key, String defaultValue) {
/*  98 */     ConfigurationService cs = ConfigurationServiceProvider.getService();
/*  99 */     String ret = cs.getProperty(key);
/* 100 */     if (ret == null) {
/* 101 */       ret = defaultValue;
/*     */     }
/* 103 */     return ret;
/*     */   }
/*     */   public String getJNLPDescriptor(HttpServletRequest request) {
/*     */     URL codebase;
/* 107 */     String cookie = request.getHeader("Cookie");
/* 108 */     if (cookie == null || cookie.length() == 0) {
/*     */       
/* 110 */       this.cookiesDisabled = true;
/*     */     } else {
/* 112 */       this.cookiesDisabled = false;
/*     */     } 
/*     */     
/* 115 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/* 116 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.sps.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/* 120 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/* 121 */     } catch (MalformedURLException e) {
/* 122 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.sps.url.task.execution"), request);
/*     */ 
/*     */     
/* 129 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 130 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 132 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/* 133 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 135 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 136 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/* 137 */     StringUtilities.replace(jnlp, "{COUNTRYCODE}", SharedContext.getInstance(this.context).getCountry());
/* 138 */     StringUtilities.replace(jnlp, "{SECTOKEN}", SecurityToken.getInstance(this.context).createToken());
/* 139 */     StringUtilities.replace(jnlp, "{SERVERNAME}", ApplicationContext.getInstance().getHostName());
/* 140 */     StringUtilities.replace(jnlp, "{CLIENTVERSION}", SPSServiceProvider.getSPSService().getClientVersion());
/* 141 */     StringUtilities.replace(jnlp, "{DTCUPLOAD}", getConfigurationProperty("frame.dtc.upload.disabled", "false"));
/*     */     
/* 143 */     String appServerDwnld = ApplicationContext.getInstance().getProperty("frame.dwnld.include.appserver");
/* 144 */     if (Util.isNullOrEmpty(appServerDwnld)) {
/* 145 */       appServerDwnld = "true";
/*     */     }
/* 147 */     StringUtilities.replace(jnlp, "{APPSERVER_DOWNLOAD}", appServerDwnld);
/*     */     
/* 149 */     String bacCode = retrieveBACCode();
/* 150 */     if (bacCode != null && bacCode.trim().compareTo("") != 0) {
/* 151 */       StringUtilities.replace(jnlp, "{BACCODE}", bacCode);
/*     */     }
/* 153 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */     
/* 155 */     Collection validBrands = BrandProvider.getInstance(this.context).getBrands();
/*     */     
/* 157 */     if (validBrands.size() == 0) {
/* 158 */       log.warn("client does not have access to any brand (check ACL)");
/* 159 */       StringUtilities.replace(jnlp, "{BRANDS}", "");
/*     */     } else {
/* 161 */       StringBuffer brandsString = new StringBuffer("{BRAND}");
/* 162 */       for (Iterator<Brand> iter = validBrands.iterator(); iter.hasNext(); ) {
/* 163 */         Brand brand = iter.next();
/* 164 */         StringUtilities.replace(brandsString, "{BRAND}", brand.getIdentifier() + (iter.hasNext() ? ", {BRAND}" : ""));
/*     */       } 
/* 166 */       StringUtilities.replace(jnlp, "{BRANDS}", brandsString.toString());
/*     */     } 
/*     */     
/*     */     try {
/* 170 */       Object[] resources = ResourceCatalog.getInstance().getResources();
/* 171 */       handleDatabaseProvidedResources(jnlp, resources, "jar", "{DATABASE-PROVIDED-JARS}");
/* 172 */       handleDatabaseProvidedResources(jnlp, resources, "nativelib", "{DATABASE-PROVIDED-NATIVELIBS}");
/* 173 */     } catch (Exception e) {
/* 174 */       StringUtilities.replace(jnlp, "{DATABASE-PROVIDED-JARS}", "");
/* 175 */       StringUtilities.replace(jnlp, "{DATABASE-PROVIDED-NATIVELIBS}", "");
/*     */     } 
/*     */     
/* 178 */     if (ApplicationContext.getInstance().isStandalone()) {
/* 179 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "local");
/*     */     } else {
/* 181 */       StringUtilities.replace(jnlp, "{SERVER-INSTALLATION}", "central");
/*     */     } 
/*     */     
/*     */     try {
/* 185 */       NavTableValidationMap validationMap = SPSServer.getInstance(this.context.getSessionID()).createNavigationTableValidationMap();
/* 186 */       if (validationMap != null) {
/* 187 */         StringUtilities.replace(jnlp, "{NAVTABLEVALIDMAP}", validationMap.toExternalFrom());
/*     */       } else {
/* 189 */         StringUtilities.replace(jnlp, "{NAVTABLEVALIDMAP}", "");
/*     */       } 
/* 191 */     } catch (Exception e) {
/* 192 */       log.error("unable to create navigation table validation map, ignoring - exception:" + e, e);
/*     */     } 
/*     */     
/* 195 */     NavigationTableFilter navTableFilter = NavigationTableFilterProvider.getFilter(this.context);
/* 196 */     StringUtilities.replace(jnlp, "{NAVTABLEFILTER}", navTableFilter.toExternal());
/*     */     
/* 198 */     StringUtilities.replace(jnlp, "{WARRANTY-CODE-LIST}", getWarrantyCodeList(this.context.getSessionID()));
/*     */     
/* 200 */     StringUtilities.replace(jnlp, "{VCI-1001-SUPPORT}", checkVCI1001Supported(this.context.getSessionID()));
/*     */     
/* 202 */     ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/* 203 */     StringUtilities.replace(jnlp, "{FAILURE_MAIL_RECIPIENTS}", configurationService.getProperty("component.sps.failure.mail.recipients"));
/* 204 */     StringUtilities.replace(jnlp, "{FAILURE_MAIL_FILTERS}", configurationService.getProperty("component.sps.failure.mail.file.name.filters"));
/*     */ 
/*     */     
/* 207 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/*     */     
/* 209 */     return jnlp.toString();
/*     */   }
/*     */   
/*     */   protected String checkVCI1001Supported(String sessionID) {
/* 213 */     boolean granted = false;
/*     */     try {
/* 215 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 216 */       Map usrGroup2Manuf = this.context.getSharedContext().getUsrGroup2ManufMap();
/* 217 */       Set<String> tmp = new HashSet();
/* 218 */       tmp.add("sps-vci-1001");
/* 219 */       granted = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, this.context.getSharedContext().getCountry()).size() != 0);
/*     */     }
/* 221 */     catch (Exception e) {
/* 222 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*     */     } 
/* 224 */     if (granted) {
/* 225 */       return "enabled";
/*     */     }
/* 227 */     return "disabled";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getWarrantyCodeList(String sessionID) {
/* 232 */     boolean granted = false;
/*     */     try {
/* 234 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 235 */       Map usrGroup2Manuf = this.context.getSharedContext().getUsrGroup2ManufMap();
/* 236 */       Set<String> tmp = new HashSet();
/* 237 */       tmp.add("warranty-code-list");
/* 238 */       granted = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, this.context.getSharedContext().getCountry()).size() != 0);
/*     */     }
/* 240 */     catch (Exception e) {
/* 241 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*     */     } 
/* 243 */     if (granted) {
/* 244 */       return ApplicationContext.getInstance().getProperty("component.sps.warranty.code.list.size");
/*     */     }
/* 246 */     return "0";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void injectNativeLibResourceTemplate(StringBuffer jnlp) {
/* 251 */     int start = jnlp.indexOf("<nativelib href=\"sps-nativelibs.jar");
/* 252 */     if (start >= 0) {
/* 253 */       jnlp.delete(start, jnlp.indexOf(">", start) + 1);
/* 254 */       jnlp.insert(start, "{DATABASE-PROVIDED-NATIVELIBS}");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleDatabaseProvidedResources(StringBuffer jnlp, Object[] resources, String type, String template) {
/* 259 */     StringBuffer result = new StringBuffer();
/* 260 */     if (resources != null) {
/* 261 */       for (int i = 0; i < resources.length; i++) {
/* 262 */         JnlpDatabaseResource resource = (JnlpDatabaseResource)resources[i];
/* 263 */         if (type.equals("nativelib")) {
/* 264 */           if (!resource.isNativeLibResource()) {
/* 265 */             resource = null;
/* 266 */           } else if ("sps-nativelibs.jar".equals(resource.getName())) {
/* 267 */             injectNativeLibResourceTemplate(jnlp);
/*     */           } 
/* 269 */         } else if (resource.isNativeLibResource()) {
/* 270 */           resource = null;
/*     */         } 
/* 272 */         if (resource != null) {
/* 273 */           result.append("<" + type + " href=\"" + resource.getName() + "\" version=\"" + resource.getVersion() + "\"/>");
/*     */         }
/*     */       } 
/*     */     }
/* 277 */     StringUtilities.replace(jnlp, template, result.toString());
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map parameters) {
/* 281 */     String code = super.getHtmlCode(parameters);
/* 282 */     if (this.cookiesDisabled) {
/* 283 */       StringBuffer tmp = new StringBuffer("<table><tr><td align=\"center\"><span style=\"color:red\">{MESSAGE}</span></td></tr><tr><td align=\"center\">{BUTTON}</td></tr></table>");
/* 284 */       StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("swdl.notify.cookies.disabled"));
/* 285 */       StringUtilities.replace(tmp, "{BUTTON}", code);
/* 286 */       code = tmp.toString();
/*     */     } 
/* 288 */     return code;
/*     */   }
/*     */   
/*     */   public String retrieveBACCode() {
/* 292 */     String dcStr = null;
/*     */     try {
/* 294 */       SharedContext sc = this.context.getSharedContext();
/* 295 */       DealerCode testDC = (DealerCode)sc.getObject(DealerCode.class);
/* 296 */       dcStr = testDC.getDealerCode();
/* 297 */       log.debug("Dealercode from Shared Context: " + dcStr);
/* 298 */     } catch (Exception e) {
/* 299 */       log.error("unable to retrieve DealerCode from Shared Context: " + e, e);
/*     */     } 
/* 301 */     return dcStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\sps\home\DownloadButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */