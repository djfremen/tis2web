/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSInstructions
/*     */ {
/*     */   public static final String HTML_INSTRUCTION = "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?";
/*     */   public static final String INSTRUCTION_GRAPHIC = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?";
/*  21 */   private static final Logger log = Logger.getLogger(SPSInstructions.class);
/*     */   
/*     */   private static SPSInstructions instance;
/*     */   
/*  25 */   private IDatabaseLink dblink = null;
/*     */   
/*     */   private SPSInstructions() throws Exception {
/*  28 */     ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  29 */     this.dblink = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)applicationContext, "component.sps.instructions.table.db");
/*     */   }
/*     */   
/*     */   public static synchronized SPSInstructions getInstance() {
/*  33 */     if (instance == null) {
/*     */       try {
/*  35 */         instance = new SPSInstructions();
/*  36 */       } catch (Exception e) {
/*  37 */         log.error("failed to initialize SPSInstructions (check configuration)", e);
/*     */       } 
/*     */     }
/*  40 */     return instance;
/*     */   }
/*     */   
/*     */   public String getHTML(String locale, String id) {
/*  44 */     locale = SPSLanguage.lookupLocale(locale);
/*  45 */     String html = loadHTML(locale, id);
/*  46 */     if (html == null) {
/*  47 */       int pos = id.indexOf("-");
/*  48 */       if (pos >= 0) {
/*  49 */         html = loadHTML(locale, id.substring(pos + 1));
/*     */       }
/*     */     } 
/*  52 */     return html;
/*     */   }
/*     */   
/*     */   protected String loadHTML(String locale, String id) {
/*  56 */     Connection conn = null;
/*  57 */     ResultSet rs = null;
/*  58 */     PreparedStatement stmt = null;
/*     */     try {
/*  60 */       conn = this.dblink.requestConnection();
/*  61 */       stmt = conn.prepareStatement("SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/*  62 */       stmt.setString(1, locale);
/*  63 */       stmt.setString(2, id);
/*  64 */       rs = stmt.executeQuery();
/*  65 */       if (rs.next()) {
/*  66 */         String charset = rs.getString("HTML_CHARSET");
/*  67 */         if (charset != null) {
/*  68 */           charset = charset.trim();
/*     */         }
/*  70 */         byte[] data = null;
/*  71 */         if (this.dblink.getDBMS() == 2) {
/*  72 */           data = rs.getBytes("HTML");
/*     */         } else {
/*  74 */           Blob b = rs.getBlob("HTML");
/*  75 */           data = b.getBytes(1L, (int)b.length());
/*     */         } 
/*  77 */         if (!"ASCII".equalsIgnoreCase(charset)) {
/*  78 */           return new String(data, charset);
/*     */         }
/*  80 */         return new String(data);
/*     */       }
/*     */     
/*  83 */     } catch (Exception e) {
/*  84 */       log.error("failed to load html " + id, e);
/*     */     } finally {
/*     */       try {
/*  87 */         if (rs != null) {
/*  88 */           rs.close();
/*     */         }
/*  90 */         if (stmt != null) {
/*  91 */           stmt.close();
/*     */         }
/*  93 */         if (conn != null) {
/*  94 */           this.dblink.releaseConnection(conn);
/*     */         }
/*  96 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public byte[] getImage(String id) {
/* 103 */     Connection conn = null;
/* 104 */     ResultSet rs = null;
/* 105 */     PreparedStatement stmt = null;
/*     */     try {
/* 107 */       conn = this.dblink.requestConnection();
/* 108 */       stmt = conn.prepareStatement("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 109 */       stmt.setString(1, id);
/* 110 */       rs = stmt.executeQuery();
/* 111 */       if (rs.next()) {
/*     */         
/* 113 */         byte[] data = null;
/* 114 */         if (this.dblink.getDBMS() == 2) {
/* 115 */           data = rs.getBytes("GRAPHIC");
/*     */         } else {
/* 117 */           Blob b = rs.getBlob("GRAPHIC");
/* 118 */           data = b.getBytes(1L, (int)b.length());
/*     */         } 
/* 120 */         return data;
/*     */       } 
/* 122 */     } catch (Exception e) {
/* 123 */       log.error("failed to load image " + id, e);
/*     */     } finally {
/*     */       try {
/* 126 */         if (rs != null) {
/* 127 */           rs.close();
/*     */         }
/* 129 */         if (stmt != null) {
/* 130 */           stmt.close();
/*     */         }
/* 132 */         if (conn != null) {
/* 133 */           this.dblink.releaseConnection(conn);
/*     */         }
/* 135 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\SPSInstructions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */