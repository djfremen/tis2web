/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sids.implementation.DisplayableServiceIDItem;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.DisplayableServiceIDAttr;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVIN;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCMake;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCModel;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCModelYear;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VehicleContextData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSVIN
/*     */   implements SPSVIN
/*     */ {
/*     */   protected static final int MAKE = 3;
/*     */   protected static final int CAR_LINE = 5;
/*     */   protected static final int SERIES = 6;
/*     */   protected static final int ENGINE = 8;
/*     */   protected static final int CHECK_DIGIT = 9;
/*     */   protected static final int MODEL_YEAR = 10;
/*     */   protected String sessionID;
/*     */   protected SPSModel modelSPS;
/*     */   protected String vin;
/*     */   protected String make;
/*     */   protected String model;
/*     */   protected String year;
/*     */   protected String engine;
/*     */   protected String transmission;
/*     */   protected VCConfiguration vcc;
/*     */   protected List makes;
/*     */   
/*     */   public String getSalesMakeVC() {
/*  64 */     return this.make;
/*     */   }
/*     */   
/*     */   public String getModelVC() {
/*  68 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getModelYearVC() {
/*  72 */     return this.year;
/*     */   }
/*     */   
/*     */   public String getEngineVC() {
/*  76 */     return this.engine;
/*     */   }
/*     */   
/*     */   public String getTransmissionVC() {
/*  80 */     return this.transmission;
/*     */   }
/*     */   
/*     */   public void setSalesMakeVC(String make) {
/*  84 */     this.make = make;
/*     */   }
/*     */   
/*     */   public void setModelVC(String model) {
/*  88 */     this.model = model;
/*     */   }
/*     */   
/*     */   public void setModelYearVC(String year) {
/*  92 */     this.year = year;
/*     */   }
/*     */   
/*     */   public SPSVIN(String sessionID, AttributeValueMap data, SPSModel model, String vin) {
/*  96 */     this.sessionID = sessionID;
/*  97 */     this.modelSPS = model;
/*  98 */     this.vin = vin;
/*  99 */     extractAttributes(data);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     return this.vin;
/*     */   }
/*     */   
/*     */   protected void extractAttributes(AttributeValueMap data) {
/* 107 */     Iterator attributes = data.getAttributes().iterator();
/* 108 */     while (attributes.hasNext()) {
/* 109 */       Object attribute = attributes.next();
/* 110 */       if (attribute instanceof DisplayableServiceIDAttr) {
/* 111 */         DisplayableServiceIDItem value = (DisplayableServiceIDItem)AVUtil.accessValue(data, (Attribute)attribute);
/* 112 */         if (value == null) {
/*     */           continue;
/*     */         }
/* 115 */         String description = ((DisplayableServiceIDAttr)attribute).getDescription();
/* 116 */         if (description.equals("Make") || description.equalsIgnoreCase("badge")) {
/* 117 */           String vcmake = checkAttribute("Make", value.getDenotation(null));
/* 118 */           if (this.make == null)
/* 119 */             this.make = vcmake; 
/*     */           continue;
/*     */         } 
/* 122 */         if (description.equals("Model")) {
/* 123 */           this.model = checkAttribute("Model", value.getDenotation(null)); continue;
/* 124 */         }  if (description.equals("ModelYear")) {
/* 125 */           this.year = checkAttribute("ModelYear", value.getDenotation(null)); continue;
/* 126 */         }  if (description.equals("Engine")) {
/* 127 */           this.engine = checkAttribute("Engine", value.getDenotation(null)); continue;
/* 128 */         }  if (description.equals("Transmission"))
/* 129 */           this.transmission = checkAttribute("Transmission", value.getDenotation(null));  continue;
/*     */       } 
/* 131 */       if (attribute.equals(CommonAttribute.SALESMAKE)) {
/* 132 */         this.make = (String)AVUtil.accessValue(data, CommonAttribute.SALESMAKE); continue;
/* 133 */       }  if (attribute.equals(CommonAttribute.MODEL)) {
/* 134 */         this.model = (String)AVUtil.accessValue(data, CommonAttribute.MODEL); continue;
/* 135 */       }  if (attribute.equals(CommonAttribute.MODELYEAR)) {
/* 136 */         this.year = (String)AVUtil.accessValue(data, CommonAttribute.MODELYEAR); continue;
/* 137 */       }  if (attribute.equals(CommonAttribute.ENGINE)) {
/* 138 */         this.engine = (String)AVUtil.accessValue(data, CommonAttribute.ENGINE); continue;
/* 139 */       }  if (attribute.equals(CommonAttribute.TRANSMISSION)) {
/* 140 */         this.transmission = (String)AVUtil.accessValue(data, CommonAttribute.TRANSMISSION);
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String checkAttribute(String domain, String value) {
/*     */     try {
/* 170 */       VCProxy vc = this.modelSPS.getVC();
/* 171 */       List<VCRValue> values = vc.getDomain(domain);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (values != null) {
/* 177 */         for (int i = 0; i < values.size(); i++) {
/* 178 */           VCRValue item = values.get(i);
/* 179 */           if (value.equalsIgnoreCase(item.toString())) {
/* 180 */             return item.toString();
/*     */           }
/*     */         } 
/*     */       }
/* 184 */     } catch (Exception x) {}
/*     */     
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   public boolean confirm(String candidate) {
/* 190 */     if (this.vcc == null) {
/* 191 */       return false;
/*     */     }
/* 193 */     candidate = SPSVehicle.normalize(candidate);
/* 194 */     List associations = this.vcc.getAssociations();
/* 195 */     for (int i = 0; i < associations.size(); i++) {
/* 196 */       Object element = associations.get(i);
/* 197 */       if (element instanceof com.eoos.gm.tis2web.vc.service.cai.VCEngine) {
/* 198 */         String engine = SPSVehicle.normalize(element.toString());
/* 199 */         if (engine.equals(candidate)) {
/* 200 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   public boolean validate() throws Exception {
/*     */     try {
/* 210 */       VCProxy vc = this.modelSPS.getVC();
/* 211 */       List configurations = vc.resolve(this.vin);
/* 212 */       configurations = checkModelYearWildcardConfigurations(vc, configurations);
/* 213 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.sessionID, false);
/* 214 */       List<List> result = VehicleContextData.getInstance(context).filterAuthorizedConfigurations(configurations, vc.getLVCAdapter());
/* 215 */       if (result == null) {
/* 216 */         if (configurations != null) {
/* 217 */           throw new SPSException(CommonException.NoAuthorization);
/*     */         }
/* 219 */         return false;
/*     */       } 
/* 221 */       if (result.size() > 1) {
/* 222 */         List<E> makes = extractSalesMakeList(result);
/* 223 */         if (makes == null || makes.size() == 0) {
/* 224 */           return false;
/*     */         }
/* 226 */         if (this.make != null) {
/* 227 */           for (int j = 0; j < result.size(); j++) {
/* 228 */             if (this.make.equalsIgnoreCase(makes.get(j).toString())) {
/* 229 */               result = result.get(j);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 234 */           requestSalesMakeSelection(vc, makes);
/*     */         } 
/*     */       } else {
/* 237 */         result = result.get(0);
/*     */       } 
/* 239 */       for (int i = 0; i < result.size(); i++) {
/* 240 */         Object attribute = result.get(i);
/* 241 */         if (attribute instanceof VCConfiguration) {
/* 242 */           VCConfiguration vcc = (VCConfiguration)attribute;
/* 243 */           this.vcc = vcc;
/* 244 */           List elements = vcc.getElements();
/* 245 */           for (int j = 0; j < elements.size(); j++) {
/* 246 */             Object element = elements.get(j);
/* 247 */             if (element instanceof VCMake) {
/* 248 */               this.make = element.toString();
/*     */             
/*     */             }
/* 251 */             else if (element instanceof VCModel) {
/* 252 */               this.model = element.toString();
/*     */             }
/* 254 */             else if (element instanceof VCModelYear) {
/* 255 */               this.year = element.toString();
/*     */             }
/*     */           
/*     */           } 
/* 259 */         } else if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCEngine) {
/* 260 */           this.engine = attribute.toString();
/*     */         }
/* 262 */         else if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCTransmission) {
/* 263 */           this.transmission = attribute.toString();
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       return true;
/* 268 */     } catch (Exception x) {
/* 269 */       if (x instanceof RequestException) {
/* 270 */         throw x;
/*     */       }
/* 272 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inspectConfigurations(List configurations, List<Object> makes, List<Object> models) {
/* 278 */     if (configurations != null) {
/* 279 */       for (int i = 0; i < configurations.size(); i++) {
/* 280 */         Object element = configurations.get(i);
/* 281 */         if (element instanceof List) {
/* 282 */           inspectConfigurations((List)element, makes, models);
/* 283 */         } else if (element instanceof VCConfiguration) {
/* 284 */           VCConfiguration vcc = (VCConfiguration)element;
/* 285 */           List elements = vcc.getElements();
/* 286 */           for (int k = 0; k < elements.size(); k++) {
/* 287 */             Object attribute = elements.get(k);
/* 288 */             if (attribute instanceof VCMake) {
/* 289 */               makes.add(attribute);
/*     */             }
/*     */           } 
/* 292 */         } else if (element instanceof VCMake) {
/* 293 */           makes.add(element);
/* 294 */         } else if (element instanceof VCModel) {
/* 295 */           models.add(element);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected List checkModelYearWildcardConfigurations(VCProxy vc, List configurations) throws Exception {
/* 302 */     List makes = new ArrayList();
/* 303 */     List models = new ArrayList();
/* 304 */     inspectConfigurations(configurations, makes, models);
/* 305 */     if (models.size() == 0) {
/* 306 */       return configurations;
/*     */     }
/* 308 */     List<VCModelYear> years = new ArrayList();
/* 309 */     VCMake make = checkModelYearWildcardMakes(vc, makes);
/* 310 */     List<VCConfiguration> vccs = make.getConfigurations();
/* 311 */     if (vccs != null) {
/* 312 */       for (int j = 0; j < vccs.size(); j++) {
/* 313 */         VCConfiguration vcc = vccs.get(j);
/* 314 */         if (match(vcc, models)) {
/* 315 */           VCModelYear my = extractModelYear(vcc);
/* 316 */           if (my != null && !years.contains(my)) {
/* 317 */             years.add(my);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 322 */     if (years.size() == 0)
/* 323 */       throw new SPSException(CommonException.NoAuthorization); 
/* 324 */     if (this.year != null)
/*     */     {
/* 326 */       return filterWildcardConfigurations(configurations);
/*     */     }
/* 328 */     List<ValueAdapter> values = new ArrayList();
/* 329 */     for (int i = 0; i < years.size(); i++) {
/* 330 */       ValueAdapter choice = new ValueAdapter(years.get(i).toString());
/* 331 */       values.add(choice);
/*     */     } 
/* 333 */     Collections.sort(values);
/* 334 */     SelectionRequest selectionRequest = SPSSchemaAdapterGME.builder.makeSelectionRequest(CommonAttribute.MODELYEAR, values, null, null);
/* 335 */     throw new RequestException(selectionRequest);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List filterWildcardConfigurations(List configurations) {
/* 341 */     if (configurations == null) {
/* 342 */       return null;
/*     */     }
/* 344 */     Iterator<List> it = configurations.iterator();
/* 345 */     while (it.hasNext()) {
/* 346 */       List results = it.next();
/* 347 */       if (!matchWildcardConfiguration(results)) {
/* 348 */         it.remove();
/*     */       }
/*     */     } 
/* 351 */     return (configurations.size() == 0) ? null : configurations;
/*     */   }
/*     */   
/*     */   protected boolean matchWildcardConfiguration(List<VCConfiguration> results) {
/* 355 */     VCModel model = null;
/* 356 */     Iterator it = results.iterator();
/* 357 */     while (it.hasNext()) {
/* 358 */       Object attribute = it.next();
/* 359 */       if (attribute instanceof VCMake) {
/* 360 */         if (!this.make.equals(attribute.toString())) {
/* 361 */           return false;
/*     */         }
/* 363 */         it.remove(); continue;
/* 364 */       }  if (attribute instanceof VCModel) {
/* 365 */         model = (VCModel)attribute;
/* 366 */         it.remove();
/*     */       } 
/*     */     } 
/* 369 */     List<VCConfiguration> configurations = model.getConfigurations();
/* 370 */     if (configurations != null) {
/* 371 */       for (int i = 0; i < configurations.size(); i++) {
/* 372 */         VCConfiguration vcc = configurations.get(i);
/* 373 */         List elements = vcc.getElements();
/* 374 */         for (int j = 0; j < elements.size(); j++) {
/* 375 */           Object element = elements.get(j);
/* 376 */           if (element instanceof VCMake) {
/* 377 */             if (!this.make.equals(element.toString())) {
/*     */               break;
/*     */             }
/* 380 */           } else if (element instanceof VCModelYear && 
/* 381 */             this.year.equals(element.toString())) {
/* 382 */             results.add(0, vcc);
/* 383 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 389 */     return false;
/*     */   }
/*     */   
/*     */   protected VCModelYear extractModelYear(VCConfiguration vcc) {
/* 393 */     List<VCRValue> attributes = vcc.getElements();
/* 394 */     for (int k = 0; k < attributes.size(); k++) {
/* 395 */       VCRValue value = attributes.get(k);
/* 396 */       if (value instanceof VCModelYear) {
/* 397 */         return (VCModelYear)value;
/*     */       }
/*     */     } 
/* 400 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean match(VCConfiguration vcc, List<VCModel> models) {
/* 404 */     for (int i = 0; i < models.size(); i++) {
/* 405 */       VCModel model = models.get(i);
/* 406 */       if (vcc.match((VCValue)model)) {
/* 407 */         return true;
/*     */       }
/*     */     } 
/* 410 */     return false;
/*     */   }
/*     */   
/*     */   protected VCMake checkModelYearWildcardMakes(VCProxy vc, List<VCMake> makes) throws Exception {
/* 414 */     List<String> values = new ArrayList();
/* 415 */     VCMake make = null;
/* 416 */     for (int i = 0; i < makes.size(); i++) {
/* 417 */       make = makes.get(i);
/* 418 */       String label = make.toString();
/* 419 */       if (checkAttribute("Make", label) != null)
/*     */       {
/* 421 */         if (this.make == null || this.make.equals(label))
/*     */         {
/*     */           
/* 424 */           if (!values.contains(label)) {
/* 425 */             values.add(label);
/*     */           }
/*     */         }
/*     */       }
/*     */     } 
/* 430 */     if (values.size() == 0)
/* 431 */       throw new SPSException(CommonException.NoAuthorization); 
/* 432 */     if (values.size() == 1) {
/* 433 */       return make;
/*     */     }
/* 435 */     requestSalesMakeSelection(vc, values);
/*     */     
/* 437 */     return null;
/*     */   }
/*     */   
/*     */   protected void requestSalesMakeSelection(VCProxy vc, List<E> makes) throws Exception {
/* 441 */     String vcmake = vc.getSalesmake(this.sessionID);
/* 442 */     ValueAdapter selection = null;
/* 443 */     List<ValueAdapter> values = new ArrayList();
/* 444 */     for (int i = 0; i < makes.size(); i++) {
/* 445 */       ValueAdapter choice = new ValueAdapter(makes.get(i));
/* 446 */       values.add(choice);
/* 447 */       if (vcmake != null && vcmake.equalsIgnoreCase(makes.get(i).toString())) {
/* 448 */         selection = choice;
/*     */       }
/*     */     } 
/* 451 */     SelectionRequest selectionRequest = SPSSchemaAdapterGME.builder.makeSelectionRequest(CommonAttribute.SALESMAKE, values, (Value)selection, null);
/* 452 */     throw new RequestException(selectionRequest);
/*     */   }
/*     */   
/*     */   protected List extractSalesMakeList(List<List> result) {
/* 456 */     List<String> makes = new ArrayList();
/* 457 */     for (int i = 0; i < result.size(); i++) {
/* 458 */       List record = result.get(i);
/* 459 */       for (int j = 0; j < record.size(); j++) {
/* 460 */         Object attribute = record.get(j);
/* 461 */         if (attribute instanceof VCConfiguration) {
/* 462 */           VCConfiguration vcc = (VCConfiguration)attribute;
/* 463 */           List elements = vcc.getElements();
/* 464 */           for (int k = 0; k < elements.size(); k++) {
/* 465 */             Object element = elements.get(k);
/* 466 */             if (element instanceof VCMake && 
/* 467 */               checkAttribute("Make", element.toString()) != null)
/*     */             {
/* 469 */               makes.add(element.toString());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 476 */     return makes;
/*     */   }
/*     */   
/*     */   public char getPosition(int pos) {
/* 480 */     return this.vin.charAt(pos - 1);
/*     */   }
/*     */   
/*     */   public String getWMI() {
/* 484 */     return this.vin.substring(0, 3);
/*     */   }
/*     */   
/*     */   public char getMake() {
/* 488 */     return getPosition(3);
/*     */   }
/*     */   
/*     */   public char getLine() {
/* 492 */     return getPosition(5);
/*     */   }
/*     */   
/*     */   public char getSeries() {
/* 496 */     return getPosition(6);
/*     */   }
/*     */   
/*     */   public char getEngine() {
/* 500 */     return getPosition(8);
/*     */   }
/*     */   
/*     */   public char getCheckDigit() {
/* 504 */     return getPosition(9);
/*     */   }
/*     */   
/*     */   public char getModelYear() {
/* 508 */     return getPosition(10);
/*     */   }
/*     */   
/*     */   public String getSequence() {
/* 512 */     return this.vin.substring(11);
/*     */   }
/*     */   
/*     */   public int getSequenceNo() {
/* 516 */     String sequence = getSequence();
/* 517 */     StringBuffer number = new StringBuffer();
/* 518 */     for (int i = 0; i < sequence.length(); i++) {
/* 519 */       char c = sequence.charAt(i);
/* 520 */       number.append(Character.isDigit(c) ? c : 48);
/*     */     } 
/* 522 */     return Integer.parseInt(number.toString());
/*     */   }
/*     */   
/*     */   boolean match(SPSVINRange range) {
/* 526 */     return match(range.getFromSN(), range.getToSN());
/*     */   }
/*     */   
/*     */   boolean match(String snFrom) {
/* 530 */     String sequence = this.vin.substring(10);
/* 531 */     if (snFrom != null) {
/* 532 */       snFrom = replaceWildcard(snFrom, sequence);
/* 533 */       if (snFrom.compareTo(sequence) > 0) {
/* 534 */         return false;
/*     */       }
/*     */     } 
/* 537 */     return true;
/*     */   }
/*     */   
/*     */   boolean matchSN(String snFrom) {
/* 541 */     String sequence = this.vin.substring(10);
/* 542 */     if (snFrom != null) {
/* 543 */       return snFrom.equals(sequence);
/*     */     }
/* 545 */     return true;
/*     */   }
/*     */   
/*     */   boolean match(String snFrom, String snTo) {
/* 549 */     String sequence = this.vin.substring(10);
/* 550 */     if (snFrom != null && !"#".equals(snFrom)) {
/* 551 */       snFrom = replaceWildcard(snFrom, sequence);
/* 552 */       if (snFrom.compareTo(sequence) > 0) {
/* 553 */         return false;
/*     */       }
/*     */     } 
/* 556 */     if (snTo != null && !"#".equals(snTo)) {
/* 557 */       snTo = replaceWildcard(snTo, sequence);
/* 558 */       return (snTo.compareTo(sequence) >= 0);
/*     */     } 
/* 560 */     return true;
/*     */   }
/*     */   
/*     */   protected String replaceWildcard(String template, String target) {
/* 564 */     if (template.indexOf('?') >= 0) {
/* 565 */       StringBuffer buffer = new StringBuffer();
/* 566 */       for (int i = 0; i < template.length(); i++) {
/* 567 */         char c = template.charAt(i);
/* 568 */         buffer.append((c == '?') ? target.charAt(i) : c);
/*     */       } 
/* 570 */       template = buffer.toString();
/*     */     } 
/* 572 */     return template;
/*     */   }
/*     */   
/*     */   boolean match(SPSVINCode code) {
/* 576 */     return (matchWildcard(code.getWmi(), getWMI()) && matchWildcard(code.getVds(), this.vin.substring(3, 9)));
/*     */   }
/*     */   
/*     */   boolean matchWildcardWMI(SPSVINCode code) {
/* 580 */     return matchWildcard(code.getWmi(), getWMI());
/*     */   }
/*     */   
/*     */   boolean matchWildcardVDS(SPSVINCode code) {
/* 584 */     return matchWildcard(code.getVds(), this.vin.substring(3, 9));
/*     */   }
/*     */   
/*     */   boolean matchWildcardVIS(SPSVINCode code) {
/* 588 */     return matchWildcard(code.getVis(), this.vin.substring(11));
/*     */   }
/*     */   
/*     */   public boolean matchWildcard(String pattern, String value) {
/* 592 */     if ("#".equals(pattern) || value == null || pattern == null || pattern.length() != value.length()) {
/* 593 */       return true;
/*     */     }
/* 595 */     for (int i = 0; i < pattern.length(); i++) {
/* 596 */       char c = pattern.charAt(i);
/* 597 */       if (c != '?' && c != value.charAt(i)) {
/* 598 */         return false;
/*     */       }
/*     */     } 
/* 601 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 605 */     return this.vin.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 609 */     return (object != null && object instanceof SPSVIN && ((SPSVIN)object).toString().equals(this.vin));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */