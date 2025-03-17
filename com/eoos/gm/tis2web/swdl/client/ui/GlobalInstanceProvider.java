/*    */ package com.eoos.gm.tis2web.swdl.client.ui;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlobalInstanceProvider
/*    */ {
/* 15 */   private static Map mapInstances = new HashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized Object getInstance(String strIdentifier) {
/* 27 */     return mapInstances.get(strIdentifier);
/*    */   }
/*    */   
/*    */   public static synchronized Object getInstance(String strIdentifier, String strClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/* 31 */     Object objTmp = mapInstances.get(strIdentifier);
/* 32 */     if (objTmp == null) {
/* 33 */       objTmp = Class.forName(strClassName).newInstance();
/* 34 */       mapInstances.put(strIdentifier, objTmp);
/*    */     } 
/* 36 */     return objTmp;
/*    */   }
/*    */   
/*    */   public static synchronized Object getInstance(String strIdentifier, String strClassName, Object[] aConstructorParameters) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
/* 40 */     Object objTmp = mapInstances.get(strIdentifier);
/* 41 */     if (objTmp == null) {
/*    */ 
/*    */       
/* 44 */       Class[] aParameterTypes = new Class[aConstructorParameters.length];
/* 45 */       for (int i = 0; i < aParameterTypes.length; i++) {
/* 46 */         aParameterTypes[i] = aConstructorParameters[i].getClass();
/*    */       }
/* 48 */       Constructor<?> constructor = Class.forName(strClassName).getConstructor(aParameterTypes);
/*    */ 
/*    */       
/* 51 */       objTmp = constructor.newInstance(aConstructorParameters);
/*    */ 
/*    */       
/* 54 */       mapInstances.put(strIdentifier, objTmp);
/*    */     } 
/* 56 */     return objTmp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void registerInstance(String strIdentifier, Object objInstance) {
/* 68 */     mapInstances.put(strIdentifier, objInstance);
/*    */   }
/*    */   
/*    */   public static synchronized void unregisterInstance(String strIdentifier) {
/* 72 */     mapInstances.remove(strIdentifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\GlobalInstanceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */