/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSSaturnData
/*     */ {
/*     */   public static final int DEFAULT_MODE = 0;
/*     */   public static final int INFO_MODE = 1;
/*     */   protected SPSLanguage language;
/*     */   protected SPSVehicle vehicle;
/*     */   protected int device;
/*  25 */   protected List byteData = new ArrayList();
/*     */   
/*  27 */   protected List definedBits = new ArrayList();
/*     */   
/*  29 */   protected List tireSize = new ArrayList();
/*     */   
/*  31 */   protected List options = null;
/*     */   
/*  33 */   protected int mode = 0;
/*     */   
/*     */   public List getByteData() {
/*  36 */     return this.byteData;
/*     */   }
/*     */   
/*     */   public List getDefinedBits() {
/*  40 */     return this.definedBits;
/*     */   }
/*     */   
/*     */   public int getDevice() {
/*  44 */     return this.device;
/*     */   }
/*     */   
/*     */   public SPSLanguage getLanguage() {
/*  48 */     return this.language;
/*     */   }
/*     */   
/*     */   public List getTireSize() {
/*  52 */     return this.tireSize;
/*     */   }
/*     */   
/*     */   public SPSVehicle getVehicle() {
/*  56 */     return this.vehicle;
/*     */   }
/*     */   
/*     */   public int getMode() {
/*  60 */     return this.mode;
/*     */   }
/*     */   
/*     */   SPSSaturnData(SPSLanguage language, SPSVehicle vehicle, int device) {
/*  64 */     this.language = language;
/*  65 */     this.vehicle = vehicle;
/*  66 */     this.device = device;
/*     */   }
/*     */   
/*     */   SPSSaturnData(SPSSession session, int device) {
/*  70 */     this.language = (SPSLanguage)session.getLanguage();
/*  71 */     this.vehicle = (SPSVehicle)session.getVehicle();
/*  72 */     this.device = device;
/*  73 */     this.mode = 1;
/*     */   }
/*     */   
/*     */   List getOptions(SPSSchemaAdapterNAO adapter) throws Exception {
/*  77 */     this.options = SPSOptionByte.loadOptionBytes(this, adapter);
/*  78 */     return this.options;
/*     */   }
/*     */   
/*     */   List getOptions(SPSVehicle vehicle) throws Exception {
/*  82 */     if (this.options == null || this.options.size() == 0) {
/*  83 */       return null;
/*     */     }
/*  85 */     Set selections = checkSelectedOptions(vehicle);
/*  86 */     List<SPSSpecialOption> obOptions = new ArrayList();
/*  87 */     for (int i = 0; i < this.options.size(); i++) {
/*  88 */       SPSSpecialOption option = this.options.get(i);
/*  89 */       if (option.getType() != null && option.getType().getDescription() != null && 
/*  90 */         !selections.contains(option.getType().getDescription())) {
/*  91 */         obOptions.add(option);
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return (obOptions.size() > 0) ? obOptions : null;
/*     */   }
/*     */   
/*     */   protected Set checkSelectedOptions(SPSVehicle vehicle) {
/*  99 */     Set<String> selections = new HashSet();
/* 100 */     List options = vehicle.getOptions();
/* 101 */     if (options == null || options.size() == 0) {
/* 102 */       return selections;
/*     */     }
/* 104 */     for (int i = 0; i < options.size(); i++) {
/* 105 */       Object selection = options.get(i);
/* 106 */       if (selection instanceof SPSSpecialOption) {
/* 107 */         SPSSpecialOption option = (SPSSpecialOption)selection;
/* 108 */         if (option.getType() != null && option.getType().getDescription() != null) {
/* 109 */           selections.add(option.getType().getDescription());
/*     */         }
/*     */       } 
/*     */     } 
/* 113 */     return selections;
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
/*     */   protected List determineSelectedOptions(SPSVehicle vehicle) {
/* 131 */     if (this.options == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     List candidates = vehicle.getOptions();
/* 135 */     if (candidates == null || candidates.size() == 0) {
/* 136 */       return null;
/*     */     }
/* 138 */     List<SPSOption> result = new ArrayList();
/* 139 */     Map<Object, Object> selections = new HashMap<Object, Object>(); int i;
/* 140 */     for (i = 0; i < candidates.size(); i++) {
/* 141 */       Object selection = candidates.get(i);
/* 142 */       if (selection instanceof SPSSpecialOption) {
/* 143 */         SPSSpecialOption option = (SPSSpecialOption)selection;
/* 144 */         if (option.getType() != null && option.getType().getDescription() != null) {
/* 145 */           selections.put(option.getType().getDescription(), option);
/*     */         }
/* 147 */       } else if (selection instanceof SPSOption) {
/* 148 */         SPSOption option = (SPSOption)selection;
/* 149 */         if (SPSOptionByte.isEmissionRPO(option) || SPSOptionByte.isBCMRPO(option)) {
/* 150 */           result.add(option);
/*     */         }
/*     */       } 
/*     */     } 
/* 154 */     for (i = 0; i < this.options.size(); i++) {
/* 155 */       SPSSpecialOption option = this.options.get(i);
/* 156 */       if (option.getType() != null && option.getType().getDescription() != null) {
/* 157 */         SPSSpecialOption selection = (SPSSpecialOption)selections.get(option.getType().getDescription());
/* 158 */         if (selection != null && (selection.equals(option) || match(option, selection))) {
/* 159 */           result.add(option);
/*     */         }
/*     */       } 
/*     */     } 
/* 163 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean match(SPSSpecialOption option, SPSSpecialOption selection) {
/* 167 */     return (option.getDescription() == null) ? false : option.getDescription().equals(selection.getDescription());
/*     */   }
/*     */   
/*     */   List getOptionBytes(SPSVehicle vehicle, SPSSchemaAdapterNAO adapter) throws Exception {
/* 171 */     List<SPSOptionByte> vit2 = new ArrayList();
/* 172 */     if (vehicle.getAdaptiveBytes(this.device) != null) {
/* 173 */       vit2.addAll(vehicle.getAdaptiveBytes(this.device));
/*     */     }
/* 175 */     List selections = determineSelectedOptions(vehicle);
/* 176 */     List<?> optionBytes = SPSOptionByte.constructOptionBytes(selections, this.tireSize, this.definedBits, this.byteData, adapter);
/*     */ 
/*     */     
/* 179 */     if (optionBytes != null) {
/* 180 */       Collections.sort(optionBytes, new OptionByteSortOrder()); int i;
/* 181 */       for (i = 0; i < optionBytes.size(); i++) {
/* 182 */         Object element = optionBytes.get(i);
/* 183 */         if (element instanceof SPSOptionByte) {
/* 184 */           SPSOptionByte b = (SPSOptionByte)element;
/* 185 */           if (!b.isBitData())
/*     */           {
/* 187 */             if (b.getOrder() >= 0)
/*     */             {
/*     */               
/* 190 */               vit2.add(b); } 
/*     */           }
/*     */         } 
/*     */       } 
/* 194 */       for (i = 0; i < optionBytes.size(); i++) {
/* 195 */         Object element = optionBytes.get(i);
/* 196 */         if (element instanceof SPSOptionByte) {
/* 197 */           SPSOptionByte b = (SPSOptionByte)element;
/* 198 */           if (b.isBitData() && b.getOrder() >= 0) {
/* 199 */             vit2.add(b);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 205 */       for (i = 0; i < optionBytes.size(); i++) {
/* 206 */         Object element = optionBytes.get(i);
/* 207 */         if (element instanceof SPSOptionByte) {
/* 208 */           SPSOptionByte b = (SPSOptionByte)element;
/* 209 */           if (b.getOrder() < 0) {
/* 210 */             vit2.add(b);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 217 */     return (vit2.size() > 0) ? vit2 : null;
/*     */   }
/*     */   
/*     */   protected static class OptionByteSortOrder implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/* 222 */       if (o1 instanceof SPSOptionByte && o2 instanceof SPSOptionByte) {
/* 223 */         SPSOptionByte b1 = (SPSOptionByte)o1;
/* 224 */         SPSOptionByte b2 = (SPSOptionByte)o2;
/* 225 */         return b1.getOrder() - b2.getOrder();
/*     */       } 
/* 227 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSSaturnData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */