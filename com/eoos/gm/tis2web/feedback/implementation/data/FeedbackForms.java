/*     */ package com.eoos.gm.tis2web.feedback.implementation.data;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class FeedbackForms
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger(FeedbackForms.class);
/*     */   
/*     */   public static final String INDEX_FILE = "index.dat";
/*     */   
/*     */   protected IDatabaseLink dblink;
/*     */   private static FeedbackForms instance;
/*     */   
/*     */   private FeedbackForms() {
/*     */     try {
/*  25 */       ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  26 */       this.dblink = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)applicationContext, "frame.feedback.db");
/*  27 */     } catch (Exception e) {
/*  28 */       this.dblink = null;
/*  29 */       log.info("using feedback forms provided by WAR file");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized FeedbackForms getInstance() {
/*  34 */     if (instance == null) {
/*  35 */       instance = new FeedbackForms();
/*     */     }
/*  37 */     return instance;
/*     */   }
/*     */   
/*     */   public byte[] loadIndexFile() throws Exception {
/*  41 */     if (this.dblink != null) {
/*     */       try {
/*  43 */         byte[] indexfile = loadFeedbackFormFromDatabase("index.dat");
/*  44 */         if (indexfile != null) {
/*  45 */           return indexfile;
/*     */         }
/*  47 */       } catch (Exception e) {}
/*     */     }
/*     */     
/*  50 */     return ApplicationContext.getInstance().loadResource("/feedback/forms/index.dat");
/*     */   }
/*     */   
/*     */   public byte[] loadFeedbackForm(String form) throws Exception {
/*  54 */     if (form.indexOf("/feedback/forms/") == 0) {
/*  55 */       form = form.substring(form.lastIndexOf('/') + 1);
/*     */     }
/*  57 */     if (this.dblink != null) {
/*     */       try {
/*  59 */         byte[] indexfile = loadFeedbackFormFromDatabase(form);
/*  60 */         if (indexfile != null) {
/*  61 */           return indexfile;
/*     */         }
/*  63 */       } catch (Exception e) {}
/*     */     }
/*     */     
/*  66 */     return ApplicationContext.getInstance().loadResource("/feedback/forms/" + form);
/*     */   }
/*     */   
/*     */   protected byte[] loadFeedbackFormFromDatabase(String form) throws Exception {
/*  70 */     Connection conn = null;
/*  71 */     ResultSet rs = null;
/*  72 */     PreparedStatement stmt = null;
/*     */     try {
/*  74 */       conn = this.dblink.requestConnection();
/*  75 */       String sql = "SELECT FEEDBACK_FORM FROM FEEDBACK_FORMS WHERE FORM_NAME = ?";
/*  76 */       stmt = conn.prepareStatement(sql);
/*  77 */       stmt.setString(1, form);
/*  78 */       rs = stmt.executeQuery();
/*  79 */       if (rs.next()) {
/*  80 */         if (this.dblink.getDBMS() == 1) {
/*  81 */           Blob b = rs.getBlob("FEEDBACK_FORM");
/*  82 */           return b.getBytes(1L, (int)b.length());
/*     */         } 
/*  84 */         return (byte[])rs.getObject("FEEDBACK_FORM");
/*     */       }
/*     */     
/*  87 */     } catch (Exception e) {
/*  88 */       log.error("failed to load feedback form '" + form + "'", e);
/*  89 */       throw e;
/*     */     } finally {
/*     */       try {
/*  92 */         if (rs != null) {
/*  93 */           rs.close();
/*     */         }
/*  95 */         if (stmt != null) {
/*  96 */           stmt.close();
/*     */         }
/*  98 */         if (conn != null) {
/*  99 */           this.dblink.releaseConnection(conn);
/*     */         }
/* 101 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementation\data\FeedbackForms.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */