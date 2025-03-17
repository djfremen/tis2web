/*    */ package com.eoos.gm.tis2web.vc.v2.value;
/*    */ 
/*    */ import com.eoos.scsm.v2.collection.MultiMap;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class NormalizationRegistry
/*    */ {
/* 11 */   private static NormalizationRegistry instance = null;
/*    */   
/* 13 */   private Map map = Collections.synchronizedMap((Map<?, ?>)new MultiMap());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized NormalizationRegistry getInstance() {
/* 20 */     if (instance == null) {
/* 21 */       instance = new NormalizationRegistry();
/*    */     }
/* 23 */     return instance;
/*    */   }
/*    */   
/*    */   public void add(Object normalizedObject, Object originalForm) {
/* 27 */     this.map.put(normalizedObject, originalForm);
/*    */   }
/*    */   
/*    */   public Collection getOriginalForms(Object normalizedObject) {
/* 31 */     Collection ret = (Collection)this.map.get(normalizedObject);
/* 32 */     if (ret == null) {
/* 33 */       ret = Collections.EMPTY_SET;
/*    */     }
/* 35 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\value\NormalizationRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */