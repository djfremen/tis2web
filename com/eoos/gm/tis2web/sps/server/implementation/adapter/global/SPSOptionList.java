/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSOptionList
/*     */   extends ArrayList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   private static Logger log = Logger.getLogger(SPSOption.class);
/*     */   
/*     */   static SPSOptionList getOptionList(SPSSchemaAdapterGlobal adapter, Integer id) throws Exception {
/*  20 */     return StaticData.getInstance(adapter).getOptionList(id);
/*     */   }
/*     */   protected Integer optionListID;
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/*  24 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */   
/*     */   public static final class OptionListEntry {
/*     */     protected int order;
/*     */     protected SPSOption option;
/*     */     protected SPSOption group;
/*     */     
/*     */     public OptionListEntry(int order, SPSOption option, SPSOption group) {
/*  33 */       this.order = order;
/*  34 */       this.option = option;
/*  35 */       this.group = group;
/*     */     }
/*     */     
/*     */     public SPSOption getGroup() {
/*  39 */       return this.group;
/*     */     }
/*     */     
/*     */     public SPSOption getOption() {
/*  43 */       return this.option;
/*     */     }
/*     */     
/*     */     public int getOrder() {
/*  47 */       return this.order;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class StaticData
/*     */   {
/*     */     private Map lists;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  56 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  57 */       this.lists = new HashMap<Object, Object>();
/*  58 */       Map options = SPSOption.StaticData.getInstance(adapter).getOptions();
/*  59 */       Map groups = SPSOption.StaticData.getInstance(adapter).getGroups();
/*  60 */       Connection conn = null;
/*  61 */       DBMS.PreparedStatement stmt = null;
/*  62 */       ResultSet rs = null;
/*     */       try {
/*  64 */         conn = db.requestConnection();
/*  65 */         String sql = DBMS.getSQL(db, "SELECT Option_List_Id, RPO_Code, RPO_Label_Id, Order FROM RPO_Option_List ORDER BY 4");
/*  66 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  67 */         rs = stmt.executeQuery();
/*  68 */         while (rs.next()) {
/*  69 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  70 */           String rpo = rs.getString(2).trim();
/*  71 */           if (rpo != null)
/*  72 */             rpo = rpo.toUpperCase(Locale.ENGLISH); 
/*  73 */           int label = rs.getInt(3);
/*  74 */           int order = rs.getInt(4);
/*  75 */           SPSOption option = (SPSOption)options.get(rpo);
/*  76 */           SPSOption group = (SPSOption)groups.get("#" + label);
/*  77 */           SPSOptionList list = (SPSOptionList)this.lists.get(id);
/*  78 */           if (list == null) {
/*  79 */             list = new SPSOptionList(id);
/*  80 */             this.lists.put(id, list);
/*     */           } 
/*  82 */           list.add((E)new SPSOptionList.OptionListEntry(order, option, group));
/*     */         } 
/*  84 */       } catch (Exception e) {
/*  85 */         this.lists = null;
/*  86 */         SPSOptionList.log.warn("RPO option list not available: ", e);
/*     */       } finally {
/*     */         try {
/*  89 */           if (rs != null) {
/*  90 */             rs.close();
/*     */           }
/*  92 */           if (stmt != null) {
/*  93 */             stmt.close();
/*     */           }
/*  95 */           if (conn != null) {
/*  96 */             db.releaseConnection(conn);
/*     */           }
/*  98 */         } catch (Exception x) {
/*  99 */           SPSOptionList.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getOptionLists() {
/* 109 */       return this.lists;
/*     */     }
/*     */     
/*     */     public SPSOptionList getOptionList(Integer id) {
/* 113 */       return (SPSOptionList)this.lists.get(id);
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
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getOptionListID() {
/* 131 */     return this.optionListID;
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSOptionList(Integer optionListID) {
/* 136 */     this.optionListID = optionListID;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSOptionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */