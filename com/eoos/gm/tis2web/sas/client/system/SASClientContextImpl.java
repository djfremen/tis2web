/*    */ package com.eoos.gm.tis2web.sas.client.system;
/*    */ 
/*    */ import com.eoos.context.Context;
/*    */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*    */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*    */ import com.eoos.scsm.v2.io.StreamUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.File;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SASClientContextImpl
/*    */   extends Context
/*    */   implements SASClientContext
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(SASClientContextImpl.class);
/*    */   
/* 20 */   private Locale locale = null;
/*    */   
/* 22 */   private String sessionID = null;
/*    */   
/*    */   private boolean isLocalInstallation;
/*    */   
/* 26 */   private DeviceSettings deviceSettings = null;
/*    */   
/*    */   private File homeDir;
/*    */   
/*    */   SASClientContextImpl(File homeDir) {
/* 31 */     this.homeDir = homeDir;
/*    */     
/* 33 */     String _locale = System.getProperty("locale");
/* 34 */     if (_locale != null) {
/* 35 */       this.locale = StringUtilities.getLocale(_locale);
/*    */     } else {
/* 37 */       this.locale = Locale.getDefault();
/*    */     } 
/* 39 */     this.sessionID = System.getProperty("session.id");
/* 40 */     this.isLocalInstallation = "local".equals(System.getProperty("server.installation"));
/*    */     
/*    */     try {
/* 43 */       File fileDS = getDeviceSettingsFile();
/* 44 */       if (fileDS.exists()) {
/* 45 */         this.deviceSettings = (DeviceSettings)StreamUtil.readObject(fileDS);
/*    */       }
/* 47 */     } catch (Exception e) {
/* 48 */       log.warn("unable to load device settings, ignoring - exception: " + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private File getDeviceSettingsFile() {
/* 53 */     return new File(this.homeDir, ".devset");
/*    */   }
/*    */   
/*    */   public Locale getLocale() {
/* 57 */     return this.locale;
/*    */   }
/*    */   
/*    */   public String getSessionID() {
/* 61 */     return this.sessionID;
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 65 */     return LabelResourceProvider.getInstance().getLabelResource().getLabel(getLocale(), key);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage(String key) {
/* 70 */     return LabelResourceProvider.getInstance().getLabelResource().getMessage(getLocale(), key);
/*    */   }
/*    */   
/*    */   public boolean isStandaloneInstallation() {
/* 74 */     return this.isLocalInstallation;
/*    */   }
/*    */   
/*    */   public DeviceSettings getDeviceSettings() {
/* 78 */     return this.deviceSettings;
/*    */   }
/*    */   
/*    */   public void setDeviceSettings(DeviceSettings settings) {
/* 82 */     log.debug("storing device settings: " + String.valueOf(settings));
/* 83 */     this.deviceSettings = settings;
/*    */     try {
/* 85 */       StreamUtil.writeObject(getDeviceSettingsFile(), settings);
/* 86 */     } catch (Exception e) {
/* 87 */       log.warn("unable to write device settings, ignoring - exception: " + e, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\SASClientContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */