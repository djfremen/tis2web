/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class PreparedStatementWrapper extends StatementWrapper implements PreparedStatement {
/*     */   private PreparedStatement delegate;
/*     */   
/*     */   public PreparedStatementWrapper(PreparedStatement stmt) {
/*  25 */     super(stmt);
/*  26 */     this.delegate = stmt;
/*     */   }
/*     */   
/*     */   public void addBatch() throws SQLException {
/*  30 */     this.delegate.addBatch();
/*     */   }
/*     */   
/*     */   public void clearParameters() throws SQLException {
/*  34 */     this.delegate.clearParameters();
/*     */   }
/*     */   
/*     */   public boolean execute() throws SQLException {
/*  38 */     return this.delegate.execute();
/*     */   }
/*     */   
/*     */   public ResultSet executeQuery() throws SQLException {
/*  42 */     return this.delegate.executeQuery();
/*     */   }
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*  46 */     return this.delegate.executeUpdate();
/*     */   }
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/*  50 */     return this.delegate.getMetaData();
/*     */   }
/*     */   
/*     */   public ParameterMetaData getParameterMetaData() throws SQLException {
/*  54 */     return this.delegate.getParameterMetaData();
/*     */   }
/*     */   
/*     */   public void setArray(int parameterIndex, Array x) throws SQLException {
/*  58 */     this.delegate.setArray(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/*  62 */     this.delegate.setAsciiStream(parameterIndex, x, length);
/*     */   }
/*     */   
/*     */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/*  66 */     this.delegate.setBigDecimal(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/*  70 */     this.delegate.setBinaryStream(parameterIndex, x, length);
/*     */   }
/*     */   
/*     */   public void setBlob(int parameterIndex, Blob x) throws SQLException {
/*  74 */     this.delegate.setBlob(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/*  78 */     this.delegate.setBoolean(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setByte(int parameterIndex, byte x) throws SQLException {
/*  82 */     this.delegate.setByte(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/*  86 */     this.delegate.setBytes(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/*  90 */     this.delegate.setCharacterStream(parameterIndex, reader, length);
/*     */   }
/*     */   
/*     */   public void setClob(int parameterIndex, Clob x) throws SQLException {
/*  94 */     this.delegate.setClob(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/*  98 */     this.delegate.setDate(parameterIndex, x, cal);
/*     */   }
/*     */   
/*     */   public void setDate(int parameterIndex, Date x) throws SQLException {
/* 102 */     this.delegate.setDate(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setDouble(int parameterIndex, double x) throws SQLException {
/* 106 */     this.delegate.setDouble(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setFloat(int parameterIndex, float x) throws SQLException {
/* 110 */     this.delegate.setFloat(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setInt(int parameterIndex, int x) throws SQLException {
/* 114 */     this.delegate.setInt(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setLong(int parameterIndex, long x) throws SQLException {
/* 118 */     this.delegate.setLong(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
/* 122 */     this.delegate.setNull(parameterIndex, sqlType, typeName);
/*     */   }
/*     */   
/*     */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/* 126 */     this.delegate.setNull(parameterIndex, sqlType);
/*     */   }
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
/* 130 */     this.delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
/*     */   }
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 134 */     this.delegate.setObject(parameterIndex, x, targetSqlType);
/*     */   }
/*     */   
/*     */   public void setObject(int parameterIndex, Object x) throws SQLException {
/* 138 */     this.delegate.setObject(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setRef(int parameterIndex, Ref x) throws SQLException {
/* 142 */     this.delegate.setRef(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setShort(int parameterIndex, short x) throws SQLException {
/* 146 */     this.delegate.setShort(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setString(int parameterIndex, String x) throws SQLException {
/* 150 */     this.delegate.setString(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
/* 154 */     this.delegate.setTime(parameterIndex, x, cal);
/*     */   }
/*     */   
/*     */   public void setTime(int parameterIndex, Time x) throws SQLException {
/* 158 */     this.delegate.setTime(parameterIndex, x);
/*     */   }
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
/* 162 */     this.delegate.setTimestamp(parameterIndex, x, cal);
/*     */   }
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 166 */     this.delegate.setTimestamp(parameterIndex, x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 171 */     this.delegate.setUnicodeStream(parameterIndex, x, length);
/*     */   }
/*     */   
/*     */   public void setURL(int parameterIndex, URL x) throws SQLException {
/* 175 */     this.delegate.setURL(parameterIndex, x);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\PreparedStatementWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */