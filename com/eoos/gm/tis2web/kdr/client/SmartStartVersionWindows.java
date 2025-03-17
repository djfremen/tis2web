/*    */ package com.eoos.gm.tis2web.kdr.client;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import ca.beq.util.win32.registry.RegistryValue;
/*    */ import ca.beq.util.win32.registry.RootKey;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SmartStartVersionWindows
/*    */   extends SmartStartVersion
/*    */ {
/*    */   private static final String REGKEY_SMARTSTART_VERSION = "GDS_SMARTSTART_VERSION";
/* 13 */   private static final Logger log = Logger.getLogger(SmartStartVersionWindows.class);
/*    */ 
/*    */   
/*    */   public String getInstalledSmartStartVersion() {
/* 17 */     String ret = null;
/* 18 */     RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "Software\\GM\\GDS");
/* 19 */     if (r.exists() && r.hasValue("GDS_SMARTSTART_VERSION")) {
/* 20 */       RegistryValue v = r.getValue("GDS_SMARTSTART_VERSION");
/* 21 */       ret = v.getStringValue();
/* 22 */       log.info("Read SmartStart version from Registry: " + ret);
/*    */     } 
/* 24 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInstalledSmartStartVersion(String version) {
/* 29 */     RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "Software\\GM\\GDS");
/* 30 */     r.setValue(new RegistryValue("GDS_SMARTSTART_VERSION", version));
/* 31 */     log.info("Updated SmartStart version in Registry to: " + version);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\SmartStartVersionWindows.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */