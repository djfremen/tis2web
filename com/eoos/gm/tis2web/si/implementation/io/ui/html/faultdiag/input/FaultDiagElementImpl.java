/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.db.SIOWISElementImpl;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.text.Collator;
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
/*    */ public class FaultDiagElementImpl
/*    */   implements FaultDiagElement, Comparable
/*    */ {
/*    */   protected SIO sio;
/*    */   protected Collator col;
/* 33 */   protected String symptom = "";
/*    */ 
/*    */   
/*    */   public FaultDiagElementImpl(SITOCElement x, Collator col, ClientContext context) {
/* 37 */     this.col = col;
/* 38 */     Object ff = x.getProperty((SITOCProperty)SIOProperty.WIS_FAULTFINDER);
/* 39 */     int sioid = Integer.parseInt((String)ff);
/* 40 */     this.sio = SIDataAdapterFacade.getInstance(context).getSI().getSIO(CTOCType.WIS, sioid);
/*    */   }
/*    */   
/*    */   public Integer getDocID() {
/* 44 */     return (this.sio instanceof SIOWISElementImpl && ((SIOWISElementImpl)this.sio).hasProperty((SITOCProperty)SIOProperty.Page)) ? Integer.valueOf((String)this.sio.getProperty((SITOCProperty)SIOProperty.Page)) : this.sio.getID();
/*    */   }
/*    */   
/*    */   public int compareTo(Object o) {
/* 48 */     int ret = 0;
/* 49 */     if (o instanceof FaultDiagElementImpl) {
/* 50 */       FaultDiagElementImpl bEl = (FaultDiagElementImpl)o;
/* 51 */       ret = this.col.compare(this.symptom, bEl.getSymptom());
/* 52 */       if (ret == 0) {
/* 53 */         Integer t1 = getDocID();
/* 54 */         Integer t2 = bEl.getDocID();
/* 55 */         ret = t1.compareTo(t2);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     int ret = FaultDiagElementImpl.class.hashCode();
/* 66 */     ret = HashCalc.addHashCode(ret, this.symptom);
/* 67 */     ret = HashCalc.addHashCode(ret, getDocID());
/* 68 */     return ret;
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
/*    */   public SIO getSIO() {
/* 83 */     return this.sio;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSymptom() {
/* 93 */     return this.symptom;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 97 */     return (compareTo(obj) == 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\FaultDiagElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */