/*    */ package com.eoos.gm.tis2web.snapshot.client.system.classloader;
/*    */ 
/*    */ import com.eoos.gm.tis2web.snapshot.client.system.ServerTaskExecution;
/*    */ import com.eoos.gm.tis2web.snapshot.common.export.system.classloader.ClassDataLoadingTask;
/*    */ import com.eoos.util.ByteUtil;
/*    */ import com.eoos.util.Task;
/*    */ import java.math.BigDecimal;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientClassLoader
/*    */   extends ClassLoader
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(ClientClassLoader.class);
/*    */   
/* 18 */   private static BigDecimal count = BigDecimal.valueOf(0L);
/*    */   static {
/* 20 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*    */           public void run() {
/*    */             try {
/* 23 */               ClientClassLoader.log.debug("total size of downloaded data:" + ByteUtil.toString(ClientClassLoader.count.longValue()));
/*    */             } finally {}
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientClassLoader(ClassLoader parent) {
/* 31 */     super(parent);
/*    */   }
/*    */   
/*    */   protected Class findClass(String name) throws ClassNotFoundException {
/*    */     try {
/* 36 */       byte[] b = null;
/* 37 */       if (name.startsWith("com.eoos.")) {
/* 38 */         b = loadData(name);
/*    */       }
/* 40 */       if (b != null) {
/* 41 */         return defineClass(name, b, 0, b.length, getClass().getProtectionDomain());
/*    */       }
/* 43 */       throw new ClassNotFoundException("could not find class:" + name);
/*    */     }
/* 45 */     catch (Exception e) {
/* 46 */       throw new ClassNotFoundException(name);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] loadData(String name) throws Exception {
/* 52 */     byte[] retValue = null;
/* 53 */     ClassDataLoadingTask classDataLoadingTask = new ClassDataLoadingTask(name);
/* 54 */     Object tmp = null;
/*    */     try {
/* 56 */       tmp = ServerTaskExecution.getInstance().execute((Task)classDataLoadingTask);
/* 57 */     } catch (Exception e) {
/* 58 */       log.error("unable to execute class data loading - exception:" + e, e);
/* 59 */       throw e;
/*    */     } 
/* 61 */     if (tmp instanceof Exception) {
/* 62 */       throw (Exception)tmp;
/*    */     }
/* 64 */     retValue = (byte[])tmp;
/* 65 */     log.debug("loaded class data for:" + name + " (size:" + retValue.length + ")");
/* 66 */     synchronized (getClass()) {
/* 67 */       count = count.add(BigDecimal.valueOf(retValue.length));
/*    */     } 
/* 69 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\system\classloader\ClientClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */