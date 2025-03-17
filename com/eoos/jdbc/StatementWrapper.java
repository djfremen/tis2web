/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ public class StatementWrapper
/*     */   implements Statement {
/*     */   private Statement delegate;
/*     */   
/*     */   public StatementWrapper(Statement stmt) {
/*  14 */     this.delegate = stmt;
/*     */   }
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/*  18 */     this.delegate.addBatch(sql);
/*     */   }
/*     */   
/*     */   public void cancel() throws SQLException {
/*  22 */     this.delegate.cancel();
/*     */   }
/*     */   
/*     */   public void clearBatch() throws SQLException {
/*  26 */     this.delegate.clearBatch();
/*     */   }
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/*  30 */     this.delegate.clearWarnings();
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/*  34 */     this.delegate.close();
/*     */   }
/*     */   
/*     */   public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
/*  38 */     return this.delegate.execute(sql, autoGeneratedKeys);
/*     */   }
/*     */   
/*     */   public boolean execute(String sql, int[] columnIndexes) throws SQLException {
/*  42 */     return this.delegate.execute(sql, columnIndexes);
/*     */   }
/*     */   
/*     */   public boolean execute(String sql, String[] columnNames) throws SQLException {
/*  46 */     return this.delegate.execute(sql, columnNames);
/*     */   }
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/*  50 */     return this.delegate.execute(sql);
/*     */   }
/*     */   
/*     */   public int[] executeBatch() throws SQLException {
/*  54 */     return this.delegate.executeBatch();
/*     */   }
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/*  58 */     return new ResultSetWrapper(this.delegate.executeQuery(sql));
/*     */   }
/*     */   
/*     */   public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
/*  62 */     return this.delegate.executeUpdate(sql, autoGeneratedKeys);
/*     */   }
/*     */   
/*     */   public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
/*  66 */     return this.delegate.executeUpdate(sql, columnIndexes);
/*     */   }
/*     */   
/*     */   public int executeUpdate(String sql, String[] columnNames) throws SQLException {
/*  70 */     return this.delegate.executeUpdate(sql, columnNames);
/*     */   }
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/*  74 */     return this.delegate.executeUpdate(sql);
/*     */   }
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*  78 */     return this.delegate.getConnection();
/*     */   }
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/*  82 */     return this.delegate.getFetchDirection();
/*     */   }
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/*  86 */     return this.delegate.getFetchSize();
/*     */   }
/*     */   
/*     */   public ResultSet getGeneratedKeys() throws SQLException {
/*  90 */     return this.delegate.getGeneratedKeys();
/*     */   }
/*     */   
/*     */   public int getMaxFieldSize() throws SQLException {
/*  94 */     return this.delegate.getMaxFieldSize();
/*     */   }
/*     */   
/*     */   public int getMaxRows() throws SQLException {
/*  98 */     return this.delegate.getMaxRows();
/*     */   }
/*     */   
/*     */   public boolean getMoreResults() throws SQLException {
/* 102 */     return this.delegate.getMoreResults();
/*     */   }
/*     */   
/*     */   public boolean getMoreResults(int current) throws SQLException {
/* 106 */     return this.delegate.getMoreResults(current);
/*     */   }
/*     */   
/*     */   public int getQueryTimeout() throws SQLException {
/* 110 */     return this.delegate.getQueryTimeout();
/*     */   }
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/* 114 */     return this.delegate.getResultSet();
/*     */   }
/*     */   
/*     */   public int getResultSetConcurrency() throws SQLException {
/* 118 */     return this.delegate.getResultSetConcurrency();
/*     */   }
/*     */   
/*     */   public int getResultSetHoldability() throws SQLException {
/* 122 */     return this.delegate.getResultSetHoldability();
/*     */   }
/*     */   
/*     */   public int getResultSetType() throws SQLException {
/* 126 */     return this.delegate.getResultSetType();
/*     */   }
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/* 130 */     return this.delegate.getUpdateCount();
/*     */   }
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 134 */     return this.delegate.getWarnings();
/*     */   }
/*     */   
/*     */   public void setCursorName(String name) throws SQLException {
/* 138 */     this.delegate.setCursorName(name);
/*     */   }
/*     */   
/*     */   public void setEscapeProcessing(boolean enable) throws SQLException {
/* 142 */     this.delegate.setEscapeProcessing(enable);
/*     */   }
/*     */   
/*     */   public void setFetchDirection(int direction) throws SQLException {
/* 146 */     this.delegate.setFetchDirection(direction);
/*     */   }
/*     */   
/*     */   public void setFetchSize(int rows) throws SQLException {
/* 150 */     this.delegate.setFetchSize(rows);
/*     */   }
/*     */   
/*     */   public void setMaxFieldSize(int max) throws SQLException {
/* 154 */     this.delegate.setMaxFieldSize(max);
/*     */   }
/*     */   
/*     */   public void setMaxRows(int max) throws SQLException {
/* 158 */     this.delegate.setMaxRows(max);
/*     */   }
/*     */   
/*     */   public void setQueryTimeout(int seconds) throws SQLException {
/* 162 */     this.delegate.setQueryTimeout(seconds);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\StatementWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */