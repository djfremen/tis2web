/*     */ package com.eoos.gm.tis2web.vc.v2.vin;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.datatype.MultiMapWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.LegacyVCDBDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.cache.CacheHitRatioAdapter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LegacyVCDBVINResolver
/*     */   implements VINResolver
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(LegacyVCDBVINResolver.class);
/*     */   private static LegacyVCDBVINResolver instance;
/*     */   private IDatabaseLink dbLink;
/*     */   
/*     */   private static class WMI
/*     */   {
/*     */     public int valueID;
/*     */     private int hashCode;
/*     */     
/*     */     public WMI(String wmi, int valueID) {
/*  51 */       this.valueID = valueID;
/*  52 */       this.hashCode = WMI.class.hashCode();
/*  53 */       this.hashCode = HashCalc.addHashCode(this.hashCode, valueID);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  57 */       return this.hashCode;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/*  61 */       if (this == obj)
/*  62 */         return true; 
/*  63 */       if (obj instanceof WMI) {
/*  64 */         WMI wmi = (WMI)obj;
/*  65 */         return (this.valueID == wmi.valueID);
/*     */       } 
/*  67 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MYC
/*     */   {
/*     */     public int valueID;
/*     */     
/*     */     private int hashCode;
/*     */     
/*     */     public MYC(String myc, int valueID) {
/*  79 */       this.valueID = valueID;
/*  80 */       this.hashCode = MYC.class.hashCode();
/*  81 */       this.hashCode = HashCalc.addHashCode(this.hashCode, valueID);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  85 */       return this.hashCode;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/*  89 */       if (this == obj)
/*  90 */         return true; 
/*  91 */       if (obj instanceof MYC) {
/*  92 */         MYC myc = (MYC)obj;
/*  93 */         return (this.valueID == myc.valueID);
/*     */       } 
/*  95 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AttributeResolver
/*     */   {
/*     */     public Make make;
/*     */     
/*     */     public Object key;
/*     */     
/*     */     public Object value;
/*     */     public Acceptor acceptor;
/*     */     
/*     */     public AttributeResolver(LegacyVCDBVINResolver.WMI wmi, LegacyVCDBVINResolver.MYC myc, Make make, Acceptor acceptor, Object key, Object value) {
/* 110 */       this.make = make;
/* 111 */       this.acceptor = acceptor;
/* 112 */       this.key = key;
/* 113 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean accepts(String vin) {
/* 117 */       return this.acceptor.accept(vin);
/*     */     }
/*     */     
/*     */     public boolean accepts(VIN vin) {
/* 121 */       return accepts(vin.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private final Object SYNC_RESOLVE = new Object();
/*     */   
/*     */   private static final int INDEX_WMI = 0;
/*     */   
/*     */   private static final int INDEX_MYC = 1;
/*     */   
/* 134 */   private Map[] resolveMaps = null;
/*     */ 
/*     */   
/*     */   private CacheHitRatioAdapter cache;
/*     */ 
/*     */   
/*     */   private CachedOperation cachedOperation;
/*     */ 
/*     */   
/*     */   private LegacyVCDBDataProvider dataProvider;
/*     */ 
/*     */   
/*     */   public LegacyVCDBVINResolver(IDatabaseLink dblink) {
/* 147 */     this.dbLink = dblink;
/* 148 */     this.cache = new CacheHitRatioAdapter(Tis2webUtil.createStdCache());
/* 149 */     this.cachedOperation = (CachedOperation)ReflectionUtil.createCachingProxy(new CachedOperation()
/*     */         {
/*     */           public Set getAttributeResolver(LegacyVCDBVINResolver.WMI wmi, LegacyVCDBVINResolver.MYC myc) throws Exception {
/* 152 */             return LegacyVCDBVINResolver.this._getAttributeResolver(wmi, myc);
/*     */           }
/*     */         }(Cache)this.cache, ReflectionUtil.CachingProxyCallback.STD);
/*     */ 
/*     */     
/* 157 */     ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/* 160 */             Logger.getLogger("statistics").info("cache hit ratio for internal cache of singleton " + LegacyVCDBVINResolver.this + ": " + LegacyVCDBVINResolver.this.cache.getHitRatio() + "hits/requests");
/*     */           }
/*     */         });
/*     */     
/* 164 */     this.dataProvider = new LegacyVCDBDataProvider(dblink);
/*     */   }
/*     */   
/*     */   private Map[] getResolveMaps() throws Exception {
/* 168 */     synchronized (this.SYNC_RESOLVE) {
/* 169 */       if (this.resolveMaps == null) {
/* 170 */         StopWatch sw = StopWatch.getInstance().start();
/*     */         try {
/* 172 */           Connection connection = this.dbLink.requestConnection();
/*     */           try {
/* 174 */             connection.setReadOnly(true);
/* 175 */             connection.setAutoCommit(false);
/* 176 */             String query = "select a.domain_id, a.value_id, b.label from vcrvalue a, vcrlabel b where a.value_label=b.label_id and b.language_id=0 and a.domain_id in (4,5)";
/* 177 */             PreparedStatement stmt = connection.prepareStatement(query);
/*     */             try {
/* 179 */               ResultSet rs = stmt.executeQuery();
/*     */               try {
/* 181 */                 Map<Object, Object> wmiResolveMap = new HashMap<Object, Object>();
/* 182 */                 Map<Object, Object> mycResolveMap = new HashMap<Object, Object>();
/*     */                 
/* 184 */                 while (rs.next()) {
/* 185 */                   int domainID = rs.getInt(1);
/* 186 */                   int valueID = rs.getInt(2);
/* 187 */                   String label = rs.getString(3).toUpperCase(Locale.ENGLISH);
/* 188 */                   if (domainID == 5) {
/* 189 */                     wmiResolveMap.put(label, new WMI(label, valueID)); continue;
/*     */                   } 
/* 191 */                   mycResolveMap.put(label, new MYC(label, valueID));
/*     */                 } 
/*     */                 
/* 194 */                 this.resolveMaps = new Map[2];
/* 195 */                 this.resolveMaps[0] = Collections.unmodifiableMap(wmiResolveMap);
/* 196 */                 this.resolveMaps[1] = Collections.unmodifiableMap(mycResolveMap);
/* 197 */                 if (log.isDebugEnabled()) {
/* 198 */                   log.debug("created resolve maps in: " + sw.getElapsedTime() + " ms");
/*     */                 }
/*     */               } finally {
/* 201 */                 JDBCUtil.close(rs);
/*     */               } 
/*     */             } finally {
/* 204 */               JDBCUtil.close(stmt);
/*     */             } 
/*     */           } finally {
/*     */             
/* 208 */             this.dbLink.releaseConnection(connection);
/*     */           } 
/*     */         } finally {
/* 211 */           StopWatch.freeInstance(sw);
/*     */         } 
/*     */       } 
/*     */       
/* 215 */       return this.resolveMaps;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map getWMIResolveMap() throws Exception {
/* 220 */     return getResolveMaps()[0];
/*     */   }
/*     */   
/*     */   private Map getMYCResolveMap() throws Exception {
/* 224 */     return getResolveMaps()[1];
/*     */   }
/*     */   
/*     */   public static synchronized LegacyVCDBVINResolver getInstance() {
/* 228 */     if (instance == null) {
/* 229 */       IDatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.vc.db."));
/*     */       
/* 231 */       instance = new LegacyVCDBVINResolver(dbLink);
/*     */     } 
/* 233 */     return instance;
/*     */   }
/*     */   
/*     */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/*     */     try {
/* 238 */       StopWatch sw = StopWatch.getInstance().start();
/*     */       try {
/* 240 */         WMI wmi = (WMI)getWMIResolveMap().get(vin.getWMI());
/* 241 */         if (wmi == null) {
/* 242 */           return Collections.EMPTY_SET;
/*     */         }
/*     */         
/* 245 */         MYC myc = (MYC)getMYCResolveMap().get(Character.toString(vin.getModelyearCode()));
/* 246 */         Modelyear modelyear = null;
/* 247 */         if (myc != null) {
/* 248 */           modelyear = VehicleConfigurationUtil.toModelyear(String.valueOf(myc.valueID));
/*     */         }
/*     */         
/* 251 */         MultiMapWrapper<Make, IConfiguration> multiMapWrapper = new MultiMapWrapper(new HashMap<Object, Object>());
/*     */         
/* 253 */         Set attributeResolvers = this.cachedOperation.getAttributeResolver(wmi, myc);
/* 254 */         for (Iterator<AttributeResolver> iter = attributeResolvers.iterator(); iter.hasNext(); ) {
/* 255 */           AttributeResolver resolver = iter.next();
/* 256 */           if (resolver.accepts(vin)) {
/* 257 */             Collection<?> cfgs = (Collection)multiMapWrapper.get(resolver.make);
/* 258 */             if (cfgs == null) {
/* 259 */               IConfiguration cfg = VehicleConfigurationUtil.createVC(resolver.make, null, modelyear, null, null);
/* 260 */               cfg = VehicleConfigurationUtil.set(cfg, resolver.key, resolver.value);
/* 261 */               multiMapWrapper.put(resolver.make, cfg);
/*     */               continue;
/*     */             } 
/* 264 */             for (Iterator<?> iterCfgs = (new ArrayList(cfgs)).iterator(); iterCfgs.hasNext(); ) {
/* 265 */               IConfiguration cfg = (IConfiguration)iterCfgs.next();
/* 266 */               IConfiguration newConfig = VehicleConfigurationUtil.set(cfg, resolver.key, resolver.value);
/* 267 */               multiMapWrapper.put(resolver.make, newConfig);
/* 268 */               if (cfg.getValue(resolver.key) == null) {
/* 269 */                 multiMapWrapper.remove(cfg);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         Collection<IConfiguration> cconfigs = multiMapWrapper.values();
/* 279 */         Set ret = new HashSet(cconfigs.size() * 2);
/* 280 */         CollectionUtil.flatten(cconfigs, ret);
/* 281 */         if (log.isDebugEnabled()) {
/* 282 */           log.debug("resolved vin in: " + sw.getElapsedTime() + " ms");
/*     */         }
/* 284 */         return ret;
/*     */       } finally {
/* 286 */         StopWatch.freeInstance(sw);
/*     */       } 
/* 288 */     } catch (Exception e) {
/* 289 */       log.error("unable to resolve vin, returning empty set - exception:" + e, e);
/* 290 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Set _getAttributeResolver(WMI wmi, MYC myc) throws Exception {
/* 295 */     Connection connection = this.dbLink.requestConnection();
/*     */     try {
/* 297 */       connection.setReadOnly(true);
/* 298 */       connection.setAutoCommit(false);
/* 299 */       StringBuffer query = new StringBuffer("select a.make_id,a.position_from, a.position_to, b.vin_element, b.domain_id, b.value_id from vinstructure a, vcrvin b where a.structure_id=b.structure_id and a.wmi_id=? ");
/* 300 */       if (myc != null) {
/* 301 */         query.append("and a.model_year_id=?");
/*     */       }
/* 303 */       PreparedStatement stmt = connection.prepareStatement(query.toString());
/*     */       try {
/* 305 */         stmt.setInt(1, wmi.valueID);
/* 306 */         if (myc != null) {
/* 307 */           stmt.setInt(2, myc.valueID);
/*     */         }
/* 309 */         Set<AttributeResolver> ret = new HashSet();
/* 310 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 312 */           while (rs.next()) {
/* 313 */             int makeID = rs.getInt(1);
/* 314 */             int indexFrom = rs.getInt(2) - 1;
/* 315 */             int indexTo = rs.getInt(3);
/* 316 */             String pattern = rs.getString(4).trim();
/*     */             
/* 318 */             int domainID = rs.getInt(5);
/* 319 */             int valueID = rs.getInt(6);
/*     */             
/* 321 */             Acceptor acceptor = createAcceptor(pattern, indexFrom, indexTo);
/* 322 */             Make make = this.dataProvider.resolveMake(makeID);
/* 323 */             Object key = this.dataProvider.getKey(domainID);
/* 324 */             Object value = this.dataProvider.resolveToObject(domainID, valueID);
/* 325 */             AttributeResolver resolver = new AttributeResolver(wmi, myc, make, acceptor, key, value);
/* 326 */             ret.add(resolver);
/*     */           } 
/* 328 */           return ret;
/*     */         } finally {
/* 330 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/*     */         
/* 334 */         JDBCUtil.close(stmt);
/*     */       } 
/*     */     } finally {
/*     */       
/* 338 */       this.dbLink.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Acceptor createAcceptor(final String pattern, final int indexFrom, final int indexTo) {
/* 343 */     if (pattern.indexOf("*") != -1) {
/* 344 */       String _pattern = pattern.replace('*', '?');
/* 345 */       final StringAcceptor patternAcceptor = StringAcceptor.create(_pattern, true);
/* 346 */       return new Acceptor()
/*     */         {
/*     */           public boolean accept(Object object) {
/*     */             try {
/* 350 */               return patternAcceptor.accept(((String)object).substring(indexFrom, indexTo));
/* 351 */             } catch (Exception e) {
/* 352 */               LegacyVCDBVINResolver.log.error("unable to determine acceptance for input: " + String.valueOf(object) + ", returing false - exception:" + e, e);
/* 353 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */     } 
/*     */     
/* 359 */     return new Acceptor()
/*     */       {
/*     */         public boolean accept(Object object) {
/*     */           try {
/* 363 */             return pattern.equals(((String)object).substring(indexFrom, indexTo));
/* 364 */           } catch (Exception e) {
/* 365 */             LegacyVCDBVINResolver.log.error("unable to determine acceptance for input: " + String.valueOf(object) + ", returing false - exception:" + e, e);
/* 366 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface CachedOperation {
/*     */     Set getAttributeResolver(LegacyVCDBVINResolver.WMI param1WMI, LegacyVCDBVINResolver.MYC param1MYC) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\LegacyVCDBVINResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */