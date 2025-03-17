/*    */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ import com.eoos.util.HashCalc;
/*    */ import com.eoos.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayAdapter
/*    */ {
/* 16 */   protected Object adaptee = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DisplayAdapter(Object adaptee) {
/* 24 */     this.adaptee = adaptee;
/*    */   }
/*    */   
/*    */   public Object getAdaptee() {
/* 28 */     return this.adaptee;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 32 */     boolean retValue = false;
/* 33 */     if (this == obj) {
/* 34 */       retValue = true;
/* 35 */     } else if (obj instanceof DisplayAdapter) {
/* 36 */       DisplayAdapter adapter = (DisplayAdapter)obj;
/* 37 */       retValue = Util.equals(this.adaptee, adapter.adaptee);
/*    */     } 
/* 39 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 43 */     int retValue = getClass().hashCode();
/* 44 */     retValue = HashCalc.addHashCode(retValue, this.adaptee);
/* 45 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     String retValue = null;
/* 51 */     Object value = this.adaptee;
/* 52 */     if (value instanceof ValueAdapter) {
/* 53 */       value = ((ValueAdapter)value).getAdaptee();
/*    */     }
/* 55 */     if (value instanceof Denotation) {
/*    */       try {
/* 57 */         retValue = ((Denotation)value).getDenotation(null);
/* 58 */       } catch (Exception e) {
/*    */         
/* 60 */         retValue = String.valueOf(value);
/*    */       } 
/*    */     } else {
/* 63 */       retValue = String.valueOf(value);
/*    */     } 
/* 65 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\DisplayAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */