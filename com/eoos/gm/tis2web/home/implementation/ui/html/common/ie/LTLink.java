/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.lt.service.LTService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public LTLink(ClientContext context) {
/* 19 */     super(context, "lt.jpg", "home.tooltip.lt", "home.lt");
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 23 */     LTService service = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/* 24 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\LTLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */