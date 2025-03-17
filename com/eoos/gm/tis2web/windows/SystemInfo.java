/*    */ package com.eoos.gm.tis2web.windows;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemInfo
/*    */ {
/* 12 */   private static Logger log = Logger.getLogger(SystemInfo.class);
/*    */   
/* 14 */   private static SystemInfo instance = null;
/*    */   
/*    */   private static final String LIBRARY = "wsibridge";
/*    */   
/*    */   public static SystemInfo getInstance() {
/* 19 */     synchronized (SystemInfo.class) {
/* 20 */       if (instance == null) {
/*    */         try {
/* 22 */           System.loadLibrary("wsibridge");
/* 23 */           instance = new SystemInfo();
/* 24 */         } catch (UnsatisfiedLinkError e) {
/* 25 */           log.error("Could not load library: library does not exist" + e);
/* 26 */         } catch (SecurityException e) {
/* 27 */           log.error("Could not load library: security exception" + e);
/* 28 */         } catch (Exception e) {
/* 29 */           log.error("Could not load library: unexpected error " + e);
/*    */         } 
/*    */       }
/* 32 */       return instance;
/*    */     } 
/*    */   }
/*    */   private native long nativeGetFreeSpace(String paramString);
/*    */   public long getFreeSpace(String path) throws IllegalArgumentException, FileNotFoundException, RuntimeException {
/* 37 */     synchronized (SystemInfo.class) {
/* 38 */       if (path == null || path.length() == 0) {
/* 39 */         throw new IllegalArgumentException("Parameter path must not be null or empty");
/*    */       }
/* 41 */       File f = new File(path);
/* 42 */       if (!f.exists()) {
/* 43 */         throw new FileNotFoundException("The specified path does not exist");
/*    */       }
/* 45 */       return nativeGetFreeSpace(path);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\windows\SystemInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */