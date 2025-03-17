/*    */ package com.eoos.gm.tis2web.sps.server.implementation.calibinfo;
/*    */ 
/*    */ import com.eoos.collection.MinimalMap;
/*    */ import com.eoos.collection.implementation.MinimalMapAdapter;
/*    */ import com.eoos.context.Context;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.WeakHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PartContext
/*    */   extends Context
/*    */ {
/* 18 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 20 */           return new PartContext((Part)identifier);
/*    */         }
/*    */       }) {
/*    */       protected MinimalMap createInstanceStorageMap() {
/* 24 */         return (MinimalMap)new MinimalMapAdapter(new WeakHashMap<Object, Object>());
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private Part part;
/* 30 */   private List calibrationPartNumbers = null;
/*    */   
/*    */   private PartContext(Part part) {
/* 33 */     this.part = part;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized PartContext getInstance(Part part) {
/* 38 */     return (PartContext)multitonSupport.getInstance(part);
/*    */   }
/*    */   
/*    */   public synchronized List getCalibrationPartNumbers() {
/* 42 */     if (this.calibrationPartNumbers == null) {
/*    */       try {
/* 44 */         List tmp = this.part.getCalibrationParts();
/* 45 */         if (tmp == null) {
/* 46 */           tmp = Collections.EMPTY_LIST;
/*    */         }
/* 48 */         this.calibrationPartNumbers = tmp;
/*    */       }
/* 50 */       catch (Exception e) {
/* 51 */         this.calibrationPartNumbers = Collections.EMPTY_LIST;
/*    */       } 
/*    */     }
/*    */     
/* 55 */     return this.calibrationPartNumbers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasSubParts() {
/* 60 */     return (getCalibrationPartNumbers().size() > 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\PartContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */