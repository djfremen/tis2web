/*    */ package com.eoos.gm.tis2web.acl.implementation.io;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.ConnectionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.jdbc.ConnectionProvider;
/*    */ import com.eoos.propcfg.Configuration;
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
/*    */ public class Database
/*    */ {
/* 24 */   private static Database instance = null;
/*    */   
/* 26 */   protected ConnectionProvider db = null;
/*    */   
/* 28 */   private static final Logger log = Logger.getLogger(Database.class);
/*    */   
/*    */   public static final String SELECT_RESOURCES = "select distinct f.RESOURCE_VALUE from AUTHORIZATION_CRITERIA a, AUTHORIZATION_DOMAIN b, AUTHORIZATION_VALUE c, AUTHORIZATION d, AUTHORIZATION_RESOURCE e, RESOURCE_TABLE f, RESOURCE_DOMAIN g, RESOURCE_GROUP h where a.CRITERIA_ID=b.CRITERIA_ID and b.VALUE_ID=c.VALUE_ID and a.CRITERIA=? and c.Value=? and c.VALUE_ID=d.VALUE_ID and d.AUTHORIZATION_ID=e.AUTHORIZATION_ID and e.RESOURCE_ID=f.RESOURCE_ID and e.RESOURCE_ID=g.RESOURCE_ID and g.RESOURCE_GROUP_ID=h.RESOURCE_GROUP_ID and h.RESOURCE_GROUP=?";
/*    */   
/*    */   public static final String SELECT_ALL_RESOURCES = "select distinct f.RESOURCE_VALUE from AUTHORIZATION_CRITERIA a, AUTHORIZATION_DOMAIN b, AUTHORIZATION_VALUE c, AUTHORIZATION d, AUTHORIZATION_RESOURCE e, RESOURCE_TABLE f, RESOURCE_DOMAIN g, RESOURCE_GROUP h where a.CRITERIA_ID=b.CRITERIA_ID and b.VALUE_ID=c.VALUE_ID and c.VALUE_ID=d.VALUE_ID and d.AUTHORIZATION_ID=e.AUTHORIZATION_ID and e.RESOURCE_ID=f.RESOURCE_ID and e.RESOURCE_ID=g.RESOURCE_ID and g.RESOURCE_GROUP_ID=h.RESOURCE_GROUP_ID and h.RESOURCE_GROUP=?";
/*    */   
/*    */   private Database() {
/*    */     try {
/* 36 */       final DatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "frame.adapter.acl.db");
/* 37 */       log.info(databaseLink.getDatabaseLinkDescription());
/* 38 */       log.info(databaseLink.getDatabaseLinkInformation());
/* 39 */       this.db = ConNvent.create(new ConnectionProvider()
/*    */           {
/*    */             public void releaseConnection(Connection connection) {
/* 42 */               dbLink.releaseConnection(connection);
/*    */             }
/*    */             
/*    */             public Connection getConnection() {
/*    */               try {
/* 47 */                 return dbLink.requestConnection();
/* 48 */               } catch (Exception e) {
/* 49 */                 throw new RuntimeException(e);
/*    */               } 
/*    */             }
/*    */           },  60000L);
/* 53 */     } catch (Exception e) {
/* 54 */       throw new RuntimeException("unable to link to database", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized Database getInstance() {
/* 60 */     if (instance == null) {
/* 61 */       instance = new Database();
/*    */     }
/* 63 */     return instance;
/*    */   }
/*    */   
/*    */   public Connection requestConnection() throws Exception {
/* 67 */     return (Connection)new ConnectionWrapper(this.db.getConnection());
/*    */   }
/*    */   
/*    */   public void releaseConnection(Connection connection) {
/* 71 */     this.db.releaseConnection(connection);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\implementation\io\Database.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */