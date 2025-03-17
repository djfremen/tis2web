/*     */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.dtc.TroubleCode;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import java.io.File;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
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
/*     */ public class DTCMonitoring
/*     */ {
/*  25 */   private Logger log = Logger.getLogger(DTCMonitoring.class);
/*  26 */   private static DTCMonitoring instance = null;
/*  27 */   private Timer timer = new Timer();
/*  28 */   private TimerTask timerTask = null;
/*     */ 
/*     */   
/*     */   private boolean isMonitoring = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized DTCMonitoring getInstance() {
/*  36 */     if (instance == null) {
/*  37 */       instance = new DTCMonitoring();
/*     */     }
/*  39 */     return instance;
/*     */   }
/*     */   
/*     */   public void startMonitoring() {
/*  43 */     if (this.isMonitoring) {
/*     */       return;
/*     */     }
/*  46 */     this.isMonitoring = true;
/*     */     try {
/*  48 */       long millis = Long.parseLong((String)SDCurrentContext.getInstance().getLocSettings().get("dtcupload.monitoring.timeout")) * 1000L;
/*  49 */       this.timerTask = new TimerTask() {
/*     */           public void run() {
/*  51 */             DTCMonitoring.this.uploadDTCFiles();
/*     */           }
/*     */         };
/*  54 */       this.timer.scheduleAtFixedRate(this.timerTask, 50L, millis);
/*  55 */     } catch (Exception e) {
/*  56 */       this.log.error("Error when start dtc upload monitoring.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pauseMonitoring() {
/*  61 */     if (this.isMonitoring) {
/*  62 */       this.timerTask.cancel();
/*  63 */       this.isMonitoring = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopMonitoring() {
/*  68 */     pauseMonitoring();
/*  69 */     this.timer.cancel();
/*     */   }
/*     */   
/*     */   private synchronized void uploadDTCFiles() {
/*     */     try {
/*  74 */       SWCacheManager cacheMan = new SWCacheManager();
/*  75 */       File[] dtcFiles = cacheMan.getDTCFiles();
/*  76 */       if (dtcFiles == null || dtcFiles.length < 1) {
/*  77 */         pauseMonitoring();
/*     */         
/*     */         return;
/*     */       } 
/*  81 */       Set<TroubleCode> troubleCodes = new HashSet();
/*  82 */       for (int i = 0; i < dtcFiles.length; i++) {
/*  83 */         TroubleCode dtcData = cacheMan.getDTCFile(dtcFiles[i]);
/*  84 */         troubleCodes.add(dtcData);
/*     */       } 
/*  86 */       Command command = new Command(6);
/*  87 */       command.addParameter("troublecode", troubleCodes);
/*     */       
/*  89 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/*  90 */       if (obj != null && obj instanceof Boolean && ((Boolean)obj).booleanValue()) {
/*  91 */         for (int j = 0; j < dtcFiles.length; j++) {
/*  92 */           dtcFiles[j].delete();
/*     */         }
/*  94 */         pauseMonitoring();
/*     */       }
/*  96 */       else if (obj != null && obj instanceof ServerError) {
/*  97 */         this.log.error("Server error when upload the DTCData to server, errNR: " + ((ServerError)obj).getError());
/*     */       } else {
/*  99 */         this.log.error("Incorect object: when upload the DTCData to server.");
/*     */       }
/*     */     
/* 102 */     } catch (Exception e) {
/* 103 */       this.log.error("Exception when upload the DTCData to Server, " + e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\DTCMonitoring.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */