/*      */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*      */ 
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWAWFormula;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWFormula;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.AWHourFormula;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKElement;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKGruppen;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKQual;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKSchluessel;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTroublecode;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.OperatorCacheElement;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.domain.BLOBProperty;
/*      */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*      */ import com.eoos.jdbc.ConnectionProvider;
/*      */ import com.eoos.jdbc.IStatementManager;
/*      */ import com.eoos.jdbc.JDBCUtil;
/*      */ import com.eoos.jdbc.StatementManagerV2;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ public class LTStore
/*      */   implements ILTStore
/*      */ {
/*   49 */   protected static final Logger log = Logger.getLogger(LTStore.class);
/*      */   
/*      */   protected static final int IDS_AWHOUR = 2;
/*      */   protected DBMS dbms;
/*      */   protected DBVersionInformation version;
/*      */   protected Map SMC2MCMapAlgo;
/*      */   private ConnectionProvider connectionProvider;
/*      */   private StatementManagerV2 stmtManager;
/*      */   private CachedRetrievalSupportV3 crDocument;
/*      */   
/*      */   public DBVersionInformation getVersionInfo() {
/*   60 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LTStore(final DBMS dbms) throws Exception {
/*   70 */     this.dbms = dbms;
/*   71 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*      */         {
/*      */           public void releaseConnection(Connection connection) {
/*   74 */             dbms.releaseConnection(connection);
/*      */           }
/*      */           
/*      */           public Connection getConnection() {
/*      */             try {
/*   79 */               Connection connection = dbms.requestConnection();
/*   80 */               if (connection.getAutoCommit()) {
/*   81 */                 connection.setAutoCommit(false);
/*      */               }
/*   83 */               if (!connection.isReadOnly()) {
/*   84 */                 connection.setReadOnly(true);
/*      */               }
/*   86 */               return connection;
/*   87 */             } catch (Exception e) {
/*   88 */               throw new RuntimeException(e);
/*      */             } 
/*      */           }
/*      */         },  60000L);
/*      */ 
/*      */     
/*   94 */     this.stmtManager = new StatementManagerV2(this.connectionProvider);
/*   95 */     this.crDocument = new CachedRetrievalSupportV3("DOCUMENT", this.connectionProvider, Tis2webUtil.createStdCache());
/*      */     
/*   97 */     this.version = loadVersionInformation();
/*      */   }
/*      */   
/*      */   private IStatementManager getStatementManager() {
/*  101 */     return (IStatementManager)this.stmtManager;
/*      */   }
/*      */   
/*      */   protected DBVersionInformation loadVersionInformation() throws Exception {
/*  105 */     Connection con = this.connectionProvider.getConnection();
/*      */     try {
/*  107 */       PreparedStatement stmt = con.prepareStatement("SELECT * FROM RELEASE");
/*      */       try {
/*  109 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  111 */           if (rs.next()) {
/*  112 */             return new DBVersionInformation(rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*      */           }
/*  114 */           return null;
/*      */         } finally {
/*      */           
/*  117 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  119 */       } catch (Exception e) {
/*  120 */         log.error("loading version information failed.");
/*  121 */         throw e;
/*      */       } finally {
/*  123 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*      */     } finally {
/*  126 */       this.connectionProvider.releaseConnection(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Map loadLangMap() {
/*  133 */     HashMap<Object, Object> langMap = new HashMap<Object, Object>();
/*      */     
/*  135 */     Connection con = this.connectionProvider.getConnection();
/*      */     try {
/*  137 */       PreparedStatement stmt = con.prepareStatement(this.dbms.getSQL("SELECT DISTINCT wl.LanguageAcronym, b.lc FROM WinLanguages wl, WinLang2LC b WHERE wl.LCID = b.LCID"));
/*      */       try {
/*  139 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  141 */           while (rs.next()) {
/*  142 */             String tla = rs.getString(1).trim();
/*  143 */             Integer lc = Integer.valueOf(rs.getInt(2));
/*  144 */             LocaleInfo li = LocaleInfoProvider.getInstance().getLocaleTLA(tla);
/*  145 */             if (li == null || 
/*  146 */               tla.equals("PTB")) {
/*      */               continue;
/*      */             }
/*  149 */             langMap.put(tla, lc);
/*      */           } 
/*      */         } finally {
/*      */           
/*  153 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  155 */         return langMap;
/*      */       } finally {
/*      */         
/*  158 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*  160 */     } catch (Exception e) {
/*  161 */       throw new RuntimeException(e);
/*      */     } finally {
/*  163 */       this.connectionProvider.releaseConnection(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<String> getWinLanguages() {
/*  169 */     Set<String> winLanguages = new HashSet<String>();
/*      */     try {
/*  171 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT DISTINCT LanguageAcronym FROM WinLanguages"));
/*      */       try {
/*  173 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  175 */           while (rs.next()) {
/*  176 */             String lang = rs.getString(1).trim();
/*  177 */             winLanguages.add(lang);
/*      */           } 
/*      */         } finally {
/*  180 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  182 */         return winLanguages;
/*      */       } finally {
/*      */         
/*  185 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  187 */     } catch (Exception e) {
/*  188 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map loadSmcMap() {
/*  194 */     Map<Object, Object> theCache = new HashMap<Object, Object>();
/*      */     
/*  196 */     Connection connection = this.connectionProvider.getConnection();
/*      */     try {
/*  198 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL("SELECT smc, smd FROM SalesMakes"));
/*      */       try {
/*  200 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  202 */           while (rs.next()) {
/*  203 */             theCache.put(rs.getString(2).toLowerCase(Locale.ENGLISH), Integer.valueOf(rs.getInt(1)));
/*      */           }
/*      */         } finally {
/*  206 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  208 */         return theCache;
/*      */       } finally {
/*  210 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*  212 */     } catch (Exception e) {
/*  213 */       throw new RuntimeException(e);
/*      */     } finally {
/*  215 */       this.connectionProvider.releaseConnection(connection);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Map loadSmcMcMapWithoutAlgo(Connection connection) {
/*  222 */     Map<Object, Object> theCache = new HashMap<Object, Object>();
/*      */     
/*      */     try {
/*  225 */       PreparedStatement stmt = connection.prepareStatement(this.dbms.getSQL("SELECT smc, vd, mc FROM AWarbeitskataloge"));
/*      */       try {
/*  227 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  229 */           while (rs.next()) {
/*  230 */             theCache.put("" + rs.getInt(1) + rs.getString(2), Integer.valueOf(rs.getInt(3)));
/*      */           }
/*      */         } finally {
/*  233 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  235 */         return theCache;
/*      */       } finally {
/*  237 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*  239 */     } catch (Exception e) {
/*  240 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map loadSmcMcMap() {
/*  246 */     Map<Object, Object> theCache = new HashMap<Object, Object>();
/*  247 */     this.SMC2MCMapAlgo = new HashMap<Object, Object>();
/*      */     
/*  249 */     Connection con = this.connectionProvider.getConnection();
/*      */     try {
/*  251 */       PreparedStatement stmt = con.prepareStatement(this.dbms.getSQL("SELECT smc, vd, mc, algo_code FROM AWarbeitskataloge"));
/*      */       try {
/*  253 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  255 */           while (rs.next()) {
/*  256 */             theCache.put("" + rs.getInt(1) + rs.getString(2).toLowerCase(Locale.ENGLISH), Integer.valueOf(rs.getInt(3)));
/*  257 */             String algo_code = rs.getString(4);
/*  258 */             if (algo_code == null || algo_code.trim().length() == 0) {
/*  259 */               algo_code = "A";
/*      */             }
/*  261 */             this.SMC2MCMapAlgo.put(rs.getInt(1) + ":" + rs.getInt(3), algo_code.trim().toUpperCase());
/*      */           } 
/*      */         } finally {
/*  264 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  266 */         return theCache;
/*      */       } finally {
/*      */         
/*  269 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*  271 */     } catch (Exception e) {
/*  272 */       this.SMC2MCMapAlgo = null;
/*  273 */       return loadSmcMcMapWithoutAlgo(con);
/*      */     } finally {
/*      */       
/*  276 */       this.connectionProvider.releaseConnection(con);
/*      */     } 
/*      */   }
/*      */   
/*      */   private List getMainAWs(int mc, String nr, boolean sonder) {
/*  281 */     List<Integer> retList = new LinkedList();
/*      */     
/*  283 */     String query = null;
/*  284 */     if (sonder) {
/*  285 */       query = this.dbms.getSQL("SELECT mc, nr, sx, aw, change_flag FROM AWsonderschluessel WHERE mc=? and TRIM(nr)=?");
/*      */     } else {
/*  287 */       query = this.dbms.getSQL("SELECT mc, nr, '', aw, change_flag FROM AWarbeitswerte  WHERE mc=? and TRIM(nr)=?");
/*      */     } 
/*      */     
/*      */     try {
/*  291 */       PreparedStatement stmt = getStatementManager().getStatement(query);
/*      */       try {
/*  293 */         stmt.setInt(1, mc);
/*  294 */         stmt.setString(2, nr + "000");
/*      */         
/*  296 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  298 */           while (rs.next()) {
/*  299 */             retList.add(Integer.valueOf(rs.getInt(4)));
/*      */           }
/*      */         } finally {
/*  302 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  304 */         return retList;
/*      */       } finally {
/*      */         
/*  307 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  309 */     } catch (Exception e) {
/*  310 */       log.error("getMainAWs() - Exception " + e, e);
/*  311 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public Integer getW100000AW(int imc) {
/*  316 */     List retList = getMainAWs(imc, "W100000", false);
/*  317 */     if (retList != null && retList.size() > 0) {
/*  318 */       Iterator<Integer> it = retList.iterator();
/*  319 */       Integer i = it.next();
/*  320 */       return i;
/*      */     } 
/*  322 */     return Integer.valueOf(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public LTDataWork getMainWork(Integer lc, Integer smc, Integer mc, String workNo) {
/*  327 */     LTDataWork ret = null;
/*      */     
/*      */     try {
/*  330 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT b.nr, b.ab, h.it, h.ls, h.gf, h.change_flag, h.pos, h.algo_code  FROM AWbezeichnung b,AWhauptarbeiten h WHERE b.lc = ? AND TRIM(h.nr)=? AND b.mc=? AND TRIM(b.nr)=? AND h.mc=b.mc ORDER BY h.pos"));
/*      */       try {
/*  332 */         stmt.setInt(1, lc.intValue());
/*  333 */         stmt.setString(2, workNo + "000");
/*  334 */         stmt.setInt(3, mc.intValue());
/*  335 */         stmt.setString(4, workNo);
/*  336 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  338 */           while (rs.next()) {
/*  339 */             ret = new LTDataWork();
/*  340 */             ret.setMc(mc);
/*  341 */             ret.setNr(rs.getString(1));
/*  342 */             ret.setDescription(rs.getString(2));
/*  343 */             ret.setTasktype(rs.getInt(3));
/*  344 */             ret.setLaquerDegree(rs.getString(4));
/*  345 */             ret.setWarrantyFlag(rs.getInt(5));
/*  346 */             ret.setChangeFlag(rs.getBoolean(6));
/*  347 */             ret.setAlgoCode(rs.getString("algo_code"));
/*  348 */             String algoCode = ret.getAlgoCode();
/*  349 */             if (algoCode == null) {
/*  350 */               ret.setAlgoCode((String)this.SMC2MCMapAlgo.get(smc + ":" + mc));
/*      */             }
/*      */           } 
/*      */         } finally {
/*  354 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  356 */         return ret;
/*  357 */       } catch (Exception x11488) {
/*  358 */         return getMainWorkWithoutAlgo(lc, mc, workNo);
/*      */       } finally {
/*  360 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  362 */     } catch (Exception e) {
/*  363 */       log.error("getMainWork() - Exception " + e, e);
/*  364 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private LTDataWork getMainWorkWithoutAlgo(Integer lc, Integer mc, String workNo) {
/*  369 */     LTDataWork ret = null;
/*      */     
/*      */     try {
/*  372 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT b.nr, b.ab, h.it, h.ls, h.gf, h.change_flag, h.pos FROM AWbezeichnung b,AWhauptarbeiten h WHERE b.lc = ? AND TRIM(h.nr)=? AND b.mc=? AND TRIM(b.nr) =? AND h.mc=b.mc ORDER BY h.pos"));
/*      */       try {
/*  374 */         stmt.setInt(1, lc.intValue());
/*  375 */         stmt.setString(2, workNo + "000");
/*  376 */         stmt.setInt(3, mc.intValue());
/*  377 */         stmt.setString(4, workNo);
/*  378 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  380 */           while (rs.next()) {
/*  381 */             ret = new LTDataWork();
/*  382 */             ret.setMc(mc);
/*  383 */             ret.setNr(rs.getString(1));
/*  384 */             ret.setDescription(rs.getString(2));
/*  385 */             ret.setTasktype(rs.getInt(3));
/*  386 */             ret.setLaquerDegree(rs.getString(4));
/*  387 */             ret.setWarrantyFlag(rs.getInt(5));
/*  388 */             ret.setChangeFlag(rs.getBoolean(6));
/*      */           } 
/*      */         } finally {
/*  391 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  393 */         return ret;
/*      */       } finally {
/*      */         
/*  396 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  398 */     } catch (Exception e) {
/*  399 */       log.error("getMainWork() - Exception " + e, e);
/*  400 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public List getAddOnWorks(Integer langID, Integer modellcode, String mainWorkNo) {
/*  405 */     List<LTDataWork> ret = new LinkedList();
/*      */     
/*      */     try {
/*  408 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT b.nr,b.ab,z.it,z.change_flag,z.pos FROM AWbezeichnung b, AWzusatzarbeiten z WHERE b.lc=? AND b.mc=?  AND TRIM(z.ha) = ? AND z.mc= ? AND TRIM(b.nr) = ? || RTRIM(z.za) ORDER BY z.pos"));
/*      */       try {
/*  410 */         stmt.setInt(1, langID.intValue());
/*  411 */         stmt.setInt(2, modellcode.intValue());
/*  412 */         stmt.setString(3, mainWorkNo + "000");
/*  413 */         stmt.setInt(4, modellcode.intValue());
/*  414 */         stmt.setString(5, mainWorkNo);
/*      */         
/*  416 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  418 */           while (rs.next()) {
/*  419 */             LTDataWork dw = new LTDataWork();
/*  420 */             dw.setNr(rs.getString(1));
/*  421 */             dw.setDescription(rs.getString(2));
/*  422 */             dw.setTasktype(rs.getInt(3));
/*  423 */             dw.setChangeFlag(rs.getBoolean(4));
/*  424 */             dw.setMc(modellcode);
/*  425 */             ret.add(dw);
/*      */           } 
/*      */         } finally {
/*  428 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*      */         
/*  432 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  434 */     } catch (Exception e) {
/*  435 */       log.error("getAddOnWorks() - Exception " + e);
/*      */     } 
/*      */ 
/*      */     
/*  439 */     return ret;
/*      */   }
/*      */   
/*      */   public boolean fillMainworkMultiText(Integer langID, LTDataWork ltwork) {
/*  443 */     boolean bRet = true;
/*  444 */     LinkedList textList = new LinkedList();
/*  445 */     ltwork.setTextList(textList);
/*      */     
/*      */     try {
/*  448 */       for (int i = 0; i < 2; i++) {
/*  449 */         String query = null;
/*  450 */         if (i == 0) {
/*  451 */           query = this.dbms.getSQL("SELECT ad FROM AWbeschreibung WHERE lc=? and mc=? and TRIM(nr) = ?");
/*      */         } else {
/*  453 */           query = this.dbms.getSQL("SELECT at FROM AWtexte WHERE lc=? AND mc=? AND TRIM(nr) = ?");
/*      */         } 
/*      */         
/*  456 */         PreparedStatement stmt = getStatementManager().getStatement(query);
/*      */         try {
/*  458 */           stmt.setInt(1, langID.intValue());
/*  459 */           stmt.setInt(2, ltwork.getMc().intValue());
/*  460 */           stmt.setString(3, ltwork.getNr() + "000");
/*  461 */           ResultSet rs = stmt.executeQuery();
/*      */           try {
/*  463 */             while (rs.next()) {
/*  464 */               ltwork.getTextList().add(rs.getString(1));
/*      */             }
/*      */           } finally {
/*  467 */             JDBCUtil.close(rs, log);
/*      */           } 
/*      */         } finally {
/*  470 */           getStatementManager().releaseStatement(stmt);
/*      */         } 
/*      */       } 
/*      */       
/*  474 */       return bRet;
/*  475 */     } catch (Exception e) {
/*  476 */       log.error("fillMainworkMultiText() - Exception " + e);
/*  477 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean fillAddonworkMultiText(Integer langID, LTDataWork ltwork) {
/*  484 */     boolean bRet = true;
/*  485 */     LinkedList textList = new LinkedList();
/*  486 */     ltwork.setTextList(textList);
/*      */     
/*      */     try {
/*  489 */       for (int i = 0; i < 2; i++) {
/*  490 */         String query = null;
/*  491 */         if (i == 0) {
/*  492 */           query = this.dbms.getSQL("SELECT ad FROM AWbeschreibung WHERE lc=? and mc=? and TRIM(nr) = ? ");
/*      */         } else {
/*  494 */           query = this.dbms.getSQL("SELECT at FROM AWtexte WHERE lc=? and mc=? and TRIM(nr) = ? ");
/*      */         } 
/*      */         
/*  497 */         PreparedStatement stmt = getStatementManager().getStatement(query);
/*      */         try {
/*  499 */           stmt.setInt(1, langID.intValue());
/*  500 */           stmt.setInt(2, ltwork.getMc().intValue());
/*  501 */           stmt.setString(3, ltwork.getNr());
/*      */           
/*  503 */           ResultSet rs = stmt.executeQuery();
/*      */           try {
/*  505 */             while (rs.next()) {
/*  506 */               ltwork.getTextList().add(rs.getString(1));
/*      */             }
/*      */           } finally {
/*  509 */             JDBCUtil.close(rs, log);
/*      */           } 
/*      */         } finally {
/*      */           
/*  513 */           getStatementManager().releaseStatement(stmt);
/*      */         } 
/*      */       } 
/*      */       
/*  517 */       return bRet;
/*  518 */     } catch (Exception e) {
/*  519 */       log.error("fillAddonworkMultiText() - Exception " + e);
/*  520 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean fillMainworkTC(Integer langID, LTDataWork ltwork) {
/*  527 */     boolean bRet = true;
/*  528 */     LinkedList<LTTroublecode> tcList = new LinkedList();
/*  529 */     ltwork.setTcList(tcList);
/*      */     
/*      */     try {
/*  532 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT t.tc, w.td FROM AWtcliste t, AWtcwerte w WHERE t.mc=? and TRIM(t.nr)=? || '000' and w.tc = t.tc and w.lc = ?"));
/*      */       try {
/*  534 */         stmt.setInt(1, ltwork.getMc().intValue());
/*  535 */         stmt.setString(2, ltwork.getNr());
/*  536 */         stmt.setInt(3, langID.intValue());
/*  537 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  539 */           LTTroublecode curtc = null;
/*  540 */           while (rs.next()) {
/*  541 */             curtc = new LTTroublecode();
/*  542 */             curtc.setTroubleCode(rs.getString(1));
/*  543 */             curtc.setTroubleCodeText(rs.getString(2));
/*  544 */             tcList.add(curtc);
/*      */           } 
/*      */         } finally {
/*  547 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  549 */         return bRet;
/*      */       } finally {
/*  551 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  553 */     } catch (Exception e) {
/*  554 */       log.error("fillMainworkTC() - Exception " + e, e);
/*  555 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean fillMainworkSXAW(Integer langID, LTDataWork ltwork, Map schluesselCache) {
/*  560 */     boolean bRet = true;
/*  561 */     List<LTSXAWData> sxawList = new LinkedList();
/*  562 */     boolean bNeedsValidation = false;
/*      */     
/*  564 */     final Connection connection = this.connectionProvider.getConnection();
/*      */     try {
/*  566 */       StatementManagerV2 statementManagerV2 = new StatementManagerV2(new ConnectionProvider()
/*      */           {
/*      */             public void releaseConnection(Connection connection) {}
/*      */ 
/*      */             
/*      */             public Connection getConnection() {
/*  572 */               return connection;
/*      */             }
/*      */           });
/*      */       
/*      */       try {
/*  577 */         PreparedStatement stmt1 = statementManagerV2.getStatement(this.dbms.getSQL("SELECT mc, nr, '', aw, change_flag FROM AWarbeitswerte WHERE mc=? and TRIM(nr)=?"));
/*      */         try {
/*  579 */           stmt1.setInt(1, ltwork.getMc().intValue());
/*  580 */           stmt1.setString(2, ltwork.getNr() + "000");
/*  581 */           ResultSet rs1 = stmt1.executeQuery();
/*      */           try {
/*  583 */             LTSXAWData sxawdata = null;
/*  584 */             String x = "";
/*  585 */             boolean cflag = false;
/*      */             
/*  587 */             int iIdx = 0;
/*  588 */             while (rs1.next()) {
/*  589 */               String awstr = null;
/*  590 */               x = rs1.getString(3);
/*  591 */               Integer aw = Integer.valueOf(rs1.getInt(4));
/*  592 */               cflag = rs1.getBoolean(5);
/*  593 */               if (aw.intValue() != 0) {
/*  594 */                 if (aw.intValue() < 0) {
/*  595 */                   PreparedStatement stmt2 = statementManagerV2.getStatement(this.dbms.getSQL("SELECT ax FROM AWwerte WHERE lc=? and aw = ? "));
/*      */                   try {
/*  597 */                     stmt2.setInt(1, langID.intValue());
/*  598 */                     stmt2.setInt(2, aw.intValue());
/*  599 */                     ResultSet rs2 = stmt2.executeQuery();
/*      */                     try {
/*  601 */                       if (rs2.next()) {
/*  602 */                         awstr = rs2.getString(1);
/*      */                       }
/*      */                     } finally {
/*  605 */                       JDBCUtil.close(rs2, log);
/*      */                     } 
/*      */                   } finally {
/*      */                     
/*  609 */                     statementManagerV2.releaseStatement(stmt2);
/*      */                   } 
/*      */                 } else {
/*  612 */                   awstr = aw.toString();
/*      */                 } 
/*      */                 
/*  615 */                 sxawdata = new LTSXAWData();
/*  616 */                 sxawList.add(sxawdata);
/*  617 */                 sxawdata.setInternalID(iIdx++);
/*  618 */                 sxawdata.setSxnr("");
/*  619 */                 sxawdata.setSx(x);
/*  620 */                 sxawdata.setAw(awstr);
/*  621 */                 sxawdata.setChange_flag(cflag);
/*      */               } 
/*      */             } 
/*  624 */             List awkSchluesselList = buildAWKSchluesselList((IStatementManager)statementManagerV2, langID, ltwork.getMc(), ltwork.getNr(), schluesselCache);
/*  625 */             if (awkSchluesselList.size() > 0 || sxawList.size() > 0)
/*  626 */               ltwork.setTasktype(5); 
/*  627 */             LTAWKSchluessel curSchl = null;
/*  628 */             Iterator<LTAWKSchluessel> it = awkSchluesselList.iterator();
/*  629 */             while (it.hasNext()) {
/*      */               
/*  631 */               bNeedsValidation = true;
/*      */               
/*  633 */               curSchl = it.next();
/*  634 */               LTSXAWData newsxaw = new LTSXAWData();
/*  635 */               newsxaw.setInternalID(iIdx++);
/*  636 */               newsxaw.setSx(curSchl.getAWText(langID));
/*  637 */               newsxaw.setSxnr(curSchl.getSx());
/*  638 */               newsxaw.setAw(curSchl.getAw());
/*  639 */               newsxaw.setChange_flag(curSchl.isChange_flag());
/*  640 */               newsxaw.setAWKSchluessel(curSchl);
/*  641 */               sxawList.add(newsxaw);
/*      */             } 
/*      */           } finally {
/*      */             
/*  645 */             JDBCUtil.close(rs1, log);
/*      */           } 
/*      */         } finally {
/*      */           
/*  649 */           statementManagerV2.releaseStatement(stmt1);
/*      */         } 
/*  651 */       } catch (Exception e) {
/*  652 */         log.error("fillMainworkSXAW() - Exception " + e);
/*  653 */         bRet = false;
/*      */       } 
/*      */       
/*  656 */       ltwork.setSXAWList(sxawList, bNeedsValidation);
/*  657 */       return bRet;
/*      */     } finally {
/*  659 */       this.connectionProvider.releaseConnection(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   private List buildAWKSchluesselList(IStatementManager stmtManager, Integer langID, Integer mc, String workno, Map<String, LTAWKSchluessel> schluesselCache) {
/*  664 */     List<LTAWKSchluessel> ret = null;
/*  665 */     List<LTAWKSchluessel> schluessellist = new LinkedList();
/*  666 */     List<LTAWKSchluessel> notFoundInCache = new LinkedList();
/*  667 */     LTAWKSchluessel keyFromCache = null;
/*      */     
/*  669 */     String strLangLookupKey = langID.toString();
/*      */     
/*      */     try {
/*  672 */       PreparedStatement stmt = stmtManager.getStatement("SELECT mc, nr, sx, aw, change_flag FROM AWsonderschluessel WHERE mc=? and TRIM(nr)= ?");
/*      */       try {
/*  674 */         stmt.setInt(1, mc.intValue());
/*  675 */         if (workno.length() == 7) {
/*  676 */           stmt.setString(2, workno + "000");
/*      */         } else {
/*  678 */           stmt.setString(2, workno);
/*      */         } 
/*  680 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  682 */           while (rs.next()) {
/*  683 */             LTAWKSchluessel key = new LTAWKSchluessel();
/*  684 */             key.setNr(workno);
/*  685 */             key.setChange_flag(rs.getBoolean(5));
/*      */             
/*  687 */             String val = rs.getString(3);
/*  688 */             key.setSx(val);
/*  689 */             int aw = rs.getInt(4);
/*  690 */             if (aw < 0) {
/*  691 */               PreparedStatement stmt2 = stmtManager.getStatement(this.dbms.getSQL("SELECT ax FROM AWwerte WHERE lc=? and aw = ? "));
/*      */               try {
/*  693 */                 stmt2.setInt(1, langID.intValue());
/*  694 */                 stmt2.setInt(2, aw);
/*  695 */                 ResultSet rs2 = stmt2.executeQuery();
/*      */                 try {
/*  697 */                   if (rs2.next()) {
/*  698 */                     val = rs2.getString(1);
/*      */                   }
/*      */                 } finally {
/*  701 */                   JDBCUtil.close(rs2, log);
/*      */                 } 
/*      */               } finally {
/*      */                 
/*  705 */                 stmtManager.releaseStatement(stmt2);
/*      */               } 
/*      */             } else {
/*  708 */               val = "" + aw;
/*      */             } 
/*  710 */             key.setAw(val);
/*  711 */             keyFromCache = (LTAWKSchluessel)schluesselCache.get(strLangLookupKey + "/" + mc + "/" + key.getNr());
/*  712 */             if (keyFromCache == null) {
/*  713 */               schluessellist.add(key);
/*  714 */               notFoundInCache.add(key); continue;
/*      */             } 
/*  716 */             keyFromCache.setAw(key.getAw());
/*  717 */             keyFromCache.setSx(key.getSx());
/*  718 */             keyFromCache.setChange_flag(key.isChange_flag());
/*  719 */             schluessellist.add(keyFromCache);
/*      */           } 
/*      */           
/*  722 */           Iterator<LTAWKSchluessel> notInCacheIt = notFoundInCache.iterator();
/*  723 */           while (notInCacheIt.hasNext()) {
/*  724 */             LTAWKSchluessel curkey = notInCacheIt.next();
/*  725 */             createAWKQualList(stmtManager, langID, curkey);
/*  726 */             schluesselCache.put(strLangLookupKey + "/" + mc + '/' + curkey.getNr(), curkey);
/*      */           } 
/*  728 */           ret = schluessellist;
/*      */         } finally {
/*  730 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*      */         
/*  734 */         stmtManager.releaseStatement(stmt);
/*      */       } 
/*  736 */     } catch (Exception e) {
/*  737 */       log.error("buildAWKSchluesselList() - Exception " + e);
/*      */     } 
/*      */     
/*  740 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean createAWKQualList(IStatementManager stmtManager, Integer langID, LTAWKSchluessel curkey) {
/*  745 */     boolean bRet = true;
/*      */ 
/*      */     
/*      */     try {
/*  749 */       PreparedStatement stmt = stmtManager.getStatement(this.dbms.getSQL("SELECT q.aw_ordnung, q.aw_gueltigkeit, q.aw_operator, q.aw_element FROM AWKqual q WHERE q.aw_schluessel = ? order by q.aw_ordnung"));
/*      */       try {
/*  751 */         stmt.setString(1, curkey.getSx());
/*  752 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  754 */           while (rs.next()) {
/*  755 */             LTAWKQual lTAWKQual = new LTAWKQual();
/*  756 */             lTAWKQual.setOrdnung(rs.getByte(1));
/*  757 */             lTAWKQual.setGueltigkeit(rs.getString(2).charAt(0));
/*  758 */             lTAWKQual.setOperator(rs.getString(3).charAt(0));
/*  759 */             lTAWKQual.setAwElement(rs.getString(4));
/*  760 */             List<LTAWKQual> qualList = curkey.getQualList();
/*  761 */             if (qualList == null) {
/*  762 */               qualList = new LinkedList();
/*  763 */               curkey.setQualList(qualList);
/*      */             } 
/*  765 */             qualList.add(lTAWKQual);
/*      */           } 
/*      */         } finally {
/*  768 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  770 */         LTAWKQual qual = null;
/*  771 */         if (curkey.getQualList() != null) {
/*  772 */           Iterator<LTAWKQual> qualit = curkey.getQualList().iterator();
/*  773 */           while (qualit.hasNext()) {
/*  774 */             qual = qualit.next();
/*  775 */             createAWKElementeList(stmtManager, langID, qual);
/*      */           } 
/*      */         } 
/*      */         
/*  779 */         return bRet;
/*      */       } finally {
/*  781 */         stmtManager.releaseStatement(stmt);
/*      */       } 
/*  783 */     } catch (Exception e) {
/*  784 */       log.error("createAWKQualList() - Exception " + e, e);
/*  785 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean createAWKElementeList(IStatementManager stmtManager, Integer langID, LTAWKQual curQual) {
/*  790 */     boolean bRet = true;
/*  791 */     List<LTAWKElement> havingGroups = new LinkedList();
/*      */ 
/*      */     
/*      */     try {
/*  795 */       PreparedStatement stmt = stmtManager.getStatement(this.dbms.getSQL("SELECT e.aw_element_verweis, e.aw_gruppen_indikator, e.aw_element_typ FROM AWKelemente e WHERE e.aw_element = ? order by e.aw_element_verweis"));
/*      */       try {
/*  797 */         stmt.setString(1, curQual.getAwElement());
/*  798 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  800 */           while (rs.next()) {
/*  801 */             LTAWKElement elem = new LTAWKElement();
/*  802 */             elem.setAwElementVerweis(rs.getString(1));
/*  803 */             elem.setAwGruppenIndikator(rs.getBoolean(2));
/*  804 */             elem.setAwElementTyp(rs.getString(3).charAt(0));
/*  805 */             List<LTAWKElement> elemList = curQual.getElementeList();
/*  806 */             if (elemList == null) {
/*  807 */               elemList = new LinkedList();
/*  808 */               curQual.setElementeList(elemList);
/*      */             } 
/*  810 */             elemList.add(elem);
/*  811 */             if (elem.isAwGruppenIndikator()) {
/*  812 */               havingGroups.add(elem);
/*      */             }
/*      */           } 
/*      */         } finally {
/*      */           
/*  817 */           JDBCUtil.close(rs, log);
/*      */         } 
/*  819 */         LTAWKElement curelem = null;
/*  820 */         Iterator<LTAWKElement> elemit = havingGroups.iterator();
/*  821 */         while (elemit.hasNext()) {
/*  822 */           curelem = elemit.next();
/*  823 */           createAWKGruppenList(stmtManager, langID, curelem);
/*      */         } 
/*  825 */         return bRet;
/*      */       } finally {
/*      */         
/*  828 */         stmtManager.releaseStatement(stmt);
/*      */       } 
/*  830 */     } catch (Exception e) {
/*  831 */       log.error("createAWKElementeList() - Exception " + e, e);
/*  832 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean createAWKGruppenList(IStatementManager stmtManager, Integer langID, LTAWKElement curelem) {
/*  837 */     boolean bRet = true;
/*      */ 
/*      */     
/*      */     try {
/*  841 */       PreparedStatement stmt = stmtManager.getStatement(this.dbms.getSQL("SELECT aw_element_verweis FROM AWKgruppen WHERE aw_gruppen_code=?"));
/*      */       try {
/*  843 */         stmt.setString(1, curelem.getAwElementVerweis());
/*  844 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  846 */           while (rs.next()) {
/*  847 */             LTAWKGruppen group = new LTAWKGruppen();
/*  848 */             group.setAwElementVerweis(rs.getString(1));
/*  849 */             List<LTAWKGruppen> groupList = curelem.getGruppenList();
/*  850 */             if (groupList == null) {
/*  851 */               groupList = new LinkedList();
/*  852 */               curelem.setGruppenList(groupList);
/*      */             } 
/*  854 */             groupList.add(group);
/*      */           } 
/*  856 */           return bRet;
/*      */         } finally {
/*  858 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*      */         
/*  862 */         stmtManager.releaseStatement(stmt);
/*      */       } 
/*  864 */     } catch (Exception e) {
/*  865 */       log.error("createAWKGruppenList() - Exception " + e, e);
/*  866 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean fillLangContextHourLabel(LTLanguageContext cont) {
/*  872 */     boolean bRet = true;
/*      */     
/*      */     try {
/*  875 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT awe.ed FROM AWeinheiten awe WHERE awe.lc = ? AND awe.ec = 2"));
/*      */       try {
/*  877 */         stmt.setInt(1, cont.getLangID().intValue());
/*  878 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  880 */           if (rs.next()) {
/*  881 */             cont.setAwHourUnit(rs.getString(1));
/*      */           }
/*  883 */           return bRet;
/*      */         } finally {
/*      */           
/*  886 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*      */         
/*  890 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  892 */     } catch (Exception e) {
/*  893 */       log.error("fillLangContextHourLabel() - Exception " + e, e);
/*  894 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean fillLangContext(LTLanguageContext cont) {
/*  899 */     boolean bRet = true;
/*  900 */     byte formula = 0;
/*      */     
/*      */     try {
/*  903 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT awf.mc, awe.ed FROM AWKformel awf, AWeinheiten awe WHERE awf.lc = ? AND awe.ec = awf.mc AND awe.lc = awf.lc"));
/*      */       try {
/*  905 */         stmt.setInt(1, cont.getLangID().intValue());
/*  906 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  908 */           if (rs.next()) {
/*  909 */             formula = rs.getByte(1);
/*  910 */             cont.setAwUnit(rs.getString(2));
/*      */           } 
/*  912 */           switch (formula) {
/*      */             case 0:
/*  914 */               cont.setFormula(new AWFormula());
/*      */               break;
/*      */             
/*      */             case 1:
/*  918 */               cont.setFormula((AWFormula)new AWAWFormula());
/*      */               break;
/*      */             
/*      */             case 2:
/*  922 */               cont.setFormula((AWFormula)new AWHourFormula());
/*  923 */               cont.setAwHourUnit(cont.getAwUnit());
/*      */               break;
/*      */             
/*      */             default:
/*  927 */               cont.setFormula(null);
/*      */               break;
/*      */           } 
/*  930 */           return bRet;
/*      */         } finally {
/*      */           
/*  933 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*      */         
/*  937 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/*  939 */     } catch (Exception e) {
/*  940 */       log.error("fillLangContext() - Exception " + e, e);
/*  941 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean loadOperatorCache(Integer lc, Map<Integer, LinkedList> operatorcache, Map<PairImpl, String> textcache) {
/*  946 */     boolean bRet = true;
/*      */     
/*  948 */     LinkedList<OperatorCacheElement> ocList = new LinkedList();
/*  949 */     operatorcache.put(lc, ocList);
/*      */     
/*  951 */     Connection con = this.connectionProvider.getConnection();
/*      */     try {
/*  953 */       PreparedStatement stmt = con.prepareStatement(this.dbms.getSQL("SELECT DISTINCT t.aw_bezeichnung, v.aw_gueltigkeit FROM AWKtexte t, AWKvalidierung v WHERE t.lc=? AND t.aw_text_code = v.aw_text_code"));
/*      */       try {
/*  955 */         stmt.setInt(1, lc.intValue());
/*  956 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  958 */           OperatorCacheElement oce = null;
/*  959 */           while (rs.next()) {
/*  960 */             oce = new OperatorCacheElement();
/*  961 */             oce.setOp(rs.getString(2).charAt(0));
/*  962 */             oce.setText(rs.getString(1));
/*  963 */             ocList.add(oce);
/*      */           } 
/*      */         } finally {
/*      */           
/*  967 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*  970 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*      */       
/*  973 */       stmt = con.prepareStatement(this.dbms.getSQL("SELECT aw_bezeichnung, aw_text_code FROM AWKtexte WHERE lc=?"));
/*      */       try {
/*  975 */         stmt.setInt(1, lc.intValue());
/*  976 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  978 */           while (rs.next()) {
/*  979 */             String bez = rs.getString(1);
/*  980 */             String ky = rs.getString(2);
/*      */ 
/*      */ 
/*      */             
/*  984 */             PairImpl pairImpl = new PairImpl(lc, ky);
/*      */             
/*      */             try {
/*  987 */               textcache.put(pairImpl, bez);
/*  988 */             } catch (Exception e) {
/*  989 */               log.warn("unable to add, skipping - exception: " + e, e);
/*      */             } 
/*      */           } 
/*      */         } finally {
/*      */           
/*  994 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*  997 */         JDBCUtil.close(stmt, log);
/*      */       } 
/*  999 */       return bRet;
/* 1000 */     } catch (Exception e) {
/* 1001 */       log.error("createAWKSchluesselCache() - Exception " + e);
/* 1002 */       return false;
/*      */     } finally {
/*      */       
/* 1005 */       this.connectionProvider.releaseConnection(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean fillAddonworkSXAW(Integer langID, List worklist, Map schluesselcache) {
/* 1012 */     boolean bRet = true;
/* 1013 */     int iIdx = 0;
/*      */ 
/*      */     
/* 1016 */     final Connection connection = this.connectionProvider.getConnection();
/*      */     try {
/* 1018 */       StatementManagerV2 statementManagerV2 = new StatementManagerV2(new ConnectionProvider()
/*      */           {
/*      */             public void releaseConnection(Connection connection) {}
/*      */ 
/*      */             
/*      */             public Connection getConnection() {
/* 1024 */               return connection;
/*      */             }
/*      */           });
/*      */       
/*      */       try {
/* 1029 */         PreparedStatement stmt = statementManagerV2.getStatement(this.dbms.getSQL("SELECT mc, nr,  aw, change_flag FROM AWarbeitswerte WHERE mc=? and TRIM(nr)=?"));
/*      */         
/*      */         try {
/* 1032 */           Iterator<LTDataWork> workit = worklist.iterator();
/* 1033 */           while (workit.hasNext()) {
/* 1034 */             LTDataWork ltwork = workit.next();
/*      */             
/* 1036 */             boolean bNeedsValidation = false;
/* 1037 */             List<LTSXAWData> sxawList = new LinkedList();
/*      */             
/* 1039 */             stmt.setInt(1, ltwork.getMc().intValue());
/* 1040 */             stmt.setString(2, ltwork.getNr());
/* 1041 */             ResultSet rs = stmt.executeQuery();
/*      */             try {
/* 1043 */               while (rs.next()) {
/* 1044 */                 String awstr = null;
/* 1045 */                 String x = "";
/* 1046 */                 int aw = rs.getInt(3);
/* 1047 */                 boolean cflag = rs.getBoolean(4);
/* 1048 */                 if (aw != 0) {
/* 1049 */                   if (aw < 0) {
/* 1050 */                     PreparedStatement stmt2 = statementManagerV2.getStatement(this.dbms.getSQL("SELECT ax FROM AWwerte WHERE lc=? and aw =? "));
/*      */                     try {
/* 1052 */                       stmt2.setInt(1, langID.intValue());
/* 1053 */                       stmt2.setInt(2, aw);
/* 1054 */                       ResultSet rs2 = stmt2.executeQuery();
/*      */                       try {
/* 1056 */                         if (rs2.next()) {
/* 1057 */                           awstr = rs2.getString(1);
/*      */                         }
/*      */                       } finally {
/* 1060 */                         JDBCUtil.close(rs2, log);
/*      */                       } 
/*      */                     } finally {
/* 1063 */                       statementManagerV2.releaseStatement(stmt2);
/*      */                     } 
/*      */                   } else {
/* 1066 */                     awstr = "" + aw;
/*      */                   } 
/* 1068 */                   LTSXAWData sxawdata = new LTSXAWData();
/* 1069 */                   sxawList.add(sxawdata);
/* 1070 */                   sxawdata.setInternalID(iIdx++);
/* 1071 */                   sxawdata.setSxnr("");
/* 1072 */                   sxawdata.setSx(x);
/* 1073 */                   sxawdata.setAw(awstr);
/* 1074 */                   sxawdata.setChange_flag(cflag);
/*      */                 } 
/* 1076 */                 iIdx++;
/*      */               } 
/*      */             } finally {
/*      */               
/* 1080 */               JDBCUtil.close(rs, log);
/*      */             } 
/*      */             
/* 1083 */             List awkSchluesselList = buildAWKSchluesselList((IStatementManager)statementManagerV2, langID, ltwork.getMc(), ltwork.getNr(), schluesselcache);
/* 1084 */             LTAWKSchluessel curSchl = null;
/* 1085 */             Iterator<LTAWKSchluessel> it = awkSchluesselList.iterator();
/* 1086 */             while (it.hasNext()) {
/*      */               
/* 1088 */               bNeedsValidation = true;
/*      */               
/* 1090 */               curSchl = it.next();
/* 1091 */               LTSXAWData newsxaw = new LTSXAWData();
/* 1092 */               newsxaw.setInternalID(iIdx++);
/* 1093 */               newsxaw.setSx(curSchl.getAWText(langID));
/* 1094 */               newsxaw.setSxnr(curSchl.getSx());
/* 1095 */               newsxaw.setAw(curSchl.getAw());
/* 1096 */               newsxaw.setChange_flag(curSchl.isChange_flag());
/* 1097 */               newsxaw.setAWKSchluessel(curSchl);
/* 1098 */               sxawList.add(newsxaw);
/*      */             } 
/* 1100 */             ltwork.setSXAWList(sxawList, bNeedsValidation);
/*      */           } 
/* 1102 */           return bRet;
/*      */         } finally {
/* 1104 */           statementManagerV2.releaseStatement(stmt);
/*      */         } 
/* 1106 */       } catch (Exception e) {
/* 1107 */         log.error("fillAddonworkSXAW() - Exception " + e, e);
/* 1108 */         return false;
/*      */       } 
/*      */     } finally {
/* 1111 */       this.connectionProvider.releaseConnection(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public AWBlob loadGraphic(int sioID) throws Exception {
/* 1116 */     AWBlob blob = new AWBlob();
/*      */     
/*      */     try {
/* 1119 */       PreparedStatement stmt = getStatementManager().getStatement(this.dbms.getSQL("SELECT b.BLOB_ID,b.ELEM_TYPE,b.ELEMENT FROM AWBLOB b WHERE  b.BLOB_ID=?"));
/*      */       try {
/* 1121 */         stmt.setInt(1, sioID);
/* 1122 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/* 1124 */           if (rs.next()) {
/*      */ 
/*      */             
/* 1127 */             blob.setProperty(BLOBProperty.ID, Integer.valueOf(rs.getInt(1)));
/* 1128 */             Blob b = rs.getBlob(3);
/* 1129 */             byte[] data = b.getBytes(1L, (int)b.length());
/* 1130 */             blob.setProperty(BLOBProperty.BLOB, data);
/* 1131 */             blob.setProperty(BLOBProperty.LANGUAGE, Integer.valueOf(0));
/* 1132 */             switch (rs.getInt(2)) {
/*      */               case 1:
/* 1134 */                 blob.setProperty(BLOBProperty.MIMETYPE, "text/html");
/*      */                 break;
/*      */               case 2:
/* 1137 */                 blob.setProperty(BLOBProperty.MIMETYPE, "image/jpg");
/*      */                 break;
/*      */               case 3:
/* 1140 */                 blob.setProperty(BLOBProperty.MIMETYPE, "image/gif");
/*      */                 break;
/*      */               case 5:
/* 1143 */                 blob.setProperty(BLOBProperty.MIMETYPE, "application/pdf");
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/*      */         } finally {
/* 1149 */           JDBCUtil.close(rs, log);
/*      */         } 
/* 1151 */         return blob;
/*      */       } finally {
/*      */         
/* 1154 */         getStatementManager().releaseStatement(stmt);
/*      */       } 
/* 1156 */     } catch (Exception e) {
/* 1157 */       throw new RuntimeException("failed to load AWBlob (sio-id='" + Integer.valueOf(sioID).toString() + "')", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public AWBlob loadDocument(int sio, final Integer lc) throws Exception {
/* 1163 */     Integer[] key = { Integer.valueOf(sio), lc };
/*      */     
/* 1165 */     return (AWBlob)this.crDocument.getObjects(Collections.singleton(key), new CachedRetrievalSupportV3.Callback()
/*      */         {
/*      */           public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 1168 */             Integer[] key = (Integer[])identifier;
/* 1169 */             stmt.setInt(1, key[0].intValue());
/* 1170 */             stmt.setInt(2, key[1].intValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public void initStatement(PreparedStatement stmt) throws SQLException {}
/*      */ 
/*      */           
/*      */           public String getQuery() {
/* 1178 */             return LTStore.this.dbms.getSQL("SELECT b.BLOB_ID,b.ELEM_TYPE,b.ELEMENT FROM AWDATA a,AWBLOB b WHERE a.DATA_ID =? and a.LANGUAGE=? and b.BLOB_ID= a.BLOB_ID");
/*      */           }
/*      */           
/*      */           public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 1182 */             AWBlob blob = new AWBlob();
/* 1183 */             blob.setProperty(BLOBProperty.ID, Integer.valueOf(rs.getInt(1)));
/* 1184 */             Blob b = rs.getBlob(3);
/* 1185 */             byte[] data = b.getBytes(1L, (int)b.length());
/* 1186 */             blob.setProperty(BLOBProperty.BLOB, data);
/* 1187 */             blob.setProperty(BLOBProperty.LANGUAGE, lc);
/* 1188 */             switch (rs.getInt(2)) {
/*      */               case 1:
/* 1190 */                 blob.setProperty(BLOBProperty.MIMETYPE, "text/html");
/*      */                 break;
/*      */               case 2:
/* 1193 */                 blob.setProperty(BLOBProperty.MIMETYPE, "image/jpg");
/*      */                 break;
/*      */               case 3:
/* 1196 */                 blob.setProperty(BLOBProperty.MIMETYPE, "image/gif");
/*      */                 break;
/*      */               case 5:
/* 1199 */                 blob.setProperty(BLOBProperty.MIMETYPE, "application/pdf");
/*      */                 break;
/*      */             } 
/*      */             
/* 1203 */             return blob;
/*      */           }
/*      */           
/*      */           public Object createKey(Object identifier) {
/* 1207 */             return identifier;
/*      */           }
/*      */         }).get(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public static ILTStore create(DBMS dbms2) throws Exception {
/* 1214 */     ILTStore ret = new LTStore(dbms2);
/* 1215 */     if (ApplicationContext.getInstance().developMode()) {
/* 1216 */       ret = (ILTStore)Tis2webUtil.hookWithExecutionTimeStatistics(ret);
/*      */     }
/* 1218 */     return ret;
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\LTStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */