/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*    */ 
/*    */ public class VersionFilter
/*    */   implements DwnldFilter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String version;
/*    */   
/*    */   public VersionFilter(String version) {
/* 12 */     this.version = version;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 16 */     return this.version;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\VersionFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */