/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.util.HashCalc;
/*     */ import com.eoos.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ class SchemaAdapterWrapper
/*     */   implements CfgProviderRetrieval, SchemaAdapterSPI, VINResolverRetrieval
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(SchemaAdapterWrapper.class);
/*     */   
/*     */   enum InitMode {
/*  27 */     EAGER, LAZY;
/*     */   }
/*     */   
/*  30 */   private InitMode initMode = InitMode.LAZY;
/*     */   
/*     */   private String identifier;
/*     */   
/*     */   private SchemaAdapterSPI adapter;
/*  35 */   private final Object SYNC_INIT = new Object();
/*     */   
/*     */   private boolean initialized = false;
/*     */   
/*     */   public SchemaAdapterWrapper(String identifier, InitMode initMode, SchemaAdapterSPI adapter) throws Exception {
/*  40 */     this.identifier = identifier;
/*  41 */     this.adapter = adapter;
/*  42 */     this.initMode = (initMode != null) ? initMode : InitMode.LAZY;
/*  43 */     if (this.initMode == InitMode.EAGER) {
/*  44 */       init();
/*     */     }
/*     */   }
/*     */   
/*     */   private SchemaAdapterSPI getAdapter() throws Exception {
/*  49 */     synchronized (this.SYNC_INIT) {
/*  50 */       if (this.initMode != InitMode.EAGER && !this.initialized) {
/*  51 */         init();
/*     */       }
/*  53 */       return this.adapter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public InitMode getInitMode() {
/*  58 */     return this.initMode;
/*     */   }
/*     */   
/*     */   public String getIdentifier() {
/*  62 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  66 */     return getClass().getName() + "@" + String.valueOf(this.identifier);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  70 */     boolean retValue = false;
/*  71 */     if (this == obj) {
/*  72 */       retValue = true;
/*  73 */     } else if (obj instanceof SchemaAdapterWrapper) {
/*  74 */       SchemaAdapterWrapper wrapper = (SchemaAdapterWrapper)obj;
/*  75 */       retValue = Util.equals(this.identifier, wrapper.identifier);
/*     */     } 
/*  77 */     return retValue;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  81 */     int retValue = getClass().hashCode();
/*  82 */     retValue = HashCalc.addHashCode(retValue, this.identifier);
/*  83 */     return retValue;
/*     */   }
/*     */   
/*     */   public Boolean reprogram(ProgrammingData data, AttributeValueMap avMap) throws RequestException, Exception {
/*  87 */     return getAdapter().reprogram(data, avMap);
/*     */   }
/*     */   
/*     */   public ProgrammingData getProgrammingData(AttributeValueMap avMap) throws RequestException, Exception {
/*  91 */     return getAdapter().getProgrammingData(avMap);
/*     */   }
/*     */   
/*     */   public Controller getController(AttributeValueMap avMap) throws RequestException, Exception {
/*  95 */     return getAdapter().getController(avMap);
/*     */   }
/*     */   
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit, AttributeValueMap avMap) throws Exception {
/*  99 */     return getAdapter().getData(dataUnit, avMap);
/*     */   }
/*     */   
/*     */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/*     */     try {
/* 104 */       return getAdapter().getCalibrationVerificationNumber(sessionID, partNumber);
/* 105 */     } catch (Exception e) {
/* 106 */       throw new RuntimeException("unable to retrieve calibration verification number", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) {
/*     */     try {
/* 112 */       return getAdapter().getBulletin(locale, bulletin);
/* 113 */     } catch (Exception e) {
/* 114 */       throw new RuntimeException("unable to retrieve bulletin", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHTML(String locale, String id) {
/*     */     try {
/* 121 */       return getAdapter().getHTML(locale, id);
/* 122 */     } catch (Exception e) {
/* 123 */       throw new RuntimeException("unable to retrieve html", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getImage(String id) {
/*     */     try {
/* 130 */       return getAdapter().getImage(id);
/* 131 */     } catch (Exception e) {
/* 132 */       throw new RuntimeException("unable to retrieve image data", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getVersionInfo() {
/*     */     try {
/* 139 */       return getAdapter().getVersionInfo();
/* 140 */     } catch (Exception e) {
/* 141 */       throw new RuntimeException("unable to retrieve version information", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 146 */     return getAdapter().getDatabaseInfo(avMap, lastRequestedAttribute);
/*     */   }
/*     */   
/*     */   public void reset() throws Exception {
/* 150 */     synchronized (this.SYNC_INIT) {
/* 151 */       this.adapter.reset();
/* 152 */       this.initialized = false;
/* 153 */       if (this.initMode == InitMode.EAGER) {
/* 154 */         init();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/* 160 */     log.debug("initializing wrapped adapter");
/* 161 */     this.adapter.init();
/* 162 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   public Set getCfgProviders() {
/* 166 */     if (this.adapter instanceof CfgProviderRetrieval)
/* 167 */       return ((CfgProviderRetrieval)this.adapter).getCfgProviders(); 
/* 168 */     if (this.adapter instanceof CfgProvider) {
/* 169 */       return Collections.singleton((CfgProvider)this.adapter);
/*     */     }
/* 171 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 176 */     if (this.adapter instanceof VINResolverRetrieval)
/* 177 */       return ((VINResolverRetrieval)this.adapter).getVINResolvers(context); 
/* 178 */     if (this.adapter instanceof VINResolver) {
/* 179 */       return Collections.singleton((VINResolver)this.adapter);
/*     */     }
/* 181 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 187 */     this.adapter.close();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\SchemaAdapterWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */