/*    */ package com.eoos.gm.tis2web.si.implementation.log;
/*    */ public interface SIEventLog { void add(Entry paramEntry) throws Exception;
/*    */   
/*    */   public static interface Entry { long getTSDisplay();
/*    */     
/*    */     String getUserID();
/*    */     
/*    */     String getSIInformation();
/*    */     
/*    */     String getVC(); }
/*    */   
/*    */   public static interface Query { public static class AbortionException extends Exception {
/*    */       private static final long serialVersionUID = 1L;
/*    */       
/*    */       public AbortionException(Collection processedEntries) {
/* 16 */         this.entries = processedEntries;
/*    */       }
/*    */       private Collection entries;
/*    */       public Collection getProcessedEntries() {
/* 20 */         return this.entries;
/*    */       }
/*    */     } }
/*    */ 
/*    */   
/*    */   public static interface SPI {
/*    */     void add(Collection param1Collection);
/*    */   } }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\log\SIEventLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */