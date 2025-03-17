/*     */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.driver.SDDriver;
/*     */ import com.eoos.gm.tis2web.swdl.client.driver.TechDriver;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.ApplicationInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.starter.impl.Starter;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class IOManager
/*     */ {
/*  26 */   private Logger log = Logger.getLogger(IOManager.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean selectDeviceDriver(String device) {
/*     */     try {
/*  34 */       this.log.debug("call selectDeviceDriver(" + device + ")");
/*  35 */       TechDriver.getInstance().selectDriver(device);
/*  36 */     } catch (Exception e) {
/*  37 */       this.log.error("Exception when select the driver for: " + device + ". " + e, e);
/*  38 */       return false;
/*     */     } 
/*  40 */     return true;
/*     */   }
/*     */   
/*     */   public boolean initDevice(String initParam) {
/*  44 */     boolean retVal = false;
/*     */     try {
/*  46 */       Pair[] properties = null;
/*     */ 
/*     */ 
/*     */       
/*  50 */       String tech2WinComPort = Starter.getInstance().getTech2WinComPort();
/*  51 */       this.log.debug("Set Tech2Win COM port driver property: " + tech2WinComPort);
/*  52 */       if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/*  53 */         properties = new Pair[1];
/*  54 */         String str = "tech2win_com_port," + tech2WinComPort;
/*  55 */         properties[0] = (Pair)new PairImpl(str, SDDriver.DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT);
/*  56 */         TechDriver.getInstance().setDriverProperties(properties);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  61 */       properties = new Pair[1];
/*  62 */       String licExpirationDelay = System.getProperty("license.expiration.delay");
/*  63 */       String property = "license_expiration_delay," + licExpirationDelay;
/*  64 */       properties[0] = (Pair)new PairImpl(property, SDDriver.DRIVER_PROPERTY_CATEGORY_TECH2WIN_LICENSE_EXPIRATION_DELAY);
/*  65 */       TechDriver.getInstance().setDriverProperties(properties);
/*     */ 
/*     */ 
/*     */       
/*  69 */       properties = ServerRequestor.getInstance().getDTCUploadDriverProperties();
/*  70 */       this.log.debug("Set DTCUpload driver properties" + properties.toString());
/*  71 */       TechDriver.getInstance().setDriverProperties(properties);
/*     */ 
/*     */ 
/*     */       
/*  75 */       this.log.debug("Initialize Device Driver - Init Params: " + initParam);
/*  76 */       retVal = TechDriver.getInstance().initDriver(initParam);
/*  77 */     } catch (Exception e) {
/*  78 */       this.log.error("Exception when init the device with parameter: " + initParam + ". " + e, e);
/*  79 */       retVal = false;
/*     */     } 
/*  81 */     return retVal;
/*     */   }
/*     */   
/*     */   public DeviceInfo getDeviceInfo() {
/*     */     try {
/*  86 */       this.log.debug("call getDeviceInfo()");
/*  87 */       return TechDriver.getInstance().getDeviceContent();
/*  88 */     } catch (Exception e) {
/*  89 */       this.log.error("Exception when get the device content. " + e, e);
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int TestDeviceDriver(String initParams) {
/*     */     try {
/*  98 */       String tech2WinComPort = Starter.getInstance().getTech2WinComPort();
/*  99 */       this.log.debug("Set Tech2Win COM port driver property: " + tech2WinComPort);
/* 100 */       if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/* 101 */         Pair[] properties = new Pair[1];
/* 102 */         String property = "tech2win_com_port," + tech2WinComPort;
/* 103 */         properties[0] = (Pair)new PairImpl(property, SDDriver.DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT);
/* 104 */         TechDriver.getInstance().setDriverProperties(properties);
/*     */       } 
/*     */       
/* 107 */       return TechDriver.getInstance().testDeviceDriver(initParams);
/* 108 */     } catch (Exception e) {
/* 109 */       this.log.error("Exception when testing. " + e, e);
/* 110 */       return 3;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean StartDownload(ApplicationInfo appInf, SDFileInfo[] filesInfo, SDFileSel fileSelector, SDNotifHandler notifHandler, boolean erase) {
/* 115 */     boolean res = false;
/*     */     try {
/* 117 */       this.log.debug("call download(" + appInf + ", " + filesInfo + ", " + fileSelector + ", " + notifHandler + ", " + erase + ")");
/* 118 */       res = TechDriver.getInstance().download(appInf, filesInfo, fileSelector, notifHandler, erase);
/* 119 */     } catch (Exception e) {
/* 120 */       this.log.error("Exception during download. " + e, e);
/* 121 */       return false;
/*     */     } 
/* 123 */     return res;
/*     */   }
/*     */   
/*     */   public String[] getComPorts() {
/*     */     try {
/* 128 */       this.log.debug("call getComPorts()");
/* 129 */       return (String[])Starter.getInstance().getAvailableCommPorts().toArray((Object[])new String[0]);
/* 130 */     } catch (Exception e) {
/* 131 */       this.log.error("Exception when get the com ports. " + e, e);
/* 132 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] DTCUpload(String dealerCode) {
/*     */     try {
/* 138 */       this.log.debug("call DTCUpload()");
/* 139 */       return TechDriver.getInstance().dtcUpload(dealerCode);
/* 140 */     } catch (Exception e) {
/* 141 */       this.log.error("Exception when call DTCUpload. " + e, e);
/* 142 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\IOManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */