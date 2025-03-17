/*     */ package com.eoos.gm.tis2web.registration.server.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.registration.common.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.server.RegistryImpl;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationAttribute;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFilter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SortDirection;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class RegistrationData
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(RegistrationData.class);
/*     */   public static final int CREATE = 0;
/*     */   public static final int PARTIAL = 1;
/*     */   public static final int COMPLETE = 2;
/*     */   public static final String INDICATOR = "~";
/*     */   public static final String DELIMITER = ",";
/*     */   protected static Connection dbexport;
/*     */   protected static PreparedStatement dbdata;
/*     */   
/*     */   public static String getSubscriberID(Registry registry) {
/*  41 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/*  42 */     Connection conn = null;
/*     */     try {
/*  44 */       conn = databaseLink.requestConnection();
/*  45 */       return "T" + format(getID(conn), 9);
/*  46 */     } catch (Exception e) {
/*  47 */       log.debug(e);
/*     */     } finally {
/*  49 */       if (conn != null) {
/*  50 */         databaseLink.releaseConnection(conn);
/*     */       }
/*     */     } 
/*  53 */     return null;
/*     */   }
/*     */   
/*     */   public static String getRequestID(Registry registry, RequestType type) {
/*  57 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/*  58 */     Connection conn = null;
/*     */     try {
/*  60 */       conn = databaseLink.requestConnection();
/*  61 */       if (type == null)
/*  62 */         return "M" + format(getID(conn), 9); 
/*  63 */       if (type.equals(RequestType.STANDARD))
/*  64 */         return "R" + format(getID(conn), 9); 
/*  65 */       if (type.equals(RequestType.HWKMIGRATION))
/*  66 */         return "H" + format(getID(conn), 9); 
/*  67 */       if (type.equals(RequestType.EXTENSION))
/*  68 */         return "E" + format(getID(conn), 9); 
/*  69 */       if (type.equals(RequestType.REPEAT)) {
/*  70 */         return "X" + format(getID(conn), 9);
/*     */       }
/*  72 */       return "G" + format(getID(conn), 9);
/*     */     }
/*  74 */     catch (Exception e) {
/*  75 */       log.debug(e);
/*     */     } finally {
/*  77 */       if (conn != null) {
/*  78 */         databaseLink.releaseConnection(conn);
/*     */       }
/*     */     } 
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   private static String format(int value, int length) {
/*  85 */     String data = Integer.toString(value);
/*  86 */     if (data.length() < length) {
/*  87 */       StringBuffer padding = new StringBuffer();
/*  88 */       while (padding.length() < length - data.length()) {
/*  89 */         padding.append('0');
/*     */       }
/*  91 */       data = padding + data;
/*     */     } 
/*  93 */     return data;
/*     */   }
/*     */   
/*     */   public static void deleteRegistrationRecord(Registry registry, Registration registration) {
/*  97 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/*  98 */     Connection conn = null;
/*  99 */     PreparedStatement stmt = null;
/*     */     try {
/* 101 */       conn = databaseLink.requestConnection();
/* 102 */       deleteDealershipRecord(conn, ((DealershipEntity)registration.getDealership()).getDealershipPK());
/* 103 */       String sql = "DELETE FROM Registration WHERE \"RegistrationPK\" = ?";
/* 104 */       stmt = conn.prepareStatement(sql);
/* 105 */       stmt.setInt(1, ((RegistrationEntity)registration).getRequestPK());
/* 106 */       stmt.execute();
/* 107 */     } catch (RegistrationException r) {
/* 108 */       throw r;
/* 109 */     } catch (Exception e) {
/* 110 */       throw new RegistrationException("registration.db.delete.failed");
/*     */     } finally {
/*     */       try {
/* 113 */         if (stmt != null) {
/* 114 */           stmt.close();
/*     */         }
/* 116 */         if (conn != null) {
/* 117 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 119 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDealershipInformation(Registry registry, Dealership dealership) {
/* 125 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 126 */     Connection conn = null;
/*     */     try {
/* 128 */       conn = databaseLink.requestConnection();
/* 129 */       int dealershipPK = lookupDealershipRecord(conn, dealership.getDealershipID());
/* 130 */       if (dealershipPK == Integer.MAX_VALUE) {
/* 131 */         throw new IllegalArgumentException();
/*     */       }
/* 133 */       deleteDealershipRecord(conn, dealershipPK);
/* 134 */       store(conn, (DealershipEntity)dealership, dealershipPK);
/* 135 */     } catch (Exception e) {
/* 136 */       log.error("failed to update dealership information", e);
/* 137 */       throw new RegistrationException("registration.db.update.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 140 */         if (conn != null) {
/* 141 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 143 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int lookupDealershipRecord(Connection conn, String dealerID) {
/* 149 */     PreparedStatement stmt = null;
/* 150 */     ResultSet rs = null;
/*     */     try {
/* 152 */       String sql = "SELECT \"DealershipPK\" FROM Dealership WHERE \"DealershipID\" = ?";
/* 153 */       stmt = conn.prepareStatement(sql);
/* 154 */       stmt.setString(1, dealerID);
/* 155 */       rs = stmt.executeQuery();
/* 156 */       if (rs.next()) {
/* 157 */         return rs.getInt("DealershipPK");
/*     */       }
/* 159 */     } catch (Exception e) {
/* 160 */       return Integer.MAX_VALUE;
/*     */     } finally {
/*     */       try {
/* 163 */         if (rs != null) {
/* 164 */           rs.close();
/*     */         }
/* 166 */         if (stmt != null) {
/* 167 */           stmt.close();
/*     */         }
/* 169 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 172 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private static void deleteDealershipRecord(Connection conn, int dealershipPK) {
/* 176 */     PreparedStatement stmt = null;
/*     */     try {
/* 178 */       String sql = "DELETE FROM Dealership WHERE \"DealershipPK\" = ?";
/* 179 */       stmt = conn.prepareStatement(sql);
/* 180 */       stmt.setInt(1, dealershipPK);
/* 181 */       stmt.execute();
/* 182 */       sql = "DELETE FROM DealershipContact WHERE \"DealershipPK\" = ?";
/* 183 */       stmt = conn.prepareStatement(sql);
/* 184 */       stmt.setInt(1, dealershipPK);
/* 185 */       stmt.execute();
/* 186 */     } catch (Exception e) {
/* 187 */       throw new RegistrationException("registration.db.delete.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 190 */         if (stmt != null) {
/* 191 */           stmt.close();
/*     */         }
/* 193 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String existsRegistrationRecord(Registry registry, String hwid, String dealerID) {
/* 199 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 200 */     Connection conn = null;
/* 201 */     PreparedStatement stmt = null;
/* 202 */     ResultSet rs = null;
/*     */     try {
/* 204 */       conn = databaseLink.requestConnection();
/* 205 */       String sql = "SELECT \"DealershipPK\" FROM Dealership WHERE \"DealershipID\" = ?";
/* 206 */       stmt = conn.prepareStatement(sql);
/* 207 */       stmt.setString(1, dealerID);
/* 208 */       rs = stmt.executeQuery();
/* 209 */       if (rs.next()) {
/* 210 */         int dealership = rs.getInt("DealershipPK");
/* 211 */         rs.close();
/* 212 */         rs = null;
/* 213 */         stmt.close();
/* 214 */         stmt = null;
/* 215 */         return existsRegistrationRecord(conn, hwid, dealership);
/*     */       } 
/* 217 */     } catch (RegistrationException r) {
/* 218 */       throw r;
/* 219 */     } catch (Exception e) {
/* 220 */       throw new RegistrationException("registration.duplicate");
/*     */     } finally {
/*     */       try {
/* 223 */         if (rs != null) {
/* 224 */           rs.close();
/*     */         }
/* 226 */         if (stmt != null) {
/* 227 */           stmt.close();
/*     */         }
/* 229 */         if (conn != null) {
/* 230 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 232 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 235 */     return null;
/*     */   }
/*     */   
/*     */   public static String existsRegistrationRecord(Connection conn, String hwid, int dealershipPK) {
/* 239 */     PreparedStatement stmt = null;
/* 240 */     ResultSet rs = null;
/*     */     try {
/* 242 */       String sql = "SELECT \"SubscriberID\",\"RequestStatus\", \"SoftwareKey\" FROM Registration WHERE \"Dealership\" = ?";
/* 243 */       stmt = conn.prepareStatement(sql);
/* 244 */       stmt.setInt(1, dealershipPK);
/* 245 */       rs = stmt.executeQuery();
/* 246 */       while (rs.next()) {
/* 247 */         String swk = rs.getString("SoftwareKey");
/* 248 */         if (!matchHWID(hwid, swk)) {
/*     */           continue;
/*     */         }
/* 251 */         RequestStatus status = RequestStatus.get(rs.getInt("RequestStatus"));
/* 252 */         if (status == RequestStatus.PENDING) {
/* 253 */           throw new RegistrationException("registration.duplicate");
/*     */         }
/* 255 */         return rs.getString("SubscriberID");
/*     */       } 
/* 257 */     } catch (RegistrationException r) {
/* 258 */       throw r;
/* 259 */     } catch (Exception e) {
/* 260 */       log.debug("failed to check registration record '" + dealershipPK + "'", e);
/*     */     } finally {
/*     */       try {
/* 263 */         if (rs != null) {
/* 264 */           rs.close();
/*     */         }
/* 266 */         if (stmt != null) {
/* 267 */           stmt.close();
/*     */         }
/* 269 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 272 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean matchHWID(String hwid, String swk) {
/*     */     try {
/* 277 */       SoftwareKey swkey = new SoftwareKey("TIS2WEB", swk);
/* 278 */       return swk.equals(swkey.getHardwareHashID());
/* 279 */     } catch (Exception e) {
/*     */       
/* 281 */       return false;
/*     */     } 
/*     */   }
/*     */   public static Registration loadRegistrationRecord(Registry registry, String subscriberID) {
/* 285 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 286 */     Connection conn = null;
/* 287 */     PreparedStatement stmt = null;
/* 288 */     ResultSet rs = null;
/*     */     try {
/* 290 */       conn = databaseLink.requestConnection();
/* 291 */       String sql = "SELECT r.*, d.* FROM Registration r, Dealership d WHERE \"SubscriberID\" = ? AND r.\"Dealership\" = d.\"DealershipPK\"";
/* 292 */       stmt = conn.prepareStatement(sql);
/* 293 */       stmt.setString(1, subscriberID);
/* 294 */       rs = stmt.executeQuery();
/* 295 */       if (rs.next()) {
/* 296 */         Registration registration = decodeListRow(registry, rs);
/* 297 */         decodeRow((RegistrationEntity)registration, rs);
/* 298 */         decodeRow((DealershipEntity)registration.getDealership(), rs);
/* 299 */         load(conn, registration.getDealership());
/* 300 */         ((RegistrationEntity)registration).setStatus(2);
/* 301 */         ((DealershipEntity)registration.getDealership()).setStatus(2);
/* 302 */         return registration;
/*     */       } 
/* 304 */     } catch (Exception e) {
/* 305 */       log.debug("failed to load registration record '" + subscriberID + "'", e);
/*     */     } finally {
/*     */       try {
/* 308 */         if (rs != null) {
/* 309 */           rs.close();
/*     */         }
/* 311 */         if (stmt != null) {
/* 312 */           stmt.close();
/*     */         }
/* 314 */         if (conn != null) {
/* 315 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 317 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 320 */     return null;
/*     */   }
/*     */   
/*     */   public static List load(Registry registry, SalesOrganization organization, RequestStatus status, RegistrationFilter filter, RegistrationAttribute sort, SortDirection direction) throws Exception {
/* 324 */     List<Registration> registrations = new ArrayList();
/* 325 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 326 */     Connection conn = null;
/* 327 */     PreparedStatement stmt = null;
/* 328 */     ResultSet rs = null;
/*     */     try {
/* 330 */       conn = databaseLink.requestConnection();
/* 331 */       String sql = "SELECT r.\"RegistrationPK\", r.\"RequestID\", r.\"Organization\", r.\"RequestStatus\", r.\"RequestType\", r.\"RequestDate\", r.\"Sessions\", r.\"Dealership\", d.\"DealershipID\", d.\"DealershipName\", d.\"DealershipZIP\", d.\"DealershipCity\", d.\"DealershipState\", d.\"DealershipCountry\" FROM REGISTRATION r, Dealership d WHERE r.\"Dealership\" = d.\"DealershipPK\"";
/* 332 */       sql = appendFilter(sql, organization, status, filter);
/* 333 */       sql = appendSortOrder(sql, sort, direction);
/* 334 */       stmt = conn.prepareStatement(sql);
/* 335 */       rs = stmt.executeQuery();
/* 336 */       while (rs.next()) {
/* 337 */         registrations.add(decodeListRow(registry, rs));
/*     */       }
/* 339 */     } catch (Exception e) {
/* 340 */       log.debug(e);
/* 341 */       throw new RegistrationException("registration.db.load.failed");
/*     */     } finally {
/*     */       try {
/* 344 */         if (rs != null) {
/* 345 */           rs.close();
/*     */         }
/* 347 */         if (stmt != null) {
/* 348 */           stmt.close();
/*     */         }
/* 350 */         if (conn != null) {
/* 351 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 353 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 356 */     return registrations;
/*     */   }
/*     */   
/*     */   private static String appendFilter(String sql, SalesOrganization organization, RequestStatus status, RegistrationFilter filter) {
/* 360 */     sql = sql + " AND r.\"Organization\" = '" + organization + "'";
/* 361 */     if (status != null) {
/* 362 */       sql = sql + " AND r.\"RequestStatus\" = " + status.ord();
/*     */     }
/* 364 */     if (filter == null) {
/* 365 */       return sql;
/*     */     }
/* 367 */     RegistrationAttribute attribute = filter.getAttribute();
/* 368 */     if (attribute != null) {
/* 369 */       if (attribute.equals(RegistrationAttribute.DEALERSHIP_ID)) {
/* 370 */         if (filter.getValue() == null) {
/* 371 */           return sql;
/*     */         }
/* 373 */         sql = sql + " AND d.\"DealershipID\" like '%" + filter.getValue() + "%'";
/* 374 */       } else if (attribute.equals(RegistrationAttribute.DEALERSHIP_NAME)) {
/* 375 */         if (filter.getValue() == null) {
/* 376 */           return sql;
/*     */         }
/* 378 */         sql = sql + " AND d.\"DealershipName\" like '%" + filter.getValue() + "%'";
/* 379 */       } else if (attribute.equals(RegistrationAttribute.REQUEST_ID)) {
/* 380 */         if (filter.getValue() == null) {
/* 381 */           return sql;
/*     */         }
/* 383 */         sql = sql + " AND r.\"RequestID\" like '%" + filter.getValue() + "%'";
/*     */       } 
/*     */     }
/* 386 */     if (filter.getFromTimeStamp() != 0L) {
/* 387 */       Calendar date = Calendar.getInstance();
/* 388 */       date.setTimeInMillis(filter.getFromTimeStamp());
/* 389 */       sql = sql + " AND r.\"RequestDate\" >= " + constructDateExpression(date);
/*     */     } 
/* 391 */     if (filter.getToTimeStamp() != 0L) {
/* 392 */       Calendar date = Calendar.getInstance();
/* 393 */       date.setTimeInMillis(filter.getToTimeStamp());
/* 394 */       sql = sql + " AND r.\"RequestDate\" <= " + constructDateExpression(date);
/*     */     } 
/*     */     
/* 397 */     return sql;
/*     */   }
/*     */   
/*     */   private static String constructDateExpression(Calendar date) {
/* 401 */     return "TO_DATE('" + (date.get(2) + 1) + "-" + date.get(5) + "-" + date.get(1) + "','MM-DD-YY')";
/*     */   }
/*     */   
/*     */   private static String appendSortOrder(String sql, RegistrationAttribute sort, SortDirection direction) {
/* 405 */     if (sort == null) {
/* 406 */       sort = RegistrationAttribute.REQUEST_ID;
/*     */     }
/* 408 */     if (direction == null) {
/* 409 */       direction = SortDirection.UP;
/*     */     }
/* 411 */     if (sort.equals(RegistrationAttribute.DEALERSHIP_ID))
/* 412 */       return sql + " ORDER BY d.\"DealershipID\" " + (direction.equals(SortDirection.UP) ? "ASC" : "DESC"); 
/* 413 */     if (sort.equals(RegistrationAttribute.DEALERSHIP_NAME))
/* 414 */       return sql + " ORDER BY d.\"DealershipName\" " + (direction.equals(SortDirection.UP) ? "ASC" : "DESC"); 
/* 415 */     if (sort.equals(RegistrationAttribute.REQUEST_ID)) {
/* 416 */       return sql + " ORDER BY r.\"RequestID\" " + (direction.equals(SortDirection.UP) ? "ASC" : "DESC");
/*     */     }
/* 418 */     return sql + " ORDER BY r.\"RequestDate\" " + (direction.equals(SortDirection.UP) ? "ASC" : "DESC");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Registration decodeListRow(Registry registry, ResultSet rs) throws Exception {
/* 423 */     RegistrationEntity r = new RegistrationEntity(registry);
/* 424 */     r.setRequestPK(rs.getInt("RegistrationPK"));
/* 425 */     r.setRequestID(rs.getString("RequestID"));
/* 426 */     String org = rs.getString("Organization");
/* 427 */     r.setOrganization(SalesOrganization.get(org.toUpperCase(Locale.ENGLISH)));
/* 428 */     int status = rs.getInt("RequestStatus");
/* 429 */     r.setRequestStatus(RequestStatus.get(status));
/* 430 */     int type = rs.getByte("RequestType");
/* 431 */     r.setRequestType(RequestType.get(type));
/* 432 */     r.setRequestDate(rs.getDate("RequestDate"));
/* 433 */     DealershipEntity dealership = new DealershipEntity(registry);
/* 434 */     dealership.setDealershipPK(rs.getInt("Dealership"));
/* 435 */     dealership.setDealershipID(rs.getString("DealershipID"));
/* 436 */     dealership.setDealershipName(rs.getString("DealershipName"));
/* 437 */     dealership.setDealershipZIP(rs.getString("DealershipZIP"));
/* 438 */     dealership.setDealershipCity(rs.getString("DealershipCity"));
/* 439 */     dealership.setDealershipState(rs.getString("DealershipState"));
/* 440 */     dealership.setDealershipCountry(rs.getString("DealershipCountry"));
/* 441 */     r.setDealership(dealership);
/* 442 */     int sessions = rs.getInt("Sessions");
/* 443 */     if (!rs.wasNull()) {
/* 444 */       r.setSessionCount(Integer.valueOf(sessions));
/*     */     }
/* 446 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void prepareRegistrationDatabaseExport(Registry registry) {
/* 453 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/*     */     try {
/* 455 */       dbexport = databaseLink.requestConnection();
/* 456 */       dbdata = dbexport.prepareStatement("SELECT r.\"SubscriberID\", r.\"Authorization\", r.\"SoftwareKey\", r.\"LicenseKey\" FROM REGISTRATION r WHERE r.\"RegistrationPK\" = ?");
/* 457 */     } catch (Exception e) {
/* 458 */       log.debug(e);
/*     */       try {
/* 460 */         if (dbexport != null) {
/* 461 */           databaseLink.releaseConnection(dbexport);
/*     */         }
/* 463 */       } catch (Exception x) {}
/*     */       
/* 465 */       throw new RegistrationException("registration.db.load.failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void cleanupRegistrationDatabaseExport(Registry registry) {
/* 470 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/*     */     try {
/* 472 */       if (dbdata != null) {
/* 473 */         dbdata.close();
/*     */       }
/* 475 */       if (dbexport != null) {
/* 476 */         databaseLink.releaseConnection(dbexport);
/*     */       }
/* 478 */     } catch (Exception e) {
/* 479 */       log.debug(e);
/* 480 */       throw new RegistrationException("registration.db.load.failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void load(Registry registry, Registration registration) {
/* 485 */     DatabaseLink databaseLink = (dbdata != null) ? null : ((RegistryImpl)registry).getDatabaseLink();
/* 486 */     Connection conn = null;
/* 487 */     PreparedStatement stmt = null;
/* 488 */     ResultSet rs = null;
/*     */     try {
/* 490 */       conn = (dbdata != null) ? null : databaseLink.requestConnection();
/* 491 */       stmt = (dbdata != null) ? dbdata : conn.prepareStatement("SELECT r.\"SubscriberID\", r.\"Authorization\", r.\"SoftwareKey\", r.\"LicenseKey\" FROM REGISTRATION r WHERE r.\"RegistrationPK\" = ?");
/* 492 */       stmt.setInt(1, ((RegistrationEntity)registration).getRequestPK());
/* 493 */       rs = stmt.executeQuery();
/* 494 */       if (rs.next()) {
/* 495 */         decodeRow((RegistrationEntity)registration, rs);
/*     */       } else {
/* 497 */         log.debug("failed to load registration '" + ((RegistrationEntity)registration).getRequestPK() + "'");
/* 498 */         throw new RegistrationException("registration.db.load.failed");
/*     */       } 
/* 500 */     } catch (Exception e) {
/* 501 */       log.debug(e);
/* 502 */       throw new RegistrationException("registration.db.load.failed");
/*     */     } finally {
/*     */       try {
/* 505 */         if (rs != null) {
/* 506 */           rs.close();
/*     */         }
/* 508 */         if (stmt != null && dbdata == null) {
/* 509 */           stmt.close();
/*     */         }
/* 511 */         if (conn != null && dbdata == null) {
/* 512 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 514 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void decodeRow(RegistrationEntity r, ResultSet rs) throws Exception {
/* 520 */     r.setSubscriberID(rs.getString("SubscriberID"));
/* 521 */     String authorization = rs.getString("Authorization");
/* 522 */     if (authorization.indexOf("~") < 0) {
/* 523 */       r.setAuthorizationID(rs.getString("Authorization"));
/*     */     } else {
/* 525 */       splitAuthorizationList(r, authorization);
/*     */     } 
/* 527 */     r.setSoftwareKey(rs.getString("SoftwareKey"));
/* 528 */     r.setLicenseKey(rs.getString("LicenseKey"));
/*     */   }
/*     */   
/*     */   private static void splitAuthorizationList(RegistrationEntity r, String authorization) throws Exception {
/* 532 */     List<String> authorizations = new ArrayList();
/* 533 */     StringTokenizer st = new StringTokenizer(authorization, ",~");
/* 534 */     while (st.hasMoreTokens()) {
/* 535 */       String token = st.nextToken();
/* 536 */       if (authorization.startsWith(token)) {
/* 537 */         r.setAuthorizationID(token); continue;
/*     */       } 
/* 539 */       authorizations.add(token);
/*     */     } 
/*     */     
/* 542 */     r.setAuthorizationList(authorizations);
/*     */   }
/*     */   
/*     */   public static void store(Registry registry, Registration registration) throws Exception {
/* 546 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 547 */     store((IDatabaseLink)databaseLink, registration);
/*     */   }
/*     */   
/*     */   public static void store(IDatabaseLink db, Registration registration) throws Exception {
/* 551 */     Connection conn = null;
/* 552 */     PreparedStatement stmt = null;
/*     */     try {
/* 554 */       conn = db.requestConnection();
/* 555 */       ((RegistrationEntity)registration).setRequestPK(getID(conn));
/* 556 */       store(conn, (DealershipEntity)registration.getDealership());
/* 557 */       stmt = conn.prepareStatement("INSERT INTO Registration(\"RegistrationPK\", \"RequestID\", \"Organization\", \"RequestStatus\", \"RequestType\", \"RequestDate\", \"Dealership\", \"Sessions\", \"SubscriberID\", \"Authorization\", \"SoftwareKey\", \"LicenseKey\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
/* 558 */       stmt.setInt(1, ((RegistrationEntity)registration).getRequestPK());
/* 559 */       stmt.setString(2, registration.getRequestID());
/* 560 */       stmt.setString(3, registration.getOrganization().toString());
/* 561 */       stmt.setInt(4, registration.getRequestStatus().ord());
/* 562 */       stmt.setInt(5, registration.getRequestType().ord());
/* 563 */       Date date = new Date(registration.getRequestDate().getTime());
/* 564 */       stmt.setDate(6, date);
/* 565 */       stmt.setInt(7, ((DealershipEntity)registration.getDealership()).getDealershipPK());
/* 566 */       stmt.setInt(8, registration.getSessionCount().intValue());
/* 567 */       stmt.setString(9, registration.getSubscriberID());
/* 568 */       stmt.setString(10, registration.getAuthorizationID());
/* 569 */       stmt.setString(11, registration.getSoftwareKey());
/* 570 */       if (registration.getLicenseKey() == null) {
/* 571 */         stmt.setNull(12, 1);
/*     */       } else {
/* 573 */         stmt.setString(12, registration.getLicenseKey());
/*     */       } 
/* 575 */       stmt.execute();
/* 576 */     } catch (Exception e) {
/* 577 */       log.error("unable to store registration, throwing RegistrationException - exception:" + e, e);
/* 578 */       throw new RegistrationException("registration.db.store.failed");
/*     */     } finally {
/*     */       try {
/* 581 */         if (stmt != null) {
/* 582 */           stmt.close();
/*     */         }
/* 584 */         if (conn != null) {
/* 585 */           db.releaseConnection(conn);
/*     */         }
/* 587 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(RegistryImpl registry, Registration registration) {
/* 593 */     DatabaseLink databaseLink = registry.getDatabaseLink();
/* 594 */     Connection conn = null;
/* 595 */     PreparedStatement stmt = null;
/*     */     try {
/* 597 */       conn = databaseLink.requestConnection();
/* 598 */       if (registration.getRequestStatus() == RequestStatus.PENDING) {
/* 599 */         String authorization = null;
/* 600 */         if (registration.getAuthorizationList() != null) {
/* 601 */           authorization = registration.getAuthorizationID() + "~";
/* 602 */           List<String> authorizations = registration.getAuthorizationList();
/* 603 */           for (int i = 0; i < authorizations.size(); i++) {
/* 604 */             if (i > 0) {
/* 605 */               authorization = authorization + ",";
/*     */             }
/* 607 */             authorization = authorization + authorizations.get(i);
/*     */           } 
/*     */         } 
/* 610 */         stmt = conn.prepareStatement((authorization == null) ? "UPDATE Registration SET \"RequestID\" = ?, \"RequestStatus\" = ?, \"RequestType\" = ?, \"RequestDate\" = ?,\"SoftwareKey\" = ?, \"Sessions\" = ? WHERE \"RegistrationPK\" = ?" : "UPDATE Registration SET \"RequestID\" = ?, \"RequestStatus\" = ?, \"RequestType\" = ?, \"RequestDate\" = ?, \"SoftwareKey\" = ?, \"Authorization\" = ?, \"Sessions\" = ? WHERE \"RegistrationPK\" = ?");
/* 611 */         stmt.setString(1, registration.getRequestID());
/* 612 */         stmt.setInt(2, registration.getRequestStatus().ord());
/* 613 */         stmt.setInt(3, registration.getRequestType().ord());
/* 614 */         Date date = new Date(registration.getRequestDate().getTime());
/* 615 */         stmt.setDate(4, date);
/* 616 */         stmt.setString(5, registration.getSoftwareKey());
/* 617 */         if (authorization != null) {
/* 618 */           stmt.setString(6, authorization);
/* 619 */           stmt.setInt(7, registration.getSessionCount().intValue());
/* 620 */           stmt.setInt(8, ((RegistrationEntity)registration).getRequestPK());
/*     */         } else {
/* 622 */           stmt.setInt(6, registration.getSessionCount().intValue());
/* 623 */           stmt.setInt(7, ((RegistrationEntity)registration).getRequestPK());
/*     */         } 
/* 625 */       } else if (registration.getRequestStatus() == RequestStatus.AUTHORIZED) {
/* 626 */         stmt = conn.prepareStatement("UPDATE Registration SET \"RequestStatus\" = ?, \"Authorization\" = ?, \"LicenseKey\" = ?, \"Sessions\" = ?, \"RequestID\" = ? WHERE \"RegistrationPK\" = ?");
/* 627 */         stmt.setInt(1, registration.getRequestStatus().ord());
/* 628 */         stmt.setString(2, registration.getAuthorizationID());
/* 629 */         stmt.setString(3, registration.getLicenseKey());
/* 630 */         stmt.setInt(4, registration.getSessionCount().intValue());
/* 631 */         stmt.setString(5, registration.getRequestID());
/* 632 */         stmt.setInt(6, ((RegistrationEntity)registration).getRequestPK());
/*     */       } else {
/* 634 */         stmt = conn.prepareStatement("UPDATE Registration SET \"RequestStatus\" = ? WHERE \"RegistrationPK\" = ?");
/* 635 */         stmt.setInt(1, registration.getRequestStatus().ord());
/* 636 */         stmt.setInt(2, ((RegistrationEntity)registration).getRequestPK());
/*     */       } 
/* 638 */       stmt.execute();
/* 639 */     } catch (Exception e) {
/* 640 */       log.error("unable to update registration, throwing RegistrationException - exception:" + e, e);
/* 641 */       throw new RegistrationException("registration.db.update.failed");
/*     */     } finally {
/*     */       try {
/* 644 */         if (stmt != null) {
/* 645 */           stmt.close();
/*     */         }
/* 647 */         if (conn != null) {
/* 648 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 650 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void load(Registry registry, Dealership dealership) {
/* 656 */     DatabaseLink databaseLink = ((RegistryImpl)registry).getDatabaseLink();
/* 657 */     Connection conn = null;
/* 658 */     PreparedStatement stmt = null;
/* 659 */     ResultSet rs = null;
/*     */     try {
/* 661 */       conn = databaseLink.requestConnection();
/* 662 */       stmt = conn.prepareStatement("SELECT \"DealershipStreet\", \"DealershipLanguage\", \"DealershipPhone\", \"DealershipFax\", \"DealershipEmail\" FROM Dealership WHERE \"DealershipPK\" = ?");
/* 663 */       stmt.setInt(1, ((DealershipEntity)dealership).getDealershipPK());
/* 664 */       rs = stmt.executeQuery();
/* 665 */       if (rs.next()) {
/* 666 */         decodeRow((DealershipEntity)dealership, rs);
/*     */       } else {
/* 668 */         log.debug("failed to load dealership data '" + ((DealershipEntity)dealership).getDealershipPK() + "'");
/* 669 */         throw new RegistrationException("registration.db.load.dealership.failed");
/*     */       } 
/* 671 */       if (rs != null) {
/* 672 */         rs.close();
/* 673 */         rs = null;
/*     */       } 
/* 675 */       if (stmt != null) {
/* 676 */         stmt.close();
/* 677 */         stmt = null;
/*     */       } 
/* 679 */       load(conn, dealership);
/* 680 */     } catch (Exception e) {
/* 681 */       log.debug(e);
/* 682 */       throw new RegistrationException("registration.db.load.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 685 */         if (rs != null) {
/* 686 */           rs.close();
/*     */         }
/* 688 */         if (stmt != null) {
/* 689 */           stmt.close();
/*     */         }
/* 691 */         if (conn != null) {
/* 692 */           databaseLink.releaseConnection(conn);
/*     */         }
/* 694 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void decodeRow(DealershipEntity d, ResultSet rs) throws Exception {
/* 700 */     d.setDealershipStreet(rs.getString("DealershipStreet"));
/* 701 */     d.setDealershipLanguage(rs.getString("DealershipLanguage"));
/* 702 */     d.setDealershipPhone(rs.getString("DealershipPhone"));
/* 703 */     d.setDealershipFax(rs.getString("DealershipFax"));
/* 704 */     d.setDealershipEmail(rs.getString("DealershipEmail"));
/*     */   }
/*     */   
/*     */   public static void load(Connection conn, Dealership dealership) {
/* 708 */     PreparedStatement stmt = null;
/* 709 */     ResultSet rs = null;
/*     */     try {
/* 711 */       List<DealershipContactEntity> contacts = new ArrayList();
/* 712 */       stmt = conn.prepareStatement("SELECT d.\"ContactName\", d.\"ContactLanguage\" FROM DealershipContact d WHERE d.\"DealershipPK\" = ?");
/* 713 */       stmt.setInt(1, ((DealershipEntity)dealership).getDealershipPK());
/* 714 */       rs = stmt.executeQuery();
/* 715 */       while (rs.next()) {
/* 716 */         contacts.add(new DealershipContactEntity(rs.getString("ContactName"), rs.getString("ContactLanguage")));
/*     */       }
/* 718 */       if (contacts.size() > 0) {
/* 719 */         dealership.setDealershipContacts(contacts);
/*     */       }
/* 721 */     } catch (Exception e) {
/* 722 */       log.debug(e);
/* 723 */       throw new RegistrationException("registration.db.load.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 726 */         if (rs != null) {
/* 727 */           rs.close();
/*     */         }
/* 729 */         if (stmt != null) {
/* 730 */           stmt.close();
/*     */         }
/* 732 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void store(Connection conn, DealershipEntity dealership) throws Exception {
/* 738 */     store(conn, dealership, getID(conn));
/*     */   }
/*     */   
/*     */   public static void store(Connection conn, DealershipEntity dealership, int dealershipPK) throws Exception {
/* 742 */     PreparedStatement stmt = null;
/*     */     try {
/* 744 */       dealership.setDealershipPK(dealershipPK);
/* 745 */       store(conn, dealership.getDealershipPK(), dealership.getDealershipContacts());
/* 746 */       stmt = conn.prepareStatement("INSERT INTO Dealership(\"DealershipPK\", \"DealershipID\", \"DealershipName\", \"DealershipStreet\", \"DealershipZIP\", \"DealershipCity\", \"DealershipState\", \"DealershipCountry\", \"DealershipLanguage\", \"DealershipPhone\", \"DealershipFax\", \"DealershipEmail\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
/* 747 */       stmt.setInt(1, dealership.getDealershipPK());
/* 748 */       stmt.setString(2, dealership.getDealershipID());
/* 749 */       stmt.setString(3, dealership.getDealershipName());
/* 750 */       stmt.setString(4, dealership.getDealershipStreet());
/* 751 */       stmt.setString(5, dealership.getDealershipZIP());
/* 752 */       stmt.setString(6, dealership.getDealershipCity());
/* 753 */       if (dealership.getDealershipState() == null) {
/* 754 */         stmt.setNull(7, 1);
/*     */       } else {
/* 756 */         stmt.setString(7, dealership.getDealershipState());
/*     */       } 
/* 758 */       stmt.setString(8, dealership.getDealershipCountry());
/* 759 */       stmt.setString(9, dealership.getDealershipLanguage());
/* 760 */       if (dealership.getDealershipPhone() == null) {
/* 761 */         stmt.setNull(10, 1);
/*     */       } else {
/* 763 */         stmt.setString(10, dealership.getDealershipPhone());
/*     */       } 
/* 765 */       if (dealership.getDealershipFax() == null) {
/* 766 */         stmt.setNull(11, 1);
/*     */       } else {
/* 768 */         stmt.setString(11, dealership.getDealershipFax());
/*     */       } 
/* 770 */       if (dealership.getDealershipEmail() == null) {
/* 771 */         stmt.setNull(12, 1);
/*     */       } else {
/* 773 */         stmt.setString(12, dealership.getDealershipEmail());
/*     */       } 
/* 775 */       stmt.execute();
/* 776 */     } catch (Exception e) {
/* 777 */       log.debug(e);
/* 778 */       throw new RegistrationException("registration.db.store.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 781 */         if (stmt != null) {
/* 782 */           stmt.close();
/*     */         }
/* 784 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void store(Connection conn, int dealership, List<DealershipContact> contacts) throws Exception {
/* 790 */     if (contacts == null) {
/*     */       return;
/*     */     }
/* 793 */     PreparedStatement stmt = null;
/*     */     try {
/* 795 */       stmt = conn.prepareStatement("INSERT INTO DealershipContact(\"DealershipPK\", \"ContactName\", \"ContactLanguage\") VALUES(?,?,?)");
/* 796 */       for (int i = 0; i < contacts.size(); i++) {
/* 797 */         DealershipContact contact = contacts.get(i);
/* 798 */         stmt.setInt(1, dealership);
/* 799 */         stmt.setString(2, contact.getContactName());
/* 800 */         stmt.setString(3, contact.getContactLanguage());
/* 801 */         stmt.execute();
/*     */       } 
/* 803 */     } catch (Exception e) {
/* 804 */       log.debug(e);
/* 805 */       throw new RegistrationException("registration.db.store.dealership.failed");
/*     */     } finally {
/*     */       try {
/* 808 */         if (stmt != null) {
/* 809 */           stmt.close();
/*     */         }
/* 811 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getID(Connection connection) throws Exception {
/* 817 */     int retValue = -1;
/* 818 */     String query1 = "select \"NextKey\" from Keygen";
/* 819 */     PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */     try {
/* 821 */       String query2 = "update Keygen set \"NextKey\"=? where \"NextKey\"=?";
/* 822 */       PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */ 
/*     */       
/*     */       try { while (true) {
/* 826 */           ResultSet rs = stmt1.executeQuery();
/*     */           try {
/* 828 */             if (rs.next()) {
/* 829 */               retValue = rs.getInt(1);
/*     */             } else {
/* 831 */               throw new IllegalStateException();
/*     */             } 
/*     */           } finally {
/*     */             
/* 835 */             rs.close();
/*     */           } 
/*     */           
/* 838 */           stmt2.setInt(1, retValue + 1);
/* 839 */           stmt2.setInt(2, retValue);
/* 840 */           int result = stmt2.executeUpdate();
/* 841 */           if (result != 1) {
/* 842 */             log.debug("id: " + retValue + " is already in use, retrying");
/* 843 */             retValue = -1;
/*     */           } 
/*     */           
/* 846 */           if (retValue != -1)
/* 847 */             return retValue; 
/*     */         }  }
/* 849 */       finally { stmt2.close(); }
/*     */     
/*     */     } finally {
/* 852 */       stmt1.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\RegistrationData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */