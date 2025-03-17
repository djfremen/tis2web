/*    */ package com.eoos.gm.tis2web.dtc.implementation;
/*    */ 
/*    */ 
/*    */ public interface DTCLog {
/*    */   void add(Collection paramCollection, DTCStorageService.Callback paramCallback) throws Exception;
/*    */   
/*    */   Collection getEntries(BackendFilter paramBackendFilter, int paramInt) throws Exception;
/*    */   
/*    */   void delete(Collection paramCollection) throws Exception;
/*    */   
/*    */   public static class AbortionException extends Exception { private static final long serialVersionUID = 1L;
/*    */     private Collection entries;
/*    */     
/*    */     public AbortionException(Collection processedEntries) {
/* 15 */       this.entries = processedEntries;
/*    */     }
/*    */     
/*    */     public Collection getProcessedEntries() {
/* 19 */       return this.entries;
/*    */     } }
/*    */   public static interface BackendFilter { public static final String WILDCARD_ANY = "%"; public static final String WILDCARD_ONE = "_";
/*    */     
/*    */     Long getTimestampMIN();
/*    */     
/*    */     Long getTimestampMAX();
/*    */     
/*    */     Long getIdMin();
/*    */     
/*    */     Long getIdMax();
/*    */     
/*    */     String getBACCodePattern();
/*    */     
/*    */     List getPortalIDs(Type param1Type);
/*    */     
/*    */     List getApplicationIDs();
/*    */     
/*    */     List getCountryCodes();
/*    */     
/* 39 */     public enum Type { INCLUDE, EXCLUDE; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\DTCLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */