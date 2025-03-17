/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.hardware;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HardwareNumberInputRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HardwareNumberInputPanel
/*    */   extends HtmlElementContainerBase
/*    */   implements RequestHandlerPanel
/*    */ {
/*    */   private ClientContext context;
/*    */   private HardwareNumberInputRequest request;
/*    */   private TextInputElement inputNumber;
/*    */   
/*    */   public HardwareNumberInputPanel(ClientContext context, HardwareNumberInputRequest request) {
/* 28 */     this.context = context;
/* 29 */     this.request = request;
/*    */     
/* 31 */     this.inputNumber = new TextInputElement(context.createID());
/* 32 */     addElement((HtmlElement)this.inputNumber);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 36 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{MESSAGE}:</td><td>{INPUT_NUMBER}</td></tr></table>");
/*    */     
/*    */     try {
/* 39 */       StringBuffer description = new StringBuffer(this.context.getMessage("sps.calid.input.hw.number"));
/* 40 */       StringUtilities.replace(description, "\r\n", "<br>");
/* 41 */       StringUtilities.replace(description, "\n", "<br>");
/* 42 */       StringUtilities.replace(tmp, "{MESSAGE}", description.toString());
/* 43 */     } catch (NullPointerException e) {
/* 44 */       StringUtilities.replace(tmp, "{MESSAGE}", "");
/*    */     } 
/* 46 */     StringUtilities.replace(tmp, "{INPUT_NUMBER}", this.inputNumber.getHtmlCode(params));
/* 47 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public boolean onNext(CustomAVMap avMap) {
/* 51 */     String input = (String)this.inputNumber.getValue();
/* 52 */     if (!Util.isNullOrEmpty(input)) {
/* 53 */       avMap.explicitSet(this.request.getAttribute(), (Value)new ValueAdapter(input));
/* 54 */       return true;
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\hardware\HardwareNumberInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */