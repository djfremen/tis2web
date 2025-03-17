/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ 
/*     */ public class SPSECU {
/*     */   protected Integer ecu;
/*     */   protected String release;
/*     */   protected int validFrom;
/*     */   protected int validTo;
/*     */   protected boolean serviceECU;
/*     */   
/*     */   public static class Provider {
/*     */     private Provider(SPSSchemaAdapterGME adapter) {
/*  16 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  17 */       this.instances = new HashMap<Object, Object>();
/*  18 */       Connection conn = null;
/*  19 */       DBMS.PreparedStatement stmt = null;
/*  20 */       ResultSet rs = null;
/*     */       try {
/*  22 */         conn = db.requestConnection();
/*  23 */         String sql = DBMS.getSQL(db, "SELECT ECUID, ServiceECUFlag, ReleaseDate, ValidFrom, ValidTo FROM SPS_ECUDescription");
/*  24 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  25 */         rs = stmt.executeQuery();
/*  26 */         while (rs.next()) {
/*     */ 
/*     */           
/*  29 */           Integer ecu = Integer.valueOf(rs.getInt(1));
/*  30 */           boolean serviceECUFlag = (rs.getInt(2) != 0);
/*  31 */           String release = rs.getString(3).trim();
/*  32 */           int validFrom = Integer.parseInt(rs.getString(4).trim());
/*  33 */           int validTo = Integer.parseInt(rs.getString(5).trim());
/*  34 */           this.instances.put(ecu, new SPSECU(ecu, release, validFrom, validTo, serviceECUFlag));
/*     */         } 
/*  36 */       } catch (RuntimeException e) {
/*  37 */         throw e;
/*  38 */       } catch (Exception e) {
/*  39 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  42 */           if (rs != null) {
/*  43 */             rs.close();
/*     */           }
/*  45 */           if (stmt != null) {
/*  46 */             stmt.close();
/*     */           }
/*  48 */           if (conn != null) {
/*  49 */             db.releaseConnection(conn);
/*     */           }
/*  51 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */     private Map instances;
/*     */     
/*     */     public static Provider getInstance(SPSSchemaAdapterGME adapter) {
/*  57 */       synchronized (adapter.getSyncObject()) {
/*  58 */         Provider instance = (Provider)adapter.getObject(Provider.class);
/*  59 */         if (instance == null) {
/*  60 */           instance = new Provider(adapter);
/*  61 */           adapter.storeObject(Provider.class, instance);
/*     */         } 
/*  63 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getInstances() {
/*  68 */       return this.instances;
/*     */     }
/*     */     
/*     */     boolean accept(Integer id, String my) {
/*  72 */       return accept(id, Integer.parseInt(my));
/*     */     }
/*     */     
/*     */     boolean accept(Integer id, int my) {
/*  76 */       SPSECU ecu = getInstance(id);
/*  77 */       return (ecu != null && !ecu.isServiceECU() && ecu.getValidFrom() <= my && ecu.getValidTo() >= my);
/*     */     }
/*     */     
/*     */     SPSControllerGME compareReleaseDate(SPSControllerGME current, SPSControllerGME ecu) {
/*  81 */       if (current == null) {
/*  82 */         return ecu;
/*     */       }
/*  84 */       SPSECU actual = getInstance(Integer.valueOf(current.getID()));
/*  85 */       SPSECU competitor = getInstance(Integer.valueOf(ecu.getID()));
/*  86 */       if (actual == null)
/*  87 */         return ecu; 
/*  88 */       if (competitor == null) {
/*  89 */         return current;
/*     */       }
/*  91 */       return (actual.getRelease().compareTo(competitor.getRelease()) > 0) ? current : ecu;
/*     */     }
/*     */ 
/*     */     
/*     */     SPSECU getInstance(Integer ecu) {
/*  96 */       return (SPSECU)getInstances().get(ecu);
/*     */     }
/*     */     
/*     */     SPSECU getInstance(int ecu) {
/* 100 */       return (SPSECU)getInstances().get(Integer.valueOf(ecu));
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
/*     */   public SPSECU(Integer ecu, String release, int validFrom, int validTo, boolean serviceECU) {
/* 116 */     this.ecu = ecu;
/* 117 */     this.release = release;
/* 118 */     this.validFrom = validFrom;
/* 119 */     this.validTo = validTo;
/* 120 */     this.serviceECU = serviceECU;
/*     */   }
/*     */   
/*     */   public Integer getID() {
/* 124 */     return this.ecu;
/*     */   }
/*     */   
/*     */   public String getRelease() {
/* 128 */     return this.release;
/*     */   }
/*     */   
/*     */   public boolean isServiceECU() {
/* 132 */     return this.serviceECU;
/*     */   }
/*     */   
/*     */   public int getValidFrom() {
/* 136 */     return this.validFrom;
/*     */   }
/*     */   
/*     */   public int getValidTo() {
/* 140 */     return this.validTo;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 144 */     Provider.getInstance(adapter);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSECU.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */