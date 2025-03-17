/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DBAdapter
/*    */ {
/*    */   public static DBAdapter getInstance(IDatabaseLink dblink) {
/* 18 */     if (dblink.getDBMS() == 2) {
/* 19 */       return DBAdapter_Transbase.getInstance();
/*    */     }
/* 21 */     return DBAdapter_Oracle.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DBAdapter getInstance(Connection connection) {
/*    */     try {
/* 28 */       String driverName = connection.getMetaData().getDriverName();
/* 29 */       if (driverName.toLowerCase(Locale.ENGLISH).indexOf("oracle") != -1) {
/* 30 */         return DBAdapter_Oracle.getInstance();
/*    */       }
/* 32 */       return DBAdapter_Transbase.getInstance();
/*    */     }
/* 34 */     catch (SQLException e) {
/* 35 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public PreparedStatement prepareStatement(Connection connection, String sql) throws SQLException {
/* 41 */     return connection.prepareStatement(sql);
/*    */   }
/*    */   
/*    */   public abstract byte[] getBinary(ResultSet paramResultSet, int paramInt) throws SQLException;
/*    */   
/*    */   public InputStream getBinaryStream(ResultSet rs, int col) throws SQLException {
/* 47 */     return new ByteArrayInputStream(getBinary(rs, col));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DBAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */