/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINDecoder;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.IVCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class VCProxy
/*     */   implements CfgProviderRetrieval, VINResolverRetrieval
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(VCProxy.class);
/*     */   
/*     */   protected ILVCAdapter lvc;
/*     */   
/*     */   protected VINDecoder decoder;
/*     */   
/*     */   public VCProxy(SPSSchemaAdapterGME adapter) {
/*  34 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(adapter.getConfiguration(), "vc.");
/*  35 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper(adapter.getConfiguration(), "vcr.");
/*     */     
/*  37 */     if (!Util.isNullOrEmpty(subConfigurationWrapper1.getProperty("db.url"))) {
/*  38 */       this.lvc = LVCAdapterProvider.getAdapter((Configuration)subConfigurationWrapper1, (Configuration)subConfigurationWrapper2);
/*     */     } else {
/*  40 */       log.warn("!! missing configuration for adapter specific vc database, using global service");
/*  41 */       this.lvc = LVCAdapterProvider.getGlobalAdapter();
/*     */     } 
/*  43 */     this.decoder = this.lvc.getVC().getVINDecoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public DBVersionInformation getDatabaseInfo() {
/*  48 */     return this.lvc.getVC().getVersionInfo();
/*     */   }
/*     */   
/*     */   public static synchronized VCProxy getInstance(SPSSchemaAdapterGME context) {
/*  52 */     VCProxy instance = (VCProxy)context.getObject(VCProxy.class);
/*  53 */     if (instance == null) {
/*  54 */       instance = new VCProxy(context);
/*  55 */       context.storeObject(VCProxy.class, instance);
/*     */     } 
/*  57 */     return instance;
/*     */   }
/*     */   
/*     */   public List resolve(String vin) throws Exception {
/*  61 */     return this.decoder.decode(vin);
/*     */   }
/*     */   
/*     */   public ILVCAdapter getLVCAdapter() {
/*  65 */     return this.lvc;
/*     */   }
/*     */   
/*     */   public String getSalesmake(String sessionID) throws Exception {
/*  69 */     return VCFacade.getInstance(ClientContextProvider.getInstance().getContext(sessionID, false)).getCurrentSalesmake();
/*     */   }
/*     */   
/*     */   public List getDomain(String domain) throws Exception {
/*  73 */     if (domain.equals("Make"))
/*  74 */       return this.lvc.getSalesmakeDomain(); 
/*  75 */     if (domain.equals("Model"))
/*  76 */       return this.lvc.getModelDomain(); 
/*  77 */     if (domain.equals("Engine"))
/*  78 */       return this.lvc.getEngineDomain(); 
/*  79 */     if (domain.equals("Transmission")) {
/*  80 */       return this.lvc.getTransmissionDomain();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String normalize(String value) {
/*  87 */     if (value == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     StringBuffer buffer = new StringBuffer();
/*  91 */     for (int i = 0; i < value.length(); i++) {
/*  92 */       char c = value.charAt(i);
/*  93 */       if (c != ' ') {
/*  94 */         buffer.append(c);
/*     */       }
/*     */     } 
/*  97 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   protected String checkEngine(String engine) {
/* 101 */     if (engine == null) {
/* 102 */       return null;
/*     */     }
/*     */     try {
/* 105 */       engine = normalize(engine);
/* 106 */       List<VCRValue> values = this.lvc.getEngineDomain();
/* 107 */       if (values != null) {
/* 108 */         for (int i = 0; i < values.size(); i++) {
/* 109 */           VCRValue e = values.get(i);
/* 110 */           if (engine.equalsIgnoreCase(normalize(e.toString()))) {
/* 111 */             return e.toString();
/*     */           }
/*     */         } 
/*     */       }
/* 115 */     } catch (Exception x) {}
/*     */     
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public void setVC(String sessionID, SPSVehicle vehicle) {
/*     */     try {
/* 122 */       SPSVIN vin = (SPSVIN)vehicle.getVIN();
/* 123 */       String make = vin.getSalesMakeVC();
/* 124 */       String model = vin.getModelVC();
/* 125 */       String modelyear = vin.getModelYearVC();
/* 126 */       String engine = checkEngine(vehicle.getEngine());
/* 127 */       IVCFacade vc = VCFacade.getInstance(ClientContextProvider.getInstance().getContext(sessionID));
/* 128 */       IConfiguration cfg = vc.createConfiguration(make, model, modelyear, engine, null);
/* 129 */       VIN _vin = vc.asVIN(vin.toString());
/* 130 */       vc.storeCfg(cfg, _vin);
/* 131 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getCfgProviders() {
/* 136 */     return Collections.singleton(this.lvc);
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 140 */     return Collections.singleton(this.lvc);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\VCProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */