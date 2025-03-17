/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*    */ 
/*    */ public class FileNameFilter
/*    */   implements DwnldFilter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String pattern;
/*    */   
/*    */   public FileNameFilter(String sqlNamePattern) {
/* 12 */     this.pattern = sqlNamePattern;
/*    */   }
/*    */   
/*    */   public String getSQLPattern() {
/* 16 */     return this.pattern;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\FileNameFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */