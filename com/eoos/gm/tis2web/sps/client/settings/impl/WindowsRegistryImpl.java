/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import ca.beq.util.win32.registry.RegistryValue;
/*    */ import ca.beq.util.win32.registry.RootKey;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.Registry;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WindowsRegistryImpl
/*    */   implements Registry
/*    */ {
/* 19 */   private RegistryKey j2534Key = null;
/*    */   
/*    */   protected synchronized RegistryKey getJ2534Key() {
/* 22 */     if (this.j2534Key == null) {
/* 23 */       ClientSettings settings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/* 24 */       String j2534Version = settings.getProperty("windows.j2534.version");
/* 25 */       if (j2534Version == null) {
/* 26 */         j2534Version = "02.02";
/*    */       }
/* 28 */       String j2534Path = settings.getProperty("windows.j2534." + j2534Version + ".path");
/* 29 */       if (j2534Path == null) {
/* 30 */         j2534Path = "Software\\PassThruSupport";
/*    */       }
/* 32 */       RegistryKey.initialize();
/* 33 */       RegistryKey key = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, j2534Path);
/* 34 */       if (key.exists()) {
/* 35 */         this.j2534Key = key;
/*    */       }
/*    */     } 
/* 38 */     return this.j2534Key;
/*    */   }
/*    */   
/*    */   protected String getValue(RegistryKey regKey, String valueName) {
/* 42 */     String result = null;
/* 43 */     if (regKey.hasValue(valueName)) {
/* 44 */       RegistryValue value = regKey.getValue(valueName);
/* 45 */       result = value.getData().toString();
/*    */     } 
/* 47 */     return result;
/*    */   }
/*    */   
/*    */   public abstract List getLocalTools();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\WindowsRegistryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */