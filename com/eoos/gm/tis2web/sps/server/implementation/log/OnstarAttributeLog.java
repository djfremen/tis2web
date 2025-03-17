/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnstarAttributeLog
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(OnstarAttributeLog.class);
/*    */   
/*    */   private IDatabaseLink dbLink;
/*    */   
/*    */   private OnstarAttributeLog(Configuration configuration) throws Exception {
/* 22 */     this.dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(configuration, "db."));
/*    */   }
/*    */   
/*    */   public static OnstarAttributeLog create(Configuration configuration) throws Exception {
/* 26 */     OnstarAttributeLog instance = new OnstarAttributeLog(configuration);
/* 27 */     Connection connection = instance.dbLink.requestConnection();
/* 28 */     instance.dbLink.releaseConnection(connection);
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public void writeAttributes(long entryID, List<SPSEventLog.Attribute> attributes) throws Exception {
/* 33 */     log.debug("writing attributes ...");
/* 34 */     Connection connection = this.dbLink.requestConnection();
/*    */     try {
/* 36 */       connection.setAutoCommit(false);
/* 37 */       String createQuery = "insert into onstar_sps_attribute (NAME, VALUE, ORD, ENTRY_REFID) values(?,?,?,?)";
/*    */       
/* 39 */       PreparedStatement createStmt = connection.prepareStatement(createQuery);
/*    */       try {
/* 41 */         createStmt.setLong(4, entryID);
/* 42 */         for (int i = 0; i < attributes.size(); i++) {
/* 43 */           SPSEventLog.Attribute attribute = attributes.get(i);
/* 44 */           if (!(attribute instanceof SPSEventLog.VoltAttribute)) {
/*    */ 
/*    */             
/* 47 */             createStmt.setString(1, attribute.getName().toLowerCase(Locale.ENGLISH));
/* 48 */             createStmt.setString(2, String.valueOf(attribute.getValue()));
/* 49 */             createStmt.setInt(3, i);
/* 50 */             createStmt.addBatch();
/*    */           } 
/* 52 */         }  int[] results = createStmt.executeBatch();
/* 53 */         for (int j = 0; j < results.length; j++) {
/* 54 */           if (results[j] != 1 && results[j] != -2) {
/* 55 */             throw new IllegalStateException("unable to write attributes ");
/*    */           }
/*    */         } 
/*    */       } finally {
/* 59 */         createStmt.close();
/*    */       } 
/* 61 */       connection.commit();
/* 62 */       log.debug("...successfully wrote attributes");
/* 63 */     } catch (Exception e) {
/* 64 */       log.error("unable to write attributes - exception: " + e, e);
/* 65 */       connection.rollback();
/* 66 */       throw e;
/*    */     } finally {
/* 68 */       this.dbLink.releaseConnection(connection);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\OnstarAttributeLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */