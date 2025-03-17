/*    */ package com.eoos.gm.tis2web.sps.server.implementation.dbinfo.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatabaseInfoImpl
/*    */   implements DatabaseInfo
/*    */ {
/* 13 */   public String title = null;
/* 14 */   public String vin = null;
/* 15 */   public List tables = new LinkedList();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 21 */     return this.title;
/*    */   }
/*    */   
/*    */   public String getVIN() {
/* 25 */     return this.vin;
/*    */   }
/*    */   
/*    */   public List getTables() {
/* 29 */     return this.tables;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\dbinfo\impl\DatabaseInfoImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */