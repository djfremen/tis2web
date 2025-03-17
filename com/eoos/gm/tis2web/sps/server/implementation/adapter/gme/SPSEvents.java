/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
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
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSEvents extends SPSEvents {
/*  27 */   private static final Logger log = Logger.getLogger(SPSEvents.class);
/*     */   
/*     */   static void logReprogramEvent(AttributeValueMap data, SPSSession session, SPSSchemaAdapterGME adapter) throws RequestException {
/*     */     try {
/*  31 */       Attachment[] attachments = getVIT1Attachments(data);
/*     */       
/*  33 */       session.markFinished();
/*  34 */       List<SPSEventLog.Attribute> attributes = new LinkedList();
/*  35 */       boolean success = CommonValue.OK.equals(data.getValue(CommonAttribute.REPROGRAM));
/*  36 */       boolean replace = CommonValue.REPLACE_AND_REPROGRAM.equals(data.getValue(CommonAttribute.MODE));
/*     */       
/*  38 */       synchronized (DFORMAT) {
/*  39 */         attributes.add(new SPSEventLog.Attribute("date", DFORMAT.format(new Date())));
/*     */       } 
/*  41 */       attributes.add(new SPSEventLog.Attribute("replace", String.valueOf(replace)));
/*  42 */       attributes.add(new SPSEventLog.Attribute("status", success ? "success" : "failed"));
/*  43 */       attributes.add(new SPSEventLog.Attribute("sessionID", (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID)));
/*  44 */       attributes.add(new SPSEventLog.Attribute("vin", String.valueOf(session.getVehicle().getVIN())));
/*  45 */       if (session.getController() instanceof SPSControllerSequenceGME) {
/*  46 */         attributes.add(new SPSEventLog.Attribute("controller", String.valueOf(((SPSControllerSequenceGME)session.getController()).getSequenceID())));
/*     */       } else {
/*  48 */         attributes.add(new SPSEventLog.Attribute("sequence", String.valueOf(session.getController().getID())));
/*     */       } 
/*  50 */       attributes.add(new SPSEventLog.Attribute("device", (String)AVUtil.accessValue(data, CommonAttribute.DEVICE)));
/*     */       
/*     */       try {
/*  53 */         String label = session.getController().getLabel();
/*  54 */         if (label != null) {
/*  55 */           attributes.add(new SPSEventLog.Attribute("controller", label));
/*     */         } else {
/*  57 */           log.debug("unable to add 'controller' attribute to event-log entry, label unavailable");
/*     */         } 
/*  59 */       } catch (Exception e) {
/*  60 */         log.error("unable to add 'controller' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*     */       try {
/*  64 */         String toolID = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_NAME);
/*  65 */         if (toolID != null) {
/*  66 */           attributes.add(new SPSEventLog.Attribute("j2534name", toolID));
/*     */         }
/*  68 */       } catch (Exception e) {
/*  69 */         log.error("unable to add 'j2534name' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*     */       try {
/*  73 */         String version = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE_J2534_VERSION);
/*  74 */         if (version != null) {
/*  75 */           attributes.add(new SPSEventLog.Attribute("j2534version", version));
/*     */         }
/*  77 */       } catch (Exception e) {
/*  78 */         log.error("unable to add 'j2534version' attribute to event-log entry, ignoring - exception:" + e, e);
/*     */       } 
/*     */       
/*  81 */       if (session.getController() instanceof SPSControllerSequenceGME) {
/*  82 */         List state = (List)AVUtil.accessValue(data, CommonAttribute.REPROGRAM_STATUS);
/*  83 */         logSequenceReprogramEvent(attributes, (SPSControllerSequenceGME)session.getController(), state);
/*     */       } else {
/*     */         try {
/*  86 */           SPSSoftware current = ((SPSControllerGME)session.getController()).getCurrentSoftware();
/*  87 */           SPSSoftware selected = ((SPSControllerGME)session.getController()).getSoftware();
/*  88 */           if (current != null) {
/*  89 */             attributes.add(new SPSEventLog.Attribute("current-ecu-software", output(current)));
/*     */           } else {
/*  91 */             attributes.add(new SPSEventLog.Attribute("current-ecu-software", "unknown"));
/*     */           } 
/*  93 */           attributes.add(new SPSEventLog.Attribute("selected-ecu-software", output(selected)));
/*  94 */         } catch (Exception x) {
/*  95 */           log.error("...unable to determine calibration - exception: " + x, x);
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       if (data.getValue(CommonAttribute.LABOR_TIME_TRACKING) != null) {
/* 100 */         SPSLaborTime.log(attributes, data.getValue(CommonAttribute.LABOR_TIME_TRACKING));
/* 101 */         if (data.getValue(CommonAttribute.WARRANTY_CLAIM_CODE) != null) {
/* 102 */           attributes.add(new SPSEventLog.Attribute("claimcode", (String)AVUtil.accessValue(data, CommonAttribute.WARRANTY_CLAIM_CODE)));
/*     */         }
/*     */       } 
/*     */       
/* 106 */       Collection<SPSEventLog.Flag> flags = new LinkedList();
/*     */       
/* 108 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 109 */       if (handleVoltAttributes(adapter, vit1, attributes)) {
/* 110 */         flags.add(SPSEventLog.FLAG_VOLT);
/*     */       }
/*     */       
/* 113 */       EntryImpl logEntry = EntryImpl.create(SPSEventLog.ADAPTER_GME, "reprogram", attributes);
/* 114 */       SPSEventLogFacade.getInstance().add((SPSEventLog.Entry)logEntry, flags, attachments);
/*     */     }
/* 116 */     catch (RequestException e) {
/* 117 */       throw e;
/* 118 */     } catch (Exception e) {
/* 119 */       log.error("...unable to add entry to sps log - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static boolean handleVoltAttributes(SPSSchemaAdapterGME adapter, VIT1Data vit1, List attributes) {
/*     */     try {
/* 125 */       return adapter.getVoltAttributeHandler().extract(attributes, vit1.getVIT());
/* 126 */     } catch (Exception e) {
/*     */       
/* 128 */       return false;
/*     */     } 
/*     */   }
/*     */   protected static void logSequenceReprogramEvent(List<SPSEventLog.Attribute> attributes, SPSControllerSequenceGME controller, List<Pair> state) {
/* 132 */     List<SPSControllerGME> functions = controller.getSequenceFunctions();
/* 133 */     if (functions == null) {
/* 134 */       log.error("... no sequence functions");
/*     */       return;
/*     */     } 
/* 137 */     for (int i = 0; i < functions.size(); i++) {
/* 138 */       SPSControllerGME function = functions.get(i);
/* 139 */       String position = (functions.size() > 1) ? ("." + (i + 1)) : "";
/* 140 */       if (state != null) {
/* 141 */         Pair status = state.get(i);
/* 142 */         attributes.add(new SPSEventLog.Attribute("function" + position, status.getFirst().toString()));
/* 143 */         if (functions.size() > 1) {
/* 144 */           if (status.getSecond().equals(ProgrammingSequence.SUCCESS)) {
/* 145 */             attributes.add(new SPSEventLog.Attribute("status" + position, "success"));
/* 146 */           } else if (status.getSecond().equals(ProgrammingSequence.SKIP)) {
/* 147 */             attributes.add(new SPSEventLog.Attribute("status" + position, "skip"));
/*     */           } else {
/* 149 */             attributes.add(new SPSEventLog.Attribute("status" + position, "fail"));
/*     */           } 
/*     */         }
/*     */       } 
/*     */       try {
/* 154 */         SPSSoftware current = function.getCurrentSoftware();
/* 155 */         SPSSoftware selected = function.getSoftware();
/* 156 */         if (current != null) {
/* 157 */           attributes.add(new SPSEventLog.Attribute("current-ecu-software" + position, output(current)));
/*     */         } else {
/* 159 */           attributes.add(new SPSEventLog.Attribute("current-ecu-software" + position, "unknown"));
/*     */         } 
/* 161 */         attributes.add(new SPSEventLog.Attribute("selected-ecu-software" + position, output(selected)));
/* 162 */       } catch (Exception x) {
/* 163 */         log.error("...unable to determine calibration - exception: " + x, x);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String output(SPSSoftware software) {
/* 169 */     StringBuffer buffer = new StringBuffer();
/* 170 */     List<Pair> attributes = software.getAttributes();
/* 171 */     if (attributes != null) {
/* 172 */       for (int i = 0; i < attributes.size(); i++) {
/* 173 */         Pair ident = attributes.get(i);
/* 174 */         String label = (String)ident.getFirst();
/* 175 */         String value = (String)ident.getSecond();
/* 176 */         if (value == null) {
/* 177 */           value = "unknown";
/* 178 */         } else if (value.indexOf("#NULL#") >= 0) {
/* 179 */           value = "n/a";
/*     */         } 
/* 181 */         if (buffer.length() > 0) {
/* 182 */           buffer.append("; ");
/*     */         }
/* 184 */         buffer.append(label + "=" + value);
/*     */       } 
/*     */     }
/* 187 */     return (buffer.length() > 0) ? buffer.toString() : "unknown";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSEvents.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */