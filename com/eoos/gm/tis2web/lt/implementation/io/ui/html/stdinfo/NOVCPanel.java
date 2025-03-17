/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.lt.service.LTService;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NOVCPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(SplitContentPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 35 */       template = ApplicationContext.getInstance().loadFile(NOVCPanel.class, "novcpanel.html", null).toString();
/* 36 */     } catch (Exception e) {
/* 37 */       log.error("unable to load template - error:" + e, e);
/* 38 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private ClickButtonElement buttonRetry;
/*    */   
/*    */   public NOVCPanel(final ClientContext context) {
/* 48 */     this.context = context;
/*    */     
/* 50 */     this.buttonRetry = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 52 */           return context.getLabel("retry");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 56 */           MainPage main = MainPage.getInstance(context);
/* 57 */           LTService service = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/* 58 */           return new ResultObject(0, main.switchModule((VisualModule)service));
/*    */         }
/*    */       };
/* 61 */     addElement((HtmlElement)this.buttonRetry);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext getContext() {
/* 66 */     return this.context;
/*    */   }
/*    */   
/*    */   private String getMessage() {
/* 70 */     IConfiguration vc = VCFacade.getInstance(this.context).getCfg();
/* 71 */     if (VehicleConfigurationUtil.getMake(vc) != null)
/*    */     {
/* 73 */       return this.context.getMessage("lt.no.lt.for.vc");
/*    */     }
/* 75 */     return this.context.getMessage("lt.novc");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 80 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 82 */     StringUtilities.replace(code, "{MESSAGE}", getMessage());
/* 83 */     StringUtilities.replace(code, "{BUTTON_RETRY}", this.buttonRetry.getHtmlCode(params));
/*    */     
/* 85 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\NOVCPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */