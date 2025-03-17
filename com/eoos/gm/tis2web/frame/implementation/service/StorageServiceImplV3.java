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
/*     */ import java.sql.DatabaseMetaData;
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
/*     */ public class StorageServiceImplV3
/*     */   implements StorageService
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(StorageServiceImpl.class);
/*     */   
/*     */   private static final String TABLE_DS = "DATA_STORE_V2";
/*     */   
/*     */   private static final String COL_ID = "DATA_ID";
/*     */   
/*     */   private static final String COL_DATA = "DATA_BLOB";
/*     */   
/*  44 */   private static final DateFormat TSFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*  48 */   private LockMap lockMap = new LockMap();
/*     */   
/*  50 */   private String prefix = null;
/*     */   
/*     */   private boolean isTransbaseDBMS;
/*     */   
/*     */   private IStatementManager smStore;
/*     */   
/*  56 */   private int tableCount = 0;
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
/*     */   private StatementManagerSupport stmtManagerSupport;
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
/*     */   public static StorageService create(Configuration cfg) {
/* 121 */     StorageService ret = new StorageServiceImplV3(cfg);
/* 122 */     if (ApplicationContext.getInstance().developMode()) {
/* 123 */       ret = (StorageService)Tis2webUtil.hookWithExecutionTimeStatistics(ret);
/*     */     }
/* 125 */     return ret;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     return Util.getClassName(getClass()).toString();
/*     */   }
/*     */   
/*     */   private CharSequence adjustIdentifier(CharSequence identifier) {
/* 133 */     if (this.prefix == null) {
/* 134 */       return identifier;
/*     */     }
/* 136 */     return this.prefix + identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(CharSequence identifier, byte[] data) throws Exception {
/* 141 */     identifier = adjustIdentifier(identifier);
/* 142 */     synchronized (this.lockMap.getLockObject(identifier)) {
/* 143 */       log.debug("storing data identified by " + identifier + "....");
/*     */       try {
/* 145 */         String queryClear = "delete from DATA_STORE_V2" + getTableIndex(identifier) + " where " + "DATA_ID" + "=?";
/* 146 */         PreparedStatement stmtClear = this.smStore.getStatement(queryClear);
/*     */         try {
/* 148 */           stmtClear.setString(1, identifier.toString());
/* 149 */           if (stmtClear.executeUpdate() != 0) {
/* 150 */             log.debug("...deleted old data");
/*     */           }
/* 152 */           stmtClear.getConnection().commit();
/* 153 */         } catch (Exception e) {
/* 154 */           stmtClear.getConnection().rollback();
/* 155 */           throw e;
/*     */         } finally {
/* 157 */           this.smStore.releaseStatement(stmtClear);
/*     */         } 
/*     */         
/* 160 */         if (data != null) {
/* 161 */           String queryBlobInsert = "insert into DATA_STORE_V2" + getTableIndex(identifier) + " values (?,?,?,?)";
/* 162 */           PreparedStatement stmtCreate = this.smStore.getStatement(queryBlobInsert);
/*     */           try {
/* 164 */             stmtCreate.setString(1, identifier.toString());
/* 165 */             if (this.isTransbaseDBMS) {
/* 166 */               stmtCreate.setBytes(2, data);
/*     */             } else {
/* 168 */               stmtCreate.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
/*     */             } 
/* 170 */             long ts = System.currentTimeMillis();
/* 171 */             stmtCreate.setLong(3, ts);
/* 172 */             stmtCreate.setString(4, TSFORMAT.format(new Date(ts)));
/* 173 */             if (stmtCreate.executeUpdate() != 1) {
/* 174 */               throw new IllegalStateException();
/*     */             }
/* 176 */             stmtCreate.getConnection().commit();
/* 177 */           } catch (Exception e) {
/* 178 */             stmtCreate.getConnection().rollback();
/* 179 */             throw e;
/*     */           } finally {
/* 181 */             this.smStore.releaseStatement(stmtCreate);
/*     */           }
/*     */         
/*     */         } 
/* 185 */       } catch (Exception e) {
/* 186 */         log.error("...unable to store data for id: " + identifier + " - exception:" + e, e);
/* 187 */         throw e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IStatementManager getStatementManager() {
/* 195 */     return this.stmtManagerSupport.getStatementManager();
/*     */   }
/*     */   
/*     */   private int getTableIndex(CharSequence identifier) {
/* 199 */     return (int)(Math.abs(identifier.hashCode()) % this.tableCount);
/*     */   }
/*     */   
/*     */   public byte[] load(CharSequence identifier) throws Exception {
/* 203 */     identifier = adjustIdentifier(identifier);
/* 204 */     byte[] result = null;
/*     */     try {
/* 206 */       log.debug("loading data for id: " + identifier + " ....");
/* 207 */       PreparedStatement stmt = getStatementManager().getStatement("select a.DATA_BLOB from DATA_STORE_V2" + getTableIndex(identifier) + " a where a." + "DATA_ID" + "=?");
/*     */       try {
/* 209 */         stmt.setString(1, identifier.toString());
/* 210 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 212 */           if (rs.next()) {
/* 213 */             if (this.isTransbaseDBMS) {
/* 214 */               result = rs.getBytes(1);
/*     */             } else {
/* 216 */               result = StreamUtil.readFully(rs.getBinaryStream(1));
/*     */             } 
/*     */           }
/* 219 */           return result;
/*     */         } finally {
/* 221 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 224 */         getStatementManager().releaseStatement(stmt);
/*     */       }
/*     */     
/* 227 */     } catch (Exception e) {
/* 228 */       log.error("...unable to load data for id: " + identifier + " - exception:" + e, e);
/* 229 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 234 */     return BigInteger.valueOf(hashCode());
/*     */   }
/*     */   
/* 237 */   public StorageServiceImplV3(Configuration configuration) { this.objectStore = null; log.debug("initializing " + this + "..."); this.prefix = configuration.getProperty("identifier.prefix"); final IDatabaseLink dblink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(configuration, "db.")); this.isTransbaseDBMS = (dblink.getDBMS() == 2); this.connectionProvider = ConNvent.create(new ConnectionProvider() { public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { return dblink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  }
/*     */         },  300000L); this.stmtManagerSupport = new StatementManagerSupport(this.connectionProvider); try { Connection connection = this.connectionProvider.getConnection(); try { DatabaseMetaData dbMD = connection.getMetaData(); ResultSet rs = dbMD.getTables(null, null, "DATA_STORE_V2%", new String[] { "TABLE" }); while (rs.next()) { String name = rs.getString("TABLE_NAME"); log.debug("...found table: " + name); this.tableCount++; }  } finally { this.connectionProvider.releaseConnection(connection); }  } catch (Exception e) { throw new RuntimeException(e); }  ConnectionProvider writeConnectionProvider = new ConnectionProvider() {
/*     */         public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { Connection ret = dblink.requestConnection(); ret.setReadOnly(false); ret.setAutoCommit(false); return ret; } catch (Exception e) { throw new RuntimeException(e); }  }
/* 240 */       }; writeConnectionProvider = ConNvent.create(writeConnectionProvider, 300000L); this.smStore = (IStatementManager)new StatementManagerV2(writeConnectionProvider); } public StorageService.ObjectStore getObjectStoreFacade() { if (this.objectStore == null) {
/* 241 */       synchronized (this) {
/* 242 */         if (this.objectStore == null) {
/* 243 */           this.objectStore = new ObjectStoreFacadeImpl(this);
/* 244 */           if (ApplicationContext.getInstance().developMode()) {
/* 245 */             this.objectStore = (StorageService.ObjectStore)Tis2webUtil.hookWithExecutionTimeStatistics(this.objectStore);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 251 */     return this.objectStore; }
/*     */ 
/*     */   
/*     */   public void delete(CharSequence identifier) throws Exception {
/* 255 */     log.debug("deleting data for id:" + identifier + " (calling store with 'null' ...");
/* 256 */     store(identifier, null);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 260 */     for (int i = 1; i < 210; i++) {
/* 261 */       if (210 % i == 0)
/* 262 */         System.out.println(i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\StorageServiceImplV3.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */