/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.rpo.RPOServiceImpl;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPO;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.gme.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.gme.SPSControllerGME;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.gme.SPSControllerXML;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.gme.SPSLanguage;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.gme.SPSSchemaAdapterGME;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class XMLSupport
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(SPSControllerGME.class);
/*     */   private static boolean dblog = true;
/*  29 */   private static Map cache = new HashMap<Object, Object>();
/*     */   
/*     */   protected SPSControllerXML controller;
/*     */   protected SPSLanguage language;
/*     */   protected int vci;
/*     */   protected int idRPOString;
/*     */   protected int idModelDesignator;
/*     */   protected int idXML;
/*     */   protected String stringRPO;
/*     */   protected String modelDesignator;
/*     */   
/*     */   protected static void initCache(IDatabaseLink dblink, Connection conn, SPSSchemaAdapterGME adapter) {
/*  41 */     Set<Integer> controllers = new HashSet();
/*  42 */     cache.put(adapter, controllers);
/*  43 */     DBMS.PreparedStatement stmt = null;
/*  44 */     ResultSet rs = null;
/*     */     try {
/*  46 */       String sql = DBMS.getSQL(dblink, adapter.supportsSPSFunctions() ? "SELECT FunctionID FROM SPS_XML_Support" : "SELECT ControllerID FROM SPS_XML_Support");
/*  47 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  48 */       rs = stmt.executeQuery();
/*  49 */       while (rs.next()) {
/*  50 */         controllers.add(new Integer(rs.getInt(1)));
/*     */       }
/*  52 */     } catch (Exception e) {
/*  53 */       if (dblog) {
/*  54 */         log.debug("failed to query table 'SPS_XML_SUPPORT'.", e);
/*  55 */         dblog = false;
/*     */       } 
/*     */     } finally {
/*     */       try {
/*  59 */         if (rs != null) {
/*  60 */           rs.close();
/*     */         }
/*  62 */         if (stmt != null) {
/*  63 */           stmt.close();
/*     */         }
/*  65 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasXMLSupport(SPSSchemaAdapterGME adapter, IDatabaseLink dblink, Connection conn, int ControllerID) throws Exception {
/*  71 */     synchronized (XMLSupport.class) {
/*  72 */       if (cache.get(adapter) == null) {
/*  73 */         initCache(dblink, conn, adapter);
/*     */       }
/*  75 */       Set controllers = (Set)cache.get(adapter);
/*  76 */       return controllers.contains(new Integer(ControllerID));
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBuildFile(SPSSchemaAdapterGME adapter, String vin) {
/* 109 */     StringBuffer result = new StringBuffer();
/* 110 */     result.append(vin);
/*     */     
/* 112 */     try { RPOContainer rpos = RPOServiceImpl.getInstance().getRPOs(vin);
/* 113 */       if (rpos != null) {
/* 114 */         result.append("\r\n");
/* 115 */         result.append(rpos.getModelDesignator());
/* 116 */         String filter = filterRPO(adapter, Integer.valueOf(this.controller.getID()));
/* 117 */         Iterator<RPO> it = rpos.getRPOs().iterator();
/* 118 */         while (it.hasNext()) {
/* 119 */           RPO rpo = it.next();
/* 120 */           if (filter != null && filter.indexOf(rpo.getCode()) < 0)
/*     */             continue; 
/* 122 */           result.append("\r\n");
/* 123 */           result.append(rpo.getCode());
/*     */         } 
/*     */       }  }
/* 126 */     catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException v1) {  }
/* 127 */     catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.UnsupportedVINException v2) {}
/*     */     
/* 129 */     return result.toString();
/*     */   }
/*     */   
/*     */   public String getBuildFileSPS(String vin) {
/* 133 */     StringBuffer result = new StringBuffer();
/* 134 */     result.append(vin);
/* 135 */     result.append("\r\n");
/* 136 */     result.append(this.modelDesignator.trim());
/* 137 */     StringTokenizer tokenizer = new StringTokenizer(this.stringRPO.trim(), " ,");
/* 138 */     while (tokenizer.hasMoreTokens()) {
/* 139 */       result.append("\r\n");
/* 140 */       result.append(tokenizer.nextToken());
/*     */     } 
/* 142 */     return result.toString();
/*     */   }
/*     */   
/*     */   public XMLSupport(SPSSchemaAdapterGME adapter, SPSLanguage language, SPSControllerXML controller, int vci) throws Exception {
/* 146 */     this.controller = controller;
/* 147 */     this.vci = vci;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(SPSSchemaAdapterGME adapter, IDatabaseLink dblink, Connection conn, int vci) throws Exception {
/*     */     try {
/* 167 */       evaluateXMLVCI(dblink, conn);
/* 168 */       resolveRPOStringID(dblink, conn);
/* 169 */       resolveModelDesignatorID(dblink, conn);
/* 170 */       log.debug("xml-vci=" + vci + " (md=" + this.idModelDesignator + "/" + this.modelDesignator.trim() + ",rpo=" + this.idRPOString + "/" + this.stringRPO.trim() + ")");
/* 171 */     } catch (Exception x11551) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveModelDesignatorID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 176 */     DBMS.PreparedStatement stmt = null;
/* 177 */     ResultSet rs = null;
/*     */     try {
/* 179 */       String sql = DBMS.getSQL(dblink, "SELECT Model_Designator FROM Model_Designator WHERE Model_Designator_ID = ?");
/* 180 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 181 */       stmt.setInt(1, this.idModelDesignator);
/* 182 */       rs = stmt.executeQuery();
/* 183 */       while (rs.next()) {
/* 184 */         this.modelDesignator = rs.getString(1);
/*     */       }
/* 186 */     } catch (Exception e) {
/* 187 */       throw e;
/*     */     } finally {
/*     */       try {
/* 190 */         if (rs != null) {
/* 191 */           rs.close();
/*     */         }
/* 193 */         if (stmt != null) {
/* 194 */           stmt.close();
/*     */         }
/* 196 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveRPOStringID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 202 */     DBMS.PreparedStatement stmt = null;
/* 203 */     ResultSet rs = null;
/*     */     try {
/* 205 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_String FROM RPO_String WHERE RPO_String_ID = ?");
/* 206 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 207 */       stmt.setInt(1, this.idRPOString);
/* 208 */       rs = stmt.executeQuery();
/* 209 */       while (rs.next()) {
/* 210 */         this.stringRPO = rs.getString(1);
/*     */       }
/* 212 */     } catch (Exception e) {
/* 213 */       throw e;
/*     */     } finally {
/*     */       try {
/* 216 */         if (rs != null) {
/* 217 */           rs.close();
/*     */         }
/* 219 */         if (stmt != null) {
/* 220 */           stmt.close();
/*     */         }
/* 222 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evaluateXMLVCI(IDatabaseLink dblink, Connection conn) throws Exception {
/* 228 */     DBMS.PreparedStatement stmt = null;
/* 229 */     ResultSet rs = null;
/*     */     try {
/* 231 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 232 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 233 */       stmt.setInt(1, this.vci);
/* 234 */       rs = stmt.executeQuery();
/* 235 */       while (rs.next()) {
/* 236 */         this.idRPOString = rs.getInt(1);
/* 237 */         this.idModelDesignator = rs.getInt(2);
/* 238 */         this.idXML = rs.getInt(3);
/*     */       } 
/* 240 */     } catch (Exception e) {
/* 241 */       throw e;
/*     */     } finally {
/*     */       try {
/* 244 */         if (rs != null) {
/* 245 */           rs.close();
/*     */         }
/* 247 */         if (stmt != null) {
/* 248 */           stmt.close();
/*     */         }
/* 250 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String filterRPO(SPSSchemaAdapterGME adapter, Integer ecu) {
/* 256 */     Connection conn = null;
/* 257 */     DBMS.PreparedStatement stmt = null;
/* 258 */     ResultSet rs = null;
/*     */     try {
/* 260 */       conn = adapter.getDatabaseLink().requestConnection();
/* 261 */       String sql = DBMS.getSQL(adapter.getDatabaseLink(), "SELECT RPOFilter FROM SPS_RPOFilter WHERE ECUID = ?");
/* 262 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 263 */       stmt.setInt(1, ecu.intValue());
/* 264 */       rs = stmt.executeQuery();
/* 265 */       if (rs.next()) {
/* 266 */         return rs.getString(1);
/*     */       }
/* 268 */     } catch (Exception e) {
/* 269 */       log.error("failed to query table RPOFilter", e);
/*     */     } finally {
/*     */       try {
/* 272 */         if (rs != null) {
/* 273 */           rs.close();
/*     */         }
/* 275 */         if (stmt != null) {
/* 276 */           stmt.close();
/*     */         }
/* 278 */       } catch (Exception x) {}
/*     */       
/*     */       try {
/* 281 */         if (conn != null) {
/* 282 */           adapter.getDatabaseLink().releaseConnection(conn);
/*     */         }
/* 284 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 287 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\xml\XMLSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */