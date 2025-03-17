/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
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
/*     */   extends ArrayList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   protected static transient Logger log = Logger.getLogger(SPSType4Data.class);
/*     */   
/*     */   public static final class StaticData {
/*     */     private List languageIndependentTables;
/*     */     private IDatabaseLink releaseTextDB;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  24 */       Connection conn = null;
/*     */       try {
/*  26 */         conn = adapter.getDatabaseLink().requestConnection();
/*  27 */         this.languageIndependentTables = new ArrayList();
/*  28 */         this.languageIndependentTables.add(new PairImpl("Supported_TPM.txt", SPSType4Data.loadTable(conn, "SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM")));
/*  29 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRange_Index.txt", SPSType4Data.loadTable(conn, "SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index")));
/*  30 */         this.languageIndependentTables.add(new PairImpl("TPM_Extended_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_Extended_Type1")));
/*  31 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeC_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeC_Type1")));
/*  32 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeD_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeD_Type1")));
/*  33 */         this.languageIndependentTables.add(new PairImpl("TPM_LoadRangeE_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_LoadRangeE_Type1")));
/*  34 */         this.languageIndependentTables.add(new PairImpl("TPM_Standard_Type1.txt", SPSType4Data.loadTable(conn, "SELECT Description, Value FROM TPM_Standard_Type1")));
/*  35 */         this.languageIndependentTables.add(new PairImpl("Win_Languages.txt", SPSType4Data.loadTable(conn, "SELECT LCID, ACP, Language_Acronym FROM Win_Languages")));
/*  36 */       } catch (Exception e) {
/*  37 */         throw new RuntimeException(e);
/*     */       } finally {
/*  39 */         if (conn != null) {
/*  40 */           adapter.getDatabaseLink().releaseConnection(conn);
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
/*  57 */       return this.languageIndependentTables;
/*     */     }
/*     */ 
/*     */     
/*     */     public PairImpl getReleaseText(int calibration, String locale) {
/*  62 */       if (this.releaseTextDB == null) {
/*  63 */         return null;
/*     */       }
/*  65 */       byte[] data = null;
/*  66 */       Connection conn = null;
/*  67 */       DBMS.PreparedStatement stmt = null;
/*  68 */       ResultSet rs = null;
/*     */       try {
/*  70 */         conn = this.releaseTextDB.requestConnection();
/*  71 */         stmt = DBMS.prepareSQLStatement(conn, DBMS.getSQL(this.releaseTextDB, "SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?"));
/*  72 */         stmt.setInt(1, calibration);
/*  73 */         stmt.setString(2, locale);
/*  74 */         rs = stmt.executeQuery();
/*  75 */         if (rs.next()) {
/*  76 */           data = rs.getBytes("RELEASE_TEXT");
/*     */         }
/*  78 */       } catch (Exception e) {
/*  79 */         SPSType4Data.log.error("failed to query type4 release text table", e);
/*     */       } finally {
/*     */         try {
/*  82 */           if (rs != null) {
/*  83 */             rs.close();
/*     */           }
/*  85 */           if (stmt != null) {
/*  86 */             stmt.close();
/*     */           }
/*  88 */           if (conn != null) {
/*  89 */             this.releaseTextDB.releaseConnection(conn);
/*     */           }
/*  91 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*  94 */       return (data == null) ? null : new PairImpl("Release.txt", data);
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  98 */       synchronized (adapter.getSyncObject()) {
/*  99 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 100 */         if (instance == null) {
/* 101 */           instance = new StaticData(adapter);
/* 102 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 104 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   SPSType4Data(String locale, SPSSchemaAdapterNAO adapter) throws Exception {
/* 110 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 111 */     Connection conn = null;
/*     */     try {
/* 113 */       conn = dblink.requestConnection();
/* 114 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), adapter);
/* 115 */       byte[] data = loadTable(conn, "SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?", language.getID());
/* 116 */       add((E)new PairImpl("Type4_String.txt", data));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       addAll(StaticData.getInstance(adapter).getLITables());
/*     */     } finally {
/*     */       try {
/* 125 */         if (conn != null) {
/* 126 */           dblink.releaseConnection(conn);
/*     */         }
/* 128 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] loadTable(Connection conn, String sql) throws Exception {
/* 134 */     return loadTable(conn, sql, (String)null);
/*     */   }
/*     */   
/*     */   private static byte[] loadTable(Connection conn, String sql, String criteria) throws Exception {
/* 138 */     DBMS.PreparedStatement stmt = null;
/* 139 */     ResultSet rs = null;
/*     */     try {
/* 141 */       StringBuffer data = new StringBuffer();
/* 142 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 143 */       if (criteria != null) {
/* 144 */         stmt.setString(1, criteria);
/*     */       }
/* 146 */       rs = stmt.executeQuery();
/* 147 */       ResultSetMetaData rmd = rs.getMetaData();
/* 148 */       while (rs.next()) {
/* 149 */         if (data.length() > 0) {
/* 150 */           data.append("\r\n");
/*     */         }
/* 152 */         for (int i = 0; i < rmd.getColumnCount(); i++) {
/* 153 */           if (i > 0) {
/* 154 */             data.append('\t');
/*     */           }
/* 156 */           if (rmd.getColumnType(i + 1) == 4) {
/* 157 */             data.append(rs.getInt(i + 1));
/* 158 */           } else if (rmd.getColumnType(i + 1) == 5) {
/* 159 */             data.append(rs.getInt(i + 1));
/* 160 */           } else if (rmd.getColumnType(i + 1) == -6) {
/* 161 */             data.append(rs.getInt(i + 1));
/* 162 */           } else if (rmd.getColumnName(i + 1).equalsIgnoreCase("Language_Code")) {
/*     */ 
/*     */ 
/*     */             
/* 166 */             data.append(rs.getString(i + 1));
/* 167 */           } else if (rmd.getColumnName(i + 1).equalsIgnoreCase("Language_Acronym")) {
/*     */ 
/*     */ 
/*     */             
/* 171 */             data.append(rs.getString(i + 1));
/*     */           } else {
/* 173 */             data.append(rs.getString(i + 1));
/*     */           } 
/*     */         } 
/*     */       } 
/* 177 */       return data.toString().getBytes("UTF-16LE");
/* 178 */     } catch (Exception e) {
/* 179 */       throw e;
/*     */     } finally {
/*     */       try {
/* 182 */         if (rs != null) {
/* 183 */           rs.close();
/*     */         }
/* 185 */         if (stmt != null) {
/* 186 */           stmt.close();
/*     */         }
/* 188 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) {
/* 194 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSType4Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */