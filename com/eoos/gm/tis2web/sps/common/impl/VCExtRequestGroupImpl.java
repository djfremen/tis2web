/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VCExtRequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VCExtRequestGroupImpl
/*    */   implements VCExtRequestGroup, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   protected String vin = null;
/*    */   
/* 22 */   protected List attributes = null;
/*    */   
/* 24 */   protected Map pairvalueattr = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VCExtRequestGroupImpl(String vin, List attributes, Map assignments) {
/* 30 */     this.vin = vin;
/* 31 */     this.attributes = attributes;
/* 32 */     this.pairvalueattr = assignments;
/*    */   }
/*    */   
/*    */   public String getVIN() {
/* 36 */     return this.vin;
/*    */   }
/*    */   
/*    */   public List getAttributes() {
/* 40 */     return this.attributes;
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attr) {
/* 44 */     return (Value)this.pairvalueattr.get(attr);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VCExtRequestGroupImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */