/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSProgrammingData
/*     */   extends SPSProgrammingData {
/*     */   private static final long serialVersionUID = 1L;
/*  27 */   private static Logger log = Logger.getLogger(SPSProgrammingData.class);
/*     */   
/*     */   protected transient boolean valid = true;
/*     */   
/*     */   protected boolean skipSameCalibration = false;
/*     */   
/*     */   protected String odometer;
/*     */   
/*     */   protected String keycode;
/*     */   
/*     */   protected List optionBytes;
/*     */   
/*     */   protected List transfer;
/*     */   
/*     */   void setVIN(String vin) {
/*  42 */     this.vin = vin;
/*     */   }
/*     */   
/*     */   void setCalibrationFiles(List files) {
/*  46 */     this.files = files;
/*     */   }
/*     */   
/*     */   void setDeviceID(Integer deviceID) {
/*  50 */     this.deviceID = deviceID;
/*     */   }
/*     */   
/*     */   void setVMECUHN(String vmecuhn) {
/*  54 */     this.vmecuhn = vmecuhn;
/*     */   }
/*     */   
/*     */   void setSSECUSVN(String ssecusvn) {
/*  58 */     this.ssecusvn = ssecusvn;
/*     */   }
/*     */   
/*     */   void setOptionBytes(List optionBytes) {
/*  62 */     this.optionBytes = optionBytes;
/*     */   }
/*     */   
/*     */   public List getOptionBytes() {
/*  66 */     return this.optionBytes;
/*     */   }
/*     */   
/*     */   public void setOdometer(String odometer) {
/*  70 */     this.odometer = odometer;
/*     */   }
/*     */   
/*     */   public void setKeycode(String keycode) {
/*  74 */     this.keycode = keycode;
/*     */   }
/*     */   
/*     */   public String getOdometer() {
/*  78 */     return this.odometer;
/*     */   }
/*     */   
/*     */   public String getKeycode() {
/*  82 */     return this.keycode;
/*     */   }
/*     */   
/*     */   void setVIT1TransferAttributes(List transfer) {
/*  86 */     this.transfer = transfer;
/*     */   }
/*     */   
/*     */   public List getVIT1TransferAttributes() {
/*  90 */     return this.transfer;
/*     */   }
/*     */   
/*     */   boolean isValid() {
/*  94 */     return this.valid;
/*     */   }
/*     */   
/*     */   public boolean skipSameCalibration() {
/*  98 */     return this.skipSameCalibration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setArchive(SPSLanguage language, Archive archive, SPSSchemaAdapterNAO adapter) {
/* 106 */     SPSPart part = new SPSPart(archive.getPartNo(), archive.getChangeReason(), adapter);
/* 107 */     this.files = archive.getCalibrationUnits();
/* 108 */     SPSModule module = new SPSModule(language, "238", part);
/* 109 */     module.setDescription(ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.zip-file"));
/* 110 */     this.modules = new ArrayList();
/* 111 */     this.modules.add(module);
/*     */   }
/*     */   
/*     */   void setPartFile(SPSLanguage language, PartFile file, SPSSchemaAdapterNAO adapter) {
/* 115 */     List<String> parts = file.getParts();
/* 116 */     List<String> modids = file.getModules();
/* 117 */     this.modules = new ArrayList();
/* 118 */     for (int i = 0; i < parts.size(); i++) {
/* 119 */       String part = parts.get(i);
/* 120 */       String module = modids.get(i);
/* 121 */       this.modules.add(new SPSModule(language, module, new SPSPart(part, adapter)));
/*     */     } 
/*     */   }
/*     */   
/*     */   void invalidate() {
/* 126 */     this.valid = false;
/*     */   }
/*     */   
/*     */   void indicateSameCalibration() {
/* 130 */     this.skipSameCalibration = true;
/*     */   }
/*     */   
/*     */   void add(SPSLanguage language, SPSPartEI partEI, String module) {
/* 134 */     if (this.modules == null) {
/* 135 */       this.modules = new ArrayList();
/*     */     }
/* 137 */     this.modules.add(new SPSModule(language, module, partEI));
/*     */   }
/*     */   
/*     */   void add(SPSLanguage language, String part, String module, SPSSchemaAdapterNAO adapter) {
/* 141 */     if (this.modules == null) {
/* 142 */       this.modules = new ArrayList();
/*     */     }
/* 144 */     this.modules.add(new SPSModule(language, module, new SPSPart(part, adapter)));
/*     */   }
/*     */   
/*     */   void build(SPSLanguage language, List hardware, SPSSchemaAdapterNAO adapter) throws Exception {
/* 148 */     build(language, hardware, (Set)null, adapter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void build(SPSLanguage language, List hardware, Set usage, SPSSchemaAdapterNAO adapter) throws Exception {
/* 158 */     for (int i = 0; i < this.modules.size(); i++) {
/* 159 */       SPSModule module = this.modules.get(i);
/* 160 */       SPSPart origin = (SPSPart)module.getOriginPart();
/* 161 */       origin.setOriginPartFlagTrue();
/* 162 */       SPSCOP.handle(language, origin, origin, usage, adapter);
/*     */     } 
/* 164 */     hardware = confirmHardwareList(hardware, adapter);
/* 165 */     checkHardwareList(language, hardware, adapter);
/* 166 */     if (this.hardware == null) {
/* 167 */       computeSelectionMode();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void computeSelectionMode() throws Exception {
/* 172 */     for (int i = 0; i < this.modules.size(); i++) {
/* 173 */       SPSModule module = this.modules.get(i);
/* 174 */       SPSPart origin = (SPSPart)module.getOriginPart();
/* 175 */       SPSCOP.computeMode(origin);
/* 176 */       module.init();
/* 177 */       if (module.getID() == "0" && module.getSelectedPart() == null) {
/* 178 */         throw new SPSException(CommonException.NoValidCOP);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List confirmHardwareList(List hardware, SPSSchemaAdapterNAO adapter) throws Exception {
/* 184 */     if (hardware == null || hardware.size() == 0) {
/* 185 */       return hardware;
/*     */     }
/* 187 */     Iterator<SPSHardware> it = hardware.iterator();
/* 188 */     while (it.hasNext()) {
/* 189 */       SPSHardware part = it.next();
/* 190 */       if (part.isVIT1Hardware() && !confirmHardware(part, adapter)) {
/* 191 */         it.remove();
/*     */       }
/*     */     } 
/* 194 */     return (hardware.size() == 0) ? null : hardware;
/*     */   }
/*     */   
/*     */   protected boolean confirmHardware(SPSHardware hardware, SPSSchemaAdapterNAO adapter) throws Exception {
/* 198 */     for (int i = 0; i < this.modules.size(); i++) {
/* 199 */       SPSModule module = this.modules.get(i);
/* 200 */       SPSPart part = (SPSPart)module.getOriginPart();
/* 201 */       if (confirmHardware(part, hardware, adapter)) {
/* 202 */         return true;
/*     */       }
/*     */     } 
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean confirmHardware(SPSPart part, SPSHardware hardware, SPSSchemaAdapterNAO adapter) throws Exception {
/* 209 */     if (part.getHardwareIndex() != 0) {
/* 210 */       SPSHardwareIndex hwidx = SPSHardwareIndex.getHardwareIndex(part.getHardwareIndex(), adapter);
/* 211 */       if (hwidx != null && 
/* 212 */         hwidx.contains(hardware)) {
/* 213 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 217 */     List cop = part.getCOP();
/* 218 */     if (cop != null) {
/* 219 */       Iterator<SPSCOP> it = cop.iterator();
/* 220 */       while (it.hasNext()) {
/* 221 */         part = (SPSPart)((SPSCOP)it.next()).getPart();
/* 222 */         if (confirmHardware(part, hardware, adapter)) {
/* 223 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 227 */     return false;
/*     */   }
/*     */   
/*     */   protected void checkHardwareList(SPSLanguage language, List parts, SPSSchemaAdapterNAO adapter) throws Exception {
/* 231 */     if (language != null) {
/* 232 */       this.hardware = new ArrayList();
/*     */     }
/* 234 */     for (int i = 0; i < this.modules.size(); i++) {
/* 235 */       SPSModule module = this.modules.get(i);
/* 236 */       SPSPart part = (SPSPart)module.getOriginPart();
/* 237 */       if (!checkHardware(language, parts, part, this.hardware, adapter));
/*     */     } 
/*     */ 
/*     */     
/* 241 */     if (this.hardware.size() == 0) {
/* 242 */       this.hardware = null;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean checkHardware(SPSLanguage language, List parts, SPSPart part, List<SPSHardwareIndex> hardware, SPSSchemaAdapterNAO adapter) {
/* 247 */     if (part.getHardwareIndex() != 0) {
/* 248 */       SPSHardwareIndex hwidx = SPSHardwareIndex.getHardwareIndex(part.getHardwareIndex(), adapter);
/* 249 */       if (hwidx != null) {
/* 250 */         if (parts != null) {
/* 251 */           if (!hwidx.contains(parts)) {
/* 252 */             return false;
/*     */           }
/* 254 */         } else if (!hardware.contains(hwidx)) {
/*     */           
/* 256 */           hwidx.setDescription(language, adapter);
/* 257 */           hardware.add(hwidx);
/*     */         } 
/*     */       }
/*     */     } 
/* 261 */     List cop = part.getCOP();
/* 262 */     if (cop != null) {
/* 263 */       Iterator<SPSCOP> it = cop.iterator();
/* 264 */       while (it.hasNext()) {
/* 265 */         part = (SPSPart)((SPSCOP)it.next()).getPart();
/* 266 */         if (!checkHardware(language, parts, part, hardware, adapter)) {
/* 267 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 271 */     return true;
/*     */   }
/*     */   
/*     */   void update(List hardware, SPSSchemaAdapterNAO adapter) throws Exception {
/* 275 */     checkHardwareList((SPSLanguage)null, hardware, adapter);
/* 276 */     computeSelectionMode();
/* 277 */     this.hardware = null;
/*     */   }
/*     */ 
/*     */   
/*     */   void build(SPSSession session, SPSControllerPROM controller, SPSSchemaAdapterNAO adapter) throws Exception {
/* 282 */     List<SPSPartPROM> origins = SPSCOP.build((SPSLanguage)session.getLanguage(), controller, adapter);
/* 283 */     load(session, origins, adapter);
/* 284 */     this.modules = new ArrayList();
/* 285 */     for (int i = 0; i < origins.size(); i++) {
/* 286 */       SPSPartPROM origin = origins.get(i);
/* 287 */       SPSModule module = new SPSModule((SPSLanguage)session.getLanguage(), i + 1, origin);
/* 288 */       SPSCOP.computeMode(origin);
/* 289 */       module.init();
/* 290 */       this.modules.add(module);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void load(SPSSession session, List<SPSPartPROM> origins, SPSSchemaAdapterNAO adapter) throws Exception {
/* 295 */     Map<Object, Object> proms = new HashMap<Object, Object>();
/* 296 */     for (int i = 0; i < origins.size(); i++) {
/* 297 */       SPSPartPROM origin = origins.get(i);
/* 298 */       proms.put(Integer.valueOf(origin.getID()), origin);
/* 299 */       collect(proms, origin.getCOP());
/*     */     } 
/* 301 */     loadPROM(session, proms, adapter);
/* 302 */     update((SPSLanguage)session.getLanguage(), proms, adapter);
/*     */   }
/*     */   
/*     */   protected void collect(Map<Integer, SPSPartPROM> proms, List<SPSCOP> cop) throws Exception {
/* 306 */     if (cop == null || cop.size() == 0) {
/*     */       return;
/*     */     }
/* 309 */     for (int i = 0; i < cop.size(); i++) {
/* 310 */       SPSCOP link = cop.get(i);
/* 311 */       SPSPartPROM part = (SPSPartPROM)link.getPart();
/* 312 */       proms.put(Integer.valueOf(part.getID()), part);
/* 313 */       collect(proms, part.getCOP());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void update(SPSLanguage language, Map proms, SPSSchemaAdapterNAO adapter) {
/* 318 */     language = SPSLanguage.getLanguagePROM(language, adapter);
/* 319 */     Iterator<SPSPartPROM> it = proms.values().iterator();
/* 320 */     while (it.hasNext()) {
/* 321 */       SPSPartPROM part = it.next();
/* 322 */       part.update(language);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadPROM(SPSSession session, Map proms, SPSSchemaAdapterNAO adapter) throws Exception {
/* 327 */     SPSVIN vin = (SPSVIN)session.getVehicle().getVIN();
/* 328 */     char make = vin.getTruckMake(adapter);
/* 329 */     String vinLine = vin.getVINLine(make, adapter);
/* 330 */     if (vinLine == null) {
/* 331 */       throw new SPSException(CommonException.NoValidCOP);
/*     */     }
/* 333 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 334 */     Connection conn = null;
/* 335 */     DBMS.PreparedStatement stmt = null;
/* 336 */     ResultSet rs = null;
/* 337 */     vin.getSeries();
/* 338 */     if (vin.getModelYear() >= 'F' || !vin.isTruck());
/*     */     
/*     */     try {
/* 341 */       conn = dblink.requestConnection();
/* 342 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT PROM_No, RPO_Code, RPO_Type, RPO_Exists, RPO_Code_Label_ID FROM PROM_Supported_PROMs WHERE Model_Year = ? AND Make = ? AND Line = ? AND PROM_No IN (#list#)", proms.size());
/* 343 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 344 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 345 */       stmt.setString(2, DBMS.toString(make));
/* 346 */       stmt.setString(3, DBMS.toString(vinLine.charAt(0)));
/* 347 */       Iterator<Integer> it = proms.keySet().iterator();
/* 348 */       int pos = 3;
/* 349 */       while (it.hasNext()) {
/* 350 */         stmt.setInt(++pos, ((Integer)it.next()).intValue());
/*     */       }
/* 352 */       rs = stmt.executeQuery();
/* 353 */       while (rs.next()) {
/* 354 */         Integer prom = Integer.valueOf(rs.getInt(1));
/* 355 */         String rpoCode = rs.getString(2).trim();
/*     */         
/* 357 */         String rpoExists = rs.getString(4).trim();
/* 358 */         int rpoCodeLabel = rs.getInt(5);
/* 359 */         SPSPartPROM part = (SPSPartPROM)proms.get(prom);
/* 360 */         if (part == null) {
/*     */           try {
/* 362 */             rs.close();
/* 363 */             stmt.close();
/* 364 */             dblink.releaseConnection(conn);
/* 365 */           } catch (Exception x) {}
/*     */           
/* 367 */           throw new SPSException(CommonException.NoValidCOP);
/*     */         } 
/* 369 */         if (rpoCodeLabel == 1) {
/*     */           continue;
/*     */         }
/* 372 */         SPSOptionPROM option = (SPSOptionPROM)SPSOptionPROM.getRPO(SPSLanguage.getLanguagePROM((SPSLanguage)session.getLanguage(), adapter), rpoCode, vin.getYear(adapter), adapter);
/* 373 */         if (rpoExists.equals("N")) {
/* 374 */           option.setDescription("w/o " + option.getDescription());
/*     */         } else {
/* 376 */           option.setDescription(rpoCode + " " + option.getDescription());
/*     */         } 
/* 378 */         part.addOption(option);
/*     */       }
/*     */     
/* 381 */     } catch (Exception e) {
/* 382 */       throw e;
/*     */     } finally {
/*     */       try {
/* 385 */         if (rs != null) {
/* 386 */           rs.close();
/*     */         }
/* 388 */         if (stmt != null) {
/* 389 */           stmt.close();
/*     */         }
/* 391 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*     */     try {
/* 395 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT PROM_No, Transm_Type, Air_Flag, PROM_Part_No, Broadcast_Code, Program_Part_No, OEM_No, ECU_Part_No, Scanner_ID FROM PROM_Data WHERE PROM_No IN (#list#)", proms.size());
/* 396 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 397 */       Iterator<Integer> it = proms.keySet().iterator();
/* 398 */       int pos = 0;
/* 399 */       while (it.hasNext()) {
/* 400 */         stmt.setInt(++pos, ((Integer)it.next()).intValue());
/*     */       }
/* 402 */       rs = stmt.executeQuery();
/* 403 */       while (rs.next()) {
/* 404 */         Integer prom = Integer.valueOf(rs.getInt(1));
/* 405 */         SPSPartPROM part = (SPSPartPROM)proms.get(prom);
/* 406 */         if (part == null) {
/*     */           try {
/* 408 */             rs.close();
/* 409 */             stmt.close();
/* 410 */             dblink.releaseConnection(conn);
/* 411 */           } catch (Exception x) {}
/*     */           
/* 413 */           throw new SPSException(CommonException.NoValidCOP);
/*     */         } 
/* 415 */         part.setPROMPartNo(rs.getInt(4));
/* 416 */         part.setBroadcastCode(rs.getString(5).trim());
/* 417 */         part.setECUPartNo(rs.getInt(8));
/* 418 */         part.setScannerID(vin.getModelYear(), rs.getInt(9));
/*     */       } 
/* 420 */     } catch (Exception e) {
/* 421 */       throw e;
/*     */     } finally {
/*     */       try {
/* 424 */         if (rs != null) {
/* 425 */           rs.close();
/*     */         }
/* 427 */         if (stmt != null) {
/* 428 */           stmt.close();
/*     */         }
/* 430 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/*     */     try {
/* 434 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)", proms.size());
/* 435 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 436 */       stmt.setString(1, SPSLanguage.getLanguagePROM((SPSLanguage)session.getLanguage(), adapter).getID());
/* 437 */       Iterator<SPSPartPROM> it = proms.values().iterator();
/* 438 */       int pos = 1;
/* 439 */       while (it.hasNext()) {
/* 440 */         SPSPartPROM part = it.next();
/* 441 */         stmt.setInt(++pos, part.getECUPartNo());
/*     */       } 
/* 443 */       rs = stmt.executeQuery();
/* 444 */       while (rs.next()) {
/* 445 */         int ecu = rs.getInt(1);
/* 446 */         it = proms.values().iterator();
/* 447 */         while (it.hasNext()) {
/* 448 */           SPSPartPROM part = it.next();
/* 449 */           if (ecu == part.getECUPartNo()) {
/* 450 */             part.setCalpakPartNo(rs.getInt(2));
/* 451 */             part.setCalpakDescription(rs.getString(3).trim());
/*     */           } 
/*     */         } 
/*     */       } 
/* 455 */     } catch (Exception e) {
/* 456 */       throw e;
/*     */     } finally {
/*     */       try {
/* 459 */         if (rs != null) {
/* 460 */           rs.close();
/*     */         }
/* 462 */         if (stmt != null) {
/* 463 */           stmt.close();
/*     */         }
/* 465 */         if (conn != null) {
/* 466 */           dblink.releaseConnection(conn);
/*     */         }
/* 468 */       } catch (Exception x) {
/* 469 */         log.error("failed to clean-up: ", x);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */