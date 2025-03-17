/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.db;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.MajorOperation;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.VehicleCheckList;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CheckList;
/*     */ import java.util.HashMap;
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
/*     */ public class ICLCache
/*     */ {
/*  21 */   protected static final Logger log = Logger.getLogger(ICLCache.class);
/*     */   
/*  23 */   public static final Integer MULTIPLE_HITS = Integer.valueOf(0);
/*     */   
/*  25 */   public static final Integer REQUIRE_MODELYEAR = Integer.valueOf(-1);
/*     */   
/*  27 */   public static final Integer REQUIRE_ENGINE = Integer.valueOf(-2);
/*     */   
/*  29 */   public static final Integer REQUIRE_MODELYEAR_AND_ENGINE = Integer.valueOf(-3);
/*     */ 
/*     */   
/*     */   protected ICLStore store;
/*     */ 
/*     */   
/*     */   protected Map mConstants2Value;
/*     */ 
/*     */   
/*     */   protected Map mAttribute2Value;
/*     */ 
/*     */   
/*     */   protected Map mComment2Value;
/*     */ 
/*     */   
/*     */   protected Map mDriverType2Value;
/*     */   
/*     */   protected Map mServiceType2Value;
/*     */   
/*     */   protected Map mServiceTypeXSL;
/*     */   
/*     */   protected Map mLan2LID;
/*     */   
/*     */   protected Map mEngine2ID;
/*     */   
/*     */   protected Map mID2Engine;
/*     */   
/*     */   protected Map mModel2ID;
/*     */   
/*     */   protected Map mModelYear2ID;
/*     */   
/*     */   protected Map mID2ModelYear;
/*     */   
/*     */   Map mCountry2CountryGroup;
/*     */   
/*     */   protected Map mMajorOperation2Data;
/*     */   
/*     */   protected Map mMajorOperationID2Data;
/*     */   
/*     */   protected List lVehicle2Checklist;
/*     */ 
/*     */   
/*     */   public Set getModels() {
/*  72 */     return this.mModel2ID.keySet();
/*     */   }
/*     */   
/*     */   public Set getModelYears() {
/*  76 */     return this.mModelYear2ID.keySet();
/*     */   }
/*     */   
/*     */   public Set getEngines() {
/*  80 */     return this.mEngine2ID.keySet();
/*     */   }
/*     */   
/*     */   public ICLCache(DBMS dbms) {
/*  84 */     this.store = new ICLStore(dbms, this);
/*  85 */     this.mLan2LID = this.store.loadLanguages();
/*  86 */     this.mConstants2Value = this.store.loadConstants();
/*  87 */     this.mAttribute2Value = this.store.loadAttributes();
/*  88 */     this.mComment2Value = this.store.loadComments();
/*  89 */     this.mDriverType2Value = this.store.loadDriverTypes();
/*  90 */     this.mServiceType2Value = this.store.loadServiceTypes();
/*  91 */     this.mServiceTypeXSL = this.store.getServiceTypeXSL();
/*  92 */     this.mCountry2CountryGroup = this.store.loadCountryGroups();
/*  93 */     this.mEngine2ID = this.store.loadEngines();
/*  94 */     this.mID2Engine = new HashMap<Object, Object>();
/*  95 */     for (Iterator<String> iterator2 = this.mEngine2ID.keySet().iterator(); iterator2.hasNext(); ) {
/*  96 */       String engine = iterator2.next();
/*  97 */       this.mID2Engine.put((Integer)this.mEngine2ID.get(engine), engine);
/*     */     } 
/*  99 */     this.mModel2ID = this.store.loadModels();
/* 100 */     this.mModelYear2ID = this.store.loadModelYears();
/* 101 */     this.mID2ModelYear = new HashMap<Object, Object>();
/* 102 */     for (Iterator<String> iterator1 = this.mModelYear2ID.keySet().iterator(); iterator1.hasNext(); ) {
/* 103 */       String my = iterator1.next();
/* 104 */       this.mID2ModelYear.put((Integer)this.mModelYear2ID.get(my), my);
/*     */     } 
/* 106 */     this.mMajorOperation2Data = this.store.loadMajorOperations();
/* 107 */     this.mMajorOperationID2Data = new HashMap<Object, Object>();
/* 108 */     for (Iterator<MajorOperation> iter = this.mMajorOperation2Data.values().iterator(); iter.hasNext(); ) {
/* 109 */       MajorOperation mo = iter.next();
/* 110 */       this.mMajorOperationID2Data.put(mo.getID(), mo);
/*     */     } 
/* 112 */     this.lVehicle2Checklist = this.store.loadVehicle2Checklist();
/* 113 */     for (int i = 0; i < this.lVehicle2Checklist.size(); i++) {
/* 114 */       VehicleCheckList vcl = this.lVehicle2Checklist.get(i);
/* 115 */       Integer model = vcl.getModel();
/*     */       
/* 117 */       if (model.equals(ICLStore.MODEL_WILDCARD)) {
/* 118 */         vcl.setModel(null);
/*     */       }
/* 120 */       Integer modelYear = vcl.getModelYear();
/*     */       
/* 122 */       if (modelYear.equals(ICLStore.MODELYEAR_WILDCARD)) {
/* 123 */         vcl.setModelYear(null);
/*     */       }
/* 125 */       Integer engine = vcl.getEngine();
/*     */       
/* 127 */       if (engine.equals(ICLStore.ENGINE_WILDCARD)) {
/* 128 */         vcl.setEngine(null);
/*     */       }
/* 130 */       Integer countrygroup = vcl.getCountryGroup();
/*     */       
/* 132 */       if (countrygroup.equals(ICLStore.COUNTRYGROUP_WILDCARD)) {
/* 133 */         vcl.setCountryGroup(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCheckListID(String country, String model, String modelYear, String engine, String majorOperation) {
/* 141 */     Integer countrygroupID = (Integer)this.mCountry2CountryGroup.get(country);
/*     */     
/* 143 */     Integer modelID = (Integer)this.mModel2ID.get(model);
/* 144 */     if (modelID == null) {
/* 145 */       throw new IllegalArgumentException("unknown model");
/*     */     }
/* 147 */     Integer modelYearID = null;
/* 148 */     if (modelYear != null) {
/* 149 */       modelYearID = (Integer)this.mModelYear2ID.get(modelYear);
/* 150 */       if (modelYearID == null) {
/* 151 */         throw new IllegalArgumentException("unknown model year");
/*     */       }
/*     */     } 
/* 154 */     Integer engineID = null;
/* 155 */     if (engine != null) {
/* 156 */       engineID = (Integer)this.mEngine2ID.get(engine);
/* 157 */       if (engineID == null) {
/* 158 */         throw new IllegalArgumentException("unknown engine");
/*     */       }
/*     */     } 
/* 161 */     MajorOperation mo = (MajorOperation)this.mMajorOperation2Data.get(majorOperation);
/* 162 */     if (mo == null) {
/* 163 */       throw new IllegalArgumentException("unknown major operation");
/*     */     }
/* 165 */     Integer result = null;
/* 166 */     for (int i = 0; i < this.lVehicle2Checklist.size(); i++) {
/* 167 */       VehicleCheckList vcl = this.lVehicle2Checklist.get(i);
/* 168 */       if (vcl.getMajorOp().equals(mo.getID()))
/*     */       {
/*     */         
/* 171 */         if (vcl.getModel() == null || vcl.getModel().equals(modelID))
/*     */         {
/*     */           
/* 174 */           if (vcl.getModelYear() == null || 
/* 175 */             modelYearID == null || 
/* 176 */             vcl.getModelYear().equals(modelYearID))
/*     */           {
/*     */ 
/*     */             
/* 180 */             if (vcl.getEngine() == null || 
/* 181 */               engineID == null || 
/* 182 */               vcl.getEngine().equals(engineID))
/*     */             {
/*     */ 
/*     */               
/* 186 */               if (vcl.getCountryGroup() == null || 
/* 187 */                 vcl.getCountryGroup().equals(countrygroupID))
/*     */               {
/*     */ 
/*     */                 
/* 191 */                 if (result == null) {
/* 192 */                   result = vcl.getCheckListID();
/*     */                 } else {
/* 194 */                   return MULTIPLE_HITS;
/*     */                 }  }  }  }  }  } 
/*     */     } 
/* 197 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getCheckListAttributes(String country, String model, String modelYear, String engine, String majorOperation) {
/* 202 */     Integer countrygroupID = (Integer)this.mCountry2CountryGroup.get(country);
/* 203 */     Integer modelID = (Integer)this.mModel2ID.get(model);
/* 204 */     if (modelID == null) {
/* 205 */       throw new IllegalArgumentException("unknown model");
/*     */     }
/* 207 */     Integer modelYearID = null;
/* 208 */     if (modelYear != null) {
/* 209 */       modelYearID = (Integer)this.mModelYear2ID.get(modelYear);
/* 210 */       if (modelYearID == null) {
/* 211 */         throw new IllegalArgumentException("unknown model year");
/*     */       }
/*     */     } 
/* 214 */     Integer engineID = null;
/* 215 */     if (engine != null) {
/* 216 */       engineID = (Integer)this.mEngine2ID.get(engine);
/* 217 */       if (engineID == null) {
/* 218 */         throw new IllegalArgumentException("unknown engine");
/*     */       }
/*     */     } 
/* 221 */     MajorOperation mo = (MajorOperation)this.mMajorOperation2Data.get(majorOperation);
/* 222 */     if (mo == null) {
/* 223 */       throw new IllegalArgumentException("unknown major operation");
/*     */     }
/* 225 */     List<PairImpl> result = new LinkedList();
/* 226 */     for (int i = 0; i < this.lVehicle2Checklist.size(); i++) {
/* 227 */       VehicleCheckList vcl = this.lVehicle2Checklist.get(i);
/* 228 */       if (vcl.getMajorOp().equals(mo.getID()))
/*     */       {
/*     */         
/* 231 */         if (vcl.getModel() == null || vcl.getModel().equals(modelID))
/*     */         {
/*     */           
/* 234 */           if (vcl.getModelYear() == null || 
/* 235 */             modelYearID == null || 
/* 236 */             vcl.getModelYear().equals(modelYearID))
/*     */           {
/*     */ 
/*     */             
/* 240 */             if (vcl.getEngine() == null || 
/* 241 */               engineID == null || 
/* 242 */               vcl.getEngine().equals(engineID))
/*     */             {
/*     */ 
/*     */               
/* 246 */               if (vcl.getCountryGroup() == null || 
/* 247 */                 vcl.getCountryGroup().equals(countrygroupID)) {
/*     */ 
/*     */ 
/*     */                 
/* 251 */                 Object first = null;
/* 252 */                 Object second = null;
/* 253 */                 if (vcl.getModelYear() == null) {
/* 254 */                   first = "*";
/*     */                 } else {
/* 256 */                   first = this.mID2ModelYear.get(vcl.getModelYear());
/*     */                 } 
/* 258 */                 if (vcl.getEngine() == null) {
/* 259 */                   second = "*";
/*     */                 } else {
/* 261 */                   second = this.mID2Engine.get(vcl.getEngine());
/*     */                 } 
/* 263 */                 PairImpl pairImpl = new PairImpl(first, second);
/* 264 */                 result.add(pairImpl);
/*     */               }  }  }  }  } 
/*     */     } 
/* 267 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getCheckListModelYears(String country, String model, String engine, String majorOperation) {
/* 272 */     Integer countrygroupID = (Integer)this.mCountry2CountryGroup.get(country);
/* 273 */     Integer modelID = (Integer)this.mModel2ID.get(model);
/* 274 */     if (modelID == null) {
/* 275 */       throw new IllegalArgumentException("unknown model");
/*     */     }
/* 277 */     Integer engineID = null;
/* 278 */     if (engine != null) {
/* 279 */       engineID = (Integer)this.mEngine2ID.get(engine);
/* 280 */       if (engineID == null) {
/* 281 */         throw new IllegalArgumentException("unknown engine");
/*     */       }
/*     */     } 
/* 284 */     MajorOperation mo = (MajorOperation)this.mMajorOperation2Data.get(majorOperation);
/* 285 */     if (mo == null) {
/* 286 */       throw new IllegalArgumentException("unknown major operation");
/*     */     }
/* 288 */     List<String> result = new LinkedList();
/* 289 */     for (int i = 0; i < this.lVehicle2Checklist.size(); i++) {
/* 290 */       VehicleCheckList vcl = this.lVehicle2Checklist.get(i);
/* 291 */       if (vcl.getMajorOp().equals(mo.getID()))
/*     */       {
/*     */         
/* 294 */         if (vcl.getModel() == null || vcl.getModel().equals(modelID))
/*     */         {
/*     */           
/* 297 */           if (vcl.getEngine() == null || 
/* 298 */             engineID == null || vcl.getEngine().equals(engineID))
/*     */           {
/*     */ 
/*     */             
/* 302 */             if (vcl.getCountryGroup() == null || 
/* 303 */               vcl.getCountryGroup().equals(countrygroupID))
/*     */             {
/*     */ 
/*     */               
/* 307 */               if (vcl.getModelYear() != null) {
/* 308 */                 String modelYear = (String)this.mID2ModelYear.get(vcl.getModelYear());
/* 309 */                 if (modelYear != null)
/* 310 */                   result.add(modelYear); 
/*     */               }  }  }  } 
/*     */       }
/*     */     } 
/* 314 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getCheckListEngines(String country, String model, String modelYear, String majorOperation) {
/* 319 */     Integer countrygroupID = (Integer)this.mCountry2CountryGroup.get(country);
/* 320 */     Integer modelID = (Integer)this.mModel2ID.get(model);
/* 321 */     if (modelID == null) {
/* 322 */       throw new IllegalArgumentException("unknown model");
/*     */     }
/* 324 */     Integer modelYearID = null;
/* 325 */     if (modelYear != null) {
/* 326 */       modelYearID = (Integer)this.mModelYear2ID.get(modelYear);
/* 327 */       if (modelYearID == null) {
/* 328 */         throw new IllegalArgumentException("unknown model year");
/*     */       }
/*     */     } 
/* 331 */     MajorOperation mo = (MajorOperation)this.mMajorOperation2Data.get(majorOperation);
/* 332 */     if (mo == null) {
/* 333 */       throw new IllegalArgumentException("unknown major operation");
/*     */     }
/* 335 */     List<String> result = new LinkedList();
/* 336 */     for (int i = 0; i < this.lVehicle2Checklist.size(); i++) {
/* 337 */       VehicleCheckList vcl = this.lVehicle2Checklist.get(i);
/* 338 */       if (vcl.getMajorOp().equals(mo.getID()))
/*     */       {
/*     */         
/* 341 */         if (vcl.getModel() == null || vcl.getModel().equals(modelID))
/*     */         {
/*     */           
/* 344 */           if (vcl.getModelYear() == null || 
/* 345 */             modelYearID == null || vcl.getModelYear().equals(modelYearID))
/*     */           {
/*     */ 
/*     */             
/* 349 */             if (vcl.getCountryGroup() == null || 
/* 350 */               vcl.getCountryGroup().equals(countrygroupID))
/*     */             {
/*     */ 
/*     */               
/* 354 */               if (vcl.getEngine() != null) {
/* 355 */                 String engine = (String)this.mID2Engine.get(vcl.getEngine());
/* 356 */                 if (engine != null)
/* 357 */                   result.add(engine); 
/*     */               }  }  }  } 
/*     */       }
/*     */     } 
/* 361 */     return result;
/*     */   }
/*     */   
/*     */   public Integer getLanguageID(Locale locale) {
/*     */     try {
/* 366 */       return (Integer)this.mLan2LID.get(locale.getLanguage().toLowerCase(Locale.ENGLISH));
/* 367 */     } catch (Exception e) {
/* 368 */       log.debug("Exception in ICL.getLanguage()", e);
/* 369 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getConstant(String oKey, int iLanID) {
/*     */     try {
/* 375 */       return (String)((Map)this.mConstants2Value.get(Integer.valueOf(iLanID))).get(oKey);
/* 376 */     } catch (Exception e) {
/* 377 */       log.debug("Exception in ICL.getConstant()", e);
/* 378 */       return oKey;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(Integer oKey, int iLanID) {
/*     */     try {
/* 385 */       return (String)((Map)this.mAttribute2Value.get(Integer.valueOf(iLanID))).get(oKey);
/* 386 */     } catch (Exception e) {
/* 387 */       log.debug("Exception in ICL.getAttribute()", e);
/* 388 */       return "attribute " + oKey;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getComment(Integer oKey, int iLanID) {
/*     */     try {
/* 394 */       return (String)((Map)this.mComment2Value.get(Integer.valueOf(iLanID))).get(oKey);
/* 395 */     } catch (Exception e) {
/* 396 */       log.debug("Exception in ICL.getComment()", e);
/* 397 */       return "comment " + oKey;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getDriverType(Integer oKey, int iLanID) {
/*     */     try {
/* 403 */       return (String)((Map)this.mDriverType2Value.get(Integer.valueOf(iLanID))).get(oKey);
/* 404 */     } catch (Exception e) {
/* 405 */       log.debug("Exception in ICL.getDriverType()", e);
/* 406 */       return "driver-type " + oKey;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getServiceType(Integer oKey, int iLanID) {
/*     */     try {
/* 412 */       return (String)((Map)this.mServiceType2Value.get(Integer.valueOf(iLanID))).get(oKey);
/* 413 */     } catch (Exception e) {
/* 414 */       log.debug("Exception in ICL.getServiceType()", e);
/* 415 */       return "service-type " + oKey;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getServiceTypeXSL(Integer oKey, int iLanID) {
/*     */     try {
/* 421 */       return (String)((Map)this.mServiceTypeXSL.get(Integer.valueOf(iLanID))).get(oKey);
/* 422 */     } catch (Exception e) {
/* 423 */       log.debug("Exception in ICL.getServiceTypeXSL()", e);
/* 424 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public MajorOperation getMajorOperation(String mo) {
/*     */     try {
/* 430 */       return (MajorOperation)this.mMajorOperation2Data.get(mo);
/* 431 */     } catch (Exception e) {
/* 432 */       log.debug("Exception in ICL.getMajorOperation()", e);
/* 433 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public CheckList getCheckList(long checklistID, int lgID) {
/* 438 */     return this.store.getCheckList(checklistID, lgID);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\db\ICLCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */