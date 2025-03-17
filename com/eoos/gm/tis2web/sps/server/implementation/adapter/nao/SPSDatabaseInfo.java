/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSDatabaseInfo
/*     */   implements DatabaseInfo
/*     */ {
/*     */   protected transient SPSSession session;
/*     */   protected transient AttributeValueMap data;
/*     */   protected String title;
/*     */   protected String vin;
/*  28 */   protected List tables = new ArrayList();
/*     */   
/*     */   public String getTitle() {
/*  31 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getVIN() {
/*  35 */     return this.vin;
/*     */   }
/*     */   
/*     */   public List getTables() {
/*  39 */     return this.tables;
/*     */   }
/*     */   
/*     */   public SPSDatabaseInfo(SPSSession session, AttributeValueMap data) {
/*  43 */     this.session = session;
/*  44 */     this.data = data;
/*     */   }
/*     */   
/*     */   public void handle(Attribute attribute, SPSSchemaAdapterNAO adapter) throws Exception {
/*  48 */     this.vin = this.session.getVehicle().getVIN().toString();
/*  49 */     if (attribute.equals(CommonAttribute.CONTROLLER_METHOD) || attribute.equals(CommonAttribute.CONTROLLER)) {
/*  50 */       this.title = "Supported Controllers Data";
/*  51 */       if (this.session.getController() != null) {
/*  52 */         if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */           return;
/*     */         }
/*  55 */         prepareControllerData();
/*  56 */       } else if (this.session.getControllerReference() != null) {
/*  57 */         SPSControllerReference reference = (SPSControllerReference)this.session.getControllerReference();
/*  58 */         if (!"reprogrammable".equals(reference.getType())) {
/*     */           return;
/*     */         }
/*  61 */         prepareControllerReferenceData(null);
/*     */       } else {
/*  63 */         SPSControllerReference reference = (SPSControllerReference)this.data.getValue(CommonAttribute.CONTROLLER);
/*  64 */         if (!"reprogrammable".equals(reference.getType())) {
/*     */           return;
/*     */         }
/*  67 */         prepareControllerReferenceData(reference);
/*     */       } 
/*  69 */     } else if (attribute.equals(CommonAttribute.HARDWARE)) {
/*  70 */       this.title = "Hardware Selection Data";
/*  71 */       prepareHardwareData(adapter);
/*  72 */     } else if (attribute.equals(CommonAttribute.PROGRAMMING_DATA_SELECTION)) {
/*  73 */       this.title = "Calibration Selection Data";
/*  74 */       prepareCalibrationData(adapter);
/*  75 */     } else if (attribute.equals(CommonAttribute.SUMMARY)) {
/*  76 */       this.title = "Summary Data";
/*  77 */       prepareSummaryData(adapter);
/*     */     } else {
/*  79 */       this.title = "Vehicle Option Data";
/*  80 */       if (this.session.getController() != null) {
/*  81 */         if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */           return;
/*     */         }
/*  84 */         preparePostOptionData(adapter);
/*  85 */       } else if (this.session.getControllerReference() != null) {
/*  86 */         SPSControllerReference reference = (SPSControllerReference)this.session.getControllerReference();
/*  87 */         if (!"reprogrammable".equals(reference.getType())) {
/*     */           return;
/*     */         }
/*  90 */         preparePostOptionData(adapter);
/*  91 */       } else if (this.data.getValue(CommonAttribute.CONTROLLER) != null) {
/*  92 */         preparePostOptionData(adapter);
/*     */       } else {
/*  94 */         preparePreOptionData();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareOptionData() {
/* 100 */     List<SPSOption> options = this.session.getVehicle().getOptions();
/* 101 */     if (options != null && options.size() > 0) {
/* 102 */       DatabaseTable optionTable = new DatabaseTable("Option_List Table", new String[] { "RPO_Label_Id", "RPO_Code" });
/* 103 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/* 104 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/* 105 */       Set<String> types = new HashSet();
/* 106 */       for (int i = 0; i < options.size(); i++) {
/* 107 */         SPSOption option = options.get(i);
/* 108 */         SPSOption type = (SPSOption)option.getType();
/* 109 */         List<String> data = new ArrayList();
/* 110 */         data.add((type == null) ? "" : type.getID().substring(1));
/* 111 */         data.add(option.getID());
/* 112 */         optionTable.add(data);
/* 113 */         if (type != null && !types.contains(type.getID())) {
/* 114 */           data = new ArrayList<String>();
/* 115 */           data.add(type.getID().substring(1));
/* 116 */           data.add(type.getDescription());
/* 117 */           typeLabels.add(data);
/* 118 */           types.add(type.getID());
/*     */         } 
/* 120 */         data = new ArrayList<String>();
/* 121 */         data.add(option.getID());
/* 122 */         data.add(option.getDescription());
/* 123 */         optionLabels.add(data);
/*     */       } 
/* 125 */       this.tables.add(optionTable);
/* 126 */       this.tables.add(typeLabels);
/* 127 */       this.tables.add(optionLabels);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preparePreOptionData() throws Exception {
/* 132 */     SPSControllerList controllers = (SPSControllerList)this.session.getControllers();
/* 133 */     List<SPSOption> options = new ArrayList();
/* 134 */     Set<String> entries = new HashSet();
/* 135 */     if (controllers == null) {
/*     */       return;
/*     */     }
/* 138 */     for (int c = 0; c < controllers.size(); c++) {
/* 139 */       SPSControllerReference reference = (SPSControllerReference)controllers.get(c);
/* 140 */       if ("reprogrammable".equals(reference.getType())) {
/*     */ 
/*     */         
/* 143 */         List<SPSOption> pre = reference.getPreOptions();
/* 144 */         if (pre != null)
/* 145 */           for (int i = 0; i < pre.size(); i++) {
/* 146 */             SPSOption option = pre.get(i);
/* 147 */             if (!entries.contains(option.getID())) {
/* 148 */               options.add(option);
/* 149 */               entries.add(option.getID());
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     } 
/* 154 */     if (options != null && options.size() > 0) {
/* 155 */       DatabaseTable optionTable = new DatabaseTable("Option_List Table", new String[] { "RPO_Label_Id", "RPO_Code" });
/* 156 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/* 157 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/* 158 */       Set<String> types = new HashSet();
/* 159 */       for (int i = 0; i < options.size(); i++) {
/* 160 */         SPSOption option = options.get(i);
/* 161 */         SPSOption type = (SPSOption)option.getType();
/* 162 */         List<String> data = new ArrayList();
/* 163 */         data.add((type == null) ? "" : type.getID().substring(1));
/* 164 */         data.add(option.getID());
/* 165 */         optionTable.add(data);
/* 166 */         if (type != null && !types.contains(type.getID())) {
/* 167 */           data = new ArrayList<String>();
/* 168 */           data.add(type.getID().substring(1));
/* 169 */           data.add(type.getDescription());
/* 170 */           typeLabels.add(data);
/* 171 */           types.add(type.getID());
/*     */         } 
/* 173 */         data = new ArrayList<String>();
/* 174 */         data.add(option.getID());
/* 175 */         data.add(option.getDescription());
/* 176 */         optionLabels.add(data);
/*     */       } 
/* 178 */       this.tables.add(optionTable);
/* 179 */       this.tables.add(typeLabels);
/* 180 */       this.tables.add(optionLabels);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preparePostOptionData(SPSSchemaAdapterNAO adapter) throws Exception {
/* 185 */     List<SPSOption> options = new ArrayList();
/* 186 */     Set<String> entries = new HashSet();
/* 187 */     SPSControllerReference reference = (SPSControllerReference)this.session.getControllerReference();
/* 188 */     if (reference == null) {
/* 189 */       reference = (SPSControllerReference)this.data.getValue(CommonAttribute.CONTROLLER);
/*     */     }
/* 191 */     if (!"reprogrammable".equals(reference.getType())) {
/*     */       return;
/*     */     }
/* 194 */     List<SPSOption> post = reference.getPostOptions();
/* 195 */     if (post != null) {
/* 196 */       for (int i = 0; i < post.size(); i++) {
/* 197 */         SPSOption option = post.get(i);
/* 198 */         if (!entries.contains(option.getID())) {
/* 199 */           options.add(option);
/* 200 */           entries.add(option.getID());
/*     */         } 
/*     */       } 
/*     */     }
/* 204 */     List<SPSControllerVCI> controllers = reference.getControllers();
/* 205 */     if (controllers != null) {
/* 206 */       for (int i = 0; i < controllers.size(); i++) {
/* 207 */         SPSControllerVCI controller = controllers.get(i);
/* 208 */         SPSSaturnData sdata = new SPSSaturnData(this.session, controller.getDeviceID());
/* 209 */         List<SPSOption> saturn = sdata.getOptions(adapter);
/* 210 */         if (saturn != null) {
/* 211 */           for (int j = 0; j < saturn.size(); j++) {
/* 212 */             SPSOption option = saturn.get(j);
/* 213 */             if (!entries.contains(option.getID())) {
/* 214 */               options.add(option);
/* 215 */               entries.add(option.getID());
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 221 */     if (options != null && options.size() > 0) {
/* 222 */       DatabaseTable optionTable = new DatabaseTable("Post_Option_Label_Ids Table", new String[] { "Post_RPO_Code", "RPO_Label_Id" });
/* 223 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/* 224 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/* 225 */       Set<String> types = new HashSet();
/* 226 */       for (int i = 0; i < options.size(); i++) {
/* 227 */         SPSOption option = options.get(i);
/* 228 */         SPSOption type = (SPSOption)option.getType();
/* 229 */         if (!option.getID().startsWith("%")) {
/* 230 */           List<String> list = new ArrayList();
/* 231 */           list.add(option.getID());
/* 232 */           list.add((type == null) ? "" : type.getID().substring(1));
/* 233 */           optionTable.add(list);
/*     */         } 
/* 235 */         if (type != null && !types.contains(type.getID())) {
/* 236 */           List<String> list = new ArrayList();
/* 237 */           if (type.getID().startsWith("#%")) {
/* 238 */             if (type.getID().indexOf(':') >= 0) {
/*     */               continue;
/*     */             }
/* 241 */             list.add(type.getID().substring(2));
/*     */           } else {
/* 243 */             list.add(type.getID().substring(1));
/*     */           } 
/* 245 */           list.add(type.getDescription());
/* 246 */           typeLabels.add(list);
/* 247 */           types.add(type.getID());
/*     */         } 
/* 249 */         List<String> data = new ArrayList();
/* 250 */         if (option.getID().startsWith("%")) {
/* 251 */           data.add(option.getID().substring(1));
/*     */         } else {
/* 253 */           data.add(option.getID());
/*     */         } 
/* 255 */         data.add(option.getDescription());
/* 256 */         optionLabels.add(data); continue;
/*     */       } 
/* 258 */       if (optionTable.getRowCount() > 1) {
/* 259 */         this.tables.add(optionTable);
/*     */       }
/* 261 */       if (typeLabels.getRowCount() > 1) {
/* 262 */         this.tables.add(typeLabels);
/*     */       }
/* 264 */       if (optionLabels.getRowCount() > 1) {
/* 265 */         this.tables.add(optionLabels);
/*     */       }
/*     */     } 
/* 268 */     handleSaturnData(adapter);
/*     */   }
/*     */   
/*     */   protected void handleSaturnData(SPSSchemaAdapterNAO adapter) throws Exception {
/* 272 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 273 */     SPSVIN vin = (SPSVIN)this.session.getVehicle().getVIN();
/* 274 */     DatabaseTable table = null;
/* 275 */     List<Integer> devices = new ArrayList();
/* 276 */     List<Integer> defaultBits = new ArrayList();
/* 277 */     List<Integer> dependencies = new ArrayList();
/* 278 */     Connection conn = null;
/* 279 */     DBMS.PreparedStatement stmt = null;
/* 280 */     ResultSet rs = null;
/*     */     try {
/* 282 */       conn = dblink.requestConnection();
/* 283 */       String sql = DBMS.getSQL(dblink, "SELECT Device_ID, Block_No, Byte_Offset, Defined_Bits, Dependency_ID, Descrpt_ID, Label_ID, Default_Bits_Index FROM OptionByte WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?)  AND (Engine = '~' OR Engine = ?)  AND (Body = '~' OR Body = ?) ORDER BY Device_ID, Label_ID");
/* 284 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 285 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 286 */       stmt.setString(2, DBMS.toString(vin.getMake()));
/* 287 */       stmt.setString(3, DBMS.toString(vin.getLine()));
/* 288 */       stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 289 */       stmt.setString(5, DBMS.toString(vin.getEngine()));
/* 290 */       stmt.setString(6, DBMS.toString(vin.getPosition4or6()));
/* 291 */       rs = stmt.executeQuery();
/* 292 */       while (rs.next()) {
/* 293 */         int Device_ID = rs.getInt(1);
/* 294 */         int Block_No = rs.getInt(2);
/* 295 */         int Byte_Offset = rs.getInt(3);
/* 296 */         int Defined_Bits = (byte)rs.getInt(4);
/* 297 */         int Dependency_ID = rs.getInt(5);
/* 298 */         int Descrpt_ID = rs.getInt(6);
/* 299 */         int Label_ID = rs.getInt(7);
/* 300 */         int Default_Bits_Index = rs.getInt(8);
/* 301 */         if (!checkSaturnDevice(Device_ID)) {
/*     */           continue;
/*     */         }
/* 304 */         devices.add(Integer.valueOf(Device_ID));
/* 305 */         if (Default_Bits_Index > 0) {
/* 306 */           defaultBits.add(Integer.valueOf(Default_Bits_Index));
/*     */         }
/* 308 */         if (Dependency_ID > 0) {
/* 309 */           dependencies.add(Integer.valueOf(Dependency_ID));
/*     */         }
/* 311 */         if (table == null) {
/* 312 */           table = new DatabaseTable("OptionByte Table", new String[] { "Device_ID", "Block_No", "Byte_Offset", "Defined_Bits", "Dependency_ID", "Descrpt_ID", "Label_ID", "Default_Bits_Index" });
/* 313 */           this.tables.add(table);
/*     */         } 
/* 315 */         List<Integer> data = new ArrayList();
/* 316 */         data.add(Integer.valueOf(Device_ID));
/* 317 */         data.add(Integer.valueOf(Block_No));
/* 318 */         data.add(Integer.valueOf(Byte_Offset));
/* 319 */         data.add(Integer.valueOf(Defined_Bits));
/* 320 */         data.add(Integer.valueOf(Dependency_ID));
/* 321 */         data.add(Integer.valueOf(Descrpt_ID));
/* 322 */         data.add(Integer.valueOf(Label_ID));
/* 323 */         data.add(Integer.valueOf(Default_Bits_Index));
/* 324 */         table.add(data);
/*     */       } 
/* 326 */       if (devices.size() > 0) {
/* 327 */         if (rs != null) {
/* 328 */           rs.close();
/* 329 */           rs = null;
/*     */         } 
/* 331 */         if (stmt != null) {
/* 332 */           stmt.close();
/* 333 */           stmt = null;
/*     */         } 
/* 335 */         handleSaturnOptionBits(conn, vin, devices, adapter);
/* 336 */         if (defaultBits.size() > 0) {
/* 337 */           handleSaturnDefaultBits(conn, defaultBits, adapter);
/*     */         }
/* 339 */         if (dependencies.size() > 0) {
/* 340 */           handleSaturnDependencies(conn, devices, dependencies, adapter);
/*     */         }
/*     */       } 
/* 343 */     } catch (Exception e) {
/* 344 */       throw e;
/*     */     } finally {
/*     */       try {
/* 347 */         if (rs != null) {
/* 348 */           rs.close();
/*     */         }
/* 350 */         if (stmt != null) {
/* 351 */           stmt.close();
/*     */         }
/* 353 */         if (conn != null) {
/* 354 */           dblink.releaseConnection(conn);
/*     */         }
/* 356 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSaturnOptionBits(Connection conn, SPSVIN vin, List devices, SPSSchemaAdapterNAO adapter) throws Exception {
/* 362 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 363 */     DatabaseTable table = null;
/* 364 */     List<Integer> descriptions = new ArrayList();
/* 365 */     DBMS.PreparedStatement stmt = null;
/* 366 */     ResultSet rs = null;
/*     */     try {
/* 368 */       String sql = DBMS.getSQL(dblink, "SELECT Device_ID, Block_No, Byte_Offset, Bit_Offset, Bit_Descrpt_ID FROM OptionByte_Bit_Descrpt WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Body = '~' OR Body = ?)");
/* 369 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 370 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 371 */       stmt.setString(2, DBMS.toString(vin.getMake()));
/* 372 */       stmt.setString(3, DBMS.toString(vin.getLine()));
/* 373 */       stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 374 */       stmt.setString(5, DBMS.toString(vin.getEngine()));
/* 375 */       stmt.setString(6, DBMS.toString(vin.getPosition4or6()));
/* 376 */       rs = stmt.executeQuery();
/* 377 */       while (rs.next()) {
/* 378 */         int Device_ID = rs.getInt(1);
/* 379 */         int Block_No = rs.getInt(2);
/* 380 */         int Byte_Offset = rs.getInt(3);
/* 381 */         int Bit_Offset = rs.getInt(4);
/* 382 */         int Bit_Descrpt_ID = rs.getInt(5);
/* 383 */         if (!checkSaturnDevice(devices, Device_ID)) {
/*     */           continue;
/*     */         }
/* 386 */         descriptions.add(Integer.valueOf(Bit_Descrpt_ID));
/* 387 */         if (table == null) {
/* 388 */           table = new DatabaseTable("OptionByte_Bit_Descrpt Table", new String[] { "Device_ID", "Block_No", "Byte_Offset", "Bit_Offset", "Bit_Descrpt_ID" });
/* 389 */           this.tables.add(table);
/*     */         } 
/* 391 */         List<Integer> data = new ArrayList();
/* 392 */         data.add(Integer.valueOf(Device_ID));
/* 393 */         data.add(Integer.valueOf(Block_No));
/* 394 */         data.add(Integer.valueOf(Byte_Offset));
/* 395 */         data.add(Integer.valueOf(Bit_Offset));
/* 396 */         data.add(Integer.valueOf(Bit_Descrpt_ID));
/* 397 */         table.add(data);
/*     */       } 
/* 399 */       if (descriptions.size() > 0) {
/* 400 */         if (rs != null) {
/* 401 */           rs.close();
/* 402 */           rs = null;
/*     */         } 
/* 404 */         if (stmt != null) {
/* 405 */           stmt.close();
/* 406 */           stmt = null;
/*     */         } 
/* 408 */         handleSaturnOptionBitDescriptions(conn, descriptions, adapter);
/*     */       } 
/* 410 */     } catch (Exception e) {
/* 411 */       throw e;
/*     */     } finally {
/*     */       try {
/* 414 */         if (rs != null) {
/* 415 */           rs.close();
/*     */         }
/* 417 */         if (stmt != null) {
/* 418 */           stmt.close();
/*     */         }
/* 420 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSaturnOptionBitDescriptions(Connection conn, List<Integer> descriptions, SPSSchemaAdapterNAO adapter) throws Exception {
/* 426 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 427 */     DatabaseTable table = null;
/* 428 */     DBMS.PreparedStatement stmt = null;
/* 429 */     ResultSet rs = null;
/*     */     try {
/* 431 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT Descrpt_ID, Description FROM OptionByte_Descrpt WHERE Descrpt_ID IN (#list#) AND Language_Code = ?", descriptions.size());
/* 432 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 433 */       for (int i = 1; i <= descriptions.size(); i++) {
/* 434 */         stmt.setInt(i, ((Integer)descriptions.get(i - 1)).intValue());
/*     */       }
/* 436 */       stmt.setString(descriptions.size() + 1, this.session.getLanguage().getID());
/* 437 */       rs = stmt.executeQuery();
/* 438 */       while (rs.next()) {
/* 439 */         int Descrpt_ID = rs.getInt(1);
/* 440 */         String Description = rs.getString(2);
/* 441 */         if (table == null) {
/* 442 */           table = new DatabaseTable("OptionByte_Descrpt Table", new String[] { "Descrpt_ID", "Description" });
/* 443 */           this.tables.add(table);
/*     */         } 
/* 445 */         List<Integer> data = new ArrayList();
/* 446 */         data.add(Integer.valueOf(Descrpt_ID));
/* 447 */         data.add(Description);
/* 448 */         table.add(data);
/*     */       } 
/* 450 */     } catch (Exception e) {
/* 451 */       throw e;
/*     */     } finally {
/*     */       try {
/* 454 */         if (rs != null) {
/* 455 */           rs.close();
/*     */         }
/* 457 */         if (stmt != null) {
/* 458 */           stmt.close();
/*     */         }
/* 460 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSaturnDefaultBits(Connection conn, List<Integer> defaultBits, SPSSchemaAdapterNAO adapter) throws Exception {
/* 466 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 467 */     DatabaseTable table = null;
/* 468 */     DBMS.PreparedStatement stmt = null;
/* 469 */     ResultSet rs = null;
/*     */     try {
/* 471 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT Default_Bits_Index, RPO_Code, Default_Bits FROM Default_Bits WHERE Default_Bits_Index IN (#list#)", defaultBits.size());
/* 472 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 473 */       for (int i = 1; i <= defaultBits.size(); i++) {
/* 474 */         stmt.setInt(i, ((Integer)defaultBits.get(i - 1)).intValue());
/*     */       }
/* 476 */       rs = stmt.executeQuery();
/* 477 */       while (rs.next()) {
/* 478 */         int Default_Bits_Index = rs.getInt(1);
/* 479 */         String RPO_Code = rs.getString(2);
/* 480 */         int Default_Bits = rs.getInt(3);
/* 481 */         if (table == null) {
/* 482 */           table = new DatabaseTable("Default_Bits Table", new String[] { "Default_Bits_Index", "RPO_Code", "Default_Bits" });
/* 483 */           this.tables.add(table);
/*     */         } 
/* 485 */         List<Integer> data = new ArrayList();
/* 486 */         data.add(Integer.valueOf(Default_Bits_Index));
/* 487 */         data.add(RPO_Code);
/* 488 */         data.add(Integer.valueOf(Default_Bits));
/* 489 */         table.add(data);
/*     */       } 
/* 491 */     } catch (Exception e) {
/* 492 */       throw e;
/*     */     } finally {
/*     */       try {
/* 495 */         if (rs != null) {
/* 496 */           rs.close();
/*     */         }
/* 498 */         if (stmt != null) {
/* 499 */           stmt.close();
/*     */         }
/* 501 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSaturnDependencies(Connection conn, List devices, List<Integer> dependencies, SPSSchemaAdapterNAO adapter) throws Exception {
/* 507 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 508 */     DatabaseTable table = null;
/* 509 */     DBMS.PreparedStatement stmt = null;
/* 510 */     ResultSet rs = null;
/*     */     try {
/* 512 */       String sql = DBMS.getSQL(dblink, "SELECT DISTINCT Dependency_ID, Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID IN (#list#)", dependencies.size());
/* 513 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 514 */       for (int i = 1; i <= dependencies.size(); i++) {
/* 515 */         stmt.setInt(i, ((Integer)dependencies.get(i - 1)).intValue());
/*     */       }
/* 517 */       rs = stmt.executeQuery();
/* 518 */       while (rs.next()) {
/* 519 */         int Dependency_ID = rs.getInt(1);
/* 520 */         int Device_ID = rs.getInt(2);
/* 521 */         int Block_No = rs.getInt(3);
/* 522 */         int Byte_Offset = rs.getInt(4);
/* 523 */         int Byte_Data = rs.getInt(5);
/* 524 */         if (!checkSaturnDevice(devices, Device_ID)) {
/*     */           continue;
/*     */         }
/* 527 */         if (table == null) {
/* 528 */           table = new DatabaseTable("OptionByte_Dependency Table", new String[] { "Dependency_ID", "Device_ID", "Block_No", "Byte_Offset", "Byte_Data" });
/* 529 */           this.tables.add(table);
/*     */         } 
/* 531 */         List<Integer> data = new ArrayList();
/* 532 */         data.add(Integer.valueOf(Dependency_ID));
/* 533 */         data.add(Integer.valueOf(Device_ID));
/* 534 */         data.add(Integer.valueOf(Block_No));
/* 535 */         data.add(Integer.valueOf(Byte_Offset));
/* 536 */         data.add(Integer.valueOf(Byte_Data));
/* 537 */         table.add(data);
/*     */       } 
/* 539 */     } catch (Exception e) {
/* 540 */       throw e;
/*     */     } finally {
/*     */       try {
/* 543 */         if (rs != null) {
/* 544 */           rs.close();
/*     */         }
/* 546 */         if (stmt != null) {
/* 547 */           stmt.close();
/*     */         }
/* 549 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkSaturnDevice(List<Integer> devices, int device) {
/* 555 */     if (device < 0) {
/* 556 */       return true;
/*     */     }
/* 558 */     for (int i = 0; i < devices.size(); i++) {
/* 559 */       Integer deviceID = devices.get(i);
/* 560 */       if (deviceID.intValue() == device) {
/* 561 */         return true;
/*     */       }
/*     */     } 
/* 564 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkSaturnDevice(int device) {
/* 568 */     SPSControllerReference reference = (SPSControllerReference)this.session.getControllerReference();
/* 569 */     if (reference == null) {
/* 570 */       reference = (SPSControllerReference)this.data.getValue(CommonAttribute.CONTROLLER);
/*     */     }
/* 572 */     if (reference != null) {
/* 573 */       return checkSaturnDevice(reference, device);
/*     */     }
/* 575 */     SPSControllerList<SPSControllerReference> sPSControllerList = this.session.getControllers();
/* 576 */     if (sPSControllerList != null) {
/* 577 */       for (int i = 0; i < sPSControllerList.size(); i++) {
/* 578 */         reference = sPSControllerList.get(i);
/* 579 */         if (checkSaturnDevice(reference, device)) {
/* 580 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 585 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkSaturnDevice(SPSControllerReference reference, int device) {
/* 589 */     List<SPSControllerVCI> controllers = reference.getControllers();
/* 590 */     if (controllers != null) {
/* 591 */       for (int i = 0; i < controllers.size(); i++) {
/* 592 */         SPSControllerVCI controller = controllers.get(i);
/* 593 */         if (controller.getDeviceID() == device) {
/* 594 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 598 */     return false;
/*     */   }
/*     */   
/*     */   protected void prepareSummaryData(SPSSchemaAdapterNAO adapter) {
/* 602 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 606 */       DatabaseTable table = new DatabaseTable("EIL (enditem) Table", new String[] { "Module_ID", "Calibration_Part_No" });
/* 607 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 608 */       List<SPSModule> modules = pdata.getModules();
/* 609 */       if (modules.size() == 1) {
/* 610 */         SPSModule module = modules.get(0);
/* 611 */         SPSPart eil = (SPSPart)module.getSelectedPart();
/* 612 */         List<List> calibrations = SPSModule.getCalibrationInfoEIL(eil.getID(), adapter);
/* 613 */         if (calibrations != null) {
/* 614 */           for (int i = 0; i < calibrations.size(); i++) {
/* 615 */             table.add(calibrations.get(i));
/*     */           }
/* 617 */           this.tables.add(table);
/*     */         } 
/*     */       } 
/* 620 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareHardwareData(SPSSchemaAdapterNAO adapter) {
/* 625 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 629 */       Set<Integer> hardware = new HashSet();
/* 630 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 631 */       List<SPSModule> modules = pdata.getModules();
/* 632 */       for (int i = 0; i < modules.size(); i++) {
/* 633 */         SPSModule module = modules.get(i);
/* 634 */         SPSPart origin = (SPSPart)module.getOriginPart();
/* 635 */         List<List> data = SPSCOP.getCOP(origin, adapter);
/* 636 */         if (data != null) {
/* 637 */           DatabaseTable table = new DatabaseTable("COP Table (" + origin.getLabel() + ")", new String[] { "Origin_Part_No", "Old_Part_No", "New_Part_No", "Hardware_Indx" });
/* 638 */           Set<Object> entries = new HashSet();
/* 639 */           for (int j = 0; j < data.size(); j++) {
/* 640 */             List<Integer> entry = data.get(j);
/* 641 */             Integer hwidx = entry.get(5);
/* 642 */             Object key = (new StringBuilder()).append(entry.get(0)).append(":").append(entry.get(1)).append(":").append(entry.get(2)).toString();
/* 643 */             if (!entries.contains(key) && hwidx.intValue() > 0) {
/* 644 */               hardware.add(hwidx);
/* 645 */               entry.remove(3);
/* 646 */               entry.remove(3);
/* 647 */               table.add(entry);
/* 648 */               entries.add(key);
/*     */             } 
/*     */           } 
/* 651 */           if (entries.size() > 0) {
/* 652 */             this.tables.add(table);
/*     */           }
/*     */         } 
/*     */       } 
/* 656 */       if (hardware.size() > 0) {
/* 657 */         DatabaseTable table = new DatabaseTable("Hardware_List", new String[] { "Hardware_Indx", "Hardware_List", "Description_Id" });
/* 658 */         Iterator<Integer> it = hardware.iterator();
/* 659 */         while (it.hasNext()) {
/* 660 */           Integer hwidx = it.next();
/* 661 */           SPSHardwareIndex info = SPSHardwareIndex.getHardwareIndex(hwidx.intValue(), adapter);
/* 662 */           List<Integer> data = new ArrayList();
/* 663 */           data.add(hwidx);
/* 664 */           List parts = info.getParts();
/* 665 */           if (parts == null || parts.size() == 0) {
/* 666 */             data.add("");
/*     */           } else {
/* 668 */             StringBuffer list = new StringBuffer();
/* 669 */             for (int j = 0; j < parts.size(); j++) {
/* 670 */               if (j > 0) {
/* 671 */                 list.append(", ");
/*     */               }
/* 673 */               list.append(parts.get(j));
/*     */             } 
/* 675 */             data.add(list.toString());
/*     */           } 
/* 677 */           data.add(Integer.valueOf(info.getDescriptionID()));
/* 678 */           table.add(data);
/*     */         } 
/* 680 */         this.tables.add(table);
/*     */       } 
/* 682 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareCalibrationData(SPSSchemaAdapterNAO adapter) {
/* 687 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 691 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 692 */       List<SPSModule> modules = pdata.getModules();
/* 693 */       for (int i = 0; i < modules.size(); i++) {
/* 694 */         SPSModule module = modules.get(i);
/* 695 */         SPSPart origin = (SPSPart)module.getOriginPart();
/* 696 */         List<List> data = SPSCOP.getCOP(origin, adapter);
/* 697 */         if (data != null) {
/* 698 */           DatabaseTable table = new DatabaseTable("COP Table (" + module + ")", new String[] { "Origin_Part_No", "Old_Part_No", "New_Part_No", "Rep_Type", "Description" });
/* 699 */           Set<Object> entries = new HashSet();
/* 700 */           for (int j = 0; j < data.size(); j++) {
/* 701 */             List entry = data.get(j);
/* 702 */             Object key = (new StringBuilder()).append(entry.get(0)).append(":").append(entry.get(1)).append(":").append(entry.get(2)).toString();
/* 703 */             if (!entries.contains(key)) {
/* 704 */               entry.remove(5);
/* 705 */               table.add(entry);
/* 706 */               entries.add(key);
/*     */             } 
/*     */           } 
/* 709 */           this.tables.add(table);
/*     */         } 
/*     */       } 
/* 712 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareControllerData() {
/* 717 */     SPSControllerVCI controller = (SPSControllerVCI)this.session.getController();
/* 718 */     DatabaseTable table = new DatabaseTable("Controller_Description Table", new String[] { "Controller_Id", "Controller_Name", "Description" });
/* 719 */     List<Integer> data = new ArrayList();
/* 720 */     data.add(Integer.valueOf(controller.getID()));
/* 721 */     String description = controller.getDescription();
/* 722 */     if (description.indexOf('\t') >= 0) {
/* 723 */       data.add(description.substring(0, description.indexOf('\t')));
/* 724 */       data.add(description.substring(description.indexOf('\t') + 1));
/*     */     } else {
/* 726 */       data.add("");
/* 727 */       data.add(description);
/*     */     } 
/* 729 */     table.add(data);
/* 730 */     this.tables.add(table);
/* 731 */     table = new DatabaseTable("Supported_Controllers Table", new String[] { "VCI", "Pre_Programming_Inst", "Post_Programming_Inst", "AfterMarket" });
/* 732 */     data = new ArrayList<Integer>();
/* 733 */     data.add(Integer.valueOf(controller.getControllerVCI()));
/* 734 */     data.add(SPSProgrammingInstructions.toString(controller.getPreProgrammingInstructions()));
/* 735 */     data.add(SPSProgrammingInstructions.toString(controller.getPostProgrammingInstructions()));
/* 736 */     data.add(controller.getAfterMarketFlag());
/* 737 */     table.add(data);
/* 738 */     this.tables.add(table);
/* 739 */     List<SPSProgrammingType> methods = this.session.getControllerReference().getProgrammingMethods();
/* 740 */     if (methods != null) {
/* 741 */       table = new DatabaseTable("Programming_Method_Description Table", new String[] { "Programming_Method", "Description" });
/* 742 */       for (int i = 0; i < methods.size(); i++) {
/* 743 */         SPSProgrammingType method = methods.get(i);
/* 744 */         data = new ArrayList<Integer>();
/* 745 */         data.add(method.getID());
/* 746 */         data.add(method.getDescription());
/* 747 */         table.add(data);
/*     */       } 
/* 749 */       this.tables.add(table);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareControllerReferenceData(SPSControllerReference reference) {
/* 754 */     if (reference == null) {
/* 755 */       reference = (SPSControllerReference)this.session.getControllerReference();
/*     */     }
/* 757 */     DatabaseTable table = new DatabaseTable("Controller_Description Table", new String[] { "Controller_Id", "Controller_Name", "Description" });
/* 758 */     List<Integer> data = new ArrayList();
/* 759 */     List<SPSControllerVCI> controllers = reference.getControllers();
/* 760 */     Set<Object> entries = new HashSet();
/* 761 */     if (controllers == null)
/*     */       return; 
/*     */     int c;
/* 764 */     for (c = 0; c < controllers.size(); c++) {
/* 765 */       SPSControllerVCI controller = controllers.get(c);
/* 766 */       Object key = controller.getID() + ":" + controller.getDescription();
/* 767 */       if (!entries.contains(key)) {
/* 768 */         data.add(Integer.valueOf(controller.getID()));
/* 769 */         String description = controller.getDescription();
/* 770 */         if (description.indexOf('\t') >= 0) {
/* 771 */           data.add(description.substring(0, description.indexOf('\t')));
/* 772 */           data.add(description.substring(description.indexOf('\t') + 1));
/*     */         } else {
/* 774 */           data.add("");
/* 775 */           data.add(description);
/*     */         } 
/* 777 */         entries.add(key);
/*     */       } 
/*     */     } 
/* 780 */     table.add(data);
/* 781 */     this.tables.add(table);
/* 782 */     table = new DatabaseTable("Supported_Controllers Table", new String[] { "VCI", "Pre_Programming_Inst", "Post_Programming_Inst", "AfterMarket" });
/* 783 */     data = new ArrayList<Integer>();
/* 784 */     entries.clear();
/* 785 */     for (c = 0; c < controllers.size(); c++) {
/* 786 */       SPSControllerVCI controller = controllers.get(c);
/* 787 */       Object key = controller.getID() + ":" + controller.getDescription();
/* 788 */       if (!entries.contains(key)) {
/* 789 */         data.add(Integer.valueOf(controller.getControllerVCI()));
/* 790 */         data.add(SPSProgrammingInstructions.toString(controller.getPreProgrammingInstructions()));
/* 791 */         data.add(SPSProgrammingInstructions.toString(controller.getPostProgrammingInstructions()));
/* 792 */         data.add(controller.getAfterMarketFlag());
/* 793 */         entries.add(key);
/*     */       } 
/*     */     } 
/* 796 */     table.add(data);
/* 797 */     this.tables.add(table);
/* 798 */     List<SPSProgrammingType> methods = reference.getProgrammingMethods();
/* 799 */     if (methods != null) {
/* 800 */       table = new DatabaseTable("Programming_Method_Description Table", new String[] { "Programming_Method", "Description" });
/* 801 */       for (int i = 0; i < methods.size(); i++) {
/* 802 */         SPSProgrammingType method = methods.get(i);
/* 803 */         data = new ArrayList<Integer>();
/* 804 */         data.add(method.getID());
/* 805 */         data.add(method.getDescription());
/* 806 */         table.add(data);
/*     */       } 
/* 808 */       this.tables.add(table);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class DatabaseTable
/*     */     implements DatabaseInfo.Table
/*     */   {
/*     */     protected String title;
/*     */     protected String[] header;
/* 817 */     protected List data = new ArrayList();
/*     */     
/*     */     protected DatabaseTable(String title, String[] header) {
/* 820 */       this.title = title;
/* 821 */       this.header = header;
/*     */     }
/*     */     
/*     */     protected void add(List row) {
/* 825 */       this.data.add(row);
/*     */     }
/*     */     
/*     */     public String getTitle() {
/* 829 */       return this.title;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 833 */       return this.data.size() + 1;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 837 */       return this.header.length;
/*     */     }
/*     */     
/*     */     public Object getContent(int rowIndex, int colIndex) {
/* 841 */       if (rowIndex == 0) {
/* 842 */         return this.header[colIndex];
/*     */       }
/* 844 */       List row = this.data.get(rowIndex - 1);
/* 845 */       return row.get(colIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isHeader(int rowIndex, int colIndex) {
/* 850 */       return (rowIndex == 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSDatabaseInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */