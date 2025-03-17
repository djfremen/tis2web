/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.filter.FilterLinkage;
/*     */ import com.eoos.filter.Filter_All;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.IndeterminableVCRException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.Symptom;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBAdapter;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBComparator_PublicationDate;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TroubleCode;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Engine;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Model;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_RemedyNumber;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Salesmake;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Symptom;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_TroubleCode;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.BulletinSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VehicleContextData;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.IVCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BulletinSearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  79 */   private static Logger log = Logger.getLogger(BulletinSearchInputPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  84 */       template = ApplicationContext.getInstance().loadFile(BulletinSearchInputPanel.class, "bulletinsearchinputpanel.html", null).toString();
/*  85 */     } catch (Exception e) {
/*  86 */       log.error("unable to load template - error:" + e, e);
/*  87 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */ 
/*     */   
/*     */   protected SelectBoxSelectionElement ieAssemblyGroup;
/*     */   
/*     */   protected SelectBoxSelectionElement ieSymptom;
/*     */   
/*     */   protected SelectBoxSelectionElement ieTroubleCode;
/*     */   
/*     */   protected SelectBoxSelectionElement ieComparator;
/*     */   
/*     */   protected ClickButtonElement beSearch;
/*     */   
/*     */   protected TextInputElement ieRemedyNumber;
/*     */   
/*     */   protected ClickButtonElement beSearchRemedyNumber;
/*     */   
/*     */   protected static final int STATUS_NO_CONTENT = 1;
/*     */   
/*     */   protected static final int STATUS_OK = 2;
/*     */   
/* 114 */   protected int status = 2;
/*     */   
/* 116 */   protected HashSet smallFilterMakes = new HashSet();
/*     */   
/*     */   public BulletinSearchInputPanel(final ClientContext context) {
/* 119 */     this.context = context;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     this.ieAssemblyGroup = new AssemblyGroupSelectionElement(context);
/* 130 */     addElement((HtmlElement)this.ieAssemblyGroup);
/*     */     
/* 132 */     this.ieSymptom = new SymptomSelectionElement(context);
/* 133 */     addElement((HtmlElement)this.ieSymptom);
/*     */     
/* 135 */     this.ieTroubleCode = new TroubleCodeSelectionElement(context);
/* 136 */     addElement((HtmlElement)this.ieTroubleCode);
/*     */     
/* 138 */     this.beSearch = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 140 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/* 144 */           return BulletinSearchInputPanel.this.onClick_Search(params);
/*     */         }
/*     */       };
/* 147 */     addElement((HtmlElement)this.beSearch);
/*     */     
/* 149 */     this.ieComparator = new TSBComparatorSelectionElement(context);
/* 150 */     addElement((HtmlElement)this.ieComparator);
/*     */     
/* 152 */     this.ieRemedyNumber = new TextInputElement(context.createID());
/* 153 */     addElement((HtmlElement)this.ieRemedyNumber);
/*     */     
/* 155 */     this.beSearchRemedyNumber = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 157 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/* 161 */           return BulletinSearchInputPanel.this.onClick_SearchRemedyNumber(params);
/*     */         }
/*     */       };
/* 164 */     addElement((HtmlElement)this.beSearchRemedyNumber);
/*     */     
/* 166 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 169 */             BulletinSearchInputPanel.this.reset();
/*     */           }
/*     */         });
/* 172 */     fillSmallFilterMakes();
/*     */   }
/*     */   
/*     */   protected void fillSmallFilterMakes() {
/* 176 */     String sfms = ApplicationContext.getInstance().getProperty("component.si.tsb.SmallFilterMakes");
/* 177 */     if (sfms == null)
/*     */       return; 
/* 179 */     StringTokenizer st = new StringTokenizer(sfms, ";");
/* 180 */     while (st.hasMoreTokens()) {
/* 181 */       String next = st.nextToken().toLowerCase(Locale.ENGLISH);
/* 182 */       next = next.trim();
/* 183 */       this.smallFilterMakes.add(next);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 190 */     this.ieAssemblyGroup.setValue(null);
/* 191 */     this.ieSymptom.setValue(null);
/* 192 */     this.ieTroubleCode.setValue(null);
/* 193 */     this.ieComparator.setValue(null);
/* 194 */     this.ieRemedyNumber.setValue(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStatus() {
/* 199 */     switch (this.status) {
/*     */       case 1:
/* 201 */         return this.context.getMessage("si.bulletin.search.nothing.found");
/*     */       case 2:
/* 203 */         return "";
/*     */     } 
/* 205 */     throw new IllegalArgumentException("unknown status");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void smallFilter() {
/* 210 */     String make = VCFacade.getInstance(this.context).getCurrentSalesmake();
/* 211 */     make = make.toLowerCase(Locale.ENGLISH);
/*     */     
/* 213 */     Boolean isSmall = new Boolean(this.smallFilterMakes.contains(make));
/* 214 */     this.ieSymptom.setDisabled(isSmall);
/* 215 */     this.ieTroubleCode.setDisabled(isSmall);
/* 216 */     this.ieComparator.setDisabled(isSmall);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 221 */     smallFilter();
/* 222 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 224 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("si.bulletins.search.message"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     StringUtilities.replace(code, "{LABEL_ASSEMBLYGROUP}", this.context.getLabel("assembly.group") + ":");
/* 234 */     StringUtilities.replace(code, "{SELECTION_ASSEMBLYGROUP}", this.ieAssemblyGroup.getHtmlCode(params));
/*     */     
/* 236 */     StringUtilities.replace(code, "{LABEL_SYMPTOM}", this.context.getLabel("symptom") + ":");
/* 237 */     StringUtilities.replace(code, "{SELECTION_SYMPTOM}", this.ieSymptom.getHtmlCode(params));
/*     */     
/* 239 */     StringUtilities.replace(code, "{LABEL_TROUBLECODE}", this.context.getLabel("trouble.code") + ":");
/* 240 */     StringUtilities.replace(code, "{SELECTION_TROUBLECODE}", this.ieTroubleCode.getHtmlCode(params));
/*     */     
/* 242 */     StringUtilities.replace(code, "{BUTTON_SEARCH}", this.beSearch.getHtmlCode(params));
/*     */     
/* 244 */     StringUtilities.replace(code, "{LABEL_COMPARATOR}", this.context.getLabel("order.by") + ":");
/* 245 */     StringUtilities.replace(code, "{SELECTION_COMPARATOR}", this.ieComparator.getHtmlCode(params));
/*     */     
/* 247 */     StringUtilities.replace(code, "{MESSAGE_SEARCH_REMEDYNUMBER}", this.context.getMessage("si.bulletins.search.alternative"));
/*     */     
/* 249 */     StringUtilities.replace(code, "{INPUT_REMEDYNUMBER}", this.ieRemedyNumber.getHtmlCode(params));
/* 250 */     StringUtilities.replace(code, "{BUTTON_SEARCH_REMEDYNUMBER}", this.beSearchRemedyNumber.getHtmlCode(params));
/*     */     
/* 252 */     StringUtilities.replace(code, "{STATUS}", getStatus());
/*     */     
/* 254 */     return code.toString();
/*     */   }
/*     */   
/*     */   private VCRValue createVCRValue(String domainID, String value) throws IndeterminableVCRException {
/* 258 */     ILVCAdapter adapter = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/* 259 */     VC vc = adapter.getVC();
/* 260 */     VCRDomain domain = (VCRDomain)vc.getDomain(domainID);
/* 261 */     VCRValue ret = domain.lookup(value);
/* 262 */     if (ret != null) {
/* 263 */       return ret;
/*     */     }
/* 265 */     throw new IndeterminableVCRException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getVCRValueMap() throws IndeterminableVCRException {
/* 270 */     Map<Object, Object> ret = new LinkedHashMap<Object, Object>();
/* 271 */     IVCFacade vc = VCFacade.getInstance(this.context);
/* 272 */     IConfiguration cfg = vc.getCfg();
/* 273 */     if (cfg != null) {
/* 274 */       Make make = VehicleConfigurationUtil.getMake(cfg);
/* 275 */       if (make != null) {
/* 276 */         ret.put("Make", createVCRValue("Make", vc.getDisplayValue(make)));
/*     */       }
/* 278 */       Model model = VehicleConfigurationUtil.getModel(cfg);
/* 279 */       if (model != null) {
/* 280 */         ret.put("Model", createVCRValue("Model", vc.getDisplayValue(model)));
/*     */       }
/* 282 */       Modelyear modelyear = VehicleConfigurationUtil.getModelyear(cfg);
/* 283 */       if (modelyear != null) {
/* 284 */         ret.put("ModelYear", createVCRValue("ModelYear", vc.getDisplayValue(modelyear)));
/*     */       }
/* 286 */       Engine engine = VehicleConfigurationUtil.getEngine(cfg);
/* 287 */       if (engine != null) {
/* 288 */         ret.put("Engine", createVCRValue("Engine", vc.getDisplayValue(engine)));
/*     */       }
/* 290 */       Transmission transmission = VehicleConfigurationUtil.getTransmission(cfg);
/* 291 */       if (transmission != null) {
/* 292 */         ret.put("Transmission", createVCRValue("Transmission", vc.getDisplayValue(transmission)));
/*     */       }
/*     */     } 
/* 295 */     return ret;
/*     */   }
/*     */   protected Filter getFilter() {
/*     */     FilterLinkage filterLinkage;
/* 299 */     Filter_All filter_All = new Filter_All();
/*     */ 
/*     */     
/*     */     try {
/* 303 */       Map vcrValueMap = getVCRValueMap();
/*     */       
/* 305 */       VCValue value = (VCValue)vcrValueMap.get("Make");
/* 306 */       if (value != null) {
/* 307 */         filterLinkage = new FilterLinkage((Filter)filter_All, (Filter)new TSBFilter_Salesmake(value), 2);
/*     */       }
/*     */       
/* 310 */       value = (VCValue)vcrValueMap.get("Model");
/* 311 */       if (value != null) {
/* 312 */         filterLinkage = new FilterLinkage((Filter)filterLinkage, (Filter)new TSBFilter_Model(value), 2);
/*     */       }
/*     */       
/* 315 */       value = (VCValue)vcrValueMap.get("Engine");
/* 316 */       if (value != null) {
/* 317 */         filterLinkage = new FilterLinkage((Filter)filterLinkage, (Filter)new TSBFilter_Engine(value), 2);
/*     */       }
/* 319 */     } catch (Exception e) {
/* 320 */       log.warn("unable to determine vcr, ignoring vehicle filter");
/*     */     } 
/*     */     
/* 323 */     if (this.ieAssemblyGroup.getValue() != AssemblyGroupSelectionElement.ANY) {
/* 324 */       filterLinkage = new FilterLinkage((Filter)filterLinkage, (Filter)new TSBFilter_AssemblyGroup((AssemblyGroup)this.ieAssemblyGroup.getValue()), 2);
/*     */     }
/* 326 */     if (this.ieSymptom.getValue() != SymptomSelectionElement.ANY) {
/* 327 */       filterLinkage = new FilterLinkage((Filter)filterLinkage, (Filter)new TSBFilter_Symptom((Symptom)this.ieSymptom.getValue()), 2);
/*     */     }
/* 329 */     if (this.ieTroubleCode.getValue() != TroubleCodeSelectionElement.ANY) {
/* 330 */       filterLinkage = new FilterLinkage((Filter)filterLinkage, (Filter)new TSBFilter_TroubleCode((TroubleCode)this.ieTroubleCode.getValue()), 2);
/*     */     }
/*     */     
/* 333 */     return (Filter)filterLinkage;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void filterAuthorizedVCR(List tsbList) {
/* 338 */     if (tsbList == null) {
/*     */       return;
/*     */     }
/* 341 */     ILVCAdapter.Retrieval lvcr = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter().createRetrievalImpl();
/*     */     
/*     */     try {
/* 344 */       vCR = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 345 */     } catch (Exception e) {
/* 346 */       vCR = VCR.NULL;
/*     */     } 
/*     */ 
/*     */     
/* 350 */     VCR vCR = VehicleContextData.getInstance(this.context).filterAuthorizedVCR(vCR, lvcr);
/* 351 */     Iterator<SIOTSB> it = tsbList.iterator();
/* 352 */     while (it.hasNext()) {
/* 353 */       SIOTSB tsb = it.next();
/* 354 */       VCR elementVCR = tsb.getVCR();
/* 355 */       if (elementVCR != null && vCR != null && !elementVCR.match(vCR)) {
/* 356 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List reduceListTSBs(List tsbList) {
/* 362 */     boolean isSCDSAG = AssemblyGroup.existSCDSGroupeDisabled();
/* 363 */     if (tsbList == null)
/* 364 */       return null; 
/* 365 */     Map<Object, Object> result = new HashMap<Object, Object>();
/* 366 */     Iterator it = tsbList.iterator();
/* 367 */     while (it.hasNext()) {
/* 368 */       Object tsb = it.next();
/* 369 */       if (tsb instanceof SIOTSB) {
/* 370 */         Integer id = ((SIOTSB)tsb).getID();
/* 371 */         if (result.containsKey(id)) {
/* 372 */           it.remove(); continue;
/*     */         } 
/* 374 */         if (isSCDSAG)
/*     */         {
/* 376 */           if (AssemblyGroup.isSCDSGroupeDisabled(((SIOTSB)tsb).getAssemblyGroup())) {
/* 377 */             it.remove();
/*     */             continue;
/*     */           } 
/*     */         }
/* 381 */         result.put(id, tsb);
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     return tsbList;
/*     */   }
/*     */   protected Object onClick_Search(Map params) {
/*     */     HtmlElementContainer htmlElementContainer;
/* 389 */     Filter filter = getFilter();
/* 390 */     String ag = null;
/* 391 */     String dtc = null;
/* 392 */     String troublecode = null;
/* 393 */     if (this.ieAssemblyGroup.getValue() != AssemblyGroupSelectionElement.ANY) {
/* 394 */       AssemblyGroup assemblyGroup = (AssemblyGroup)this.ieAssemblyGroup.getValue();
/* 395 */       ag = (String)assemblyGroup.getNode().getProperty((SITOCProperty)CTOCProperty.AssemblyGroup);
/*     */     } 
/* 397 */     if (this.ieSymptom.getValue() != SymptomSelectionElement.ANY) {
/* 398 */       Symptom symptom = (Symptom)this.ieSymptom.getValue();
/* 399 */       dtc = (String)symptom.getNode().getProperty((SITOCProperty)CTOCProperty.SITQ);
/* 400 */       int index = dtc.indexOf("/");
/* 401 */       dtc = dtc.substring(index + 1);
/*     */     } 
/*     */     
/* 404 */     if (this.ieTroubleCode.getValue() != TroubleCodeSelectionElement.ANY) {
/* 405 */       TroubleCode troubleCode = (TroubleCode)this.ieTroubleCode.getValue();
/* 406 */       troublecode = troubleCode.getIdentifier();
/*     */     } 
/*     */     
/* 409 */     List<?> tsbList = TSBAdapter.getTSBDomain(ag, dtc, troublecode, this.context);
/* 410 */     CollectionUtil.filter(tsbList, filter);
/* 411 */     filterAuthorizedVCR(tsbList);
/*     */ 
/*     */     
/* 414 */     TSBFilter_AssemblyGroup filterAG = null;
/* 415 */     if (this.ieAssemblyGroup.getValue() != AssemblyGroupSelectionElement.ANY) {
/* 416 */       filterAG = new TSBFilter_AssemblyGroup((AssemblyGroup)this.ieAssemblyGroup.getValue());
/*     */     }
/* 418 */     TSBFilter_Symptom filterSymptom = null;
/* 419 */     if (this.ieSymptom.getValue() != SymptomSelectionElement.ANY) {
/* 420 */       filterSymptom = new TSBFilter_Symptom((Symptom)this.ieSymptom.getValue());
/*     */     }
/* 422 */     TSBFilter_TroubleCode filterTroubleCode = null;
/* 423 */     if (this.ieTroubleCode.getValue() != TroubleCodeSelectionElement.ANY) {
/* 424 */       filterTroubleCode = new TSBFilter_TroubleCode((TroubleCode)this.ieTroubleCode.getValue());
/*     */     }
/* 426 */     tsbList = reduceListTSBs(tsbList);
/* 427 */     loadChildrenSubjects(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()), tsbList);
/* 428 */     loadProperties(tsbList);
/* 429 */     if (tsbList != null && tsbList.size() > 0) {
/* 430 */       this.status = 2;
/* 431 */       Collections.sort(tsbList, TSBComparator_PublicationDate.COMPARATOR_DESC);
/*     */       
/* 433 */       BulletinSearchPanel.getInstance(this.context).switchView(1, new Object[] { tsbList, filterAG, filterSymptom, filterTroubleCode, this.ieComparator.getValue() });
/*     */     } else {
/* 435 */       this.status = 1;
/*     */     } 
/*     */     
/* 438 */     BulletinSearchInputPanel bulletinSearchInputPanel = this;
/* 439 */     while (bulletinSearchInputPanel.getContainer() != null) {
/* 440 */       htmlElementContainer = bulletinSearchInputPanel.getContainer();
/*     */     }
/* 442 */     return htmlElementContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadChildrenSubjects(LocaleInfo locale, List tsbList) {}
/*     */ 
/*     */   
/*     */   protected void loadProperties(final List tsbList) {
/* 451 */     SI si = SIDataAdapterFacade.getInstance(this.context).getSI();
/*     */     
/* 453 */     if (!Util.isNullOrEmpty(tsbList)) {
/* 454 */       si.loadProperties(new AbstractList()
/*     */           {
/*     */             public Object get(int index)
/*     */             {
/* 458 */               return ((SIOTSB)tsbList.get(index)).getSIOLU();
/*     */             }
/*     */ 
/*     */             
/*     */             public int size() {
/* 463 */               return tsbList.size();
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object onClick_SearchRemedyNumber(Map params) {
/* 470 */     String number = (String)this.ieRemedyNumber.getValue();
/* 471 */     TSBFilter_RemedyNumber tSBFilter_RemedyNumber = new TSBFilter_RemedyNumber(number);
/*     */     
/* 473 */     List<SIOTSB> tsbList = TSBAdapter.getTSBDomain(null, null, null, this.context);
/* 474 */     CollectionUtil.filter(tsbList, (Filter)tSBFilter_RemedyNumber);
/* 475 */     filterAuthorizedVCR(tsbList);
/*     */     
/* 477 */     if (tsbList != null && tsbList.size() > 0) {
/* 478 */       this.status = 2;
/*     */       
/* 480 */       BulletinSearchPanel.getInstance(this.context).switchView(2, new Object[] { tsbList.get(0), new Boolean(true) });
/*     */     } else {
/*     */       
/* 483 */       this.status = 1;
/*     */     } 
/*     */     
/* 486 */     HtmlElementContainer parent = getContainer();
/* 487 */     while (parent.getContainer() != null) {
/* 488 */       parent = parent.getContainer();
/*     */     }
/* 490 */     return parent;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\input\BulletinSearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */