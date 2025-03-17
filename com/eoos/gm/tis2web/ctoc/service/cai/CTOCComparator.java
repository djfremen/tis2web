/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public interface CTOCComparator extends Comparator {
/*  6 */   public static final CTOCComparator INSTANCE = new CTOCComparator()
/*    */     {
/*    */       public int compare(Object o1, Object o2) {
/*  9 */         SITOCElement n1 = (SITOCElement)o1;
/* 10 */         SITOCElement n2 = (SITOCElement)o2;
/*    */         
/* 12 */         if (n1.getType() == n2.getType()) {
/* 13 */           return n1.getOrder() - n2.getOrder();
/*    */         }
/* 15 */         return n1.getType().ord() - n2.getType().ord();
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */