/*    */ package com.eoos.gm.tis2web.registration.v2.client;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.VisualModuleProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.InconsistentSystemException;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*    */ import com.eoos.gm.tis2web.registration.v2.client.ui.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_Registration
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 22 */   private static ASServiceImpl_Registration instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_Registration(Configuration configuration) {
/* 27 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_Registration createInstance(Configuration configuration) {
/* 32 */     instance = new ASServiceImpl_Registration(configuration);
/* 33 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Registration getInstance() {
/* 37 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 41 */     return ApplicationContext.getInstance().isStandalone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void checkSystemState(ClientContext context) throws InconsistentSystemException {
/* 53 */     boolean inconsistent = SoftwareKeyProvider.getInstance().getService().hasValidAuthorization();
/* 54 */     if (!inconsistent) {
/*    */       return;
/*    */     }
/*    */     
/* 58 */     List modules = VisualModuleProvider.getInstance(context).getVisualModules();
/* 59 */     VisualModule admin = VisualModuleProvider.getInstance(context).getVisualModule("admin");
/* 60 */     inconsistent = (inconsistent && (modules.size() == 0 || (modules.size() == 1 && modules.contains(admin))));
/*    */     
/* 62 */     if (inconsistent) {
/* 63 */       throw new InconsistentSystemException(context.getMessage("registration.error.no.services.available"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/*    */     try {
/* 70 */       checkSystemState(context);
/* 71 */     } catch (InconsistentSystemException e) {
/* 72 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 75 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 80 */     return ApplicationContext.getInstance().getLabel(locale, "registration.admin");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 84 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 88 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\client\ASServiceImpl_Registration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */