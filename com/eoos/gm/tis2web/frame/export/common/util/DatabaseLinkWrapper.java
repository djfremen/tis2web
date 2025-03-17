/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.sql.Connection;
/*    */ 
/*    */ public class DatabaseLinkWrapper implements IDatabaseLink {
/*    */   public void close() {
/*  8 */     this.databaseLink.close();
/*    */   }
/*    */   private DatabaseLink databaseLink;
/*    */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*    */   public boolean equals(Object obj) {
/* 13 */     return this.databaseLink.equals(obj);
/*    */   }
/*    */   
/*    */   public String getDatabaseLinkDescription() {
/* 17 */     return this.databaseLink.getDatabaseLinkDescription();
/*    */   }
/*    */   
/*    */   public String getDatabaseLinkInformation() {
/* 21 */     return this.databaseLink.getDatabaseLinkInformation();
/*    */   }
/*    */   
/*    */   public String getDatabaseLinkInformation(Connection connection) {
/* 25 */     return this.databaseLink.getDatabaseLinkInformation(connection);
/*    */   }
/*    */   
/*    */   public int getDBMS() {
/* 29 */     return this.databaseLink.getDBMS();
/*    */   }
/*    */   
/*    */   public void handleError(Connection connection, Exception e) {
/* 33 */     this.databaseLink.handleError(connection, e);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 37 */     return this.databaseLink.hashCode();
/*    */   }
/*    */   
/*    */   public boolean isDataSource() {
/* 41 */     return this.databaseLink.isDataSource();
/*    */   }
/*    */   
/*    */   public void logException(Connection connection, Exception e) {
/* 45 */     this.databaseLink.logException(connection, e);
/*    */   }
/*    */   
/*    */   public void releaseConnection(Connection connection) {
/* 49 */     this.databaseLink.releaseConnection(connection);
/*    */   }
/*    */   
/*    */   public Connection requestConnection() throws Exception {
/* 53 */     return this.databaseLink.requestConnection();
/*    */   }
/*    */   
/*    */   public void testConnection(Connection connection) throws Exception {
/* 57 */     this.databaseLink.testConnection(connection);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     return this.databaseLink.toString();
/*    */   }
/*    */   
/*    */   public String translate(String sql) {
/* 65 */     return this.databaseLink.translate(sql);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DatabaseLinkWrapper(DatabaseLink dbLink) {
/* 71 */     this.databaseLink = dbLink;
/*    */   }
/*    */   
/*    */   public Connection requestConnection_ConfigurationService() throws Exception {
/* 75 */     return this.databaseLink.requestConnection_ConfigurationService();
/*    */   }
/*    */   
/*    */   public void releaseConnection_ConfigurationService(Connection connection) {
/* 79 */     this.databaseLink.releaseConnection_ConfigurationService(connection);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DatabaseLinkWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */