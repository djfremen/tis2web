/*    */ package com.eoos.gm.tis2web.vc.v2.base.value;
/*    */ 
/*    */ 
/*    */ public class Mixin1_STD
/*    */   implements ValueManagement.Mixin1
/*    */ {
/*    */   private ValueManagement valueMgmt;
/*    */   
/*    */   public Mixin1_STD(ValueManagement valueMgmt) {
/* 10 */     this.valueMgmt = valueMgmt;
/*    */   }
/*    */   
/*    */   public boolean haveIntersection(Value value1, Value value2) {
/* 14 */     return (this.valueMgmt.intersect(value1, value2) != null);
/*    */   }
/*    */   
/*    */   public boolean includes(Value value1, Value value2) {
/* 18 */     return this.valueMgmt.equals(this.valueMgmt.intersect(value1, value2), value2);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\value\Mixin1_STD.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */