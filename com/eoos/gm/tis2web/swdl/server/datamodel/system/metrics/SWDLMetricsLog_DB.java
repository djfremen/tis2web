/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.thread.CustomThread;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWDLMetricsLog_DB
/*     */   implements SWDLMetricsLog, SWDLMetricsLog.Deletion
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(SWDLMetricsLog_DB.class);
/*     */   
/*  35 */   private String serverName = null;
/*     */   
/*  37 */   private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH);
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   public SWDLMetricsLog_DB(Configuration configuration) throws Exception {
/*  42 */     this.serverName = ApplicationContext.getInstance().getHostName();
/*  43 */     this.serverName += "(" + ApplicationContext.getInstance().getIPAddr() + ")";
/*     */     
/*  45 */     final IDatabaseLink dblink = DatabaseLink.openDatabase(configuration);
/*     */     
/*  47 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  50 */             dblink.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  55 */               return dblink.requestConnection();
/*  56 */             } catch (Exception e) {
/*  57 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  300000L);
/*     */ 
/*     */     
/*     */     try {
/*  64 */       releaseConnection(getReadConnection());
/*  65 */     } catch (Exception e) {
/*  66 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/*  73 */     Connection ret = this.connectionProvider.getConnection();
/*  74 */     ret.setAutoCommit(false);
/*  75 */     ret.setReadOnly(true);
/*  76 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws Exception {
/*  80 */     Connection ret = this.connectionProvider.getConnection();
/*  81 */     ret.setReadOnly(false);
/*  82 */     ret.setAutoCommit(false);
/*  83 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  87 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private long getID(Connection connection) throws Exception {
/*  91 */     long retValue = -1L;
/*     */     
/*  93 */     String query1 = "select nextkey from swdl_keygen";
/*  94 */     PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */     try {
/*  96 */       String query2 = "update swdl_keygen set nextkey=? where nextkey=?";
/*  97 */       PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */ 
/*     */       
/*     */       try { while (true) {
/* 101 */           ResultSet rs = stmt1.executeQuery();
/*     */           try {
/* 103 */             if (rs.next()) {
/* 104 */               retValue = rs.getLong(1);
/*     */             } else {
/* 106 */               throw new IllegalStateException();
/*     */             } 
/*     */           } finally {
/*     */             
/* 110 */             rs.close();
/*     */           } 
/*     */           
/* 113 */           stmt2.setLong(1, retValue + 1L);
/* 114 */           stmt2.setLong(2, retValue);
/* 115 */           int result = stmt2.executeUpdate();
/* 116 */           if (result != 1) {
/*     */             
/* 118 */             log.info("id: " + retValue + " is already in use, retrying");
/* 119 */             retValue = -1L;
/*     */           } 
/*     */           
/* 122 */           if (retValue != -1L)
/* 123 */             return retValue; 
/*     */         }  }
/* 125 */       finally { stmt2.close(); }
/*     */     
/*     */     } finally {
/* 128 */       stmt1.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(SWDLMetricsLog.Entry entry) throws Exception {
/* 134 */     Connection connection = getWriteConnection();
/*     */     try {
/* 136 */       long identifier = getID(connection);
/* 137 */       String query = "insert into swdl_metrics_log values(?,?,?,?,?,?,?,?,?)";
/* 138 */       PreparedStatement stmt = connection.prepareStatement(query);
/*     */       try {
/* 140 */         stmt.setLong(1, identifier);
/* 141 */         stmt.setLong(2, entry.getTimestamp());
/* 142 */         stmt.setString(3, this.dateFormat.format(new Date(entry.getTimestamp())));
/* 143 */         stmt.setString(4, entry.getDevice());
/* 144 */         stmt.setString(5, entry.getApplication());
/* 145 */         stmt.setString(6, entry.getVersion());
/* 146 */         stmt.setString(7, entry.getLanguage());
/* 147 */         stmt.setString(8, this.serverName);
/* 148 */         stmt.setString(9, entry.getUserID());
/* 149 */         if (stmt.executeUpdate() != 1) {
/* 150 */           throw new IllegalStateException();
/*     */         }
/*     */       } finally {
/* 153 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */       
/* 156 */       connection.commit();
/* 157 */     } catch (Exception e) {
/* 158 */       JDBCUtil.rollback(connection, log);
/* 159 */       throw e;
/*     */     } finally {
/* 161 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(SWDLMetricsLog.Entry entry) throws Exception {
/* 166 */     log.info("adding " + String.valueOf(entry) + " to swdl metrics log (database)");
/*     */     try {
/* 168 */       write(entry);
/* 169 */     } catch (Exception e) {
/* 170 */       log.warn("...unable to add entry (" + String.valueOf(entry) + ") - exception: " + e, e);
/* 171 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getEntries(SWDLMetricsLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 176 */     log.debug("processing entries retrieval request");
/*     */     try {
/* 178 */       return _getEntries(backendFilter, entryFilter, hitLimit);
/* 179 */     } catch (Exception e) {
/* 180 */       log.warn("...unable to process entries retrieval request - exception: " + e, e);
/* 181 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isAborted() {
/* 186 */     if (Thread.currentThread() instanceof CustomThread) {
/* 187 */       return ((CustomThread)Thread.currentThread()).isAborted();
/*     */     }
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection _getEntries(SWDLMetricsLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 194 */     Connection connection = getReadConnection();
/*     */     try {
/* 196 */       StringBuffer query = new StringBuffer("select a.ts, a.device, a.application, a.version, a.language, a.entry_id, a.server_name, a.userid from swdl_metrics_log a #");
/*     */       
/* 198 */       if (backendFilter != null) {
/* 199 */         if (backendFilter.getTimestampMIN() != null) {
/* 200 */           query.append("and a.ts >= " + backendFilter.getTimestampMIN().longValue() + " ");
/*     */         }
/* 202 */         if (backendFilter.getTimestampMAX() != null) {
/* 203 */           query.append("and a.ts <= " + backendFilter.getTimestampMAX().longValue() + " ");
/*     */         }
/*     */         
/* 206 */         StringUtilities.replace(query, "#and", "where");
/*     */       } 
/* 208 */       StringUtilities.replace(query, "#", "");
/* 209 */       Collection<SWDLMetricsLog.Entry2> retValue = new LinkedList();
/* 210 */       PreparedStatement stmt = connection.prepareStatement(query.toString());
/*     */       try {
/* 212 */         ResultSet rs = JDBCUtil.asyncExecuteQuery(stmt);
/*     */         try {
/* 214 */           while (rs.next() && !isAborted() && (hitLimit == -1 || retValue.size() <= hitLimit)) {
/* 215 */             long ts = rs.getLong(1);
/* 216 */             String device = rs.getString(2);
/* 217 */             String application = rs.getString(3);
/* 218 */             String version = rs.getString(4);
/* 219 */             String language = rs.getString(5);
/* 220 */             long identifier = rs.getLong(6);
/* 221 */             String server = rs.getString(7);
/* 222 */             String userID = rs.getString(8);
/* 223 */             SWDLMetricsLog.Entry2 entry = new EntryImpl(ts, version, device, application, server, language, identifier, userID);
/* 224 */             if (entryFilter == null || entryFilter.include(entry)) {
/* 225 */               retValue.add(entry);
/*     */             }
/*     */           } 
/* 228 */           if (isAborted()) {
/* 229 */             throw new SWDLMetricsLog.Query.AbortionException(retValue);
/*     */           }
/* 231 */           return retValue;
/*     */         }
/*     */         finally {
/*     */           
/* 235 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 238 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 241 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(Collection entries) throws Exception {
/* 248 */     if (entries != null) {
/* 249 */       log.debug("deleting " + entries.size() + " entries");
/* 250 */       Connection connection = getWriteConnection();
/*     */       try {
/* 252 */         PreparedStatement stmt = connection.prepareStatement("delete from swdl_metrics_log a where a.entry_id=?");
/*     */         try {
/* 254 */           for (Iterator<SWDLMetricsLog.Entry2> iter = entries.iterator(); iter.hasNext(); ) {
/* 255 */             SWDLMetricsLog.Entry2 entry = iter.next();
/* 256 */             stmt.setLong(1, entry.getIdentifier());
/* 257 */             stmt.addBatch();
/*     */           } 
/* 259 */           stmt.executeBatch();
/* 260 */           connection.commit();
/* 261 */         } catch (Exception e) {
/* 262 */           log.error("...unable to delete entries - exception:" + e, e);
/* 263 */           connection.rollback();
/*     */         } finally {
/* 265 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 268 */         releaseConnection(connection);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\SWDLMetricsLog_DB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */