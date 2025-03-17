/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSBulletin
/*     */ {
/*     */   public static String loadBulletin(SPSLanguage language, String bulletinNo, SPSSchemaAdapterNAO adapter) throws Exception {
/*  24 */     String bulletin = loadBulletinDB(language, bulletinNo, adapter);
/*  25 */     if (bulletin == null) {
/*  26 */       bulletin = loadBulletinDB(language, normalize(bulletinNo), adapter);
/*     */     }
/*  28 */     return bulletin;
/*     */   }
/*     */   
/*     */   public static String loadBulletinDB(SPSLanguage language, String bulletinNo, SPSSchemaAdapterNAO adapter) throws Exception {
/*  32 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/*  33 */     Connection conn = null;
/*  34 */     DBMS.PreparedStatement stmt = null;
/*  35 */     ResultSet rs = null;
/*     */     try {
/*  37 */       conn = dblink.requestConnection();
/*  38 */       String sql = DBMS.getSQL(dblink, "SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?");
/*  39 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  40 */       stmt.setString(1, language.getID());
/*  41 */       stmt.setString(2, bulletinNo + "%");
/*     */       
/*  43 */       rs = stmt.executeQuery();
/*  44 */       if (rs.next()) {
/*     */         
/*  46 */         Blob b = rs.getBlob("BULLETIN");
/*  47 */         byte[] data = b.getBytes(1L, (int)b.length());
/*     */         try {
/*  49 */           return new String(ZipUtil.gunzip(data));
/*     */         }
/*  51 */         catch (Exception x) {
/*  52 */           return new String(data);
/*     */         } 
/*     */       } 
/*  55 */     } catch (Exception e) {
/*  56 */       throw e;
/*     */     } finally {
/*     */       try {
/*  59 */         if (rs != null) {
/*  60 */           rs.close();
/*     */         }
/*  62 */         if (stmt != null) {
/*  63 */           stmt.close();
/*     */         }
/*  65 */         if (conn != null) {
/*  66 */           dblink.releaseConnection(conn);
/*     */         }
/*  68 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   protected static String normalize(String bulletinNo) {
/*  75 */     bulletinNo = bulletinNo.trim();
/*  76 */     if (bulletinNo.indexOf('(') >= 0) {
/*  77 */       bulletinNo = bulletinNo.substring(0, bulletinNo.indexOf('('));
/*     */     }
/*  79 */     if (bulletinNo.indexOf('-') < 0) {
/*  80 */       if (bulletinNo.length() == 6)
/*  81 */         return bulletinNo.substring(0, 2) + "-" + bulletinNo.substring(2, 4) + "-" + bulletinNo.substring(4); 
/*  82 */       if (bulletinNo.length() == 9) {
/*  83 */         String bulletin = null;
/*  84 */         if (bulletinNo.startsWith("21")) {
/*  85 */           bulletin = "01-";
/*  86 */         } else if (bulletinNo.startsWith("30")) {
/*  87 */           bulletin = "03-";
/*     */         } else {
/*  89 */           bulletin = bulletinNo.substring(0, 2) + "-";
/*     */         } 
/*  91 */         return bulletin + bulletinNo.substring(2, 4) + "-" + bulletinNo.substring(4, 6) + "-" + bulletinNo.substring(6);
/*  92 */       }  if (bulletinNo.length() == 10 && 
/*  93 */         bulletinNo.startsWith("21"))
/*     */       {
/*  95 */         return "01-" + bulletinNo.substring(2, 4) + "-" + bulletinNo.substring(4, 7) + "-" + bulletinNo.substring(7);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return bulletinNo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 242 */     DatabaseLink databaseLink = new DatabaseLink("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@haydn:1521:tis2web", "SPSNAO", "dgs");
/* 243 */     int bulletins = 0;
/* 244 */     int sioLinks = 0;
/* 245 */     int imageLinks = 0;
/* 246 */     int imageRefs = 0;
/* 247 */     int styleSheetRefs = 0;
/* 248 */     int detailLinks = 0;
/* 249 */     int cellLinks = 0;
/* 250 */     Connection conn = null;
/* 251 */     ResultSet rs = null;
/* 252 */     PreparedStatement stmt = null;
/*     */     try {
/* 254 */       conn = databaseLink.requestConnection();
/* 255 */       stmt = conn.prepareStatement("SELECT BULLETIN_ID, BULLETIN FROM BULLETINS");
/* 256 */       rs = stmt.executeQuery();
/* 257 */       while (rs.next()) {
/* 258 */         String name = rs.getString("BULLETIN_ID").trim();
/* 259 */         Blob b = rs.getBlob("BULLETIN");
/* 260 */         byte[] data = ZipUtil.gunzip(b.getBytes(1L, (int)b.length()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 267 */         String bulletin = new String(data);
/* 268 */         bulletins++;
/* 269 */         if (bulletin.indexOf("LINK_SIO:ID:") >= 0) {
/* 270 */           System.out.println("siolink: " + name);
/* 271 */           sioLinks++;
/*     */         } 
/* 273 */         if (bulletin.indexOf("LINK_IMAGE:ID:") >= 0) {
/* 274 */           System.out.println("imglink: " + name);
/* 275 */           imageLinks++;
/*     */         } 
/* 277 */         if (bulletin.indexOf("IMAGE:FILE:") >= 0) {
/* 278 */           System.out.println("imgref: " + name);
/* 279 */           imageRefs++;
/*     */         } 
/* 281 */         if (bulletin.indexOf("STYLE_SHEET:FILE:") >= 0) {
/* 282 */           styleSheetRefs++;
/*     */         }
/* 284 */         if (bulletin.indexOf("LINK_DETAIL:ID:") >= 0) {
/* 285 */           System.out.println("detail: " + name);
/* 286 */           detailLinks++;
/*     */         } 
/* 288 */         if (bulletin.indexOf("LINK_CELL:ID:") >= 0) {
/* 289 */           cellLinks++;
/*     */         }
/*     */       } 
/* 292 */       System.out.println("LOADED bulletins: " + bulletins);
/* 293 */       System.out.println("sio links: " + sioLinks);
/* 294 */       System.out.println("image links: " + imageLinks);
/* 295 */       System.out.println("image refs: " + imageRefs);
/* 296 */       System.out.println("style sheet refs: " + styleSheetRefs);
/* 297 */       System.out.println("detail links: " + detailLinks);
/* 298 */       System.out.println("cell links: " + cellLinks);
/* 299 */     } catch (Exception e) {
/* 300 */       throw e;
/*     */     } finally {
/*     */       try {
/* 303 */         if (rs != null) {
/* 304 */           rs.close();
/*     */         }
/* 306 */         if (stmt != null) {
/* 307 */           stmt.close();
/*     */         }
/* 309 */         if (conn != null) {
/* 310 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 312 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSBulletin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */