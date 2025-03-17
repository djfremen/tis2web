/*     */ package com.eoos.gm.tis2web.vc.implementation.io.datamodel;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRDomainImpl;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRValueImpl;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.db.VehicleOptionGroup;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.implementation.VehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VehicleOptionsData
/*     */ {
/*     */   private class OptionPair
/*     */     implements Comparable
/*     */   {
/*  42 */     private String name = null;
/*     */     
/*  44 */     private Integer domainID = null;
/*     */     
/*     */     public OptionPair(String name, Integer domainID) {
/*  47 */       this.name = name;
/*  48 */       this.domainID = domainID;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  52 */       return this.name;
/*     */     }
/*     */     
/*     */     public Integer getDomainID() {
/*  56 */       return this.domainID;
/*     */     }
/*     */     
/*     */     public int compareTo(Object o) {
/*  60 */       OptionPair pair = (OptionPair)o;
/*  61 */       int retValue = getName().compareTo(pair.getName());
/*  62 */       if (retValue == 0 && 
/*  63 */         this.domainID != null && pair.getDomainID() != null) {
/*  64 */         retValue = getDomainID().compareTo(pair.getDomainID());
/*     */       }
/*     */       
/*  67 */       return retValue;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/*  71 */       return (o == null) ? false : ((compareTo(o) == 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class OptionDataContainer implements Comparable {
/*  76 */     private VehicleOptionsData.OptionPair option = null;
/*     */ 
/*     */ 
/*     */     
/*  80 */     private Set values = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     private VehicleOptionsData.OptionPair selectedValue = null;
/*     */ 
/*     */     
/*     */     private boolean groupFlag = false;
/*     */ 
/*     */ 
/*     */     
/*     */     public OptionDataContainer(VehicleOptionsData.OptionPair option, Set values, boolean group, boolean current) {
/*  94 */       this.selectedValue = new VehicleOptionsData.OptionPair("$UNKNOWN$", null);
/*  95 */       this.option = option;
/*  96 */       this.groupFlag = group;
/*  97 */       this.values = new TreeSet();
/*  98 */       addOptionValues(values);
/*     */     }
/*     */     
/*     */     public void addOptionValues(Set values) {
/* 102 */       if (isGroupOption() == true) {
/* 103 */         this.values.addAll(values);
/*     */       }
/*     */     }
/*     */     
/*     */     public Set getOptionValues() {
/* 108 */       return this.values;
/*     */     }
/*     */     
/*     */     public List getOptionValuesToShow() {
/* 112 */       List<VehicleOptionsData.OptionPair> retValues = new ArrayList();
/* 113 */       if (isGroupOption() == true) {
/* 114 */         retValues.addAll(this.values);
/* 115 */         retValues.add(new VehicleOptionsData.OptionPair("$NOT_INSTALLED$", null));
/* 116 */         retValues.add(new VehicleOptionsData.OptionPair("$UNKNOWN$", null));
/*     */       } else {
/* 118 */         retValues.add(new VehicleOptionsData.OptionPair("$INSTALLED$", null));
/* 119 */         retValues.add(new VehicleOptionsData.OptionPair("$NOT_INSTALLED$", null));
/* 120 */         retValues.add(new VehicleOptionsData.OptionPair("$UNKNOWN$", null));
/*     */       } 
/* 122 */       return retValues;
/*     */     }
/*     */     
/*     */     public String getOptionName() {
/* 126 */       return this.option.getName();
/*     */     }
/*     */     
/*     */     public void setSelectedValue(String valueName) {
/* 130 */       if (valueName.equalsIgnoreCase("$INSTALLED$")) {
/* 131 */         this.selectedValue = new VehicleOptionsData.OptionPair("$INSTALLED$", null);
/* 132 */       } else if (valueName.equalsIgnoreCase("$NOT_INSTALLED$")) {
/* 133 */         this.selectedValue = new VehicleOptionsData.OptionPair("$NOT_INSTALLED$", null);
/* 134 */       } else if (valueName.equalsIgnoreCase("$UNKNOWN$")) {
/* 135 */         this.selectedValue = new VehicleOptionsData.OptionPair("$UNKNOWN$", null);
/*     */       }
/* 137 */       else if (this.values != null && !this.values.isEmpty()) {
/* 138 */         Iterator<VehicleOptionsData.OptionPair> it = this.values.iterator();
/* 139 */         while (it.hasNext()) {
/* 140 */           VehicleOptionsData.OptionPair value = it.next();
/* 141 */           if (value.getName().equalsIgnoreCase(valueName)) {
/* 142 */             this.selectedValue = value;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSelectedValueName() {
/* 150 */       return this.selectedValue.getName();
/*     */     }
/*     */     
/*     */     public VehicleOptionsData.OptionPair getSelectedValue() {
/* 154 */       return this.selectedValue;
/*     */     }
/*     */     
/*     */     public VehicleOptionsData.OptionPair getOption() {
/* 158 */       return this.option;
/*     */     }
/*     */     
/*     */     public boolean isGroupOption() {
/* 162 */       return this.groupFlag;
/*     */     }
/*     */     
/*     */     public boolean isNotInstalled() {
/* 166 */       return this.selectedValue.getName().equalsIgnoreCase("$NOT_INSTALLED$");
/*     */     }
/*     */     
/*     */     public boolean isUnknown() {
/* 170 */       return this.selectedValue.getName().equalsIgnoreCase("$UNKNOWN$");
/*     */     }
/*     */     
/*     */     public int compareTo(Object o) {
/* 174 */       VehicleOptionsData.OptionPair option = (VehicleOptionsData.OptionPair)o;
/* 175 */       return this.option.compareTo(option);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 179 */       OptionDataContainer odc = (OptionDataContainer)o;
/* 180 */       return (odc == null) ? false : ((compareTo(odc.getOption()) == 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 185 */   private ClientContext context = null;
/*     */   
/*     */   public static final String VO_INSTALLED = "$INSTALLED$";
/*     */   
/*     */   public static final String VO_NOT_INSTALLED = "$NOT_INSTALLED$";
/*     */   
/*     */   public static final String VO_UNKNOWN = "$UNKNOWN$";
/*     */   
/* 193 */   private Integer localeID = null;
/*     */   
/* 195 */   private List data = null;
/*     */   
/* 197 */   private List truncatedValueNames = null;
/*     */   
/* 199 */   private static LockObjectProvider lockProvider = new LockObjectProvider();
/*     */   private Map optionsMap;
/*     */   
/*     */   public VehicleOptionsData(ClientContext context) {
/* 203 */     this.context = context;
/* 204 */     Locale locale = context.getLocale();
/* 205 */     this.localeID = LocaleInfoProvider.getInstance().getLocale(locale).getLocaleID();
/* 206 */     this.data = new ArrayList();
/* 207 */     this.truncatedValueNames = new ArrayList();
/* 208 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 211 */             VehicleOptionsData.this.resetVehicleOptions();
/*     */           }
/*     */         });
/*     */   }
/*     */   private String positiveOptionsVCR; private String negativeOptionsVCR;
/*     */   public static VehicleOptionsData getInstance(ClientContext context) {
/* 217 */     synchronized (lockProvider.getLockObject(context)) {
/* 218 */       VehicleOptionsData instance = (VehicleOptionsData)context.getObject(VehicleOptionsData.class);
/* 219 */       if (instance == null) {
/* 220 */         instance = new VehicleOptionsData(context);
/* 221 */         context.storeObject(VehicleOptionsData.class, instance);
/*     */       } 
/* 223 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setElectronicSystemCode(List<Integer> esc, ILVCAdapter adapter) {
/* 228 */     this.truncatedValueNames.clear();
/* 229 */     this.data.clear();
/* 230 */     for (int i = 0; i < esc.size(); i++) {
/* 231 */       if (i == 0) {
/* 232 */         getDataFromDB(esc.get(i), true, adapter);
/*     */       } else {
/* 234 */         getDataFromDB(esc.get(i), false, adapter);
/*     */       } 
/*     */     } 
/* 237 */     restoreVehicleOptions(adapter);
/*     */   }
/*     */   
/*     */   private void getDataFromDB(Integer esc, boolean currentES, ILVCAdapter adapter) {
/* 241 */     this.context.getLocale();
/* 242 */     List options = adapter.getVC().getVehicleOptions(esc);
/* 243 */     if (options != null) {
/* 244 */       for (int i = 0; i < options.size(); i++) {
/* 245 */         Object item = options.get(i);
/* 246 */         if (item instanceof VehicleOptionGroup) {
/* 247 */           VehicleOptionGroup group = (VehicleOptionGroup)item;
/* 248 */           List<VCRValueImpl> choices = group.getOptions();
/* 249 */           Set<OptionPair> values = new TreeSet();
/* 250 */           for (int j = 0; j < choices.size(); j++) {
/* 251 */             VCRValueImpl value = choices.get(j);
/* 252 */             values.add(new OptionPair(adapter.getVC().getLabel(this.localeID, (VCValue)value), value.getDomainID()));
/*     */           } 
/* 254 */           OptionPair option = new OptionPair(adapter.getVC().getLabel(this.localeID, (VCValue)group.getGroup()), group.getGroup().getDomainID());
/* 255 */           OptionDataContainer odc = new OptionDataContainer(option, values, true, currentES);
/* 256 */           addData(odc);
/*     */         } else {
/* 258 */           VCRValueImpl vcrValue = (VCRValueImpl)item;
/* 259 */           OptionPair option = new OptionPair(adapter.getVC().getLabel(this.localeID, (VCValue)vcrValue), vcrValue.getDomainID());
/* 260 */           OptionDataContainer odc = new OptionDataContainer(option, null, false, currentES);
/* 261 */           addData(odc);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void addData(OptionDataContainer odc) {
/* 268 */     if (this.data.contains(odc)) {
/* 269 */       Iterator<OptionDataContainer> it = this.data.iterator();
/* 270 */       while (it.hasNext()) {
/* 271 */         OptionDataContainer container = it.next();
/* 272 */         if (container.equals(odc)) {
/* 273 */           container.addOptionValues(odc.getOptionValues());
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 281 */       this.data.add(odc);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String truncateValueName(String fullValueName) {
/* 286 */     String truncatedValueName = fullValueName;
/* 287 */     int maxLength = 0;
/*     */     try {
/* 289 */       maxLength = Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.vc.vehicle.options.value.length"));
/* 290 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 293 */     if (maxLength != 0 && fullValueName.length() > maxLength) {
/* 294 */       truncatedValueName = fullValueName.substring(0, maxLength) + "... " + Integer.toString(this.truncatedValueNames.size() + 1) + ")";
/* 295 */       PairImpl pairImpl = new PairImpl(truncatedValueName, fullValueName);
/*     */       
/* 297 */       boolean containsFlag = false;
/* 298 */       Pair pair = null;
/* 299 */       Iterator<Pair> it = this.truncatedValueNames.iterator();
/* 300 */       while (it.hasNext()) {
/* 301 */         pair = it.next();
/* 302 */         if (((String)pair.getSecond()).equalsIgnoreCase(fullValueName)) {
/* 303 */           containsFlag = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 307 */       if (containsFlag == true) {
/* 308 */         truncatedValueName = (String)pair.getFirst();
/*     */       } else {
/* 310 */         this.truncatedValueNames.add(pairImpl);
/*     */       } 
/*     */     } 
/* 313 */     return truncatedValueName;
/*     */   }
/*     */   
/*     */   public String truncatedToFullName(String truncatedValueName) {
/* 317 */     String fullValueName = truncatedValueName;
/* 318 */     Iterator<Pair> it = this.truncatedValueNames.iterator();
/* 319 */     while (it.hasNext()) {
/* 320 */       Pair p = it.next();
/* 321 */       if (((String)p.getFirst()).equalsIgnoreCase(truncatedValueName)) {
/* 322 */         fullValueName = (String)p.getSecond();
/*     */         break;
/*     */       } 
/*     */     } 
/* 326 */     return fullValueName;
/*     */   }
/*     */   
/*     */   public List getTruncatedValueNamesList() {
/* 330 */     return this.truncatedValueNames;
/*     */   }
/*     */   
/*     */   private OptionDataContainer getOptionDataContainer(String optionName) {
/* 334 */     OptionDataContainer retValue = null;
/* 335 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 336 */     while (it.hasNext()) {
/* 337 */       OptionDataContainer odc = it.next();
/* 338 */       if (odc.getOptionName().equalsIgnoreCase(optionName)) {
/* 339 */         retValue = odc;
/*     */         break;
/*     */       } 
/*     */     } 
/* 343 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getOptionNames() {
/* 347 */     List<String> optionNames = new ArrayList();
/* 348 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 349 */     while (it.hasNext()) {
/* 350 */       OptionDataContainer odc = it.next();
/* 351 */       optionNames.add(odc.getOptionName());
/*     */     } 
/* 353 */     Collections.sort(optionNames);
/* 354 */     return optionNames;
/*     */   }
/*     */   
/*     */   public List getOptionValues(String optionName) {
/* 358 */     List<String> retValue = new ArrayList();
/* 359 */     OptionDataContainer odc = getOptionDataContainer(optionName);
/* 360 */     if (odc != null) {
/* 361 */       List values = odc.getOptionValuesToShow();
/* 362 */       Iterator<OptionPair> it = values.iterator();
/* 363 */       while (it.hasNext()) {
/* 364 */         OptionPair pare = it.next();
/* 365 */         retValue.add(pare.getName());
/*     */       } 
/*     */     } 
/* 368 */     return retValue;
/*     */   }
/*     */   
/*     */   public void setSelectedValue(String optionName, String selectedValue) {
/* 372 */     OptionDataContainer odc = getOptionDataContainer(optionName);
/* 373 */     if (odc != null) {
/* 374 */       odc.setSelectedValue(selectedValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSelectedValue(String optionName) {
/* 379 */     String retValue = null;
/* 380 */     OptionDataContainer odc = getOptionDataContainer(optionName);
/* 381 */     if (odc != null) {
/* 382 */       retValue = odc.getSelectedValueName();
/*     */     }
/* 384 */     return retValue;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 388 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 389 */     while (it.hasNext()) {
/* 390 */       OptionDataContainer odc = it.next();
/* 391 */       odc.setSelectedValue("$UNKNOWN$");
/*     */     } 
/*     */   }
/*     */   
/*     */   private List getInstalledOptions() {
/* 396 */     List<OptionPair> retValue = new ArrayList();
/* 397 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 398 */     while (it.hasNext()) {
/* 399 */       OptionDataContainer odc = it.next();
/* 400 */       if (!odc.isNotInstalled() && !odc.isUnknown()) {
/* 401 */         if (odc.isGroupOption() == true) {
/* 402 */           retValue.add(odc.getSelectedValue()); continue;
/*     */         } 
/* 404 */         retValue.add(odc.getOption());
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     return retValue;
/*     */   }
/*     */   
/*     */   private List getNotInstalledOptions() {
/* 412 */     List<OptionPair> retValue = new ArrayList();
/* 413 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 414 */     while (it.hasNext()) {
/* 415 */       OptionDataContainer odc = it.next();
/* 416 */       if (!odc.isUnknown()) {
/* 417 */         if (odc.isGroupOption() == true) {
/* 418 */           List optionValues = odc.getOptionValuesToShow();
/* 419 */           if (optionValues != null) {
/* 420 */             OptionPair selectedValue = odc.getSelectedValue();
/* 421 */             Iterator<OptionPair> valuesIt = optionValues.iterator();
/* 422 */             while (valuesIt.hasNext()) {
/* 423 */               OptionPair value = valuesIt.next();
/* 424 */               if (!value.equals(selectedValue) && value.getDomainID() != null) {
/* 425 */                 retValue.add(value);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 430 */         if (odc.isNotInstalled() == true) {
/* 431 */           retValue.add(odc.getOption());
/*     */         }
/*     */       } 
/*     */     } 
/* 435 */     return retValue;
/*     */   }
/*     */   
/*     */   private VCR createPositiveVCR(ILVCAdapter adapter) {
/* 439 */     VCR retValue = null;
/* 440 */     IVehicleOptionExpression iVehicleOptionExpression = adapter.createVehicleOptionExpression();
/* 441 */     List installed = getInstalledOptions();
/*     */     
/* 443 */     if (installed.size() != 0) {
/* 444 */       fillExpression(installed, (VCRExpression)iVehicleOptionExpression, adapter);
/* 445 */       if (((VehicleOptionExpression)iVehicleOptionExpression).isInvalid()) {
/* 446 */         retValue = VCR.NULL;
/*     */       } else {
/* 448 */         retValue = adapter.makeVCR((VCRExpression)iVehicleOptionExpression);
/*     */       } 
/*     */     } else {
/* 451 */       retValue = VCR.NULL;
/*     */     } 
/* 453 */     return retValue;
/*     */   }
/*     */   
/*     */   private VCR createNegativeVCR(ILVCAdapter adapter) {
/* 457 */     VCR retValue = null;
/* 458 */     IVehicleOptionExpression iVehicleOptionExpression = adapter.createVehicleOptionExpression();
/* 459 */     List notInstalled = getNotInstalledOptions();
/* 460 */     if (notInstalled.size() != 0) {
/* 461 */       fillExpression(notInstalled, (VCRExpression)iVehicleOptionExpression, adapter);
/* 462 */       if (((VehicleOptionExpression)iVehicleOptionExpression).isInvalid()) {
/* 463 */         retValue = VCR.NULL;
/*     */       } else {
/* 465 */         retValue = adapter.makeVCR((VCRExpression)iVehicleOptionExpression);
/*     */       } 
/*     */     } else {
/* 468 */       retValue = VCR.NULL;
/*     */     } 
/* 470 */     return retValue;
/*     */   }
/*     */   
/*     */   private void fillExpression(List options, VCRExpression vcrExpression, ILVCAdapter adapter) {
/* 474 */     Iterator<OptionPair> it = options.iterator();
/* 475 */     while (it.hasNext()) {
/* 476 */       OptionPair option = it.next();
/* 477 */       VCRDomainImpl domain = (VCRDomainImpl)adapter.getVC().getDomain(option.getDomainID());
/* 478 */       VCRValueImpl vcrValue = (VCRValueImpl)adapter.getVC().getValue(this.localeID, (VCDomain)domain, option.getName());
/* 479 */       if (vcrValue != null) {
/* 480 */         vcrExpression.add(adapter.makeAttribute((VCValue)vcrValue));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setSelectedValueFromPositiveVCR(String label) {
/* 486 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 487 */     while (it.hasNext()) {
/* 488 */       OptionDataContainer odc = it.next();
/* 489 */       if (odc.getOptionName().compareToIgnoreCase(label) == 0) {
/* 490 */         odc.setSelectedValue("$INSTALLED$"); continue;
/*     */       } 
/* 492 */       List values = odc.getOptionValuesToShow();
/* 493 */       Iterator<OptionPair> itValues = values.iterator();
/* 494 */       while (itValues.hasNext()) {
/* 495 */         OptionPair op = itValues.next();
/* 496 */         if (op.getName().compareToIgnoreCase(label) == 0) {
/* 497 */           odc.setSelectedValue(label);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSelectedValueFromNegativeVCR(String label) {
/* 505 */     Iterator<OptionDataContainer> it = this.data.iterator();
/* 506 */     while (it.hasNext()) {
/* 507 */       OptionDataContainer odc = it.next();
/* 508 */       if (odc.getOptionName().compareToIgnoreCase(label) == 0) {
/* 509 */         odc.setSelectedValue("$NOT_INSTALLED$");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setValuesFromPositiveVCR(String positiveVCR, ILVCAdapter adapter) {
/* 515 */     if (positiveVCR == null || positiveVCR.equals(VCR.NULL.toString())) {
/*     */       return;
/*     */     }
/* 518 */     VCR vcr = adapter.makeVCR(positiveVCR);
/* 519 */     List attrbutes = vcr.listAttributes();
/* 520 */     if (attrbutes.size() != 0) {
/* 521 */       Locale locale = this.context.getLocale();
/* 522 */       Iterator<VCRAttribute> it = attrbutes.iterator();
/* 523 */       while (it.hasNext()) {
/* 524 */         VCRAttribute attribute = it.next();
/* 525 */         VCRDomain domain = (VCRDomain)adapter.getVC().getDomain(attribute.getDomainID());
/* 526 */         VCRValue value = domain.getValue(attribute.getValueID());
/* 527 */         String label = adapter.getVC().getLabel(LocaleInfoProvider.getInstance().getLocale(locale).getLocaleID(), (VCValue)value);
/* 528 */         setSelectedValueFromPositiveVCR(label);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setValuesFromNegativeVCR(String negativeVCR, ILVCAdapter adapter) {
/* 534 */     if (negativeVCR == null || negativeVCR.equals(VCR.NULL.toString())) {
/*     */       return;
/*     */     }
/* 537 */     VCR vcr = adapter.makeVCR(negativeVCR);
/* 538 */     List attrbutes = vcr.listAttributes();
/* 539 */     if (attrbutes.size() != 0) {
/* 540 */       Locale locale = this.context.getLocale();
/* 541 */       Iterator<VCRAttribute> it = attrbutes.iterator();
/* 542 */       while (it.hasNext()) {
/* 543 */         VCRAttribute attribute = it.next();
/* 544 */         VCRDomain domain = (VCRDomain)adapter.getVC().getDomain(attribute.getDomainID());
/* 545 */         VCRValue value = domain.getValue(attribute.getValueID());
/* 546 */         String label = adapter.getVC().getLabel(LocaleInfoProvider.getInstance().getLocale(locale).getLocaleID(), (VCValue)value);
/* 547 */         setSelectedValueFromNegativeVCR(label);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String localizeOptionsValue(String value) {
/* 553 */     String retValue = null;
/* 554 */     if (value != null) {
/* 555 */       if (value.equalsIgnoreCase("$INSTALLED$")) {
/* 556 */         retValue = new String(this.context.getLabel("vo.installed"));
/* 557 */       } else if (value.equalsIgnoreCase("$NOT_INSTALLED$")) {
/* 558 */         retValue = new String(this.context.getLabel("vo.not.installed"));
/* 559 */       } else if (value.equalsIgnoreCase("$UNKNOWN$")) {
/* 560 */         retValue = new String(this.context.getLabel("vo.unknown"));
/*     */       } else {
/* 562 */         return retValue = new String(value);
/*     */       } 
/*     */     }
/* 565 */     return retValue;
/*     */   }
/*     */   
/*     */   private Map _getVehicleOptionMap() {
/* 569 */     Map<Object, Object> retValue = new HashMap<Object, Object>();
/* 570 */     List optionNames = getOptionNames();
/* 571 */     Iterator<String> it = optionNames.iterator();
/* 572 */     while (it.hasNext()) {
/* 573 */       String optionName = it.next();
/* 574 */       String value = getSelectedValue(optionName);
/* 575 */       if (optionName != null && value != null && 
/* 576 */         value.compareToIgnoreCase("$UNKNOWN$") != 0) {
/* 577 */         retValue.put(optionName, localizeOptionsValue(value));
/*     */       }
/*     */     } 
/*     */     
/* 581 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVehicleOptions(ILVCAdapter adapter) {
/* 589 */     VCR positiveVCR = createPositiveVCR(adapter);
/* 590 */     VCR negativeVCR = createNegativeVCR(adapter);
/* 591 */     Map vehicleOptionsMap = _getVehicleOptionMap();
/* 592 */     this.positiveOptionsVCR = positiveVCR.toString();
/* 593 */     this.negativeOptionsVCR = negativeVCR.toString();
/* 594 */     this.optionsMap = vehicleOptionsMap;
/*     */   }
/*     */   
/*     */   public void restoreVehicleOptions(ILVCAdapter adapter) {
/* 598 */     setValuesFromPositiveVCR(getPositiveOptionsVCR(), adapter);
/* 599 */     setValuesFromNegativeVCR(getNegativeOptionsVCR(), adapter);
/*     */   }
/*     */   
/*     */   public String getPositiveOptionsVCR() {
/* 603 */     return this.positiveOptionsVCR;
/*     */   }
/*     */   
/*     */   public String getNegativeOptionsVCR() {
/* 607 */     return this.negativeOptionsVCR;
/*     */   }
/*     */   
/*     */   public void resetVehicleOptions() {
/* 611 */     this.optionsMap = null;
/* 612 */     this.positiveOptionsVCR = null;
/* 613 */     this.negativeOptionsVCR = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getVehicleOptionMap() {
/* 618 */     return this.optionsMap;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\datamodel\VehicleOptionsData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */