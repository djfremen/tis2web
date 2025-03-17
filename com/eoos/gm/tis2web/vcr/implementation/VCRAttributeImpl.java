/*    */ package com.eoos.gm.tis2web.vcr.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class VCRAttributeImpl
/*    */   implements VCRAttribute {
/*  9 */   protected int attributeDomain = 0;
/*    */   
/* 11 */   protected int attributeValue = 0;
/*    */   
/*    */   public VCRAttributeImpl(VCValue value) {
/* 14 */     this.attributeDomain = value.getDomainID().intValue();
/* 15 */     this.attributeValue = value.getValueID().intValue();
/*    */   }
/*    */   
/*    */   public VCRAttributeImpl(int attributeDomain, int attributeValue) {
/* 19 */     this.attributeDomain = attributeDomain;
/* 20 */     this.attributeValue = attributeValue;
/*    */   }
/*    */   
/*    */   VCRAttributeImpl(VCRAttributeImpl attribute) {
/* 24 */     this.attributeDomain = attribute.attributeDomain;
/* 25 */     this.attributeValue = attribute.attributeValue;
/*    */   }
/*    */ 
/*    */   
/*    */   VCRAttributeImpl() {}
/*    */   
/*    */   boolean gt(VCRAttributeImpl attribute) {
/* 32 */     return (gtDomain(attribute) || (eqDomain(attribute) && gtValue(attribute)));
/*    */   }
/*    */   
/*    */   public int getDomainID() {
/* 36 */     return this.attributeDomain;
/*    */   }
/*    */   
/*    */   public int getValueID() {
/* 40 */     return this.attributeValue;
/*    */   }
/*    */ 
/*    */   
/*    */   long getHashCode() {
/* 45 */     return (new Hash(this.attributeDomain)).sag(new Hash(this.attributeValue)).bag(1666666666661L);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 49 */     return this.attributeDomain + ":" + this.attributeValue;
/*    */   }
/*    */   
/*    */   boolean scan(StringTokenizer st, String s) {
/*    */     try {
/* 54 */       this.attributeDomain = Integer.parseInt(s);
/* 55 */       s = st.nextToken();
/* 56 */       s = st.nextToken();
/* 57 */       this.attributeValue = Integer.parseInt(s);
/* 58 */       return true;
/* 59 */     } catch (Exception ex) {
/* 60 */       throw new IllegalArgumentException();
/*    */     } 
/*    */   }
/*    */   
/*    */   boolean matchDomain(VCRAttributeImpl other) {
/* 65 */     return eqDomain(other);
/*    */   }
/*    */   
/*    */   public boolean match(VCRAttribute other) {
/* 69 */     return (eqDomain((VCRAttributeImpl)other) && eqValue((VCRAttributeImpl)other));
/*    */   }
/*    */   
/*    */   protected boolean gtDomain(VCRAttributeImpl attribute) {
/* 73 */     return (this.attributeDomain > attribute.attributeDomain);
/*    */   }
/*    */   
/*    */   protected boolean eqDomain(VCRAttributeImpl attribute) {
/* 77 */     return (this.attributeDomain == attribute.attributeDomain);
/*    */   }
/*    */   
/*    */   protected boolean gtValue(VCRAttributeImpl attribute) {
/* 81 */     return (this.attributeValue > attribute.attributeValue);
/*    */   }
/*    */   
/*    */   protected boolean eqValue(VCRAttributeImpl attribute) {
/* 85 */     return (this.attributeValue == attribute.attributeValue);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VCRAttributeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */