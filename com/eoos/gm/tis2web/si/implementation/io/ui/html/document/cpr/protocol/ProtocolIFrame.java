/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.html.element.IFrameElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtocolIFrame
/*    */   extends IFrameElement
/*    */ {
/*    */   ProtocolPage protocolPage;
/* 22 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   public ProtocolIFrame(ClientContext context, ProtocolPage protocolPage) {
/* 26 */     super("ProtocolIFrame");
/* 27 */     this.protocolPage = protocolPage;
/* 28 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 29 */     this.height = scp.getDisplayHeight();
/* 30 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 32 */             ProtocolIFrame.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHeight() {
/* 38 */     return String.valueOf(this.height);
/*    */   }
/*    */   
/*    */   protected String getSourceURL(Map params) {
/* 42 */     return this.protocolPage.getURL(params);
/*    */   }
/*    */   
/*    */   protected String getWidth() {
/* 46 */     return "100%";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\protocol\ProtocolIFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */