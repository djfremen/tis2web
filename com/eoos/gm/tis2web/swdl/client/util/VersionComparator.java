/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VersionComparator
/*    */   implements Comparator<Version>
/*    */ {
/*    */   public int compare(Version v1, Version v2) {
/*    */     try {
/* 18 */       StringVersionComparator svc = new StringVersionComparator(".", 10);
/* 19 */       return svc.compare(v1.getNumber(), v2.getNumber());
/* 20 */     } catch (Exception e) {
/* 21 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\VersionComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */