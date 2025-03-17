/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassUtil
/*    */ {
/*    */   public static Collection getAllInterfaces(Class clazz) {
/* 17 */     Collection<Class<?>> retValue = new HashSet();
/*    */     
/* 19 */     for (Iterator<Class<?>> iter = Arrays.<Class<?>>asList(clazz.getInterfaces()).iterator(); iter.hasNext(); ) {
/* 20 */       Class<?> c = iter.next();
/* 21 */       retValue.add(c);
/* 22 */       retValue.addAll(getAllInterfaces(c));
/*    */     } 
/*    */     
/* 25 */     if (clazz.getSuperclass() != null) {
/* 26 */       retValue.addAll(getAllInterfaces(clazz.getSuperclass()));
/*    */     }
/* 28 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\ClassUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */