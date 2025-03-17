/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class WriteableConfigurationFacade
/*    */   extends ConfigurationWrapperBase {
/*    */   private File file;
/*    */   private Properties writtenProperties;
/*    */   
/*    */   private WriteableConfigurationFacade(Configuration backend, File file) {
/* 15 */     super(backend);
/* 16 */     this.file = file;
/*    */     
/*    */     try {
/* 19 */       this.writtenProperties = Util.readProperties(file);
/* 20 */     } catch (IOException e) {
/* 21 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static WriteableConfigurationFacade create(Configuration backend, File file) {
/* 27 */     return new WriteableConfigurationFacade(backend, file);
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(String key) {
/* 32 */     this.writtenProperties.remove(key);
/*    */     try {
/* 34 */       Util.writeProperties(this.file, this.writtenProperties);
/* 35 */     } catch (IOException e) {
/* 36 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProperty(String key, String value) {
/* 42 */     this.writtenProperties.setProperty(key, value);
/*    */     try {
/* 44 */       Util.writeProperties(this.file, this.writtenProperties);
/* 45 */     } catch (IOException e) {
/* 46 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getProperty(String key) {
/* 52 */     if (this.writtenProperties.containsKey(key)) {
/* 53 */       return this.writtenProperties.getProperty(key);
/*    */     }
/* 55 */     return super.getProperty(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\WriteableConfigurationFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */