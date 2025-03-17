/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.gm.tis2web.dtc.service.DTCStorageServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.dtc.TroubleCode;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWDLController
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(SWDLController.class);
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private SWDLController(ClientContext context) {
/*  33 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SWDLController getInstance(ClientContext context) {
/*  38 */     synchronized (context.getLockObject()) {
/*  39 */       SWDLController instance = (SWDLController)context.getObject(SWDLController.class);
/*  40 */       if (instance == null) {
/*  41 */         instance = new SWDLController(context);
/*  42 */         context.storeObject(SWDLController.class, instance);
/*     */       } 
/*  44 */       return instance;
/*     */     }  } public Object execute(Command command) { String sessionID, appID, versNo; Set troubleCodes; final String _deviceName, fileID;
/*     */     boolean retValue;
/*     */     final String _appDesc, _version, langID;
/*     */     final Language _language;
/*  49 */     Device device = null;
/*  50 */     String appDesc = null;
/*  51 */     String lanID = null;
/*  52 */     switch (command.getCommandID())
/*     */     { case 13:
/*  54 */         sessionID = (String)command.getParameter("sessionid");
/*  55 */         log.debug("received CID_LOGOUT, sessionID:" + sessionID);
/*  56 */         return Boolean.valueOf(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */ 
/*     */       
/*     */       case 2:
/*  60 */         device = (Device)command.getParameter("device");
/*  61 */         log.debug("received CID_GET_APPLICATIONS, device:" + device);
/*  62 */         return ApplicationRegistry.getInstance(this.context).getApplications(device);
/*     */       
/*     */       case 1:
/*  65 */         log.debug("received CID_GET_DEVICES");
/*  66 */         return DeviceRegistry.getInstance().getDevices();
/*     */       
/*     */       case 4:
/*  69 */         device = (Device)command.getParameter("device");
/*  70 */         appID = (String)command.getParameter("applicationid");
/*  71 */         fileID = (String)command.getParameter("fileid");
/*  72 */         log.debug("received CID_GET_FILE, device:" + device + ", appID:" + appID + ", fileID:" + fileID);
/*     */         
/*  74 */         return ApplicationRegistry.getInstance(this.context).getDatabaseAdapter(device, appID).getFile(fileID);
/*     */       
/*     */       case 5:
/*  77 */         device = (Device)command.getParameter("device");
/*  78 */         appDesc = (String)command.getParameter("applicationdesc");
/*  79 */         lanID = (String)command.getParameter("languageid");
/*  80 */         log.debug("received CID_GET_NEWEST_VERSION, device:" + device + ", appDesc:" + appDesc + ", langID:" + lanID);
/*  81 */         return ApplicationRegistry.getInstance(this.context).getNewestVersion(device, appDesc, lanID);
/*     */       
/*     */       case 12:
/*  84 */         device = (Device)command.getParameter("device");
/*  85 */         appDesc = (String)command.getParameter("applicationdesc");
/*  86 */         versNo = (String)command.getParameter("versionno");
/*  87 */         lanID = (String)command.getParameter("languageid");
/*  88 */         log.debug("received CID_GET_NEW_VERSION, device:" + device + ", appDesc:" + appDesc + ", langID:" + lanID);
/*  89 */         return ApplicationRegistry.getInstance(this.context).getNewestVersion(device, appDesc, versNo, lanID);
/*     */       
/*     */       case 9:
/*  92 */         device = (Device)command.getParameter("device");
/*  93 */         appDesc = (String)command.getParameter("applicationdesc");
/*  94 */         versNo = (String)command.getParameter("versionno");
/*  95 */         lanID = (String)command.getParameter("languageid");
/*  96 */         log.debug("received CID_GET_VERSION, device:" + device + ", appDesc:" + appDesc + ", versNo:" + versNo + ", langID:" + lanID);
/*  97 */         return ApplicationRegistry.getInstance(this.context).getVersion(device, appDesc, versNo, lanID);
/*     */       
/*     */       case 6:
/* 100 */         troubleCodes = (Set)command.getParameter("troublecode");
/* 101 */         log.debug("received CID_SAVE_TROUBLECODE, troubleCodes:" + troubleCodes);
/* 102 */         retValue = false;
/* 103 */         if (troubleCodes != null) {
/* 104 */           Set<DTCAdapter> dtcs = new HashSet();
/* 105 */           for (Iterator<TroubleCode> iter = troubleCodes.iterator(); iter.hasNext();) {
/* 106 */             dtcs.add(new DTCAdapter(iter.next(), Tis2webUtil.getPortalID(this.context)));
/*     */           }
/*     */           try {
/* 109 */             retValue = DTCStorageServiceProvider.getInstance().getService().store(dtcs);
/* 110 */           } catch (Exception e) {}
/*     */         } 
/*     */         
/* 113 */         return new Boolean(retValue);
/*     */       
/*     */       case 10:
/* 116 */         lanID = (String)command.getParameter("languageid");
/* 117 */         log.debug("received CID_GET_OBJ_LANGUAGE, langID:" + lanID);
/* 118 */         return LanguageRegistry.getInstance().getLanguage(lanID);
/*     */       
/*     */       case 11:
/* 121 */         _deviceName = (String)command.getParameter("devicename");
/* 122 */         _appDesc = (String)command.getParameter("applicationdesc");
/* 123 */         _version = (String)command.getParameter("versionno");
/* 124 */         langID = (String)command.getParameter("languageid");
/* 125 */         _language = LanguageRegistry.getInstance().getLanguage(langID);
/* 126 */         log.debug("received CID_NOTIFY_TECH_DOWNLOAD");
/*     */         try {
/* 128 */           SWDLMetricsLogFacade.getInstance().add(new SWDLMetricsLog.Entry()
/*     */               {
/*     */                 public String getLanguage() {
/* 131 */                   return _language.getDescription(Locale.US);
/*     */                 }
/*     */                 
/*     */                 public String getVersion() {
/* 135 */                   return _version;
/*     */                 }
/*     */                 
/*     */                 public String getApplication() {
/* 139 */                   return _appDesc;
/*     */                 }
/*     */                 
/*     */                 public String getDevice() {
/* 143 */                   return _deviceName;
/*     */                 }
/*     */                 
/*     */                 public long getTimestamp() {
/* 147 */                   return System.currentTimeMillis();
/*     */                 }
/*     */                 
/*     */                 public String getUserID() {
/* 151 */                   return SWDLController.this.context.getSessionID();
/*     */                 }
/*     */               });
/*     */         }
/* 155 */         catch (Exception e) {
/* 156 */           log.warn("...unable to log download notification - exception: " + e, e);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         return null; }  log.error("received unknown command, returning null"); return null; }
/*     */ 
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\SWDLController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */