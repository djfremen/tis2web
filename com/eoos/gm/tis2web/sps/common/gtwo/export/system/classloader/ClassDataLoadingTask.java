/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export.system.classloader;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassDataLoadingTask
/*    */   implements Serializable, Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String name;
/*    */   
/*    */   public ClassDataLoadingTask(String name) {
/* 20 */     StringBuffer tmp = new StringBuffer(name);
/* 21 */     StringUtilities.replace(tmp, ".", "/");
/* 22 */     tmp.append(".class");
/*    */     
/* 24 */     this.name = tmp.toString();
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 29 */       InputStream is = getClass().getClassLoader().getResourceAsStream(this.name);
/* 30 */       byte[] result = StreamUtil.readFully(is);
/* 31 */       is.close();
/* 32 */       return result;
/* 33 */     } catch (NullPointerException e) {
/* 34 */       return e;
/* 35 */     } catch (IOException e) {
/* 36 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return super.toString() + "[classname: " + String.valueOf(this.name) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\system\classloader\ClassDataLoadingTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */