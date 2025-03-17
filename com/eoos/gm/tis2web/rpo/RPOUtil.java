/*    */ package com.eoos.gm.tis2web.rpo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPO;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RPOUtil
/*    */ {
/*    */   public static String getFamilyDescription(RPO rpo, Locale locale) {
/* 15 */     String description = null;
/* 16 */     if (rpo != null) {
/* 17 */       RPOFamily family = rpo.getFamily();
/* 18 */       if (family != null) {
/* 19 */         description = family.getDescription(locale);
/*    */       }
/*    */     } 
/* 22 */     return description;
/*    */   }
/*    */   
/*    */   public static String getDescription(RPO rpo, Locale locale) {
/* 26 */     String description = null;
/* 27 */     if (rpo != null) {
/* 28 */       description = rpo.getDescription(locale);
/*    */     }
/*    */     
/* 31 */     return description;
/*    */   }
/*    */   
/* 34 */   public static final Comparator RPO_COMPARATOR = new Comparator()
/*    */     {
/*    */       public int compare(Object o1, Object o2) {
/* 37 */         return ((RPO)o1).compareTo(o2);
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\RPOUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */