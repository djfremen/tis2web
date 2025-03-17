/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RPOLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public RPOLink(ClientContext context) {
/* 19 */     super(context, "rpo.jpg", "home.tooltip.rpo", "home.rpo");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 24 */     RPOService service = (RPOService)ConfiguredServiceProvider.getInstance().getService(RPOService.class);
/* 25 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\RPOLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */