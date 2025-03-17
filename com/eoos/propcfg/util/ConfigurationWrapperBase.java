/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationWrapperBase
/*    */   implements Configuration, Configuration.Modifyable
/*    */ {
/*    */   private Configuration backend;
/*    */   
/*    */   protected ConfigurationWrapperBase(Configuration backend) {
/* 15 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   protected Configuration getWrappedConfiguration() {
/* 19 */     return this.backend;
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 23 */     return getWrappedConfiguration().getProperty(key);
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 27 */     return getWrappedConfiguration().getKeys();
/*    */   }
/*    */   
/*    */   public void setProperty(String key, String value) {
/* 31 */     if (this.backend instanceof Configuration.Modifyable) {
/* 32 */       ((Configuration.Modifyable)this.backend).setProperty(key, value);
/*    */     } else {
/* 34 */       throw new UnsupportedOperationException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 39 */     return this.backend.getFullKey(key);
/*    */   }
/*    */   
/*    */   public void remove(String key) {
/* 43 */     if (this.backend instanceof Configuration.Modifyable) {
/* 44 */       ((Configuration.Modifyable)this.backend).remove(key);
/*    */     } else {
/* 46 */       throw new UnsupportedOperationException();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\ConfigurationWrapperBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */