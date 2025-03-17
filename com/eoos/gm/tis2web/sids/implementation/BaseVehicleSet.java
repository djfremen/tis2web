/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.UnresolvableException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseVehicleSet
/*     */ {
/*  24 */   private static Logger log = Logger.getLogger(BaseVehicleSet.class);
/*  25 */   private static ServiceID SID_NAO = ServiceIDImpl.getInstance("SID_NAO");
/*     */   
/*     */   protected List vehicles;
/*     */   
/*     */   public List getBaseVehicles() {
/*  30 */     return this.vehicles;
/*     */   }
/*     */   
/*     */   public boolean isSupportedVIN() {
/*  34 */     return (this.vehicles != null && !this.vehicles.isEmpty());
/*     */   }
/*     */   
/*     */   protected BaseVehicleSet(List vehicles) {
/*  38 */     this.vehicles = vehicles;
/*     */   }
/*     */   
/*     */   public BaseVehicleSet(List<BaseVehicle> domain, String vin) {
/*  42 */     List<BaseVehicle> matches = new ArrayList();
/*  43 */     List<BaseVehicle> defaults = new ArrayList();
/*  44 */     String wmi = vin.substring(0, 3);
/*  45 */     String vds = vin.substring(3, 9);
/*  46 */     String my = vin.substring(9, 10);
/*  47 */     String vis = vin.substring(10, 17);
/*  48 */     for (int i = 0; i < domain.size(); i++) {
/*  49 */       BaseVehicle candidate = domain.get(i);
/*  50 */       if (candidate.match(wmi, vds, my, vis)) {
/*  51 */         if (candidate.isDefault()) {
/*  52 */           defaults.add(candidate);
/*     */         } else {
/*  54 */           matches.add(candidate);
/*     */         } 
/*     */       }
/*     */     } 
/*  58 */     if (matches.size() != 0) {
/*  59 */       this.vehicles = matches;
/*  60 */     } else if (defaults.size() != 0) {
/*  61 */       this.vehicles = reduce(vin, defaults);
/*     */     } else {
/*  63 */       log.info("no matching base vehicle entry found for vin = " + vin);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List reduce(String vin, List<BaseVehicle> candidates) {
/*  68 */     int max = 0;
/*  69 */     String attribute = null;
/*  70 */     for (int i = 0; i < candidates.size(); i++) {
/*  71 */       BaseVehicle candidate = candidates.get(i);
/*  72 */       String defaultAttribute = candidate.getDefaultAttribute();
/*  73 */       if (attribute == null) {
/*  74 */         attribute = defaultAttribute;
/*  75 */       } else if (attribute == null || !attribute.equalsIgnoreCase(defaultAttribute)) {
/*  76 */         log.error("default value mismatch encountered (vin=" + vin + ")");
/*  77 */         return null;
/*     */       } 
/*  79 */       if (candidate.getDefaultPrefixLength(attribute) > max) {
/*  80 */         max = candidate.getDefaultPrefixLength(attribute);
/*     */       }
/*     */     } 
/*  83 */     if (max > 0) {
/*  84 */       Iterator<BaseVehicle> it = candidates.iterator();
/*  85 */       while (it.hasNext()) {
/*  86 */         BaseVehicle candidate = it.next();
/*  87 */         if (candidate.getDefaultPrefixLength(attribute) < max) {
/*  88 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*  92 */     return candidates;
/*     */   }
/*     */   
/*     */   public ServiceID getServiceID() {
/*  96 */     ServiceID sid = null;
/*  97 */     for (int i = 0; i < this.vehicles.size(); i++) {
/*  98 */       BaseVehicle candidate = this.vehicles.get(i);
/*  99 */       if (sid == null) {
/* 100 */         sid = candidate.getServiceID();
/* 101 */       } else if (sid != candidate.getServiceID()) {
/* 102 */         return null;
/*     */       } 
/*     */     } 
/* 105 */     return sid;
/*     */   }
/*     */   
/*     */   public List getQualifierAttributes() {
/* 109 */     List<VehicleAttribute> qualifiers = new ArrayList();
/* 110 */     for (int i = 0; i < this.vehicles.size(); i++) {
/* 111 */       BaseVehicle candidate = this.vehicles.get(i);
/* 112 */       if (candidate.getQualifier() != null) {
/* 113 */         List<VehicleAttribute> attributes = candidate.getQualifier().getAttributes();
/* 114 */         for (int k = 0; k < attributes.size(); k++) {
/* 115 */           VehicleAttribute attribute = attributes.get(k);
/* 116 */           for (int j = 0; j < qualifiers.size(); j++) {
/* 117 */             if (((VehicleAttribute)qualifiers.get(j)).getID().equals(attribute.getID())) {
/* 118 */               attribute = null;
/*     */               break;
/*     */             } 
/*     */           } 
/* 122 */           if (attribute != null) {
/* 123 */             qualifiers.add(attribute);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 128 */     return (qualifiers.size() == 0) ? null : qualifiers;
/*     */   }
/*     */   
/*     */   public BaseVehicleSet reduce(String vin, Map selections, ClientContext context) {
/* 132 */     Iterator<BaseVehicle> it = this.vehicles.iterator();
/* 133 */     while (it.hasNext()) {
/* 134 */       BaseVehicle candidate = it.next();
/* 135 */       if (!candidate.qualifies(selections)) {
/* 136 */         it.remove(); continue;
/* 137 */       }  if (!candidate.getServiceID().equals(SID_NAO) && !isAuthorizedConfiguration(vin, candidate, selections, context)) {
/* 138 */         it.remove();
/*     */       }
/*     */     } 
/* 141 */     return new BaseVehicleSet(this.vehicles);
/*     */   }
/*     */   
/*     */   protected boolean isAuthorizedConfiguration(String vin, BaseVehicle candidate, Map selections, ClientContext context) {
/* 145 */     String make = null;
/* 146 */     String model = null;
/* 147 */     List<VehicleAttribute> attributes = candidate.getQualifier().getAttributes();
/* 148 */     for (int i = 0; i < attributes.size(); i++) {
/* 149 */       VehicleAttribute attribute = attributes.get(i);
/* 150 */       Integer valueID = (Integer)selections.get(attribute.getID());
/* 151 */       if (valueID != null) {
/* 152 */         String description = attribute.getDescription(Locale.ENGLISH);
/* 153 */         if (description.equalsIgnoreCase("badge") || description.equalsIgnoreCase("Make")) {
/* 154 */           make = candidate.getQualifier().getValue(attribute).getDescription(Locale.ENGLISH);
/* 155 */         } else if (description.equalsIgnoreCase("Model")) {
/* 156 */           model = candidate.getQualifier().getValue(attribute).getDescription(Locale.ENGLISH);
/*     */         } 
/*     */       } 
/*     */     } 
/* 160 */     if (make != null || model != null) {
/* 161 */       log.info("check vehicle context for make ='" + make + "', model='" + model + "'");
/* 162 */       boolean ret = false;
/* 163 */       IConfiguration cfg = VehicleConfigurationUtil.createVC(null, model, null, null, null);
/* 164 */       Make _make = VehicleConfigurationUtil.toMake(make);
/* 165 */       Set<Value> values = GlobalVCDataProvider.getInstance(context).getValues(VehicleConfigurationUtil.KEY_MAKE, cfg);
/* 166 */       for (Iterator<Value> iter = values.iterator(); iter.hasNext() && !ret; ) {
/* 167 */         Value value = iter.next();
/*     */         try {
/* 169 */           Set<Make> makes = VehicleConfigurationUtil.valueUtil.resolve(value, null);
/*     */           
/* 171 */           for (Iterator<Make> iterMakes = makes.iterator(); iterMakes.hasNext() && !ret; ) {
/* 172 */             Make _make2 = iterMakes.next();
/* 173 */             ret = _make2.toString().startsWith(_make.toString());
/*     */           } 
/* 175 */         } catch (UnresolvableException e) {
/* 176 */           log.warn("unable to resolve value " + String.valueOf(value) + ", skipping");
/*     */         } 
/*     */       } 
/* 179 */       return ret;
/*     */     } 
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getQualifierValues(VehicleAttribute attribute) {
/* 186 */     List<VehicleValue> values = new ArrayList();
/* 187 */     Iterator<BaseVehicle> it = this.vehicles.iterator();
/* 188 */     while (it.hasNext()) {
/* 189 */       BaseVehicle candidate = it.next();
/* 190 */       VehicleValue value = candidate.getQualifier().getValue(attribute);
/* 191 */       if (value != null && !values.contains(value)) {
/* 192 */         values.add(value);
/*     */       }
/*     */     } 
/* 195 */     return (values.size() == 0) ? null : values;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\BaseVehicleSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */