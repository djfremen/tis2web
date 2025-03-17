/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Properties;
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
/*    */ public abstract class DBReader
/*    */ {
/*    */   public static void setStatConn(IDatabaseLink dbr) throws Exception {
/* 25 */     statConn = dbr.requestConnection();
/*    */   }
/*    */   
/* 28 */   private static Connection statConn = null;
/*    */   private IDatabaseLink db;
/*    */   
/*    */   public DBReader(String dbProp) throws Exception {
/* 32 */     this.db = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), dbProp);
/*    */   }
/*    */ 
/*    */   
/*    */   public DBReader(String file, String database) throws Exception {
/* 37 */     Properties props = new Properties();
/* 38 */     InputStream ip = getClass().getClassLoader().getResourceAsStream(file);
/* 39 */     props.load(ip);
/* 40 */     String driver = props.getProperty(database + ".drv");
/* 41 */     String url = props.getProperty(database + ".url");
/* 42 */     String user = props.getProperty(database + ".usr");
/* 43 */     String password = props.getProperty(database + ".pwd");
/* 44 */     this.db = (IDatabaseLink)new DatabaseLink(driver, url, user, password, false, 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public DBReader(IDatabaseLink db) {
/* 49 */     this.db = db;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(String sql) throws Exception {
/* 54 */     Connection conn = null;
/* 55 */     PreparedStatement stmt = null;
/* 56 */     ResultSet rs = null;
/*    */     try {
/* 58 */       conn = (statConn == null) ? this.db.requestConnection() : statConn;
/*    */       
/* 60 */       stmt = conn.prepareStatement(sql);
/* 61 */       setParams(stmt);
/* 62 */       rs = stmt.executeQuery();
/* 63 */       while (more() && rs.next()) {
/* 64 */         readRow(rs);
/*    */       }
/* 66 */     } catch (Exception e) {
/* 67 */       throw e;
/*    */     } finally {
/*    */       try {
/* 70 */         if (rs != null) {
/* 71 */           rs.close();
/*    */         }
/* 73 */         if (stmt != null) {
/* 74 */           stmt.close();
/*    */         }
/* 76 */         if (conn != null && statConn == null) {
/* 77 */           this.db.releaseConnection(conn);
/*    */         }
/* 79 */       } catch (Exception x) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean more() {
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void readRow(ResultSet paramResultSet) throws Exception;
/*    */ 
/*    */ 
/*    */   
/*    */   public void setParams(PreparedStatement stmt) throws Exception {}
/*    */ 
/*    */   
/*    */   public IDatabaseLink getDb() {
/* 99 */     return this.db;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\DBReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */