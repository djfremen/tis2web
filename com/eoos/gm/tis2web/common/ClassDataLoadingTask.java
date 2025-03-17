/*    */ package com.eoos.gm.tis2web.common;
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
/*    */ 
/*    */ public class ClassDataLoadingTask
/*    */   implements Serializable, Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String name;
/*    */   
/*    */   public ClassDataLoadingTask(String name) {
/* 21 */     StringBuffer tmp = new StringBuffer();
/* 22 */     tmp.append(name);
/* 23 */     StringUtilities.replace(tmp, ".", "/");
/* 24 */     tmp.append(".class");
/*    */     
/* 26 */     this.name = tmp.toString();
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 31 */       InputStream is = getClass().getClassLoader().getResourceAsStream(this.name);
/* 32 */       byte[] result = StreamUtil.readFully(is);
/* 33 */       is.close();
/* 34 */       return result;
/* 35 */     } catch (NullPointerException e) {
/* 36 */       return e;
/* 37 */     } catch (IOException e) {
/* 38 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     return super.toString() + "[classname: " + String.valueOf(this.name) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\ClassDataLoadingTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */