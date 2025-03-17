/*     */ package com.eoos.gm.tis2web.vc.v2.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.UnresolvableException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class VCFacade
/*     */   implements IVCFacade
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(VCFacade.class); private VCService vcservice; private ClientContext context; private final Object SYNC_SMKDOMAIN; private List salesmakeDomain;
/*     */   
/*     */   public static IVCFacade getInstance(ClientContext context) {
/*  35 */     synchronized (context.getLockObject()) {
/*  36 */       IVCFacade instance = (IVCFacade)context.getObject(VCFacade.class);
/*  37 */       if (instance == null) {
/*  38 */         instance = new VCFacade(context);
/*  39 */         if (ApplicationContext.getInstance().developMode()) {
/*  40 */           instance = (IVCFacade)Tis2webUtil.hookWithExecutionTimeStatistics(instance, context);
/*     */         }
/*  42 */         context.storeObject(VCFacade.class, instance);
/*     */       } 
/*  44 */       return instance;
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
/*     */   private VCFacade(ClientContext context) {
/* 166 */     this.SYNC_SMKDOMAIN = new Object();
/* 167 */     this.salesmakeDomain = null; this.vcservice = VCServiceProvider.getInstance().getService(context); this.context = context;
/*     */   }
/*     */   public void addObserver(VehicleCfgStorage.Observer observer) { this.vcservice.addObserver(observer); }
/* 170 */   public VIN asVIN(String vin) throws VIN.InvalidVINException { return (VIN)new VINImpl(vin); } public IConfiguration createConfiguration(String make, String model, String modelyear, String engine, String transmission) { Make _make = VehicleConfigurationUtil.toMake(make); Model _model = VehicleConfigurationUtil.toModel(model); Modelyear _modelyear = VehicleConfigurationUtil.toModelyear(modelyear); Engine _engine = VehicleConfigurationUtil.toEngine(engine); Transmission _transmission = VehicleConfigurationUtil.toTransmission(transmission); return VehicleConfigurationUtil.createVC(_make, _model, _modelyear, _engine, _transmission); } public IConfiguration getCfg() { return this.vcservice.getCfg(); } public String getCurrentSalesmake() { String ret = null; IConfiguration cfg = getCfg(); if (cfg != null) { Make make = VehicleConfigurationUtil.getMake(cfg); if (make != null) ret = getDisplayValue(make);  }  return ret; } public String getCurrentModel() { String ret = null; IConfiguration cfg = getCfg(); if (cfg != null) { Model model = VehicleConfigurationUtil.getModel(cfg); ret = VehicleConfigurationUtil.toString(model); }  return ret; } public Collection getSalesmakeDomain() { synchronized (this.SYNC_SMKDOMAIN)
/* 171 */     { if (this.salesmakeDomain == null) {
/* 172 */         Set ret = new HashSet();
/* 173 */         for (Iterator<Value> iter = GlobalVCDataProvider.getInstance(this.context).getValues(VehicleConfigurationUtil.KEY_MAKE, VehicleConfigurationUtil.cfgManagement.getEmptyConfiguration()).iterator(); iter.hasNext(); ) {
/* 174 */           Value value = iter.next();
/*     */           try {
/* 176 */             ret.addAll(VehicleConfigurationUtil.valueManagement.resolve(value, null));
/* 177 */           } catch (UnresolvableException e) {
/* 178 */             log.warn("unable to resolve value: " + String.valueOf(value) + ", ignoring - exception:" + e, (Throwable)e);
/*     */           } 
/*     */         } 
/* 181 */         this.salesmakeDomain = CollectionUtil.toSortedList(ret, new VCService.ValueComparator(this.context));
/*     */       } 
/* 183 */       return this.salesmakeDomain; }  }
/*     */   public String getCurrentModelyear() { String ret = null; IConfiguration cfg = getCfg(); if (cfg != null) { Modelyear modelyear = VehicleConfigurationUtil.getModelyear(cfg); ret = VehicleConfigurationUtil.toString(modelyear); }  return ret; }
/*     */   public String getCurrentEngine() { String ret = null; IConfiguration cfg = getCfg(); if (cfg != null) { Engine engine = VehicleConfigurationUtil.getEngine(cfg); ret = VehicleConfigurationUtil.toString(engine); }  return ret; }
/*     */   public String getCurrentTransmission() { String ret = null; IConfiguration cfg = getCfg(); if (cfg != null) { Transmission transmission = VehicleConfigurationUtil.getTransmission(cfg); ret = VehicleConfigurationUtil.toString(transmission); }  return ret; }
/*     */   public String getCurrentVCDisplay(boolean includeVIN) { IConfiguration cfg = this.vcservice.getCfg(); String ret = null; if (cfg != null) ret = this.vcservice.getDisplayValue(cfg);  String vin; if (includeVIN && (vin = getCurrentVIN()) != null && vin.trim().length() > 0) if (Util.isNullOrEmpty(ret)) { ret = vin; } else { ret = ret + ", " + vin; }   return (ret == null) ? "" : ret; }
/* 188 */   public String getCurrentVIN() { VIN vin = this.vcservice.getVIN(); if (vin != null) return vin.toString();  return null; } public HtmlCodeRenderer getDialog(VCService.DialogCallback callback) { return this.vcservice.getDialog(callback); } public String getDisplayValue(Object obj) { return this.vcservice.getDisplayValue(obj); } public Collection getValidModels(IConfiguration cfg) { Set ret = new HashSet();
/* 189 */     for (Iterator<Value> iter = GlobalVCDataProvider.getInstance(this.context).getValues(VehicleConfigurationUtil.KEY_MODEL, cfg).iterator(); iter.hasNext(); ) {
/* 190 */       Value value = iter.next();
/*     */       try {
/* 192 */         ret.addAll(VehicleConfigurationUtil.valueManagement.resolve(value, null));
/* 193 */       } catch (UnresolvableException e) {
/* 194 */         log.warn("unable to resolve value: " + String.valueOf(value) + ", ignoring - exception:" + e, (Throwable)e);
/*     */       } 
/*     */     } 
/* 197 */     return ret; }
/*     */ 
/*     */   
/*     */   public Collection getValidModelyears(IConfiguration cfg) {
/* 201 */     Set ret = new HashSet();
/* 202 */     for (Iterator<Value> iter = GlobalVCDataProvider.getInstance(this.context).getValues(VehicleConfigurationUtil.KEY_MODELYEAR, cfg).iterator(); iter.hasNext(); ) {
/* 203 */       Value value = iter.next();
/*     */       try {
/* 205 */         ret.addAll(VehicleConfigurationUtil.valueManagement.resolve(value, null));
/* 206 */       } catch (UnresolvableException e) {
/* 207 */         log.warn("unable to resolve value: " + String.valueOf(value) + ", ignoring - exception:" + e, (Throwable)e);
/*     */       } 
/*     */     } 
/* 210 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection getValidEngines(IConfiguration cfg) {
/* 214 */     Set ret = new HashSet();
/* 215 */     for (Iterator<Value> iter = GlobalVCDataProvider.getInstance(this.context).getValues(VehicleConfigurationUtil.KEY_ENGINE, cfg).iterator(); iter.hasNext(); ) {
/* 216 */       Value value = iter.next();
/*     */       try {
/* 218 */         ret.addAll(VehicleConfigurationUtil.valueManagement.resolve(value, null));
/* 219 */       } catch (UnresolvableException e) {
/* 220 */         log.warn("unable to resolve value: " + String.valueOf(value) + ", ignoring - exception:" + e, (Throwable)e);
/*     */       } 
/*     */     } 
/* 223 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection getValidTransmissions(IConfiguration cfg) {
/* 227 */     Set ret = new HashSet();
/* 228 */     for (Iterator<Value> iter = GlobalVCDataProvider.getInstance(this.context).getValues(VehicleConfigurationUtil.KEY_TRANSMISSION, cfg).iterator(); iter.hasNext(); ) {
/* 229 */       Value value = iter.next();
/*     */       try {
/* 231 */         ret.addAll(VehicleConfigurationUtil.valueManagement.resolve(value, null));
/* 232 */       } catch (UnresolvableException e) {
/* 233 */         log.warn("unable to resolve value: " + String.valueOf(value) + ", ignoring - exception:" + e, (Throwable)e);
/*     */       } 
/*     */     } 
/* 236 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean isCurrentSalesmake(String salesmake) {
/* 240 */     Make make = VehicleConfigurationUtil.toMake(salesmake);
/* 241 */     IConfiguration cfg = this.vcservice.getCfg();
/* 242 */     if (cfg != null) {
/* 243 */       Make currentMake = VehicleConfigurationUtil.getMake(cfg);
/* 244 */       return VehicleConfigurationUtil.equals(make, currentMake);
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeObserver(VehicleCfgStorage.Observer observer) {
/* 251 */     this.vcservice.removeObserver(observer);
/*     */   }
/*     */   
/*     */   public void setSalesmake(String make) {
/* 255 */     Make _make = VehicleConfigurationUtil.toMake(make);
/* 256 */     setSalesmake(_make);
/*     */   }
/*     */   
/*     */   public void setSalesmake(Make make) {
/* 260 */     IConfiguration vc = VehicleConfigurationUtil.createVC(make, null, null, null, null);
/* 261 */     this.vcservice.storeCfg(vc, null);
/*     */   }
/*     */   
/*     */   public void storeCfg(IConfiguration cfg, VIN vin) {
/* 265 */     this.vcservice.storeCfg(cfg, vin);
/*     */   }
/*     */   
/*     */   public static Make toMake(String make) {
/* 269 */     return VehicleConfigurationUtil.toMake(make);
/*     */   }
/*     */   
/*     */   public void setVIN(String vin) throws VIN.InvalidVINException {
/* 273 */     VIN _vin = asVIN(vin);
/* 274 */     if (!Util.equals(this.vcservice.getVIN(), _vin)) {
/* 275 */       storeCfg(null, _vin);
/*     */     }
/*     */   }
/*     */   
/*     */   public VIN getVIN() {
/* 280 */     return this.vcservice.getVIN();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VCFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */