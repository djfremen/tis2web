/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.DTCMonitoring;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SWCacheManager;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.NotificationHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.IOManagerUI;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.DomainUtil;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SDUINotifHandler
/*     */   implements NotificationHandler
/*     */ {
/*  25 */   private int success = -1;
/*  26 */   private SDEvent event = null;
/*  27 */   private String screen = null;
/*     */ 
/*     */   
/*     */   public SDUINotifHandler() {
/*  31 */     SDNotificationServer.getInstance().register(16L, this);
/*  32 */     SDNotificationServer.getInstance().register(6L, this);
/*  33 */     SDNotificationServer.getInstance().register(9L, this);
/*  34 */     SDNotificationServer.getInstance().register(17L, this);
/*  35 */     SDNotificationServer.getInstance().register(18L, this);
/*  36 */     SDNotificationServer.getInstance().register(19L, this);
/*  37 */     SDNotificationServer.getInstance().register(22L, this);
/*  38 */     SDNotificationServer.getInstance().register(23L, this);
/*  39 */     SDNotificationServer.getInstance().register(20L, this);
/*  40 */     SDNotificationServer.getInstance().register(3L, this);
/*  41 */     SDNotificationServer.getInstance().register(31L, this);
/*     */   }
/*     */   
/*     */   public void handleNotification(Notification msg) {
/*     */     try {
/*  46 */       SDEvent eve = (SDEvent)msg;
/*  47 */       switch ((int)eve.getType()) {
/*     */         case 16:
/*  49 */           onInit();
/*     */           break;
/*     */         case 6:
/*  52 */           onEventDriverSelection((String[])eve.getParam(0));
/*     */           break;
/*     */         case 9:
/*  55 */           onEventStartDownload();
/*     */           break;
/*     */         case 17:
/*  58 */           eve.addParam(IOManagerUI.getInstance().TestDevice((String)eve.getParam(0)));
/*     */           break;
/*     */         case 18:
/*  61 */           IOManagerUI.getInstance().saveDevSettings((String)eve.getParam(0), (String)eve.getParam(1));
/*     */           break;
/*     */         case 19:
/*  64 */           IOManagerUI.getInstance().saveLogSettings(((Boolean)eve.getParam(0)).booleanValue(), ((Boolean)eve.getParam(1)).booleanValue(), (String)eve.getParam(2));
/*     */           break;
/*     */         case 20:
/*  67 */           IOManagerUI.getInstance().updateSettings();
/*     */           break;
/*     */         case 22:
/*  70 */           IOManagerUI.getInstance().initComPorts();
/*     */           break;
/*     */         case 23:
/*  73 */           onDownload(eve);
/*     */           break;
/*     */         case 3:
/*  76 */           onBtnPressed(((Byte)eve.getParam(0)).byteValue());
/*     */           break;
/*     */         case 31:
/*  79 */           onClearCache();
/*     */           break;
/*     */       } 
/*  82 */     } catch (Exception e) {
/*  83 */       System.out.println(getClass().getName() + ".handleNotification() - Caught exception: " + e.toString());
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onClearCache() {
/*  89 */     SWCacheManager cacheMan = new SWCacheManager();
/*  90 */     if (cacheMan.clearCache()) {
/*  91 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_DELCACHE_SUCCES", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */     } else {
/*  93 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERROR_DELCACHE", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onBtnPressed(byte btnCode) {}
/*     */   
/*     */   private void onDownload(SDEvent eve) {
/* 101 */     this.event = eve;
/* 102 */     (new Thread() {
/*     */         public void run() {
/* 104 */           SDUINotifHandler.this.startDownload();
/*     */         }
/*     */       }).start();
/*     */   }
/*     */   
/*     */   private void startDownload() {
/* 110 */     if (IOManagerUI.getInstance().StartDownload((SDNotifHandler)this.event.getParam(0))) {
/* 111 */       ((SDNotifHandler)this.event.getParam(0)).OnEndDownload();
/*     */     } else {
/* 113 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)2), new Boolean(true)));
/*     */     } 
/*     */   }
/*     */   private void onInit() {
/* 117 */     IOManagerUI.getInstance().loadSettings();
/* 118 */     IOManagerUI.getInstance().initComPorts();
/* 119 */     IOManagerUI.getInstance().initDevices();
/* 120 */     SDCurrentContext.getInstance().setSelectedTool(Device.TECH2);
/* 121 */     DTCMonitoring.getInstance().startMonitoring();
/* 122 */     IOManagerUI.getInstance().initUILanguage();
/*     */   }
/*     */   
/*     */   private void getDeviceInfo() {
/* 126 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_READ_DEVICE"));
/* 127 */     this.success = IOManagerUI.getInstance().getDeviceInfo();
/*     */   }
/*     */   
/*     */   private void getApplications() {
/* 131 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_READ_SERVER"));
/* 132 */     this.success = IOManagerUI.getInstance().getApplications();
/*     */   }
/*     */   
/*     */   private void getNewAppVers() {
/* 136 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_READ_SERVER"));
/* 137 */     this.success = -1;
/* 138 */     this.success = IOManagerUI.getInstance().getCurrVersion();
/* 139 */     if (this.success == -3) {
/* 140 */       IOManagerUI.getInstance().getLanguageObj();
/* 141 */       this.success = IOManagerUI.getInstance().getNewAppVersion();
/*     */     } 
/* 143 */     if (this.success == 5) {
/* 144 */       this.success = IOManagerUI.getInstance().getNewAppVers();
/*     */     }
/*     */   }
/*     */   
/*     */   private void getCurrAppVers() {
/* 149 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_READ_SERVER"));
/* 150 */     this.success = -1;
/* 151 */     this.success = IOManagerUI.getInstance().getCurrVersion();
/* 152 */     if (this.success != 5) {
/* 153 */       this.success = IOManagerUI.getInstance().getLanguageObj();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initDevDTCUpload() {
/* 158 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_INIT_DEVICE"));
/* 159 */     this.success = 0;
/* 160 */     if (initializeDeviceDriver() == true) {
/* 161 */       if (Device.TECH2.equals(SDCurrentContext.getInstance().getSelectedTool()) == true) {
/* 162 */         IOManagerUI.getInstance().DTCUpload();
/* 163 */         DTCMonitoring.getInstance().startMonitoring();
/*     */       } 
/* 165 */       this.success = 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initDevice() {
/* 170 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(30L, "IDS_INIT_DEVICE"));
/* 171 */     this.success = 0;
/* 172 */     if (initializeDeviceDriver() == true) {
/* 173 */       this.success = 5;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean initializeDeviceDriver() {
/* 178 */     boolean retValue = false;
/* 179 */     String device = SDCurrentContext.getInstance().getSelectedTool().getDescription();
/* 180 */     if (IOManagerUI.getInstance().initDevice(device) == true) {
/* 181 */       retValue = true;
/*     */     }
/* 183 */     return retValue;
/*     */   }
/*     */   
/*     */   private void onEventDriverSelection(String[] driverInfo) {
/* 187 */     if (!IOManagerUI.getInstance().isComPortInstalled(driverInfo[0]) || SDCurrentContext.getInstance().getSettings().get("CachePath") == null) {
/* 188 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)1)));
/*     */       return;
/*     */     } 
/* 191 */     SDCurrentContext.getInstance().setSelectedTool(Device.getTech(driverInfo[0]));
/* 192 */     SDCurrentContext.getInstance().setSelectedMode(driverInfo[1]);
/* 193 */     this.screen = null;
/*     */     
/* 195 */     (new Thread() {
/*     */         public void run() {
/* 197 */           SDUINotifHandler.this.doEventDriverSelectionJob();
/* 198 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/*     */         }
/*     */       }).start();
/* 201 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(true), "IDS_READ_DEVICE"));
/* 202 */     if (this.screen != null) {
/* 203 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(15L, this.screen));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void doEventDriverSelectionJob() {
/* 209 */     initDevDTCUpload();
/*     */     
/* 211 */     if (SDCurrentContext.getInstance().getSelectedMode().compareTo("Standard") == 0) {
/*     */       
/* 213 */       if (this.success == 0) {
/* 214 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 215 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERR_E1037", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */         
/*     */         return;
/*     */       } 
/* 219 */       if (checkDeviceAndAppVers(true) == 3) {
/* 220 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(15L, this.screen = "ConfirmSWChange"));
/*     */       }
/*     */     } else {
/*     */       
/* 224 */       if (this.success == 0) {
/* 225 */         SDEvent event = new SDEvent(14L);
/* 226 */         SDNotificationServer.getInstance().sendNotification((Notification)event);
/* 227 */         if (!((Boolean)event.getParam(0)).booleanValue()) {
/*     */           return;
/*     */         }
/*     */       } else {
/*     */         
/* 232 */         getDeviceInfo();
/*     */         
/* 234 */         if (this.success == -1) {
/* 235 */           SDEvent event = new SDEvent(14L);
/* 236 */           SDNotificationServer.getInstance().sendNotification((Notification)event);
/* 237 */           if (!((Boolean)event.getParam(0)).booleanValue()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 243 */       getCurrAppVers();
/*     */ 
/*     */       
/* 246 */       getApplications();
/*     */       
/* 248 */       if (this.success == -1) {
/* 249 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 250 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_COMM_SRV_FAILED", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */         return;
/*     */       } 
/* 253 */       if (this.success == -4) {
/* 254 */         String ids = DomainUtil.SrvErr2IDS((SDCurrentContext.getInstance()).srvError);
/* 255 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 256 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, ids, "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */         
/*     */         return;
/*     */       } 
/* 260 */       SDCurrentContext.getInstance().setNewAppInfo(null);
/* 261 */       this.screen = "SelectApplication";
/*     */     } 
/*     */   }
/*     */   
/*     */   private int checkDeviceAndAppVers(boolean getNewestVers) {
/* 266 */     this.success = 3;
/* 267 */     if (getNewestVers || ((String)SDCurrentContext.getInstance().getSettings().get("CheckTech2Times")).compareTo("Yes") == 0) {
/* 268 */       initDevice();
/*     */       
/* 270 */       if (this.success == 0) {
/* 271 */         this.success = -1;
/*     */       } else {
/*     */         
/* 274 */         getDeviceInfo();
/*     */       } 
/*     */     } 
/* 277 */     if (this.success == 3) {
/* 278 */       String ids, params[]; if (getNewestVers && (SDCurrentContext.getInstance().getDeviceInfo() == null || SDCurrentContext.getInstance().getDeviceInfo().getAppName().compareTo("Unknown Software Orgin") == 0)) {
/* 279 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_NO_DEVICEDATA", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/* 280 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 281 */         return 0;
/*     */       } 
/* 283 */       if (getNewestVers || SDCurrentContext.getInstance().getSelectedMode().compareTo("Standard") == 0) {
/*     */         
/* 285 */         getNewAppVers();
/*     */       } else {
/* 287 */         this.success = IOManagerUI.getInstance().getVersDisscution();
/*     */       } 
/* 289 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 290 */       switch (this.success) {
/*     */         case -4:
/* 292 */           ids = DomainUtil.SrvErr2IDS((SDCurrentContext.getInstance()).srvError);
/* 293 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, ids, "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */           break;
/*     */         case -1:
/* 296 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_COMM_SRV_FAILED", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/*     */           break;
/*     */         case 0:
/* 299 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_MSG_M1119", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */           break;
/*     */         case 4:
/* 302 */           params = new String[1];
/* 303 */           params[0] = Integer.toString(SDCurrentContext.getInstance().getNewAppInfo().getVersionSize());
/* 304 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_MSG_NEWVERS_DIFFCARD", "IDS_TITLE_SWDL", Integer.valueOf(1), params));
/*     */           break;
/*     */         case 6:
/* 307 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_MSG_NO_STRATA_SUPPORT", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */           break;
/*     */         case 2:
/* 310 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_NO_DEVICEDATA", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */           break;
/*     */         case -3:
/* 313 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_CURRAPP_NOTFOUND", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */           break;
/*     */       } 
/* 316 */       return this.success;
/*     */     } 
/* 318 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 319 */     if (this.success == -1) {
/* 320 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERR_E1037", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 321 */       return 2;
/*     */     } 
/* 323 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERR_E1296", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 324 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onEventStartDownload() {
/* 331 */     (new Thread() {
/*     */         public void run() {
/* 333 */           SDUINotifHandler.this.success = SDUINotifHandler.this.checkDeviceAndAppVers(false);
/* 334 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(false)));
/* 335 */           switch (SDUINotifHandler.this.success) {
/*     */             case 3:
/* 337 */               SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(15L, "Download"));
/*     */               return;
/*     */             case 1:
/* 340 */               SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_MSG_VERS_DIFF_SIZE", "IDS_TITLE_SWDL", Integer.valueOf(1)));
/*     */               break;
/*     */           } 
/* 343 */           if (SDCurrentContext.getInstance().getSelectedMode().compareTo("Standard") == 0) {
/* 344 */             SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)4)));
/*     */           }
/*     */         }
/*     */       }).start();
/* 348 */     if (((String)SDCurrentContext.getInstance().getSettings().get("CheckTech2Times")).compareTo("Yes") == 0)
/* 349 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(7L, new Boolean(true), "IDS_READ_DEVICE")); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SDUINotifHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */