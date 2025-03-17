/*    */ package com.eoos.datatype;
/*    */ 
/*    */ import com.eoos.util.HashCalc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IdentifiableObjectBase
/*    */   implements Identifiable
/*    */ {
/*    */   public boolean equals(Object obj) {
/* 15 */     boolean retValue = false;
/* 16 */     if (this == obj) {
/* 17 */       retValue = true;
/* 18 */     } else if (obj instanceof Identifiable) {
/* 19 */       Identifiable identifiable = (Identifiable)obj;
/* 20 */       retValue = getIdentifier().equals(identifiable.getIdentifier());
/*    */     } 
/* 22 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 26 */     int retValue = Identifiable.class.hashCode();
/* 27 */     retValue = HashCalc.addHashCode(retValue, getIdentifier());
/* 28 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return String.valueOf(getIdentifier());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\IdentifiableObjectBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */