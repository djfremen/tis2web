/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class GetFileContentTask
/*    */   implements Task, Task.ClusterExecution
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 14 */   private static final Logger log = Logger.getLogger(GetFileContentTask.class);
/*    */   
/*    */   private File file;
/*    */   
/*    */   public GetFileContentTask(File file) {
/* 19 */     this.file = file;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 24 */       FileInputStream fis = new FileInputStream(this.file);
/*    */       try {
/* 26 */         return StreamUtil.readFully(fis);
/*    */       } finally {
/*    */         
/* 29 */         fis.close();
/*    */       } 
/* 31 */     } catch (Exception e) {
/* 32 */       log.error("unable to retrieve file content for file: " + this.file + ", returning null - exception:" + e, e);
/* 33 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlog\GetFileContentTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */