/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingType;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSProgrammingType
/*    */   implements ProgrammingType
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 12 */   public static final Integer NORMAL = Integer.valueOf(1);
/*    */   
/* 14 */   public static final Integer VCI = Integer.valueOf(2);
/*    */   
/* 16 */   public static final Integer FILE = Integer.valueOf(4);
/*    */   
/* 18 */   public static final Integer RECONFIGURE = Integer.valueOf(8);
/*    */   
/*    */   protected Integer id;
/*    */   
/*    */   protected String description;
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 25 */     return this.description;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     return this.description;
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 37 */     return this.description;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 41 */     return this.id.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 45 */     return (object != null && object instanceof SPSProgrammingType && ((SPSProgrammingType)object).getID().equals(this.id));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSProgrammingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */