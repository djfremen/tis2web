/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSType4Data
/*     */   extends ArrayList {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   protected static transient Logger log = Logger.getLogger(SPSType4Data.class);
/*     */   
/*     */   public static final class StaticData {
/*     */     private List languageIndependentTables;
/*     */     private IDatabaseLink releaseTextDB;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  23 */       Connection conn = null;
/*     */       try {
/*  25 */         conn = adapter.getDatabaseLink().requestConnection();
/*  26 */         this.languageIndependentTables = new ArrayList();
/*  27 */         this.languageIndependentTables.add(new PairImpl("Supported_TPM.txt", SPSType4Data.loadTable(conn, "SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM")));
/*  28 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRange_Index.txt", SPSType4Data.loadTable(conn, "SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index")));
/*  29 */         this.languageIndependentTables.add(new PairImpl("TPM_Extended_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_Extended_Type1")));
/*  30 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeC_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeC_Type1")));
/*  31 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeD_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeD_Type1")));
/*  32 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeE_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeE_Type1")));
/*  33 */         this.languageIndependentTables.add(new PairImpl("TPM_Standard_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_Standard_Type1")));
/*  34 */         this.languageIndependentTables.add(new PairImpl("Win_Languages.txt", SPSType4Data.loadTable(conn, "SELECT LCID, ACP, Language_Acronym FROM Win_Languages")));
/*  35 */       } catch (Exception e) {
/*  36 */         throw new RuntimeException(e);
/*     */       } finally {
/*  38 */         if (conn != null) {
/*  39 */           adapter.getDatabaseLink().releaseConnection(conn);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List getLITables() {
/*  56 */       return this.languageIndependentTables;
/*     */     }
/*     */ 
/*     */     
/*     */     public PairImpl getReleaseText(int calibration, String locale) {
/*  61 */       if (this.releaseTextDB == null) {
/*  62 */         return null;
/*     */       }
/*  64 */       byte[] data = null;
/*  65 */       Connection conn = null;
/*  66 */       DBMS.PreparedStatement stmt = null;
/*  67 */       ResultSet rs = null;
/*     */       try {
/*  69 */         conn = this.releaseTextDB.requestConnection();
/*  70 */         stmt = DBMS.prepareSQLStatement(conn, DBMS.getSQL(this.releaseTextDB, "SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?"));
/*  71 */         stmt.setInt(1, calibration);
/*  72 */         stmt.setString(2, locale);
/*  73 */         rs = stmt.executeQuery();
/*  74 */         if (rs.next()) {
/*  75 */           data = rs.getBytes("RELEASE_TEXT");
/*     */         }
/*  77 */       } catch (Exception e) {
/*  78 */         SPSType4Data.log.error("failed to query type4 release text table", e);
/*     */       } finally {
/*     */         try {
/*  81 */           if (rs != null) {
/*  82 */             rs.close();
/*     */           }
/*  84 */           if (stmt != null) {
/*  85 */             stmt.close();
/*     */           }
/*  87 */           if (conn != null) {
/*  88 */             this.releaseTextDB.releaseConnection(conn);
/*     */           }
/*  90 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*  93 */       return (data == null) ? null : new PairImpl("Release.txt", data);
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  97 */       synchronized (adapter.getSyncObject()) {
/*  98 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  99 */         if (instance == null) {
/* 100 */           instance = new StaticData(adapter);
/* 101 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 103 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   SPSType4Data(String locale, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 109 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 110 */     Connection conn = null;
/*     */     try {
/* 112 */       conn = dblink.requestConnection();
/* 113 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), adapter);
/* 114 */       byte[] data = loadTable(conn, "SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?", language.getID());
/* 115 */       add((E)new PairImpl("Type4_String.txt", data));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       addAll(StaticData.getInstance(adapter).getLITables());
/*     */     } finally {
/*     */       try {
/* 124 */         if (conn != null) {
/* 125 */           dblink.releaseConnection(conn);
/*     */         }
/* 127 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] loadTable(Connection conn, String sql) throws Exception {
/* 133 */     return loadTable(conn, sql, (String)null);
/*     */   }
/*     */   
/*     */   private static byte[] loadTable(Connection conn, String sql, String criteria) throws Exception {
/* 137 */     DBMS.PreparedStatement stmt = null;
/* 138 */     ResultSet rs = null;
/*     */     try {
/* 140 */       StringBuffer data = new StringBuffer();
/* 141 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 142 */       if (criteria != null) {
/* 143 */         stmt.setString(1, criteria);
/*     */       }
/* 145 */       rs = stmt.executeQuery();
/* 146 */       ResultSetMetaData rmd = rs.getMetaData();
/* 147 */       while (rs.next()) {
/* 148 */         if (data.length() > 0) {
/* 149 */           data.append("\r\n");
/*     */         }
/* 151 */         for (int i = 0; i < rmd.getColumnCount(); i++) {
/* 152 */           if (i > 0) {
/* 153 */             data.append('\t');
/*     */           }
/* 155 */           if (rmd.getColumnType(i + 1) == 4) {
/* 156 */             data.append(rs.getInt(i + 1));
/* 157 */           } else if (rmd.getColumnType(i + 1) == 5) {
/* 158 */             data.append(rs.getInt(i + 1));
/* 159 */           } else if (rmd.getColumnType(i + 1) == -6) {
/* 160 */             data.append(rs.getInt(i + 1));
/* 161 */           } else if (rmd.getColumnName(i + 1).equalsIgnoreCase("Language_Code")) {
/*     */ 
/*     */ 
/*     */             
/* 165 */             data.append(rs.getString(i + 1));
/* 166 */           } else if (rmd.getColumnName(i + 1).equalsIgnoreCase("Language_Acronym")) {
/*     */ 
/*     */ 
/*     */             
/* 170 */             data.append(rs.getString(i + 1));
/*     */           } else {
/* 172 */             data.append(rs.getString(i + 1));
/*     */           } 
/*     */         } 
/*     */       } 
/* 176 */       return data.toString().getBytes("UTF-16LE");
/* 177 */     } catch (Exception e) {
/* 178 */       throw e;
/*     */     } finally {
/*     */       try {
/* 181 */         if (rs != null) {
/* 182 */           rs.close();
/*     */         }
/* 184 */         if (stmt != null) {
/* 185 */           stmt.close();
/*     */         }
/* 187 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) {
/* 193 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSType4Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */