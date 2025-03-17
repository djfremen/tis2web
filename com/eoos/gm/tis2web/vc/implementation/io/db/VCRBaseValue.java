/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCBaseValue;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VCRBaseValue
/*    */   extends VCRValueImpl implements VCBaseValue {
/*    */   private static final long serialVersionUID = 1L;
/* 13 */   protected List configurations = new ArrayList();
/*    */   
/*    */   public List getConfigurations() {
/* 16 */     return this.configurations;
/*    */   }
/*    */   
/*    */   public VCRBaseValue(VCRDomain domain, int value_id, VCRLabel label) {
/* 20 */     super(domain, value_id, label);
/*    */   }
/*    */   
/*    */   void addConfiguration(VCConfiguration configuration) {
/* 24 */     if (this.configurations == null) {
/* 25 */       this.configurations = new ArrayList();
/*    */     }
/* 27 */     this.configurations.add(configuration);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRBaseValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */