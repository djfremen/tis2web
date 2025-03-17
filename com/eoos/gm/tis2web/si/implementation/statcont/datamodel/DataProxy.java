/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system.DataProvider;
/*    */ import com.eoos.util.AssertUtil;
/*    */ import com.eoos.util.HashCalc;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class DataProxy
/*    */   implements Data
/*    */ {
/* 13 */   private static final Logger logNonTRACEABLE = Logger.getLogger("nontraceable.docs");
/*    */   
/*    */   private String filename;
/*    */   
/* 17 */   private final Object SYNC_TRACEABLE = new Object();
/*    */   
/* 19 */   private Boolean traceable = null;
/*    */   
/*    */   public DataProxy(String filename) {
/* 22 */     AssertUtil.ensure(filename, AssertUtil.NOT_NULL);
/*    */     
/* 24 */     if (!filename.startsWith("/")) {
/* 25 */       filename = "/" + filename;
/*    */     }
/* 27 */     this.filename = filename.toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] toByteArray() {
/*    */     try {
/* 33 */       return DataProvider.getInstance().getData(this.filename);
/* 34 */     } catch (Exception e) {
/* 35 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 40 */     boolean retValue = false;
/* 41 */     if (this == obj) {
/* 42 */       retValue = true;
/* 43 */     } else if (obj instanceof DataProxy) {
/* 44 */       DataProxy dp = (DataProxy)obj;
/* 45 */       retValue = this.filename.equals(dp.filename);
/*    */     } 
/* 47 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 51 */     int retValue = DataProxy.class.hashCode();
/* 52 */     retValue = HashCalc.addHashCode(retValue, this.filename);
/* 53 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return super.toString() + "[filename: " + this.filename + ", " + (isTraceableDocument() ? "traceable" : "not traceable") + "]";
/*    */   }
/*    */   
/*    */   public boolean isTraceableDocument() {
/* 61 */     synchronized (this.SYNC_TRACEABLE) {
/* 62 */       if (this.traceable == null) {
/* 63 */         this.traceable = Boolean.valueOf(TrackingList.getInstance().isIncluded(this.filename));
/* 64 */         if (!this.traceable.booleanValue() && this.filename.endsWith(".html")) {
/* 65 */           logNonTRACEABLE.info("non-traceable document: " + this.filename);
/*    */         }
/*    */       } 
/* 68 */       return this.traceable.booleanValue();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getFilename() {
/* 73 */     return this.filename;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 77 */     int end = this.filename.lastIndexOf("/");
/* 78 */     return this.filename.substring(0, end);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\DataProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */