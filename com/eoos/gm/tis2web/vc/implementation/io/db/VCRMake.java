/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCMake;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCModel;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VCRMake
/*    */   extends VCRBaseValue
/*    */   implements VCMake
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected List boundValues;
/*    */   
/*    */   public VCRMake(VCRDomain domain, int value_id, VCRLabel label) {
/* 22 */     super(domain, value_id, label);
/*    */   }
/*    */   
/*    */   public List getModels(VC vc) {
/* 26 */     if (this.boundValues == null && getConfigurations() != null) {
/* 27 */       this.boundValues = new ArrayList();
/* 28 */       VCDomain domain = vc.getDomain("Model");
/* 29 */       for (Iterator<VCRConfiguration> iter = getConfigurations().iterator(); iter.hasNext(); ) {
/* 30 */         VCRConfiguration element = iter.next();
/* 31 */         VCModel model = (VCModel)element.getElement(domain);
/* 32 */         if (!this.boundValues.contains(model)) {
/* 33 */           this.boundValues.add(model);
/*    */         }
/*    */       } 
/*    */     } 
/* 37 */     return this.boundValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public List getModels() {
/* 42 */     return this.boundValues;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRMake.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */