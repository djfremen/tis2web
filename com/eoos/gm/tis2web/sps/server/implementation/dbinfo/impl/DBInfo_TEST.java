/*    */ package com.eoos.gm.tis2web.sps.server.implementation.dbinfo.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBInfo_TEST
/*    */   implements DatabaseInfo
/*    */ {
/* 13 */   private List tables = new LinkedList();
/*    */ 
/*    */   
/*    */   public DBInfo_TEST() {
/* 17 */     final Object[][] content1 = { { "RPO_LABEL_ID", "RPO_CODE" }, { "20", "+F55" }, { "20", "-F55" } };
/* 18 */     final Object[][] content2 = { { "RPO_CODE", "Description" }, { "+F55", "With Magnetic Ride Control (RPO F55)" }, { "-F55", "Without MagneticRide Control " } };
/*    */     
/* 20 */     this.tables.add(new TableImplAdapter() {
/*    */           public String getTitle() {
/* 22 */             return "Option_List Table";
/*    */           }
/*    */           
/*    */           public int getRowCount() {
/* 26 */             return content1.length;
/*    */           }
/*    */           
/*    */           public int getColumnCount() {
/* 30 */             return (content1[0]).length;
/*    */           }
/*    */           
/*    */           public Object getContent(int rowIndex, int colIndex) {
/* 34 */             return content1[rowIndex][colIndex];
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 39 */     this.tables.add(new TableImplAdapter() {
/*    */           public String getTitle() {
/* 41 */             return "RPO_Description Table";
/*    */           }
/*    */           
/*    */           public int getRowCount() {
/* 45 */             return content2.length;
/*    */           }
/*    */           
/*    */           public int getColumnCount() {
/* 49 */             return (content2[0]).length;
/*    */           }
/*    */           
/*    */           public Object getContent(int rowIndex, int colIndex) {
/* 53 */             return content2[rowIndex][colIndex];
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 61 */     return "Test Data";
/*    */   }
/*    */   
/*    */   public String getVIN() {
/* 65 */     return "1G6KY54904U!!!TEST!!!";
/*    */   }
/*    */   
/*    */   public List getTables() {
/* 69 */     return this.tables;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\dbinfo\impl\DBInfo_TEST.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */