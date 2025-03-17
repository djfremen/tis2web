/*   */ package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;
/*   */ 
/*   */ import java.io.Serializable;
/*   */ import java.util.List;
/*   */ 
/*   */ public interface ProgrammingSequence extends Serializable {
/* 7 */   public static final Integer FAIL = Integer.valueOf(-1);
/* 8 */   public static final Integer SKIP = Integer.valueOf(0);
/* 9 */   public static final Integer SUCCESS = Integer.valueOf(1);
/*   */   
/*   */   List getSequence();
/*   */   
/*   */   List getProgrammingData(int paramInt);
/*   */   
/*   */   boolean hasSequenceHardwareDependency();
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\ProgrammingSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */