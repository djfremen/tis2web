/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*    */ import java.sql.Blob;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ 
/*    */ public class SPSInstructions {
/*    */   protected static transient IDatabaseLink dblink;
/*    */   
/*    */   static void init(IDatabaseLink db) throws Exception {
/* 13 */     dblink = db;
/*    */   }
/*    */   
/*    */   protected static String loadHTML(SPSLanguage language, String id) throws Exception {
/* 17 */     Connection conn = null;
/* 18 */     ResultSet rs = null;
/* 19 */     DBMS.PreparedStatement stmt = null;
/*    */     try {
/* 21 */       conn = dblink.requestConnection();
/* 22 */       String sql = DBMS.getSQL(dblink, "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/* 23 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 24 */       stmt.setString(1, language.getID());
/* 25 */       stmt.setString(2, id);
/* 26 */       rs = stmt.executeQuery();
/* 27 */       if (rs.next()) {
/*    */         
/* 29 */         Blob b = rs.getBlob("HTML");
/* 30 */         byte[] data = b.getBytes(1L, (int)b.length());
/* 31 */         return new String(data);
/*    */       } 
/* 33 */     } catch (Exception e) {
/* 34 */       throw e;
/*    */     } finally {
/*    */       try {
/* 37 */         if (rs != null) {
/* 38 */           rs.close();
/*    */         }
/* 40 */         if (stmt != null) {
/* 41 */           stmt.close();
/*    */         }
/* 43 */         if (conn != null) {
/* 44 */           dblink.releaseConnection(conn);
/*    */         }
/* 46 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   protected static byte[] loadImage(String id) throws Exception {
/* 53 */     Connection conn = null;
/* 54 */     ResultSet rs = null;
/* 55 */     DBMS.PreparedStatement stmt = null;
/*    */     try {
/* 57 */       conn = dblink.requestConnection();
/* 58 */       String sql = DBMS.getSQL(dblink, "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 59 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 60 */       stmt.setString(1, id);
/* 61 */       rs = stmt.executeQuery();
/* 62 */       if (rs.next()) {
/*    */         
/* 64 */         Blob b = rs.getBlob("GRAPHIC");
/* 65 */         byte[] data = b.getBytes(1L, (int)b.length());
/* 66 */         return data;
/*    */       } 
/* 68 */     } catch (Exception e) {
/* 69 */       throw e;
/*    */     } finally {
/*    */       try {
/* 72 */         if (rs != null) {
/* 73 */           rs.close();
/*    */         }
/* 75 */         if (stmt != null) {
/* 76 */           stmt.close();
/*    */         }
/* 78 */         if (conn != null) {
/* 79 */           dblink.releaseConnection(conn);
/*    */         }
/* 81 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSInstructions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */