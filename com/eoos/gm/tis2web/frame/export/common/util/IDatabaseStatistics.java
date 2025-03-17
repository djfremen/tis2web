/*   */ package com.eoos.gm.tis2web.frame.export.common.util;
/*   */ 
/*   */ public interface IDatabaseStatistics {
/* 4 */   public static final IDatabaseStatistics DISABLED = new IDatabaseStatistics()
/*   */     {
/*   */       public String register(String query) {
/* 7 */         return query;
/*   */       }
/*   */     };
/*   */   
/*   */   String register(String paramString);
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\IDatabaseStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */