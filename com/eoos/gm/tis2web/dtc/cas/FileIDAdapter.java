/*    */ package com.eoos.gm.tis2web.dtc.cas;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.cas.api.Identifier;
/*    */ import java.io.File;
/*    */ 
/*    */ public class FileIDAdapter
/*    */   implements Identifier
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private File file;
/*    */   
/*    */   public FileIDAdapter(File file) {
/* 13 */     this.file = file;
/*    */   }
/*    */   
/*    */   public File getFile() {
/* 17 */     return this.file;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\FileIDAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */