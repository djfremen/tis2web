/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SPSOptionGroup
/*    */   extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*  9 */   List members = new ArrayList();
/*    */   
/*    */   public List getMembers() {
/* 12 */     return this.members;
/*    */   }
/*    */   
/*    */   public SPSOptionGroup(String id, String label, SPSOption option) {
/* 16 */     super(id, label, option);
/*    */   }
/*    */   
/*    */   public void add(SPSOption option) {
/* 20 */     for (int i = 0; i < this.members.size(); i++) {
/* 21 */       SPSOption member = this.members.get(i);
/* 22 */       if (member.equals(option)) {
/*    */         return;
/*    */       }
/*    */     } 
/* 26 */     this.members.add(option);
/*    */   }
/*    */   
/*    */   static SPSOptionGroup getOptionGroup(SPSLanguage language, Integer id, SPSSchemaAdapterGlobal adapter) {
/* 30 */     SPSOption option = (SPSOption)SPSOption.StaticData.getInstance(adapter).getGroups().get("#" + id);
/* 31 */     if (option != null) {
/* 32 */       return new SPSOptionGroup(id.toString(), option.descriptions.get(language), (SPSOption)option.getType());
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSOptionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */