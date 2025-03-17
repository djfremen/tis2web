/*     */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBMS
/*     */ {
/*     */   public static final String ORACLE = "Oracle";
/*     */   public static final String TRANSBASE = "Transbase";
/*  19 */   protected static Logger log = Logger.getLogger(DBMS.class);
/*     */   
/*     */   public static final String SELECT_SUBJECT = "SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE =? AND SIO_ID =?";
/*     */   
/*     */   public static final String SELECT_PROPERTIES = "SELECT SIO_ID, PROPERTY_TYPE, PROPERTY FROM SIO_PROPERTY";
/*     */   
/*     */   public static final String SELECT_PROPERTY = "SELECT PROPERTY_TYPE, PROPERTY FROM SIO_PROPERTY WHERE SIO_ID = ?";
/*     */   
/*  27 */   public static final String SELECT_LU = "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = " + SIOProperty.LU.ord() + " AND PROPERTY = ?";
/*     */   
/*  29 */   public static final String SELECT_CPR_Chapter = "SELECT a.SIO_ID FROM SIO_PROPERTY a, SIO_PROPERTY b WHERE a.SIO_ID = b.SIO_ID AND a.PROPERTY_TYPE = " + SIOProperty.ElectronicSystem.ord() + " AND a.PROPERTY = ? AND b.PROPERTY_TYPE=" + SIOProperty.CPRSection.ord();
/*     */   
/*  31 */   public static final String SELECT_WD = "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = " + SIOProperty.WD.ord() + " AND PROPERTY = ?";
/*     */   
/*  33 */   public static final String SELECT_PUBLICATION = "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = " + SIOProperty.PublicationID.ord() + " AND PROPERTY = ?";
/*     */   
/*     */   public static final String SELECT_RELATEDLU = "SELECT REL_ID FROM SIO_REL WHERE SIO_ID =? ";
/*     */   
/*     */   public static final String SELECT_IMAGE = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String SELECT_GRAPHIC = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String SELECT_DOCUMENT = "SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?";
/*     */   
/*     */   public static final String SELECT_DTCs = "SELECT TROUBLECODE FROM DTC";
/*     */   
/*     */   public static final String SELECT_INSPECTIONS = "SELECT TOC_PARENT, SIO_ID, VCR FROM INSPECTIONS WHERE TOC_PARENT =? ";
/*     */   
/*     */   protected Connection dbConnection;
/*     */   
/*     */   protected IDatabaseLink dblink;
/*     */   
/*     */   public Connection requestConnection() throws Exception {
/*  52 */     if (this.dblink != null) {
/*  53 */       return this.dblink.requestConnection();
/*     */     }
/*  55 */     return this.dbConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public DBMS(Connection db) {
/*  60 */     this.dbConnection = db;
/*     */   }
/*     */   
/*     */   public DBMS(IDatabaseLink dblink) {
/*  64 */     this.dblink = dblink;
/*     */   }
/*     */   
/*     */   public boolean isOracleDB() {
/*  68 */     if (this.dblink != null) {
/*  69 */       return (this.dblink.getDBMS() == 1);
/*     */     }
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSQL(String sql) {
/*  76 */     return sql;
/*     */   }
/*     */ 
/*     */   
/*     */   public void release(Connection conn, Statement stmt, ResultSet rs) {
/*     */     try {
/*  82 */       if (rs != null) {
/*  83 */         rs.close();
/*     */       }
/*  85 */     } catch (Exception x) {
/*  86 */       log.error("failed to close result set", x);
/*     */     } 
/*     */     
/*     */     try {
/*  90 */       if (stmt != null) {
/*  91 */         stmt.close();
/*     */       }
/*  93 */     } catch (Exception x) {
/*  94 */       log.error("failed to close statement", x);
/*     */     } 
/*     */     
/*     */     try {
/*  98 */       if (conn != null) {
/*  99 */         releaseConnection(conn);
/*     */       }
/* 101 */     } catch (Exception x) {
/* 102 */       log.error("failed to release connection", x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void releaseConnection(Connection connection) {
/* 108 */     if (this.dblink != null)
/* 109 */       this.dblink.releaseConnection(connection); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */