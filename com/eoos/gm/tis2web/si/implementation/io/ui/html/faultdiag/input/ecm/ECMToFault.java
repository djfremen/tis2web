/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultCallback;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FilterCallback;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.SITOCElementContainer;
/*    */ import java.util.List;
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
/*    */ public class ECMToFault
/*    */   implements FilterCallback, FilterCallback2
/*    */ {
/*    */   protected ClientContext context;
/* 26 */   SITOCElementContainer cont = null;
/*    */   
/*    */   FaultCallback fcb;
/*    */   
/*    */   public ECMToFault(ClientContext context) {
/* 31 */     this.context = context;
/*    */   }
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
/*    */ 
/*    */   
/*    */   public List getData(Object filter1, Object filter2) {
/* 49 */     return this.fcb.getData(filter2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getData(Object filter) {
/* 56 */     SITOCElementContainer contNew = (SITOCElementContainer)filter;
/* 57 */     if (contNew != this.cont) {
/* 58 */       SITOCElement elem = contNew.getElement();
/* 59 */       this.fcb = new ECMFaultCallback(elem, this.context);
/* 60 */       this.cont = contNew;
/*    */     } 
/*    */ 
/*    */     
/* 64 */     return this.fcb.getData();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\ecm\ECMToFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */