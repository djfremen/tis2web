/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
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
/*    */ public class ApplicationComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 22 */       return ((Application)o1).getDescription().compareTo(((Application)o2).getDescription());
/* 23 */     } catch (Exception e) {
/* 24 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\ApplicationComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */