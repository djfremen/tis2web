/*     */ package com.eoos.gm.tis2web.sids.implementation.test;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sids.implementation.DBReader;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDService;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDServiceProvider;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestNaoVins
/*     */   extends DBReader
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(TestNaoVins.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private ServiceIDServiceProvider sisProv = ServiceIDServiceProvider.getInstance();
/*     */   
/*  36 */   private ServiceIDService sis = this.sisProv.getService();
/*     */   
/*  38 */   private Locale loc = Locale.GERMANY;
/*     */   
/*  40 */   private SIAttrValMap map = new SIAttrValMap();
/*     */   
/*  42 */   private long number = 0L;
/*     */   
/*  44 */   long start = System.currentTimeMillis();
/*     */   
/*     */   public TestNaoVins(String dbProp) throws Exception {
/*  47 */     super(dbProp);
/*  48 */     DatabaseLink databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.sids.service.db");
/*  49 */     DBReader.setStatConn((IDatabaseLink)databaseLink);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TestNaoVins(IDatabaseLink db) throws Exception {
/*  56 */     super(db);
/*  57 */     DatabaseLink databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.sids.service.db");
/*  58 */     DBReader.setStatConn((IDatabaseLink)databaseLink);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readRow(ResultSet rs) throws Exception {
/*  67 */     String vin = rs.getString(1);
/*  68 */     int tries = 1000;
/*     */     while (true)
/*     */     { 
/*  71 */       try { ServiceID sID = this.sis.getServiceID(this.loc, vin, this.map);
/*     */         
/*  73 */         if (!sID.toString().equals("sid_nao")) {
/*  74 */           log.error("wrong ServiceID vin=" + vin + ", ServiceID=" + sID);
/*     */         }
/*     */         break; }
/*  77 */       catch (Exception e)
/*  78 */       { tries--;
/*  79 */         if (tries == 0 || !(e instanceof RuntimeException)) {
/*  80 */           log.error("Exception vin=" + vin);
/*  81 */           log.error(e, e);
/*     */           break;
/*     */         } 
/*  84 */         Thread.sleep(1000L);
/*  85 */         DatabaseLink databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.sids.service.db");
/*  86 */         DBReader.setStatConn((IDatabaseLink)databaseLink);
/*     */ 
/*     */         
/*  89 */         if (tries <= 0)
/*  90 */           break;  }  }  this.number++;
/*  91 */     if (this.number % 10000L == 0L) {
/*  92 */       long curTime = System.currentTimeMillis();
/*  93 */       curTime -= this.start;
/*     */       
/*  95 */       System.out.println("Number=" + this.number + ", Time=" + (curTime / 1000L));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean more() {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(String sql) throws Exception {
/* 106 */     Connection conn = null;
/* 107 */     PreparedStatement stmt = null;
/* 108 */     ResultSet rs = null;
/*     */     try {
/* 110 */       conn = getDb().requestConnection();
/*     */       
/* 112 */       stmt = conn.prepareStatement(sql);
/* 113 */       setParams(stmt);
/* 114 */       rs = stmt.executeQuery();
/* 115 */       while (more() && rs.next()) {
/* 116 */         readRow(rs);
/*     */       }
/* 118 */       log.info("finished: " + this.number);
/* 119 */     } catch (Exception e) {
/* 120 */       throw e;
/*     */     } finally {
/*     */       try {
/* 123 */         if (rs != null) {
/* 124 */           rs.close();
/*     */         }
/* 126 */         if (stmt != null) {
/* 127 */           stmt.close();
/*     */         }
/* 129 */         if (conn != null) {
/* 130 */           getDb().releaseConnection(conn);
/*     */         }
/* 132 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 141 */       DirectoryResourceLoading drl = new DirectoryResourceLoading(new File("C:/projects/GM/tis2web/delivery.refactored/build/env/geitel/dest/war"));
/* 142 */       FrameServiceProvider.create((ResourceLoading)drl);
/*     */       
/* 144 */       TestNaoVins tst = new TestNaoVins((IDatabaseLink)new DatabaseLink("com.microsoft.jdbc.sqlserver.SQLServerDriver", "jdbc:microsoft:sqlserver://sps:1433;DatabaseName=sps1", "sa", "passwd4root", false, 0));
/* 145 */       tst.read("select distinct vin from VCI_AsBuilt");
/* 146 */       System.out.println("finished");
/* 147 */     } catch (Exception e) {
/* 148 */       log.error(e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\test\TestNaoVins.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */