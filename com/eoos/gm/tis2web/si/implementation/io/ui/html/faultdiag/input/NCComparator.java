/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NCComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 24 */     return o1.toString().compareToIgnoreCase(o2.toString());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\NCComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */