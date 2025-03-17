/*    */ package com.eoos.gm.tis2web.sas.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*    */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*    */ import java.util.Locale;
/*    */ 
/*    */ final class DeviceSettingsAdapter
/*    */   implements Tool.CommunicationSettings {
/*    */   private final DeviceSettings settings;
/*    */   
/*    */   DeviceSettingsAdapter(DeviceSettings settings) {
/* 12 */     this.settings = settings;
/*    */   }
/*    */   
/*    */   public Object getPort() {
/* 16 */     return this.settings.getPort();
/*    */   }
/*    */   
/*    */   public Object getBaudrate() {
/* 20 */     return this.settings.getBaudrate();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 24 */     return String.valueOf(getPort()).toUpperCase(Locale.ENGLISH) + "," + String.valueOf(getBaudrate());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\DeviceSettingsAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */