/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSPart
/*     */   implements SPSPart
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int INVALID_PARTNO = -1;
/*  21 */   private static Logger log = Logger.getLogger(SPSPart.class);
/*     */   protected int id;
/*     */   protected String label;
/*     */   
/*     */   public static final class CVN {
/*     */     private CVN(SPSSchemaAdapterNAO adapter) {
/*  27 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  28 */       Connection conn = null;
/*     */       try {
/*  30 */         conn = dblink.requestConnection();
/*  31 */         SPSPartDescription.init(adapter);
/*  32 */         SPSHardwareIndex.init(adapter);
/*  33 */         loadCVN(conn, adapter);
/*  34 */       } catch (Exception e) {
/*  35 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  38 */           if (conn != null) {
/*  39 */             dblink.releaseConnection(conn);
/*     */           }
/*  41 */         } catch (Exception x) {
/*  42 */           SPSPart.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private Map cvns;
/*     */     private void loadCVN(Connection conn, SPSSchemaAdapterNAO adapter) throws Exception {
/*  48 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*     */       
/*  50 */       this.cvns = new HashMap<Object, Object>();
/*  51 */       DBMS.PreparedStatement stmt = null;
/*  52 */       ResultSet rs = null;
/*     */       try {
/*  54 */         String sql = DBMS.getSQL(dblink, "SELECT Part_No, CVN FROM CVN");
/*  55 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  56 */         rs = stmt.executeQuery();
/*  57 */         while (rs.next()) {
/*  58 */           String id = Integer.toString(rs.getInt(1));
/*  59 */           this.cvns.put(id, rs.getString(2));
/*     */         } 
/*  61 */       } catch (Exception e) {
/*  62 */         throw e;
/*     */       } finally {
/*     */         try {
/*  65 */           if (rs != null) {
/*  66 */             rs.close();
/*     */           }
/*  68 */           if (stmt != null) {
/*  69 */             stmt.close();
/*     */           }
/*  71 */         } catch (Exception x) {
/*  72 */           SPSPart.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getCVNs() {
/*  82 */       return this.cvns;
/*     */     }
/*     */     
/*     */     public static CVN getInstance(SPSSchemaAdapterNAO adapter) {
/*  86 */       synchronized (adapter.getSyncObject()) {
/*  87 */         CVN instance = (CVN)adapter.getObject(CVN.class);
/*  88 */         if (instance == null) {
/*  89 */           instance = new CVN(adapter);
/*  90 */           adapter.storeObject(CVN.class, instance);
/*     */         } 
/*  92 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class EIL
/*     */   {
/*     */     private Map eil;
/*     */     
/*     */     private EIL(SPSSchemaAdapterNAO adapter) {
/* 102 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/* 103 */       Connection conn = null;
/*     */       try {
/* 105 */         conn = dblink.requestConnection();
/* 106 */         initEndItemList(conn, dblink, adapter);
/* 107 */       } catch (Exception e) {
/* 108 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 111 */           if (conn != null) {
/* 112 */             dblink.releaseConnection(conn);
/*     */           }
/* 114 */         } catch (Exception x) {
/* 115 */           SPSPart.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void initEndItemList(Connection conn, IDatabaseLink dblink, SPSSchemaAdapterNAO adapter) throws Exception {
/* 121 */       this.eil = new HashMap<Object, Object>();
/* 122 */       DBMS.PreparedStatement stmt = null;
/* 123 */       ResultSet rs = null;
/*     */       try {
/* 125 */         String sql = DBMS.getSQL(dblink, "SELECT DISTINCT End_Item_No FROM EIL");
/* 126 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 127 */         rs = stmt.executeQuery();
/* 128 */         while (rs.next()) {
/* 129 */           String id = Integer.toString(rs.getInt(1));
/* 130 */           SPSPartEI part = (SPSPartEI)this.eil.get(id);
/* 131 */           if (part == null) {
/* 132 */             part = new SPSPartEI(id, adapter);
/* 133 */             this.eil.put(id, part);
/*     */           } 
/*     */         } 
/* 136 */       } catch (Exception e) {
/* 137 */         throw e;
/*     */       } finally {
/*     */         try {
/* 140 */           if (rs != null) {
/* 141 */             rs.close();
/*     */           }
/* 143 */           if (stmt != null) {
/* 144 */             stmt.close();
/*     */           }
/* 146 */         } catch (Exception x) {
/* 147 */           SPSPart.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getEIL() {
/* 157 */       return this.eil;
/*     */     }
/*     */     
/*     */     public static EIL getInstance(SPSSchemaAdapterNAO adapter) {
/* 161 */       synchronized (adapter.getSyncObject()) {
/* 162 */         EIL instance = (EIL)adapter.getObject(EIL.class);
/* 163 */         if (instance == null) {
/* 164 */           instance = new EIL(adapter);
/* 165 */           adapter.storeObject(EIL.class, instance);
/*     */         } 
/* 167 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean originPartFlag = false;
/*     */ 
/*     */   
/*     */   protected String description;
/*     */ 
/*     */   
/*     */   protected int hardwareIndex;
/*     */   
/*     */   protected String bulletinID;
/*     */   
/*     */   protected String cvn;
/*     */   
/*     */   protected List cop;
/*     */   
/*     */   protected transient SPSSchemaAdapterNAO adapter;
/*     */ 
/*     */   
/*     */   SPSPart(SPSSchemaAdapterNAO adapter) {
/* 191 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   SPSPart(String id, SPSSchemaAdapterNAO adapter) {
/* 195 */     this.id = Integer.parseInt(id);
/* 196 */     this.adapter = adapter;
/* 197 */     this.cvn = (String)CVN.getInstance(adapter).getCVNs().get(id);
/*     */   }
/*     */   
/*     */   SPSPart(int id, SPSSchemaAdapterNAO adapter) {
/* 201 */     this.id = id;
/* 202 */     this.adapter = adapter;
/* 203 */     this.cvn = (String)CVN.getInstance(adapter).getCVNs().get(Integer.toString(id));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SPSPart(String id, String description, SPSSchemaAdapterNAO adapter) {
/* 209 */     this.adapter = adapter;
/*     */     try {
/* 211 */       this.id = Integer.parseInt(id);
/* 212 */     } catch (Exception x) {
/* 213 */       this.id = -1;
/* 214 */       this.label = id;
/*     */     } 
/* 216 */     this.description = description;
/*     */   }
/*     */   
/*     */   public void setOriginPartFlagTrue() {
/* 220 */     this.originPartFlag = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 224 */     if (this.originPartFlag == true) {
/* 225 */       return getPartNumber() + ((this.label == null || this.id == -1) ? "" : (" " + this.label));
/*     */     }
/* 227 */     return getPartNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   void setBulletinID(String bulletinID) {
/* 232 */     if (bulletinID != null && !bulletinID.equals("0")) {
/* 233 */       this.bulletinID = bulletinID;
/*     */     }
/*     */   }
/*     */   
/*     */   void setHardwareIndex(SPSLanguage language, int hardwareIndex, SPSSchemaAdapterNAO adapter) throws Exception {
/* 238 */     this.hardwareIndex = hardwareIndex;
/* 239 */     if (this.description == null && hardwareIndex != 0) {
/* 240 */       this.description = getHardwareDescription(language, hardwareIndex, adapter);
/*     */     }
/*     */   }
/*     */   
/*     */   void setDescription(SPSLanguage language, int descriptionID, SPSSchemaAdapterNAO adapter) {
/* 245 */     this.label = SPSPartDescription.getLabel(language, descriptionID, adapter);
/* 246 */     this.description = SPSPartDescription.getDescription(language, descriptionID, adapter);
/*     */   }
/*     */   
/*     */   void replace(SPSCOP link) {
/* 250 */     if (this.cop == null) {
/* 251 */       this.cop = new ArrayList();
/*     */     }
/* 253 */     this.cop.add(link);
/*     */   }
/*     */   
/*     */   void invalidateCOP() {
/* 257 */     this.cop = null;
/*     */   }
/*     */   
/*     */   public int getID() {
/* 261 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getPartNumber() {
/* 265 */     return (-1 == this.id) ? this.label : Integer.toString(this.id);
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 269 */     if (this.originPartFlag == true) {
/* 270 */       return this.label;
/*     */     }
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 277 */     return this.description;
/*     */   }
/*     */   
/*     */   public List getCOP() {
/* 281 */     return (this.cop == null || this.cop.size() == 0) ? null : this.cop;
/*     */   }
/*     */   
/*     */   public String getCVN() {
/* 285 */     return this.cvn;
/*     */   }
/*     */   
/*     */   public List getBulletins() {
/* 289 */     return makeBulletinList(this.bulletinID);
/*     */   }
/*     */   
/*     */   int getHardwareIndex() {
/* 293 */     return this.hardwareIndex;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 297 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 301 */     return (object != null && object instanceof SPSPart && ((SPSPart)object).getID() == this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addBulletin(List<String> bulletins, String bulletin) {
/* 309 */     if (bulletins.indexOf("/") >= 0) {
/* 310 */       StringBuffer prefix = new StringBuffer();
/* 311 */       String base = null;
/* 312 */       StringBuffer postfix = new StringBuffer();
/* 313 */       for (int i = 0; i < bulletin.length(); i++) {
/* 314 */         char c = bulletin.charAt(i);
/* 315 */         if (c == '-') {
/* 316 */           base = prefix.toString().trim();
/* 317 */           postfix = new StringBuffer();
/* 318 */         } else if (c != '/') {
/* 319 */           String str = base + postfix.toString().trim();
/* 320 */           bulletins.add(str);
/* 321 */           postfix = new StringBuffer();
/*     */         } else {
/* 323 */           prefix.append(c);
/* 324 */           postfix.append(c);
/*     */         } 
/*     */       } 
/* 327 */       String b = postfix.toString().trim();
/* 328 */       if (b.length() > 0) {
/* 329 */         bulletins.add(base + b);
/*     */       }
/*     */     } else {
/* 332 */       bulletins.add(bulletin);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List makeBulletinList(String bulletins) {
/* 337 */     if (bulletins == null) {
/* 338 */       return null;
/*     */     }
/* 340 */     List list = new ArrayList();
/* 341 */     if (bulletins.indexOf("S/B") >= 0) {
/* 342 */       bulletins = StringUtilities.replace(bulletins, "S/B", "\t");
/* 343 */       StringBuffer bulletin = new StringBuffer();
/* 344 */       for (int i = 0; i < bulletins.length(); i++) {
/* 345 */         char c = bulletins.charAt(i);
/* 346 */         if (c == '\t') {
/* 347 */           String str = bulletin.toString().trim();
/* 348 */           if (str.length() > 0) {
/* 349 */             addBulletin(list, str);
/*     */           }
/* 351 */           bulletin = new StringBuffer();
/* 352 */         } else if (c != ',') {
/* 353 */           bulletin.append(c);
/*     */         } 
/*     */       } 
/* 356 */       String b = bulletin.toString().trim();
/* 357 */       if (b.length() > 0) {
/* 358 */         addBulletin(list, b);
/*     */       }
/* 360 */     } else if (bulletins.indexOf(",") >= 0) {
/* 361 */       StringBuffer bulletin = new StringBuffer();
/* 362 */       for (int i = 0; i < bulletins.length(); i++) {
/* 363 */         char c = bulletins.charAt(i);
/* 364 */         if (c == ',') {
/* 365 */           String str = bulletin.toString().trim();
/* 366 */           if (str.length() > 0) {
/* 367 */             addBulletin(list, str);
/*     */           }
/* 369 */           bulletin = new StringBuffer();
/*     */         } else {
/* 371 */           bulletin.append(c);
/*     */         } 
/*     */       } 
/* 374 */       String b = bulletin.toString().trim();
/* 375 */       if (b.length() > 0) {
/* 376 */         addBulletin(list, b);
/*     */       }
/*     */     } else {
/* 379 */       addBulletin(list, bulletins);
/*     */     } 
/* 381 */     return list;
/*     */   }
/*     */   
/*     */   static String getHardwareDescription(SPSLanguage language, int hardwareIndex, SPSSchemaAdapterNAO adapter) throws Exception {
/* 385 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/*     */     
/* 387 */     Connection conn = null;
/* 388 */     DBMS.PreparedStatement stmt = null;
/* 389 */     ResultSet rs = null;
/*     */     try {
/* 391 */       conn = dblink.requestConnection();
/* 392 */       String sql = DBMS.getSQL(dblink, "SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?");
/* 393 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 394 */       stmt.setInt(1, hardwareIndex);
/* 395 */       rs = stmt.executeQuery();
/* 396 */       if (rs.next()) {
/* 397 */         return SPSPartDescription.getDescription(language, rs.getInt(1), adapter);
/*     */       }
/* 399 */       return null;
/*     */     }
/* 401 */     catch (Exception e) {
/* 402 */       throw e;
/*     */     } finally {
/*     */       try {
/* 405 */         if (rs != null) {
/* 406 */           rs.close();
/*     */         }
/* 408 */         if (stmt != null) {
/* 409 */           stmt.close();
/*     */         }
/* 411 */         if (conn != null) {
/* 412 */           dblink.releaseConnection(conn);
/*     */         }
/* 414 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isEndItemPart(String part, SPSSchemaAdapterNAO adapter) {
/* 420 */     return (EIL.getInstance(adapter).getEIL().get(part) != null);
/*     */   }
/*     */   
/*     */   static SPSPartEI getEndItemPart(String id, SPSSchemaAdapterNAO adapter) {
/* 424 */     SPSPartEI part = (SPSPartEI)EIL.getInstance(adapter).getEIL().get(id);
/* 425 */     return part;
/*     */   }
/*     */   
/*     */   public String getCalibrationVerificationNumber(String partNumber) {
/* 429 */     return getCVN(partNumber, this.adapter);
/*     */   }
/*     */   
/*     */   static String getCVN(String part, SPSSchemaAdapterNAO adapter) {
/* 433 */     return (String)CVN.getInstance(adapter).getCVNs().get(part);
/*     */   }
/*     */   
/*     */   public List getCalibrationParts() {
/* 437 */     if (!isEndItemPart(getPartNumber(), this.adapter)) {
/* 438 */       return null;
/*     */     }
/*     */     try {
/* 441 */       List parts = SPSModule.getCalibrationInfo(this.id, this.adapter);
/* 442 */       parts.remove(0);
/* 443 */       return parts;
/* 444 */     } catch (Exception x) {
/* 445 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 450 */     CVN.getInstance(adapter).init();
/* 451 */     EIL.getInstance(adapter).init();
/*     */   }
/*     */   
/*     */   public String getDescription(Locale locale) {
/* 455 */     return getDescription();
/*     */   }
/*     */   
/*     */   public String getShortDescription(Locale locale) {
/* 459 */     return getLabel();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */