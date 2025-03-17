/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSEventLog_Disabled
/*    */   implements SPSEventLog.Deletion, SPSEventLog.SPI
/*    */ {
/*    */   public void delete(Collection entries) throws Exception {}
/*    */   
/*    */   public Object getAttachedObject(SPSEventLog.Entry entry, Attachment.Key key) throws Exception {
/* 20 */     return null;
/*    */   }
/*    */   
/*    */   public Collection getEntries(SPSEventLog.Query.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 24 */     return Collections.EMPTY_LIST;
/*    */   }
/*    */   
/*    */   public void add(Collection entries) throws Exception {}
/*    */   
/*    */   public void ensureInit() {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLog_Disabled.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */