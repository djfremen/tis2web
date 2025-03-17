/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SPSSecurityCode
/*     */ {
/*  12 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   
/*     */   private SPSSchemaAdapterGME adapter;
/*     */   
/*     */   private boolean databaseSecurityCodeSupport = true;
/*     */   
/*     */   public static SPSSecurityCode getInstance(SPSSchemaAdapterGME adapter) {
/*  19 */     synchronized (adapter.getSyncObject()) {
/*  20 */       SPSSecurityCode instance = (SPSSecurityCode)adapter.getObject(SPSSecurityCode.class);
/*  21 */       if (instance == null) {
/*  22 */         instance = new SPSSecurityCode(adapter);
/*  23 */         instance.init();
/*  24 */         adapter.storeObject(SPSSecurityCode.class, instance);
/*     */       } 
/*  26 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   private SPSSecurityCode(SPSSchemaAdapterGME adapter) {
/*  31 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public void init() {
/*     */     try {
/*  36 */       isSecurityCodeRequired(Integer.valueOf(0));
/*  37 */     } catch (Exception e) {
/*  38 */       this.databaseSecurityCodeSupport = false;
/*  39 */       log.info("No support for SPS-IO Security Code.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSecurityCodeRequired(Integer vehicleID) throws Exception {
/*  44 */     if (!this.databaseSecurityCodeSupport) {
/*  45 */       return false;
/*     */     }
/*  47 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*  48 */     Connection conn = null;
/*  49 */     DBMS.PreparedStatement stmt = null;
/*  50 */     ResultSet rs = null;
/*     */     try {
/*  52 */       conn = dblink.requestConnection();
/*  53 */       String sql = DBMS.getSQL(dblink, "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?");
/*  54 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  55 */       stmt.setInt(1, vehicleID.intValue());
/*  56 */       rs = stmt.executeQuery();
/*  57 */       if (rs.next()) {
/*  58 */         return true;
/*     */       }
/*  60 */       return false;
/*  61 */     } catch (Exception e) {
/*  62 */       throw e;
/*     */     } finally {
/*     */       try {
/*  65 */         if (rs != null) {
/*  66 */           rs.close();
/*     */         }
/*  68 */         if (stmt != null) {
/*  69 */           stmt.close();
/*     */         }
/*  71 */         if (conn != null) {
/*  72 */           dblink.releaseConnection(conn);
/*     */         }
/*  74 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSecurityCode(String vin) throws Exception {
/*  80 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*  81 */     Connection conn = null;
/*  82 */     DBMS.PreparedStatement stmt = null;
/*  83 */     ResultSet rs = null;
/*     */     try {
/*  85 */       conn = dblink.requestConnection();
/*  86 */       String sql = DBMS.getSQL(dblink, "SELECT SecurityCode FROM SPS_SecurityCode WHERE VIN = ?");
/*  87 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  88 */       stmt.setString(1, vin);
/*  89 */       rs = stmt.executeQuery();
/*  90 */       if (rs.next()) {
/*  91 */         return rs.getString("SecurityCode");
/*     */       }
/*  93 */       return null;
/*  94 */     } catch (Exception e) {
/*  95 */       throw e;
/*     */     } finally {
/*     */       try {
/*  98 */         if (rs != null) {
/*  99 */           rs.close();
/*     */         }
/* 101 */         if (stmt != null) {
/* 102 */           stmt.close();
/*     */         }
/* 104 */         if (conn != null) {
/* 105 */           dblink.releaseConnection(conn);
/*     */         }
/* 107 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSecurityCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */