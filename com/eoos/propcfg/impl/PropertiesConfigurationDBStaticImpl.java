/*    */ package com.eoos.propcfg.impl;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class PropertiesConfigurationDBStaticImpl extends PropertiesConfigurationImpl {
/*    */   public static interface Callback { Connection getConnection();
/*    */     
/*    */     void releaseConnection(Connection param1Connection);
/*    */     
/*    */     String getTableName();
/*    */     
/*    */     String getColumnName(Column param1Column);
/*    */     
/* 18 */     public static final class Column { public static final Column KEY = new Column();
/* 19 */       public static final Column VALUE = new Column(); } } public static final class Column { public static final Column VALUE = new Column();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static final Column KEY = new Column(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertiesConfigurationDBStaticImpl(Properties properties) {
/* 35 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertiesConfigurationDBStaticImpl create(Callback callback) throws SQLException {
/* 40 */     Connection connection = callback.getConnection();
/*    */     try {
/* 42 */       Properties properties = new Properties();
/* 43 */       PreparedStatement stmt = connection.prepareStatement("SELECT " + callback.getColumnName(Callback.Column.KEY) + ", " + callback.getColumnName(Callback.Column.VALUE) + " FROM " + callback.getTableName());
/* 44 */       ResultSet rs = stmt.executeQuery();
/* 45 */       while (rs.next()) {
/* 46 */         String key = rs.getString(callback.getColumnName(Callback.Column.KEY));
/* 47 */         String value = Util.toThreadLocalMultiton(rs.getString(callback.getColumnName(Callback.Column.VALUE)));
/* 48 */         if (value == null) {
/* 49 */           value = "";
/*    */         }
/* 51 */         properties.put(key, value);
/*    */       } 
/* 53 */       return new PropertiesConfigurationDBStaticImpl(properties);
/*    */     } finally {
/* 55 */       callback.releaseConnection(connection);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcfg\impl\PropertiesConfigurationDBStaticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */