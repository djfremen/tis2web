/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSSoftware implements History {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected String release;
/*     */   protected int ecu;
/*     */   
/*     */   public static final class StaticData {
/*     */     private Map idents;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGME adapter) {
/*  26 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  27 */       this.idents = new HashMap<Object, Object>();
/*  28 */       Connection conn = null;
/*  29 */       DBMS.PreparedStatement stmt = null;
/*  30 */       ResultSet rs = null;
/*     */       try {
/*  32 */         conn = db.requestConnection();
/*  33 */         String sql = DBMS.getSQL(db, "SELECT DISTINCT IdentType, VITType, VIT1Ident, VIT1Pos FROM SPS_IdentDescription");
/*  34 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  35 */         rs = stmt.executeQuery();
/*  36 */         while (rs.next()) {
/*  37 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  38 */           String vit1Type = DBMS.trimString(rs.getString(2));
/*  39 */           String vit1Ident = DBMS.trimString(rs.getString(3));
/*     */ 
/*     */           
/*  42 */           Integer vit1Pos = Integer.valueOf(rs.getInt(4));
/*  43 */           String key = vit1Ident.toUpperCase(Locale.ENGLISH) + ":" + vit1Type.toUpperCase(Locale.ENGLISH);
/*  44 */           this.idents.put(key, id);
/*  45 */           SPSIdentType ident = new SPSIdentType(id, vit1Ident, vit1Pos, null);
/*  46 */           key = id + ":" + vit1Type.toUpperCase(Locale.ENGLISH);
/*  47 */           this.idents.put(key, ident);
/*     */         } 
/*  49 */       } catch (RuntimeException e) {
/*  50 */         throw e;
/*  51 */       } catch (Exception e) {
/*  52 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  55 */           if (rs != null) {
/*  56 */             rs.close();
/*     */           }
/*  58 */           if (stmt != null) {
/*  59 */             stmt.close();
/*     */           }
/*  61 */           if (conn != null) {
/*  62 */             db.releaseConnection(conn);
/*     */           }
/*  64 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGME adapter) {
/*  74 */       synchronized (adapter.getSyncObject()) {
/*  75 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  76 */         if (instance == null) {
/*  77 */           instance = new StaticData(adapter);
/*  78 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  80 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getIdents() {
/*  85 */       return this.idents;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected List attributes = new ArrayList();
/*     */   
/*     */   protected String vmecuhn;
/*     */   
/*     */   protected String ssecusvn;
/*     */   
/*     */   protected String description;
/*     */   
/*     */   SPSSoftware(String archive) {
/* 102 */     this.description = archive;
/*     */   }
/*     */   
/*     */   SPSSoftware(String release, int ecu) {
/* 106 */     this.release = release;
/* 107 */     this.ecu = ecu;
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/* 111 */     return this.attributes;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 115 */     return this.description;
/*     */   }
/*     */   
/*     */   public int getID() {
/* 119 */     return this.ecu;
/*     */   }
/*     */   
/*     */   public String getReleaseDate() {
/* 123 */     return this.release;
/*     */   }
/*     */   
/*     */   public String getVMECUHN() {
/* 127 */     return this.vmecuhn;
/*     */   }
/*     */   
/*     */   public String getSSECUSVN() {
/* 131 */     return this.ssecusvn;
/*     */   }
/*     */   
/*     */   void setAttribute(Pair attribute) {
/* 135 */     this.attributes.add(attribute);
/*     */   }
/*     */   
/*     */   void setDescription(String description) {
/* 139 */     this.description = description;
/*     */   }
/*     */   
/*     */   void setVMECUHN(String vmecuhn) {
/* 143 */     this.vmecuhn = vmecuhn;
/*     */   }
/*     */   
/*     */   void setSSECUSVN(String ssecusvn) {
/* 147 */     this.ssecusvn = ssecusvn;
/*     */   }
/*     */   
/*     */   boolean identical(SPSSoftware software) {
/* 151 */     if (software == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     for (Iterator<Pair> iter = this.attributes.iterator(); iter.hasNext(); ) {
/* 155 */       Pair ident = iter.next();
/* 156 */       if (!software.match(ident)) {
/* 157 */         return false;
/*     */       }
/*     */     } 
/* 160 */     return (software.getAttributes().size() == this.attributes.size());
/*     */   }
/*     */   
/*     */   protected boolean match(Pair target) {
/* 164 */     for (Iterator<Pair> iter = this.attributes.iterator(); iter.hasNext(); ) {
/* 165 */       Pair ident = iter.next();
/* 166 */       if (ident.getFirst() == null || ident.getSecond() == null) {
/* 167 */         return false;
/*     */       }
/* 169 */       if (ident.getFirst().equals(target.getFirst()) && ident.getSecond().equals(target.getSecond())) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   static SPSSoftware load(SPSLanguage language, VIT1Data vit1, List<SPSIdentType> identTypeList, SPSSchemaAdapterGME adapter) throws Exception {
/* 177 */     if (identTypeList == null || identTypeList.size() == 0) {
/* 178 */       return null;
/*     */     }
/* 180 */     SPSSoftware software = new SPSSoftware("", 0);
/* 181 */     for (int i = 0; i < identTypeList.size(); i++) {
/* 182 */       SPSIdentType ident = identTypeList.get(i);
/* 183 */       String value = ident.getIdentValue(vit1);
/* 184 */       String label = ident.getDisplay(language, adapter);
/* 185 */       PairImpl pairImpl = new PairImpl(label, value);
/* 186 */       software.setAttribute((Pair)pairImpl);
/*     */     } 
/* 188 */     return software;
/*     */   }
/*     */   
/*     */   static SPSSoftware load(SPSLanguage language, VIT1Data vit1, int ecu, SPSSchemaAdapterGME adapter) throws Exception {
/* 192 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 193 */     Connection conn = null;
/* 194 */     DBMS.PreparedStatement stmt = null;
/* 195 */     ResultSet rs = null;
/*     */     try {
/* 197 */       conn = dblink.requestConnection();
/* 198 */       String sql = DBMS.getSQL(dblink, "SELECT id.Display, es.IdentValue, ed.ReleaseDate, es.ECUID, id.IdentType FROM SPS_ECUSoftware es, SPS_ECUDescription ed, SPS_IdentDisplay id WHERE es.ECUID = ed.ECUID  AND es.IdentType = id.IdentType  AND id.LanguageID = ?  AND es.ECUID IN (#list#) ORDER BY ed.ReleaseDate DESC, id.IdentType", 1);
/* 199 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 200 */       stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 201 */       stmt.setInt(2, ecu);
/* 202 */       rs = stmt.executeQuery();
/* 203 */       SPSSoftware software = null;
/* 204 */       Integer ssecusvn = (Integer)StaticData.getInstance(adapter).getIdents().get("SSECUSVN:A7");
/* 205 */       Integer vmecuhn = (Integer)StaticData.getInstance(adapter).getIdents().get("VMECUHN:A7");
/* 206 */       while (rs.next()) {
/* 207 */         String label = DBMS.getString(dblink, language, rs, 1);
/* 208 */         String value = DBMS.trimString(rs.getString(2));
/* 209 */         String release = DBMS.trimString(rs.getString(3));
/* 210 */         int itype = rs.getInt(5);
/*     */         
/* 212 */         if (vit1 != null) {
/* 213 */           String vit1Value = extractIdentValue(itype, vit1, adapter);
/* 214 */           if (vit1Value == null || !vit1Value.equals(value))
/*     */           {
/* 216 */             if ("*".equals(value)) {
/* 217 */               value = value + vit1Value;
/*     */             } else {
/*     */               continue;
/*     */             }  } 
/*     */         } 
/* 222 */         PairImpl pairImpl = new PairImpl(label, value);
/* 223 */         if (software == null) {
/* 224 */           software = new SPSSoftware(release, ecu);
/*     */         }
/* 226 */         software.setAttribute((Pair)pairImpl);
/* 227 */         if (ssecusvn != null && itype == ssecusvn.intValue()) {
/* 228 */           software.setSSECUSVN((String)pairImpl.getSecond()); continue;
/* 229 */         }  if (vmecuhn != null && itype == vmecuhn.intValue()) {
/* 230 */           software.setVMECUHN((String)pairImpl.getSecond());
/*     */         }
/*     */       } 
/* 233 */       return software;
/* 234 */     } catch (Exception e) {
/* 235 */       throw e;
/*     */     } finally {
/*     */       try {
/* 238 */         if (rs != null) {
/* 239 */           rs.close();
/*     */         }
/* 241 */         if (stmt != null) {
/* 242 */           stmt.close();
/*     */         }
/* 244 */         if (conn != null) {
/* 245 */           dblink.releaseConnection(conn);
/*     */         }
/* 247 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String extractIdentValue(int itype, VIT1Data vit1, SPSSchemaAdapterGME adapter) {
/* 253 */     String key = itype + ":" + vit1.getID().toUpperCase(Locale.ENGLISH);
/* 254 */     SPSIdentType ident = (SPSIdentType)StaticData.getInstance(adapter).getIdents().get(key);
/* 255 */     if (ident != null) {
/* 256 */       return ident.getIdentValue(vit1);
/*     */     }
/* 258 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 263 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSoftware.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */