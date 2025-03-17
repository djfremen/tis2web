/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.List;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class SPSControllerList extends SPSControllerList {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient List options;
/*    */   protected transient SPSSession session;
/*    */   
/*    */   public SPSControllerList(SPSSession session) {
/* 17 */     this.session = session;
/*    */   }
/*    */   
/*    */   void insert(SPSController controller, SPSSchemaAdapterGME adapter) {
/* 21 */     for (int i = 0; i < size(); i++) {
/* 22 */       SPSControllerReference ref = (SPSControllerReference)get(i);
/* 23 */       if (ref.accept((SPSControllerGME)controller)) {
/* 24 */         ref.add(controller, adapter);
/*    */         return;
/*    */       } 
/*    */     } 
/* 28 */     add(new SPSControllerReference(this.session, controller));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void merge(SPSControllerReference master, SPSControllerReference reference) {
/* 35 */     List controllers = master.getControllers();
/* 36 */     controllers.addAll(reference.getControllers());
/*    */   }
/*    */   
/*    */   public List getOptions() {
/* 40 */     return this.options;
/*    */   }
/*    */   
/*    */   protected void addOption(List options, List categories) {
/* 44 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void qualify(List options, SPSSchemaAdapter adapter) throws Exception {
/* 51 */     this.options = options;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */