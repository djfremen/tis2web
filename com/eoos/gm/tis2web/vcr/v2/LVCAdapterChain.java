/*     */ package com.eoos.gm.tis2web.vcr.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LVCAdapterChain
/*     */   implements ILVCAdapter
/*     */ {
/*     */   private Collection<ILVCAdapter.Retrieval> delegates;
/*     */   private ILVCAdapter.Retrieval retrieval;
/*     */   
/*     */   public LVCAdapterChain(Collection<ILVCAdapter.Retrieval> delegates) {
/*  46 */     this.retrieval = new ILVCAdapter.Retrieval()
/*     */       {
/*     */         public ILVCAdapter getLVCAdapter()
/*     */         {
/*  50 */           return LVCAdapterChain.this; }
/*     */       };
/*     */     this.delegates = delegates;
/*     */   }
/*     */   public CfgDataProvider asCfgDataProvider() {
/*     */     throw new UnsupportedOperationException();
/*  56 */   } public ILVCAdapter.Retrieval createRetrievalImpl() { return this.retrieval; }
/*     */   public List checkOptionRestriction(List elements, VCR vcr, VCR positiveOptions, VCR negativeOptions) { throw new UnsupportedOperationException(); } public VCR createConstraintVCR() {
/*     */     throw new UnsupportedOperationException();
/*     */   } public VCR createVCR(String make, String model, String modelyear, String engine, String transmission, Locale locale) {
/*  60 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public IVehicleOptionExpression createVehicleOptionExpression() {
/*  64 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getEngineDomain() {
/*  68 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getModelDomain() {
/*  72 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR getNullVCR() {
/*  76 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getSalesmakeDomain() {
/*  80 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getTransmissionDomain() {
/*  84 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VC getVC() {
/*  88 */     return (VC)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/*  91 */             return ((ILVCAdapter.Retrieval)item).getLVCAdapter().getVC();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Object getVehicleOptionsDialog(String sessionID, List esc, ILVCAdapter.ReturnHandler returnHandler) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isNullVCR(VCR vcr) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isVCRCached() {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRAttribute makeAttribute(VCValue value) {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRAttribute makeAttribute(int aDomain, int aValue) {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRExpression makeExpression() {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm() {
/* 121 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm(VCValue value) {
/* 125 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm(VCRAttribute attribute) {
/* 129 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR() {
/* 133 */     return (VCR)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 136 */             return ((ILVCAdapter.Retrieval)item).getLVCAdapter().makeVCR();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRExpression expression) {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRTerm arg0) {
/* 146 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRAttribute attribute) {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCValue value) {
/* 154 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(int id) {
/* 158 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(String v) {
/* 162 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR makeVCR(final VCConfiguration vcc) {
/* 166 */     return (VCR)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 169 */             return ((ILVCAdapter.Retrieval)item).getLVCAdapter().makeVCR(vcc);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCConfiguration vcc, VCValue engine, VCValue transmission) {
/* 175 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Map<Integer, VCR> getVCRs(Collection ids) {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void provideVCRs(Map icrs, List vcrs, int offset, int limit) {
/* 183 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Collection toConfiguration(VCR vcr) {
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR toVCR(final IConfiguration cfg) {
/* 191 */     return (VCR)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 194 */             return ((ILVCAdapter.Retrieval)item).getLVCAdapter().toVCR(cfg);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public VCR toVCR(final List cfgs) {
/* 200 */     return (VCR)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 203 */             return ((ILVCAdapter.Retrieval)item).getLVCAdapter().toVCR(cfgs);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Set getConfigurations() {
/* 209 */     Set ret = new HashSet();
/* 210 */     for (Iterator<ILVCAdapter.Retrieval> iter = this.delegates.iterator(); iter.hasNext();) {
/* 211 */       ret.addAll(((ILVCAdapter)iter.next()).getConfigurations());
/*     */     }
/* 213 */     return ret;
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 217 */     Set ret = new HashSet();
/* 218 */     for (Iterator<ILVCAdapter.Retrieval> iter = this.delegates.iterator(); iter.hasNext();) {
/* 219 */       ret.addAll(((ILVCAdapter)iter.next()).getKeys());
/*     */     }
/* 221 */     return ret;
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 225 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 229 */     Set ret = new HashSet();
/* 230 */     for (Iterator<ILVCAdapter.Retrieval> iter = this.delegates.iterator(); iter.hasNext();) {
/* 231 */       ret.addAll(((ILVCAdapter)iter.next()).resolveVIN(vin));
/*     */     }
/* 233 */     return ret;
/*     */   }
/*     */   
/*     */   public Set getConfigurations_Legacy() {
/* 237 */     Set ret = new HashSet();
/* 238 */     for (Iterator<ILVCAdapter.Retrieval> iter = this.delegates.iterator(); iter.hasNext();) {
/* 239 */       ret.addAll(((ILVCAdapter)iter.next()).getConfigurations_Legacy());
/*     */     }
/* 241 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\v2\LVCAdapterChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */