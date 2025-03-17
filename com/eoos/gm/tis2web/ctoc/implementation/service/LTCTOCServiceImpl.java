/*     */ package com.eoos.gm.tis2web.ctoc.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.CTOCImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.CachingStrategy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTCTOCServiceImpl
/*     */   extends CTOCServiceImpl
/*     */   implements LTCTOCService
/*     */ {
/*  52 */   private static AtomicInteger instanceCount = new AtomicInteger();
/*     */   
/*     */   private Logger log;
/*     */   
/*  56 */   private final Object SYNC_CTOC = new Object();
/*     */   
/*  58 */   private CTOCImpl ctoc = null;
/*     */   private Configuration cfg; private CTOCFactory factory; private ConnectionProvider connectionProvider; private final Object SYNC_SUPPORTED_CFGS; private Filter supportedCfgsFilter; private Set configurations; private Cache resultCache;
/*     */   public void reset() {
/*     */     synchronized (this.SYNC_CTOC) {
/*     */       this.log.debug("resetting");
/*     */       this.ctoc = null;
/*     */     } 
/*     */   }
/*     */   
/*  67 */   public LTCTOCServiceImpl(Configuration cfg, CTOCFactory factory, ILVCAdapter.Retrieval lvcRetrieval, FTSService.Retrieval ftsRetrieval) { super(null, lvcRetrieval, ftsRetrieval);
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
/* 137 */     this.SYNC_SUPPORTED_CFGS = new Object();
/* 138 */     this.supportedCfgsFilter = null;
/*     */     
/* 140 */     this.configurations = null;
/*     */     
/* 142 */     this.resultCache = Tis2webUtil.createStdCache(); this.log = Logger.getLogger(LTCTOCServiceImpl.class.getName() + "/" + instanceCount.getAndIncrement()); this.cfg = cfg; this.factory = factory; try { final DatabaseLink dbLink = DatabaseLink.openDatabase(this.cfg, "db"); this.connectionProvider = ConNvent.create(new ConnectionProvider() {
/*     */             public void releaseConnection(Connection connection) { dbLink.releaseConnection(connection); } public Connection getConnection() { try { return dbLink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  }
/*     */           },  60000L); } catch (Exception e) { throw new RuntimeException(e); }
/* 145 */      } public synchronized boolean supports(IConfiguration cfg) { Boolean result = (Boolean)this.resultCache.lookup(cfg);
/* 146 */     if (result == null) {
/* 147 */       result = Boolean.valueOf(VehicleConfigurationUtil.cfgUtil.supports((CfgProvider)this, cfg));
/* 148 */       this.resultCache.store(cfg, result);
/*     */     } 
/* 150 */     return result.booleanValue(); } public CTOC getCTOC() { synchronized (this.SYNC_CTOC) { if (this.ctoc == null)
/*     */         try { DatabaseLink databaseLink; new CachingStrategy(); Map<Object, Object> factories = new ConcurrentHashMap<Object, Object>(); ConfiguredServiceProvider.getInstance().getService(LTService.class); factories.put(CTOCType.MajorOperation, this.factory); factories.put(CTOCType.SI, this.factory); IDatabaseLink db = null; try { databaseLink = DatabaseLink.openDatabase(this.cfg, "db"); } catch (Exception e) { this.log.error("failed to init data source: " + ((databaseLink != null) ? databaseLink.getDatabaseLinkDescription() : null), e); throw new ExceptionWrapper(e); }  this.ctoc = new CTOCImpl((IDatabaseLink)databaseLink, CTOCDomain.LT, factories, this.lvcRetrieval, null, null, this.ftsRetrieval); }
/*     */         catch (Exception e) { this.log.error("unable to init ctoc for labour times - exception:" + e); throw new ExceptionWrapper(e); }
/*     */           return (CTOC)this.ctoc; }
/*     */      }
/* 155 */   private Connection getConnection() throws SQLException { Connection conn = this.connectionProvider.getConnection();
/* 156 */     conn.setReadOnly(true);
/* 157 */     conn.setAutoCommit(false);
/* 158 */     return conn; }
/*     */ 
/*     */   
/*     */   public Set getConfigurations() {
/* 162 */     synchronized (this.SYNC_SUPPORTED_CFGS) {
/* 163 */       if (this.configurations == null) {
/* 164 */         this.log.debug("determining supported vehicle configurations...");
/* 165 */         StopWatch sw = StopWatch.getInstance().start();
/*     */         try {
/* 167 */           this.configurations = (Set)CollectionUtil.filterAndReturn(new HashSet(this.lvcRetrieval.getLVCAdapter().getConfigurations()), getSupportedCfgsFilter());
/* 168 */           this.log.debug("...done (" + sw.stop() + " ms)");
/* 169 */         } catch (SQLException e) {
/* 170 */           this.log.error("unable to determine set of supported vehicle configurations, returning empty set - exception: " + e, e);
/* 171 */           return Collections.EMPTY_SET;
/*     */         } finally {
/* 173 */           StopWatch.freeInstance(sw);
/*     */         } 
/*     */       } 
/* 176 */       return this.configurations;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Filter getSupportedCfgsFilter() throws SQLException {
/* 181 */     synchronized (this.SYNC_SUPPORTED_CFGS) {
/* 182 */       if (this.supportedCfgsFilter == null) {
/* 183 */         this.log.debug("...initializing configuration filter...");
/* 184 */         Connection connection = getConnection();
/*     */         
/*     */         try {
/* 187 */           boolean hasFilterTable = false;
/*     */           
/* 189 */           ResultSet rs = connection.getMetaData().getTables(null, null, "SUBSET", new String[] { "TABLE" });
/*     */           try {
/* 191 */             hasFilterTable = rs.next();
/*     */           } finally {
/* 193 */             rs.close();
/*     */           } 
/*     */ 
/*     */           
/* 197 */           if (!hasFilterTable) {
/* 198 */             this.log.debug("...filter table not found, setting filter to INCLUDE_ALL");
/* 199 */             this.supportedCfgsFilter = Filter.INCLUDE_ALL;
/*     */           } else {
/* 201 */             Collection<IConfiguration> cfgs = new LinkedHashSet();
/* 202 */             PreparedStatement stmt = connection.prepareStatement("select  a.\"SUBSET\", a.\"VALUE\"  from \"SUBSET\" a where UPPER(a.\"SUBSET\")='VCR'");
/*     */             try {
/* 204 */               ResultSet resultSet = stmt.executeQuery();
/*     */               try {
/* 206 */                 while (resultSet.next()) {
/* 207 */                   String value = resultSet.getString(2);
/*     */                   
/* 209 */                   Object[] values = new Object[5];
/* 210 */                   Arrays.fill(values, VehicleConfigurationUtil.valueManagement.getANY());
/*     */                   
/* 212 */                   String[] parts = value.split("\\s*,\\s*");
/* 213 */                   for (int j = 0; j < parts.length; j++) {
/* 214 */                     Object key = VehicleConfigurationUtil.KEYS[j];
/* 215 */                     values[j] = VehicleConfigurationUtil.toModelObject(key, parts[j]);
/*     */                   } 
/*     */                   
/* 218 */                   cfgs.add(VehicleConfigurationUtil.createCfg(values[0], values[1], values[2], values[3], values[4]));
/*     */                 } 
/*     */               } finally {
/*     */                 
/* 222 */                 JDBCUtil.close(resultSet, this.log);
/*     */               } 
/*     */             } finally {
/*     */               
/* 226 */               JDBCUtil.close(stmt, this.log);
/*     */             } 
/*     */             
/* 229 */             this.log.debug("...found " + cfgs.size() + " filter configurations ...");
/* 230 */             if (this.log.isDebugEnabled() && cfgs.size() < 10) {
/* 231 */               this.log.debug("......cfgs: " + cfgs);
/*     */             }
/* 233 */             this.log.debug("...creating filter...");
/* 234 */             this.supportedCfgsFilter = VehicleConfigurationUtil.cfgUtil.createInclusionFilter(cfgs);
/*     */           } 
/*     */         } finally {
/*     */           
/* 238 */           this.connectionProvider.releaseConnection(connection);
/*     */         } 
/*     */       } 
/* 241 */       return this.supportedCfgsFilter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 246 */     return this.lvcRetrieval.getLVCAdapter().getKeys();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 250 */     return 0L;
/*     */   }
/*     */   
/*     */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 254 */     this.log.debug("resolving VIN: " + vin + "...");
/*     */     try {
/* 256 */       Set<IConfiguration> ret = new HashSet(CollectionUtil.toSet(this.lvcRetrieval.getLVCAdapter().resolveVIN(vin)));
/* 257 */       for (Iterator<IConfiguration> iter = ret.iterator(); iter.hasNext(); ) {
/* 258 */         IConfiguration cfg = iter.next();
/* 259 */         if (!getSupportedCfgsFilter().include(cfg)) {
/* 260 */           this.log.debug("...removing unsupported cfg: " + cfg);
/* 261 */           iter.remove();
/*     */         } 
/*     */       } 
/* 264 */       this.log.debug("...returning " + ret.size() + " supported configurations");
/* 265 */       return ret;
/* 266 */     } catch (SQLException e) {
/* 267 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\service\LTCTOCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */