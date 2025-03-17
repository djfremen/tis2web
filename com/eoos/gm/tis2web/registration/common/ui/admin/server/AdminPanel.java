/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequestImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.dialog.AuthReqDetailDialog;
/*     */ import com.eoos.gm.tis2web.registration.server.ConfigurationMediator;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AdminPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(AdminPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(AdminPanel.class, "adminpanel.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  42 */   private AuthRequestListElement listElement = null;
/*     */   
/*  44 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*     */   private ClickButtonElement buttonCreateRequest;
/*     */   
/*     */   private ClickButtonElement buttonExportDB;
/*     */ 
/*     */   
/*     */   public AdminPanel(final ClientContext context, List requests) {
/*  52 */     this.context = context;
/*     */     
/*  54 */     this.listElement = new AuthRequestListElement(requests, context);
/*  55 */     int pageSize = 15;
/*     */     try {
/*  57 */       pageSize = Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.registration.adminpanel.pagesize"));
/*  58 */     } catch (Exception e) {}
/*     */     
/*  60 */     this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, pageSize, 20);
/*  61 */     addElement(this.listElementPagedFront);
/*     */     
/*  63 */     this.buttonCreateRequest = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  66 */           return context.getLabel("create.manual.registration.request");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  71 */             return AdminPanel.this.onCreateRequest();
/*  72 */           } catch (Exception e) {
/*  73 */             AdminPanel.log.error("unable to perform action 'create request'  - exception: " + e, e);
/*  74 */             final HtmlElementContainer topLevel = AdminPanel.this.getTopLevelContainer();
/*  75 */             return new ErrorMessageBox(context, null, context.getMessage("exception.unable.to.execute.action"))
/*     */               {
/*     */                 protected Object onOK(Map params) {
/*  78 */                   return topLevel;
/*     */                 }
/*     */               };
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  86 */     addElement((HtmlElement)this.buttonCreateRequest);
/*     */     
/*  88 */     this.buttonExportDB = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  91 */           return context.getLabel("export.registration.database");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  96 */             return AdminPanel.this.onExportDB();
/*  97 */           } catch (Exception e) {
/*  98 */             AdminPanel.log.error("unable to perform action 'export db'  - exception: " + e, e);
/*  99 */             final HtmlElementContainer topLevel = AdminPanel.this.getTopLevelContainer();
/* 100 */             return new ErrorMessageBox(context, null, context.getMessage("exception.unable.to.execute.action"))
/*     */               {
/*     */                 protected Object onOK(Map params) {
/* 103 */                   return topLevel;
/*     */                 }
/*     */               };
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 111 */     addElement((HtmlElement)this.buttonExportDB);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 115 */     HtmlElementContainer container = getContainer();
/* 116 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 117 */       container = container.getContainer();
/*     */     }
/* 119 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 123 */     StringBuffer retvalue = new StringBuffer(template);
/* 124 */     if (this.listElement.getDataCallback().getData().size() > 0) {
/* 125 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/*     */     } else {
/* 127 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("auth.req.no.entries"));
/*     */     } 
/* 129 */     StringUtilities.replace(retvalue, "{BUTTON_CREATE_REQUEST}", this.buttonCreateRequest.getHtmlCode(params));
/* 130 */     StringUtilities.replace(retvalue, "{BUTTON_EXPORT_DB}", this.buttonExportDB.getHtmlCode(params));
/*     */     
/* 132 */     return retvalue.toString();
/*     */   }
/*     */   
/*     */   private Object onCreateRequest() throws Exception {
/* 136 */     final HtmlElementContainer returnElement = getTopLevelContainer();
/* 137 */     Registry registry = RegistrationProvider.getInstance().getService();
/* 138 */     Registration registration = registry.createRegistrationRequest();
/* 139 */     AuthorizationRequestImpl authorizationRequestImpl = new AuthorizationRequestImpl(this.context, registration);
/* 140 */     AuthReqDetailDialog detailDialog = new AuthReqDetailDialog(this.context, (AuthorizationRequest)authorizationRequestImpl)
/*     */       {
/*     */         protected Object onClose() {
/* 143 */           return returnElement;
/*     */         }
/*     */       };
/*     */     
/* 147 */     return detailDialog;
/*     */   }
/*     */   
/*     */   private Object onExportDB() throws Exception {
/*     */     try {
/* 152 */       Registry registry = RegistrationProvider.getInstance().getService();
/* 153 */       SalesOrganization organization = ConfigurationMediator.getSalesOrganization(this.context);
/* 154 */       if (organization == null) {
/* 155 */         final HtmlElementContainer topLevel = getTopLevelContainer();
/* 156 */         return new ErrorMessageBox(this.context, null, this.context.getMessage("auth.req.regdb.no.organization"))
/*     */           {
/*     */             protected Object onOK(Map params) {
/* 159 */               return topLevel;
/*     */             }
/*     */           };
/*     */       } 
/* 163 */       String regdb = registry.exportRegistrationDatabase(organization);
/* 164 */       ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 165 */       props.filename = "registration.db";
/* 166 */       props.mime = "text/plain; charset=utf-8";
/* 167 */       props.data = regdb.getBytes();
/* 168 */       return new ResultObject(13, true, true, props);
/* 169 */     } catch (Exception e) {
/* 170 */       log.error("unable to perform action 'create request'  - exception: " + e, e);
/* 171 */       final HtmlElementContainer topLevel = getTopLevelContainer();
/* 172 */       return new ErrorMessageBox(this.context, null, this.context.getMessage("exception.unable.to.execute.action"))
/*     */         {
/*     */           protected Object onOK(Map params) {
/* 175 */             return topLevel;
/*     */           }
/*     */         };
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\server\AdminPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */