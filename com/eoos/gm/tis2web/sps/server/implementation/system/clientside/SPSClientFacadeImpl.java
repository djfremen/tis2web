/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataDownloadRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.LocalAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.SPSClientFacade;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.navtables.NavTableValidationMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSClientFacadeImpl
/*     */   implements SPSClientFacade
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(SPSClientFacadeImpl.class);
/*     */   
/*     */   private static class MyAttribute implements Attribute, LocalAttribute {
/*     */     private MyAttribute() {}
/*     */   }
/*     */   
/*  50 */   private static final Attribute CONTEXT = new MyAttribute();
/*     */ 
/*     */   
/*     */   public SPSClientFacadeImpl() {
/*  54 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientFacadeContext getContext(AttributeValueMap avMap) {
/*  59 */     ClientFacadeContext context = null;
/*     */     
/*  61 */     Value value = avMap.getValue(CONTEXT);
/*  62 */     if (value != null) {
/*  63 */       context = (ClientFacadeContext)((ValueAdapter)value).getAdaptee();
/*     */     }
/*  65 */     return context;
/*     */   }
/*     */   
/*     */   private static String getSessionID() {
/*  69 */     return ClientAppContextProvider.getClientAppContext().getSessionID();
/*     */   }
/*     */   
/*     */   private ClientFacadeContext createContext(AttributeValueMap avMap) {
/*  73 */     ClientFacadeContext context = new ClientFacadeContext();
/*  74 */     avMap.set(CONTEXT, (Value)new ValueAdapter(context));
/*  75 */     return context;
/*     */   }
/*     */   
/*     */   public Boolean execute(AttributeValueMap avMap) throws RequestException, Exception {
/*  79 */     log.debug("received execution request (client call), dispatching to server");
/*  80 */     log.debug("....AttributeValueMap instance is " + String.valueOf(avMap));
/*     */     try {
/*  82 */       return SPSServerAccess.getInstance(getSessionID()).execute(avMap);
/*  83 */     } catch (RequestException e) {
/*  84 */       if (e.getRequest() instanceof ProgrammingDataDownloadRequest) {
/*  85 */         log.debug("received ProgrammingDataDownloadRequest (server answer)");
/*  86 */         DownloadProgressDisplayRequest dpdr = handleDownload((ProgrammingDataDownloadRequest)e.getRequest(), avMap);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  91 */         throw new RequestException(dpdr);
/*  92 */       }  if (e.getRequest() instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingDataDownloadContinuationRequest) {
/*  93 */         log.debug("received ProgrammingDataDownloadContinuationRequest (server answer)");
/*  94 */         DownloadThread dt = getDownloadThread(avMap);
/*  95 */         if (dt != null) {
/*  96 */           if (!dt.isFinished()) {
/*  97 */             DownloadProgressDisplayRequest request = createDPDR(dt);
/*  98 */             log.debug("throwing DownloadProgressDisplayRequest exception");
/*  99 */             throw new RequestException(request);
/*     */           } 
/* 101 */           throw e;
/*     */         } 
/*     */         
/* 104 */         throw new IllegalStateException("received continuation request, but download has not been started yet");
/*     */       } 
/*     */       
/* 107 */       log.debug("rethrowing  :" + String.valueOf(e));
/* 108 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private DownloadThread getDownloadThread(AttributeValueMap avMap) {
/* 114 */     ClientFacadeContext context = getContext(avMap);
/* 115 */     DownloadThread dt = null;
/* 116 */     if (context != null) {
/* 117 */       dt = context.getDownloadThread();
/*     */     }
/* 119 */     return dt;
/*     */   }
/*     */ 
/*     */   
/*     */   public DownloadProgressDisplayRequest handleDownload(final ProgrammingDataDownloadRequest pddr, final AttributeValueMap avMap) throws Exception {
/* 124 */     DownloadThread dt = getDownloadThread(avMap);
/* 125 */     if (dt != null) {
/*     */       
/* 127 */       if (dt.getStatus() == DownloadThread.RUNNING)
/* 128 */         return createDPDR(dt); 
/* 129 */       if (dt.getStatus() == DownloadThread.ERROR)
/* 130 */         throw dt.getException(); 
/* 131 */       if (dt.getStatus() == DownloadThread.FINISHED)
/*     */       {
/* 133 */         return null;
/*     */       }
/* 135 */       throw new IllegalStateException("unknown/unhandled status of download thread:" + String.valueOf(dt.getStatus()));
/*     */     } 
/*     */ 
/*     */     
/* 139 */     dt = new DownloadThread(getSessionID(), pddr.getCalibrationFiles(), avMap) {
/*     */         protected void onFinished() {
/* 141 */           SPSClientFacadeImpl.log.debug("download thread is finished");
/* 142 */           if (avMap.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) != null && 
/* 143 */             avMap.getValue(pddr.getAttribute()) == null) {
/* 144 */             SPSClientFacadeImpl.log.debug("set corresponding attribute in AttributeValueMap " + String.valueOf(avMap));
/* 145 */             avMap.set(pddr.getAttribute(), pddr.getConfirmationValue());
/*     */           } 
/*     */           
/* 148 */           super.onFinished();
/*     */         }
/*     */       };
/* 151 */     avMap.set(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START, CommonValue.OK);
/* 152 */     dt.start();
/* 153 */     log.debug("started download thread, set corresponding attribute in AttributeValueMap " + String.valueOf(avMap));
/*     */     
/* 155 */     ClientFacadeContext context = getContext(avMap);
/* 156 */     if (context == null) {
/* 157 */       context = createContext(avMap);
/*     */     }
/* 159 */     context.setDownloadThread(dt);
/* 160 */     return createDPDR(dt);
/*     */   }
/*     */ 
/*     */   
/*     */   private DownloadProgressDisplayRequest createDPDR(final DownloadThread dt) {
/* 165 */     DownloadProgressDisplayRequest result = new DownloadProgressDisplayRequest()
/*     */       {
/* 167 */         private RequestGroup requestGroup = null;
/*     */         
/*     */         private boolean autoSubmit = false;
/*     */         
/*     */         public void addObserver(DownloadProgressDisplayRequest.Observer observer) {
/* 172 */           dt.addObserver(observer);
/*     */         }
/*     */         
/*     */         public void removeObserver(DownloadProgressDisplayRequest.Observer observer) {
/* 176 */           dt.removeObserver(observer);
/*     */         }
/*     */         
/*     */         public Value getConfirmationValue() {
/* 180 */           return CommonValue.OK;
/*     */         }
/*     */         
/*     */         public RequestGroup getRequestGroup() {
/* 184 */           return this.requestGroup;
/*     */         }
/*     */         
/*     */         public Attribute getAttribute() {
/* 188 */           return AttributeImpl.getInstance("download.progress.display.confirmation");
/*     */         }
/*     */         
/*     */         public void setRequestGroup(RequestGroup requestGroup) {
/* 192 */           this.requestGroup = requestGroup;
/*     */         }
/*     */         
/*     */         public boolean autoSubmit() {
/* 196 */           return this.autoSubmit;
/*     */         }
/*     */         
/*     */         public void setAutoSubmit(boolean autoSubmit) {
/* 200 */           this.autoSubmit = autoSubmit;
/*     */         }
/*     */         
/*     */         public void cancelDownload() {
/* 204 */           dt.stopDownload();
/*     */         }
/*     */       };
/* 207 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getNavigationTables() {
/* 212 */     throw new UnsupportedOperationException("take the files from the navtable directory");
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
/*     */   public byte[] getSPSBlob(ProgrammingDataUnit dataUnit, AttributeValueMap avMap) throws Exception {
/* 224 */     ClientFacadeContext context = getContext(avMap);
/* 225 */     DownloadThread dt = null;
/* 226 */     if (context == null || (dt = context.getDownloadThread()) == null) {
/* 227 */       log.warn("received call for blob, but download thread has not been created yet");
/* 228 */       log.warn("creating and starting download thread");
/*     */       
/* 230 */       List<ProgrammingDataUnit> data = new LinkedList();
/* 231 */       data.add(dataUnit);
/* 232 */       dt = new DownloadThread(getSessionID(), data, avMap);
/* 233 */       dt.start();
/*     */     } 
/*     */     
/* 236 */     while (!dt.isFinished()) {
/* 237 */       dt.join(1000L);
/*     */     }
/*     */     
/* 240 */     return dt.getData(dataUnit);
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) throws Exception {
/* 244 */     return SPSServerAccess.getInstance(getSessionID()).getBulletin(locale, bulletin);
/*     */   }
/*     */   
/*     */   public String getHTML(String locale, String id) throws Exception {
/* 248 */     return SPSServerAccess.getInstance(getSessionID()).getHTML(locale, id);
/*     */   }
/*     */   
/*     */   public byte[] getImage(String id) throws Exception {
/* 252 */     return SPSServerAccess.getInstance(getSessionID()).getImage(id);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/*     */     try {
/* 258 */       log.debug("validating / tranfering navigation tables");
/*     */       
/* 260 */       boolean retransfer = false;
/* 261 */       ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/* 262 */       File homeDirectory = new File(context.getHomeDir());
/* 263 */       File destDir = new File(homeDirectory, "navtables");
/* 264 */       if (!destDir.exists()) {
/* 265 */         log.debug("creating directory for navigation table files");
/* 266 */         retransfer = true;
/* 267 */         destDir.mkdir();
/*     */       } 
/* 269 */       if (!retransfer) {
/* 270 */         log.debug("validating navigation tables");
/*     */         
/*     */         try {
/* 273 */           MessageDigest md5 = MessageDigest.getInstance("MD5");
/*     */           
/* 275 */           NavTableValidationMap validationMap = NavTableValidationMap.createInstance(context.getNavTableValidationMap());
/* 276 */           for (Iterator<Map.Entry> iter = validationMap.entrySet().iterator(); iter.hasNext() && !retransfer; ) {
/* 277 */             Map.Entry entry = iter.next();
/* 278 */             String filename = entry.getKey().toString();
/* 279 */             byte[] hash = (byte[])entry.getValue();
/*     */             
/* 281 */             File file = new File(destDir, filename);
/* 282 */             if (!file.exists()) {
/* 283 */               log.debug("missing navtable file: " + filename);
/* 284 */               retransfer = true; continue;
/*     */             } 
/* 286 */             FileInputStream fis = new FileInputStream(file);
/* 287 */             md5.reset();
/* 288 */             byte[] hash2 = md5.digest(StreamUtil.readFully(fis));
/* 289 */             retransfer = !MessageDigest.isEqual(hash, hash2);
/* 290 */             log.debug("validation of navtable file: " + filename + " " + (retransfer ? "FAILED" : "was successful"));
/*     */           }
/*     */         
/* 293 */         } catch (Exception e) {
/* 294 */           log.error("unable to validate navigation tables, indicating retranfer - exception:" + e, e);
/*     */         } 
/*     */       } 
/*     */       
/* 298 */       if (retransfer) {
/* 299 */         log.debug("requesting navigation tables from server");
/* 300 */         MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 301 */         SPSServerAccess server = SPSServerAccess.getInstance(getSessionID());
/* 302 */         Collection data = server.getNavigationTableData();
/* 303 */         log.debug("received navigation table data collection: " + String.valueOf(data));
/* 304 */         for (Iterator<NavigationTableData> dataIter = data.iterator(); dataIter.hasNext(); ) {
/* 305 */           NavigationTableData ntd = dataIter.next();
/*     */           try {
/* 307 */             if (Arrays.equals(ntd.getChecksum(), md5.digest(ntd.getData()))) {
/* 308 */               File file = new File(destDir, ntd.getIdentifier());
/* 309 */               FileOutputStream fos = new FileOutputStream(file);
/* 310 */               fos.write(ntd.getData());
/* 311 */               fos.close();
/* 312 */               log.debug("wrote data file :" + file.getName()); continue;
/*     */             } 
/* 314 */             log.error("***** checksum validation failed for navigation table data with identifier: " + String.valueOf(ntd.getIdentifier()));
/* 315 */             log.error("...calculated checksum: " + StringUtilities.bytesToHex(md5.digest(ntd.getData())));
/* 316 */             log.error("...indicated checksum: " + StringUtilities.bytesToHex(ntd.getChecksum()));
/* 317 */             log.error("***** skipping data");
/*     */           }
/* 319 */           catch (Exception e) {
/* 320 */             log.error("**** unable to create file for " + String.valueOf(ntd.getIdentifier()));
/* 321 */             log.error("***** skipping data");
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 326 */         log.debug("all navtable files are valid, skipped retransmission");
/*     */       } 
/* 328 */     } catch (Exception e) {
/* 329 */       log.debug("unable to init - exception:" + e);
/* 330 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHWKReplacement() throws UnprivilegedUserException {
/* 336 */     return SPSServerAccess.getInstance(getSessionID()).getHWKReplacement();
/*     */   }
/*     */   
/*     */   public void sendMail(String sender, Collection recipients, String subject, String text, Collection attachments) throws Exception {
/* 340 */     SPSServerAccess.getInstance(getSessionID()).sendEmail(sender, recipients, subject, text, attachments);
/*     */   }
/*     */   
/*     */   public String getHardwareID() throws Exception {
/* 344 */     return SPSServerAccess.getInstance(getSessionID()).getHardwareID();
/*     */   }
/*     */   
/*     */   public String getMessage(String errorCode, AttributeValueMap data) {
/* 348 */     return SPSServerAccess.getInstance(getSessionID()).getMessage(errorCode, data);
/*     */   }
/*     */   
/*     */   public void sendErrorNotificationEmail(String errorCode, AttributeValueMap data) {
/* 352 */     SPSServerAccess.getInstance(getSessionID()).sendErrorNotificationMail(errorCode, data);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\SPSClientFacadeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */