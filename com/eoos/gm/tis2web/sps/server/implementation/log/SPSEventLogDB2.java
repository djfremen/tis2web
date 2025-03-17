/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.thread.CustomThread;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import oracle.sql.BLOB;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSEventLogDB2
/*     */   implements SPSEventLog.SPI, SPSEventLog.Deletion
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(SPSEventLogDB2.class);
/*     */   
/*  47 */   private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH);
/*     */   
/*     */   private String serverName;
/*     */   
/*  51 */   private OnstarAttributeLog onstarLog = null;
/*     */ 
/*     */ 
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
/*     */ 
/*     */   
/*     */   private long[] availableIDs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getReadonlyConnection() throws SQLException {
/*  79 */     Connection ret = this.connectionProvider.getConnection();
/*  80 */     ret.setReadOnly(true);
/*  81 */     ret.setAutoCommit(false);
/*  82 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() throws SQLException {
/*  86 */     Connection ret = this.connectionProvider.getConnection();
/*  87 */     ret.setReadOnly(false);
/*  88 */     ret.setAutoCommit(false);
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  93 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   boolean testDBConnection() {
/*     */     try {
/*  98 */       Connection connection = getReadonlyConnection();
/*     */       try {
/* 100 */         return (connection != null);
/*     */       } finally {
/* 102 */         releaseConnection(connection);
/*     */       } 
/* 104 */     } catch (Exception e) {
/* 105 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SPSEventLogDB2(Configuration configuration, String serverName, OnstarAttributeLog onstarLog) {
/* 110 */     this.availableIDs = null; this.onstarLog = onstarLog; this.serverName = serverName; final IDatabaseLink dbLink = DatabaseLink.openDatabase(configuration); this.connectionProvider = new ConnectionProvider() { public void releaseConnection(Connection connection) { dbLink.releaseConnection(connection); } public Connection getConnection() { try { return dbLink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }
/*     */            } }
/*     */       ; this.connectionProvider = ConNvent.create(this.connectionProvider, 300000L);
/* 113 */   } private synchronized long getID() throws Exception { if (this.availableIDs == null || this.availableIDs[0] > this.availableIDs[1]) {
/* 114 */       this.availableIDs = reserveIDBlock();
/*     */     }
/* 116 */     this.availableIDs[0] = this.availableIDs[0] + 1L; return this.availableIDs[0]; }
/*     */ 
/*     */   
/*     */   private long[] reserveIDBlock() throws Exception {
/* 120 */     log.debug("reserving ID block...");
/* 121 */     Connection connection = getWriteConnection();
/*     */     try {
/* 123 */       long[] retValue = null;
/*     */       
/* 125 */       String query1 = "select nextkey from sps_keygen";
/* 126 */       PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */       try {
/* 128 */         String query2 = "update sps_keygen set nextkey=? where nextkey=?";
/* 129 */         PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */         try {
/* 131 */           while (retValue == null) {
/* 132 */             retValue = new long[] { -1L, -1L };
/* 133 */             ResultSet rs = stmt1.executeQuery();
/*     */             try {
/* 135 */               if (rs.next()) {
/* 136 */                 retValue[0] = rs.getLong(1);
/*     */               } else {
/* 138 */                 throw new IllegalStateException();
/*     */               } 
/*     */             } finally {
/* 141 */               rs.close();
/*     */             } 
/*     */             
/* 144 */             retValue[1] = retValue[0] + 499L;
/* 145 */             stmt2.setLong(1, retValue[1] + 1L);
/* 146 */             stmt2.setLong(2, retValue[0]);
/* 147 */             int result = stmt2.executeUpdate();
/* 148 */             if (result != 1) {
/*     */               
/* 150 */               log.info("block  is already in use, retrying");
/* 151 */               retValue = null;
/*     */             } 
/*     */           } 
/* 154 */           log.debug("...done");
/* 155 */           connection.commit();
/* 156 */           return retValue;
/*     */         } finally {
/* 158 */           stmt2.close();
/*     */         } 
/*     */       } finally {
/* 161 */         stmt1.close();
/*     */       } 
/* 163 */     } catch (Exception e) {
/* 164 */       JDBCUtil.rollback(connection, log);
/* 165 */       throw e;
/*     */     } finally {
/* 167 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeAttributes(Connection connection, long entryID, List<SPSEventLog.Attribute> attributes) throws Exception {
/* 173 */     if (attributes != null && attributes.size() > 0) {
/* 174 */       String createQuery = "insert into sps_attribute values(?,?,?,?)";
/* 175 */       PreparedStatement createStmt = connection.prepareStatement(createQuery);
/*     */       try {
/* 177 */         createStmt.setLong(4, entryID);
/* 178 */         for (int i = 0; i < attributes.size(); i++) {
/* 179 */           SPSEventLog.Attribute attribute = attributes.get(i);
/* 180 */           if (!(attribute instanceof SPSEventLog.VoltAttribute)) {
/*     */ 
/*     */             
/* 183 */             createStmt.setString(1, attribute.getName().toLowerCase(Locale.ENGLISH));
/* 184 */             createStmt.setString(2, Util.trim(String.valueOf(attribute.getValue()), 1024));
/* 185 */             createStmt.setInt(3, i);
/* 186 */             createStmt.addBatch();
/*     */           } 
/* 188 */         }  int[] results = createStmt.executeBatch();
/* 189 */         for (int j = 0; j < results.length; j++) {
/* 190 */           if (results[j] != 1 && results[j] != -2) {
/* 191 */             throw new IllegalStateException("unable to write attributes ");
/*     */           }
/*     */         } 
/*     */       } finally {
/* 195 */         createStmt.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeEntries(Collection metaEntries) throws Exception {
/* 202 */     Connection connection = getWriteConnection();
/*     */     try {
/* 204 */       for (Iterator<SPSEventLog.MetaEntry> iter = metaEntries.iterator(); iter.hasNext(); ) {
/* 205 */         SPSEventLog.MetaEntry mEntry = iter.next();
/* 206 */         SPSEventLog.Entry entry = mEntry.entry;
/* 207 */         Collection<?> flags = mEntry.flags;
/* 208 */         Attachment[] attachments = mEntry.attachments;
/*     */         
/* 210 */         if (flags == null || !flags.contains(SPSEventLog.FLAG_ALL)) {
/* 211 */           flags = (flags != null) ? new HashSet(flags) : new HashSet();
/* 212 */           flags.add(SPSEventLog.FLAG_ALL);
/*     */         } 
/* 214 */         long entryID = getID();
/* 215 */         Date ts = new Date();
/*     */ 
/*     */         
/* 218 */         String query = "insert into sps_event_log values(?,?,?,?,?,?,?)";
/* 219 */         PreparedStatement stmt = connection.prepareStatement(query);
/*     */         
/*     */         try {
/* 222 */           stmt.setLong(1, entryID);
/* 223 */           stmt.setLong(2, ts.getTime());
/* 224 */           stmt.setString(3, this.dateFormat.format(ts));
/* 225 */           stmt.setString(4, entry.getAdapter().toString());
/* 226 */           stmt.setString(5, this.serverName.toLowerCase(Locale.ENGLISH));
/* 227 */           stmt.setString(6, entry.getEventName().toLowerCase(Locale.ENGLISH));
/* 228 */           stmt.setString(7, String.valueOf(getCodeFor(flags)));
/* 229 */           int result = stmt.executeUpdate();
/* 230 */           if (result != 1) {
/* 231 */             throw new IllegalStateException();
/*     */           }
/*     */         } finally {
/* 234 */           stmt.close();
/*     */         } 
/*     */         
/* 237 */         writeAttributes(connection, entryID, entry.getEventAttributes());
/* 238 */         if (flags.contains(SPSEventLog.FLAG_ONSTAR) && this.onstarLog != null) {
/*     */           try {
/* 240 */             this.onstarLog.writeAttributes(entryID, entry.getEventAttributes());
/* 241 */           } catch (Exception e) {
/* 242 */             log.error("...unable to add entry to onstar log - exception (ignored):" + e, e);
/*     */           } 
/*     */         }
/*     */         
/* 246 */         if (flags.contains(SPSEventLog.FLAG_VOLT)) {
/* 247 */           writeVoltAttributes(connection, entryID, entry.getEventAttributes());
/*     */         }
/*     */         
/* 250 */         if (attachments != null && attachments.length > 0) {
/* 251 */           writeAttachments(connection, entryID, attachments);
/*     */         }
/*     */       } 
/* 254 */       connection.commit();
/* 255 */     } catch (Exception e) {
/* 256 */       connection.rollback();
/* 257 */       throw e;
/*     */     } finally {
/* 259 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeVoltAttributes(Connection connection, long entryID, List<SPSEventLog.Attribute> attributes) throws SQLException, IOException {
/* 264 */     if (attributes != null && attributes.size() > 0) {
/* 265 */       String createQuery = "insert into VOLT_ATTRIBUTES values(?,?,?)";
/* 266 */       PreparedStatement createStmt = connection.prepareStatement(createQuery);
/*     */       try {
/* 268 */         createStmt.setLong(1, entryID);
/* 269 */         for (int i = 0; i < attributes.size(); i++) {
/* 270 */           SPSEventLog.Attribute attribute = attributes.get(i);
/* 271 */           if (attribute instanceof SPSEventLog.VoltAttribute) {
/*     */ 
/*     */             
/* 274 */             createStmt.setString(2, attribute.getName().toLowerCase(Locale.ENGLISH));
/* 275 */             createStmt.setString(3, Util.trim(String.valueOf(attribute.getValue()), 1024));
/* 276 */             createStmt.addBatch();
/*     */           } 
/* 278 */         }  int[] results = createStmt.executeBatch();
/* 279 */         for (int j = 0; j < results.length; j++) {
/* 280 */           if (results[j] != 1 && results[j] != -2) {
/* 281 */             throw new IllegalStateException("unable to write attributes ");
/*     */           }
/*     */         } 
/*     */       } finally {
/* 285 */         createStmt.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeAttachments(Connection connection, long entryID, Attachment[] attachments) throws SQLException, IOException {
/* 292 */     for (int i = 0; i < attachments.length; i++) {
/* 293 */       String key = attachments[i].getKey().toString();
/* 294 */       Object attachment = attachments[i].getObject();
/* 295 */       PreparedStatement stmt = connection.prepareStatement("insert into sps_event_log_attach(entry_refid,key,attachment) values(?,?,empty_blob())");
/*     */       try {
/* 297 */         stmt.setLong(1, entryID);
/* 298 */         stmt.setString(2, key);
/* 299 */         if (stmt.executeUpdate() != 1) {
/* 300 */           throw new IllegalStateException();
/*     */         }
/*     */       } finally {
/* 303 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */       
/* 306 */       PreparedStatement stmt2 = connection.prepareStatement("select a.attachment from sps_event_log_attach a where a.entry_refid=? and a.key=?");
/*     */       try {
/* 308 */         stmt2.setLong(1, entryID);
/* 309 */         stmt2.setString(2, key);
/* 310 */         ResultSet rs = stmt2.executeQuery();
/*     */         try {
/* 312 */           if (!rs.next()) {
/* 313 */             throw new IllegalStateException();
/*     */           }
/* 315 */           BLOB b = (BLOB)rs.getBlob(1);
/* 316 */           OutputStream os = b.getBinaryOutputStream();
/* 317 */           ObjectOutputStream oos = new ObjectOutputStream(os);
/* 318 */           oos.writeObject(attachment);
/* 319 */           oos.close();
/*     */         } finally {
/*     */           
/* 322 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 325 */         JDBCUtil.close(stmt2, log);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void add(Collection metaEntries) throws Exception {
/* 332 */     if (!Util.isNullOrEmpty(metaEntries)) {
/* 333 */       ensureInit();
/* 334 */       log.debug("adding " + metaEntries.size() + " entires...");
/* 335 */       writeEntries(metaEntries);
/* 336 */       log.debug("...done");
/*     */     } 
/*     */   }
/*     */   
/*     */   private CharSequence getCodeFor(Collection flags) {
/* 341 */     if (flags == null || flags.size() == 0) {
/* 342 */       return "";
/*     */     }
/* 344 */     StringBuffer retValue = new StringBuffer();
/* 345 */     for (Iterator<SPSEventLog.Flag> iter = flags.iterator(); iter.hasNext();) {
/* 346 */       retValue.append(getCodeFor(iter.next()));
/*     */     }
/* 348 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private CharSequence getCodeFor(SPSEventLog.Flag flag) {
/* 353 */     return "-" + String.valueOf(SPSEventLog.FLAG_DOMAIN.indexOf(flag)) + "-";
/*     */   }
/*     */   
/*     */   private boolean isAborted() {
/* 357 */     if (Thread.currentThread() instanceof CustomThread) {
/* 358 */       return ((CustomThread)Thread.currentThread()).isAborted();
/*     */     }
/* 360 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assertNotAborted() {
/* 365 */     if (Thread.currentThread() instanceof CustomThread) {
/* 366 */       ((CustomThread)Thread.currentThread()).assertNotAborted();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized Collection getEntries(SPSEventLog.Query.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 371 */     ensureInit();
/* 372 */     log.debug("processing entries retrieval request");
/* 373 */     return _getEntries(backendFilter, hitLimit);
/*     */   }
/*     */ 
/*     */   
/* 377 */   private static final String[] VARNAME_DOMAIN_ATTR = new String[] { "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10" };
/*     */   
/*     */   @SuppressWarnings({"SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"})
/*     */   private synchronized Collection _getEntries(SPSEventLog.Query.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 381 */     Connection connection = getReadonlyConnection();
/*     */     try {
/* 383 */       StringBuffer query = new StringBuffer("select distinct a.entry_id, a.ts, a.adapter, a.server_name, a.event_name, a.flags  from sps_event_log a {INSERT_FROM} #");
/*     */       
/* 385 */       if (backendFilter != null) {
/* 386 */         if (backendFilter.getTimestampMIN() != null) {
/* 387 */           query.append("and a.ts >= " + backendFilter.getTimestampMIN().longValue() + " ");
/*     */         }
/* 389 */         if (backendFilter.getTimestampMAX() != null) {
/* 390 */           query.append("and a.ts <= " + backendFilter.getTimestampMAX().longValue() + " ");
/*     */         }
/*     */         
/* 393 */         if (backendFilter.getAdapter() != null) {
/* 394 */           String adapter = (backendFilter.getAdapter() == SPSEventLog.ADAPTER_GME) ? "gme" : null;
/* 395 */           if (adapter == null) {
/* 396 */             adapter = (backendFilter.getAdapter() == SPSEventLog.ADAPTER_NAO) ? "nao" : "global";
/*     */           }
/* 398 */           query.append("and a.adapter like '" + adapter + "' ");
/*     */         } 
/* 400 */         if (backendFilter.getNamePattern() != null) {
/* 401 */           query.append("and a.event_name like '" + backendFilter.getNamePattern() + "' ");
/*     */         }
/*     */ 
/*     */         
/* 405 */         Collection<?> _flags = backendFilter.getFlags();
/* 406 */         if (_flags != null) {
/* 407 */           List<SPSEventLog.Flag> flags = new LinkedList(_flags);
/* 408 */           if (flags.size() > 0) {
/* 409 */             query.append("and (a.flags like '%" + getCodeFor(flags.get(0)) + "%' ");
/* 410 */             for (int i = 1; i < flags.size(); i++) {
/* 411 */               query.append("or a.flags like '%" + getCodeFor(flags.get(i)) + "%' ");
/*     */             }
/* 413 */             query.append(") ");
/*     */           } 
/*     */         } 
/*     */         
/* 417 */         SPSEventLog.Query.BackendFilter.AttributeFilter[] attributeFilters = backendFilter.getAttributeFilters();
/* 418 */         if (attributeFilters != null && attributeFilters.length > 0) {
/* 419 */           if (attributeFilters.length > 10) {
/* 420 */             throw new IllegalArgumentException("more than 10 attribute filters are not supported");
/*     */           }
/* 422 */           StringBuffer addToFromClause = new StringBuffer();
/* 423 */           StringBuffer addToWhereClause = new StringBuffer();
/* 424 */           for (int i = 0; i < attributeFilters.length; i++) {
/* 425 */             SPSEventLog.Query.BackendFilter.AttributeFilter attributeFilter = attributeFilters[i];
/* 426 */             String as = VARNAME_DOMAIN_ATTR[i];
/*     */             
/* 428 */             addToFromClause.append(", sps_attribute " + as);
/* 429 */             addToWhereClause.append(" and a.entry_id=" + as + ".entry_refid");
/* 430 */             addToWhereClause.append(" and " + as + ".name like '" + attributeFilter.getNamePattern() + "'");
/* 431 */             addToWhereClause.append(" and " + as + ".value like '" + attributeFilter.getValuePattern() + "'");
/*     */           } 
/*     */           
/* 434 */           StringUtilities.replace(query, "{INSERT_FROM}", addToFromClause + " {INSERT_FROM}");
/* 435 */           query.append(addToWhereClause);
/*     */         } 
/*     */       } 
/*     */       
/* 439 */       if (hitLimit != -1 && hitLimit > 0) {
/* 440 */         query.append(" and rownum <= " + hitLimit);
/*     */       }
/* 442 */       query.append(" order by a.entry_id  asc");
/*     */       
/* 444 */       StringUtilities.replace(query, "#and", "where");
/* 445 */       StringUtilities.replace(query, "# and", "where");
/* 446 */       StringUtilities.replace(query, "{INSERT_FROM}", "");
/* 447 */       StringUtilities.replace(query, "  ", " ");
/* 448 */       log.debug("querying sps event log entries - query:" + query);
/*     */       
/* 450 */       PreparedStatement stmt = connection.prepareStatement(query.toString());
/*     */       try {
/* 452 */         Collection<EntryImpl> entries = new LinkedList();
/* 453 */         ResultSet rs = JDBCUtil.asyncExecuteQuery(stmt);
/*     */         try {
/* 455 */           while (rs.next()) {
/* 456 */             assertNotAborted();
/* 457 */             final long entryID = rs.getLong(1);
/* 458 */             long ts = rs.getLong(2);
/* 459 */             Adapter adapter = Adapter.getInstance(rs.getString(3));
/* 460 */             String servername = rs.getString(4);
/* 461 */             String eventname = rs.getString(5);
/* 462 */             entries.add(new EntryImpl(adapter, eventname, servername, ts, entryID)
/*     */                 {
/*     */                   protected List initAttributes() {
/* 465 */                     return SPSEventLogDB2.this.getAttributes(entryID);
/*     */                   }
/*     */                   
/*     */                   protected Set retrieveAttachmentKeys() {
/* 469 */                     return SPSEventLogDB2.this.getAttachmentKeys(entryID);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */           
/* 474 */           return entries;
/* 475 */         } catch (Exception e) {
/* 476 */           if (isAborted()) {
/* 477 */             throw new SPSEventLog.Query.AbortionException(entries);
/*     */           }
/* 479 */           throw e;
/*     */         } finally {
/*     */           
/* 482 */           rs.close();
/*     */         } 
/*     */       } finally {
/*     */         
/* 486 */         stmt.close();
/*     */       } 
/*     */     } finally {
/*     */       
/* 490 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List getAttributes(long entryID) {
/*     */     try {
/* 496 */       Connection connection = getReadonlyConnection();
/*     */       try {
/* 498 */         String query = "select a.name,a.value from sps_attribute a where a.entry_refid=? order by a.ord asc";
/* 499 */         PreparedStatement stmt = connection.prepareStatement(query);
/*     */         try {
/* 501 */           stmt.setLong(1, entryID);
/* 502 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 504 */             List<SPSEventLog.Attribute> attributes = new ArrayList(30);
/* 505 */             while (rs.next()) {
/* 506 */               String name = rs.getString(1);
/* 507 */               String value = rs.getString(2);
/* 508 */               attributes.add(new SPSEventLog.Attribute(name, value));
/*     */             } 
/* 510 */             return attributes;
/*     */           } finally {
/* 512 */             rs.close();
/*     */           } 
/*     */         } finally {
/* 515 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 518 */         releaseConnection(connection);
/*     */       } 
/* 520 */     } catch (Exception e) {
/* 521 */       log.error("unable to retrieve attributes list for sps entry: " + entryID + ", returning empty list - exception:" + e, e);
/* 522 */       return Collections.EMPTY_LIST;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Set getAttachmentKeys(long entryID) {
/*     */     try {
/* 528 */       Connection connection = getReadonlyConnection();
/*     */       try {
/* 530 */         String query = "select distinct a.key from sps_event_log_attach a where a.entry_refid=?";
/* 531 */         PreparedStatement stmt = connection.prepareStatement(query);
/*     */         try {
/* 533 */           stmt.setLong(1, entryID);
/* 534 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 536 */             Set<Attachment.Key> keys = new HashSet(5);
/* 537 */             while (rs.next()) {
/* 538 */               String tmp = rs.getString(1);
/* 539 */               Attachment.Key key = Attachment.Key.forString(tmp);
/* 540 */               if (key == null) {
/* 541 */                 log.warn("unrecognized key found: " + tmp + "  - ignoring"); continue;
/*     */               } 
/* 543 */               keys.add(key);
/*     */             } 
/*     */             
/* 546 */             return keys;
/*     */           } finally {
/* 548 */             rs.close();
/*     */           } 
/*     */         } finally {
/* 551 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 554 */         releaseConnection(connection);
/*     */       } 
/* 556 */     } catch (Exception e) {
/* 557 */       log.error("unable to retrieve key set  for sps entry: " + entryID + ", returning empty set - exception:" + e, e);
/* 558 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void delete(Collection entries) throws Exception {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual ensureInit : ()V
/*     */     //   4: aload_1
/*     */     //   5: ifnull -> 332
/*     */     //   8: aload_1
/*     */     //   9: invokeinterface size : ()I
/*     */     //   14: ifle -> 332
/*     */     //   17: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   20: new java/lang/StringBuilder
/*     */     //   23: dup
/*     */     //   24: invokespecial <init> : ()V
/*     */     //   27: ldc 'deleting '
/*     */     //   29: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   32: aload_1
/*     */     //   33: invokeinterface size : ()I
/*     */     //   38: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   41: ldc ' entries'
/*     */     //   43: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   46: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   49: invokevirtual debug : (Ljava/lang/Object;)V
/*     */     //   52: aload_0
/*     */     //   53: invokespecial getWriteConnection : ()Ljava/sql/Connection;
/*     */     //   56: astore_2
/*     */     //   57: aload_1
/*     */     //   58: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   63: astore_3
/*     */     //   64: aload_3
/*     */     //   65: invokeinterface hasNext : ()Z
/*     */     //   70: ifeq -> 264
/*     */     //   73: aload_3
/*     */     //   74: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   79: checkcast com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLog$LoggedEntry
/*     */     //   82: astore #4
/*     */     //   84: aload_2
/*     */     //   85: ldc 'delete from sps_event_log a where a.entry_id=?'
/*     */     //   87: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   92: astore #5
/*     */     //   94: aload #5
/*     */     //   96: iconst_1
/*     */     //   97: aload #4
/*     */     //   99: invokeinterface getIdentifier : ()J
/*     */     //   104: invokeinterface setLong : (IJ)V
/*     */     //   109: aload #5
/*     */     //   111: invokeinterface executeUpdate : ()I
/*     */     //   116: pop
/*     */     //   117: jsr -> 131
/*     */     //   120: goto -> 143
/*     */     //   123: astore #6
/*     */     //   125: jsr -> 131
/*     */     //   128: aload #6
/*     */     //   130: athrow
/*     */     //   131: astore #7
/*     */     //   133: aload #5
/*     */     //   135: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   138: invokestatic close : (Ljava/sql/Statement;Lorg/apache/log4j/Logger;)V
/*     */     //   141: ret #7
/*     */     //   143: aload_2
/*     */     //   144: ldc 'delete from sps_attribute a where a.entry_refid=?'
/*     */     //   146: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   151: astore #6
/*     */     //   153: aload #6
/*     */     //   155: iconst_1
/*     */     //   156: aload #4
/*     */     //   158: invokeinterface getIdentifier : ()J
/*     */     //   163: invokeinterface setLong : (IJ)V
/*     */     //   168: aload #6
/*     */     //   170: invokeinterface executeUpdate : ()I
/*     */     //   175: pop
/*     */     //   176: jsr -> 190
/*     */     //   179: goto -> 202
/*     */     //   182: astore #8
/*     */     //   184: jsr -> 190
/*     */     //   187: aload #8
/*     */     //   189: athrow
/*     */     //   190: astore #9
/*     */     //   192: aload #6
/*     */     //   194: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   197: invokestatic close : (Ljava/sql/Statement;Lorg/apache/log4j/Logger;)V
/*     */     //   200: ret #9
/*     */     //   202: aload_2
/*     */     //   203: ldc 'delete from sps_event_log_attach a where a.entry_refid=?'
/*     */     //   205: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   210: astore #7
/*     */     //   212: aload #7
/*     */     //   214: iconst_1
/*     */     //   215: aload #4
/*     */     //   217: invokeinterface getIdentifier : ()J
/*     */     //   222: invokeinterface setLong : (IJ)V
/*     */     //   227: aload #7
/*     */     //   229: invokeinterface executeUpdate : ()I
/*     */     //   234: pop
/*     */     //   235: jsr -> 249
/*     */     //   238: goto -> 261
/*     */     //   241: astore #10
/*     */     //   243: jsr -> 249
/*     */     //   246: aload #10
/*     */     //   248: athrow
/*     */     //   249: astore #11
/*     */     //   251: aload #7
/*     */     //   253: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   256: invokestatic close : (Ljava/sql/Statement;Lorg/apache/log4j/Logger;)V
/*     */     //   259: ret #11
/*     */     //   261: goto -> 64
/*     */     //   264: aload_2
/*     */     //   265: invokeinterface commit : ()V
/*     */     //   270: goto -> 309
/*     */     //   273: astore_3
/*     */     //   274: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   277: new java/lang/StringBuilder
/*     */     //   280: dup
/*     */     //   281: invokespecial <init> : ()V
/*     */     //   284: ldc '...unable to delete entries - exception:'
/*     */     //   286: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   289: aload_3
/*     */     //   290: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   293: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   296: aload_3
/*     */     //   297: invokevirtual error : (Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   300: aload_2
/*     */     //   301: getstatic com/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2.log : Lorg/apache/log4j/Logger;
/*     */     //   304: invokestatic rollback : (Ljava/sql/Connection;Lorg/apache/log4j/Logger;)V
/*     */     //   307: aload_3
/*     */     //   308: athrow
/*     */     //   309: jsr -> 323
/*     */     //   312: goto -> 332
/*     */     //   315: astore #12
/*     */     //   317: jsr -> 323
/*     */     //   320: aload #12
/*     */     //   322: athrow
/*     */     //   323: astore #13
/*     */     //   325: aload_0
/*     */     //   326: aload_2
/*     */     //   327: invokespecial releaseConnection : (Ljava/sql/Connection;)V
/*     */     //   330: ret #13
/*     */     //   332: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #563	-> 0
/*     */     //   #564	-> 4
/*     */     //   #565	-> 17
/*     */     //   #566	-> 52
/*     */     //   #569	-> 57
/*     */     //   #570	-> 73
/*     */     //   #571	-> 84
/*     */     //   #573	-> 94
/*     */     //   #574	-> 109
/*     */     //   #575	-> 117
/*     */     //   #577	-> 120
/*     */     //   #576	-> 123
/*     */     //   #579	-> 143
/*     */     //   #581	-> 153
/*     */     //   #582	-> 168
/*     */     //   #583	-> 176
/*     */     //   #585	-> 179
/*     */     //   #584	-> 182
/*     */     //   #586	-> 202
/*     */     //   #588	-> 212
/*     */     //   #589	-> 227
/*     */     //   #590	-> 235
/*     */     //   #592	-> 238
/*     */     //   #591	-> 241
/*     */     //   #594	-> 261
/*     */     //   #595	-> 264
/*     */     //   #600	-> 270
/*     */     //   #596	-> 273
/*     */     //   #597	-> 274
/*     */     //   #598	-> 300
/*     */     //   #599	-> 307
/*     */     //   #602	-> 309
/*     */     //   #604	-> 312
/*     */     //   #603	-> 315
/*     */     //   #606	-> 332
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   84	177	4	entry	Lcom/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLog$LoggedEntry;
/*     */     //   94	167	5	stmt	Ljava/sql/PreparedStatement;
/*     */     //   153	108	6	stmt2	Ljava/sql/PreparedStatement;
/*     */     //   212	49	7	stmt3	Ljava/sql/PreparedStatement;
/*     */     //   64	200	3	iter	Ljava/util/Iterator;
/*     */     //   274	35	3	e	Ljava/lang/Exception;
/*     */     //   57	275	2	connection	Ljava/sql/Connection;
/*     */     //   0	333	0	this	Lcom/eoos/gm/tis2web/sps/server/implementation/log/SPSEventLogDB2;
/*     */     //   0	333	1	entries	Ljava/util/Collection;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   57	270	273	java/lang/Exception
/*     */     //   57	312	315	finally
/*     */     //   94	120	123	finally
/*     */     //   123	128	123	finally
/*     */     //   153	179	182	finally
/*     */     //   182	187	182	finally
/*     */     //   212	238	241	finally
/*     */     //   241	246	241	finally
/*     */     //   315	320	315	finally
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttachedObject(SPSEventLog.Entry entry, Attachment.Key key) throws Exception {
/* 609 */     ensureInit();
/* 610 */     Connection connection = getReadonlyConnection();
/*     */     try {
/* 612 */       PreparedStatement stmt = connection.prepareStatement("select a.attachment from sps_event_log_attach a where a.entry_refid=? and a.key=?");
/*     */       try {
/* 614 */         stmt.setLong(1, ((EntryImpl)entry).getIdentifier());
/* 615 */         stmt.setString(2, key.toString());
/* 616 */         ResultSet rs = JDBCUtil.asyncExecuteQuery(stmt);
/*     */         try {
/* 618 */           if (rs.next()) {
/* 619 */             InputStream is = rs.getBlob(1).getBinaryStream();
/* 620 */             ObjectInputStream ois = new ObjectInputStream(is);
/* 621 */             return ois.readObject();
/*     */           } 
/* 623 */           return null;
/*     */         }
/*     */         finally {
/*     */           
/* 627 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/*     */         
/* 631 */         JDBCUtil.close(stmt);
/*     */       } 
/*     */     } finally {
/* 634 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ensureInit() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLogDB2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */