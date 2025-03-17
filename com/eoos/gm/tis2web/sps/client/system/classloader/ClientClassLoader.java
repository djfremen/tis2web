/*    */ package com.eoos.gm.tis2web.sps.client.system.classloader;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.system.ServerTaskExecution;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.system.classloader.ClassDataLoadingTask;
/*    */ import com.eoos.util.ByteUtil;
/*    */ import com.eoos.util.Task;
/*    */ import java.math.BigDecimal;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientClassLoader
/*    */   extends ClassLoader
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(ClientClassLoader.class);
/*    */   
/* 22 */   private static BigDecimal count = BigDecimal.valueOf(0L);
/*    */   static {
/* 24 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*    */           public void run() {
/*    */             try {
/* 27 */               ClientClassLoader.log.debug("total size of downloaded data:" + ByteUtil.toString(ClientClassLoader.count.longValue()));
/*    */             } finally {}
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientClassLoader(ClassLoader parent) {
/* 35 */     super(parent);
/*    */   }
/*    */   
/*    */   protected Class findClass(String name) throws ClassNotFoundException {
/*    */     try {
/* 40 */       byte[] b = null;
/* 41 */       if (name.startsWith("com.eoos.")) {
/* 42 */         b = loadData(name);
/* 43 */       } else if (name.startsWith("snmp.")) {
/* 44 */         return loadSnmpClass(name);
/*    */       } 
/* 46 */       if (b != null) {
/* 47 */         return defineClass(name, b, 0, b.length, getClass().getProtectionDomain());
/*    */       }
/* 49 */       throw new ClassNotFoundException("could not find class:" + name);
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       throw new ClassNotFoundException(name);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private Class loadSnmpClass(String name) throws ClassNotFoundException {
/*    */     try {
/* 59 */       ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/* 60 */       String jarSnmp = context.getClientSettings().getProperty("snmp.library");
/* 61 */       JarClassLoader loader = new JarClassLoader(new URL(jarSnmp));
/* 62 */       return loader.getClass();
/* 63 */     } catch (Exception x) {
/* 64 */       throw new ClassNotFoundException("could not find class:" + name);
/*    */     } 
/*    */   }
/*    */   
/*    */   static class JarClassLoader
/*    */     extends URLClassLoader {
/*    */     public JarClassLoader(URL url) {
/* 71 */       super(new URL[] { url });
/*    */     }
/*    */   }
/*    */   
/*    */   private byte[] loadData(String name) throws Exception {
/* 76 */     byte[] retValue = null;
/* 77 */     ClassDataLoadingTask classDataLoadingTask = new ClassDataLoadingTask(name);
/* 78 */     Object tmp = null;
/*    */     try {
/* 80 */       tmp = ServerTaskExecution.getInstance().execute((Task)classDataLoadingTask);
/* 81 */     } catch (Exception e) {
/* 82 */       log.error("unable to execute class data loading - exception:" + e, e);
/* 83 */       throw e;
/*    */     } 
/* 85 */     if (tmp instanceof Exception) {
/* 86 */       throw (Exception)tmp;
/*    */     }
/* 88 */     retValue = (byte[])tmp;
/* 89 */     log.debug("loaded class data for:" + name + " (size:" + retValue.length + ")");
/* 90 */     synchronized (getClass()) {
/* 91 */       count = count.add(BigDecimal.valueOf(retValue.length));
/*    */     } 
/* 93 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\system\classloader\ClientClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */