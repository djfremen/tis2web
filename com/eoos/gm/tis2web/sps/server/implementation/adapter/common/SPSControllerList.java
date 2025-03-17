/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class SPSControllerList
/*    */   extends ArrayList
/*    */   implements Value {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SPSControllerReference getControllerReference(int i) {
/* 13 */     return (SPSControllerReference)get(i);
/*    */   }
/*    */   
/*    */   public abstract List getOptions();
/*    */   
/*    */   public void update(List options, SPSSchemaAdapter adapter) throws Exception {
/* 19 */     qualify(options, adapter);
/*    */   }
/*    */   
/*    */   protected abstract void qualify(List paramList, SPSSchemaAdapter paramSPSSchemaAdapter) throws Exception;
/*    */   
/*    */   protected List getPreSelectionOptions() throws Exception {
/* 25 */     List options = new ArrayList();
/* 26 */     for (int i = 0; i < size(); i++) {
/* 27 */       SPSControllerReference ref = (SPSControllerReference)get(i);
/* 28 */       List pre = ref.getPreSelectionOptions();
/* 29 */       if (pre != null) {
/* 30 */         addOption(options, pre);
/*    */       }
/*    */     } 
/* 33 */     return (options.size() > 0) ? options : null;
/*    */   }
/*    */   
/*    */   protected void addOption(List options, List criteria) {
/* 37 */     if (criteria != null)
/* 38 */       for (int i = 0; i < criteria.size(); i++) {
/* 39 */         if (!options.contains(criteria.get(i)))
/* 40 */           options.add(criteria.get(i)); 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSControllerList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */