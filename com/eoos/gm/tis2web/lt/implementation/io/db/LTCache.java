/*     */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWFormatter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWFormula;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWHourFormula;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKElement;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKSchluessel;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.OperatorCacheElement;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.W100000Handler;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter.AWKInterpreter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.Solver;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.TriStateLogic;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class LTCache
/*     */   implements CTOCFactory, ILTCache
/*     */ {
/*  42 */   protected static Logger log = Logger.getLogger(LTCache.class);
/*     */   
/*  44 */   protected static AWHourFormula AWHOUR_FORMULA = new AWHourFormula();
/*     */   
/*  46 */   protected Map sios = new ConcurrentHashMap<Object, Object>();
/*     */   
/*     */   protected ILTStore store;
/*     */   
/*     */   protected CTOC ctoc;
/*     */   
/*  52 */   private Map<String, Object> langMap = null;
/*     */   
/*  54 */   private Map SMCMap = null;
/*     */   
/*  56 */   private Map SMC2MCMap = null;
/*     */   
/*  58 */   private Map MC2W100000AW = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  60 */   private Map MWorks = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  62 */   private Map AWorks = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  64 */   private Map langCtxMap = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  66 */   private AWFormatter formatter = new AWFormatter(1L);
/*     */ 
/*     */   
/*  69 */   private Map operatorcache = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  71 */   private Map textcache = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  73 */   private Map schluesselcache = new ConcurrentHashMap<Object, Object>(); private ILVCAdapter.Retrieval lvcr; private LockObjectProvider lockProvider; private final Object SYNC_LANGCTX;
/*     */   
/*     */   public DBVersionInformation getVersionInfo() {
/*  76 */     return this.store.getVersionInfo();
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
/*     */   public void register(CTOC ctoc) {
/*  91 */     this.ctoc = ctoc;
/*     */   }
/*     */   
/*     */   private void init() {
/*  95 */     log.info("LTCache: loading cache");
/*     */     
/*  97 */     this.langMap = this.store.loadLangMap();
/*  98 */     this.SMCMap = this.store.loadSmcMap();
/*  99 */     this.SMC2MCMap = this.store.loadSmcMcMap();
/*     */ 
/*     */     
/* 102 */     LTAWKSchluessel.connect2Cache(this);
/* 103 */     LTAWKElement.connect2Cache(this);
/* 104 */     Collection vals = this.langMap.values();
/* 105 */     for (Iterator<Integer> it = vals.iterator(); it.hasNext();) {
/* 106 */       this.store.loadOperatorCache(it.next(), this.operatorcache, this.textcache);
/*     */     }
/* 108 */     log.info("LTCache: done loading cache");
/*     */   }
/*     */ 
/*     */   
/*     */   public String findOperatorCacheElement(Integer lcode, char op) {
/* 113 */     String ret = null;
/*     */ 
/*     */     
/* 116 */     List ocList = (List)this.operatorcache.get(lcode);
/* 117 */     if (ocList != null) {
/* 118 */       Iterator<OperatorCacheElement> it = ocList.iterator();
/* 119 */       OperatorCacheElement oce = null;
/* 120 */       while (it.hasNext()) {
/* 121 */         oce = it.next();
/* 122 */         if (oce.getOp() == op) {
/* 123 */           ret = oce.getText();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 128 */     if (ret == null) {
/* 129 */       return "";
/*     */     }
/* 131 */     return ret;
/*     */   }
/*     */   
/*     */   public String findCacheTextElement(Integer lc, String element) {
/* 135 */     PairImpl pairImpl = new PairImpl(lc, element);
/* 136 */     String ret = (String)this.textcache.get(pairImpl);
/* 137 */     if (ret == null) {
/* 138 */       return "";
/*     */     }
/* 140 */     return ret;
/*     */   }
/*     */   
/*     */   public Integer getW100000AW(Integer mc) {
/* 144 */     if (this.MC2W100000AW.containsKey(mc)) {
/* 145 */       return (Integer)this.MC2W100000AW.get(mc);
/*     */     }
/* 147 */     Integer iAW = this.store.getW100000AW(mc.intValue());
/* 148 */     if (iAW != null) {
/* 149 */       this.MC2W100000AW.put(mc, iAW);
/*     */     }
/* 151 */     return iAW;
/*     */   }
/*     */   
/*     */   public SITOCElement make(CTOCType contentType, int contentID, int order, long labelID, VCR vcr) {
/*     */     SIOLTElement sIOLTElement;
/* 156 */     SITOCElement sio = null;
/* 157 */     if (contentType == CTOCType.MajorOperation) {
/* 158 */       Integer sioID = Integer.valueOf(contentID);
/* 159 */       sio = (SITOCElement)this.sios.get(sioID);
/* 160 */       if (sio != null) {
/* 161 */         return sio;
/*     */       }
/* 163 */       sIOLTElement = new SIOLTElement(sioID, order, (int)labelID, vcr, this.lvcr.getLVCAdapter());
/* 164 */     } else if (contentType != CTOCType.SI) {
/*     */ 
/*     */       
/* 167 */       throw new IllegalArgumentException();
/*     */     } 
/* 169 */     return (SITOCElement)sIOLTElement;
/*     */   }
/*     */   
/*     */   public SITOCElement getSITOCElement(int sioID) {
/* 173 */     return (SITOCElement)this.ctoc.lookupMO(Integer.valueOf(sioID));
/*     */   }
/*     */   
/*     */   public Integer getLc(LocaleInfo localeInfo) {
/* 177 */     Integer lc = null;
/* 178 */     List flc = localeInfo.getLocaleFLC(LGSIT.LT);
/* 179 */     String tla = localeInfo.getLocaleTLA();
/* 180 */     Iterator<Integer> it = flc.iterator();
/* 181 */     while ((lc = (Integer)this.langMap.get(tla)) == null && it.hasNext()) {
/* 182 */       tla = LocaleInfoProvider.getInstance().getLocale(it.next()).getLocaleTLA();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (lc == null) {
/* 188 */       lc = Integer.valueOf(-1);
/*     */     }
/* 190 */     return lc;
/*     */   }
/*     */   
/*     */   public Integer getSmc(String vcSalesmake) {
/* 194 */     Integer ismc = (Integer)this.SMCMap.get(vcSalesmake.toLowerCase(Locale.ENGLISH));
/* 195 */     if (ismc != null) {
/* 196 */       return ismc;
/*     */     }
/* 198 */     return Integer.valueOf(-1);
/*     */   }
/*     */   
/*     */   public Integer getMc(Integer smc, String vcModel) {
/* 202 */     String lookup = smc.toString();
/* 203 */     lookup = lookup + vcModel.toLowerCase(Locale.ENGLISH);
/* 204 */     Integer mc = (Integer)this.SMC2MCMap.get(lookup);
/* 205 */     if (mc != null) {
/* 206 */       return mc;
/*     */     }
/* 208 */     return Integer.valueOf(-1);
/*     */   }
/*     */   
/*     */   public List getAddOnWorks(Integer lc, Integer mc, String mainWorkNo) {
/* 212 */     String oKey = mainWorkNo + "-" + lc.toString() + "-" + mc.toString();
/* 213 */     synchronized (this.lockProvider.getLockObject(oKey)) {
/* 214 */       List oW = (List)this.AWorks.get(oKey);
/* 215 */       if (oW == null) {
/* 216 */         oW = this.store.getAddOnWorks(lc, mc, mainWorkNo);
/* 217 */         if (oW != null) {
/* 218 */           this.AWorks.put(oKey, oW);
/*     */         }
/* 220 */         LoadAddOnWorkData(oW, lc);
/*     */       } 
/*     */       
/* 223 */       return oW;
/*     */     } 
/*     */   }
/*     */   
/* 227 */   public LTCache(DBMS dbms, ILVCAdapter.Retrieval lvcr) throws Exception { this.lockProvider = new LockObjectProvider();
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
/* 320 */     this.SYNC_LANGCTX = new Object(); this.store = LTStore.create(dbms); this.lvcr = lvcr; init(); }
/*     */   public LTDataWork getMainWork(Integer lc, Integer smc, Integer mc, String workNo) { String oKey = workNo + "-" + lc.toString() + "-" + mc.toString(); synchronized (this.lockProvider.getLockObject(oKey)) { LTDataWork oW = (LTDataWork)this.MWorks.get(oKey); if (oW == null) { oW = this.store.getMainWork(lc, smc, mc, workNo); if (oW != null) { this.MWorks.put(oKey, oW); LoadMainWorkData(oW, lc); }  }  return oW; }  }
/*     */   public void formatAWs(Locale locale, boolean useLTHours, Integer lc, List ltwork, W100000Handler handler) { for (Iterator<LTDataWork> it = ltwork.iterator(); it.hasNext();) formatAWs(locale, useLTHours, lc, it.next(), handler);  }
/* 323 */   public String getAWUnit(boolean useLTHours, Integer lc) { LTLanguageContext langCtx = getLanguageContext(lc); return useLTHours ? langCtx.getAwHourUnit() : langCtx.getAwUnit(); } public LTLanguageContext getLanguageContext(Integer langID) { synchronized (this.SYNC_LANGCTX)
/* 324 */     { LTLanguageContext langCtx = (LTLanguageContext)this.langCtxMap.get(langID);
/* 325 */       if (langCtx == null) {
/* 326 */         langCtx = new LTLanguageContext();
/* 327 */         langCtx.setLangID(langID);
/* 328 */         this.store.fillLangContext(langCtx);
/* 329 */         if (langCtx.getAwHourUnit() == null) {
/* 330 */           this.store.fillLangContextHourLabel(langCtx);
/*     */         }
/* 332 */         if (langCtx != null) {
/* 333 */           this.langCtxMap.put(langID, langCtx);
/*     */         }
/*     */       } 
/* 336 */       return langCtx; }  }
/*     */   protected AWFormula determineAWFormula(boolean useLTHours, LTLanguageContext langCtx) { return useLTHours ? (AWFormula)AWHOUR_FORMULA : langCtx.getFormula(); }
/*     */   public String formatAW(Locale locale, boolean useLTHours, Integer lc, String algoCode, Integer awValue, Integer iCount, W100000Handler handler, boolean doCorrection) { LTLanguageContext langCtx = getLanguageContext(lc); AWFormula usedFormula = determineAWFormula(useLTHours, langCtx); AWFormatter format = new AWFormatter(iCount.intValue()); return format.format(locale, handler, algoCode, usedFormula, awValue.doubleValue(), doCorrection); }
/*     */   public String formatAW(Locale locale, W100000Handler handler, boolean useLTHours, Integer lc, String algoCode, double awFloat, boolean bcorrect) { LTLanguageContext langCtx = getLanguageContext(lc); return this.formatter.format(locale, handler, algoCode, determineAWFormula(useLTHours, langCtx), awFloat, bcorrect); }
/*     */   public void formatAWs(Locale locale, boolean useLTHours, Integer lc, LTDataWork ltwork, W100000Handler handler) { double awfloat = 0.0D; boolean noNumber = false; List sxAWList = ltwork.getSXAWList(); if (sxAWList == null) return;  String algoCode = ltwork.getAlgoCode(); if (algoCode == null) algoCode = "A";  LTLanguageContext langCtx = getLanguageContext(lc); AWFormula usedFormula = determineAWFormula(useLTHours, langCtx); String aw = null; String convert = null; LTSXAWData sx = null; Iterator<LTSXAWData> it = sxAWList.iterator(); while (it.hasNext()) { sx = it.next(); if (sx.getAwFormatted() != null)
/* 341 */         continue;  aw = sx.getAw(); if (aw != null && aw.length() > 0) { try { awfloat = Double.parseDouble(aw); } catch (NumberFormatException nfe) { awfloat = 0.0D; noNumber = true; }  if (awfloat != 0.0D) { if ((ltwork.getTasktype() == 5 || ltwork.getTasktype() == 1) && !ltwork.getNr().equals("W100000")) { convert = this.formatter.format(locale, handler, algoCode, usedFormula, awfloat, true); } else { convert = this.formatter.format(locale, handler, algoCode, usedFormula, awfloat, false); }  } else if (noNumber) { convert = aw; }  } else { convert = ""; }  sx.setAwFormatted(convert); }  } private void LoadAddOnWorkData(List oW, Integer lc) { this.store.fillAddonworkSXAW(lc, oW, this.schluesselcache);
/*     */     
/* 343 */     for (Iterator<LTDataWork> it = oW.iterator(); it.hasNext();) {
/* 344 */       this.store.fillAddonworkMultiText(lc, it.next());
/*     */     } }
/*     */ 
/*     */   
/*     */   private void LoadMainWorkData(LTDataWork oW, Integer lc) {
/* 349 */     this.store.fillMainworkMultiText(lc, oW);
/* 350 */     this.store.fillMainworkTC(lc, oW);
/* 351 */     this.store.fillMainworkSXAW(lc, oW, this.schluesselcache);
/*     */   }
/*     */   
/*     */   public AWBlob getGraphic(int sioID) {
/*     */     try {
/* 356 */       return this.store.loadGraphic(sioID);
/* 357 */     } catch (Exception e) {
/*     */       
/* 359 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AWBlob getDocument(int sio, Integer lc) {
/*     */     try {
/* 365 */       return this.store.loadDocument(sio, lc);
/* 366 */     } catch (Exception e) {
/* 367 */       log.error("Document for sioid " + Integer.valueOf(sio).toString() + " does not exist");
/* 368 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeNotValidSXAWData(LTDataWork w, AWKInterpreter i) {
/* 373 */     if (w.getSXAWList() == null) {
/*     */       return;
/*     */     }
/* 376 */     for (Iterator<LTSXAWData> it = w.getSXAWList().iterator(); it.hasNext(); ) {
/* 377 */       LTSXAWData d = it.next();
/* 378 */       if (d.getAWKSchluessel() != null) {
/* 379 */         Solver solver = i.Interpret(d.getAWKSchluessel().getQualList());
/* 380 */         if (solver != null && 
/* 381 */           solver.apply().equals(TriStateLogic.FALSE)) {
/* 382 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedWinLangs() {
/* 390 */     return this.langMap.keySet();
/*     */   }
/*     */   
/*     */   public Set<String> getWinLanguagesTable() {
/* 394 */     return this.store.getWinLanguages();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\LTCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */