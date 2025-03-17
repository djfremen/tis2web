/*     */ package com.eoos.gm.tis2web.sps.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.ReprogramProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ReprogramProgressDisplayRequestImpl
/*     */   implements ReprogramProgressDisplayRequest {
/*  17 */   public static final Status FINISHED = Status.getInstance("finished");
/*     */   
/*  19 */   public static final Status RUNNING = Status.getInstance("running");
/*     */   
/*  21 */   public static final Status INIT = Status.getInstance("init");
/*     */   
/*  23 */   public static final Status ERROR = Status.getInstance("error");
/*     */   
/*  25 */   private Collection observers = Collections.synchronizedCollection(new HashSet());
/*     */   
/*  27 */   private RequestGroup requestGroup = null;
/*     */   
/*     */   private boolean autoSubmit = false;
/*     */   
/*  31 */   private Status status = INIT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(long totalCount) {
/*  38 */     this.status = RUNNING;
/*  39 */     synchronized (this.observers) {
/*  40 */       for (Iterator<ReprogramProgressDisplayRequest.Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/*  41 */         ReprogramProgressDisplayRequest.Observer observer = iter.next();
/*     */         try {
/*  43 */           observer.onStart(totalCount);
/*  44 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void progress(long actualCount) {
/*  51 */     synchronized (this.observers) {
/*  52 */       for (Iterator<ReprogramProgressDisplayRequest.Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/*  53 */         ReprogramProgressDisplayRequest.Observer observer = iter.next();
/*     */         try {
/*  55 */           observer.onProgress(actualCount);
/*  56 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStatusChange(String labelKey) {
/*  63 */     synchronized (this.observers) {
/*  64 */       for (Iterator<ReprogramProgressDisplayRequest.Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/*  65 */         ReprogramProgressDisplayRequest.Observer observer = iter.next();
/*     */         try {
/*  67 */           observer.onStatusChange(labelKey);
/*  68 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  75 */     this.status = FINISHED;
/*  76 */     synchronized (this.observers) {
/*  77 */       for (Iterator<ReprogramProgressDisplayRequest.Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/*  78 */         ReprogramProgressDisplayRequest.Observer observer = iter.next();
/*     */         try {
/*  80 */           observer.onFinished();
/*  81 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addObserver(final ReprogramProgressDisplayRequest.Observer observer) {
/*  88 */     this.observers.add(observer);
/*  89 */     if (this.status == FINISHED) {
/*  90 */       (new Thread() {
/*     */           public void run() {
/*  92 */             observer.onFinished();
/*     */           }
/*     */         }).start();
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeObserver(ReprogramProgressDisplayRequest.Observer observer) {
/*  99 */     this.observers.remove(observer);
/*     */   }
/*     */   
/*     */   public Value getConfirmationValue() {
/* 103 */     return CommonValue.OK;
/*     */   }
/*     */   
/*     */   public void setRequestGroup(RequestGroup requestGroup) {
/* 107 */     this.requestGroup = requestGroup;
/*     */   }
/*     */   
/*     */   public RequestGroup getRequestGroup() {
/* 111 */     return this.requestGroup;
/*     */   }
/*     */   
/*     */   public boolean autoSubmit() {
/* 115 */     return this.autoSubmit;
/*     */   }
/*     */   
/*     */   public void setAutoSubmit(boolean autoSubmit) {
/* 119 */     this.autoSubmit = autoSubmit;
/*     */   }
/*     */   
/*     */   public Attribute getAttribute() {
/* 123 */     return AttributeImpl.getInstance("reprogram.progress.display.confirmation");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\ReprogramProgressDisplayRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */