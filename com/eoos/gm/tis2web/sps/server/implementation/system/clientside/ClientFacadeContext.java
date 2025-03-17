/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*    */ 
/*    */ import com.eoos.context.Context;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientFacadeContext
/*    */   extends Context
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(ClientFacadeContext.class);
/*    */   
/*    */   public ClientFacadeContext() {
/* 14 */     log.debug("creating " + this);
/*    */   }
/*    */ 
/*    */   
/*    */   public DownloadThread getDownloadThread() {
/* 19 */     return (DownloadThread)getObject(DownloadThread.class);
/*    */   }
/*    */   
/*    */   public void setDownloadThread(DownloadThread dt) {
/* 23 */     storeObject(DownloadThread.class, dt);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\ClientFacadeContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */