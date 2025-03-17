/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.CTOCReference;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProxy;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.IStatementManager;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.jdbc.StatementManagerV2;
/*     */ import com.eoos.scsm.v2.objectpool.ListPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class CTOCStore extends CTOCStore {
/*  46 */   protected static Logger log = Logger.getLogger(CTOCStore.class);
/*     */   
/*     */   public static final int ROOT_INDICATOR = -1;
/*     */   
/*     */   public static final String DELIMITER = ",";
/*     */   
/*     */   public static final String TECHNICAL_SERVICE_BULLETINS = "SIT-12";
/*     */   
/*     */   protected DBMS dbms;
/*     */   
/*     */   protected SI si;
/*     */   
/*     */   protected CTOCLabelCache labels;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private CachedRetrievalSupportV3 contentRetrieval;
/*     */   
/*     */   private CachedRetrievalSupportV3 propertiesSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 childrenSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 electronicSystemSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 nodeSupport;
/*     */   
/*     */   protected CTOCRootElement tsb;
/*     */   
/*     */   private ILVCAdapter lvcAdapter;
/*     */   
/*     */   private IStatementManager stmtManager;
/*     */   
/*     */   public CTOCStore(final DBMS dbms, CTOCDomain domain, Map factories, ILVCAdapter adapter) throws Exception {
/*  79 */     this.dbms = dbms;
/*  80 */     this.lvcAdapter = adapter;
/*  81 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  84 */             dbms.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/*  89 */               return dbms.requestConnection();
/*  90 */             } catch (Exception e) {
/*  91 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  60000L);
/*     */ 
/*     */     
/*  97 */     this.contentRetrieval = new CachedRetrievalSupportV3("CONTENT", this.connectionProvider, Tis2webUtil.createStdCache());
/*  98 */     this.propertiesSupport = new CachedRetrievalSupportV3("CTOC-PROPERTIES", this.connectionProvider, Tis2webUtil.createStdCache());
/*  99 */     this.childrenSupport = new CachedRetrievalSupportV3("CTOC-CHILDREN", this.connectionProvider, Tis2webUtil.createStdCache());
/* 100 */     this.electronicSystemSupport = new CachedRetrievalSupportV3("ELECTRONIC-SYSTEM", this.connectionProvider, Tis2webUtil.createStdCache());
/* 101 */     this.nodeSupport = new CachedRetrievalSupportV3("NODES", this.connectionProvider, Tis2webUtil.createStdCache());
/* 102 */     this.stmtManager = (IStatementManager)new StatementManagerV2(this.connectionProvider);
/*     */     
/* 104 */     this.factories = factories;
/* 105 */     this.ctocs = new HashMap<Object, Object>();
/* 106 */     this.tsb = new CTOCRootElement(-1 * CTOCDomain.TSB.ord(), CTOCType.CTOC.ord(), true, false, null, adapter);
/* 107 */     loadCTOCs(domain);
/* 108 */     if (domain.equals(CTOCDomain.SI)) {
/* 109 */       loadCTOC4SIT();
/*     */       try {
/* 111 */         loadCTOC4AG();
/* 112 */       } catch (Exception x) {
/* 113 */         log.info("ag-ctoc not available");
/*     */       } 
/*     */       try {
/* 116 */         loadCTOC4SCDS2GT();
/* 117 */       } catch (Exception x) {
/* 118 */         log.info("scds2gt-ctoc not available");
/*     */       } 
/* 120 */       loadCTOC4SCT();
/* 121 */       makeCTOC4Complaints();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Connection getReadOnlyConnection() throws Exception {
/* 126 */     Connection ret = this.connectionProvider.getConnection();
/* 127 */     if (!ret.isReadOnly()) {
/* 128 */       ret.setReadOnly(true);
/*     */     }
/* 130 */     if (!ret.getAutoCommit()) {
/* 131 */       ret.setAutoCommit(false);
/*     */     }
/* 133 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 137 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */ 
/*     */   
/*     */   public void release(Connection conn, Statement stmt, ResultSet rs) {
/*     */     try {
/* 143 */       if (rs != null) {
/* 144 */         rs.close();
/*     */       }
/* 146 */     } catch (Exception x) {
/* 147 */       log.error("failed to close result set", x);
/*     */     } 
/*     */     
/*     */     try {
/* 151 */       if (stmt != null) {
/* 152 */         stmt.close();
/*     */       }
/* 154 */     } catch (Exception x) {
/* 155 */       log.error("failed to close statement", x);
/*     */     } 
/*     */     
/*     */     try {
/* 159 */       if (conn != null) {
/* 160 */         releaseConnection(conn);
/*     */       }
/* 162 */     } catch (Exception x) {
/* 163 */       log.error("failed to release connection", x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected VCR makeVCR(int vcrID) {
/* 169 */     return getLVCAdapter().makeVCR(vcrID);
/*     */   }
/*     */   
/*     */   synchronized Map<Integer, VCR> getVCRs(Collection ids) {
/* 173 */     return getLVCAdapter().getVCRs(ids);
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale, Integer labelID) {
/* 177 */     return this.labels.getLabel(labelID, locale);
/*     */   }
/*     */   
/*     */   protected synchronized void loadCTOCs(CTOCDomain domain) throws Exception {
/* 181 */     this.labels = new CTOCLabelCache(new CTOCLabelCache.ConnectionCallback()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/* 184 */             CTOCStore.this.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getReadOnlyConnection() throws Exception {
/* 188 */             return CTOCStore.this.getReadOnlyConnection();
/*     */           }
/*     */         },  this.dbms);
/*     */     
/* 192 */     loadRoots(domain);
/* 193 */     loadCTOC4NTF();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SITOCElement createContent(Integer parentID, ResultSet rs, boolean loadVCRs) throws Exception {
/* 198 */     return createContent(parentID, -1, rs, loadVCRs);
/*     */   }
/*     */   
/*     */   protected SITOCElement createContent(Integer parentID, int contentID, ResultSet rs, boolean loadVCRs) throws Exception {
/* 202 */     int contentType = rs.getInt("CONTENT_TYPE");
/* 203 */     if (contentID == -1) {
/* 204 */       contentID = rs.getInt("CONTENT_ID");
/*     */     }
/* 206 */     int order = rs.getInt("CONTENT_ORDER");
/* 207 */     long properties = rs.getLong("CONTENT_PROPERTY");
/* 208 */     int vcrID = rs.getInt("CONTENT_VCR");
/* 209 */     VCR vcr = loadVCRs ? makeVCR(vcrID) : null;
/* 210 */     CTOCType ctocType = CTOCType.get(contentType);
/* 211 */     boolean proxy = false;
/* 212 */     if (contentType == CTOCType.WIS.ord()) {
/* 213 */       ctocType = CTOCType.SI;
/* 214 */       proxy = true;
/* 215 */     } else if (contentType == CTOCType.WIS_ECM.ord()) {
/* 216 */       ctocType = CTOCType.SI;
/* 217 */     } else if (contentType == CTOCType.WIS_DTC.ord()) {
/* 218 */       ctocType = CTOCType.SI;
/*     */     } 
/* 220 */     CTOCFactory factory = (CTOCFactory)this.factories.get(ctocType);
/* 221 */     if (factory == null) {
/* 222 */       log.error("no factory available (content-id='" + contentID + "','" + ctocType + "').");
/* 223 */       throw new Exception("no ctoc content factory available: " + ctocType);
/*     */     } 
/* 225 */     SITOCElement sio = factory.make(ctocType, contentID, order, properties, vcr);
/* 226 */     if (proxy) {
/* 227 */       return (SITOCElement)new SIOProxy((SIOElement)sio, order);
/*     */     }
/*     */     
/* 230 */     return sio;
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/* 234 */     return this.lvcAdapter;
/*     */   }
/*     */   
/*     */   protected CTOCElementImpl createElement(ResultSet rs, boolean loadVCR) throws Exception {
/* 238 */     int tocID = rs.getInt("TOC_ID");
/* 239 */     Integer labelID = Integer.valueOf(rs.getInt("TOC_LABEL"));
/* 240 */     int order = rs.getInt("TOC_ORDER");
/* 241 */     int tocType = rs.getInt("TOC_TYPE");
/* 242 */     boolean hasChildren = rs.getBoolean("TOC_CHILDREN");
/* 243 */     boolean hasContent = rs.getBoolean("TOC_CONTENT");
/* 244 */     int vcrID = rs.getInt("TOC_VCR");
/* 245 */     VCR vcr = loadVCR ? makeVCR(vcrID) : null;
/* 246 */     if (tocType == CTOCType.MajorOperation.ord()) {
/* 247 */       CTOCFactory factory = (CTOCFactory)this.factories.get(CTOCType.MajorOperation);
/* 248 */       if (factory == null) {
/* 249 */         log.error("no factory available (content-id='" + tocID + "','" + CTOCType.MajorOperation + "').");
/* 250 */         throw new Exception("no ctoc content factory available: " + CTOCType.MajorOperation);
/*     */       } 
/* 252 */       return (CTOCElementImpl)factory.make(CTOCType.MajorOperation, tocID, order, labelID.intValue(), vcr);
/* 253 */     }  if (tocType == CTOCType.SI.ord()) {
/* 254 */       CTOCFactory factory = (CTOCFactory)this.factories.get(CTOCType.SI);
/* 255 */       if (factory == null) {
/* 256 */         log.error("no factory available (content-id='" + tocID + "','" + CTOCType.SI + "').");
/* 257 */         throw new Exception("no ctoc content factory available: " + CTOCType.SI);
/*     */       } 
/* 259 */       return new CTOCElementImpl(tocID, labelID, order, tocType, hasChildren, hasContent, vcr, getLVCAdapter());
/*     */     } 
/* 261 */     return new CTOCElementImpl(tocID, labelID, order, tocType, hasChildren, hasContent, vcr, getLVCAdapter());
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void loadRoots(CTOCDomain domain) throws Exception {
/* 266 */     PreparedStatement stmt = this.stmtManager.getStatement(this.dbms.getSQL("SELECT TOC_ID, TOC_TYPE, TOC_CHILDREN, TOC_CONTENT, TOC_VCR FROM TOC WHERE TOC_PARENT = ?"));
/*     */     
/*     */     try {
/* 269 */       stmt.setInt(1, domain.ord() * -1);
/* 270 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 272 */         while (rs.next()) {
/* 273 */           int tocID = rs.getInt("TOC_ID");
/* 274 */           int tocType = rs.getInt("TOC_TYPE");
/* 275 */           boolean hasChildren = rs.getBoolean("TOC_CHILDREN");
/* 276 */           boolean hasContent = rs.getBoolean("TOC_CONTENT");
/* 277 */           int vcrID = rs.getInt("TOC_VCR");
/* 278 */           VCR vcr = makeVCR(vcrID);
/* 279 */           String key = (vcr == VCR.NULL) ? CTOCCache.makeKey(tocType) : CTOCCache.makeKey(CTOCDomain.SI.ord(), vcrID);
/* 280 */           CTOCRootElement root = new CTOCRootElement(tocID, tocType, hasChildren, hasContent, vcr, getLVCAdapter());
/* 281 */           CTOCCache ctoc = new CTOCCache(this, root);
/* 282 */           this.ctocs.put(key, ctoc);
/* 283 */           root.setCache(ctoc);
/*     */         } 
/*     */       } finally {
/* 286 */         JDBCUtil.close(rs, log);
/*     */       } 
/* 288 */       log.info("loaded " + this.ctocs.size() + " ctoc roots.");
/*     */     }
/* 290 */     catch (Exception e) {
/* 291 */       log.error("loading ctoc roots failed, exception:" + e, e);
/* 292 */       throw e;
/*     */     } finally {
/* 294 */       this.stmtManager.releaseStatement(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadCTOC4SIT() throws Exception {
/* 299 */     CTOCRootElement root = null;
/*     */     
/* 301 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 303 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL(DBMS.SELECT_SITROOT));
/*     */       try {
/* 305 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 307 */           if (rs.next()) {
/* 308 */             int tocID = rs.getInt("TOC_ID");
/* 309 */             int tocType = rs.getInt("TOC_TYPE");
/* 310 */             root = new CTOCRootElement(tocID, tocType, true, false, null, getLVCAdapter());
/*     */           } else {
/* 312 */             throw new Exception("sit-ctoc not available");
/*     */           } 
/*     */         } finally {
/* 315 */           JDBCUtil.close(rs, log);
/*     */         } 
/* 317 */       } catch (Exception e) {
/* 318 */         log.error("loading sit-ctoc failed.");
/* 319 */         throw e;
/*     */       } finally {
/* 321 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 324 */       releaseConnection(connection);
/*     */     } 
/*     */     
/* 327 */     CTOCCache ctoc = new CTOCCache(this, root);
/* 328 */     String key = CTOCCache.makeKey(CTOCDomain.SIT.ord());
/* 329 */     this.ctocs.put(key, ctoc);
/* 330 */     root.setCache(ctoc);
/* 331 */     loadChildrenSIT((CTOCElement)root);
/*     */   }
/*     */   
/*     */   protected void loadChildrenSIT(CTOCElement element) throws Exception {
/* 335 */     List<CTOCElement> children = element.getChildren();
/* 336 */     if (children == null) {
/*     */       return;
/*     */     }
/* 339 */     for (int i = 0; i < children.size(); i++) {
/* 340 */       loadChildrenSIT(children.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeCTOC4Complaints() throws Exception {
/*     */     try {
/* 348 */       String key = CTOCCache.makeKey(CTOCDomain.SIT.ord());
/* 349 */       CTOCCache cache = (CTOCCache)this.ctocs.get(key);
/* 350 */       List<CTOCElement> children = cache.getRoot().getChildren();
/* 351 */       for (int i = 0; i < children.size(); i++) {
/* 352 */         CTOCElement child = children.get(i);
/* 353 */         String sit = (String)child.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 354 */         if ("SIT-12".equalsIgnoreCase(sit)) {
/* 355 */           CTOCRootElement root = new CTOCRootElement(-1 * CTOCDomain.COMPLAINT.ord(), CTOCType.CTOC.ord(), true, false, null, getLVCAdapter());
/* 356 */           CTOCCache ctoc = new CTOCCache(this, root);
/* 357 */           key = CTOCCache.makeKey(CTOCDomain.COMPLAINT.ord());
/* 358 */           this.ctocs.put(key, ctoc);
/* 359 */           root.setCache(ctoc);
/* 360 */           root.shareChildren(child.getChildren());
/*     */           return;
/*     */         } 
/*     */       } 
/* 364 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadCTOC4AG() throws Exception {
/* 369 */     CTOCRootElement root = null;
/*     */     
/* 371 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 373 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL(DBMS.SELECT_SCDSROOT));
/*     */       try {
/* 375 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 377 */           if (rs.next()) {
/* 378 */             int tocID = rs.getInt("TOC_ID");
/* 379 */             int tocType = rs.getInt("TOC_TYPE");
/* 380 */             root = new CTOCRootElement(tocID, tocType, true, false, null, getLVCAdapter());
/*     */           } else {
/* 382 */             throw new Exception("ag-ctoc not available");
/*     */           } 
/*     */         } finally {
/* 385 */           JDBCUtil.close(rs, log);
/*     */         }
/*     */       
/* 388 */       } catch (Exception e) {
/* 389 */         log.error("loading ag-ctoc failed.");
/* 390 */         throw e;
/*     */       } finally {
/* 392 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 395 */       releaseConnection(connection);
/*     */     } 
/*     */     
/* 398 */     CTOCCache ctoc = new CTOCCache(this, root);
/* 399 */     String key = CTOCCache.makeKey(CTOCDomain.SCDS.ord());
/* 400 */     this.ctocs.put(key, ctoc);
/* 401 */     root.setCache(ctoc);
/* 402 */     root.getChildren();
/*     */   }
/*     */   
/*     */   public void loadCTOC4SCDS2GT() throws Exception {
/* 406 */     CTOCRootElement root = null;
/*     */     
/* 408 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 410 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL(DBMS.SELECT_SCDS2GTROOT));
/*     */       try {
/* 412 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 414 */           if (rs.next()) {
/*     */             
/* 416 */             int tocID = rs.getInt("TOC_ID");
/* 417 */             int tocType = rs.getInt("TOC_TYPE");
/* 418 */             root = new CTOCRootElement(tocID, tocType, true, false, null, getLVCAdapter());
/*     */           } else {
/* 420 */             throw new Exception("scds2gt-ctoc not available");
/*     */           } 
/*     */         } finally {
/* 423 */           JDBCUtil.close(rs, log);
/*     */         } 
/* 425 */       } catch (Exception e) {
/* 426 */         log.error("loading scds2gt-ctoc failed.");
/* 427 */         throw e;
/*     */       } finally {
/* 429 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 432 */       releaseConnection(connection);
/*     */     } 
/* 434 */     CTOCCache ctoc = new CTOCCache(this, root);
/* 435 */     String key = CTOCCache.makeKey(CTOCDomain.SCDS2GT.ord());
/* 436 */     this.ctocs.put(key, ctoc);
/* 437 */     root.setCache(ctoc);
/* 438 */     root.getChildren();
/* 439 */     log.info("scds2gt-ctoc mapping is available.");
/*     */   }
/*     */   
/*     */   public void loadCTOC4SCT() throws Exception {
/* 443 */     CTOCRootElement root = null;
/*     */     
/* 445 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 447 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL(DBMS.SELECT_SCTROOT));
/*     */       try {
/* 449 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 451 */           if (rs.next()) {
/* 452 */             int tocID = rs.getInt("TOC_ID");
/* 453 */             int tocType = rs.getInt("TOC_TYPE");
/* 454 */             root = new CTOCRootElement(tocID, tocType, true, false, null, getLVCAdapter());
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         } finally {
/* 459 */           JDBCUtil.close(rs, log);
/*     */         } 
/* 461 */       } catch (Exception e) {
/* 462 */         log.error("loading sct-ctoc failed.");
/* 463 */         throw e;
/*     */       } finally {
/* 465 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 468 */       releaseConnection(connection);
/*     */     } 
/* 470 */     CTOCCache ctoc = new CTOCCache(this, root);
/* 471 */     String key = CTOCCache.makeKey(CTOCDomain.WIS_SCT.ord());
/* 472 */     this.ctocs.put(key, ctoc);
/* 473 */     root.setCache(ctoc);
/* 474 */     root.getChildren();
/*     */   }
/*     */ 
/*     */   
/*     */   void loadChildren(CTOCElement element) {
/*     */     try {
/* 480 */       List children = getChildren(element.getID());
/* 481 */       for (Iterator<SITOCElement> iter = children.iterator(); iter.hasNext();) {
/* 482 */         element.add(iter.next());
/*     */       }
/* 484 */     } catch (Exception e) {
/* 485 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getChildren(Integer id) {
/*     */     try {
/* 494 */       List children = (List)this.childrenSupport.getObjects(Collections.singleton(id), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 497 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */ 
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 505 */               List<CTOCElementImpl> ret = new LinkedList();
/*     */               while (true) {
/* 507 */                 CTOCElementImpl node = CTOCStore.this.createElement(rs, true);
/* 508 */                 ret.add(node);
/* 509 */                 if (!rs.next())
/* 510 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 514 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 518 */               return CTOCStore.this.dbms.getSQL("SELECT TOC_ID,TOC_LABEL,TOC_ORDER,TOC_TYPE,TOC_CHILDREN,TOC_CONTENT,TOC_VCR FROM TOC WHERE TOC_PARENT = ? ");
/*     */             }
/*     */           }).get(id);
/*     */ 
/*     */       
/* 523 */       return (children != null) ? children : Collections.EMPTY_LIST;
/*     */     }
/* 525 */     catch (Exception e) {
/* 526 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, Map> getProperties(Collection elementIDs) {
/*     */     try {
/* 533 */       return this.propertiesSupport.getObjects(elementIDs, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 536 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */ 
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 544 */               Map<Object, Object> ret = new HashMap<Object, Object>();
/*     */               while (true) {
/* 546 */                 int propertyType = rs.getInt("PROPERTY_TYPE");
/* 547 */                 CTOCProperty property = CTOCProperty.get(propertyType);
/* 548 */                 String value = Util.toThreadLocalMultiton(Util.trim(rs.getString("PROPERTY")));
/* 549 */                 ret.put(property, value);
/* 550 */                 if (!rs.next())
/* 551 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 555 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 559 */               return CTOCStore.this.dbms.getSQL("SELECT PROPERTY_TYPE,PROPERTY FROM TOC_PROPERTY WHERE TOC_ID = ? ");
/*     */             }
/*     */           });
/*     */     }
/* 563 */     catch (Exception e) {
/* 564 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map getProperties(CTOCElement element) {
/* 569 */     return getProperties(Collections.singleton(element.getID())).get(element.getID());
/*     */   }
/*     */   
/*     */   public void loadContent(CTOCElement element) {
/*     */     try {
/* 574 */       List<Integer> ids = ListPool.getThreadInstance().get();
/*     */       try {
/* 576 */         PreparedStatement stmt = this.stmtManager.getStatement("SELECT distinct CONTENT_ID FROM TOC_CONTENT WHERE TOC_ID = ?");
/*     */         try {
/* 578 */           stmt.setInt(1, element.getID().intValue());
/*     */           
/* 580 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 582 */             while (rs.next()) {
/* 583 */               ids.add(Integer.valueOf(rs.getInt(1)));
/*     */             }
/*     */           } finally {
/* 586 */             JDBCUtil.close(rs, log);
/*     */           }
/*     */         
/*     */         } finally {
/*     */           
/* 591 */           this.stmtManager.releaseStatement(stmt);
/*     */         } 
/*     */         
/* 594 */         Map<Integer, SITOCElement> resolved = loadContent(ids);
/* 595 */         for (Iterator<Integer> iter = ids.iterator(); iter.hasNext();) {
/* 596 */           element.add(resolved.get(iter.next()));
/*     */         }
/*     */       } finally {
/*     */         
/* 600 */         ListPool.getThreadInstance().free(ids);
/*     */       }
/*     */     
/* 603 */     } catch (Exception e) {
/* 604 */       log.error("loading ctoc content failed (toc-id='" + element.getID() + "'), rethrowing exception:" + e, e);
/* 605 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SITOCElement loadContent(Integer contentID) {
/* 610 */     return loadContent(Collections.singleton(contentID)).get(contentID);
/*     */   }
/*     */   
/*     */   public Map<Integer, SITOCElement> loadContent(Collection sios) {
/*     */     try {
/* 615 */       Map<Integer, SITOCElement> resolved = this.contentRetrieval.getObjects(sios, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 618 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public String getQuery() {
/* 623 */               return "SELECT CONTENT_TYPE,CONTENT_ORDER,CONTENT_PROPERTY,CONTENT_VCR FROM TOC_CONTENT WHERE CONTENT_ID=?";
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 630 */               return CTOCStore.this.createContent((Integer)null, ((Integer)identifier).intValue(), rs, true);
/*     */             }
/*     */             
/*     */             public Object createKey(Object identifier) {
/* 634 */               return identifier;
/*     */             }
/*     */           });
/*     */       
/* 638 */       return (resolved != null) ? resolved : Collections.EMPTY_MAP;
/* 639 */     } catch (Exception e) {
/* 640 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode loadNode(Integer ctocID) {
/*     */     try {
/* 647 */       Map nodes = this.nodeSupport.getObjects(Collections.singleton(ctocID), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 650 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 657 */               return CTOCStore.this.createElement(rs, true);
/*     */             }
/*     */ 
/*     */             
/*     */             public Object createKey(Object identifier) {
/* 662 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 666 */               return "SELECT * FROM TOC WHERE TOC_ID=?";
/*     */             }
/*     */           });
/*     */       
/* 670 */       return (nodes != null) ? (CTOCNode)nodes.get(ctocID) : null;
/* 671 */     } catch (Exception e) {
/* 672 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, CTOCNode> getNodes(Collection ids) {
/*     */     try {
/* 679 */       return this.nodeSupport.getObjects(ids, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 682 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 689 */               return CTOCStore.this.createElement(rs, true);
/*     */             }
/*     */ 
/*     */             
/*     */             public Object createKey(Object identifier) {
/* 694 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 698 */               return "SELECT * FROM TOC WHERE TOC_ID=?";
/*     */             }
/*     */           });
/* 701 */     } catch (Exception e) {
/* 702 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized List loadMajorOperations(String es) {
/* 707 */     return getElectronicSystemIDs(es);
/*     */   }
/*     */   
/*     */   public List getElectronicSystemIDs(String es) {
/*     */     try {
/* 712 */       return (List)this.electronicSystemSupport.getObjects(Collections.singleton(es), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 715 */               stmt.setString(2, (String)identifier);
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {
/* 720 */               stmt.setInt(1, CTOCReference.CTOC_ES.ord());
/*     */             }
/*     */ 
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 725 */               List<Integer> ret = new LinkedList();
/*     */               while (true) {
/* 727 */                 ret.add(Integer.valueOf(rs.getInt("TOC_ID")));
/*     */                 
/* 729 */                 if (!rs.next())
/* 730 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 734 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 738 */               return CTOCStore.this.dbms.getSQL("SELECT TOC_ID FROM TOC_REFERENCES WHERE REFERENCE_TYPE = ? AND REFERENCE_KEY=?");
/*     */             }
/*     */           }).get(es);
/* 741 */     } catch (Exception e) {
/* 742 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadCTOC4NTF() throws Exception {
/* 748 */     CTOCRootElement root = null;
/*     */     
/* 750 */     Connection connection = getReadOnlyConnection();
/*     */     try {
/* 752 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL(DBMS.SELECT_NTFROOT));
/*     */       try {
/* 754 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 756 */           if (rs.next()) {
/* 757 */             int tocID = rs.getInt("TOC_ID");
/* 758 */             int tocType = rs.getInt("TOC_TYPE");
/* 759 */             root = new CTOCRootElement(tocID, tocType, true, false, null, getLVCAdapter());
/*     */           } else {
/* 761 */             log.warn("ntf-ctoc not available.");
/*     */             return;
/*     */           } 
/*     */         } finally {
/* 765 */           JDBCUtil.close(rs, log);
/*     */         } 
/* 767 */       } catch (Exception e) {
/* 768 */         log.error("loading ntf-ctoc failed.");
/* 769 */         throw e;
/*     */       } finally {
/* 771 */         JDBCUtil.close(stmt, log);
/*     */       } 
/*     */     } finally {
/* 774 */       releaseConnection(connection);
/*     */     } 
/* 776 */     CTOCCache ctoc = new CTOCCache(this, root);
/* 777 */     String key = CTOCCache.makeKey(CTOCDomain.NTF.ord());
/* 778 */     this.ctocs.put(key, ctoc);
/* 779 */     root.setCache(ctoc);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getElectronicSystemID(String escode) {
/* 784 */     List<Integer> ids = getElectronicSystemIDs(escode);
/* 785 */     if (!Util.isNullOrEmpty(ids)) {
/* 786 */       return ids.get(0);
/*     */     }
/* 788 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/* 793 */     throw new IllegalArgumentException("not supported");
/*     */   }
/*     */   
/*     */   public List getCellLinks(String publicationID, int cellID) {
/* 797 */     List<Integer> links = new LinkedList();
/* 798 */     Connection conn = null;
/* 799 */     PreparedStatement stmt = null;
/* 800 */     ResultSet rs = null;
/*     */     try {
/* 802 */       conn = getReadOnlyConnection();
/* 803 */       stmt = conn.prepareStatement(this.dbms.getSQL("SELECT info_id, order_sequence FROM pbpsdsie WHERE pub_id=?  and cell_id=? order by order_sequence"));
/* 804 */       stmt.setInt(1, Integer.parseInt(publicationID));
/* 805 */       stmt.setInt(2, cellID);
/*     */       
/* 807 */       rs = stmt.executeQuery();
/* 808 */       while (rs.next()) {
/* 809 */         links.add(Integer.valueOf(rs.getInt("info_id")));
/*     */       }
/* 811 */     } catch (Exception e) {
/* 812 */       log.error("lookup cell links failed (pub-id='" + publicationID + ",'cell-id='" + cellID + "').");
/*     */     } finally {
/* 814 */       release(conn, stmt, rs);
/*     */     } 
/* 816 */     return (links.size() == 0) ? null : links;
/*     */   }
/*     */   
/*     */   public List getAssociatedCellLinks(String publicationID, int cellID) {
/* 820 */     List<Integer> links = new LinkedList();
/* 821 */     Connection conn = null;
/* 822 */     PreparedStatement stmt = null;
/* 823 */     ResultSet rs = null;
/*     */     try {
/* 825 */       conn = getReadOnlyConnection();
/* 826 */       stmt = conn.prepareStatement(this.dbms.getSQL("SELECT l.pub_id, l.info_id, l.order_sequence FROM pbpsdsie l, psdassoc p WHERE p.pub_id = ? and l.pub_id = p.assoc_id AND l.cell_id =? order by l.pub_id, l.order_sequence"));
/* 827 */       stmt.setInt(1, Integer.parseInt(publicationID));
/* 828 */       stmt.setInt(2, cellID);
/* 829 */       rs = stmt.executeQuery();
/* 830 */       while (rs.next()) {
/* 831 */         links.add(Integer.valueOf(rs.getInt("info_id")));
/*     */       }
/* 833 */     } catch (Exception e) {
/* 834 */       log.error("lookup associated cell links failed (pub-id='" + publicationID + ",'cell-id='" + cellID + "').");
/*     */     } finally {
/* 836 */       release(conn, stmt, rs);
/*     */     } 
/* 838 */     return (links.size() == 0) ? null : links;
/*     */   }
/*     */   
/*     */   public CTOCLabelCache getCTOCCacheLabel() {
/* 842 */     return this.labels;
/*     */   }
/*     */   
/*     */   public HashMap getCTOCs() {
/* 846 */     return this.ctocs;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\CTOCStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */