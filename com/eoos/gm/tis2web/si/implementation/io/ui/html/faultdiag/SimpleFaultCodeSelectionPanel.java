/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FilterCallback;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class SimpleFaultCodeSelectionPanel
/*    */   extends FaultCodeSelectionPanel
/*    */ {
/*    */   private FilterCallback cprs;
/*    */   
/*    */   public SimpleFaultCodeSelectionPanel(ClientContext context, DataRetrievalAbstraction.DataCallback faultCodes, FilterCallback cprs, HtmlElementStack stack) {
/* 29 */     init(context, faultCodes, stack);
/* 30 */     this.cprs = cprs;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HtmlElement getNextPanel() {
/*    */     FaultDiagListElement faultDiagListElement;
/* 37 */     final Object val = this.faultSelection.getValue();
/* 38 */     HtmlElement ret = null;
/* 39 */     if (val != null) {
/* 40 */       DataRetrievalAbstraction.DataCallback dcb = new DataRetrievalAbstraction.DataCallback() {
/*    */           public List getData() {
/* 42 */             return SimpleFaultCodeSelectionPanel.this.cprs.getData(val);
/*    */           }
/*    */         };
/*    */ 
/*    */       
/* 47 */       faultDiagListElement = new FaultDiagListElement(dcb, val.toString(), this.context, this.stack);
/*    */     } 
/* 49 */     return (HtmlElement)faultDiagListElement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String EcmSelectionCode(Map params) {
/* 56 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\SimpleFaultCodeSelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */