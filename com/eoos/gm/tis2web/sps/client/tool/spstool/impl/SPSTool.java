/*     */ package com.eoos.gm.tis2web.sps.client.tool.spstool.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ReprogramProgress;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.RIMParams;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ITraceInfo;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ReprogramProgressDisplay;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingStatus;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ProgrammingStatusImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class SPSTool
/*     */   implements ISPSTool
/*     */ {
/*  53 */   private static Logger log = Logger.getLogger(SPSTool.class);
/*     */   
/*     */   private static final String LIBRARY = "SPSToolBridge";
/*     */   
/*  57 */   private String instanceID = null;
/*     */   
/*     */   private boolean j2534Flag = false;
/*     */   
/*     */   private boolean reprogrammingAbortedFlag = false;
/*     */   
/*  63 */   public static Map<String, SPSTool> id2Instance = new HashMap<String, SPSTool>();
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
/*     */   private ReprogramProgress progress;
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
/*  91 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */ 
/*     */   
/*     */   private MessageCallback messageCallback;
/*     */ 
/*     */   
/*     */   private ProgrammingStatus lastError;
/*     */ 
/*     */ 
/*     */   
/*     */   private SPSTool() {
/* 102 */     this.instanceID = null;
/* 103 */     this.j2534Flag = false;
/* 104 */     this.reprogrammingAbortedFlag = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ISPSTool getInstance(String name) {
/* 112 */     return getInstance(name, null);
/*     */   }
/*     */   
/*     */   private static ISPSTool getInstance(String name, ReprogramProgress reprogramProgress) {
/* 116 */     synchronized (SPSTool.class) {
/* 117 */       ISPSTool instance = id2Instance.get(name);
/* 118 */       if (instance == null) {
/*     */         try {
/* 120 */           System.loadLibrary("SPSToolBridge");
/* 121 */           SPSTool newInstance = new SPSTool();
/* 122 */           if ((newInstance.instanceID = newInstance.nativeGetInstance(name)) != null) {
/* 123 */             id2Instance.put(newInstance.instanceID, newInstance);
/* 124 */             instance = newInstance;
/* 125 */             instance.setReprogramProgress(reprogramProgress);
/*     */           } else {
/* 127 */             newInstance = null;
/* 128 */             log.error("Invalid instance object. Instance = null");
/*     */           } 
/* 130 */         } catch (Exception e) {
/* 131 */           log.error("Can't load SPSToolBridge library. " + e);
/*     */         } 
/*     */       }
/* 134 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean discardInstance(String instanceID) {
/* 139 */     synchronized (SPSTool.class) {
/* 140 */       boolean ret = false;
/* 141 */       ISPSTool instance = id2Instance.get(instanceID);
/* 142 */       if (instance != null && 
/* 143 */         instance instanceof SPSTool) {
/* 144 */         SPSTool spsToolInstance = (SPSTool)instance;
/* 145 */         ret = spsToolInstance.nativeDiscardInstance(instanceID);
/* 146 */         id2Instance.remove(spsToolInstance.instanceID);
/* 147 */         spsToolInstance = null;
/*     */       } 
/*     */       
/* 150 */       return ret;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String initialize(String initParams) {
/* 159 */     synchronized (SPSTool.class) {
/* 160 */       if (initParams.length() > 5) {
/*     */         try {
/* 162 */           String device = initParams.substring(0, 4);
/* 163 */           if (device.compareToIgnoreCase("J2534") == 0) {
/* 164 */             this.j2534Flag = true;
/*     */           } else {
/* 166 */             this.j2534Flag = false;
/*     */           } 
/* 168 */         } catch (Exception e) {
/* 169 */           log.error("Cannot get substring from initialize string" + e);
/*     */         } 
/*     */       }
/* 172 */       return nativeInitialize(this.instanceID, initParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean installCallback() {
/* 177 */     synchronized (SPSTool.class) {
/* 178 */       log.debug("Calling native installCallback().");
/* 179 */       return nativeInstallCallback(this.instanceID);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean uninstallCallback() {
/* 184 */     synchronized (SPSTool.class) {
/* 185 */       log.debug("Calling native uninstallCallback().");
/* 186 */       return nativeUninstallCallback(this.instanceID);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Pair[] appendAdditionalProperties(Pair[] properties) {
/* 193 */     List<Pair> p = new ArrayList<Pair>(Arrays.asList(properties));
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (!ClientAppContextProvider.getClientAppContext().DTCUploadEnabled() && !ClientAppContextProvider.getClientAppContext().DTCDebugMode()) {
/* 198 */       p.add(new PairImpl("dtcupload,disabled", ISPSTool.DRIVER_PROPERTY_CATEGORY_DTCUPLOAD));
/*     */     } else {
/* 200 */       p.add(new PairImpl("dtcupload,enabled", ISPSTool.DRIVER_PROPERTY_CATEGORY_DTCUPLOAD));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 205 */     return (Pair[])p.toArray((Object[])new PairImpl[0]);
/*     */   }
/*     */   
/*     */   public boolean setToolProperties(Pair[] properties) {
/* 209 */     synchronized (SPSTool.class) {
/* 210 */       log.debug("Calling native setToolProperties()");
/* 211 */       return nativeSetToolProperties(this.instanceID, appendAdditionalProperties(properties));
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getToolProperty(String property) {
/* 216 */     synchronized (SPSTool.class) {
/* 217 */       String ret = nativeGetToolProperty(this.instanceID, property);
/* 218 */       log.debug("SPS labor time: " + ret);
/* 219 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setReqMthdProperties(RIMParams params) {
/* 224 */     synchronized (SPSTool.class) {
/* 225 */       return nativeSetReqMthdProperties(this.instanceID, params);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setReqMthdProperties(RequestMethodData[] rmd) {
/* 230 */     synchronized (SPSTool.class) {
/* 231 */       return nativeSetReqMthdProperties(this.instanceID, rmd);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Pair[] getECUData() {
/* 236 */     synchronized (SPSTool.class) {
/* 237 */       return nativeGetECUData(this.instanceID, new DTCCallback()
/*     */           {
/*     */             public void onReadDTC(byte[] dtcData) {
/* 240 */               if (ClientAppContextProvider.getClientAppContext().DTCDebugMode()) {
/* 241 */                 (new ToolUtils()).writeDebugDTC(dtcData);
/*     */               }
/* 243 */               SPSTool.log.debug("onReadDTC - delegate to DTCAutoTransfer");
/*     */               try {
/* 245 */                 DTCUploadProvider.getDTCUpload().upload(Collections.singleton(dtcData));
/* 246 */               } catch (Exception e) {
/* 247 */                 throw new RuntimeException("unable to upload dtc", e);
/*     */               } 
/*     */             }
/*     */             
/*     */             public String getBACCode() {
/* 252 */               return ClientAppContextProvider.getClientAppContext().getBACCode();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   public ProgrammingStatus reprogECU(Pair[] vit2, SPSBlob[] blobs, String hardwareKey, MessageCallback mcb) {
/*     */     ProgrammingStatus result;
/* 259 */     this.messageCallback = mcb;
/*     */     
/* 261 */     boolean nativeResult = false;
/* 262 */     synchronized (SPSTool.class) {
/* 263 */       nativeResult = nativeReprogECU(this.instanceID, vit2, blobs, hardwareKey);
/* 264 */       this.messageCallback = null;
/*     */     } 
/* 266 */     if (nativeResult == true) {
/* 267 */       ProgrammingStatusImpl programmingStatusImpl = new ProgrammingStatusImpl(true, null, null, null);
/*     */     } else {
/* 269 */       result = this.lastError;
/*     */     } 
/* 271 */     return result;
/*     */   } public static interface DTCCallback {
/*     */     String getBACCode(); void onReadDTC(byte[] param1ArrayOfbyte); }
/*     */   public boolean clearVehicleDTCs(Integer connector) {
/* 275 */     synchronized (SPSTool.class) {
/* 276 */       int conn = 0;
/* 277 */       if (connector != null) {
/* 278 */         conn = connector.intValue();
/*     */       }
/* 280 */       log.debug("Try to clear vehicle DTCs. Connector: " + conn);
/* 281 */       return nativeClearVehicleDTCs(this.instanceID, conn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onStartReprogramming(Integer count) {
/* 290 */     if (this.reprogrammingAbortedFlag == true) {
/* 291 */       log.debug("Callback: onStartProgramming - " + count + " Reprogramming aborted - " + this.reprogrammingAbortedFlag);
/*     */     } else {
/* 293 */       log.debug("Callback: onStartProgramming - " + count);
/* 294 */       if (this.progress == null) {
/* 295 */         this.progress = (ReprogramProgress)new ReprogramProgressDisplay(count.intValue());
/*     */       } else {
/*     */         
/* 298 */         this.progress.init(count.intValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onEndReprogramming() {
/* 304 */     if (this.reprogrammingAbortedFlag == true) {
/* 305 */       log.debug("Callback: onEndProgramming. Reprogramming aborted - " + this.reprogrammingAbortedFlag);
/*     */     } else {
/* 307 */       log.debug("Callback: onEndProgramming");
/* 308 */       forwardDone();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onDataTransmitted(Integer count) {
/* 313 */     if (this.reprogrammingAbortedFlag == true) {
/* 314 */       log.debug("Callback: onDataTransmitted - " + count + " Reprogramming aborted - " + this.reprogrammingAbortedFlag);
/*     */     } else {
/* 316 */       log.debug("Callback: onDataTransmitted - " + count);
/* 317 */       forwardProgress(count);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onSeeking() {
/* 322 */     if (this.reprogrammingAbortedFlag == true) {
/* 323 */       log.debug("Callback: onSeeking. Reprogramming aborted - " + this.reprogrammingAbortedFlag);
/*     */     } else {
/* 325 */       log.debug("Callback: onSeeking");
/* 326 */       forwardStatusChange("transferdataScreen.seeking");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onReprogrammingAborted(Integer error) {
/* 331 */     this.reprogrammingAbortedFlag = true;
/* 332 */     log.debug("Callback: onReprogrammingAborted - " + error);
/* 333 */     forwardStatusChange("transferdataScreen.reprogramming.aborted");
/*     */   }
/*     */   
/*     */   protected boolean onTech2Reset() {
/* 337 */     boolean ret = true;
/* 338 */     log.debug("Callback: onTech2Reset");
/* 339 */     if (this.j2534Flag == true) {
/* 340 */       ret = displayQuestionDialog("sps.spstool.callback.j2534.reset");
/*     */     } else {
/* 342 */       ret = displayQuestionDialog("sps.spstool.callback.tech2.reset");
/*     */     } 
/* 344 */     return ret;
/*     */   }
/*     */   
/*     */   protected void onStatusUpdate(Integer msgData) {
/* 348 */     log.debug("Callback: onStatusUpdate - " + msgData);
/* 349 */     if (msgData.intValue() == 25) {
/* 350 */       forwardStatusChange("transferdataScreen.verifying");
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean onSendError(Integer stepNo, Integer errorCode, ITraceInfo[] traceInfo) {
/* 355 */     boolean ret = false;
/* 356 */     log.debug("Callback: onSendError - stepNo = " + stepNo + " errorCode = " + errorCode);
/*     */     
/* 358 */     String msgSPSError = getMessage("sps.spsvcs.error.msg") + "\n\n";
/* 359 */     StringBuffer message = new StringBuffer(msgSPSError);
/*     */     
/* 361 */     if (stepNo.intValue() != 1) {
/* 362 */       boolean msgDlgWithCancel = false;
/* 363 */       int stepNumber = stepNo.intValue() & 0x8000;
/*     */       
/* 365 */       if (stepNumber != 0) {
/* 366 */         stepNumber = stepNo.intValue() & 0x7FFF;
/* 367 */         msgDlgWithCancel = true;
/*     */       } else {
/* 369 */         stepNumber = stepNo.intValue();
/*     */       } 
/*     */       
/* 372 */       int msgID = 1000 + errorCode.intValue();
/* 373 */       String msg = getMessage("sps.spsvcs.error.msg." + msgID, false);
/*     */       
/* 375 */       if (msg == null) {
/* 376 */         msg = getMessage("sps.spsvcs.error.msg.unknown") + " " + errorCode.toString();
/*     */       }
/*     */       
/* 379 */       message.append(msg);
/* 380 */       message.append(" ");
/* 381 */       message.append(getMessage("sps.spsvcs.error.msg.at.step"));
/* 382 */       message.append(" ");
/* 383 */       message.append(stepNumber);
/*     */       
/* 385 */       if (msgDlgWithCancel == true) {
/* 386 */         ret = displayQuestionHTMLMessage(message.toString(), traceInfo);
/*     */       } else {
/* 388 */         Integer value = ClientAppContextProvider.getClientAppContext().getToolAutoRetryDelay();
/* 389 */         if (value != null) {
/*     */           try {
/* 391 */             Thread.sleep((value.intValue() * 1000));
/* 392 */           } catch (InterruptedException e) {
/* 393 */             log.debug("Exception received: " + e);
/*     */           } 
/*     */         } else {
/* 396 */           displayHTMLMessage(message.toString(), traceInfo);
/*     */         } 
/* 398 */         ret = true;
/*     */       } 
/*     */     } else {
/* 401 */       message.append(getMessage("sps.spsvcs.error.msg.std.hint"));
/* 402 */       displayHTMLMessage(message.toString(), traceInfo);
/* 403 */       ret = true;
/*     */     } 
/* 405 */     if (!ret) {
/* 406 */       this.lastError = (ProgrammingStatus)new ProgrammingStatusImpl(false, stepNo, errorCode, message.toString() + "[traceInfo: " + formatTraceInfo(traceInfo) + "]");
/* 407 */       this.reprogrammingAbortedFlag = true;
/*     */     } 
/* 409 */     return ret; } protected boolean onDispError(Integer success, Integer flags, String[] strings) {
/*     */     boolean msgDlgWithCancel;
/*     */     int minutes, stepNo, deviceID, msgID;
/*     */     String msg;
/* 413 */     boolean ret = false;
/* 414 */     log.debug("Callback: onDispError - success = " + success + " flags = " + flags + " strings = " + strings);
/*     */     
/* 416 */     StringBuffer message = new StringBuffer();
/*     */     
/* 418 */     switch (success.intValue()) {
/*     */       case 5:
/* 420 */         message.append(getMessage("sps.spsvcs.error.msg.no.cal.file"));
/* 421 */         message.append(" ");
/* 422 */         if (strings.length > 0) {
/* 423 */           message.append(strings[1]);
/*     */         }
/* 425 */         displayMessageDialogMsg(message.toString());
/*     */         break;
/*     */       
/*     */       case 6:
/* 429 */         message.append(getMessage("sps.spsvcs.error.msg.no.vtd.support"));
/* 430 */         displayMessageDialogMsg(message.toString());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 435 */         msgDlgWithCancel = ((flags.intValue() & 0x8000) != 0);
/* 436 */         stepNo = flags.intValue() & 0x7FFF;
/* 437 */         deviceID = 0;
/*     */         
/* 439 */         if (strings.length > 0) {
/*     */           try {
/* 441 */             deviceID = Integer.parseInt(strings[0]);
/* 442 */           } catch (Exception e) {
/* 443 */             log.error("Error while parse string: " + strings[0]);
/*     */           } 
/*     */         } else {
/* 446 */           log.error("Invalid argument - string array is empty.");
/*     */         } 
/*     */         
/* 449 */         msgID = 5000 + deviceID;
/* 450 */         msg = getMessage("sps.spsvcs.error.msg." + msgID, false);
/*     */         
/* 452 */         if (msg == null) {
/* 453 */           msg = getMessage("sps.spsvcs.error.msg.dvcid.unknown");
/*     */         }
/*     */         
/* 456 */         message.append(getMessage("sps.spsvcs.error.msg"));
/* 457 */         message.append("\n");
/* 458 */         message.append(getMessage("sps.spsvcs.error.msg.device.error.1"));
/* 459 */         message.append(" ");
/* 460 */         message.append("'");
/* 461 */         message.append(msg);
/* 462 */         message.append("'");
/* 463 */         message.append(" ( ");
/* 464 */         message.append(deviceID);
/* 465 */         message.append(" ) ");
/* 466 */         message.append(getMessage("sps.spsvcs.error.msg.device.error.2"));
/* 467 */         message.append(" ");
/* 468 */         message.append(getMessage("sps.spsvcs.error.msg.at.step"));
/* 469 */         message.append(" ");
/* 470 */         message.append(stepNo);
/* 471 */         message.append("\n");
/* 472 */         message.append(getMessage("sps.spsvcs.error.msg.rmv.device"));
/*     */         
/* 474 */         if (msgDlgWithCancel == true) {
/* 475 */           ret = displayQuestionDialogMsg(message.toString()); break;
/*     */         } 
/* 477 */         displayMessageDialogMsg(message.toString());
/* 478 */         ret = true;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 485 */         message.append(getMessage("sps.spsvcs.msg.please.wait"));
/* 486 */         message.append("\n");
/* 487 */         message.append(getMessage("sps.spsvcs.msg.minutes.remaining"));
/* 488 */         displayInformationText(message.toString());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 494 */         minutes = -1;
/* 495 */         if (strings != null && strings.length > 0) {
/* 496 */           minutes = Integer.parseInt(strings[0]);
/*     */         } else {
/* 498 */           log.error("Error while parse string: " + strings[0]);
/*     */         } 
/* 500 */         if (minutes == 0) {
/* 501 */           destroyInformationText(); break;
/*     */         } 
/* 503 */         minutes /= 60000;
/* 504 */         message.append(getMessage("sps.spsvcs.msg.please.wait"));
/* 505 */         message.append("\n");
/* 506 */         message.append(minutes);
/* 507 */         message.append(" ");
/* 508 */         message.append(getMessage("sps.spsvcs.msg.minutes.remaining"));
/* 509 */         updateInformationText(message.toString());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 515 */         message.append(getMessage("sps.spsvcs.error.msg.no.cal.file"));
/* 516 */         message.append(" ");
/* 517 */         if (strings.length > 0) {
/* 518 */           message.append(strings[1]);
/*     */         }
/* 520 */         displayMessageDialogMsg(message.toString());
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 527 */     return ret;
/*     */   }
/*     */   
/*     */   public void setReprogramProgress(ReprogramProgress reprogramProgress) {
/* 531 */     synchronized (SPSTool.class) {
/* 532 */       this.progress = reprogramProgress;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void forwardProgress(Integer count) {
/* 542 */     if (this.progress != null) {
/* 543 */       this.progress.progress(count.longValue());
/*     */     } else {
/* 545 */       log.error("Reference to progress callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void forwardDone() {
/* 550 */     if (this.progress != null) {
/* 551 */       this.progress.done();
/*     */     } else {
/* 553 */       log.error("Reference to progress callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void forwardStatusChange(String labelKey) {
/* 558 */     if (this.progress != null) {
/* 559 */       this.progress.onStatusChange(getLabel(labelKey));
/*     */     } else {
/* 561 */       log.error("Reference to progress callback is NULL");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLabel(String labelKey) {
/* 570 */     String label = labelKey;
/* 571 */     if (resourceProvider != null) {
/* 572 */       label = resourceProvider.getLabel(null, labelKey);
/*     */     }
/* 574 */     return label;
/*     */   }
/*     */   
/*     */   private String getMessage(String messageKey) {
/* 578 */     String message = messageKey;
/* 579 */     if (resourceProvider != null) {
/* 580 */       message = resourceProvider.getMessage(null, messageKey);
/*     */     }
/* 582 */     return message;
/*     */   }
/*     */   
/*     */   private String getMessage(String messageKey, boolean allowReturnKey) {
/* 586 */     String message = null;
/* 587 */     if (resourceProvider != null) {
/* 588 */       String buffer = getMessage(messageKey);
/* 589 */       if (allowReturnKey == true) {
/* 590 */         message = buffer;
/*     */       }
/* 592 */       else if (messageKey.compareTo(buffer) == 0) {
/* 593 */         message = null;
/*     */       } else {
/* 595 */         message = buffer;
/*     */       } 
/*     */     } 
/*     */     
/* 599 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void displayMessageDialogMsg(String message) {
/* 608 */     if (this.messageCallback != null) {
/* 609 */       this.messageCallback.displayMessageDialogMsg(message);
/*     */     } else {
/* 611 */       log.error("Reference to message callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void displayHTMLMessage(String message, ITraceInfo[] traceInfo) {
/* 616 */     if (this.messageCallback != null) {
/* 617 */       if (traceInfo != null) {
/* 618 */         this.messageCallback.displayHTMLMessage(message, Arrays.asList(traceInfo));
/*     */       } else {
/* 620 */         this.messageCallback.displayHTMLMessage(message, null);
/*     */       } 
/*     */     } else {
/* 623 */       log.error("Reference to message callback is NULL");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean displayQuestionDialog(String messageKey) {
/* 632 */     boolean ret = false;
/* 633 */     if (this.messageCallback != null) {
/* 634 */       ret = this.messageCallback.displayQuestionDialog(messageKey);
/*     */     } else {
/* 636 */       log.error("Reference to message callback is NULL");
/*     */     } 
/* 638 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean displayQuestionDialogMsg(String message) {
/* 642 */     boolean ret = false;
/* 643 */     if (this.messageCallback != null) {
/* 644 */       ret = this.messageCallback.displayQuestionDialogMsg(message.toString());
/*     */     } else {
/* 646 */       log.error("Reference to message callback is NULL");
/*     */     } 
/* 648 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean displayQuestionHTMLMessage(String message, ITraceInfo[] traceInfo) {
/* 652 */     boolean ret = false;
/* 653 */     if (this.messageCallback != null) {
/* 654 */       if (traceInfo != null) {
/* 655 */         ret = this.messageCallback.displayQuestionHTMLMessage(message, Arrays.asList(traceInfo));
/*     */       } else {
/* 657 */         ret = this.messageCallback.displayQuestionHTMLMessage(message, null);
/*     */       } 
/*     */     } else {
/* 660 */       log.error("Reference to message callback is NULL");
/*     */     } 
/* 662 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void displayInformationText(String message) {
/* 670 */     if (this.messageCallback != null) {
/* 671 */       this.messageCallback.displayInformationText(message);
/*     */     } else {
/* 673 */       log.error("Reference to message callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateInformationText(String message) {
/* 678 */     if (this.messageCallback != null) {
/* 679 */       this.messageCallback.updateInformationText(message);
/*     */     } else {
/* 681 */       log.error("Reference to message callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroyInformationText() {
/* 686 */     if (this.messageCallback != null) {
/* 687 */       this.messageCallback.destroyInformationText();
/*     */     } else {
/* 689 */       log.error("Reference to message callback is NULL");
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatTraceInfo(ITraceInfo[] traceInfo) {
/* 694 */     StringBuffer result = new StringBuffer();
/* 695 */     if (traceInfo != null) {
/*     */       try {
/* 697 */         for (int i = 0; i < traceInfo.length; i++) {
/* 698 */           if (i > 0) {
/* 699 */             result.append(", ");
/*     */           }
/* 701 */           result.append('(');
/* 702 */           result.append(Integer.toHexString(traceInfo[i].getStep().intValue()) + ", ");
/* 703 */           result.append(Integer.toHexString(traceInfo[i].getOperationCode().intValue()) + ", ");
/* 704 */           result.append(Integer.toHexString(traceInfo[i].getResponseCode().intValue()) + ")");
/*     */         } 
/* 706 */       } catch (Exception e) {
/* 707 */         result.append(" ...exception in evaluating traceInfo.");
/*     */       } 
/*     */     }
/* 710 */     return result.toString();
/*     */   }
/*     */   
/*     */   private native String nativeGetInstance(String paramString);
/*     */   
/*     */   private native boolean nativeDiscardInstance(String paramString);
/*     */   
/*     */   private native String nativeInitialize(String paramString1, String paramString2);
/*     */   
/*     */   private native boolean nativeInstallCallback(String paramString);
/*     */   
/*     */   private native boolean nativeUninstallCallback(String paramString);
/*     */   
/*     */   private native boolean nativeSetToolProperties(String paramString, Pair[] paramArrayOfPair);
/*     */   
/*     */   private native String nativeGetToolProperty(String paramString1, String paramString2);
/*     */   
/*     */   private native boolean nativeSetReqMthdProperties(String paramString, RIMParams paramRIMParams);
/*     */   
/*     */   private native boolean nativeSetReqMthdProperties(String paramString, RequestMethodData[] paramArrayOfRequestMethodData);
/*     */   
/*     */   private native Pair[] nativeGetECUData(String paramString, DTCCallback paramDTCCallback);
/*     */   
/*     */   private native boolean nativeReprogECU(String paramString1, Pair[] paramArrayOfPair, SPSBlob[] paramArrayOfSPSBlob, String paramString2);
/*     */   
/*     */   private native boolean nativeClearVehicleDTCs(String paramString, int paramInt);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\spstool\impl\SPSTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */