/*     */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*     */ 
/*     */ import com.akamai.token.tokenFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.InvalidLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.DLSServiceServer;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.LeaseValidationProvider;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.WellKnownFilter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DwnldServiceServer
/*     */   implements IServer
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(DwnldServiceServer.class);
/*     */   
/*     */   private SoftwareKey swk;
/*     */   
/*     */   private Lease lease;
/*     */   
/*     */   private DwnldServiceServer(SoftwareKey swk, Lease lease) {
/*  37 */     this.swk = swk;
/*  38 */     this.lease = lease;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateLease(SoftwareKey swk, Lease lease) throws InvalidLeaseException {
/*  43 */     if (!LeaseValidationProvider.getLeaseValidation().validateLease(swk, lease)) {
/*  44 */       throw new InvalidLeaseException(lease);
/*     */     }
/*     */   }
/*     */   
/*     */   public static DwnldServiceServer getInstance(SoftwareKey swk, Lease lease) throws InvalidLeaseException {
/*  49 */     validateLease(swk, lease);
/*  50 */     return new DwnldServiceServer(swk, lease);
/*     */   }
/*     */   
/*     */   public Collection getDownloadUnits(Collection filters) {
/*  54 */     log.debug("retrieving download units for filter: " + String.valueOf(filters));
/*     */     try {
/*  56 */       log.debug("...retrieving units from database adapter");
/*  57 */       Collection ret = DatabaseAdapter.getInstance().getDownloadUnits(filters);
/*  58 */       log.debug("...filtering collection (original size:" + ret.size() + ") with access permission filter");
/*  59 */       final DownloadUnitAccessPermission accessCheck = DownloadUnitAccessPermission.getInstance(DLSServiceServer.getUserGroup(this.swk, this.lease));
/*  60 */       CollectionUtil.filter(ret, new Filter()
/*     */           {
/*     */             public boolean include(Object obj) {
/*  63 */               return accessCheck.check((DownloadUnit)obj);
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/*  69 */       if (filters != null && filters.contains(WellKnownFilter.NEWEST_VERSION)) {
/*  70 */         log.debug("...handling <newest version> filter");
/*  71 */         if (ret.size() > 1) {
/*  72 */           ret = CollectionUtil.mutableSingletonSet(getNewest(ret));
/*     */         }
/*     */       } 
/*  75 */       log.debug("...done retrieving download units, returning " + ret.size() + " units");
/*  76 */       return ret;
/*  77 */     } catch (Exception e) {
/*  78 */       log.error("unable to retrieve download units, returning empty collection - exception: " + e, e);
/*  79 */       return Collections.EMPTY_LIST;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DownloadUnit getNewest(Collection units) {
/*  84 */     DownloadUnit ret = null;
/*  85 */     for (Iterator<DownloadUnit> iter = units.iterator(); iter.hasNext(); ) {
/*  86 */       DownloadUnit unit = iter.next();
/*  87 */       if (ret == null || ret.getReleaseDate() < unit.getReleaseDate()) {
/*  88 */         ret = unit;
/*     */       }
/*     */     } 
/*  91 */     return ret;
/*     */   }
/*     */   
/*     */   public void getData(DownloadFile file, OutputStream os) throws IOException {
/*  95 */     log.debug("retrieving data for file : " + String.valueOf(file));
/*  96 */     DatabaseAdapter.getInstance().getData(file, os);
/*  97 */     log.debug("...done retrieving file data");
/*     */   }
/*     */   
/*     */   public Collection getRelatedUnits(Collection downloadUnits) {
/* 101 */     log.debug("retrieving related download units for unit(s): " + String.valueOf(downloadUnits));
/* 102 */     Collection ret = new HashSet();
/* 103 */     for (Iterator<DownloadUnit> iter = downloadUnits.iterator(); iter.hasNext(); ) {
/* 104 */       DownloadUnit downloadUnit = iter.next();
/* 105 */       ret.addAll(DatabaseAdapter.getInstance().getRelatedUnits(downloadUnit));
/*     */     } 
/* 107 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieWrapper getAkamaiCookie() {
/* 113 */     long l1, time = System.currentTimeMillis();
/* 114 */     String _duration = ApplicationContext.getInstance().getProperty("frame.dwnld.sec.token.duration");
/*     */     
/*     */     try {
/* 117 */       l1 = Util.parseMillis(_duration);
/* 118 */     } catch (Exception e) {
/* 119 */       log.warn("unable to parse value for token duration, setting duration to default (1 hour)");
/* 120 */       l1 = 3600000L;
/*     */     } 
/* 122 */     String access = ApplicationContext.getInstance().getProperty("frame.dwnld.sec.token.access");
/* 123 */     if (Util.isNullOrEmpty(access)) {
/* 124 */       access = "/gds/*";
/*     */     }
/*     */     
/* 127 */     tokenFactory fact = new tokenFactory();
/* 128 */     CookieWrapper ret = new CookieWrapper();
/* 129 */     ret.cookie = "eoos_akamai=" + fact.generateToken("", time / 1000L, l1 / 1000L, access, "greentomatoe");
/* 130 */     ret.expirationDate = time + l1;
/* 131 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection getAllDownloadURIs() {
/* 135 */     return DatabaseAdapter.getInstance().getAllDownloadURIs();
/*     */   }
/*     */   
/*     */   public void logDwnldEvent(long tsStart, long tsEnd, Collection downloadUnits, Object status) {
/* 139 */     if (log.isDebugEnabled()) {
/* 140 */       log.debug("received download event log request");
/*     */     }
/* 142 */     EventLogProvider.getEventLog().logDwnldEvent(DLSServiceServer.getSessionIDs(this.swk), tsStart, tsEnd, downloadUnits, status);
/*     */   }
/*     */   
/*     */   public String getAppServerDwnldMode() {
/* 146 */     String appServerDwnld = ApplicationContext.getInstance().getProperty("frame.dwnld.include.appserver");
/* 147 */     if (Util.isNullOrEmpty(appServerDwnld)) {
/* 148 */       appServerDwnld = "true";
/*     */     }
/* 150 */     return appServerDwnld;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\DwnldServiceServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */