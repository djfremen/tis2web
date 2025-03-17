/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SPSOptionHWO
/*    */   extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient String name;
/*    */   protected transient Map hwlocations;
/*    */   
/*    */   SPSOptionHWO(Integer id, String name) {
/* 12 */     super(id);
/* 13 */     this.name = name;
/* 14 */     this.descriptions = null;
/*    */   }
/*    */   
/*    */   String getHWName() {
/* 18 */     return this.name;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 22 */     return this.id + ":" + this.name;
/*    */   }
/*    */   
/*    */   public void setHWLocations(Map hwlocations) {
/* 26 */     this.hwlocations = hwlocations;
/*    */   }
/*    */   
/*    */   public Map getHWLocations() {
/* 30 */     return this.hwlocations;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionHWO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */