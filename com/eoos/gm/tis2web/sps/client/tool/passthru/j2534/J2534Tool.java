/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.SupportedProtocols;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolCommunicationException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Device;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Error;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Version;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl.J2534DeviceImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl.J2534VersionImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class J2534Tool
/*     */   extends PtTool
/*     */   implements SupportedProtocols
/*     */ {
/*  24 */   private static Logger log = Logger.getLogger(J2534Tool.class);
/*     */   private boolean isConnected;
/*     */   private J2534ToolParams toolParams;
/*     */   
/*     */   public J2534Tool(Object _params) {
/*  29 */     this.type = "PT_J2534";
/*  30 */     this.toolParams = (J2534ToolParams)_params;
/*  31 */     this.id = "J2534 " + this.toolParams.getName();
/*     */   }
/*     */   
/*     */   public List getSupportedProtocols() {
/*  35 */     List<String> result = new ArrayList();
/*  36 */     String protocols = this.toolParams.getProtocols();
/*  37 */     if (protocols != null) {
/*  38 */       StringTokenizer tok = new StringTokenizer(this.toolParams.getProtocols(), ",");
/*  39 */       while (tok.hasMoreTokens()) {
/*  40 */         String protocol = tok.nextToken();
/*  41 */         result.add(protocol);
/*     */       } 
/*     */     } 
/*  44 */     return result;
/*     */   }
/*     */   
/*     */   public boolean testConnection(Object params) {
/*  48 */     boolean result = false;
/*     */     
/*  50 */     if (ClientAppContextProvider.getClientAppContext().testMode() || ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("windows.j2534.version").compareTo("02.02") == 0) {
/*  51 */       result = true;
/*     */     } else {
/*  53 */       J2534Device device = J2534DeviceImpl.getInstance();
/*  54 */       if (device.loadLibrary(this.toolParams.getJ2534Version(), this.toolParams.getDllPath()) == true) {
/*  55 */         J2534Error err = device.passThruOpen();
/*  56 */         if (err.getErrorCode() == 0) {
/*  57 */           J2534VersionImpl j2534VersionImpl = new J2534VersionImpl();
/*  58 */           err = device.passThruReadVersion((J2534Version)j2534VersionImpl);
/*  59 */           if (err.getErrorCode() == 0) {
/*  60 */             log.debug("J2534 tool version: " + j2534VersionImpl);
/*  61 */             result = true;
/*     */           } else {
/*  63 */             log.error("Cannot read J2534 Tool Version: " + err.getErrorDescription());
/*     */           } 
/*     */         } else {
/*  66 */           log.error("Cannot open J2534 Tool: " + err.getErrorDescription());
/*     */         } 
/*     */         
/*  69 */         err = device.passThruClose();
/*  70 */         if (err.getErrorCode() != 0) {
/*  71 */           log.error("Cannot close J2534 Tool: " + err.getErrorDescription());
/*  72 */           result = false;
/*     */         } 
/*     */       } else {
/*  75 */         log.error("Cannot load J2534 library for tool: " + this.id);
/*     */       } 
/*     */       
/*  78 */       device.freeLibrary();
/*     */     } 
/*  80 */     log.info("Connection test " + getId() + ": " + result);
/*  81 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/*  89 */     super.init();
/*  90 */     this.isConnected = false;
/*  91 */     String j2534Version = ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("windows.j2534.version");
/*  92 */     if (j2534Version == null) {
/*  93 */       j2534Version = "02.02";
/*     */     }
/*  95 */     String initString = "%" + j2534Version + "%" + "J2534," + this.toolParams.getName();
/*  96 */     log.debug("SPSVCS J2534 init string: " + initString);
/*  97 */     this.spsTool.initialize(initString);
/*     */   }
/*     */   
/*     */   public Object getVIN(DeviceCommunicationCallback dcb, Object requestData) throws Exception {
/* 101 */     Object result = null;
/* 102 */     while (!this.isConnected) {
/* 103 */       this.isConnected = testConnection((Object)null);
/*     */       
/* 105 */       if (!this.isConnected) {
/* 106 */         boolean retry = dcb.displayQuestionDialog("sps.tool.notconnected");
/* 107 */         if (retry) {
/*     */           continue;
/*     */         }
/* 110 */         throw new ToolCommunicationException();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     result = super.getVIN(dcb, requestData);
/* 117 */     this.isConnected = false;
/* 118 */     return result;
/*     */   }
/*     */   
/*     */   public String getDriverVersion() {
/* 122 */     return this.toolParams.getDriverVersion();
/*     */   }
/*     */   
/*     */   public String getDriverId() {
/* 126 */     return this.toolParams.getName();
/*     */   }
/*     */   
/*     */   public String getConfigApplication() {
/* 130 */     return this.toolParams.getConfigApplication();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\J2534Tool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */