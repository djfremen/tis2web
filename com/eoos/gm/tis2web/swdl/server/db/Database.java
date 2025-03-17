/*    */ package com.eoos.gm.tis2web.swdl.server.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.jdbc.ConnectionProvider;
/*    */ import java.sql.Connection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Database
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(Database.class);
/*    */   
/* 23 */   protected IDatabaseLink dblink = null;
/*    */   
/*    */   static {
/*    */     try {
/* 27 */       Class.forName("oracle.jdbc.OracleDriver");
/*    */     }
/* 29 */     catch (Exception e) {
/* 30 */       log.error("Exception when get database driver; " + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private ConnectionProvider connectionProvider;
/*    */   
/*    */   public Database(final IDatabaseLink dblink) {
/* 37 */     this.dblink = dblink;
/* 38 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*    */         {
/*    */           public void releaseConnection(Connection connection) {
/* 41 */             dblink.releaseConnection(connection);
/*    */           }
/*    */           
/*    */           public Connection getConnection() {
/*    */             try {
/* 46 */               return dblink.requestConnection();
/* 47 */             } catch (Exception e) {
/* 48 */               throw new RuntimeException(e);
/*    */             } 
/*    */           }
/*    */         },  60000L);
/*    */ 
/*    */     
/* 54 */     log.debug(dblink.getDatabaseLinkDescription());
/*    */     try {
/* 56 */       Connection connection = requestConnection();
/*    */       try {
/* 58 */         log.debug(dblink.getDatabaseLinkInformation(connection));
/*    */       } finally {
/* 60 */         releaseConnection(connection);
/*    */       } 
/* 62 */     } catch (Exception e) {
/* 63 */       log.warn("unable to retrieve database link information - exception: " + e, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Connection requestConnection() throws Exception {
/* 69 */     return this.connectionProvider.getConnection();
/*    */   }
/*    */   
/*    */   public synchronized void releaseConnection(Connection connection) {
/* 73 */     this.connectionProvider.releaseConnection(connection);
/*    */   }
/*    */   
/*    */   public void close() throws Exception {
/* 77 */     if (this.dblink != null) {
/* 78 */       this.dblink = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 83 */     return this.dblink.getDatabaseLinkDescription();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\Database.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */