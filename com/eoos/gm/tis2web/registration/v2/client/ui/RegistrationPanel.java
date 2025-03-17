/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.LogoutLinkElement;
/*     */ import com.eoos.gm.tis2web.registration.common.task.AuthenticationRI;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationSTE;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.admin.standalone.AuthenticationDialog;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SubscriptionProvider;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
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
/*     */ public class RegistrationPanel
/*     */   extends ContextualElementContainerBase
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger(RegistrationPanel.class); private static final String TEMPLATE; private Callback callback; private ClickButtonElement buttonEditDealershipInfo; private LicensedUserCountInput ieUserCount; private SubscriptionsInputElement ieSubscriptions; private ClickButtonElement buttonRegisterOnline; private ClickButtonElement buttonRegisterManual; private ClickButtonElement buttonLogout; private RequestIDDisplayElement displayRequestID; private SubscriberIDInputElement ieSubscriberID; private LicenseKeyInputElement ieLicenseKey; private ClickButtonElement buttonRegisterLicense; private RegistrationSTE.Authentication authentication; private final Object SYNC_AUTHDIALOG;
/*     */   private AuthenticationDialog authDialog;
/*     */   
/*     */   static {
/*     */     try {
/*  73 */       TEMPLATE = ApplicationContext.getInstance().loadFile(RegistrationPanel.class, "registrationpanel.html", null).toString();
/*  74 */     } catch (Exception e) {
/*  75 */       throw new RuntimeException(e);
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
/*     */   public RegistrationPanel(final ClientContext context, final Callback callback) {
/* 102 */     super(context);
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
/* 330 */     this.authentication = null;
/*     */     
/* 332 */     this.SYNC_AUTHDIALOG = new Object();
/*     */     
/* 334 */     this.authDialog = null; this.callback = callback; this.buttonEditDealershipInfo = new ClickButtonElement(context.createID(), null) { protected String getLabel() { return context.getLabel("registration.edit.dealership.data"); } public Object onClick(Map submitParams) { return RegistrationPanel.this.editDealershipInfo(); } }; addElement((HtmlElement)this.buttonEditDealershipInfo); this.ieUserCount = new LicensedUserCountInput(context); addElement((HtmlElement)this.ieUserCount); this.ieUserCount.setValue(callback.getLicensedSessionCount()); final boolean firstReg = (callback.getSubscription() == null); final SubscriptionAdapter defaultSubscription = new SubscriptionAdapter((Subscription)SoftwareKeyProvider.getInstance().getService().getDefaultSubscription()); this.ieSubscriptions = new SubscriptionsInputElement(context, firstReg, new SubscriptionsInputElement.Callback() { public Collection getExistingSubscriptions() { Subscription s = RegistrationPanel.this.callback.getSubscription(); return (s == null) ? Collections.EMPTY_SET : Collections.<Subscription>singleton(s); } public List getSubscriptionDomain() { return firstReg ? Collections.<Subscription>singletonList(defaultSubscription) : SubscriptionProvider.getInstance().getSubscriptions(context); } }); addElement((HtmlElement)this.ieSubscriptions); if (firstReg) this.ieSubscriptions.setValue(subscriptionAdapter);  this.buttonRegisterOnline = new ClickButtonElement(context.createID(), null) { protected String getLabel() { boolean firstReg = (callback.getSubscription() == null); return context.getLabel(firstReg ? "online.registration" : "repeat.extend.online.registration"); } public Object onClick(Map submitParams) { return RegistrationPanel.this.registerOnline(); } public boolean isDisabled() { return (callback.getDealershipInfo() == null || callback.getRequestID() != null); } }; addElement((HtmlElement)this.buttonRegisterOnline); this.buttonRegisterManual = new ClickButtonElement(context.createID(), null) { protected String getLabel() { boolean firstReg = (callback.getSubscription() == null); return context.getLabel(firstReg ? "manual.registration" : "repeat.extend.manual.registration"); } public Object onClick(Map submitParams) { return RegistrationPanel.this.registerManual(); } public boolean isDisabled() { return (callback.getDealershipInfo() == null); } }; addElement((HtmlElement)this.buttonRegisterManual); final LogoutLinkElement link = new LogoutLinkElement(context); addElement((HtmlElement)link); this.buttonLogout = new ClickButtonElement(context.createID(), null) { protected String getLabel() { return context.getLabel("logout"); } public Object onClick(Map submitParams) { return link.onClick(submitParams); } public boolean isDisabled() { return (callback.getDealershipInfo() == null); } }; addElement((HtmlElement)this.buttonLogout); this.displayRequestID = new RequestIDDisplayElement(context); this.displayRequestID.setValue(callback.getRequestID()); this.ieSubscriberID = new SubscriberIDInputElement(context) { public boolean isDisabled() { return (callback.getSubscriberID() != null); } }; addElement((HtmlElement)this.ieSubscriberID); if (callback.getSubscriberID() != null) this.ieSubscriberID.setValue(callback.getSubscriberID());  this.ieLicenseKey = new LicenseKeyInputElement(context); addElement((HtmlElement)this.ieLicenseKey); this.buttonRegisterLicense = new ClickButtonElement(context.createID(), null) { protected String getLabel() { return context.getLabel("auth.req.register.license.key"); } public Object onClick(Map submitParams) { return RegistrationPanel.this.registerLicenseKey(); } public boolean isDisabled() { return (callback.getRequestID() == null); } }; addElement((HtmlElement)this.buttonRegisterLicense); update();
/*     */   } public static interface OutputData {
/*     */     DealershipInfo getDealershipInfo(); LicenseKey getLicenseKey(); String getSubscriberID(); Set getAdditionalSubscriptions(); Integer getLicensedSessionCount(); } public static interface Callback {
/* 337 */     DealershipInfo getDealershipInfo(); void storeDealership(DealershipInfo param1DealershipInfo) throws Exception; Integer getLicensedSessionCount(); Subscription getSubscription(); Object registerOnline(RegistrationPanel.OutputData param1OutputData, RegistrationSTE.Authentication param1Authentication) throws Exception; Object registerManual(RegistrationPanel.OutputData param1OutputData) throws Exception; String getRequestID(); String getSubscriberID(); LicenseKey getLicenseKey(); Object registerLicenseKey(RegistrationPanel.OutputData param1OutputData) throws Exception; } public void update() {} private AuthenticationDialog getAuthenticationDialog() { synchronized (this.SYNC_AUTHDIALOG)
/* 338 */     { if (this.authDialog == null) {
/* 339 */         final HtmlElementContainer topLevel = getTopLevelContainer();
/* 340 */         this.authDialog = new AuthenticationDialog(this.context, new AuthenticationDialog.Callback()
/*     */             {
/*     */               public Object onOK(String user, String pwd) {
/* 343 */                 RegistrationPanel.this.authentication = (RegistrationSTE.Authentication)new AuthenticationRI(user, pwd);
/* 344 */                 return RegistrationPanel.this.registerOnline();
/*     */               }
/*     */               
/*     */               public Object onCancel() {
/* 348 */                 return topLevel;
/*     */               }
/*     */             });
/*     */       } 
/*     */       
/* 353 */       return this.authDialog; }  }
/*     */   public String getHtmlCode(Map params) { StringBuffer ret = new StringBuffer(TEMPLATE); StringUtilities.replace(ret, "{MSG_STEP1}", this.context.getMessage("registration.step.1")); StringUtilities.replace(ret, "{MSG_STEP2}", this.context.getMessage("registration.step.2")); StringUtilities.replace(ret, "{MSG_STEP3}", this.context.getMessage("registration.step.3")); StringUtilities.replace(ret, "{MSG_STEP4}", this.context.getMessage("registration.step.4")); StringUtilities.replace(ret, "{MSG_STEP5}", this.context.getMessage("registration.step.5")); StringUtilities.replace(ret, "{MSG_STEP6}", this.context.getMessage("registration.step.6")); StringUtilities.replace(ret, "{MSG_STEP7}", this.context.getMessage("registration.step.7")); StringUtilities.replace(ret, "{BUTTON_DEALERSHIPINFO}", this.buttonEditDealershipInfo.getHtmlCode(params)); StringUtilities.replace(ret, "{IE_USER_COUNT}", this.ieUserCount.getHtmlCode(params)); StringUtilities.replace(ret, "{IE_SUBSCRIPTIONS}", this.ieSubscriptions.getHtmlCode(params)); StringUtilities.replace(ret, "{BUTTON_REG_ONLINE}", this.buttonRegisterOnline.getHtmlCode(params)); StringUtilities.replace(ret, "{BUTTON_REG_MANUAL}", this.buttonRegisterManual.getHtmlCode(params)); StringUtilities.replace(ret, "{BUTTON_LOGOUT}", this.buttonLogout.getHtmlCode(params)); StringUtilities.replace(ret, "{DISPLAY_REQUESTID}", this.displayRequestID.getHtmlCode(params)); StringUtilities.replace(ret, "{IE_SUBSCRIBERID}", this.ieSubscriberID.getHtmlCode(params)); StringUtilities.replace(ret, "{IE_LICENSEKEY}", this.ieLicenseKey.getHtmlCode(params)); StringUtilities.replace(ret, "{BUTTON_REG_LICENSE_KEY}", this.buttonRegisterLicense.getHtmlCode(params)); return ret.toString(); }
/*     */   private OutputData getOutputData() { return new OutputData() {
/*     */         public String getSubscriberID() { return (String)RegistrationPanel.this.ieSubscriberID.getValue(); } public LicenseKey getLicenseKey() { return (LicenseKey)RegistrationPanel.this.ieLicenseKey.getValue(); } public DealershipInfo getDealershipInfo() { return RegistrationPanel.this.callback.getDealershipInfo(); } public Set getAdditionalSubscriptions() { Set subscriptions = new HashSet((Collection)RegistrationPanel.this.ieSubscriptions.getValue()); subscriptions.remove(RegistrationPanel.this.callback.getSubscription()); return subscriptions; } public Integer getLicensedSessionCount() { return (Integer)RegistrationPanel.this.ieUserCount.getValue(); }
/*     */       }; } private Object editDealershipInfo() { try { return new DealershipInputDialog(this.context, new DealershipInputDialog.Callback() {
/*     */             public Object onStore(DealershipInfo dealershipInfo) throws Exception { RegistrationPanel.this.callback.storeDealership(dealershipInfo); Object ret = RegistrationPanel.this.getTopLevelContainer(); return RegistrationPanel.this.getInfoPopup(RegistrationPanel.this.context.getMessage("auth.req.dialog.dealership.data.stored"), ret); } public Object onCancel() { return RegistrationPanel.this.getTopLevelContainer(); } public DealershipInfo getDealershipInfo() { return RegistrationPanel.this.callback.getDealershipInfo(); }
/* 359 */           }); } catch (Exception e) { log.error("unable to provide dealership input dialog - exception: " + e, e); return getErrorPopup(this.context.getMessage("error.see.log")); }  } private Object registerOnline() { try { Object ret = this.callback.registerOnline(getOutputData(), this.authentication);
/* 360 */       this.authentication = null;
/* 361 */       this.displayRequestID.setValue(this.callback.getRequestID());
/* 362 */       this.ieLicenseKey.setValue(null);
/* 363 */       return ret; }
/*     */     
/* 365 */     catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e)
/* 366 */     { Object ret = getAuthenticationDialog();
/* 367 */       if (this.authentication != null) {
/* 368 */         ret = getErrorPopup(this.context.getMessage("invalid.authentication"), ret);
/*     */       }
/* 370 */       return ret; }
/* 371 */     catch (Exception e)
/* 372 */     { return getErrorPopup(e); }
/*     */      }
/*     */ 
/*     */   
/*     */   private Object registerManual() {
/*     */     try {
/* 378 */       Object ret = this.callback.registerManual(getOutputData());
/* 379 */       this.displayRequestID.setValue(this.callback.getRequestID());
/* 380 */       this.ieLicenseKey.setValue(null);
/* 381 */       return ret;
/*     */     }
/* 383 */     catch (Exception e) {
/* 384 */       return getErrorPopup(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object registerLicenseKey() {
/*     */     try {
/* 390 */       return this.callback.registerLicenseKey(getOutputData());
/* 391 */     } catch (Exception e) {
/* 392 */       return getErrorPopup(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\RegistrationPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */