/*   */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*   */ 
/*   */ 
/*   */ 
/*   */ public interface Result
/*   */ {
/* 7 */   public static final Status OK = Status.OK;
/* 8 */   public static final Status EXCEPTION = Status.EXCEPTION;
/* 9 */   public static final Status EXCEPTION_CUSTOM = Status.getInstance("custom.exception");
/*   */   
/*   */   Status getStatus();
/*   */   
/*   */   Object getObject();
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\Result.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */