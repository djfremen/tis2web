/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaAdapterFacade
/*     */   implements SchemaAdapterAPI, CfgProviderRetrieval, VINResolverRetrieval
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(SchemaAdapterFacade.class);
/*     */   
/*  29 */   public static final Attribute ATTR_SESSION_VALIDATION = AttributeImpl.getInstance(SchemaAdapterFacade.class.getName() + "session.validation");
/*     */   
/*  31 */   private static SchemaAdapterFacade instance = null;
/*     */   
/*     */   private ServiceResolution delegate;
/*     */   
/*  35 */   private Long validationTimestamp = null;
/*     */   
/*     */   private SchemaAdapterFacade() {
/*  38 */     log.info("initializing");
/*  39 */     this.delegate = ServiceResolution.getInstance();
/*  40 */     this.delegate.init();
/*  41 */     this.validationTimestamp = Long.valueOf(System.currentTimeMillis());
/*  42 */     this.delegate.addResetObserver(new ServiceResolution.ResetObserver()
/*     */         {
/*     */           public void onReset() {
/*  45 */             SchemaAdapterFacade.log.debug("being notified about reset, updating validation timestamp");
/*  46 */             SchemaAdapterFacade.this.validationTimestamp = Long.valueOf(System.currentTimeMillis());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */   
/*     */   public static synchronized SchemaAdapterFacade getInstance() {
/*  57 */     if (instance == null) {
/*  58 */       instance = new SchemaAdapterFacade();
/*     */     }
/*  60 */     return instance;
/*     */   }
/*     */   
/*     */   private void validateSession(AttributeValueMap avMap) throws AdapterResetException {
/*  64 */     Long ts = (Long)AVUtil.accessValue(avMap, ATTR_SESSION_VALIDATION);
/*  65 */     if (ts == null) {
/*  66 */       log.debug("found no validation timestamp for session: " + AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID) + ", setting current");
/*  67 */       avMap.set(ATTR_SESSION_VALIDATION, (Value)new ValueAdapter(this.validationTimestamp));
/*  68 */     } else if (!ts.equals(this.validationTimestamp)) {
/*  69 */       log.debug("found contradicting timestamp for session: " + AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID) + ", throwing exception");
/*  70 */       throw new AdapterResetException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Boolean reprogram(ProgrammingData data, AttributeValueMap avMap) throws RequestException, Exception {
/*  75 */     validateSession(avMap);
/*  76 */     return this.delegate.reprogram(data, avMap);
/*     */   }
/*     */   
/*     */   public ProgrammingData getProgrammingData(AttributeValueMap avMap) throws RequestException, Exception {
/*  80 */     validateSession(avMap);
/*  81 */     return this.delegate.getProgrammingData(avMap);
/*     */   }
/*     */   
/*     */   public Controller getController(AttributeValueMap avMap) throws RequestException, Exception {
/*  85 */     validateSession(avMap);
/*  86 */     return this.delegate.getController(avMap);
/*     */   }
/*     */   
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit, AttributeValueMap avMap) throws Exception {
/*  90 */     validateSession(avMap);
/*  91 */     return this.delegate.getData(dataUnit, avMap);
/*     */   }
/*     */   
/*     */   public Collection getCalibrationVerificationNumber(String sessionID, String partNumber) {
/*  95 */     return this.delegate.getCalibrationVerificationNumber(sessionID, partNumber);
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) {
/*  99 */     return this.delegate.getBulletin(locale, bulletin);
/*     */   }
/*     */   
/*     */   public String getHTML(String locale, String id) {
/* 103 */     return this.delegate.getHTML(locale, id);
/*     */   }
/*     */   
/*     */   public byte[] getImage(String id) {
/* 107 */     return this.delegate.getImage(id);
/*     */   }
/*     */   
/*     */   public Object getVersionInfo() {
/* 111 */     return this.delegate.getVersionInfo();
/*     */   }
/*     */   
/*     */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 115 */     validateSession(avMap);
/* 116 */     return this.delegate.getDatabaseInfo(avMap, lastRequestedAttribute);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 120 */     this.delegate.reset();
/*     */   }
/*     */   
/*     */   public Set getCfgProviders() {
/* 124 */     return this.delegate.getCfgProviders();
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 128 */     return this.delegate.getVINResolvers(context);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\SchemaAdapterFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */