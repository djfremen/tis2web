/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSCOP;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSCOP
/*     */   extends SPSCOP
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final char ORIGIN = 'O';
/*     */   protected static final char VALID = 'V';
/*     */   protected static final char RETROFIT = 'R';
/*     */   protected static final char SUPERSEED = 'S';
/*     */   protected static final char PRELIMINARY = 'P';
/*  35 */   protected static final Integer ELSE = Integer.valueOf(0);
/*  36 */   protected static final Integer UNDEFINED = Integer.valueOf(-2147483648);
/*     */   
/*  38 */   private static Logger log = Logger.getLogger(SPSCOP.class);
/*     */   
/*     */   protected transient char replacementType;
/*     */   
/*     */   public static final class StaticData
/*     */   {
/*     */     private Map cop;
/*     */     private Set parts;
/*     */     private Map bulletins;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  49 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  50 */       this.cop = new HashMap<Object, Object>();
/*  51 */       this.parts = new HashSet();
/*  52 */       this.bulletins = new HashMap<Object, Object>();
/*  53 */       Connection conn = null;
/*  54 */       DBMS.PreparedStatement stmt = null;
/*  55 */       ResultSet rs = null;
/*     */       try {
/*  57 */         conn = dblink.requestConnection();
/*  58 */         String sql = DBMS.getSQL(dblink, "SELECT * FROM COP");
/*  59 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  60 */         rs = stmt.executeQuery();
/*  61 */         while (rs.next()) {
/*  62 */           int originPart = rs.getInt("Origin_Part_No");
/*  63 */           int oldPart = rs.getInt("Old_Part_No");
/*  64 */           String key = Util.toThreadLocalMultiton(SPSCOP.COP.makeKey(originPart, oldPart));
/*  65 */           List<SPSCOP.COP> chain = (List)this.cop.get(key);
/*  66 */           if (chain == null) {
/*  67 */             chain = new ArrayList();
/*  68 */             this.cop.put(key, chain);
/*     */           } 
/*  70 */           SPSCOP.COP entry = new SPSCOP.COP();
/*  71 */           chain.add(entry);
/*  72 */           entry.newPart = rs.getInt("New_Part_No");
/*  73 */           entry.replacementType = Character.valueOf(rs.getString("Rep_Type").charAt(0)).charValue();
/*  74 */           entry.descriptionID = rs.getInt("Description_Id");
/*  75 */           entry.bulletinID = Util.toThreadLocalMultiton(rs.getString("Bulletin_No").trim());
/*  76 */           entry.hardwareIndex = rs.getInt("Hardware_Indx");
/*     */           try {
/*  78 */             entry.usageIndex = Integer.valueOf(rs.getInt("Usage_Index_No"));
/*  79 */             if (rs.wasNull()) {
/*  80 */               entry.usageIndex = SPSCOP.UNDEFINED;
/*     */             }
/*  82 */           } catch (Exception x) {
/*  83 */             entry.usageIndex = SPSCOP.UNDEFINED;
/*     */           } 
/*  85 */           this.parts.add(Integer.valueOf(entry.newPart));
/*  86 */           if (entry.bulletinID != null && !"0".equals(entry.bulletinID)) {
/*  87 */             this.bulletins.put(Integer.toString(entry.newPart), entry.bulletinID);
/*     */           }
/*     */         } 
/*  90 */       } catch (Exception e) {
/*  91 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  94 */           if (rs != null) {
/*  95 */             rs.close();
/*  96 */             rs = null;
/*     */           } 
/*  98 */           if (stmt != null) {
/*  99 */             stmt.close();
/* 100 */             stmt = null;
/*     */           } 
/* 102 */           if (conn != null) {
/* 103 */             dblink.releaseConnection(conn);
/* 104 */             conn = null;
/*     */           } 
/* 106 */         } catch (Exception x) {
/* 107 */           SPSCOP.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getCOP() {
/* 117 */       return this.cop;
/*     */     }
/*     */     
/*     */     public Set getParts() {
/* 121 */       return this.parts;
/*     */     }
/*     */     
/*     */     public Map getBulletins() {
/* 125 */       return this.bulletins;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 129 */       synchronized (adapter.getSyncObject()) {
/* 130 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 131 */         if (instance == null) {
/* 132 */           instance = new StaticData(adapter);
/* 133 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 135 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   char getReplacementType() {
/* 143 */     return this.replacementType;
/*     */   }
/*     */   
/*     */   public SPSCOP(SPSPart part, char replacementType) {
/* 147 */     super(part, 0);
/* 148 */     this.replacementType = replacementType;
/*     */   }
/*     */   
/*     */   static void computeMode(SPSPart origin) {
/* 152 */     List<SPSCOP> cop = origin.getCOP();
/* 153 */     if (cop == null) {
/*     */       return;
/*     */     }
/* 156 */     for (int i = 0; i < cop.size(); i++) {
/* 157 */       SPSCOP link = cop.get(i);
/* 158 */       if (link.getReplacementType() == 'V' && isLeaf((SPSPart)link.getPart())) {
/* 159 */         link.setMode(1);
/*     */       } else {
/* 161 */         link.setMode(2);
/*     */       } 
/* 163 */       computeMode((SPSPart)link.getPart());
/*     */     } 
/* 165 */     assertSelectablePaths(origin);
/*     */   }
/*     */   
/*     */   static void assertSelectablePaths(SPSPart part) {
/* 169 */     List cop = part.getCOP();
/* 170 */     if (cop == null) {
/*     */       return;
/*     */     }
/* 173 */     Iterator<SPSCOP> it = cop.iterator();
/* 174 */     while (it.hasNext()) {
/* 175 */       SPSCOP link = it.next();
/* 176 */       if (!isValidPath(link)) {
/* 177 */         it.remove(); continue;
/*     */       } 
/* 179 */       assertSelectablePaths((SPSPart)link.getPart());
/*     */     } 
/*     */     
/* 182 */     if (cop.size() == 0) {
/* 183 */       part.invalidateCOP();
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isValidPath(SPSCOP link) {
/* 188 */     if (link.getMode() == 1) {
/* 189 */       return true;
/*     */     }
/* 191 */     SPSPart part = (SPSPart)link.getPart();
/* 192 */     List<SPSCOP> cop = part.getCOP();
/* 193 */     if (cop != null) {
/* 194 */       for (int i = 0; i < cop.size(); i++) {
/* 195 */         if (isValidPath(cop.get(i))) {
/* 196 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isLeaf(SPSPart part) {
/* 205 */     return (part == null || part.getCOP() == null);
/*     */   }
/*     */   
/*     */   static boolean exists(String part, SPSSchemaAdapterNAO adapter) {
/*     */     try {
/* 210 */       return StaticData.getInstance(adapter).getParts().contains(Integer.valueOf(part));
/* 211 */     } catch (Exception x) {
/*     */       
/* 213 */       return false;
/*     */     } 
/*     */   }
/*     */   public static void handle(SPSLanguage language, SPSPart origin, SPSPart part, Set usage, SPSSchemaAdapterNAO adapter) throws Exception {
/* 217 */     List<COP> chain = (List)StaticData.getInstance(adapter).getCOP().get(COP.makeKey(origin.getID(), part.getID()));
/* 218 */     if (chain != null) {
/* 219 */       Set restrictions = null;
/* 220 */       for (int i = 0; i < chain.size(); i++) {
/* 221 */         COP entry = chain.get(i);
/* 222 */         if (!entry.usageIndex.equals(UNDEFINED)) {
/* 223 */           if (usage != null) {
/* 224 */             if (entry.usageIndex.equals(ELSE)) {
/* 225 */               if (restrictions == null) {
/* 226 */                 restrictions = collectUsageRestrictions(chain);
/*     */               }
/* 228 */               if (match(usage, restrictions)) {
/*     */                 continue;
/*     */               }
/* 231 */             } else if (!usage.contains(entry.usageIndex)) {
/*     */               continue;
/*     */             } 
/* 234 */           } else if (!entry.usageIndex.equals(ELSE)) {
/*     */             continue;
/*     */           } 
/*     */         }
/* 238 */         if (entry.newPart != part.getID()) {
/* 239 */           if (entry.replacementType == 'P') {
/*     */             return;
/*     */           }
/* 242 */           SPSPart replacement = new SPSPart(entry.newPart, adapter);
/* 243 */           replacement.setDescription(language, entry.descriptionID, adapter);
/* 244 */           replacement.setBulletinID(entry.bulletinID);
/* 245 */           replacement.setHardwareIndex(language, entry.hardwareIndex, adapter);
/* 246 */           part.replace(new SPSCOP(replacement, entry.replacementType));
/* 247 */           if (entry.replacementType != 'V') {
/* 248 */             handle(language, origin, replacement, usage, adapter);
/*     */           }
/*     */         } else {
/* 251 */           part.setDescription(language, entry.descriptionID, adapter);
/* 252 */           part.setBulletinID(entry.bulletinID);
/* 253 */           if (entry.hardwareIndex > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 259 */           part.setHardwareIndex(language, entry.hardwareIndex, adapter);
/*     */         }  continue;
/*     */       } 
/*     */     } else {
/* 263 */       throw new SPSException(CommonException.NoValidCOP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean match(Set usage, Set restrictions) {
/* 269 */     Iterator<Integer> it = usage.iterator();
/* 270 */     while (it.hasNext()) {
/* 271 */       Integer use = it.next();
/* 272 */       if (restrictions.contains(use)) {
/* 273 */         return true;
/*     */       }
/*     */     } 
/* 276 */     return false;
/*     */   }
/*     */   
/*     */   static Set collectUsageRestrictions(List<COP> chain) {
/* 280 */     Set<Integer> restrictions = new HashSet();
/* 281 */     for (int i = 0; i < chain.size(); i++) {
/* 282 */       COP entry = chain.get(i);
/* 283 */       if (!entry.usageIndex.equals(UNDEFINED) && !entry.usageIndex.equals(ELSE)) {
/* 284 */         restrictions.add(entry.usageIndex);
/*     */       }
/*     */     } 
/* 287 */     return restrictions;
/*     */   }
/*     */   
/*     */   static List getCOP(SPSPart origin, SPSSchemaAdapterNAO adapter) throws Exception {
/* 291 */     List data = new ArrayList();
/* 292 */     fill(data, origin, origin, adapter);
/* 293 */     return data;
/*     */   }
/*     */   
/*     */   static void fill(List<List<String>> data, SPSPart origin, SPSPart part, SPSSchemaAdapterNAO adapter) throws Exception {
/* 297 */     List<COP> chain = (List)StaticData.getInstance(adapter).getCOP().get(COP.makeKey(origin.getID(), part.getID()));
/* 298 */     if (chain != null) {
/* 299 */       for (int i = 0; i < chain.size(); i++) {
/* 300 */         COP entry = chain.get(i);
/* 301 */         List<String> info = new ArrayList();
/* 302 */         info.add(origin.getPartNumber());
/* 303 */         info.add(Integer.valueOf(part.getID()));
/* 304 */         info.add(Integer.valueOf(entry.newPart));
/* 305 */         info.add("" + entry.replacementType);
/* 306 */         info.add(Integer.valueOf(entry.descriptionID));
/* 307 */         info.add(Integer.valueOf(entry.hardwareIndex));
/* 308 */         if (!entry.usageIndex.equals(UNDEFINED)) {
/* 309 */           info.add(entry.usageIndex);
/*     */         }
/* 311 */         data.add(info);
/* 312 */         if (entry.newPart != part.getID()) {
/* 313 */           SPSPart replacement = new SPSPart(entry.newPart, adapter);
/* 314 */           fill(data, origin, replacement, adapter);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkBulletin(SPSModule module, SPSSchemaAdapterNAO adapter) {
/* 321 */     SPSPart part = (SPSPart)module.getOriginPart();
/* 322 */     String bulletin = (String)StaticData.getInstance(adapter).getBulletins().get(part.getPartNumber());
/* 323 */     if (bulletin != null) {
/* 324 */       part.setBulletinID(bulletin);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class COP
/*     */   {
/*     */     int newPart;
/*     */ 
/*     */ 
/*     */     
/*     */     char replacementType;
/*     */ 
/*     */ 
/*     */     
/*     */     int descriptionID;
/*     */ 
/*     */ 
/*     */     
/*     */     String bulletinID;
/*     */ 
/*     */ 
/*     */     
/*     */     int hardwareIndex;
/*     */ 
/*     */     
/*     */     Integer usageIndex;
/*     */ 
/*     */ 
/*     */     
/*     */     static String makeKey(int originPart, int oldPart) {
/* 357 */       StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*     */       try {
/* 359 */         tmp.append(originPart);
/* 360 */         tmp.append(":");
/* 361 */         tmp.append(oldPart);
/* 362 */         return tmp.toString();
/*     */       } finally {
/* 364 */         StringBufferPool.getThreadInstance().free(tmp);
/*     */       } 
/*     */     }
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
/*     */     public String toString() {
/* 381 */       return this.newPart + "/" + this.replacementType + "(" + this.usageIndex + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class COPPROM {
/*     */     static String makeKey(int originPart, int oldPart) {
/* 387 */       return originPart + ":" + oldPart;
/*     */     }
/*     */ 
/*     */     
/*     */     int newPart;
/*     */     
/*     */     char replacementType;
/*     */     
/*     */     String description;
/*     */     String comment;
/*     */   }
/*     */   
/*     */   static List build(SPSLanguage language, SPSControllerPROM controller, SPSSchemaAdapterNAO adapter) throws Exception {
/* 400 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 401 */     language = SPSLanguage.getLanguagePROM(language, adapter);
/* 402 */     List<SPSPartPROM> origins = new ArrayList();
/* 403 */     Connection conn = null;
/* 404 */     DBMS.PreparedStatement stmt = null;
/* 405 */     ResultSet rs = null;
/*     */     try {
/* 407 */       conn = dblink.requestConnection();
/* 408 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code = ?");
/* 409 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 410 */       stmt.setInt(1, controller.getID());
/*     */       
/* 412 */       stmt.setString(2, language.getID());
/* 413 */       rs = stmt.executeQuery();
/* 414 */       while (rs.next()) {
/* 415 */         SPSPartPROM origin = new SPSPartPROM(rs.getInt(1), adapter);
/* 416 */         origins.add(origin);
/*     */       } 
/* 418 */     } catch (Exception e) {
/* 419 */       throw e;
/*     */     } finally {
/*     */       try {
/* 422 */         if (rs != null) {
/* 423 */           rs.close();
/*     */         }
/* 425 */         if (stmt != null) {
/* 426 */           stmt.close();
/*     */         }
/* 428 */         if (origins.size() == 0 && 
/* 429 */           conn != null) {
/* 430 */           dblink.releaseConnection(conn);
/*     */         }
/*     */       }
/* 433 */       catch (Exception x) {}
/*     */     } 
/*     */     
/* 436 */     if (origins.size() == 0) {
/* 437 */       throw new SPSException(CommonException.NoValidCOP);
/*     */     }
/* 439 */     Map<Object, Object> cop = new HashMap<Object, Object>();
/*     */     try {
/* 441 */       String sql = DBMS.getSQL(dblink, "SELECT Origin_Part_No, Old_Part_No, New_Part_No, Rep_Type, Rep_Description, Release_Comments FROM PROM_COP WHERE Origin_Part_No IN (#list#) AND Language_Code = ?", origins.size());
/* 442 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 443 */       for (int j = 1; j <= origins.size(); j++) {
/* 444 */         stmt.setInt(j, ((SPSPartPROM)origins.get(j - 1)).getID());
/*     */       }
/* 446 */       stmt.setString(origins.size() + 1, language.getID());
/* 447 */       rs = stmt.executeQuery();
/* 448 */       while (rs.next()) {
/* 449 */         int originPart = rs.getInt(1);
/* 450 */         int oldPart = rs.getInt(2);
/* 451 */         String key = COP.makeKey(originPart, oldPart);
/* 452 */         List<COPPROM> chain = (List)cop.get(key);
/* 453 */         if (chain == null) {
/* 454 */           chain = new ArrayList();
/* 455 */           cop.put(key, chain);
/*     */         } 
/* 457 */         COPPROM entry = new COPPROM();
/* 458 */         chain.add(entry);
/* 459 */         entry.newPart = rs.getInt(3);
/* 460 */         entry.replacementType = rs.getString(4).charAt(0);
/*     */         
/* 462 */         entry.description = rs.getString(5);
/* 463 */         if (entry.description != null) {
/* 464 */           entry.description = entry.description.trim();
/*     */         }
/* 466 */         entry.comment = rs.getString(6);
/* 467 */         if (entry.comment != null) {
/* 468 */           entry.comment = entry.comment.trim();
/*     */         }
/*     */       } 
/* 471 */     } catch (Exception e) {
/* 472 */       throw e;
/*     */     } finally {
/*     */       try {
/* 475 */         if (rs != null) {
/* 476 */           rs.close();
/*     */         }
/* 478 */         if (stmt != null) {
/* 479 */           stmt.close();
/*     */         }
/* 481 */         if (conn != null) {
/* 482 */           dblink.releaseConnection(conn);
/*     */         }
/* 484 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 487 */     Map<Object, Object> proms = new HashMap<Object, Object>();
/* 488 */     for (int i = 0; i < origins.size(); i++) {
/* 489 */       SPSPartPROM origin = origins.get(i);
/* 490 */       handle(cop, proms, origin, origin, adapter);
/*     */     } 
/* 492 */     return origins;
/*     */   }
/*     */   
/*     */   static void handle(Map cop, Map<Integer, SPSPartPROM> proms, SPSPartPROM origin, SPSPartPROM part, SPSSchemaAdapterNAO adapter) throws Exception {
/* 496 */     List<COPPROM> chain = (List)cop.get(COP.makeKey(origin.getID(), part.getID()));
/* 497 */     if (chain != null) {
/* 498 */       for (int i = 0; i < chain.size(); i++) {
/* 499 */         COPPROM entry = chain.get(i);
/* 500 */         if (entry.newPart != part.getID()) {
/* 501 */           SPSPartPROM replacement = (SPSPartPROM)proms.get(Integer.valueOf(entry.newPart));
/* 502 */           if (replacement == null) {
/* 503 */             replacement = new SPSPartPROM(entry.newPart, adapter);
/* 504 */             proms.put(Integer.valueOf(entry.newPart), replacement);
/*     */           } 
/* 506 */           replacement.setComment(entry.comment);
/* 507 */           replacement.setBulletinID(entry.description);
/* 508 */           part.replace(new SPSCOP(replacement, entry.replacementType));
/* 509 */           if (entry.replacementType != 'V') {
/* 510 */             handle(cop, proms, origin, replacement, adapter);
/*     */           }
/*     */         } else {
/* 513 */           part.setComment(entry.comment);
/* 514 */           part.setBulletinID(entry.description);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 521 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSCOP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */