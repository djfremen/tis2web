/*     */ package com.eoos.gm.tis2web.dtc.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.dtc.service.DTCStorageService;
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.LoggedDTC;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.thread.CustomThread;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Blob;
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
/*     */ public class DTCLogImpl
/*     */   implements DTCLog
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(DTCLogImpl.class);
/*     */   
/*  38 */   private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH);
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private Configuration configuration;
/*     */   
/*     */   public DTCLogImpl() throws Exception {
/*  45 */     this.configuration = (Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.dtclog.");
/*  46 */     final IDatabaseLink dblink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(this.configuration, "db."));
/*     */     
/*  48 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  51 */             dblink.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  56 */               return dblink.requestConnection();
/*  57 */             } catch (Exception e) {
/*  58 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  300000L);
/*     */     
/*  63 */     releaseConnection(getReadConnection());
/*     */   }
/*     */ 
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/*  68 */     Connection ret = this.connectionProvider.getConnection();
/*  69 */     ret.setAutoCommit(false);
/*  70 */     ret.setReadOnly(true);
/*  71 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws Exception {
/*  75 */     Connection ret = this.connectionProvider.getConnection();
/*  76 */     ret.setReadOnly(false);
/*  77 */     ret.setAutoCommit(false);
/*  78 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  82 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private long getID(Connection connection) throws Exception {
/*  86 */     long retValue = -1L;
/*     */     
/*  88 */     String query1 = "select nextkey from dtc_keygen";
/*  89 */     PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */     try {
/*  91 */       String query2 = "update dtc_keygen set nextkey=? where nextkey=?";
/*  92 */       PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */ 
/*     */       
/*     */       try { while (true) {
/*  96 */           ResultSet rs = stmt1.executeQuery();
/*     */           try {
/*  98 */             if (rs.next()) {
/*  99 */               retValue = rs.getLong(1);
/*     */             } else {
/* 101 */               throw new IllegalStateException();
/*     */             } 
/*     */           } finally {
/*     */             
/* 105 */             rs.close();
/*     */           } 
/*     */           
/* 108 */           stmt2.setLong(1, retValue + 1L);
/* 109 */           stmt2.setLong(2, retValue);
/* 110 */           int result = stmt2.executeUpdate();
/* 111 */           if (result != 1) {
/*     */             
/* 113 */             log.info("id: " + retValue + " is already in use, retrying");
/* 114 */             retValue = -1L;
/*     */           } 
/*     */           
/* 117 */           if (retValue != -1L)
/* 118 */             return retValue; 
/*     */         }  }
/* 120 */       finally { stmt2.close(); }
/*     */     
/*     */     } finally {
/* 123 */       stmt1.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Collection dtcs, DTCStorageService.Callback callback) throws Exception {
/* 129 */     if (dtcs == null || dtcs.size() == 0) {
/*     */       return;
/*     */     }
/* 132 */     log.info("writing dtcs to database");
/* 133 */     Connection connection = getWriteConnection();
/*     */     try {
/* 135 */       String query = "insert into dtc_data values (?, ?, ?, ?, ?,?,?,?)";
/* 136 */       PreparedStatement stmt = connection.prepareStatement(query);
/*     */       try {
/* 138 */         for (Iterator<DTC> iter = dtcs.iterator(); iter.hasNext(); ) {
/* 139 */           DTC dtc = iter.next();
/* 140 */           if (dtc.getContent() != null) {
/* 141 */             long ts = System.currentTimeMillis();
/* 142 */             long id = getID(connection);
/*     */             
/* 144 */             stmt.setLong(1, id);
/* 145 */             String bac = dtc.getBACCode();
/* 146 */             if (Util.isNullOrEmpty(bac) && callback != null) {
/* 147 */               bac = callback.getBAC();
/*     */             }
/* 149 */             stmt.setString(2, bac);
/* 150 */             stmt.setLong(3, ts);
/* 151 */             stmt.setString(4, this.dateFormat.format(new Date(ts)));
/* 152 */             stmt.setBytes(5, dtc.getContent());
/*     */             
/* 154 */             String country = dtc.getCountryCode();
/* 155 */             if (Util.isNullOrEmpty(country) && callback != null) {
/* 156 */               country = callback.getCountry();
/*     */             }
/* 158 */             stmt.setString(6, country);
/*     */             
/* 160 */             stmt.setString(7, dtc.getApplicationID());
/* 161 */             stmt.setString(8, dtc.getPortalID());
/*     */             
/* 163 */             if (stmt.executeUpdate() != 1)
/* 164 */               throw new IllegalStateException(); 
/*     */             continue;
/*     */           } 
/* 167 */           log.warn("...skipping <null> dtc : " + dtc);
/*     */         } 
/*     */       } finally {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 174 */           stmt.close();
/* 175 */         } catch (SQLException e) {
/* 176 */           log.error("unable to close statement - exception (ignored):" + e, e);
/*     */         } 
/*     */       } 
/*     */       
/* 180 */       connection.commit();
/* 181 */       log.debug("....wrote " + dtcs.size() + " dtcs to database");
/* 182 */     } catch (Exception e) {
/* 183 */       JDBCUtil.rollback(connection, log);
/* 184 */       throw e;
/*     */     } finally {
/* 186 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAborted() {
/* 192 */     if (Thread.currentThread() instanceof CustomThread) {
/* 193 */       return ((CustomThread)Thread.currentThread()).isAborted();
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assertNotAborted() {
/* 200 */     if (Thread.currentThread() instanceof CustomThread) {
/* 201 */       ((CustomThread)Thread.currentThread()).assertNotAborted();
/*     */     }
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"})
/*     */   public Collection getEntries(DTCLog.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 207 */     log.info("processing entries retrieval request");
/* 208 */     Connection connection = getReadConnection();
/*     */     try {
/* 210 */       StringBuffer query = new StringBuffer("select a.dtc_id, a.bac_code,a.ts,a.dtc_content, a.country_code, a.application_id, a.portal_id from dtc_data a #");
/*     */       
/* 212 */       if (backendFilter != null) {
/* 213 */         if (backendFilter.getTimestampMIN() != null) {
/* 214 */           query.append("and a.ts >= " + backendFilter.getTimestampMIN().longValue() + " ");
/*     */         }
/* 216 */         if (backendFilter.getTimestampMAX() != null) {
/* 217 */           query.append("and a.ts <= " + backendFilter.getTimestampMAX().longValue() + " ");
/*     */         }
/*     */         
/* 220 */         if (backendFilter.getBACCodePattern() != null) {
/* 221 */           query.append("and a.bac_code like '" + backendFilter.getBACCodePattern() + "' ");
/*     */         }
/*     */         
/* 224 */         if (backendFilter.getIdMin() != null) {
/* 225 */           query.append("and a.dtc_id >= " + backendFilter.getIdMin().longValue() + " ");
/*     */         }
/* 227 */         if (backendFilter.getIdMax() != null) {
/* 228 */           query.append("and a.dtc_id <= " + backendFilter.getIdMax().longValue() + " ");
/*     */         }
/* 230 */         if (!Util.isNullOrEmpty(backendFilter.getApplicationIDs())) {
/* 231 */           query.append("and LOWER(a.application_id) in (");
/* 232 */           for (Iterator iter = backendFilter.getApplicationIDs().iterator(); iter.hasNext(); ) {
/* 233 */             query.append("'").append(String.valueOf(iter.next()).toLowerCase(Locale.ENGLISH)).append("'");
/* 234 */             if (iter.hasNext()) {
/* 235 */               query.append(", "); continue;
/*     */             } 
/* 237 */             query.append(")");
/*     */           } 
/*     */         } 
/*     */         
/* 241 */         if (!Util.isNullOrEmpty(backendFilter.getPortalIDs(DTCLog.BackendFilter.Type.INCLUDE))) {
/* 242 */           query.append("and LOWER(a.portal_id) in (");
/* 243 */           for (Iterator iter = backendFilter.getPortalIDs(DTCLog.BackendFilter.Type.INCLUDE).iterator(); iter.hasNext(); ) {
/* 244 */             query.append("'").append(String.valueOf(iter.next()).toLowerCase(Locale.ENGLISH)).append("'");
/* 245 */             if (iter.hasNext()) {
/* 246 */               query.append(", "); continue;
/*     */             } 
/* 248 */             query.append(")");
/*     */           }
/*     */         
/* 251 */         } else if (!Util.isNullOrEmpty(backendFilter.getPortalIDs(DTCLog.BackendFilter.Type.EXCLUDE))) {
/* 252 */           query.append("and LOWER(a.portal_id) not in (");
/* 253 */           for (Iterator iter = backendFilter.getPortalIDs(DTCLog.BackendFilter.Type.EXCLUDE).iterator(); iter.hasNext(); ) {
/* 254 */             query.append("'").append(String.valueOf(iter.next()).toLowerCase(Locale.ENGLISH)).append("'");
/* 255 */             if (iter.hasNext()) {
/* 256 */               query.append(", "); continue;
/*     */             } 
/* 258 */             query.append(")");
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 263 */         if (!Util.isNullOrEmpty(backendFilter.getCountryCodes())) {
/* 264 */           query.append("and LOWER(a.country_code) in (");
/* 265 */           for (Iterator iter = backendFilter.getCountryCodes().iterator(); iter.hasNext(); ) {
/* 266 */             query.append("'").append(String.valueOf(iter.next()).toLowerCase(Locale.ENGLISH)).append("'");
/* 267 */             if (iter.hasNext()) {
/* 268 */               query.append(", "); continue;
/*     */             } 
/* 270 */             query.append(")");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 277 */       query.append(" order by a.dtc_id  asc");
/*     */       
/* 279 */       StringUtilities.replace(query, "#and", "where");
/* 280 */       StringUtilities.replace(query, "#", "");
/* 281 */       StringUtilities.replace(query, "  ", " ");
/* 282 */       log.debug("querying dtc log entries - query:" + query);
/*     */       
/* 284 */       PreparedStatement stmt = connection.prepareStatement(query.toString());
/*     */       try {
/* 286 */         if (hitLimit != -1 && hitLimit > 0) {
/* 287 */           stmt.setMaxRows(hitLimit);
/*     */         }
/*     */         
/* 290 */         Collection<LoggedDTC> entries = new LinkedList();
/* 291 */         ResultSet rs = JDBCUtil.asyncExecuteQuery(stmt);
/*     */         try {
/* 293 */           while (rs.next()) {
/* 294 */             assertNotAborted();
/* 295 */             long id = rs.getLong(1);
/* 296 */             String bacCode = rs.getString(2);
/* 297 */             long ts = rs.getLong(3);
/* 298 */             Blob blob = rs.getBlob(4);
/* 299 */             if (blob != null) {
/* 300 */               byte[] content = null;
/* 301 */               InputStream is = blob.getBinaryStream();
/*     */               try {
/* 303 */                 content = StreamUtil.readFully(is);
/*     */               } finally {
/* 305 */                 is.close();
/*     */               } 
/* 307 */               String countryCode = rs.getString(5);
/* 308 */               String applicationID = rs.getString(6);
/* 309 */               String portalID = rs.getString(7);
/* 310 */               entries.add(new LoggedDTC(id, ts, bacCode, countryCode, content, applicationID, portalID)); continue;
/*     */             } 
/* 312 */             log.warn("...skipping <null> dtc (id: " + id + ")");
/*     */           } 
/*     */           
/* 315 */           return entries;
/* 316 */         } catch (Exception e) {
/* 317 */           if (isAborted()) {
/* 318 */             throw new DTCLog.AbortionException(entries);
/*     */           }
/* 320 */           throw e;
/*     */         } finally {
/*     */           
/* 323 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 327 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/*     */       
/* 331 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void delete(Collection loggedDTCs) throws Exception {
/* 336 */     log.info("deleting dtcs from database");
/* 337 */     Connection connection = getWriteConnection();
/*     */     try {
/* 339 */       String query = "delete from dtc_data a where a.dtc_id=?";
/* 340 */       PreparedStatement stmt = connection.prepareStatement(query);
/*     */       try {
/* 342 */         for (Iterator<DTC.Logged> iter = loggedDTCs.iterator(); iter.hasNext(); ) {
/* 343 */           DTC.Logged loggedDTC = iter.next();
/* 344 */           stmt.setLong(1, loggedDTC.getID());
/* 345 */           stmt.addBatch();
/*     */         } 
/* 347 */         stmt.executeBatch();
/*     */       } finally {
/* 349 */         JDBCUtil.close(stmt, log);
/*     */       } 
/* 351 */       connection.commit();
/* 352 */     } catch (Exception e) {
/* 353 */       JDBCUtil.rollback(connection, log);
/* 354 */       throw e;
/*     */     } finally {
/* 356 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\DTCLogImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */