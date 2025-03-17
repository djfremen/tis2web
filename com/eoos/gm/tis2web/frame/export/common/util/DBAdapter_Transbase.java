/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class DBAdapter_Transbase
/*    */   extends DBAdapter {
/*  8 */   private static DBAdapter_Transbase instance = new DBAdapter_Transbase();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DBAdapter_Transbase getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] getBinary(ResultSet rs, int col) throws SQLException {
/* 19 */     return rs.getBytes(col);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DBAdapter_Transbase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */