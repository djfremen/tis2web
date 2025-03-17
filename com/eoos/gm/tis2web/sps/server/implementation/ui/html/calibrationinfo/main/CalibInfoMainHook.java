/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.CalibInfoHomePanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
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
/*    */ 
/*    */ 
/*    */ public class CalibInfoMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public CalibInfoMainHook(ClientContext context) {
/* 25 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 29 */     return (HtmlElement)CalibInfoHomePanel.getInstance(this.context);
/*    */   }
/*    */   
/*    */   public synchronized String getHtmlCode(Map params) {
/* 33 */     StringBuffer retValue = new StringBuffer();
/* 34 */     retValue.append("<span class=\"sps_ci\" >");
/* 35 */     retValue.append(super.getHtmlCode(params));
/* 36 */     retValue.append("</span>");
/* 37 */     return retValue.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\main\CalibInfoMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */