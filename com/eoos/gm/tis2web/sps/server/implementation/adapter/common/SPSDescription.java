/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SPSDescription {
/*    */   protected Map descriptions;
/*    */   
/*    */   public String get(SPSLanguage language) {
/* 11 */     return (String)this.descriptions.get(language);
/*    */   }
/*    */   
/*    */   public String getDefaultLabel() {
/* 15 */     if (this.descriptions.isEmpty()) {
/* 16 */       return null;
/*    */     }
/* 18 */     Iterator<SPSLanguage> it = this.descriptions.keySet().iterator();
/* 19 */     while (it.hasNext()) {
/* 20 */       SPSLanguage language = it.next();
/* 21 */       if (language.getLocale().startsWith("en_")) {
/* 22 */         return (String)this.descriptions.get(language);
/*    */       }
/*    */     } 
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public SPSDescription() {
/* 29 */     this.descriptions = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void add(SPSLanguage language, String description) {
/* 33 */     this.descriptions.put(language, description);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSDescription.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */