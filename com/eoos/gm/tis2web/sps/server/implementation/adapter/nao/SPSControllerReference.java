/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
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
/*     */   String getType() {
/*  56 */     SPSController template = this.controllers.get(0);
/*  57 */     if (template instanceof SPSControllerPROM) {
/*  58 */       return "pluggable";
/*     */     }
/*  60 */     return "reprogrammable";
/*     */   }
/*     */ 
/*     */   
/*     */   boolean accept(SPSController controller) {
/*  65 */     SPSController template = this.controllers.get(0);
/*  66 */     if (template instanceof SPSControllerVCI) {
/*  67 */       if (!(controller instanceof SPSControllerVCI)) {
/*  68 */         return false;
/*     */       }
/*  70 */       return (controller.getID() == template.getID() && ((SPSControllerVCI)controller).getDescIndex() == ((SPSControllerVCI)template).getDescIndex());
/*     */     } 
/*  72 */     if (controller instanceof SPSControllerVCI) {
/*  73 */       return false;
/*     */     }
/*  75 */     return (controller.getID() == template.getID());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSController checkDeviceDependency(AttributeValueMap data) {
/*  80 */     List devices = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*  81 */     if (devices == null) {
/*  82 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  83 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  84 */       return isCALID ? checkIdenticalVCI() : null;
/*     */     } 
/*  86 */     SPSController deviceController = null;
/*  87 */     Iterator<SPSController> it = this.controllers.iterator();
/*  88 */     while (it.hasNext()) {
/*  89 */       SPSController controller = it.next();
/*  90 */       if (controller instanceof SPSControllerVCI && 
/*  91 */         match(devices, controller)) {
/*  92 */         if (deviceController == null) {
/*  93 */           deviceController = controller; continue;
/*     */         } 
/*  95 */         log.warn("no unambiquous controller selection possible");
/*  96 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 101 */     if (deviceController != null) {
/* 102 */       it = this.controllers.iterator();
/* 103 */       while (it.hasNext()) {
/* 104 */         SPSController controller = it.next();
/* 105 */         if (controller != deviceController) {
/* 106 */           disqualify(controller);
/* 107 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/* 111 */     return deviceController;
/*     */   }
/*     */   
/*     */   boolean match(int controller) {
/* 115 */     SPSController template = this.controllers.get(0);
/* 116 */     if (template instanceof SPSControllerVCI) {
/* 117 */       return (template.getID() == controller);
/*     */     }
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void add(SPSController controller) {
/* 124 */     this.controllers.add(controller);
/* 125 */     add(controller.getProgrammingTypes());
/*     */   }
/*     */   
/*     */   protected void add(List cmethods) {
/* 129 */     for (int i = 0; i < cmethods.size(); i++) {
/* 130 */       if (!this.methods.contains(cmethods.get(i))) {
/* 131 */         this.methods.add(cmethods.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getOptions() {
/* 137 */     return this.options;
/*     */   }
/*     */   
/*     */   protected List getPreOptions() throws Exception {
/* 141 */     List options = new ArrayList();
/* 142 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 143 */       SPSController controller = this.controllers.get(i);
/* 144 */       List preoptions = controller.getPreSelectionOptions();
/* 145 */       if (preoptions != null) {
/* 146 */         addOption(options, preoptions);
/*     */       }
/*     */     } 
/* 149 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected List getPostOptions() throws Exception {
/* 153 */     List options = new ArrayList();
/* 154 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 155 */       SPSController controller = this.controllers.get(i);
/* 156 */       addOption(options, controller.getPostSelectionOptions());
/*     */     } 
/* 158 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected void addOption(List<SPSOption> options, List<SPSOption> criteria) {
/* 162 */     if (criteria != null) {
/* 163 */       for (int i = 0; i < criteria.size(); i++) {
/* 164 */         if (!options.contains(criteria.get(i))) {
/* 165 */           options.add(criteria.get(i));
/*     */           
/*     */           try {
/* 168 */             SPSOption option = criteria.get(i);
/* 169 */             if (option.getType() != null && option.getType() instanceof SPSOptionGroup) {
/* 170 */               List<SPSOption> members = ((SPSOptionGroup)option.getType()).getMembers();
/* 171 */               for (int j = 0; j < members.size(); j++) {
/* 172 */                 SPSOption member = members.get(j);
/* 173 */                 if (!options.contains(member)) {
/* 174 */                   options.add(member);
/*     */                 }
/*     */               } 
/*     */             } 
/* 178 */           } catch (Exception x) {}
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean match(SPSProgrammingType method, List methods) {
/* 186 */     for (int i = 0; i < methods.size(); i++) {
/* 187 */       if (method.equals(methods.get(i))) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   protected SPSController evaluate(AttributeValueMap data, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/* 195 */     if (method != null) {
/* 196 */       Iterator<SPSController> it = this.controllers.iterator();
/* 197 */       while (it.hasNext()) {
/* 198 */         SPSController sPSController = it.next();
/* 199 */         if (!match(method, sPSController.getProgrammingTypes())) {
/* 200 */           disqualify(sPSController);
/* 201 */           it.remove();
/*     */         } 
/*     */       } 
/* 204 */       if (method.getID().equals(SPSProgrammingType.VCI)) {
/* 205 */         return identifyControllerVCI();
/*     */       }
/*     */     } 
/* 208 */     this.options = collectPostOptions();
/*     */     
/* 210 */     if (this.options.size() > 0)
/* 211 */       return null; 
/* 212 */     if (this.controllers.size() == 0)
/* 213 */       return null; 
/* 214 */     if (this.controllers.size() == 1) {
/* 215 */       return this.controllers.get(0);
/*     */     }
/* 217 */     SPSController controller = checkDeviceDependency(data);
/* 218 */     if (controller != null) {
/* 219 */       return controller;
/*     */     }
/* 221 */     throw new SPSException(CommonException.InvalidControllerSelection);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSController identifyControllerVCI() throws Exception {
/* 226 */     SPSControllerVCI target = null;
/* 227 */     boolean unambiguous = true;
/* 228 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 229 */       if (this.controllers.get(i) instanceof SPSControllerVCI)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 234 */         if (target == null) {
/* 235 */           target = this.controllers.get(i);
/*     */         } else {
/* 237 */           SPSControllerVCI alternative = this.controllers.get(i);
/* 238 */           if (target.getDeviceID() != alternative.getDeviceID()) {
/* 239 */             unambiguous = false;
/*     */           }
/* 241 */           if (target.getRequestMethodID() != alternative.getRequestMethodID()) {
/* 242 */             unambiguous = false;
/*     */           }
/* 244 */           if (!match(target.getPreProgrammingInstructions(), alternative.getPreProgrammingInstructions())) {
/* 245 */             unambiguous = false;
/*     */           }
/* 247 */           if (!match(target.getPostProgrammingInstructions(), alternative.getPostProgrammingInstructions())) {
/* 248 */             unambiguous = false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 253 */     if (unambiguous) {
/* 254 */       this.options = collectPostOptions();
/* 255 */       return (this.options.size() > 0) ? null : target;
/*     */     } 
/* 257 */     this.options = collectPostOptions();
/* 258 */     if (this.options.size() > 0) {
/* 259 */       return null;
/*     */     }
/*     */     
/* 262 */     throw new SPSException(CommonException.NoUsableControllerSelection);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean match(List instructionsA, List instructionsB) {
/* 268 */     if (instructionsA != null && instructionsB == null)
/* 269 */       return false; 
/* 270 */     if (instructionsA == null && instructionsB != null)
/* 271 */       return false; 
/* 272 */     if (instructionsA == null && instructionsB == null) {
/* 273 */       return true;
/*     */     }
/* 275 */     return instructionsA.equals(instructionsB);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List collectPostOptions() throws Exception {
/* 280 */     List options = new ArrayList();
/* 281 */     Iterator<SPSController> it = this.controllers.iterator();
/* 282 */     while (it.hasNext()) {
/* 283 */       SPSController controller = it.next();
/* 284 */       addOption(options, controller.getPostSelectionOptions());
/*     */     } 
/* 286 */     return options;
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
/* 304 */     Iterator<SPSController> it = this.controllers.iterator();
/* 305 */     boolean hasPROM = false;
/* 306 */     while (it.hasNext()) {
/* 307 */       SPSController controller = it.next();
/* 308 */       if (controller instanceof SPSControllerPROM) {
/* 309 */         hasPROM = true;
/* 310 */         if (!SPSOption.contains(options, controller.getPreSelectionOptions())) {
/* 311 */           disqualify(controller);
/* 312 */           it.remove(); continue;
/*     */         } 
/* 314 */         ((SPSControllerPROM)controller).update(adapter); continue;
/*     */       } 
/* 316 */       if (controller instanceof SPSProgrammingSequence) {
/* 317 */         if (!((SPSProgrammingSequence)controller).qualify(options, (SPSSchemaAdapterNAO)adapter)) {
/* 318 */           disqualify(controller);
/* 319 */           it.remove();
/*     */         }  continue;
/*     */       } 
/* 322 */       if (!SPSOption.match(options, controller.getPreSelectionOptions())) {
/* 323 */         disqualify(controller);
/* 324 */         it.remove();
/*     */       } 
/* 326 */       if (!SPSOption.match(options, controller.getPostSelectionOptions())) {
/* 327 */         disqualify(controller);
/* 328 */         it.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     if (hasPROM) {
/* 333 */       this.options = getPreOptions();
/* 334 */       if (options != null) {
/* 335 */         return null;
/*     */       }
/*     */     } 
/* 338 */     this.options = SPSOption.filter(options, collectPostOptions());
/*     */     
/* 340 */     if (this.options != null && this.options.size() > 0) {
/* 341 */       return null;
/*     */     }
/* 343 */     if (this.controllers.size() == 0)
/* 344 */       return null; 
/* 345 */     if (this.controllers.size() == 1) {
/* 346 */       return this.controllers.get(0);
/*     */     }
/* 348 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean match(List devices, SPSController controller) {
/* 353 */     if (devices != null) {
/* 354 */       Iterator<Integer> it = devices.iterator();
/* 355 */       while (it.hasNext()) {
/* 356 */         Integer device = it.next();
/* 357 */         if (controller.getDeviceID() == device.intValue()) {
/* 358 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 362 */     return false;
/*     */   }
/*     */   
/*     */   protected SPSController checkIdenticalVCI() {
/* 366 */     SPSControllerVCI target = null;
/* 367 */     boolean sameVCI = true;
/* 368 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 369 */       if (this.controllers.get(i) instanceof SPSControllerVCI) {
/* 370 */         if (target == null) {
/* 371 */           target = this.controllers.get(i);
/*     */         } else {
/* 373 */           SPSControllerVCI alternative = this.controllers.get(i);
/* 374 */           if (target.getVCI() != alternative.getVCI()) {
/* 375 */             sameVCI = false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 380 */     if (sameVCI) {
/* 381 */       return target;
/*     */     }
/* 383 */     log.warn("no unambiquous controller selection possible");
/* 384 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disqualify(SPSController controller) {
/* 389 */     if (this.disqualified == null) {
/* 390 */       this.disqualified = new ArrayList();
/*     */     }
/*     */     
/* 393 */     this.disqualified.add(controller);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 397 */     if (this.disqualified != null) {
/* 398 */       this.controllers.addAll(this.disqualified);
/* 399 */       this.disqualified = null;
/*     */     } 
/* 401 */     Iterator<SPSController> it = this.controllers.iterator();
/* 402 */     while (it.hasNext()) {
/* 403 */       SPSController controller = it.next();
/* 404 */       if (controller instanceof SPSControllerVCI) {
/* 405 */         ((SPSControllerVCI)controller).setArchive(null);
/* 406 */         ((SPSControllerVCI)controller).setPartFile(null); continue;
/* 407 */       }  if (controller instanceof SPSProgrammingSequence) {
/* 408 */         ((SPSProgrammingSequence)controller).reset();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getProgrammingFunctions() {
/* 414 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getProgrammingSequences() {
/* 418 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getProgrammingMethods(String sequence) {
/* 422 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */