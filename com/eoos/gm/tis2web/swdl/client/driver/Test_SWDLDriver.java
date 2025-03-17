/*     */ package com.eoos.gm.tis2web.swdl.client.driver;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDFileSel;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.ApplicationInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.starter.IStarter;
/*     */ import com.eoos.gm.tis2web.swdl.client.starter.impl.Starter;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Test_SWDLDriver {
/*     */   class StatusCallback
/*     */     implements SDNotifHandler {
/*     */     public void OnBuildAppFile() {
/*  18 */       System.out.println("OnBuildAppFile");
/*     */     }
/*     */     
/*     */     public void OnStartDownload(int maxCount) {
/*  22 */       System.out.println("OnStartDownload");
/*     */     }
/*     */     
/*     */     public void OnEndDownload() {
/*  26 */       System.out.println("OnEndDownload");
/*     */     }
/*     */     
/*     */     public void OnDataTransmitted(int progress) {
/*  30 */       System.out.println("OnDataTransmitted");
/*     */     }
/*     */     
/*     */     public void OnDownAborted(int progress) {
/*  34 */       System.out.println("OnDownAborted");
/*     */     }
/*     */     
/*     */     public void OnSeeking() {
/*  38 */       System.out.println("OnSeeking");
/*     */     }
/*     */     
/*     */     public boolean OnTechReset() {
/*  42 */       System.out.println("OnTechReset");
/*  43 */       return true;
/*     */     }
/*     */     
/*     */     public void PDTBIOSMessage() {
/*  47 */       System.out.println("PDTBIOSMessage");
/*     */     }
/*     */     
/*     */     public void OnEndErase() {
/*  51 */       System.out.println("OnEndErase");
/*     */     }
/*     */     
/*     */     public void OnStartErase(int start) {
/*  55 */       System.out.println("OnStartErase");
/*     */     }
/*     */     
/*     */     public void OnEraseProgress(int progress) {
/*  59 */       System.out.println("OnEraseProgress");
/*     */     }
/*     */     
/*     */     public void OnDirRelearn() {
/*  63 */       System.out.println("OnDirRelearn");
/*     */     }
/*     */   }
/*     */   
/*     */   void testStarter() {
/*  68 */     IStarter starter = Starter.getInstance();
/*  69 */     if (starter != null) {
/*  70 */       List<String> ports = starter.getAvailableCommPorts();
/*  71 */       System.out.println("COM ports: " + ports);
/*     */       
/*  73 */       String tech2WinComPort = starter.getTech2WinComPort();
/*  74 */       System.out.println("Tech2Win COM port: " + tech2WinComPort);
/*     */     } else {
/*  76 */       System.out.println("Starter test failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void testSWDL() {
/*     */     try {
/*  86 */       TechDriver.getInstance().selectDriver("Tech2");
/*     */       
/*  88 */       if (!TechDriver.getInstance().initDriver("COM1,115200")) {
/*  89 */         throw new RuntimeException("Could not initialize device driver");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       if (TechDriver.getInstance().testDeviceDriver("AUTO,115200") == 1) {
/*  97 */         System.out.println("Driver test - success");
/*     */       } else {
/*  99 */         System.out.println("Driver test - failed");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 104 */       byte[] dtcData = TechDriver.getInstance().dtcUpload(new String("12345678901"));
/*     */       
/* 106 */       if (dtcData != null) {
/* 107 */         System.out.println("Download DTC data - success");
/*     */       } else {
/* 109 */         System.out.println("Download DTC data - failed");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 114 */       Thread.sleep(5000L);
/*     */       
/* 116 */       DeviceInfo deviceInfo = TechDriver.getInstance().getDeviceContent();
/*     */       
/* 118 */       if (deviceInfo != null) {
/* 119 */         System.out.println("Download device info ( current device version ) - success: \n");
/*     */         
/* 121 */         StringBuffer buffer = new StringBuffer();
/* 122 */         buffer.append("Current device application info:\n\n");
/* 123 */         buffer.append("Application Name - ");
/* 124 */         buffer.append(deviceInfo.getAppName());
/* 125 */         buffer.append("\n");
/* 126 */         buffer.append("Application version - ");
/* 127 */         buffer.append(deviceInfo.getVersion());
/* 128 */         buffer.append("\n");
/* 129 */         buffer.append("Application date - ");
/* 130 */         buffer.append(deviceInfo.getVersDate());
/* 131 */         buffer.append("\n");
/* 132 */         buffer.append("Application language - ");
/* 133 */         buffer.append(deviceInfo.getLanguage());
/* 134 */         buffer.append("\n");
/*     */         
/* 136 */         System.out.println(buffer.toString());
/*     */       } else {
/*     */         
/* 139 */         System.out.println("Download device info ( current device version ) - failed");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 144 */       PairImpl[] arrayOfPairImpl = new PairImpl[5];
/* 145 */       arrayOfPairImpl[0] = new PairImpl("dtcupload,disabled", SDDriver.DRIVER_PROPERTY_CATEGORY_DTCUPLOAD);
/*     */       
/* 147 */       if (TechDriver.getInstance().setDriverProperties((Pair[])arrayOfPairImpl) == true) {
/* 148 */         System.out.println("Set driver properties - success");
/*     */       } else {
/* 150 */         System.out.println("Set driver properties - failed");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 155 */       ApplicationInfo appInfo = new ApplicationInfo("Eoos Test Application", "88.888", "2007-11-21", "DE");
/*     */       
/* 157 */       SDFileInfo[] filesInfo = new SDFileInfo[10];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       SDFileSel fileSelector = new SDFileSel()
/*     */         {
/*     */           public boolean Seek(int appPos, int fid) {
/* 174 */             return false;
/*     */           }
/*     */           
/*     */           public byte[] getNextFile(int size) {
/* 178 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 183 */       SDNotifHandler status = new StatusCallback();
/*     */       
/* 185 */       TechDriver.getInstance().download(appInfo, filesInfo, fileSelector, status, true);
/*     */       
/* 187 */       TechDriver.discardInstance();
/*     */     }
/* 189 */     catch (UnsatisfiedLinkError e) {
/* 190 */       System.out.println("Error while loading the DLL: " + e);
/* 191 */     } catch (Exception e) {
/* 192 */       System.out.println("Error: " + e.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 197 */     Test_SWDLDriver t = new Test_SWDLDriver();
/*     */     
/* 199 */     t.testStarter();
/* 200 */     t.testSWDL();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\driver\Test_SWDLDriver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */