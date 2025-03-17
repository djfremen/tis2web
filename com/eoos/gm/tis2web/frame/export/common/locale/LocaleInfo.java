/*     */ package com.eoos.gm.tis2web.frame.export.common.locale;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class LocaleInfo
/*     */ {
/*  13 */   protected static Logger log = Logger.getLogger(LocaleInfo.class);
/*     */   
/*     */   protected HashMap localesFLC;
/*     */   
/*     */   protected Integer localeID;
/*     */   
/*     */   protected String locale;
/*     */   
/*     */   protected String language;
/*     */   
/*     */   protected String country;
/*     */   
/*     */   protected String tla;
/*     */   
/*     */   protected String scds;
/*     */   
/*     */   protected String description;
/*     */   
/*     */   public Integer getLocaleID() {
/*  32 */     return this.localeID;
/*     */   }
/*     */   
/*     */   public String getLocale() {
/*  36 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getLanguage() {
/*  40 */     return this.language;
/*     */   }
/*     */   
/*     */   public String getCountry() {
/*  44 */     return this.country;
/*     */   }
/*     */   
/*     */   public String getLocaleTLA() {
/*  48 */     return this.tla;
/*     */   }
/*     */   
/*     */   public String getLocaleSCDS() {
/*  52 */     return this.scds;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  56 */     return this.description;
/*     */   }
/*     */   
/*     */   public List getLocaleFLC(LGSIT sit) {
/*  60 */     if (sit == LGSIT.SI) {
/*  61 */       return null;
/*     */     }
/*  63 */     List flc = (List)this.localesFLC.get(sit);
/*  64 */     return (flc == null) ? getLocaleFLC() : flc;
/*     */   }
/*     */   
/*     */   protected LocaleInfo(int localeID, String locale, String tla, String scds, String description) {
/*  68 */     this.localeID = Integer.valueOf(localeID);
/*  69 */     this.locale = locale;
/*  70 */     int delimiter = locale.indexOf('_');
/*  71 */     if (delimiter >= 0) {
/*  72 */       this.language = locale.substring(0, delimiter);
/*  73 */       this.country = locale.substring(delimiter + 1);
/*     */     } else {
/*  75 */       throw new IllegalArgumentException("invalid locale:" + locale);
/*     */     } 
/*  77 */     this.tla = tla;
/*  78 */     this.scds = scds;
/*  79 */     this.description = description;
/*  80 */     this.localesFLC = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   protected List getLocaleFLC() {
/*  84 */     return (List)this.localesFLC.get(LGSIT.DEFAULT);
/*     */   }
/*     */   
/*     */   protected void addFLC(int sit, int localeFLC) throws Exception {
/*  88 */     LGSIT lgsit = findLGSIT(sit);
/*  89 */     List<Integer> flc = (List)this.localesFLC.get(lgsit);
/*  90 */     if (flc == null) {
/*  91 */       flc = new ArrayList();
/*  92 */       this.localesFLC.put(lgsit, flc);
/*     */     } 
/*  94 */     flc.add(Integer.valueOf(localeFLC));
/*     */   }
/*     */   
/*     */   protected LGSIT findLGSIT(int sit) throws Exception {
/*  98 */     Iterator<LGSIT> it = LGSIT.iterator();
/*  99 */     while (it.hasNext()) {
/* 100 */       LGSIT instance = it.next();
/* 101 */       if (instance.ord() == sit) {
/* 102 */         return instance;
/*     */       }
/*     */     } 
/* 105 */     throw new Exception("Invalid LG-SIT '" + sit + "'");
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 109 */     if (this == obj)
/* 110 */       return true; 
/* 111 */     if (obj instanceof LocaleInfo) {
/* 112 */       return ((LocaleInfo)obj).localeID.equals(this.localeID);
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     int ret = LocaleInfo.class.hashCode();
/* 121 */     ret = HashCalc.addHashCode(ret, this.localeID);
/* 122 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\locale\LocaleInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */