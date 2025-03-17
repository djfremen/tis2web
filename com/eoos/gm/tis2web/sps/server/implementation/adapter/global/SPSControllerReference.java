/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSControllerReference
/*     */   extends SPSControllerReference {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   private static Logger log = Logger.getLogger(SPSControllerReference.class);
/*     */ 
/*     */   
/*     */   protected transient List controllers;
/*     */   
/*     */   protected transient List options;
/*     */   
/*     */   protected transient List disqualified;
/*     */   
/*     */   protected boolean isType4;
/*     */ 
/*     */   
/*     */   SPSControllerReference(SPSController controller) {
/*  37 */     this.controllers = new ArrayList();
/*  38 */     this.controllers.add(controller);
/*  39 */     this.description = controller.getDescription();
/*  40 */     this.methods = new ArrayList();
/*  41 */     add(controller.getProgrammingTypes());
/*  42 */     if (controller instanceof SPSControllerVCI) {
/*  43 */       this.isType4 = (controller.getDeviceID() == 253);
/*     */     }
/*     */   }
/*     */   
/*     */   List getControllers() {
/*  48 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public boolean isType4Application() {
/*  52 */     return this.isType4;
/*     */   }
/*     */   
/*     */   boolean accept(SPSController controller) {
/*  56 */     SPSController template = this.controllers.get(0);
/*  57 */     if (template instanceof SPSControllerVCI) {
/*  58 */       if (!(controller instanceof SPSControllerVCI)) {
/*  59 */         return false;
/*     */       }
/*  61 */       return (controller.getID() == template.getID() && ((SPSControllerVCI)controller).getDescIndex() == ((SPSControllerVCI)template).getDescIndex());
/*     */     } 
/*  63 */     if (controller instanceof SPSControllerVCI) {
/*  64 */       return false;
/*     */     }
/*  66 */     return (controller.getID() == template.getID());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSController checkDeviceDependency(AttributeValueMap data) {
/*  71 */     List devices = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*  72 */     if (devices == null) {
/*  73 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  74 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  75 */       return isCALID ? checkIdenticalVCI() : null;
/*     */     } 
/*  77 */     SPSController deviceController = null;
/*  78 */     Iterator<SPSController> it = this.controllers.iterator();
/*  79 */     while (it.hasNext()) {
/*  80 */       SPSController controller = it.next();
/*  81 */       if (controller instanceof SPSControllerVCI && 
/*  82 */         match(devices, controller)) {
/*  83 */         if (deviceController == null) {
/*  84 */           deviceController = controller; continue;
/*     */         } 
/*  86 */         log.warn("no unambiquous controller selection possible");
/*  87 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     if (deviceController != null) {
/*  93 */       it = this.controllers.iterator();
/*  94 */       while (it.hasNext()) {
/*  95 */         SPSController controller = it.next();
/*  96 */         if (controller != deviceController) {
/*  97 */           disqualify(controller);
/*  98 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return deviceController;
/*     */   }
/*     */   
/*     */   boolean match(int controller) {
/* 106 */     SPSController template = this.controllers.get(0);
/* 107 */     if (template instanceof SPSControllerVCI) {
/* 108 */       return (template.getID() == controller);
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void add(SPSController controller) {
/* 115 */     this.controllers.add(controller);
/* 116 */     add(controller.getProgrammingTypes());
/*     */   }
/*     */   
/*     */   protected void add(List cmethods) {
/* 120 */     for (int i = 0; i < cmethods.size(); i++) {
/* 121 */       if (!this.methods.contains(cmethods.get(i))) {
/* 122 */         this.methods.add(cmethods.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getOptions() {
/* 128 */     return this.options;
/*     */   }
/*     */   
/*     */   protected List getPreOptions() throws Exception {
/* 132 */     List options = new ArrayList();
/* 133 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 134 */       SPSController controller = this.controllers.get(i);
/* 135 */       List preoptions = controller.getPreSelectionOptions();
/* 136 */       if (preoptions != null) {
/* 137 */         addOption(options, preoptions);
/*     */       }
/*     */     } 
/* 140 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected List getPostOptions() throws Exception {
/* 144 */     List options = new ArrayList();
/* 145 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 146 */       SPSController controller = this.controllers.get(i);
/* 147 */       addOption(options, controller.getPostSelectionOptions());
/*     */     } 
/* 149 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected void addOption(List<SPSOption> options, List<SPSOption> criteria) {
/* 153 */     if (criteria != null) {
/* 154 */       for (int i = 0; i < criteria.size(); i++) {
/* 155 */         if (!options.contains(criteria.get(i))) {
/* 156 */           options.add(criteria.get(i));
/*     */           
/*     */           try {
/* 159 */             SPSOption option = criteria.get(i);
/* 160 */             if (option.getType() != null && option.getType() instanceof SPSOptionGroup) {
/* 161 */               List<SPSOption> members = ((SPSOptionGroup)option.getType()).getMembers();
/* 162 */               for (int j = 0; j < members.size(); j++) {
/* 163 */                 SPSOption member = members.get(j);
/* 164 */                 if (!options.contains(member)) {
/* 165 */                   options.add(member);
/*     */                 }
/*     */               } 
/*     */             } 
/* 169 */           } catch (Exception x) {}
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean match(SPSProgrammingType method, List methods) {
/* 177 */     for (int i = 0; i < methods.size(); i++) {
/* 178 */       if (method.equals(methods.get(i))) {
/* 179 */         return true;
/*     */       }
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   protected SPSController evaluate(AttributeValueMap data, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/* 186 */     if (method != null) {
/* 187 */       Iterator<SPSController> it = this.controllers.iterator();
/* 188 */       while (it.hasNext()) {
/* 189 */         SPSController sPSController = it.next();
/* 190 */         if (!match(method, sPSController.getProgrammingTypes())) {
/* 191 */           disqualify(sPSController);
/* 192 */           it.remove();
/*     */         } 
/*     */       } 
/* 195 */       if (method.getID().equals(SPSProgrammingType.VCI)) {
/* 196 */         return identifyControllerVCI();
/*     */       }
/*     */     } 
/* 199 */     this.options = collectPostOptions();
/*     */     
/* 201 */     if (this.options.size() > 0)
/* 202 */       return null; 
/* 203 */     if (this.controllers.size() == 0)
/* 204 */       return null; 
/* 205 */     if (this.controllers.size() == 1) {
/* 206 */       return this.controllers.get(0);
/*     */     }
/* 208 */     SPSController controller = checkDeviceDependency(data);
/* 209 */     if (controller != null) {
/* 210 */       return controller;
/*     */     }
/* 212 */     throw new SPSException(CommonException.InvalidControllerSelection);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSController identifyControllerVCI() throws Exception {
/* 217 */     SPSControllerVCI target = null;
/* 218 */     boolean unambiguous = true;
/* 219 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 220 */       if (this.controllers.get(i) instanceof SPSControllerVCI)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 225 */         if (target == null) {
/* 226 */           target = this.controllers.get(i);
/*     */         } else {
/* 228 */           SPSControllerVCI alternative = this.controllers.get(i);
/* 229 */           if (target.getDeviceID() != alternative.getDeviceID()) {
/* 230 */             unambiguous = false;
/*     */           }
/* 232 */           if (target.getRequestMethodID() != alternative.getRequestMethodID()) {
/* 233 */             unambiguous = false;
/*     */           }
/* 235 */           if (!match(target.getPreProgrammingInstructions(), alternative.getPreProgrammingInstructions())) {
/* 236 */             unambiguous = false;
/*     */           }
/* 238 */           if (!match(target.getPostProgrammingInstructions(), alternative.getPostProgrammingInstructions())) {
/* 239 */             unambiguous = false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 244 */     if (unambiguous) {
/* 245 */       this.options = collectPostOptions();
/* 246 */       return (this.options.size() > 0) ? null : target;
/*     */     } 
/* 248 */     this.options = collectPostOptions();
/* 249 */     if (this.options.size() > 0) {
/* 250 */       return null;
/*     */     }
/*     */     
/* 253 */     throw new SPSException(CommonException.NoUsableControllerSelection);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean match(List instructionsA, List instructionsB) {
/* 259 */     if (instructionsA != null && instructionsB == null)
/* 260 */       return false; 
/* 261 */     if (instructionsA == null && instructionsB != null)
/* 262 */       return false; 
/* 263 */     if (instructionsA == null && instructionsB == null) {
/* 264 */       return true;
/*     */     }
/* 266 */     return instructionsA.equals(instructionsB);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List collectPostOptions() throws Exception {
/* 271 */     List options = new ArrayList();
/* 272 */     Iterator<SPSController> it = this.controllers.iterator();
/* 273 */     while (it.hasNext()) {
/* 274 */       SPSController controller = it.next();
/* 275 */       addOption(options, controller.getPostSelectionOptions());
/*     */     } 
/* 277 */     return options;
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
/*     */   protected SPSController qualify(List options, SPSSchemaAdapter adapter) throws Exception {
/* 295 */     Iterator<SPSController> it = this.controllers.iterator();
/* 296 */     boolean hasPROM = false;
/* 297 */     while (it.hasNext()) {
/* 298 */       SPSController controller = it.next();
/* 299 */       if (controller instanceof SPSProgrammingSequence) {
/* 300 */         if (!((SPSProgrammingSequence)controller).qualify(options, (SPSSchemaAdapterGlobal)adapter)) {
/* 301 */           disqualify(controller);
/* 302 */           it.remove();
/*     */         }  continue;
/*     */       } 
/* 305 */       if (!SPSOption.match(options, controller.getPreSelectionOptions())) {
/* 306 */         disqualify(controller);
/* 307 */         it.remove();
/*     */       } 
/* 309 */       if (!SPSOption.match(options, controller.getPostSelectionOptions())) {
/* 310 */         disqualify(controller);
/* 311 */         it.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     if (hasPROM) {
/* 316 */       this.options = getPreOptions();
/* 317 */       if (options != null) {
/* 318 */         return null;
/*     */       }
/*     */     } 
/* 321 */     this.options = SPSOption.filter(options, collectPostOptions());
/*     */     
/* 323 */     if (this.options != null && this.options.size() > 0) {
/* 324 */       return null;
/*     */     }
/* 326 */     if (this.controllers.size() == 0)
/* 327 */       return null; 
/* 328 */     if (this.controllers.size() == 1) {
/* 329 */       return this.controllers.get(0);
/*     */     }
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean match(List devices, SPSController controller) {
/* 336 */     if (devices != null) {
/* 337 */       Iterator<Integer> it = devices.iterator();
/* 338 */       while (it.hasNext()) {
/* 339 */         Integer device = it.next();
/* 340 */         if (controller.getDeviceID() == device.intValue()) {
/* 341 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 345 */     return false;
/*     */   }
/*     */   
/*     */   protected SPSController checkIdenticalVCI() {
/* 349 */     SPSControllerVCI target = null;
/* 350 */     boolean sameVCI = true;
/* 351 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 352 */       if (this.controllers.get(i) instanceof SPSControllerVCI) {
/* 353 */         if (target == null) {
/* 354 */           target = this.controllers.get(i);
/*     */         } else {
/* 356 */           SPSControllerVCI alternative = this.controllers.get(i);
/* 357 */           if (target.getVCI() != alternative.getVCI()) {
/* 358 */             sameVCI = false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 363 */     if (sameVCI) {
/* 364 */       return target;
/*     */     }
/* 366 */     log.warn("no unambiquous controller selection possible");
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disqualify(SPSController controller) {
/* 372 */     if (this.disqualified == null) {
/* 373 */       this.disqualified = new ArrayList();
/*     */     }
/*     */     
/* 376 */     this.disqualified.add(controller);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 380 */     if (this.disqualified != null) {
/* 381 */       this.controllers.addAll(this.disqualified);
/* 382 */       this.disqualified = null;
/*     */     } 
/* 384 */     Iterator<SPSController> it = this.controllers.iterator();
/* 385 */     while (it.hasNext()) {
/* 386 */       SPSController controller = it.next();
/* 387 */       if (controller instanceof SPSControllerVCI) {
/* 388 */         ((SPSControllerVCI)controller).setArchive(null);
/* 389 */         ((SPSControllerVCI)controller).setPartFile(null); continue;
/* 390 */       }  if (controller instanceof SPSProgrammingSequence) {
/* 391 */         ((SPSProgrammingSequence)controller).reset();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getProgrammingFunctions() {
/* 397 */     return null;
/*     */   }
/*     */   
/*     */   public List getProgrammingSequences() {
/* 401 */     return null;
/*     */   }
/*     */   
/*     */   public List getProgrammingMethods(String sequence) {
/* 405 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSControllerReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */