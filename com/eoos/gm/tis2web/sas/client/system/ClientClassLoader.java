/*    */ package com.eoos.gm.tis2web.sas.client.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.system.starter.StarterProvider;
/*    */ import com.eoos.gm.tis2web.sas.common.system.ClassDataLoadingTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.File;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientClassLoader
/*    */   extends ClassLoader
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger("system." + ClientClassLoader.class);
/*    */   
/* 19 */   private static Map dataCache = new HashMap<Object, Object>();
/*    */   
/* 21 */   private final Object SYNC_LIB_PATH = new Object();
/* 22 */   private String libPath = null;
/*    */   
/*    */   public ClientClassLoader() {
/* 25 */     super(ClientClassLoader.class.getClassLoader());
/*    */   }
/*    */   
/*    */   public ClientClassLoader(ClassLoader parent) {
/* 29 */     super(parent);
/*    */   }
/*    */   
/*    */   private String getLibPath() {
/* 33 */     synchronized (this.SYNC_LIB_PATH) {
/* 34 */       if (this.libPath == null) {
/* 35 */         this.libPath = StarterProvider.getInstance().getStarter().getLibraryLocation();
/*    */       }
/* 37 */       return this.libPath;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String findLibrary(String libname) {
/* 42 */     File file = new File(getLibPath(), libname + ".dll");
/* 43 */     if (file.exists()) {
/* 44 */       log.debug("found library " + libname + ", returning absolute path:" + file.getAbsolutePath());
/* 45 */       return file.getAbsolutePath();
/*    */     } 
/* 47 */     log.warn("could not find library " + libname + " at " + getLibPath());
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
/* 53 */     return super.loadClass(name, false);
/*    */   }
/*    */   
/*    */   protected Class findClass(String name) throws ClassNotFoundException {
/*    */     try {
/* 58 */       byte[] b = null;
/* 59 */       if (name.startsWith("com.eoos.")) {
/* 60 */         b = loadData(name);
/*    */       }
/* 62 */       if (b != null) {
/* 63 */         return defineClass(name, b, 0, b.length, getClass().getProtectionDomain());
/*    */       }
/* 65 */       throw new ClassNotFoundException("could not find class:" + name);
/*    */     }
/* 67 */     catch (Exception e) {
/* 68 */       throw new ClassNotFoundException(name);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] loadData(String name) throws Exception {
/* 74 */     byte[] retValue = (byte[])dataCache.get(name);
/* 75 */     if (retValue == null) {
/* 76 */       ClassDataLoadingTask classDataLoadingTask = new ClassDataLoadingTask(name);
/* 77 */       Object tmp = ServerTaskExecution.getInstance().execute((Task)classDataLoadingTask);
/* 78 */       if (tmp instanceof Exception) {
/* 79 */         throw (Exception)tmp;
/*    */       }
/* 81 */       retValue = (byte[])tmp;
/* 82 */       dataCache.put(name, retValue);
/* 83 */       log.debug("loaded class data for:" + name + " (size:" + retValue.length + ")");
/*    */     } 
/* 85 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\ClientClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */