/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*      */ 
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerFunction;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerReference;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerSequence;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*      */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*      */ public class SPSControllerReference
/*      */   extends SPSControllerReference {
/*      */   private static final long serialVersionUID = 1L;
/*   32 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*      */   
/*      */   protected transient SPSSession session;
/*      */   
/*      */   protected transient List controllers;
/*      */   
/*      */   protected transient List options;
/*      */   
/*      */   protected transient List disqualified;
/*      */   
/*      */   protected transient List rcategories;
/*      */   
/*      */   protected transient List selections;
/*      */   
/*      */   protected transient String function;
/*      */   
/*      */   protected transient String sequence;
/*      */   
/*      */   protected transient boolean supportsSPSFunctions;
/*      */   
/*      */   protected List sequences;
/*      */   
/*      */   protected List functions;
/*      */   
/*      */   protected Map smethods;
/*      */   
/*      */   SPSControllerReference(SPSSession session, SPSControllerSequenceGME sequence, SPSSequenceFunction function) {
/*   59 */     this.session = session;
/*   60 */     this.controllers = new ArrayList();
/*   61 */     this.disqualified = new ArrayList();
/*      */     
/*   63 */     this.description = "function = " + function.getFunctionID();
/*   64 */     this.supportsSPSFunctions = true;
/*      */   }
/*      */   
/*      */   SPSControllerReference(SPSSession session, SPSController controller) {
/*   68 */     this.session = session;
/*   69 */     this.controllers = new ArrayList();
/*   70 */     this.disqualified = new ArrayList();
/*   71 */     if (controller instanceof SPSControllerFunction) {
/*   72 */       this.description = ((SPSControllerFunction)controller).getControllerDescription();
/*      */     } else {
/*   74 */       this.description = controller.getDescription();
/*      */     } 
/*   76 */     this.methods = controller.getProgrammingTypes();
/*   77 */     this.controllers.add(controller);
/*      */   }
/*      */   
/*      */   boolean accept(SPSControllerGME controller) {
/*   81 */     SPSControllerGME template = this.controllers.get(0);
/*   82 */     if (template instanceof SPSControllerSequenceGME)
/*   83 */       return template.getControllerID().equals(controller.getControllerID()); 
/*   84 */     if (template instanceof SPSControllerXML && !(controller instanceof SPSControllerXML))
/*   85 */       return false; 
/*   86 */     if (!(template instanceof SPSControllerXML) && controller instanceof SPSControllerXML)
/*   87 */       return false; 
/*   88 */     if (controller instanceof SPSControllerXML || SPSModel.XML_DEVICE_ID.equals(controller.getSystemTypeID())) {
/*   89 */       return template.getControllerID().equals(controller.getControllerID());
/*      */     }
/*   91 */     return (controller.getSystemTypeID().equals(template.getSystemTypeID()) && template.getControllerID().equals(controller.getControllerID()));
/*      */   }
/*      */   
/*      */   public List getHistory() {
/*   95 */     if (this.controllers.size() == 0) {
/*   96 */       return null;
/*      */     }
/*   98 */     SPSControllerGME controller = this.controllers.get(0);
/*      */     try {
/*  100 */       return controller.getHistory();
/*  101 */     } catch (Exception x) {
/*      */       
/*  103 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SPSControllerGME getSelectedController() {
/*  108 */     if (this.controllers.size() != 1) {
/*  109 */       throw new UnsupportedOperationException();
/*      */     }
/*  111 */     return this.controllers.get(0);
/*      */   }
/*      */   
/*      */   public SPSControllerGME selectControllerByVehicleID(SPSSchemaAdapter adapter, SPSSession session, SPSControllerList controllers) throws Exception {
/*  115 */     this.controllers.clear();
/*  116 */     Iterator<SPSControllerReference> it = controllers.iterator();
/*  117 */     while (it.hasNext()) {
/*  118 */       SPSControllerReference reference = it.next();
/*  119 */       addControllerByVehicleID(reference);
/*      */     } 
/*  121 */     return (SPSControllerGME)qualify(session.getVehicle().getOptions(), adapter);
/*      */   }
/*      */   
/*      */   protected void addControllerByVehicleID(SPSControllerReference reference) {
/*  125 */     this.controllers.addAll(reference.getControllers());
/*      */   }
/*      */   
/*      */   public boolean hasAsBuiltSupport(int device) {
/*  129 */     if (this.controllers.size() > 0) {
/*  130 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  131 */         SPSControllerGME controller = this.controllers.get(i);
/*  132 */         if (controller.hasAsBuiltSupport(device)) {
/*  133 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  137 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAsBuiltController(int ecu) {
/*  141 */     if (this.controllers.size() > 0) {
/*  142 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  143 */         SPSControllerGME controller = this.controllers.get(i);
/*  144 */         if (controller.checkAsBuiltController(ecu)) {
/*  145 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  149 */     return false;
/*      */   }
/*      */   
/*      */   public int getVINID(int ecu) {
/*  153 */     if (this.controllers.size() > 0) {
/*  154 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  155 */         SPSControllerGME controller = this.controllers.get(i);
/*  156 */         if (controller.checkAsBuiltController(ecu)) {
/*  157 */           return controller.getVINID();
/*      */         }
/*      */       } 
/*      */     }
/*  161 */     return -1;
/*      */   }
/*      */   
/*      */   public void qualifyAsBuiltController() {
/*  165 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  166 */     while (it.hasNext()) {
/*  167 */       SPSControllerGME controller = it.next();
/*  168 */       if (!controller.isAsBuiltController() && ((
/*  169 */         this.supportsSPSFunctions && this.sequence != null) || !this.supportsSPSFunctions)) {
/*  170 */         this.disqualified.add(controller);
/*  171 */         it.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void qualifyAsBuiltController(int ecu) {
/*  178 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  179 */     while (it.hasNext()) {
/*  180 */       SPSControllerGME controller = it.next();
/*  181 */       if (!controller.isAsBuiltController(ecu) && ((
/*  182 */         this.supportsSPSFunctions && this.sequence != null) || !this.supportsSPSFunctions)) {
/*  183 */         this.disqualified.add(controller);
/*  184 */         it.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void add(SPSController controller, SPSSchemaAdapterGME adapter) {
/*  192 */     if (!(controller instanceof SPSControllerSequenceGME) && this.controllers.size() > 0) {
/*  193 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  194 */         SPSControllerGME other = this.controllers.get(i);
/*  195 */         if (other.getVehicleID().equals(((SPSControllerGME)controller).getVehicleID())) {
/*  196 */           if (controller == SPSECU.Provider.getInstance(adapter).compareReleaseDate((SPSControllerGME)controller, other)) {
/*  197 */             ((SPSControllerGME)controller).register(other);
/*      */ 
/*      */ 
/*      */             
/*  201 */             this.controllers.set(i, controller);
/*      */           } else {
/*  203 */             other.register((SPSControllerGME)controller);
/*      */           } 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  212 */     this.controllers.add(controller);
/*      */   }
/*      */   
/*      */   List getControllers() {
/*  216 */     return this.controllers;
/*      */   }
/*      */ 
/*      */   
/*      */   Integer getControllerID() {
/*  221 */     if (this.controllers.size() == 0) {
/*  222 */       return null;
/*      */     }
/*  224 */     SPSControllerGME template = this.controllers.get(0);
/*  225 */     return template.getControllerID();
/*      */   }
/*      */   
/*      */   protected List getDeviceList() {
/*  229 */     List<Integer> devices = new ArrayList();
/*  230 */     if (this.controllers.size() > 0) {
/*  231 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  232 */         SPSControllerGME controller = this.controllers.get(i);
/*  233 */         Integer device = controller.getSystemTypeID();
/*  234 */         if (!devices.contains(device)) {
/*  235 */           devices.add(device);
/*      */         }
/*      */       } 
/*      */     }
/*  239 */     return devices;
/*      */   }
/*      */   
/*      */   Integer getSystemTypeID() {
/*  243 */     if (this.controllers.size() == 0) {
/*  244 */       return null;
/*      */     }
/*  246 */     SPSControllerGME template = this.controllers.get(0);
/*  247 */     return template.getSystemTypeID();
/*      */   }
/*      */   
/*      */   protected boolean isEngineController(SPSSchemaAdapter adapter) {
/*  251 */     if (this.controllers.size() == 0) {
/*  252 */       return false;
/*      */     }
/*  254 */     SPSControllerGME template = this.controllers.get(0);
/*  255 */     String label = template.getDescription(SPSLanguage.getLanguage(Locale.ENGLISH, (SPSSchemaAdapterGME)adapter));
/*  256 */     return (label != null) ? ((label.toLowerCase(Locale.ENGLISH).indexOf("engine") >= 0)) : false;
/*      */   }
/*      */   
/*      */   public List getOptions() {
/*  260 */     if (this.sequence != null) {
/*  261 */       return (this.options != null) ? this.options : getSequenceOptions();
/*      */     }
/*  263 */     if (this.options == null) {
/*  264 */       return null;
/*      */     }
/*  266 */     SPSOptionCategory category = null;
/*  267 */     int order = Integer.MAX_VALUE;
/*  268 */     for (int i = 0; i < this.options.size(); i++) {
/*  269 */       SPSOptionCategory candidate = this.options.get(i);
/*  270 */       if (category != null && candidate.getOrder() < order) {
/*  271 */         candidate = checkCandidate(category, candidate);
/*  272 */         if (candidate != null) {
/*  273 */           order = candidate.getOrder();
/*  274 */           category = candidate;
/*      */         } 
/*  276 */       } else if (category == null && checkCandidate((SPSOptionCategory)null, candidate) != null) {
/*  277 */         order = candidate.getOrder();
/*  278 */         category = candidate;
/*      */       } 
/*      */     } 
/*  281 */     if (category != null) {
/*  282 */       List<SPSOptionCategory> result = new ArrayList();
/*  283 */       result.add(category);
/*  284 */       return result;
/*      */     } 
/*  286 */     return this.options;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSOptionCategory checkCandidate(SPSOptionCategory selection, SPSOptionCategory candidate) {
/*  291 */     if (this.selections == null) {
/*  292 */       return candidate;
/*      */     }
/*  294 */     for (int i = 0; i < this.selections.size(); i++) {
/*  295 */       SPSOption option = this.selections.get(i);
/*  296 */       if (option.getType().getID().equals(candidate.getID())) {
/*  297 */         return selection;
/*      */       }
/*      */     } 
/*  300 */     return candidate;
/*      */   }
/*      */   
/*      */   protected List getSequenceOptions() {
/*  304 */     List<SPSOptionCategory> options = null;
/*      */     try {
/*  306 */       options = getPreOptions();
/*  307 */     } catch (Exception e) {}
/*      */     
/*  309 */     if (options == null) {
/*  310 */       return null;
/*      */     }
/*  312 */     List<SPSOptionCategory> result = new ArrayList();
/*  313 */     for (int i = 0; i < options.size(); i++) {
/*  314 */       SPSOptionCategory candidate = options.get(i);
/*  315 */       if (!checkSelectionCandidate(this.session.getVehicle().getOptions(), candidate)) {
/*  316 */         result.add(candidate);
/*      */       }
/*      */     } 
/*  319 */     return (result.size() > 0) ? result : null;
/*      */   }
/*      */   
/*      */   protected boolean checkSelectionCandidate(List<SPSOption> selections, SPSOptionCategory candidate) {
/*  323 */     if (selections == null) {
/*  324 */       return false;
/*      */     }
/*  326 */     for (int i = 0; i < selections.size(); i++) {
/*  327 */       SPSOption option = selections.get(i);
/*  328 */       if (option.getType().getID().equals(candidate.getID())) {
/*  329 */         return true;
/*      */       }
/*      */     } 
/*  332 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List getPreOptions() throws Exception {
/*  337 */     if (this.sequence != null) {
/*  338 */       int order = Integer.MAX_VALUE;
/*  339 */       Iterator<SPSControllerSequence> it = this.controllers.iterator();
/*  340 */       while (it.hasNext()) {
/*  341 */         SPSControllerSequence controller = it.next();
/*  342 */         boolean accept = controller.qualifySequence(order);
/*  343 */         if (!accept) {
/*  344 */           this.disqualified.add(controller);
/*  345 */           it.remove();
/*      */         } 
/*      */       } 
/*  348 */       if (this.controllers.size() == 1) {
/*  349 */         return null;
/*      */       }
/*  351 */       List<SPSOptionCategory> options = new ArrayList();
/*  352 */       it = this.controllers.iterator();
/*  353 */       while (it.hasNext()) {
/*  354 */         SPSControllerSequence controller = it.next();
/*  355 */         List<SPSOptionCategory> categories = ((SPSController)controller).getPreSelectionOptions();
/*  356 */         for (int i = 0; i < categories.size(); i++) {
/*  357 */           SPSOptionCategory category = categories.get(i);
/*  358 */           boolean exists = false;
/*  359 */           for (int j = 0; j < options.size(); j++) {
/*  360 */             SPSOptionCategory master = options.get(j);
/*  361 */             if (master.equals(category)) {
/*  362 */               exists = true;
/*  363 */               SPSOptionCategory merge = (SPSOptionCategory)SPSOptionCategory.getOptionCategory(master, category);
/*  364 */               merge.setOrder(master.getOrder());
/*  365 */               options.set(j, merge);
/*      */             } 
/*      */           } 
/*  368 */           if (!exists) {
/*  369 */             options.add(category);
/*      */           }
/*      */         } 
/*      */       } 
/*  373 */       return (options.size() > 0) ? options : null;
/*      */     } 
/*      */     
/*  376 */     return null;
/*      */   }
/*      */   
/*      */   protected void addRequestMethodGroupCandidate(Map<Integer, List> rmgs, SPSRequestInfoCategory rcategory) {
/*  380 */     List<String> categories = (List)rmgs.get(rcategory.getRequestMethodGroupID());
/*  381 */     if (categories == null) {
/*  382 */       categories = new ArrayList();
/*  383 */       rmgs.put(rcategory.getRequestMethodGroupID(), categories);
/*      */     } 
/*  385 */     categories.add(rcategory.getCategory().getID());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addRequestMethodIDCandidate(Map<Integer, Integer> rmids, SPSRequestInfoCategory rcategory) {
/*  390 */     Integer rmid = Integer.valueOf(rcategory.getRequestMethodID());
/*  391 */     Integer ReqInfoMethGroup = (Integer)rmids.get(rmid);
/*  392 */     if (ReqInfoMethGroup == null) {
/*  393 */       ReqInfoMethGroup = rcategory.getRequestMethodGroupID();
/*      */     }
/*  395 */     rmids.put(rmid, ReqInfoMethGroup);
/*      */   }
/*      */   
/*      */   protected Integer checkRequestMethodGroupCandidates(Map rmgs) {
/*  399 */     Integer rmg = null;
/*  400 */     Iterator<Integer> it = rmgs.keySet().iterator();
/*      */     
/*  402 */     while (it.hasNext()) {
/*  403 */       Integer candidate = it.next();
/*  404 */       if (rmg == null) {
/*  405 */         rmg = candidate; continue;
/*  406 */       }  if (((List)rmgs.get(rmg)).size() < ((List)rmgs.get(candidate)).size()) {
/*  407 */         rmg = candidate;
/*      */       }
/*      */     } 
/*  410 */     return rmg;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Integer checkRequestMethodIDCandidates(Map rmids) {
/*  415 */     Integer rmg = null;
/*  416 */     Iterator<Integer> it = rmids.keySet().iterator();
/*      */     
/*  418 */     while (it.hasNext()) {
/*  419 */       Integer candidate = it.next();
/*  420 */       if (rmg == null) {
/*  421 */         rmg = (Integer)rmids.get(candidate);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  427 */     return rmg;
/*      */   }
/*      */   
/*      */   protected Map collectRequestMethodGroupCandidates(List rcategories) {
/*  431 */     Map<Object, Object> rmgs = new HashMap<Object, Object>();
/*  432 */     Iterator<SPSRequestInfoCategory> it = rcategories.iterator();
/*  433 */     while (it.hasNext()) {
/*  434 */       SPSRequestInfoCategory rcategory = it.next();
/*  435 */       if (rcategory.isEvaluated()) {
/*  436 */         addRequestMethodGroupCandidate(rmgs, rcategory);
/*  437 */         it.remove();
/*      */       } 
/*      */     } 
/*  440 */     return rmgs;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Map collectRequestMethodIDCandidates(List rcategories) {
/*  445 */     Map<Object, Object> rmids = new HashMap<Object, Object>();
/*  446 */     Iterator<SPSRequestInfoCategory> it = rcategories.iterator();
/*  447 */     while (it.hasNext()) {
/*  448 */       SPSRequestInfoCategory rcategory = it.next();
/*  449 */       if (rcategory.isEvaluated()) {
/*  450 */         addRequestMethodIDCandidate(rmids, rcategory);
/*  451 */         it.remove();
/*      */       } 
/*      */     } 
/*  454 */     return rmids;
/*      */   }
/*      */   
/*      */   protected List reduceRequestMethodGroupOptions(Map rmgs, List rcategories) {
/*  458 */     Iterator<SPSRequestInfoCategory> it = rcategories.iterator();
/*  459 */     while (it.hasNext()) {
/*  460 */       SPSRequestInfoCategory rcategory = it.next();
/*  461 */       if (rmgs.get(rcategory.getRequestMethodGroupID()) == null) {
/*  462 */         it.remove();
/*      */       }
/*      */     } 
/*  465 */     return rcategories;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List reduceRequestMethodIDOptions(Map rmids, List rcategories) {
/*  470 */     Iterator<SPSRequestInfoCategory> it = rcategories.iterator();
/*  471 */     while (it.hasNext()) {
/*  472 */       SPSRequestInfoCategory rcategory = it.next();
/*  473 */       Integer rmid = Integer.valueOf(rcategory.getRequestMethodID());
/*  474 */       if (rmids.get(rmid) == null) {
/*  475 */         it.remove();
/*      */       }
/*      */     } 
/*  478 */     return rcategories;
/*      */   }
/*      */   
/*      */   List getSequenceRequestMethodData(SPSSession session, SPSSchemaAdapterGME adapter) throws Exception {
/*  482 */     if (!SPSRequestInfoCategory.hasRequestInfo(adapter)) {
/*  483 */       return null;
/*      */     }
/*  485 */     List<SPSRequestMethodData> result = new ArrayList();
/*  486 */     Iterator<SPSControllerSequence> it = this.controllers.iterator();
/*  487 */     while (it.hasNext()) {
/*  488 */       SPSControllerSequence controller = it.next();
/*  489 */       List<SPSControllerFunction> functions = controller.getFunctions();
/*  490 */       for (int i = 0; i < functions.size(); i++) {
/*  491 */         SPSControllerFunction function = functions.get(i);
/*  492 */         Integer rmg = SPSRequestInfoCategory.getRequestMethodGroupID(function.getRequestInfoID(), adapter);
/*  493 */         SPSRequestMethodData rmd = new SPSRequestMethodData(rmg, ((SPSControllerGME)function).getSystemTypeID());
/*  494 */         boolean exists = false;
/*  495 */         for (int j = 0; j < result.size(); j++) {
/*  496 */           SPSRequestMethodData data = result.get(j);
/*  497 */           if (data.getDeviceID() == rmd.getDeviceID()) {
/*  498 */             exists = true;
/*      */           }
/*      */         } 
/*  501 */         if (!exists) {
/*  502 */           result.add(rmd);
/*      */         }
/*      */       } 
/*      */     } 
/*  506 */     return result;
/*      */   }
/*      */   
/*      */   List getRequestMethodData(SPSSession session, SPSSchemaAdapterGME adapter) throws Exception {
/*  510 */     if (adapter.supportsSPSFunctions() && this.sequence != null) {
/*  511 */       return getSequenceRequestMethodData(session, adapter);
/*      */     }
/*  513 */     if (!SPSRequestInfoCategory.hasRequestInfo(adapter))
/*  514 */       return null; 
/*  515 */     if (this.rcategories == null) {
/*  516 */       this.rcategories = SPSRequestInfoCategory.loadRequestInfoCategories((SPSLanguage)session.getLanguage(), (SPSVehicle)session.getVehicle(), getControllerID(), adapter);
/*      */     }
/*  518 */     if (this.rcategories != null && this.rcategories.size() == 1 && this.rcategories.get(0) instanceof Integer) {
/*  519 */       Integer rmg = this.rcategories.get(0);
/*  520 */       log.debug("request method group = " + rmg);
/*  521 */       List<RequestMethodData> result = new ArrayList();
/*  522 */       result.add(handleDeviceListRMD(rmg));
/*  523 */       return result;
/*      */     } 
/*  525 */     boolean accept = SPSRequestInfoCategory.qualify(session, this.rcategories, 2147483647);
/*  526 */     if (accept && this.rcategories != null) {
/*  527 */       this.options = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  535 */       Map rmids = collectRequestMethodIDCandidates(this.rcategories);
/*  536 */       if (rmids.size() > 0) {
/*  537 */         this.rcategories = reduceRequestMethodIDOptions(rmids, this.rcategories);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  548 */       for (int j = 0; j < this.rcategories.size(); j++) {
/*  549 */         SPSRequestInfoCategory rcategory = this.rcategories.get(j);
/*  550 */         if (!rcategory.isEvaluated() && rcategory.isOptionCategory()) {
/*  551 */           List<SPSOptionCategory> list = new ArrayList();
/*  552 */           list.add(rcategory.getCategory());
/*  553 */           addOptions(this.options, list);
/*  554 */           accept = false;
/*      */         } 
/*      */       } 
/*  557 */       if (accept) {
/*  558 */         this.options = null;
/*      */ 
/*      */         
/*  561 */         Integer rmg = checkRequestMethodIDCandidates(rmids);
/*  562 */         log.debug("request method group = " + rmg);
/*  563 */         if (rmg == null) {
/*  564 */           return null;
/*      */         }
/*  566 */         List<RequestMethodData> result = new ArrayList();
/*  567 */         result.add(handleDeviceListRMD(rmg));
/*  568 */         return result;
/*  569 */       }  if (this.options.size() == 0) {
/*  570 */         this.options = null;
/*      */       }
/*      */     } 
/*      */     
/*  574 */     return null;
/*      */   }
/*      */   
/*      */   protected RequestMethodData handleDeviceListRMD(Integer rmg) throws Exception {
/*  578 */     SPSRequestMethodData rmd = null;
/*  579 */     List<Integer> devices = getDeviceList();
/*  580 */     if (devices.size() == 1) {
/*  581 */       rmd = new SPSRequestMethodData(rmg, devices.get(0));
/*  582 */     } else if (devices.size() > 1) {
/*  583 */       rmd = new SPSRequestMethodData(rmg, Integer.valueOf(0));
/*  584 */       rmd.setDeviceList(devices);
/*      */     } 
/*  586 */     return rmd;
/*      */   }
/*      */   
/*      */   protected void qualifyDevice(int device) {
/*  590 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  591 */     while (it.hasNext()) {
/*  592 */       SPSControllerGME controller = it.next();
/*  593 */       if (device != controller.getSystemTypeID().intValue()) {
/*  594 */         this.disqualified.add(controller);
/*  595 */         it.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPSController qualify(List options, SPSSchemaAdapter adapter) throws Exception {
/*  615 */     boolean isCALID = ((SPSSession)this.session).isCALID();
/*  616 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  617 */     if (!isCALID) {
/*  618 */       vehicle.setWildcardControllerRNG((SPSController)null);
/*      */     }
/*  620 */     this.selections = options;
/*  621 */     int order = Integer.MAX_VALUE;
/*  622 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  635 */     while (it.hasNext()) {
/*  636 */       SPSControllerGME controller = it.next();
/*  637 */       if (isCALID && vehicle.getHardware() == null) {
/*  638 */         disableHWOCheck();
/*      */       }
/*  640 */       boolean accept = controller.qualify(order);
/*  641 */       if (!accept) {
/*  642 */         this.disqualified.add(controller);
/*  643 */         it.remove();
/*      */       } 
/*  645 */       if (isCALID) {
/*  646 */         enableHWOCheck();
/*      */       }
/*      */     } 
/*  649 */     if (!isCALID && vehicle.getWildcardControllerRNG() != null) {
/*  650 */       it = this.controllers.iterator();
/*  651 */       boolean exactMatchRNG = false;
/*  652 */       while (it.hasNext()) {
/*  653 */         SPSControllerGME controller = it.next();
/*  654 */         if (hasOptionDependencyRNG(controller) && !matchWildcardControllerRNG(vehicle, controller)) {
/*  655 */           exactMatchRNG = true;
/*      */         }
/*      */       } 
/*  658 */       if (exactMatchRNG) {
/*  659 */         this.controllers.removeAll(vehicle.getWildcardControllerRNG());
/*  660 */         this.disqualified.addAll(vehicle.getWildcardControllerRNG());
/*      */       } 
/*      */     } 
/*  663 */     if (this.controllers.size() == 0) {
/*  664 */       SPSOption engine = findEngineOptionSNOET();
/*  665 */       if (engine != null) {
/*  666 */         ((SPSVehicle)this.session.getVehicle()).removeDefaultOption(engine);
/*  667 */         options.remove(engine);
/*  668 */         reset();
/*  669 */         return qualify(options, adapter);
/*      */       } 
/*      */     } 
/*  672 */     return check();
/*      */   }
/*      */   
/*      */   protected boolean matchWildcardControllerRNG(SPSVehicle vehicle, SPSControllerGME controller) {
/*  676 */     List wildcardControllerListRNG = vehicle.getWildcardControllerRNG();
/*  677 */     if (wildcardControllerListRNG != null) {
/*  678 */       for (int i = 0; i < wildcardControllerListRNG.size(); i++) {
/*  679 */         if (controller.equals(wildcardControllerListRNG.get(i))) {
/*  680 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  684 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean hasOptionDependencyRNG(SPSControllerGME controller) {
/*      */     try {
/*  689 */       List<SPSBaseCategory> bcategories = controller.getOptionCategories();
/*  690 */       for (int i = 0; i < bcategories.size(); i++) {
/*  691 */         SPSBaseCategory bcategory = bcategories.get(i);
/*  692 */         if (bcategory.isEvaluated() && !bcategory.isOptionCategory() && bcategory.getGroup() != null && 
/*  693 */           bcategory.getCategory().getID().equals("RNG")) {
/*  694 */           return true;
/*      */         }
/*      */       }
/*      */     
/*  698 */     } catch (Exception e) {}
/*      */     
/*  700 */     return false;
/*      */   }
/*      */   
/*      */   protected void disableHWOCheck() {
/*  704 */     ((SPSSession)this.session).disableHWOCheck();
/*      */   }
/*      */   
/*      */   protected void enableHWOCheck() {
/*  708 */     ((SPSSession)this.session).enableHWOCheck();
/*      */   }
/*      */   
/*      */   protected SPSOption findEngineOptionSNOET() {
/*      */     try {
/*  713 */       List<SPSOption> options = this.session.getVehicle().getOptions();
/*  714 */       String snoet = ((SPSVehicle)this.session.getVehicle()).getEngine();
/*  715 */       for (int i = 0; i < options.size(); i++) {
/*  716 */         SPSOption option = options.get(i);
/*  717 */         if (option.getType().getID().equals("ENG")) {
/*  718 */           String engine = SPSVehicle.normalize(option.getDescription());
/*  719 */           if (engine.equals(snoet)) {
/*  720 */             return option;
/*      */           }
/*      */         } 
/*      */       } 
/*  724 */     } catch (Exception e) {}
/*      */     
/*  726 */     return null;
/*      */   }
/*      */   
/*      */   protected SPSController checkSelectedController(SPSControllerGME controller) throws Exception {
/*  730 */     if (this.sequence != null && 
/*  731 */       controller instanceof SPSControllerSequenceGME) {
/*  732 */       if (((SPSControllerSequenceGME)controller).checkQualifiedSequence()) {
/*  733 */         return this.controllers.get(0);
/*      */       }
/*  735 */       this.options = collectPostOptions();
/*  736 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  740 */     if (controller.getID() == SPSModel.DUMMY_ECU.intValue()) {
/*  741 */       log.info("Exception: No available Software");
/*  742 */       throw new SPSException(CommonException.SoftwareNotAvailable);
/*  743 */     }  if (!controller.checkHardware()) {
/*  744 */       this.disqualified.add(this.controllers.get(0));
/*  745 */       this.controllers.clear();
/*  746 */       log.info("Exception: Invalid Hardware");
/*  747 */       throw new SPSException(CommonException.InvalidHardware);
/*      */     } 
/*  749 */     if (!checkNonEvaluatedOptions(controller)) {
/*  750 */       this.options = collectPostOptions();
/*  751 */       return null;
/*      */     } 
/*  753 */     return this.controllers.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SPSController check() throws Exception {
/*  759 */     this.options = null;
/*  760 */     if (this.controllers.size() == 0) {
/*  761 */       log.info("Exception: No Controller Selection possible");
/*  762 */       throw new SPSException(CommonException.SoftwareNotAvailable);
/*  763 */     }  if (this.controllers.size() == 1) {
/*  764 */       return checkSelectedController(this.controllers.get(0));
/*      */     }
/*  766 */     if (!checkEvaluationState()) {
/*  767 */       Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  768 */       while (it.hasNext()) {
/*  769 */         SPSControllerGME controller = it.next();
/*  770 */         if (!controller.checkHardware()) {
/*  771 */           this.disqualified.add(controller);
/*  772 */           it.remove();
/*      */         } 
/*      */       } 
/*  775 */       if (this.controllers.size() == 1 || rejectWildcardSelections()) {
/*  776 */         return checkSelectedController(this.controllers.get(0));
/*      */       }
/*  778 */       it = this.controllers.iterator();
/*  779 */       while (it.hasNext()) {
/*  780 */         it.next();
/*      */       }
/*  782 */       log.info("Exception: Ambiguous Controller Selection");
/*  783 */       throw new SPSException(CommonException.AmbiguousControllerSelection);
/*      */     } 
/*      */     
/*  786 */     this.options = collectPostOptions();
/*      */     
/*  788 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkNonEvaluatedOptions(SPSControllerGME controller) throws Exception {
/*  793 */     List<SPSBaseCategory> vcategories = controller.getOptionCategories();
/*  794 */     if (vcategories == null) {
/*  795 */       return true;
/*      */     }
/*  797 */     for (int i = 0; i < vcategories.size(); i++) {
/*  798 */       SPSBaseCategory vcategory = vcategories.get(i);
/*  799 */       if (vcategory.isOptionCategory() && 
/*  800 */         !vcategory.isEvaluated()) {
/*  801 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  805 */     return true;
/*      */   }
/*      */   
/*      */   protected boolean rejectWildcardSelections() throws Exception {
/*  809 */     SPSControllerGME selected = null;
/*  810 */     Map<Object, Object> wildcards = new HashMap<Object, Object>();
/*  811 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  812 */     while (it.hasNext()) {
/*  813 */       SPSControllerGME controller = it.next();
/*  814 */       int order = Integer.MAX_VALUE;
/*  815 */       List<SPSBaseCategory> vcategories = controller.getOptionCategories();
/*  816 */       if (vcategories == null) {
/*      */         continue;
/*      */       }
/*  819 */       for (int i = 0; i < vcategories.size(); i++) {
/*  820 */         SPSBaseCategory vcategory = vcategories.get(i);
/*  821 */         if (!vcategory.isOptionCategory() && vcategory.getGroup() != null) {
/*  822 */           if (vcategory.getCategory().getID().equals("RNG")) {
/*  823 */             if (vcategory.getGroup().matchDefaultVINRange() && 
/*  824 */               order > vcategory.getOrder()) {
/*  825 */               order = vcategory.getOrder();
/*      */             }
/*      */           }
/*  828 */           else if (vcategory.getCategory().getID().equals("VIN")) {
/*  829 */             if (vcategory.getGroup().matchDefaultVINCode((SPSVIN)controller.getSession().getVehicle().getVIN()) && 
/*  830 */               order > vcategory.getOrder()) {
/*  831 */               order = vcategory.getOrder();
/*      */             }
/*      */           }
/*  834 */           else if (vcategory.getCategory().getID().equals("V10")) {
/*      */           
/*      */           } 
/*      */         }
/*      */       } 
/*  839 */       if (order != Integer.MAX_VALUE) {
/*  840 */         wildcards.put(controller, Integer.valueOf(order)); continue;
/*  841 */       }  if (selected == null) {
/*  842 */         selected = controller; continue;
/*      */       } 
/*  844 */       return false;
/*      */     } 
/*      */     
/*  847 */     if (selected != null) {
/*  848 */       removeWildcardControllers(wildcards.keySet());
/*  849 */       return true;
/*      */     } 
/*  851 */     int wildcard = -1;
/*  852 */     boolean draw = false;
/*  853 */     it = wildcards.keySet().iterator();
/*  854 */     while (it.hasNext()) {
/*  855 */       SPSControllerGME controller = it.next();
/*  856 */       Integer order = (Integer)wildcards.get(controller);
/*  857 */       if (order.intValue() > wildcard) {
/*  858 */         wildcard = order.intValue();
/*  859 */         selected = controller;
/*  860 */         draw = false; continue;
/*  861 */       }  if (order.intValue() == wildcard) {
/*  862 */         draw = true;
/*      */       }
/*      */     } 
/*  865 */     if (selected != null && !draw) {
/*  866 */       wildcards.remove(selected);
/*  867 */       removeWildcardControllers(wildcards.keySet());
/*      */     } 
/*      */     
/*  870 */     return (this.controllers.size() == 1);
/*      */   }
/*      */   
/*      */   protected void removeWildcardControllers(Set wildcards) {
/*  874 */     Iterator<SPSControllerGME> it = wildcards.iterator();
/*  875 */     while (it.hasNext()) {
/*  876 */       SPSControllerGME controller = it.next();
/*  877 */       this.disqualified.add(controller);
/*  878 */       this.controllers.remove(controller);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean checkEvaluationState() throws Exception {
/*  883 */     if (this.controllers.size() > 1) {
/*  884 */       Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  885 */       while (it.hasNext()) {
/*  886 */         SPSControllerGME controller = it.next();
/*  887 */         List<SPSBaseCategory> vcategories = controller.getOptionCategories();
/*  888 */         if (vcategories != null) {
/*  889 */           for (int i = 0; i < vcategories.size(); i++) {
/*  890 */             SPSBaseCategory vcategory = vcategories.get(i);
/*  891 */             if (!vcategory.isEvaluated()) {
/*  892 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*  897 */       return false;
/*      */     } 
/*  899 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public List collectPostOptions() throws Exception {
/*  904 */     List options = new ArrayList();
/*  905 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  906 */     while (it.hasNext()) {
/*  907 */       SPSControllerGME controller = it.next();
/*  908 */       addOptions(options, controller.getPostSelectionOptions());
/*      */     } 
/*  910 */     return options;
/*      */   }
/*      */   
/*      */   protected void addOptions(List<SPSOptionCategory> options, List<SPSOptionCategory> categories) {
/*  914 */     if (categories == null) {
/*      */       return;
/*      */     }
/*  917 */     for (int i = 0; i < categories.size(); i++) {
/*  918 */       SPSOptionCategory category = categories.get(i);
/*  919 */       boolean exists = false;
/*  920 */       for (int j = 0; j < options.size(); j++) {
/*  921 */         SPSOptionCategory master = options.get(j);
/*  922 */         if (master.equals(category)) {
/*  923 */           exists = true;
/*  924 */           SPSOptionCategory merge = (SPSOptionCategory)SPSOptionCategory.getOptionCategory(master, category);
/*  925 */           merge.setOrder(master.getOrder());
/*  926 */           options.set(j, merge);
/*      */         } 
/*      */       } 
/*  929 */       if (!exists) {
/*  930 */         options.add(category);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SPSController evaluate(AttributeValueMap data, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/*  939 */     if (method.getID().equals(SPSProgrammingType.VCI)) {
/*  940 */       return this.controllers.get(0);
/*      */     }
/*  942 */     if (this.sequence != null) {
/*  943 */       SPSControllerSequenceGME s = this.controllers.get(0);
/*  944 */       s.initSequence();
/*      */       do {
/*  946 */         evaluateVIT1(data, method, adapter);
/*  947 */       } while (s.advanceSequence());
/*      */     } else {
/*  949 */       evaluateVIT1(data, method, adapter);
/*      */     } 
/*      */     
/*  952 */     return qualify(this.session.getVehicle().getOptions(), adapter);
/*      */   }
/*      */   
/*      */   protected void evaluateVIT1(AttributeValueMap data, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/*  956 */     int vid = SPSModel.getInstance((SPSSchemaAdapterGME)adapter).evaluateVIT1(this.session, isEngineController(adapter));
/*  957 */     if (vid == -2) {
/*  958 */       log.error("Exception: Unknown Software");
/*  959 */       throw new SPSException(CommonException.UnknownSoftware);
/*  960 */     }  if (vid == -3) {
/*  961 */       log.error("Exception: Unknown Software");
/*  962 */       throw new SPSException(CommonException.UnknownSoftware);
/*  963 */     }  if (vid == -4) {
/*  964 */       log.error("Exception: Unknown VIN");
/*  965 */       throw new SPSException(CommonException.UnknownVIN);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHardware(SPSHardware part) {
/*  971 */     if (this.controllers.size() > 0) {
/*  972 */       for (int i = 0; i < this.controllers.size(); i++) {
/*  973 */         SPSControllerGME controller = this.controllers.get(i);
/*  974 */         controller.setHardware(part);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void reset() {
/*  980 */     if (this.disqualified != null && this.disqualified.size() > 0) {
/*  981 */       this.controllers.addAll(this.disqualified);
/*  982 */       this.disqualified.clear();
/*  983 */       this.rcategories = null;
/*  984 */       if (this.sequence != null) {
/*  985 */         this.sequence = null;
/*  986 */         Iterator<SPSControllerSequenceGME> iterator = this.controllers.iterator();
/*  987 */         while (iterator.hasNext()) {
/*  988 */           SPSControllerSequenceGME controller = iterator.next();
/*  989 */           controller.reset();
/*      */         } 
/*      */         return;
/*      */       } 
/*  993 */       Iterator<SPSControllerGME> it = this.controllers.iterator();
/*  994 */       while (it.hasNext()) {
/*  995 */         SPSControllerGME controller = it.next();
/*  996 */         controller.setArchive(null);
/*      */         try {
/*  998 */           List<SPSBaseCategory> vcategories = controller.getOptionCategories();
/*  999 */           if (vcategories == null) {
/*      */             continue;
/*      */           }
/* 1002 */           for (int i = 0; i < vcategories.size(); i++) {
/* 1003 */             SPSBaseCategory vcategory = vcategories.get(i);
/* 1004 */             vcategory.setEvaluated(false);
/*      */           } 
/* 1006 */         } catch (Exception x) {}
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List getProgrammingFunctions() {
/* 1013 */     if (this.functions != null) {
/* 1014 */       return this.functions;
/*      */     }
/* 1016 */     this.functions = new ArrayList(); int i;
/* 1017 */     for (i = 0; i < this.controllers.size(); i++) {
/* 1018 */       SPSControllerGME controller = this.controllers.get(i);
/* 1019 */       if (!(controller instanceof SPSControllerSequence))
/*      */       {
/*      */         
/* 1022 */         if (!this.functions.contains(controller.getDescription()))
/* 1023 */           this.functions.add(controller.getDescription()); 
/*      */       }
/*      */     } 
/* 1026 */     for (i = 0; i < this.functions.size(); i++) {
/* 1027 */       String display = this.functions.get(i);
/* 1028 */       this.functions.set(i, new ValueAdapter(display));
/*      */     } 
/* 1030 */     this.function = null;
/* 1031 */     return this.functions;
/*      */   }
/*      */   
/*      */   public List getProgrammingSequences() {
/* 1035 */     if (this.sequences != null) {
/* 1036 */       return this.sequences;
/*      */     }
/* 1038 */     List<SPSControllerGME> instances = new ArrayList(); int i;
/* 1039 */     for (i = 0; i < this.controllers.size(); i++) {
/* 1040 */       SPSControllerGME controller = this.controllers.get(i);
/* 1041 */       if (controller instanceof SPSControllerSequence) {
/* 1042 */         instances.add(controller);
/*      */       }
/*      */     } 
/* 1045 */     Collections.sort(instances, new Comparator<SPSControllerGME>() {
/*      */           public int compare(Object o1, Object o2) {
/* 1047 */             Integer order1 = ((SPSControllerSequence)o1).getDisplayOrder();
/* 1048 */             Integer order2 = ((SPSControllerSequence)o2).getDisplayOrder();
/* 1049 */             return order1.compareTo(order2);
/*      */           }
/*      */         });
/* 1052 */     this.sequences = new ArrayList();
/* 1053 */     for (i = 0; i < instances.size(); i++) {
/* 1054 */       SPSControllerSequence controller = (SPSControllerSequence)instances.get(i);
/* 1055 */       if (!this.sequences.contains(controller.getDisplay())) {
/* 1056 */         prepareProgrammingMethods(controller.getDisplay());
/* 1057 */         this.sequences.add(controller.getDisplay());
/*      */       } 
/*      */     } 
/* 1060 */     for (i = 0; i < this.sequences.size(); i++) {
/* 1061 */       String display = this.sequences.get(i);
/* 1062 */       this.sequences.set(i, new ValueAdapter(display));
/*      */     } 
/* 1064 */     this.sequence = null;
/* 1065 */     return this.sequences;
/*      */   }
/*      */   
/*      */   protected void prepareProgrammingMethods(String sequence) {
/* 1069 */     if (this.smethods == null) {
/* 1070 */       this.smethods = new HashMap<Object, Object>();
/*      */     }
/* 1072 */     this.smethods.put(sequence, getSequenceProgrammingMethods(sequence));
/*      */   }
/*      */   
/*      */   protected List getSequenceProgrammingMethods(String sequence) {
/* 1076 */     List<SPSProgrammingType> methods = new ArrayList();
/* 1077 */     methods.addAll(getProgrammingMethods());
/* 1078 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 1079 */       SPSController controller = this.controllers.get(i);
/* 1080 */       if (sequence.equals(((SPSControllerSequence)controller).getDisplay())) {
/* 1081 */         if (!((SPSControllerSequence)controller).supportsVCI()) {
/* 1082 */           for (int j = 0; j < methods.size(); j++) {
/* 1083 */             SPSProgrammingType ptype = methods.get(j);
/* 1084 */             if (ptype.getID().equals(SPSProgrammingType.VCI)) {
/* 1085 */               methods.remove(j);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1093 */     return methods;
/*      */   }
/*      */   
/*      */   public List getProgrammingMethods(String sequence) {
/* 1097 */     if (this.smethods != null) {
/* 1098 */       return (List)this.smethods.get(sequence);
/*      */     }
/* 1100 */     return getSequenceProgrammingMethods(sequence);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSequence() {
/* 1105 */     return this.sequence;
/*      */   }
/*      */   
/*      */   public void qualifySequence(String sequence) {
/* 1109 */     if (sequence.equals(this.sequence)) {
/*      */       return;
/*      */     }
/* 1112 */     this.sequence = sequence;
/* 1113 */     Iterator<SPSControllerSequence> it = this.controllers.iterator();
/* 1114 */     while (it.hasNext()) {
/* 1115 */       SPSControllerSequence controller = it.next();
/* 1116 */       if (!sequence.equals(controller.getDisplay())) {
/* 1117 */         this.disqualified.add(controller);
/* 1118 */         it.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void evaluateDefaultSelections(SPSSchemaAdapterGME adapter, SPSControllerList clist) throws Exception {
/* 1124 */     if (this.controllers.size() > 1 && 
/* 1125 */       clist.getOptions() == null) {
/* 1126 */       List<SPSOptionCategory> options = getPreOptions();
/* 1127 */       if (options.size() > 0 && this.session.getVehicle().getOptions() == null) {
/* 1128 */         SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/* 1129 */         boolean done = false;
/* 1130 */         String engine = SPSVehicle.normalize(vehicle.getEngine());
/* 1131 */         for (int i = 0; !done && i < options.size(); i++) {
/* 1132 */           SPSOptionCategory category = options.get(i);
/* 1133 */           List<SPSOption> alternatives = category.getOptions();
/* 1134 */           for (int j = 0; j < alternatives.size(); j++) {
/* 1135 */             SPSOption option = alternatives.get(j);
/* 1136 */             if (SPSVehicle.normalize(option.getDescription()).equals(engine)) {
/* 1137 */               vehicle.setOption(option);
/* 1138 */               clist.update(vehicle.getOptions(), adapter);
/* 1139 */               done = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void qualifyFunction(String function) {
/* 1150 */     if (function.equals(this.function)) {
/*      */       return;
/*      */     }
/* 1153 */     this.function = function;
/* 1154 */     Iterator<SPSControllerGME> it = this.controllers.iterator();
/* 1155 */     while (it.hasNext()) {
/* 1156 */       SPSControllerGME controller = it.next();
/* 1157 */       if (!function.equals(controller.getDescription())) {
/* 1158 */         this.disqualified.add(controller);
/* 1159 */         it.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */