/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSProgrammingData
/*     */   extends SPSProgrammingData
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient boolean valid = true;
/*     */   protected boolean skipSameCalibration = false;
/*     */   protected String odometer;
/*     */   protected String keycode;
/*     */   protected List optionBytes;
/*     */   protected List transfer;
/*     */   
/*     */   void setVIN(String vin) {
/*  33 */     this.vin = vin;
/*     */   }
/*     */   
/*     */   void setCalibrationFiles(List files) {
/*  37 */     this.files = files;
/*     */   }
/*     */   
/*     */   void setDeviceID(Integer deviceID) {
/*  41 */     this.deviceID = deviceID;
/*     */   }
/*     */   
/*     */   void setVMECUHN(String vmecuhn) {
/*  45 */     this.vmecuhn = vmecuhn;
/*     */   }
/*     */   
/*     */   void setSSECUSVN(String ssecusvn) {
/*  49 */     this.ssecusvn = ssecusvn;
/*     */   }
/*     */   
/*     */   void setOptionBytes(List optionBytes) {
/*  53 */     this.optionBytes = optionBytes;
/*     */   }
/*     */   
/*     */   public List getOptionBytes() {
/*  57 */     return this.optionBytes;
/*     */   }
/*     */   
/*     */   public void setOdometer(String odometer) {
/*  61 */     this.odometer = odometer;
/*     */   }
/*     */   
/*     */   public void setKeycode(String keycode) {
/*  65 */     this.keycode = keycode;
/*     */   }
/*     */   
/*     */   public String getOdometer() {
/*  69 */     return this.odometer;
/*     */   }
/*     */   
/*     */   public String getKeycode() {
/*  73 */     return this.keycode;
/*     */   }
/*     */   
/*     */   void setVIT1TransferAttributes(List transfer) {
/*  77 */     this.transfer = transfer;
/*     */   }
/*     */   
/*     */   public List getVIT1TransferAttributes() {
/*  81 */     return this.transfer;
/*     */   }
/*     */   
/*     */   boolean isValid() {
/*  85 */     return this.valid;
/*     */   }
/*     */   
/*     */   public boolean skipSameCalibration() {
/*  89 */     return this.skipSameCalibration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setArchive(SPSLanguage language, Archive archive, SPSSchemaAdapterGlobal adapter) {
/*  97 */     SPSPart part = new SPSPart(archive.getPartNo(), archive.getChangeReason(), adapter);
/*  98 */     this.files = archive.getCalibrationUnits();
/*  99 */     SPSModule module = new SPSModule(language, "238", part);
/* 100 */     module.setDescription(ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.zip-file"));
/* 101 */     this.modules = new ArrayList();
/* 102 */     this.modules.add(module);
/*     */   }
/*     */   
/*     */   void setPartFile(SPSLanguage language, PartFile file, SPSSchemaAdapterGlobal adapter) {
/* 106 */     List<String> parts = file.getParts();
/* 107 */     List<String> modids = file.getModules();
/* 108 */     this.modules = new ArrayList();
/* 109 */     for (int i = 0; i < parts.size(); i++) {
/* 110 */       String part = parts.get(i);
/* 111 */       String module = modids.get(i);
/* 112 */       this.modules.add(new SPSModule(language, module, new SPSPart(part, adapter)));
/*     */     } 
/*     */   }
/*     */   
/*     */   void invalidate() {
/* 117 */     this.valid = false;
/*     */   }
/*     */   
/*     */   void indicateSameCalibration() {
/* 121 */     this.skipSameCalibration = true;
/*     */   }
/*     */   
/*     */   void add(SPSLanguage language, SPSPartEI partEI, String module) {
/* 125 */     if (this.modules == null) {
/* 126 */       this.modules = new ArrayList();
/*     */     }
/* 128 */     this.modules.add(new SPSModule(language, module, partEI));
/*     */   }
/*     */   
/*     */   void add(SPSLanguage language, String part, String module, SPSSchemaAdapterGlobal adapter) {
/* 132 */     if (this.modules == null) {
/* 133 */       this.modules = new ArrayList();
/*     */     }
/* 135 */     this.modules.add(new SPSModule(language, module, new SPSPart(part, adapter)));
/*     */   }
/*     */   
/*     */   void build(SPSLanguage language, List hardware, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 139 */     build(language, hardware, (Set)null, adapter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void build(SPSLanguage language, List hardware, Set usage, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 149 */     for (int i = 0; i < this.modules.size(); i++) {
/* 150 */       SPSModule module = this.modules.get(i);
/* 151 */       SPSPart origin = (SPSPart)module.getOriginPart();
/* 152 */       origin.setOriginPartFlagTrue();
/* 153 */       SPSCOP.handle(language, origin, origin, usage, adapter);
/*     */     } 
/* 155 */     hardware = confirmHardwareList(hardware, adapter);
/* 156 */     checkHardwareList(language, hardware, adapter);
/* 157 */     if (this.hardware == null) {
/* 158 */       computeSelectionMode();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void computeSelectionMode() throws Exception {
/* 163 */     for (int i = 0; i < this.modules.size(); i++) {
/* 164 */       SPSModule module = this.modules.get(i);
/* 165 */       SPSPart origin = (SPSPart)module.getOriginPart();
/* 166 */       SPSCOP.computeMode(origin);
/* 167 */       module.init();
/* 168 */       if (module.getID() == "0" && module.getSelectedPart() == null) {
/* 169 */         throw new SPSException(CommonException.NoValidCOP);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List confirmHardwareList(List hardware, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 175 */     if (hardware == null || hardware.size() == 0) {
/* 176 */       return hardware;
/*     */     }
/* 178 */     Iterator<SPSHardware> it = hardware.iterator();
/* 179 */     while (it.hasNext()) {
/* 180 */       SPSHardware part = it.next();
/* 181 */       if (part.isVIT1Hardware() && !confirmHardware(part, adapter)) {
/* 182 */         it.remove();
/*     */       }
/*     */     } 
/* 185 */     return (hardware.size() == 0) ? null : hardware;
/*     */   }
/*     */   
/*     */   protected boolean confirmHardware(SPSHardware hardware, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 189 */     for (int i = 0; i < this.modules.size(); i++) {
/* 190 */       SPSModule module = this.modules.get(i);
/* 191 */       SPSPart part = (SPSPart)module.getOriginPart();
/* 192 */       if (confirmHardware(part, hardware, adapter)) {
/* 193 */         return true;
/*     */       }
/*     */     } 
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean confirmHardware(SPSPart part, SPSHardware hardware, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 200 */     if (part.getHardwareIndex() > 0) {
/* 201 */       SPSHardwareIndex hwidx = SPSHardwareIndex.getHardwareIndex(part.getHardwareIndex(), adapter);
/* 202 */       if (hwidx != null && 
/* 203 */         hwidx.contains(hardware)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 208 */     List cop = part.getCOP();
/* 209 */     if (cop != null) {
/* 210 */       Iterator<SPSCOP> it = cop.iterator();
/* 211 */       while (it.hasNext()) {
/* 212 */         part = (SPSPart)((SPSCOP)it.next()).getPart();
/* 213 */         if (confirmHardware(part, hardware, adapter)) {
/* 214 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 218 */     return false;
/*     */   }
/*     */   
/*     */   protected void checkHardwareList(SPSLanguage language, List parts, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 222 */     if (language != null) {
/* 223 */       this.hardware = new ArrayList();
/*     */     }
/* 225 */     for (int i = 0; i < this.modules.size(); i++) {
/* 226 */       SPSModule module = this.modules.get(i);
/* 227 */       SPSPart part = (SPSPart)module.getOriginPart();
/* 228 */       if (!checkHardware(language, parts, part, this.hardware, adapter));
/*     */     } 
/*     */ 
/*     */     
/* 232 */     if (this.hardware.size() == 0) {
/* 233 */       this.hardware = null;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean checkHardware(SPSLanguage language, List parts, SPSPart part, List<SPSHardwareIndex> hardware, SPSSchemaAdapterGlobal adapter) {
/* 238 */     if (part.getHardwareIndex() > 0) {
/* 239 */       SPSHardwareIndex hwidx = null;
/* 240 */       if (hwidx != null) {
/* 241 */         if (parts != null) {
/* 242 */           if (!hwidx.contains(parts)) {
/* 243 */             return false;
/*     */           }
/*     */         }
/* 246 */         else if (!hardware.contains(hwidx)) {
/* 247 */           hwidx.setDescription(language, adapter);
/* 248 */           hardware.add(hwidx);
/*     */         } 
/*     */       }
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
/* 265 */     List cop = part.getCOP();
/* 266 */     if (cop != null) {
/* 267 */       Iterator<SPSCOP> it = cop.iterator();
/* 268 */       while (it.hasNext()) {
/* 269 */         part = (SPSPart)((SPSCOP)it.next()).getPart();
/* 270 */         if (!checkHardware(language, parts, part, hardware, adapter)) {
/* 271 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 275 */     return true;
/*     */   }
/*     */   
/*     */   void update(List hardware, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 279 */     checkHardwareList((SPSLanguage)null, hardware, adapter);
/* 280 */     computeSelectionMode();
/* 281 */     this.hardware = null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */