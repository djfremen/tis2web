/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class DBMS
/*     */ {
/*  24 */   protected static Logger log = Logger.getLogger(DBMS.class);
/*     */   
/*     */   protected static boolean logging = false;
/*     */   
/*     */   public static void enableLogging() {
/*  29 */     logging = true;
/*     */   }
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query, int lparams) {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public static String toString(char c) {
/*  41 */     return new String(new char[] { c });
/*     */   }
/*     */   
/*     */   public static PreparedStatement prepareSQLStatement(Connection conn, String sql) throws Exception {
/*  45 */     java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
/*  46 */     if (logging) {
/*  47 */       log.debug(sql);
/*     */     }
/*  49 */     return new PreparedStatement(stmt);
/*     */   }
/*     */   
/*     */   public static class PreparedStatement {
/*     */     protected java.sql.PreparedStatement stmt;
/*     */     
/*     */     public PreparedStatement(java.sql.PreparedStatement stmt) {
/*  56 */       this.stmt = stmt;
/*     */     }
/*     */     
/*     */     public int executeUpdate() throws SQLException {
/*  60 */       return this.stmt.executeUpdate();
/*     */     }
/*     */     
/*     */     public void addBatch() throws SQLException {
/*  64 */       this.stmt.addBatch();
/*     */     }
/*     */     
/*     */     public void clearParameters() throws SQLException {
/*  68 */       this.stmt.clearParameters();
/*     */     }
/*     */     
/*     */     public boolean execute() throws SQLException {
/*  72 */       return this.stmt.execute();
/*     */     }
/*     */     
/*     */     public void setByte(int parameterIndex, byte x) throws SQLException {
/*  76 */       if (DBMS.logging) {
/*  77 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/*  79 */       this.stmt.setByte(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setDouble(int parameterIndex, double x) throws SQLException {
/*  83 */       if (DBMS.logging) {
/*  84 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/*  86 */       this.stmt.setDouble(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setFloat(int parameterIndex, float x) throws SQLException {
/*  90 */       if (DBMS.logging) {
/*  91 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/*  93 */       this.stmt.setFloat(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setInt(int parameterIndex, int x) throws SQLException {
/*  97 */       if (DBMS.logging) {
/*  98 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 100 */       this.stmt.setInt(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setNull(int parameterIndex, int sqlType) throws SQLException {
/* 104 */       if (DBMS.logging) {
/* 105 */         DBMS.log.debug("set parameter " + parameterIndex + ": 'null'");
/*     */       }
/* 107 */       this.stmt.setNull(parameterIndex, sqlType);
/*     */     }
/*     */     
/*     */     public void setLong(int parameterIndex, long x) throws SQLException {
/* 111 */       if (DBMS.logging) {
/* 112 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 114 */       this.stmt.setLong(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setShort(int parameterIndex, short x) throws SQLException {
/* 118 */       if (DBMS.logging) {
/* 119 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 121 */       this.stmt.setShort(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/* 125 */       if (DBMS.logging) {
/* 126 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 128 */       this.stmt.setBoolean(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/* 132 */       this.stmt.setBytes(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 136 */       this.stmt.setAsciiStream(parameterIndex, x, length);
/*     */     }
/*     */     
/*     */     public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 140 */       this.stmt.setBinaryStream(parameterIndex, x, length);
/*     */     }
/*     */     
/*     */     public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/* 144 */       this.stmt.setCharacterStream(parameterIndex, reader, length);
/*     */     }
/*     */     
/*     */     public void setObject(int parameterIndex, Object x) throws SQLException {
/* 148 */       this.stmt.setObject(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 152 */       this.stmt.setObject(parameterIndex, x, targetSqlType);
/*     */     }
/*     */     
/*     */     public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
/* 156 */       this.stmt.setObject(parameterIndex, x, targetSqlType, scale);
/*     */     }
/*     */     
/*     */     public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
/* 160 */       this.stmt.setNull(paramIndex, sqlType, typeName);
/*     */     }
/*     */     
/*     */     public void setString(int parameterIndex, String x) throws SQLException {
/* 164 */       if (DBMS.logging) {
/* 165 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 167 */       this.stmt.setString(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/* 171 */       if (DBMS.logging) {
/* 172 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 174 */       this.stmt.setBigDecimal(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setArray(int i, Array x) throws SQLException {
/* 178 */       this.stmt.setArray(i, x);
/*     */     }
/*     */     
/*     */     public void setBlob(int i, Blob x) throws SQLException {
/* 182 */       this.stmt.setBlob(i, x);
/*     */     }
/*     */     
/*     */     public void setClob(int i, Clob x) throws SQLException {
/* 186 */       this.stmt.setClob(i, x);
/*     */     }
/*     */     
/*     */     public void setDate(int parameterIndex, Date x) throws SQLException {
/* 190 */       if (DBMS.logging) {
/* 191 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 193 */       this.stmt.setDate(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public ResultSet executeQuery() throws SQLException {
/* 197 */       return this.stmt.executeQuery();
/*     */     }
/*     */     
/*     */     public ResultSetMetaData getMetaData() throws SQLException {
/* 201 */       return this.stmt.getMetaData();
/*     */     }
/*     */     
/*     */     public void setTime(int parameterIndex, Time x) throws SQLException {
/* 205 */       if (DBMS.logging) {
/* 206 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 208 */       this.stmt.setTime(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 212 */       if (DBMS.logging) {
/* 213 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 215 */       this.stmt.setTimestamp(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/* 219 */       if (DBMS.logging) {
/* 220 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 222 */       this.stmt.setDate(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
/* 226 */       if (DBMS.logging) {
/* 227 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 229 */       this.stmt.setTime(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
/* 233 */       if (DBMS.logging) {
/* 234 */         DBMS.log.debug("set parameter " + parameterIndex + ": '" + x + "'");
/*     */       }
/* 236 */       this.stmt.setTimestamp(parameterIndex, x);
/*     */     }
/*     */     
/*     */     public int getFetchDirection() throws SQLException {
/* 240 */       return this.stmt.getFetchDirection();
/*     */     }
/*     */     
/*     */     public int getFetchSize() throws SQLException {
/* 244 */       return this.stmt.getFetchSize();
/*     */     }
/*     */     
/*     */     public int getMaxFieldSize() throws SQLException {
/* 248 */       return this.stmt.getMaxFieldSize();
/*     */     }
/*     */     
/*     */     public int getMaxRows() throws SQLException {
/* 252 */       return this.stmt.getMaxRows();
/*     */     }
/*     */     
/*     */     public int getQueryTimeout() throws SQLException {
/* 256 */       return this.stmt.getQueryTimeout();
/*     */     }
/*     */     
/*     */     public int getResultSetConcurrency() throws SQLException {
/* 260 */       return this.stmt.getResultSetConcurrency();
/*     */     }
/*     */     
/*     */     public int getResultSetType() throws SQLException {
/* 264 */       return this.stmt.getResultSetType();
/*     */     }
/*     */     
/*     */     public int getUpdateCount() throws SQLException {
/* 268 */       return this.stmt.getUpdateCount();
/*     */     }
/*     */     
/*     */     public void cancel() throws SQLException {
/* 272 */       this.stmt.cancel();
/*     */     }
/*     */     
/*     */     public void clearBatch() throws SQLException {
/* 276 */       this.stmt.clearBatch();
/*     */     }
/*     */     
/*     */     public void clearWarnings() throws SQLException {
/* 280 */       this.stmt.clearWarnings();
/*     */     }
/*     */     
/*     */     public void close() throws SQLException {
/* 284 */       this.stmt.close();
/*     */     }
/*     */     
/*     */     public boolean getMoreResults() throws SQLException {
/* 288 */       return this.stmt.getMoreResults();
/*     */     }
/*     */     
/*     */     public int[] executeBatch() throws SQLException {
/* 292 */       return this.stmt.executeBatch();
/*     */     }
/*     */     
/*     */     public void setFetchDirection(int direction) throws SQLException {
/* 296 */       this.stmt.setFetchDirection(direction);
/*     */     }
/*     */     
/*     */     public void setFetchSize(int rows) throws SQLException {
/* 300 */       this.stmt.setFetchSize(rows);
/*     */     }
/*     */     
/*     */     public void setMaxFieldSize(int max) throws SQLException {
/* 304 */       this.stmt.setMaxFieldSize(max);
/*     */     }
/*     */     
/*     */     public void setMaxRows(int max) throws SQLException {
/* 308 */       this.stmt.setMaxRows(max);
/*     */     }
/*     */     
/*     */     public void setQueryTimeout(int seconds) throws SQLException {
/* 312 */       this.stmt.setQueryTimeout(seconds);
/*     */     }
/*     */     
/*     */     public void setEscapeProcessing(boolean enable) throws SQLException {
/* 316 */       this.stmt.setEscapeProcessing(enable);
/*     */     }
/*     */     
/*     */     public int executeUpdate(String sql) throws SQLException {
/* 320 */       return this.stmt.executeUpdate(sql);
/*     */     }
/*     */     
/*     */     public void addBatch(String sql) throws SQLException {
/* 324 */       this.stmt.addBatch(sql);
/*     */     }
/*     */     
/*     */     public void setCursorName(String name) throws SQLException {
/* 328 */       this.stmt.setCursorName(name);
/*     */     }
/*     */     
/*     */     public boolean execute(String sql) throws SQLException {
/* 332 */       return this.stmt.execute(sql);
/*     */     }
/*     */     
/*     */     public Connection getConnection() throws SQLException {
/* 336 */       return this.stmt.getConnection();
/*     */     }
/*     */     
/*     */     public ResultSet getResultSet() throws SQLException {
/* 340 */       return this.stmt.getResultSet();
/*     */     }
/*     */     
/*     */     public SQLWarning getWarnings() throws SQLException {
/* 344 */       return this.stmt.getWarnings();
/*     */     }
/*     */     
/*     */     public ResultSet executeQuery(String sql) throws SQLException {
/* 348 */       return this.stmt.executeQuery(sql);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */