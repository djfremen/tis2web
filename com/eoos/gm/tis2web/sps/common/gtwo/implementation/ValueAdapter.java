/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.util.HashCalc;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueAdapter
/*    */   implements Value, Serializable, Denotation, Comparable
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(ValueAdapter.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private Object adaptee;
/*    */   
/*    */   public ValueAdapter(Object object) {
/* 23 */     this.adaptee = object;
/*    */   }
/*    */   
/*    */   public Object getAdaptee() {
/* 27 */     return this.adaptee;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 31 */     int retValue = Value.class.hashCode();
/* 32 */     retValue = HashCalc.addHashCode(retValue, getAdaptee());
/* 33 */     return retValue;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 37 */     if (getAdaptee() instanceof Denotation) {
/* 38 */       return ((Denotation)getAdaptee()).getDenotation(locale);
/*    */     }
/* 40 */     return String.valueOf(getAdaptee());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return String.valueOf(getAdaptee());
/*    */   }
/*    */   
/*    */   public int compareTo(Object o) {
/* 49 */     int retValue = 0;
/*    */     
/* 51 */     try { ValueAdapter valueAdapter = (ValueAdapter)o;
/* 52 */       Comparable<Object> c1 = (Comparable)getAdaptee();
/* 53 */       Object obj = valueAdapter.getAdaptee();
/* 54 */       retValue = c1.compareTo(obj); }
/* 55 */     catch (ClassCastException e) {  }
/* 56 */     catch (Exception e)
/* 57 */     { log.warn("unable to compare " + String.valueOf(this) + " to " + String.valueOf(o) + " - exception:" + e); }
/*    */     
/* 59 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ValueAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */