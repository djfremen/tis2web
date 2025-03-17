/*     */ package com.eoos.gm.tis2web.vcr.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.vc.implementation.service.VCServiceImpl;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProviderImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.service.VCRServiceImpl;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Collection;
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
/*     */ public class LVCAdapter
/*     */   implements ILVCAdapter
/*     */ {
/*     */   private VCServiceImpl vcDelegate;
/*     */   private VCRServiceImpl vcrDelegate;
/*     */   private String id;
/*     */   private final Object SYNC_CFGDATAPROVIDER;
/*     */   private CfgDataProvider cfgDataProvider;
/*     */   
/*     */   public LVCAdapter(Configuration cfgVC, Configuration cfgVCR) {
/* 202 */     this.SYNC_CFGDATAPROVIDER = new Object();
/* 203 */     this.cfgDataProvider = null; this.vcDelegate = new VCServiceImpl(cfgVC, createRetrievalImpl()); this.vcrDelegate = new VCRServiceImpl(cfgVCR); this.id = LVCAdapterProvider.getKey(cfgVC, cfgVCR);
/*     */   }
/*     */   public String toString() { return this.id; }
/* 206 */   public Set getConfigurations() { return this.vcDelegate.getConfigurations(); } public DBVersionInformation getDatabaseInfo() { return this.vcDelegate.getDatabaseInfo(); } public List getEngineDomain() { return this.vcDelegate.getEngineDomain(); } public Object getIdentifier() { return this.vcDelegate.getIdentifier(); } public Set getKeys() { return this.vcDelegate.getKeys(); } public long getLastModified() { return this.vcDelegate.getLastModified(); } public List getModelDomain() { return this.vcDelegate.getModelDomain(); } public ModuleInformation getModuleInformation() { return this.vcDelegate.getModuleInformation(); } public List getSalesmakeDomain() { return this.vcDelegate.getSalesmakeDomain(); } public List getTransmissionDomain() { return this.vcDelegate.getTransmissionDomain(); } public String getType() { return this.vcDelegate.getType(); } public VC getVC() { return this.vcDelegate.getVC(); } public Object getVehicleOptionsDialog(String sessionID, List esc, ILVCAdapter.ReturnHandler returnHandler) { return this.vcDelegate.getVehicleOptionsDialog(sessionID, esc, returnHandler, this); } public boolean isSupported(String salesmake) { return this.vcDelegate.isSupported(salesmake); } public Set resolveVIN(VIN vin) throws VIN.InvalidVINException { return this.vcDelegate.resolveVIN(vin); } public List checkOptionRestriction(List elements, VCR vcr, VCR positiveOptions, VCR negativeOptions) { return this.vcrDelegate.checkOptionRestriction(elements, vcr, positiveOptions, negativeOptions); } public VCR createConstraintVCR() { return this.vcrDelegate.createConstraintVCR(); } public VCR createVCR(String make, String model, String modelyear, String engine, String transmission, Locale locale) { return this.vcrDelegate.createVCR(make, model, modelyear, engine, transmission, locale, this); } public CfgDataProvider asCfgDataProvider() { synchronized (this.SYNC_CFGDATAPROVIDER)
/* 207 */     { if (this.cfgDataProvider == null) {
/* 208 */         this.cfgDataProvider = (CfgDataProvider)new CfgDataProviderImpl(this, VehicleConfigurationUtil.cfgUtil);
/*     */       }
/* 210 */       return this.cfgDataProvider; }  }
/*     */   public IVehicleOptionExpression createVehicleOptionExpression() { return this.vcrDelegate.createVehicleOptionExpression(); }
/*     */   public boolean isNullVCR(VCR vcr) { return (VCR.NULL == vcr); }
/*     */   public VCRAttribute makeAttribute(int domain, int value) { return this.vcrDelegate.makeAttribute(domain, value); }
/*     */   public VCRAttribute makeAttribute(VCValue value) { return this.vcrDelegate.makeAttribute(value); }
/* 215 */   public VCRExpression makeExpression() { return this.vcrDelegate.makeExpression(); } public VCRTerm makeTerm() { return this.vcrDelegate.makeTerm(); } public VCRTerm makeTerm(VCRAttribute attribute) { return this.vcrDelegate.makeTerm(attribute); } public VCRTerm makeTerm(VCValue value) { return this.vcrDelegate.makeTerm(value); } public VCR makeVCR() { return this.vcrDelegate.makeVCR(); } public VCR makeVCR(int id) { return this.vcrDelegate.makeVCR(id); } public VCR makeVCR(String v) { return this.vcrDelegate.makeVCR(v); } public VCR makeVCR(VCConfiguration vcc, VCValue engine, VCValue transmission) { return this.vcrDelegate.makeVCR(vcc, engine, transmission); } public VCR makeVCR(VCConfiguration vcc) { return this.vcrDelegate.makeVCR(vcc); } public VCR makeVCR(VCRAttribute attribute) { return this.vcrDelegate.makeVCR(attribute); } public VCR makeVCR(VCRExpression expression) { return this.vcrDelegate.makeVCR(expression); } public VCR makeVCR(VCRTerm term) { return this.vcrDelegate.makeVCR(term); } public VCR makeVCR(VCValue value) { return this.vcrDelegate.makeVCR(value); } public Map<Integer, VCR> getVCRs(Collection ids) { return this.vcrDelegate.getVCRs(ids); } public VCR toVCR(IConfiguration cfg) { return this.vcDelegate.toVCR(cfg); } public ILVCAdapter.Retrieval createRetrievalImpl() { return new ILVCAdapter.Retrieval.RI(this); } public Collection toConfiguration(VCR vcr) { return this.vcDelegate.toConfiguration(vcr); }
/*     */ 
/*     */   
/*     */   public VCR toVCR(List cfgs) {
/* 219 */     return this.vcDelegate.toVCR(cfgs);
/*     */   }
/*     */   
/*     */   public Collection getConfigurations_Legacy() {
/* 223 */     return this.vcDelegate.getConfigurations_Legacy();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\v2\LVCAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */