/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.IVCRMapping;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ 
/*    */ public class VCRMapping
/*    */   implements IVCRMapping
/*    */ {
/*    */   protected int mappingType;
/*    */   protected VCRValueImpl value;
/*    */   protected VCRValueImpl mappedValue;
/*    */   
/*    */   public int getType() {
/* 17 */     return this.mappingType;
/*    */   }
/*    */   
/*    */   public VCRValue getValue() {
/* 21 */     return this.value;
/*    */   }
/*    */   
/*    */   public VCRValue getMappedValue() {
/* 25 */     return this.mappedValue;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 29 */     return makeKey(this.value, this.mappedValue.getDomainID()) + ":" + this.mappedValue.getValueID();
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 33 */     return makeKey(this.value, this.mappedValue.getDomainID());
/*    */   }
/*    */   
/*    */   public static String makeKey(VCRValue value, VCRDomain domain) {
/* 37 */     return makeKey(value, domain.getDomainID());
/*    */   }
/*    */   
/*    */   public VCRMapping(int mappingType, VCValue value, VCValue mappedValue) {
/* 41 */     this.mappingType = mappingType;
/* 42 */     this.value = (VCRValueImpl)value;
/* 43 */     this.mappedValue = (VCRValueImpl)mappedValue;
/*    */   }
/*    */   
/*    */   public VCRMapping(VCValue value, VCValue mappedValue) {
/* 47 */     this(0, value, mappedValue);
/*    */   }
/*    */   
/*    */   public boolean match(IVCRMapping mapping) {
/* 51 */     return (this.value == mapping.getValue() && this.mappedValue == mapping.getMappedValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     if (obj != null && obj.getClass() == getClass()) {
/* 57 */       return match((IVCRMapping)obj);
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     int ret = VCRMapping.class.hashCode();
/* 66 */     ret = HashCalc.addHashCode(ret, this.value);
/* 67 */     ret = HashCalc.addHashCode(ret, this.mappedValue);
/*    */     
/* 69 */     return ret;
/*    */   }
/*    */   
/*    */   protected static String makeKey(VCRValue value, Integer domainID) {
/* 73 */     return value.getDomainID() + ":" + value.getValueID() + "-" + domainID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */