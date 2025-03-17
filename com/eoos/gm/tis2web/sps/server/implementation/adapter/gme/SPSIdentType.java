/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSIdentType {
/*     */   protected Integer identType;
/*     */   protected String vit1Ident;
/*  16 */   public static String WILDCARD = "?"; protected Integer vit1Pos;
/*     */   protected Integer hwid;
/*     */   
/*  19 */   public static class StaticData { private Map hwlocations = new HashMap<Object, Object>();
/*     */     
/*  21 */     private Map descriptions = new HashMap<Object, Object>();
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGME adapter) {
/*  24 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  25 */       Connection conn = null;
/*  26 */       DBMS.PreparedStatement stmt = null;
/*  27 */       ResultSet rs = null;
/*     */       try {
/*  29 */         conn = db.requestConnection();
/*  30 */         String sql = DBMS.getSQL(db, "SELECT HWLocID, IdentOrder, VITType, VIT1Ident, VITPos FROM SPS_HWLocation ORDER BY 1, 2");
/*  31 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  32 */         rs = stmt.executeQuery();
/*  33 */         while (rs.next()) {
/*  34 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  35 */           rs.getInt(2);
/*  36 */           String vit1Type = rs.getString(3);
/*  37 */           if (vit1Type != null)
/*  38 */             vit1Type = vit1Type.trim(); 
/*  39 */           String vit1Ident = rs.getString(4);
/*  40 */           if (vit1Ident != null)
/*  41 */             vit1Ident = vit1Ident.trim(); 
/*  42 */           Integer vit1Pos = Integer.valueOf(rs.getInt(5));
/*  43 */           if (rs.wasNull()) {
/*  44 */             vit1Pos = null;
/*     */           }
/*  46 */           String key = id + ":" + vit1Type;
/*  47 */           List<SPSIdentType> sequence = (List)this.hwlocations.get(key);
/*  48 */           if (sequence == null) {
/*  49 */             sequence = new ArrayList();
/*  50 */             this.hwlocations.put(key, sequence);
/*     */           } 
/*  52 */           sequence.add(new SPSIdentType(id, vit1Ident, vit1Pos, null));
/*     */         } 
/*  54 */       } catch (RuntimeException e) {
/*  55 */         throw e;
/*  56 */       } catch (Exception e) {
/*  57 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  60 */           if (rs != null) {
/*  61 */             rs.close();
/*     */           }
/*  63 */           if (stmt != null) {
/*  64 */             stmt.close();
/*     */           }
/*  66 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/*  70 */         String sql = DBMS.getSQL(db, "SELECT IdentType, Display, LanguageID FROM SPS_IdentDisplay");
/*  71 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  72 */         rs = stmt.executeQuery();
/*  73 */         while (rs.next()) {
/*  74 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  75 */           String lg = rs.getString(3).trim();
/*  76 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*  77 */           String display = DBMS.getString(db, language, rs, 2);
/*  78 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  79 */           if (description == null) {
/*  80 */             description = new SPSDescription();
/*  81 */             this.descriptions.put(id, description);
/*     */           } 
/*  83 */           description.add(language, display);
/*     */         } 
/*  85 */       } catch (RuntimeException e) {
/*  86 */         throw e;
/*  87 */       } catch (Exception e) {
/*  88 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  91 */           if (rs != null) {
/*  92 */             rs.close();
/*     */           }
/*  94 */           if (stmt != null) {
/*  95 */             stmt.close();
/*     */           }
/*  97 */           if (conn != null) {
/*  98 */             db.releaseConnection(conn);
/*     */           }
/* 100 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGME adapter) {
/* 110 */       synchronized (adapter.getSyncObject()) {
/* 111 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 112 */         if (instance == null) {
/* 113 */           instance = new StaticData(adapter);
/* 114 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 116 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getHWLocations() {
/* 121 */       return this.hwlocations;
/*     */     }
/*     */     
/*     */     public Map getDescriptions() {
/* 125 */       return this.descriptions;
/*     */     } }
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
/*     */   public Integer getIdentType() {
/* 139 */     return this.identType;
/*     */   }
/*     */   
/*     */   public String getVIT1Ident() {
/* 143 */     return this.vit1Ident;
/*     */   }
/*     */   
/*     */   public Integer getVIT1Pos() {
/* 147 */     return this.vit1Pos;
/*     */   }
/*     */   
/*     */   public Integer getHWID() {
/* 151 */     return this.hwid;
/*     */   }
/*     */   
/*     */   public SPSIdentType(Integer identType, String vit1Ident, Integer vit1Pos, Integer hwid) {
/* 155 */     this.identType = identType;
/* 156 */     this.vit1Ident = (vit1Ident != null) ? vit1Ident.trim().toLowerCase(Locale.ENGLISH) : null;
/* 157 */     this.vit1Pos = vit1Pos;
/* 158 */     this.hwid = hwid;
/*     */   }
/*     */   
/*     */   public SPSIdentType(String vit1Ident, int vit1Pos) {
/* 162 */     this.identType = null;
/* 163 */     this.vit1Ident = (vit1Ident != null) ? vit1Ident.trim().toLowerCase(Locale.ENGLISH) : null;
/* 164 */     this.vit1Pos = (vit1Pos == 0) ? null : Integer.valueOf(vit1Pos);
/*     */   }
/*     */   
/*     */   public String getDisplay(SPSLanguage language, SPSSchemaAdapterGME adapter) {
/* 168 */     if (this.identType != null) {
/* 169 */       SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(this.identType);
/* 170 */       if (description != null) {
/* 171 */         return description.get(language);
/*     */       }
/*     */     } 
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   public String getIdentValue(VIT1Data vit1) {
/* 178 */     if (this.vit1Ident == null)
/* 179 */       return vit1.getOPNumber(); 
/* 180 */     if (this.vit1Ident.equals("vmecuhn"))
/* 181 */       return vit1.getOPNumber(); 
/* 182 */     if (this.vit1Ident.equals("ssecusvn"))
/* 183 */       return vit1.getSWVersion(); 
/* 184 */     if (this.vit1Ident.equals("snoet")) {
/* 185 */       if (this.vit1Pos == null) {
/* 186 */         return vit1.getSNOET();
/*     */       }
/* 188 */       return select(vit1.getTokenizedSNOET(), this.vit1Pos.intValue());
/*     */     } 
/* 190 */     if (this.vit1Ident.equals("ddi")) {
/* 191 */       List<String> elements = vit1.getTokenizedSNOET();
/* 192 */       if (elements == null || elements.size() == 1) {
/* 193 */         return null;
/*     */       }
/* 195 */       return elements.get(elements.size() - 1);
/*     */     } 
/* 197 */     if (this.vit1Ident.equals("partno")) {
/* 198 */       if (this.vit1Pos == null) {
/* 199 */         StringBuffer result = new StringBuffer();
/* 200 */         List parts = vit1.getParts();
/* 201 */         if (parts == null) {
/* 202 */           return null;
/*     */         }
/* 204 */         for (int i = 0; i < parts.size(); i++) {
/* 205 */           if (i > 0) {
/* 206 */             result.append('.');
/*     */           }
/* 208 */           result.append(parts.get(i));
/*     */         } 
/* 210 */         return result.toString();
/*     */       } 
/* 212 */       return select(vit1.getParts(), this.vit1Pos.intValue());
/*     */     } 
/* 214 */     if (this.vit1Ident.equals("ssecuhn"))
/* 215 */       return vit1.getHWNumber(); 
/* 216 */     if (this.vit1Ident.equals("swcompat_id"))
/* 217 */       return vit1.getCompatibilityNr(); 
/* 218 */     if (this.vit1Ident.equals("seed")) {
/* 219 */       if (this.vit1Pos == null) {
/* 220 */         return select(vit1.getSeeds(), 0);
/*     */       }
/* 222 */       return select(vit1.getSeeds(), this.vit1Pos.intValue());
/*     */     } 
/*     */     
/* 225 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String select(List<String> elements, int position) {
/*     */     try {
/* 231 */       if (position >= 0) {
/* 232 */         return elements.get(position);
/*     */       }
/* 234 */       return elements.get(elements.size() + position);
/*     */     }
/* 236 */     catch (Exception x) {
/* 237 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getIdentVals(VIT1Data vit1) {
/* 242 */     List<String> values = new ArrayList();
/* 243 */     if (this.vit1Ident == null)
/* 244 */       return values; 
/* 245 */     if (this.vit1Ident.equals("vmecuhn")) {
/* 246 */       values.add(vit1.getOPNumber());
/* 247 */     } else if (this.vit1Ident.equals("ssecusvn")) {
/* 248 */       values.add(vit1.getSWVersion());
/* 249 */     } else if (this.vit1Ident.equals("snoet")) {
/* 250 */       values.addAll(vit1.getTokenizedSNOET());
/* 251 */     } else if (this.vit1Ident.equals("partno")) {
/* 252 */       values.addAll(vit1.getParts());
/* 253 */     } else if (this.vit1Ident.equals("ssecuhn")) {
/* 254 */       values.add(vit1.getHWNumber());
/* 255 */     } else if (this.vit1Ident.equals("swcompat_id")) {
/* 256 */       values.add(vit1.getCompatibilityNr());
/*     */     } 
/* 258 */     return values;
/*     */   }
/*     */   
/*     */   public String getIdentVal(VIT1Data vit1) {
/* 262 */     List<String> values = getIdentVals(vit1);
/* 263 */     int indx = 0;
/* 264 */     if (this.vit1Pos != null) {
/* 265 */       indx = (this.vit1Pos.intValue() >= 0) ? this.vit1Pos.intValue() : (values.size() + this.vit1Pos.intValue());
/*     */     }
/* 267 */     return (indx < values.size() && indx >= 0) ? values.get(indx) : null;
/*     */   }
/*     */   
/*     */   protected static String getHWIdentFromVIT1(VIT1Data vit1, List<SPSIdentType> idents) {
/* 271 */     String vit1HWIdent = "";
/*     */     
/* 273 */     for (int i = 0; i < idents.size(); i++) {
/* 274 */       SPSIdentType ident = idents.get(i);
/* 275 */       if (ident == null) {
/* 276 */         return null;
/*     */       }
/* 278 */       String attrVal = ident.getIdentVal(vit1);
/*     */       
/* 280 */       if (attrVal != null) {
/* 281 */         vit1HWIdent = vit1HWIdent + attrVal.trim() + ".";
/*     */       }
/*     */     } 
/* 284 */     if (vit1HWIdent.endsWith(".")) {
/* 285 */       vit1HWIdent = vit1HWIdent.substring(0, vit1HWIdent.length() - 1);
/*     */     }
/*     */     
/* 288 */     return vit1HWIdent;
/*     */   }
/*     */   
/*     */   public static boolean CheckFlexibleHWIdentifierForECUID(VIT1Data vit1, List idents, String hwIdents) {
/* 292 */     String vit1HWIdent = (idents == null || idents.isEmpty()) ? vit1.getHWNumber() : getHWIdentFromVIT1(vit1, idents);
/*     */     
/* 294 */     if (vit1HWIdent == null || hwIdents == null) {
/* 295 */       return false;
/*     */     }
/*     */     
/* 298 */     if (hwIdents.equals(vit1HWIdent) || hwIdents.equals(WILDCARD)) {
/* 299 */       return true;
/*     */     }
/*     */     
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   public static List getHardwareLocation(Integer id, String vit1Type, SPSSchemaAdapterGME adapter) {
/* 306 */     String key = id + ":" + vit1Type;
/* 307 */     return (List)StaticData.getInstance(adapter).getHWLocations().get(key);
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 311 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSIdentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */