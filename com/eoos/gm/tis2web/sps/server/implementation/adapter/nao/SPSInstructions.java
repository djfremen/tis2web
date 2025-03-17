/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*    */ import java.sql.Blob;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ 
/*    */ public class SPSInstructions
/*    */ {
/*    */   protected static String loadHTML(SPSLanguage language, String id, SPSSchemaAdapterNAO adapter) throws Exception {
/* 12 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 13 */     Connection conn = null;
/* 14 */     ResultSet rs = null;
/* 15 */     DBMS.PreparedStatement stmt = null;
/*    */     try {
/* 17 */       conn = dblink.requestConnection();
/* 18 */       String sql = DBMS.getSQL(dblink, "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/* 19 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 20 */       stmt.setString(1, language.getID());
/* 21 */       stmt.setString(2, id);
/* 22 */       rs = stmt.executeQuery();
/* 23 */       if (rs.next()) {
/*    */         
/* 25 */         Blob b = rs.getBlob("HTML");
/* 26 */         byte[] data = b.getBytes(1L, (int)b.length());
/* 27 */         return new String(data);
/*    */       } 
/* 29 */     } catch (Exception e) {
/* 30 */       throw e;
/*    */     } finally {
/*    */       try {
/* 33 */         if (rs != null) {
/* 34 */           rs.close();
/*    */         }
/* 36 */         if (stmt != null) {
/* 37 */           stmt.close();
/*    */         }
/* 39 */         if (conn != null) {
/* 40 */           dblink.releaseConnection(conn);
/*    */         }
/* 42 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   protected static byte[] loadImage(String id, SPSSchemaAdapterNAO adapter) throws Exception {
/* 49 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 50 */     Connection conn = null;
/* 51 */     ResultSet rs = null;
/* 52 */     DBMS.PreparedStatement stmt = null;
/*    */     try {
/* 54 */       conn = dblink.requestConnection();
/* 55 */       String sql = DBMS.getSQL(dblink, "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 56 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 57 */       stmt.setString(1, id);
/* 58 */       rs = stmt.executeQuery();
/* 59 */       if (rs.next()) {
/*    */         
/* 61 */         Blob b = rs.getBlob("GRAPHIC");
/* 62 */         byte[] data = b.getBytes(1L, (int)b.length());
/* 63 */         return data;
/*    */       } 
/* 65 */     } catch (Exception e) {
/* 66 */       throw e;
/*    */     } finally {
/*    */       try {
/* 69 */         if (rs != null) {
/* 70 */           rs.close();
/*    */         }
/* 72 */         if (stmt != null) {
/* 73 */           stmt.close();
/*    */         }
/* 75 */         if (conn != null) {
/* 76 */           dblink.releaseConnection(conn);
/*    */         }
/* 78 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 81 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSInstructions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */