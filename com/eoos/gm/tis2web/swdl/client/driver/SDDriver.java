/*    */ package com.eoos.gm.tis2web.swdl.client.driver;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDFileSel;
/*    */ import com.eoos.gm.tis2web.swdl.client.model.ApplicationInfo;
/*    */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*    */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*    */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
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
/*    */ 
/*    */ public interface SDDriver
/*    */ {
/*    */   public static final String DRIVER_PROPERTY_DTCUPLOAD = "dtcupload";
/*    */   public static final String DRIVER_PROPERTY_DTCUPLOAD_ENABLED = "enabled";
/*    */   public static final String DRIVER_PROPERTY_DTCUPLOAD_DISABLED = "disabled";
/* 37 */   public static final Integer DRIVER_PROPERTY_CATEGORY_DTCUPLOAD = Integer.valueOf(1001);
/*    */   
/*    */   public static final String DRIVER_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
/* 40 */   public static final Integer DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT = Integer.valueOf(1003);
/*    */   
/*    */   public static final String DRIVER_PROPERTY_TECH2WIN_LICENSE_EXPIRATION_DELAY = "license_expiration_delay";
/* 43 */   public static final Integer DRIVER_PROPERTY_CATEGORY_TECH2WIN_LICENSE_EXPIRATION_DELAY = Integer.valueOf(1005);
/*    */   
/*    */   boolean initDriver(String paramString);
/*    */   
/*    */   void selectDriver(String paramString);
/*    */   
/*    */   DeviceInfo getDeviceContent();
/*    */   
/*    */   byte[] dtcUpload(String paramString);
/*    */   
/*    */   boolean download(ApplicationInfo paramApplicationInfo, SDFileInfo[] paramArrayOfSDFileInfo, SDFileSel paramSDFileSel, SDNotifHandler paramSDNotifHandler, boolean paramBoolean);
/*    */   
/*    */   int testDeviceDriver(String paramString);
/*    */   
/*    */   boolean setDriverProperties(Pair[] paramArrayOfPair);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\driver\SDDriver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */