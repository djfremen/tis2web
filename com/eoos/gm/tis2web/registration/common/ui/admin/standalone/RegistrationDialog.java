/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.standalone;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.task.AuthenticationRI;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationSTE;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationTask;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.dialog.AuthReqCreationDialog;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipAdapter;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipInformation;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.LicenseFileException;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RegistrationDialog {
/*     */   public static HtmlElement create(final ClientContext context, final Object retValue) {
/*  28 */     final SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/*  29 */     AuthReqCreationDialog regDialog = new AuthReqCreationDialog(context, new AuthReqCreationDialog.Callback()
/*     */         {
/*     */           public Subscription getSubscription() {
/*  32 */             return new Subscription()
/*     */               {
/*     */                 public String getDenotation(Locale locale) {
/*  35 */                   return service.getDefaultSubscription().getDescription(locale);
/*     */                 }
/*     */                 
/*     */                 public String getSubscriptionID() {
/*  39 */                   return service.getDefaultSubscription().getAuthorizationID();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public String getSubscriberID() {
/*  45 */             return null;
/*     */           }
/*     */           
/*     */           public SoftwareKey getSoftwareKey() {
/*  49 */             return SoftwareKeyFactory.createSoftwareKey(service.getSoftwareKey());
/*     */           }
/*     */           
/*     */           public LicenseKey getLicenseKey() {
/*  53 */             return null;
/*     */           }
/*     */           
/*     */           public DealershipInfo getDealershipInfo() {
/*  57 */             if (service.getDealershipInformation() == null) {
/*  58 */               return null;
/*     */             }
/*  60 */             return (DealershipInfo)new DealershipAdapter(context, service.getDealershipInformation());
/*     */           }
/*     */ 
/*     */           
/*     */           public Integer getLicensedSessionCount() {
/*  65 */             return Integer.valueOf(service.getLicensedSessionCount());
/*     */           }
/*     */           
/*     */           public String getRequestID() {
/*  69 */             return null;
/*     */           }
/*     */         })
/*     */       {
/*     */         public Object registerOnline(AuthReqCreationDialog.OutputData data) throws Exception {
/*  74 */           DealershipInformation dealership = new DealershipInformation(context, data.getDealershipInfo());
/*  75 */           String check = dealership.check();
/*  76 */           if (check != null) {
/*  77 */             return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */           }
/*  79 */           service.setDealershipInformation((Dealership)dealership);
/*  80 */           String softwareKey = service.getSoftwareKey();
/*  81 */           String subscriptionID = service.getDefaultSubscription().getAuthorizationID();
/*  82 */           Integer sessions = null;
/*     */           try {
/*  84 */             sessions = data.getLicensedSessionCount();
/*  85 */           } catch (Exception e) {
/*  86 */             return getErrorPopup("auth.req.dialog.online.registration.invalid.session.count");
/*     */           } 
/*  88 */           final RegistrationTask task = new RegistrationTask(service.getDealershipInformation(), subscriptionID, softwareKey, sessions);
/*     */           try {
/*  90 */             return evaluateROResult(RegistrationSTE.getInstance().execute((Task)task));
/*  91 */           } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/*  92 */             final HtmlElementContainer topLevel = getTopLevelContainer();
/*  93 */             return new AuthenticationDialog(context, new AuthenticationDialog.Callback()
/*     */                 {
/*     */                   public Object onOK(String user, String pwd) throws RegistrationSTE.MissingAuthenticationException {
/*     */                     try {
/*  97 */                       return RegistrationDialog.null.this.evaluateROResult(RegistrationSTE.getInstance().execute((Task)task, (RegistrationSTE.Authentication)new AuthenticationRI(user, pwd)));
/*  98 */                     } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/*  99 */                       throw e;
/* 100 */                     } catch (Exception e) {
/* 101 */                       return RegistrationDialog.null.this.evaluateROResult((Object)null);
/*     */                     } 
/*     */                   }
/*     */                   
/*     */                   public Object onCancel() {
/* 106 */                     return topLevel;
/*     */                   }
/*     */                 });
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private Object evaluateROResult(Object retValue) {
/* 115 */           if (retValue == null)
/* 116 */             return getErrorPopup("auth.req.dialog.online.registration.failed"); 
/* 117 */           if (retValue instanceof com.eoos.gm.tis2web.registration.service.cai.RegistrationException) {
/* 118 */             return getErrorPopup("auth.req.dialog.online.registration.duplicate");
/*     */           }
/* 120 */           return getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.online.registration.suceeded", "{REQUEST-ID}", (String)retValue);
/*     */         }
/*     */ 
/*     */         
/*     */         protected Object printRequest(AuthReqCreationDialog.OutputData data) throws Exception {
/* 125 */           DealershipInformation dealership = new DealershipInformation(context, data.getDealershipInfo());
/* 126 */           String check = dealership.check();
/* 127 */           if (check != null) {
/* 128 */             return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */           }
/* 130 */           Integer sessions = data.getLicensedSessionCount();
/* 131 */           service.setDealershipInformation((Dealership)dealership);
/* 132 */           byte[] pdf = service.createManualRegistrationForm(null, sessions, null);
/* 133 */           ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 134 */           props.data = pdf;
/* 135 */           props.filename = "registration.pdf";
/* 136 */           props.mime = "application/pdf";
/* 137 */           props.inline = false;
/* 138 */           return new ResultObject(13, true, false, props);
/*     */         }
/*     */         
/*     */         protected Object storeDealershipData(AuthReqCreationDialog.OutputData data) throws Exception {
/* 142 */           DealershipInformation dealership = new DealershipInformation(context, data.getDealershipInfo());
/* 143 */           String check = dealership.check();
/* 144 */           if (check != null) {
/* 145 */             return getErrorPopup("auth.req.dialog.dealership." + check);
/*     */           }
/*     */           try {
/* 148 */             service.setDealershipInformation((Dealership)dealership);
/* 149 */             return getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.dealership.data.stored", null, null);
/* 150 */           } catch (Exception e) {
/* 151 */             return getErrorPopup("auth.req.dialog.dealership.data.failure");
/*     */           } 
/*     */         }
/*     */         
/*     */         protected Object registerLicenseKey(final AuthReqCreationDialog.OutputData data) throws Exception {
/* 156 */           final HtmlElementContainer topLevel = getTopLevelContainer();
/* 157 */           return new NotificationMessageBox(context, context.getLabel("auth.req.dialog.caption"), context.getMessage("auth.req.dialog.registration.may.take.some.time"))
/*     */             {
/*     */               protected Object onOK(Map params) {
/*     */                 try {
/* 161 */                   service.updateLicense(data.getSubscriberID(), data.getLicenseKey().toString());
/* 162 */                   return RegistrationDialog.null.this.getMessagePopup("auth.req.dialog.caption", "auth.req.dialog.license.registration.suceeded", null, null);
/* 163 */                 } catch (LicenseFileException el) {
/* 164 */                   return getErrorPopup("frame.registration.license.exception", topLevel);
/* 165 */                 } catch (InvalidLicenseException ei) {
/* 166 */                   return getErrorPopup("frame.registration.license.invalid", topLevel);
/* 167 */                 } catch (Exception e) {
/* 168 */                   return getErrorPopup("auth.req.dialog.license.registration.failed", topLevel);
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public Object onClose() {
/* 175 */           ClientContextProvider.getInstance().invalidateSession("TIS2WEB");
/* 176 */           return retValue;
/*     */         }
/*     */         
/*     */         public String getHtmlCode(Map params) {
/* 180 */           return getContent(params);
/*     */         }
/*     */       };
/*     */     
/* 184 */     return (HtmlElement)regDialog;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\standalone\RegistrationDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */