/*    */ package com.eoos.gm.tis2web.rpo.ri;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPO;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractRPO
/*    */   implements RPO
/*    */ {
/*    */   public int hashCode() {
/* 14 */     int ret = RPO.class.hashCode();
/* 15 */     ret = HashCalc.addHashCode(ret, getCode());
/* 16 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 20 */     if (this == obj)
/* 21 */       return true; 
/* 22 */     if (obj instanceof RPO) {
/* 23 */       RPO other = (RPO)obj;
/* 24 */       boolean ret = Util.equals(getCode(), other.getCode());
/* 25 */       return ret;
/*    */     } 
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(Object o) {
/* 32 */     return Util.compare(getCode(), ((RPO)o).getCode());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     return getCode();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\ri\AbstractRPO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */