/*    */ package com.eoos.gm.tis2web.sps.client.system;
/*    */ 
/*    */ public class CommunicationException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String servername;
/*    */   
/*    */   public CommunicationException(Throwable cause, String servername) {
/* 10 */     super("error on communication with server: " + servername, cause);
/* 11 */     this.servername = servername;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServerName() {
/* 16 */     return this.servername;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\system\CommunicationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */