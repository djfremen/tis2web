/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.sps.service.CalibrationInfoService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CalIdLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public CalIdLink(ClientContext context) {
/* 21 */     super(context, "calid.jpg", "home.tooltip.calid", "home.sps_ci");
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 25 */     CalibrationInfoService service = (CalibrationInfoService)ConfiguredServiceProvider.getInstance().getService(CalibrationInfoService.class);
/* 26 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\CalIdLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */