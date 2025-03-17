/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AttributeValueMapImplBase
/*    */   implements AttributeValueMap
/*    */ {
/*    */   public boolean equals(Object obj) {
/* 15 */     boolean retValue = false;
/* 16 */     if (obj instanceof AttributeValueMap) {
/* 17 */       retValue = Util.equals(this, (AttributeValueMap)obj);
/*    */     }
/* 19 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 23 */     return Util.hashCode(this);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 27 */     return String.valueOf(Util.asPairCollection(this));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\avmap\AttributeValueMapImplBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */