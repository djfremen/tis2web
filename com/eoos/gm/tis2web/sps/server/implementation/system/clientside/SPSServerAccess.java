/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*     */ 
/*     */ import com.eoos.cache.Cache;
/*     */ import com.eoos.cache.implementation.FileCache;
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.system.ServerTaskExecution;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap.AttributeValueMapDeltaCommulatingWrapper;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap.AttributeValueMapImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapRI;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.io.ObservableInputStream;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.MultitonSupport;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSServerAccess
/*     */ {
/*  60 */   private static final Logger log = Logger.getLogger(SPSServerAccess.class);
/*     */   
/*     */   private static class MethodCallExecute
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     private AttributeValueMap avMap;
/*     */     
/*     */     public MethodCallExecute(String sessionID, AttributeValueMap avMap) {
/*  70 */       this.sessionID = sessionID;
/*  71 */       this.avMap = avMap;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/*  75 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/*  80 */         Object result = getSPSServer().execute(this.avMap);
/*  81 */         return new PairImpl(result, this.avMap);
/*  82 */       } catch (Exception e) {
/*  83 */         return new PairImpl(e, this.avMap);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/*  88 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MethodCallGetData
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private String sessionID;
/*     */     private ProgrammingDataUnit dataUnit;
/*     */     private AttributeValueMap avMap;
/*     */     
/*     */     public MethodCallGetData(String sessionID, ProgrammingDataUnit dataUnit, AttributeValueMap avMap) {
/* 103 */       this.sessionID = sessionID;
/* 104 */       this.dataUnit = dataUnit;
/* 105 */       this.avMap = avMap;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 109 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 114 */         return getSPSServer().getData(this.dataUnit, this.avMap);
/* 115 */       } catch (Exception e) {
/* 116 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MethodCallGetAkamaiCookie
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 133 */         return SPSServer.getAkamaiCookie();
/* 134 */       } catch (Exception e) {
/* 135 */         return e;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MethodCallGetBulletin
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private String sessionID;
/*     */     private String locale;
/*     */     private String bulletin;
/*     */     
/*     */     public MethodCallGetBulletin(String sessionID, String locale, String bulletin) {
/* 151 */       this.sessionID = sessionID;
/* 152 */       this.locale = locale;
/* 153 */       this.bulletin = bulletin;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 157 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 162 */         return getSPSServer().getBulletin(this.locale, this.bulletin);
/* 163 */       } catch (Exception e) {
/* 164 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 169 */       return super.toString() + "[sessionID:" + this.sessionID + ", locale:" + String.valueOf(this.locale) + ", bulletinID:" + this.bulletin + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MethodCallGetHTML
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     private String locale;
/*     */     private String id;
/*     */     
/*     */     public MethodCallGetHTML(String sessionID, String locale, String id) {
/* 183 */       this.sessionID = sessionID;
/* 184 */       this.locale = locale;
/* 185 */       this.id = id;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 189 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 194 */         return getSPSServer().getHTML(this.locale, this.id);
/* 195 */       } catch (Exception e) {
/* 196 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 201 */       return super.toString() + "[sessionID:" + this.sessionID + ", locale:" + String.valueOf(this.locale) + ", htmlID:" + this.id + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MethodCallGetImage
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     private String id;
/*     */     
/*     */     public MethodCallGetImage(String sessionID, String id) {
/* 213 */       this.sessionID = sessionID;
/* 214 */       this.id = id;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 218 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 223 */         return getSPSServer().getImage(this.id);
/* 224 */       } catch (Exception e) {
/* 225 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 230 */       return super.toString() + "[sessionID:" + this.sessionID + ", imageID:" + this.id + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MethodCallGetNavTableData
/*     */     implements Task, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     
/*     */     public MethodCallGetNavTableData(String sessionID) {
/* 240 */       this.sessionID = sessionID;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 244 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 249 */         return getSPSServer().getNavigationTableData();
/* 250 */       } catch (Exception e) {
/* 251 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 256 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MC_GetHWKReplacement
/*     */     implements Task, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     
/*     */     public MC_GetHWKReplacement(String sessionID) {
/* 266 */       this.sessionID = sessionID;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 270 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 275 */         return getSPSServer().getHWKReplacement();
/* 276 */       } catch (Exception e) {
/* 277 */         SPSServerAccess.log.error("unable to retrieve hwk replacement - exception:" + e, e);
/* 278 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 283 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MC_SendMail
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private String sessionID;
/*     */     
/*     */     private String sender;
/*     */     
/*     */     private Collection recipients;
/*     */     private String subject;
/*     */     private String text;
/*     */     private Collection attachments;
/*     */     
/*     */     public MC_SendMail(String sessionID, String sender, Collection recipients, String subject, String text, Collection attachments) {
/* 303 */       this.sessionID = sessionID;
/* 304 */       this.sender = sender;
/* 305 */       this.recipients = recipients;
/* 306 */       this.subject = subject;
/* 307 */       this.text = text;
/* 308 */       this.attachments = attachments;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 312 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 317 */         getSPSServer().sendEmail(this.sender, this.recipients, this.subject, this.text, this.attachments);
/* 318 */         return null;
/* 319 */       } catch (Exception e) {
/* 320 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 325 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MC_HardwareID
/*     */     implements Task, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     
/*     */     public MC_HardwareID(String sessionID) {
/* 335 */       this.sessionID = sessionID;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 339 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 344 */         return getSPSServer().getHardwareID();
/* 345 */       } catch (Exception e) {
/* 346 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 351 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MC_GetMessage
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     private String errorCode;
/*     */     private AttributeValueMap data;
/*     */     
/*     */     public MC_GetMessage(String sessionID, String errorCode, AttributeValueMap data) {
/* 365 */       this.sessionID = sessionID;
/* 366 */       this.errorCode = errorCode;
/* 367 */       this.data = data;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 371 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 376 */         return getSPSServer().getMessage(this.errorCode, this.data);
/* 377 */       } catch (Exception e) {
/* 378 */         return e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 383 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MC_SendENMail
/*     */     implements Task, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String sessionID;
/*     */     private String errorCode;
/*     */     private AttributeValueMap data;
/*     */     
/*     */     public MC_SendENMail(String sessionID, String errorCode, AttributeValueMap data) {
/* 397 */       this.sessionID = sessionID;
/* 398 */       this.errorCode = errorCode;
/* 399 */       this.data = data;
/*     */     }
/*     */     
/*     */     protected SPSServer getSPSServer() throws InvalidSessionException {
/* 403 */       return SPSServer.getInstance(this.sessionID);
/*     */     }
/*     */     
/*     */     public Object execute() {
/*     */       try {
/* 408 */         getSPSServer().sendAutomaticErrorNotification(this.errorCode, this.data);
/* 409 */       } catch (Exception e) {
/* 410 */         SPSServerAccess.log.error(e, e);
/*     */       } 
/* 412 */       return null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 416 */       return super.toString() + "[sessionID:" + this.sessionID + "]";
/*     */     }
/*     */   }
/*     */   
/* 420 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*     */         public Object createObject(Object identifier) {
/* 422 */           return new SPSServerAccess((String)identifier);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private String sessionID;
/*     */   
/*     */   private BlobDataCacheAdapter dataReadThroughCache;
/*     */   private DwnldServerAccess serverAccess;
/*     */   
/*     */   private SPSServerAccess(String sessionID) {
/* 433 */     this.sessionID = sessionID;
/*     */     
/* 435 */     this.serverAccess = new DwnldServerAccess(new DwnldServerAccess.Callback()
/*     */         {
/*     */           public CookieWrapper getAkamaiCookie() {
/*     */             try {
/* 439 */               SPSServerAccess.MethodCallGetAkamaiCookie task = new SPSServerAccess.MethodCallGetAkamaiCookie();
/* 440 */               Object retValue = ServerTaskExecution.getInstance().execute(task, null);
/* 441 */               return (CookieWrapper)SPSServerAccess.returnHook(retValue);
/* 442 */             } catch (Exception e) {
/* 443 */               SPSServerAccess.log.error("unable to download cookie - exception: " + e);
/* 444 */               throw Util.toRuntimeException(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 450 */     ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/* 451 */     ClientSettings settings = context.getClientSettings();
/* 452 */     String cacheDir = settings.getSPSCache();
/*     */     
/* 454 */     FileCache fileCache = new FileCache(new File(cacheDir));
/* 455 */     this.dataReadThroughCache = new BlobDataCacheAdapter((Cache)fileCache, new BlobDataCacheAdapter.Retrieval() {
/*     */           public Object get(Object dataUnit, final ObservableInputStream.Observer observer, AttributeValueMap avMap) throws Exception {
/* 457 */             SPSServerAccess.log.debug("downloading calibration data ...");
/* 458 */             byte[] data = null;
/* 459 */             ProgrammingDataUnit unit = (ProgrammingDataUnit)dataUnit;
/* 460 */             DownloadServer server = unit.getDownloadSite();
/*     */             
/* 462 */             if (server != null) {
/* 463 */               SPSServerAccess.log.debug("...requesting calibration data from " + server);
/*     */               
/*     */               try {
/* 466 */                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 467 */                 final Counter counter = new Counter();
/* 468 */                 OutputStream os = new FilterOutputStream(baos)
/*     */                   {
/* 470 */                     private BigInteger lastDiv = null;
/*     */                     
/*     */                     public void write(int b) throws IOException {
/* 473 */                       this.out.write(b);
/* 474 */                       onWrite(1);
/*     */                     }
/*     */                     
/*     */                     public void write(byte[] b, int off, int len) throws IOException {
/* 478 */                       this.out.write(b, off, len);
/* 479 */                       onWrite(len);
/*     */                     }
/*     */                     
/*     */                     private void onWrite(int len) {
/* 483 */                       counter.inc(len);
/* 484 */                       BigInteger newDiv = counter.getCount().divide(BigInteger.valueOf(1000L));
/* 485 */                       if (this.lastDiv == null || newDiv.compareTo(this.lastDiv) != 0) {
/* 486 */                         observer.onRead(counter.getCount().longValue());
/*     */                       }
/* 488 */                       this.lastDiv = newDiv;
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public void close() throws IOException {
/* 493 */                       observer.onEOF();
/*     */                     }
/*     */                   };
/*     */                 
/*     */                 try {
/* 498 */                   SPSServerAccess.this.serverAccess.getData(server, unit.getDownloadID(), os);
/*     */                 } finally {
/* 500 */                   os.close();
/*     */                 } 
/* 502 */                 data = baos.toByteArray();
/* 503 */                 SPSServerAccess.verifyChecksum(unit, data);
/*     */               }
/* 505 */               catch (Exception e) {
/* 506 */                 data = null;
/* 507 */                 SPSServerAccess.log.warn("unable to load data from download site, using application server - exception: " + e);
/*     */               } 
/*     */             } 
/*     */             
/* 511 */             if (data == null) {
/* 512 */               SPSServerAccess.log.debug("...requesting calibration data from application server ");
/* 513 */               SPSServerAccess.MethodCallGetData task = new SPSServerAccess.MethodCallGetData(SPSServerAccess.this.sessionID, (ProgrammingDataUnit)dataUnit, avMap);
/* 514 */               Object retValue = ServerTaskExecution.getInstance().execute(task, observer);
/* 515 */               data = (byte[])SPSServerAccess.returnHook(retValue);
/* 516 */               SPSServerAccess.verifyChecksum(unit, data);
/*     */             } 
/*     */             
/* 519 */             SPSServerAccess.log.debug("...done");
/* 520 */             return data;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyChecksum(ProgrammingDataUnit unit, byte[] data) throws InvalidChecksumException {
/* 530 */     byte[] checksum = unit.getCheckSum();
/* 531 */     if (checksum != null && checksum.length > 0) {
/*     */       MessageDigest md5;
/*     */       try {
/* 534 */         md5 = MessageDigest.getInstance("MD5");
/* 535 */       } catch (NoSuchAlgorithmException e) {
/* 536 */         throw new RuntimeException(e);
/*     */       } 
/* 538 */       byte[] calculatedChecksum = md5.digest(data);
/* 539 */       if (!Arrays.equals(checksum, calculatedChecksum)) {
/* 540 */         log.error("checksum verification for " + String.valueOf(unit) + " failed *****");
/* 541 */         log.error("....given checksum: " + StringUtilities.bytesToHex(checksum));
/* 542 */         log.error("....calculated checksum: " + StringUtilities.bytesToHex(calculatedChecksum));
/* 543 */         log.error("***** throwing InvalidChecksumException");
/* 544 */         throw new InvalidChecksumException();
/*     */       } 
/* 546 */       log.debug("successfully verified checksum for: " + String.valueOf(unit));
/*     */     }
/* 548 */     else if (checksum == null) {
/* 549 */       log.debug("skipped checksum verification (no checksum provided) for:" + String.valueOf(unit));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized SPSServerAccess getInstance(String sessionID) {
/* 554 */     return (SPSServerAccess)multitonSupport.getInstance(sessionID);
/*     */   }
/*     */   private AttributeValueMap getDeltaChain(AttributeValueMap avMap) {
/*     */     AttributeValueMapImpl attributeValueMapImpl;
/* 558 */     if (!(avMap instanceof AttributeValueMapImpl)) {
/* 559 */       attributeValueMapImpl = new AttributeValueMapImpl(avMap);
/*     */     }
/* 561 */     return (AttributeValueMap)new AttributeValueMapDeltaCommulatingWrapper((AttributeValueMap)attributeValueMapImpl);
/*     */   }
/*     */   
/*     */   private void update(AttributeValueMap originalMap, AttributeValueMap deltaChain) {
/* 565 */     AttributeValueMapDeltaCommulatingWrapper deltaWrapper = (AttributeValueMapDeltaCommulatingWrapper)deltaChain;
/* 566 */     deltaWrapper.synchronize(originalMap);
/*     */   }
/*     */   
/*     */   public Boolean execute(AttributeValueMap avMap) throws RequestException, Exception {
/* 570 */     Pair pair = null;
/* 571 */     if (System.getProperty("sps-client-mode") != null) {
/* 572 */       AttributeValueMapRI serverData = null;
/*     */       try {
/* 574 */         String sessionID = (String)AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID);
/* 575 */         Locale locale = (Locale)AVUtil.accessValue(avMap, CommonAttribute.LOCALE);
/* 576 */         if (locale == null) {
/* 577 */           avMap.set(CommonAttribute.LOCALE, (Value)new ValueAdapter(Locale.US));
/*     */         }
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
/* 589 */         if (this.sessionID == null || ClientContextProvider.getInstance().getContext(sessionID) == null || ClientContextProvider.getInstance().getContext(sessionID).getObject("avmap") == null) {
/* 590 */           String cfg = System.getProperty("user.dir") + "\\build\\env\\" + System.getProperty("user.name") + "\\overwrite\\";
/* 591 */           FrameServiceProvider.create((ResourceLoading)new DirectoryResourceLoading(new File(cfg)));
/* 592 */           FrameService fservice = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 593 */           ClientContext cc = ClientContextProvider.getInstance().getContext(sessionID, true);
/* 594 */           cc.registerDispatchable((Dispatchable)ResourceController.getInstance(cc));
/* 595 */           fservice.setLocale(Locale.US, sessionID);
/* 596 */           serverData = (AttributeValueMapRI)avMap;
/* 597 */           cc.storeObject("avmap", serverData);
/* 598 */           this.sessionID = sessionID;
/* 599 */           ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/* 600 */           String cacheDir = context.getClientSettings().getSPSCache();
/* 601 */           FileCache fileCache = new FileCache(new File(cacheDir));
/* 602 */           this.dataReadThroughCache = new BlobDataCacheAdapter((Cache)fileCache, new BlobDataCacheAdapter.Retrieval() {
/*     */                 public Object get(Object identifier, ObservableInputStream.Observer observer, AttributeValueMap avMap) throws Exception {
/* 604 */                   String sessionID = (String)AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID);
/* 605 */                   return SPSServer.getInstance(sessionID).getData((ProgrammingDataUnit)identifier, avMap);
/*     */                 }
/*     */               });
/*     */         } else {
/* 609 */           ClientContextProvider.getInstance().getContext(sessionID);
/* 610 */           serverData = (AttributeValueMapRI)avMap;
/*     */         } 
/*     */         
/* 613 */         Object result = SPSServer.getInstance(sessionID).execute((AttributeValueMap)serverData);
/* 614 */         PairImpl pairImpl = new PairImpl(result, serverData);
/* 615 */       } catch (Exception e) {
/* 616 */         PairImpl pairImpl = new PairImpl(e, serverData);
/*     */       } 
/*     */     } else {
/* 619 */       MethodCallExecute task = new MethodCallExecute(this.sessionID, getDeltaChain(avMap));
/* 620 */       pair = (Pair)ServerTaskExecution.getInstance().execute(task);
/* 621 */       update(avMap, (AttributeValueMap)pair.getSecond());
/*     */     } 
/* 623 */     return (Boolean)returnHook(pair.getFirst());
/*     */   }
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit, ObservableInputStream.Observer observer, AttributeValueMap avMap) throws Exception {
/*     */     AttributeValueMapImpl attributeValueMapImpl;
/* 627 */     if (!(avMap instanceof AttributeValueMapImpl)) {
/* 628 */       attributeValueMapImpl = new AttributeValueMapImpl(avMap);
/*     */     }
/* 630 */     Object result = this.dataReadThroughCache.get(dataUnit, observer, (AttributeValueMap)attributeValueMapImpl);
/* 631 */     observer.onEOF();
/* 632 */     return (byte[])result;
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) throws Exception {
/* 636 */     if (System.getProperty("sps-client-mode") != null)
/*     */     {
/* 638 */       return SPSServer.getInstance(this.sessionID).getBulletin(locale, bulletin);
/*     */     }
/* 640 */     MethodCallGetBulletin task = new MethodCallGetBulletin(this.sessionID, locale, bulletin);
/* 641 */     Object retValue = ServerTaskExecution.getInstance().execute(task);
/* 642 */     return (String)returnHook(retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHTML(String locale, String id) throws Exception {
/* 647 */     if (System.getProperty("sps-client-mode") != null)
/*     */     {
/* 649 */       return SPSServer.getInstance(this.sessionID).getHTML(locale, id);
/*     */     }
/* 651 */     MethodCallGetHTML task = new MethodCallGetHTML(this.sessionID, locale, id);
/* 652 */     Object retValue = ServerTaskExecution.getInstance().execute(task);
/* 653 */     return (String)returnHook(retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getImage(String id) throws Exception {
/* 658 */     if (System.getProperty("sps-client-mode") != null)
/*     */     {
/* 660 */       return SPSServer.getInstance(this.sessionID).getImage(id);
/*     */     }
/* 662 */     MethodCallGetImage task = new MethodCallGetImage(this.sessionID, id);
/* 663 */     Object retValue = ServerTaskExecution.getInstance().execute(task);
/*     */     
/* 665 */     return (byte[])returnHook(retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getNavigationTableData() throws Exception {
/* 670 */     if (System.getProperty("sps-client-mode") != null)
/*     */     {
/* 672 */       return new ArrayList();
/*     */     }
/* 674 */     MethodCallGetNavTableData task = new MethodCallGetNavTableData(this.sessionID);
/* 675 */     Object retValue = ServerTaskExecution.getInstance().execute(task);
/* 676 */     return (Collection)returnHook(retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object returnHook(Object retValue) throws Exception {
/* 681 */     if (retValue instanceof Exception) {
/* 682 */       Exception e = (Exception)retValue;
/* 683 */       log.debug("rethrowing exception " + String.valueOf(e));
/* 684 */       throw e;
/*     */     } 
/* 686 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHWKReplacement() throws UnprivilegedUserException {
/* 691 */     MC_GetHWKReplacement task = new MC_GetHWKReplacement(this.sessionID);
/* 692 */     Object retValue = ServerTaskExecution.getInstance().execute(task);
/* 693 */     if (retValue instanceof UnprivilegedUserException)
/* 694 */       throw (UnprivilegedUserException)retValue; 
/* 695 */     if (retValue instanceof RuntimeException)
/* 696 */       throw (RuntimeException)retValue; 
/* 697 */     if (retValue instanceof Exception) {
/* 698 */       throw new RuntimeException((Exception)retValue);
/*     */     }
/* 700 */     return (String)retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendEmail(String sender, Collection recipients, String subject, String text, Collection attachments) throws Exception {
/* 705 */     MC_SendMail sendMail = new MC_SendMail(this.sessionID, sender, recipients, subject, text, attachments);
/* 706 */     Object ret = ServerTaskExecution.getInstance().execute(sendMail);
/* 707 */     if (ret instanceof Exception) {
/* 708 */       throw (Exception)ret;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getHardwareID() throws Exception {
/* 713 */     MC_HardwareID hwid = new MC_HardwareID(this.sessionID);
/* 714 */     Object ret = ServerTaskExecution.getInstance().execute(hwid);
/* 715 */     if (ret instanceof Exception) {
/* 716 */       throw (Exception)ret;
/*     */     }
/* 718 */     return (String)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage(String errorCode, AttributeValueMap data) {
/* 723 */     MC_GetMessage mc = new MC_GetMessage(this.sessionID, errorCode, data);
/* 724 */     Object ret = ServerTaskExecution.getInstance().execute(mc);
/* 725 */     if (ret instanceof Exception) {
/* 726 */       throw Util.toRuntimeException((Exception)ret);
/*     */     }
/* 728 */     return (String)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendErrorNotificationMail(String errorCode, AttributeValueMap data) {
/* 733 */     MC_SendENMail mc = new MC_SendENMail(this.sessionID, errorCode, data);
/* 734 */     ServerTaskExecution.getInstance().execute(mc);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\SPSServerAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */