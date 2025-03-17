/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.IStatementManager;
/*     */ import com.eoos.jdbc.StatementManagerSupport;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.cache.HotspotCache2;
/*     */ import java.io.IOException;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.lucene.store.Directory;
/*     */ import org.apache.lucene.store.InputStream;
/*     */ import org.apache.lucene.store.Lock;
/*     */ import org.apache.lucene.store.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SQLDirectory
/*     */   extends Directory
/*     */ {
/*  47 */   protected static final Logger log = Logger.getLogger(SQLDirectory.class);
/*     */   
/*     */   private static final String DEFAULT_FILE_TABLE = "LUCENE_FILE";
/*     */   
/*     */   private static final String DEFAULT_BUFFER_TABLE = "LUCENE_BUFFER";
/*     */   
/*     */   public static final int SQL_BUFFER_SIZE = 1024;
/*     */   
/*     */   private final Integer locale;
/*     */   
/*     */   private final String fileTable;
/*     */   
/*     */   private final String bufferTable;
/*  60 */   public static int cacheSize = 512;
/*     */ 
/*     */   
/*     */   private Cache cache;
/*     */   
/*     */   private StatementManagerSupport stmtManagerSupport;
/*     */ 
/*     */   
/*     */   public SQLDirectory(ConnectionProvider connectionProvider, Integer locale) {
/*  69 */     this.stmtManagerSupport = new StatementManagerSupport(connectionProvider);
/*  70 */     this.locale = locale;
/*  71 */     this.fileTable = "LUCENE_FILE";
/*  72 */     this.bufferTable = "LUCENE_BUFFER";
/*  73 */     this.cache = (Cache)new HotspotCache2(30000L);
/*     */   }
/*     */ 
/*     */   
/*     */   Cache getCache() {
/*  78 */     return this.cache;
/*     */   }
/*     */   
/*     */   IStatementManager getStatementManager() {
/*  82 */     return this.stmtManagerSupport.getStatementManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String[] list() throws IOException {
/*  87 */     if (log.isDebugEnabled())
/*  88 */       log.debug("list() ..."); 
/*  89 */     List<String> list = new LinkedList();
/*     */     try {
/*  91 */       PreparedStatement stmt = getStatementManager().getStatement("select filename from " + this.fileTable + " where locale = ?");
/*     */       try {
/*  93 */         stmt.setInt(1, this.locale.intValue());
/*  94 */         ResultSet rs = stmt.executeQuery();
/*     */         
/*  96 */         while (rs.next()) {
/*  97 */           list.add(rs.getString(1));
/*     */         }
/*  99 */         rs.close();
/*     */       } finally {
/* 101 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/*     */       
/* 104 */       throw new ClassCastException();
/* 105 */     } catch (Exception e) {
/* 106 */       throw new IOException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean fileExists(String name) throws IOException {
/*     */     boolean result;
/* 121 */     if (log.isDebugEnabled()) {
/* 122 */       log.debug("fileExists(" + name + ") ...");
/*     */     }
/*     */     try {
/* 125 */       PreparedStatement stmt = getStatementManager().getStatement("select filename from " + this.fileTable + " where locale = ? and filename = ?");
/*     */       try {
/* 127 */         stmt.setInt(1, this.locale.intValue());
/* 128 */         stmt.setString(2, name);
/* 129 */         ResultSet rs = stmt.executeQuery();
/* 130 */         result = rs.next();
/* 131 */         rs.close();
/*     */       } finally {
/* 133 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 135 */     } catch (Exception e) {
/* 136 */       throw new IOException(e.getMessage());
/*     */     } 
/* 138 */     if (log.isDebugEnabled())
/* 139 */       log.debug(" done: " + result); 
/* 140 */     return result;
/*     */   }
/*     */   
/*     */   public final long fileModified(String name) throws IOException {
/*     */     long result;
/* 145 */     if (log.isDebugEnabled()) {
/* 146 */       log.debug("fileModified(" + name + ") ...");
/*     */     }
/*     */     try {
/* 149 */       PreparedStatement stmt = getStatementManager().getStatement("select modified from " + this.fileTable + " where locale = ? and filename = ?");
/*     */       try {
/* 151 */         stmt.setInt(1, this.locale.intValue());
/*     */         
/* 153 */         stmt.setString(2, name);
/* 154 */         ResultSet rs = stmt.executeQuery();
/* 155 */         rs.next();
/* 156 */         result = rs.getLong(1);
/* 157 */         rs.close();
/*     */       } finally {
/* 159 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 161 */     } catch (Exception e) {
/* 162 */       throw new IOException(e.getMessage());
/*     */     } 
/* 164 */     if (log.isDebugEnabled())
/* 165 */       log.debug(" done:  " + result); 
/* 166 */     return result;
/*     */   }
/*     */   
/*     */   public final long fileLength(String name) throws IOException {
/*     */     long result;
/* 171 */     if (log.isDebugEnabled()) {
/* 172 */       log.debug("fileLength(" + name + ") ...");
/*     */     }
/*     */     try {
/* 175 */       PreparedStatement stmt = getStatementManager().getStatement("select length from " + this.fileTable + " where locale = ? and filename = ?");
/*     */       try {
/* 177 */         stmt.setInt(1, this.locale.intValue());
/*     */         
/* 179 */         stmt.setString(2, name);
/* 180 */         ResultSet rs = stmt.executeQuery();
/* 181 */         rs.next();
/* 182 */         result = rs.getLong(1);
/* 183 */         rs.close();
/*     */       } finally {
/* 185 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 187 */     } catch (Exception e) {
/* 188 */       throw new IOException(e.getMessage());
/*     */     } 
/* 190 */     if (log.isDebugEnabled())
/* 191 */       log.debug(" done: " + result); 
/* 192 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void deleteFile(String name) throws IOException {
/* 197 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void renameFile(String from, String to) throws IOException {
/* 202 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void touchFile(String name) throws IOException {
/* 206 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final OutputStream createFile(String name) {
/* 214 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public final InputStream openFile(String name) throws IOException {
/* 219 */     if (log.isDebugEnabled())
/* 220 */       log.debug("openFile(" + name + ")"); 
/* 221 */     return new SQLInputStream(this, this.locale, this.fileTable, this.bufferTable, name, fileLength(name));
/*     */   }
/*     */   
/*     */   public final Lock makeLock(String name) {
/* 225 */     return new Lock() {
/*     */         public boolean obtain() {
/* 227 */           return true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void release() {}
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final void close() {
/* 238 */     if (log.isDebugEnabled())
/* 239 */       log.debug("close()"); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\SQLDirectory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */