/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.sps.service.SPSService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public SPSLink(ClientContext context) {
/* 19 */     super(context, "sps.jpg", "home.tooltip.sps", "home.sps");
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 23 */     SPSService service = (SPSService)ConfiguredServiceProvider.getInstance().getService(SPSService.class);
/* 24 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\SPSLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */