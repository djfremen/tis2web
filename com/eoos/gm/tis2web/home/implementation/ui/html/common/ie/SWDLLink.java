/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.swdl.service.SWDLService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SWDLLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public SWDLLink(ClientContext context) {
/* 19 */     super(context, "swdl.jpg", "home.tooltip.swdl", "home.swdl");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 24 */     SWDLService service = (SWDLService)ConfiguredServiceProvider.getInstance().getService(SWDLService.class);
/* 25 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\SWDLLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */