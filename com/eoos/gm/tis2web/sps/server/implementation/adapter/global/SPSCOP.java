/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSCOP;
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
/*  33 */   protected static final Integer ELSE = Integer.valueOf(0);
/*  34 */   protected static final Integer UNDEFINED = Integer.valueOf(-2147483648);
/*     */   
/*  36 */   private static Logger log = Logger.getLogger(SPSCOP.class);
/*     */   
/*     */   protected transient char replacementType;
/*     */   
/*     */   public static final class StaticData
/*     */   {
/*     */     private Map cop;
/*     */     private Set parts;
/*     */     private Map bulletins;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  47 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  48 */       this.cop = new HashMap<Object, Object>();
/*  49 */       this.parts = new HashSet();
/*  50 */       this.bulletins = new HashMap<Object, Object>();
/*  51 */       Connection conn = null;
/*  52 */       DBMS.PreparedStatement stmt = null;
/*  53 */       ResultSet rs = null;
/*     */       try {
/*  55 */         conn = dblink.requestConnection();
/*  56 */         String sql = DBMS.getSQL(dblink, "SELECT * FROM COP");
/*  57 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  58 */         rs = stmt.executeQuery();
/*  59 */         while (rs.next()) {
/*  60 */           int originPart = rs.getInt("Origin_Part_No");
/*  61 */           int oldPart = rs.getInt("Old_Part_No");
/*  62 */           String key = SPSCOP.COP.makeKey(originPart, oldPart);
/*  63 */           List<SPSCOP.COP> chain = (List)this.cop.get(key);
/*  64 */           if (chain == null) {
/*  65 */             chain = new ArrayList();
/*  66 */             this.cop.put(key, chain);
/*     */           } 
/*  68 */           SPSCOP.COP entry = new SPSCOP.COP();
/*  69 */           chain.add(entry);
/*  70 */           entry.newPart = rs.getInt("New_Part_No");
/*  71 */           entry.replacementType = rs.getString("Rep_Type").charAt(0);
/*  72 */           entry.descriptionID = rs.getInt("Description_Id");
/*  73 */           entry.bulletinID = rs.getString("Bulletin_No").trim();
/*  74 */           entry.hardwareIndex = rs.getInt("Hardware_Indx");
/*     */           try {
/*  76 */             entry.usageIndex = Integer.valueOf(rs.getInt("Usage_Index_No"));
/*  77 */             if (rs.wasNull()) {
/*  78 */               entry.usageIndex = SPSCOP.UNDEFINED;
/*     */             }
/*  80 */           } catch (Exception x) {
/*  81 */             entry.usageIndex = SPSCOP.UNDEFINED;
/*     */           } 
/*  83 */           this.parts.add(Integer.valueOf(entry.newPart));
/*  84 */           if (entry.bulletinID != null && !"0".equals(entry.bulletinID)) {
/*  85 */             this.bulletins.put(Integer.toString(entry.newPart), entry.bulletinID);
/*     */           }
/*     */         } 
/*  88 */       } catch (Exception e) {
/*  89 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  92 */           if (rs != null) {
/*  93 */             rs.close();
/*  94 */             rs = null;
/*     */           } 
/*  96 */           if (stmt != null) {
/*  97 */             stmt.close();
/*  98 */             stmt = null;
/*     */           } 
/* 100 */           if (conn != null) {
/* 101 */             dblink.releaseConnection(conn);
/* 102 */             conn = null;
/*     */           } 
/* 104 */         } catch (Exception x) {
/* 105 */           SPSCOP.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getCOP() {
/* 115 */       return this.cop;
/*     */     }
/*     */     
/*     */     public Set getParts() {
/* 119 */       return this.parts;
/*     */     }
/*     */     
/*     */     public Map getBulletins() {
/* 123 */       return this.bulletins;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/* 127 */       synchronized (adapter.getSyncObject()) {
/* 128 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 129 */         if (instance == null) {
/* 130 */           instance = new StaticData(adapter);
/* 131 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 133 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   char getReplacementType() {
/* 141 */     return this.replacementType;
/*     */   }
/*     */   
/*     */   public SPSCOP(SPSPart part, char replacementType) {
/* 145 */     super(part, 0);
/* 146 */     this.replacementType = replacementType;
/*     */   }
/*     */   
/*     */   static void computeMode(SPSPart origin) {
/* 150 */     List<SPSCOP> cop = origin.getCOP();
/* 151 */     if (cop == null) {
/*     */       return;
/*     */     }
/* 154 */     for (int i = 0; i < cop.size(); i++) {
/* 155 */       SPSCOP link = cop.get(i);
/* 156 */       if (link.getReplacementType() == 'V' && isLeaf((SPSPart)link.getPart())) {
/* 157 */         link.setMode(1);
/*     */       } else {
/* 159 */         link.setMode(2);
/*     */       } 
/* 161 */       computeMode((SPSPart)link.getPart());
/*     */     } 
/* 163 */     assertSelectablePaths(origin);
/*     */   }
/*     */   
/*     */   static void assertSelectablePaths(SPSPart part) {
/* 167 */     List cop = part.getCOP();
/* 168 */     if (cop == null) {
/*     */       return;
/*     */     }
/* 171 */     Iterator<SPSCOP> it = cop.iterator();
/* 172 */     while (it.hasNext()) {
/* 173 */       SPSCOP link = it.next();
/* 174 */       if (!isValidPath(link)) {
/* 175 */         it.remove(); continue;
/*     */       } 
/* 177 */       assertSelectablePaths((SPSPart)link.getPart());
/*     */     } 
/*     */     
/* 180 */     if (cop.size() == 0) {
/* 181 */       part.invalidateCOP();
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isValidPath(SPSCOP link) {
/* 186 */     if (link.getMode() == 1) {
/* 187 */       return true;
/*     */     }
/* 189 */     SPSPart part = (SPSPart)link.getPart();
/* 190 */     List<SPSCOP> cop = part.getCOP();
/* 191 */     if (cop != null) {
/* 192 */       for (int i = 0; i < cop.size(); i++) {
/* 193 */         if (isValidPath(cop.get(i))) {
/* 194 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isLeaf(SPSPart part) {
/* 203 */     return (part == null || part.getCOP() == null);
/*     */   }
/*     */   
/*     */   static boolean exists(String part, SPSSchemaAdapterGlobal adapter) {
/*     */     try {
/* 208 */       return StaticData.getInstance(adapter).getParts().contains(Integer.valueOf(part));
/* 209 */     } catch (Exception x) {
/*     */       
/* 211 */       return false;
/*     */     } 
/*     */   }
/*     */   public static void handle(SPSLanguage language, SPSPart origin, SPSPart part, Set usage, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 215 */     List<COP> chain = (List)StaticData.getInstance(adapter).getCOP().get(COP.makeKey(origin.getID(), part.getID()));
/* 216 */     if (chain != null) {
/* 217 */       Set restrictions = null;
/* 218 */       for (int i = 0; i < chain.size(); i++) {
/* 219 */         COP entry = chain.get(i);
/* 220 */         if (entry.usageIndex != UNDEFINED) {
/* 221 */           if (usage != null) {
/* 222 */             if (entry.usageIndex.equals(ELSE)) {
/* 223 */               if (restrictions == null) {
/* 224 */                 restrictions = collectUsageRestrictions(chain);
/*     */               }
/* 226 */               if (match(usage, restrictions)) {
/*     */                 continue;
/*     */               }
/* 229 */             } else if (!usage.contains(entry.usageIndex)) {
/*     */               continue;
/*     */             } 
/* 232 */           } else if (!entry.usageIndex.equals(ELSE)) {
/*     */             continue;
/*     */           } 
/*     */         }
/* 236 */         if (entry.newPart != part.getID()) {
/* 237 */           if (entry.replacementType == 'P') {
/*     */             return;
/*     */           }
/* 240 */           SPSPart replacement = new SPSPart(entry.newPart, adapter);
/* 241 */           replacement.setDescription(language, entry.descriptionID, adapter);
/* 242 */           replacement.setBulletinID(entry.bulletinID);
/* 243 */           replacement.setHardwareIndex(language, entry.hardwareIndex, adapter);
/* 244 */           part.replace(new SPSCOP(replacement, entry.replacementType));
/* 245 */           if (entry.replacementType != 'V') {
/* 246 */             handle(language, origin, replacement, usage, adapter);
/*     */           }
/*     */         } else {
/* 249 */           part.setDescription(language, entry.descriptionID, adapter);
/* 250 */           part.setBulletinID(entry.bulletinID);
/* 251 */           if (entry.hardwareIndex > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 257 */           part.setHardwareIndex(language, entry.hardwareIndex, adapter);
/*     */         }  continue;
/*     */       } 
/*     */     } else {
/* 261 */       throw new SPSException(CommonException.NoValidCOP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean match(Set usage, Set restrictions) {
/* 267 */     Iterator<Integer> it = usage.iterator();
/* 268 */     while (it.hasNext()) {
/* 269 */       Integer use = it.next();
/* 270 */       if (restrictions.contains(use)) {
/* 271 */         return true;
/*     */       }
/*     */     } 
/* 274 */     return false;
/*     */   }
/*     */   
/*     */   static Set collectUsageRestrictions(List<COP> chain) {
/* 278 */     Set<Integer> restrictions = new HashSet();
/* 279 */     for (int i = 0; i < chain.size(); i++) {
/* 280 */       COP entry = chain.get(i);
/* 281 */       if (entry.usageIndex != UNDEFINED && !entry.usageIndex.equals(ELSE)) {
/* 282 */         restrictions.add(entry.usageIndex);
/*     */       }
/*     */     } 
/* 285 */     return restrictions;
/*     */   }
/*     */   
/*     */   static List getCOP(SPSPart origin, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 289 */     List data = new ArrayList();
/* 290 */     fill(data, origin, origin, adapter);
/* 291 */     return data;
/*     */   }
/*     */   
/*     */   static void fill(List<List<String>> data, SPSPart origin, SPSPart part, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 295 */     List<COP> chain = (List)StaticData.getInstance(adapter).getCOP().get(COP.makeKey(origin.getID(), part.getID()));
/* 296 */     if (chain != null) {
/* 297 */       for (int i = 0; i < chain.size(); i++) {
/* 298 */         COP entry = chain.get(i);
/* 299 */         List<String> info = new ArrayList();
/* 300 */         info.add(origin.getPartNumber());
/* 301 */         info.add(Integer.valueOf(part.getID()));
/* 302 */         info.add(Integer.valueOf(entry.newPart));
/* 303 */         info.add("" + entry.replacementType);
/* 304 */         info.add(Integer.valueOf(entry.descriptionID));
/* 305 */         info.add(Integer.valueOf(entry.hardwareIndex));
/* 306 */         if (entry.usageIndex != UNDEFINED) {
/* 307 */           info.add(entry.usageIndex);
/*     */         }
/* 309 */         data.add(info);
/* 310 */         if (entry.newPart != part.getID()) {
/* 311 */           SPSPart replacement = new SPSPart(entry.newPart, adapter);
/* 312 */           fill(data, origin, replacement, adapter);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkBulletin(SPSModule module, SPSSchemaAdapterGlobal adapter) {
/* 319 */     SPSPart part = (SPSPart)module.getOriginPart();
/* 320 */     String bulletin = (String)StaticData.getInstance(adapter).getBulletins().get(part.getPartNumber());
/* 321 */     if (bulletin != null)
/* 322 */       part.setBulletinID(bulletin); 
/*     */   }
/*     */   
/*     */   protected static class COP { int newPart;
/*     */     
/*     */     static String makeKey(int originPart, int oldPart) {
/* 328 */       return originPart + ":" + oldPart;
/*     */     }
/*     */ 
/*     */     
/*     */     char replacementType;
/*     */     
/*     */     int descriptionID;
/*     */     
/*     */     String bulletinID;
/*     */     
/*     */     int hardwareIndex;
/*     */     
/*     */     Integer usageIndex;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 344 */       return this.newPart + "/" + this.replacementType + "(" + this.usageIndex + ")";
/*     */     } }
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 349 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSCOP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */