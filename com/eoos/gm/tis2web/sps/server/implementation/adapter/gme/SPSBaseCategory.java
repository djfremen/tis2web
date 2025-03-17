/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSBaseCategory
/*     */ {
/*     */   protected int id;
/*     */   protected SPSOptionCategory category;
/*     */   protected SPSOptionGroup group;
/*     */   protected int order;
/*     */   protected boolean evaluated;
/*     */   
/*     */   public int getID() {
/*  25 */     return this.id;
/*     */   }
/*     */   
/*     */   public SPSOptionCategory getCategory() {
/*  29 */     return this.category;
/*     */   }
/*     */   
/*     */   public SPSOptionGroup getGroup() {
/*  33 */     return this.group;
/*     */   }
/*     */   
/*     */   public int getNoPreEval() {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  41 */     return this.order;
/*     */   }
/*     */   
/*     */   public boolean isEvaluated() {
/*  45 */     return this.evaluated;
/*     */   }
/*     */   
/*     */   public void setEvaluated(boolean evaluated) {
/*  49 */     this.evaluated = evaluated;
/*     */   }
/*     */   
/*     */   public boolean isOptionCategory() {
/*  53 */     return this.category.isOptionCategory();
/*     */   }
/*     */   
/*     */   public SPSBaseCategory(SPSLanguage language, int id, SPSOptionCategory category, SPSOptionGroup group, int order) {
/*  57 */     this.id = id;
/*  58 */     this.category = SPSOptionCategory.getOptionCategory(language, category, group);
/*  59 */     this.group = group;
/*  60 */     this.order = order;
/*     */   }
/*     */   
/*     */   public static List getOrderSequence(List<SPSBaseCategory> bcategories) {
/*  64 */     if (bcategories == null) {
/*  65 */       return null;
/*     */     }
/*  67 */     List<Integer> orders = new ArrayList();
/*  68 */     for (int i = 0; i < bcategories.size(); i++) {
/*  69 */       SPSBaseCategory bcategory = bcategories.get(i);
/*  70 */       Integer order = Integer.valueOf(bcategory.getOrder());
/*  71 */       if (!orders.contains(order)) {
/*  72 */         orders.add(order);
/*     */       }
/*     */     } 
/*  75 */     Collections.sort(orders);
/*  76 */     return orders;
/*     */   }
/*     */   
/*     */   public static List getReductionCategories(List<SPSBaseCategory> bcategories, Integer order) {
/*  80 */     if (bcategories == null) {
/*  81 */       return null;
/*     */     }
/*  83 */     List<SPSBaseCategory> candidates = new ArrayList();
/*  84 */     for (int i = 0; i < bcategories.size(); i++) {
/*  85 */       SPSBaseCategory bcategory = bcategories.get(i);
/*  86 */       if (bcategory.getOrder() == order.intValue()) {
/*  87 */         candidates.add(bcategory);
/*     */       }
/*     */     } 
/*  90 */     return (candidates.size() == 0) ? null : candidates;
/*     */   }
/*     */   
/*     */   public static boolean qualify(SPSSession session, SPSControllerGME controller, List<SPSBaseCategory> bcategories, int target) throws Exception {
/*  94 */     if (bcategories == null) {
/*  95 */       return true;
/*     */     }
/*  97 */     List<Integer> orders = getOrderSequence(bcategories);
/*  98 */     boolean accept = true;
/*  99 */     for (int o = 0; o < orders.size(); o++) {
/* 100 */       Integer order = orders.get(o);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       for (int i = 0; i < bcategories.size(); i++) {
/* 107 */         SPSBaseCategory bcategory = bcategories.get(i);
/* 108 */         if (bcategory.getOrder() == order.intValue() && !bcategory.isEvaluated()) {
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
/* 119 */           if (bcategory.isOptionCategory()) {
/*     */             
/* 121 */             accept = checkOptionCategory(bcategory, (SPSVehicle)session.getVehicle());
/*     */           }
/* 123 */           else if (bcategory.getGroup() != null) {
/* 124 */             boolean isCALID = ((SPSSession)session).isCALID();
/* 125 */             boolean isHWOCheckDisabled = ((SPSSession)session).isHWOCheckDisabled();
/* 126 */             accept = checkOptionGroup(isCALID, isHWOCheckDisabled, (SPSVehicle)session.getVehicle(), controller, bcategory);
/* 127 */             if (isHWOCheckDisabled) {
/* 128 */               ((SPSSession)session).storeHWOCategory(bcategory);
/*     */             }
/*     */           } else {
/* 131 */             bcategory.setEvaluated(true);
/*     */           } 
/*     */           
/* 134 */           if (!accept)
/*     */             break; 
/*     */         } 
/*     */       } 
/* 138 */       if (!accept) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return accept;
/*     */   }
/*     */   
/*     */   protected static boolean checkOptionCategory(SPSBaseCategory bcategory, SPSVehicle vehicle) {
/* 151 */     List<SPSOption> options = vehicle.getOptions();
/* 152 */     if (options == null) {
/* 153 */       if (vehicle.getEngine() != null && bcategory.getCategory().getID().equals("ENG")) {
/* 154 */         return checkEngineOption(bcategory.getCategory(), SPSVehicle.normalize(vehicle.getEngine()));
/*     */       }
/* 156 */       return true;
/*     */     } 
/* 158 */     SPSOptionCategory category = bcategory.getCategory();
/* 159 */     for (int i = 0; i < options.size(); i++) {
/* 160 */       SPSOption option = options.get(i);
/* 161 */       if (category.equals(option.getType())) {
/* 162 */         bcategory.setEvaluated(true);
/* 163 */         return category.match(option);
/*     */       } 
/*     */     } 
/* 166 */     return true;
/*     */   }
/*     */   
/*     */   protected static boolean checkEngineOption(SPSOptionCategory category, String engine) {
/* 170 */     List<SPSOption> alternatives = category.getOptions();
/* 171 */     for (int j = 0; j < alternatives.size(); j++) {
/* 172 */       SPSOption option = alternatives.get(j);
/* 173 */       if (SPSVehicle.normalize(option.getDescription()).equals(engine)) {
/* 174 */         return true;
/*     */       }
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean checkOptionGroup(boolean isCALID, boolean isHWOCheckDisabled, SPSVehicle vehicle, SPSControllerGME controller, SPSBaseCategory bcategory) throws Exception {
/* 181 */     bcategory.setEvaluated(true);
/* 182 */     SPSOptionGroup group = bcategory.getGroup();
/* 183 */     if (bcategory.getCategory().getID().equals("RNG")) {
/* 184 */       if (group.matchVINRange((SPSVIN)vehicle.getVIN())) {
/* 185 */         return true;
/*     */       }
/* 187 */       boolean match = (group.matchDefaultVINRange() || group.matchNullVINRange((SPSVIN)vehicle.getVIN()));
/* 188 */       if (match) {
/* 189 */         vehicle.setWildcardControllerRNG(controller);
/*     */       }
/* 191 */       return match;
/*     */     } 
/* 193 */     if (bcategory.getCategory().getID().equals("VIN")) {
/* 194 */       if (group.matchVINCode((SPSVIN)vehicle.getVIN())) {
/* 195 */         return true;
/*     */       }
/* 197 */       return group.matchDefaultVINCode((SPSVIN)vehicle.getVIN());
/*     */     } 
/* 199 */     if (bcategory.getCategory().getID().equals("V10")) {
/* 200 */       return group.matchV10Code((SPSVIN)vehicle.getVIN());
/*     */     }
/* 202 */     VIT1Data vit1 = vehicle.getVIT1();
/* 203 */     if (isCALID || (vit1 != null && vit1.getID() != null)) {
/* 204 */       if (bcategory.getCategory().isHWOCategory()) {
/* 205 */         if (isCALID) {
/* 206 */           if (isHWOCheckDisabled) {
/* 207 */             bcategory.setEvaluated(false);
/* 208 */             return true;
/*     */           } 
/*     */           try {
/* 211 */             String hwNumber = getHWNumberCALID(vehicle, bcategory, null);
/* 212 */             return group.matchHWO(hwNumber, bcategory.getCategory());
/* 213 */           } catch (RequestException r) {
/* 214 */             bcategory.setEvaluated(false);
/* 215 */             throw r;
/*     */           } 
/*     */         } 
/* 218 */         return group.matchHWO(vit1, bcategory.getCategory());
/*     */       } 
/*     */ 
/*     */       
/* 222 */       return group.matchVIT1(vit1, bcategory.getCategory());
/*     */     } 
/*     */     
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHWNumberCALID(SPSVehicle vehicle, SPSBaseCategory bcategory, List<SPSOptionHWO> optionsHWO) throws Exception {
/* 231 */     if (vehicle.getHardware() != null) {
/* 232 */       return vehicle.getHardware();
/*     */     }
/* 234 */     List<SPSOptionHWO> options = null;
/* 235 */     if (bcategory != null) {
/* 236 */       SPSOptionCategory category = bcategory.getCategory();
/* 237 */       options = category.getOptions();
/* 238 */       if (options == null || options.size() == 0) {
/* 239 */         if (bcategory.getGroup() != null && hasOptions(bcategory.getGroup())) {
/* 240 */           options = bcategory.getGroup().getOptions();
/*     */         } else {
/* 242 */           throw new RequestException(SPSSchemaAdapterGME.builder.createHWNumberInputRequest());
/*     */         } 
/* 244 */       } else if (options.size() == 1) {
/* 245 */         return ((SPSOptionHWO)options.get(0)).getHWName();
/*     */       } 
/* 247 */     } else if (optionsHWO != null) {
/* 248 */       if (optionsHWO.size() == 1 && options != null && options.size() > 0) {
/* 249 */         return ((SPSOptionHWO)options.get(0)).getHWName();
/*     */       }
/* 251 */       options = optionsHWO;
/*     */     } else {
/* 253 */       throw new RequestException(SPSSchemaAdapterGME.builder.createHWNumberInputRequest());
/*     */     } 
/* 255 */     List<ValueAdapter> hardware = new ArrayList();
/* 256 */     for (int i = 0; i < options.size(); i++) {
/* 257 */       SPSOptionHWO option = options.get(i);
/* 258 */       hardware.add(new ValueAdapter(option.getHWName()));
/*     */     } 
/* 260 */     Collections.sort(hardware);
/* 261 */     throw new RequestException(SPSSchemaAdapterGME.builder.makeSelectionRequest(CommonAttribute.HARDWARE_NUMBER, hardware, null));
/*     */   }
/*     */   
/*     */   protected static boolean hasOptions(SPSOptionGroup group) {
/* 265 */     return (group.getOptions() != null && group.getOptions().size() > 0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 269 */     StringBuffer buffer = new StringBuffer();
/* 270 */     buffer.append(this.id + ":" + this.order + ":" + this.evaluated + ":" + this.group + ":" + this.category.getID() + ":options=");
/* 271 */     List options = this.category.getOptions();
/* 272 */     for (int i = 0; i < options.size(); i++) {
/* 273 */       if (i > 0) {
/* 274 */         buffer.append(',');
/*     */       }
/* 276 */       buffer.append(options.get(i));
/*     */     } 
/* 278 */     buffer.append('.');
/* 279 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSBaseCategory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */