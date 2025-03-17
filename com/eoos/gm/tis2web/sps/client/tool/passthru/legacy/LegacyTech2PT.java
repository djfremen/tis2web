/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.legacy;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolCommunicationException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolInitException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolOptionsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.hardware.tech2.Tech2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.SPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegacyTech2PT
/*     */   extends PtTool
/*     */   implements ToolOptions, SettingsObserver
/*     */ {
/*  26 */   private static Logger logg = Logger.getLogger(LegacyTech2PT.class);
/*  27 */   private ToolOptionsImpl toolOptions = null;
/*     */   private boolean isConnected;
/*     */   
/*     */   public LegacyTech2PT() {
/*  31 */     this.type = "PT_LEGACY";
/*  32 */     this.id = "Tech2 Legacy Pass-Thru";
/*  33 */     setToolOptions();
/*     */   }
/*     */   
/*     */   public List getOptions() {
/*  37 */     return this.toolOptions.getOptions();
/*     */   }
/*     */   
/*     */   public ToolOption getOptionByPropertyKey(String key) {
/*  41 */     return this.toolOptions.getOptionByPropertyKey(key);
/*     */   }
/*     */   
/*     */   public void update(ObservableSubject savedSettings, Object o) {
/*  45 */     this.toolOptions.update(savedSettings, o);
/*     */   }
/*     */   
/*     */   public boolean testConnection(Object params) {
/*  49 */     boolean result = false;
/*  50 */     if (ClientAppContextProvider.getClientAppContext().testMode()) {
/*  51 */       result = true;
/*     */     } else {
/*  53 */       Tech2 tech2HW = Tech2.getInstance();
/*  54 */       String port = tech2HW.getComPort();
/*  55 */       String baudRate = tech2HW.getBaudRate();
/*  56 */       if (params != null && params instanceof Map) {
/*  57 */         if (((Map)params).get("Port") != null) {
/*  58 */           port = (String)((Map)params).get("Port");
/*     */         }
/*  60 */         if (((Map)params).get("Baud Rate") != null) {
/*  61 */           baudRate = (String)((Map)params).get("Baud Rate");
/*     */         }
/*     */       } 
/*  64 */       if (this.spsTool == null) {
/*  65 */         this.spsTool = SPSTool.getInstance("SPSVCS");
/*     */       }
/*  67 */       String commIFace = this.spsTool.initialize(port + "," + baudRate);
/*  68 */       if (commIFace != null) {
/*  69 */         result = true;
/*     */       }
/*  71 */       logg.info("Connection test on port " + port + ", baud rate " + baudRate + ": " + commIFace);
/*     */     } 
/*  73 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/*  81 */     super.init();
/*  82 */     this.isConnected = false;
/*  83 */     Tech2 tech2HW = Tech2.getInstance();
/*  84 */     String port = tech2HW.getComPort();
/*  85 */     String baudRate = tech2HW.getBaudRate();
/*  86 */     if (port == null && !ClientAppContextProvider.getClientAppContext().testMode()) {
/*  87 */       throw new ToolInitException();
/*     */     }
/*  89 */     String commIFace = this.spsTool.initialize(port + "," + baudRate);
/*  90 */     logg.debug("Communication interface tried: " + port + " got: " + commIFace);
/*     */     
/*  92 */     if (commIFace != null && commIFace.compareToIgnoreCase(port) != 0) {
/*  93 */       tech2HW.setDetectedPort(commIFace);
/*  94 */       commIFace = this.spsTool.initialize(commIFace + "," + baudRate);
/*     */     } 
/*  96 */     logg.info("Port settings: " + tech2HW.getComPort() + ", " + tech2HW.getBaudRate());
/*     */   }
/*     */   
/*     */   public Object getVIN(DeviceCommunicationCallback dcb, Object requestData) throws Exception {
/* 100 */     Object result = null;
/* 101 */     while (!this.isConnected) {
/* 102 */       this.isConnected = testConnection((Object)null);
/*     */       
/* 104 */       if (!this.isConnected) {
/* 105 */         boolean retry = dcb.displayQuestionDialog("sps.tool.notconnected");
/* 106 */         if (retry) {
/*     */           continue;
/*     */         }
/* 109 */         throw new ToolCommunicationException();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 115 */     result = super.getVIN(dcb, requestData);
/* 116 */     this.isConnected = false;
/* 117 */     return result;
/*     */   }
/*     */   
/*     */   private void setToolOptions() {
/* 121 */     this.toolOptions = ToolOptionsImpl.getInstance("Tech2");
/* 122 */     if (this.toolOptions.getOptions().isEmpty()) {
/* 123 */       Tech2.getInstance().setToolOptions();
/*     */     }
/* 125 */     String toolPropertyKeyPrefix = (new ToolUtils()).trim("Tech2");
/* 126 */     this.toolOptions.adjustToolOptions(ClientAppContextProvider.getClientAppContext().getClientSettings().getProperties(toolPropertyKeyPrefix));
/* 127 */     ClientAppContextProvider.getClientAppContext().registerSettingsObserver((SettingsObserver)this.toolOptions);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\legacy\LegacyTech2PT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */