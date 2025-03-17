/*     */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.PersistentClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistentClientSettingsImpl
/*     */   implements PersistentClientSettings, ObservableSubject
/*     */ {
/*  33 */   private Logger log = null;
/*  34 */   private Properties mySettings = null;
/*  35 */   private List observers = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentClientSettingsImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentClientSettingsImpl(Properties mySettings) {
/*  44 */     this.mySettings = mySettings;
/*  45 */     this.log = Logger.getLogger(PersistentClientSettingsImpl.class);
/*     */   }
/*     */   
/*     */   public void registerObserver(SettingsObserver o) {
/*  49 */     if (!this.observers.contains(o)) {
/*  50 */       this.observers.add(o);
/*  51 */       this.log.debug("Observer registered: " + o);
/*     */     } else {
/*  53 */       this.log.debug("Observer already registered: " + o);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean removeObserver(SettingsObserver o) {
/*  58 */     boolean ret = this.observers.remove(o);
/*  59 */     if (ret) {
/*  60 */       this.log.debug("Observer removed: " + o);
/*     */     }
/*  62 */     return ret;
/*     */   }
/*     */   
/*     */   public void notifyObservers(Object o) {
/*  66 */     for (int i = 0; i < this.observers.size(); i++) {
/*  67 */       SettingsObserver observer = this.observers.get(i);
/*  68 */       observer.update(this, o);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Properties getSettings() {
/*  73 */     Properties result = new Properties();
/*  74 */     result.putAll(this.mySettings);
/*  75 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean saveSettings(Properties _properties) {
/*  83 */     this.mySettings.clear();
/*  84 */     this.mySettings.putAll(_properties);
/*  85 */     return saveMySettings();
/*     */   }
/*     */   
/*     */   private synchronized boolean saveMySettings() {
/*  89 */     boolean result = false;
/*  90 */     File configFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "sps" + System.getProperty("file.separator") + "configuration.properties");
/*     */     try {
/*  92 */       OutputStream os = new FileOutputStream(configFile);
/*  93 */       this.mySettings.store(os, "SPS Client settings - please do not edit manually.");
/*     */       try {
/*  95 */         os.close();
/*  96 */       } catch (Exception x) {}
/*     */       
/*  98 */       notifyObservers("Configuration change");
/*  99 */       this.log.info("Log settings successfully saved.");
/* 100 */       result = true;
/* 101 */     } catch (Exception e) {
/* 102 */       String msg = "Cannot save local settings: " + e;
/* 103 */       if (this.log != null) {
/* 104 */         this.log.warn(msg);
/*     */       } else {
/* 106 */         System.out.println(msg);
/*     */       } 
/*     */     } 
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   public void loadSettings() {
/* 113 */     boolean foundSettingsFile = false;
/* 114 */     File configFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "sps" + System.getProperty("file.separator") + "configuration.properties");
/* 115 */     InputStream is = null;
/*     */     try {
/* 117 */       is = new FileInputStream(configFile);
/* 118 */       this.mySettings = new Properties();
/* 119 */       this.mySettings.load(is);
/* 120 */       foundSettingsFile = true;
/* 121 */     } catch (Exception e) {
/* 122 */       String msg = "Cannot load local settings";
/* 123 */       if (this.log != null) {
/* 124 */         this.log.warn(msg);
/*     */       } else {
/* 126 */         System.out.println(msg);
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 130 */         if (is != null) {
/* 131 */           is.close();
/*     */         }
/* 133 */       } catch (IOException x) {}
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (this.mySettings == null || this.mySettings.isEmpty()) {
/*     */       try {
/* 139 */         TemplateProperties defaultSettings = new TemplateProperties();
/* 140 */         defaultSettings.load("com/eoos/gm/tis2web/sps/client/settings/defaultConfiguration.properties");
/* 141 */         this.mySettings = new Properties();
/* 142 */         this.mySettings.putAll(defaultSettings);
/* 143 */       } catch (Exception e) {
/* 144 */         String msg = "Cannot load default settings";
/* 145 */         if (this.log != null) {
/* 146 */           this.log.warn(msg);
/*     */         } else {
/* 148 */           System.out.println(msg);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 154 */     updateMySettings();
/* 155 */     if (foundSettingsFile) {
/* 156 */       saveMySettings();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLogger() {
/* 161 */     this.log = Logger.getLogger(PersistentClientSettingsImpl.class);
/*     */   }
/*     */   
/*     */   public boolean createSettingsDir() {
/* 165 */     boolean result = false;
/* 166 */     File spsDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "sps");
/*     */     try {
/* 168 */       if (!spsDir.exists()) {
/* 169 */         spsDir.mkdir();
/* 170 */         result = true;
/*     */       } 
/* 172 */     } catch (Exception e) {
/* 173 */       String msg = "Cannot create local directory: " + e;
/* 174 */       if (this.log != null) {
/* 175 */         this.log.error(msg);
/*     */       } else {
/* 177 */         System.out.println(msg);
/*     */       } 
/*     */     } 
/* 180 */     return result;
/*     */   }
/*     */   
/*     */   private void updateMySettings() {
/*     */     try {
/* 185 */       TemplateProperties newSettings = new TemplateProperties();
/* 186 */       newSettings.load("com/eoos/gm/tis2web/sps/client/settings/changedConfiguration.properties");
/* 187 */       String settingsVersion = this.mySettings.getProperty("settings.version");
/* 188 */       String changedVersion = newSettings.getProperty("settings.version");
/* 189 */       if (settingsVersion == null || changedVersion == null || settingsVersion.compareTo(changedVersion) < 0) {
/* 190 */         this.mySettings.putAll(newSettings);
/*     */       }
/* 192 */     } catch (Exception e) {
/* 193 */       String msg = "Cannot load changed settings";
/* 194 */       if (this.log != null) {
/* 195 */         this.log.warn(msg);
/*     */       } else {
/* 197 */         System.out.println(msg);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\PersistentClientSettingsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */