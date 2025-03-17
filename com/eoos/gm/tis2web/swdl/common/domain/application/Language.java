/*    */ package com.eoos.gm.tis2web.swdl.common.domain.application;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.Identifiable;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.LocaleTransformer;
/*    */ import java.io.Serializable;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Language
/*    */   implements Identifiable, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/* 26 */   private static final Logger log = Logger.getLogger(Language.class);
/*    */   
/* 28 */   private String identifier = null;
/*    */   
/* 30 */   private Map id2desc = new Hashtable<Object, Object>();
/*    */ 
/*    */   
/*    */   public Language(String id) {
/* 34 */     this.identifier = id;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 38 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale loc) {
/* 42 */     Locale locale = loc;
/* 43 */     String retValue = null;
/*    */     
/* 45 */     while (retValue == null && locale != null) {
/* 46 */       retValue = (String)this.id2desc.get(String.valueOf(locale));
/* 47 */       locale = LocaleTransformer.transform(locale);
/*    */     } 
/*    */     
/* 50 */     if (retValue == null) {
/* 51 */       Locale fallbackLocale = Locale.ENGLISH;
/* 52 */       if (!fallbackLocale.equals(loc)) {
/* 53 */         log.debug("Description for the Language: " + this.identifier + " and the Locale: " + loc.toString() + " not found.");
/* 54 */         retValue = getDescription(fallbackLocale);
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return retValue;
/*    */   }
/*    */   
/*    */   public void addLangDesc(String id, String desc) {
/* 62 */     this.id2desc.put(id, desc);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.identifier.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 71 */     if (this == obj)
/* 72 */       return true; 
/* 73 */     if (obj instanceof Language) {
/* 74 */       Language other = (Language)obj;
/* 75 */       boolean ret = Util.equals(this.identifier, other.identifier);
/* 76 */       return ret;
/*    */     } 
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\application\Language.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */