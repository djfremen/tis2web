/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.kdr.KDRService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KDRLink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public KDRLink(ClientContext context) {
/* 19 */     super(context, "kdr.jpg", "home.tooltip.kdr", "home.kdr");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 24 */     KDRService service = (KDRService)ConfiguredServiceProvider.getInstance().getService(KDRService.class);
/* 25 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\KDRLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */