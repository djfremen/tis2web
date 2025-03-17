/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SPSSecurityCode
/*    */ {
/* 11 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*    */   
/*    */   private SPSSchemaAdapterNAO adapter;
/*    */   private static IDatabaseLink quickUpdateTCSC;
/*    */   
/*    */   public static SPSSecurityCode getInstance(SPSSchemaAdapterNAO adapter) {
/* 17 */     synchronized (adapter.getSyncObject()) {
/* 18 */       SPSSecurityCode instance = (SPSSecurityCode)adapter.getObject(SPSSecurityCode.class);
/* 19 */       if (instance == null) {
/* 20 */         instance = new SPSSecurityCode(adapter);
/* 21 */         instance.init();
/* 22 */         adapter.storeObject(SPSSecurityCode.class, instance);
/*    */       } 
/* 24 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   private SPSSecurityCode(SPSSchemaAdapterNAO adapter) {
/* 29 */     this.adapter = adapter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */   
/*    */   public String getSecurityCode(String vin) throws Exception {
/*    */     try {
/* 37 */       if (quickUpdateTCSC != null) {
/* 38 */         String securityCode = getSecurityCode(quickUpdateTCSC, vin);
/* 39 */         if (securityCode != null) {
/* 40 */           return securityCode;
/*    */         }
/*    */       } 
/* 43 */     } catch (Exception e) {
/* 44 */       log.error("failed to query security code shadow table", e);
/*    */     } 
/* 46 */     return getSecurityCode(this.adapter.getDatabaseLink(), vin);
/*    */   }
/*    */   
/*    */   private String getSecurityCode(IDatabaseLink dblink, String vin) throws Exception {
/* 50 */     Connection conn = null;
/* 51 */     DBMS.PreparedStatement stmt = null;
/* 52 */     ResultSet rs = null;
/*    */     try {
/* 54 */       conn = dblink.requestConnection();
/* 55 */       String sql = DBMS.getSQL(dblink, "SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?");
/* 56 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 57 */       stmt.setString(1, vin);
/* 58 */       rs = stmt.executeQuery();
/* 59 */       if (rs.next()) {
/* 60 */         return rs.getString("SecCode");
/*    */       }
/* 62 */       return null;
/* 63 */     } catch (Exception e) {
/* 64 */       throw e;
/*    */     } finally {
/*    */       try {
/* 67 */         if (rs != null) {
/* 68 */           rs.close();
/*    */         }
/* 70 */         if (stmt != null) {
/* 71 */           stmt.close();
/*    */         }
/* 73 */         if (conn != null) {
/* 74 */           dblink.releaseConnection(conn);
/*    */         }
/* 76 */       } catch (Exception x) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void initQuickUpdate(SPSSchemaAdapterNAO schemaAdapterNAO, IDatabaseLink db) {
/* 82 */     quickUpdateTCSC = db;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSSecurityCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */