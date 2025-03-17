/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LanguageComparator
/*    */   implements Comparator
/*    */ {
/* 17 */   private Locale locale = null;
/*    */ 
/*    */   
/*    */   public LanguageComparator(Locale loc) {
/* 21 */     this.locale = loc;
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 26 */       return ((Language)o1).getDescription(this.locale).compareTo(((Language)o2).getDescription(this.locale));
/* 27 */     } catch (Exception e) {
/* 28 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\LanguageComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */