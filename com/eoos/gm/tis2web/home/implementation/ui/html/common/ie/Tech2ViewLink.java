/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.tech2view.service.Tech2ViewService;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Tech2ViewLink extends ApplicationLinkBase {
/*    */   public Tech2ViewLink(ClientContext context) {
/* 12 */     super(context, "t2v.jpg", "home.tooltip.tech2view", "home.tech2view");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 17 */     Tech2ViewService service = (Tech2ViewService)ConfiguredServiceProvider.getInstance().getService(Tech2ViewService.class);
/* 18 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\Tech2ViewLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */