/*    */ package com.eoos.gm.tis2web.sps.client.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.impl.ClientAppContextImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientAppContextProvider
/*    */ {
/* 13 */   private static ClientAppContext appContext = ClientAppContextImpl.getInstance();
/*    */   
/*    */   public static ClientAppContext getClientAppContext() {
/* 16 */     return appContext;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\ClientAppContextProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */