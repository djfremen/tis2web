/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBAdapter;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ public class TSBFilter_AssemblyGroup
/*    */   extends TSBFilter
/*    */ {
/*    */   protected AssemblyGroup assemblyGroup;
/* 28 */   protected Map gt2scds = AssemblyGroup.getGT2SCDS();
/*    */   private Collection resolvedGroups;
/*    */   
/*    */   public TSBFilter_AssemblyGroup(AssemblyGroup assemblyGroup) {
/* 32 */     this.assemblyGroup = assemblyGroup;
/* 33 */     this.resolvedGroups = resolve(assemblyGroup);
/*    */   }
/*    */   
/*    */   private Collection resolve(AssemblyGroup ag) {
/* 37 */     if (this.gt2scds == null)
/* 38 */       return Collections.singleton(ag); 
/* 39 */     List<AssemblyGroup> scds_groups = new LinkedList();
/* 40 */     scds_groups.add(ag);
/* 41 */     List<? extends AssemblyGroup> scsds = (List)this.gt2scds.get(ag);
/* 42 */     if (scsds != null)
/* 43 */       scds_groups.addAll(scsds); 
/* 44 */     return scds_groups;
/*    */   }
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 49 */       boolean match = false;
/* 50 */       Iterator it = this.resolvedGroups.iterator();
/*    */       
/* 52 */       while (it.hasNext()) {
/* 53 */         Object ag = it.next();
/* 54 */         match = TSBAdapter.matchAssemblyGroup(tsb, (AssemblyGroup)ag);
/* 55 */         if (match)
/*    */           break; 
/*    */       } 
/* 58 */       return match;
/* 59 */     } catch (Exception e) {
/* 60 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public AssemblyGroup getAssemblyGroup() {
/* 65 */     return this.assemblyGroup;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_AssemblyGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */