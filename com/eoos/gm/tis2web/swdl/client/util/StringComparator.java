/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
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
/*    */ public class StringComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object obj, Object obj1) {
/*    */     try {
/* 20 */       return ((String)obj).compareTo((String)obj1);
/* 21 */     } catch (Exception e) {
/* 22 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\StringComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */