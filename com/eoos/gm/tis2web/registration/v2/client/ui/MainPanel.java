/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.admin.implementation.ui.html.home.AdminLayoutControl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationSTE;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationTask;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.AuthorizationImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipAdapter;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipInformation;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.LicenseFileException;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends ContextualElementContainerBase
/*     */   implements AdminLayoutControl {
/*  47 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   
/*  49 */   private HtmlElement panel = null;
/*     */   
/*     */   private MainPanel(ClientContext context) {
/*  52 */     super(context);
/*     */   }
/*     */   
/*     */   private synchronized HtmlElement getPanel() {
/*  56 */     if (this.panel == null) {
/*  57 */       this.panel = createPanel();
/*  58 */       addElement(this.panel);
/*     */     } 
/*  60 */     return this.panel;
/*     */   }
/*     */   
/*     */   private HtmlElement createPanel() {
/*  64 */     final SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/*  65 */     final boolean initialRegistration = !service.hasValidAuthorization();
/*  66 */     final boolean hwkMigration = service.hasMigratedHardwareKeyAuthorization();
/*     */ 
/*     */     
/*  69 */     File dealershipFile = new File(ApplicationContext.getInstance().getProperty("frame.registration.standalone.dealership"));
/*  70 */     final File storageFile = new File(dealershipFile.getParentFile(), "requ.est");
/*  71 */     storageFile.getParentFile().mkdirs();
/*     */     
/*  73 */     return (HtmlElement)new RegistrationPanel(this.context, new RegistrationPanel.Callback()
/*     */         {
/*  75 */           private String requestID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           private synchronized void storeRequestID(String requestID) {
/*  94 */             this.requestID = requestID;
/*     */             try {
/*  96 */               ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile));
/*     */               try {
/*  98 */                 oos.writeObject(this.requestID);
/*     */               } finally {
/* 100 */                 oos.close();
/*     */               } 
/* 102 */             } catch (Exception e) {
/* 103 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */           
/*     */           public void storeDealership(DealershipInfo dealershipInfo) throws Exception {
/* 108 */             DealershipInformation dealership = new DealershipInformation(MainPanel.this.context, dealershipInfo);
/* 109 */             String check = dealership.check();
/* 110 */             if (check != null) {
/* 111 */               throw new InvalidInputException(MainPanel.this.context.getMessage("auth.req.dialog.dealership." + check));
/*     */             }
/*     */             try {
/* 114 */               service.setDealershipInformation((Dealership)dealership);
/* 115 */             } catch (Exception e) {
/* 116 */               MainPanel.log.error("unable to store dealership info - exception: " + e, e);
/* 117 */               throw new Exception(MainPanel.this.context.getMessage("auth.req.dialog.dealership.data.failure"));
/*     */             } 
/*     */           }
/*     */           
/*     */           private List toAuthorizationIDList(Set subscriptions) {
/* 122 */             List<String> ret = new ArrayList(subscriptions.size());
/* 123 */             for (Iterator<Subscription> iter = subscriptions.iterator(); iter.hasNext();) {
/* 124 */               ret.add(((Subscription)iter.next()).getSubscriptionID());
/*     */             }
/* 126 */             return ret;
/*     */           }
/*     */           public Object registerOnline(RegistrationPanel.OutputData data, RegistrationSTE.Authentication authentication) throws Exception {
/*     */             RegistrationTask task;
/* 130 */             Integer sessionCount = data.getLicensedSessionCount();
/* 131 */             if (sessionCount == null) {
/* 132 */               throw new InvalidInputException(MainPanel.this.context.getMessage("auth.req.dialog.online.registration.invalid.session.count"));
/*     */             }
/*     */ 
/*     */             
/* 136 */             if (initialRegistration) {
/* 137 */               Subscription subscription = null;
/*     */               try {
/* 139 */                 subscription = data.getAdditionalSubscriptions().iterator().next();
/* 140 */               } catch (Exception e) {
/* 141 */                 MainPanel.log.debug("...unable to retrieve additional subscriptions, continuing with null ");
/*     */               } 
/* 143 */               if (subscription == null) {
/* 144 */                 throw new Exception(MainPanel.this.context.getMessage("registration.exception.empty.subscription.selection"));
/*     */               }
/* 146 */               task = new RegistrationTask(service.getDealershipInformation(), subscription.getSubscriptionID(), service.getSoftwareKey(), sessionCount);
/*     */             
/*     */             }
/* 149 */             else if (hwkMigration) {
/* 150 */               if (sessionCount == null || sessionCount.intValue() == 0) {
/* 151 */                 sessionCount = Integer.valueOf(service.getMaxSessionCount());
/*     */               } else {
/* 153 */                 sessionCount = Integer.valueOf(Math.max(service.getMaxSessionCount(), sessionCount.intValue()));
/*     */               } 
/* 155 */               task = new RegistrationTask(false, service.getSubscriberID(), service.getSoftwareKey(), sessionCount);
/*     */             } else {
/* 157 */               Set subscriptions = data.getAdditionalSubscriptions();
/* 158 */               if (service.isRepeatRegistration(subscriptions)) {
/* 159 */                 task = new RegistrationTask(true, service.getSubscriberID(), service.getSoftwareKey(), sessionCount);
/*     */               } else {
/* 161 */                 List<String> authorizations = toAuthorizationIDList(subscriptions);
/* 162 */                 if (Util.isNullOrEmpty(authorizations)) {
/* 163 */                   authorizations = Collections.singletonList(getSubscription().getSubscriptionID());
/*     */                 }
/* 165 */                 task = new RegistrationTask(service.getSubscriberID(), authorizations, sessionCount);
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 170 */             Object retValue = null;
/*     */             try {
/* 172 */               retValue = RegistrationSTE.getInstance().execute((Task)task, authentication);
/* 173 */             } catch (Exception e) {
/* 174 */               Throwable t = e;
/* 175 */               while (t.getCause() != null) {
/* 176 */                 t = t.getCause();
/*     */               }
/* 178 */               MainPanel.log.error("unable to execute registration task - root exception: " + t, t);
/* 179 */               throw e;
/*     */             } 
/* 181 */             if (retValue == null)
/* 182 */               throw new Exception(MainPanel.this.context.getMessage("auth.req.dialog.online.registration.failed")); 
/* 183 */             if (retValue instanceof RegistrationException) {
/* 184 */               throw new Exception(MainPanel.this.context.getMessage("registration.exception." + ((RegistrationException)retValue).getMessage()));
/*     */             }
/* 186 */             storeRequestID((String)retValue);
/* 187 */             String message = MainPanel.this.context.getMessage("auth.req.dialog.online.registration.suceeded");
/* 188 */             message = StringUtilities.replace(message, "{REQUEST-ID}", (String)retValue);
/* 189 */             return MainPanel.this.getInfoPopup(message);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           protected List toAuthorizationList(Set subscriptions) {
/* 195 */             List<Authorization> authorizations = new LinkedList();
/* 196 */             if (!Util.isNullOrEmpty(subscriptions)) {
/* 197 */               for (Iterator<Subscription> iter = subscriptions.iterator(); iter.hasNext();) {
/* 198 */                 authorizations.add(AuthorizationImpl.lookup(((Subscription)iter.next()).getSubscriptionID()));
/*     */               }
/*     */             }
/* 201 */             return authorizations;
/*     */           }
/*     */           
/*     */           public Object registerManual(RegistrationPanel.OutputData data) throws Exception {
/* 205 */             Integer sessionCount = null;
/*     */             try {
/* 207 */               sessionCount = data.getLicensedSessionCount();
/* 208 */             } catch (Exception e) {
/* 209 */               throw new Exception(MainPanel.this.context.getMessage("auth.req.dialog.online.registration.invalid.session.count"));
/*     */             } 
/*     */             
/* 212 */             List authorizations = toAuthorizationList(data.getAdditionalSubscriptions());
/* 213 */             if (getSubscription() != null) {
/* 214 */               authorizations.addAll(0, toAuthorizationList(Collections.singleton(getSubscription())));
/*     */             }
/* 216 */             if (authorizations.size() == 0) {
/* 217 */               throw new Exception(MainPanel.this.context.getMessage("registration.exception.empty.subscription.selection"));
/*     */             }
/*     */             
/* 220 */             byte[] pdf = service.createManualRegistrationForm(authorizations, sessionCount, new SoftwareKeyService.Output()
/*     */                 {
/*     */                   public void setRequestID(String requestID) {
/* 223 */                     MainPanel.null.this.storeRequestID(requestID);
/*     */                   }
/*     */                 });
/*     */ 
/*     */             
/* 228 */             ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 229 */             props.data = pdf;
/* 230 */             props.filename = "registration.pdf";
/* 231 */             props.mime = "application/pdf";
/* 232 */             props.inline = false;
/* 233 */             return new ResultObject(13, true, false, props);
/*     */           }
/*     */           
/*     */           public Object registerLicenseKey(final RegistrationPanel.OutputData data) throws LicenseFileException, InvalidLicenseException, Exception {
/* 237 */             return new NotificationMessageBox(MainPanel.this.context, MainPanel.this.context.getLabel("auth.req.dialog.caption"), MainPanel.this.context.getMessage("auth.req.dialog.registration.may.take.some.time"))
/*     */               {
/*     */                 protected Object onOK(Map params) {
/*     */                   try {
/* 241 */                     service.updateLicense(data.getSubscriberID(), data.getLicenseKey().toExternalForm(false));
/* 242 */                     MainPanel.null.this.storeRequestID(null);
/*     */                     
/* 244 */                     return new SuccessfulRegistrationNotification(getContext());
/* 245 */                   } catch (LicenseFileException el) {
/* 246 */                     return MainPanel.this.getErrorPopup(this.context.getMessage("frame.registration.license.exception"));
/* 247 */                   } catch (InvalidLicenseException ei) {
/* 248 */                     return MainPanel.this.getErrorPopup(this.context.getMessage("frame.registration.license.invalid"));
/* 249 */                   } catch (Exception e) {
/* 250 */                     return MainPanel.this.getErrorPopup(this.context.getMessage("auth.req.dialog.license.registration.failed"));
/*     */                   } 
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public Subscription getSubscription() {
/* 257 */             return initialRegistration ? null : (Subscription)new SubscriptionAdapter((Subscription)service.getSubscription());
/*     */           }
/*     */           
/*     */           public String getSubscriberID() {
/* 261 */             return initialRegistration ? null : service.getSubscriberID();
/*     */           }
/*     */           
/*     */           public synchronized String getRequestID() {
/* 265 */             return this.requestID;
/*     */           }
/*     */           
/*     */           public Integer getLicensedSessionCount() {
/*     */             try {
/* 270 */               if (service.getLicensedSessionCount() > 0) {
/* 271 */                 return Integer.valueOf(service.getLicensedSessionCount());
/*     */               }
/* 273 */             } catch (Exception e) {}
/*     */             
/* 275 */             return (service.getMaxSessionCount() > 0) ? Integer.valueOf(service.getMaxSessionCount()) : null;
/*     */           }
/*     */           
/*     */           public LicenseKey getLicenseKey() {
/* 279 */             return null;
/*     */           }
/*     */           
/*     */           public DealershipInfo getDealershipInfo() {
/* 283 */             Dealership ret = service.getDealershipInformation();
/* 284 */             return (ret != null) ? (DealershipInfo)new DealershipAdapter(MainPanel.this.context, ret) : null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 292 */     synchronized (context.getLockObject()) {
/* 293 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 294 */       if (instance == null) {
/* 295 */         instance = new MainPanel(context);
/* 296 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 298 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 303 */     return getPanel().getHtmlCode(params);
/*     */   }
/*     */   
/*     */   public int getLayoutConstraint() {
/* 307 */     return 1;
/*     */   }
/*     */   
/*     */   public HtmlElement createErrorPopup(String message) {
/* 311 */     return (HtmlElement)getErrorPopup(message);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */