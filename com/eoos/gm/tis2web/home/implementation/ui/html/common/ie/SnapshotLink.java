/*    */ package com.eoos.gm.tis2web.home.implementation.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.snapshot.service.SnapshotService;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SnapshotLink extends ApplicationLinkBase {
/*    */   public SnapshotLink(ClientContext context) {
/* 12 */     super(context, "snap.jpg", "home.tooltip.snapshot", "home.snapshot");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 17 */     SnapshotService service = (SnapshotService)ConfiguredServiceProvider.getInstance().getService(SnapshotService.class);
/* 18 */     return MainPage.getInstance(this.context).switchModule((VisualModule)service);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\common\ie\SnapshotLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */