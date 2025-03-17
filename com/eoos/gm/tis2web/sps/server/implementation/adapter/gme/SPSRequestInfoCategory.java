/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSRequestInfoCategory
/*     */   extends SPSBaseCategory {
/*  20 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   private SPSSchemaAdapterGME adapter;
/*     */   
/*     */   public static final class StaticData {
/*  24 */     protected Map mapping = new HashMap<Object, Object>();
/*     */     
/*  26 */     protected IDatabaseLink dblink = null;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGME adapter) {
/*  29 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  30 */       this.dblink = db;
/*  31 */       Connection conn = null;
/*  32 */       DBMS.PreparedStatement stmt = null;
/*  33 */       ResultSet rs = null;
/*     */       try {
/*  35 */         conn = db.requestConnection();
/*  36 */         String sql = DBMS.getSQL(db, "SELECT ReqInfoID, ReqInfoMeth_Group FROM SPS_ReqInfo");
/*  37 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  38 */         rs = stmt.executeQuery();
/*  39 */         while (rs.next()) {
/*  40 */           Integer rid = Integer.valueOf(rs.getInt(1));
/*  41 */           Integer rimg = Integer.valueOf(rs.getInt(2));
/*  42 */           this.mapping.put(rid, rimg);
/*     */         } 
/*  44 */       } catch (Exception e) {
/*  45 */         this.dblink = null;
/*     */       } finally {
/*     */         try {
/*  48 */           if (rs != null) {
/*  49 */             rs.close();
/*     */           }
/*  51 */           if (stmt != null) {
/*  52 */             stmt.close();
/*     */           }
/*  54 */           if (conn != null) {
/*  55 */             db.releaseConnection(conn);
/*     */           }
/*  57 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGME adapter) {
/*  67 */       synchronized (adapter.getSyncObject()) {
/*  68 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  69 */         if (instance == null) {
/*  70 */           instance = new StaticData(adapter);
/*  71 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  73 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getMapping() {
/*  78 */       return this.mapping;
/*     */     }
/*     */     
/*     */     public IDatabaseLink getDBLink() {
/*  82 */       return this.dblink;
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean hasRequestInfo(SPSSchemaAdapterGME adapter) {
/*  87 */     return (StaticData.getInstance(adapter).getDBLink() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSRequestInfoCategory(SPSLanguage language, int rid, SPSOptionCategory category, SPSOptionGroup group, int order, SPSSchemaAdapterGME adapter) {
/*  93 */     super(language, rid, category, group, order);
/*  94 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   int getRequestMethodID() {
/*  98 */     return this.id;
/*     */   }
/*     */   
/*     */   Integer getRequestMethodGroupID() {
/* 102 */     return (Integer)StaticData.getInstance(this.adapter).getMapping().get(Integer.valueOf(this.id));
/*     */   }
/*     */   
/*     */   public static Integer getRequestMethodGroupID(Integer rid, SPSSchemaAdapterGME adapter) {
/* 106 */     return (Integer)StaticData.getInstance(adapter).getMapping().get(new Integer(rid.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean qualify(SPSSession session, List bcategories, int target) throws Exception {
/* 112 */     if (bcategories == null) {
/* 113 */       return true;
/*     */     }
/* 115 */     Iterator<SPSBaseCategory> it = bcategories.iterator();
/* 116 */     while (it.hasNext()) {
/* 117 */       SPSBaseCategory bcategory = it.next();
/* 118 */       boolean accept = false;
/* 119 */       if (bcategory.isEvaluated())
/*     */         continue; 
/* 121 */       if (bcategory.isOptionCategory()) {
/* 122 */         accept = checkOptionCategory(bcategory, (SPSVehicle)session.getVehicle());
/* 123 */       } else if (bcategory.getGroup() != null) {
/* 124 */         boolean isCALID = ((SPSSession)session).isCALID();
/* 125 */         boolean isHWOCheckDisabled = ((SPSSession)session).isHWOCheckDisabled();
/* 126 */         accept = checkOptionGroup(isCALID, isHWOCheckDisabled, (SPSVehicle)session.getVehicle(), null, bcategory);
/*     */       } else {
/* 128 */         bcategory.setEvaluated(true);
/*     */       } 
/* 130 */       if (!accept) {
/* 131 */         it.remove();
/*     */       }
/*     */     } 
/* 134 */     return (bcategories.size() > 0);
/*     */   }
/*     */   
/*     */   protected static boolean accept(SPSVehicle vehicle, SPSVINCode pattern) {
/* 138 */     SPSVIN vin = (SPSVIN)vehicle.getVIN();
/* 139 */     if (pattern.getWmi() != null && 
/* 140 */       !"#".equals(pattern.getWmi()) && !vin.matchWildcardWMI(pattern)) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     if (pattern.getVds() != null && 
/* 145 */       !"#".equals(pattern.getVds()) && !vin.matchWildcardVDS(pattern)) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     if (pattern.getVis() != null && 
/* 150 */       !"#".equals(pattern.getVis()) && !vin.matchWildcardVIS(pattern)) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void filterRequestInfoCategoriesVIN(List categories, Map vins, boolean hasRequestInfoCategories) throws Exception {
/* 165 */     if (categories.size() <= 1) {
/*     */       return;
/*     */     }
/* 168 */     HashSet<Integer> noncatchalls = new HashSet();
/* 169 */     Iterator<SPSRequestInfoCategory> it = categories.iterator();
/* 170 */     while (it.hasNext()) {
/* 171 */       Object key = it.next();
/* 172 */       Integer id = null;
/* 173 */       if (hasRequestInfoCategories) {
/* 174 */         SPSRequestInfoCategory rcategory = (SPSRequestInfoCategory)key;
/* 175 */         id = Integer.valueOf(rcategory.getID());
/*     */       } else {
/* 177 */         id = (Integer)key;
/*     */       } 
/* 179 */       SPSVINCode pattern = (SPSVINCode)vins.get(key);
/* 180 */       if (pattern.toString().indexOf('#') < 0) {
/* 181 */         noncatchalls.add(id); continue;
/* 182 */       }  if (!hasRequestInfoCategories) {
/* 183 */         it.remove();
/*     */       }
/*     */     } 
/* 186 */     if (!hasRequestInfoCategories) {
/* 187 */       if (categories.size() != 1) {
/* 188 */         log.debug("invalid request method data (check category assignments w/o options)");
/* 189 */         throw new SPSException(CommonException.ServerSideFailure);
/*     */       } 
/* 191 */     } else if (noncatchalls.size() > 0) {
/* 192 */       it = categories.iterator();
/* 193 */       while (it.hasNext()) {
/* 194 */         SPSRequestInfoCategory rcategory = it.next();
/* 195 */         SPSVINCode pattern = (SPSVINCode)vins.get(rcategory);
/* 196 */         if (pattern.toString().indexOf('#') >= 0 && 
/* 197 */           noncatchalls.contains(Integer.valueOf(rcategory.getID()))) {
/* 198 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List loadRequestInfoCategories(SPSLanguage language, SPSVehicle vehicle, Integer controller, SPSSchemaAdapterGME adapter) throws Exception {
/* 210 */     if (!hasRequestInfo(adapter)) {
/* 211 */       return null;
/*     */     }
/* 213 */     List<Integer> categories = new ArrayList();
/* 214 */     boolean hasRequestInfoCategories = false;
/* 215 */     Map<Object, Object> vins = new HashMap<Object, Object>();
/* 216 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 217 */     Connection conn = null;
/* 218 */     DBMS.PreparedStatement stmt = null;
/* 219 */     ResultSet rs = null;
/*     */     try {
/* 221 */       conn = dblink.requestConnection();
/* 222 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v, SPS_BaseVehOption o WHERE v.ReqInfoID = o.ReqInfoID (+)  AND v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder");
/* 223 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 224 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 225 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 226 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 227 */       stmt.setInt(4, controller.intValue());
/* 228 */       rs = stmt.executeQuery();
/* 229 */       while (rs.next()) {
/* 230 */         int rid = rs.getInt(1);
/* 231 */         if (StaticData.getInstance(adapter).getMapping().get(Integer.valueOf(rid)) == null) {
/* 232 */           log.error("invalid request method data: '" + rid + "'.");
/*     */           continue;
/*     */         } 
/* 235 */         String wmi = DBMS.trimString(rs.getString(2));
/* 236 */         String vds = DBMS.trimString(rs.getString(3));
/* 237 */         String vis = DBMS.trimString(rs.getString(4));
/* 238 */         SPSVINCode pattern = new SPSVINCode(wmi, vds, vis);
/* 239 */         if (!accept(vehicle, pattern)) {
/*     */           continue;
/*     */         }
/* 242 */         String categoryID = DBMS.trimString(rs.getString(5));
/* 243 */         if (rs.wasNull()) {
/* 244 */           if (categories.size() > 0 && hasRequestInfoCategories) {
/* 245 */             log.debug("invalid request method data (check category assignments)");
/* 246 */             throw new SPSException(CommonException.ServerSideFailure);
/*     */           } 
/* 248 */           log.debug("request method = " + rid);
/* 249 */           Integer rmg = (Integer)StaticData.getInstance(adapter).getMapping().get(Integer.valueOf(rid));
/* 250 */           categories.add(rmg);
/* 251 */           vins.put(rmg, pattern); continue;
/*     */         } 
/* 253 */         if (categories.size() > 0 && !hasRequestInfoCategories) {
/* 254 */           log.debug("invalid request method data (check category assignments)");
/* 255 */           throw new SPSException(CommonException.ServerSideFailure);
/*     */         } 
/* 257 */         categoryID = categoryID.trim();
/* 258 */         SPSOptionCategory category = SPSOptionCategory.getOptionCategory(categoryID, adapter);
/* 259 */         int groupID = rs.getInt(6);
/* 260 */         SPSOptionGroup group = SPSOptionGroup.getOptionGroup(categoryID, groupID, adapter);
/* 261 */         if (group == null) {
/* 262 */           log.error("unknown option group (load request method data): '" + groupID + "'.");
/*     */           continue;
/*     */         } 
/* 265 */         int order = rs.getInt(7);
/* 266 */         log.debug("request method = " + rid + " category = " + categoryID);
/*     */         
/* 268 */         SPSRequestInfoCategory rcategory = new SPSRequestInfoCategory(language, rid, category, group, order, adapter);
/* 269 */         categories.add(rcategory);
/* 270 */         vins.put(rcategory, pattern);
/* 271 */         hasRequestInfoCategories = true;
/*     */       }
/*     */     
/* 274 */     } catch (Exception e) {
/* 275 */       throw e;
/*     */     } finally {
/*     */       try {
/* 278 */         if (rs != null) {
/* 279 */           rs.close();
/*     */         }
/* 281 */         if (stmt != null) {
/* 282 */           stmt.close();
/*     */         }
/* 284 */         if (conn != null) {
/* 285 */           dblink.releaseConnection(conn);
/*     */         }
/* 287 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 290 */     filterRequestInfoCategoriesVIN(categories, vins, hasRequestInfoCategories);
/* 291 */     return (categories.size() == 0) ? null : categories;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 295 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSRequestInfoCategory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */