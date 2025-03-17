/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*    */ import java.util.HashSet;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationChain
/*    */   implements Configuration, Configuration.Modifyable
/*    */ {
/*    */   private Configuration[] chain;
/*    */   
/*    */   public ConfigurationChain(Configuration[] configurations) {
/* 19 */     this.chain = configurations;
/* 20 */     if (!(this.chain[0] instanceof Configuration.Modifyable)) {
/* 21 */       this.chain = new Configuration[configurations.length + 1];
/* 22 */       this.chain[0] = (Configuration)new PropertiesConfigurationImpl(new Properties());
/* 23 */       System.arraycopy(configurations, 0, this.chain, 1, configurations.length);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 28 */     String retValue = null;
/* 29 */     for (int i = 0; i < this.chain.length && retValue == null; i++) {
/* 30 */       retValue = this.chain[i].getProperty(key);
/*    */     }
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 36 */     Set retValue = new HashSet();
/* 37 */     for (int i = 0; i < this.chain.length; i++) {
/* 38 */       retValue.addAll(this.chain[i].getKeys());
/*    */     }
/* 40 */     return retValue;
/*    */   }
/*    */   
/*    */   public void setProperty(String key, String value) {
/* 44 */     ((Configuration.Modifyable)this.chain[0]).setProperty(key, value);
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 48 */     String ret = null;
/* 49 */     for (int i = 0; i < this.chain.length && ret == null; i++) {
/* 50 */       if (this.chain[i].getProperty(key) != null) {
/* 51 */         ret = this.chain[i].getFullKey(key);
/*    */       }
/*    */     } 
/* 54 */     return ret;
/*    */   }
/*    */   
/*    */   public void remove(String key) {
/* 58 */     ((Configuration.Modifyable)this.chain[0]).remove(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\ConfigurationChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */