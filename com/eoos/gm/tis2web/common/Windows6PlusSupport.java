/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Windows6PlusSupport
/*    */ {
/* 10 */   private static final Logger log = Logger.getLogger(Windows6PlusSupport.class);
/*    */   
/*    */   private boolean isWindows6Plus = false;
/*    */   
/*    */   public Windows6PlusSupport() {
/* 15 */     String osName = System.getProperty("os.name");
/* 16 */     String osVersion = System.getProperty("os.version");
/*    */     try {
/* 18 */       this.isWindows6Plus = (osName.toUpperCase().indexOf("WINDOWS") >= 0 && osVersion.compareTo("6.0") >= 0);
/*    */     }
/* 20 */     catch (Exception e) {}
/*    */     
/* 22 */     log.debug("os.name=" + osName + ", os.version=" + osVersion + ", isWindows6Plus=" + this.isWindows6Plus);
/*    */   }
/*    */   
/*    */   public boolean isWindows6Plus() {
/* 26 */     return this.isWindows6Plus;
/*    */   }
/*    */   
/*    */   public boolean setFullAccess(File dir) {
/* 30 */     boolean result = false;
/* 31 */     if (isWindows6Plus()) {
/* 32 */       String[] cmdArray = new String[4];
/* 33 */       cmdArray[0] = "icacls.exe";
/* 34 */       cmdArray[1] = dir.getAbsolutePath();
/* 35 */       cmdArray[2] = "/grant";
/* 36 */       cmdArray[3] = "*S-1-5-32-545:(OI)(CI)F";
/* 37 */       StringBuffer sbuf = new StringBuffer();
/* 38 */       for (int i = 0; i < cmdArray.length; i++) {
/* 39 */         sbuf.append(cmdArray[i] + " ");
/*    */       }
/* 41 */       log.debug("executing " + sbuf.toString() + " ...");
/*    */       try {
/* 43 */         Process process = Runtime.getRuntime().exec(cmdArray, (String[])null, (File)null);
/*    */ 
/*    */         
/* 46 */         int status = process.waitFor();
/* 47 */         log.debug("... ICACLS command returned: " + status);
/* 48 */         if (status == 0) {
/* 49 */           result = true;
/*    */         }
/*    */       }
/* 52 */       catch (Exception e) {
/* 53 */         log.error("... executing ICACLS command threw Exception: " + e.toString());
/*    */       } 
/*    */     } 
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\Windows6PlusSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */