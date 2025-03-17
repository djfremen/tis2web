/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ 
/*    */ public class SPSSecurityCode
/*    */ {
/*    */   private SPSSchemaAdapterGlobal adapter;
/*    */   
/*    */   public static SPSSecurityCode getInstance(SPSSchemaAdapterGlobal adapter) {
/* 13 */     synchronized (adapter.getSyncObject()) {
/* 14 */       SPSSecurityCode instance = (SPSSecurityCode)adapter.getObject(SPSSecurityCode.class);
/* 15 */       if (instance == null) {
/* 16 */         instance = new SPSSecurityCode(adapter);
/* 17 */         instance.init();
/* 18 */         adapter.storeObject(SPSSecurityCode.class, instance);
/*    */       } 
/* 20 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   private SPSSecurityCode(SPSSchemaAdapterGlobal adapter) {
/* 25 */     this.adapter = adapter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */   
/*    */   public String getSecurityCode(String vin) throws Exception {
/* 32 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/* 33 */     Connection conn = null;
/* 34 */     DBMS.PreparedStatement stmt = null;
/* 35 */     ResultSet rs = null;
/*    */     try {
/* 37 */       conn = dblink.requestConnection();
/* 38 */       String sql = DBMS.getSQL(dblink, "SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?");
/* 39 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 40 */       stmt.setString(1, vin);
/* 41 */       rs = stmt.executeQuery();
/* 42 */       if (rs.next()) {
/* 43 */         return rs.getString("SecCode");
/*    */       }
/* 45 */       return null;
/* 46 */     } catch (Exception e) {
/* 47 */       throw e;
/*    */     } finally {
/*    */       try {
/* 50 */         if (rs != null) {
/* 51 */           rs.close();
/*    */         }
/* 53 */         if (stmt != null) {
/* 54 */           stmt.close();
/*    */         }
/* 56 */         if (conn != null) {
/* 57 */           dblink.releaseConnection(conn);
/*    */         }
/* 59 */       } catch (Exception x) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSSecurityCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */