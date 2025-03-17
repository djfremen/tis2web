/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultDiagElementImpl;
/*    */ import java.text.Collator;
/*    */ import java.util.StringTokenizer;
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
/*    */ 
/*    */ public class ECMFaultDiagElementImpl
/*    */   extends FaultDiagElementImpl
/*    */   implements ECMFaultDiagElement
/*    */ {
/* 29 */   protected String configuration = "";
/*    */   
/*    */   public ECMFaultDiagElementImpl(SITOCElement x, ClientContext context, Collator col) {
/* 32 */     super(x, col, context);
/* 33 */     String label = x.getLabel(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/* 34 */     if (label != null) {
/* 35 */       StringTokenizer st = new StringTokenizer(label, "#");
/* 36 */       if (st.hasMoreTokens()) {
/* 37 */         this.symptom = st.nextToken();
/* 38 */         if (st.hasMoreTokens()) {
/* 39 */           this.configuration = st.nextToken().trim();
/*    */         }
/*    */       } 
/*    */     } 
/* 43 */     if (this.symptom == null) {
/* 44 */       this.symptom = "";
/*    */     }
/* 46 */     this.symptom = this.symptom.trim();
/* 47 */     this.configuration = this.configuration.trim();
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
/*    */   public String getConfiguration() {
/* 65 */     return this.configuration;
/*    */   }
/*    */   
/*    */   public int compareTo(Object o) {
/* 69 */     int ret = 0;
/* 70 */     if (o instanceof ECMFaultDiagElementImpl) {
/* 71 */       ECMFaultDiagElementImpl bEl = (ECMFaultDiagElementImpl)o;
/* 72 */       ret = this.col.compare(this.configuration, bEl.getConfiguration());
/* 73 */       if (ret == 0)
/*    */       {
/* 75 */         ret = super.compareTo(o);
/*    */       }
/*    */     } else {
/*    */       
/* 79 */       ret = super.compareTo(o);
/*    */     } 
/* 81 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\ecm\ECMFaultDiagElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */