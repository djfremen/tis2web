/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.settings.Registry;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.RegistryProvider;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalRegistryProvider
/*    */   implements RegistryProvider
/*    */ {
/* 18 */   private static Logger log = Logger.getLogger(LocalRegistryProvider.class);
/*    */   
/*    */   public Registry getRegistry() {
/* 21 */     Registry result = null;
/* 22 */     String os = System.getProperty("os.name");
/* 23 */     if (os != null && os.toUpperCase(Locale.ENGLISH).indexOf("WINDOWS") >= 0) {
/* 24 */       result = (new WindowsRegistryProvider()).getRegistry();
/* 25 */     } else if (os != null && os.toUpperCase(Locale.ENGLISH).indexOf("LINUX") >= 0) {
/* 26 */       result = (new LinuxRegistryProvider()).getRegistry();
/*    */     } else {
/* 28 */       log.error("Unsupported Operating System: " + os);
/*    */     } 
/* 30 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\LocalRegistryProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */