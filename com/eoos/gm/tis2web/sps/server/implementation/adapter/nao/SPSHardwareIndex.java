/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSHardwareIndex
/*     */ {
/*  16 */   private static Logger log = Logger.getLogger(SPSHardwareIndex.class); protected int id;
/*     */   protected String description;
/*     */   public int descriptionID;
/*     */   protected List parts;
/*     */   
/*     */   public static final class StaticData { public Map getHardware() {
/*  22 */       return this.hardware;
/*     */     }
/*     */     private Map hardware;
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  26 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  27 */       this.hardware = new HashMap<Object, Object>();
/*  28 */       DBMS.PreparedStatement stmt = null;
/*  29 */       ResultSet rs = null;
/*  30 */       Connection conn = null;
/*     */       try {
/*  32 */         conn = dblink.requestConnection();
/*  33 */         String sql = DBMS.getSQL(dblink, "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List");
/*  34 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  35 */         rs = stmt.executeQuery();
/*  36 */         while (rs.next()) {
/*  37 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  38 */           String hwlist = rs.getString(2);
/*  39 */           int description = rs.getInt(3);
/*  40 */           StringTokenizer tokenizer = new StringTokenizer(hwlist, ", ");
/*  41 */           List<SPSHardware> parts = new ArrayList();
/*     */           try {
/*  43 */             while (tokenizer.hasMoreTokens()) {
/*  44 */               String partno = tokenizer.nextToken();
/*  45 */               parts.add(new SPSHardware(2, partno));
/*     */             } 
/*  47 */             this.hardware.put(id, new SPSHardwareIndex(id.intValue(), description, parts));
/*  48 */           } catch (Exception x) {
/*  49 */             SPSHardwareIndex.log.warn("ignore hw-idx '" + id + "' (" + hwlist + ")");
/*     */           } 
/*     */         } 
/*  52 */       } catch (Exception e) {
/*  53 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  56 */           if (rs != null) {
/*  57 */             rs.close();
/*     */           }
/*  59 */           if (stmt != null) {
/*  60 */             stmt.close();
/*     */           }
/*  62 */           if (conn != null) {
/*  63 */             dblink.releaseConnection(conn);
/*     */           }
/*  65 */         } catch (Exception x) {
/*  66 */           SPSHardwareIndex.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  72 */       synchronized (adapter.getSyncObject()) {
/*  73 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  74 */         if (instance == null) {
/*  75 */           instance = new StaticData(adapter);
/*  76 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  78 */         return instance;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSHardwareIndex(int id, int descriptionID, List parts) {
/*  96 */     this.id = id;
/*  97 */     this.descriptionID = descriptionID;
/*  98 */     this.parts = parts;
/*     */   }
/*     */   
/*     */   void setDescription(SPSLanguage language, SPSSchemaAdapterNAO adapter) {
/* 102 */     this.description = SPSPartDescription.getDescription(language, this.descriptionID, adapter);
/*     */   }
/*     */   
/*     */   public int getID() {
/* 106 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 110 */     return this.description;
/*     */   }
/*     */   
/*     */   public int getDescriptionID() {
/* 114 */     return this.descriptionID;
/*     */   }
/*     */   
/*     */   public List getParts() {
/* 118 */     return this.parts;
/*     */   }
/*     */   
/*     */   public boolean contains(SPSHardware part) {
/* 122 */     for (int i = 0; i < this.parts.size(); i++) {
/* 123 */       SPSHardware hw = this.parts.get(i);
/* 124 */       if (hw.equals(part)) {
/* 125 */         return true;
/*     */       }
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(List<SPSHardware> parts) {
/* 132 */     for (int i = 0; i < parts.size(); i++) {
/* 133 */       SPSHardware part = parts.get(i);
/* 134 */       if (contains(part)) {
/* 135 */         return true;
/*     */       }
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 142 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 146 */     return (object != null && object instanceof SPSHardwareIndex && ((SPSHardwareIndex)object).id == this.id);
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 150 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */   
/*     */   static SPSHardwareIndex getHardwareIndex(int hardwareIndex, SPSSchemaAdapterNAO adapter) {
/* 154 */     return (SPSHardwareIndex)StaticData.getInstance(adapter).getHardware().get(Integer.valueOf(hardwareIndex));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSHardwareIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */