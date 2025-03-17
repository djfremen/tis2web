/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SPSControllerPROM implements SPSController {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient SPSSession session;
/*     */   protected transient SPSOption engineRPO;
/*     */   protected transient List transmissions;
/*     */   protected transient List aircondition;
/*     */   protected transient List emissionTypes;
/*     */   protected transient List options;
/*     */   protected transient List qualifiers;
/*     */   protected int id;
/*     */   protected int PROMPartNo;
/*     */   protected String BroadcastCode;
/*     */   protected String ProgramPartNo;
/*     */   protected int OEMNo;
/*     */   protected int ECUPartNo;
/*     */   
/*     */   public static final class StaticData {
/*     */     private Map descriptions;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  31 */       IDatabaseLink db = adapter.getDatabaseLink();
/*  32 */       this.descriptions = new HashMap<Object, Object>();
/*  33 */       this.names = new HashMap<Object, Object>();
/*  34 */       Connection conn = null;
/*  35 */       DBMS.PreparedStatement stmt = null;
/*  36 */       ResultSet rs = null;
/*     */       try {
/*  38 */         conn = db.requestConnection();
/*  39 */         String sql = DBMS.getSQL(db, "SELECT ECU_Part_No, Controller, Language_Code, Description FROM PROM_ECU");
/*  40 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  41 */         rs = stmt.executeQuery();
/*  42 */         while (rs.next()) {
/*  43 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  44 */           String name = rs.getString(2).trim();
/*  45 */           this.names.put(id, name);
/*  46 */           String lg = rs.getString(3);
/*  47 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*  48 */           if (description == null) {
/*  49 */             description = new SPSDescription();
/*  50 */             this.descriptions.put(id, description);
/*     */           } 
/*  52 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  53 */           description.add(locale, DBMS.getString(db, locale, rs, 4));
/*     */         }
/*     */       
/*     */       }
/*  57 */       catch (Exception e) {
/*  58 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  61 */           if (rs != null) {
/*  62 */             rs.close();
/*     */           }
/*  64 */           if (stmt != null) {
/*  65 */             stmt.close();
/*     */           }
/*  67 */           if (conn != null) {
/*  68 */             db.releaseConnection(conn);
/*     */           }
/*  70 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */     private Map names;
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  76 */       synchronized (adapter.getSyncObject()) {
/*  77 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  78 */         if (instance == null) {
/*  79 */           instance = new StaticData(adapter);
/*  80 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  82 */         return instance;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getNames() {
/*  87 */       return this.names;
/*     */     }
/*     */     
/*     */     public Map getDescriptions() {
/*  91 */       return this.descriptions;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   protected int ScannerID = -1;
/*     */   
/*     */   private transient SPSSchemaAdapterNAO adapter;
/*     */   
/*     */   void setPROMPartNo(int PROMPartNo) {
/* 131 */     this.PROMPartNo = PROMPartNo;
/*     */   }
/*     */   
/*     */   void setBroadcastCode(String BroadcastCode) {
/* 135 */     this.BroadcastCode = BroadcastCode;
/*     */   }
/*     */   
/*     */   void setProgramPartNo(String ProgramPartNo) {
/* 139 */     this.ProgramPartNo = ProgramPartNo;
/*     */   }
/*     */   
/*     */   void setOEMNo(int OEMNo) {
/* 143 */     this.OEMNo = OEMNo;
/*     */   }
/*     */   
/*     */   void setECUPartNo(int ECUPartNo) {
/* 147 */     this.ECUPartNo = ECUPartNo;
/*     */   }
/*     */   
/*     */   void setScannerID(int ScannerID) {
/* 151 */     if (this.session.getVehicle().getVIN().getModelYear() < 'F') {
/* 152 */       this.ScannerID = -1;
/*     */     } else {
/* 154 */       this.ScannerID = ScannerID;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SPSControllerPROM(SPSSession session, Integer prom, SPSSchemaAdapterNAO adapter) {
/* 159 */     this.session = session;
/* 160 */     this.id = prom.intValue();
/* 161 */     this.adapter = adapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHardware(SPSPart part) {}
/*     */   
/*     */   public List getHardware() {
/* 168 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setControllerData(Object data) {
/* 172 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getID() {
/* 176 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 180 */     return pad(this.PROMPartNo) + '\t' + getControllerName(this.adapter) + '\t' + ((this.ScannerID < 0) ? "N/A" : Integer.toString(this.ScannerID)) + '\t' + this.BroadcastCode + tail(this.ProgramPartNo);
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 184 */     return getControllerName(this.adapter);
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/* 188 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getRequestMethodID() {
/* 192 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getProgrammingTypes() {
/* 196 */     return SPSProgrammingType.getProgrammingTypes((SPSLanguage)this.session.getLanguage(), SPSProgrammingType.INFORMATION_ONLY.intValue(), this.adapter);
/*     */   }
/*     */   
/*     */   public List getPreProgrammingInstructions() {
/* 200 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getPostProgrammingInstructions() {
/* 204 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getPreSelectionOptions() throws Exception {
/* 208 */     return this.qualifiers;
/*     */   }
/*     */   
/*     */   public List getPostSelectionOptions() throws Exception {
/* 212 */     return null;
/*     */   }
/*     */   
/*     */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/* 216 */     return constructProgrammingData((SPSSchemaAdapterNAO)adapter);
/*     */   }
/*     */   
/*     */   public void update(SPSSchemaAdapter adapter) throws Exception {
/* 220 */     update(this.session.getVehicle().getOptions());
/*     */   }
/*     */   
/*     */   public Object getControllerData() {
/* 224 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   int getPROMPartNo() {
/* 228 */     return this.PROMPartNo;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 232 */     return getID();
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*     */   public boolean equals(Object object) {
/* 237 */     if (object == null)
/* 238 */       return false; 
/* 239 */     if (object instanceof SPSControllerPROM && ((SPSControllerPROM)object).getID() == getID())
/* 240 */       return true; 
/* 241 */     if (object instanceof SPSControllerReference) {
/* 242 */       return ((SPSControllerReference)object).accept(this);
/*     */     }
/* 244 */     return false;
/*     */   }
/*     */   
/*     */   protected String getControllerName(SPSSchemaAdapterNAO adapter) {
/* 248 */     return StaticData.getInstance(adapter).getNames().get(Integer.valueOf(this.ECUPartNo)).toString();
/*     */   }
/*     */   
/*     */   protected String tail(String ProgramPartNo) {
/* 252 */     if (ProgramPartNo == null) {
/* 253 */       return "";
/*     */     }
/* 255 */     StringBuffer tail = new StringBuffer();
/* 256 */     for (int i = ProgramPartNo.length() - 1; i >= 0; i--) {
/* 257 */       tail.append(ProgramPartNo.charAt(i));
/* 258 */       if (tail.length() == 4) {
/*     */         break;
/*     */       }
/*     */     } 
/* 262 */     tail.append('/');
/* 263 */     return tail.reverse().toString();
/*     */   }
/*     */   
/*     */   protected String pad(int PartNo) {
/* 267 */     String pno = Integer.toString(PartNo);
/* 268 */     StringBuffer pad = new StringBuffer();
/* 269 */     for (int i = pno.length(); i < 8; i++) {
/* 270 */       pad.append('0');
/*     */     }
/* 272 */     pad.append(pno);
/* 273 */     return pad.toString();
/*     */   }
/*     */   
/*     */   protected SPSLanguage getLanguagePROM(SPSSchemaAdapterNAO adapter) {
/* 277 */     return SPSLanguage.getLanguagePROM((SPSLanguage)this.session.getLanguage(), adapter);
/*     */   }
/*     */   
/*     */   void registerTransmissionFlag(String tflag) {
/* 281 */     if (this.transmissions == null) {
/* 282 */       this.transmissions = new ArrayList();
/*     */     }
/* 284 */     add(this.transmissions, tflag);
/*     */   }
/*     */   
/*     */   List getTransmissionFlags() {
/* 288 */     return this.transmissions;
/*     */   }
/*     */   
/*     */   void evaluateTransmissionFlags(SPSSchemaAdapterNAO adapter) {
/* 292 */     SPSVIN vin = (SPSVIN)this.session.getVehicle().getVIN();
/* 293 */     if (this.transmissions == null || this.transmissions.contains("~")) {
/* 294 */       add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "AUT", vin.getYear(adapter), adapter));
/* 295 */       add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "MAN", vin.getYear(adapter), adapter));
/*     */     } else {
/* 297 */       String tflag = this.transmissions.get(0);
/* 298 */       if (tflag.equals("A")) {
/* 299 */         add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "AUT", vin.getYear(adapter), adapter));
/* 300 */       } else if (tflag.equals("M")) {
/* 301 */         add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "MAN", vin.getYear(adapter), adapter));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void registerAirconditionFlag(String aflag) {
/* 307 */     if (this.aircondition == null) {
/* 308 */       this.aircondition = new ArrayList();
/*     */     }
/* 310 */     add(this.aircondition, aflag);
/*     */   }
/*     */   
/*     */   List getAirconditionFlags() {
/* 314 */     return this.aircondition;
/*     */   }
/*     */   
/*     */   void evaluateAirconditionFlags(SPSSchemaAdapterNAO adapter) {
/* 318 */     SPSVIN vin = (SPSVIN)this.session.getVehicle().getVIN();
/* 319 */     if (this.aircondition == null || this.aircondition.contains("~")) {
/* 320 */       add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "+AIR", vin.getYear(adapter), adapter));
/* 321 */       add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "-AIR", vin.getYear(adapter), adapter));
/*     */     } else {
/* 323 */       String aflag = this.aircondition.get(0);
/* 324 */       if (aflag.equals("y")) {
/* 325 */         add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "+AIR", vin.getYear(adapter), adapter));
/* 326 */       } else if (aflag.equals("n")) {
/* 327 */         add(SPSOptionPROM.getRPO(getLanguagePROM(adapter), "-AIR", vin.getYear(adapter), adapter));
/*     */       } 
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
/*     */   void registerEmissionRPOType(String rpoType, String rpoCode, SPSSchemaAdapterNAO adapter) {
/* 341 */     if (this.emissionTypes == null) {
/* 342 */       this.emissionTypes = new ArrayList();
/*     */     }
/* 344 */     add(this.emissionTypes, SPSOptionPROM.getRPO(getLanguagePROM(adapter), rpoType, adapter));
/*     */   }
/*     */   
/*     */   List getEmissionTypes() {
/* 348 */     return this.emissionTypes;
/*     */   }
/*     */   
/*     */   void evaluateEmissionTypes() {
/* 352 */     if (this.qualifiers == null) {
/* 353 */       this.qualifiers = new ArrayList();
/*     */     }
/* 355 */     if (this.emissionTypes == null) {
/* 356 */       this.qualifiers.add(SPSOptionPROM.getRPO(getLanguagePROM(this.adapter), "CAL", this.adapter));
/* 357 */       this.qualifiers.add(SPSOptionPROM.getRPO(getLanguagePROM(this.adapter), "FED", this.adapter));
/* 358 */       this.qualifiers.add(SPSOptionPROM.getRPO(getLanguagePROM(this.adapter), "ALT", this.adapter));
/* 359 */       this.qualifiers.add(SPSOptionPROM.getRPO(getLanguagePROM(this.adapter), "EXP", this.adapter));
/*     */     } else {
/* 361 */       this.qualifiers.addAll(this.emissionTypes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void registerEngineRPO(String engineRPO, SPSSchemaAdapterNAO adapter) {
/* 368 */     SPSVIN vin = (SPSVIN)this.session.getVehicle().getVIN();
/* 369 */     this.engineRPO = SPSOptionPROM.getRPO(getLanguagePROM(adapter), engineRPO, vin.getYear(adapter), adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   void registerRPO(String rpoCode, String rpoType, int rpoCodeLabel, SPSSchemaAdapterNAO adapter) {
/* 374 */     if (this.options == null) {
/* 375 */       this.options = new ArrayList();
/*     */     }
/* 377 */     SPSVIN vin = (SPSVIN)this.session.getVehicle().getVIN();
/* 378 */     add(this.options, SPSOptionPROM.getRPO(getLanguagePROM(adapter), rpoCode, vin.getYear(adapter), adapter));
/*     */   }
/*     */   
/*     */   protected void add(List<Object> list, Object flag) {
/* 382 */     if (!list.contains(flag)) {
/* 383 */       list.add(flag);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void add(SPSOption option) {
/* 388 */     if (this.qualifiers == null) {
/* 389 */       this.qualifiers = new ArrayList();
/*     */     }
/* 391 */     this.qualifiers.add(option);
/*     */   }
/*     */   
/*     */   protected void update(List<SPSOption> options) {
/* 395 */     if (options == null || this.qualifiers == null) {
/*     */       return;
/*     */     }
/* 398 */     for (int i = 0; i < options.size(); i++) {
/* 399 */       SPSOption option = options.get(i);
/* 400 */       update(option);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void update(SPSOption selection) {
/* 405 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/* 406 */       SPSOption option = this.qualifiers.get(i);
/* 407 */       if (option.equals(selection)) {
/* 408 */         Iterator<SPSOption> it = this.qualifiers.iterator();
/* 409 */         while (it.hasNext()) {
/* 410 */           SPSOption other = it.next();
/* 411 */           if (other.getType().equals(option.getType()) && !other.equals(option)) {
/* 412 */             it.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SPSProgrammingData constructProgrammingData(SPSSchemaAdapterNAO adapter) throws Exception {
/* 420 */     SPSProgrammingData data = new SPSProgrammingData();
/* 421 */     data.build(this.session, this, adapter);
/* 422 */     return data;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 426 */     return "id = " + this.id + " " + getDescription();
/*     */   }
/*     */   
/*     */   static String getDescription(SPSLanguage language, int ecu, SPSSchemaAdapterNAO adapter) {
/* 430 */     SPSDescription description = (SPSDescription)StaticData.getInstance(adapter).getDescriptions().get(Integer.valueOf(ecu));
/* 431 */     return (description == null) ? null : description.get(language);
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 435 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerPROM.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */