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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VersDateComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 22 */       long date1 = ((Version)o1).getDate().longValue();
/* 23 */       long date2 = ((Version)o2).getDate().longValue();
/* 24 */       if (date1 > date2) {
/* 25 */         return -1;
/*    */       }
/* 27 */       if (date1 == date2) {
/* 28 */         return 0;
/*    */       }
/* 30 */       return 1;
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\VersDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */