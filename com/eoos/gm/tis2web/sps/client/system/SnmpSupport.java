/*     */ package com.eoos.gm.tis2web.sps.client.system;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.log4j.Logger;
/*     */ import snmp.SNMPBitString;
/*     */ import snmp.SNMPObject;
/*     */ import snmp.SNMPObjectIdentifier;
/*     */ import snmp.SNMPSequence;
/*     */ import snmp.SNMPTimeTicks;
/*     */ import snmp.SNMPTrapSenderInterface;
/*     */ import snmp.SNMPVarBindList;
/*     */ import snmp.SNMPVariablePair;
/*     */ import snmp.SNMPv2TrapPDU;
/*     */ 
/*     */ public class SnmpSupport
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(SnmpSupport.class);
/*     */   
/*  24 */   private static final long TIMESTAMP_OFFSET = TimeStampOffset();
/*     */   
/*     */   private static final String COMMUNITY = "public";
/*  27 */   private static final long startTime = System.currentTimeMillis();
/*     */   
/*     */   private static final String TIS2WEB_EVENT = "1.3.6.1.4.1.33023.2.2.1";
/*     */   
/*     */   private static final String TIS2WEB_START_EVENT = "1.3.6.1.4.1.33023.2.2.2";
/*     */   private static final String TIS2WEB_SHUTDOWN_EVENT = "1.3.6.1.4.1.33023.2.2.3";
/*     */   private static final String SPS_APPLICATION = "SPS";
/*     */   private static final String TIS2WEB_TIMESTAMP = "1.3.6.1.4.1.33023.2.1.1.0";
/*     */   private static final String TIS2WEB_INSTALLATION = "1.3.6.1.4.1.33023.2.1.5.0";
/*     */   private static final String TIS2WEB_APPLICATION = "1.3.6.1.4.1.33023.2.1.6.0";
/*     */   private static final String TIS2WEB_ADAPTER = "1.3.6.1.4.1.33023.2.1.3.0";
/*     */   private static final String TIS2WEB_ADAPTER_GME = "GME";
/*     */   private static final String TIS2WEB_STATUS = "1.3.6.1.4.1.33023.2.1.4.0";
/*     */   private static final String TIS2WEB_STATUS_SUCCESS = "SUCCESS";
/*     */   private static final String TIS2WEB_STATUS_FAILURE = "FAILURE";
/*     */   private static final String TIS2WEB_RESPONSE_TIME = "1.3.6.1.4.1.33023.2.1.2.0";
/*     */   private static SnmpSupport instance;
/*     */   private static SNMPTrapSenderInterface trapSenderInterface;
/*     */   private static InetAddress agent;
/*     */   private static String session;
/*     */   
/*     */   private static long TimeStampOffset() {
/*  49 */     Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
/*  50 */     if (cal.get(2) <= 5) {
/*  51 */       cal.set(cal.get(1), 0, 1, 0, 0);
/*  52 */       return cal.getTimeInMillis();
/*     */     } 
/*  54 */     cal.set(cal.get(1), 6, 1, 0, 0);
/*  55 */     return cal.getTimeInMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized SnmpSupport getSnmpService() {
/*  60 */     if (instance == null) {
/*     */       try {
/*  62 */         instance = new SnmpSupport();
/*  63 */       } catch (Exception e) {
/*  64 */         log.error("failed to initialize SNMP service", e);
/*     */       } 
/*     */     }
/*  67 */     return instance;
/*     */   }
/*     */   
/*     */   private SnmpSupport() throws Exception {
/*  71 */     ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/*  72 */     session = context.getSessionID();
/*  73 */     agent = InetAddress.getByName(context.getClientSettings().getProperty("snmp.agent"));
/*  74 */     trapSenderInterface = new SNMPTrapSenderInterface(Integer.parseInt(context.getClientSettings().getProperty("snmp.port")));
/*     */   }
/*     */   
/*     */   private SNMPVarBindList initNotification() throws Exception {
/*  78 */     SNMPTimeTicks sysTime = new SNMPTimeTicks((System.currentTimeMillis() - TIMESTAMP_OFFSET) / 10L);
/*     */     
/*  80 */     SNMPVarBindList varBindList = new SNMPVarBindList();
/*     */     
/*  82 */     SNMPObjectIdentifier tis2webTimestampOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.1.0");
/*  83 */     SNMPVariablePair tis2webTimestamp = new SNMPVariablePair(tis2webTimestampOID, (SNMPObject)sysTime);
/*  84 */     varBindList.addSNMPObject((SNMPObject)tis2webTimestamp);
/*     */     
/*  86 */     SNMPObjectIdentifier tis2webInstallationOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.5.0");
/*  87 */     SNMPVariablePair tis2webInstallation = new SNMPVariablePair(tis2webInstallationOID, (SNMPObject)new SNMPBitString("SPS-" + session));
/*  88 */     varBindList.addSNMPObject((SNMPObject)tis2webInstallation);
/*     */     
/*  90 */     SNMPObjectIdentifier tis2webApplicationOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.6.0");
/*  91 */     SNMPVariablePair tis2webApplication = new SNMPVariablePair(tis2webApplicationOID, (SNMPObject)new SNMPBitString("SPS"));
/*  92 */     varBindList.addSNMPObject((SNMPObject)tis2webApplication);
/*     */     
/*  94 */     SNMPObjectIdentifier tis2webAdapterOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.3.0");
/*  95 */     SNMPVariablePair tis2webAdapter = new SNMPVariablePair(tis2webAdapterOID, (SNMPObject)new SNMPBitString("GME"));
/*  96 */     varBindList.addSNMPObject((SNMPObject)tis2webAdapter);
/*     */     
/*  98 */     return varBindList;
/*     */   }
/*     */   
/*     */   public synchronized void sendStartTrap() throws Exception {
/* 102 */     SNMPObjectIdentifier snmpTrapOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.2.2");
/* 103 */     SNMPVarBindList varBindList = initNotification();
/* 104 */     sendV2Trap(snmpTrapOID, varBindList);
/*     */   }
/*     */   
/*     */   public synchronized void sendShutdownTrap() throws Exception {
/* 108 */     SNMPObjectIdentifier snmpTrapOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.2.3");
/* 109 */     SNMPVarBindList varBindList = initNotification();
/* 110 */     sendV2Trap(snmpTrapOID, varBindList);
/*     */   }
/*     */   
/*     */   public synchronized void sendEventTrap(boolean success, int responseTime) throws Exception {
/* 114 */     SNMPObjectIdentifier snmpTrapOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.2.1");
/*     */     
/* 116 */     SNMPVarBindList varBindList = initNotification();
/*     */     
/* 118 */     SNMPObjectIdentifier tis2webStatusOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.4.0");
/* 119 */     if (success) {
/* 120 */       SNMPVariablePair tis2webStatus = new SNMPVariablePair(tis2webStatusOID, (SNMPObject)new SNMPBitString("SUCCESS"));
/* 121 */       varBindList.addSNMPObject((SNMPObject)tis2webStatus);
/*     */     } else {
/* 123 */       SNMPVariablePair tis2webStatus = new SNMPVariablePair(tis2webStatusOID, (SNMPObject)new SNMPBitString("FAILURE"));
/* 124 */       varBindList.addSNMPObject((SNMPObject)tis2webStatus);
/*     */     } 
/*     */     
/* 127 */     SNMPObjectIdentifier tis2webResponseTimeOID = new SNMPObjectIdentifier("1.3.6.1.4.1.33023.2.1.2.0");
/* 128 */     SNMPTimeTicks t2wResponseTime = new SNMPTimeTicks(responseTime);
/* 129 */     SNMPVariablePair tis2webResponseTime = new SNMPVariablePair(tis2webResponseTimeOID, (SNMPObject)t2wResponseTime);
/* 130 */     varBindList.addSNMPObject((SNMPObject)tis2webResponseTime);
/*     */     
/* 132 */     sendV2Trap(snmpTrapOID, varBindList);
/*     */   }
/*     */   
/*     */   private synchronized void sendV2Trap(final SNMPObjectIdentifier snmpTrapOID, final SNMPVarBindList varBindList) throws Exception {
/* 136 */     (new Thread(new Runnable() {
/*     */           public void run() {
/* 138 */             SNMPTimeTicks sysUptime = new SNMPTimeTicks((SnmpSupport.startTime - SnmpSupport.TIMESTAMP_OFFSET) / 10L);
/*     */             try {
/* 140 */               SNMPv2TrapPDU pdu = new SNMPv2TrapPDU(sysUptime, snmpTrapOID, (SNMPSequence)varBindList);
/* 141 */               SnmpSupport.trapSenderInterface.sendTrap(SnmpSupport.agent, "public", pdu);
/* 142 */             } catch (Exception x) {}
/*     */           }
/*     */         })).start();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\system\SnmpSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */