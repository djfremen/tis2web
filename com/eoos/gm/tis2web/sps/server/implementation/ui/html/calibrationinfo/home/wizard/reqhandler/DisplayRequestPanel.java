/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler;
/*    */ 
/*    */ import com.eoos.condition.Condition;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.AssertUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayRequestPanel
/*    */   extends HtmlElementContainerBase
/*    */   implements RequestHandlerPanel
/*    */ {
/*    */   private DisplayRequest request;
/*    */   
/*    */   public DisplayRequestPanel(ClientContext context, DisplayRequest request) {
/* 24 */     this.request = request;
/*    */     
/* 26 */     AssertUtil.ensure(request, (Condition)new AssertUtil.OfType(HtmlDisplayRequest.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 31 */     StringBuffer retValue = new StringBuffer(((HtmlDisplayRequest)this.request).getHtmlCode());
/* 32 */     StringUtilities.replace(retValue, "http://pre-prog-instr=", "");
/* 33 */     return retValue.toString();
/*    */   }
/*    */   
/*    */   public boolean onNext(CustomAVMap avMap) {
/* 37 */     Object value = this.request.getConfirmationValue();
/* 38 */     avMap.set(this.request.getAttribute(), (Value)value);
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\DisplayRequestPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */