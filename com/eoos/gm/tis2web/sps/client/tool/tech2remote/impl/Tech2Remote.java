/*     */ package com.eoos.gm.tis2web.sps.client.tool.tech2remote.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ReprogramProgress;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.ReprogramProgressTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ProgrammingResult;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolCallback;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ReprogrammingException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolCommunicationException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolInitException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.VINReadException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ReProgrammer;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.RemoteTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolContextImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolOptionsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.VIT1LoggingData;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.hardware.tech2.Tech2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.SimpleVIT;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.SPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tech2Remote
/*     */   extends RemoteTool
/*     */   implements ToolCallback, ToolOptions, SettingsObserver, ReprogramProgressTool
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(Tech2Remote.class);
/*     */   private static final String TECH2DLL = "TECH2";
/*     */   private static final String REPROGRAM = "REPROG";
/*     */   private static final String REPLACE_AND_REPROGRAM = "REPLACE";
/*     */   private static final int WAIT_AFTER_INIT = 2;
/*  51 */   private ToolOptionsImpl toolOptions = null;
/*     */   private ISPSTool spsTool;
/*     */   private ToolContext toolContext;
/*  54 */   private VIT1 vit1 = null;
/*     */   private VIT1LoggingData loggingMap;
/*     */   
/*     */   public Tech2Remote() {
/*  58 */     this.type = "T2_REMOTE";
/*  59 */     this.id = "Tech2 Remote";
/*  60 */     setToolOptions();
/*     */   }
/*     */   
/*     */   public List<ToolOption> getOptions() {
/*  64 */     return new ArrayList<ToolOption>(this.toolOptions.getOptions());
/*     */   }
/*     */   
/*     */   public ToolOption getOptionByPropertyKey(String key) {
/*  68 */     return this.toolOptions.getOptionByPropertyKey(key);
/*     */   }
/*     */   
/*     */   public void update(ObservableSubject savedSettings, Object o) {
/*  72 */     log.debug("update: " + this.id);
/*  73 */     this.toolOptions.update(savedSettings, o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getVIN(DeviceCommunicationCallback dcb, Object requestData) throws Exception {
/*  78 */     log.info("Requesting VIN: (" + this.id + ", " + this.type + ")");
/*     */     
/*  80 */     Value programmingMode = ((AttributeValueMap)requestData).getValue(CommonAttribute.MODE);
/*  81 */     String progMode = "REPROG";
/*  82 */     if (programmingMode.equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/*  83 */       progMode = "REPLACE";
/*     */     }
/*  85 */     this.toolContext.setProgrammingMode(progMode);
/*     */     
/*  87 */     boolean isConnected = false;
/*  88 */     while (!isConnected) {
/*  89 */       isConnected = testConnection((Object)null);
/*  90 */       if (!isConnected) {
/*  91 */         boolean retry = dcb.displayQuestionDialog("sps.tool.notconnected");
/*  92 */         if (retry) {
/*     */           continue;
/*     */         }
/*  95 */         throw new ToolCommunicationException();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.loggingMap = new VIT1LoggingData();
/*     */     
/* 103 */     VIT1 vit1 = (VIT1)getECUData(dcb, requestData, (AttributeValueMap)null);
/* 104 */     if (vit1 == null) {
/* 105 */       throw new VINReadException();
/*     */     }
/* 107 */     if (log.isDebugEnabled()) {
/*     */       try {
/* 109 */         String vinStr = (String)vit1.getVIN();
/* 110 */         log.debug("VIN: " + vinStr);
/* 111 */       } catch (Exception e) {
/* 112 */         log.error("Cannot extract VIN from VIT1: " + e);
/*     */       } 
/*     */     }
/* 115 */     return vit1;
/*     */   }
/*     */   
/*     */   public Object getECUData(DeviceCommunicationCallback dcb, Object requestData, AttributeValueMap context) {
/* 119 */     log.info("Requesting ECU Data: (" + this.id + ", " + this.type + ")");
/* 120 */     if (this.vit1 == null) {
/* 121 */       Pair[] rawResult = null;
/* 122 */       Pair[] toolProperties = new Pair[2];
/* 123 */       toolProperties[0] = this.toolContext.getProgrammingMode();
/* 124 */       toolProperties[1] = this.toolContext.getErrorFlag();
/* 125 */       if (log.isDebugEnabled()) {
/* 126 */         for (int i = 0; i < toolProperties.length; i++) {
/* 127 */           log.debug("Tool property: " + toolProperties[i].getFirst() + ", " + toolProperties[i].getSecond());
/*     */         }
/*     */       }
/* 130 */       ToolUtils toolUtils = new ToolUtils();
/* 131 */       this.spsTool.setToolProperties(toolProperties);
/* 132 */       dcb.start();
/* 133 */       toolUtils.installNativeToolCallback(this.spsTool);
/*     */       try {
/* 135 */         rawResult = this.spsTool.getECUData();
/* 136 */       } catch (Exception e) {
/* 137 */         log.error("Cannot read ECU data: " + e);
/*     */       } finally {
/* 139 */         toolUtils.uninstallNativeToolCallback(this.spsTool, 1);
/*     */       } 
/* 141 */       dcb.stop();
/*     */       try {
/* 143 */         if (toolUtils.extendedDebugEnabled()) {
/* 144 */           toolUtils.writeDebugVIT("VIT1-Remote", rawResult);
/*     */         }
/* 146 */         this.vit1 = (new ToolUtils()).createVIT1(rawResult);
/* 147 */       } catch (Exception e) {
/* 148 */         log.error("Cannot build VIT1 from raw data: " + e);
/*     */       } 
/*     */       try {
/* 151 */         SimpleVIT simpleVIT = new SimpleVIT(rawResult);
/* 152 */         this.loggingMap.addSimpleVIT(simpleVIT.getEcuAddr(), simpleVIT);
/* 153 */       } catch (Exception e) {
/*     */         
/* 155 */         log.info("Cannot add VIT1 data to tool's internal VIT1DataLoggingMap: " + e);
/*     */       } 
/*     */     } 
/* 158 */     return this.vit1;
/*     */   }
/*     */   
/*     */   public VIT1 getVIT1(Integer deviceId) {
/* 162 */     return this.vit1;
/*     */   }
/*     */   
/*     */   public ISPSTool getSPSTool() {
/* 166 */     return this.spsTool;
/*     */   }
/*     */   
/*     */   public ToolContext getToolContext() {
/* 170 */     return this.toolContext;
/*     */   }
/*     */   
/*     */   public Object reprogram(MessageCallback mcb, Object programmingData, Object dataUnits, String securityCode, String securityCodeCtrl) throws Exception {
/* 174 */     log.info("Reprogramming: (" + this.id + ", " + this.type + ")");
/* 175 */     ReProgrammer prog = new ReProgrammer();
/* 176 */     ProgrammingResult result = null;
/*     */     try {
/* 178 */       result = prog.reprogram(this, mcb, programmingData, dataUnits, securityCode, securityCodeCtrl);
/* 179 */       log.info("Reprogramming: " + result.getStatus().booleanValue());
/* 180 */     } catch (Exception e) {
/* 181 */       log.info("Reprogramming failed: " + e.toString());
/* 182 */       throw new ReprogrammingException();
/*     */     } 
/* 184 */     return result;
/*     */   }
/*     */   
/*     */   public boolean testConnection(Object params) {
/* 188 */     boolean result = false;
/* 189 */     if (ClientAppContextProvider.getClientAppContext().testMode()) {
/* 190 */       result = true;
/*     */     } else {
/* 192 */       Tech2 tech2HW = Tech2.getInstance();
/* 193 */       String port = tech2HW.getComPort();
/* 194 */       String baudRate = tech2HW.getBaudRate();
/* 195 */       if (params != null && params instanceof Map) {
/* 196 */         if (((Map)params).get("Port") != null) {
/* 197 */           port = (String)((Map)params).get("Port");
/*     */         }
/* 199 */         if (((Map)params).get("Baud Rate") != null) {
/* 200 */           baudRate = (String)((Map)params).get("Baud Rate");
/*     */         }
/*     */       } 
/* 203 */       if (this.spsTool == null) {
/* 204 */         this.spsTool = SPSTool.getInstance("TECH2");
/*     */       }
/* 206 */       String commIFace = this.spsTool.initialize(port + "," + baudRate);
/* 207 */       if (commIFace != null) {
/* 208 */         result = true;
/*     */       }
/* 210 */       log.info("Connection test on port " + port + ", baud rate " + baudRate + ": " + commIFace);
/*     */     } 
/* 212 */     return result;
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/* 216 */     log.info("Initializing tool: (" + this.id + ", " + this.type + ")");
/* 217 */     this.vit1 = null;
/* 218 */     this.loggingMap = new VIT1LoggingData();
/* 219 */     this.toolContext = (ToolContext)new ToolContextImpl();
/* 220 */     Tech2 tech2HW = Tech2.getInstance();
/* 221 */     this.toolContext.setTech2WinComPort(tech2HW.getTech2WinComPort());
/* 222 */     String port = tech2HW.getComPort();
/* 223 */     String baudRate = tech2HW.getBaudRate();
/* 224 */     if (port == null && !ClientAppContextProvider.getClientAppContext().testMode()) {
/* 225 */       throw new ToolInitException();
/*     */     }
/* 227 */     if (this.spsTool == null) {
/* 228 */       this.spsTool = SPSTool.getInstance("TECH2");
/*     */     }
/*     */     
/* 231 */     Pair tech2WinComPortproperty = this.toolContext.getTech2WinComPortProperty();
/* 232 */     if (tech2WinComPortproperty != null) {
/* 233 */       this.spsTool.setToolProperties(new Pair[] { tech2WinComPortproperty });
/*     */     }
/*     */     
/* 236 */     String commIFace = this.spsTool.initialize(port + "," + baudRate);
/* 237 */     log.debug("Communication interface tried: " + port + " got: " + commIFace);
/*     */     
/* 239 */     if (commIFace != null && commIFace.compareToIgnoreCase(port) != 0) {
/* 240 */       tech2HW.setDetectedPort(commIFace);
/* 241 */       commIFace = this.spsTool.initialize(commIFace + "," + baudRate);
/*     */     } 
/* 243 */     log.info("Port settings: " + tech2HW.getComPort() + ", " + tech2HW.getBaudRate());
/*     */     
/*     */     try {
/* 246 */       log.debug("Waiting for Tech2 to reset: 2 seconds");
/* 247 */       Thread.sleep(2000L);
/* 248 */     } catch (InterruptedException e) {
/* 249 */       log.debug("Exception received: " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 254 */     log.debug("Reset called: " + this.id);
/* 255 */     this.vit1 = null;
/* 256 */     this.loggingMap = new VIT1LoggingData();
/* 257 */     if (this.spsTool != null) {
/* 258 */       log.debug("Giving up DLL.");
/* 259 */       SPSTool.discardInstance("TECH2");
/* 260 */       this.spsTool = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setToolOptions() {
/* 265 */     this.toolOptions = ToolOptionsImpl.getInstance("Tech2");
/* 266 */     if (this.toolOptions.getOptions().isEmpty()) {
/* 267 */       Tech2.getInstance().setToolOptions();
/*     */     }
/* 269 */     String toolPropertyKeyPrefix = (new ToolUtils()).trim("Tech2");
/* 270 */     this.toolOptions.adjustToolOptions(ClientAppContextProvider.getClientAppContext().getClientSettings().getProperties(toolPropertyKeyPrefix));
/* 271 */     ClientAppContextProvider.getClientAppContext().registerSettingsObserver((SettingsObserver)this.toolOptions);
/*     */   }
/*     */   
/*     */   public void setReprogramProgress(ReprogramProgress reprogramProgress) {
/* 275 */     if (this.spsTool != null) {
/* 276 */       this.spsTool.setReprogramProgress(reprogramProgress);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getVIT1History() {
/* 286 */     return this.loggingMap.getVIT1History();
/*     */   }
/*     */   
/*     */   public void resetVIT1History() {
/* 290 */     this.loggingMap.resetVIT1History();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\tech2remote\impl\Tech2Remote.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */