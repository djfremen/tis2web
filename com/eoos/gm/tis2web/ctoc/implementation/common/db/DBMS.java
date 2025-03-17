/*    */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import java.sql.Connection;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class DBMS
/*    */ {
/*    */   protected Connection dbConnection;
/*    */   
/*    */   public Connection getConnection() {
/* 13 */     return this.dbConnection;
/*    */   }
/*    */   
/*    */   public DBMS(Connection db) {
/* 17 */     this.dbConnection = db;
/*    */   }
/*    */   
/*    */   public String getSQL(String sql) {
/* 21 */     return sql;
/*    */   }
/*    */   
/*    */   public static boolean isOracleDB(IDatabaseLink poolDB, String url) {
/* 25 */     if (poolDB != null) {
/* 26 */       return (poolDB.getDBMS() == 1);
/*    */     }
/* 28 */     return (url.toLowerCase(Locale.ENGLISH).indexOf("oracle") >= 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */