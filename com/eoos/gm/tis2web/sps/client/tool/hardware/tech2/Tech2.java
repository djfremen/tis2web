/*     */ package com.eoos.gm.tis2web.sps.client.tool.hardware.tech2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*     */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.SelectOptionImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolOptionsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tech2
/*     */ {
/*  19 */   private static Logger log = Logger.getLogger(Tech2.class);
/*     */   public static final String TECH2_OPTIONS_KEY = "Tech2";
/*  21 */   private static Tech2 instance = null;
/*  22 */   private List<String> localPorts = null;
/*  23 */   private String tech2WinComPort = null;
/*  24 */   private static final String[] baudrates = new String[] { "115200", "57600", "38400", "19200", "9600" };
/*  25 */   private String detectedPort = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Tech2 getInstance() {
/*  32 */     if (instance == null) {
/*  33 */       instance = new Tech2();
/*     */     }
/*  35 */     return instance;
/*     */   }
/*     */   
/*     */   public String getComPort() {
/*  39 */     String result = null;
/*  40 */     if (this.detectedPort != null) {
/*  41 */       result = this.detectedPort;
/*  42 */       log.info("Detected COM port: " + result);
/*     */     } else {
/*     */       try {
/*  45 */         ToolOptionsImpl options = ToolOptionsImpl.getInstance("Tech2");
/*  46 */         ToolOption port = options.getOptionByPropertyKey(options.getToolOptionKey("Tech2", "Port"));
/*  47 */         OptionValue optionValue = port.getOptionValue(port.getDefaultValueIndex());
/*  48 */         result = optionValue.getKey();
/*  49 */         if (result == null || result.compareTo("AUTO") == 0) {
/*  50 */           result = "AUTO";
/*  51 */           log.debug("Determined COM Port: " + result);
/*     */         } 
/*  53 */       } catch (Exception e) {
/*  54 */         log.error("No COM ports found");
/*     */       } 
/*     */     } 
/*  57 */     return result;
/*     */   }
/*     */   
/*     */   public String getBaudRate() {
/*  61 */     String result = null;
/*  62 */     ToolOptionsImpl options = ToolOptionsImpl.getInstance("Tech2");
/*  63 */     ToolOption baudRate = options.getOptionByPropertyKey(options.getToolOptionKey("Tech2", "Baud Rate"));
/*  64 */     OptionValue optionValue = baudRate.getOptionValue(baudRate.getDefaultValueIndex());
/*  65 */     result = optionValue.getKey();
/*  66 */     if (result == null) {
/*  67 */       result = getFirstBaudRate();
/*     */     }
/*  69 */     return result;
/*     */   }
/*     */   
/*     */   public void setToolOptions() {
/*  73 */     ToolOptionsImpl options = ToolOptionsImpl.getInstance("Tech2");
/*  74 */     this.localPorts = determineCommPorts();
/*  75 */     this.tech2WinComPort = determineTech2WinComPort();
/*  76 */     SelectOptionImpl selectOptionImpl = new SelectOptionImpl("Tech2", "Port", (new ToolUtils()).getElementList("Tech2", this.localPorts));
/*  77 */     options.addOption((ToolOption)selectOptionImpl);
/*  78 */     selectOptionImpl = new SelectOptionImpl("Tech2", "Baud Rate", (new ToolUtils()).getElementList("Tech2", baudrates));
/*  79 */     options.addOption((ToolOption)selectOptionImpl);
/*     */   }
/*     */   
/*     */   public List<String> getComPorts() {
/*  83 */     return this.localPorts;
/*     */   }
/*     */   
/*     */   public String getTech2WinComPort() {
/*  87 */     return this.tech2WinComPort;
/*     */   }
/*     */   
/*     */   public int getNumberOfComPorts() {
/*  91 */     return this.localPorts.size();
/*     */   }
/*     */   
/*     */   public String getFirstComPort() {
/*  95 */     String result = null;
/*  96 */     if (this.localPorts.size() >= 2) {
/*  97 */       result = this.localPorts.get(1);
/*     */     }
/*  99 */     return result;
/*     */   }
/*     */   
/*     */   public String getFirstBaudRate() {
/* 103 */     return baudrates[0];
/*     */   }
/*     */   
/*     */   public void setDetectedPort(String port) {
/* 107 */     int pos = port.indexOf(",");
/* 108 */     if (pos != -1) {
/* 109 */       this.detectedPort = port.substring(0, pos);
/*     */     } else {
/* 111 */       this.detectedPort = port;
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<String> determineCommPorts() {
/* 116 */     List<String> result = new ArrayList<String>();
/* 117 */     IStarter starter = Starter.getInstance();
/* 118 */     result.addAll(starter.getAvailableCommPorts());
/* 119 */     if (!result.isEmpty()) {
/* 120 */       result.add(0, "AUTO");
/*     */     }
/* 122 */     return result;
/*     */   }
/*     */   
/*     */   private String determineTech2WinComPort() {
/* 126 */     return Starter.getInstance().getTech2WinComPort();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\hardware\tech2\Tech2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */