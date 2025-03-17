/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IOUtil
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(IOUtil.class);
/*    */   
/*    */   private static File dir;
/*    */   private static File[] vit1Files;
/*    */   private static int last;
/*    */   
/*    */   public static boolean CreateDir(String path) throws Exception {
/* 19 */     boolean result = false;
/*    */     try {
/* 21 */       File dir = new File(path);
/* 22 */       if (!dir.isDirectory() && !dir.exists() && 
/* 23 */         !dir.mkdir()) {
/* 24 */         throw new Exception("Can't create directory: " + path);
/*    */       }
/*    */       
/* 27 */       if (dir.exists() && dir.isDirectory()) {
/* 28 */         result = true;
/*    */       } else {
/* 30 */         log.debug("Name conflict: " + path);
/* 31 */         result = false;
/*    */       } 
/* 33 */     } catch (Exception e) {
/* 34 */       throw e;
/*    */     } 
/* 36 */     return result;
/*    */   }
/*    */   
/*    */   public static File getDirectory() {
/* 40 */     return dir;
/*    */   }
/*    */   
/*    */   public static File getLastVIT1File() {
/* 44 */     return vit1Files[last - 1];
/*    */   }
/*    */   
/*    */   public static File getNextVIT1File(File dir) throws Exception {
/* 48 */     if (IOUtil.dir == null) {
/* 49 */       IOUtil.dir = dir;
/* 50 */       vit1Files = dir.listFiles(new VIT1FileFilter());
/* 51 */       last = 0;
/*    */     } 
/* 53 */     if (vit1Files.length > last) {
/* 54 */       return vit1Files[last++];
/*    */     }
/* 56 */     throw new NoMoreFilesException("All VIT1 files in directory: " + IOUtil.dir.getPath() + " processed.");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\IOUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */