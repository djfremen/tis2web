/*     */ package com.eoos.gm.tis2web.registration.common.ui.dialog;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.AuthReqDetailPanel;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.server.db.DealershipEntity;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipInformation;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class AuthReqDetailDialog extends DialogBase {
/*  32 */   private static final Logger log = Logger.getLogger(AuthReqDetailDialog.class); private static final String TEMPLATE;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  37 */       TEMPLATE = ApplicationContext.getInstance().loadFile(AuthReqDetailDialog.class, "authreqdetaildialog.html", null).toString();
/*  38 */     } catch (Exception e) {
/*  39 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  40 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthorizationRequest request;
/*     */ 
/*     */   
/*     */   private AuthReqDetailPanel detailPanel;
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonCompute;
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonAuthorize;
/*     */   
/*     */   private ClickButtonElement buttonPrint;
/*     */   
/*     */   private ClickButtonElement buttonStore;
/*     */   
/*     */   private ClickButtonElement buttonCancel;
/*     */ 
/*     */   
/*     */   public AuthReqDetailDialog(final ClientContext context, AuthorizationRequest request) {
/*  65 */     super(context);
/*  66 */     this.context = context;
/*  67 */     this.request = request;
/*     */     
/*  69 */     this.detailPanel = new AuthReqDetailPanel(context, request);
/*  70 */     addElement((HtmlElement)this.detailPanel);
/*     */     
/*  72 */     this.buttonCompute = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  75 */           return context.getLabel("compute.license.key");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  80 */             return AuthReqDetailDialog.this.onCompute(AuthReqDetailDialog.this.convert(AuthReqDetailDialog.this.detailPanel.getOutputData()));
/*  81 */           } catch (Exception e) {
/*  82 */             AuthReqDetailDialog.log.error("unable to execute 'compute' action - exception:" + e, e);
/*  83 */             return AuthReqDetailDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  88 */     addElement((HtmlElement)this.buttonCompute);
/*     */     
/*  90 */     this.buttonStore = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  93 */           return context.getLabel("update.dealership");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  98 */             return AuthReqDetailDialog.this.onStore(AuthReqDetailDialog.this.convert(AuthReqDetailDialog.this.detailPanel.getOutputData()));
/*  99 */           } catch (Exception e) {
/* 100 */             AuthReqDetailDialog.log.error("unable to execute 'store' action - exception:" + e, e);
/* 101 */             return AuthReqDetailDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 106 */     addElement((HtmlElement)this.buttonStore);
/* 107 */     if (request.getType() == RequestType.TEMPORARY) {
/* 108 */       this.buttonStore.setDisabled(Boolean.TRUE);
/*     */     }
/*     */     
/* 111 */     this.buttonAuthorize = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 114 */           return context.getLabel("authorize.subscription");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 119 */             return AuthReqDetailDialog.this.onAuthorize(AuthReqDetailDialog.this.convert(AuthReqDetailDialog.this.detailPanel.getOutputData()));
/* 120 */           } catch (Exception e) {
/* 121 */             AuthReqDetailDialog.log.error("unable to execute 'authorize' action - exception:" + e, e);
/* 122 */             return AuthReqDetailDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 127 */     addElement((HtmlElement)this.buttonAuthorize);
/* 128 */     this.buttonAuthorize.setDisabled(Boolean.TRUE);
/*     */     
/* 130 */     this.buttonPrint = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 133 */           return context.getLabel("print.fax.notification");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 138 */             return AuthReqDetailDialog.this.onPrint(AuthReqDetailDialog.this.convert(AuthReqDetailDialog.this.detailPanel.getOutputData()));
/* 139 */           } catch (Exception e) {
/* 140 */             AuthReqDetailDialog.log.error("unable to execute 'print' action - exception: " + e, e);
/* 141 */             return AuthReqDetailDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 146 */     addElement((HtmlElement)this.buttonPrint);
/* 147 */     this.buttonPrint.setDisabled(Boolean.TRUE);
/*     */     
/* 149 */     this.buttonCancel = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 152 */           return context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 157 */             return AuthReqDetailDialog.this.onCancel();
/* 158 */           } catch (Exception e) {
/* 159 */             AuthReqDetailDialog.log.error("unable to execute 'cancel' action - exception: " + e, e);
/* 160 */             return AuthReqDetailDialog.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 165 */     addElement((HtmlElement)this.buttonCancel);
/*     */   }
/*     */   
/*     */   private Object getErrorPopup(Exception e) {
/* 169 */     return getErrorPopup(this.context.getMessage("exception.unable.to.execute.action"));
/*     */   }
/*     */   public static interface OutputData extends AuthReqDetailPanel.OutputData {}
/*     */   private Object getErrorPopup(String message, String parameter, String value) {
/* 173 */     if (parameter != null) {
/* 174 */       message = StringUtilities.replace(this.context.getMessage(message), parameter, value);
/*     */     } else {
/* 176 */       message = this.context.getMessage(message);
/*     */     } 
/* 178 */     return getErrorPopup(message);
/*     */   }
/*     */   
/*     */   protected Object getErrorPopup(String message) {
/* 182 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 183 */     return new ErrorMessageBox(this.context, null, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 186 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getMessagePopup(String caption, String message, String parameter, String value, final Object returnUI) {
/* 193 */     if (parameter != null) {
/* 194 */       message = StringUtilities.replace(this.context.getMessage(message), parameter, value);
/*     */     } else {
/* 196 */       message = this.context.getMessage(message);
/*     */     } 
/* 198 */     return new NotificationMessageBox(this.context, this.context.getLabel(caption), message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 201 */           return returnUI;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private OutputData convert(final AuthReqDetailPanel.OutputData backend) {
/* 208 */     return new OutputData()
/*     */       {
/*     */         public Set getSubscriptions() {
/* 211 */           return backend.getSubscriptions();
/*     */         }
/*     */         
/*     */         public String getSubscriberID() {
/* 215 */           String data = backend.getSubscriberID();
/* 216 */           return (data != null) ? data.trim() : null;
/*     */         }
/*     */         
/*     */         public SoftwareKey getSoftwareKey() {
/* 220 */           return backend.getSoftwareKey();
/*     */         }
/*     */         
/*     */         public int getSessionCount() {
/* 224 */           return backend.getSessionCount();
/*     */         }
/*     */         
/*     */         public String getRequestID() {
/* 228 */           String data = backend.getRequestID();
/* 229 */           return (data != null) ? data.trim() : null;
/*     */         }
/*     */         
/*     */         public long getRequestDate() {
/* 233 */           return backend.getRequestDate();
/*     */         }
/*     */         
/*     */         public LicenseKey getLicenseKey() {
/* 237 */           return backend.getLicenseKey();
/*     */         }
/*     */         
/*     */         public DealershipInfo getDealershipInfo() {
/* 241 */           return backend.getDealershipInfo();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getContentAlign() {
/* 248 */     return "left";
/*     */   }
/*     */   
/*     */   protected String getID() {
/* 252 */     return "authreqdetaildialog";
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 256 */     StringBuffer ret = new StringBuffer("<div class=\"main\">{TITLE}</div><div class=\"sub\">{MESSAGE}</div>");
/* 257 */     StringUtilities.replace(ret, "{TITLE}", this.context.getLabel("auth.req.detail"));
/* 258 */     StringUtilities.replace(ret, "{MESSAGE}", this.context.getMessage("auth.req.detail.dialog.title.msg"));
/* 259 */     return ret.toString();
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/* 263 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 264 */     StringUtilities.replace(ret, "{DETAILPANEL}", this.detailPanel.getHtmlCode(params));
/* 265 */     StringUtilities.replace(ret, "{BUTTON_COMPUTE}", this.buttonCompute.getHtmlCode(params));
/* 266 */     StringUtilities.replace(ret, "{BUTTON_STORE}", this.buttonStore.getHtmlCode(params));
/* 267 */     StringUtilities.replace(ret, "{BUTTON_AUTHORIZE}", this.buttonAuthorize.getHtmlCode(params));
/* 268 */     StringUtilities.replace(ret, "{BUTTON_PRINT}", this.buttonPrint.getHtmlCode(params));
/* 269 */     StringUtilities.replace(ret, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/* 270 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object onCompute(OutputData data) {
/*     */     try {
/* 276 */       this.detailPanel.computeLicenseKey();
/* 277 */     } catch (Exception e) {
/* 278 */       return getErrorPopup("auth.req.detail.dialog.license.key.not.computed", (String)null, (String)null);
/*     */     } 
/* 280 */     this.buttonAuthorize.setDisabled(Boolean.FALSE);
/* 281 */     return getTopLevelContainer();
/*     */   }
/*     */   
/*     */   protected Object onStore(OutputData data) {
/*     */     try {
/* 286 */       DealershipInformation dealership = new DealershipInformation(this.context, data.getDealershipInfo());
/* 287 */       String check = dealership.check();
/* 288 */       if (check != null) {
/* 289 */         return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */       }
/* 291 */       Registry registry = RegistrationProvider.getInstance().getService();
/* 292 */       registry.updateDealershipInformation((Dealership)new DealershipEntity((Dealership)dealership));
/* 293 */     } catch (Exception e) {
/* 294 */       return getErrorPopup("auth.req.dialog.dealership.data.failure", (String)null, (String)null);
/*     */     } 
/* 296 */     return getMessagePopup("auth.req.detail.dialog.caption", "auth.req.dialog.dealership.data.stored", (String)null, (String)null, getTopLevelContainer());
/*     */   }
/*     */   
/*     */   protected Object onAuthorize(OutputData data) throws Exception {
/* 300 */     if (data.getSubscriptions() == null || data.getSubscriptions().size() != 1) {
/* 301 */       return getErrorPopup("auth.req.detail.dialog.subscription.not.selected", (String)null, (String)null);
/*     */     }
/* 303 */     if (data.getRequestID() == null || data.getRequestID().length() > 10) {
/* 304 */       return getErrorPopup("auth.req.detail.dialog.request.ident.invalid", (String)null, (String)null);
/*     */     }
/* 306 */     if (data.getSubscriberID() == null || data.getSubscriberID().length() > 10) {
/* 307 */       return getErrorPopup("auth.req.detail.dialog.subscriber.ident.invalid", (String)null, (String)null);
/*     */     }
/* 309 */     DealershipInformation dealership = new DealershipInformation(this.context, data.getDealershipInfo());
/* 310 */     String check = dealership.check();
/* 311 */     if (check != null) {
/* 312 */       return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */     }
/* 314 */     Registration registration = this.request.getRegistration();
/* 315 */     if ((registration.getDealership() == null || registration.getRequestType() == RequestType.HWKLOCAL) && 
/* 316 */       data.getRequestID() != null) {
/* 317 */       registration.setRequestID(data.getRequestID());
/*     */     }
/*     */     
/* 320 */     registration.setDealership((Dealership)new DealershipEntity((Dealership)dealership));
/* 321 */     registration.setSubscriberID(data.getSubscriberID());
/* 322 */     Subscription subscription = data.getSubscriptions().iterator().next();
/* 323 */     registration.setAuthorizationID(subscription.getSubscriptionID());
/* 324 */     Integer users = null;
/* 325 */     if (data.getSessionCount() > 0) {
/*     */       try {
/* 327 */         users = Integer.valueOf(data.getSessionCount());
/* 328 */       } catch (Exception e) {
/* 329 */         return getErrorPopup("auth.req.dialog.online.registration.invalid.session.count");
/*     */       } 
/*     */     }
/* 332 */     Registry registry = RegistrationProvider.getInstance().getService();
/*     */     try {
/* 334 */       registry.computeLicenceKey(registration, null, users);
/* 335 */       registration.setSessionCount(users);
/* 336 */       registry.authorizeRegistrationRequest(registration);
/* 337 */     } catch (RegistrationException e) {
/* 338 */       return getErrorPopup("auth.req.detail.dialog.authorization.failed");
/* 339 */     } catch (Exception e) {
/* 340 */       return getErrorPopup(e);
/*     */     } 
/* 342 */     this.buttonPrint.setDisabled(Boolean.FALSE);
/* 343 */     if (dealership.getDealershipEmail() != null) {
/* 344 */       return getMessagePopup("auth.req.detail.dialog.caption", "auth.req.detail.dialog.authorization.completed", (String)null, (String)null, getTopLevelContainer());
/*     */     }
/* 346 */     return getMessagePopup("auth.req.detail.dialog.caption", "auth.req.detail.dialog.authorization.suceeded", (String)null, (String)null, getTopLevelContainer());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object onPrint(OutputData data) throws Exception {
/* 351 */     Registration registration = this.request.getRegistration();
/* 352 */     Integer sessions = (data.getSessionCount() != -1) ? Integer.valueOf(data.getSessionCount()) : null;
/* 353 */     Registry registry = RegistrationProvider.getInstance().getService();
/* 354 */     byte[] pdf = registry.generateFaxNotification(registration, sessions);
/* 355 */     ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 356 */     props.data = pdf;
/* 357 */     props.filename = "registration.pdf";
/* 358 */     props.mime = "application/pdf";
/* 359 */     props.inline = false;
/* 360 */     return new ResultObject(13, true, false, props);
/*     */   }
/*     */   
/*     */   protected Object onCancel() throws Exception {
/* 364 */     return onClose();
/*     */   }
/*     */   
/*     */   protected abstract Object onClose();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\dialog\AuthReqDetailDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */