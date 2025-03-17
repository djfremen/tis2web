/*    */ package com.eoos.gm.tis2web.vc.v2.base.value;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ValueUtil
/*    */   implements ValueManagement, ValueManagement.Mixin1
/*    */ {
/*    */   public final ValueManagement valueManagement;
/*    */   private ValueManagement.Mixin1 mixin1;
/*    */   
/*    */   public ValueUtil(ValueManagement valueManagement) {
/* 13 */     this.valueManagement = valueManagement;
/* 14 */     if (this.valueManagement instanceof ValueManagement.Mixin1) {
/* 15 */       this.mixin1 = (ValueManagement.Mixin1)this.valueManagement;
/*    */     } else {
/* 17 */       this.mixin1 = new Mixin1_STD(this.valueManagement);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean haveIntersection(Value value1, Value value2) {
/* 22 */     return this.mixin1.haveIntersection(value1, value2);
/*    */   }
/*    */   
/*    */   public Value intersect(Value value1, Value value2) {
/* 26 */     return this.valueManagement.intersect(value1, value2);
/*    */   }
/*    */   
/*    */   public boolean equals(Value value1, Value value2) {
/* 30 */     return this.valueManagement.equals(value1, value2);
/*    */   }
/*    */   
/*    */   public int hashCode(Value value) {
/* 34 */     return this.valueManagement.hashCode(value);
/*    */   }
/*    */   
/*    */   public boolean includes(Value value1, Value value2) {
/* 38 */     return this.mixin1.includes(value1, value2);
/*    */   }
/*    */   
/*    */   public Set createDisjunctiveValues(Set values, Set result) {
/* 42 */     return this.valueManagement.createDisjunctiveValues(values, result);
/*    */   }
/*    */   
/*    */   public Value union(Collection values) {
/* 46 */     return this.valueManagement.union(values);
/*    */   }
/*    */   
/*    */   public Set resolve(Value value, ValueManagement.ResolveCallback callback) throws UnresolvableException {
/* 50 */     return this.valueManagement.resolve(value, callback);
/*    */   }
/*    */   
/*    */   public boolean isANY(Value value) {
/* 54 */     return this.valueManagement.isANY(value);
/*    */   }
/*    */   
/*    */   public Value toValue(Object obj) {
/* 58 */     return this.valueManagement.toValue(obj);
/*    */   }
/*    */   
/*    */   public Value getANY() {
/* 62 */     return this.valueManagement.getANY();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\value\ValueUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */