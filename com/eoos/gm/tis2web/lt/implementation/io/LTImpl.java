/*     */ package com.eoos.gm.tis2web.lt.implementation.io;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.W100000Handler;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter.AWKInterpreter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.DBMS;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.ILTCache;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.LTCache;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.ICL;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Serviceplan;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LTImpl
/*     */   implements LT {
/*  32 */   private static final Logger log = Logger.getLogger(LTImpl.class);
/*     */   
/*     */   private ILTCache cache;
/*     */   
/*  36 */   private ICL icl = null;
/*     */   
/*     */   public LTImpl(IDatabaseLink ltdb, IDatabaseLink icldb, ILVCAdapter.Retrieval lvcr) throws Exception {
/*  39 */     this.cache = (ILTCache)new LTCache(new DBMS(ltdb), lvcr);
/*     */     try {
/*  41 */       this.icl = new ICL(icldb);
/*  42 */     } catch (Exception e) {
/*  43 */       log.error("unable to init icl - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String findCacheTextElement(Integer lc, String element) {
/*  49 */     return this.cache.findCacheTextElement(lc, element);
/*     */   }
/*     */   
/*     */   public String findOperatorCacheElement(Integer lcode, char op) {
/*  53 */     return this.cache.findOperatorCacheElement(lcode, op);
/*     */   }
/*     */   
/*     */   public String formatAW(Locale locale, boolean useLTHours, Integer lc, String algoCode, Integer awValue, Integer count, W100000Handler handler, boolean doCorrection) {
/*  57 */     return this.cache.formatAW(locale, useLTHours, lc, algoCode, awValue, count, handler, doCorrection);
/*     */   }
/*     */   
/*     */   public void formatAWs(Locale locale, boolean useLTHours, Integer lc, LTDataWork ltwork, W100000Handler handler) {
/*  61 */     this.cache.formatAWs(locale, useLTHours, lc, ltwork, handler);
/*     */   }
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
/*     */   public List getAddOnWorks(Integer lc, Integer mc, String mainWorkNo) {
/*  82 */     return this.cache.getAddOnWorks(lc, mc, mainWorkNo);
/*     */   }
/*     */   
/*     */   public String getAWUnit(boolean useLTHours, Integer lc) {
/*  86 */     return this.cache.getAWUnit(useLTHours, lc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCheckListAttributes(String country, String model, String modelYear, String engine, String majorOperation) {
/*  95 */     if (this.icl != null) {
/*  96 */       return this.icl.getCheckListAttributes(country, model, modelYear, engine, majorOperation);
/*     */     }
/*  98 */     return Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCheckListEngines(String country, String model, String modelYear, String majorOperation) {
/* 104 */     if (this.icl != null) {
/* 105 */       return this.icl.getCheckListEngines(country, model, modelYear, majorOperation);
/*     */     }
/* 107 */     return Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getCheckListID(String country, String model, String modelYear, String engine, String majorOperation) {
/* 112 */     if (this.icl != null) {
/* 113 */       return this.icl.getCheckListID(country, model, modelYear, engine, majorOperation);
/*     */     }
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCheckListModelYears(String country, String model, String engine, String majorOperation) {
/* 121 */     if (this.icl != null) {
/* 122 */       return this.icl.getCheckListModelYears(country, model, engine, majorOperation);
/*     */     }
/* 124 */     return Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public AWBlob getDocument(int sio, Integer lc) {
/* 129 */     return this.cache.getDocument(sio, lc);
/*     */   }
/*     */   
/*     */   public CTOCFactory getFactory() {
/* 133 */     return (CTOCFactory)this.cache;
/*     */   }
/*     */   
/*     */   public AWBlob getGraphic(int sioID) {
/* 137 */     return this.cache.getGraphic(sioID);
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 141 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */   
/*     */   public LTLanguageContext getLanguageContext(Integer langID) {
/* 145 */     return this.cache.getLanguageContext(langID);
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 149 */     return 0L;
/*     */   }
/*     */   
/*     */   public Integer getLc(LocaleInfo localeInfo) {
/* 153 */     return this.cache.getLc(localeInfo);
/*     */   }
/*     */   
/*     */   public ILTCache getLTCache() {
/* 157 */     return this.cache;
/*     */   }
/*     */   
/*     */   public LTDataWork getMainWork(Integer lc, Integer smc, Integer mc, String workNo) {
/* 161 */     return this.cache.getMainWork(lc, smc, mc, workNo);
/*     */   }
/*     */   
/*     */   public Integer getMc(Integer smc, String vcModel) {
/* 165 */     return this.cache.getMc(smc, vcModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serviceplan getServiceplan(Locale locale, Integer clid, String majorOperation, String make, String model, String modelYear, String engine, String transmission, String vin) throws Exception {
/* 175 */     if (this.icl != null) {
/* 176 */       return this.icl.getServiceplan(locale, clid, majorOperation, make, model, modelYear, engine, transmission, vin);
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServiceTypeXSL(Locale locale, String majorOperation) throws Exception {
/* 184 */     if (this.icl != null) {
/* 185 */       return this.icl.getServiceTypeXSL(locale, majorOperation);
/*     */     }
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SITOCElement getSITOCElement(int sioID) {
/* 192 */     return this.cache.getSITOCElement(sioID);
/*     */   }
/*     */   
/*     */   public Integer getSmc(String vcSalesmake) {
/* 196 */     return this.cache.getSmc(vcSalesmake);
/*     */   }
/*     */   
/*     */   public Collection getVersionInfo() {
/* 200 */     return Collections.singleton(this.cache.getVersionInfo());
/*     */   }
/*     */   
/*     */   public Integer getW100000AW(Integer mc) {
/* 204 */     return this.cache.getW100000AW(mc);
/*     */   }
/*     */   
/*     */   public SITOCElement make(CTOCType contentType, int contentID, int order, long labelID, VCR vcr) {
/* 208 */     return this.cache.make(contentType, contentID, order, labelID, vcr);
/*     */   }
/*     */   
/*     */   public void register(CTOC ctoc) {
/* 212 */     this.cache.register(ctoc);
/*     */   }
/*     */   
/*     */   public void removeNotValidSXAWData(LTDataWork w, AWKInterpreter i) {
/* 216 */     this.cache.removeNotValidSXAWData(w, i);
/*     */   }
/*     */   
/*     */   public LT.Retrieval createRetrievalImpl() {
/* 220 */     return (LT.Retrieval)new LT.Retrieval.RI(this);
/*     */   }
/*     */   
/*     */   public Set<String> getSupportedWinLangs() {
/* 224 */     return this.cache.getSupportedWinLangs();
/*     */   }
/*     */   
/*     */   public Set<String> getWinLanguagesTable() {
/* 228 */     return this.cache.getWinLanguagesTable();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\LTImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */