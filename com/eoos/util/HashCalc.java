/*    */ package com.eoos.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashCalc
/*    */ {
/*    */   public static int addHashCode(int hashCode, Object object) {
/* 12 */     int retValue = 37 * hashCode;
/* 13 */     retValue += (object != null) ? object.hashCode() : 0;
/* 14 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int addHashCode(int hashCode, long object) {
/* 18 */     int retValue = 37 * hashCode;
/* 19 */     retValue += (int)(object ^ object >>> 32L);
/* 20 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int addHashCode(int hashCode, int object) {
/* 24 */     int retValue = 37 * hashCode;
/* 25 */     retValue += object;
/* 26 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int addHashCode(int hashCode, boolean object) {
/* 30 */     int retValue = 37 * hashCode;
/* 31 */     retValue += object ? 1 : 0;
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int addHashCode(int hashCode, float object) {
/* 36 */     int retValue = 37 * hashCode;
/* 37 */     retValue += Float.floatToIntBits(object);
/* 38 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int addHashCode(int hashCode, double object) {
/* 42 */     long _object = Double.doubleToLongBits(object);
/* 43 */     return addHashCode(hashCode, _object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\HashCalc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */