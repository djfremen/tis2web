/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.standalone;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.task.AuthenticationRI;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationSTE;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationTask;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.DealershipInfoInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.LicenseKeyInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.SoftwareKeyInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.SubscriberIDInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.SubscriptionsInputElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.dialog.AuthReqCreationDialog;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.AuthorizationImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipInformation;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.LicenseFileException;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RegistrationPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  88 */   private static final Logger log = Logger.getLogger(RegistrationPanel.class); private static final String TEMPLATE; private ClientContext context; private Callback callback; private DealershipInfoInputElement ieDealershipInfo; private SoftwareKeyInputElement ieSoftwareKey; private LicenseKeyInputElement ieLicenseKey; private SubscriptionsInputElement ieSubscriptions; private SubscriberIDInputElement ieSubscriberID; private TextInputElement ieSessionCount; private ClickButtonElement buttonOnlineReg; private ClickButtonElement buttonPrint; private ClickButtonElement buttonStoreDealership;
/*     */   private ClickButtonElement buttonRegLicenseKey;
/*     */   
/*     */   static {
/*     */     try {
/*  93 */       TEMPLATE = ApplicationContext.getInstance().loadFile(AuthReqCreationDialog.class, "authorizationrequestdialog.html", null).toString();
/*  94 */     } catch (Exception e) {
/*  95 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  96 */       throw new RuntimeException(e);
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
/*     */ 
/*     */   
/*     */   public RegistrationPanel(final ClientContext context, final Callback callback) {
/* 126 */     this.context = context;
/*     */     
/* 128 */     this.callback = callback;
/*     */     
/* 130 */     this.ieDealershipInfo = new DealershipInfoInputElement(context);
/* 131 */     addElement((HtmlElement)this.ieDealershipInfo);
/* 132 */     this.ieDealershipInfo.setValue(callback.getDealershipInfo());
/*     */     
/* 134 */     this.ieSoftwareKey = new SoftwareKeyInputElement(context);
/* 135 */     addElement((HtmlElement)this.ieSoftwareKey);
/* 136 */     this.ieSoftwareKey.setValue(callback.getSoftwareKey());
/* 137 */     this.ieSoftwareKey.setDisabled(Boolean.TRUE);
/*     */     
/* 139 */     this.ieLicenseKey = new LicenseKeyInputElement(context);
/* 140 */     addElement((HtmlElement)this.ieLicenseKey);
/* 141 */     this.ieLicenseKey.setValue(callback.getLicenseKey());
/*     */     
/* 143 */     this.ieSubscriberID = new SubscriberIDInputElement(context);
/* 144 */     addElement((HtmlElement)this.ieSubscriberID);
/* 145 */     this.ieSubscriberID.setValue(callback.getSubscriberID());
/* 146 */     this.ieSubscriberID.setDisabled(Boolean.TRUE);
/*     */     
/* 148 */     this.ieSubscriptions = new SubscriptionsInputElement(context);
/* 149 */     addElement((HtmlElement)this.ieSubscriptions);
/* 150 */     this.ieSubscriptions.setValue(callback.getSubscription());
/* 151 */     this.ieSubscriptions.setUnremovableSubscription(callback.getSubscription());
/*     */     
/* 153 */     this.ieSessionCount = new TextInputElement(context.createID());
/*     */     try {
/* 155 */       Integer sessions = callback.getLicensedSessionCount();
/* 156 */       if (sessions != null) {
/* 157 */         this.ieSessionCount.setValue(callback.getLicensedSessionCount().toString());
/*     */       }
/* 159 */     } catch (Exception x) {}
/*     */     
/* 161 */     addElement((HtmlElement)this.ieSessionCount);
/*     */     
/* 163 */     this.buttonOnlineReg = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 166 */           if (callback.getRegistrationType() == 1) {
/* 167 */             return context.getLabel("auth.reqext.register.online");
/*     */           }
/* 169 */           return context.getLabel("auth.reqhwk.register.online");
/*     */         }
/*     */ 
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 175 */             if (callback.getRegistrationType() == 1) {
/* 176 */               return RegistrationPanel.this.extendSubscriptionOnline(RegistrationPanel.this.getOutputData());
/*     */             }
/* 178 */             return RegistrationPanel.this.authorizeHardwareMigrationOnline(RegistrationPanel.this.getOutputData());
/*     */           }
/* 180 */           catch (Exception e) {
/* 181 */             RegistrationPanel.log.error("unable to perform action 'online registration'  - exception: " + e, e);
/* 182 */             return RegistrationPanel.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 187 */     addElement((HtmlElement)this.buttonOnlineReg);
/*     */     
/* 189 */     this.buttonPrint = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 192 */           return context.getLabel("auth.req.print.for.manual");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 197 */             return RegistrationPanel.this.printRequest(RegistrationPanel.this.getOutputData());
/* 198 */           } catch (Exception e) {
/* 199 */             RegistrationPanel.log.error("unable to perform action 'print request'  - exception: " + e, e);
/* 200 */             return RegistrationPanel.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 205 */     addElement((HtmlElement)this.buttonPrint);
/*     */     
/* 207 */     this.buttonStoreDealership = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 210 */           return context.getLabel("auth.req.store.dealership.data");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 215 */             return RegistrationPanel.this.storeDealershipData(RegistrationPanel.this.getOutputData());
/* 216 */           } catch (Exception e) {
/* 217 */             RegistrationPanel.log.error("unable to perform action 'store dealership request'  - exception: " + e, e);
/* 218 */             return RegistrationPanel.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 223 */     addElement((HtmlElement)this.buttonStoreDealership);
/*     */     
/* 225 */     this.buttonRegLicenseKey = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 227 */           return context.getLabel("auth.req.register.license.key");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 232 */             return RegistrationPanel.this.registerLicenseKey(RegistrationPanel.this.getOutputData());
/* 233 */           } catch (Exception e) {
/* 234 */             RegistrationPanel.log.error("unable to perform action 'register license key'  - exception: " + e, e);
/* 235 */             return RegistrationPanel.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 240 */     addElement((HtmlElement)this.buttonRegLicenseKey);
/*     */   } public static interface OutputData {
/*     */     DealershipInfo getDealershipInfo(); SoftwareKey getSoftwareKey(); LicenseKey getLicenseKey(); String getSubscriberID(); Subscription getSubscription(); Set getAdditionalSubscriptions();
/*     */     Integer getLicensedSessionCount(); }
/*     */   protected String getContentAlign() {
/* 245 */     return "left";
/*     */   } public static interface Callback {
/*     */     public static final int HARDWARE_KEY_MIGRATION = 0; public static final int EXTEND_SUBSCRIPTION = 1; int getRegistrationType(); DealershipInfo getDealershipInfo(); SoftwareKey getSoftwareKey(); LicenseKey getLicenseKey(); String getSubscriberID(); Subscription getSubscription(); Integer getLicensedSessionCount(); String getRequestID(); }
/*     */   protected String getID() {
/* 249 */     return "authreqcreationdialog";
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 253 */     StringBuffer ret = new StringBuffer("<div class=\"sub\">{MESSAGE}</div>");
/* 254 */     StringUtilities.replace(ret, "{TITLE}", this.context.getLabel("auth.req.dialog.title"));
/* 255 */     if (this.callback.getRegistrationType() == 1) {
/* 256 */       StringUtilities.replace(ret, "{MESSAGE}", this.context.getMessage("auth.reqext.dialog.title.message"));
/*     */     } else {
/* 258 */       StringUtilities.replace(ret, "{MESSAGE}", this.context.getMessage("auth.reqhwk.dialog.title.message"));
/*     */     } 
/* 260 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 264 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/*     */     
/* 266 */     StringUtilities.replace(ret, "{STEP1}", this.context.getMessage("registration.step1"));
/* 267 */     StringUtilities.replace(ret, "{STEP2}", this.context.getMessage("registration.step2"));
/* 268 */     StringUtilities.replace(ret, "{STEP3}", this.context.getMessage("registration.step3"));
/* 269 */     StringUtilities.replace(ret, "{STEP4}", this.context.getMessage("registration.step4"));
/* 270 */     StringUtilities.replace(ret, "{STEP5}", this.context.getMessage("registration.step5"));
/*     */     
/* 272 */     String requestID = this.callback.getRequestID();
/* 273 */     if (requestID == null) {
/* 274 */       StringUtilities.replace(ret, "{REQUESTID}", "");
/*     */     } else {
/* 276 */       StringUtilities.replace(ret, "{REQUESTID}", "<div id=\"reqid\">" + this.context.getLabel("request.id") + ": " + requestID + "</div>");
/*     */     } 
/* 278 */     ret.insert(0, "<div class=\"title\">" + getTitle(params) + "</div>");
/* 279 */     StringUtilities.replace(ret, "{DEALERSHIPINFO}", this.ieDealershipInfo.getHtmlCode(params));
/*     */     
/* 281 */     StringUtilities.replace(ret, "{LICENSEKEY}", this.ieLicenseKey.getHtmlCode(params));
/* 282 */     StringUtilities.replace(ret, "{SUBSCRIBERID}", this.ieSubscriberID.getHtmlCode(params));
/* 283 */     StringUtilities.replace(ret, "{SUBSCRIPTION}", this.ieSubscriptions.getHtmlCode(params));
/* 284 */     StringUtilities.replace(ret, "{LABEL_SESSIONCOUNT}", this.context.getLabel("licensed.session.count"));
/* 285 */     StringUtilities.replace(ret, "{INPUT_SESSIONCOUNT}", this.ieSessionCount.getHtmlCode(params));
/*     */     
/* 287 */     StringUtilities.replace(ret, "{BUTTON_ONLINE_REG}", this.buttonOnlineReg.getHtmlCode(params));
/* 288 */     StringUtilities.replace(ret, "{BUTTON_PRINT}", this.buttonPrint.getHtmlCode(params));
/* 289 */     StringUtilities.replace(ret, "{BUTTON_STORE_DEALERSHIP}", this.buttonStoreDealership.getHtmlCode(params));
/* 290 */     StringUtilities.replace(ret, "{BUTTON_REG_SW_KEY}", this.buttonRegLicenseKey.getHtmlCode(params));
/*     */     
/* 292 */     return ret.toString();
/*     */   }
/*     */   
/*     */   private Object getErrorPopup(Exception e) {
/* 296 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 297 */     return new ErrorMessageBox(this.context, null, this.context.getMessage("exception.unable.to.execute.action"))
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 300 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getErrorPopup(String message) {
/* 307 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 308 */     return new ErrorMessageBox(this.context, null, this.context.getMessage(message))
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 311 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getMessagePopup(String caption, String message, String parameter, String value) {
/* 318 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 319 */     if (parameter != null) {
/* 320 */       message = StringUtilities.replace(this.context.getMessage(message), parameter, value);
/*     */     } else {
/* 322 */       message = this.context.getMessage(message);
/*     */     } 
/* 324 */     return new NotificationMessageBox(this.context, this.context.getLabel(caption), message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 327 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private OutputData getOutputData() {
/* 334 */     return new OutputData()
/*     */       {
/*     */         public String getSubscriberID() {
/* 337 */           return (String)RegistrationPanel.this.ieSubscriberID.getValue();
/*     */         }
/*     */         
/*     */         public LicenseKey getLicenseKey() {
/* 341 */           return (LicenseKey)RegistrationPanel.this.ieLicenseKey.getValue();
/*     */         }
/*     */         
/*     */         public DealershipInfo getDealershipInfo() {
/* 345 */           return (DealershipInfo)RegistrationPanel.this.ieDealershipInfo.getValue();
/*     */         }
/*     */         
/*     */         public SoftwareKey getSoftwareKey() {
/* 349 */           return (SoftwareKey)RegistrationPanel.this.ieSoftwareKey.getValue();
/*     */         }
/*     */         
/*     */         public Subscription getSubscription() {
/* 353 */           return RegistrationPanel.this.callback.getSubscription();
/*     */         }
/*     */         
/*     */         public Set getAdditionalSubscriptions() {
/* 357 */           Set subscriptions = new HashSet((Collection)RegistrationPanel.this.ieSubscriptions.getValue());
/* 358 */           subscriptions.remove(RegistrationPanel.this.callback.getSubscription());
/* 359 */           return subscriptions;
/*     */         }
/*     */         
/*     */         public Integer getLicensedSessionCount() {
/*     */           try {
/* 364 */             return Integer.valueOf((String)RegistrationPanel.this.ieSessionCount.getValue());
/* 365 */           } catch (Exception e) {
/* 366 */             throw new IllegalArgumentException();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected Object extendSubscriptionOnline(OutputData data) throws Exception {
/* 373 */     RegistrationTask _task = null;
/* 374 */     final SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 375 */     Integer sessions = null;
/*     */     try {
/* 377 */       sessions = data.getLicensedSessionCount();
/* 378 */     } catch (Exception e) {
/* 379 */       return getErrorPopup("auth.req.dialog.online.registration.invalid.session.count");
/*     */     } 
/* 381 */     final Set subscriptions = data.getAdditionalSubscriptions();
/* 382 */     if (service.isRepeatRegistration(subscriptions)) {
/* 383 */       _task = new RegistrationTask(true, service.getSubscriberID(), service.getSoftwareKey(), sessions);
/*     */     } else {
/* 385 */       String subscriptionID = data.getSubscription().getSubscriptionID();
/* 386 */       List authorizations = transferSubscriptions(subscriptions, subscriptionID);
/* 387 */       _task = new RegistrationTask(service.getSubscriberID(), authorizations, sessions);
/*     */     } 
/* 389 */     final RegistrationTask task = _task;
/*     */     try {
/* 391 */       return evaluateESResult(RegistrationSTE.getInstance().execute((Task)registrationTask1), service, subscriptions);
/* 392 */     } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/* 393 */       final HtmlElementContainer topLevel = getTopLevelContainer();
/* 394 */       return new AuthenticationDialog(this.context, new AuthenticationDialog.Callback()
/*     */           {
/*     */             public Object onOK(String user, String pwd) throws RegistrationSTE.MissingAuthenticationException {
/*     */               try {
/* 398 */                 return RegistrationPanel.this.evaluateESResult(RegistrationSTE.getInstance().execute(task, (RegistrationSTE.Authentication)new AuthenticationRI(user, pwd)), service, subscriptions);
/* 399 */               } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/* 400 */                 throw e;
/* 401 */               } catch (Exception e) {
/* 402 */                 return RegistrationPanel.this.evaluateESResult((Object)null, (SoftwareKeyService)null, (Set)null);
/*     */               } 
/*     */             }
/*     */             
/*     */             public Object onCancel() {
/* 407 */               return topLevel;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object evaluateESResult(Object retValue, SoftwareKeyService service, Set subscriptions) {
/* 416 */     if (retValue == null)
/* 417 */       return getErrorPopup("auth.req.dialog.online.registration.failed"); 
/* 418 */     if (retValue instanceof RegistrationException) {
/* 419 */       if (((RegistrationException)retValue).getMessage().equals("registration.db.load.failed")) {
/* 420 */         return getErrorPopup("auth.reqhwk.dialog.online.registration.unknown");
/*     */       }
/* 422 */       return getErrorPopup("auth.req.dialog.online.registration.duplicate");
/*     */     } 
/*     */     
/* 425 */     if (!service.isRepeatRegistration(subscriptions)) {
/* 426 */       service.extendAuthorization(subscriptions);
/*     */     }
/* 428 */     return getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.online.registration.suceeded", "{REQUEST-ID}", (String)retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List transferSubscriptions(Set subscriptions, String subscriptionID) {
/* 433 */     List<String> authorizations = new ArrayList();
/* 434 */     Iterator<Subscription> it = subscriptions.iterator();
/* 435 */     while (it.hasNext()) {
/* 436 */       Subscription subscription = it.next();
/* 437 */       authorizations.add(subscription.getSubscriptionID());
/*     */     } 
/* 439 */     if (authorizations.isEmpty()) {
/* 440 */       authorizations.add(subscriptionID);
/*     */     }
/* 442 */     return authorizations;
/*     */   }
/*     */   
/*     */   protected Object authorizeHardwareMigrationOnline(OutputData data) throws Exception {
/* 446 */     SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 447 */     Integer sessions = null;
/*     */     try {
/* 449 */       sessions = data.getLicensedSessionCount();
/* 450 */       if (sessions == null || sessions.intValue() == 0) {
/* 451 */         sessions = Integer.valueOf(service.getMaxSessionCount());
/*     */       } else {
/* 453 */         sessions = Integer.valueOf(Math.max(service.getMaxSessionCount(), sessions.intValue()));
/*     */       } 
/* 455 */     } catch (Exception e) {
/* 456 */       return getErrorPopup("auth.req.dialog.online.registration.invalid.session.count");
/*     */     } 
/* 458 */     final RegistrationTask task = new RegistrationTask(false, service.getSubscriberID(), service.getSoftwareKey(), sessions);
/*     */     try {
/* 460 */       return evaluateHMResult(RegistrationSTE.getInstance().execute((Task)task));
/* 461 */     } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/* 462 */       final HtmlElementContainer topLevel = getTopLevelContainer();
/* 463 */       return new AuthenticationDialog(this.context, new AuthenticationDialog.Callback()
/*     */           {
/*     */             public Object onOK(String user, String pwd) throws RegistrationSTE.MissingAuthenticationException {
/*     */               try {
/* 467 */                 return RegistrationPanel.this.evaluateHMResult(RegistrationSTE.getInstance().execute((Task)task, (RegistrationSTE.Authentication)new AuthenticationRI(user, pwd)));
/* 468 */               } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/* 469 */                 throw e;
/* 470 */               } catch (Exception e) {
/* 471 */                 return RegistrationPanel.this.evaluateHMResult((Object)null);
/*     */               } 
/*     */             }
/*     */             
/*     */             public Object onCancel() {
/* 476 */               return topLevel;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object evaluateHMResult(Object result) {
/* 485 */     if (result == null)
/* 486 */       return getErrorPopup("auth.req.dialog.online.registration.failed"); 
/* 487 */     if (result instanceof RegistrationException) {
/* 488 */       if (((RegistrationException)result).getMessage().equals("registration.db.load.failed")) {
/* 489 */         return getErrorPopup("auth.reqhwk.dialog.online.registration.unknown");
/*     */       }
/* 491 */       return getErrorPopup("auth.req.dialog.online.registration.duplicate");
/*     */     } 
/*     */     
/* 494 */     return getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.online.registration.suceeded", "{REQUEST-ID}", (String)result);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List mapSubscriptions(Set subscriptions, String subscriptionID) {
/* 499 */     List<Authorization> authorizations = new ArrayList();
/* 500 */     authorizations.add(AuthorizationImpl.lookup(subscriptionID));
/* 501 */     Iterator<Subscription> it = subscriptions.iterator();
/* 502 */     while (it.hasNext()) {
/* 503 */       Subscription subscription = it.next();
/* 504 */       authorizations.add(AuthorizationImpl.lookup(subscription.getSubscriptionID()));
/*     */     } 
/* 506 */     return authorizations;
/*     */   }
/*     */   
/*     */   protected Object printRequest(OutputData data) throws Exception {
/* 510 */     Integer sessions = null;
/*     */     try {
/* 512 */       sessions = data.getLicensedSessionCount();
/* 513 */     } catch (Exception e) {
/* 514 */       return getErrorPopup("auth.req.dialog.online.registration.invalid.session.count");
/*     */     } 
/* 516 */     Set subscriptions = data.getAdditionalSubscriptions();
/* 517 */     String subscriptionID = data.getSubscription().getSubscriptionID();
/* 518 */     Collection authorizations = mapSubscriptions(subscriptions, subscriptionID);
/* 519 */     SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 520 */     byte[] pdf = service.createManualRegistrationForm(authorizations, sessions, null);
/* 521 */     ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 522 */     props.data = pdf;
/* 523 */     props.filename = "registration.pdf";
/* 524 */     props.mime = "application/pdf";
/* 525 */     props.inline = false;
/* 526 */     return new ResultObject(13, true, false, props);
/*     */   }
/*     */   
/*     */   protected Object storeDealershipData(OutputData data) throws Exception {
/* 530 */     SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 531 */     DealershipInformation dealership = new DealershipInformation(this.context, data.getDealershipInfo());
/* 532 */     String check = dealership.check();
/* 533 */     if (check != null) {
/* 534 */       return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */     }
/*     */     try {
/* 537 */       service.setDealershipInformation((Dealership)dealership);
/* 538 */       return getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.dealership.data.stored", (String)null, (String)null);
/* 539 */     } catch (Exception e) {
/* 540 */       return getErrorPopup("auth.req.dialog.dealership.data.failure");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object registerLicenseKey(final OutputData data) throws Exception {
/* 545 */     return new NotificationMessageBox(this.context, this.context.getLabel("auth.req.dialog.caption"), this.context.getMessage("auth.req.dialog.registration.may.take.some.time"))
/*     */       {
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 549 */             SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 550 */             service.updateLicense(data.getSubscriberID(), data.getLicenseKey().toString());
/* 551 */             return RegistrationPanel.this.getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.license.registration.suceeded", (String)null, (String)null);
/* 552 */           } catch (LicenseFileException el) {
/* 553 */             return RegistrationPanel.this.getErrorPopup("frame.registration.license.exception");
/* 554 */           } catch (InvalidLicenseException ei) {
/* 555 */             return RegistrationPanel.this.getErrorPopup("frame.registration.license.invalid");
/* 556 */           } catch (Exception e) {
/* 557 */             return RegistrationPanel.this.getErrorPopup("auth.req.dialog.license.registration.failed");
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected abstract Object onClose();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\standalone\RegistrationPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */