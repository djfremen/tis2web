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
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ResultSetWrapper
/*     */   implements ResultSet {
/*     */   public boolean absolute(int row) throws SQLException {
/*  25 */     return this.delegate.absolute(row);
/*     */   }
/*     */   private ResultSet delegate;
/*     */   public void afterLast() throws SQLException {
/*  29 */     this.delegate.afterLast();
/*     */   }
/*     */   
/*     */   public void beforeFirst() throws SQLException {
/*  33 */     this.delegate.beforeFirst();
/*     */   }
/*     */   
/*     */   public void cancelRowUpdates() throws SQLException {
/*  37 */     this.delegate.cancelRowUpdates();
/*     */   }
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/*  41 */     this.delegate.clearWarnings();
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/*  45 */     this.delegate.close();
/*     */   }
/*     */   
/*     */   public void deleteRow() throws SQLException {
/*  49 */     this.delegate.deleteRow();
/*     */   }
/*     */   
/*     */   public int findColumn(String columnName) throws SQLException {
/*  53 */     return this.delegate.findColumn(columnName);
/*     */   }
/*     */   
/*     */   public boolean first() throws SQLException {
/*  57 */     return this.delegate.first();
/*     */   }
/*     */   
/*     */   public Array getArray(int i) throws SQLException {
/*  61 */     return this.delegate.getArray(i);
/*     */   }
/*     */   
/*     */   public Array getArray(String colName) throws SQLException {
/*  65 */     return this.delegate.getArray(colName);
/*     */   }
/*     */   
/*     */   public InputStream getAsciiStream(int columnIndex) throws SQLException {
/*  69 */     return this.delegate.getAsciiStream(columnIndex);
/*     */   }
/*     */   
/*     */   public InputStream getAsciiStream(String columnName) throws SQLException {
/*  73 */     return this.delegate.getAsciiStream(columnName);
/*     */   }
/*     */ 
/*     */   
/*     */   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
/*  78 */     return this.delegate.getBigDecimal(columnIndex, scale);
/*     */   }
/*     */   
/*     */   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
/*  82 */     return this.delegate.getBigDecimal(columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
/*  87 */     return this.delegate.getBigDecimal(columnName, scale);
/*     */   }
/*     */   
/*     */   public BigDecimal getBigDecimal(String columnName) throws SQLException {
/*  91 */     return this.delegate.getBigDecimal(columnName);
/*     */   }
/*     */   
/*     */   public InputStream getBinaryStream(int columnIndex) throws SQLException {
/*  95 */     return this.delegate.getBinaryStream(columnIndex);
/*     */   }
/*     */   
/*     */   public InputStream getBinaryStream(String columnName) throws SQLException {
/*  99 */     return this.delegate.getBinaryStream(columnName);
/*     */   }
/*     */   
/*     */   public Blob getBlob(int i) throws SQLException {
/* 103 */     return this.delegate.getBlob(i);
/*     */   }
/*     */   
/*     */   public Blob getBlob(String colName) throws SQLException {
/* 107 */     return this.delegate.getBlob(colName);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int columnIndex) throws SQLException {
/* 111 */     return this.delegate.getBoolean(columnIndex);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String columnName) throws SQLException {
/* 115 */     return this.delegate.getBoolean(columnName);
/*     */   }
/*     */   
/*     */   public byte getByte(int columnIndex) throws SQLException {
/* 119 */     return this.delegate.getByte(columnIndex);
/*     */   }
/*     */   
/*     */   public byte getByte(String columnName) throws SQLException {
/* 123 */     return this.delegate.getByte(columnName);
/*     */   }
/*     */   
/*     */   public byte[] getBytes(int columnIndex) throws SQLException {
/* 127 */     return this.delegate.getBytes(columnIndex);
/*     */   }
/*     */   
/*     */   public byte[] getBytes(String columnName) throws SQLException {
/* 131 */     return this.delegate.getBytes(columnName);
/*     */   }
/*     */   
/*     */   public Reader getCharacterStream(int columnIndex) throws SQLException {
/* 135 */     return this.delegate.getCharacterStream(columnIndex);
/*     */   }
/*     */   
/*     */   public Reader getCharacterStream(String columnName) throws SQLException {
/* 139 */     return this.delegate.getCharacterStream(columnName);
/*     */   }
/*     */   
/*     */   public Clob getClob(int i) throws SQLException {
/* 143 */     return this.delegate.getClob(i);
/*     */   }
/*     */   
/*     */   public Clob getClob(String colName) throws SQLException {
/* 147 */     return this.delegate.getClob(colName);
/*     */   }
/*     */   
/*     */   public int getConcurrency() throws SQLException {
/* 151 */     return this.delegate.getConcurrency();
/*     */   }
/*     */   
/*     */   public String getCursorName() throws SQLException {
/* 155 */     return this.delegate.getCursorName();
/*     */   }
/*     */   
/*     */   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
/* 159 */     return this.delegate.getDate(columnIndex, cal);
/*     */   }
/*     */   
/*     */   public Date getDate(int columnIndex) throws SQLException {
/* 163 */     return this.delegate.getDate(columnIndex);
/*     */   }
/*     */   
/*     */   public Date getDate(String columnName, Calendar cal) throws SQLException {
/* 167 */     return this.delegate.getDate(columnName, cal);
/*     */   }
/*     */   
/*     */   public Date getDate(String columnName) throws SQLException {
/* 171 */     return this.delegate.getDate(columnName);
/*     */   }
/*     */   
/*     */   public double getDouble(int columnIndex) throws SQLException {
/* 175 */     return this.delegate.getDouble(columnIndex);
/*     */   }
/*     */   
/*     */   public double getDouble(String columnName) throws SQLException {
/* 179 */     return this.delegate.getDouble(columnName);
/*     */   }
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/* 183 */     return this.delegate.getFetchDirection();
/*     */   }
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/* 187 */     return this.delegate.getFetchSize();
/*     */   }
/*     */   
/*     */   public float getFloat(int columnIndex) throws SQLException {
/* 191 */     return this.delegate.getFloat(columnIndex);
/*     */   }
/*     */   
/*     */   public float getFloat(String columnName) throws SQLException {
/* 195 */     return this.delegate.getFloat(columnName);
/*     */   }
/*     */   
/*     */   public int getInt(int columnIndex) throws SQLException {
/* 199 */     return this.delegate.getInt(columnIndex);
/*     */   }
/*     */   
/*     */   public int getInt(String columnName) throws SQLException {
/* 203 */     return this.delegate.getInt(columnName);
/*     */   }
/*     */   
/*     */   public long getLong(int columnIndex) throws SQLException {
/* 207 */     return this.delegate.getLong(columnIndex);
/*     */   }
/*     */   
/*     */   public long getLong(String columnName) throws SQLException {
/* 211 */     return this.delegate.getLong(columnName);
/*     */   }
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/* 215 */     return this.delegate.getMetaData();
/*     */   }
/*     */   
/*     */   public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
/* 219 */     return this.delegate.getObject(i, map);
/*     */   }
/*     */   
/*     */   public Object getObject(int columnIndex) throws SQLException {
/* 223 */     return this.delegate.getObject(columnIndex);
/*     */   }
/*     */   
/*     */   public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
/* 227 */     return this.delegate.getObject(colName, map);
/*     */   }
/*     */   
/*     */   public Object getObject(String columnName) throws SQLException {
/* 231 */     return this.delegate.getObject(columnName);
/*     */   }
/*     */   
/*     */   public Ref getRef(int i) throws SQLException {
/* 235 */     return this.delegate.getRef(i);
/*     */   }
/*     */   
/*     */   public Ref getRef(String colName) throws SQLException {
/* 239 */     return this.delegate.getRef(colName);
/*     */   }
/*     */   
/*     */   public int getRow() throws SQLException {
/* 243 */     return this.delegate.getRow();
/*     */   }
/*     */   
/*     */   public short getShort(int columnIndex) throws SQLException {
/* 247 */     return this.delegate.getShort(columnIndex);
/*     */   }
/*     */   
/*     */   public short getShort(String columnName) throws SQLException {
/* 251 */     return this.delegate.getShort(columnName);
/*     */   }
/*     */   
/*     */   public Statement getStatement() throws SQLException {
/* 255 */     return this.delegate.getStatement();
/*     */   }
/*     */   
/*     */   public String getString(int columnIndex) throws SQLException {
/* 259 */     return this.delegate.getString(columnIndex);
/*     */   }
/*     */   
/*     */   public String getString(String columnName) throws SQLException {
/* 263 */     return this.delegate.getString(columnName);
/*     */   }
/*     */   
/*     */   public Time getTime(int columnIndex, Calendar cal) throws SQLException {
/* 267 */     return this.delegate.getTime(columnIndex, cal);
/*     */   }
/*     */   
/*     */   public Time getTime(int columnIndex) throws SQLException {
/* 271 */     return this.delegate.getTime(columnIndex);
/*     */   }
/*     */   
/*     */   public Time getTime(String columnName, Calendar cal) throws SQLException {
/* 275 */     return this.delegate.getTime(columnName, cal);
/*     */   }
/*     */   
/*     */   public Time getTime(String columnName) throws SQLException {
/* 279 */     return this.delegate.getTime(columnName);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
/* 283 */     return this.delegate.getTimestamp(columnIndex, cal);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(int columnIndex) throws SQLException {
/* 287 */     return this.delegate.getTimestamp(columnIndex);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
/* 291 */     return this.delegate.getTimestamp(columnName, cal);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(String columnName) throws SQLException {
/* 295 */     return this.delegate.getTimestamp(columnName);
/*     */   }
/*     */   
/*     */   public int getType() throws SQLException {
/* 299 */     return this.delegate.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
/* 304 */     return this.delegate.getUnicodeStream(columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getUnicodeStream(String columnName) throws SQLException {
/* 309 */     return this.delegate.getUnicodeStream(columnName);
/*     */   }
/*     */   
/*     */   public URL getURL(int columnIndex) throws SQLException {
/* 313 */     return this.delegate.getURL(columnIndex);
/*     */   }
/*     */   
/*     */   public URL getURL(String columnName) throws SQLException {
/* 317 */     return this.delegate.getURL(columnName);
/*     */   }
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 321 */     return this.delegate.getWarnings();
/*     */   }
/*     */   
/*     */   public void insertRow() throws SQLException {
/* 325 */     this.delegate.insertRow();
/*     */   }
/*     */   
/*     */   public boolean isAfterLast() throws SQLException {
/* 329 */     return this.delegate.isAfterLast();
/*     */   }
/*     */   
/*     */   public boolean isBeforeFirst() throws SQLException {
/* 333 */     return this.delegate.isBeforeFirst();
/*     */   }
/*     */   
/*     */   public boolean isFirst() throws SQLException {
/* 337 */     return this.delegate.isFirst();
/*     */   }
/*     */   
/*     */   public boolean isLast() throws SQLException {
/* 341 */     return this.delegate.isLast();
/*     */   }
/*     */   
/*     */   public boolean last() throws SQLException {
/* 345 */     return this.delegate.last();
/*     */   }
/*     */   
/*     */   public void moveToCurrentRow() throws SQLException {
/* 349 */     this.delegate.moveToCurrentRow();
/*     */   }
/*     */   
/*     */   public void moveToInsertRow() throws SQLException {
/* 353 */     this.delegate.moveToInsertRow();
/*     */   }
/*     */   
/*     */   public boolean next() throws SQLException {
/* 357 */     return this.delegate.next();
/*     */   }
/*     */   
/*     */   public boolean previous() throws SQLException {
/* 361 */     return this.delegate.previous();
/*     */   }
/*     */   
/*     */   public void refreshRow() throws SQLException {
/* 365 */     this.delegate.refreshRow();
/*     */   }
/*     */   
/*     */   public boolean relative(int rows) throws SQLException {
/* 369 */     return this.delegate.relative(rows);
/*     */   }
/*     */   
/*     */   public boolean rowDeleted() throws SQLException {
/* 373 */     return this.delegate.rowDeleted();
/*     */   }
/*     */   
/*     */   public boolean rowInserted() throws SQLException {
/* 377 */     return this.delegate.rowInserted();
/*     */   }
/*     */   
/*     */   public boolean rowUpdated() throws SQLException {
/* 381 */     return this.delegate.rowUpdated();
/*     */   }
/*     */   
/*     */   public void setFetchDirection(int direction) throws SQLException {
/* 385 */     this.delegate.setFetchDirection(direction);
/*     */   }
/*     */   
/*     */   public void setFetchSize(int rows) throws SQLException {
/* 389 */     this.delegate.setFetchSize(rows);
/*     */   }
/*     */   
/*     */   public void updateArray(int columnIndex, Array x) throws SQLException {
/* 393 */     this.delegate.updateArray(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateArray(String columnName, Array x) throws SQLException {
/* 397 */     this.delegate.updateArray(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
/* 401 */     this.delegate.updateAsciiStream(columnIndex, x, length);
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
/* 405 */     this.delegate.updateAsciiStream(columnName, x, length);
/*     */   }
/*     */   
/*     */   public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
/* 409 */     this.delegate.updateBigDecimal(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
/* 413 */     this.delegate.updateBigDecimal(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
/* 417 */     this.delegate.updateBinaryStream(columnIndex, x, length);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
/* 421 */     this.delegate.updateBinaryStream(columnName, x, length);
/*     */   }
/*     */   
/*     */   public void updateBlob(int columnIndex, Blob x) throws SQLException {
/* 425 */     this.delegate.updateBlob(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateBlob(String columnName, Blob x) throws SQLException {
/* 429 */     this.delegate.updateBlob(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateBoolean(int columnIndex, boolean x) throws SQLException {
/* 433 */     this.delegate.updateBoolean(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateBoolean(String columnName, boolean x) throws SQLException {
/* 437 */     this.delegate.updateBoolean(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateByte(int columnIndex, byte x) throws SQLException {
/* 441 */     this.delegate.updateByte(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateByte(String columnName, byte x) throws SQLException {
/* 445 */     this.delegate.updateByte(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateBytes(int columnIndex, byte[] x) throws SQLException {
/* 449 */     this.delegate.updateBytes(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateBytes(String columnName, byte[] x) throws SQLException {
/* 453 */     this.delegate.updateBytes(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
/* 457 */     this.delegate.updateCharacterStream(columnIndex, x, length);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
/* 461 */     this.delegate.updateCharacterStream(columnName, reader, length);
/*     */   }
/*     */   
/*     */   public void updateClob(int columnIndex, Clob x) throws SQLException {
/* 465 */     this.delegate.updateClob(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateClob(String columnName, Clob x) throws SQLException {
/* 469 */     this.delegate.updateClob(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateDate(int columnIndex, Date x) throws SQLException {
/* 473 */     this.delegate.updateDate(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateDate(String columnName, Date x) throws SQLException {
/* 477 */     this.delegate.updateDate(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateDouble(int columnIndex, double x) throws SQLException {
/* 481 */     this.delegate.updateDouble(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateDouble(String columnName, double x) throws SQLException {
/* 485 */     this.delegate.updateDouble(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateFloat(int columnIndex, float x) throws SQLException {
/* 489 */     this.delegate.updateFloat(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateFloat(String columnName, float x) throws SQLException {
/* 493 */     this.delegate.updateFloat(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateInt(int columnIndex, int x) throws SQLException {
/* 497 */     this.delegate.updateInt(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateInt(String columnName, int x) throws SQLException {
/* 501 */     this.delegate.updateInt(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateLong(int columnIndex, long x) throws SQLException {
/* 505 */     this.delegate.updateLong(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateLong(String columnName, long x) throws SQLException {
/* 509 */     this.delegate.updateLong(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateNull(int columnIndex) throws SQLException {
/* 513 */     this.delegate.updateNull(columnIndex);
/*     */   }
/*     */   
/*     */   public void updateNull(String columnName) throws SQLException {
/* 517 */     this.delegate.updateNull(columnName);
/*     */   }
/*     */   
/*     */   public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
/* 521 */     this.delegate.updateObject(columnIndex, x, scale);
/*     */   }
/*     */   
/*     */   public void updateObject(int columnIndex, Object x) throws SQLException {
/* 525 */     this.delegate.updateObject(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateObject(String columnName, Object x, int scale) throws SQLException {
/* 529 */     this.delegate.updateObject(columnName, x, scale);
/*     */   }
/*     */   
/*     */   public void updateObject(String columnName, Object x) throws SQLException {
/* 533 */     this.delegate.updateObject(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateRef(int columnIndex, Ref x) throws SQLException {
/* 537 */     this.delegate.updateRef(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateRef(String columnName, Ref x) throws SQLException {
/* 541 */     this.delegate.updateRef(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateRow() throws SQLException {
/* 545 */     this.delegate.updateRow();
/*     */   }
/*     */   
/*     */   public void updateShort(int columnIndex, short x) throws SQLException {
/* 549 */     this.delegate.updateShort(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateShort(String columnName, short x) throws SQLException {
/* 553 */     this.delegate.updateShort(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateString(int columnIndex, String x) throws SQLException {
/* 557 */     this.delegate.updateString(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateString(String columnName, String x) throws SQLException {
/* 561 */     this.delegate.updateString(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateTime(int columnIndex, Time x) throws SQLException {
/* 565 */     this.delegate.updateTime(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateTime(String columnName, Time x) throws SQLException {
/* 569 */     this.delegate.updateTime(columnName, x);
/*     */   }
/*     */   
/*     */   public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
/* 573 */     this.delegate.updateTimestamp(columnIndex, x);
/*     */   }
/*     */   
/*     */   public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
/* 577 */     this.delegate.updateTimestamp(columnName, x);
/*     */   }
/*     */   
/*     */   public boolean wasNull() throws SQLException {
/* 581 */     return this.delegate.wasNull();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetWrapper(ResultSet delegate) {
/* 587 */     this.delegate = delegate;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\ResultSetWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */