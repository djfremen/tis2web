/*     */ package com.eoos.gm.tis2web.dtc.cas;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.common.AuthenticatorCI;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*     */ import com.eoos.gm.tis2web.dtc.cas.api.IDTCUpload;
/*     */ import com.eoos.gm.tis2web.dtc.cas.api.Identifier;
/*     */ import com.eoos.gm.tis2web.dtc.cas.api.UnavailableServiceException;
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.DTCImpl;
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.DTCStorageTask;
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.ExpiredLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.MissingLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.idfactory.IDFactory;
/*     */ import com.eoos.idfactory.SystemTimeBasedIDFactory;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.net.Authenticator;
/*     */ import java.net.URL;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public final class DTCUpload
/*     */   implements IDTCUpload
/*     */ {
/*  61 */   private static final Logger log = Logger.getLogger(DTCUpload.class);
/*     */   
/*     */   private File wrkDir;
/*     */ 
/*     */   
/*     */   public static final class SecurityException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*  71 */   private final Object SYNC_IDF = new Object();
/*     */   
/*  73 */   private IDFactory idFactory = null;
/*     */   
/*  75 */   private final Object SYNC_TEC = new Object();
/*     */   
/*  77 */   private TaskExecutionClient taskExecution = null;
/*     */   
/*  79 */   private final Object SYNC_SWK = new Object();
/*     */   
/*  81 */   private SoftwareKey softwareKey = null;
/*     */   
/*  83 */   private final Object SYNC_LEASE = new Object();
/*     */   
/*  85 */   private Lease lease = null;
/*     */   
/*     */   private String applicationID;
/*     */   
/*     */   public DTCUpload(String applicationID, File wrkDir, AuthenticationQuery authenticationCallback) {
/*  90 */     this.applicationID = applicationID;
/*  91 */     this.wrkDir = wrkDir;
/*  92 */     if (!this.wrkDir.exists() && !this.wrkDir.mkdirs())
/*  93 */       throw new IllegalArgumentException("unable to create working dir: " + String.valueOf(wrkDir)); 
/*  94 */     if (!this.wrkDir.isDirectory()) {
/*  95 */       throw new IllegalArgumentException(String.valueOf(wrkDir) + " is not a directory");
/*     */     }
/*     */     
/*  98 */     if (authenticationCallback != null) {
/*  99 */       Authenticator.setDefault((Authenticator)new AuthenticatorCI(authenticationCallback));
/*     */     }
/*     */     
/* 102 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 106 */               DTCUpload.log.debug("trying to upload deferred data (if any)");
/* 107 */               DTCUpload.this.upload(null);
/* 108 */             } catch (Exception e) {
/* 109 */               DTCUpload.log.warn("unable to upload deferred data, ignoring - exception: " + e, e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SoftwareKey getSoftwareKey() {
/* 119 */     synchronized (this.SYNC_SWK) {
/* 120 */       if (this.softwareKey == null) {
/* 121 */         String sessionID = System.getProperty("session.id");
/* 122 */         if (!Util.isNullOrEmpty(sessionID)) {
/* 123 */           log.debug("...detected session identifier, assuming online mode");
/* 124 */           this.softwareKey = (SoftwareKey)new SessionKey(sessionID);
/*     */         } else {
/* 126 */           log.debug("...offline mode");
/* 127 */           DLSService dlsService = DLSServiceFactory.createService(null);
/* 128 */           this.softwareKey = dlsService.getSoftwareKey();
/*     */         } 
/*     */       } 
/*     */       
/* 132 */       return this.softwareKey;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Lease getLease() throws ExpiredLeaseException, MissingLeaseException {
/* 137 */     synchronized (this.SYNC_LEASE) {
/* 138 */       if (this.lease == null && !(getSoftwareKey() instanceof SessionKey)) {
/* 139 */         DLSService dlsService = DLSServiceFactory.createService(null);
/* 140 */         this.lease = dlsService.getNewestValidLease();
/*     */       } 
/* 142 */       return this.lease;
/*     */     } 
/*     */   }
/*     */   
/*     */   private TaskExecutionClient getTaskExecution() throws ExpiredLeaseException, MissingLeaseException {
/* 147 */     synchronized (this.SYNC_TEC) {
/* 148 */       if (this.taskExecution == null) {
/* 149 */         if (getLease() != null) {
/* 150 */           URL url = DLSServiceFactory.createService(null).getURL(getLease());
/* 151 */           log.debug("...retrieved server URL for lease: " + String.valueOf(url));
/* 152 */           this.taskExecution = TaskExecutionClientFactory.createTaskExecutionClient(url);
/*     */         } else {
/* 154 */           this.taskExecution = TaskExecutionClientFactory.createTaskExecutionClient();
/*     */         } 
/*     */       }
/* 157 */       return this.taskExecution;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean uploadEnabled() throws IOException {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private IDFactory getIDFactory() {
/* 172 */     synchronized (this.SYNC_IDF) {
/* 173 */       if (this.idFactory == null) {
/* 174 */         this.idFactory = (IDFactory)SystemTimeBasedIDFactory.getInstance();
/*     */       }
/* 176 */       return this.idFactory;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Identifier store(Set data) throws IOException {
/* 182 */     Set<FileIDAdapter> ids = new HashSet(data.size());
/* 183 */     synchronized (this.wrkDir) {
/* 184 */       for (Iterator<byte[]> iter = data.iterator(); iter.hasNext(); ) {
/* 185 */         byte[] dtc = iter.next();
/* 186 */         File file = null;
/* 187 */         while (file == null || file.exists()) {
/* 188 */           file = new File(this.wrkDir, getIDFactory().createID().toString() + ".dtc");
/*     */         }
/* 190 */         FileOutputStream fos = new FileOutputStream(file);
/*     */         try {
/* 192 */           fos.write(dtc);
/*     */         } finally {
/* 194 */           fos.close();
/*     */         } 
/* 196 */         ids.add(new FileIDAdapter(file));
/*     */       } 
/*     */     } 
/* 199 */     return new IdentifierContainer(ids);
/*     */   }
/*     */   
/*     */   private void startUpload() {
/* 203 */     (new Thread() {
/*     */         public void run() {
/* 205 */           DTCUpload.this._upload();
/*     */         }
/*     */       }).start();
/*     */   }
/*     */   
/*     */   private boolean _upload() {
/*     */     try {
/* 212 */       synchronized (this.wrkDir) {
/*     */         
/* 214 */         File[] files = this.wrkDir.listFiles(new FilenameFilter()
/*     */             {
/*     */               public boolean accept(File dir, String name) {
/* 217 */                 return name.endsWith(".dtc");
/*     */               }
/*     */             });
/*     */         
/* 221 */         if (files != null && files.length != 0) {
/* 222 */           List<DTCImpl> dtcs = new LinkedList();
/* 223 */           for (int i = 0; i < files.length; i++) {
/* 224 */             FileInputStream fis = new FileInputStream(files[i]);
/*     */             try {
/* 226 */               dtcs.add(new DTCImpl(StreamUtil.readFully(fis), getBAC(), getCountryCode(), this.applicationID, getPortalID()));
/*     */             } finally {
/* 228 */               fis.close();
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 233 */           DTCStorageTask task = new DTCStorageTask(getSoftwareKey(), getLease(), dtcs);
/* 234 */           DTCStorageTask.resolveResult(getTaskExecution().execute((Task)task));
/* 235 */           for (int j = 0; j < files.length; j++) {
/* 236 */             if (!files[j].delete()) {
/* 237 */               log.warn("unable to delete file for transferred dtc: " + files[j].getAbsolutePath());
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 242 */         return true;
/*     */       } 
/* 244 */     } catch (Throwable t) {
/* 245 */       log.error("unable to upload files  - exception:" + t, t);
/* 246 */       return false;
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
/*     */   public Identifier upload(Set data) throws SecurityException, IOException, UnavailableServiceException {
/* 258 */     Identifier ret = null;
/* 259 */     if (data != null && data.size() > 0) {
/* 260 */       ret = store(data);
/*     */     } else {
/* 262 */       ret = lookupDeferred();
/*     */     } 
/* 264 */     startUpload();
/* 265 */     return ret;
/*     */   }
/*     */   
/*     */   private Identifier lookupDeferred() {
/* 269 */     log.debug("looking for deferred data...");
/* 270 */     Set<FileIDAdapter> ids = new HashSet();
/* 271 */     synchronized (this.wrkDir) {
/* 272 */       File[] files = this.wrkDir.listFiles(new FilenameFilter()
/*     */           {
/*     */             public boolean accept(File dir, String name) {
/* 275 */               return name.endsWith(".dtc");
/*     */             }
/*     */           });
/*     */       
/* 279 */       if (files != null) {
/* 280 */         for (int i = 0; i < files.length; i++) {
/* 281 */           ids.add(new FileIDAdapter(files[i]));
/*     */         }
/*     */       }
/*     */     } 
/* 285 */     if (log.isDebugEnabled()) {
/* 286 */       log.debug("....found " + (ids.isEmpty() ? "none" : String.valueOf(ids)));
/*     */     }
/* 288 */     return new IdentifierContainer(ids);
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
/*     */   public boolean finished(Identifier id) {
/* 300 */     boolean ret = true;
/* 301 */     IdentifierContainer ic = (IdentifierContainer)id;
/* 302 */     for (Iterator<FileIDAdapter> iter = ic.getIdentifiers().iterator(); iter.hasNext() && ret; ) {
/* 303 */       FileIDAdapter fia = iter.next();
/* 304 */       ret = !fia.getFile().exists();
/*     */     } 
/* 306 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 316 */     synchronized (this.wrkDir) {
/* 317 */       File[] files = this.wrkDir.listFiles(new FilenameFilter()
/*     */           {
/*     */             public boolean accept(File dir, String name) {
/* 320 */               return name.endsWith(".dtc");
/*     */             }
/*     */           });
/*     */       
/* 324 */       if (files != null) {
/* 325 */         for (int i = 0; i < files.length; i++) {
/* 326 */           if (!files[i].delete()) {
/* 327 */             throw new IllegalStateException("unable to delete file : " + String.valueOf(files[i]));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getBAC() {
/* 335 */     return System.getProperty("bac.code");
/*     */   }
/*     */   
/*     */   private String getCountryCode() {
/* 339 */     return System.getProperty("country.code");
/*     */   }
/*     */   
/*     */   private String getPortalID() {
/* 343 */     String ret = System.getProperty("portal.id");
/* 344 */     if (Util.isNullOrEmpty(ret)) {
/* 345 */       ret = System.getProperty("session.id");
/* 346 */       if (!Util.isNullOrEmpty(ret)) {
/* 347 */         ret = ret.substring(0, ret.indexOf('.'));
/*     */       }
/*     */     } 
/* 350 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\DTCUpload.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */