/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.standalone;
/*     */ 
/*     */ import com.eoos.gm.tis2web.admin.implementation.ui.html.home.AdminLayoutControl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKeyFactory;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipAdapter;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements AdminLayoutControl {
/*     */   private ClientContext context;
/*     */   private HtmlElement panel;
/*     */   
/*     */   private MainPanel(ClientContext context) {
/*  26 */     this.context = context;
/*  27 */     this.panel = createPanel();
/*  28 */     addElement(this.panel);
/*     */   }
/*     */   
/*     */   private HtmlElement createPanel() {
/*  32 */     final SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/*  33 */     if (!service.hasValidAuthorization()) {
/*  34 */       return RegistrationDialog.create(this.context, null);
/*     */     }
/*  36 */     RegistrationPanel.Callback callback = new RegistrationPanel.Callback()
/*     */       {
/*     */         public int getRegistrationType() {
/*  39 */           if (service.hasMigratedHardwareKeyAuthorization()) {
/*  40 */             return 0;
/*     */           }
/*  42 */           return 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public Subscription getSubscription() {
/*  47 */           return (Subscription)new SubscriptionAdapter((Subscription)service.getSubscription());
/*     */         }
/*     */         
/*     */         public String getSubscriberID() {
/*  51 */           return service.getSubscriberID();
/*     */         }
/*     */         
/*     */         public SoftwareKey getSoftwareKey() {
/*  55 */           return SoftwareKeyFactory.createSoftwareKey(service.getSoftwareKey());
/*     */         }
/*     */         
/*     */         public LicenseKey getLicenseKey() {
/*  59 */           return null;
/*     */         }
/*     */         
/*     */         public DealershipInfo getDealershipInfo() {
/*  63 */           return (DealershipInfo)new DealershipAdapter(MainPanel.this.context, service.getDealershipInformation());
/*     */         }
/*     */         
/*     */         public Integer getLicensedSessionCount() {
/*     */           try {
/*  68 */             if (service.getLicensedSessionCount() > 0) {
/*  69 */               return Integer.valueOf(service.getLicensedSessionCount());
/*     */             }
/*  71 */           } catch (Exception e) {}
/*     */           
/*  73 */           return (service.getMaxSessionCount() > 0) ? Integer.valueOf(service.getMaxSessionCount()) : null;
/*     */         }
/*     */         
/*     */         public String getRequestID() {
/*  77 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  81 */     return (HtmlElement)new RegistrationPanel(this.context, callback)
/*     */       {
/*     */         protected Object onClose() {
/*  84 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/*  91 */     synchronized (context.getLockObject()) {
/*  92 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/*  93 */       if (instance == null) {
/*  94 */         instance = new MainPanel(context);
/*  95 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/*  97 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 102 */     return this.panel.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   public int getLayoutConstraint() {
/* 106 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\standalone\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */