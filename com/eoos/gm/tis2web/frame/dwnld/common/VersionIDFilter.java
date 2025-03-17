/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ public class VersionIDFilter
/*    */   implements DwnldFilter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Collection versions;
/*    */   
/*    */   public VersionIDFilter(Collection versions) {
/* 14 */     this.versions = versions;
/*    */   }
/*    */   
/*    */   public Collection getVersions() {
/* 18 */     return this.versions;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\VersionIDFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */