/*    */ package com.eoos.propcfg;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubConfigurationWrapper
/*    */   implements Configuration
/*    */ {
/* 15 */   private Configuration backend = null;
/*    */   
/* 17 */   private String prefix = null;
/*    */   
/*    */   public SubConfigurationWrapper(Configuration backend, String prefix) {
/* 20 */     this.backend = backend;
/* 21 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getProperty(String key) {
/* 26 */     return this.backend.getProperty(this.prefix + key);
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 30 */     Set orgKeys = this.backend.getKeys();
/* 31 */     Set<String> retValue = new HashSet(orgKeys.size());
/* 32 */     for (Iterator<String> iter = orgKeys.iterator(); iter.hasNext(); ) {
/* 33 */       String key = iter.next();
/* 34 */       int index = key.indexOf(this.prefix);
/* 35 */       if (index == 0) {
/* 36 */         retValue.add(key.substring(this.prefix.length()));
/*    */       }
/*    */     } 
/* 39 */     return retValue;
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 43 */     String ret = this.prefix + key;
/* 44 */     if (this.backend instanceof SubConfigurationWrapper) {
/* 45 */       ret = ((SubConfigurationWrapper)this.backend).getFullKey(ret);
/*    */     }
/* 47 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcfg\SubConfigurationWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */