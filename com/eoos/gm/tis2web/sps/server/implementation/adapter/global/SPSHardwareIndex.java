/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSHardwareIndex {
/*     */   protected int id;
/*     */   protected String description;
/*  17 */   private static Logger log = Logger.getLogger(SPSHardwareIndex.class);
/*     */   public int descriptionID;
/*     */   protected List parts;
/*     */   
/*     */   public static final class StaticData {
/*     */     public Map getHardware() {
/*  23 */       return this.hardware;
/*     */     }
/*     */     private Map hardware;
/*     */     private Map hardwareVCI;
/*     */     
/*     */     public Map getHardwareVCI() {
/*  29 */       return this.hardwareVCI;
/*     */     }
/*     */     
/*     */     @SuppressWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  34 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  35 */       this.hardware = new HashMap<Object, Object>();
/*  36 */       DBMS.PreparedStatement stmt = null;
/*  37 */       ResultSet rs = null;
/*  38 */       Connection conn = null;
/*     */       try {
/*  40 */         conn = dblink.requestConnection();
/*  41 */         String sql = DBMS.getSQL(dblink, "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List");
/*  42 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  43 */         rs = stmt.executeQuery();
/*  44 */         while (rs.next()) {
/*  45 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  46 */           String hwlist = rs.getString(2);
/*  47 */           int description = rs.getInt(3);
/*  48 */           StringTokenizer tokenizer = new StringTokenizer(hwlist, ", ");
/*  49 */           List<SPSHardware> parts = new ArrayList();
/*     */           try {
/*  51 */             while (tokenizer.hasMoreTokens()) {
/*  52 */               String partno = tokenizer.nextToken();
/*  53 */               parts.add(new SPSHardware(Integer.valueOf(partno)));
/*     */             } 
/*  55 */             this.hardware.put(id, new SPSHardwareIndex(id.intValue(), description, parts));
/*  56 */           } catch (Exception x) {
/*  57 */             SPSHardwareIndex.log.warn("ignore hw-idx '" + id + "' (" + hwlist + ")");
/*     */           } 
/*     */         } 
/*  60 */       } catch (Exception e) {
/*  61 */         this.hardware = null;
/*  62 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  65 */           if (rs != null) {
/*  66 */             rs.close();
/*     */           }
/*  68 */           if (stmt != null) {
/*  69 */             stmt.close();
/*     */           }
/*  71 */           if (this.hardware == null && conn != null) {
/*  72 */             dblink.releaseConnection(conn);
/*     */           }
/*  74 */         } catch (Exception x) {
/*  75 */           SPSHardwareIndex.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*  78 */       this.hardwareVCI = new HashMap<Object, Object>();
/*     */       try {
/*  80 */         conn = dblink.requestConnection();
/*  81 */         String sql = DBMS.getSQL(dblink, "SELECT VCI, Hardware_Indx FROM VCI_Hardware");
/*  82 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  83 */         rs = stmt.executeQuery();
/*  84 */         while (rs.next()) {
/*  85 */           Integer vci = Integer.valueOf(rs.getInt(1));
/*  86 */           Integer id = Integer.valueOf(rs.getInt(2));
/*  87 */           if (id.intValue() == 0) {
/*     */             continue;
/*     */           }
/*  90 */           Object hw = this.hardware.get(id);
/*  91 */           if (id == null) {
/*  92 */             SPSHardwareIndex.log.error("VCI_Hardware references invalid Hardware Index: " + id); continue;
/*     */           } 
/*  94 */           this.hardwareVCI.put(vci, hw);
/*     */         }
/*     */       
/*  97 */       } catch (Exception e) {
/*  98 */         SPSHardwareIndex.log.warn("VCI_Hardware not available");
/*     */       } finally {
/*     */         try {
/* 101 */           if (rs != null) {
/* 102 */             rs.close();
/*     */           }
/* 104 */           if (stmt != null) {
/* 105 */             stmt.close();
/*     */           }
/* 107 */           if (conn != null) {
/* 108 */             dblink.releaseConnection(conn);
/*     */           }
/* 110 */         } catch (Exception x) {
/* 111 */           SPSHardwareIndex.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/* 117 */       synchronized (adapter.getSyncObject()) {
/* 118 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 119 */         if (instance == null) {
/* 120 */           instance = new StaticData(adapter);
/* 121 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 123 */         return instance;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSHardwareIndex(int id, int descriptionID, List parts) {
/* 141 */     this.id = id;
/* 142 */     this.descriptionID = descriptionID;
/* 143 */     this.parts = parts;
/*     */   }
/*     */   
/*     */   void setDescription(SPSLanguage language, SPSSchemaAdapterGlobal adapter) {
/* 147 */     this.description = SPSPartDescription.getDescription(language, this.descriptionID, adapter);
/*     */   }
/*     */   
/*     */   public int getID() {
/* 151 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 155 */     return this.description;
/*     */   }
/*     */   
/*     */   public int getDescriptionID() {
/* 159 */     return this.descriptionID;
/*     */   }
/*     */   
/*     */   public List getParts() {
/* 163 */     return this.parts;
/*     */   }
/*     */   
/*     */   public boolean contains(SPSHardware part) {
/* 167 */     for (int i = 0; i < this.parts.size(); i++) {
/* 168 */       SPSHardware hw = this.parts.get(i);
/* 169 */       if (hw.equals(part)) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(String part) {
/* 177 */     for (int i = 0; i < this.parts.size(); i++) {
/* 178 */       SPSHardware hw = this.parts.get(i);
/* 179 */       if (hw.toString().equals(part)) {
/* 180 */         return true;
/*     */       }
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(List<SPSHardware> parts) {
/* 187 */     for (int i = 0; i < parts.size(); i++) {
/* 188 */       SPSHardware part = parts.get(i);
/* 189 */       if (contains(part)) {
/* 190 */         return true;
/*     */       }
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 197 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 201 */     return (object != null && object instanceof SPSHardwareIndex && ((SPSHardwareIndex)object).id == this.id);
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 205 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */   
/*     */   static SPSHardwareIndex getHardwareIndex(int hardwareIndex, SPSSchemaAdapterGlobal adapter) {
/* 209 */     return (SPSHardwareIndex)StaticData.getInstance(adapter).getHardware().get(Integer.valueOf(hardwareIndex));
/*     */   }
/*     */   
/*     */   static SPSHardwareIndex getHardwareIndexVCI(int vci, SPSSchemaAdapterGlobal adapter) {
/* 213 */     return (SPSHardwareIndex)StaticData.getInstance(adapter).getHardwareVCI().get(Integer.valueOf(vci));
/*     */   }
/*     */   
/*     */   public static boolean checkHardware(SPSHardwareIndex hwidx, VIT1Data vit1) {
/* 217 */     if (hwidx != null) {
/* 218 */       String hw = vit1.getHWNumber();
/* 219 */       return hwidx.contains(hw);
/*     */     } 
/* 221 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSHardwareIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */