/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSEvents;
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
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSEvents extends SPSEvents {
/*  27 */   private static final Logger log = Logger.getLogger(SPSEvents.class);
/*     */   
/*     */   static void logReprogramEvent(AttributeValueMap data, SPSSession session, SPSSchemaAdapterGlobal adapter) throws RequestException {
/*     */     try {
/*  31 */       Attachment[] attachments = getVIT1Attachments(data);
/*     */       
/*  33 */       session.markFinished();
/*     */       
/*  35 */       String eventName = "reprogram";
/*  36 */       List<SPSEventLog.Attribute> attributes = new LinkedList();
/*  37 */       Collection<SPSEventLog.Flag> flags = new LinkedList();
/*  38 */       boolean success = CommonValue.OK.equals(data.getValue(CommonAttribute.REPROGRAM));
/*  39 */       boolean replace = CommonValue.REPLACE_AND_REPROGRAM.equals(data.getValue(CommonAttribute.MODE));
/*     */       
/*  41 */       synchronized (DFORMAT) {
/*  42 */         attributes.add(new SPSEventLog.Attribute("date", DFORMAT.format(new Date())));
/*     */       } 
/*  44 */       attributes.add(new SPSEventLog.Attribute("replace", String.valueOf(replace)));
/*  45 */       attributes.add(new SPSEventLog.Attribute("status", success ? "success" : "failed"));
/*  46 */       attributes.add(new SPSEventLog.Attribute("sessionID", (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID)));
/*  47 */       attributes.add(new SPSEventLog.Attribute("bac_code", (String)AVUtil.accessValue(data, CommonAttribute.BACCODE)));
/*  48 */       attributes.add(new SPSEventLog.Attribute("vin", String.valueOf(session.getVehicle().getVIN())));
/*  49 */       attributes.add(new SPSEventLog.Attribute("deviceID", String.valueOf(session.getController().getDeviceID())));
/*  50 */       attributes.add(new SPSEventLog.Attribute("device", (String)AVUtil.accessValue(data, CommonAttribute.DEVICE)));
/*     */       
/*     */       try {
/*  53 */         String toolID = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_NAME);
/*  54 */         if (toolID != null) {
/*  55 */           attributes.add(new SPSEventLog.Attribute("j2534name", toolID));
/*     */         }
/*  57 */       } catch (Exception e) {
/*  58 */         log.error("unable to add 'j2534name' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*     */       try {
/*  62 */         String version = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_VERSION);
/*  63 */         if (version != null) {
/*  64 */           attributes.add(new SPSEventLog.Attribute("j2534version", version));
/*     */         }
/*  66 */       } catch (Exception e) {
/*  67 */         log.error("unable to add 'j2534version' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*  70 */       if (session.getController() instanceof SPSControllerVCI) {
/*  71 */         int vci = ((SPSControllerVCI)session.getController()).getVCI();
/*  72 */         attributes.add(new SPSEventLog.Attribute("vci", Integer.toString(vci)));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       if (session.getController() instanceof SPSProgrammingSequence) {
/*  79 */         SPSProgrammingSequence SPSps = (SPSProgrammingSequence)session.getController();
/*  80 */         List Clist = SPSps.getSequence();
/*  81 */         for (int idx = 0; idx < Clist.size(); idx++) {
/*  82 */           SPSControllerVCI tcontroller = SPSps.getController(idx);
/*  83 */           int vci = tcontroller.getVCI();
/*  84 */           attributes.add(new SPSEventLog.Attribute("Programming Sequence: " + tcontroller.getDescription() + ": vci", Integer.toString(vci)));
/*  85 */           List<SPSProgrammingData> pdataList = SPSps.getProgrammingDataList();
/*  86 */           SPSProgrammingData pdata = pdataList.get(idx);
/*  87 */           List<SPSModule> modules = pdata.getModules();
/*  88 */           if (modules != null) {
/*  89 */             for (int i = 0; i < modules.size(); i++) {
/*  90 */               SPSModule module = modules.get(i);
/*  91 */               String selected = (module.getSelectedPart() == null) ? null : module.getSelectedPart().getPartNumber();
/*     */               
/*  93 */               boolean isEIL = (selected != null) ? SPSPart.isEndItemPart(selected, adapter) : false;
/*  94 */               if (selected == null && module.getCurrentCalibration() != null) {
/*  95 */                 isEIL = SPSPart.isEndItemPart(module.getCurrentCalibration(), adapter);
/*     */               } else {
/*  97 */                 isEIL = (modules.size() == 1);
/*     */               } 
/*  99 */               String partID = isEIL ? "eil" : ("#" + i);
/* 100 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current.id", module.getCurrentCalibration()));
/* 101 */               logCVNMismatchEvent(attributes, module, partID);
/* 102 */               if (module.isMismatchCVN()) {
/* 103 */                 logCVNMismatchEvent(attributes, module, partID);
/*     */               }
/* 105 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".selected.id", selected));
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 111 */       int onstar = 0;
/* 112 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 113 */       if (vit1 != null) {
/* 114 */         VIT vit = vit1.getVIT();
/* 115 */         if (vit != null) {
/* 116 */           String stid = vit.getAttrValue("StID");
/* 117 */           if (stid != null) {
/* 118 */             attributes.add(new SPSEventLog.Attribute("stid", stid));
/* 119 */             onstar++;
/*     */           } 
/*     */           
/* 122 */           String esn = vit.getAttrValue("ESN");
/* 123 */           if (esn != null) {
/* 124 */             attributes.add(new SPSEventLog.Attribute("esn", esn));
/* 125 */             onstar++;
/*     */           } 
/* 127 */           String meid = vit.getAttrValue("MEID");
/* 128 */           if (meid != null) {
/* 129 */             attributes.add(new SPSEventLog.Attribute("esn", meid));
/* 130 */             esn = meid;
/* 131 */             onstar++;
/*     */           } 
/* 133 */           String min = vit.getAttrValue("MIN");
/* 134 */           if (min != null) {
/* 135 */             attributes.add(new SPSEventLog.Attribute("min", min));
/* 136 */             onstar++;
/*     */           } 
/* 138 */           String mdn = vit.getAttrValue("MDN");
/* 139 */           if (mdn != null) {
/* 140 */             attributes.add(new SPSEventLog.Attribute("mdn", mdn));
/* 141 */             onstar++;
/*     */           } 
/* 143 */           if (onstar > 0 && onstar != 4) {
/* 144 */             if (stid == null) {
/* 145 */               attributes.add(new SPSEventLog.Attribute("stid", "0"));
/*     */             }
/* 147 */             if (esn == null) {
/* 148 */               attributes.add(new SPSEventLog.Attribute("esn", "0"));
/*     */             }
/* 150 */             if (min == null) {
/* 151 */               attributes.add(new SPSEventLog.Attribute("min", "0"));
/*     */             }
/* 153 */             if (mdn == null) {
/* 154 */               attributes.add(new SPSEventLog.Attribute("mdn", "0"));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 160 */       if (onstar > 0) {
/* 161 */         flags.add(SPSEventLog.FLAG_ONSTAR);
/*     */       }
/*     */       
/* 164 */       if (!success) {
/*     */         try {
/* 166 */           ProgrammingStatus status = (ProgrammingStatus)((ValueAdapter)data.getValue(CommonAttribute.REPROGRAM)).getAdaptee();
/* 167 */           attributes.add(new SPSEventLog.Attribute("error_details.step-number", String.valueOf(status.getStepNumber())));
/* 168 */           attributes.add(new SPSEventLog.Attribute("error_details.error-code", String.valueOf(status.getErrorCode())));
/* 169 */           attributes.add(new SPSEventLog.Attribute("error_details.message", status.getMessage()));
/*     */         }
/* 171 */         catch (Exception e) {
/* 172 */           log.error("... unable to determine error details - exception: " + e, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 177 */       if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null)
/*     */       {
/* 179 */         attributes.add(new SPSEventLog.Attribute("warning.reason", "same calibration"));
/*     */       }
/*     */       
/* 182 */       if (session.getController() instanceof SPSControllerVCI) {
/*     */         try {
/* 184 */           SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(adapter);
/* 185 */           List<SPSModule> modules = pdata.getModules();
/* 186 */           if (modules != null) {
/* 187 */             for (int i = 0; i < modules.size(); i++) {
/* 188 */               SPSModule module = modules.get(i);
/* 189 */               String selected = (module.getSelectedPart() == null) ? null : module.getSelectedPart().getPartNumber();
/*     */               
/* 191 */               boolean isEIL = (selected != null) ? SPSPart.isEndItemPart(selected, adapter) : false;
/* 192 */               if (selected == null && module.getCurrentCalibration() != null) {
/* 193 */                 isEIL = SPSPart.isEndItemPart(module.getCurrentCalibration(), adapter);
/*     */               } else {
/* 195 */                 isEIL = (modules.size() == 1);
/*     */               } 
/*     */               
/* 198 */               String partID = isEIL ? "eil" : ("#" + i);
/* 199 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current.id", module.getCurrentCalibration()));
/* 200 */               logCVNMismatchEvent(attributes, module, partID);
/* 201 */               if (module.isMismatchCVN()) {
/* 202 */                 logCVNMismatchEvent(attributes, module, partID);
/*     */               }
/* 204 */               attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".selected.id", selected));
/*     */             }
/*     */           
/*     */           }
/* 208 */         } catch (Exception x) {
/* 209 */           log.warn("... unable to determine calibration - exception:" + x, x);
/*     */         } 
/*     */       }
/*     */       
/* 213 */       EntryImpl logEntry = EntryImpl.create(SPSEventLog.ADAPTER_NAO, eventName, attributes);
/* 214 */       SPSEventLogFacade.getInstance().add((SPSEventLog.Entry)logEntry, flags, attachments);
/*     */     }
/* 216 */     catch (RequestException e) {
/* 217 */       throw e;
/* 218 */     } catch (Exception e) {
/* 219 */       log.error("... unable to add entry - exception :" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void logCVNMismatchEvent(List<SPSEventLog.Attribute> attributes, SPSModule module, String partID) {
/* 226 */     List<SPSCVN> allCVNs = module.getAllCVNs();
/* 227 */     if (allCVNs != null)
/* 228 */       for (int i = 0; i < allCVNs.size(); i++) {
/* 229 */         SPSCVN record = allCVNs.get(i);
/* 230 */         String assemblyID = "";
/* 231 */         if (partID.equals("eil")) {
/* 232 */           assemblyID = ".#" + i;
/* 233 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".id", record.getPart()));
/*     */         } 
/* 235 */         String cvn_db = null;
/* 236 */         if ("n/a".equals(record.getCVNDB())) {
/* 237 */           cvn_db = "n/a";
/*     */         } else {
/* 239 */           cvn_db = record.getCVNDB();
/*     */         } 
/* 241 */         attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".cvn-db", Long.toHexString(Long.parseLong(cvn_db, 16))));
/* 242 */         attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".cvn-ecu", Long.toHexString(Long.parseLong(record.getCVNECU()))));
/*     */         
/* 244 */         if (cvn_db.equals("n/a")) {
/* 245 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "DB_CVN_NOT_AVAILABLE"));
/* 246 */         } else if (Long.valueOf(cvn_db, 16).longValue() != Long.parseLong(record.getCVNECU())) {
/* 247 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "CVN_CHECK_FAIL"));
/* 248 */         } else if (Long.valueOf(cvn_db, 16).longValue() == Long.parseLong(record.getCVNECU())) {
/* 249 */           attributes.add(new SPSEventLog.Attribute("calibration." + partID + ".current" + assemblyID + ".status", "CVN_CHECK_SUCCESS"));
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSEvents.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */