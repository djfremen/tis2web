/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class SessionKey
/*    */   implements SoftwareKey, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 11 */   private String sessionID = null;
/*    */   
/*    */   public SessionKey(String sessionID) {
/* 14 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public String getSessionID() {
/* 18 */     return this.sessionID;
/*    */   }
/*    */   
/*    */   public byte[] toExternal() {
/* 22 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\SessionKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */