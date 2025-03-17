/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class XMLConfiguration
/*     */ {
/*  12 */   private static Logger log = Logger.getLogger(SPSControllerVCI.class);
/*     */   
/*     */   public static final String TYPE4_FILENAME = "Type4App.dll";
/*     */   
/*     */   public static final String XML_FILENAME = "XMLFile.xml";
/*     */   public static final String BUILD_FILENAME = "BuildRecord.bld";
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
/*  32 */     StringBuffer result = new StringBuffer();
/*  33 */     result.append(vin);
/*  34 */     result.append("\r\n");
/*  35 */     result.append(this.modelDesignator.trim());
/*  36 */     StringTokenizer tokenizer = new StringTokenizer(this.stringRPO.trim(), " ,");
/*  37 */     while (tokenizer.hasMoreTokens()) {
/*  38 */       result.append("\r\n");
/*  39 */       result.append(tokenizer.nextToken());
/*     */     } 
/*  41 */     return result.toString();
/*     */   }
/*     */   
/*     */   public String getModelDesignator() {
/*  45 */     return this.modelDesignator;
/*     */   }
/*     */   
/*     */   public int getOriginType4() {
/*  49 */     return this.originType4;
/*     */   }
/*     */   
/*     */   public int getOriginXML() {
/*  53 */     return this.originXML;
/*     */   }
/*     */   
/*     */   public String getStringRPO() {
/*  57 */     return this.stringRPO;
/*     */   }
/*     */   
/*     */   public SPSPart getPartType4() {
/*  61 */     return this.partType4;
/*     */   }
/*     */   
/*     */   public SPSPart getPartXML() {
/*  65 */     return this.partXML;
/*     */   }
/*     */   
/*     */   public XMLConfiguration(SPSSchemaAdapterGlobal adapter, SPSLanguage language, SPSControllerXML controller, int vci) throws Exception {
/*  69 */     this.controller = controller;
/*  70 */     this.vci = vci;
/*  71 */     Connection conn = null;
/*     */     try {
/*  73 */       conn = adapter.getDatabaseLink().requestConnection();
/*  74 */       init(adapter, adapter.getDatabaseLink(), conn, vci);
/*  75 */     } catch (Exception e) {
/*  76 */       throw e;
/*     */     } finally {
/*     */       try {
/*  79 */         if (conn != null) {
/*  80 */           adapter.getDatabaseLink().releaseConnection(conn);
/*     */         }
/*  82 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(SPSSchemaAdapterGlobal adapter, IDatabaseLink dblink, Connection conn, int vci) throws Exception {
/*  88 */     evaluateXMLVCI(dblink, conn);
/*  89 */     resolveRPOStringID(dblink, conn);
/*  90 */     resolveModelDesignatorID(dblink, conn);
/*  91 */     queryXMLParts(dblink, conn);
/*  92 */     log.debug("xml-vci=" + vci + " (origin=" + this.originXML + "/" + this.originType4 + ",md=" + this.idModelDesignator + "/" + this.modelDesignator.trim() + ",rpo=" + this.idRPOString + "/" + this.stringRPO.trim() + ")");
/*  93 */     this.partType4 = this.controller.getPart(this.originType4);
/*  94 */     this.partXML = this.controller.getPart(this.originXML);
/*     */   }
/*     */   
/*     */   protected void queryXMLParts(IDatabaseLink dblink, Connection conn) throws Exception {
/*  98 */     DBMS.PreparedStatement stmt = null;
/*  99 */     ResultSet rs = null;
/*     */     try {
/* 101 */       String sql = DBMS.getSQL(dblink, "SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?");
/* 102 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 103 */       stmt.setInt(1, this.idXML);
/* 104 */       rs = stmt.executeQuery();
/* 105 */       while (rs.next()) {
/* 106 */         this.originXML = rs.getInt(1);
/* 107 */         this.originType4 = rs.getInt(2);
/*     */       } 
/* 109 */     } catch (Exception e) {
/* 110 */       throw e;
/*     */     } finally {
/*     */       try {
/* 113 */         if (rs != null) {
/* 114 */           rs.close();
/*     */         }
/* 116 */         if (stmt != null) {
/* 117 */           stmt.close();
/*     */         }
/* 119 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveModelDesignatorID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 125 */     DBMS.PreparedStatement stmt = null;
/* 126 */     ResultSet rs = null;
/*     */     try {
/* 128 */       String sql = DBMS.getSQL(dblink, "SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?");
/* 129 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 130 */       stmt.setInt(1, this.idModelDesignator);
/* 131 */       rs = stmt.executeQuery();
/* 132 */       while (rs.next()) {
/* 133 */         this.modelDesignator = rs.getString(1);
/*     */       }
/* 135 */     } catch (Exception e) {
/* 136 */       throw e;
/*     */     } finally {
/*     */       try {
/* 139 */         if (rs != null) {
/* 140 */           rs.close();
/*     */         }
/* 142 */         if (stmt != null) {
/* 143 */           stmt.close();
/*     */         }
/* 145 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resolveRPOStringID(IDatabaseLink dblink, Connection conn) throws Exception {
/* 151 */     DBMS.PreparedStatement stmt = null;
/* 152 */     ResultSet rs = null;
/*     */     try {
/* 154 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?");
/* 155 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 156 */       stmt.setInt(1, this.idRPOString);
/* 157 */       rs = stmt.executeQuery();
/* 158 */       while (rs.next()) {
/* 159 */         this.stringRPO = rs.getString(1);
/*     */       }
/* 161 */     } catch (Exception e) {
/* 162 */       throw e;
/*     */     } finally {
/*     */       try {
/* 165 */         if (rs != null) {
/* 166 */           rs.close();
/*     */         }
/* 168 */         if (stmt != null) {
/* 169 */           stmt.close();
/*     */         }
/* 171 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evaluateXMLVCI(IDatabaseLink dblink, Connection conn) throws Exception {
/* 177 */     DBMS.PreparedStatement stmt = null;
/* 178 */     ResultSet rs = null;
/*     */     try {
/* 180 */       String sql = DBMS.getSQL(dblink, "SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 181 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 182 */       stmt.setInt(1, this.vci);
/* 183 */       rs = stmt.executeQuery();
/* 184 */       while (rs.next()) {
/* 185 */         this.idRPOString = rs.getInt(1);
/* 186 */         this.idModelDesignator = rs.getInt(2);
/* 187 */         this.idXML = rs.getInt(3);
/*     */       } 
/* 189 */     } catch (Exception e) {
/* 190 */       throw e;
/*     */     } finally {
/*     */       try {
/* 193 */         if (rs != null) {
/* 194 */           rs.close();
/*     */         }
/* 196 */         if (stmt != null) {
/* 197 */           stmt.close();
/*     */         }
/* 199 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\XMLConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */