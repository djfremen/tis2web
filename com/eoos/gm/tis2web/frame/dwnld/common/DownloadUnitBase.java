/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DownloadUnitBase
/*    */   implements IDownloadUnit
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public boolean equals(Object obj) {
/* 16 */     if (this == obj)
/* 17 */       return true; 
/* 18 */     if (obj instanceof DownloadUnitBase) {
/* 19 */       return (getIdentifier() == ((DownloadUnitBase)obj).getIdentifier());
/*    */     }
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 26 */     int ret = DownloadUnitBase.class.hashCode();
/* 27 */     ret = HashCalc.addHashCode(ret, getIdentifier());
/* 28 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return String.valueOf(getDescripition(Locale.ENGLISH)) + "[V." + String.valueOf(getVersionNumber()) + ", unit id:" + getIdentifier() + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\DownloadUnitBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */