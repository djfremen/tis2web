/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SPSOptionGroup extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*  8 */   List members = new ArrayList();
/*    */   
/*    */   public List getMembers() {
/* 11 */     return this.members;
/*    */   }
/*    */   
/*    */   public SPSOptionGroup(String id, String label, SPSOption option) {
/* 15 */     super(id, label, option);
/*    */   }
/*    */   
/*    */   public void add(SPSOption option) {
/* 19 */     for (int i = 0; i < this.members.size(); i++) {
/* 20 */       SPSOption member = this.members.get(i);
/* 21 */       if (member.equals(option)) {
/*    */         return;
/*    */       }
/*    */     } 
/* 25 */     this.members.add(option);
/*    */   }
/*    */   
/*    */   static SPSOptionGroup getOptionGroup(SPSLanguage language, Integer id, SPSSchemaAdapterNAO adapter) {
/* 29 */     SPSOption option = (SPSOption)SPSOption.StaticData.getInstance(adapter).getGroups().get("#" + id);
/* 30 */     if (option != null) {
/* 31 */       return new SPSOptionGroup(id.toString(), option.descriptions.get(language), (SPSOption)option.getType());
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSOptionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */