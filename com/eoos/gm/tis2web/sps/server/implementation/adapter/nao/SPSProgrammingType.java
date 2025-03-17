/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
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
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSProgrammingType extends SPSProgrammingType {
/*     */   private static final long serialVersionUID = 1L;
/*  20 */   private static Logger log = Logger.getLogger(SPSProgrammingType.class);
/*     */   
/*  22 */   public static final Integer NORMAL = Integer.valueOf(1);
/*     */   
/*  24 */   public static final Integer RECONFIGURE = Integer.valueOf(8);
/*     */   
/*  26 */   public static final Integer INFORMATION_ONLY = Integer.valueOf(99);
/*     */   
/*     */   public static final class StaticData {
/*     */     private Map descriptions;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  32 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  33 */       this.descriptions = new HashMap<Object, Object>();
/*  34 */       Connection conn = null;
/*  35 */       DBMS.PreparedStatement stmt = null;
/*  36 */       ResultSet rs = null;
/*     */       try {
/*  38 */         conn = dblink.requestConnection();
/*  39 */         String sql = DBMS.getSQL(dblink, "SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description");
/*  40 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  41 */         rs = stmt.executeQuery();
/*  42 */         while (rs.next()) {
/*  43 */           Integer id = Integer.valueOf(rs.getInt(2));
/*  44 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  45 */           if (description == null) {
/*  46 */             description = new SPSDescription();
/*  47 */             this.descriptions.put(id, description);
/*     */           } 
/*  49 */           String lg = rs.getString(1);
/*  50 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  51 */           description.add(locale, DBMS.getString(dblink, locale, rs, 3));
/*     */         } 
/*  53 */       } catch (Exception e) {
/*  54 */         this.descriptions = null;
/*  55 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  58 */           if (rs != null) {
/*  59 */             rs.close();
/*     */           }
/*  61 */           if (stmt != null) {
/*  62 */             stmt.close();
/*     */           }
/*  64 */           if (this.descriptions == null && conn != null) {
/*  65 */             dblink.releaseConnection(conn);
/*     */           }
/*  67 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/*  71 */         String sql = DBMS.getSQL(dblink, "SELECT Language_Code, Method_Id, Description FROM PROM_Prog_Method_Description");
/*  72 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  73 */         rs = stmt.executeQuery();
/*  74 */         while (rs.next()) {
/*  75 */           Integer id = Integer.valueOf(rs.getInt(2));
/*  76 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  77 */           if (description == null) {
/*  78 */             description = new SPSDescription();
/*  79 */             this.descriptions.put(id, description);
/*     */           } 
/*  81 */           String lg = rs.getString(1);
/*  82 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  83 */           description.add(locale, DBMS.getString(dblink, locale, rs, 3));
/*     */         } 
/*  85 */       } catch (Exception e) {
/*  86 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  89 */           if (rs != null) {
/*  90 */             rs.close();
/*     */           }
/*  92 */           if (stmt != null) {
/*  93 */             stmt.close();
/*     */           }
/*  95 */           if (conn != null) {
/*  96 */             dblink.releaseConnection(conn);
/*     */           }
/*  98 */         } catch (Exception x) {
/*  99 */           SPSProgrammingType.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getDescriptions() {
/* 109 */       return this.descriptions;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 113 */       synchronized (adapter.getSyncObject()) {
/* 114 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 115 */         if (instance == null) {
/* 116 */           instance = new StaticData(adapter);
/* 117 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 119 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static List getProgrammingTypes(SPSLanguage language, int encoding, SPSSchemaAdapterNAO adapter) {
/* 125 */     List<SPSProgrammingType> result = new ArrayList();
/* 126 */     if (encoding == INFORMATION_ONLY.intValue()) {
/* 127 */       SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(INFORMATION_ONLY);
/* 128 */       result.add(new SPSProgrammingType(INFORMATION_ONLY, description.get(language)));
/*     */     } else {
/* 130 */       for (int i = 0; i < 8; i++) {
/* 131 */         if ((encoding & 1 << i) > 0) {
/* 132 */           Integer mid = Integer.valueOf(1 << i);
/* 133 */           if (!mid.equals(RECONFIGURE)) {
/*     */ 
/*     */             
/* 136 */             SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(mid);
/* 137 */             if (description == null) {
/* 138 */               result.add(new SPSProgrammingType(mid, "prog-method=" + mid));
/*     */             }
/* 140 */             else if (description.get(language) == null) {
/* 141 */               result.add(new SPSProgrammingType(mid, description.get(SPSLanguage.getLanguage(Locale.US, adapter))));
/*     */             } else {
/* 143 */               result.add(new SPSProgrammingType(mid, description.get(language)));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 149 */     return result;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingType(Integer id, String description) {
/* 153 */     this.id = id;
/* 154 */     this.description = description;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 158 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSProgrammingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */