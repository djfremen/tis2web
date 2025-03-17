/*    */ package com.eoos.gm.tis2web.frame.dls.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class SoftwareKeyCheckServer
/*    */ {
/*    */   public static StringBuffer setSoftwareKeyCheck(String sessionID, StringBuffer jnlp) {
/* 13 */     String portalID = getPortal(sessionID);
/* 14 */     StringBuffer JNLP = jnlp;
/* 15 */     if (portalID != null) {
/* 16 */       boolean portalIDFound = existInConfiguration(portalID);
/*    */       
/* 18 */       if (portalIDFound == true)
/* 19 */       { String value = getValueOf(portalID);
/* 20 */         if (value != null && !value.equals("") && !value.equalsIgnoreCase("null")) {
/* 21 */           StringUtilities.replace(JNLP, "{INSTSWKEYLOCAL}", "true");
/*    */         } else {
/* 23 */           StringUtilities.replace(JNLP, "{INSTSWKEYLOCAL}", "false");
/*    */         }  }
/* 25 */       else { StringUtilities.replace(JNLP, "{INSTSWKEYLOCAL}", "false"); }
/*    */     
/*    */     } 
/* 28 */     return JNLP;
/*    */   }
/*    */   
/*    */   public static String getPortal(String sessionID) {
/* 32 */     String session = sessionID;
/* 33 */     if (session != null && session.indexOf('.') > -1) {
/* 34 */       session = session.substring(0, session.indexOf('.'));
/* 35 */       return session;
/*    */     } 
/* 37 */     return sessionID;
/*    */   }
/*    */   
/*    */   private static Configuration getCfgMaxCountSWK() {
/* 41 */     return (Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.software.key.maxcount.");
/*    */   }
/*    */   
/*    */   private static boolean existInConfiguration(String reference) {
/* 45 */     Configuration cfgMaxCountSWK = getCfgMaxCountSWK();
/* 46 */     for (Iterator<String> it = cfgMaxCountSWK.getKeys().iterator(); it.hasNext(); ) {
/* 47 */       String next = it.next();
/* 48 */       if (next.equalsIgnoreCase(reference)) {
/* 49 */         return true;
/*    */       }
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public static String getValueOf(String reference) {
/* 56 */     String ret = null;
/* 57 */     Configuration cfgMaxCountSWK = getCfgMaxCountSWK();
/* 58 */     for (Iterator<String> it = cfgMaxCountSWK.getKeys().iterator(); it.hasNext(); ) {
/* 59 */       String next = it.next();
/* 60 */       if (next.equalsIgnoreCase(reference)) {
/* 61 */         return cfgMaxCountSWK.getProperty(next);
/*    */       }
/*    */     } 
/* 64 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\SoftwareKeyCheckServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */