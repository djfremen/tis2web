/*     */ package com.eoos.gm.tis2web.sps.common.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.OptionByte;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.OptionByteImpl;
/*     */ import com.eoos.tokenizer.StringTokenizer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class VIT1DataImpl
/*     */   implements VIT1Data
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected List devices;
/*     */   protected String ID;
/*     */   protected String CompatibilityNo;
/*  24 */   protected int Protocol = -1;
/*  25 */   protected int ECUAddress = -1;
/*     */   protected String OPNumber;
/*     */   protected String HardwareNumber;
/*     */   protected String SNOET;
/*     */   protected String SoftwareVersion;
/*     */   protected List Parts;
/*     */   protected List Seeds;
/*     */   protected String NavInfo;
/*     */   protected String DevType;
/*     */   protected String SPSMode;
/*     */   protected int SeedCount;
/*     */   protected String Odometer;
/*     */   protected String Keycode;
/*     */   protected Map OptionBytes;
/*     */   protected Map AdaptiveBytes;
/*     */   protected Map partsByDevice;
/*     */   protected boolean ReadCVN;
/*     */   protected List PartNumbers;
/*     */   protected Map partnumsByDevice;
/*     */   protected List SubAssemblyParts;
/*     */   protected Map subasmsByDevice;
/*     */   protected boolean valid;
/*     */   protected int ecu;
/*     */   protected List options;
/*     */   protected VIT vit;
/*     */   
/*     */   public VIT getVIT() {
/*  52 */     return this.vit;
/*     */   }
/*     */   
/*     */   public VIT1DataImpl(VIT1 vit1, boolean isNAO) {
/*  56 */     this.devices = extractDeviceList(vit1);
/*  57 */     if (isNAO) {
/*  58 */       this.NavInfo = vit1.getAttrValue("nav_info");
/*  59 */       this.DevType = vit1.getAttrValue("devicetype");
/*  60 */       this.SPSMode = vit1.getAttrValue("spsmode");
/*  61 */       this.SeedCount = computeSeedCount(vit1);
/*  62 */       handleSaturnAttributes(vit1);
/*  63 */       handleParts(vit1);
/*     */     } else {
/*  65 */       this.vit = (VIT)vit1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public VIT1DataImpl(Integer systemID, VIT1 vit1) {
/*  70 */     selectDevice(vit1, systemID.intValue(), (String)vit1.getVIN());
/*     */   }
/*     */   
/*     */   public VIT1DataImpl(Integer systemID, String vin, VIT1 vit1) {
/*  74 */     selectDevice(vit1, systemID.intValue(), vin);
/*     */   }
/*     */   
/*     */   public void merge(VIT1Data data, List<String> attributes) {
/*  78 */     if (attributes == null) {
/*     */       return;
/*     */     }
/*  81 */     for (int i = 0; i < attributes.size(); i++) {
/*  82 */       String attribute = attributes.get(i);
/*  83 */       if (attribute.equals("sps_id")) {
/*  84 */         this.ID = data.getID();
/*  85 */       } else if (attribute.equals("swcompat_id")) {
/*  86 */         this.CompatibilityNo = data.getCompatibilityNr();
/*  87 */       } else if (attribute.equals("protocol")) {
/*  88 */         this.Protocol = data.getProtocol();
/*  89 */       } else if (attribute.equals("vmecuhn")) {
/*  90 */         this.OPNumber = data.getOPNumber();
/*  91 */       } else if (attribute.equals("ssecuhn")) {
/*  92 */         this.HardwareNumber = data.getHWNumber();
/*  93 */       } else if (attribute.equals("ssecusvn")) {
/*  94 */         this.SoftwareVersion = data.getSWVersion();
/*  95 */       } else if (attribute.equals("snoet")) {
/*  96 */         this.SNOET = data.getSNOET();
/*  97 */       } else if (attribute.equals("partno")) {
/*  98 */         this.Parts = data.getParts();
/*  99 */       } else if (attribute.equals("cvn_in_vit1")) {
/* 100 */         this.ReadCVN = data.getReadCVN();
/* 101 */       } else if (attribute.equals("partnum")) {
/* 102 */         this.PartNumbers = data.getPartNumbers();
/* 103 */       } else if (attribute.equals("sub_asm")) {
/* 104 */         this.SubAssemblyParts = data.getSubAssembly();
/* 105 */       } else if (attribute.equals("seed")) {
/* 106 */         this.Seeds = getSeeds();
/* 107 */       } else if (attribute.equals("odometer")) {
/* 108 */         this.Odometer = getOdometer();
/* 109 */       } else if (attribute.equals("keycode")) {
/* 110 */         this.Keycode = getKeycode();
/* 111 */       } else if (attribute.equals("ecu_config_data_length")) {
/* 112 */         this.OptionBytes = getOptionBytes();
/* 113 */         this.AdaptiveBytes = getAdaptiveBytes();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void selectDevice(VIT1 vit1, int systemID, String vin) {
/* 119 */     this.vit = vit1.getControlModuleBlock(Integer.valueOf(systemID));
/* 120 */     if (this.vit == null) {
/*     */       return;
/*     */     }
/* 123 */     if (vin.equals(this.vit.getAttrValue("vin"))) {
/* 124 */       this.valid = true;
/*     */     }
/* 126 */     this.ID = vit1.getAttrValue("sps_id");
/* 127 */     if (this.ID != null) {
/* 128 */       this.ID = this.ID.toUpperCase(Locale.ENGLISH);
/*     */     }
/* 130 */     this.CompatibilityNo = this.vit.getAttrValue("swcompat_id");
/* 131 */     if (this.vit.getAttrValue("protocol") != null) {
/* 132 */       this.Protocol = Integer.parseInt(this.vit.getAttrValue("protocol"));
/*     */     }
/* 134 */     if (this.vit.getAttrValue("int_ver") != null) {
/* 135 */       this.Protocol = Integer.parseInt(this.vit.getAttrValue("int_ver"));
/*     */     }
/* 137 */     this.ECUAddress = systemID;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.OPNumber = handleVMECUHN(this.vit.getAttrValue("vmecuhn"));
/* 143 */     if (this.OPNumber != null) {
/* 144 */       this.OPNumber = this.OPNumber.trim();
/*     */     }
/* 146 */     this.HardwareNumber = this.vit.getAttrValue("ssecuhn");
/* 147 */     if (this.HardwareNumber != null) {
/* 148 */       this.HardwareNumber = this.HardwareNumber.trim();
/*     */     }
/* 150 */     this.SNOET = this.vit.getAttrValue("snoet");
/* 151 */     this.SoftwareVersion = handleSSECUSVN(this.vit.getAttrValue("ssecusvn"));
/* 152 */     if (this.SoftwareVersion != null) {
/* 153 */       this.SoftwareVersion = this.SoftwareVersion.trim();
/*     */     }
/* 155 */     this.Parts = this.vit.getAttrValues("partno");
/* 156 */     String readCVN = this.vit.getAttrValue("cvn_in_vit1");
/* 157 */     this.ReadCVN = (readCVN != null && readCVN.equals("1"));
/* 158 */     if (this.ReadCVN) {
/* 159 */       this.PartNumbers = this.vit.getAttrValues("partnum");
/* 160 */       if ((this.Parts == null || this.Parts.size() == 0) && this.PartNumbers != null && this.PartNumbers.size() > 0) {
/* 161 */         this.Parts = discardCVNs(this.PartNumbers);
/*     */       }
/* 163 */       this.SubAssemblyParts = this.vit.getAttrValues("sub_asm");
/*     */     } 
/* 165 */     this.Seeds = this.vit.getAttrValues("seed");
/* 166 */     VIT fo = vit1.getFreeOptions();
/* 167 */     if (fo != null && fo.getAttributes() != null) {
/* 168 */       this.options = fo.getAttributes();
/*     */     }
/* 170 */     if (this.Odometer == null && this.vit.getAttrValue("odometer") != null) {
/* 171 */       this.Odometer = this.vit.getAttrValue("odometer");
/*     */     }
/* 173 */     if (this.Keycode == null && this.vit.getAttrValue("keycode") != null) {
/* 174 */       this.Keycode = this.vit.getAttrValue("keycode");
/*     */     }
/*     */   }
/*     */   
/*     */   protected int computeSeedCount(VIT1 vit1) {
/* 179 */     int count = 0;
/* 180 */     if (vit1.getControlModuleBlocks() != null) {
/* 181 */       for (int i = 0; i < vit1.getControlModuleBlocks().size(); i++) {
/* 182 */         VIT vit = vit1.getControlModuleBlocks().get(i);
/* 183 */         List seeds = vit.getAttrValues("seed");
/* 184 */         if (seeds != null) {
/* 185 */           count += seeds.size();
/*     */         }
/*     */       } 
/*     */     }
/* 189 */     return count;
/*     */   }
/*     */   
/*     */   protected List extractDeviceList(VIT1 vit1) {
/* 193 */     List<Integer> devices = new ArrayList();
/*     */     try {
/* 195 */       if (vit1.getControlModuleBlocks() != null) {
/* 196 */         for (int i = 0; i < vit1.getControlModuleBlocks().size(); i++) {
/* 197 */           VIT vit = vit1.getControlModuleBlocks().get(i);
/* 198 */           String ecu = vit.getAttrValue("ecu_adr");
/* 199 */           devices.add(Integer.valueOf(Integer.parseInt(ecu, 16)));
/*     */         } 
/* 201 */         return devices;
/*     */       } 
/* 203 */       return null;
/*     */     }
/* 205 */     catch (Exception e) {
/* 206 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getDeviceList() {
/* 211 */     return this.devices;
/*     */   }
/*     */   
/*     */   protected String handleVMECUHN(String part) {
/* 215 */     if (part == null) {
/* 216 */       return null;
/*     */     }
/* 218 */     StringBuffer result = new StringBuffer();
/* 219 */     for (int i = 0; i < part.length(); i++) {
/* 220 */       char c = part.charAt(i);
/* 221 */       if (c == ' ') {
/*     */         break;
/*     */       }
/* 224 */       result.append(c);
/*     */     } 
/* 226 */     return result.toString();
/*     */   }
/*     */   
/*     */   protected String handleSSECUSVN(String version) {
/* 230 */     if (version == null) {
/* 231 */       return null;
/*     */     }
/* 233 */     StringBuffer result = new StringBuffer();
/* 234 */     for (int i = 0; i < version.length(); i++) {
/* 235 */       char c = version.charAt(i);
/* 236 */       if (i != 48 || result.length() != 0)
/*     */       {
/*     */         
/* 239 */         result.append(c); } 
/*     */     } 
/* 241 */     return result.toString();
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 245 */     return this.valid;
/*     */   }
/*     */   
/*     */   public int getECU() {
/* 249 */     return this.ecu;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 253 */     return this.ID;
/*     */   }
/*     */   
/*     */   public String getCompatibilityNr() {
/* 257 */     return this.CompatibilityNo;
/*     */   }
/*     */   
/*     */   public int getProtocol() {
/* 261 */     return this.Protocol;
/*     */   }
/*     */   
/*     */   public int getECUAdress() {
/* 265 */     return this.ECUAddress;
/*     */   }
/*     */   
/*     */   public String getOPNumber() {
/* 269 */     return this.OPNumber;
/*     */   }
/*     */   
/*     */   public String getHWNumber() {
/* 273 */     return this.HardwareNumber;
/*     */   }
/*     */   
/*     */   public String getSNOET() {
/* 277 */     return this.SNOET;
/*     */   }
/*     */   
/*     */   public List getTokenizedSNOET() {
/* 281 */     List vals = new ArrayList();
/* 282 */     StringTokenizer stringTokenizer = new StringTokenizer(this.SNOET, " ");
/* 283 */     Iterator it = stringTokenizer.iterator();
/* 284 */     while (it.hasNext()) {
/* 285 */       vals.add(it.next());
/*     */     }
/* 287 */     return vals;
/*     */   }
/*     */   
/*     */   public String getSWVersion() {
/* 291 */     return this.SoftwareVersion;
/*     */   }
/*     */   
/*     */   public List getParts() {
/* 295 */     return this.Parts;
/*     */   }
/*     */   
/*     */   public List getParts(int device) {
/* 299 */     return (this.partsByDevice == null) ? null : (List)this.partsByDevice.get(Integer.valueOf(device));
/*     */   }
/*     */   
/*     */   public List getSeeds() {
/* 303 */     return this.Seeds;
/*     */   }
/*     */   
/*     */   public List getOptions() {
/* 307 */     return this.options;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 311 */     return this.DevType;
/*     */   }
/*     */   
/*     */   public String getSPSMode() {
/* 315 */     return this.SPSMode;
/*     */   }
/*     */   
/*     */   public String getNavigationInfo() {
/* 319 */     return this.NavInfo;
/*     */   }
/*     */   
/*     */   public int getSeedCount() {
/* 323 */     return this.SeedCount;
/*     */   }
/*     */   
/*     */   public void setECU(int ecu) {
/* 327 */     this.ecu = ecu;
/*     */   }
/*     */   
/*     */   protected void handleParts(VIT1 vit1) {
/* 331 */     this.PartNumbers = vit1.getAttrValues("partnum");
/* 332 */     this.partsByDevice = new HashMap<Object, Object>();
/* 333 */     if (this.PartNumbers != null && this.PartNumbers.size() > 0) {
/* 334 */       handleParts(vit1, this.partsByDevice, "partnum");
/* 335 */       this.partnumsByDevice = new HashMap<Object, Object>();
/* 336 */       handleParts(vit1, this.partnumsByDevice, "partnum");
/* 337 */       if (this.Parts == null || this.Parts.size() == 0) {
/* 338 */         this.Parts = discardCVNs(this.PartNumbers);
/*     */       }
/*     */     } else {
/* 341 */       handleParts(vit1, this.partsByDevice, "partno");
/*     */     } 
/* 343 */     this.SubAssemblyParts = vit1.getAttrValues("sub_asm");
/* 344 */     if (this.SubAssemblyParts != null && this.SubAssemblyParts.size() > 0) {
/* 345 */       this.subasmsByDevice = new HashMap<Object, Object>();
/* 346 */       handleParts(vit1, this.subasmsByDevice, "sub_asm");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleParts(VIT1 vit1, Map<Integer, List> map, String attribute) {
/*     */     try {
/* 352 */       if (vit1.getControlModuleBlocks() != null) {
/* 353 */         for (int i = 0; i < vit1.getControlModuleBlocks().size(); i++) {
/* 354 */           VIT vit = vit1.getControlModuleBlocks().get(i);
/* 355 */           List parts = vit.getAttrValues(attribute);
/* 356 */           if (parts != null && parts.size() > 0 && 
/* 357 */             vit.getAttrValue("ecu_adr") != null) {
/* 358 */             int ecu = Integer.parseInt(vit.getAttrValue("ecu_adr"), 16);
/* 359 */             if ("partnum".equals(attribute) && map == this.partsByDevice) {
/* 360 */               parts = discardCVNs(parts);
/*     */             }
/* 362 */             map.put(Integer.valueOf(ecu), parts);
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 367 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected List discardCVNs(List<String> partnums) {
/* 372 */     List<String> parts = new ArrayList();
/* 373 */     for (int i = 0; i < partnums.size(); i++) {
/* 374 */       String partnum = partnums.get(i);
/* 375 */       int idx = partnum.indexOf('-');
/* 376 */       if (idx >= 0) {
/* 377 */         parts.add(partnum.substring(idx + 1));
/*     */       } else {
/* 379 */         parts.add(partnum);
/*     */       } 
/*     */     } 
/* 382 */     return parts;
/*     */   }
/*     */   
/*     */   protected void handleSaturnAttributes(VIT1 vit1) {
/*     */     try {
/* 387 */       if (vit1.getAttrValue("odometer") != null) {
/* 388 */         this.Odometer = vit1.getAttrValue("odometer");
/*     */       }
/* 390 */       if (vit1.getAttrValue("keycode") != null) {
/* 391 */         this.Keycode = vit1.getAttrValue("keycode");
/*     */       }
/* 393 */       if (vit1.getControlModuleBlocks() != null) {
/* 394 */         this.OptionBytes = new HashMap<Object, Object>();
/* 395 */         this.AdaptiveBytes = new HashMap<Object, Object>();
/* 396 */         for (int i = 0; i < vit1.getControlModuleBlocks().size(); i++) {
/* 397 */           VIT vit = vit1.getControlModuleBlocks().get(i);
/* 398 */           handleSaturnAttributes(vit);
/*     */         } 
/* 400 */         if (this.OptionBytes.size() == 0) {
/* 401 */           this.OptionBytes = null;
/*     */         }
/* 403 */         if (this.AdaptiveBytes.size() == 0) {
/* 404 */           this.AdaptiveBytes = null;
/*     */         }
/*     */       } 
/* 407 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSaturnAttributes(VIT vit) {
/* 412 */     String ecu = vit.getAttrValue("ecu_adr");
/* 413 */     Integer device = Integer.valueOf(ecu, 16);
/* 414 */     if (this.Odometer == null && vit.getAttrValue("odometer") != null) {
/* 415 */       this.Odometer = vit.getAttrValue("odometer");
/*     */     }
/* 417 */     if (this.Keycode == null && vit.getAttrValue("keycode") != null) {
/* 418 */       this.Keycode = vit.getAttrValue("keycode");
/*     */     }
/* 420 */     List<Pair> attributes = vit.getAttributes();
/* 421 */     for (int i = 0; i < attributes.size(); i++) {
/* 422 */       Pair pair = attributes.get(i);
/* 423 */       String attribute = (String)pair.getFirst();
/* 424 */       String value = (String)pair.getSecond();
/* 425 */       if (attribute.equals("ecu_config_data_length")) {
/* 426 */         int length = Integer.parseInt(value, 16);
/* 427 */         OptionByteImpl record = null;
/* 428 */         for (int j = 0; j < length; j++, i++) {
/* 429 */           pair = attributes.get(i + 1);
/* 430 */           attribute = (String)pair.getFirst();
/* 431 */           value = (String)pair.getSecond();
/* 432 */           if (attribute.equals("type")) {
/* 433 */             if (value.equals("01")) {
/* 434 */               record = new OptionByteImpl(1, device.intValue());
/* 435 */             } else if (value.equals("00")) {
/* 436 */               record = new OptionByteImpl(0, device.intValue());
/*     */             } else {
/* 438 */               record = null;
/*     */             } 
/* 440 */           } else if (attribute.equals("blocknum")) {
/* 441 */             if (record != null) {
/* 442 */               record.setBlockNo(value);
/*     */             }
/* 444 */           } else if (attribute.equals("bytenum")) {
/* 445 */             if (record != null) {
/* 446 */               record.setByteOffset(value);
/*     */             }
/* 448 */           } else if (attribute.equals("bytedata") && 
/* 449 */             record != null) {
/* 450 */             record.setData(value);
/* 451 */             if (record.getType() == 1) {
/* 452 */               addSaturnBytes(device, this.OptionBytes, (OptionByte)record);
/*     */             } else {
/* 454 */               addSaturnBytes(device, this.AdaptiveBytes, (OptionByte)record);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSaturnBytes(Integer device, Map<Integer, List> bytes, OptionByte record) {
/* 464 */     List<OptionByte> entries = (List)bytes.get(device);
/* 465 */     if (entries == null) {
/* 466 */       entries = new ArrayList();
/* 467 */       bytes.put(device, entries);
/*     */     } 
/* 469 */     entries.add(record);
/*     */   }
/*     */   
/*     */   public String getOdometer() {
/* 473 */     return this.Odometer;
/*     */   }
/*     */   
/*     */   public String getKeycode() {
/* 477 */     return this.Keycode;
/*     */   }
/*     */   
/*     */   public Map getOptionBytes() {
/* 481 */     return this.OptionBytes;
/*     */   }
/*     */   
/*     */   public Map getAdaptiveBytes() {
/* 485 */     return this.AdaptiveBytes;
/*     */   }
/*     */   
/*     */   public List getOptionBytes(int device) {
/* 489 */     return (this.OptionBytes == null) ? null : (List)this.OptionBytes.get(Integer.valueOf(device));
/*     */   }
/*     */   
/*     */   public List getAdaptiveBytes(int device) {
/* 493 */     return (this.AdaptiveBytes == null) ? null : (List)this.AdaptiveBytes.get(Integer.valueOf(device));
/*     */   }
/*     */   
/*     */   public boolean getReadCVN() {
/* 497 */     return this.ReadCVN;
/*     */   }
/*     */   
/*     */   public List getPartNumbers() {
/* 501 */     return this.PartNumbers;
/*     */   }
/*     */   
/*     */   public List getPartNumbers(int device) {
/* 505 */     return (this.partnumsByDevice == null) ? this.PartNumbers : (List)this.partnumsByDevice.get(Integer.valueOf(device));
/*     */   }
/*     */   
/*     */   public List getSubAssembly() {
/* 509 */     return this.SubAssemblyParts;
/*     */   }
/*     */   
/*     */   public List getSubAssembly(int device) {
/* 513 */     return (this.subasmsByDevice == null) ? this.SubAssemblyParts : (List)this.subasmsByDevice.get(Integer.valueOf(device));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VIT1DataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */