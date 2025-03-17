/*    */ package com.eoos.gm.tis2web.registration.server.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.registration.server.RegistryImpl;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthorizationData
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(AuthorizationData.class);
/*    */   
/*    */   private static List authorizations;
/*    */   
/*    */   public static AuthorizationEntity getAuthorization(String authorizationID) {
/* 24 */     if (authorizations == null) {
/* 25 */       return null;
/*    */     }
/* 27 */     for (int i = 0; i < authorizations.size(); i++) {
/* 28 */       AuthorizationEntity authorization = authorizations.get(i);
/* 29 */       if (authorization.getAuthorizationID().equals(authorizationID)) {
/* 30 */         return authorization;
/*    */       }
/*    */     } 
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public static synchronized List getAuthorizations(RegistryImpl registry) {
/* 37 */     if (authorizations != null) {
/* 38 */       return authorizations;
/*    */     }
/* 40 */     authorizations = new ArrayList();
/* 41 */     AuthorizationEntity authorization = null;
/* 42 */     DatabaseLink databaseLink = registry.getDatabaseLink();
/* 43 */     Connection conn = null;
/* 44 */     PreparedStatement stmt = null;
/* 45 */     ResultSet rs = null;
/*    */     try {
/* 47 */       conn = databaseLink.requestConnection();
/* 48 */       String sql = "SELECT s.\"SubscriptionID\", s.\"Organization\", d.\"Locale\", d.\"Description\" FROM Subscription s, SubscriptionDescription d WHERE s.\"SubscriptionPK\" = d.\"Subscription\" ORDER BY s.\"SubscriptionID\"";
/* 49 */       stmt = conn.prepareStatement(sql);
/* 50 */       rs = stmt.executeQuery();
/* 51 */       while (rs.next()) {
/* 52 */         String id = rs.getString(1).trim();
/* 53 */         if (authorization == null || !authorization.getAuthorizationID().equals(id)) {
/* 54 */           authorization = new AuthorizationEntity(id);
/* 55 */           authorizations.add(authorization);
/*    */         } 
/* 57 */         String org = rs.getString(2).trim();
/* 58 */         authorization.setOrganization(SalesOrganization.get(org.toUpperCase(Locale.ENGLISH)));
/* 59 */         String locale = rs.getString(3).trim();
/* 60 */         authorization.setDescription(new Locale(locale), rs.getString(4).trim());
/*    */       } 
/* 62 */     } catch (Exception e) {
/* 63 */       log.debug(e);
/* 64 */       throw new RegistrationException("registration.db.load.authorizations.failed");
/*    */     } finally {
/*    */       try {
/* 67 */         if (rs != null) {
/* 68 */           rs.close();
/*    */         }
/* 70 */         if (stmt != null) {
/* 71 */           stmt.close();
/*    */         }
/* 73 */         if (conn != null) {
/* 74 */           databaseLink.releaseConnection(conn);
/*    */         }
/* 76 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 79 */     return authorizations;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\AuthorizationData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */