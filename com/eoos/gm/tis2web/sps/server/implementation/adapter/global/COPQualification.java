/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class COPQualification
/*     */ {
/*  19 */   private static Logger log = Logger.getLogger(SPSControllerVCI.class);
/*     */   
/*     */   public static final char WILDCARD_CHAR = '~';
/*     */   
/*     */   public static final String WILDCARD_STRING = "~";
/*     */   
/*     */   public static final int UNDEFINED = -1;
/*     */   
/*     */   public static final int VIN_MIN_SEQUENCE = 0;
/*     */   
/*     */   public static final int VIN_MAX_SEQUENCE = 999999;
/*     */   
/*     */   protected static List qualifications;
/*     */   
/*     */   protected static Map usage;
/*     */   
/*     */   protected Integer id;
/*     */   
/*     */   protected char my;
/*     */   protected char make;
/*     */   protected char line;
/*     */   protected char series;
/*     */   protected char engine;
/*     */   protected String preRPOCode;
/*     */   protected String postRPOCode;
/*     */   protected int VINPos;
/*     */   protected char VINValue;
/*     */   protected int VINBeginningSequence;
/*     */   protected int VINEndingSequence;
/*     */   
/*     */   COPQualification(ResultSet rs) throws SQLException {
/*  50 */     this.id = Integer.valueOf(rs.getInt(1));
/*  51 */     this.my = rs.getString(2).charAt(0);
/*  52 */     this.make = rs.getString(3).charAt(0);
/*  53 */     this.line = rs.getString(4).charAt(0);
/*  54 */     this.series = rs.getString(5).charAt(0);
/*  55 */     this.engine = rs.getString(6).charAt(0);
/*  56 */     this.preRPOCode = rs.getString(7).trim();
/*  57 */     this.postRPOCode = rs.getString(8).trim();
/*  58 */     this.VINPos = rs.getInt(9);
/*  59 */     this.VINValue = rs.getString(10).charAt(0);
/*  60 */     this.VINBeginningSequence = rs.getInt(11);
/*  61 */     this.VINEndingSequence = rs.getInt(12);
/*     */   }
/*     */   
/*     */   public static Set qualify(SPSSchemaAdapterGlobal adapter, SPSVehicle vehicle, String preRPOCode, String postRPOCode) {
/*  65 */     if (qualifications == null || usage == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     Set result = null;
/*  69 */     SPSVIN vin = (SPSVIN)vehicle.getVIN();
/*  70 */     int sequenceNo = -1;
/*  71 */     for (int i = 0; i < qualifications.size(); i++) {
/*  72 */       COPQualification q = qualifications.get(i);
/*  73 */       if (match(q.my, vin.getModelYear()) && match(q.make, vin.getMake()) && match(q.line, vin.getLine()) && match(q.series, vin.getSeries()) && match(q.engine, vin.getEngine()))
/*     */       {
/*  75 */         if (match(q.preRPOCode, preRPOCode) && match(q.postRPOCode, postRPOCode))
/*     */         {
/*  77 */           if (match(q.preRPOCode, preRPOCode) && match(q.postRPOCode, postRPOCode))
/*     */           {
/*  79 */             if (q.VINPos <= 0 || q.VINValue == vin.getPosition(q.VINPos)) {
/*     */ 
/*     */               
/*  82 */               if (sequenceNo == -1) {
/*  83 */                 sequenceNo = vin.getSequenceNo();
/*     */               }
/*  85 */               if (q.VINBeginningSequence == 0 || q.VINBeginningSequence <= sequenceNo)
/*     */               {
/*  87 */                 if (q.VINEndingSequence == 999999 || q.VINEndingSequence >= sequenceNo) {
/*     */ 
/*     */ 
/*     */                   
/*  91 */                   Set uses = (Set)usage.get(q.id);
/*  92 */                   if (uses != null)
/*  93 */                   { if (result == null) {
/*  94 */                       result = new HashSet();
/*     */                     }
/*  96 */                     result.addAll(uses); } 
/*     */                 }  } 
/*     */             }  }  }  } 
/*  99 */     }  if (result != null && log.isDebugEnabled()) {
/* 100 */       log.debug("VIN " + vin.toString() + " (cop usage index =" + dump(result) + ")");
/*     */     }
/* 102 */     return result;
/*     */   }
/*     */   
/*     */   protected static String dump(Set result) {
/* 106 */     StringBuffer buffer = new StringBuffer();
/* 107 */     buffer.append('[');
/* 108 */     for (Iterator<Integer> iter = result.iterator(); iter.hasNext(); ) {
/* 109 */       if (buffer.length() > 1) {
/* 110 */         buffer.append(',');
/*     */       }
/* 112 */       buffer.append(iter.next());
/*     */     } 
/* 114 */     buffer.append(']');
/* 115 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   protected static boolean match(char qualifier, char vin) {
/* 119 */     return (qualifier == '~' || qualifier == vin);
/*     */   }
/*     */   
/*     */   protected static boolean match(String qualifier, String rpo) {
/* 123 */     return ("~".equals(qualifier) || qualifier.equals(rpo));
/*     */   }
/*     */   
/*     */   public static void init(IDatabaseLink dblink) {
/* 127 */     qualifications = new ArrayList();
/* 128 */     Connection conn = null;
/* 129 */     DBMS.PreparedStatement stmt = null;
/* 130 */     ResultSet rs = null;
/*     */     try {
/* 132 */       conn = dblink.requestConnection();
/* 133 */       String sql = DBMS.getSQL(dblink, "SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers");
/* 134 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 135 */       rs = stmt.executeQuery();
/* 136 */       while (rs.next()) {
/* 137 */         COPQualification q = new COPQualification(rs);
/* 138 */         qualifications.add(q);
/*     */       } 
/* 140 */       log.info("pre-loaded cop controller table (" + qualifications.size() + " records).");
/* 141 */       if (qualifications.size() > 0) {
/* 142 */         rs.close();
/* 143 */         stmt.close();
/* 144 */         usage = new HashMap<Object, Object>();
/* 145 */         sql = DBMS.getSQL(dblink, "SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate");
/* 146 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 147 */         rs = stmt.executeQuery();
/* 148 */         while (rs.next()) {
/* 149 */           Integer usageIDX = Integer.valueOf(rs.getInt(1));
/* 150 */           Integer controllerIDX = Integer.valueOf(rs.getInt(2));
/* 151 */           Set<Integer> map = (Set)usage.get(controllerIDX);
/* 152 */           if (map == null) {
/* 153 */             map = new HashSet();
/* 154 */             usage.put(controllerIDX, map);
/*     */           } 
/* 156 */           map.add(usageIDX);
/*     */         } 
/* 158 */         log.info("pre-loaded cop usage associate table (" + usage.size() + " records).");
/*     */       } 
/* 160 */     } catch (Exception e) {
/*     */       
/* 162 */       qualifications = null;
/* 163 */       usage = null;
/*     */     } finally {
/*     */       try {
/* 166 */         if (rs != null) {
/* 167 */           rs.close();
/*     */         }
/* 169 */         if (stmt != null) {
/* 170 */           stmt.close();
/*     */         }
/* 172 */         if (conn != null) {
/* 173 */           dblink.releaseConnection(conn);
/*     */         }
/* 175 */       } catch (Exception x) {
/* 176 */         log.error("failed to clean-up: ", x);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\COPQualification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */