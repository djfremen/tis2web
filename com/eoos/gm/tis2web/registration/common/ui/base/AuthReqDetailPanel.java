/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKeyFactory;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*     */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationData;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AuthReqDetailPanel extends HtmlElementContainerBase {
/*  37 */   private static final Logger log = Logger.getLogger(AuthReqDetailPanel.class); private static final String TEMPLATE; private ClientContext context; private AuthorizationRequest request; private DealershipInfoInputElement ieDealership; private SoftwareKeyInputElement ieSoftwareKey; private SubscriptionsInputElement ieSubscriptions; private TextInputElement ieRequestID; private DateSelectionElement ieDateSelectionElement; private TextInputElement ieSubscriberID; private TextInputElement ieSessionCount; private LicenseKeyInputElement ieLicenseKey;
/*     */   private ClickButtonElement buttonResolveSubscriberID;
/*     */   
/*     */   static {
/*     */     try {
/*  42 */       TEMPLATE = ApplicationContext.getInstance().loadFile(AuthReqDetailPanel.class, "authreqdetail.html", null).toString();
/*  43 */     } catch (Exception e) {
/*  44 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  45 */       throw new RuntimeException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthReqDetailPanel(final ClientContext context, AuthorizationRequest request) {
/*  90 */     this.context = context;
/*  91 */     this.request = request;
/*     */     
/*  93 */     this.ieDealership = new DealershipInfoInputElement(context);
/*  94 */     addElement((HtmlElement)this.ieDealership);
/*  95 */     if (request != null && request.getDealershipInfo() != null) {
/*  96 */       this.ieDealership.setValue(request.getDealershipInfo());
/*     */     }
/*     */     
/*  99 */     this.ieSoftwareKey = new SoftwareKeyInputElement(context);
/* 100 */     addElement((HtmlElement)this.ieSoftwareKey);
/* 101 */     if (request != null && request.getSoftwareKey() != null) {
/* 102 */       this.ieSoftwareKey.setValue(request.getSoftwareKey());
/*     */     }
/*     */     
/* 105 */     this.ieSubscriptions = new SubscriptionsInputElement(context);
/* 106 */     addElement((HtmlElement)this.ieSubscriptions);
/* 107 */     if (request != null && request.getSubscription() != null) {
/* 108 */       this.ieSubscriptions.setValue(request.getSubscription());
/* 109 */       if (RequestType.EXTENSION.equals(request.getRegistration().getRequestType()) && 
/* 110 */         request.getRegistration().getAuthorizationList() != null) {
/* 111 */         this.ieSubscriptions.addValue(makeSubscriptions(request.getRegistration().getAuthorizationList()));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 116 */     this.ieRequestID = new TextInputElement(context.createID(), -1, 10);
/* 117 */     addElement((HtmlElement)this.ieRequestID);
/* 118 */     if (request != null && request.getType() != RequestType.TEMPORARY) {
/* 119 */       this.ieRequestID.setValue(request.getRequestID());
/*     */     }
/* 121 */     if (request.getType() != RequestType.TEMPORARY && request.getType() != RequestType.HWKLOCAL) {
/* 122 */       this.ieRequestID.setDisabled(Boolean.TRUE);
/*     */     }
/*     */     
/* 125 */     this.ieDateSelectionElement = new DateSelectionElement(context.createID(), 3, context.getLocale());
/* 126 */     addElement((HtmlElement)this.ieDateSelectionElement);
/* 127 */     if (request.getType() != RequestType.TEMPORARY) {
/* 128 */       this.ieDateSelectionElement.setValue(new Date(request.getRequestDate()));
/* 129 */       this.ieDateSelectionElement.setDisabled(Boolean.TRUE);
/*     */     } else {
/* 131 */       this.ieDateSelectionElement.setValue(new Date());
/*     */     } 
/*     */     
/* 134 */     this.ieSubscriberID = new TextInputElement(context.createID(), -1, 10);
/* 135 */     addElement((HtmlElement)this.ieSubscriberID);
/* 136 */     if (request != null && request.getSubscriberID() != null) {
/* 137 */       this.ieSubscriberID.setValue(request.getSubscriberID());
/* 138 */       if (request.getType() == RequestType.HWKMIGRATION || request.getType() == RequestType.EXTENSION) {
/* 139 */         this.ieSubscriberID.setDisabled(Boolean.TRUE);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     this.ieSessionCount = new TextInputElement(context.createID(), -1, 10);
/* 144 */     if (request != null && request.getSessions() != null) {
/* 145 */       this.ieSessionCount.setValue(request.getSessions().toString());
/*     */     }
/* 147 */     addElement((HtmlElement)this.ieSessionCount);
/*     */     
/* 149 */     this.ieLicenseKey = new LicenseKeyInputElement(context);
/*     */     
/* 151 */     this.ieLicenseKey.setDisabled(Boolean.TRUE);
/*     */     
/* 153 */     this.buttonResolveSubscriberID = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 156 */           return context.getLabel("look.up");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 161 */             AuthReqDetailPanel.this.resolveSubscriberID();
/* 162 */             return null;
/* 163 */           } catch (Exception e) {
/* 164 */             AuthReqDetailPanel.log.error("unable to resolve subscriber id, ignoring - exception: " + e, e);
/* 165 */             return ErrorMessageBox.create(context, context.getMessage("unable.to.resolve.subscriber.id"), getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 170 */     addElement((HtmlElement)this.buttonResolveSubscriberID);
/*     */   }
/*     */ 
/*     */   
/*     */   private List makeSubscriptions(List<String> authorizations) {
/* 175 */     List<SubscriptionAdapter> subscriptions = new ArrayList();
/* 176 */     for (int i = 0; i < authorizations.size(); i++) {
/* 177 */       String authorization = authorizations.get(i);
/* 178 */       subscriptions.add(new SubscriptionAdapter((Subscription)AuthorizationData.getAuthorization(authorization)));
/*     */     } 
/* 180 */     return subscriptions;
/*     */   } public static interface OutputData {
/*     */     DealershipInfo getDealershipInfo(); String getRequestID(); String getSubscriberID(); long getRequestDate(); SoftwareKey getSoftwareKey(); Set getSubscriptions(); int getSessionCount(); LicenseKey getLicenseKey(); }
/*     */   public AuthorizationRequest getRequest() {
/* 184 */     return this.request;
/*     */   }
/*     */   
/*     */   public void computeLicenseKey() throws Exception {
/* 188 */     SoftwareKey swk = (SoftwareKey)this.ieSoftwareKey.getValue();
/* 189 */     String subscriberID = (String)this.ieSubscriberID.getValue();
/* 190 */     Set subscriptions = new HashSet((Collection)this.ieSubscriptions.getValue());
/* 191 */     Integer users = null;
/*     */     try {
/* 193 */       users = Integer.valueOf((String)this.ieSessionCount.getValue());
/* 194 */     } catch (Exception e) {
/* 195 */       throw new IllegalArgumentException();
/*     */     } 
/* 197 */     LicenseKey key = createLicenseKey(swk, subscriberID, subscriptions, users);
/* 198 */     if (key != null) {
/* 199 */       this.ieLicenseKey.setValue(key);
/*     */     } else {
/* 201 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 206 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 207 */     StringUtilities.replace(ret, "{DEALERSHIP_INFO}", this.ieDealership.getHtmlCode(params));
/*     */     
/* 209 */     StringUtilities.replace(ret, "{LABEL_REQID}", this.context.getLabel("request.id"));
/* 210 */     StringUtilities.replace(ret, "{INPUT_REQID}", this.ieRequestID.getHtmlCode(params));
/*     */     
/* 212 */     StringUtilities.replace(ret, "{LABEL_REQDATE}", this.context.getLabel("request.date"));
/* 213 */     StringUtilities.replace(ret, "{INPUT_REQDATE}", this.ieDateSelectionElement.getHtmlCode(params));
/*     */     
/* 215 */     StringUtilities.replace(ret, "{LABEL_SUBSID}", this.context.getLabel("subscriber.id"));
/* 216 */     StringUtilities.replace(ret, "{INPUT_SUBSID}", this.ieSubscriberID.getHtmlCode(params));
/* 217 */     StringUtilities.replace(ret, "{BUTTON_RESOLVE_SUBSID}", this.buttonResolveSubscriberID.getHtmlCode(params));
/*     */     
/* 219 */     StringUtilities.replace(ret, "{SOFTWAREKEY}", this.ieSoftwareKey.getHtmlCode(params));
/*     */     
/* 221 */     StringUtilities.replace(ret, "{SUBSCRIPTIONS}", this.ieSubscriptions.getHtmlCode(params));
/*     */     
/* 223 */     StringUtilities.replace(ret, "{LABEL_SESSIONCOUNT}", this.context.getLabel("licensed.session.count"));
/* 224 */     StringUtilities.replace(ret, "{INPUT_SESSIONCOUNT}", this.ieSessionCount.getHtmlCode(params));
/*     */     
/* 226 */     StringUtilities.replace(ret, "{LICENSEKEY}", this.ieLicenseKey.getHtmlCode(params));
/*     */     
/* 228 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public OutputData getOutputData() {
/* 232 */     return new OutputData()
/*     */       {
/*     */         public Set getSubscriptions() {
/* 235 */           return new HashSet((Collection)AuthReqDetailPanel.this.ieSubscriptions.getValue());
/*     */         }
/*     */         
/*     */         public String getSubscriberID() {
/* 239 */           return (String)AuthReqDetailPanel.this.ieSubscriberID.getValue();
/*     */         }
/*     */         
/*     */         public SoftwareKey getSoftwareKey() {
/* 243 */           return (SoftwareKey)AuthReqDetailPanel.this.ieSoftwareKey.getValue();
/*     */         }
/*     */         
/*     */         public int getSessionCount() {
/*     */           try {
/* 248 */             return Integer.parseInt((String)AuthReqDetailPanel.this.ieSessionCount.getValue());
/* 249 */           } catch (Exception e) {
/* 250 */             return -1;
/*     */           } 
/*     */         }
/*     */         
/*     */         public String getRequestID() {
/* 255 */           return (String)AuthReqDetailPanel.this.ieRequestID.getValue();
/*     */         }
/*     */         
/*     */         public long getRequestDate() {
/* 259 */           return ((Date)AuthReqDetailPanel.this.ieDateSelectionElement.getValue()).getTime();
/*     */         }
/*     */         
/*     */         public LicenseKey getLicenseKey() {
/* 263 */           return (LicenseKey)AuthReqDetailPanel.this.ieLicenseKey.getValue();
/*     */         }
/*     */         
/*     */         public DealershipInfo getDealershipInfo() {
/* 267 */           return (DealershipInfo)AuthReqDetailPanel.this.ieDealership.getValue();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private LicenseKey createLicenseKey(SoftwareKey swk, String subscriberID, Set<Subscription> subscriptions, Integer users) {
/* 274 */     if (subscriptions.size() != 1) {
/* 275 */       return null;
/*     */     }
/* 277 */     Registry registry = RegistrationProvider.getInstance().getService();
/* 278 */     Registration registration = this.request.getRegistration();
/* 279 */     registration.setSoftwareKey(swk.toString());
/* 280 */     Subscription subscription = subscriptions.iterator().next();
/* 281 */     registration.setAuthorizationID(subscription.getSubscriptionID());
/*     */     try {
/* 283 */       registry.computeLicenceKey(this.request.getRegistration(), subscriberID, users);
/* 284 */     } catch (Exception e) {
/* 285 */       return null;
/*     */     } 
/* 287 */     return LicenseKeyFactory.createLicenseKey(registration.getLicenseKey());
/*     */   }
/*     */   
/*     */   private void resolveSubscriberID() throws Exception {
/* 291 */     log.debug("resolving subscriber id ...");
/* 292 */     String subscriberID = (String)this.ieSubscriberID.getValue();
/* 293 */     log.debug("...subscriber id: " + subscriberID);
/* 294 */     if (!Util.isNullOrEmpty(subscriberID)) {
/* 295 */       Dealership dealership = RegistrationProvider.getInstance().getService().loadRegistrationRecord(subscriberID).getDealership();
/* 296 */       if (dealership != null) {
/* 297 */         this.ieDealership.setValue(new DealershipAdapter(this.context, dealership));
/*     */       } else {
/* 299 */         log.debug("...dealership info not found");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\AuthReqDetailPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */