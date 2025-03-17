/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.HashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SPSType4Data
/*    */ {
/* 13 */   protected static transient Logger log = Logger.getLogger(SPSType4Data.class);
/*    */   
/* 15 */   public final String FILE_NAME = "type4_string.txt";
/*    */   
/* 17 */   private HashMap strings = new HashMap<Object, Object>();
/*    */   
/*    */   byte[] getType4Strings(SPSLanguage language) {
/* 20 */     return (byte[])this.strings.get(language.getLocale());
/*    */   }
/*    */   
/*    */   private SPSType4Data(SPSSchemaAdapterGME adapter) {
/* 24 */     Connection conn = null;
/*    */     try {
/* 26 */       conn = adapter.getDatabaseLink().requestConnection();
/* 27 */       DBMS.PreparedStatement stmt = null;
/* 28 */       ResultSet rs = null;
/*    */       try {
/* 30 */         StringBuffer data = new StringBuffer();
/* 31 */         String last = null;
/* 32 */         stmt = DBMS.prepareSQLStatement(conn, "SELECT String_ID, Language_Code, Description FROM Type4_String order by Language_Code");
/* 33 */         rs = stmt.executeQuery();
/* 34 */         while (rs.next()) {
/* 35 */           String language = rs.getString("Language_Code").trim();
/* 36 */           if (last != null && !language.equals(last)) {
/* 37 */             this.strings.put(last, data.toString().getBytes("UTF-16LE"));
/* 38 */             data = new StringBuffer();
/*    */           } 
/* 40 */           last = language;
/* 41 */           if (data.length() > 0) {
/* 42 */             data.append("\r\n");
/*    */           }
/* 44 */           data.append(rs.getInt("String_ID"));
/* 45 */           data.append('\t');
/* 46 */           data.append(language);
/* 47 */           data.append('\t');
/* 48 */           data.append(rs.getString("Description"));
/*    */         } 
/* 50 */         this.strings.put(last, data.toString().getBytes("UTF-16LE"));
/*    */       } finally {
/*    */         try {
/* 53 */           if (rs != null) {
/* 54 */             rs.close();
/*    */           }
/* 56 */           if (stmt != null) {
/* 57 */             stmt.close();
/*    */           }
/* 59 */         } catch (Exception x) {}
/*    */       }
/*    */     
/* 62 */     } catch (Exception e) {
/* 63 */       log.info("no type4 strings available");
/*    */     } finally {
/* 65 */       if (conn != null) {
/* 66 */         adapter.getDatabaseLink().releaseConnection(conn);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */   
/*    */   public static SPSType4Data getInstance(SPSSchemaAdapterGME adapter) {
/* 75 */     synchronized (adapter.getSyncObject()) {
/* 76 */       SPSType4Data instance = (SPSType4Data)adapter.getObject(SPSType4Data.class);
/* 77 */       if (instance == null) {
/* 78 */         instance = new SPSType4Data(adapter);
/* 79 */         adapter.storeObject(SPSType4Data.class, instance);
/*    */       } 
/* 81 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   static void init(SPSSchemaAdapterGME adapter) {
/* 86 */     getInstance(adapter).init();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSType4Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */