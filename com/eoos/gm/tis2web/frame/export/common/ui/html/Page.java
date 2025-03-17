/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.HttpContext;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlFormElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public abstract class Page
/*     */   extends HtmlFormElement
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(Page.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       template = ApplicationContext.getInstance().loadFile(Page.class, "page.html", null).toString();
/*  37 */     } catch (Exception e) {
/*  38 */       log.error("error loading template - error:" + e, e);
/*  39 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Page(ClientContext context) {
/*  45 */     super((ClientContextBase)context, context.createID());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  50 */     StringBuffer code = new StringBuffer(template);
/*  51 */     StringUtilities.replace(code, "{LANGUAGE}", Util.escapeReservedHTMLChars(this.context.getLocale().getLanguage()));
/*  52 */     StringUtilities.replace(code, "{CONTENT}", super.getHtmlCode(params));
/*     */     
/*  54 */     if (params != null) {
/*  55 */       HttpServletRequest request = (HttpServletRequest)params.get("request");
/*  56 */       if (request != null && (request.getRequestURI() == null || request.getRequestURI().endsWith("tis2web"))) {
/*  57 */         StringUtilities.replace(code, "{BASE}", "<base href=\"" + getServerURL(params) + "\" />");
/*     */       }
/*     */     } 
/*  60 */     StringUtilities.replace(code, "{BASE}", "");
/*  61 */     StringUtilities.replace(code, "{ENCODING}", getEncoding());
/*  62 */     StringUtilities.replace(code, "{STYLESHEET}", Util.escapeReservedHTMLChars(getStyleSheetURL()));
/*  63 */     StringUtilities.replace(code, "{TITLE}", getTitle());
/*     */     
/*  65 */     String onLoadHandler = getOnLoadHandlerCode(params);
/*  66 */     if (onLoadHandler != null) {
/*  67 */       StringUtilities.replace(code, "{ONLOAD}", "onLoad=\"" + onLoadHandler + "\"");
/*     */     } else {
/*  69 */       StringUtilities.replace(code, "{ONLOAD}", "");
/*     */     } 
/*     */     
/*  72 */     String additionalScript = getAdditionalScript(params);
/*  73 */     if (additionalScript == null) {
/*  74 */       additionalScript = "";
/*     */     }
/*  76 */     StringUtilities.replace(code, "{ADDITIONAL_SCRIPT}", additionalScript);
/*     */     
/*  78 */     String bodyAttributes = getBodyStyleAttribute(params);
/*  79 */     if (bodyAttributes != null) {
/*  80 */       StringUtilities.replace(code, "{BODY_ATTRIBUTES}", "style=\"" + bodyAttributes + "\"");
/*     */     } else {
/*  82 */       StringUtilities.replace(code, "{BODY_ATTRIBUTES}", "");
/*     */     } 
/*     */     
/*  85 */     return code.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object onUnhandledSubmit(Map params) {
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   protected String getStyleSheetURL() {
/*  96 */     return "res/common/style.css";
/*     */   }
/*     */   
/*     */   protected String getTitle() {
/* 100 */     return this.context.getLabel("application.title");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 105 */     return "utf-8";
/*     */   }
/*     */   
/*     */   protected String getOnLoadHandlerCode(Map params) {
/* 109 */     return "javascript:window.focus()";
/*     */   }
/*     */   
/*     */   protected String getBodyStyleAttribute(Map params) {
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   protected String getAdditionalScript(Map params) {
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   protected ClientContext getContext() {
/* 121 */     return (ClientContext)this.context;
/*     */   }
/*     */   
/*     */   protected Object getErrorPopup(String message) {
/* 125 */     return getErrorPopup(message, (Object)null);
/*     */   }
/*     */   
/*     */   protected Object getErrorPopup(Throwable t) {
/* 129 */     return getErrorPopup(t, (Object)null);
/*     */   }
/*     */   
/*     */   protected Object getErrorPopup(Throwable t, Object onOKReturnValue) {
/* 133 */     return getErrorPopup(t.getMessage(), onOKReturnValue);
/*     */   }
/*     */   
/*     */   public Object getErrorPopup(String message, final Object onOKReturnValue) {
/* 137 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 138 */     return new ErrorMessageBox(getContext(), null, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 141 */           return (onOKReturnValue == null) ? topLevel : onOKReturnValue;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getWarningPopup(String message) {
/* 148 */     return getWarningPopup(message, (Object)null);
/*     */   } public static interface ConfirmationCallback {
/*     */     Object onConfirm(); Object onCancel(); }
/*     */   public Object getWarningPopup(String message, final Object onOKReturnValue) {
/* 152 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 153 */     return new WarningMessageBox(getContext(), null, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 156 */           return (onOKReturnValue == null) ? topLevel : onOKReturnValue;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getInfoPopup(String message) {
/* 163 */     return getInfoPopup(message, (Object)null);
/*     */   }
/*     */   
/*     */   public Object getInfoPopup(String message, Object onOKReturnValue) {
/* 167 */     HtmlElementContainer htmlElementContainer = getTopLevelContainer();
/* 168 */     return NotificationMessageBox.createInfoMessage(getContext(), this.context.getLabel("information"), message, (onOKReturnValue == null) ? htmlElementContainer : onOKReturnValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getConfirmationPopup(String message, final ConfirmationCallback callback) {
/* 178 */     getTopLevelContainer();
/* 179 */     return new SimpleConfirmationMessageBox(getContext(), null, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 182 */           return callback.onConfirm();
/*     */         }
/*     */         
/*     */         protected Object onCancel(Map params) {
/* 186 */           return callback.onCancel();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private String getServerURL(Map params) {
/*     */     try {
/* 194 */       URL tmp = new URL(ConfigurationServiceProvider.getService().getProperty("tis2web.server.url"));
/* 195 */       String scheme = HttpContext.getInstance(getContext()).getRequestScheme();
/* 196 */       if (!Util.isNullOrEmpty(scheme)) {
/* 197 */         tmp = new URL(scheme, tmp.getHost(), tmp.getPort(), tmp.getPath());
/*     */       }
/*     */ 
/*     */       
/* 201 */       String ret = tmp.toString();
/* 202 */       return ret.endsWith("/") ? ret : (ret + "/");
/* 203 */     } catch (MalformedURLException e) {
/* 204 */       throw new RuntimeException("unable to determine base URL", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 210 */     return new ResultObject(0, getHtmlCode(params));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURL() {
/* 215 */     return this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*     */   }
/*     */   
/*     */   protected abstract String getFormContent(Map paramMap);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\Page.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */