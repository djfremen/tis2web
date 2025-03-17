/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.MajorOperation;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.VehicleCheckList;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Attribute;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CE2value;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CLElement;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CheckList;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Description;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Footnote;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Value;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.IStatementManager;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.jdbc.StatementManagerV2;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ICLStore
/*     */ {
/*  33 */   protected static final Logger log = Logger.getLogger(ICLStore.class);
/*     */   
/*     */   static Integer ENGINE_WILDCARD;
/*     */   
/*     */   static Integer MODEL_WILDCARD;
/*     */   
/*     */   static Integer MODELYEAR_WILDCARD;
/*     */   
/*     */   static Integer COUNTRYGROUP_WILDCARD;
/*     */   
/*     */   protected DBMS dbms;
/*     */   
/*     */   protected ICLCache cache;
/*     */   
/*  47 */   protected Map mServiceTypeXSL = null;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private IStatementManager stmtManager;
/*     */   
/*     */   public ICLStore(final DBMS dbms, ICLCache cache) {
/*  54 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  57 */             dbms.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  62 */               return dbms.requestConnection();
/*  63 */             } catch (Exception e) {
/*  64 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */     
/*  69 */     this.stmtManager = (IStatementManager)new StatementManagerV2(this.connectionProvider);
/*  70 */     this.dbms = dbms;
/*     */     
/*  72 */     this.cache = cache;
/*     */   }
/*     */   
/*     */   private Connection getReadConnection() throws Exception {
/*  76 */     Connection ret = this.connectionProvider.getConnection();
/*  77 */     ret.setReadOnly(true);
/*  78 */     ret.setAutoCommit(false);
/*  79 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  83 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   Map loadLanguages() {
/*  87 */     Map<Object, Object> mLan2LID = new HashMap<Object, Object>();
/*  88 */     Connection con = null;
/*  89 */     Statement stmt = null;
/*  90 */     ResultSet rs = null;
/*     */     try {
/*  92 */       String strQuery = this.dbms.getSQL("select la_id,la_scds from Languages");
/*  93 */       con = getReadConnection();
/*  94 */       stmt = con.createStatement();
/*  95 */       rs = stmt.executeQuery(strQuery);
/*  96 */       while (rs.next()) {
/*  97 */         mLan2LID.put(new String(rs.getString(2).toLowerCase(Locale.ENGLISH).trim().getBytes(), "UTF-8"), Integer.valueOf(rs.getInt(1)));
/*     */       }
/*  99 */     } catch (Exception e) {
/* 100 */       log.error("Exception in loadLanguages" + e);
/* 101 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 104 */         if (rs != null) {
/* 105 */           rs.close();
/*     */         }
/* 107 */         if (stmt != null) {
/* 108 */           stmt.close();
/*     */         }
/* 110 */         if (con != null) {
/* 111 */           releaseConnection(con);
/*     */         }
/* 113 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 116 */     return mLan2LID;
/*     */   }
/*     */   
/*     */   Map loadConstants() {
/* 120 */     Map<Object, Object> mConstants2Value = new HashMap<Object, Object>();
/* 121 */     Connection con = null;
/* 122 */     Statement stmt = null;
/* 123 */     ResultSet rs = null;
/*     */     try {
/* 125 */       String strQuery = this.dbms.getSQL("select a.cs_name,b.la_fk,b.tx_text from Constants a,Strings b where b.tx_id = a.tx_fk");
/* 126 */       con = getReadConnection();
/* 127 */       stmt = con.createStatement();
/* 128 */       rs = stmt.executeQuery(strQuery);
/* 129 */       while (rs.next()) {
/* 130 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 131 */         Map<Object, Object> oLan = (Map)mConstants2Value.get(iLan);
/* 132 */         if (oLan == null) {
/* 133 */           mConstants2Value.put(iLan, oLan = new HashMap<Object, Object>());
/*     */         }
/* 135 */         oLan.put(new String(rs.getString(1).trim().getBytes(), "UTF-8"), new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/*     */       } 
/* 137 */     } catch (Exception e) {
/* 138 */       log.error("Exception in loadConstants" + e);
/* 139 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 142 */         if (rs != null) {
/* 143 */           rs.close();
/*     */         }
/* 145 */         if (stmt != null) {
/* 146 */           stmt.close();
/*     */         }
/* 148 */         if (con != null) {
/* 149 */           releaseConnection(con);
/*     */         }
/* 151 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 154 */     return mConstants2Value;
/*     */   }
/*     */   
/*     */   Map loadCountryGroups() {
/* 158 */     Map<Object, Object> mCountry2CountryGroup = new HashMap<Object, Object>();
/* 159 */     Connection con = null;
/* 160 */     Statement stmt = null;
/* 161 */     ResultSet rs = null;
/*     */     try {
/* 163 */       String strQuery = this.dbms.getSQL("select CG_ID, CG_NAME from COUNTRYGROUPS");
/* 164 */       con = getReadConnection();
/* 165 */       stmt = con.createStatement();
/* 166 */       rs = stmt.executeQuery(strQuery);
/* 167 */       if (rs.next()) {
/* 168 */         String e = new String(rs.getString(2).trim().getBytes(), "UTF-8");
/* 169 */         if ("*".equals(e)) {
/* 170 */           COUNTRYGROUP_WILDCARD = Integer.valueOf(rs.getInt(1));
/*     */         }
/*     */       } else {
/* 173 */         throw new IllegalStateException();
/*     */       } 
/* 175 */       if (COUNTRYGROUP_WILDCARD == null) {
/* 176 */         throw new IllegalStateException();
/*     */       }
/* 178 */       rs.close();
/* 179 */       stmt.close();
/* 180 */       strQuery = this.dbms.getSQL("select cg.CG_ID, cg.CG_NAME, c.CO_CODE from COUNTRIES c, COUNTRYGROUPS cg WHERE c.CG_FK = cg.CG_ID");
/* 181 */       stmt = con.createStatement();
/* 182 */       rs = stmt.executeQuery(strQuery);
/* 183 */       while (rs.next()) {
/* 184 */         String e = new String(rs.getString(2).trim().getBytes(), "UTF-8");
/* 185 */         if (!"*".equals(e)) {
/* 186 */           mCountry2CountryGroup.put(new String(rs.getString(3).trim().getBytes(), "UTF-8"), Integer.valueOf(rs.getInt(1)));
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 191 */     catch (Exception e) {
/* 192 */       log.error("Exception in loadCountryGroups" + e);
/* 193 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 196 */         if (rs != null) {
/* 197 */           rs.close();
/*     */         }
/* 199 */         if (stmt != null) {
/* 200 */           stmt.close();
/*     */         }
/* 202 */         if (con != null) {
/* 203 */           releaseConnection(con);
/*     */         }
/* 205 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 208 */     return mCountry2CountryGroup;
/*     */   }
/*     */   
/*     */   Map loadEngines() {
/* 212 */     Map<Object, Object> mEngine2ID = new HashMap<Object, Object>();
/* 213 */     Connection con = null;
/* 214 */     Statement stmt = null;
/* 215 */     ResultSet rs = null;
/*     */     try {
/* 217 */       String strQuery = this.dbms.getSQL("select en_id,en_code from Engines");
/* 218 */       con = getReadConnection();
/* 219 */       stmt = con.createStatement();
/* 220 */       rs = stmt.executeQuery(strQuery);
/* 221 */       while (rs.next()) {
/* 222 */         String e = new String(rs.getString(2).trim().getBytes(), "UTF-8");
/* 223 */         if (!"*".equals(e)) {
/* 224 */           mEngine2ID.put(e, Integer.valueOf(rs.getInt(1))); continue;
/*     */         } 
/* 226 */         ENGINE_WILDCARD = Integer.valueOf(rs.getInt(1));
/*     */       }
/*     */     
/* 229 */     } catch (Exception e) {
/* 230 */       log.error("Exception in loadEngines" + e);
/* 231 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 234 */         if (rs != null) {
/* 235 */           rs.close();
/*     */         }
/* 237 */         if (stmt != null) {
/* 238 */           stmt.close();
/*     */         }
/* 240 */         if (con != null) {
/* 241 */           releaseConnection(con);
/*     */         }
/* 243 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 246 */     return mEngine2ID;
/*     */   }
/*     */   
/*     */   Map loadModels() {
/* 250 */     Map<Object, Object> mModel2ID = new HashMap<Object, Object>();
/* 251 */     Connection con = null;
/* 252 */     Statement stmt = null;
/* 253 */     ResultSet rs = null;
/*     */     try {
/* 255 */       String strQuery = this.dbms.getSQL("select mo_id,mo_name from Models");
/* 256 */       con = getReadConnection();
/* 257 */       stmt = con.createStatement();
/* 258 */       rs = stmt.executeQuery(strQuery);
/* 259 */       while (rs.next()) {
/* 260 */         String m = new String(rs.getString(2).trim().getBytes(), "UTF-8");
/* 261 */         if (!"*".equals(m)) {
/* 262 */           mModel2ID.put(m, Integer.valueOf(rs.getInt(1))); continue;
/*     */         } 
/* 264 */         MODEL_WILDCARD = Integer.valueOf(rs.getInt(1));
/*     */       }
/*     */     
/* 267 */     } catch (Exception e) {
/* 268 */       log.error("Exception in loadModels" + e);
/* 269 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 272 */         if (rs != null) {
/* 273 */           rs.close();
/*     */         }
/* 275 */         if (stmt != null) {
/* 276 */           stmt.close();
/*     */         }
/* 278 */         if (con != null) {
/* 279 */           releaseConnection(con);
/*     */         }
/* 281 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 284 */     return mModel2ID;
/*     */   }
/*     */   
/*     */   Map loadModelYears() {
/* 288 */     Map<Object, Object> mModelYear2ID = new HashMap<Object, Object>();
/* 289 */     Connection con = null;
/* 290 */     Statement stmt = null;
/* 291 */     ResultSet rs = null;
/*     */     try {
/* 293 */       String strQuery = this.dbms.getSQL("select my_id,my_year from ModelYears");
/* 294 */       con = getReadConnection();
/* 295 */       stmt = con.createStatement();
/* 296 */       rs = stmt.executeQuery(strQuery);
/* 297 */       while (rs.next()) {
/* 298 */         String myd = new String(rs.getString(2).trim().getBytes(), "UTF-8");
/* 299 */         if (!"*".equals(myd)) {
/* 300 */           mModelYear2ID.put(myd, Integer.valueOf(rs.getInt(1))); continue;
/*     */         } 
/* 302 */         MODELYEAR_WILDCARD = Integer.valueOf(rs.getInt(1));
/*     */       }
/*     */     
/* 305 */     } catch (Exception e) {
/* 306 */       log.error("Exception in loadModelYears" + e);
/* 307 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 310 */         if (rs != null) {
/* 311 */           rs.close();
/*     */         }
/* 313 */         if (stmt != null) {
/* 314 */           stmt.close();
/*     */         }
/* 316 */         if (con != null) {
/* 317 */           releaseConnection(con);
/*     */         }
/* 319 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 322 */     return mModelYear2ID;
/*     */   }
/*     */   
/*     */   Map loadAttributes() {
/* 326 */     Map<Object, Object> mAttribute2Value = new HashMap<Object, Object>();
/* 327 */     Connection con = null;
/* 328 */     Statement stmt = null;
/* 329 */     ResultSet rs = null;
/*     */     try {
/* 331 */       String strQuery = this.dbms.getSQL("select a.at_id,b.la_fk,b.tx_text from Attributes a,Strings b where b.tx_id = a.tx_fk");
/* 332 */       con = getReadConnection();
/* 333 */       stmt = con.createStatement();
/* 334 */       rs = stmt.executeQuery(strQuery);
/* 335 */       while (rs.next()) {
/* 336 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 337 */         Map<Object, Object> oLan = (Map)mAttribute2Value.get(iLan);
/* 338 */         if (oLan == null) {
/* 339 */           mAttribute2Value.put(iLan, oLan = new HashMap<Object, Object>());
/*     */         }
/* 341 */         oLan.put(Integer.valueOf(rs.getInt(1)), new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/*     */       } 
/* 343 */     } catch (Exception e) {
/* 344 */       log.error("Exception in loadAttributes" + e);
/* 345 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 348 */         if (rs != null) {
/* 349 */           rs.close();
/*     */         }
/* 351 */         if (stmt != null) {
/* 352 */           stmt.close();
/*     */         }
/* 354 */         if (con != null) {
/* 355 */           releaseConnection(con);
/*     */         }
/* 357 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 360 */     return mAttribute2Value;
/*     */   }
/*     */   
/*     */   Map loadComments() {
/* 364 */     Map<Object, Object> mComment2Value = new HashMap<Object, Object>();
/* 365 */     Connection con = null;
/* 366 */     Statement stmt = null;
/* 367 */     ResultSet rs = null;
/*     */     try {
/* 369 */       String strQuery = this.dbms.getSQL("select a.cm_id,b.la_fk,b.tx_text from Comments a,Strings b where b.tx_id = a.tx_fk");
/* 370 */       con = getReadConnection();
/* 371 */       stmt = con.createStatement();
/* 372 */       rs = stmt.executeQuery(strQuery);
/* 373 */       while (rs.next()) {
/* 374 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 375 */         Map<Object, Object> oLan = (Map)mComment2Value.get(iLan);
/* 376 */         if (oLan == null) {
/* 377 */           mComment2Value.put(iLan, oLan = new HashMap<Object, Object>());
/*     */         }
/* 379 */         oLan.put(Integer.valueOf(rs.getInt(1)), new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/*     */       } 
/* 381 */     } catch (Exception e) {
/* 382 */       log.error("Exception in loadComments" + e);
/* 383 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 386 */         if (rs != null) {
/* 387 */           rs.close();
/*     */         }
/* 389 */         if (stmt != null) {
/* 390 */           stmt.close();
/*     */         }
/* 392 */         if (con != null) {
/* 393 */           releaseConnection(con);
/*     */         }
/* 395 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 398 */     return mComment2Value;
/*     */   }
/*     */   
/*     */   Map loadDriverTypes() {
/* 402 */     Map<Object, Object> mDriverType2Value = new HashMap<Object, Object>();
/* 403 */     Connection con = null;
/* 404 */     Statement stmt = null;
/* 405 */     ResultSet rs = null;
/*     */     try {
/* 407 */       String strQuery = this.dbms.getSQL("select a.dt_id,b.la_fk,b.tx_text from DriverTypes a,Strings b where b.tx_id = a.tx_fk");
/* 408 */       con = getReadConnection();
/* 409 */       stmt = con.createStatement();
/* 410 */       rs = stmt.executeQuery(strQuery);
/* 411 */       while (rs.next()) {
/* 412 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 413 */         Map<Object, Object> oLan = (Map)mDriverType2Value.get(iLan);
/* 414 */         if (oLan == null) {
/* 415 */           mDriverType2Value.put(iLan, oLan = new HashMap<Object, Object>());
/*     */         }
/* 417 */         oLan.put(Integer.valueOf(rs.getInt(1)), new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/*     */       } 
/* 419 */     } catch (Exception e) {
/* 420 */       log.error("Exception in loadDriverTypes" + e);
/* 421 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 424 */         if (rs != null) {
/* 425 */           rs.close();
/*     */         }
/* 427 */         if (stmt != null) {
/* 428 */           stmt.close();
/*     */         }
/* 430 */         if (con != null) {
/* 431 */           releaseConnection(con);
/*     */         }
/* 433 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 436 */     return mDriverType2Value;
/*     */   }
/*     */   
/*     */   Map loadServiceTypes() {
/* 440 */     Map<Object, Object> mServiceType2Value = new HashMap<Object, Object>();
/* 441 */     this.mServiceTypeXSL = new HashMap<Object, Object>();
/* 442 */     Connection con = null;
/* 443 */     Statement stmt = null;
/* 444 */     ResultSet rs = null;
/* 445 */     boolean isSQLError = false;
/*     */     
/*     */     try {
/* 448 */       String strQuery = this.dbms.getSQL("select a.st_id,b.la_fk,b.tx_text,a.st_style from ServiceTypes a,Strings b where b.tx_id = a.tx_fk");
/* 449 */       con = getReadConnection();
/* 450 */       stmt = con.createStatement();
/*     */       try {
/* 452 */         rs = stmt.executeQuery(strQuery);
/* 453 */       } catch (SQLException ex) {
/* 454 */         isSQLError = true;
/* 455 */         this.mServiceTypeXSL = null;
/* 456 */         strQuery = this.dbms.getSQL("select a.st_id,b.la_fk,b.tx_text from ServiceTypes a,Strings b where b.tx_id = a.tx_fk");
/* 457 */         rs = stmt.executeQuery(strQuery);
/*     */       } 
/*     */       
/* 460 */       while (rs.next()) {
/* 461 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 462 */         Map<Object, Object> oLan = (Map)mServiceType2Value.get(iLan);
/* 463 */         if (oLan == null) {
/* 464 */           mServiceType2Value.put(iLan, oLan = new HashMap<Object, Object>());
/*     */         }
/* 466 */         oLan.put(Integer.valueOf(rs.getInt(1)), new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/* 467 */         if (!isSQLError) {
/* 468 */           Map<Object, Object> oLanXSL = (Map)this.mServiceTypeXSL.get(iLan);
/* 469 */           if (oLanXSL == null) {
/* 470 */             this.mServiceTypeXSL.put(iLan, oLanXSL = new HashMap<Object, Object>());
/*     */           }
/* 472 */           oLanXSL.put(Integer.valueOf(rs.getInt(1)), rs.getString(4).trim());
/*     */         }
/*     */       
/*     */       } 
/* 476 */     } catch (Exception e) {
/* 477 */       log.error("Exception in loadServiceTypes" + e);
/* 478 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 481 */         if (rs != null) {
/* 482 */           rs.close();
/*     */         }
/* 484 */         if (stmt != null) {
/* 485 */           stmt.close();
/*     */         }
/* 487 */         if (con != null) {
/* 488 */           releaseConnection(con);
/*     */         }
/* 490 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 493 */     return mServiceType2Value;
/*     */   }
/*     */   
/*     */   protected Map getServiceTypeXSL() {
/* 497 */     return this.mServiceTypeXSL;
/*     */   }
/*     */   
/*     */   Map loadMajorOperations() {
/* 501 */     Map<Object, Object> mMajorOperation2Data = new HashMap<Object, Object>();
/* 502 */     Connection con = null;
/* 503 */     Statement stmt = null;
/* 504 */     ResultSet rs = null;
/*     */     try {
/* 506 */       String strQuery = this.dbms.getSQL("select ha_id,ha_descr,tx_fk,dt_fk,st_fk from MajorOps");
/* 507 */       con = getReadConnection();
/* 508 */       stmt = con.createStatement();
/* 509 */       rs = stmt.executeQuery(strQuery);
/* 510 */       while (rs.next()) {
/* 511 */         Integer ID = Integer.valueOf(rs.getInt(1));
/* 512 */         String majorOpNr = rs.getString(2).trim();
/* 513 */         Integer.valueOf(rs.getInt(3));
/* 514 */         Integer serviceType = Integer.valueOf(rs.getInt(5));
/* 515 */         Integer driverType = Integer.valueOf(rs.getInt(4));
/* 516 */         if (rs.wasNull()) {
/* 517 */           driverType = null;
/*     */         }
/* 519 */         MajorOperation mo = new MajorOperation(ID, serviceType, driverType, majorOpNr);
/* 520 */         mMajorOperation2Data.put(mo.getMajorOpNr().toUpperCase(Locale.ENGLISH), mo);
/*     */       } 
/* 522 */       rs.close();
/* 523 */       stmt.close();
/* 524 */       strQuery = this.dbms.getSQL("select a.ha_descr,b.la_fk,b.tx_text from MajorOps a,Strings b where b.tx_id = a.tx_fk");
/* 525 */       stmt = con.createStatement();
/* 526 */       rs = stmt.executeQuery(strQuery);
/* 527 */       while (rs.next()) {
/* 528 */         String majorOpNr = rs.getString(1).trim();
/* 529 */         MajorOperation mo = (MajorOperation)mMajorOperation2Data.get(majorOpNr);
/* 530 */         if (mo == null) {
/* 531 */           throw new IllegalArgumentException();
/*     */         }
/* 533 */         Integer iLan = Integer.valueOf(rs.getInt(2));
/* 534 */         String description = new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8");
/* 535 */         mo.setDescription(iLan, description);
/*     */       } 
/* 537 */     } catch (Exception e) {
/* 538 */       log.error("Exception in loadMajorOperations" + e);
/* 539 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 542 */         if (rs != null) {
/* 543 */           rs.close();
/*     */         }
/* 545 */         if (stmt != null) {
/* 546 */           stmt.close();
/*     */         }
/* 548 */         if (con != null) {
/* 549 */           releaseConnection(con);
/*     */         }
/* 551 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 554 */     return mMajorOperation2Data;
/*     */   }
/*     */   
/*     */   List loadVehicle2Checklist() {
/* 558 */     List<VehicleCheckList> lVehicle2Checklist = new LinkedList();
/* 559 */     Connection con = null;
/* 560 */     Statement stmt = null;
/* 561 */     ResultSet rs = null;
/*     */     try {
/* 563 */       String strQuery = this.dbms.getSQL("select a.mo_fk,a.my_fk,a.en_fk,a.ha_fk,a.cg_fk,a.cl_fk from CL2Vehicle a, Checklists b where a.cl_fk = b.cl_id and b.rls_flag = 1");
/* 564 */       con = getReadConnection();
/* 565 */       stmt = con.createStatement();
/* 566 */       rs = stmt.executeQuery(strQuery);
/* 567 */       while (rs.next()) {
/* 568 */         Integer model = Integer.valueOf(rs.getInt(1));
/* 569 */         Integer modelyear = Integer.valueOf(rs.getInt(2));
/* 570 */         Integer engine = Integer.valueOf(rs.getInt(3));
/* 571 */         Integer majorop = Integer.valueOf(rs.getInt(4));
/* 572 */         Integer countrygroup = Integer.valueOf(rs.getInt(5));
/* 573 */         Integer checklistid = Integer.valueOf(rs.getInt(6));
/* 574 */         VehicleCheckList vcl = new VehicleCheckList();
/* 575 */         vcl.setModel(model);
/* 576 */         vcl.setModelYear(modelyear);
/* 577 */         vcl.setEngine(engine);
/* 578 */         vcl.setMajorOp(majorop);
/* 579 */         vcl.setCountryGroup(countrygroup);
/* 580 */         vcl.setCheckListID(checklistid);
/* 581 */         lVehicle2Checklist.add(vcl);
/*     */       } 
/* 583 */     } catch (Exception e) {
/* 584 */       log.error("Exception in loadVehicle2Checklist" + e);
/* 585 */       throw new RuntimeException();
/*     */     } finally {
/*     */       try {
/* 588 */         if (rs != null) {
/* 589 */           rs.close();
/*     */         }
/* 591 */         if (stmt != null) {
/* 592 */           stmt.close();
/*     */         }
/* 594 */         if (con != null) {
/* 595 */           releaseConnection(con);
/*     */         }
/* 597 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 600 */     return lVehicle2Checklist;
/*     */   }
/*     */   
/*     */   protected List getFootersForCheckList(long lID, int iLanID) {
/* 604 */     List<Footnote> elems = new LinkedList();
/*     */     try {
/* 606 */       PreparedStatement stmt = getStatementManager().getStatement("select a.fo_fk, a.fo_order, b.tx_id, b.tx_text from CL2Footer a,Footers c,Strings b where  a.cl_fk=? and c.fo_id= a.fo_fk and b.tx_id =c.tx_fk and b.la_fk=? order by a.fo_order");
/*     */       try {
/* 608 */         stmt.setLong(1, lID);
/* 609 */         stmt.setInt(2, iLanID);
/* 610 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 612 */           while (rs.next()) {
/* 613 */             Footnote oO = new Footnote();
/* 614 */             oO.setID(rs.getLong(1));
/* 615 */             oO.setOrder(rs.getInt(2));
/* 616 */             Description oD = new Description();
/* 617 */             oD.setID(rs.getLong(3));
/* 618 */             oD.setLangID(iLanID);
/* 619 */             oD.setDescription(new String(StringUtilities.hexToBytes(rs.getString(4).trim()), "UTF-8"));
/* 620 */             oO.setDescription(oD);
/* 621 */             elems.add(oO);
/*     */           } 
/*     */         } finally {
/* 624 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 627 */         getStatementManager().releaseStatement(stmt);
/*     */       }
/*     */     
/* 630 */     } catch (Exception e) {
/* 631 */       log.error("Exception in getFootersForCheckList()", e);
/*     */     } 
/* 633 */     return elems;
/*     */   }
/*     */   
/*     */   public void addAttributesToCheckListElements(long lCheckID, int iLanID, Map oMap) {
/*     */     try {
/* 638 */       PreparedStatement stmt = getStatementManager().getStatement("select cle.ce_id, cev.at_order, cev.va_order, cev.at_fk, ass.tx_id, ass.tx_text, cev.va_fk, v.va_order as valorder, vs.tx_id, vs.tx_text from CL2Element cle, CE2Value cev, Attributes a, Strings  ass,\"Values\" v, Strings vs where cle.cl_fk=? and cev.ce_fk=cle.ce_id and a.at_id = cev.at_fk and ass.tx_id = a.tx_fk and ass.la_fk=? and v.va_id=cev.va_fk and vs.tx_id = v.tx_fk and (vs.la_fk=? OR vs.la_fk=0)order by cev.at_order,cev.va_order");
/*     */       try {
/* 640 */         stmt.setLong(1, lCheckID);
/*     */         
/* 642 */         stmt.setInt(2, iLanID);
/* 643 */         stmt.setInt(3, iLanID);
/*     */         
/* 645 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 647 */           while (rs.next()) {
/*     */             
/* 649 */             CLElement oE = (CLElement)oMap.get(Long.valueOf(rs.getLong(1)));
/* 650 */             if (oE == null) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 659 */             CE2value oV = new CE2value();
/* 660 */             oV.setAttrorder(rs.getInt(2));
/* 661 */             oV.setValueorder(rs.getInt(3));
/* 662 */             Attribute oA = new Attribute();
/* 663 */             oA.setID(rs.getLong(4));
/* 664 */             Description oD = new Description();
/* 665 */             oD.setID(rs.getLong(5));
/* 666 */             oD.setLangID(iLanID);
/* 667 */             oD.setDescription(new String(StringUtilities.hexToBytes(rs.getString(6).trim()), "UTF-8"));
/* 668 */             oA.setDescription(oD);
/* 669 */             oV.setAttribute(oA);
/* 670 */             Value oVa = new Value();
/* 671 */             oVa.setID(rs.getLong(7));
/* 672 */             oVa.setAttributeID(oA.getID());
/* 673 */             oVa.setOrder(rs.getInt(8));
/* 674 */             oD = new Description();
/* 675 */             oD.setID(rs.getLong(9));
/* 676 */             oD.setLangID(iLanID);
/* 677 */             oD.setDescription(new String(StringUtilities.hexToBytes(rs.getString(10).trim()), "UTF-8"));
/* 678 */             oVa.setDescription(oD);
/* 679 */             oV.setValue(oVa);
/* 680 */             if (oE.getAttributes() == null) {
/* 681 */               oE.setAttributes(new LinkedList());
/*     */             }
/* 683 */             oE.getAttributes().add(oV);
/*     */           } 
/*     */         } finally {
/* 686 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 689 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 691 */     } catch (Exception e) {
/* 692 */       log.error("Exception in addAttributesToCheckList", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFootersToCheckListElements(long lCheckID, int iLanID, Map oMap) {
/*     */     try {
/* 699 */       PreparedStatement stmt = getStatementManager().getStatement("select aa.ce_id, c.fn_id, c.fn_order, b.tx_id, b.tx_text from CL2Element aa, CE2Footnote a, Footnotes c, Strings b where aa.cl_fk=? and a.ce_fk=aa.ce_id and c.fn_id = a.fn_fk and b.tx_id =c.tx_fk and b.la_fk=? order by c.fn_order");
/*     */       try {
/* 701 */         stmt.setLong(1, lCheckID);
/*     */         
/* 703 */         stmt.setInt(2, iLanID);
/*     */         
/* 705 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 707 */           while (rs.next()) {
/* 708 */             CLElement oE = (CLElement)oMap.get(Long.valueOf(rs.getLong(1)));
/* 709 */             if (oE == null) {
/*     */               continue;
/*     */             }
/* 712 */             Footnote oO = new Footnote();
/* 713 */             oO.setID(rs.getLong(2));
/* 714 */             oO.setOrder(rs.getInt(3));
/* 715 */             Description oD = new Description();
/* 716 */             oD.setID(rs.getLong(4));
/* 717 */             oD.setLangID(iLanID);
/* 718 */             oD.setDescription(new String(StringUtilities.hexToBytes(rs.getString(5).trim()), "UTF-8"));
/* 719 */             oO.setDescription(oD);
/* 720 */             if (oE.getFootnode() == null) {
/* 721 */               oE.setFootnode(new LinkedList());
/*     */             }
/* 723 */             oE.getFootnode().add(oO);
/*     */           } 
/*     */         } finally {
/* 726 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 729 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 731 */     } catch (Exception e) {
/* 732 */       log.error("Exception in addFootersToCheckListElement()", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private IStatementManager getStatementManager() {
/* 737 */     return this.stmtManager;
/*     */   }
/*     */   
/*     */   CheckList getCheckList(long lID, int iLanID) {
/* 741 */     CheckList oL = new CheckList(lID);
/* 742 */     List<CLElement> elems = new LinkedList();
/* 743 */     oL.setElements(elems);
/* 744 */     oL.setFootnotes(getFootersForCheckList(lID, iLanID));
/*     */     try {
/* 746 */       PreparedStatement stmt = getStatementManager().getStatement("select a.ce_id, b.tx_id, b.tx_text, a.ai_flag, a.ec_flag, e.el_sp_link, a.cm_fk, a.el_order from Strings b, Elements e, CL2Element a  where  a.cl_fk=? and b.tx_id = a.tx_fk and b.la_fk=? and a.el_fk = e.el_id order by a.el_order");
/*     */       try {
/* 748 */         stmt.setLong(1, lID);
/*     */         
/* 750 */         stmt.setInt(2, iLanID);
/* 751 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 753 */           while (rs.next()) {
/*     */             
/* 755 */             CLElement oO = new CLElement();
/* 756 */             oO.setID(rs.getLong(1));
/*     */             
/* 758 */             Description oD = new Description();
/* 759 */             oD.setID(rs.getLong(2));
/* 760 */             oD.setLangID(iLanID);
/* 761 */             oD.setDescription(new String(StringUtilities.hexToBytes(rs.getString(3).trim()), "UTF-8"));
/* 762 */             oO.setDescription(oD);
/* 763 */             oO.setAi_flag(rs.getBoolean(4));
/* 764 */             oO.setEc_flag(rs.getBoolean(5));
/* 765 */             oO.setSpLink(rs.getString(6));
/*     */             
/* 767 */             if (rs.getLong(7) != 0L) {
/* 768 */               oD = new Description();
/* 769 */               oD.setID(rs.getLong(7));
/* 770 */               oD.setLangID(iLanID);
/* 771 */               String comment = this.cache.getComment(Integer.valueOf((int)oD.getID()), iLanID);
/* 772 */               if (comment != null) {
/* 773 */                 oD.setDescription(comment);
/*     */               }
/* 775 */               oO.setComment(oD);
/*     */             } 
/* 777 */             elems.add(oO);
/*     */           } 
/*     */         } finally {
/* 780 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/* 783 */         getStatementManager().releaseStatement(stmt);
/*     */       } 
/* 785 */     } catch (Exception e) {
/* 786 */       log.error("Exception in getCheckList()", e);
/*     */     } 
/*     */     
/* 789 */     if (log.isDebugEnabled() && elems.size() == 0) {
/* 790 */       log.debug("No Elements found for Checklist ID=" + lID);
/*     */     }
/* 792 */     Map oEmap = BuildID2ElementMap(elems);
/* 793 */     addFootersToCheckListElements(lID, iLanID, oEmap);
/* 794 */     addAttributesToCheckListElements(lID, iLanID, oEmap);
/* 795 */     return oL;
/*     */   }
/*     */   
/*     */   protected Map BuildID2ElementMap(List elements) {
/* 799 */     HashMap<Object, Object> oMap = new HashMap<Object, Object>();
/* 800 */     for (Iterator<CLElement> it = elements.iterator(); it.hasNext(); ) {
/*     */       
/* 802 */       CLElement e = it.next();
/* 803 */       oMap.put(Long.valueOf(e.getID()), e);
/*     */     } 
/* 805 */     return oMap;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\db\ICLStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */