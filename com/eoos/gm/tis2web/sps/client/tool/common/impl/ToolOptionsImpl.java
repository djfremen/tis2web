/*     */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.PersistentClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolOptionsImpl
/*     */   implements ToolOptions, SettingsObserver
/*     */ {
/*  25 */   private static Logger log = Logger.getLogger(ToolOptionsImpl.class);
/*  26 */   private static Map store = new HashMap<Object, Object>();
/*     */   private String toolId;
/*  28 */   private List<ToolOption> options = new ArrayList<ToolOption>();
/*     */   
/*     */   private ToolOptionsImpl(String _toolId) {
/*  31 */     this.toolId = _toolId;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  35 */     return this.toolId;
/*     */   }
/*     */   
/*     */   public static synchronized ToolOptionsImpl getInstance(String key) {
/*  39 */     ToolOptionsImpl result = null;
/*  40 */     if (store.get(key) != null) {
/*  41 */       result = (ToolOptionsImpl)store.get(key);
/*     */     } else {
/*  43 */       result = new ToolOptionsImpl(key);
/*  44 */       store.put(key, result);
/*     */     } 
/*  46 */     return result;
/*     */   }
/*     */   
/*     */   public void addOption(ToolOption opt) {
/*  50 */     this.options.add(opt);
/*     */   }
/*     */   
/*     */   public ToolOption getOptionByPropertyKey(String key) {
/*  54 */     ToolOption result = null;
/*  55 */     Iterator<ToolOption> it = this.options.iterator();
/*  56 */     while (it.hasNext()) {
/*  57 */       ToolOption opt = it.next();
/*  58 */       String id = opt.getPropertyKey();
/*  59 */       if (id.compareTo(key) == 0) {
/*  60 */         result = opt;
/*     */         break;
/*     */       } 
/*     */     } 
/*  64 */     return result;
/*     */   }
/*     */   
/*     */   public List<ToolOption> getOptions() {
/*  68 */     return new ArrayList<ToolOption>(this.options);
/*     */   }
/*     */   
/*     */   public void update(ObservableSubject savedSettings, Object o) {
/*  72 */     log.debug("update: " + this.toolId);
/*  73 */     Properties settings = ((PersistentClientSettings)savedSettings).getSettings();
/*  74 */     adjustToolOptions(settings);
/*     */   }
/*     */   
/*     */   public synchronized void adjustToolOptions(Properties settings) {
/*  78 */     Enumeration<String> en = settings.keys();
/*  79 */     while (en.hasMoreElements()) {
/*  80 */       String settingsKey = en.nextElement();
/*  81 */       if (settingsKey.startsWith(trim(this.toolId))) {
/*  82 */         ToolOption opt = getOptionByPropertyKey(settingsKey);
/*  83 */         if (opt != null) {
/*  84 */           String defaultValue = settings.getProperty(settingsKey);
/*  85 */           if (opt instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.SelectOption) {
/*  86 */             OptionValue value = opt.getOptionValueByPropertyValue(defaultValue);
/*  87 */             int valueIndex = opt.valueIndex(value);
/*  88 */             if (valueIndex >= 0)
/*  89 */               opt.setDefaultValueIndex(valueIndex);  continue;
/*     */           } 
/*  91 */           if (opt instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.InputOption) {
/*  92 */             OptionValue value = opt.getOptionValue(0);
/*  93 */             value.setKey(settings.getProperty(settingsKey)); continue;
/*     */           } 
/*  95 */           log.warn("Unknown ToolOption type: " + opt.toString());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getToolOptionKey(String toolId, String optionKey) {
/* 103 */     return trim(toolId + ".option." + optionKey);
/*     */   }
/*     */   
/*     */   private String trim(String str) {
/* 107 */     return (new ToolUtils()).trim(str);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ToolOptionsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */