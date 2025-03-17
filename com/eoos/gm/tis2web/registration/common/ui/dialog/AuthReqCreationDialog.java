/*     */ package com.eoos.gm.tis2web.registration.common.ui.dialog;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.DealershipInfoInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.LicenseKeyInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.SoftwareKeyInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.SubscriberIDInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
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
/*     */ public abstract class AuthReqCreationDialog
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  58 */   private static final Logger log = Logger.getLogger(AuthReqCreationDialog.class); private static final String TEMPLATE; private ClientContext context; private Callback callback; private DealershipInfoInputElement ieDealershipInfo; private SoftwareKeyInputElement ieSoftwareKey; private LicenseKeyInputElement ieLicenseKey; private SubscriberIDInputElement ieSubscriberID; private TextInputElement ieSessionCount; private ClickButtonElement buttonOnlineReg; private ClickButtonElement buttonPrint; private ClickButtonElement buttonStoreDealership;
/*     */   private ClickButtonElement buttonRegLicenseKey;
/*     */   
/*     */   static {
/*     */     try {
/*  63 */       TEMPLATE = ApplicationContext.getInstance().loadFile(AuthReqCreationDialog.class, "authorizationrequestdialog.html", null).toString();
/*  64 */     } catch (Exception e) {
/*  65 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  66 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
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
/*     */   public AuthReqCreationDialog(final ClientContext context, Callback callback) {
/*  94 */     this.context = context;
/*     */     
/*  96 */     this.callback = callback;
/*     */     
/*  98 */     this.ieDealershipInfo = new DealershipInfoInputElement(context);
/*  99 */     addElement((HtmlElement)this.ieDealershipInfo);
/* 100 */     this.ieDealershipInfo.setValue(callback.getDealershipInfo());
/*     */     
/* 102 */     this.ieSoftwareKey = new SoftwareKeyInputElement(context);
/* 103 */     addElement((HtmlElement)this.ieSoftwareKey);
/* 104 */     this.ieSoftwareKey.setValue(callback.getSoftwareKey());
/* 105 */     this.ieSoftwareKey.setDisabled(Boolean.TRUE);
/*     */     
/* 107 */     this.ieLicenseKey = new LicenseKeyInputElement(context);
/* 108 */     addElement((HtmlElement)this.ieLicenseKey);
/* 109 */     this.ieLicenseKey.setValue(callback.getLicenseKey());
/*     */     
/* 111 */     this.ieSubscriberID = new SubscriberIDInputElement(context);
/* 112 */     addElement((HtmlElement)this.ieSubscriberID);
/* 113 */     this.ieSubscriberID.setValue(callback.getSubscriberID());
/*     */     
/* 115 */     this.ieSessionCount = new TextInputElement(context.createID(), -1, 10);
/*     */     try {
/* 117 */       Integer sessions = callback.getLicensedSessionCount();
/* 118 */       if (sessions != null) {
/* 119 */         this.ieSessionCount.setValue(callback.getLicensedSessionCount().toString());
/*     */       }
/* 121 */     } catch (Exception x) {}
/*     */     
/* 123 */     addElement((HtmlElement)this.ieSessionCount);
/*     */     
/* 125 */     this.buttonOnlineReg = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 128 */           return context.getLabel("auth.req.register.online");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 133 */             return AuthReqCreationDialog.this.registerOnline(AuthReqCreationDialog.this.getOutputData());
/* 134 */           } catch (Exception e) {
/* 135 */             AuthReqCreationDialog.log.error("unable to perform action 'register online'  - exception: " + e, e);
/* 136 */             return AuthReqCreationDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 141 */     addElement((HtmlElement)this.buttonOnlineReg);
/*     */     
/* 143 */     this.buttonPrint = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 146 */           return context.getLabel("auth.req.print.for.manual");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 151 */             return AuthReqCreationDialog.this.printRequest(AuthReqCreationDialog.this.getOutputData());
/* 152 */           } catch (Exception e) {
/* 153 */             AuthReqCreationDialog.log.error("unable to perform action 'print request'  - exception: " + e, e);
/* 154 */             return AuthReqCreationDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 159 */     addElement((HtmlElement)this.buttonPrint);
/*     */     
/* 161 */     this.buttonStoreDealership = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 164 */           return context.getLabel("auth.req.store.dealership.data");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 169 */             return AuthReqCreationDialog.this.storeDealershipData(AuthReqCreationDialog.this.getOutputData());
/* 170 */           } catch (Exception e) {
/* 171 */             AuthReqCreationDialog.log.error("unable to perform action 'store dealership request'  - exception: " + e, e);
/* 172 */             return AuthReqCreationDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 177 */     addElement((HtmlElement)this.buttonStoreDealership);
/* 178 */     if (callback.getDealershipInfo() == null) {
/* 179 */       this.buttonStoreDealership.setDisabled(Boolean.TRUE);
/*     */     }
/*     */     
/* 182 */     this.buttonRegLicenseKey = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 184 */           return context.getLabel("auth.req.register.license.key");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 189 */             return AuthReqCreationDialog.this.registerLicenseKey(AuthReqCreationDialog.this.getOutputData());
/* 190 */           } catch (Exception e) {
/* 191 */             AuthReqCreationDialog.log.error("unable to perform action 'register license key'  - exception: " + e, e);
/* 192 */             return AuthReqCreationDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 197 */     addElement((HtmlElement)this.buttonRegLicenseKey);
/*     */   } public static interface OutputData {
/*     */     DealershipInfo getDealershipInfo(); SoftwareKey getSoftwareKey(); LicenseKey getLicenseKey(); String getSubscriberID(); Subscription getSubscription(); Integer getLicensedSessionCount(); }
/*     */   private Object getErrorPopup(Exception e) {
/* 201 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 202 */     return new ErrorMessageBox(this.context, null, this.context.getMessage("exception.unable.to.execute.action"))
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 205 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getErrorPopup(String message) {
/* 212 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 213 */     return new ErrorMessageBox(this.context, null, this.context.getMessage(message))
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 216 */           return topLevel;
/*     */         }
/*     */       };
/*     */   } public static interface Callback {
/*     */     DealershipInfo getDealershipInfo(); SoftwareKey getSoftwareKey(); LicenseKey getLicenseKey(); String getSubscriberID(); Subscription getSubscription(); Integer getLicensedSessionCount();
/*     */     String getRequestID(); }
/*     */   protected Object getMessagePopup(String caption, final String messageID, String parameter, String value) {
/* 223 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 224 */     return new NotificationMessageBox(this.context, this.context.getLabel(caption), evaluate(messageID, parameter, value))
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 227 */           if (messageID.equals("auth.req.dialog.license.registration.suceeded")) {
/* 228 */             return topLevel;
/*     */           }
/* 230 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private String evaluate(String message, String parameter, String value) {
/* 237 */     if (parameter != null) {
/* 238 */       return StringUtilities.replace(this.context.getMessage(message), parameter, value);
/*     */     }
/* 240 */     return this.context.getMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getContentAlign() {
/* 245 */     return "left";
/*     */   }
/*     */   
/*     */   protected String getID() {
/* 249 */     return "authreqcreationdialog";
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 253 */     StringBuffer ret = new StringBuffer("<div class=\"main\">{TITLE}</div><div class=\"sub\">{MESSAGE}</div>");
/* 254 */     StringUtilities.replace(ret, "{TITLE}", this.context.getLabel("auth.req.dialog.title"));
/* 255 */     StringUtilities.replace(ret, "{MESSAGE}", this.context.getMessage("auth.req.dialog.title.message"));
/* 256 */     return ret.toString();
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/* 260 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 261 */     StringUtilities.replace(ret, "{STEP1}", this.context.getMessage("registration.step1"));
/* 262 */     StringUtilities.replace(ret, "{STEP2}", this.context.getMessage("registration.step2"));
/* 263 */     StringUtilities.replace(ret, "{STEP3}", this.context.getMessage("registration.step3"));
/* 264 */     StringUtilities.replace(ret, "{STEP4}", this.context.getMessage("registration.step4"));
/* 265 */     StringUtilities.replace(ret, "{STEP5}", this.context.getMessage("registration.step5"));
/*     */     
/* 267 */     String requestID = this.callback.getRequestID();
/* 268 */     if (requestID == null) {
/* 269 */       StringUtilities.replace(ret, "{REQUESTID}", "");
/*     */     } else {
/* 271 */       StringUtilities.replace(ret, "{REQUESTID}", "<div id=\"reqid\">" + this.context.getLabel("request.id") + ": " + requestID + "</div>");
/*     */     } 
/*     */     
/* 274 */     StringUtilities.replace(ret, "{DEALERSHIPINFO}", this.ieDealershipInfo.getHtmlCode(params));
/*     */     
/* 276 */     StringUtilities.replace(ret, "{LICENSEKEY}", this.ieLicenseKey.getHtmlCode(params));
/* 277 */     StringUtilities.replace(ret, "{SUBSCRIBERID}", this.ieSubscriberID.getHtmlCode(params));
/* 278 */     StringUtilities.replace(ret, "{SUBSCRIPTION}", "<div id=\"subscription\"><table><tr><th>" + this.context.getLabel("auth.req.tis2web.subscription") + ":</th><td>" + this.callback.getSubscription().getDenotation(this.context.getLocale()) + "</td></tr></table></div>");
/* 279 */     StringUtilities.replace(ret, "{LABEL_SESSIONCOUNT}", this.context.getLabel("licensed.session.count"));
/* 280 */     StringUtilities.replace(ret, "{INPUT_SESSIONCOUNT}", this.ieSessionCount.getHtmlCode(params));
/*     */     
/* 282 */     StringUtilities.replace(ret, "{BUTTON_ONLINE_REG}", this.buttonOnlineReg.getHtmlCode(params));
/* 283 */     StringUtilities.replace(ret, "{BUTTON_PRINT}", this.buttonPrint.getHtmlCode(params));
/* 284 */     StringUtilities.replace(ret, "{BUTTON_STORE_DEALERSHIP}", this.buttonStoreDealership.getHtmlCode(params));
/* 285 */     StringUtilities.replace(ret, "{BUTTON_REG_SW_KEY}", this.buttonRegLicenseKey.getHtmlCode(params));
/*     */     
/* 287 */     return ret.toString();
/*     */   }
/*     */   
/*     */   private OutputData getOutputData() {
/* 291 */     return new OutputData()
/*     */       {
/*     */         public String getSubscriberID() {
/* 294 */           return (String)AuthReqCreationDialog.this.ieSubscriberID.getValue();
/*     */         }
/*     */         
/*     */         public LicenseKey getLicenseKey() {
/* 298 */           return (LicenseKey)AuthReqCreationDialog.this.ieLicenseKey.getValue();
/*     */         }
/*     */         
/*     */         public DealershipInfo getDealershipInfo() {
/* 302 */           return (DealershipInfo)AuthReqCreationDialog.this.ieDealershipInfo.getValue();
/*     */         }
/*     */         
/*     */         public SoftwareKey getSoftwareKey() {
/* 306 */           return (SoftwareKey)AuthReqCreationDialog.this.ieSoftwareKey.getValue();
/*     */         }
/*     */         
/*     */         public Subscription getSubscription() {
/* 310 */           return AuthReqCreationDialog.this.callback.getSubscription();
/*     */         }
/*     */         
/*     */         public Integer getLicensedSessionCount() {
/*     */           try {
/* 315 */             return Integer.valueOf((String)AuthReqCreationDialog.this.ieSessionCount.getValue());
/* 316 */           } catch (Exception e) {
/* 317 */             throw new IllegalArgumentException();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected abstract Object registerOnline(OutputData paramOutputData) throws Exception;
/*     */   
/*     */   protected abstract Object printRequest(OutputData paramOutputData) throws Exception;
/*     */   
/*     */   protected abstract Object storeDealershipData(OutputData paramOutputData) throws Exception;
/*     */   
/*     */   protected abstract Object registerLicenseKey(OutputData paramOutputData) throws Exception;
/*     */   
/*     */   protected abstract Object onClose();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\dialog\AuthReqCreationDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */