/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.Registry;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.RegistryProvider;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsRegistryProvider
/*    */   implements RegistryProvider
/*    */ {
/*    */   private static final String WINDOWSREGISTRYCLASSPREFIX = "com.eoos.gm.tis2web.sps.client.settings.impl.WindowsRegistryImplV";
/* 21 */   private static Logger log = Logger.getLogger(WindowsRegistryProvider.class);
/*    */ 
/*    */   
/*    */   public Registry getRegistry() {
/* 25 */     Registry result = null;
/* 26 */     ClientAppContextProvider.getClientAppContext();
/*    */     try {
/* 28 */       ClientSettings settings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/* 29 */       String j2534Version = settings.getProperty("windows.j2534.version");
/* 30 */       if (j2534Version == null) {
/* 31 */         j2534Version = "02.02";
/*    */       }
/* 33 */       String className = "com.eoos.gm.tis2web.sps.client.settings.impl.WindowsRegistryImplV" + stripChars(j2534Version, '.');
/* 34 */       System.out.println(className);
/* 35 */       result = (Registry)Class.forName(className).newInstance();
/* 36 */     } catch (Exception e) {
/* 37 */       log.error("Cannot access local registry: " + e);
/*    */     } 
/* 39 */     return result;
/*    */   }
/*    */   
/*    */   private String stripChars(String str, char c) {
/* 43 */     StringBuffer result = new StringBuffer();
/* 44 */     char[] inChars = str.toCharArray();
/* 45 */     for (int i = 0; i < inChars.length; i++) {
/* 46 */       char nextChar = inChars[i];
/* 47 */       if (nextChar != c) {
/* 48 */         result.append(nextChar);
/*    */       }
/*    */     } 
/* 51 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\WindowsRegistryProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */