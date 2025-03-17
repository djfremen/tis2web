/*     */ package com.eoos.gm.tis2web.swdl.client.model;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceInfo
/*     */   extends ApplicationInfo
/*     */ {
/*  20 */   private int versionSize = 0;
/*  21 */   private static int cardSize = 0;
/*     */   private static boolean strataCardFlag = false;
/*  23 */   private Object versObj = null;
/*  24 */   private Object langObj = null;
/*     */ 
/*     */   
/*     */   public DeviceInfo() {}
/*     */ 
/*     */   
/*     */   public DeviceInfo(String name, String vers, String date, String lang, int versionSize) {
/*  31 */     super(name, vers, date, lang);
/*  32 */     this.versionSize = versionSize;
/*     */   }
/*     */   
/*     */   public void setVersionSize(int versionSize) {
/*  36 */     this.versionSize = versionSize;
/*     */   }
/*     */   
/*     */   public int getVersionSize() {
/*  40 */     return this.versionSize;
/*     */   }
/*     */   
/*     */   public void setCardSize(int cardSize) {
/*  44 */     DeviceInfo.cardSize = cardSize;
/*     */   }
/*     */   
/*     */   public int getCardSize() {
/*  48 */     return cardSize;
/*     */   }
/*     */   
/*     */   public Object getVersionObject() {
/*  52 */     return this.versObj;
/*     */   }
/*     */   
/*     */   public void setVersionObject(Object versObj) {
/*  56 */     this.versObj = versObj;
/*     */   }
/*     */   
/*     */   public void setLanguageObj(Object langObj) {
/*  60 */     this.langObj = langObj;
/*     */   }
/*     */   
/*     */   public Object getLanguageObj() {
/*  64 */     if (this.langObj == null) {
/*  65 */       return this.langObj;
/*     */     }
/*  67 */     Set languages = ((Version)this.versObj).getLanguages();
/*  68 */     Iterator<Language> it = languages.iterator();
/*  69 */     while (it.hasNext()) {
/*  70 */       Language lang = it.next();
/*  71 */       if (lang.getIdentifier().equals(getLanguage())) {
/*  72 */         return lang;
/*     */       }
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean EmptyCard() {
/*  80 */     return (getAppName().compareTo("Unknown Software Origin") == 0 && getVersion().compareTo("0.0") == 0 && getVersDate().compareTo("00-00-00") == 0 && getLanguage().compareTo("**") == 0);
/*     */   }
/*     */   
/*     */   public void setStrataCardFlag(boolean flag) {
/*  84 */     strataCardFlag = flag;
/*     */   }
/*     */   
/*     */   public boolean isStrataCard() {
/*  88 */     return strataCardFlag;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  92 */     Language language = null;
/*  93 */     String locID = getLanguage();
/*  94 */     if (this.versObj == null) {
/*  95 */       if (this.langObj == null) {
/*  96 */         return locID;
/*     */       }
/*  98 */       return ((Language)this.langObj).getDescription(SDCurrentContext.getInstance().getUILanguage());
/*     */     } 
/*     */     
/* 101 */     Set languages = ((Version)this.versObj).getLanguages();
/* 102 */     Iterator<Language> it = languages.iterator();
/* 103 */     while (it.hasNext()) {
/* 104 */       Language lang = it.next();
/* 105 */       if (locID.compareTo((String)lang.getIdentifier()) == 0) {
/* 106 */         language = lang;
/*     */         break;
/*     */       } 
/*     */     } 
/* 110 */     if (language == null) {
/* 111 */       if (this.langObj == null) {
/* 112 */         return locID;
/*     */       }
/* 114 */       language = (Language)this.langObj;
/*     */     } 
/*     */     
/* 117 */     return language.getDescription(SDCurrentContext.getInstance().getUILanguage());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\model\DeviceInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */