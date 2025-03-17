/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import com.eoos.util.Task;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientClassLoader
/*    */   extends ClassLoader
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(ClientClassLoader.class);
/*    */   
/*    */   private TaskExecutionClient taskExecutionClient;
/*    */   
/*    */   public ClientClassLoader(ClassLoader parent, TaskExecutionClient taskExecutionClient) {
/* 16 */     super(parent);
/* 17 */     this.taskExecutionClient = taskExecutionClient;
/*    */   }
/*    */   
/*    */   protected Class findClass(String name) throws ClassNotFoundException {
/* 21 */     if (name.startsWith("com.eoos.") || name.startsWith("javax.mail.") || name.startsWith("com.sun.mail")) {
/*    */       byte[] data;
/*    */       try {
/* 24 */         data = loadData(name);
/* 25 */       } catch (Exception e) {
/* 26 */         throw new ClassNotFoundException("unable to find class: " + name, e);
/*    */       } 
/* 28 */       return defineClass(name, data, 0, data.length, getClass().getProtectionDomain());
/*    */     } 
/* 30 */     return super.findClass(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private byte[] loadData(String name) throws Exception {
/* 36 */     byte[] retValue = null;
/* 37 */     Task task = new ClassDataLoadingTask(name);
/* 38 */     Object tmp = this.taskExecutionClient.execute(task);
/*    */     
/* 40 */     if (tmp instanceof Exception) {
/* 41 */       throw (Exception)tmp;
/*    */     }
/* 43 */     retValue = (byte[])tmp;
/* 44 */     log.debug("loaded class data for:" + name + " (size:" + retValue.length + ")");
/* 45 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\ClientClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */