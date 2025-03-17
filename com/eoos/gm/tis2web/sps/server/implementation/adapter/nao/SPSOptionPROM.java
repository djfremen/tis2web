/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSOptionPROM
/*     */   extends SPSOption
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  18 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   protected int my_min;
/*     */   protected int my_max;
/*     */   
/*     */   public static final class Groups {
/*     */     private Groups(SPSSchemaAdapterNAO adapter) {
/*  24 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  25 */       Connection conn = null;
/*  26 */       DBMS.PreparedStatement stmt = null;
/*  27 */       ResultSet rs = null;
/*  28 */       this.groups = new HashMap<Object, Object>();
/*     */       try {
/*  30 */         conn = dblink.requestConnection();
/*  31 */         String sql = DBMS.getSQL(dblink, "SELECT RPO_Label_Id, Language_Code, Label FROM PROM_RPO_Code_Labels");
/*  32 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  33 */         rs = stmt.executeQuery();
/*  34 */         while (rs.next()) {
/*  35 */           String id = "#" + rs.getInt(1);
/*  36 */           SPSOption group = (SPSOption)this.groups.get(id);
/*  37 */           if (group == null) {
/*  38 */             group = new SPSOption(id);
/*  39 */             this.groups.put(id, group);
/*     */           } 
/*  41 */           if (group.descriptions == null) {
/*  42 */             group.descriptions = new SPSDescription();
/*     */           }
/*  44 */           String lg = rs.getString(2);
/*  45 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  46 */           group.descriptions.add(locale, DBMS.getString(dblink, locale, rs, 3));
/*     */         } 
/*  48 */       } catch (Exception e) {
/*  49 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  52 */           if (rs != null) {
/*  53 */             rs.close();
/*     */           }
/*  55 */           if (stmt != null) {
/*  56 */             stmt.close();
/*     */           }
/*  58 */           if (conn != null) {
/*  59 */             dblink.releaseConnection(conn);
/*     */           }
/*  61 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private Map groups;
/*     */     
/*     */     public void init() {}
/*     */     
/*     */     public Map getGroups() {
/*  71 */       return this.groups;
/*     */     }
/*     */     
/*     */     public static Groups getInstance(SPSSchemaAdapterNAO adapter) {
/*  75 */       synchronized (adapter.getSyncObject()) {
/*  76 */         Groups instance = (Groups)adapter.getObject(Groups.class);
/*  77 */         if (instance == null) {
/*  78 */           instance = new Groups(adapter);
/*  79 */           adapter.storeObject(Groups.class, instance);
/*     */         } 
/*  81 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Options
/*     */   {
/*     */     private Map options;
/*     */     
/*     */     private Options(SPSSchemaAdapterNAO adapter) {
/*  91 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  92 */       Connection conn = null;
/*  93 */       DBMS.PreparedStatement stmt = null;
/*  94 */       ResultSet rs = null;
/*  95 */       this.options = new HashMap<Object, Object>();
/*     */       try {
/*  97 */         conn = dblink.requestConnection();
/*     */         
/*  99 */         String sql = DBMS.getSQL(dblink, "SELECT RPO_Code, Language_Code, RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description");
/* 100 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 101 */         rs = stmt.executeQuery();
/* 102 */         while (rs.next()) {
/* 103 */           String id = rs.getString(1).trim();
/* 104 */           List<SPSOption> variants = (List)this.options.get(id);
/* 105 */           if (variants == null) {
/* 106 */             variants = new ArrayList();
/* 107 */             this.options.put(id, variants);
/*     */           } 
/* 109 */           String lg = rs.getString(2);
/* 110 */           SPSOption type = SPSOptionPROM.getOptionGroup(rs.getInt(3), adapter);
/* 111 */           if (type == null) {
/* 112 */             SPSOptionPROM.log.error("prom: missing rpo-type '" + rs.getInt(3) + "' (rpo=" + id + ").");
/*     */             continue;
/*     */           } 
/* 115 */           int my_min = rs.getInt(4);
/* 116 */           int my_max = rs.getInt(5);
/* 117 */           SPSOption rpo = SPSOptionPROM.lookupOption(variants, type, my_min, my_max);
/* 118 */           if (rpo == null) {
/* 119 */             rpo = new SPSOptionPROM(id, type, my_min, my_max);
/* 120 */             variants.add(rpo);
/*     */           } 
/* 122 */           if (rpo.descriptions == null) {
/* 123 */             rpo.descriptions = new SPSDescription();
/*     */           }
/* 125 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/* 126 */           rpo.descriptions.add(locale, DBMS.getString(dblink, locale, rs, 6));
/*     */         } 
/* 128 */       } catch (Exception e) {
/* 129 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 132 */           if (rs != null) {
/* 133 */             rs.close();
/*     */           }
/* 135 */           if (stmt != null) {
/* 136 */             stmt.close();
/*     */           }
/* 138 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*     */       try {
/* 142 */         String sql = DBMS.getSQL(dblink, "SELECT RPO_Type, RPO_Code_Label_ID, Language_Code, Description FROM PROM_Emission_Type");
/* 143 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 144 */         rs = stmt.executeQuery();
/* 145 */         while (rs.next()) {
/* 146 */           String id = rs.getString(1).trim();
/* 147 */           SPSOption type = SPSOptionPROM.getOptionGroup(rs.getInt(2), adapter);
/* 148 */           if (type == null) {
/* 149 */             SPSOptionPROM.log.error("prom-emission: missing rpo-type '" + rs.getInt(2) + "' (rpo=" + id + ").");
/*     */             continue;
/*     */           } 
/* 152 */           String lg = rs.getString(3);
/* 153 */           SPSOption rpo = (SPSOption)this.options.get(id);
/* 154 */           if (rpo == null) {
/* 155 */             rpo = new SPSOptionPROM(id, type);
/* 156 */             this.options.put(id, rpo);
/*     */           } 
/* 158 */           if (rpo.descriptions == null) {
/* 159 */             rpo.descriptions = new SPSDescription();
/*     */           }
/* 161 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/* 162 */           rpo.descriptions.add(locale, DBMS.getString(dblink, locale, rs, 4));
/*     */         } 
/* 164 */       } catch (Exception e) {
/* 165 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 168 */           if (rs != null) {
/* 169 */             rs.close();
/*     */           }
/* 171 */           if (stmt != null) {
/* 172 */             stmt.close();
/*     */           }
/* 174 */           if (conn != null) {
/* 175 */             dblink.releaseConnection(conn);
/*     */           }
/* 177 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getOptions() {
/* 187 */       return this.options;
/*     */     }
/*     */     
/*     */     public static Options getInstance(SPSSchemaAdapterNAO adapter) {
/* 191 */       synchronized (adapter.getSyncObject()) {
/* 192 */         Options instance = (Options)adapter.getObject(Options.class);
/* 193 */         if (instance == null) {
/* 194 */           instance = new Options(adapter);
/* 195 */           adapter.storeObject(Options.class, instance);
/*     */         } 
/* 197 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSOptionPROM(String rpo, SPSOption type, int my_min, int my_max) {
/* 207 */     super(rpo);
/* 208 */     this.type = type;
/* 209 */     this.my_min = my_min;
/* 210 */     this.my_max = my_max;
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSOptionPROM(String rpo, SPSOption type) {
/* 215 */     super(rpo);
/* 216 */     this.type = type;
/*     */   }
/*     */   
/*     */   SPSOptionPROM(String id, String description, SPSOption type) {
/* 220 */     super(id, description, type);
/*     */   }
/*     */   
/*     */   void setDescription(String description) {
/* 224 */     this.description = description;
/*     */   }
/*     */   
/*     */   protected SPSOptionPROM(SPSLanguage language, SPSOption type) {
/* 228 */     super(type.getID(), type.descriptions.get(language));
/*     */   }
/*     */   
/*     */   static SPSOption getRPO(SPSLanguage language, String rpo, int my, SPSSchemaAdapterNAO adapter) {
/* 232 */     SPSOption option = null;
/* 233 */     List variants = (List)Options.getInstance(adapter).getOptions().get(rpo);
/* 234 */     if (variants != null) {
/* 235 */       option = lookupOption(variants, my);
/* 236 */       if (option != null) {
/* 237 */         SPSOption group = new SPSOptionPROM(language, (SPSOption)option.getType());
/* 238 */         option = new SPSOptionPROM(rpo, option.descriptions.get(language), group);
/*     */       } 
/*     */     } 
/* 241 */     return option;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static SPSOption getRPO(SPSLanguage language, String rpo, SPSSchemaAdapterNAO adapter) {
/* 247 */     SPSOption option = (SPSOption)Options.getInstance(adapter).getOptions().get(rpo);
/* 248 */     if (option != null) {
/* 249 */       SPSOption group = new SPSOptionPROM(language, (SPSOption)option.getType());
/* 250 */       option = new SPSOptionPROM(rpo, option.descriptions.get(language), group);
/*     */     } 
/* 252 */     return option;
/*     */   }
/*     */   
/*     */   protected static SPSOption getOptionGroup(int id, SPSSchemaAdapterNAO adapter) {
/* 256 */     return (SPSOption)Groups.getInstance(adapter).getGroups().get("#" + id);
/*     */   }
/*     */   
/*     */   protected static SPSOption lookupOption(List<SPSOptionPROM> variants, int my) {
/* 260 */     for (int i = 0; i < variants.size(); i++) {
/* 261 */       SPSOptionPROM option = variants.get(i);
/* 262 */       if (option.my_min <= my && option.my_max >= my) {
/* 263 */         return option;
/*     */       }
/*     */     } 
/* 266 */     return null;
/*     */   }
/*     */   
/*     */   protected static SPSOption lookupOption(List<SPSOptionPROM> variants, SPSOption type, int my_min, int my_max) {
/* 270 */     for (int i = 0; i < variants.size(); i++) {
/* 271 */       SPSOptionPROM option = variants.get(i);
/* 272 */       if (option.type == type && option.my_min == my_min && option.my_max == my_max) {
/* 273 */         return option;
/*     */       }
/*     */     } 
/* 276 */     return null;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 280 */     Groups.getInstance(adapter).init();
/* 281 */     Options.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSOptionPROM.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */