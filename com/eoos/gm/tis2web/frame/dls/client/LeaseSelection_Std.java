/*    */ package com.eoos.gm.tis2web.frame.dls.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LeaseSelection_Std
/*    */   implements DLSService.LeaseSelection
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(LeaseSelection_Std.class);
/*    */   
/* 19 */   private static LeaseSelection_Std instance = null;
/*    */   
/* 21 */   private URL tecURL = null;
/*    */   
/*    */   private LeaseSelection_Std() {
/* 24 */     String url = System.getProperty("task.execution.url");
/* 25 */     if (!Util.isNullOrEmpty(url)) {
/*    */       try {
/* 27 */         this.tecURL = new URL(url);
/* 28 */       } catch (MalformedURLException e) {
/* 29 */         throw new IllegalStateException();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized LeaseSelection_Std getInstance() {
/* 44 */     if (instance == null) {
/* 45 */       instance = new LeaseSelection_Std();
/*    */     }
/* 47 */     return instance;
/*    */   }
/*    */   
/*    */   public Lease selectLease(Collection entries) {
/* 51 */     log.debug("selecting lease ...");
/* 52 */     Lease ret = null;
/* 53 */     for (Iterator<Map.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 54 */       Map.Entry entry = iter.next();
/* 55 */       URL url = (URL)entry.getKey();
/* 56 */       Lease lease = (Lease)entry.getValue();
/* 57 */       log.debug("...checking lease " + String.valueOf(lease) + " for URL: " + String.valueOf(url));
/*    */       
/* 59 */       if (this.tecURL != null && !this.tecURL.equals(url)) {
/* 60 */         log.debug("...skipping lease (wrong URL)");
/*    */         continue;
/*    */       } 
/* 63 */       if (ret == null || ((ILeaseInternal)lease).getCreationDate() > ((ILeaseInternal)ret).getCreationDate()) {
/* 64 */         ret = lease;
/* 65 */         log.debug("...replacing selection of newest lease with lease: " + lease);
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\LeaseSelection_Std.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */