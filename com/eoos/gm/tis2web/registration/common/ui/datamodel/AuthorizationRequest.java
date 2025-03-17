/*     */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Comparator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public interface AuthorizationRequest
/*     */ {
/*     */   public static abstract class ComparatorBase
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object arg0, Object arg1) {
/*  15 */       AuthorizationRequest request1 = (AuthorizationRequest)arg0;
/*  16 */       AuthorizationRequest request2 = (AuthorizationRequest)arg1;
/*  17 */       return compare(request1, request2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract int compare(AuthorizationRequest param1AuthorizationRequest1, AuthorizationRequest param1AuthorizationRequest2);
/*     */   }
/*     */   
/*  24 */   public static final Comparator COMPARATOR_TYPE = new ComparatorBase()
/*     */     {
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/*  27 */         RequestType type1 = ar1.getType();
/*  28 */         RequestType type2 = ar2.getType();
/*     */         
/*  30 */         return type1.compareTo(type2);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  35 */   public static final Comparator COMPARATOR_STATUS = new ComparatorBase()
/*     */     {
/*     */       private int getInt(AuthorizationRequest.Status status) {
/*  38 */         if (status == AuthorizationRequest.STATUS_PENDING) {
/*  39 */           return 1;
/*     */         }
/*  41 */         return 2;
/*     */       }
/*     */ 
/*     */       
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/*  46 */         return getInt(ar1.getStatus()) - getInt(ar2.getStatus());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  51 */   public static final Comparator COMPARATOR_REQUEST_ID = new ComparatorBase()
/*     */     {
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/*  54 */         return Util.compare(ar1.getRequestID(), ar2.getRequestID());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  59 */   public static final Comparator COMPARATOR_DEALERSHIP_ID = new ComparatorBase() {
/*  60 */       private final Logger log = Logger.getLogger(getClass());
/*     */       
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/*  63 */         String dsID1 = null;
/*     */         try {
/*  65 */           dsID1 = ar1.getDealershipInfo().getDealershipID();
/*  66 */         } catch (Exception e) {
/*  67 */           this.log.warn("unable to retrieve dealership id, using null - exception: " + e, e);
/*     */         } 
/*  69 */         String dsID2 = null;
/*     */         try {
/*  71 */           dsID2 = ar2.getDealershipInfo().getDealershipID();
/*  72 */         } catch (Exception e) {
/*  73 */           this.log.warn("unable to retrieve dealership id, using null - exception: " + e, e);
/*     */         } 
/*  75 */         return Util.compare(dsID1, dsID2);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  80 */   public static final Comparator COMPARATOR_DEALERSHIP = new ComparatorBase() {
/*  81 */       private final Logger log = Logger.getLogger(getClass());
/*     */       
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/*  84 */         String ds1 = null;
/*     */         try {
/*  86 */           ds1 = ar1.getDealershipInfo().getDealership();
/*  87 */         } catch (Exception e) {
/*  88 */           this.log.warn("unable to retrieve dealership, using null - exception: " + e, e);
/*     */         } 
/*     */         
/*  91 */         String ds2 = null;
/*     */         try {
/*  93 */           ds2 = ar2.getDealershipInfo().getDealership();
/*  94 */         } catch (Exception e) {
/*  95 */           this.log.warn("unable to retrieve dealership, using null - exception: " + e, e);
/*     */         } 
/*     */         
/*  98 */         return Util.compare(ds1, ds2);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 103 */   public static final Comparator COMPARATOR_DATE = new ComparatorBase()
/*     */     {
/*     */       protected int compare(AuthorizationRequest ar1, AuthorizationRequest ar2) {
/* 106 */         return Util.compare(ar1.getRequestDate(), ar2.getRequestDate());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static final class Status
/*     */   {
/*     */     private Status() {}
/*     */   }
/*     */   
/* 116 */   public static final Status STATUS_PENDING = new Status();
/*     */   
/* 118 */   public static final Status STATUS_AUTHORIZED = new Status();
/*     */   
/*     */   Status getStatus();
/*     */   
/*     */   String getRequestID();
/*     */   
/*     */   long getRequestDate();
/*     */   
/*     */   String getSubscriberID();
/*     */   
/*     */   Integer getSessions();
/*     */   
/*     */   SoftwareKey getSoftwareKey();
/*     */   
/*     */   DealershipInfo getDealershipInfo();
/*     */   
/*     */   Subscription getSubscription();
/*     */   
/*     */   Registration getRegistration();
/*     */   
/*     */   RequestType getType();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\AuthorizationRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */