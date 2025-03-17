/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class UIDStore
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(UIDStore.class);
/*     */   
/*  25 */   private static UIDStore instance = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private UIDStore() {
/*     */     try {
/*  31 */       ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/*  32 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)configurationService, "frame.ws.uid.store.db.");
/*  33 */       final IDatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)subConfigurationWrapper);
/*  34 */       this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */           {
/*     */             public void releaseConnection(Connection connection) {
/*  37 */               dbLink.releaseConnection(connection);
/*     */             }
/*     */             
/*     */             public Connection getConnection() {
/*     */               try {
/*  42 */                 Connection ret = dbLink.requestConnection();
/*  43 */                 ret.setReadOnly(false);
/*  44 */                 ret.setAutoCommit(false);
/*  45 */                 return ret;
/*  46 */               } catch (Exception e) {
/*  47 */                 throw new RuntimeException(e);
/*     */               } 
/*     */             }
/*     */           },  300000L);
/*     */       
/*  52 */       Util.getTimer().scheduleAtFixedRate(Util.createTimerTask(new Runnable()
/*     */             {
/*     */               public void run() {
/*  55 */                 UIDStore.this.cleanup();
/*     */               }
/*     */             },  ), 1000L, 86400000L);
/*  58 */     } catch (Exception e) {
/*  59 */       log.error("unable to init UIDStore, rethrowing - exception:", e);
/*  60 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized UIDStore getInstance() {
/*  65 */     if (instance == null) {
/*  66 */       instance = new UIDStore();
/*     */     }
/*  68 */     return instance;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/*  72 */     Connection connection = this.connectionProvider.getConnection();
/*     */     try {
/*  74 */       JDBCUtil.executeUpdate(connection, new JDBCUtil.UpdateCallback()
/*     */           {
/*     */             public boolean setParameters(PreparedStatement stmt) throws SQLException {
/*  77 */               stmt.setLong(1, System.currentTimeMillis());
/*  78 */               return false;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/*  82 */               return "delete from uidstore where ts_outdate < ?";
/*     */             }
/*     */ 
/*     */             
/*     */             public void checkResult(int result) {}
/*     */           });
/*  88 */     } catch (SQLException e) {
/*  89 */       log.error("unable to cleanup, ignoring - exception: ", e);
/*     */     } finally {
/*  91 */       this.connectionProvider.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean registerUID(String id, long validity) throws IOException {
/*  97 */     Connection connection = this.connectionProvider.getConnection();
/*     */     
/*     */     try {
/* 100 */       boolean delete = false;
/*     */ 
/*     */ 
/*     */       
/* 104 */       PreparedStatement stmt = connection.prepareStatement("select a.ts_outdate from uidstore a where a.id=?");
/*     */       try {
/* 106 */         stmt.setString(1, id);
/* 107 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 109 */           if (rs.next()) {
/* 110 */             if (System.currentTimeMillis() < rs.getLong(1)) {
/* 111 */               return false;
/*     */             }
/* 113 */             delete = true;
/*     */           } 
/*     */         } finally {
/*     */           
/* 117 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 121 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 126 */       if (delete) {
/* 127 */         stmt = connection.prepareStatement("delete from uidstore where id=?");
/*     */         try {
/* 129 */           stmt.setString(1, id);
/* 130 */           if (stmt.executeUpdate() != 1) {
/* 131 */             throw new IllegalStateException();
/*     */           }
/*     */         } finally {
/* 134 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 139 */       stmt = connection.prepareStatement("insert into uidstore(id,ts_outdate) values(?,?)");
/*     */       try {
/* 141 */         stmt.setString(1, id);
/* 142 */         stmt.setLong(2, System.currentTimeMillis() + validity);
/* 143 */         if (stmt.executeUpdate() != 1) {
/* 144 */           throw new IllegalStateException();
/*     */         }
/*     */       } finally {
/*     */         
/* 148 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */       
/* 151 */       connection.commit();
/* 152 */       return true;
/*     */     }
/* 154 */     catch (Exception e) {
/* 155 */       log.error("unable to register uid  - exception: ", e);
/* 156 */       throw new IOException();
/*     */     } finally {
/* 158 */       this.connectionProvider.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\UIDStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */