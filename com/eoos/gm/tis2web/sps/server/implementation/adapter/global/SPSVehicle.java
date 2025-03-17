/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVIN;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVehicle;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSVehicle extends SPSVehicle {
/*  17 */   protected static final Integer EMISSION_OPTION = Integer.valueOf(3);
/*     */   
/*  19 */   protected static final Integer BCM_OPTION = Integer.valueOf(16);
/*     */   
/*     */   protected Map groups;
/*     */   
/*     */   protected List mandatory;
/*     */   
/*     */   protected SPSModel model;
/*     */   
/*     */   protected Map optionBytes;
/*     */   
/*     */   protected Map adaptiveBytes;
/*     */   
/*     */   public SPSVehicle(SPSModel model, SPSVIN vin) {
/*  32 */     super(vin);
/*  33 */     this.model = model;
/*     */   }
/*     */   
/*     */   SPSOptionGroup getOptionGroup(SPSSession session, String rpo, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  37 */     if (this.groups == null) {
/*  38 */       loadOptionGroups();
/*     */     }
/*  40 */     Integer groupID = (Integer)this.groups.get(rpo);
/*  41 */     if (groupID == null) {
/*  42 */       return null;
/*     */     }
/*  44 */     SPSOptionGroup group = SPSOptionGroup.getOptionGroup((SPSLanguage)session.getLanguage(), groupID, adapter);
/*  45 */     if (group == null) {
/*  46 */       return null;
/*     */     }
/*  48 */     Iterator<Map.Entry> it = this.groups.entrySet().iterator();
/*  49 */     while (it.hasNext()) {
/*  50 */       Map.Entry entry = it.next();
/*  51 */       if (entry.getValue().equals(groupID)) {
/*  52 */         SPSOption option = SPSOption.getRPO((SPSLanguage)session.getLanguage(), (String)entry.getKey(), adapter);
/*  53 */         if (option != null) {
/*  54 */           option.setType(group);
/*  55 */           group.add(option);
/*     */         } 
/*     */       } 
/*     */     } 
/*  59 */     return group;
/*     */   }
/*     */   
/*     */   List getMandatoryOptions() {
/*  63 */     return this.mandatory;
/*     */   }
/*     */   
/*     */   protected void loadOptionGroups() throws Exception {
/*  67 */     this.groups = new HashMap<Object, Object>();
/*  68 */     Connection conn = null;
/*  69 */     DBMS.PreparedStatement stmt = null;
/*  70 */     ResultSet rs = null;
/*     */     try {
/*  72 */       conn = this.model.getDatabaseLink().requestConnection();
/*  73 */       String sql = DBMS.getSQL(this.model.getDatabaseLink(), "SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/*  74 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  75 */       stmt.setString(1, DBMS.toString(getVIN().getModelYear()));
/*  76 */       stmt.setString(2, DBMS.toString(getVIN().getMake()));
/*  77 */       stmt.setString(3, DBMS.toString(getVIN().getLine()));
/*  78 */       stmt.setString(4, DBMS.toString(getVIN().getSeries()));
/*  79 */       stmt.setString(5, DBMS.toString(getVIN().getEngine()));
/*  80 */       rs = stmt.executeQuery();
/*  81 */       while (rs.next()) {
/*  82 */         String rpo = rs.getString(1).trim();
/*  83 */         Integer group = Integer.valueOf(rs.getInt(2));
/*  84 */         this.groups.put(rpo, group);
/*  85 */         if (group.equals(EMISSION_OPTION) || group.equals(BCM_OPTION)) {
/*  86 */           if (this.mandatory == null) {
/*  87 */             this.mandatory = new ArrayList();
/*     */           }
/*  89 */           this.mandatory.add(new PairImpl(group.toString(), rpo));
/*     */         } 
/*     */       } 
/*  92 */     } catch (Exception e) {
/*  93 */       throw e;
/*     */     } finally {
/*     */       try {
/*  96 */         if (rs != null) {
/*  97 */           rs.close();
/*     */         }
/*  99 */         if (stmt != null) {
/* 100 */           stmt.close();
/*     */         }
/* 102 */         if (conn != null) {
/* 103 */           this.model.getDatabaseLink().releaseConnection(conn);
/*     */         }
/* 105 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSVehicle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */