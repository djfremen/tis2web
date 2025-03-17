/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.LanguageDatabaseAdapterImpl;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class LanguageRegistry
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(LanguageRegistry.class);
/*     */   
/*  31 */   private static LanguageRegistry instance = null;
/*     */   
/*     */   private class State {
/*  34 */     public Map id2lang = null;
/*     */     private State() {}
/*  36 */     public Integer configHash = null;
/*     */   }
/*     */ 
/*     */   
/*  40 */   private final Object SYNC_STATE = new Object();
/*  41 */   private State state = null;
/*     */ 
/*     */   
/*     */   private LanguageRegistry() {
/*  45 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  46 */     configService.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  49 */             synchronized (LanguageRegistry.this.SYNC_STATE) {
/*  50 */               if (LanguageRegistry.this.state != null && LanguageRegistry.this.state.configHash != null && 
/*  51 */                 LanguageRegistry.this.calcConfigHash() != LanguageRegistry.this.state.configHash.intValue()) {
/*  52 */                 LanguageRegistry.log.info("configuration changed - resetting");
/*  53 */                 LanguageRegistry.this.reset();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  64 */     synchronized (this.SYNC_STATE) {
/*  65 */       this.state = null;
/*     */     } 
/*     */   }
/*     */   
/*  69 */   private static final List configKeys = Arrays.asList(new String[] { "component.swdl.adapter.swdl_languages." });
/*     */   
/*     */   private int calcConfigHash() {
/*  72 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  73 */     return ConfigurationUtil.configurationHash((Configuration)configService, configKeys, ConfigurationUtil.MODE_PREFIXES);
/*     */   }
/*     */   
/*     */   public static synchronized LanguageRegistry getInstance() {
/*  77 */     if (instance == null) {
/*  78 */       instance = new LanguageRegistry();
/*     */     }
/*  80 */     return instance;
/*     */   }
/*     */   
/*     */   private Map getLanguageMap() {
/*  84 */     return (getState()).id2lang;
/*     */   }
/*     */   
/*     */   private State getState() {
/*  88 */     synchronized (this.SYNC_STATE) {
/*  89 */       if (this.state == null) {
/*  90 */         this.state = new State();
/*     */         try {
/*  92 */           DatabaseLink databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.swdl.adapter.swdl_languages.db");
/*  93 */           this.state.id2lang = (new LanguageDatabaseAdapterImpl((IDatabaseLink)databaseLink)).getLanguages();
/*  94 */         } catch (Exception e) {
/*  95 */           log.error("unable to initialize swdl language map (langid -> language) - exception:" + e, e);
/*  96 */           log.error("...initializing empty map");
/*  97 */           this.state.id2lang = Collections.EMPTY_MAP;
/*     */         } 
/*  99 */         this.state.configHash = Integer.valueOf(calcConfigHash());
/*     */       } 
/* 101 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Language getLanguage(String langID) {
/* 106 */     return (Language)getLanguageMap().get(langID);
/*     */   }
/*     */   
/*     */   public void init() {
/* 110 */     getLanguageMap();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\LanguageRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */