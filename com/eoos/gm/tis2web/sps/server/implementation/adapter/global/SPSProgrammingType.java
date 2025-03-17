/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSProgrammingType
/*     */   extends SPSProgrammingType {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   public static final Integer NORMAL = Integer.valueOf(1);
/*     */   
/*  21 */   public static final Integer RECONFIGURE = Integer.valueOf(8);
/*     */   
/*  23 */   public static final Integer INFORMATION_ONLY = Integer.valueOf(99);
/*     */   
/*     */   public static final class StaticData {
/*     */     private Map descriptions;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  29 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  30 */       this.descriptions = new HashMap<Object, Object>();
/*  31 */       Connection conn = null;
/*  32 */       DBMS.PreparedStatement stmt = null;
/*  33 */       ResultSet rs = null;
/*     */       try {
/*  35 */         conn = dblink.requestConnection();
/*  36 */         String sql = DBMS.getSQL(dblink, "SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description");
/*  37 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  38 */         rs = stmt.executeQuery();
/*  39 */         while (rs.next()) {
/*  40 */           Integer id = Integer.valueOf(rs.getInt(2));
/*  41 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  42 */           if (description == null) {
/*  43 */             description = new SPSDescription();
/*  44 */             this.descriptions.put(id, description);
/*     */           } 
/*  46 */           String lg = rs.getString(1);
/*  47 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  48 */           description.add(locale, DBMS.getString(dblink, locale, rs, 3));
/*     */         } 
/*  50 */       } catch (Exception e) {
/*  51 */         this.descriptions = null;
/*  52 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  55 */           if (rs != null) {
/*  56 */             rs.close();
/*     */           }
/*  58 */           if (stmt != null) {
/*  59 */             stmt.close();
/*     */           }
/*  61 */           if (this.descriptions == null && conn != null) {
/*  62 */             dblink.releaseConnection(conn);
/*     */           }
/*  64 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getDescriptions() {
/*  74 */       return this.descriptions;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  78 */       synchronized (adapter.getSyncObject()) {
/*  79 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  80 */         if (instance == null) {
/*  81 */           instance = new StaticData(adapter);
/*  82 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  84 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static List getProgrammingTypes(SPSLanguage language, int encoding, SPSSchemaAdapterGlobal adapter) {
/*  90 */     List<SPSProgrammingType> result = new ArrayList();
/*  91 */     if (encoding == INFORMATION_ONLY.intValue()) {
/*  92 */       SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(INFORMATION_ONLY);
/*  93 */       result.add(new SPSProgrammingType(INFORMATION_ONLY, description.get(language)));
/*     */     } else {
/*  95 */       for (int i = 0; i < 8; i++) {
/*  96 */         if ((encoding & 1 << i) > 0) {
/*  97 */           Integer mid = Integer.valueOf(1 << i);
/*  98 */           if (!mid.equals(RECONFIGURE)) {
/*     */ 
/*     */             
/* 101 */             SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(mid);
/* 102 */             if (description == null) {
/* 103 */               result.add(new SPSProgrammingType(mid, "prog-method=" + mid));
/*     */             } else {
/* 105 */               result.add(new SPSProgrammingType(mid, description.get(language)));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 110 */     }  return result;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingType(Integer id, String description) {
/* 114 */     this.id = id;
/* 115 */     this.description = description;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 119 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSProgrammingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */