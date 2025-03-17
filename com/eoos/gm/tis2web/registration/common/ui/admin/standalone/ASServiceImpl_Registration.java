/*    */ package com.eoos.gm.tis2web.registration.common.ui.admin.standalone;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ASServiceImpl_Registration
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 15 */   private static ASServiceImpl_Registration instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_Registration(Configuration configuration) {
/* 20 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_Registration createInstance(Configuration configuration) {
/* 25 */     instance = new ASServiceImpl_Registration(configuration);
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Registration getInstance() {
/* 30 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 34 */     return ApplicationContext.getInstance().isStandalone();
/*    */   }
/*    */ 
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 39 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 43 */     return ApplicationContext.getInstance().getLabel(locale, "registration.admin");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 47 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 51 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\standalone\ASServiceImpl_Registration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */