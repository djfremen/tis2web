/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ 
/*     */ import com.eoos.context.Context;
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.DownloadServerProvider;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.AdapterResetException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.SchemaAdapterSPI;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SPSSchemaAdapter
/*     */   implements SchemaAdapterSPI, IContext
/*     */ {
/*  38 */   private static final Logger log = Logger.getLogger(SPSSchemaAdapter.class);
/*     */   
/*     */   public static final String ARCHIVE_EXTENSION = "zip";
/*     */   
/*     */   public static final String PART_FILE_EXTENSION = "prt";
/*     */   
/*  44 */   public static final Attribute ATTR_SESSION_VALIDATION = AttributeImpl.getInstance(SPSSchemaAdapter.class.getName() + ".session.validation");
/*     */   
/*  46 */   private Long validationTimestamp = null;
/*     */   
/*  48 */   private IContext contextDelegate = (IContext)new Context();
/*     */   
/*  50 */   private final Object syncObject = new Object();
/*     */   
/*     */   protected Configuration configuration;
/*     */   
/*  54 */   private List<Util.Time> resetTimes = null;
/*  55 */   private int nextResetTimeIndex = -1;
/*  56 */   private TimerTask nextReset = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object SYNC_DS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DownloadServer downloadServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SPSVoltAttribute voltAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ScheduledReset
/*     */     implements Runnable
/*     */   {
/*     */     private WeakReference wrAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ScheduledReset(SPSSchemaAdapter adapter) {
/*  92 */       this.wrAdapter = new WeakReference<SPSSchemaAdapter>(adapter);
/*     */     }
/*     */     
/*     */     public void run() {
/*  96 */       SPSSchemaAdapter adapter = this.wrAdapter.get();
/*  97 */       if (adapter != null) {
/*  98 */         SPSSchemaAdapter.log.info("executing scheduled reset...");
/*  99 */         adapter.reset();
/* 100 */         adapter.scheduleNextReset();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void scheduleNextReset() {
/* 108 */     this.nextResetTimeIndex = ++this.nextResetTimeIndex % this.resetTimes.size();
/* 109 */     Date date = Util.toDate(null, this.resetTimes.get(this.nextResetTimeIndex), true);
/* 110 */     log.debug("next scheduled reset at : " + date);
/* 111 */     this.nextReset = Util.createTimerTask(new ScheduledReset(this));
/* 112 */     Util.getTimer().schedule(this.nextReset, date);
/*     */   }
/*     */   
/*     */   public Configuration getConfiguration() {
/* 116 */     return this.configuration;
/*     */   }
/*     */   
/*     */   protected void validateSession(AttributeValueMap avMap) throws AdapterResetException {
/* 120 */     Long ts = (Long)AVUtil.accessValue(avMap, ATTR_SESSION_VALIDATION);
/* 121 */     if (ts == null) {
/* 122 */       log.debug("found no validation timestamp for session: " + AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID) + ", setting current");
/* 123 */       avMap.set(ATTR_SESSION_VALIDATION, (Value)new ValueAdapter(this.validationTimestamp));
/* 124 */     } else if (!ts.equals(this.validationTimestamp)) {
/* 125 */       log.debug("found contradicting timestamp for session: " + AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID) + ", throwing exception");
/* 126 */       throw new AdapterResetException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getSyncObject() {
/* 131 */     return this.syncObject;
/*     */   }
/*     */   
/*     */   public void storeObject(Object identifier, Object data) {
/* 135 */     this.contextDelegate.storeObject(identifier, data);
/*     */   }
/*     */   
/*     */   public Object getObject(Object identifier) {
/* 139 */     return this.contextDelegate.getObject(identifier);
/*     */   }
/*     */   
/*     */   public Collection getObjects(Filter filter) {
/* 143 */     return this.contextDelegate.getObjects(filter);
/*     */   }
/*     */   
/*     */   public void removeObject(Object identifier) {
/* 147 */     this.contextDelegate.removeObject(identifier);
/*     */   }
/*     */   
/*     */   public final Controller getController(AttributeValueMap avMap) throws RequestException, Exception {
/* 151 */     validateSession(avMap);
/* 152 */     return _getController(avMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ProgrammingData getProgrammingData(AttributeValueMap avMap) throws RequestException, Exception {
/* 158 */     validateSession(avMap);
/* 159 */     return _getProgrammingData(avMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Boolean reprogram(ProgrammingData data, AttributeValueMap avMap) throws RequestException, Exception {
/* 165 */     validateSession(avMap);
/* 166 */     return _reprogram(data, avMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getData(ProgrammingDataUnit blob, AttributeValueMap avMap) throws RequestException, Exception {
/* 172 */     validateSession(avMap);
/* 173 */     return _getData(blob, avMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   protected void reset(SPSSession session) throws Exception {
/* 183 */     session.reset(null, this);
/*     */   }
/*     */   
/*     */   protected void reset(SPSSession session, Attribute attribute) throws Exception {
/* 187 */     session.reset(attribute, this);
/*     */   }
/*     */   
/*     */   protected boolean assertVIN(SPSSession session, AttributeValueMap data) {
/* 191 */     String vin = (String)AVUtil.accessValue(data, CommonAttribute.VIN);
/* 192 */     return (vin == null) ? false : vin.equals(session.getVehicle().getVIN().toString());
/*     */   }
/*     */   
/*     */   protected boolean assertSessionTag(SPSSession session, AttributeValueMap data) {
/* 196 */     Long tag = (Long)AVUtil.accessValue(data, CommonAttribute.SESSION_TAG);
/* 197 */     return (tag == null) ? true : ((tag.longValue() == session.getTag()));
/*     */   }
/*     */   
/*     */   protected boolean assertSessionSignature(SPSSession session, AttributeValueMap data) {
/* 201 */     Long signature = (Long)AVUtil.accessValue(data, CommonAttribute.SESSION_SIGNATURE);
/* 202 */     return (signature == null) ? false : ((signature.longValue() == session.getSignature()));
/*     */   }
/*     */   
/*     */   protected boolean isPassThru(AttributeValueMap data) throws Exception {
/* 206 */     String tool = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 207 */     if (tool == null || (!tool.equals("T2_REMOTE") && !tool.equals("TEST_DRIVER")))
/* 208 */       return true; 
/* 209 */     if (tool.equals("TEST_DRIVER")) {
/* 210 */       String dtype = (String)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_TYPE);
/* 211 */       if (dtype != null && (dtype.equalsIgnoreCase("pass-thru") || dtype.equalsIgnoreCase("J2534"))) {
/* 212 */         return true;
/*     */       }
/*     */     } 
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) {
/* 219 */     return null;
/*     */   }
/*     */   
/*     */   public String getHTML(String locale, String id) {
/* 223 */     return null;
/*     */   }
/*     */   
/*     */   public byte[] getImage(String id) {
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 235 */     this.validationTimestamp = Long.valueOf(System.currentTimeMillis());
/* 236 */     this.contextDelegate = (IContext)new Context();
/* 237 */     _reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 244 */     if (this.nextReset != null) {
/* 245 */       this.nextReset.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   protected SPSSchemaAdapter(Configuration configuration) {
/* 250 */     this.SYNC_DS = new Object();
/* 251 */     this.downloadServer = null; this.configuration = configuration; this.validationTimestamp = Long.valueOf(System.currentTimeMillis()); initVoltAttributes(this.configuration); try { this.resetTimes = ConfigurationUtil.getList(configuration, "reset.times", new Util.ObjectCreation<Util.Time>() { public Util.Time createObject(String string) { return Util.parseTime(string); } }
/*     */         ); if (!Util.isNullOrEmpty(this.resetTimes)) { Collections.sort(this.resetTimes); log.debug("reset times: " + this.resetTimes); Date now = new Date(System.currentTimeMillis()); for (int i = 0; i < this.resetTimes.size(); i++) { if (Util.toDate(now, this.resetTimes.get(i), false).after(now))
/*     */             this.nextResetTimeIndex = i - 1;  }  scheduleNextReset(); }  } catch (Exception e) { log.warn("unable to schedule automatic reset(s), ignoring - exception: ", e); }
/* 254 */      } public DownloadServer getCalibrationDataDownloadSite() { synchronized (this.SYNC_DS) {
/* 255 */       if (this.downloadServer == null) {
/* 256 */         String id = this.configuration.getProperty("calibration-download.site");
/* 257 */         if (!Util.isNullOrEmpty(id)) {
/* 258 */           this.downloadServer = DownloadServerProvider.getServer(id);
/*     */         }
/*     */       } 
/* 261 */       return this.downloadServer;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initVoltAttributes(Configuration configuration) {
/* 268 */     this.voltAttributes = new SPSVoltAttribute(configuration);
/*     */   }
/*     */   
/*     */   public SPSVoltAttribute getVoltAttributeHandler() {
/* 272 */     return this.voltAttributes;
/*     */   }
/*     */   
/*     */   protected abstract Controller _getController(AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
/*     */   
/*     */   protected abstract ProgrammingData _getProgrammingData(AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
/*     */   
/*     */   protected abstract Boolean _reprogram(ProgrammingData paramProgrammingData, AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
/*     */   
/*     */   protected abstract byte[] _getData(ProgrammingDataUnit paramProgrammingDataUnit, AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
/*     */   
/*     */   public abstract Object getVersionInfo();
/*     */   
/*     */   public abstract IDatabaseLink getDatabaseLink();
/*     */   
/*     */   protected abstract void _reset();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSSchemaAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */