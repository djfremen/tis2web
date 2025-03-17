/*    */ package com.eoos.gm.tis2web.swdl.server.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LanguageDatabaseAdapterImpl
/*    */   extends Database
/*    */   implements LanguageDatabaseAdapter
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(LanguageDatabaseAdapterImpl.class);
/*    */   
/* 26 */   private final String SELECT_ALL_LANGUAGES = "select * from SD_LANGUAGES order by LANGUAGE_ID";
/*    */ 
/*    */   
/*    */   public LanguageDatabaseAdapterImpl(IDatabaseLink db) {
/* 30 */     super(db);
/*    */   }
/*    */   
/*    */   public Map getLanguages() {
/* 34 */     Map<Object, Object> id2lang = new Hashtable<Object, Object>();
/* 35 */     Connection conn = null;
/* 36 */     PreparedStatement stmt = null;
/* 37 */     ResultSet rs = null;
/* 38 */     String lID = "";
/* 39 */     Language lang = null;
/*    */     try {
/* 41 */       conn = requestConnection();
/* 42 */       stmt = conn.prepareStatement("select * from SD_LANGUAGES order by LANGUAGE_ID");
/* 43 */       rs = stmt.executeQuery();
/* 44 */       while (rs.next()) {
/* 45 */         String langID = rs.getString("LANGUAGE_ID").trim();
/* 46 */         String localeID = rs.getString("JAVA_LANGUAGE").trim();
/* 47 */         String desc = rs.getString("NATIVE_LANGUAGE_DESC").trim();
/* 48 */         if (langID.compareTo(lID) != 0) {
/* 49 */           if (lang != null) {
/* 50 */             id2lang.put(lID, lang);
/*    */           }
/* 52 */           lID = langID;
/* 53 */           lang = new Language(lID);
/*    */         } 
/* 55 */         lang.addLangDesc(localeID, desc);
/*    */       } 
/* 57 */       id2lang.put(lID, lang);
/* 58 */     } catch (Exception e) {
/* 59 */       log.error("Exception when get applications for database " + toString() + "; " + e, e);
/*    */     } finally {
/*    */       try {
/* 62 */         if (rs != null)
/* 63 */           rs.close(); 
/* 64 */         if (stmt != null)
/* 65 */           stmt.close(); 
/* 66 */         if (conn != null)
/* 67 */           releaseConnection(conn); 
/* 68 */       } catch (Exception x) {
/* 69 */         log.error("Exception when close the statement " + toString() + "; " + x, x);
/*    */       } 
/*    */     } 
/* 72 */     return id2lang;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\LanguageDatabaseAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */