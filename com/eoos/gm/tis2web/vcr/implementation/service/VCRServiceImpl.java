/*     */ package com.eoos.gm.tis2web.vcr.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.ConstraintVCR;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.VCRFactory;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.VCRImpl;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.VehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.VCRService;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VCRServiceImpl
/*     */   implements VCRService
/*     */ {
/*  35 */   private VCRFactory factory = null;
/*     */   
/*     */   public VCRServiceImpl(Configuration cfg) {
/*  38 */     this.factory = new VCRFactory(cfg);
/*     */   }
/*     */   
/*     */   private VCRFactory getFactory() {
/*  42 */     return this.factory;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  46 */     return null;
/*     */   }
/*     */   
/*     */   public VCRAttribute makeAttribute(VCValue value) {
/*  50 */     return (VCRAttribute)getFactory().makeAttribute(value);
/*     */   }
/*     */   
/*     */   public VCRAttribute makeAttribute(int aDomain, int aValue) {
/*  54 */     return (VCRAttribute)getFactory().makeAttribute(aDomain, aValue);
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm() {
/*  58 */     return (VCRTerm)getFactory().makeTerm();
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm(VCValue value) {
/*  62 */     return getFactory().makeTerm(value);
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm(VCRAttribute attribute) {
/*  66 */     return (VCRTerm)getFactory().makeTerm(attribute);
/*     */   }
/*     */   
/*     */   public VCRExpression makeExpression() {
/*  70 */     return (VCRExpression)getFactory().makeExpression();
/*     */   }
/*     */   
/*     */   public VCR makeVCR() {
/*  74 */     return (VCR)getFactory().makeVCR();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRExpression expression) {
/*  78 */     return (VCR)getFactory().makeVCR(expression);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRTerm term) {
/*  82 */     return (VCR)getFactory().makeVCR(term);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRAttribute attribute) {
/*  86 */     return getFactory().makeVCR(attribute);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCValue value) {
/*  90 */     return getFactory().makeVCR(value);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(int id) {
/*  94 */     return getFactory().makeVCR(id);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(String v) {
/*  98 */     return (VCR)getFactory().makeVCR(v);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCConfiguration vcc) {
/* 102 */     return makeVCR(vcc, null, null);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCConfiguration vcc, VCValue engine, VCValue transmission) {
/* 106 */     return getFactory().makeVCR(vcc, engine, transmission);
/*     */   }
/*     */   
/*     */   public Map<Integer, VCR> getVCRs(Collection ids) {
/* 110 */     return getFactory().getVCRs(ids);
/*     */   }
/*     */   
/*     */   public boolean isNullVCR(VCR vcr) {
/* 114 */     return (VCR.NULL == vcr);
/*     */   }
/*     */   
/*     */   public IVehicleOptionExpression createVehicleOptionExpression() {
/* 118 */     return (IVehicleOptionExpression)new VehicleOptionExpression();
/*     */   }
/*     */   
/*     */   public VCR createConstraintVCR() {
/* 122 */     return (VCR)new ConstraintVCR(getFactory());
/*     */   }
/*     */   
/*     */   public List checkOptionRestriction(List elements, VCR vcr, VCR positiveOptions, VCR negativeOptions) {
/* 126 */     return VCRImpl.checkOptionRestriction(elements, vcr, positiveOptions, negativeOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public VCR createVCR(String make, String model, String modelyear, String engine, String transmission, Locale locale, ILVCAdapter adapter) {
/* 131 */     VCR vcr = makeVCR();
/* 132 */     VCRExpression expression = makeExpression();
/*     */     
/* 134 */     for (int i = 0; i < 5; i++) {
/* 135 */       String domainID = null;
/* 136 */       String value = null;
/* 137 */       switch (i) {
/*     */         case 0:
/* 139 */           domainID = "Make";
/* 140 */           value = make;
/*     */           break;
/*     */         
/*     */         case 1:
/* 144 */           domainID = "Model";
/* 145 */           value = model;
/*     */           break;
/*     */         
/*     */         case 2:
/* 149 */           domainID = "ModelYear";
/* 150 */           value = modelyear;
/*     */           break;
/*     */         
/*     */         case 3:
/* 154 */           domainID = "Engine";
/* 155 */           value = engine;
/*     */           break;
/*     */         
/*     */         case 4:
/* 159 */           domainID = "Transmission";
/* 160 */           value = transmission;
/*     */           break;
/*     */         
/*     */         default:
/* 164 */           throw new IllegalStateException();
/*     */       } 
/* 166 */       if (value != null) {
/* 167 */         VCRDomain domain = (VCRDomain)adapter.getVC().getDomain(domainID);
/* 168 */         VCRValue vcrValue = domain.lookup(value);
/* 169 */         if (vcrValue == null) {
/*     */           
/* 171 */           Integer localeID = LocaleInfoProvider.getInstance().getLocale(locale).getLocaleID();
/*     */           
/* 173 */           vcrValue = domain.lookup(localeID, value);
/*     */         } 
/* 175 */         VCRAttribute vcrAttribute = makeAttribute((VCValue)vcrValue);
/* 176 */         expression.add(vcrAttribute);
/*     */       } 
/*     */     } 
/* 179 */     vcr.add(expression);
/* 180 */     return vcr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\service\VCRServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */