/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
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
/*     */   public static final String SELECT_ROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = ?";
/*  20 */   public static final String SELECT_TSBROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.TSB.ord());
/*     */   
/*  22 */   public static final String SELECT_SITROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.SIT.ord());
/*     */   
/*  24 */   public static final String SELECT_SCDS2GTROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.SCDS2GT.ord());
/*     */   
/*  26 */   public static final String SELECT_SCDSROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.SCDS.ord());
/*     */   
/*  28 */   public static final String SELECT_SCTROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.WIS_SCT.ord());
/*     */   
/*  30 */   public static final String SELECT_NTFROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.NTF.ord());
/*     */   
/*  32 */   public static final String SELECT_PAGROOT = "SELECT TOC_ID, TOC_TYPE FROM TOC WHERE TOC_PARENT = " + (-1 * CTOCDomain.PRUNED_ASSEMBLY_GROUPS.ord());
/*     */   
/*     */   public static final String SELECT_ROOTS = "SELECT TOC_ID, TOC_TYPE, TOC_CHILDREN, TOC_CONTENT, TOC_VCR FROM TOC WHERE TOC_PARENT = ?";
/*     */   
/*     */   public static final String SELECT_STRUCTURE = "SELECT TOC_ID,TOC_LABEL,TOC_PARENT,TOC_ORDER,TOC_TYPE,TOC_CHILDREN,TOC_CONTENT,TOC_VCR FROM TOC WHERE TOC_PARENT <> -1 ORDER BY TOC_PARENT";
/*     */   
/*     */   public static final String SELECT_CHILDREN = "SELECT TOC_ID,TOC_LABEL,TOC_ORDER,TOC_TYPE,TOC_CHILDREN,TOC_CONTENT,TOC_VCR FROM TOC WHERE TOC_PARENT = ? ";
/*     */   
/*     */   public static final String SELECT_PROPERTIES = "SELECT TOC_ID,PROPERTY_TYPE,PROPERTY FROM TOC_PROPERTY";
/*     */   
/*     */   public static final String SELECT_REFERENCES = "SELECT TOC_ID FROM TOC_REFERENCES WHERE REFERENCE_TYPE = ? AND REFERENCE_KEY=?";
/*     */   
/*     */   public static final String SELECT_PROPERTY = "SELECT PROPERTY_TYPE,PROPERTY FROM TOC_PROPERTY WHERE TOC_ID = ? ";
/*     */   
/*     */   public static final String SELECT_CONTENTS = "SELECT TOC_ID,CONTENT_TYPE,CONTENT_ID,CONTENT_ORDER,CONTENT_PROPERTY,CONTENT_VCR FROM TOC_CONTENT";
/*     */   
/*     */   public static final String SELECT_CONTENT = "SELECT CONTENT_TYPE,CONTENT_ID,CONTENT_ORDER,CONTENT_PROPERTY,CONTENT_VCR FROM TOC_CONTENT WHERE TOC_ID = ?";
/*     */   
/*     */   public static final String SELECT_LABELS = "SELECT LOCALE, LABEL_ID, LABEL FROM TOC_LABEL";
/*     */   
/*     */   public static final String SELECT_LABEL = "SELECT LABEL FROM TOC_LABEL ";
/*     */   
/*     */   protected IDatabaseLink db;
/*     */   protected Connection dbConnection;
/*  56 */   protected static Logger log = Logger.getLogger(DBMS.class);
/*     */   
/*     */   protected Connection acquireConnection(int trial) throws Exception {
/*     */     try {
/*  60 */       return this.db.requestConnection();
/*  61 */     } catch (Exception x) {
/*  62 */       wait((++trial * 1000));
/*  63 */       if (trial < 3) {
/*  64 */         return acquireConnection(trial);
/*     */       }
/*  66 */       return this.db.requestConnection();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Connection requestConnection() throws Exception {
/*  72 */     if (this.db != null) {
/*  73 */       return acquireConnection(0);
/*     */     }
/*  75 */     return this.dbConnection;
/*     */   }
/*     */ 
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
/* 108 */     if (this.db != null) {
/* 109 */       this.db.releaseConnection(connection);
/*     */     }
/*     */   }
/*     */   
/*     */   public DBMS(IDatabaseLink db) {
/* 114 */     this.db = db;
/*     */   }
/*     */   
/*     */   public DBMS(Connection db) {
/* 118 */     this.dbConnection = db;
/*     */   }
/*     */   
/*     */   public String getSQL(String sql) {
/* 122 */     return sql;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */