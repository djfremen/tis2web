/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*    */ import com.eoos.gm.tis2web.lt.service.LTService;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ 
/*    */ public final class VCLinkCallback
/*    */   implements VCLinkElement.Callback {
/*    */   private ClientContext context;
/*    */   
/*    */   public VCLinkCallback(ClientContext context) {
/* 15 */     this.context = context;
/*    */   }
/*    */   
/*    */   public Object getReturnUI() {
/* 19 */     LTService service = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/*    */     
/* 21 */     return service.getUI(this.context.getSessionID(), null);
/*    */   }
/*    */   
/*    */   public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 25 */     return (key == VehicleConfigurationUtil.KEY_MAKE || key == VehicleConfigurationUtil.KEY_MODEL);
/*    */   }
/*    */   
/*    */   public boolean isReadonly(Object key) {
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\main\VCLinkCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */