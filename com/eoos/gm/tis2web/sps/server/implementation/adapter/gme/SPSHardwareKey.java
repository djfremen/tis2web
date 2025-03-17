/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSHardwareKey
/*     */ {
/*     */   public static final int REGISTERED = 1;
/*     */   public static final String CHECK_SQL = "SELECT STATUS FROM HARDWARE_KEY_REGISTRY WHERE HARDWARE_KEY = ?";
/*     */   public static final String USAGE_SQL = "SELECT SalesMakeCode FROM SPS_HWKUsage";
/*  27 */   protected Set usage = null;
/*     */   
/*     */   private IDatabaseLink dblink;
/*     */   
/*     */   private SPSHardwareKey(SPSSchemaAdapterGME adapter) {
/*     */     try {
/*  33 */       ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  34 */       String activate = applicationContext.getProperty("frame.hardware-key-registry.enabled");
/*  35 */       if (activate == null || !activate.equalsIgnoreCase("false")) {
/*  36 */         this.dblink = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)applicationContext, "frame.hardware-key-registry.table.db");
/*     */       }
/*  38 */     } catch (Exception x) {}
/*     */ 
/*     */     
/*  41 */     IDatabaseLink db = adapter.getDatabaseLink();
/*  42 */     this.usage = new HashSet();
/*  43 */     Connection conn = null;
/*  44 */     DBMS.PreparedStatement stmt = null;
/*  45 */     ResultSet rs = null;
/*     */     try {
/*  47 */       conn = db.requestConnection();
/*  48 */       stmt = DBMS.prepareSQLStatement(conn, "SELECT SalesMakeCode FROM SPS_HWKUsage");
/*  49 */       rs = stmt.executeQuery();
/*  50 */       while (rs.next()) {
/*  51 */         Integer smc = Integer.valueOf(rs.getInt(1));
/*  52 */         this.usage.add(smc);
/*     */       } 
/*  54 */     } catch (Exception e) {
/*  55 */       this.usage = null;
/*     */     } finally {
/*     */       try {
/*  58 */         if (rs != null) {
/*  59 */           rs.close();
/*     */         }
/*  61 */         if (stmt != null) {
/*  62 */           stmt.close();
/*     */         }
/*  64 */         if (conn != null) {
/*  65 */           db.releaseConnection(conn);
/*     */         }
/*  67 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static SPSHardwareKey getInstance(SPSSchemaAdapterGME adapter) {
/*  73 */     synchronized (adapter.getSyncObject()) {
/*  74 */       SPSHardwareKey instance = (SPSHardwareKey)adapter.getObject(SPSHardwareKey.class);
/*  75 */       if (instance == null) {
/*  76 */         instance = new SPSHardwareKey(adapter);
/*  77 */         adapter.storeObject(SPSHardwareKey.class, instance);
/*     */       } 
/*  79 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isActivated() {
/*  84 */     return (this.dblink != null);
/*     */   }
/*     */   
/*     */   public boolean check(String hwk) {
/*  88 */     if (this.dblink == null) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (hwk == null || "".equals(hwk)) {
/*  92 */       return false;
/*     */     }
/*  94 */     Connection conn = null;
/*  95 */     ResultSet rs = null;
/*  96 */     PreparedStatement stmt = null;
/*     */     try {
/*  98 */       conn = this.dblink.requestConnection();
/*  99 */       stmt = conn.prepareStatement("SELECT STATUS FROM HARDWARE_KEY_REGISTRY WHERE HARDWARE_KEY = ?");
/* 100 */       stmt.setString(1, hwk);
/* 101 */       rs = stmt.executeQuery();
/* 102 */       if (rs.next()) {
/* 103 */         int status = rs.getInt("STATUS");
/* 104 */         return (status == 1);
/*     */       } 
/* 106 */       return false;
/*     */     }
/* 108 */     catch (Exception e) {
/* 109 */       return true;
/*     */     } finally {
/*     */       try {
/* 112 */         if (rs != null) {
/* 113 */           rs.close();
/*     */         }
/* 115 */         if (stmt != null) {
/* 116 */           stmt.close();
/*     */         }
/* 118 */         if (conn != null) {
/* 119 */           this.dblink.releaseConnection(conn);
/*     */         }
/* 121 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Collection getAuthorizedResources(ClientContext context) {
/*     */     try {
/* 128 */       SharedContext sc = context.getSharedContext();
/* 129 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 130 */       Set resources = aclMI.getAuthorizedResources("HardwareKeyCheck", sc.getUsrGroup2ManufMap(), sc.getCountry());
/* 131 */       return resources;
/* 132 */     } catch (Exception e) {
/* 133 */       return null;
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
/*     */   public boolean checkHWK(ClientContext context, Integer smc) {
/* 148 */     boolean check = (this.usage == null) ? true : (!this.usage.contains(smc));
/* 149 */     if (check) {
/* 150 */       Collection acl = getAuthorizedResources(context);
/* 151 */       if (acl != null) {
/* 152 */         Iterator it = acl.iterator();
/* 153 */         while (it.hasNext()) {
/* 154 */           Object resource = it.next();
/* 155 */           if ("no-hwk".equalsIgnoreCase(resource.toString())) {
/* 156 */             check = false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 161 */     return check;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) {
/* 165 */     getInstance(adapter);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSHardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */