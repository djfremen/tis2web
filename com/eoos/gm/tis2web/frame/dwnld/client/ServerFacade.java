/*     */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.MissingAuthenticationException;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.GetDataTask;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.METask;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ServerFacade
/*     */   implements FileDataProvider
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(ServerFacade.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SoftwareKey swk;
/*     */ 
/*     */ 
/*     */   
/*     */   private Lease lease;
/*     */ 
/*     */ 
/*     */   
/*     */   private TaskExecutionClient taskExecutionClient;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerFacade(SoftwareKey swk, Lease lease, Configuration configuration, TaskExecutionClient taskExecution) {
/*  41 */     log.debug("initializing...");
/*  42 */     this.swk = swk;
/*  43 */     log.debug("...swk is " + ((this.swk != null) ? "set" : "null"));
/*  44 */     this.lease = lease;
/*  45 */     log.debug("...lease is " + ((this.lease != null) ? "set" : "null"));
/*     */     
/*  47 */     this.taskExecutionClient = taskExecution;
/*     */     
/*  49 */     log.debug("...done initializing");
/*     */   }
/*     */   
/*     */   private Object resolve(Object result) throws Exception {
/*  53 */     if (result instanceof Throwable) {
/*  54 */       return Util.rethrow((Throwable)result);
/*     */     }
/*  56 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getDownloadUnits(Collection filters) throws MissingAuthenticationException {
/*     */     try {
/*  62 */       METask task = new METask(this.swk, this.lease, "getDownloadUnits", new Object[] { filters });
/*  63 */       return (Collection)resolve(this.taskExecutionClient.execute((Task)task));
/*  64 */     } catch (MissingAuthenticationException e) {
/*  65 */       throw e;
/*  66 */     } catch (Exception e) {
/*  67 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTransferTimeEstimate(long bytes) {
/*  73 */     return this.taskExecutionClient.getTimeEstimate(bytes);
/*     */   }
/*     */   
/*     */   public void getData(DownloadFile file, OutputStream os) throws IOException, MissingAuthenticationException {
/*  77 */     GetDataTask getDataTask = new GetDataTask(this.swk, this.lease, file, os);
/*  78 */     this.taskExecutionClient.execute((Task)getDataTask);
/*     */   }
/*     */   
/*     */   public Collection getRelatedUnits(Collection downloadUnits) throws MissingAuthenticationException {
/*     */     try {
/*  83 */       METask task = new METask(this.swk, this.lease, "getRelatedUnits", new Object[] { downloadUnits });
/*  84 */       return (Collection)resolve(this.taskExecutionClient.execute((Task)task));
/*  85 */     } catch (MissingAuthenticationException e) {
/*  86 */       throw e;
/*  87 */     } catch (Exception e) {
/*  88 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CookieWrapper getAkamaiCookie() throws MissingAuthenticationException {
/*     */     try {
/*  95 */       METask task = new METask(this.swk, this.lease, "getAkamaiCookie", null);
/*  96 */       return (CookieWrapper)resolve(this.taskExecutionClient.execute((Task)task));
/*  97 */     } catch (MissingAuthenticationException e) {
/*  98 */       throw e;
/*  99 */     } catch (Exception e) {
/* 100 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getAllDownloadURIs() throws MissingAuthenticationException {
/*     */     try {
/* 107 */       METask task = new METask(this.swk, this.lease, "getAllDownloadURIs", null);
/* 108 */       return (Collection)resolve(this.taskExecutionClient.execute((Task)task));
/* 109 */     } catch (MissingAuthenticationException e) {
/* 110 */       throw e;
/* 111 */     } catch (Exception e) {
/* 112 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logDwnldEvent(long tsStart, long tsEnd, Collection units, Object status) throws MissingAuthenticationException {
/*     */     try {
/* 119 */       METask task = new METask(this.swk, this.lease, "logDwnldEvent", new Object[] { Long.valueOf(tsStart), Long.valueOf(tsEnd), units, status });
/* 120 */       this.taskExecutionClient.execute((Task)task);
/* 121 */     } catch (MissingAuthenticationException e) {
/* 122 */       throw e;
/* 123 */     } catch (Exception e) {
/* 124 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAppServerDwnldMode() throws MissingAuthenticationException {
/*     */     try {
/* 131 */       METask task = new METask(this.swk, this.lease, "getAppServerDwnldMode", null);
/* 132 */       return (String)resolve(this.taskExecutionClient.execute((Task)task));
/* 133 */     } catch (MissingAuthenticationException e) {
/* 134 */       throw e;
/* 135 */     } catch (Exception e) {
/* 136 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\ServerFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */