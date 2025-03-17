/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SPSHardware extends SPSPart implements Comparable {
/*    */   private static final long serialVersionUID = 1L;
/*  8 */   protected List controllers = new ArrayList();
/*    */   
/*    */   public SPSHardware(Integer ecu, String description) {
/* 11 */     super(ecu.intValue(), description);
/* 12 */     this.controllers.add(ecu);
/*    */   }
/*    */   
/*    */   public void register(Integer ecu) {
/* 16 */     this.controllers.add(ecu);
/*    */   }
/*    */   
/*    */   public boolean match(Integer ecu) {
/* 20 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 21 */       if (ecu.equals(this.controllers.get(i))) {
/* 22 */         return true;
/*    */       }
/*    */     } 
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 29 */     return this.description.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 33 */     return (object != null && object instanceof SPSHardware && ((SPSHardware)object).getDescription().equals(this.description));
/*    */   }
/*    */   
/*    */   public int compareTo(Object object) {
/* 37 */     return (object instanceof SPSHardware) ? this.description.compareTo(((SPSHardware)object).getDescription()) : 0;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSHardware.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */