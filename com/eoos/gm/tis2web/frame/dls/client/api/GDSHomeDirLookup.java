/*    */ package com.eoos.gm.tis2web.frame.dls.client.api;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import ca.beq.util.win32.registry.RegistryValue;
/*    */ import ca.beq.util.win32.registry.RootKey;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class GDSHomeDirLookup
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(GDSHomeDirLookup.class);
/*    */   
/*    */   public static File getHomeDir() {
/* 15 */     File ret = null;
/*    */     try {
/* 17 */       RegistryKey.initialize();
/*    */       
/* 19 */       RegistryKey regKey = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "SOFTWARE\\GM\\GDS");
/* 20 */       if (regKey.exists()) {
/*    */         
/* 22 */         RegistryValue value = regKey.getValue("GDS_HOME");
/* 23 */         if (value != null) {
/* 24 */           ret = new File(value.getStringValue());
/*    */         }
/*    */       } 
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to read GDS home from registry, ignoring - exception: " + e, e);
/*    */     } 
/*    */     
/* 31 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\GDSHomeDirLookup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */