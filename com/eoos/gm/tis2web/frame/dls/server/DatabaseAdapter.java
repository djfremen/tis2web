/*     */ package com.eoos.gm.tis2web.frame.dls.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DatabaseAdapter
/*     */   implements IDatabase
/*     */ {
/*  34 */   private static final Logger log = Logger.getLogger(DatabaseAdapter.class);
/*     */   
/*  36 */   private static DatabaseAdapter instance = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*  40 */   private TimerTask cleanupTask = null;
/*     */ 
/*     */   
/*     */   private DatabaseAdapter() {
/*  44 */     final SubConfigurationWrapper config = new SubConfigurationWrapper((Configuration)ConfigurationServiceProvider.getService(), "frame.adapter.dls.db.");
/*  45 */     final IDatabaseLink backend = DatabaseLink.openDatabase((Configuration)subConfigurationWrapper);
/*  46 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  49 */             backend.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  54 */               return backend.requestConnection();
/*  55 */             } catch (Exception e) {
/*  56 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */     
/*  61 */     final int hash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/*     */     
/*  63 */     long cleanupDelay = 43200000L;
/*     */     try {
/*  65 */       Util.parseMillis(subConfigurationWrapper.getProperty("cleanup.delay"));
/*     */     }
/*  67 */     catch (Exception e) {
/*  68 */       log.warn("unable to read config parameter for cleanup delay, using default value");
/*     */     } 
/*  70 */     log.debug(Util.formatDuration(cleanupDelay, "cleanup delay is ${hours} hour(s), ${minutes} minute(s)"));
/*     */     
/*  72 */     this.cleanupTask = Util.createTimerTask(new Runnable()
/*     */         {
/*     */           public void run() {
/*  75 */             DatabaseAdapter.log.debug("performing cleanup...");
/*     */             try {
/*  77 */               Connection connection = DatabaseAdapter.this.getWriteConnection();
/*     */               try {
/*  79 */                 PreparedStatement stmt = connection.prepareStatement("delete from lease_registry a where a.expiration_date < ? ");
/*     */                 try {
/*  81 */                   stmt.setLong(1, System.currentTimeMillis());
/*  82 */                   int count = stmt.executeUpdate();
/*  83 */                   DatabaseAdapter.log.debug("... removed " + count + " obsolete entries");
/*     */                 } finally {
/*  85 */                   JDBCUtil.close(stmt);
/*     */                 } 
/*  87 */                 connection.commit();
/*  88 */               } catch (Exception e) {
/*  89 */                 JDBCUtil.rollback(connection, DatabaseAdapter.log);
/*  90 */                 throw e;
/*     */               } finally {
/*  92 */                 DatabaseAdapter.this.releaseConnection(connection);
/*     */               }
/*     */             
/*  95 */             } catch (Exception e) {
/*  96 */               DatabaseAdapter.log.warn("unable to perform cleanup, ignoring - exception: " + e, e);
/*     */             } 
/*     */           }
/*     */         });
/* 100 */     Util.getTimer().schedule(this.cleanupTask, 0L, cleanupDelay);
/*     */     
/* 102 */     ConfigurationServiceProvider.getService().addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/* 105 */             if (ConfigurationUtil.configurationHash(config) != hash) {
/* 106 */               ConfigurationServiceProvider.getService().removeObserver(this);
/* 107 */               DatabaseAdapter.this.cleanupTask.cancel();
/* 108 */               DatabaseAdapter.reset();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Connection getReadConnection() {
/* 116 */     Connection ret = this.connectionProvider.getConnection();
/*     */     try {
/* 118 */       ret.setReadOnly(true);
/* 119 */       ret.setAutoCommit(false);
/* 120 */     } catch (SQLException e) {
/* 121 */       throw new RuntimeException(e);
/*     */     } 
/* 123 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() {
/* 127 */     Connection ret = this.connectionProvider.getConnection();
/*     */     try {
/* 129 */       ret.setReadOnly(false);
/* 130 */       ret.setAutoCommit(false);
/* 131 */     } catch (SQLException e) {
/* 132 */       throw new RuntimeException(e);
/*     */     } 
/* 134 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 138 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/* 142 */     instance = null;
/*     */   }
/*     */   
/*     */   public static synchronized DatabaseAdapter getInstance() {
/* 146 */     if (instance == null) {
/* 147 */       instance = new DatabaseAdapter();
/*     */     }
/* 149 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean existsSWK(Connection connection, SoftwareKey swk, String sessionID) throws Exception {
/* 154 */     PreparedStatement stmt = connection.prepareStatement("select a.swk from swk_registry a where a.swk=? and UPPER(a.user_id)=?");
/*     */     try {
/* 156 */       stmt.setString(1, swk.toString());
/* 157 */       stmt.setString(2, sessionID.toUpperCase(Locale.ENGLISH));
/* 158 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 160 */         return rs.next();
/*     */       } finally {
/* 162 */         JDBCUtil.close(rs, log);
/*     */       } 
/*     */     } finally {
/* 165 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean registerSoftwareKey(SoftwareKey swk, String sessionID, String clientIP) {
/* 170 */     if (log.isDebugEnabled()) {
/* 171 */       log.debug("registering software key: " + String.valueOf(swk) + " for user: " + String.valueOf(sessionID) + ", clientIP: " + String.valueOf(clientIP));
/*     */     }
/*     */     try {
/* 174 */       Connection connection = getWriteConnection();
/*     */       try {
/* 176 */         if (!existsSWK(connection, swk, sessionID)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 181 */           String maxCount = SoftwareKeyCheckServer.getValueOf(SoftwareKeyCheckServer.getPortal(sessionID));
/* 182 */           int maxNumberOfConnections = Integer.valueOf((maxCount == null || maxCount.equals("") || maxCount.equalsIgnoreCase("null")) ? "-1" : maxCount).intValue();
/*     */           
/* 184 */           PreparedStatement stmt = null;
/*     */           
/* 186 */           if (maxNumberOfConnections > 0) {
/* 187 */             stmt = connection.prepareStatement("select count(a.user_id) from swk_registry a where upper(a.user_id)=? ");
/*     */             try {
/* 189 */               stmt.setString(1, sessionID.toUpperCase(Locale.ENGLISH));
/* 190 */               ResultSet rs = stmt.executeQuery();
/*     */               try {
/* 192 */                 if (rs.next()) {
/* 193 */                   if (rs.getInt(1) >= maxNumberOfConnections) {
/* 194 */                     log.debug("registering software key: Found <" + Integer.valueOf(rs.getInt(1)).toString() + "> max_count <" + maxNumberOfConnections + "> exceeded");
/* 195 */                     return false;
/*     */                   } 
/* 197 */                   log.debug("registering software key: Found <" + Integer.valueOf(rs.getInt(1)).toString() + "> max_count <" + maxNumberOfConnections + ">. Start insert into swk_registry for swk <" + String.valueOf(swk) + "> user <" + String.valueOf(sessionID) + "> clientIP <" + String.valueOf(clientIP) + ">");
/*     */                 } 
/*     */               } finally {
/* 200 */                 JDBCUtil.close(rs, log);
/*     */               } 
/*     */             } finally {
/* 203 */               JDBCUtil.close(stmt, log);
/*     */             } 
/*     */           } else {
/* 206 */             log.debug("registering software key: no max_count defined => number of session not limited for swk <" + String.valueOf(swk) + "> user <" + String.valueOf(sessionID) + "> clientIP <" + String.valueOf(clientIP) + ">");
/*     */           } 
/*     */           
/* 209 */           stmt = connection.prepareStatement("insert into swk_registry(swk,user_id,client_ip,ts,ts_display) values(?,?,?,?,?)");
/*     */           try {
/* 211 */             stmt.setString(1, swk.toString());
/* 212 */             stmt.setString(2, sessionID);
/* 213 */             stmt.setString(3, clientIP);
/*     */             
/* 215 */             long ts = System.currentTimeMillis();
/* 216 */             stmt.setLong(4, ts);
/* 217 */             stmt.setString(5, Util.formatDate(ts));
/* 218 */             if (stmt.executeUpdate() != 1) {
/* 219 */               throw new IllegalStateException();
/*     */             }
/* 221 */             log.debug("...done");
/*     */           } finally {
/* 223 */             JDBCUtil.close(stmt, log);
/*     */           } 
/*     */         } else {
/* 226 */           log.debug("Software key <" + swk.toString() + "> already exists. No registration needed.");
/*     */         } 
/* 228 */         connection.commit();
/* 229 */         return true;
/* 230 */       } catch (Exception t) {
/* 231 */         connection.rollback();
/* 232 */         throw t;
/*     */       } finally {
/* 234 */         releaseConnection(connection);
/*     */       }
/*     */     
/* 237 */     } catch (Exception e) {
/* 238 */       log.error("unable to register software key, returning false - exception: " + e, e);
/* 239 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean registerLease(SoftwareKey swk, Lease lease, String sessionID, String group, String clientIP, String dealerCode, String country) {
/* 244 */     if (log.isDebugEnabled()) {
/* 245 */       log.debug("registering lease for swk: " + String.valueOf(swk) + ", lease: " + String.valueOf(lease) + ", sessionID: " + String.valueOf(sessionID) + ", userGroup: " + String.valueOf(group) + ", clientIP: " + String.valueOf(clientIP) + "...");
/*     */     }
/* 247 */     boolean ret = false;
/*     */     try {
/* 249 */       Connection connection = getWriteConnection();
/*     */       try {
/* 251 */         PreparedStatement stmt = connection.prepareStatement("insert into lease_registry(swk, sec_token, expiration_date, user_id, user_group, client_ip,ts,ts_display, bac, country) values(?,?,?,?,?,?,?,?,?,?)");
/*     */         try {
/* 253 */           stmt.setString(1, swk.toString());
/* 254 */           stmt.setString(2, ((ILeaseInternal)lease).getSecurityToken());
/* 255 */           stmt.setLong(3, ((ILeaseInternal)lease).getExpirationDate());
/* 256 */           stmt.setString(4, sessionID);
/* 257 */           stmt.setString(5, group);
/* 258 */           stmt.setString(6, clientIP);
/*     */           
/* 260 */           long ts = System.currentTimeMillis();
/* 261 */           stmt.setLong(7, ts);
/* 262 */           stmt.setString(8, Util.formatDate(ts));
/* 263 */           stmt.setString(9, dealerCode);
/* 264 */           stmt.setString(10, country);
/*     */           
/* 266 */           if (stmt.executeUpdate() != 1) {
/* 267 */             throw new IllegalStateException();
/*     */           }
/* 269 */           ret = true;
/*     */         } finally {
/*     */           
/* 272 */           JDBCUtil.close(stmt, log);
/*     */         } 
/* 274 */         connection.commit();
/* 275 */       } catch (Exception t) {
/* 276 */         connection.rollback();
/* 277 */         throw t;
/*     */       } finally {
/* 279 */         releaseConnection(connection);
/*     */       } 
/* 281 */     } catch (Exception e) {
/* 282 */       log.error("unable to register lease, returning false - exception: " + e, e);
/* 283 */       ret = false;
/*     */     } 
/* 285 */     log.debug("...successful=" + ret);
/* 286 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean validateSoftwareKey(SoftwareKey swk) {
/* 290 */     log.debug("validating software key : " + String.valueOf(swk) + "...");
/* 291 */     boolean ret = false;
/*     */     try {
/* 293 */       Connection connection = getReadConnection();
/*     */       try {
/* 295 */         PreparedStatement stmt = connection.prepareStatement("select * from swk_registry a where a.swk=? ");
/*     */         try {
/* 297 */           stmt.setString(1, swk.toString());
/* 298 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 300 */             ret = rs.next();
/*     */           } finally {
/* 302 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 306 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 310 */         releaseConnection(connection);
/*     */       } 
/* 312 */     } catch (Exception e) {
/* 313 */       log.error("unable to validate software key, returning false - exception: " + e, e);
/* 314 */       ret = false;
/*     */     } 
/* 316 */     log.debug("...valid=" + ret);
/* 317 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean validateLease(SoftwareKey swk, Lease _lease) {
/* 321 */     if (log.isDebugEnabled()) {
/* 322 */       log.debug("validating lease : " + String.valueOf(_lease) + ", swk: " + String.valueOf(swk) + "...");
/*     */     }
/*     */     
/* 325 */     boolean ret = false;
/* 326 */     ILeaseInternal lease = (ILeaseInternal)_lease;
/*     */     try {
/* 328 */       if (validateSoftwareKey(swk)) {
/*     */         
/* 330 */         Connection connection = getReadConnection();
/*     */         try {
/* 332 */           PreparedStatement stmt = connection.prepareStatement("select a.expiration_date from lease_registry a where a.swk=?  and a.sec_token=? and a.expiration_date=?");
/*     */           try {
/* 334 */             stmt.setString(1, swk.toString());
/* 335 */             stmt.setString(2, lease.getSecurityToken());
/* 336 */             stmt.setLong(3, lease.getExpirationDate());
/* 337 */             ResultSet rs = stmt.executeQuery();
/*     */             try {
/* 339 */               ret = rs.next();
/*     */             } finally {
/* 341 */               JDBCUtil.close(rs, log);
/*     */             } 
/*     */           } finally {
/*     */             
/* 345 */             JDBCUtil.close(stmt, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 349 */           releaseConnection(connection);
/*     */         } 
/*     */       } 
/* 352 */     } catch (Exception e) {
/* 353 */       log.error("unable to validate software key, returning false - exception: " + e, e);
/* 354 */       ret = false;
/*     */     } 
/* 356 */     if (log.isDebugEnabled()) {
/* 357 */       log.debug("...validation " + (ret ? " was successful" : "FAILED"));
/*     */     }
/* 359 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserGroup(SoftwareKey swk, Lease lease) {
/* 364 */     String group = "";
/*     */     try {
/* 366 */       Connection connection = getReadConnection();
/*     */       try {
/* 368 */         PreparedStatement stmt = connection.prepareStatement("select a.user_group from lease_registry a where a.swk=? and a.expiration_date=?");
/*     */         try {
/* 370 */           stmt.setString(1, swk.toString());
/* 371 */           stmt.setLong(2, ((ILeaseInternal)lease).getExpirationDate());
/* 372 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 374 */             if (rs.next()) {
/* 375 */               group = rs.getString(1);
/*     */             }
/* 377 */             return group;
/*     */           } finally {
/*     */             
/* 380 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 384 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 388 */         releaseConnection(connection);
/*     */       }
/*     */     
/* 391 */     } catch (Exception e) {
/* 392 */       log.error("unable to determine user group for swk: " + String.valueOf(swk) + " and lease: " + String.valueOf(lease) + ", returning empty group string - exception: " + e, e);
/* 393 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getSessionIDs(SoftwareKey swk) {
/*     */     try {
/* 399 */       Connection connection = getReadConnection();
/*     */       try {
/* 401 */         PreparedStatement stmt = connection.prepareStatement("select a.user_id from swk_registry a where a.swk=? order by a.ts asc");
/*     */         try {
/* 403 */           stmt.setString(1, swk.toString());
/* 404 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 406 */             Collection<String> ret = new LinkedList();
/* 407 */             while (rs.next()) {
/* 408 */               ret.add(rs.getString(1));
/*     */             }
/* 410 */             return ret;
/*     */           } finally {
/* 412 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/* 415 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/* 418 */         releaseConnection(connection);
/*     */       }
/*     */     
/* 421 */     } catch (Exception e) {
/* 422 */       log.error("unable to lookup userID for swk: " + String.valueOf(swk) + ", returning null - exception:" + e, e);
/* 423 */       return null;
/*     */     } 
/*     */   }
/*     */   public Collection getUsernames(String usernamePattern) throws Exception {
/*     */     Collection<String> ret;
/* 428 */     log.debug("retrieving usernames for pattern: " + String.valueOf(usernamePattern));
/*     */     
/* 430 */     if (Util.isNullOrEmpty(usernamePattern)) {
/* 431 */       ret = Collections.EMPTY_SET;
/*     */     } else {
/* 433 */       usernamePattern = usernamePattern.replace('*', '%').replace('?', '_');
/* 434 */       Connection connection = getReadConnection();
/*     */       try {
/* 436 */         ret = new HashSet();
/*     */         
/* 438 */         PreparedStatement stmt = connection.prepareStatement("select distinct a.user_id from swk_registry a where a.user_id like ?");
/*     */         try {
/* 440 */           stmt.setString(1, usernamePattern);
/* 441 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 443 */             while (rs.next()) {
/* 444 */               ret.add(rs.getString(1));
/*     */             }
/*     */           } finally {
/* 447 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 451 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */ 
/*     */         
/* 455 */         stmt = connection.prepareStatement("select distinct a.user_id from lease_registry a where a.user_id like ?");
/*     */         try {
/* 457 */           stmt.setString(1, usernamePattern);
/* 458 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 460 */             while (rs.next()) {
/* 461 */               ret.add(rs.getString(1));
/*     */             }
/*     */           } finally {
/*     */             
/* 465 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 469 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */         
/* 472 */         log.debug("...retrieved " + ret.size() + " entries");
/*     */       } finally {
/*     */         
/* 475 */         releaseConnection(connection);
/*     */       } 
/*     */     } 
/* 478 */     return ret;
/*     */   }
/*     */   
/*     */   public final void deleteLeases(Collection userids) throws Exception {
/* 482 */     if (!Util.isNullOrEmpty(userids)) {
/* 483 */       Connection connection = getWriteConnection();
/*     */       try {
/* 485 */         deleteLeases(connection, userids);
/*     */         
/* 487 */         connection.commit();
/* 488 */       } catch (Exception e) {
/* 489 */         connection.rollback();
/* 490 */         throw new Exception("unable to delete leases for : " + userids, e);
/*     */       } finally {
/* 492 */         releaseConnection(connection);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteLeases(Connection connection, Collection userIDs) throws SQLException {
/* 498 */     PreparedStatement stmt = connection.prepareStatement("delete from lease_registry a where a.user_id=?");
/*     */     try {
/* 500 */       for (Iterator<String> iter = userIDs.iterator(); iter.hasNext(); ) {
/* 501 */         String id = iter.next();
/* 502 */         stmt.setString(1, id);
/* 503 */         int count = stmt.executeUpdate();
/* 504 */         log.debug("...deleted (uncommitted yet) " + count + " leases for user: " + id);
/*     */       } 
/*     */     } finally {
/*     */       
/* 508 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getUsedSoftwareKeys(Connection connection, String userID) throws SQLException {
/* 513 */     Collection<String> ret = new LinkedHashSet();
/*     */     
/* 515 */     PreparedStatement stmt = connection.prepareStatement("select a.swk from lease_registry a where a.user_id=?");
/*     */     try {
/* 517 */       stmt.setString(1, userID);
/* 518 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 520 */         while (rs.next()) {
/* 521 */           ret.add(rs.getString(1));
/*     */         }
/*     */       } finally {
/*     */         
/* 525 */         JDBCUtil.close(rs, log);
/*     */       } 
/*     */     } finally {
/* 528 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */ 
/*     */     
/* 532 */     stmt = connection.prepareStatement("select a.swk from swk_registry a where a.user_id=?");
/*     */     try {
/* 534 */       stmt.setString(1, userID);
/* 535 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 537 */         while (rs.next()) {
/* 538 */           ret.add(rs.getString(1));
/*     */         }
/*     */       } finally {
/*     */         
/* 542 */         JDBCUtil.close(rs, log);
/*     */       } 
/*     */     } finally {
/* 545 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */     
/* 548 */     return ret;
/*     */   }
/*     */   
/*     */   private void deleteSoftwareKeys(Connection connection, Collection userIDs) throws SQLException {
/* 552 */     PreparedStatement stmt = connection.prepareStatement("delete from swk_registry a where a.swk=?");
/*     */     try {
/* 554 */       for (Iterator<String> iterUsers = userIDs.iterator(); iterUsers.hasNext(); ) {
/* 555 */         String id = iterUsers.next();
/*     */         
/* 557 */         Collection swks = getUsedSoftwareKeys(connection, id);
/* 558 */         for (Iterator<String> iterSWKs = swks.iterator(); iterSWKs.hasNext(); ) {
/* 559 */           String swk = iterSWKs.next();
/* 560 */           stmt.setString(1, swk);
/* 561 */           if (stmt.executeUpdate() == 1) {
/* 562 */             log.debug("...deleted (uncommitted yet) software key: " + swk + " for user: " + id);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 568 */       JDBCUtil.close(stmt, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteSoftwareKeys(Collection userIDs) throws Exception {
/* 573 */     if (!Util.isNullOrEmpty(userIDs)) {
/* 574 */       Connection connection = getWriteConnection();
/*     */       try {
/* 576 */         deleteSoftwareKeys(connection, userIDs);
/* 577 */         deleteLeases(connection, userIDs);
/*     */         
/* 579 */         connection.commit();
/* 580 */       } catch (Exception e) {
/* 581 */         connection.rollback();
/* 582 */         throw new Exception("unable to delete software keys for : " + userIDs, e);
/*     */       } finally {
/* 584 */         releaseConnection(connection);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getBAC(SoftwareKey swk, Lease lease) {
/*     */     String str;
/*     */     try {
/* 592 */       Connection connection = getReadConnection();
/*     */       try {
/* 594 */         PreparedStatement stmt = connection.prepareStatement("select a.bac from lease_registry a where a.swk=? and a.sec_token=?");
/*     */         try {
/* 596 */           stmt.setString(1, swk.toString());
/* 597 */           stmt.setString(2, ((ILeaseInternal)lease).getSecurityToken());
/* 598 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 600 */             if (rs.next()) {
/* 601 */               str = rs.getString(1);
/*     */             } else {
/* 603 */               str = null;
/*     */             } 
/*     */           } finally {
/*     */             
/* 607 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 611 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 615 */         releaseConnection(connection);
/*     */       }
/*     */     
/* 618 */     } catch (Exception e) {
/* 619 */       log.error("unable to read dealercode for swk: " + String.valueOf(swk) + " and lease: " + String.valueOf(lease) + ", returning null - exception: " + e, e);
/* 620 */       str = null;
/*     */     } 
/* 622 */     return str;
/*     */   }
/*     */   
/*     */   public String getCountryCode(SoftwareKey swk, Lease lease) {
/*     */     String str;
/*     */     try {
/* 628 */       Connection connection = getReadConnection();
/*     */       try {
/* 630 */         PreparedStatement stmt = connection.prepareStatement("select a.country from lease_registry a where a.swk=? and a.sec_token=?");
/*     */         try {
/* 632 */           stmt.setString(1, swk.toString());
/* 633 */           stmt.setString(2, ((ILeaseInternal)lease).getSecurityToken());
/* 634 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 636 */             if (rs.next()) {
/* 637 */               str = rs.getString(1);
/*     */             } else {
/* 639 */               str = null;
/*     */             } 
/*     */           } finally {
/*     */             
/* 643 */             JDBCUtil.close(rs, log);
/*     */           } 
/*     */         } finally {
/*     */           
/* 647 */           JDBCUtil.close(stmt, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 651 */         releaseConnection(connection);
/*     */       }
/*     */     
/* 654 */     } catch (Exception e) {
/* 655 */       log.error("unable to read country for swk: " + String.valueOf(swk) + " and lease: " + String.valueOf(lease) + ", returning null - exception: " + e, e);
/* 656 */       str = null;
/*     */     } 
/* 658 */     return str;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */