/*    */ package com.eoos.gm.tis2web.kdr.client;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import ca.beq.util.win32.registry.RegistryValue;
/*    */ import ca.beq.util.win32.registry.RootKey;
/*    */ import java.io.File;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public abstract class SmartStartVersion
/*    */ {
/*    */   public abstract String getInstalledSmartStartVersion();
/*    */   
/*    */   public abstract void setInstalledSmartStartVersion(String paramString) throws Exception;
/*    */   
/*    */   protected static SmartStartVersion getInstance() {
/* 17 */     SmartStartVersion result = null;
/* 18 */     if (System.getProperty("os.name").toUpperCase(Locale.ENGLISH).indexOf("WINDOWS") >= 0) {
/* 19 */       if (System.getProperty("os.name").toUpperCase(Locale.ENGLISH).indexOf("VISTA") < 0) {
/* 20 */         result = new SmartStartVersionWindows();
/*    */       } else {
/* 22 */         result = new SmartStartVersionVista();
/*    */       } 
/*    */     }
/* 25 */     return result;
/*    */   }
/*    */   
/*    */   protected File getGDSHomeDir() {
/* 29 */     String ret = null;
/* 30 */     RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "Software\\GM\\GDS");
/* 31 */     if (r.exists() && r.hasValue("GDS_HOME")) {
/* 32 */       RegistryValue v = r.getValue("GDS_HOME");
/* 33 */       ret = v.getStringValue();
/*    */     } 
/* 35 */     return (ret != null) ? new File(ret) : null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\SmartStartVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */