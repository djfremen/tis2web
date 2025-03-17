/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.settings.Registry;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.RegistryProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryProviderImpl
/*    */   implements RegistryProvider
/*    */ {
/*    */   public Registry getRegistry() {
/* 19 */     Registry result = null;
/* 20 */     result = (new LocalRegistryProvider()).getRegistry();
/* 21 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\RegistryProviderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */