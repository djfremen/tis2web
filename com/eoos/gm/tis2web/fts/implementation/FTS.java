/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.FTSLTElementImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.FTSSIElementImpl;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.reflect.IHExecutionTime;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.queryParser.QueryParser;
/*     */ import org.apache.lucene.search.BooleanQuery;
/*     */ import org.apache.lucene.search.Hits;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.Searcher;
/*     */ import org.apache.lucene.store.Directory;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ import org.apache.lucene.store.RAMDirectory;
/*     */ 
/*     */ 
/*     */ public class FTS
/*     */   implements IFTS
/*     */ {
/*  52 */   private static final Logger log = Logger.getLogger(FTS.class);
/*     */   
/*  54 */   public static String FTS_DIRECTORY = "fts.base-directory";
/*     */   
/*  56 */   public static String FTS_MODE = "fts.in-memory";
/*     */   
/*  58 */   public static String DEFAULT_TOKENIZER = "fts.tokenizer.default";
/*     */   
/*  60 */   public static String TOKENIZER = "fts.tokenizer";
/*     */   
/*  62 */   public static String STEMMER = "fts.stemmer";
/*     */   
/*  64 */   public static String SIO = "sio";
/*     */   
/*  66 */   public static String SUBJECT = "subject";
/*     */   
/*  68 */   public static String CONTENT = "content";
/*     */   
/*  70 */   public static String SIT = "sit";
/*     */ 
/*     */   
/*  73 */   public static String LTSIT = "SIT0LT";
/*     */   
/*     */   public static final String PROPERTY_FILE = "fts.properties";
/*     */   
/*  77 */   private Map fts = new HashMap<Object, Object>();
/*     */   
/*  79 */   private static Properties properties = null;
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
/*     */   private Analyzer analyzer;
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
/*     */   private Searcher searcher;
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
/*     */   private Configuration cfg;
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
/*     */   private FTSService.Callback callback;
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
/*     */   private ConnectionProvider connectionProvider;
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
/*     */   private File directory;
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
/*     */   private FilterTIS.Callback callbackFilterTis;
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
/*     */   private Semaphore gate;
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
/*     */   public FTS(Configuration cfg, FTSService.Callback callback, final VC vc) throws Exception {
/* 180 */     this.gate = null; this.cfg = cfg; this.callback = callback; this.directory = null; String ftsdir = cfg.getProperty("fts.dir"); this.connectionProvider = null; if (ftsdir != null) { this.directory = new File(ftsdir); } else { final DatabaseLink db = DatabaseLink.openDatabase(cfg, "db"); this.connectionProvider = ConNvent.create(new ConnectionProvider() { public void releaseConnection(Connection connection) { db.releaseConnection(connection); } public Connection getConnection() { try { return db.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  } }, 60000L); }  this.callbackFilterTis = new FilterTIS.Callback() { public VC getVC() { return vc; } public int getMinimalTokenLength() { return FTS.getMinimalTokenLength(); } }; int maxParallelQuery = 3; try { maxParallelQuery = ConfigurationUtil.getNumber("max.clients", cfg).intValue(); } catch (Exception e) {} this.gate = new Semaphore(maxParallelQuery); } private FTS(Configuration cfg, ConnectionProvider connectionProvider, File directory, LocaleInfo locale, FilterTIS.Callback callbackFilterTis, FTSService.Callback callback) throws Exception { this.gate = null; this.callback = callback; this.cfg = cfg; String stemmer = getProperties().getProperty(STEMMER + "." + locale.getLocale().toLowerCase(Locale.ENGLISH)); String tokenizer = getProperties().getProperty(TOKENIZER + "." + locale.getLocale().toLowerCase(Locale.ENGLISH)); if (tokenizer == null)
/*     */       tokenizer = getDefaultTokenizer();  this.analyzer = new DefaultAnalyzer(stemmer, tokenizer, callbackFilterTis); if (directory != null) { File indexDirectory = new File(directory.getAbsolutePath() + File.separator + locale.getLocale()); if (!indexDirectory.exists())
/*     */         throw new IllegalArgumentException();  boolean useRAM = getProperties().getProperty(FTS_MODE, "false").equalsIgnoreCase("true"); if (useRAM) { RAMDirectory ramDir = new RAMDirectory(indexDirectory); this.searcher = (Searcher)new IndexSearcher((Directory)ramDir); }
/*     */       else { FSDirectory fileDir = FSDirectory.getDirectory(indexDirectory, false); this.searcher = (Searcher)new IndexSearcher((Directory)fileDir); }
/*     */        }
/*     */     else { SQLDirectory sqlDir = new SQLDirectory(connectionProvider, locale.getLocaleID()); if (!sqlDir.fileExists("segments"))
/*     */         throw new IllegalArgumentException();  this.searcher = (Searcher)new IndexSearcher(sqlDir); }
/* 187 */      log.debug("initialized fts index directory '" + locale.getLocale() + "'"); } public Collection query(LocaleInfo locale, VCR vcr, String sit, String field, String query, int operator) throws IFTS.MaximumExceededException { try { if (!this.gate.tryAcquire(5000L, TimeUnit.MILLISECONDS)) {
/* 188 */         throw new IFTS.MaximumExceededException();
/*     */       } }
/* 190 */     catch (InterruptedException e)
/* 191 */     { Thread.currentThread().interrupt();
/* 192 */       return Collections.EMPTY_LIST; }
/*     */     
/*     */     try {
/* 195 */       IFTS instance = null;
/* 196 */       instance = (IFTS)this.fts.get(locale);
/* 197 */       if (instance == null) {
/*     */         try {
/* 199 */           instance = new FTS(this.cfg, this.connectionProvider, this.directory, locale, this.callbackFilterTis, this.callback);
/* 200 */           if (ApplicationContext.getInstance().developMode()) {
/* 201 */             instance = (IFTS)IHExecutionTime.createProxy(instance, new IHExecutionTime.Callback()
/*     */                 {
/*     */                   public void onSuccessfulExecution(Method m, Object[] args, Object result, long time) {
/* 204 */                     FTS.log.debug("executed method '" + m.getName() + "() in " + time + " ms");
/*     */                   }
/*     */ 
/*     */                   
/*     */                   public void onFailedExecution(Method m, Object[] args, Throwable t, long time) {}
/*     */                 });
/*     */           }
/* 211 */         } catch (Exception e) {
/* 212 */           log.error("unable to create FTS instance, using dummy - exception: " + e, e);
/* 213 */           instance = IFTS.DUMMY;
/*     */         } 
/* 215 */         this.fts.put(locale, instance);
/*     */       } 
/* 217 */       return instance.query(vcr, sit, field, query, operator, locale);
/*     */     } finally {
/* 219 */       this.gate.release();
/*     */     }  }
/*     */ 
/*     */   
/*     */   private static synchronized Properties getProperties() {
/* 224 */     if (properties == null) {
/* 225 */       log.debug("loading properties...");
/* 226 */       properties = new Properties();
/*     */       try {
/* 228 */         properties.load(FTS.class.getClassLoader().getResourceAsStream("fts.properties"));
/* 229 */         log.debug("...done");
/* 230 */       } catch (Exception e) {
/* 231 */         log.error("unable to open  property file 'fts.properties', rethrowing - exception: " + e, e);
/* 232 */         throw Util.toRuntimeException(e);
/*     */       } 
/*     */     } 
/* 235 */     return properties;
/*     */   }
/*     */   
/*     */   public Collection query(VCR vcr, String sit, String field, String query, int operator, LocaleInfo locale) {
/* 239 */     Set<Object> result = new HashSet();
/* 240 */     if (query == null || query.length() == 0) {
/* 241 */       return result;
/*     */     }
/* 243 */     long beginStart = System.currentTimeMillis();
/*     */     try {
/* 245 */       Query q = null;
/* 246 */       QueryParser parser = new QueryParser(field, this.analyzer);
/* 247 */       if (operator != 2) {
/* 248 */         parser.setOperator(operator);
/*     */       }
/*     */       
/* 251 */       if (sit != null) {
/* 252 */         BooleanQuery booleanQuery = new BooleanQuery();
/* 253 */         booleanQuery.add(parser.parse(query), true, false);
/* 254 */         booleanQuery.add(QueryParser.parse(sit, SIT, this.analyzer), true, false);
/*     */       } else {
/* 256 */         q = parser.parse(query);
/*     */       } 
/*     */       
/* 259 */       Hits hits = this.searcher.search(q);
/* 260 */       log.info(q.toString() + " -> " + hits.length() + " total matching documents (search time: " + (System.currentTimeMillis() - beginStart) + " ms)");
/*     */       
/* 262 */       if (!isFTSModeEnhanced().booleanValue()) {
/*     */ 
/*     */ 
/*     */         
/* 266 */         List<Integer> sios = new LinkedList();
/*     */         
/* 268 */         for (int i = 0; i < hits.length(); i++) {
/* 269 */           Integer hit = getSioId(hits.doc(i).get("sio"));
/* 270 */           if (hit != null) {
/* 271 */             sios.add(hit);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         List loadedSios = loadSIOs(sios);
/* 279 */         if (loadedSios != null) {
/* 280 */           result.addAll(loadedSios);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 290 */         for (int i = 0; i < hits.length(); i++) {
/* 291 */           Object ftsElement = createElement(hits.doc(i).get("sio"));
/* 292 */           if (ftsElement == null)
/*     */             continue; 
/* 294 */           if (ftsElement instanceof com.eoos.gm.tis2web.si.implementation.io.db.FTSSIElement) {
/* 295 */             String sits = hits.doc(i).get("sit");
/* 296 */             if (sits != null)
/* 297 */             { List<String> newsits = new ArrayList();
/* 298 */               StringTokenizer t = new StringTokenizer(sits, ",");
/* 299 */               while (t.hasMoreTokens()) {
/* 300 */                 newsits.add(t.nextToken().substring("SIT".length()));
/*     */               }
/* 302 */               ((FTSSIElementImpl)ftsElement).setSits(newsits);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 307 */               String order = hits.doc(i).get("sio_order");
/* 308 */               if (order != null)
/* 309 */                 ((FTSSIElementImpl)ftsElement).setOrder(Integer.valueOf(order).intValue()); 
/* 310 */               ((FTSSIElementImpl)ftsElement).setLiteratureNumber(hits.doc(i).get("lun"));
/* 311 */               ((FTSSIElementImpl)ftsElement).setNonMarketsConstraints(hits.doc(i).get("nmc"));
/* 312 */               String vcrText = hits.doc(i).get("vcr");
/* 313 */               if (vcrText != null)
/* 314 */               { ((FTSSIElementImpl)ftsElement).resolveVCR(vcrText); }
/*     */               else
/* 316 */               { ((FTSSIElementImpl)ftsElement).setVCR(VCR.NULL);
/* 317 */                 log.warn("investigate sio id '" + ((FTSSIElementImpl)ftsElement).getID() + "' has not vcr!"); }  }
/*     */             else { continue; }
/*     */           
/* 320 */           } else if (ftsElement instanceof com.eoos.gm.tis2web.lt.service.cai.FTSLTElement) {
/* 321 */             String vcrText = hits.doc(i).get("vcr");
/* 322 */             if (vcrText != null) {
/* 323 */               ((FTSLTElementImpl)ftsElement).resolveVCR(vcrText);
/*     */             } else {
/* 325 */               ((FTSLTElementImpl)ftsElement).setVCR(VCR.NULL);
/* 326 */               log.warn("investigate lt element '" + ((FTSLTElementImpl)ftsElement).getID() + "' has not vcr!");
/*     */             } 
/* 328 */             ((FTSLTElementImpl)ftsElement).setLabelID(Integer.valueOf(hits.doc(i).get("labelID")));
/* 329 */             ((FTSLTElementImpl)ftsElement).setMajorOperationNumber(hits.doc(i).get("mo_nr"));
/* 330 */             ((FTSLTElementImpl)ftsElement).setPaintStage(hits.doc(i).get("paintstage"));
/*     */           } 
/*     */           
/* 333 */           result.add(ftsElement);
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 339 */     } catch (Exception e) {
/* 340 */       log.error(field + ":" + query + " caught a " + e.getClass() + "\n with message: " + e, e);
/*     */     } 
/*     */     
/* 343 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   private Boolean isFTSModeEnhanced() {
/* 347 */     return Boolean.valueOf(ConfigurationUtil.isTrue(this.cfg.getFullKey("mode.enhanced"), (Configuration)ConfigurationServiceProvider.getService()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getDefaultTokenizer() {
/* 352 */     return getProperties().getProperty(DEFAULT_TOKENIZER);
/*     */   }
/*     */   
/*     */   private static int getMinimalTokenLength() {
/* 356 */     Number ret = ConfigurationUtil.getNumber("frame.fts.minimum-token-length", (Configuration)ApplicationContext.getInstance());
/* 357 */     return (ret == null) ? 3 : ret.intValue();
/*     */   }
/*     */   
/*     */   private Integer getSioId(String sioId) {
/* 361 */     return this.callback.getSioId(sioId);
/*     */   }
/*     */   
/*     */   private List loadSIOs(List sios) {
/* 365 */     return this.callback.loadSIOs(sios);
/*     */   }
/*     */   
/*     */   private Object createElement(String sioId) {
/* 369 */     return this.callback.createElement(sioId);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\FTS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */