/*     */ package com.eoos.gm.tis2web.frame.msg.admin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.Group;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.IMessageWrapper;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.StringIDFactory;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseAdapter
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(DatabaseAdapter.class);
/*     */   
/*     */   private static final int CONTENT_TYPE_TITLE = 1;
/*     */   
/*     */   private static final int CONTENT_TYPE_TEXT = 2;
/*     */   
/*  42 */   private StringIDFactory idFactory = new StringIDFactory("0123456789abcdefghijklmnopqrstuvwxyz", null);
/*     */   
/*  44 */   private static DatabaseAdapter instance = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */ 
/*     */   
/*     */   private DatabaseAdapter() {
/*  50 */     final IDatabaseLink dblink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.msg.db."));
/*  51 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  54 */             dblink.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  59 */               return dblink.requestConnection();
/*  60 */             } catch (Exception e) {
/*  61 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */   }
/*     */   
/*     */   private Connection getReadOnlyConnection() {
/*  68 */     Connection ret = this.connectionProvider.getConnection();
/*     */     try {
/*  70 */       ret.setAutoCommit(false);
/*  71 */       ret.setReadOnly(true);
/*  72 */     } catch (SQLException e) {
/*  73 */       throw new RuntimeException(e);
/*     */     } 
/*  75 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection getWriteConnection() {
/*  79 */     Connection ret = this.connectionProvider.getConnection();
/*     */     try {
/*  81 */       ret.setAutoCommit(false);
/*  82 */       ret.setReadOnly(false);
/*  83 */     } catch (SQLException e) {
/*  84 */       throw new RuntimeException(e);
/*     */     } 
/*  86 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  90 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   public static synchronized DatabaseAdapter getInstance() {
/*  94 */     if (instance == null) {
/*  95 */       instance = new DatabaseAdapter();
/*     */     }
/*  97 */     return instance;
/*     */   }
/*     */   
/*     */   private String getNextID(String id) {
/* 101 */     return (String)this.idFactory.createNextID(id);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getID() throws Exception {
/* 106 */     Connection connection = getWriteConnection();
/*     */     try {
/* 108 */       connection.setAutoCommit(true);
/*     */       
/* 110 */       String ret = null;
/*     */       
/* 112 */       String query1 = "select currentkey from msg_genkey";
/* 113 */       PreparedStatement stmt1 = connection.prepareStatement(query1);
/*     */       try {
/* 115 */         String query2 = "update msg_genkey set currentkey=? where currentkey=?";
/* 116 */         PreparedStatement stmt2 = connection.prepareStatement(query2);
/*     */         
/*     */         try {
/*     */           do {
/* 120 */             ResultSet rs = stmt1.executeQuery();
/*     */             try {
/* 122 */               if (rs.next()) {
/* 123 */                 ret = rs.getString(1);
/*     */               } else {
/* 125 */                 throw new IllegalStateException();
/*     */               } 
/*     */             } finally {
/*     */               
/* 129 */               rs.close();
/*     */             } 
/*     */             
/* 132 */             stmt2.setString(1, getNextID(ret));
/* 133 */             stmt2.setString(2, ret);
/* 134 */             int result = stmt2.executeUpdate();
/* 135 */             if (result == 1)
/*     */               continue; 
/* 137 */             log.info("id: " + ret + " is already in use, retrying");
/* 138 */             ret = null;
/*     */           }
/* 140 */           while (ret == null);
/*     */           
/* 142 */           return ret;
/*     */         } finally {
/* 144 */           stmt2.close();
/*     */         } 
/*     */       } finally {
/* 147 */         stmt1.close();
/*     */       } 
/*     */     } finally {
/* 150 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IMessage createMessage(IMessage msgData) throws Exception {
/* 156 */     String id = getID();
/*     */     
/* 158 */     Connection connection = getWriteConnection();
/*     */     try {
/* 160 */       connection.setAutoCommit(false);
/*     */       
/* 162 */       IMessage ret = createMessage(connection, id, msgData, System.currentTimeMillis());
/* 163 */       connection.commit();
/* 164 */       return ret;
/* 165 */     } catch (Exception e) {
/* 166 */       connection.rollback();
/* 167 */       throw e;
/*     */     } finally {
/* 169 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IMessage createMessage(Connection connection, final String id, IMessage msgData, long ts) throws Exception {
/* 175 */     PreparedStatement stmt = connection.prepareStatement("insert into msg_messages (msg_id,msg_type,msg_status,default_locale,msg_external_id,ts) values(?,?,?,?,?,?)");
/*     */     try {
/* 177 */       stmt.setString(1, id);
/* 178 */       stmt.setInt(2, msgData.getType().toExternal());
/* 179 */       stmt.setInt(3, msgData.getStatus().toExternal());
/* 180 */       stmt.setString(4, msgData.getDefaultLocale().toString());
/* 181 */       stmt.setString(5, msgData.getExternalID());
/* 182 */       stmt.setLong(6, ts);
/*     */       
/* 184 */       if (stmt.executeUpdate() != 1) {
/* 185 */         throw (stmt.getWarnings() != null) ? stmt.getWarnings() : new IllegalStateException();
/*     */       }
/*     */     } finally {
/* 188 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 191 */     stmt = connection.prepareStatement("insert into msg_acl_groups(msg_refid,group_name) values(?,?)");
/*     */     try {
/* 193 */       stmt.setString(1, id);
/* 194 */       for (Iterator<Group> iter = msgData.getUserGroups().iterator(); iter.hasNext(); ) {
/* 195 */         Group group = iter.next();
/* 196 */         stmt.setString(2, group.toExternal());
/* 197 */         stmt.addBatch();
/*     */       } 
/*     */       
/* 200 */       int[] result = stmt.executeBatch();
/* 201 */       for (int i = 0; i < result.length; i++) {
/* 202 */         if (result[i] != 1 && result[i] != -2) {
/* 203 */           throw (stmt.getWarnings() != null) ? stmt.getWarnings() : new IllegalStateException();
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 208 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 211 */     stmt = connection.prepareStatement("insert into msg_modules(msg_refid,module_name) values(?,?)");
/*     */     try {
/* 213 */       stmt.setString(1, id);
/* 214 */       for (Iterator<Module> iter = msgData.getTargetModules().iterator(); iter.hasNext(); ) {
/* 215 */         Module module = iter.next();
/* 216 */         stmt.setString(2, module.toExternal());
/* 217 */         stmt.addBatch();
/*     */       } 
/*     */       
/* 220 */       int[] result = stmt.executeBatch();
/* 221 */       for (int i = 0; i < result.length; i++) {
/* 222 */         if (result[i] != 1 && result[i] != -2) {
/* 223 */           throw (stmt.getWarnings() != null) ? stmt.getWarnings() : new IllegalStateException();
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 228 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 231 */     stmt = connection.prepareStatement("insert into msg_users(msg_refid,user_id) values(?,?)");
/*     */     try {
/* 233 */       stmt.setString(1, id);
/* 234 */       for (Iterator<String> iter = msgData.getUserIDs().iterator(); iter.hasNext(); ) {
/* 235 */         String userID = iter.next();
/* 236 */         stmt.setString(2, userID);
/* 237 */         stmt.addBatch();
/*     */       } 
/*     */       
/* 240 */       int[] result = stmt.executeBatch();
/* 241 */       for (int i = 0; i < result.length; i++) {
/* 242 */         if (result[i] != 1 && result[i] != -2) {
/* 243 */           throw (stmt.getWarnings() != null) ? stmt.getWarnings() : new IllegalStateException();
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 248 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 251 */     stmt = connection.prepareStatement("insert into msg_content(msg_refid,locale,content_type,text) values (?,?,?,?)");
/*     */     try {
/* 253 */       stmt.setString(1, id);
/* 254 */       for (Iterator<Locale> iter = msgData.getSupportedLocales().iterator(); iter.hasNext(); ) {
/* 255 */         Locale locale = iter.next();
/* 256 */         stmt.setString(2, locale.toString());
/*     */         
/* 258 */         IMessage.IContent content = msgData.getContent(locale);
/* 259 */         stmt.setInt(3, 1);
/* 260 */         stmt.setString(4, content.getTitle());
/* 261 */         stmt.addBatch();
/* 262 */         stmt.setInt(3, 2);
/* 263 */         stmt.setString(4, content.getText());
/* 264 */         stmt.addBatch();
/*     */       } 
/*     */       
/* 267 */       int[] result = stmt.executeBatch();
/* 268 */       for (int i = 0; i < result.length; i++) {
/* 269 */         if (result[i] != 1 && result[i] != -2) {
/* 270 */           throw (stmt.getWarnings() != null) ? stmt.getWarnings() : new IllegalStateException();
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 275 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 278 */     return (IMessage)new IMessageWrapper(msgData)
/*     */       {
/*     */         public String getID() {
/* 281 */           return id;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteMessage(String msgID) throws Exception {
/* 289 */     Connection connection = getWriteConnection();
/*     */     try {
/* 291 */       deleteMessage(connection, msgID);
/* 292 */       connection.commit();
/* 293 */     } catch (Exception e) {
/* 294 */       connection.rollback();
/* 295 */       throw e;
/*     */     } finally {
/* 297 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void deleteMessage(Connection connection, String msgID) throws Exception {
/* 303 */     PreparedStatement stmt = connection.prepareStatement("delete from msg_messages where msg_id=?");
/*     */     try {
/* 305 */       stmt.setString(1, msgID);
/* 306 */       stmt.executeUpdate();
/*     */     } finally {
/* 308 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 311 */     stmt = connection.prepareStatement("delete from msg_content where msg_refid=?");
/*     */     try {
/* 313 */       stmt.setString(1, msgID);
/* 314 */       stmt.executeUpdate();
/*     */     } finally {
/* 316 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 319 */     stmt = connection.prepareStatement("delete from msg_acl_groups where msg_refid=?");
/*     */     try {
/* 321 */       stmt.setString(1, msgID);
/* 322 */       stmt.executeUpdate();
/*     */     } finally {
/* 324 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 327 */     stmt = connection.prepareStatement("delete from msg_modules where msg_refid=?");
/*     */     try {
/* 329 */       stmt.setString(1, msgID);
/* 330 */       stmt.executeUpdate();
/*     */     } finally {
/* 332 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/* 335 */     stmt = connection.prepareStatement("delete from msg_users where msg_refid=?");
/*     */     try {
/* 337 */       stmt.setString(1, msgID);
/* 338 */       stmt.executeUpdate();
/*     */     } finally {
/* 340 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modify(IMessage msgData) throws Exception {
/* 346 */     Connection connection = getWriteConnection();
/*     */     try {
/* 348 */       deleteMessage(connection, msgData.getID());
/* 349 */       createMessage(connection, msgData.getID(), msgData, System.currentTimeMillis());
/* 350 */       connection.commit();
/* 351 */     } catch (Exception e) {
/* 352 */       connection.rollback();
/* 353 */       throw e;
/*     */     } finally {
/* 355 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String adjustWildcardPattern(String pattern) {
/* 361 */     String ret = pattern;
/* 362 */     if (ret != null) {
/* 363 */       ret = StringUtilities.replace(ret, "*", "%");
/* 364 */       ret = StringUtilities.replace(ret, "?", "_");
/*     */     } 
/* 366 */     return ret;
/*     */   }
/*     */   
/*     */   private String adjustQuery(String query) {
/* 370 */     String ret = query;
/* 371 */     if (Util.occurenceCount(query, " where ") > 1) {
/* 372 */       ret = Util.replaceOccurence(2, query, " where ", " and ");
/*     */     }
/* 374 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean existsMessage(String id) throws Exception {
/* 378 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 380 */       PreparedStatement stmt = connection.prepareStatement("select msg_id from msg_messages where msg_id=?");
/*     */       try {
/* 382 */         stmt.setString(1, id);
/* 383 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 385 */           return rs.next();
/*     */         } finally {
/* 387 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 390 */         JDBCUtil.close(stmt);
/*     */       } 
/*     */     } finally {
/* 393 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SuppressWarnings({"SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"})
/*     */   public Collection getMessages(QueryFilter filter) throws Exception {
/* 400 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 402 */       String _stmt1 = "select a.msg_id,  a.msg_type, a.msg_status, a.default_locale, a.msg_external_id, a.ts from msg_messages a ";
/* 403 */       if (filter != null) {
/* 404 */         String idPattern = adjustWildcardPattern(filter.getIDPattern());
/* 405 */         if (idPattern != null && idPattern.length() != 0) {
/* 406 */           _stmt1 = _stmt1 + "where a.msg_external_id like '" + idPattern + "'";
/*     */         }
/* 408 */         IMessage.Status status = filter.getStatus();
/* 409 */         if (status != null) {
/* 410 */           _stmt1 = _stmt1 + "where a.msg_status = " + status.toExternal();
/*     */         }
/*     */         
/* 413 */         _stmt1 = adjustQuery(_stmt1);
/*     */       } 
/*     */       
/* 416 */       PreparedStatement stmt1 = connection.prepareStatement(_stmt1);
/*     */       try {
/* 418 */         ResultSet rs1 = stmt1.executeQuery();
/*     */         try {
/* 420 */           Collection<MessageRI> ret = new LinkedList();
/* 421 */           while (rs1.next()) {
/* 422 */             String id = rs1.getString(1);
/* 423 */             IMessage.Type type = IMessage.Type.toType(rs1.getInt(2));
/* 424 */             IMessage.Status status = IMessage.Status.toStatus(rs1.getInt(3));
/* 425 */             Locale defaultLocale = Util.parseLocale(rs1.getString(4));
/* 426 */             String extID = rs1.getString(5);
/* 427 */             long ts = rs1.getLong(6);
/*     */             
/* 429 */             Set<Group> groups = new LinkedHashSet();
/* 430 */             PreparedStatement stmt2 = connection.prepareStatement("select b.group_name from msg_acl_groups b where b.msg_refid=?");
/*     */             try {
/* 432 */               stmt2.setString(1, id);
/* 433 */               ResultSet rs2 = stmt2.executeQuery();
/*     */               try {
/* 435 */                 while (rs2.next()) {
/* 436 */                   groups.add(Group.getInstance(rs2.getString(1)));
/*     */                 }
/*     */               } finally {
/* 439 */                 JDBCUtil.close(rs2);
/*     */               } 
/*     */             } finally {
/*     */               
/* 443 */               JDBCUtil.close(stmt2);
/*     */             } 
/*     */             
/* 446 */             Set<String> users = new LinkedHashSet();
/* 447 */             stmt2 = connection.prepareStatement("select b.user_id from msg_users b where b.msg_refid=?");
/*     */             try {
/* 449 */               stmt2.setString(1, id);
/* 450 */               ResultSet rs2 = stmt2.executeQuery();
/*     */               try {
/* 452 */                 while (rs2.next()) {
/* 453 */                   users.add(rs2.getString(1));
/*     */                 }
/*     */               } finally {
/* 456 */                 JDBCUtil.close(rs2);
/*     */               } 
/*     */             } finally {
/*     */               
/* 460 */               JDBCUtil.close(stmt2);
/*     */             } 
/*     */             
/* 463 */             Set<Module> modules = new LinkedHashSet();
/* 464 */             stmt2 = connection.prepareStatement("select module_name from msg_modules where msg_refid=?");
/*     */             try {
/* 466 */               stmt2.setString(1, id);
/* 467 */               ResultSet rs2 = stmt2.executeQuery();
/*     */               try {
/* 469 */                 while (rs2.next()) {
/* 470 */                   modules.add(Module.getInstance(rs2.getString(1)));
/*     */                 }
/*     */               } finally {
/* 473 */                 JDBCUtil.close(rs2);
/*     */               } 
/*     */             } finally {
/*     */               
/* 477 */               JDBCUtil.close(stmt2);
/*     */             } 
/*     */             
/* 480 */             Map<Object, Object> locale2Content = new LinkedHashMap<Object, Object>();
/*     */             
/* 482 */             stmt2 = connection.prepareStatement("select c.locale, c.content_type, c.text from msg_content c where c.msg_refid=?");
/*     */             try {
/* 484 */               stmt2.setString(1, id);
/* 485 */               ResultSet rs2 = stmt2.executeQuery();
/*     */               try {
/* 487 */                 while (rs2.next()) {
/* 488 */                   Locale locale = Util.parseLocale(rs2.getString(1));
/* 489 */                   int content_type = rs2.getInt(2);
/* 490 */                   String text = rs2.getString(3);
/* 491 */                   ContentImpl co = (ContentImpl)locale2Content.get(locale);
/* 492 */                   if (co == null) {
/* 493 */                     co = new ContentImpl(locale);
/* 494 */                     locale2Content.put(locale, co);
/*     */                   } 
/* 496 */                   if (content_type == 1) {
/* 497 */                     co.setTitle(text); continue;
/*     */                   } 
/* 499 */                   co.setText(text);
/*     */                 }
/*     */               
/*     */               } finally {
/*     */                 
/* 504 */                 JDBCUtil.close(rs2);
/*     */               } 
/*     */             } finally {
/*     */               
/* 508 */               JDBCUtil.close(stmt2);
/*     */             } 
/*     */             
/* 511 */             ret.add(new MessageRI(status, id, defaultLocale, groups, extID, modules, locale2Content, type, ts, users));
/*     */           } 
/*     */           
/* 514 */           return ret;
/*     */         } finally {
/*     */           
/* 517 */           JDBCUtil.close(rs1);
/*     */         } 
/*     */       } finally {
/*     */         
/* 521 */         JDBCUtil.close(stmt1);
/*     */       } 
/*     */     } finally {
/*     */       
/* 525 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStatus(IMessage msg, IMessage.Status status) throws Exception {
/* 530 */     Connection connection = getWriteConnection();
/*     */     try {
/* 532 */       PreparedStatement stmt = connection.prepareStatement("update msg_messages set msg_status=? where msg_id=?");
/*     */       try {
/* 534 */         stmt.setInt(1, status.toExternal());
/* 535 */         stmt.setString(2, msg.getID());
/* 536 */         if (stmt.executeUpdate() != 1) {
/* 537 */           throw new IllegalStateException("unexpected return count");
/*     */         }
/*     */         
/* 540 */         connection.commit();
/*     */       } finally {
/* 542 */         JDBCUtil.close(stmt);
/*     */       }
/*     */     
/* 545 */     } catch (Exception e) {
/* 546 */       connection.rollback();
/* 547 */       throw e;
/*     */     } finally {
/* 549 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getActiveMessages() throws Exception {
/* 556 */     return getMessages(new QueryFilter()
/*     */         {
/*     */           public IMessage.Status getStatus() {
/* 559 */             return IMessage.Status.ACTIVE;
/*     */           }
/*     */           
/*     */           public String getIDPattern() {
/* 563 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */