/*    */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import java.sql.Connection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBMS
/*    */ {
/*    */   public static final String ORACLE = "Oracle";
/*    */   public static final String TRANSBASE = "Transbase";
/*    */   public static final String SQL_LOAD_WINLANGUAGES = "SELECT DISTINCT LanguageAcronym FROM WinLanguages";
/*    */   public static final String SQL_LOAD_LANG_MAP = "SELECT DISTINCT wl.LanguageAcronym, b.lc FROM WinLanguages wl, WinLang2LC b WHERE wl.LCID = b.LCID";
/*    */   public static final String SQL_LOAD_SMC_MAP = "SELECT smc, smd FROM SalesMakes";
/*    */   public static final String SQL_LOAD_SM_MC_MAP = "SELECT smc, vd, mc FROM AWarbeitskataloge";
/*    */   public static final String SQL_LOAD_SM_MC_MAP_ALGO = "SELECT smc, vd, mc, algo_code FROM AWarbeitskataloge";
/*    */   protected Connection dbConnection;
/*    */   protected IDatabaseLink dblink;
/*    */   
/*    */   public Connection requestConnection() throws Exception {
/* 24 */     if (this.dblink != null) {
/* 25 */       return this.dblink.requestConnection();
/*    */     }
/* 27 */     return this.dbConnection;
/*    */   }
/*    */ 
/*    */   
/*    */   public DBMS(Connection db) {
/* 32 */     this.dbConnection = db;
/*    */   }
/*    */   
/*    */   public DBMS(IDatabaseLink dblink) {
/* 36 */     this.dblink = dblink;
/*    */   }
/*    */   
/*    */   public IDatabaseLink getDatabaseLink() {
/* 40 */     return this.dblink;
/*    */   }
/*    */   
/*    */   public synchronized void releaseConnection(Connection connection) {
/* 44 */     if (this.dblink != null) {
/* 45 */       this.dblink.releaseConnection(connection);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean reconnect() {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSQL(String sql) {
/* 55 */     return sql;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */