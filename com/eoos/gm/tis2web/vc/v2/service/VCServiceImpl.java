/*     */ package com.eoos.gm.tis2web.vc.v2.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.impl.ui.html.dialog.VCDialog;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.StoredCfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VCServiceImpl
/*     */   implements VCService
/*     */ {
/*     */   private ClientContext context;
/*  28 */   private IConfiguration currentCfg = null;
/*     */   
/*  30 */   private VIN vin = null;
/*     */   
/*  32 */   private IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */   
/*     */   private VCServiceImpl(ClientContext context) {
/*  35 */     this.context = context;
/*  36 */     this.currentCfg = VehicleConfigurationUtil.createVC((Make)null, null, null, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void init(VCServiceImpl vcservice) {
/*  41 */     StoredCfgDataProvider.getInstance(vcservice.context, vcservice);
/*     */   }
/*     */   
/*     */   public static VCServiceImpl getInstance(ClientContext context) {
/*  45 */     synchronized (context.getLockObject()) {
/*  46 */       VCServiceImpl instance = (VCServiceImpl)context.getObject(VCServiceImpl.class);
/*  47 */       if (instance == null) {
/*  48 */         instance = new VCServiceImpl(context);
/*  49 */         init(instance);
/*  50 */         context.storeObject(VCServiceImpl.class, instance);
/*     */       } 
/*  52 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  57 */     return this;
/*     */   }
/*     */   
/*     */   private String getLabel(String key, Locale locale) {
/*  61 */     return ApplicationContext.getInstance().getLabel(locale, key.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   private boolean isVCValueObject(Object obj) {
/*  65 */     boolean ret = obj instanceof Make;
/*  66 */     ret = (ret || obj instanceof com.eoos.gm.tis2web.vc.v2.value.Model);
/*  67 */     ret = (ret || obj instanceof com.eoos.gm.tis2web.vc.v2.value.Modelyear);
/*  68 */     ret = (ret || obj instanceof com.eoos.gm.tis2web.vc.v2.value.Engine);
/*  69 */     ret = (ret || obj instanceof com.eoos.gm.tis2web.vc.v2.value.Transmission);
/*  70 */     return ret;
/*     */   }
/*     */   
/*     */   public String getDisplayValue(Object obj) {
/*  74 */     Locale locale = this.context.getLocale();
/*  75 */     if (isVCValueObject(obj))
/*  76 */       return VehicleConfigurationUtil.toString_Normalized(obj); 
/*  77 */     if (obj instanceof Class)
/*  78 */       return getLabel(Util.getClassName((Class)obj).toString(), locale); 
/*  79 */     if (obj instanceof IConfiguration) {
/*  80 */       IConfiguration vc = (IConfiguration)obj;
/*  81 */       return VehicleConfigurationUtil.toString(vc, new VehicleConfigurationUtil.DisplayCallback()
/*     */           {
/*     */             public String getDisplayValue(Object obj) {
/*  84 */               return VCServiceImpl.this.getDisplayValue(obj);
/*     */             }
/*     */           },  false);
/*     */     } 
/*     */     
/*  89 */     if (obj instanceof Value && VehicleConfigurationUtil.valueManagement.isANY((Value)obj)) {
/*  90 */       return getLabel("value.any", locale);
/*     */     }
/*  92 */     return String.valueOf(obj);
/*     */   }
/*     */   
/*     */   public HtmlCodeRenderer getDialog(VCService.DialogCallback callback) {
/*  96 */     return (HtmlCodeRenderer)new VCDialog(this.context, callback);
/*     */   }
/*     */   
/*     */   public IConfiguration getCfg() {
/* 100 */     return this.currentCfg;
/*     */   }
/*     */   
/* 103 */   private static final Notification NOTIF_VCMOD = new Notification()
/*     */     {
/*     */       public void notify(Object observer) {
/* 106 */         ((VehicleCfgStorage.Observer)observer).onVehicleConfigurationChange();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public void storeCfg(IConfiguration cfg, VIN vin) {
/* 112 */     boolean notify = !VehicleConfigurationUtil.cfgUtil.equals(this.currentCfg, cfg);
/* 113 */     notify = (notify || !Util.equals(this.vin, vin));
/* 114 */     this.currentCfg = cfg;
/* 115 */     this.vin = vin;
/* 116 */     if (notify) {
/* 117 */       this.observableSupport.notifyObservers(NOTIF_VCMOD);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addObserver(VehicleCfgStorage.Observer observer) {
/* 122 */     this.observableSupport.addObserver(observer);
/*     */   }
/*     */   
/*     */   public void removeObserver(VehicleCfgStorage.Observer observer) {
/* 126 */     this.observableSupport.removeObserver(observer);
/*     */   }
/*     */   
/*     */   public VIN getVIN() {
/* 130 */     return this.vin;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */