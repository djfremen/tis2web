/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.hardware;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.SelectionRequestPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HardwareSelectionRequestPanel
/*    */   extends HtmlElementContainerBase
/*    */   implements RequestHandlerPanel
/*    */ {
/*    */   private SelectionRequestPanel selectionPanel;
/*    */   private HardwareSelectionRequest request;
/*    */   
/*    */   public HardwareSelectionRequestPanel(ClientContext context, HardwareSelectionRequest request) {
/* 24 */     this.request = request;
/* 25 */     this.selectionPanel = new SelectionRequestPanel(context, (SelectionRequest)request);
/* 26 */     addElement((HtmlElement)this.selectionPanel);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 30 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{DESCRIPTION}</td></tr><tr><td>{SELECTION_PANEL}</td></tr></table>");
/*    */     
/*    */     try {
/* 33 */       StringBuffer description = new StringBuffer(this.request.getHardwareDescription());
/* 34 */       StringUtilities.replace(description, "\r\n", "<br>");
/* 35 */       StringUtilities.replace(description, "\n", "<br>");
/* 36 */       StringUtilities.replace(tmp, "{DESCRIPTION}", description.toString());
/* 37 */     } catch (NullPointerException e) {
/* 38 */       StringUtilities.replace(tmp, "{DESCRIPTION}", "");
/*    */     } 
/* 40 */     StringUtilities.replace(tmp, "{SELECTION_PANEL}", this.selectionPanel.getHtmlCode(params));
/* 41 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public boolean onNext(CustomAVMap avMap) {
/* 45 */     return this.selectionPanel.onNext(avMap);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\hardware\HardwareSelectionRequestPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */