/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.service.SIService;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SILink
/*    */   extends ApplicationLinkBase
/*    */ {
/*    */   public SILink(final ClientContext context) {
/* 24 */     super(context, "si.jpg", "home.tooltip.si", "home.si");
/* 25 */     Util.executeAsynchronous(new Callable()
/*    */         {
/*    */           public Object call() throws Exception {
/* 28 */             SIDataAdapterFacade.getInstance(context).getSI().provideTSBs();
/* 29 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 37 */     SIService service = (SIService)ConfiguredServiceProvider.getInstance().getService(SIService.class);
/* 38 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\SILink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */