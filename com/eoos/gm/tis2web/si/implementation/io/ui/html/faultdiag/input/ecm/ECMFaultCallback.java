/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultCallback;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultDiagElement;
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
/*    */ public class ECMFaultCallback
/*    */   extends FaultCallback
/*    */ {
/*    */   public ECMFaultCallback(SITOCElement root, SITOCProperty first, ClientContext context) {
/* 23 */     super(root, first, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECMFaultCallback(SITOCElement root, ClientContext context) {
/* 31 */     super(root, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECMFaultCallback(SITOCElement root) {
/* 38 */     super(root);
/*    */   }
/*    */   
/*    */   protected FaultDiagElement createElement(SITOCElement x, ClientContext context) {
/* 42 */     return new ECMFaultDiagElementImpl(x, context, this.col);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\ecm\ECMFaultCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */