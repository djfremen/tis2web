/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.IStatementManager;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.jdbc.StatementManagerSupport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.v2.LockMap;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorageServiceImpl
/*     */   implements StorageService
/*     */ {
/*     */   private static final String TABLE_DS = "DATA_STORE";
/*     */   private static final String COL_ID = "DATA_ID";
/*     */   private static final String COL_DATA = "DATA_BLOB";
/*  40 */   private static final DateFormat TSFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  42 */   private static final Logger log = Logger.getLogger(StorageServiceImpl.class);
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*  46 */   private LockMap lockMap = new LockMap();
/*     */   
/*  48 */   private String prefix = null;
/*     */   
/*     */   private boolean isTransbaseDBMS;
/*     */   private static final String QUERY = "select a.DATA_BLOB from DATA_STORE a where a.DATA_ID=?";
/*     */   private StatementManagerSupport stmtManagerSupport;
/*     */   
/*     */   public StorageServiceImpl(Configuration configuration) {
/*  55 */     this.prefix = configuration.getProperty("identifier.prefix");
/*  56 */     final IDatabaseLink dblink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(configuration, "db."));
/*  57 */     this.isTransbaseDBMS = (dblink.getDBMS() == 2);
/*  58 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  61 */             dblink.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  66 */               return dblink.requestConnection();
/*  67 */             } catch (Exception e) {
/*  68 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  300000L);
/*     */     
/*  73 */     this.stmtManagerSupport = new StatementManagerSupport(this.connectionProvider);
/*     */     try {
/*  75 */       releaseConnection(getReadConnection());
/*  76 */     } catch (Exception e) {
/*  77 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StorageService create(Configuration cfg) {
/*  84 */     StorageService ret = new StorageServiceImpl(cfg);
/*  85 */     if (ApplicationContext.getInstance().developMode()) {
/*  86 */       ret = (StorageService)Tis2webUtil.hookWithExecutionTimeStatistics(ret);
/*     */     }
/*  88 */     return ret;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  92 */     return Util.getClassName(getClass()).toString();
/*     */   }
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/*  96 */     Connection ret = this.connectionProvider.getConnection();
/*  97 */     ret.setAutoCommit(false);
/*  98 */     ret.setReadOnly(true);
/*  99 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws Exception {
/* 103 */     Connection ret = this.connectionProvider.getConnection();
/* 104 */     ret.setReadOnly(false);
/* 105 */     ret.setAutoCommit(false);
/* 106 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 110 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private CharSequence adjustIdentifier(CharSequence identifier) {
/* 114 */     if (this.prefix == null) {
/* 115 */       return identifier;
/*     */     }
/* 117 */     return this.prefix + identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(CharSequence identifier, byte[] data) throws Exception {
/* 122 */     identifier = adjustIdentifier(identifier);
/* 123 */     synchronized (this.lockMap.getLockObject(identifier)) {
/* 124 */       log.debug("storing data identified by " + identifier + "....");
/* 125 */       Connection connection = getWriteConnection();
/*     */       try {
/* 127 */         String queryClear = "delete from DATA_STORE where DATA_ID=?";
/* 128 */         PreparedStatement stmtClear = connection.prepareStatement(queryClear);
/*     */         try {
/* 130 */           stmtClear.setString(1, identifier.toString());
/* 131 */           if (stmtClear.executeUpdate() != 0) {
/* 132 */             log.debug("...deleted old data");
/*     */           }
/*     */           
/* 135 */           if (data != null) {
/* 136 */             String queryBlobInsert = "insert into DATA_STORE values (?,?,?,?)";
/* 137 */             PreparedStatement stmtCreate = connection.prepareStatement(queryBlobInsert);
/*     */             try {
/* 139 */               stmtCreate.setString(1, identifier.toString());
/* 140 */               if (this.isTransbaseDBMS) {
/* 141 */                 stmtCreate.setBytes(2, data);
/*     */               } else {
/* 143 */                 stmtCreate.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
/*     */               } 
/* 145 */               long ts = System.currentTimeMillis();
/* 146 */               stmtCreate.setLong(3, ts);
/* 147 */               stmtCreate.setString(4, TSFORMAT.format(new Date(ts)));
/* 148 */               if (stmtCreate.executeUpdate() != 1) {
/* 149 */                 throw new IllegalStateException();
/*     */               }
/*     */             } finally {
/* 152 */               JDBCUtil.close(stmtCreate);
/*     */             } 
/*     */           } 
/*     */         } finally {
/*     */           
/* 157 */           JDBCUtil.close(stmtClear);
/*     */         } 
/* 159 */         connection.commit();
/*     */       }
/* 161 */       catch (Exception e) {
/* 162 */         log.error("...unable to store data for id: " + identifier + " - exception:" + e, e);
/* 163 */         JDBCUtil.rollback(connection, log);
/* 164 */         throw e;
/*     */       } finally {
/* 166 */         releaseConnection(connection);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IStatementManager getStatementManager() {
/* 175 */     return this.stmtManagerSupport.getStatementManager();
/*     */   }
/*     */   
/*     */   public byte[] load(CharSequence identifier) throws Exception {
/* 179 */     identifier = adjustIdentifier(identifier);
/* 180 */     byte[] result = null;
/*     */     try {
/* 182 */       log.debug("loading data for id: " + identifier + " ....");
/* 183 */       PreparedStatement stmt = getStatementManager().getStatement("select a.DATA_BLOB from DATA_STORE a where a.DATA_ID=?");
/*     */       try {
/* 185 */         stmt.setString(1, identifier.toString());
/* 186 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 188 */           if (rs.next()) {
/* 189 */             if (this.isTransbaseDBMS) {
/* 190 */               result = rs.getBytes(1);
/*     */             } else {
/* 192 */               result = StreamUtil.readFully(rs.getBinaryStream(1));
/*     */             } 
/*     */           }
/* 195 */           return result;
/*     */         } finally {
/* 197 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 200 */         getStatementManager().releaseStatement(stmt);
/*     */       }
/*     */     
/* 203 */     } catch (Exception e) {
/* 204 */       log.error("...unable to load data for id: " + identifier + " - exception:" + e, e);
/* 205 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 210 */     return BigInteger.valueOf(hashCode());
/*     */   }
/*     */   
/*     */   public StorageService.ObjectStore getObjectStoreFacade() {
/* 214 */     StorageService.ObjectStore ret = new ObjectStoreFacadeImpl(this);
/* 215 */     if (ApplicationContext.getInstance().developMode()) {
/* 216 */       ret = (StorageService.ObjectStore)Tis2webUtil.hookWithExecutionTimeStatistics(ret);
/*     */     }
/* 218 */     return ret;
/*     */   }
/*     */   
/*     */   public void delete(CharSequence identifier) throws Exception {
/* 222 */     log.debug("deleting data for id:" + identifier + " (calling store with 'null' ...");
/* 223 */     store(identifier, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\StorageServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */