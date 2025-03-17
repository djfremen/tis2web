/*    */ package com.eoos.gm.tis2web.frame.download.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.jdbc.ConnectionProvider;
/*    */ import com.eoos.jdbc.JDBCUtil;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DatabaseAdapterSupport
/*    */ {
/* 17 */   private List<String> environments = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DatabaseAdapterSupport create(ConnectionProvider connectionProvider) {
/* 24 */     DatabaseAdapterSupport ret = new DatabaseAdapterSupport();
/*    */ 
/*    */     
/* 27 */     Connection connection = connectionProvider.getConnection();
/*    */     try {
/* 29 */       boolean enableCheck = false;
/* 30 */       ResultSet rs = connection.getMetaData().getTables(null, null, "CDL_REL_VERSION_ENVIRONMENT", new String[] { "TABLE" });
/*    */       try {
/* 32 */         enableCheck = rs.next();
/*    */       } finally {
/* 34 */         JDBCUtil.close(rs);
/*    */       } 
/* 36 */       if (enableCheck) {
/* 37 */         ret.environments = ConfigurationUtil.getList((Configuration)ApplicationContext.getInstance(), "frame.dwnld.environments", null);
/*    */       }
/*    */     }
/* 40 */     catch (SQLException e) {
/* 41 */       throw Util.toRuntimeException(e);
/*    */     } finally {
/* 43 */       connectionProvider.releaseConnection(connection);
/*    */     } 
/* 45 */     return ret;
/*    */   }
/*    */   
/*    */   private String getEnvironmentFilter() {
/* 49 */     StringBuffer tmp = new StringBuffer();
/* 50 */     for (String env : this.environments) {
/* 51 */       tmp.append("'").append(env).append("',");
/*    */     }
/*    */     try {
/* 54 */       return tmp.substring(0, tmp.length() - 1);
/* 55 */     } catch (StringIndexOutOfBoundsException e) {
/* 56 */       return "";
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String checkStatement(String statement) {
/* 62 */     if (!Util.isNullOrEmpty(this.environments)) {
/* 63 */       statement = StringUtilities.replace(statement, "{JOINTABLE}", ", cdl_rel_version_environment crve {JOINTABLE}");
/* 64 */       statement = StringUtilities.replace(statement, "{JOINCONDITION}", " a.version_id=crve.ref_version_id and {JOINCONDITION}");
/* 65 */       statement = StringUtilities.replace(statement, "{FILTERCONDITION}", "crve.environment in (" + getEnvironmentFilter() + ") and {FILTERCONDITION}");
/*    */     } 
/* 67 */     return statement;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\download\server\DatabaseAdapterSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */