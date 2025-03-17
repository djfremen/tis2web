/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
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
/*  25 */   protected List tables = new ArrayList();
/*     */   
/*     */   public String getTitle() {
/*  28 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getVIN() {
/*  32 */     return this.vin;
/*     */   }
/*     */   
/*     */   public List getTables() {
/*  36 */     return this.tables;
/*     */   }
/*     */   
/*     */   public SPSDatabaseInfo(SPSSession session, AttributeValueMap data) {
/*  40 */     this.session = session;
/*  41 */     this.data = data;
/*     */   }
/*     */   
/*     */   public void handle(Attribute attribute, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  45 */     this.vin = this.session.getVehicle().getVIN().toString();
/*  46 */     if (attribute.equals(CommonAttribute.CONTROLLER_METHOD) || attribute.equals(CommonAttribute.CONTROLLER)) {
/*  47 */       this.title = "Supported Controllers Data";
/*  48 */       if (this.session.getController() != null) {
/*  49 */         if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */           return;
/*     */         }
/*  52 */         prepareControllerData();
/*  53 */       } else if (this.session.getControllerReference() != null) {
/*  54 */         prepareControllerReferenceData(null);
/*     */       } else {
/*  56 */         SPSControllerReference reference = (SPSControllerReference)this.data.getValue(CommonAttribute.CONTROLLER);
/*  57 */         prepareControllerReferenceData(reference);
/*     */       } 
/*  59 */     } else if (attribute.equals(CommonAttribute.HARDWARE)) {
/*  60 */       this.title = "Hardware Selection Data";
/*  61 */       prepareHardwareData(adapter);
/*  62 */     } else if (attribute.equals(CommonAttribute.PROGRAMMING_DATA_SELECTION)) {
/*  63 */       this.title = "Calibration Selection Data";
/*  64 */       prepareCalibrationData(adapter);
/*  65 */     } else if (attribute.equals(CommonAttribute.SUMMARY)) {
/*  66 */       this.title = "Summary Data";
/*  67 */       prepareSummaryData(adapter);
/*     */     } else {
/*  69 */       this.title = "Vehicle Option Data";
/*  70 */       if (this.session.getController() != null) {
/*  71 */         if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */           return;
/*     */         }
/*  74 */         preparePostOptionData(adapter);
/*  75 */       } else if (this.session.getControllerReference() != null) {
/*  76 */         preparePostOptionData(adapter);
/*  77 */       } else if (this.data.getValue(CommonAttribute.CONTROLLER) != null) {
/*  78 */         preparePostOptionData(adapter);
/*     */       } else {
/*  80 */         preparePreOptionData();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareOptionData() {
/*  86 */     List<SPSOption> options = this.session.getVehicle().getOptions();
/*  87 */     if (options != null && options.size() > 0) {
/*  88 */       DatabaseTable optionTable = new DatabaseTable("Option_List Table", new String[] { "RPO_Label_Id", "RPO_Code" });
/*  89 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/*  90 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/*  91 */       Set<String> types = new HashSet();
/*  92 */       for (int i = 0; i < options.size(); i++) {
/*  93 */         SPSOption option = options.get(i);
/*  94 */         SPSOption type = (SPSOption)option.getType();
/*  95 */         List<String> data = new ArrayList();
/*  96 */         data.add((type == null) ? "" : type.getID().substring(1));
/*  97 */         data.add(option.getID());
/*  98 */         optionTable.add(data);
/*  99 */         if (type != null && !types.contains(type.getID())) {
/* 100 */           data = new ArrayList<String>();
/* 101 */           data.add(type.getID().substring(1));
/* 102 */           data.add(type.getDescription());
/* 103 */           typeLabels.add(data);
/* 104 */           types.add(type.getID());
/*     */         } 
/* 106 */         data = new ArrayList<String>();
/* 107 */         data.add(option.getID());
/* 108 */         data.add(option.getDescription());
/* 109 */         optionLabels.add(data);
/*     */       } 
/* 111 */       this.tables.add(optionTable);
/* 112 */       this.tables.add(typeLabels);
/* 113 */       this.tables.add(optionLabels);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preparePreOptionData() throws Exception {
/* 118 */     SPSControllerList controllers = (SPSControllerList)this.session.getControllers();
/* 119 */     List<SPSOption> options = new ArrayList();
/* 120 */     Set<String> entries = new HashSet();
/* 121 */     if (controllers == null) {
/*     */       return;
/*     */     }
/* 124 */     for (int c = 0; c < controllers.size(); c++) {
/* 125 */       SPSControllerReference reference = (SPSControllerReference)controllers.get(c);
/* 126 */       List<SPSOption> pre = reference.getPreOptions();
/* 127 */       if (pre != null) {
/* 128 */         for (int i = 0; i < pre.size(); i++) {
/* 129 */           SPSOption option = pre.get(i);
/* 130 */           if (!entries.contains(option.getID())) {
/* 131 */             options.add(option);
/* 132 */             entries.add(option.getID());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 137 */     if (options != null && options.size() > 0) {
/* 138 */       DatabaseTable optionTable = new DatabaseTable("Option_List Table", new String[] { "RPO_Label_Id", "RPO_Code" });
/* 139 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/* 140 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/* 141 */       Set<String> types = new HashSet();
/* 142 */       for (int i = 0; i < options.size(); i++) {
/* 143 */         SPSOption option = options.get(i);
/* 144 */         SPSOption type = (SPSOption)option.getType();
/* 145 */         List<String> data = new ArrayList();
/* 146 */         data.add((type == null) ? "" : type.getID().substring(1));
/* 147 */         data.add(option.getID());
/* 148 */         optionTable.add(data);
/* 149 */         if (type != null && !types.contains(type.getID())) {
/* 150 */           data = new ArrayList<String>();
/* 151 */           data.add(type.getID().substring(1));
/* 152 */           data.add(type.getDescription());
/* 153 */           typeLabels.add(data);
/* 154 */           types.add(type.getID());
/*     */         } 
/* 156 */         data = new ArrayList<String>();
/* 157 */         data.add(option.getID());
/* 158 */         data.add(option.getDescription());
/* 159 */         optionLabels.add(data);
/*     */       } 
/* 161 */       this.tables.add(optionTable);
/* 162 */       this.tables.add(typeLabels);
/* 163 */       this.tables.add(optionLabels);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preparePostOptionData(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 168 */     List<SPSOption> options = new ArrayList();
/* 169 */     Set<String> entries = new HashSet();
/* 170 */     SPSControllerReference reference = (SPSControllerReference)this.session.getControllerReference();
/* 171 */     if (reference == null) {
/* 172 */       reference = (SPSControllerReference)this.data.getValue(CommonAttribute.CONTROLLER);
/*     */     }
/* 174 */     List<SPSOption> post = reference.getPostOptions();
/* 175 */     if (post != null) {
/* 176 */       for (int i = 0; i < post.size(); i++) {
/* 177 */         SPSOption option = post.get(i);
/* 178 */         if (!entries.contains(option.getID())) {
/* 179 */           options.add(option);
/* 180 */           entries.add(option.getID());
/*     */         } 
/*     */       } 
/*     */     }
/* 184 */     if (options != null && options.size() > 0) {
/* 185 */       DatabaseTable optionTable = new DatabaseTable("Post_Option_Label_Ids Table", new String[] { "Post_RPO_Code", "RPO_Label_Id" });
/* 186 */       DatabaseTable typeLabels = new DatabaseTable("RPO_Code_Labels Table", new String[] { "RPO_Label_Id", "Label" });
/* 187 */       DatabaseTable optionLabels = new DatabaseTable("RPO_Description Table", new String[] { "RPO_Code", "Description" });
/* 188 */       Set<String> types = new HashSet();
/* 189 */       for (int i = 0; i < options.size(); i++) {
/* 190 */         SPSOption option = options.get(i);
/* 191 */         SPSOption type = (SPSOption)option.getType();
/* 192 */         if (type != null && !types.contains(type.getID())) {
/* 193 */           List<String> list = new ArrayList();
/* 194 */           list.add(type.getID().substring(1));
/* 195 */           list.add(type.getDescription());
/* 196 */           typeLabels.add(list);
/* 197 */           types.add(type.getID());
/*     */         } 
/* 199 */         List<String> data = new ArrayList();
/* 200 */         data.add(option.getID());
/* 201 */         data.add(option.getDescription());
/* 202 */         optionLabels.add(data);
/*     */       } 
/* 204 */       if (optionTable.getRowCount() > 1) {
/* 205 */         this.tables.add(optionTable);
/*     */       }
/* 207 */       if (typeLabels.getRowCount() > 1) {
/* 208 */         this.tables.add(typeLabels);
/*     */       }
/* 210 */       if (optionLabels.getRowCount() > 1) {
/* 211 */         this.tables.add(optionLabels);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareSummaryData(SPSSchemaAdapterGlobal adapter) {
/* 217 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 221 */       DatabaseTable table = new DatabaseTable("EIL (enditem) Table", new String[] { "Module_ID", "Calibration_Part_No" });
/* 222 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 223 */       List<SPSModule> modules = pdata.getModules();
/* 224 */       if (modules.size() == 1) {
/* 225 */         SPSModule module = modules.get(0);
/* 226 */         SPSPart eil = (SPSPart)module.getSelectedPart();
/* 227 */         List<List> calibrations = SPSModule.getCalibrationInfoEIL(eil.getID(), adapter);
/* 228 */         if (calibrations != null) {
/* 229 */           for (int i = 0; i < calibrations.size(); i++) {
/* 230 */             table.add(calibrations.get(i));
/*     */           }
/* 232 */           this.tables.add(table);
/*     */         } 
/*     */       } 
/* 235 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareHardwareData(SPSSchemaAdapterGlobal adapter) {
/* 240 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 244 */       Set<Integer> hardware = new HashSet();
/* 245 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 246 */       List<SPSModule> modules = pdata.getModules();
/* 247 */       for (int i = 0; i < modules.size(); i++) {
/* 248 */         SPSModule module = modules.get(i);
/* 249 */         SPSPart origin = (SPSPart)module.getOriginPart();
/* 250 */         List<List> data = SPSCOP.getCOP(origin, adapter);
/* 251 */         if (data != null) {
/* 252 */           DatabaseTable table = new DatabaseTable("COP Table (" + origin.getLabel() + ")", new String[] { "Origin_Part_No", "Old_Part_No", "New_Part_No", "Hardware_Indx" });
/* 253 */           Set<Object> entries = new HashSet();
/* 254 */           for (int j = 0; j < data.size(); j++) {
/* 255 */             List<Integer> entry = data.get(j);
/* 256 */             Integer hwidx = entry.get(5);
/* 257 */             Object key = (new StringBuilder()).append(entry.get(0)).append(":").append(entry.get(1)).append(":").append(entry.get(2)).toString();
/* 258 */             if (!entries.contains(key) && hwidx.intValue() > 0) {
/* 259 */               hardware.add(hwidx);
/* 260 */               entry.remove(3);
/* 261 */               entry.remove(3);
/* 262 */               table.add(entry);
/* 263 */               entries.add(key);
/*     */             } 
/*     */           } 
/* 266 */           if (entries.size() > 0) {
/* 267 */             this.tables.add(table);
/*     */           }
/*     */         } 
/*     */       } 
/* 271 */       if (hardware.size() > 0) {
/* 272 */         DatabaseTable table = new DatabaseTable("Hardware_List", new String[] { "Hardware_Indx", "Hardware_List", "Description_Id" });
/* 273 */         Iterator<Integer> it = hardware.iterator();
/* 274 */         while (it.hasNext()) {
/* 275 */           Integer hwidx = it.next();
/* 276 */           SPSHardwareIndex info = SPSHardwareIndex.getHardwareIndex(hwidx.intValue(), adapter);
/* 277 */           List<Integer> data = new ArrayList();
/* 278 */           data.add(hwidx);
/* 279 */           List parts = info.getParts();
/* 280 */           if (parts == null || parts.size() == 0) {
/* 281 */             data.add("");
/*     */           } else {
/* 283 */             StringBuffer list = new StringBuffer();
/* 284 */             for (int j = 0; j < parts.size(); j++) {
/* 285 */               if (j > 0) {
/* 286 */                 list.append(", ");
/*     */               }
/* 288 */               list.append(parts.get(j));
/*     */             } 
/* 290 */             data.add(list.toString());
/*     */           } 
/* 292 */           data.add(Integer.valueOf(info.getDescriptionID()));
/* 293 */           table.add(data);
/*     */         } 
/* 295 */         this.tables.add(table);
/*     */       } 
/* 297 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareCalibrationData(SPSSchemaAdapterGlobal adapter) {
/* 302 */     if (!(this.session.getController() instanceof SPSControllerVCI)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 306 */       SPSProgrammingData pdata = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/* 307 */       List<SPSModule> modules = pdata.getModules();
/* 308 */       for (int i = 0; i < modules.size(); i++) {
/* 309 */         SPSModule module = modules.get(i);
/* 310 */         SPSPart origin = (SPSPart)module.getOriginPart();
/* 311 */         List<List> data = SPSCOP.getCOP(origin, adapter);
/* 312 */         if (data != null) {
/* 313 */           DatabaseTable table = new DatabaseTable("COP Table (" + module + ")", new String[] { "Origin_Part_No", "Old_Part_No", "New_Part_No", "Rep_Type", "Description" });
/* 314 */           Set<Object> entries = new HashSet();
/* 315 */           for (int j = 0; j < data.size(); j++) {
/* 316 */             List entry = data.get(j);
/* 317 */             Object key = (new StringBuilder()).append(entry.get(0)).append(":").append(entry.get(1)).append(":").append(entry.get(2)).toString();
/* 318 */             if (!entries.contains(key)) {
/* 319 */               entry.remove(5);
/* 320 */               table.add(entry);
/* 321 */               entries.add(key);
/*     */             } 
/*     */           } 
/* 324 */           this.tables.add(table);
/*     */         } 
/*     */       } 
/* 327 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareControllerData() {
/* 332 */     SPSControllerVCI controller = (SPSControllerVCI)this.session.getController();
/* 333 */     DatabaseTable table = new DatabaseTable("Controller_Description Table", new String[] { "Controller_Id", "Controller_Name", "Description" });
/* 334 */     List<Integer> data = new ArrayList();
/* 335 */     data.add(Integer.valueOf(controller.getID()));
/* 336 */     String description = controller.getDescription();
/* 337 */     if (description.indexOf('\t') >= 0) {
/* 338 */       data.add(description.substring(0, description.indexOf('\t')));
/* 339 */       data.add(description.substring(description.indexOf('\t') + 1));
/*     */     } else {
/* 341 */       data.add("");
/* 342 */       data.add(description);
/*     */     } 
/* 344 */     table.add(data);
/* 345 */     this.tables.add(table);
/* 346 */     table = new DatabaseTable("Supported_Controllers Table", new String[] { "VCI", "Pre_Programming_Inst", "Post_Programming_Inst", "AfterMarket" });
/* 347 */     data = new ArrayList<Integer>();
/* 348 */     data.add(Integer.valueOf(controller.getControllerVCI()));
/* 349 */     data.add(SPSProgrammingInstructions.toString(controller.getPreProgrammingInstructions()));
/* 350 */     data.add(SPSProgrammingInstructions.toString(controller.getPostProgrammingInstructions()));
/* 351 */     data.add(controller.getAfterMarketFlag());
/* 352 */     table.add(data);
/* 353 */     this.tables.add(table);
/* 354 */     List<SPSProgrammingType> methods = this.session.getControllerReference().getProgrammingMethods();
/* 355 */     if (methods != null) {
/* 356 */       table = new DatabaseTable("Programming_Method_Description Table", new String[] { "Programming_Method", "Description" });
/* 357 */       for (int i = 0; i < methods.size(); i++) {
/* 358 */         SPSProgrammingType method = methods.get(i);
/* 359 */         data = new ArrayList<Integer>();
/* 360 */         data.add(method.getID());
/* 361 */         data.add(method.getDescription());
/* 362 */         table.add(data);
/*     */       } 
/* 364 */       this.tables.add(table);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareControllerReferenceData(SPSControllerReference reference) {
/* 369 */     if (reference == null) {
/* 370 */       reference = (SPSControllerReference)this.session.getControllerReference();
/*     */     }
/* 372 */     DatabaseTable table = new DatabaseTable("Controller_Description Table", new String[] { "Controller_Id", "Controller_Name", "Description" });
/* 373 */     List<Integer> data = new ArrayList();
/* 374 */     List<SPSControllerVCI> controllers = reference.getControllers();
/* 375 */     Set<Object> entries = new HashSet();
/* 376 */     if (controllers == null)
/*     */       return; 
/*     */     int c;
/* 379 */     for (c = 0; c < controllers.size(); c++) {
/* 380 */       SPSControllerVCI controller = controllers.get(c);
/* 381 */       Object key = controller.getID() + ":" + controller.getDescription();
/* 382 */       if (!entries.contains(key)) {
/* 383 */         data.add(Integer.valueOf(controller.getID()));
/* 384 */         String description = controller.getDescription();
/* 385 */         if (description.indexOf('\t') >= 0) {
/* 386 */           data.add(description.substring(0, description.indexOf('\t')));
/* 387 */           data.add(description.substring(description.indexOf('\t') + 1));
/*     */         } else {
/* 389 */           data.add("");
/* 390 */           data.add(description);
/*     */         } 
/* 392 */         entries.add(key);
/*     */       } 
/*     */     } 
/* 395 */     table.add(data);
/* 396 */     this.tables.add(table);
/* 397 */     table = new DatabaseTable("Supported_Controllers Table", new String[] { "VCI", "Pre_Programming_Inst", "Post_Programming_Inst", "AfterMarket" });
/* 398 */     data = new ArrayList<Integer>();
/* 399 */     entries.clear();
/* 400 */     for (c = 0; c < controllers.size(); c++) {
/* 401 */       SPSControllerVCI controller = controllers.get(c);
/* 402 */       Object key = controller.getID() + ":" + controller.getDescription();
/* 403 */       if (!entries.contains(key)) {
/* 404 */         data.add(Integer.valueOf(controller.getControllerVCI()));
/* 405 */         data.add(SPSProgrammingInstructions.toString(controller.getPreProgrammingInstructions()));
/* 406 */         data.add(SPSProgrammingInstructions.toString(controller.getPostProgrammingInstructions()));
/* 407 */         data.add(controller.getAfterMarketFlag());
/* 408 */         entries.add(key);
/*     */       } 
/*     */     } 
/* 411 */     table.add(data);
/* 412 */     this.tables.add(table);
/* 413 */     List<SPSProgrammingType> methods = reference.getProgrammingMethods();
/* 414 */     if (methods != null) {
/* 415 */       table = new DatabaseTable("Programming_Method_Description Table", new String[] { "Programming_Method", "Description" });
/* 416 */       for (int i = 0; i < methods.size(); i++) {
/* 417 */         SPSProgrammingType method = methods.get(i);
/* 418 */         data = new ArrayList<Integer>();
/* 419 */         data.add(method.getID());
/* 420 */         data.add(method.getDescription());
/* 421 */         table.add(data);
/*     */       } 
/* 423 */       this.tables.add(table);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class DatabaseTable
/*     */     implements DatabaseInfo.Table
/*     */   {
/*     */     protected String title;
/*     */     protected String[] header;
/* 432 */     protected List data = new ArrayList();
/*     */     
/*     */     protected DatabaseTable(String title, String[] header) {
/* 435 */       this.title = title;
/* 436 */       this.header = header;
/*     */     }
/*     */     
/*     */     protected void add(List row) {
/* 440 */       this.data.add(row);
/*     */     }
/*     */     
/*     */     public String getTitle() {
/* 444 */       return this.title;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 448 */       return this.data.size() + 1;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 452 */       return this.header.length;
/*     */     }
/*     */     
/*     */     public Object getContent(int rowIndex, int colIndex) {
/* 456 */       if (rowIndex == 0) {
/* 457 */         return this.header[colIndex];
/*     */       }
/* 459 */       List row = this.data.get(rowIndex - 1);
/* 460 */       return row.get(colIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isHeader(int rowIndex, int colIndex) {
/* 465 */       return (rowIndex == 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSDatabaseInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */