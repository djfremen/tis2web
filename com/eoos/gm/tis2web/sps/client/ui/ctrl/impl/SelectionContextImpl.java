/*    */ package com.eoos.gm.tis2web.sps.client.ui.ctrl.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.ctrl.SelectionContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionContextImpl
/*    */   implements SelectionContext
/*    */ {
/* 18 */   private Map selectionsMap = new HashMap<Object, Object>();
/*    */   
/* 20 */   private static SelectionContextImpl instance = null;
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
/*    */   
/*    */   public static synchronized SelectionContextImpl getInstance() {
/* 33 */     if (instance == null) {
/* 34 */       instance = new SelectionContextImpl();
/*    */     }
/*    */     
/* 37 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(Attribute key, DisplayableValue value) {
/* 44 */     this.selectionsMap.put(key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(Attribute key) {
/* 52 */     this.selectionsMap.remove(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DisplayableValue getValue(Attribute key) {
/* 61 */     return (DisplayableValue)this.selectionsMap.get(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\ctrl\impl\SelectionContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */