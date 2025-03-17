/*    */ package com.eoos.gm.tis2web.sps.server.implementation.dbinfo.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TableImplAdapter
/*    */   implements DatabaseInfo.Table
/*    */ {
/* 10 */   public String title = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 17 */     return this.title;
/*    */   }
/*    */   
/*    */   public boolean isHeader(int rowIndex, int colIndex) {
/* 21 */     return (rowIndex == 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\dbinfo\impl\TableImplAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */