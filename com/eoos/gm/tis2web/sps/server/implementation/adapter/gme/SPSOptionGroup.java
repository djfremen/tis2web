/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSOptionGroup
/*     */ {
/*  17 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   protected Integer id;
/*     */   protected List options;
/*     */   
/*     */   public static final class Groups
/*     */   {
/*     */     private Groups(SPSSchemaAdapterGME adapter) {
/*  24 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  25 */       this.groups = new HashMap<Object, Object>();
/*  26 */       Connection conn = null;
/*  27 */       DBMS.PreparedStatement stmt = null;
/*  28 */       ResultSet rs = null;
/*     */       try {
/*  30 */         conn = db.requestConnection();
/*  31 */         String sql = DBMS.getSQL(db, "SELECT OptionGroup, OptionCode FROM SPS_OptionGroup");
/*  32 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  33 */         rs = stmt.executeQuery();
/*  34 */         while (rs.next()) {
/*  35 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  36 */           SPSOptionGroup group = (SPSOptionGroup)this.groups.get(id);
/*  37 */           if (group == null) {
/*  38 */             group = new SPSOptionGroup(id);
/*  39 */             this.groups.put(id, group);
/*     */           } 
/*  41 */           group.add(Integer.valueOf(rs.getInt(2)));
/*     */         } 
/*  43 */       } catch (Exception e) {
/*  44 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  47 */           if (rs != null) {
/*  48 */             rs.close();
/*     */           }
/*  50 */           if (stmt != null) {
/*  51 */             stmt.close();
/*     */           }
/*  53 */           if (conn != null) {
/*  54 */             db.releaseConnection(conn);
/*     */           }
/*  56 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private Map groups;
/*     */     
/*     */     public void init() {}
/*     */     
/*     */     public static Groups getInstance(SPSSchemaAdapterGME adapter) {
/*  66 */       synchronized (adapter.getSyncObject()) {
/*  67 */         Groups instance = (Groups)adapter.getObject(Groups.class);
/*  68 */         if (instance == null) {
/*  69 */           instance = new Groups(adapter);
/*  70 */           adapter.storeObject(Groups.class, instance);
/*     */         } 
/*  72 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getGroups() {
/*  77 */       return this.groups;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SPSOptionGroup(Integer id) {
/*  87 */     this.id = id;
/*  88 */     this.options = new ArrayList();
/*     */   }
/*     */   
/*     */   List getOptions() {
/*  92 */     return this.options;
/*     */   }
/*     */   
/*     */   boolean accept(SPSOption candidate) {
/*  96 */     if (this.options.size() == 0) {
/*  97 */       return true;
/*     */     }
/*  99 */     for (int i = 0; i < this.options.size(); i++) {
/* 100 */       SPSOption option = this.options.get(i);
/* 101 */       if (option.equals(candidate)) {
/* 102 */         return true;
/*     */       }
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 109 */     return this.id.toString();
/*     */   }
/*     */   
/*     */   protected boolean matchVINRange(SPSVIN vin) {
/* 113 */     if (this.options.size() == 0) {
/* 114 */       return true;
/*     */     }
/* 116 */     for (int i = 0; i < this.options.size(); i++) {
/* 117 */       SPSOption option = this.options.get(i);
/* 118 */       SPSVINRange range = ((SPSOptionRNG)option).getVINRange();
/* 119 */       if (range != null && !"#".equals(range.getFromSN()) && !"#".equals(range.getToSN()) && vin.match(range)) {
/* 120 */         return true;
/*     */       }
/*     */     } 
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchDefaultVINRange() {
/* 127 */     if (this.options.size() == 0) {
/* 128 */       return true;
/*     */     }
/* 130 */     for (int i = 0; i < this.options.size(); i++) {
/* 131 */       SPSOption option = this.options.get(i);
/* 132 */       SPSVINRange range = ((SPSOptionRNG)option).getVINRange();
/* 133 */       if (range != null && "#".equals(range.getFromSN()) && "#".equals(range.getToSN())) {
/* 134 */         return true;
/*     */       }
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchNullVINRange(SPSVIN vin) {
/* 141 */     if (this.options.size() == 0) {
/* 142 */       return true;
/*     */     }
/* 144 */     for (int i = 0; i < this.options.size(); i++) {
/* 145 */       SPSOption option = this.options.get(i);
/* 146 */       SPSVINRange range = ((SPSOptionRNG)option).getVINRange();
/* 147 */       if (range != null && !"#".equals(range.getFromSN()) && "#NULL#".equals(range.getToSN()) && vin.matchSN(range.getFromSN())) {
/* 148 */         return true;
/*     */       }
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchVINCode(SPSVIN vin) {
/* 155 */     if (this.options.size() == 0) {
/* 156 */       return true;
/*     */     }
/* 158 */     for (int i = 0; i < this.options.size(); i++) {
/* 159 */       SPSOption option = this.options.get(i);
/* 160 */       SPSVINCode code = ((SPSOptionVIN)option).getVINCode();
/* 161 */       if (code != null && !"#".equals(code.getWmi()) && !"#".equals(code.getVds()) && vin.match(code)) {
/* 162 */         return true;
/*     */       }
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchDefaultVINCode(SPSVIN vin) {
/* 169 */     if (this.options.size() == 0) {
/* 170 */       return true;
/*     */     }
/* 172 */     for (int i = 0; i < this.options.size(); i++) {
/* 173 */       SPSOption option = this.options.get(i);
/* 174 */       SPSVINCode code = ((SPSOptionVIN)option).getVINCode();
/* 175 */       if (code != null) {
/* 176 */         if ("#".equals(code.getWmi()) && vin.matchWildcardVDS(code))
/* 177 */           return true; 
/* 178 */         if (vin.matchWildcardWMI(code) && "#".equals(code.getVds()))
/* 179 */           return true; 
/* 180 */         if ("#".equals(code.getWmi()) && "#".equals(code.getVds())) {
/* 181 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchV10Code(SPSVIN vin) {
/* 189 */     if (this.options.size() == 0) {
/* 190 */       return true;
/*     */     }
/* 192 */     for (int i = 0; i < this.options.size(); i++) {
/* 193 */       SPSOption option = this.options.get(i);
/* 194 */       String code = ((SPSOptionV10)option).getV10Code();
/* 195 */       if (code != null && code.charAt(0) == vin.getModelYear()) {
/* 196 */         return true;
/*     */       }
/*     */     } 
/* 199 */     return false;
/*     */   }
/*     */   
/*     */   public String getValue(VIT1Data vit1, String vit1type, String parameter, int position) {
/* 203 */     if (!vit1.getID().equals(vit1type)) {
/* 204 */       return null;
/*     */     }
/* 206 */     SPSIdentType ident = new SPSIdentType(parameter, position);
/* 207 */     return ident.getIdentValue(vit1);
/*     */   }
/*     */   
/*     */   protected boolean matchVIT1(VIT1Data vit1, SPSOptionCategory category) {
/* 211 */     if (this.options.size() == 0) {
/* 212 */       return true;
/*     */     }
/* 214 */     String value = getValue(vit1, category.vit1type, category.parameter, category.position);
/* 215 */     if (value != null) {
/* 216 */       for (int i = 0; i < this.options.size(); i++) {
/* 217 */         SPSOption option = this.options.get(i);
/* 218 */         if (value.equals(((SPSOptionVIT1)option).getVIT1Value())) {
/* 219 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 223 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchHWO(VIT1Data vit1, SPSOptionCategory category) {
/* 227 */     if (this.options.size() == 0) {
/* 228 */       return true;
/*     */     }
/* 230 */     for (int i = 0; i < this.options.size(); i++) {
/* 231 */       SPSOptionHWO option = this.options.get(i);
/* 232 */       Map hwlocations = option.getHWLocations();
/* 233 */       Iterator<String> it = hwlocations.keySet().iterator();
/* 234 */       while (it.hasNext()) {
/* 235 */         String key = it.next();
/* 236 */         if (!key.endsWith(":" + vit1.getID())) {
/*     */           continue;
/*     */         }
/* 239 */         List idents = (List)hwlocations.get(key);
/* 240 */         if (idents == null || idents.size() == 0) {
/*     */           continue;
/*     */         }
/* 243 */         String hwNumber = SPSIdentType.getHWIdentFromVIT1(vit1, idents);
/* 244 */         if (hwNumber != null && hwNumber.equals(option.getHWName())) {
/* 245 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 249 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean matchHWO(String hwNumber, SPSOptionCategory category) {
/* 253 */     if (this.options.size() == 0) {
/* 254 */       return true;
/*     */     }
/* 256 */     for (int i = 0; i < this.options.size(); i++) {
/* 257 */       SPSOptionHWO option = this.options.get(i);
/* 258 */       if (hwNumber != null && hwNumber.equals(option.getHWName())) {
/* 259 */         return true;
/*     */       }
/*     */     } 
/* 262 */     return false;
/*     */   }
/*     */   
/*     */   protected void add(Integer option) {
/* 266 */     this.options.add(option);
/*     */   }
/*     */   
/*     */   static SPSOptionGroup getOptionGroup(String categoryID, Integer id, SPSSchemaAdapterGME adapter) {
/* 270 */     SPSOptionGroup template = (SPSOptionGroup)Groups.getInstance(adapter).getGroups().get(id);
/* 271 */     if (template == null) {
/* 272 */       return null;
/*     */     }
/* 274 */     List<Integer> options = template.getOptions();
/* 275 */     SPSOptionGroup group = new SPSOptionGroup(id);
/* 276 */     for (int i = 0; i < options.size(); i++) {
/* 277 */       options.get(i);
/* 278 */       SPSOption option = SPSOption.getOption(categoryID, options.get(i), adapter);
/* 279 */       if (option == null) {
/* 280 */         log.error("invalid option code (group): " + options.get(i));
/*     */       } else {
/* 282 */         group.options.add(option);
/*     */       } 
/*     */     } 
/* 285 */     return group;
/*     */   }
/*     */   
/*     */   static SPSOptionGroup getOptionGroup(String categoryID, int id, SPSSchemaAdapterGME adapter) {
/* 289 */     return getOptionGroup(categoryID, Integer.valueOf(id), adapter);
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 293 */     Groups.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */