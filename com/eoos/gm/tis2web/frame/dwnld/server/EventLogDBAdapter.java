/*     */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class EventLogDBAdapter
/*     */   implements EventLog
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(EventLogDBAdapter.class);
/*     */   
/*  27 */   private static EventLogDBAdapter instance = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private EventLogDBAdapter(Configuration dbConfiguration) {
/*  32 */     final IDatabaseLink backend = DatabaseLink.openDatabase(dbConfiguration);
/*  33 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  36 */             backend.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  41 */               return backend.requestConnection();
/*  42 */             } catch (Exception e) {
/*  43 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */   }
/*     */   
/*     */   public static synchronized EventLogDBAdapter getInstance() {
/*  50 */     if (instance == null) {
/*  51 */       log.info("creating instance");
/*  52 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.dwnld.event.log.db.");
/*  53 */       instance = new EventLogDBAdapter((Configuration)subConfigurationWrapper);
/*     */     } 
/*  55 */     return instance;
/*     */   }
/*     */   
/*     */   private synchronized Connection getWriteConnection() throws Exception {
/*  59 */     Connection ret = this.connectionProvider.getConnection();
/*  60 */     ret.setReadOnly(false);
/*  61 */     ret.setAutoCommit(false);
/*  62 */     return ret;
/*     */   }
/*     */   
/*     */   private synchronized void releaseConnection(Connection connection) throws Exception {
/*  66 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private long getID(Connection connection) throws Exception {
/*  70 */     long ret = -1L;
/*     */     
/*  72 */     PreparedStatement keyQueryStmt = connection.prepareStatement("select currentkey from cdl_event_log_genkey");
/*     */     try {
/*  74 */       PreparedStatement keyUpdateStmt = connection.prepareStatement("update cdl_event_log_genkey set currentkey=? where currentkey=?");
/*     */       try {
/*  76 */         while (ret == -1L) {
/*     */           long currentID;
/*  78 */           ResultSet rs = keyQueryStmt.executeQuery();
/*     */           try {
/*  80 */             if (rs.next()) {
/*  81 */               currentID = rs.getLong(1);
/*     */             } else {
/*  83 */               throw new IllegalStateException("table cdl_event_log_genkey has to contain a value");
/*     */             } 
/*     */           } finally {
/*  86 */             JDBCUtil.close(rs, log);
/*     */           } 
/*  88 */           long nextID = currentID + 1L;
/*  89 */           keyUpdateStmt.setLong(1, nextID);
/*  90 */           keyUpdateStmt.setLong(2, currentID);
/*     */           
/*  92 */           if (keyUpdateStmt.executeUpdate() != 1) {
/*  93 */             ret = -1L; continue;
/*     */           } 
/*  95 */           ret = nextID;
/*     */         } 
/*     */         
/*  98 */         return ret;
/*     */       } finally {
/*     */         
/* 101 */         JDBCUtil.close(keyUpdateStmt, log);
/*     */       } 
/*     */     } finally {
/*     */       
/* 105 */       JDBCUtil.close(keyQueryStmt, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String toString(Collection sessionIDs) {
/* 110 */     StringBuffer tmp = new StringBuffer();
/* 111 */     for (Iterator iter = CollectionUtil.iterator(sessionIDs); iter.hasNext(); ) {
/* 112 */       tmp.append(iter.next());
/* 113 */       if (iter.hasNext()) {
/* 114 */         tmp.append(", ");
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return tmp.substring(0, Math.min(100, tmp.length()));
/*     */   }
/*     */   
/*     */   public void logDwnldEvent(Collection sessionIDs, long tsStart, long tsEnd, Collection downloadUnits, Object status) {
/*     */     try {
/* 123 */       Connection connection = getWriteConnection();
/*     */       try {
/* 125 */         long id = getID(connection);
/*     */ 
/*     */ 
/*     */         
/* 129 */         PreparedStatement stmt = connection.prepareStatement("insert into cdl_event_log(event_id,session_id,ts_start,ts_end, status) values(?,?,?,?,?)");
/*     */         try {
/* 131 */           stmt.setLong(1, id);
/* 132 */           stmt.setString(2, toString(sessionIDs));
/* 133 */           stmt.setString(3, Util.formatDate(tsStart));
/* 134 */           stmt.setString(4, Util.formatDate(tsEnd));
/* 135 */           stmt.setString(5, String.valueOf(status));
/*     */           
/* 137 */           if (stmt.executeUpdate() != 1) {
/* 138 */             throw new IllegalStateException("unable to update base table");
/*     */           }
/*     */         } finally {
/* 141 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */         
/* 144 */         if (!Util.isNullOrEmpty(downloadUnits)) {
/*     */           
/* 146 */           stmt = connection.prepareStatement("insert into cdl_event_log_units(event_refid,unit_name,unit_version) values (?,?,?)");
/*     */           try {
/* 148 */             for (Iterator<IDownloadUnit> iter = downloadUnits.iterator(); iter.hasNext(); ) {
/* 149 */               IDownloadUnit unit = iter.next();
/*     */               
/* 151 */               stmt.setLong(1, id);
/* 152 */               stmt.setString(2, String.valueOf(unit.getDescripition(Locale.ENGLISH)));
/* 153 */               stmt.setString(3, unit.getVersionNumber().toString());
/*     */               
/* 155 */               if (stmt.executeUpdate() != 1) {
/* 156 */                 throw new IllegalStateException("unable to update unit table");
/*     */               }
/*     */             } 
/*     */           } finally {
/*     */             
/* 161 */             JDBCUtil.close(stmt, log);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 166 */         connection.commit();
/* 167 */       } catch (Exception e) {
/* 168 */         connection.rollback();
/* 169 */         throw e;
/*     */       } finally {
/* 171 */         releaseConnection(connection);
/*     */       } 
/* 173 */     } catch (Exception e) {
/* 174 */       log.error("unable to log download event, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\EventLogDBAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */