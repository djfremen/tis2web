/*    */ package com.eoos.gm.tis2web.sps.client.ui.io.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.io.IOConfiguration;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.io.SettingsProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.DisplayableValueImpl;
/*    */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SettingsProviderImpl
/*    */   implements SettingsProvider
/*    */ {
/* 21 */   private IOConfiguration ioConfig = null;
/*    */   
/* 23 */   private Properties properties = null;
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String CACHE_SIZE = "CacheSize";
/*    */ 
/*    */ 
/*    */   
/*    */   public SettingsProviderImpl() {
/* 32 */     this.ioConfig = new IOConfigurationImpl();
/* 33 */     this.properties = this.ioConfig.load();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCacheSize(String cacheSize) {
/* 38 */     ArrayList array = this.ioConfig.changeProperty("CacheSize", cacheSize);
/* 39 */     this.ioConfig.save(array);
/*    */   }
/*    */   
/*    */   public String getCacheSize() {
/* 43 */     String value = null;
/*    */     try {
/* 45 */       PropertiesConfigurationImpl propertiesConfigurationImpl = new PropertiesConfigurationImpl(this.properties);
/* 46 */       value = propertiesConfigurationImpl.getProperty("CacheSize");
/*    */     }
/* 48 */     catch (Exception e) {
/* 49 */       System.out.println("Exception in getCacheSize() methode: " + e.getMessage());
/*    */     } 
/* 51 */     return value;
/*    */   }
/*    */   
/*    */   public void setVINs(ArrayList allData) {
/* 55 */     this.ioConfig.save(allData);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector getVINs() {
/* 60 */     Vector<DisplayableValue> result = new Vector();
/* 61 */     PropertiesConfigurationImpl propertiesConfigurationImpl = new PropertiesConfigurationImpl(this.properties);
/* 62 */     int i = 1;
/* 63 */     String value = propertiesConfigurationImpl.getProperty("VIN.ITEM" + i);
/* 64 */     while (value != null) {
/* 65 */       result.add(DisplayableValueImpl.getInstance(value));
/* 66 */       i++;
/* 67 */       value = propertiesConfigurationImpl.getProperty("VIN.ITEM" + i);
/*    */     } 
/* 69 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\io\impl\SettingsProviderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */