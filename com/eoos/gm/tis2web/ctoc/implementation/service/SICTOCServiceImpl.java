/*     */ package com.eoos.gm.tis2web.ctoc.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.CTOCImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SICTOCServiceImpl
/*     */   extends CTOCServiceImpl
/*     */   implements SICTOCService
/*     */ {
/*  46 */   private static AtomicInteger instanceCount = new AtomicInteger();
/*     */   
/*     */   private Logger log;
/*     */   
/*  50 */   private final Object SYNC_CTOC = new Object();
/*     */   
/*  52 */   private CTOCImpl ctoc = null; private Configuration cfg; private ConnectionProvider connectionProvider; private SICTOCService.Retrieval retrieval;
/*     */   private final Object SYNC_SUPPORTED_CFGS;
/*     */   private Filter supportedCfgsFilter;
/*     */   private Set configurations;
/*     */   private Cache resultCache;
/*     */   
/*  58 */   public SICTOCServiceImpl(Configuration cfg, ILVCAdapter.Retrieval lvcRetrieval, SI.Retrieval siRetrieval, FTSService.Retrieval ftsRetrieval) { super(siRetrieval, lvcRetrieval, ftsRetrieval);
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
/* 118 */     this.retrieval = null;
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
/* 132 */     this.SYNC_SUPPORTED_CFGS = new Object();
/* 133 */     this.supportedCfgsFilter = null;
/*     */     
/* 135 */     this.configurations = null;
/*     */     
/* 137 */     this.resultCache = Tis2webUtil.createStdCache(); this.log = Logger.getLogger(SICTOCServiceImpl.class.getName() + "/" + instanceCount.getAndIncrement()); this.cfg = cfg; try { final IDatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(cfg, "db.")); this.connectionProvider = ConNvent.create(new ConnectionProvider() { public void releaseConnection(Connection connection) { dbLink.releaseConnection(connection); } public Connection getConnection() { try { return dbLink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  }
/*     */           },  60000L); } catch (Exception e) { throw new RuntimeException(e); }  }
/*     */   public void reset() { synchronized (this.SYNC_CTOC) { this.log.debug("resetting"); this.ctoc = null; }  }
/* 140 */   public synchronized boolean supports(IConfiguration cfg) { Boolean result = (Boolean)this.resultCache.lookup(cfg);
/* 141 */     if (result == null) {
/* 142 */       result = Boolean.valueOf(VehicleConfigurationUtil.cfgUtil.supports((CfgProvider)this, cfg));
/* 143 */       this.resultCache.store(cfg, result);
/*     */     } 
/* 145 */     return result.booleanValue(); }
/*     */   public CTOC getCTOC() { synchronized (this.SYNC_CTOC) { if (this.ctoc == null)
/*     */         try { this.log.info("initializing ctoc "); Map<Object, Object> factories = new ConcurrentHashMap<Object, Object>(); CTOCFactory factory = this.siRetrieval.getSI().getFactory(); factories.put(CTOCType.SI, factory); factories.put(CTOCType.WD, factory); factories.put(CTOCType.CPR, factory); IDatabaseLink db = null; try { db = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(this.cfg, "db.")); } catch (Exception e) { this.log.error("failed to init data source: " + ((db != null) ? db.getDatabaseLinkDescription() : null), e); }  this.ctoc = new CTOCImpl(db, CTOCDomain.SI, factories, this.lvcRetrieval, this.siRetrieval, createRetrievalImpl(), this.ftsRetrieval); }
/*     */         catch (Exception e) { throw new RuntimeException(e); }
/*     */           return (CTOC)this.ctoc; }
/* 150 */      } private Connection getConnection() throws SQLException { Connection conn = this.connectionProvider.getConnection();
/* 151 */     conn.setReadOnly(true);
/* 152 */     conn.setAutoCommit(false);
/* 153 */     return conn; } public synchronized SICTOCService.Retrieval createRetrievalImpl() { if (this.retrieval == null)
/*     */       this.retrieval = new SICTOCService.Retrieval() {
/*     */           public SICTOCService getSICTOCService() { return SICTOCServiceImpl.this; }
/*     */         }; 
/* 157 */     return this.retrieval; } public Set getConfigurations() { synchronized (this.SYNC_SUPPORTED_CFGS) {
/* 158 */       if (this.configurations == null) {
/*     */         try {
/* 160 */           this.configurations = (Set)CollectionUtil.filterAndReturn(new HashSet(this.lvcRetrieval.getLVCAdapter().getConfigurations()), getSupportedCfgsFilter());
/* 161 */         } catch (SQLException e) {
/* 162 */           this.log.error("unable to determine set of supported vehicle configurations, returning empty set - exception: " + e, e);
/* 163 */           return Collections.EMPTY_SET;
/*     */         } 
/*     */       }
/* 166 */       return this.configurations;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private Filter getSupportedCfgsFilter() throws SQLException {
/* 171 */     synchronized (this.SYNC_SUPPORTED_CFGS) {
/* 172 */       if (this.supportedCfgsFilter == null) {
/* 173 */         this.log.debug("initializing configuration filter...");
/* 174 */         Connection connection = getConnection();
/*     */         
/*     */         try {
/* 177 */           boolean hasFilterTable = false;
/*     */           
/* 179 */           ResultSet rs = connection.getMetaData().getTables(null, null, "SUBSET", new String[] { "TABLE" });
/*     */           try {
/* 181 */             hasFilterTable = rs.next();
/*     */           } finally {
/* 183 */             rs.close();
/*     */           } 
/*     */ 
/*     */           
/* 187 */           if (!hasFilterTable) {
/* 188 */             this.log.debug("...filter table not found, setting filter to INCLUDE_ALL");
/* 189 */             this.supportedCfgsFilter = Filter.INCLUDE_ALL;
/*     */           } else {
/* 191 */             Collection<IConfiguration> cfgs = new LinkedHashSet();
/* 192 */             PreparedStatement stmt = connection.prepareStatement("select  a.\"SUBSET\", a.\"VALUE\"  from \"SUBSET\" a where UPPER(a.\"SUBSET\")='VCR'");
/*     */             try {
/* 194 */               ResultSet resultSet = stmt.executeQuery();
/*     */               try {
/* 196 */                 while (resultSet.next()) {
/* 197 */                   String value = resultSet.getString(2);
/*     */                   
/* 199 */                   Object[] values = new Object[5];
/* 200 */                   Arrays.fill(values, VehicleConfigurationUtil.valueManagement.getANY());
/*     */                   
/* 202 */                   String[] parts = value.split("\\s*,\\s*");
/* 203 */                   for (int j = 0; j < parts.length; j++) {
/* 204 */                     Object key = VehicleConfigurationUtil.KEYS[j];
/* 205 */                     values[j] = VehicleConfigurationUtil.toModelObject(key, parts[j]);
/*     */                   } 
/*     */                   
/* 208 */                   cfgs.add(VehicleConfigurationUtil.createCfg(values[0], values[1], values[2], values[3], values[4]));
/*     */                 } 
/*     */               } finally {
/*     */                 
/* 212 */                 JDBCUtil.close(resultSet, this.log);
/*     */               } 
/*     */             } finally {
/*     */               
/* 216 */               JDBCUtil.close(stmt, this.log);
/*     */             } 
/*     */             
/* 219 */             this.log.debug("...found " + cfgs.size() + " filter configurations ...");
/* 220 */             if (this.log.isDebugEnabled() && cfgs.size() < 10) {
/* 221 */               this.log.debug("......cfgs: " + cfgs);
/*     */             }
/* 223 */             this.log.debug("...creating filter...");
/* 224 */             this.supportedCfgsFilter = VehicleConfigurationUtil.cfgUtil.createInclusionFilter(cfgs);
/*     */           } 
/*     */         } finally {
/*     */           
/* 228 */           this.connectionProvider.releaseConnection(connection);
/*     */         } 
/*     */       } 
/* 231 */       return this.supportedCfgsFilter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 236 */     return this.lvcRetrieval.getLVCAdapter().getKeys();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 240 */     return 0L;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\service\SICTOCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */