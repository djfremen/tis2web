/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SPSVehicleCategory extends SPSBaseCategory {
/*     */   protected int noPreEval;
/*     */   
/*     */   public SPSVehicleCategory(SPSLanguage language, int vehicle, SPSOptionCategory category, SPSOptionGroup group, int order, int noPreEval) {
/*  14 */     super(language, vehicle, category, group, order);
/*  15 */     this.noPreEval = noPreEval;
/*     */   }
/*     */   
/*     */   public int getVehicle() {
/*  19 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getNoPreEval() {
/*  23 */     return this.noPreEval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List loadVehicleCategories(IDatabaseLink db, SPSLanguage language, List<Integer> vehicles, SPSSchemaAdapterGME adapter) throws Exception {
/*  31 */     if (vehicles == null || vehicles.size() == 0) {
/*  32 */       return null;
/*     */     }
/*  34 */     List<SPSVehicleCategory> categories = new ArrayList();
/*  35 */     Connection conn = null;
/*  36 */     DBMS.PreparedStatement stmt = null;
/*  37 */     ResultSet rs = null;
/*     */     try {
/*  39 */       conn = db.requestConnection();
/*  40 */       int clauseListMax = Integer.MAX_VALUE;
/*  41 */       if (db.getDBMS() == 2)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  48 */         clauseListMax = 256;
/*     */       }
/*     */       
/*  51 */       int loopCnt = vehicles.size() / clauseListMax;
/*  52 */       int deltaSize = vehicles.size() % clauseListMax;
/*  53 */       if (deltaSize != 0) {
/*  54 */         loopCnt++;
/*     */       }
/*  56 */       int stmtListSize = -1;
/*  57 */       int vehOffset = 0;
/*     */       
/*  59 */       for (int l = 0; l < loopCnt; l++) {
/*     */         
/*  61 */         if (l < loopCnt - 1) {
/*  62 */           stmtListSize = clauseListMax;
/*     */         } else {
/*  64 */           stmtListSize = (deltaSize != 0) ? deltaSize : clauseListMax;
/*     */         } 
/*     */         
/*  67 */         String sql = DBMS.getSQL(db, "SELECT DISTINCT VehicleID, CategoryCode, OptionGroup, OptionOrder, NoPreEval FROM SPS_Option o WHERE VehicleID IN (#list#)ORDER BY OptionOrder", stmtListSize);
/*  68 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  69 */         for (int i = 1; i <= stmtListSize; i++) {
/*  70 */           stmt.setInt(i, ((Integer)vehicles.get(vehOffset + i - 1)).intValue());
/*     */         }
/*  72 */         vehOffset += stmtListSize;
/*  73 */         rs = stmt.executeQuery();
/*  74 */         while (rs.next()) {
/*  75 */           int vehicle = rs.getInt(1);
/*  76 */           String categoryID = rs.getString(2).trim();
/*  77 */           SPSOptionCategory category = SPSOptionCategory.getOptionCategory(categoryID, adapter);
/*  78 */           int groupID = rs.getInt(3);
/*  79 */           SPSOptionGroup group = SPSOptionGroup.getOptionGroup(categoryID, groupID, adapter);
/*  80 */           int order = rs.getInt(4);
/*  81 */           int noPreEval = rs.getInt(5);
/*  82 */           categories.add(new SPSVehicleCategory(language, vehicle, category, group, order, noPreEval));
/*     */         } 
/*     */       } 
/*  85 */     } catch (Exception e) {
/*  86 */       throw e;
/*     */     } finally {
/*     */       try {
/*  89 */         if (rs != null) {
/*  90 */           rs.close();
/*     */         }
/*  92 */         if (stmt != null) {
/*  93 */           stmt.close();
/*     */         }
/*  95 */         if (conn != null) {
/*  96 */           db.releaseConnection(conn);
/*     */         }
/*  98 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 101 */     return (categories.size() == 0) ? null : categories;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSVehicleCategory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */