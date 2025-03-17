/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSSpecialOption
/*     */   extends SPSOption
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final String SPECIAL_PREFIX = "%";
/*     */   protected static final String YES_OPTION = "yes";
/*     */   protected static final String NO_OPTION = "no";
/*     */   protected SPSOptionByte descriptor;
/*     */   protected byte bit;
/*     */   
/*     */   public static final class StaticData {
/*  18 */     private Map groups = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {}
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getGroups() {
/*  29 */       return this.groups;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  33 */       synchronized (adapter.getSyncObject()) {
/*  34 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  35 */         if (instance == null) {
/*  36 */           instance = new StaticData(adapter);
/*  37 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  39 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPSOptionByte getOptionByte() {
/*  49 */     return this.descriptor;
/*     */   }
/*     */   
/*     */   public byte getBit() {
/*  53 */     return this.bit;
/*     */   }
/*     */   
/*     */   public boolean isOptionYES() {
/*  57 */     return this.id.startsWith("%yes");
/*     */   }
/*     */   
/*     */   void setOptionByte(SPSOptionByte descriptor) {
/*  61 */     this.descriptor = descriptor;
/*     */   }
/*     */   
/*     */   void setOptionByte(SPSOptionByte descriptor, byte bit) {
/*  65 */     this.descriptor = descriptor;
/*  66 */     this.bit = bit;
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSSpecialOption(SPSOption type, int id, String description) {
/*  71 */     super("%" + id, description, type);
/*     */   }
/*     */   
/*     */   public SPSSpecialOption(SPSOption type, String id, String description) {
/*  75 */     super("%" + id, description, type);
/*     */   }
/*     */   
/*     */   protected SPSSpecialOption(int id, String label) {
/*  79 */     super("#%" + id);
/*  80 */     this.description = label;
/*     */   }
/*     */   
/*     */   protected SPSSpecialOption(String id, String label) {
/*  84 */     super(id);
/*  85 */     this.description = label;
/*     */   }
/*     */   
/*     */   static SPSSpecialOption getSpecialOptionGroup(SPSLanguage language, int id, String label, SPSSchemaAdapterNAO adapter) {
/*  89 */     String key = language.getID() + "%" + id;
/*  90 */     Map<String, SPSSpecialOption> groups = StaticData.getInstance(adapter).getGroups();
/*  91 */     SPSSpecialOption group = (SPSSpecialOption)groups.get(key);
/*  92 */     if (group == null) {
/*  93 */       group = new SPSSpecialOption(id, label);
/*  94 */       groups.put(key, group);
/*     */     } 
/*  96 */     return group;
/*     */   }
/*     */ 
/*     */   
/*     */   static SPSSpecialOption getSpecialOptionGroup(String id, String label) {
/* 101 */     String key = "#%" + id;
/* 102 */     return new SPSSpecialOption(key, label);
/*     */   }
/*     */   
/*     */   static SPSSpecialOption getSpecialOption(SPSOption type, SPSLanguage language, String id) {
/* 106 */     String description = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.option-byte." + id);
/* 107 */     return new SPSSpecialOption(type, id + type.getID(), description);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSSpecialOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */