/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSControllerVCI;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSControllerXML;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSLanguage;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSPart;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.nao.SPSSchemaAdapterNAO;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class XMLConfiguration
/*     */ {
/*  18 */   private static Logger log = Logger.getLogger(SPSControllerVCI.class);
/*     */   
/*     */   public static final String TYPE4_FILENAME = "Type4App.dll";
/*     */   
/*     */   public static final String XML_FILENAME = "XMLFile.xml";
/*     */   public static final String BUILD_FILENAME = "BuildRecord.bld";
/*     */   public static final String ECUPIN_FILENAME = "ECUPIN.TXT";
/*     */   protected SPSControllerXML controller;
/*     */   protected SPSLanguage language;
/*     */   protected int vci;
/*     */   protected int idRPOString;
/*     */   protected int idModelDesignator;
/*     */   protected int idXML;
/*     */   protected String stringRPO;
/*     */   protected String modelDesignator;
/*     */   protected int originXML;
/*     */   protected int originType4;
/*     */   protected SPSPart partXML;
/*     */   protected SPSPart partType4;
/*     */   
/*     */   public String getBuildFile(String vin) {
/*  39 */     StringBuffer result = new StringBuffer();
/*  40 */     result.append(vin);
/*  41 */     if (this.modelDesignator != null) {
/*  42 */       result.append("\r\n");
/*  43 */       result.append(this.modelDesignator.trim());
/*     */     } 
/*  45 */     StringTokenizer tokenizer = new StringTokenizer(this.stringRPO.trim(), " ,");
/*  46 */     while (tokenizer.hasMoreTokens()) {
/*  47 */       result.append("\r\n");
/*  48 */       result.append(tokenizer.nextToken());
/*     */     } 
/*  50 */     return result.toString();
/*     */   }
/*     */   
/*     */   public String getModelDesignator() {
/*  54 */     return this.modelDesignator;
/*     */   }
/*     */   
/*     */   public int getOriginType4() {
/*  58 */     return this.originType4;
/*     */   }
/*     */   
/*     */   public int getOriginXML() {
/*  62 */     return this.originXML;
/*     */   }
/*     */   
/*     */   public String getStringRPO() {
/*  66 */     return this.stringRPO;
/*     */   }
/*     */   
/*     */   public SPSPart getPartType4() {
/*  70 */     return this.partType4;
/*     */   }
/*     */   
/*     */   public SPSPart getPartXML() {
/*  74 */     return this.partXML;
/*     */   }
/*     */   
/*     */   public XMLConfiguration(SPSSchemaAdapterNAO adapter, SPSLanguage language, SPSControllerXML controller, int vci) throws Exception {
/*  78 */     this.controller = controller;
/*  79 */     this.vci = vci;
/*  80 */     Connection conn = null;
/*     */     try {
/*  82 */       conn = adapter.getDatabaseLink().requestConnection();
/*  83 */       init(adapter, adapter.getDatabaseLink(), conn, vci);
/*  84 */     } catch (Exception e) {
/*  85 */       throw e;
/*     */     } finally {
/*     */       try {
/*  88 */         if (conn != null) {
/*  89 */           adapter.getDatabaseLink().releaseConnection(conn);
/*     */         }
/*  91 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(SPSSchemaAdapterNAO adapter, IDatabaseLink dblink, Connection conn, int vci) throws Exception {
/*  97 */     evaluateXMLVCI(dblink, conn);
/*  98 */     resolveRPOStringID(dblink, conn);
/*  99 */     resolveModelDesignatorID(dblink, conn);
/* 100 */     queryXMLParts(dblink, conn);
/* 101 */     log.debug("xml-vci=" + vci + " (origin=" + this.originXML + "/" + this.originType4 + ",md=" + this.idModelDesignator + "/" + this.modelDesignator.trim() + ",rpo=" + this.idRPOString + "/" + this.stringRPO.trim() + ")");
/* 102 */     this.partType4 = this.controller.getPart(this.originType4);
/* 103 */     this.partXML = this.controller.getPart(this.originXML);
/*     */   }
/*     */   
/*     */   protected void queryXMLParts(IDatabaseLink dblink, Connection conn) throws Exception {
/* 107 */     DBMS.PreparedStatement stmt = null;
/* 108 */     ResultSet rs = null;
/*     */     try {
/* 110 */       String sql = DBMS.getSQL(dblink, "SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?");
/* 111 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 112 */       stmt.setInt(1, this.idXML);
/* 113 */       rs = stmt.executeQuery();
/* 114 */       while (rs.next()) {
/* 115 */         this.originXML = rs.getInt(1);
/* 116 */         this.originType4 = rs.getInt(2);
/*     */       } 
/* 118 */     } catch (Exception e) {
/* 119 */       throw e;
/*     */     } finally {
/*     */       try {
/* 122 */         if (rs != null) {
/* 123 */           rs.close();
/*     */         }
/* 125 */         if (stmt != null) {
/* 126 */           stmt.close();
/*     */         }
/* 128 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveModelDesignatorID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 134 */     DBMS.PreparedStatement stmt = null;
/* 135 */     ResultSet rs = null;
/*     */     try {
/* 137 */       String sql = DBMS.getSQL(dblink, "SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?");
/* 138 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 139 */       stmt.setInt(1, this.idModelDesignator);
/* 140 */       rs = stmt.executeQuery();
/* 141 */       if (rs.next()) {
/* 142 */         this.modelDesignator = rs.getString(1);
/* 143 */         if (rs.wasNull())
/* 144 */           this.modelDesignator = null; 
/*     */       } 
/* 146 */     } catch (Exception e) {
/* 147 */       throw e;
/*     */     } finally {
/*     */       try {
/* 150 */         if (rs != null) {
/* 151 */           rs.close();
/*     */         }
/* 153 */         if (stmt != null) {
/* 154 */           stmt.close();
/*     */         }
/* 156 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveRPOStringID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 162 */     DBMS.PreparedStatement stmt = null;
/* 163 */     ResultSet rs = null;
/*     */     try {
/* 165 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?");
/* 166 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 167 */       stmt.setInt(1, this.idRPOString);
/* 168 */       rs = stmt.executeQuery();
/* 169 */       while (rs.next()) {
/* 170 */         this.stringRPO = rs.getString(1);
/*     */       }
/* 172 */     } catch (Exception e) {
/* 173 */       throw e;
/*     */     } finally {
/*     */       try {
/* 176 */         if (rs != null) {
/* 177 */           rs.close();
/*     */         }
/* 179 */         if (stmt != null) {
/* 180 */           stmt.close();
/*     */         }
/* 182 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evaluateXMLVCI(IDatabaseLink dblink, Connection conn) throws Exception {
/* 188 */     DBMS.PreparedStatement stmt = null;
/* 189 */     ResultSet rs = null;
/*     */     try {
/* 191 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 192 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 193 */       stmt.setInt(1, this.vci);
/* 194 */       rs = stmt.executeQuery();
/* 195 */       while (rs.next()) {
/* 196 */         this.idRPOString = rs.getInt(1);
/* 197 */         this.idModelDesignator = rs.getInt(2);
/* 198 */         this.idXML = rs.getInt(3);
/*     */       } 
/* 200 */     } catch (Exception e) {
/* 201 */       throw e;
/*     */     } finally {
/*     */       try {
/* 204 */         if (rs != null) {
/* 205 */           rs.close();
/*     */         }
/* 207 */         if (stmt != null) {
/* 208 */           stmt.close();
/*     */         }
/* 210 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\xml\XMLConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */