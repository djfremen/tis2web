/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Blob;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class DBAdapter_Oracle
/*    */   extends DBAdapter
/*    */ {
/* 13 */   private static DBAdapter_Oracle instance = new DBAdapter_Oracle();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DBAdapter_Oracle getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] getBinary(ResultSet rs, int col) throws SQLException {
/* 24 */     InputStream is = getBinaryStream(rs, col);
/*    */     try {
/* 26 */       return StreamUtil.readFully(is);
/* 27 */     } catch (IOException e) {
/* 28 */       throw new SQLException(e.getMessage());
/*    */     } finally {
/*    */       try {
/* 31 */         is.close();
/* 32 */       } catch (IOException e) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getBinaryStream(ResultSet rs, int col) throws SQLException {
/* 38 */     Blob blob = rs.getBlob(col);
/* 39 */     return blob.getBinaryStream();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DBAdapter_Oracle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */