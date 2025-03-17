/*     */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.PersistentClientSettings;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientSettingsImpl
/*     */   implements ClientSettings
/*     */ {
/*  24 */   private static Logger log = Logger.getLogger(ClientSettingsImpl.class);
/*     */   private static final String VINPREFIX = "vin.";
/*     */   private static final String MAXVINSTR = "maxvin";
/*     */   private static final int MAXVIN = 10;
/*     */   private static final String DEFAULT_CACHE = "spsCache";
/*     */   private static final String DEFAULT_LOG = "sps.log";
/*  30 */   private Properties properties = null;
/*  31 */   private PersistentClientSettings persistentSettings = null;
/*     */   
/*     */   public ClientSettingsImpl(PersistentClientSettings _persistentSettings) {
/*  34 */     this.persistentSettings = _persistentSettings;
/*  35 */     this.properties = _persistentSettings.getSettings();
/*     */   }
/*     */   
/*     */   public String getProperty(String key) {
/*  39 */     return this.properties.getProperty(key);
/*     */   }
/*     */   
/*     */   public Properties getProperties(String keyPrefix) {
/*  43 */     Properties result = new Properties();
/*  44 */     Iterator<String> it = getKeys().iterator();
/*  45 */     while (it.hasNext()) {
/*  46 */       String key = it.next();
/*  47 */       if (key.indexOf(keyPrefix) == 0) {
/*  48 */         result.put(key, getProperty(key));
/*     */       }
/*     */     } 
/*  51 */     return result;
/*     */   }
/*     */   
/*     */   public void setProperty(String key, String value) {
/*  55 */     this.properties.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperties(Properties _properties) {
/*  60 */     this.properties.putAll(_properties);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getKeys() {
/*  65 */     return this.properties.keySet();
/*     */   }
/*     */   
/*     */   public synchronized void setVIN(String vin) {
/*  69 */     List<String> allVINs = getVINs();
/*  70 */     if (!allVINs.contains(vin)) {
/*  71 */       int maxVIN = 10;
/*  72 */       String maxStr = getProperty("maxvin");
/*  73 */       if (maxStr != null) {
/*     */         try {
/*  75 */           maxVIN = Integer.valueOf(maxStr).intValue();
/*  76 */         } catch (Exception e) {
/*  77 */           log.warn("Invalid MAXVIN settings");
/*     */         } 
/*     */       }
/*  80 */       allVINs.add(0, vin);
/*  81 */       if (allVINs.size() > maxVIN) {
/*  82 */         allVINs.remove(maxVIN);
/*     */       }
/*  84 */       removeProperties("vin.");
/*  85 */       int i = 0;
/*  86 */       for (; i < allVINs.size(); i++) {
/*  87 */         String key = "vin." + Integer.valueOf(i).toString();
/*  88 */         this.properties.put(key, allVINs.get(i));
/*     */       } 
/*     */     } else {
/*  91 */       log.debug("VIN already in cache: " + vin);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getVINs() {
/*  96 */     List<Object> result = new ArrayList();
/*  97 */     Properties vins = getProperties("vin.");
/*  98 */     List<?> vinKeys = new ArrayList(vins.keySet());
/*  99 */     Collections.sort(vinKeys, new Comparator() {
/*     */           public int compare(Object o1, Object o2) {
/* 101 */             return ((String)o1).compareTo((String)o2);
/*     */           }
/*     */         });
/* 104 */     Iterator<?> it = vinKeys.iterator();
/* 105 */     while (it.hasNext()) {
/* 106 */       result.add(vins.get(it.next()));
/*     */     }
/* 108 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setLogPath(String logFile) {
/* 117 */     boolean result = false;
/* 118 */     boolean stillOkay = true;
/* 119 */     String name = null;
/* 120 */     File file = new File(logFile);
/* 121 */     if (!file.isAbsolute()) {
/* 122 */       name = System.getProperty("user.home") + System.getProperty("file.separator") + "sps" + System.getProperty("file.separator") + logFile;
/*     */     } else {
/* 124 */       name = logFile;
/*     */     } 
/* 126 */     String canonicalName = null;
/* 127 */     file = new File(name);
/*     */     try {
/* 129 */       canonicalName = file.getCanonicalPath();
/* 130 */     } catch (Exception e) {
/* 131 */       log.error("Invalid path for logFile File: " + logFile + " (" + name + ")");
/* 132 */       stillOkay = false;
/*     */     } 
/* 134 */     if (stillOkay && !file.exists()) {
/*     */       try {
/* 136 */         stillOkay = file.createNewFile();
/* 137 */       } catch (Exception e) {
/* 138 */         log.error("Cannot create file: " + logFile + ", " + e);
/* 139 */         stillOkay = false;
/*     */       } 
/*     */     }
/* 142 */     if (stillOkay && file.isFile() && file.canWrite()) {
/* 143 */       log.info("Log PathName: " + canonicalName);
/* 144 */       setProperty("log.fileName", canonicalName);
/* 145 */       result = true;
/*     */     } 
/* 147 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogPath() {
/* 155 */     String result = null;
/* 156 */     String logPath = getProperty("log.fileName");
/* 157 */     if (logPath == null) {
/* 158 */       logPath = "sps.log";
/*     */     }
/*     */     
/* 161 */     if (setLogPath(logPath)) {
/* 162 */       result = getProperty("log.fileName");
/*     */     }
/* 164 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setSPSCache(String cache) {
/* 173 */     boolean result = false;
/* 174 */     boolean stillOkay = true;
/* 175 */     String name = null;
/* 176 */     File dir = new File(cache);
/* 177 */     if (!dir.isAbsolute()) {
/* 178 */       name = System.getProperty("user.home") + System.getProperty("file.separator") + "sps" + System.getProperty("file.separator") + cache;
/*     */     } else {
/* 180 */       name = cache;
/*     */     } 
/* 182 */     String canonicalName = null;
/* 183 */     dir = new File(name);
/*     */     try {
/* 185 */       canonicalName = dir.getCanonicalPath();
/* 186 */     } catch (Exception e) {
/* 187 */       log.error("Invalid path for cache directory: " + cache + " (" + name + ")");
/* 188 */       stillOkay = false;
/*     */     } 
/* 190 */     if (stillOkay && !dir.exists()) {
/* 191 */       stillOkay = dir.mkdir();
/*     */     }
/* 193 */     if (stillOkay && dir.isDirectory() && dir.canWrite()) {
/* 194 */       log.info("SPS cache: " + canonicalName);
/* 195 */       setProperty("cachePath", canonicalName);
/* 196 */       result = true;
/*     */     } 
/* 198 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSPSCache() {
/* 206 */     String result = null;
/* 207 */     String cache = getProperty("cachePath");
/* 208 */     if (cache == null) {
/* 209 */       cache = "spsCache";
/*     */     }
/* 211 */     if (setSPSCache(cache)) {
/* 212 */       result = getProperty("cachePath");
/*     */     }
/* 214 */     return result;
/*     */   }
/*     */   
/*     */   public boolean saveConfiguration() {
/* 218 */     return this.persistentSettings.saveSettings(this.properties);
/*     */   }
/*     */   
/*     */   public boolean toolsAreLocal() {
/* 222 */     boolean result = true;
/* 223 */     String isLocal = getProperty("tool.isLocal");
/* 224 */     if (isLocal != null && isLocal.toUpperCase(Locale.ENGLISH).compareTo("FALSE") == 0) {
/* 225 */       result = false;
/*     */     }
/* 227 */     return result;
/*     */   }
/*     */   
/*     */   public boolean clearSPSCache() {
/* 231 */     boolean result = false;
/*     */     try {
/* 233 */       String path = getSPSCache();
/* 234 */       if (path != null) {
/* 235 */         File dir = new File(path);
/* 236 */         if (dir.exists() && dir.isDirectory()) {
/* 237 */           File[] entries = dir.listFiles();
/* 238 */           boolean lastResult = true;
/* 239 */           for (int i = 0; i < entries.length; i++) {
/* 240 */             lastResult &= entries[i].delete();
/*     */           }
/* 242 */           result = lastResult;
/*     */         } 
/*     */       } 
/* 245 */     } catch (Exception e) {
/* 246 */       log.debug("Cannot clear SPS cache: " + e);
/*     */     } 
/* 248 */     return result;
/*     */   }
/*     */   
/*     */   private void removeProperties(String keyPrefix) {
/* 252 */     Iterator<?> it = (new HashSet(getKeys())).iterator();
/* 253 */     while (it.hasNext()) {
/* 254 */       String key = (String)it.next();
/* 255 */       if (key.indexOf(keyPrefix) == 0)
/* 256 */         this.properties.remove(key); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\ClientSettingsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */