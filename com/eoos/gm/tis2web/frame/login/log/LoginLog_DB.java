/*     */ package com.eoos.gm.tis2web.frame.login.log;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.thread.CustomThread;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.AssertUtil;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LoginLog_DB
/*     */   implements LoginLog.SPI, LoginLog.Deletion
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(LoginLog_DB.class);
/*     */   
/*     */   private static final class EntryWrapper implements LoginLog.Entry {
/*     */     private LoginLog.Entry backend;
/*     */     
/*     */     public EntryWrapper(LoginLog.Entry backend) {
/*  37 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     private String adjust(String string) {
/*  41 */       return (string == null) ? "" : string;
/*     */     }
/*     */     
/*     */     public long getTimestamp() {
/*  45 */       return this.backend.getTimestamp();
/*     */     }
/*     */     
/*     */     public String getUsername() {
/*  49 */       return adjust(this.backend.getUsername());
/*     */     }
/*     */     
/*     */     public String getSourceAddress() {
/*  53 */       return adjust(this.backend.getSourceAddress());
/*     */     }
/*     */     
/*     */     public String getFreeParameter() {
/*  57 */       return adjust(this.backend.getFreeParameter());
/*     */     }
/*     */     
/*     */     public String getOrigin() {
/*  61 */       return adjust(this.backend.getOrigin());
/*     */     }
/*     */     
/*     */     public String getUserGroup() {
/*  65 */       return adjust(this.backend.getUserGroup());
/*     */     }
/*     */     
/*     */     public String getDealerCode() {
/*  69 */       return adjust(this.backend.getDealerCode());
/*     */     }
/*     */     
/*     */     public boolean successfulLogin() {
/*  73 */       return this.backend.successfulLogin();
/*     */     }
/*     */     
/*     */     public String getDivisionCode() {
/*  77 */       return adjust(this.backend.getDivisionCode());
/*     */     }
/*     */     
/*     */     public String getMappedCountryCode() {
/*  81 */       return adjust(this.backend.getMappedCountryCode());
/*     */     }
/*     */     
/*     */     public String getOriginalCountryCode() {
/*  85 */       return adjust(this.backend.getOriginalCountryCode());
/*     */     }
/*     */     
/*     */     public String getT2WGroup() {
/*  89 */       return adjust(this.backend.getT2WGroup());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  94 */   private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String serverName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long[] availableIDs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getReadOnlyConnection() throws SQLException {
/* 123 */     Connection ret = this.connectionProvider.getConnection();
/* 124 */     ret.setReadOnly(true);
/* 125 */     ret.setAutoCommit(false);
/* 126 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws SQLException {
/* 130 */     Connection ret = this.connectionProvider.getConnection();
/* 131 */     ret.setReadOnly(false);
/* 132 */     ret.setAutoCommit(false);
/* 133 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 137 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/* 140 */   public LoginLog_DB(Configuration configuration) throws Exception { this.availableIDs = null; final IDatabaseLink dblink = DatabaseLink.openDatabase(configuration); this.connectionProvider = ConNvent.create(new ConnectionProvider() {
/*     */           public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { return dblink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }
/*     */              }
/* 143 */         },  300000L); this.serverName = ApplicationContext.getInstance().getHostName() + ":" + ApplicationContext.getInstance().getPort(); this.serverName += "(" + ApplicationContext.getInstance().getIPAddr() + ":" + ApplicationContext.getInstance().getPort() + ")"; } private synchronized long getID() throws Exception { if (this.availableIDs == null || this.availableIDs[0] > this.availableIDs[1]) {
/* 144 */       this.availableIDs = reserveIDBlock();
/*     */     }
/* 146 */     this.availableIDs[0] = this.availableIDs[0] + 1L; return this.availableIDs[0]; }
/*     */ 
/*     */   
/*     */   private long[] reserveIDBlock() throws Exception {
/* 150 */     log.debug("reserving ID block...");
/* 151 */     Connection connection = getWriteConnection();
/*     */     try {
/* 153 */       long[] retValue = null;
/*     */       
/* 155 */       String query1 = "select nextkey from loginlog_keygen";
/* 156 */       PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */       try {
/* 158 */         String query2 = "update loginlog_keygen set nextkey=? where nextkey=?";
/* 159 */         PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */         try {
/* 161 */           while (retValue == null) {
/* 162 */             retValue = new long[] { -1L, -1L };
/* 163 */             ResultSet rs = stmt1.executeQuery();
/*     */             try {
/* 165 */               if (rs.next()) {
/* 166 */                 retValue[0] = rs.getLong(1);
/*     */               } else {
/* 168 */                 throw new IllegalStateException();
/*     */               } 
/*     */             } finally {
/* 171 */               rs.close();
/*     */             } 
/*     */             
/* 174 */             retValue[1] = retValue[0] + 499L;
/* 175 */             stmt2.setLong(1, retValue[1] + 1L);
/* 176 */             stmt2.setLong(2, retValue[0]);
/* 177 */             int result = stmt2.executeUpdate();
/* 178 */             if (result != 1) {
/*     */               
/* 180 */               log.info("block  is already in use, retrying");
/* 181 */               retValue = null;
/*     */             } 
/*     */           } 
/* 184 */           log.debug("...done");
/* 185 */           connection.commit();
/* 186 */           return retValue;
/*     */         } finally {
/* 188 */           stmt2.close();
/*     */         } 
/*     */       } finally {
/* 191 */         stmt1.close();
/*     */       } 
/* 193 */     } catch (Exception e) {
/* 194 */       JDBCUtil.rollback(connection, log);
/* 195 */       throw e;
/*     */     } finally {
/* 197 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(Collection entries) {
/*     */     try {
/* 204 */       Connection connection = getWriteConnection();
/*     */       
/*     */       try {
/* 207 */         PreparedStatement stmt = connection.prepareStatement("insert into login_log values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */         try {
/* 209 */           for (Iterator<LoginLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 210 */             LoginLog.Entry entry = iter.next();
/* 211 */             entry = new EntryWrapper(entry);
/*     */             
/* 213 */             long entryID = getID();
/* 214 */             stmt.setLong(1, entryID);
/* 215 */             stmt.setLong(2, entry.getTimestamp());
/* 216 */             stmt.setString(3, this.dateFormat.format(new Date(entry.getTimestamp())));
/* 217 */             stmt.setString(4, entry.getUsername());
/* 218 */             stmt.setString(5, entry.successfulLogin() ? "allow" : "deny");
/* 219 */             stmt.setString(6, entry.getSourceAddress());
/* 220 */             stmt.setString(7, entry.getFreeParameter());
/* 221 */             stmt.setString(8, entry.getOrigin());
/* 222 */             stmt.setString(9, entry.getUserGroup());
/* 223 */             stmt.setString(10, entry.getDealerCode());
/* 224 */             stmt.setString(11, this.serverName);
/* 225 */             stmt.setString(12, entry.getDivisionCode());
/* 226 */             stmt.setString(13, entry.getOriginalCountryCode());
/* 227 */             stmt.setString(14, entry.getMappedCountryCode());
/* 228 */             stmt.setString(15, entry.getT2WGroup());
/* 229 */             stmt.addBatch();
/*     */           } 
/* 231 */           stmt.executeBatch();
/* 232 */           connection.commit();
/*     */         } finally {
/* 234 */           JDBCUtil.close(stmt, log);
/*     */         } 
/* 236 */       } catch (Exception e) {
/* 237 */         JDBCUtil.rollback(connection, log);
/* 238 */         throw e;
/*     */       } finally {
/*     */         
/* 241 */         releaseConnection(connection);
/*     */       } 
/* 243 */     } catch (Exception e) {
/* 244 */       log.error("unable to add " + entries.size() + " to database - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Collection entries) {
/* 250 */     if (!Util.isNullOrEmpty(entries)) {
/* 251 */       log.info("adding " + entries.size() + " entries to login log (database)");
/* 252 */       write(entries);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 257 */     return Util.getClassName(getClass()) + "@" + hashCode();
/*     */   }
/*     */   
/*     */   public Collection getEntries(LoginLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 261 */     log.debug("processing entries retrieval request ");
/*     */     try {
/* 263 */       return _getEntries(backendFilter, entryFilter, hitLimit);
/* 264 */     } catch (Exception e) {
/* 265 */       log.warn("...unable to process entries retrieval request - exception: " + e, e);
/* 266 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isAborted() {
/* 271 */     if (Thread.currentThread() instanceof CustomThread) {
/* 272 */       return ((CustomThread)Thread.currentThread()).isAborted();
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SuppressWarnings({"SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"})
/*     */   private Collection _getEntries(LoginLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 280 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 282 */       StringBuffer query = new StringBuffer("select a.entry_id, a.ts, a.username, a.status, a.source_address, a.login_parameters, a.origin, a.usergroup, a.dealercode, a.server_name, a.divisioncode,a.org_country,a.map_country,a.t2w_group from login_log a #");
/*     */       
/* 284 */       if (backendFilter != null) {
/* 285 */         if (backendFilter.getTimestampMIN() != null) {
/* 286 */           query.append("and a.ts >= " + backendFilter.getTimestampMIN().longValue() + " ");
/*     */         }
/* 288 */         if (backendFilter.getTimestampMAX() != null) {
/* 289 */           query.append("and a.ts <= " + backendFilter.getTimestampMAX().longValue() + " ");
/*     */         }
/*     */         
/* 292 */         if (backendFilter.getUsername() != null) {
/* 293 */           query.append("and UPPER(a.username) like UPPER('" + backendFilter.getUsername() + "') ");
/*     */         }
/*     */         
/* 296 */         if (backendFilter.getUsergroup() != null) {
/* 297 */           query.append("and UPPER(a.usergroup) like UPPER('" + backendFilter.getUsergroup() + "') ");
/*     */         }
/*     */         
/* 300 */         StringUtilities.replace(query, "#and", "where");
/*     */       } 
/* 302 */       StringUtilities.replace(query, "#", "");
/* 303 */       Collection<EntryImpl> entries = new LinkedList();
/* 304 */       PreparedStatement stmt = connection.prepareStatement(query.toString());
/*     */       try {
/* 306 */         if (hitLimit != -1) {
/* 307 */           stmt.setMaxRows(hitLimit);
/*     */         }
/* 309 */         ResultSet rs = JDBCUtil.asyncExecuteQuery(stmt);
/*     */         try {
/* 311 */           while (rs.next() && !isAborted() && (hitLimit == -1 || entries.size() <= hitLimit)) {
/* 312 */             long id = rs.getLong(1);
/* 313 */             long ts = rs.getLong(2);
/* 314 */             String userName = rs.getString(3);
/* 315 */             boolean success = convertStatus(rs.getString(4));
/* 316 */             String sourceAddress = rs.getString(5);
/* 317 */             String loginParams = rs.getString(6);
/* 318 */             String origin = rs.getString(7);
/* 319 */             String userGroup = rs.getString(8);
/* 320 */             String dealerCode = rs.getString(9);
/* 321 */             String serverName = rs.getString(10);
/* 322 */             String division = rs.getString(11);
/* 323 */             String orgCountry = rs.getString(12);
/* 324 */             String mapCountry = rs.getString(13);
/* 325 */             String t2wGroup = rs.getString(14);
/* 326 */             EntryImpl entry = new EntryImpl(ts, userName, success, sourceAddress, loginParams, origin, userGroup, dealerCode, division, orgCountry, mapCountry, t2wGroup, serverName, id);
/* 327 */             if (entryFilter == null || entryFilter.include(entry)) {
/* 328 */               entries.add(entry);
/*     */             }
/*     */           } 
/*     */           
/* 332 */           if (isAborted()) {
/* 333 */             throw new LoginLog.Query.AbortionException(entries);
/*     */           }
/* 335 */           return entries;
/*     */         } finally {
/*     */           
/* 338 */           rs.close();
/*     */         } 
/*     */       } finally {
/* 341 */         stmt.close();
/*     */       } 
/*     */     } finally {
/* 344 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean convertStatus(String status) {
/* 350 */     return "allow".equalsIgnoreCase(status);
/*     */   }
/*     */   
/*     */   public void delete(Collection entries) throws Exception {
/* 354 */     AssertUtil.ensure(entries, AssertUtil.NOT_NULL);
/*     */     
/* 356 */     log.debug("deleting " + entries.size() + " entries");
/* 357 */     Connection connection = getWriteConnection();
/*     */     try {
/* 359 */       PreparedStatement stmt = connection.prepareStatement("delete from login_log a where a.entry_id=?");
/*     */       try {
/* 361 */         for (Iterator<LoginLog.Entry2> iter = entries.iterator(); iter.hasNext(); ) {
/* 362 */           LoginLog.Entry2 entry = iter.next();
/* 363 */           stmt.setLong(1, entry.getIdentifier());
/* 364 */           stmt.addBatch();
/*     */         } 
/* 366 */         stmt.executeBatch();
/* 367 */         connection.commit();
/* 368 */       } catch (Exception e) {
/* 369 */         log.error("...unable to delete entries - exception:" + e, e);
/* 370 */         connection.rollback();
/* 371 */         throw e;
/*     */       } finally {
/* 373 */         stmt.close();
/*     */       } 
/*     */     } finally {
/* 376 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\LoginLog_DB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */