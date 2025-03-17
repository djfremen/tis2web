/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import java.sql.Connection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBMS
/*    */ {
/*    */   public static final String ORACLE = "Oracle";
/*    */   public static final String TRANSBASE = "Transbase";
/*    */   public static final String SELECT_VCRDOMAIN = "SELECT DOMAIN_ID, DOMAIN_NAME, DOMAIN_LABEL, LANGUAGE_DEPENDENT FROM VCRDOMAIN";
/*    */   public static final String SELECT_VCRVALUE = "SELECT VALUE_ID, VALUE_LABEL FROM VCRVALUE WHERE DOMAIN_ID = ?";
/*    */   public static final String SELECT_VCRLABEL = "SELECT LANGUAGE_ID, LABEL_ID, LABEL FROM VCRLABEL";
/*    */   public static final String SELECT_VCRCONFIG = "SELECT CONFIG_ID, SEQ_NO, DOMAIN_ID, VALUE_ID FROM VCRCONFIG ORDER BY CONFIG_ID, SEQ_NO, DOMAIN_ID";
/*    */   public static final String SELECT_VCRASSOC = "SELECT CONFIG_ID, DOMAIN_ID, VALUE_ID FROM VCRASSOC ORDER BY CONFIG_ID, DOMAIN_ID";
/*    */   public static final String SELECT_VCRCONSTRAINT = "SELECT CONSTRAINT_ID, SEQ_NO, DOMAIN_ID, VALUE_ID FROM VCRCONSTRAINT ORDER BY CONSTRAINT_ID, SEQ_NO, DOMAIN_ID";
/*    */   public static final String SELECT_VCRMAPPINGS = "SELECT DOMAIN_ID, VALUE_ID, DOMAIN_RELID, VALUE_RELID FROM VCRMAPPING";
/*    */   public static final String SELECT_VINSTRUCTURE = "SELECT STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, VIN_PATTERN, DOMAIN_ID, POSITION_FROM, POSITION_TO FROM VINSTRUCTURE ORDER BY STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID";
/*    */   public static final String LEGACY_VINSTRUCTURE = "SELECT STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID, POSITION_FROM, POSITION_TO FROM VINSTRUCTURE ORDER BY STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID";
/*    */   public static final String SELECT_VCRVIN = "SELECT STRUCTURE_ID, VIN_ELEMENT, DOMAIN_ID, VALUE_ID FROM VCRVIN";
/*    */   protected IDatabaseLink dbLink;
/*    */   
/*    */   public Connection getConnection() {
/*    */     try {
/* 37 */       return this.dbLink.requestConnection();
/* 38 */     } catch (Exception e) {
/* 39 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void releaseConnection(Connection connection) {
/* 45 */     this.dbLink.releaseConnection(connection);
/*    */   }
/*    */   
/*    */   public DBMS(IDatabaseLink dblink) {
/* 49 */     this.dbLink = dblink;
/*    */   }
/*    */   
/*    */   public String getSQL(String sql) {
/* 53 */     return sql;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */