/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.ecm;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagListElement;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm.ECMFaultDiagElement;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
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
/*    */ public class ECMFaultDiagListElement
/*    */   extends FaultDiagListElement
/*    */ {
/*    */   private String ecm;
/*    */   
/*    */   public ECMFaultDiagListElement(DataRetrievalAbstraction.DataCallback data, String faultCode, ClientContext context, HtmlElementStack stack, String ecm) {
/* 28 */     super(data, faultCode, context, stack, new HtmlLabel[] { new HtmlLabel(context.getLabel("si.faultdiag.ecm")), new HtmlLabel(context.getLabel("si.faultdiag.FaultCode")), new HtmlLabel(context.getLabel("si.faultdiag.Configuration")), new HtmlLabel(context.getLabel("si.faultdiag.Symptom")) });
/* 29 */     this.ecm = ecm;
/*    */   }
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/*    */     HtmlLabel htmlLabel;
/* 33 */     HtmlElement ret = null;
/* 34 */     if (data instanceof ECMFaultDiagElement)
/* 35 */     { HtmlLabel htmlLabel1; ECMFaultDiagElement fde = (ECMFaultDiagElement)data;
/* 36 */       switch (columnIndex)
/*    */       { case 0:
/* 38 */           return (HtmlElement)new HtmlLabel(this.ecm);
/*    */ 
/*    */ 
/*    */         
/*    */         case 1:
/* 43 */           return getLinkElement(data);
/*    */ 
/*    */         
/*    */         case 2:
/* 47 */           htmlLabel = new HtmlLabel(fde.getConfiguration());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 55 */           return (HtmlElement)htmlLabel; }  htmlLabel = new HtmlLabel(fde.getSymptom()); }  return (HtmlElement)htmlLabel;
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 59 */     return 4;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\ecm\ECMFaultDiagListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */