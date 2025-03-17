/*     */ package com.eoos.gm.tis2web.vcr.implementation;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.ICR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class VCRFactory
/*     */ {
/*  35 */   protected static Logger log = Logger.getLogger(VCRFactory.class);
/*     */   
/*  37 */   public static int LOCALE = 1;
/*     */   
/*     */   protected ConnectionProvider connectionProvider;
/*     */   
/*  41 */   protected HashMap cachedVCRs = new HashMap<Object, Object>(10000);
/*     */   
/*  43 */   protected HashMap poolICRs = new HashMap<Object, Object>(20000);
/*     */   
/*  45 */   protected int maxID = 0;
/*     */   
/*     */   private CachedRetrievalSupportV3 vcrRetrievalSupport;
/*     */   
/*     */   public VCRFactory(Configuration cfg) {
/*     */     try {
/*  51 */       final DatabaseLink dblink = DatabaseLink.openDatabase(cfg, "db");
/*  52 */       this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */           {
/*     */             public void releaseConnection(Connection connection) {
/*  55 */               dblink.releaseConnection(connection);
/*     */             }
/*     */             
/*     */             public Connection getConnection() {
/*     */               try {
/*  60 */                 return dblink.requestConnection();
/*  61 */               } catch (Exception e) {
/*  62 */                 throw new RuntimeException(e);
/*     */               } 
/*     */             }
/*     */           },  60000L);
/*     */       
/*  67 */       Connection vcrdb = this.connectionProvider.getConnection();
/*     */       try {
/*  69 */         this.maxID = getMaxID(vcrdb);
/*     */       } finally {
/*  71 */         this.connectionProvider.releaseConnection(vcrdb);
/*     */       } 
/*     */       
/*  74 */       this.vcrRetrievalSupport = new CachedRetrievalSupportV3("VCR", this.connectionProvider, Tis2webUtil.createStdCache());
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       log.error("unable to init VCRFactory - exception:" + e);
/*  78 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public VCRAttributeImpl makeAttribute(VCValue value) {
/*  83 */     return new VCRAttributeImpl(value);
/*     */   }
/*     */   
/*     */   public VCRAttributeImpl makeAttribute(int aDomain, int aValue) {
/*  87 */     return new VCRAttributeImpl(aDomain, aValue);
/*     */   }
/*     */   
/*     */   public VCRTermImpl makeTerm() {
/*  91 */     return new VCRTermImpl();
/*     */   }
/*     */   
/*     */   public VCRTerm makeTerm(VCValue value) {
/*  95 */     return new VCRTermImpl(makeAttribute(value));
/*     */   }
/*     */   
/*     */   public VCRTermImpl makeTerm(VCRAttribute attribute) {
/*  99 */     return new VCRTermImpl(attribute);
/*     */   }
/*     */   
/*     */   public VCRExpressionImpl makeExpression() {
/* 103 */     return new VCRExpressionImpl();
/*     */   }
/*     */   
/*     */   public VCRImpl makeVCR() {
/* 107 */     return new VCRImpl(this);
/*     */   }
/*     */   
/*     */   public VCRImpl makeVCR(VCRExpression expression) {
/* 111 */     return new VCRImpl(expression, this);
/*     */   }
/*     */   
/*     */   public VCRImpl makeVCR(VCRTerm term) {
/* 115 */     return new VCRImpl(term, this);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCRAttribute attribute) {
/* 119 */     return new VCRImpl(attribute, this);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCValue value) {
/* 123 */     return new VCRImpl(new VCRAttributeImpl(value), this);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(int id) {
/* 127 */     if (id == VCR.NULL.getID()) {
/* 128 */       return VCR.NULL;
/*     */     }
/* 130 */     return getVCR(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */   
/*     */   public VCRImpl makeVCR(String v) {
/* 135 */     return new VCRImpl(createICR(v), this);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCConfiguration vcc) {
/* 139 */     return makeVCR(vcc, null, null);
/*     */   }
/*     */   
/*     */   public VCR makeVCR(VCConfiguration vcc, VCValue engine, VCValue transmission) {
/* 143 */     VCRExpressionImpl expression = makeExpression();
/* 144 */     if (vcc == null)
/* 145 */       return VCRImpl.NULL; 
/* 146 */     List<VCValue> elements = vcc.getElements();
/* 147 */     for (int k = 0; k < elements.size(); k++) {
/* 148 */       expression.add(new VCRAttributeImpl(elements.get(k)));
/*     */     }
/* 150 */     if (engine != null) {
/* 151 */       expression.add(new VCRAttributeImpl(engine));
/*     */     }
/* 153 */     if (transmission != null) {
/* 154 */       expression.add(new VCRAttributeImpl(transmission));
/*     */     }
/* 156 */     return makeVCR(expression);
/*     */   }
/*     */   
/*     */   synchronized ICRImpl createICR(VCR vcr, ICRListImpl expressions) {
/* 160 */     return createICR(vcr, expressions, true);
/*     */   }
/*     */   
/*     */   synchronized ICRImpl createICR(VCR vcr, ICRListImpl expressions, boolean simplify) {
/* 164 */     ICRImpl icr = new ICRImpl(expressions, simplify);
/* 165 */     ICRImpl instance = registerICR(icr);
/* 166 */     if (instance == null) {
/* 167 */       icr.setID(++this.maxID);
/* 168 */       this.cachedVCRs.put(new Integer(icr.getID()), vcr);
/*     */       
/* 170 */       return icr;
/*     */     } 
/* 172 */     this.cachedVCRs.put(new Integer(instance.getID()), vcr);
/* 173 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized ICRImpl createICR(String v) {
/* 179 */     ICRImpl icr = new ICRImpl(v);
/* 180 */     ICRImpl instance = registerICR(icr);
/* 181 */     if (instance == null) {
/* 182 */       icr.setID(++this.maxID);
/*     */       
/* 184 */       return icr;
/*     */     } 
/* 186 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void registerCacheICR(ICRImpl icr) {
/* 192 */     ICR instance = registerICR(icr);
/* 193 */     if (instance == null) {
/* 194 */       icr.setID(++this.maxID);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ICRImpl registerICR(ICRImpl icr) {
/* 200 */     Integer code = new Integer(icr.getHashCode());
/* 201 */     Object slot = this.poolICRs.get(code);
/* 202 */     if (slot == null) {
/* 203 */       this.poolICRs.put(code, icr);
/* 204 */     } else if (slot instanceof ICR) {
/* 205 */       ICRImpl instance = (ICRImpl)slot;
/* 206 */       if (icr.equals(instance)) {
/* 207 */         return instance;
/*     */       }
/* 209 */       ArrayList<ICRImpl> bucket = new ArrayList(2);
/* 210 */       bucket.add(icr);
/* 211 */       bucket.add(instance);
/* 212 */       this.poolICRs.put(code, bucket);
/*     */     } else {
/* 214 */       ArrayList<ICRImpl> bucket = (ArrayList)slot;
/* 215 */       for (int i = 0; i < bucket.size(); i++) {
/* 216 */         ICRImpl instance = bucket.get(i);
/* 217 */         if (icr.equals(instance)) {
/* 218 */           return instance;
/*     */         }
/*     */       } 
/* 221 */       bucket.add(icr);
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean ignoreError() {
/*     */     try {
/* 228 */       if ("true".equalsIgnoreCase(ApplicationContext.getInstance().getProperty("frame.vcr.ignore-errors"))) {
/* 229 */         return true;
/*     */       }
/* 231 */     } catch (Exception x) {}
/*     */     
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   protected int getMaxID(Connection vcrdb) throws Exception {
/* 237 */     Statement stmt = null;
/* 238 */     ResultSet rs = null;
/*     */     try {
/* 240 */       stmt = vcrdb.createStatement();
/* 241 */       rs = stmt.executeQuery("SELECT MAX(VCR_ID) AS MAX_KEY FROM VCR");
/* 242 */       if (rs.next()) {
/* 243 */         return rs.getInt("MAX_KEY");
/*     */       }
/* 245 */       throw new Exception("failed to query max-vcr-id.");
/*     */     }
/* 247 */     catch (Exception e) {
/* 248 */       log.error("failed to query max-vcr-id.");
/* 249 */       throw e;
/*     */     } finally {
/*     */       try {
/* 252 */         if (rs != null) {
/* 253 */           rs.close();
/*     */         }
/* 255 */         if (stmt != null) {
/* 256 */           stmt.close();
/*     */         }
/* 258 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, VCR> getVCRs(Collection ids) {
/*     */     try {
/* 265 */       return this.vcrRetrievalSupport.getObjects(ids, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 268 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */ 
/*     */             
/*     */             public String getQuery() {
/* 276 */               return "SELECT VCR_TEXT FROM VCR WHERE VCR_ID=?";
/*     */             }
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 280 */               ICRImpl icr = new ICRImpl(rs.getString("VCR_TEXT"));
/* 281 */               icr.setID(((Integer)identifier).intValue());
/* 282 */               VCRFactory.this.registerICR(icr);
/* 283 */               return new VCRImpl(icr, VCRFactory.this);
/*     */             }
/*     */             
/*     */             public Object createKey(Object identifier) {
/* 287 */               return identifier;
/*     */             }
/*     */           });
/* 290 */     } catch (Exception e) {
/* 291 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VCR getVCR(Integer id) {
/* 297 */     return getVCRs(Collections.singleton(id)).get(id);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VCRFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */