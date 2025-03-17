/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequestImpl;
/*     */ import com.eoos.gm.tis2web.registration.server.ConfigurationMediator;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationAttribute;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFilter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase {
/*  27 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "mainpanel.html", null).toString();
/*  33 */     } catch (Exception e) {
/*  34 */       log.error("error loading template - error:" + e, e);
/*  35 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  41 */   private final Object SYNC = new Object();
/*     */   
/*     */   private FilterInputElement inputFilter;
/*     */   
/*     */   private ClickButtonElement applyFilter;
/*     */   
/*     */   private AdminPanel adminPanel;
/*     */   
/*     */   private boolean init = true;
/*     */ 
/*     */   
/*     */   private MainPanel(final ClientContext context) {
/*  53 */     this.context = context;
/*     */     
/*  55 */     this.inputFilter = new FilterInputElement(context);
/*  56 */     addElement((HtmlElement)this.inputFilter);
/*     */     
/*  58 */     this.applyFilter = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  61 */           return context.getLabel("apply");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  66 */             MainPanel.this.applyFilter();
/*  67 */           } catch (Exception e) {
/*  68 */             MainPanel.log.error("unable to apply filter, ignoring - exception:" + e, e);
/*     */           } 
/*  70 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  74 */     addElement((HtmlElement)this.applyFilter);
/*  75 */     update();
/*  76 */     this.init = false;
/*     */   }
/*     */   
/*     */   public void update() {
/*     */     try {
/*  81 */       init();
/*  82 */     } catch (Exception e) {
/*  83 */       log.error("unable to update - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() throws Exception {
/*  88 */     synchronized (this.SYNC) {
/*     */       
/*  90 */       Registry registry = RegistrationProvider.getInstance().getService();
/*     */       
/*  92 */       SalesOrganization organization = ConfigurationMediator.getSalesOrganization(this.context);
/*  93 */       if (organization == null) {
/*  94 */         log.error("no sales organization assigned to authorization administrator.");
/*  95 */         organization = SalesOrganization.GM;
/*     */       } 
/*  97 */       RequestStatus status = (this.inputFilter.getStatus() == null || this.inputFilter.getStatus().equals(AuthorizationRequest.STATUS_PENDING)) ? RequestStatus.PENDING : RequestStatus.AUTHORIZED;
/*  98 */       RegistrationFilter constraint = new RegistrationFilter();
/*  99 */       constraint.setFromTimeStamp(this.inputFilter.getDateFrom().getTime());
/* 100 */       constraint.setToTimeStamp(this.inputFilter.getDateUntil().getTime());
/* 101 */       if (this.inputFilter.getDealershipID() != null) {
/* 102 */         constraint.setAttribute(RegistrationAttribute.DEALERSHIP_ID);
/* 103 */         constraint.setValue(this.inputFilter.getDealershipID());
/* 104 */       } else if (this.inputFilter.getDealership() != null) {
/* 105 */         constraint.setAttribute(RegistrationAttribute.DEALERSHIP_NAME);
/* 106 */         constraint.setValue(this.inputFilter.getDealership());
/* 107 */       } else if (this.inputFilter.getRequestID() != null) {
/* 108 */         constraint.setAttribute(RegistrationAttribute.REQUEST_ID);
/* 109 */         constraint.setValue(this.inputFilter.getRequestID());
/*     */       } 
/* 111 */       List<Registration> registrations = registry.loadRegistrationRecords(organization, status, this.init ? null : constraint, null, null);
/*     */       
/* 113 */       List<AuthorizationRequestImpl> requests = null;
/* 114 */       if (registrations == null) {
/* 115 */         requests = Collections.EMPTY_LIST;
/*     */       } else {
/* 117 */         requests = new ArrayList(registrations.size());
/* 118 */         for (int i = 0; i < registrations.size(); i++) {
/* 119 */           requests.add(new AuthorizationRequestImpl(this.context, registrations.get(i)));
/*     */         }
/*     */       } 
/*     */       
/* 123 */       if (this.adminPanel != null) {
/* 124 */         removeElement((HtmlElement)this.adminPanel);
/*     */       }
/* 126 */       this.adminPanel = new AdminPanel(this.context, requests);
/* 127 */       addElement((HtmlElement)this.adminPanel);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 133 */     synchronized (context.getLockObject()) {
/* 134 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 135 */       if (instance == null) {
/* 136 */         instance = new MainPanel(context);
/* 137 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 139 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 144 */     synchronized (this.SYNC) {
/* 145 */       StringBuffer tmp = new StringBuffer(template);
/* 146 */       StringUtilities.replace(tmp, "{LABEL_FILTER}", this.context.getLabel("filter"));
/* 147 */       StringUtilities.replace(tmp, "{INPUT_FILTER}", this.inputFilter.getHtmlCode(params));
/* 148 */       StringUtilities.replace(tmp, "{BUTTON_APPLY}", this.applyFilter.getHtmlCode(params));
/*     */       
/* 150 */       StringUtilities.replace(tmp, "{PANEL}", this.adminPanel.getHtmlCode(params));
/* 151 */       return tmp.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyFilter() {
/* 156 */     update();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\server\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */