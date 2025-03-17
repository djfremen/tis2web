/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class SCASKAResultImpl
/*    */   implements SCASKAResult, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3258132453284394034L;
/*    */   private boolean statusSKA = false;
/*    */   private boolean statusSCA = false;
/*    */   
/*    */   public SCASKAResultImpl() {
/* 14 */     this.statusSKA = false;
/* 15 */     this.statusSCA = false;
/*    */   }
/*    */   
/*    */   public void setStatusSKA(boolean statusSKA) {
/* 19 */     this.statusSKA = statusSKA;
/*    */   }
/*    */   
/*    */   public boolean getStatusSKA() {
/* 23 */     return this.statusSKA;
/*    */   }
/*    */   
/*    */   public void setStatusSCA(boolean statusSCA) {
/* 27 */     this.statusSCA = statusSCA;
/*    */   }
/*    */   
/*    */   public boolean getStatusSCA() {
/* 31 */     return this.statusSCA;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\ssadata\SCASKAResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */