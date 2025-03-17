/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSEvents;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLaborTime;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Attachment;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.EntryImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSEvents extends SPSEvents {
/*  29 */   private static final Logger log = Logger.getLogger(SPSEvents.class);
/*     */   
/*     */   @SuppressWarnings({"STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE"})
/*     */   static void logReprogramEvent(AttributeValueMap data, SPSSession session, SPSSchemaAdapterNAO adapter) throws RequestException {
/*     */     try {
/*  34 */       Attachment[] attachments = getVIT1Attachments(data);
/*     */       
/*  36 */       session.markFinished();
/*     */       
/*  38 */       String eventName = "reprogram";
/*  39 */       List<SPSEventLog.Attribute> attributes = new LinkedList();
/*  40 */       Collection<SPSEventLog.Flag> flags = new LinkedList();
/*  41 */       boolean success = CommonValue.OK.equals(data.getValue(CommonAttribute.REPROGRAM));
/*  42 */       boolean replace = CommonValue.REPLACE_AND_REPROGRAM.equals(data.getValue(CommonAttribute.MODE));
/*     */       
/*  44 */       synchronized (DFORMAT) {
/*  45 */         attributes.add(new SPSEventLog.Attribute("date", DFORMAT.format(new Date())));
/*     */       } 
/*  47 */       attributes.add(new SPSEventLog.Attribute("replace", String.valueOf(replace)));
/*  48 */       attributes.add(new SPSEventLog.Attribute("status", success ? "success" : "failed"));
/*  49 */       attributes.add(new SPSEventLog.Attribute("sessionID", (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID)));
/*  50 */       attributes.add(new SPSEventLog.Attribute("bac_code", (String)AVUtil.accessValue(data, CommonAttribute.BACCODE)));
/*  51 */       attributes.add(new SPSEventLog.Attribute("vin", String.valueOf(session.getVehicle().getVIN())));
/*  52 */       attributes.add(new SPSEventLog.Attribute("deviceID", String.valueOf(session.getController().getDeviceID())));
/*  53 */       attributes.add(new SPSEventLog.Attribute("device", (String)AVUtil.accessValue(data, CommonAttribute.DEVICE)));
/*     */       
/*     */       try {
/*  56 */         String label = session.getController().getLabel();
/*  57 */         if (label != null) {
/*  58 */           attributes.add(new SPSEventLog.Attribute("controller", label));
/*     */         } else {
/*  60 */           log.debug("unable to add 'controller' attribute to event-log entry, label unavailable");
/*     */         } 
/*  62 */       } catch (Exception e) {
/*  63 */         log.error("unable to add 'controller' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*     */       try {
/*  67 */         String toolID = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_NAME);
/*  68 */         if (toolID != null) {
/*  69 */           attributes.add(new SPSEventLog.Attribute("j2534name", toolID));
/*     */         }
/*  71 */       } catch (Exception e) {
/*  72 */         log.error("unable to add 'j2534name' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*     */       try {
/*  76 */         String version = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_VERSION);
/*  77 */         if (version != null) {
/*  78 */           attributes.add(new SPSEventLog.Attribute("j2534version", version));
/*     */         }
/*  80 */       } catch (Exception e) {
/*  81 */         log.error("unable to add 'j2534version' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*  84 */       if (session.getController() instanceof SPSControllerVCI) {
/*  85 */         int vci = ((SPSControllerVCI)session.getController()).getVCI();
/*  86 */         attributes.add(new SPSEventLog.Attribute("vci", Integer.toString(vci)));
/*     */       } 
/*     */       
/*  89 */       if (data.getValue(CommonAttribute.LABOR_TIME_TRACKING) != null) {
/*  90 */         SPSLaborTime.log(attributes, data.getValue(CommonAttribute.LABOR_TIME_TRACKING));
/*  91 */         if (data.getValue(CommonAttribute.WARRANTY_CLAIM_CODE) != null) {
/*  92 */           attributes.add(new SPSEventLog.Attribute("claimcode", (String)AVUtil.accessValue(data, CommonAttribute.WARRANTY_CLAIM_CODE)));
/*     */         } else {
/*  94 */           String wcc = computeWarrantyClaimCode(data);
/*  95 */           if (wcc != null) {
/*  96 */             attributes.add(new SPSEventLog.Attribute("claimcode", wcc));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       if (session.getController() instanceof SPSProgrammingSequence) {
/* 105 */         SPSProgrammingSequence SPSps = (SPSProgrammingSequence)session.getController();
/* 106 */         List Clist = SPSps.getSequence();
/* 107 */         for (int idx = 0; idx < Clist.size(); idx++) {
/* 108 */           SPSControllerVCI tcontroller = SPSps.getController(idx);
/* 109 */           int vci = tcontroller.getVCI();
/* 110 */           attributes.add(new SPSEventLog.Attribute("Programming Sequence: " + tcontroller.getDescription() + ": vci", Integer.toString(vci)));
/* 111 */           List<SPSProgrammingData> pdataList = SPSps.getProgrammingDataList();
/* 112 */           SPSProgrammingData pdata = pdataList.get(idx);
/* 113 */           List<SPSModule> modules = pdata.getModules();
/* 114 */           if (modules != null) {
/* 115 */             for (int i = 0; i < modules.size(); i++) {
/* 116 */               SPSModule module = modules.get(i);
/* 117 */               String selected = (module.getSelectedPart() == null) ? null : module.getSelectedPart().getPartNumber();
/*     */               
/* 119 */               boolean isEIL = (selected != null) ? SPSPart.isEndItemPart(selected, adapter) : false;
/* 120 */               if (selected == null && module.getCurrentCalibration() != null) {
/* 121 */                 isEIL = SPSPart.isEndItemPart(module.getCurrentCalibration(), adapter);
/*     */               } else {
/* 123 */                 isEIL = (modules.size() == 1);
/*     */               } 
/* 125 */               String partID = isEIL ? "eil" : ("#" + i);
/* 126 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current.id", module.getCurrentCalibration()));
/* 127 */               logCVNMismatchEvent(attributes, module, partID);
/* 128 */               if (module.isMismatchCVN()) {
/* 129 */                 logCVNMismatchEvent(attributes, module, partID);
/*     */               }
/* 131 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".selected.id", selected));
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       int onstar = 0;
/* 138 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 139 */       if (vit1 != null) {
/* 140 */         VIT vit = vit1.getVIT();
/* 141 */         if (vit != null) {
/* 142 */           String stid = vit.getAttrValue("StID");
/* 143 */           if (stid != null) {
/* 144 */             attributes.add(new SPSEventLog.Attribute("stid", stid));
/* 145 */             onstar++;
/*     */           } 
/*     */           
/* 148 */           String esn = vit.getAttrValue("ESN");
/* 149 */           if (esn != null) {
/* 150 */             attributes.add(new SPSEventLog.Attribute("esn", esn));
/* 151 */             onstar++;
/*     */           } 
/* 153 */           String meid = vit.getAttrValue("MEID");
/* 154 */           if (meid != null) {
/* 155 */             attributes.add(new SPSEventLog.Attribute("esn", meid));
/* 156 */             esn = meid;
/* 157 */             onstar++;
/*     */           } 
/* 159 */           String min = vit.getAttrValue("MIN");
/* 160 */           if (min != null) {
/* 161 */             attributes.add(new SPSEventLog.Attribute("min", min));
/* 162 */             onstar++;
/*     */           } 
/* 164 */           String mdn = vit.getAttrValue("MDN");
/* 165 */           if (mdn != null) {
/* 166 */             attributes.add(new SPSEventLog.Attribute("mdn", mdn));
/* 167 */             onstar++;
/*     */           } 
/* 169 */           if (onstar > 0 && onstar != 4) {
/* 170 */             if (stid == null) {
/* 171 */               attributes.add(new SPSEventLog.Attribute("stid", "0"));
/*     */             }
/* 173 */             if (esn == null) {
/* 174 */               attributes.add(new SPSEventLog.Attribute("esn", "0"));
/*     */             }
/* 176 */             if (min == null) {
/* 177 */               attributes.add(new SPSEventLog.Attribute("min", "0"));
/*     */             }
/* 179 */             if (mdn == null) {
/* 180 */               attributes.add(new SPSEventLog.Attribute("mdn", "0"));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       if (onstar > 0) {
/* 187 */         flags.add(SPSEventLog.FLAG_ONSTAR);
/*     */       }
/*     */       
/* 190 */       if (handleVoltAttributes(adapter, vit1, attributes)) {
/* 191 */         flags.add(SPSEventLog.FLAG_VOLT);
/*     */       }
/*     */       
/* 194 */       if (!success) {
/*     */         try {
/* 196 */           ProgrammingStatus status = (ProgrammingStatus)((ValueAdapter)data.getValue(CommonAttribute.REPROGRAM)).getAdaptee();
/* 197 */           attributes.add(new SPSEventLog.Attribute("error_details.step-number", String.valueOf(status.getStepNumber())));
/* 198 */           attributes.add(new SPSEventLog.Attribute("error_details.error-code", String.valueOf(status.getErrorCode())));
/* 199 */           attributes.add(new SPSEventLog.Attribute("error_details.message", status.getMessage()));
/*     */         }
/* 201 */         catch (Exception e) {
/* 202 */           log.error("... unable to determine error details - exception: " + e, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 207 */       if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null)
/*     */       {
/* 209 */         attributes.add(new SPSEventLog.Attribute("warning.reason", "same calibration"));
/*     */       }
/*     */       
/* 212 */       if (session.getController() instanceof SPSControllerVCI) {
/*     */         try {
/* 214 */           SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(adapter);
/* 215 */           List<SPSModule> modules = pdata.getModules();
/* 216 */           if (modules != null) {
/* 217 */             for (int i = 0; i < modules.size(); i++) {
/* 218 */               SPSModule module = modules.get(i);
/* 219 */               String selected = (module.getSelectedPart() == null) ? null : module.getSelectedPart().getPartNumber();
/*     */               
/* 221 */               boolean isEIL = (selected != null) ? SPSPart.isEndItemPart(selected, adapter) : false;
/* 222 */               if (selected == null && module.getCurrentCalibration() != null) {
/* 223 */                 isEIL = SPSPart.isEndItemPart(module.getCurrentCalibration(), adapter);
/*     */               } else {
/* 225 */                 isEIL = (modules.size() == 1);
/*     */               } 
/*     */               
/* 228 */               String partID = isEIL ? "eil" : ("#" + i);
/* 229 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current.id", module.getCurrentCalibration()));
/* 230 */               logCVNMismatchEvent(attributes, module, partID);
/* 231 */               if (module.isMismatchCVN()) {
/* 232 */                 logCVNMismatchEvent(attributes, module, partID);
/*     */               }
/* 234 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".selected.id", selected));
/*     */             }
/*     */           
/*     */           }
/* 238 */         } catch (Exception x) {
/* 239 */           log.warn("... unable to determine calibration - exception:" + x, x);
/*     */         } 
/*     */       }
/*     */       
/* 243 */       EntryImpl logEntry = EntryImpl.create(SPSEventLog.ADAPTER_NAO, eventName, attributes);
/* 244 */       SPSEventLogFacade.getInstance().add((SPSEventLog.Entry)logEntry, flags, attachments);
/*     */     }
/* 246 */     catch (RequestException e) {
/* 247 */       throw e;
/* 248 */     } catch (Exception e) {
/* 249 */       log.error("... unable to add entry - exception :" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean handleVoltAttributes(SPSSchemaAdapterNAO adapter, VIT1Data vit1, List attributes) {
/*     */     try {
/* 256 */       return adapter.getVoltAttributeHandler().extract(attributes, vit1.getVIT());
/* 257 */     } catch (Exception e) {
/*     */       
/* 259 */       return false;
/*     */     } 
/*     */   }
/*     */   static String computeWarrantyClaimCode(AttributeValueMap data) {
/*     */     try {
/* 264 */       String wcc = (String)AVUtil.accessValue(data, CommonAttribute.WARRANTY_CLAIM_CODE);
/* 265 */       if (wcc == null) {
/* 266 */         Integer dealerVCI = (Integer)AVUtil.accessValue(data, CommonAttribute.DEALER_VCI);
/* 267 */         if (dealerVCI == null) {
/* 268 */           return null;
/*     */         }
/* 270 */         Integer deviceID = (Integer)AVUtil.accessValue(data, CommonAttribute.DEVICE_ID);
/* 271 */         String device = Integer.toHexString(deviceID.intValue());
/* 272 */         wcc = Integer.toHexString(dealerVCI.intValue());
/* 273 */         if (wcc.length() > 3) {
/* 274 */           wcc = wcc.substring(wcc.length() - 3);
/*     */         } else {
/* 276 */           while (wcc.length() < 3) {
/* 277 */             wcc = "0" + wcc;
/*     */           }
/*     */         } 
/* 280 */         wcc = device + wcc;
/*     */       } 
/* 282 */       return wcc.toUpperCase(Locale.ENGLISH);
/* 283 */     } catch (Exception e) {
/* 284 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void logCVNMismatchEvent(List<SPSEventLog.Attribute> attributes, SPSModule module, String partID) {
/* 290 */     List<SPSCVN> allCVNs = module.getAllCVNs();
/* 291 */     if (allCVNs != null)
/* 292 */       for (int i = 0; i < allCVNs.size(); i++) {
/* 293 */         SPSCVN record = allCVNs.get(i);
/* 294 */         String assemblyID = "";
/* 295 */         if (partID.equals("eil")) {
/* 296 */           assemblyID = ".#" + i;
/* 297 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".id", record.getPart()));
/*     */         } 
/* 299 */         String cvn_db = null;
/* 300 */         if ("n/a".equals(record.getCVNDB())) {
/* 301 */           cvn_db = "n/a";
/*     */         } else {
/* 303 */           cvn_db = record.getCVNDB();
/*     */         } 
/* 305 */         if ("n/a".equals(record.getCVNECU())) {
/* 306 */           if (cvn_db.equals("n/a")) {
/* 307 */             attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "DB_CVN_NOT_AVAILABLE"));
/*     */           }
/* 309 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".cvn-ecu", "n/a"));
/*     */         } else {
/* 311 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".cvn-ecu", Long.toHexString(Long.parseLong(record.getCVNECU()))));
/*     */           
/* 313 */           if (cvn_db.equals("n/a")) {
/* 314 */             attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "DB_CVN_NOT_AVAILABLE"));
/* 315 */           } else if (Long.valueOf(cvn_db, 16).longValue() != Long.parseLong(record.getCVNECU())) {
/* 316 */             attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "CVN_CHECK_FAIL"));
/* 317 */           } else if (Long.valueOf(cvn_db, 16).longValue() == Long.parseLong(record.getCVNECU())) {
/* 318 */             attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "CVN_CHECK_SUCCESS"));
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSEvents.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */