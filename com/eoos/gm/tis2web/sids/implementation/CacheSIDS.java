/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CacheSIDS
/*     */   implements Singleton
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(CacheSIDS.class);
/*     */   
/*     */   private static CacheSIDS instance;
/*     */   private List vehicles;
/*     */   private Map attributes;
/*     */   private Map values;
/*     */   private Map qualifiers;
/*     */   private boolean errorFlag;
/*     */   
/*     */   public List getBaseVehicles() {
/*  33 */     return this.vehicles;
/*     */   }
/*     */   
/*     */   private VehicleQualification getVehicleQualifier(Integer id) {
/*  37 */     return (VehicleQualification)this.qualifiers.get(id);
/*     */   }
/*     */   
/*     */   private VehicleAttribute getVehicleAttribute(Integer id) {
/*  41 */     return (VehicleAttribute)this.attributes.get(id);
/*     */   }
/*     */   
/*     */   private VehicleValue getVehicleValue(Integer id) {
/*  45 */     return (VehicleValue)this.values.get(id);
/*     */   }
/*     */   private CacheSIDS() {
/*     */     DatabaseLink databaseLink;
/*  49 */     IDatabaseLink db = null;
/*  50 */     Connection conn = null;
/*     */     try {
/*  52 */       databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.sids.service.db");
/*  53 */       conn = databaseLink.requestConnection();
/*  54 */       loadVehicleAttributes(conn);
/*  55 */       loadVehicleValues(conn);
/*  56 */       loadVehicleDescriptions(conn);
/*  57 */       loadBaseVehicles(conn);
/*  58 */       checkDataIntegrity();
/*  59 */       if (this.errorFlag) {
/*  60 */         if (this.vehicles != null) {
/*  61 */           this.vehicles.clear();
/*     */         }
/*  63 */         log.error("failed to load SIDS cache");
/*     */       } else {
/*  65 */         log.info("loaded SIDS cache");
/*     */       } 
/*  67 */     } catch (Exception e) {
/*  68 */       log.error("failed to initialize SIDS cache", e);
/*     */     } finally {
/*  70 */       if (conn != null) {
/*     */         try {
/*  72 */           databaseLink.releaseConnection(conn);
/*  73 */         } catch (Exception x) {}
/*     */       }
/*     */       
/*  76 */       if (databaseLink != null && 
/*  77 */         !databaseLink.isDataSource()) {
/*  78 */         databaseLink.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized CacheSIDS getInstance() {
/*  85 */     if (instance == null) {
/*  86 */       instance = new CacheSIDS();
/*     */     }
/*  88 */     return instance;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/*  92 */     instance = new CacheSIDS();
/*     */   }
/*     */   
/*     */   private void loadBaseVehicles(Connection conn) throws SQLException {
/*  96 */     Statement stmt = null;
/*  97 */     ResultSet rs = null;
/*  98 */     this.vehicles = new ArrayList();
/*     */     try {
/* 100 */       String sql = "select distinct SERVICECODE, BASEVEHICLEID, WMI, VDS, MODELYEAR, VIS, ADD_VEHID from SC_BASEVEHICLE";
/* 101 */       stmt = conn.createStatement();
/* 102 */       rs = stmt.executeQuery(sql);
/* 103 */       while (rs.next()) {
/* 104 */         String sid = rs.getString("SERVICECODE").toUpperCase(Locale.ENGLISH).trim();
/* 105 */         if (!sid.startsWith("SID_")) {
/* 106 */           sid = "SID_" + sid;
/*     */         }
/* 108 */         ServiceID serviceID = ServiceIDImpl.getInstance(sid);
/* 109 */         VehicleQualification qualifier = null;
/* 110 */         int qualifierID = rs.getInt("ADD_VEHID");
/* 111 */         if (!rs.wasNull()) {
/* 112 */           qualifier = getVehicleQualifier(Integer.valueOf(qualifierID));
/* 113 */           if (qualifier == null) {
/* 114 */             this.errorFlag = true;
/* 115 */             log.error("unresolved ADD_VEHID reference: " + qualifierID + " (vehicle " + rs.getInt("BASEVEHICLEID") + ")");
/*     */             continue;
/*     */           } 
/*     */         } 
/* 119 */         BaseVehicle vehicle = new BaseVehicle(serviceID, rs.getInt("BASEVEHICLEID"), rs.getString("WMI"), rs.getString("VDS"), rs.getString("MODELYEAR"), rs.getString("VIS"), qualifier);
/* 120 */         this.vehicles.add(vehicle);
/*     */       } 
/*     */     } finally {
/* 123 */       if (rs != null) {
/*     */         try {
/* 125 */           rs.close();
/* 126 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 129 */       if (stmt != null) {
/*     */         try {
/* 131 */           stmt.close();
/* 132 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locale determineLocale(String table, String locale) {
/* 141 */     if (locale.indexOf('_') > 0) {
/* 142 */       if (locale.length() != 5) {
/* 143 */         this.errorFlag = true;
/* 144 */         log.error("invalid locale: " + locale + " (table " + table + ")");
/*     */       } else {
/* 146 */         return new Locale(locale.substring(0, 2), locale.substring(3));
/*     */       } 
/* 148 */     } else if (locale.length() != 2) {
/* 149 */       this.errorFlag = true;
/* 150 */       log.error("invalid locale: " + locale + " (table " + table + ")");
/*     */     } else {
/* 152 */       return new Locale(locale);
/*     */     } 
/* 154 */     return Locale.US;
/*     */   }
/*     */   
/*     */   private void loadVehicleAttributes(Connection conn) throws SQLException {
/* 158 */     Statement stmt = null;
/* 159 */     ResultSet rs = null;
/* 160 */     this.attributes = new HashMap<Object, Object>();
/*     */     try {
/* 162 */       String sql = "select ATTRID, LOCALE, DESCRIPTION from SC_ATTRIBUTES";
/* 163 */       stmt = conn.createStatement();
/* 164 */       rs = stmt.executeQuery(sql);
/* 165 */       while (rs.next()) {
/* 166 */         Integer attrID = Integer.valueOf(rs.getInt("ATTRID"));
/* 167 */         VehicleAttribute attribute = (VehicleAttribute)this.attributes.get(attrID);
/* 168 */         if (attribute == null) {
/* 169 */           attribute = new VehicleAttribute(attrID);
/* 170 */           this.attributes.put(attrID, attribute);
/*     */         } 
/*     */         try {
/* 173 */           attribute.add(determineLocale("SC_ATTRIBUTES", rs.getString("LOCALE").trim()), rs.getString("DESCRIPTION"));
/* 174 */         } catch (DataModelException e) {
/* 175 */           this.errorFlag = true;
/* 176 */           log.error(e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 180 */       if (rs != null) {
/*     */         try {
/* 182 */           rs.close();
/* 183 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 186 */       if (stmt != null) {
/*     */         try {
/* 188 */           stmt.close();
/* 189 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVehicleValues(Connection conn) throws SQLException {
/* 196 */     Statement stmt = null;
/* 197 */     ResultSet rs = null;
/* 198 */     this.values = new HashMap<Object, Object>();
/*     */     try {
/* 200 */       String sql = "select VALUEID, LOCALE, DESCRIPTION from SC_VALUES";
/* 201 */       stmt = conn.createStatement();
/* 202 */       rs = stmt.executeQuery(sql);
/* 203 */       while (rs.next()) {
/* 204 */         Integer valueID = Integer.valueOf(rs.getInt("VALUEID"));
/* 205 */         VehicleValue value = (VehicleValue)this.values.get(valueID);
/* 206 */         if (value == null) {
/* 207 */           value = new VehicleValue(valueID);
/* 208 */           this.values.put(valueID, value);
/*     */         } 
/*     */         try {
/* 211 */           value.add(determineLocale("SC_VALUES", rs.getString("LOCALE").trim()), rs.getString("DESCRIPTION"));
/* 212 */         } catch (DataModelException e) {
/* 213 */           this.errorFlag = true;
/* 214 */           log.error(e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 218 */       if (rs != null) {
/*     */         try {
/* 220 */           rs.close();
/* 221 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 224 */       if (stmt != null) {
/*     */         try {
/* 226 */           stmt.close();
/* 227 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVehicleDescriptions(Connection conn) throws SQLException {
/* 234 */     Statement stmt = null;
/* 235 */     ResultSet rs = null;
/* 236 */     this.qualifiers = new HashMap<Object, Object>();
/*     */     try {
/* 238 */       String sql = "select ADD_VEHID, ATTRID, VALUEID from SC_ADDVEHDESCR";
/* 239 */       stmt = conn.createStatement();
/* 240 */       rs = stmt.executeQuery(sql);
/* 241 */       while (rs.next()) {
/* 242 */         Integer descriptionID = Integer.valueOf(rs.getInt("ADD_VEHID"));
/* 243 */         Integer attrID = Integer.valueOf(rs.getInt("ATTRID"));
/* 244 */         VehicleAttribute attribute = getVehicleAttribute(attrID);
/* 245 */         if (attribute == null) {
/* 246 */           this.errorFlag = true;
/* 247 */           log.error("unknown vehicle attribute encountered (add_veh_id=" + descriptionID + ",attrid=" + attrID + ")");
/*     */           continue;
/*     */         } 
/* 250 */         Integer valueID = Integer.valueOf(rs.getInt("VALUEID"));
/* 251 */         VehicleValue value = getVehicleValue(valueID);
/* 252 */         if (value == null) {
/* 253 */           this.errorFlag = true;
/* 254 */           log.error("unknown vehicle value encountered (add_veh_id=" + descriptionID + ",valueid=" + valueID + ")");
/*     */           continue;
/*     */         } 
/* 257 */         VehicleQualification qualifier = (VehicleQualification)this.qualifiers.get(descriptionID);
/* 258 */         if (qualifier == null) {
/* 259 */           qualifier = new VehicleQualification(descriptionID);
/* 260 */           this.qualifiers.put(descriptionID, qualifier);
/*     */         } 
/*     */         try {
/* 263 */           qualifier.add(attribute, value);
/* 264 */         } catch (DataModelException e) {
/* 265 */           this.errorFlag = true;
/* 266 */           log.error(e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 270 */       if (rs != null) {
/*     */         try {
/* 272 */           rs.close();
/* 273 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 276 */       if (stmt != null) {
/*     */         try {
/* 278 */           stmt.close();
/* 279 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkDataIntegrity() {
/* 286 */     for (int i = 0; i < this.vehicles.size(); i++) {
/* 287 */       BaseVehicle vehicle = this.vehicles.get(i);
/* 288 */       if (!checkDataIntegrity(vehicle)) {
/* 289 */         this.errorFlag = true;
/* 290 */         log.error("Data Integrity Violation: " + vehicle);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean checkDataIntegrity(BaseVehicle vehicle) {
/* 296 */     for (int i = 0; i < this.vehicles.size(); i++) {
/* 297 */       BaseVehicle v = this.vehicles.get(i);
/* 298 */       if (v != vehicle)
/*     */       {
/*     */         
/* 301 */         if (vehicle.getWmi().equals(v.getWmi()) && 
/* 302 */           match(vehicle.getVds(), v.getVds()) && match(vehicle.getMy(), v.getMy()) && match(vehicle.getVds(), v.getVds())) {
/* 303 */           if (vehicle.getQualifier() == null || v.getQualifier() == null)
/* 304 */             return false; 
/* 305 */           if (vehicle.getQualifier() == v.getQualifier())
/* 306 */             return false; 
/* 307 */           if (vehicle.getQualifier().equals(v.getQualifier())) {
/* 308 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 313 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean match(String valueA, String valueB) {
/* 317 */     if (valueA == null)
/* 318 */       return (valueB == null); 
/* 319 */     if (valueB == null)
/* 320 */       return (valueA == null); 
/* 321 */     if (valueA.equals(valueB)) {
/* 322 */       return true;
/*     */     }
/* 324 */     return sharedPrefix(valueA, valueB);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean sharedPrefix(String valueA, String valueB) {
/* 329 */     if (valueA.indexOf('#') >= 0 || valueB.indexOf('#') >= 0) {
/* 330 */       return false;
/*     */     }
/* 332 */     for (int i = 0; i < Math.min(valueA.length(), valueB.length()); i++) {
/* 333 */       char a = valueA.charAt(i);
/* 334 */       char b = valueB.charAt(i);
/* 335 */       if (a == '*' || b == '*')
/* 336 */         return true; 
/* 337 */       if (a != b) {
/* 338 */         return false;
/*     */       }
/*     */     } 
/* 341 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\CacheSIDS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */