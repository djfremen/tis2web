/*     */ package com.eoos.gm.tis2web.si.implementation.log;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SIEventLog_DB
/*     */   implements SIEventLog.SPI
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(SIEventLog_DB.class);
/*     */   
/*  25 */   private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH);
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   public SIEventLog_DB(Configuration configuration) throws Exception {
/*  30 */     final IDatabaseLink dblink = DatabaseLink.openDatabase(configuration);
/*     */     
/*  32 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  35 */             dblink.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  40 */               return dblink.requestConnection();
/*  41 */             } catch (Exception e) {
/*  42 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  300000L);
/*     */ 
/*     */     
/*     */     try {
/*  49 */       releaseConnection(getReadConnection());
/*  50 */     } catch (Exception e) {
/*  51 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/*  58 */     Connection ret = this.connectionProvider.getConnection();
/*  59 */     ret.setAutoCommit(false);
/*  60 */     ret.setReadOnly(true);
/*  61 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws Exception {
/*  65 */     Connection ret = this.connectionProvider.getConnection();
/*  66 */     ret.setReadOnly(false);
/*  67 */     ret.setAutoCommit(false);
/*  68 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  72 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private void write(Collection entries) {
/*     */     try {
/*  77 */       Connection connection = getWriteConnection();
/*     */       
/*     */       try {
/*  80 */         PreparedStatement stmt = connection.prepareStatement("INSERT INTO SI_EVENT_LOG VALUES(?,?,?,?)");
/*     */         try {
/*  82 */           for (Iterator<SIEventLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/*  83 */             SIEventLog.Entry entry = iter.next();
/*     */             
/*  85 */             stmt.setString(1, this.dateFormat.format(new Date(entry.getTSDisplay())));
/*  86 */             stmt.setString(2, entry.getUserID());
/*  87 */             stmt.setString(3, entry.getSIInformation());
/*  88 */             stmt.setString(4, entry.getVC());
/*  89 */             stmt.addBatch();
/*     */           } 
/*  91 */           stmt.executeBatch();
/*  92 */           connection.commit();
/*     */         } finally {
/*  94 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*  96 */       } catch (Exception e) {
/*  97 */         if (!(e instanceof java.sql.BatchUpdateException)) {
/*     */ 
/*     */           
/* 100 */           JDBCUtil.rollback(connection, log);
/* 101 */           throw e;
/*     */         } 
/*     */       } finally {
/*     */         
/* 105 */         releaseConnection(connection);
/*     */       } 
/* 107 */     } catch (Exception e) {
/* 108 */       log.error("unable to add " + entries.size() + " to database - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Collection entries) {
/* 114 */     if (!Util.isNullOrEmpty(entries)) {
/* 115 */       log.info("adding " + entries.size() + " entries to si event log (database)");
/* 116 */       write(entries);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 121 */     return Util.getClassName(getClass()) + "@" + hashCode();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\log\SIEventLog_DB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */