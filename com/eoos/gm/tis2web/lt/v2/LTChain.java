/*     */ package com.eoos.gm.tis2web.lt.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.W100000Handler;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter.AWKInterpreter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Serviceplan;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class LTChain
/*     */   implements LT
/*     */ {
/*     */   private Collection delegates;
/*     */   
/*     */   public LTChain(Collection delegates) {
/*  29 */     this.delegates = delegates;
/*     */   }
/*     */   
/*     */   public String findCacheTextElement(Integer lc, String element) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String findOperatorCacheElement(Integer lcode, char op) {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String formatAW(Locale locale, boolean useLTHours, Integer lc, String algoCode, Integer awValue, Integer count, W100000Handler handler, boolean doCorrection) {
/*  41 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void formatAWs(Locale locale, boolean useLTHours, Integer lc, LTDataWork ltwork, W100000Handler handler) {
/*  45 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAWUnit(boolean useLTHours, Integer lc) {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getAddOnWorks(Integer lc, Integer mc, String mainWorkNo) {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getCheckListAttributes(String country, String model, String modelYear, String engine, String majorOperation) {
/*  57 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getCheckListEngines(String country, String model, String modelYear, String majorOperation) {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Integer getCheckListID(String country, String model, String modelYear, String engine, String majorOperation) {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getCheckListModelYears(String country, String model, String engine, String majorOperation) {
/*  69 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set getConfigurations() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public AWBlob getDocument(int sio, Integer lc) {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public AWBlob getGraphic(int sioID) {
/*  81 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public LTLanguageContext getLanguageContext(Integer langID) {
/*  85 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Integer getLc(LocaleInfo localeInfo) {
/*  89 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public LTDataWork getMainWork(Integer lc, Integer smc, Integer mc, String workNo) {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Integer getMc(Integer smc, String vcModel) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SITOCElement getSITOCElement(int sioID) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getServiceTypeXSL(Locale locale, String majorOperation) throws Exception {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Serviceplan getServiceplan(Locale locale, Integer clid, String majorOperation, String make, String model, String modelYear, String engine, String transmission, String vin) throws Exception {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Integer getSmc(String vcSalesmake) {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Collection getVersionInfo() {
/* 117 */     Collection ret = new LinkedList();
/* 118 */     for (Iterator<LTDataAdapter> iter = this.delegates.iterator(); iter.hasNext();) {
/* 119 */       ret.addAll(((LTDataAdapter)iter.next()).getLT().getVersionInfo());
/*     */     }
/* 121 */     return ret;
/*     */   }
/*     */   
/*     */   public Integer getW100000AW(Integer mc) {
/* 125 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SITOCElement make(CTOCType contentType, int contentID, int order, long labelID, VCR vcr) {
/* 129 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void register(CTOC ctoc) {
/* 133 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void removeNotValidSXAWData(LTDataWork w, AWKInterpreter i) {
/* 137 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 141 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 145 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set<String> getSupportedWinLangs() {
/* 149 */     Set<String> result = new HashSet<String>();
/* 150 */     for (Iterator<LTDataAdapter> iter = this.delegates.iterator(); iter.hasNext();) {
/* 151 */       result.addAll(((LTDataAdapter)iter.next()).getLT().getSupportedWinLangs());
/*     */     }
/* 153 */     return result;
/*     */   }
/*     */   
/*     */   public Set<String> getWinLanguagesTable() {
/* 157 */     Set<String> result = new HashSet<String>();
/* 158 */     for (Iterator<LTDataAdapter> iter = this.delegates.iterator(); iter.hasNext();) {
/* 159 */       result.addAll(((LTDataAdapter)iter.next()).getLT().getWinLanguagesTable());
/*     */     }
/* 161 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */