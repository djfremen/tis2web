/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.io.ObservableInputStream;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.NotificationModeDecorator;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DownloadThread
/*     */   extends Thread
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(DownloadThread.class);
/*     */   
/*  23 */   private IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */   
/*     */   private List dataUnits;
/*     */   
/*  27 */   private Map dataUnitToData = new HashMap<Object, Object>();
/*     */   
/*  29 */   public static final Status FINISHED = DownloadProgressDisplayRequest.Observer.FINISHED;
/*     */   
/*  31 */   public static final Status RUNNING = Status.getInstance("running");
/*     */   
/*  33 */   public static final Status INIT = Status.getInstance("init");
/*     */   
/*  35 */   public static final Status ERROR = DownloadProgressDisplayRequest.Observer.ERROR;
/*     */   
/*  37 */   private Status status = INIT;
/*     */   
/*     */   private AttributeValueMap avMap;
/*     */   
/*  41 */   private Exception exception = null;
/*     */   
/*     */   private boolean stopRequest = false;
/*     */   
/*     */   private String sessionID;
/*     */   
/*     */   public DownloadThread(String sessionID, List dataUnits, AttributeValueMap avMap) {
/*  48 */     this.dataUnits = dataUnits;
/*  49 */     this.avMap = avMap;
/*  50 */     this.sessionID = sessionID;
/*     */     
/*  52 */     log.debug("creating " + this);
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/*  56 */     return this.status;
/*     */   }
/*     */   
/*     */   public Exception getException() {
/*  60 */     return this.exception;
/*     */   }
/*     */   
/*     */   private Notification createFinishNotification() {
/*  64 */     Notification notification = new Notification() {
/*     */         public void notify(Object observer) {
/*     */           try {
/*  67 */             ((DownloadProgressDisplayRequest.Observer)observer).onFinished(DownloadThread.this.status);
/*  68 */           } catch (Exception e) {
/*  69 */             DownloadThread.log.error("unable to notify " + String.valueOf(observer) + " - exception:" + e);
/*     */           } 
/*     */         }
/*     */       };
/*  73 */     return notification;
/*     */   }
/*     */   
/*     */   private Notification createReadNotification(final ProgrammingDataUnit dataUnit, final long byteCount) {
/*  77 */     Notification notification = new Notification() {
/*     */         public void notify(Object observer) {
/*     */           try {
/*  80 */             ((DownloadProgressDisplayRequest.Observer)observer).onRead(DownloadThread.this.dataUnits, dataUnit, byteCount);
/*     */           }
/*  82 */           catch (Exception e) {
/*  83 */             DownloadThread.log.error("unable to notify " + String.valueOf(observer) + " - exception:" + e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  88 */     return notification;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addObserver(DownloadProgressDisplayRequest.Observer observer) {
/*  93 */     this.observableSupport.addObserver(observer);
/*  94 */     if (this.status == FINISHED || this.status == ERROR) {
/*  95 */       NotificationModeDecorator notification = new NotificationModeDecorator(createFinishNotification());
/*  96 */       notification.notify(observer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeObserver(DownloadProgressDisplayRequest.Observer observer) {
/* 101 */     this.observableSupport.removeObserver(observer);
/*     */   }
/*     */   
/*     */   private void notifyObservers(ProgrammingDataUnit dataUnit, long byteCount) {
/* 105 */     Notification notification = createReadNotification(dataUnit, byteCount);
/* 106 */     this.observableSupport.notifyObservers(notification);
/*     */   }
/*     */   
/*     */   private void notifyObservers_EOF() {
/* 110 */     Notification notification = createFinishNotification();
/* 111 */     this.observableSupport.notifyObservers(notification);
/*     */   }
/*     */   
/*     */   public void run() {
/* 115 */     this.status = RUNNING;
/* 116 */     log.debug("download thread started");
/*     */     try {
/* 118 */       for (Iterator<ProgrammingDataUnit> iter = this.dataUnits.iterator(); iter.hasNext(); ) {
/*     */         
/* 120 */         if (this.stopRequest) {
/* 121 */           throw new DownloadAbortionException();
/*     */         }
/*     */         
/* 124 */         final ProgrammingDataUnit dataUnit = iter.next();
/* 125 */         log.debug("requesting data for blob: " + String.valueOf(dataUnit));
/* 126 */         byte[] data = null;
/* 127 */         while (data == null) {
/* 128 */           data = SPSServerAccess.getInstance(this.sessionID).getData(dataUnit, new ObservableInputStream.Observer() {
/*     */                 public void onRead(long byteCount) {
/* 130 */                   DownloadThread.this.notifyObservers(dataUnit, byteCount);
/*     */                 }
/*     */                 
/*     */                 public void onEOF() {
/* 134 */                   DownloadThread.this.notifyObservers(dataUnit, dataUnit.getBlobSize().longValue());
/*     */                 }
/*     */               }this.avMap);
/* 137 */           if (data == null) {
/* 138 */             throw new IllegalStateException("blob data must not be null");
/*     */           }
/*     */         } 
/*     */         
/* 142 */         log.debug("received data for blob: " + String.valueOf(dataUnit));
/* 143 */         this.dataUnitToData.put(dataUnit, data);
/*     */       } 
/* 145 */       this.status = FINISHED;
/* 146 */       onFinished();
/* 147 */       notifyObservers_EOF();
/* 148 */     } catch (Exception e) {
/* 149 */       this.exception = e;
/* 150 */       this.status = ERROR;
/* 151 */       log.error("unable to download blobs - exception:" + e, e);
/* 152 */       onError();
/* 153 */       notifyObservers_EOF();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFinished() {}
/*     */   
/*     */   public boolean isFinished() {
/* 161 */     return (this.status == FINISHED || this.status == ERROR);
/*     */   }
/*     */   
/*     */   public void stopDownload() {
/* 165 */     this.stopRequest = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onError() {}
/*     */ 
/*     */   
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit) {
/* 174 */     return (byte[])this.dataUnitToData.get(dataUnit);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\DownloadThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */