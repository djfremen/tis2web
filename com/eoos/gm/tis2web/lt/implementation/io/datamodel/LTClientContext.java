/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.IndeterminableVCRException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter.AWKInterpreter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTClientContext
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(LTClientContext.class);
/*     */ 
/*     */   
/*  42 */   private Integer LC = Integer.valueOf(-1);
/*     */ 
/*     */ 
/*     */   
/*  46 */   private Integer modelCode = Integer.valueOf(-1);
/*  47 */   private Integer smc = Integer.valueOf(-1);
/*     */   
/*  49 */   private LTVCContext vccontext = new LTVCContext();
/*     */ 
/*     */   
/*  52 */   private LocaleInfo olc = LocaleInfoProvider.getInstance().getLocale("en_GB");
/*     */ 
/*     */   
/*     */   private boolean isGermany = false;
/*     */   
/*     */   private boolean useLTHours = false;
/*     */   
/*  59 */   private Map mnr2MainWork = new HashMap<Object, Object>();
/*     */   
/*  61 */   private Map mnr2MainAddWorks = new HashMap<Object, Object>();
/*     */   
/*  63 */   private Map mnr2AddWorks = new HashMap<Object, Object>();
/*     */   
/*  65 */   private Set mWorksformatted = new HashSet();
/*     */   
/*  67 */   private W100000Handler handler = null;
/*     */   
/*  69 */   private AWKInterpreter interpreter = null;
/*     */   
/*  71 */   private LTSelectionLists selection = new LTSelectionLists();
/*     */   
/*  73 */   private List observers = new LinkedList();
/*     */ 
/*     */   
/*     */   private LT lt;
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private synchronized void notifyObservers(boolean bNewMC) {
/*  82 */     Iterator<LTClientContextObserver> iter = this.observers.iterator();
/*  83 */     while (iter.hasNext()) {
/*  84 */       ((LTClientContextObserver)iter.next()).onVehicleChange(bNewMC);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void addObserver(LTClientContextObserver observer) {
/*  89 */     this.observers.add(observer);
/*     */   }
/*     */   
/*     */   public synchronized void remObserver(LTClientContextObserver observer) {
/*  93 */     this.observers.remove(observer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LTClientContext(final ClientContext context) {
/*  99 */     this.context = context;
/* 100 */     this.interpreter = new AWKInterpreter(this.vccontext);
/* 101 */     if (SharedContext.getInstance(context).useLTHours() != null) {
/* 102 */       this.useLTHours = SharedContext.getInstance(context).useLTHours().booleanValue();
/*     */     }
/* 104 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange()
/*     */           {
/* 108 */             LTClientContext.this.synchronizeContents(VCFacade.getInstance(context).getCfg(), null);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 113 */     synchronizeContents(VCFacade.getInstance(context).getCfg(), SharedContext.getInstance(context).getCountry());
/* 114 */     clearCache();
/*     */   }
/*     */   
/*     */   public LTSelectionLists getSelection() {
/* 118 */     return this.selection;
/*     */   } public static interface LTClientContextObserver {
/*     */     void onVehicleChange(boolean param1Boolean); }
/*     */   public LocaleInfo getLocale() {
/* 122 */     return this.olc;
/*     */   }
/*     */   
/*     */   private void synchronizeContents(IConfiguration vc, String country) {
/* 126 */     this.lt = LTDataAdapterFacade.getInstance(this.context).getLT();
/*     */     
/* 128 */     boolean newModelCode = false;
/* 129 */     LocaleInfo currentLocale = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 130 */     if (country != null) {
/* 131 */       boolean bNew = country.equals("DE");
/* 132 */       if (this.handler != null && this.isGermany != bNew) {
/* 133 */         clearCache();
/* 134 */         newModelCode = true;
/* 135 */         this.handler.setGermany(bNew);
/*     */       } 
/* 137 */       this.isGermany = bNew;
/*     */     } 
/*     */     
/* 140 */     if (!currentLocale.getLocale().equals(this.olc.getLocale()) || this.LC.intValue() == -1) {
/* 141 */       this.olc = currentLocale;
/* 142 */       this.LC = this.lt.getLc(this.olc);
/* 143 */       clearCache();
/* 144 */       newModelCode = true;
/*     */     } 
/*     */     
/* 147 */     if (VehicleConfigurationUtil.isEmptyCfg(vc)) {
/* 148 */       this.modelCode = Integer.valueOf(-1);
/* 149 */       this.vccontext.reset();
/* 150 */       notifyObservers(false);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 156 */       if (!VehicleConfigurationUtil.cfgUtil.equals(this.vccontext.getCfg(), vc)) {
/* 157 */         Integer iMSave = this.modelCode;
/* 158 */         setVCR();
/*     */         
/* 160 */         if (!iMSave.equals(this.modelCode)) {
/*     */           
/* 162 */           newModelCode = true;
/* 163 */           this.handler = new W100000Handler(this.lt.getW100000AW(this.modelCode), this.isGermany);
/* 164 */           clearCache();
/*     */         } else {
/* 166 */           clearCache(true);
/* 167 */           this.selection.validate(this);
/*     */         } 
/*     */       } 
/* 170 */     } catch (IndeterminableVCRException e) {
/* 171 */       log.warn("unable to determine vcr change, ignoring");
/*     */     } 
/* 173 */     notifyObservers(newModelCode);
/*     */   }
/*     */   
/*     */   public LTDataWork getW100000AW(Integer iCorrCount) {
/* 177 */     LTDataWork work = getMainWork("W100000", false);
/* 178 */     work = (LTDataWork)work.clone();
/*     */     
/* 180 */     if (work.getSXAWList() == null || work.getSXAWList().size() == 0) {
/* 181 */       return work;
/*     */     }
/* 183 */     String algoCode = work.getAlgoCode();
/* 184 */     if (algoCode == null) {
/* 185 */       algoCode = "A";
/*     */     }
/* 187 */     LTSXAWData aw = work.getSXAWList().get(0);
/* 188 */     Integer iValue = null;
/* 189 */     String awF = null;
/*     */     try {
/* 191 */       iValue = Integer.decode(aw.getAw());
/*     */ 
/*     */ 
/*     */       
/* 195 */       if (SharedContext.getInstance(this.context).useLTHours() != null) {
/* 196 */         this.useLTHours = SharedContext.getInstance(this.context).useLTHours().booleanValue();
/*     */       }
/* 198 */       awF = this.lt.formatAW(this.context.getLocale(), this.useLTHours, this.LC, algoCode, iValue, iCorrCount, this.handler, false);
/* 199 */     } catch (NumberFormatException nfe) {
/* 200 */       return work;
/*     */     } 
/* 202 */     aw.setAwFormatted((awF != null) ? awF : "");
/* 203 */     work.setDescription(work.getDescription() + " (" + iCorrCount.toString() + ")");
/*     */ 
/*     */ 
/*     */     
/* 207 */     return work;
/*     */   }
/*     */   
/*     */   public W100000Handler getHandler() {
/* 211 */     return this.handler;
/*     */   }
/*     */   
/*     */   public static LTClientContext getInstance(ClientContext context) {
/* 215 */     synchronized (context.getLockObject()) {
/* 216 */       LTClientContext instance = (LTClientContext)context.getObject(LTClientContext.class);
/* 217 */       if (instance == null) {
/* 218 */         instance = new LTClientContext(context);
/* 219 */         context.storeObject(LTClientContext.class, instance);
/*     */       } 
/* 221 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearCache() {
/* 226 */     clearCache(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCache(boolean bKeepSelection) {
/* 231 */     this.mnr2MainWork.clear();
/* 232 */     this.mnr2AddWorks.clear();
/* 233 */     this.mWorksformatted.clear();
/* 234 */     if (!bKeepSelection) {
/* 235 */       this.selection.clear();
/*     */     }
/* 237 */     this.mnr2MainAddWorks.clear();
/*     */   }
/*     */   
/*     */   public LTVCContext getVCContext() {
/* 241 */     return this.vccontext;
/*     */   }
/*     */   
/*     */   public VCR getVCRObject() {
/* 245 */     ILVCAdapter lvc = LTDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/* 246 */     return lvc.toVCR(VCFacade.getInstance(this.context).getCfg());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVCR() throws IndeterminableVCRException {
/* 257 */     this.vccontext.set(this.context);
/* 258 */     this.smc = this.lt.getSmc(this.vccontext.getSalesMake());
/* 259 */     this.modelCode = this.lt.getMc(this.smc, this.vccontext.getModel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLC() {
/* 268 */     return this.LC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getModelCode() {
/* 277 */     return this.modelCode;
/*     */   }
/*     */   
/*     */   public boolean isMainWorkValid(String oNr) {
/* 281 */     LTDataWork oWork = getMainWork(oNr, false);
/*     */     
/* 283 */     if (oWork != null) {
/* 284 */       if (!Util.isNullOrEmpty(oWork.getSXAWList())) {
/* 285 */         return true;
/*     */       }
/* 287 */       if (oWork.getTasktype() == 1) {
/* 288 */         List l = getMainWorks(oWork.getNr());
/* 289 */         if (l != null && l.size() > 0) {
/* 290 */           for (Iterator<LTDataWork> itW = l.iterator(); itW.hasNext(); ) {
/* 291 */             LTDataWork work = itW.next();
/* 292 */             if (!Util.isNullOrEmpty(work.getSXAWList())) {
/* 293 */               return true;
/*     */             }
/*     */           } 
/* 296 */           return false;
/*     */         } 
/* 298 */         return false;
/*     */       } 
/*     */       
/* 301 */       return false;
/*     */     } 
/*     */     
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void formatAWs(List l) {
/* 310 */     if (SharedContext.getInstance(this.context).useLTHours() != null) {
/* 311 */       this.useLTHours = SharedContext.getInstance(this.context).useLTHours().booleanValue();
/*     */     }
/* 313 */     for (Iterator<LTDataWork> it = l.iterator(); it.hasNext();) {
/* 314 */       formatAWs(this.context.getLocale(), this.useLTHours, it.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void formatAWs(Locale locale, boolean useLTHours, LTDataWork oW) {
/* 319 */     if (!this.mWorksformatted.contains(oW.getNr())) {
/* 320 */       this.lt.formatAWs(locale, useLTHours, this.LC, oW, this.handler);
/* 321 */       this.mWorksformatted.add(oW.getNr());
/*     */     } 
/*     */   }
/*     */   
/*     */   public LTDataWork getMainWork(String oNr, boolean bFormatAWs) {
/* 326 */     LTDataWork oW = (LTDataWork)this.mnr2MainWork.get(oNr);
/* 327 */     if (oW == null) {
/* 328 */       oW = this.lt.getMainWork(this.LC, this.smc, this.modelCode, oNr);
/* 329 */       if (oW != null) {
/* 330 */         oW = oW.doCloneForSXAWData();
/*     */ 
/*     */         
/* 333 */         if (oW.needsValidation()) {
/* 334 */           this.lt.removeNotValidSXAWData(oW, this.interpreter);
/*     */         }
/* 336 */         this.mnr2MainWork.put(oNr, oW);
/*     */       } 
/*     */     } 
/* 339 */     if (bFormatAWs && oW != null) {
/* 340 */       if (SharedContext.getInstance(this.context).useLTHours() != null) {
/* 341 */         this.useLTHours = SharedContext.getInstance(this.context).useLTHours().booleanValue();
/*     */       }
/* 343 */       formatAWs(this.context.getLocale(), this.useLTHours, oW);
/*     */     } 
/* 345 */     return oW;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getMainWorks(String oMainWorkNr) {
/* 350 */     if (SharedContext.getInstance(this.context).useLTHours() != null) {
/* 351 */       this.useLTHours = SharedContext.getInstance(this.context).useLTHours().booleanValue();
/*     */     }
/* 353 */     List<LTDataWork> oW = (List)this.mnr2MainAddWorks.get(oMainWorkNr);
/* 354 */     if (oW == null) {
/* 355 */       oW = this.lt.getAddOnWorks(this.LC, this.modelCode, oMainWorkNr);
/* 356 */       if (oW != null) {
/* 357 */         oW = cloneLTDataWorkListforSXAW(oW);
/*     */         
/* 359 */         LTDataWork mainWork = (LTDataWork)this.mnr2MainWork.get(oMainWorkNr);
/*     */         
/* 361 */         List<LTDataWork> oMWs = null;
/* 362 */         for (Iterator<LTDataWork> itW = oW.iterator(); itW.hasNext(); ) {
/* 363 */           LTDataWork work = itW.next();
/*     */           
/* 365 */           work.setAlgoCode(mainWork.getAlgoCode());
/*     */           
/* 367 */           if (work.needsValidation()) {
/* 368 */             this.lt.removeNotValidSXAWData(work, this.interpreter);
/*     */           }
/* 370 */           if (work.getSXAWList() == null || work.getSXAWList().size() == 0) {
/* 371 */             itW.remove();
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 376 */           if (work.getTasktype() == 5 || work.getTasktype() == 2) {
/* 377 */             if (oMWs == null) {
/* 378 */               oMWs = new LinkedList();
/*     */             }
/* 380 */             oMWs.add(work);
/* 381 */             if (work.needsValidation()) {
/* 382 */               this.lt.removeNotValidSXAWData(work, this.interpreter);
/*     */             }
/* 384 */             itW.remove();
/*     */           } 
/* 386 */           formatAWs(this.context.getLocale(), this.useLTHours, work);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 393 */         this.mnr2AddWorks.put(oMainWorkNr, oW);
/* 394 */         if (oMWs != null) {
/* 395 */           this.mnr2MainAddWorks.put(oMainWorkNr, oMWs);
/*     */         }
/* 397 */         oW = oMWs;
/*     */       } 
/*     */     } 
/* 400 */     return oW;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getAddOnWorks(String oMainWorkNr) {
/* 405 */     List oW = (List)this.mnr2AddWorks.get(oMainWorkNr);
/* 406 */     if (oW == null) {
/* 407 */       getMainWorks(oMainWorkNr);
/* 408 */       oW = (List)this.mnr2AddWorks.get(oMainWorkNr);
/*     */     } 
/* 410 */     return oW;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List cloneLTDataWorkListforSXAW(List oL) {
/* 416 */     List<LTDataWork> oLr = new ArrayList(oL.size());
/* 417 */     for (Iterator<LTDataWork> it = oL.iterator(); it.hasNext();) {
/* 418 */       oLr.add(((LTDataWork)it.next()).doCloneForSXAWData());
/*     */     }
/* 420 */     return oLr;
/*     */   }
/*     */   
/*     */   public String getAWUnit() {
/* 424 */     if (SharedContext.getInstance(this.context).useLTHours() != null) {
/* 425 */       this.useLTHours = SharedContext.getInstance(this.context).useLTHours().booleanValue();
/*     */     }
/* 427 */     return this.lt.getAWUnit(this.useLTHours, this.LC);
/*     */   }
/*     */   
/*     */   public String getAWSum(List lWorks) {
/* 431 */     double sum = 0.0D;
/* 432 */     for (Iterator<LTDataWork> it = lWorks.iterator(); it.hasNext(); ) {
/* 433 */       LTDataWork w = it.next();
/*     */       
/* 435 */       if (w.getSXAWList() != null) {
/* 436 */         for (Iterator<LTSXAWData> its = w.getSXAWList().iterator(); its.hasNext(); ) {
/* 437 */           LTSXAWData s = its.next();
/*     */           try {
/* 439 */             String ot = s.getAwFormatted().replace(',', '.');
/* 440 */             sum += (new Double(ot)).doubleValue();
/* 441 */           } catch (NumberFormatException e) {}
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 447 */     NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
/* 448 */     nf.setMaximumFractionDigits(1);
/* 449 */     nf.setMinimumFractionDigits(0);
/*     */     
/* 451 */     String strRet = nf.format(sum);
/* 452 */     boolean useHours = this.lt.getLanguageContext(this.LC).getFormula() instanceof AWHourFormula;
/* 453 */     if (!this.useLTHours && !useHours) {
/* 454 */       strRet = strRet.replace(".", ",");
/*     */     }
/* 456 */     return strRet;
/*     */   }
/*     */   
/*     */   public AWBlob getGraphic(int sioID) {
/* 460 */     return this.lt.getGraphic(sioID);
/*     */   }
/*     */   
/*     */   public AWBlob getDocument(int sioID) {
/* 464 */     return this.lt.getDocument(sioID, this.LC);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTClientContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */