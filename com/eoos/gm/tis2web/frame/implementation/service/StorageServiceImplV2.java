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
/*     */ import com.eoos.jdbc.StatementManagerV2;
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
/*     */ public class StorageServiceImplV2
/*     */   implements StorageService
/*     */ {
/*     */   private static final String TABLE_DS = "DATA_STORE";
/*     */   private static final String COL_ID = "DATA_ID";
/*     */   private static final String COL_DATA = "DATA_BLOB";
/*  41 */   private static final DateFormat TSFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  43 */   private static final Logger log = Logger.getLogger(StorageServiceImpl.class);
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*  47 */   private LockMap lockMap = new LockMap();
/*     */   
/*  49 */   private String prefix = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTransbaseDBMS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String QUERY = "select a.DATA_BLOB from DATA_STORE a where a.DATA_ID=?";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IStatementManager smStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StatementManagerSupport stmtManagerSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StorageService.ObjectStore objectStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StorageService create(Configuration cfg) {
/* 108 */     StorageService ret = new StorageServiceImplV2(cfg);
/* 109 */     if (ApplicationContext.getInstance().developMode()) {
/* 110 */       ret = (StorageService)Tis2webUtil.hookWithExecutionTimeStatistics(ret);
/*     */     }
/* 112 */     return ret;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 116 */     return Util.getClassName(getClass()).toString();
/*     */   }
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/* 120 */     Connection ret = this.connectionProvider.getConnection();
/* 121 */     ret.setAutoCommit(false);
/* 122 */     ret.setReadOnly(true);
/* 123 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 127 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private CharSequence adjustIdentifier(CharSequence identifier) {
/* 131 */     if (this.prefix == null) {
/* 132 */       return identifier;
/*     */     }
/* 134 */     return this.prefix + identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(CharSequence identifier, byte[] data) throws Exception {
/* 139 */     identifier = adjustIdentifier(identifier);
/* 140 */     synchronized (this.lockMap.getLockObject(identifier)) {
/* 141 */       log.debug("storing data identified by " + identifier + "....");
/*     */       try {
/* 143 */         String queryClear = "delete from DATA_STORE where DATA_ID=?";
/* 144 */         PreparedStatement stmtClear = this.smStore.getStatement(queryClear);
/*     */         try {
/* 146 */           stmtClear.setString(1, identifier.toString());
/* 147 */           if (stmtClear.executeUpdate() != 0) {
/* 148 */             log.debug("...deleted old data");
/*     */           }
/* 150 */           stmtClear.getConnection().commit();
/* 151 */         } catch (Exception e) {
/* 152 */           stmtClear.getConnection().rollback();
/* 153 */           throw e;
/*     */         } finally {
/* 155 */           this.smStore.releaseStatement(stmtClear);
/*     */         } 
/*     */         
/* 158 */         if (data != null) {
/* 159 */           String queryBlobInsert = "insert into DATA_STORE values (?,?,?,?)";
/* 160 */           PreparedStatement stmtCreate = this.smStore.getStatement(queryBlobInsert);
/*     */           try {
/* 162 */             stmtCreate.setString(1, identifier.toString());
/* 163 */             if (this.isTransbaseDBMS) {
/* 164 */               stmtCreate.setBytes(2, data);
/*     */             } else {
/* 166 */               stmtCreate.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
/*     */             } 
/* 168 */             long ts = System.currentTimeMillis();
/* 169 */             stmtCreate.setLong(3, ts);
/* 170 */             stmtCreate.setString(4, TSFORMAT.format(new Date(ts)));
/* 171 */             if (stmtCreate.executeUpdate() != 1) {
/* 172 */               throw new IllegalStateException();
/*     */             }
/* 174 */             stmtCreate.getConnection().commit();
/* 175 */           } catch (Exception e) {
/* 176 */             stmtCreate.getConnection().rollback();
/* 177 */             throw e;
/*     */           } finally {
/* 179 */             this.smStore.releaseStatement(stmtCreate);
/*     */           }
/*     */         
/*     */         } 
/* 183 */       } catch (Exception e) {
/* 184 */         log.error("...unable to store data for id: " + identifier + " - exception:" + e, e);
/* 185 */         throw e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IStatementManager getStatementManager() {
/* 193 */     return this.stmtManagerSupport.getStatementManager();
/*     */   }
/*     */   
/*     */   public byte[] load(CharSequence identifier) throws Exception {
/* 197 */     identifier = adjustIdentifier(identifier);
/* 198 */     byte[] result = null;
/*     */     try {
/* 200 */       log.debug("loading data for id: " + identifier + " ....");
/* 201 */       PreparedStatement stmt = getStatementManager().getStatement("select a.DATA_BLOB from DATA_STORE a where a.DATA_ID=?");
/*     */       try {
/* 203 */         stmt.setString(1, identifier.toString());
/* 204 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 206 */           if (rs.next()) {
/* 207 */             if (this.isTransbaseDBMS) {
/* 208 */               result = rs.getBytes(1);
/*     */             } else {
/* 210 */               result = StreamUtil.readFully(rs.getBinaryStream(1));
/*     */             } 
/*     */           }
/* 213 */           return result;
/*     */         } finally {
/* 215 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 218 */         getStatementManager().releaseStatement(stmt);
/*     */       }
/*     */     
/* 221 */     } catch (Exception e) {
/* 222 */       log.error("...unable to load data for id: " + identifier + " - exception:" + e, e);
/* 223 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 228 */     return BigInteger.valueOf(hashCode());
/*     */   }
/*     */   
/* 231 */   public StorageServiceImplV2(Configuration configuration) { this.objectStore = null; this.prefix = configuration.getProperty("identifier.prefix"); final IDatabaseLink dblink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(configuration, "db.")); this.isTransbaseDBMS = (dblink.getDBMS() == 2); this.connectionProvider = ConNvent.create(new ConnectionProvider() { public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { return dblink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  }
/*     */         },  300000L); this.stmtManagerSupport = new StatementManagerSupport(this.connectionProvider); try { releaseConnection(getReadConnection()); } catch (Exception e) { throw new RuntimeException(e); }  ConnectionProvider writeConnectionProvider = new ConnectionProvider() {
/*     */         public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { Connection ret = dblink.requestConnection(); ret.setReadOnly(false); ret.setAutoCommit(false); return ret; } catch (Exception e) { throw new RuntimeException(e); }  }
/* 234 */       }; writeConnectionProvider = ConNvent.create(writeConnectionProvider, 300000L); this.smStore = (IStatementManager)new StatementManagerV2(writeConnectionProvider); } public StorageService.ObjectStore getObjectStoreFacade() { if (this.objectStore == null) {
/* 235 */       synchronized (this) {
/* 236 */         if (this.objectStore == null) {
/* 237 */           this.objectStore = new ObjectStoreFacadeImpl(this);
/* 238 */           if (ApplicationContext.getInstance().developMode()) {
/* 239 */             this.objectStore = (StorageService.ObjectStore)Tis2webUtil.hookWithExecutionTimeStatistics(this.objectStore);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 245 */     return this.objectStore; }
/*     */ 
/*     */   
/*     */   public void delete(CharSequence identifier) throws Exception {
/* 249 */     log.debug("deleting data for id:" + identifier + " (calling store with 'null' ...");
/* 250 */     store(identifier, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\StorageServiceImplV2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */