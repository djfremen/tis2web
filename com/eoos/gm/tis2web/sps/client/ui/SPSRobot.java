/*     */ package com.eoos.gm.tis2web.sps.client.ui;
/*     */ 
/*     */ import com.eoos.datatype.Denotation;
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ExecutionMode;
/*     */ import com.eoos.gm.tis2web.sps.client.system.SnmpSupport;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.TestDriverImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.SummaryLog;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.IOUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ExceptionImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.table.DefaultTableModel;
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
/*     */ public class SPSRobot
/*     */   implements UIRequestHandler
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger(SPSRobot.class);
/*     */   
/*     */   private SPSClientController controller;
/*     */   
/*     */   private TestDriverImpl testdriver;
/*     */   
/*     */   private IUIAgent ui;
/*     */   
/*     */   private boolean downloadComplete;
/*     */   
/*     */   private Value lastVIN;
/*     */   
/*     */   private VIT1 vit1;
/*     */   
/*     */   private Integer device;
/*     */   
/*     */   private DefaultTableModel summary;
/*     */   
/*     */   private String missingInputIndicator;
/*     */   
/*     */   private long startTimeVIT1;
/*     */   
/*     */   private static boolean snmp = true;
/*     */   
/*     */   private int noFiles;
/*     */   
/*     */   private int noRepr;
/*     */   
/*     */   private int noErr;
/*     */   
/*     */   private int noSkipped;
/*     */   
/*     */   private String vit1FileName;
/* 101 */   private SummaryLog summaryLog = null;
/*     */   
/*     */   private boolean vinRead = false;
/*     */   
/*     */   public SPSRobot(SPSClientController controller, TestDriverImpl testdriver, IUIAgent ui) {
/* 106 */     this.controller = controller;
/* 107 */     this.testdriver = testdriver;
/* 108 */     this.ui = ui;
/* 109 */     this.summary = new DefaultTableModel((Object[])new String[] { "VIT-ID", "Status" }, 0);
/* 110 */     this.summary.setColumnCount(2);
/* 111 */     ui.showTestSummary(this.summary);
/* 112 */     if (!ExecutionMode.isLoadTest()) {
/* 113 */       sendStartEventNotificationSNMP();
/*     */     }
/*     */   }
/*     */   
/*     */   private SummaryLog getSummaryLog() {
/* 118 */     if (this.summaryLog == null) {
/*     */       try {
/* 120 */         this.summaryLog = new SummaryLog();
/* 121 */         log.debug("SummaryLog opened:");
/* 122 */         track3("Start", getTime());
/* 123 */         track3("Locale", ClientAppContextProvider.getClientAppContext().getLocale().toString());
/* 124 */         delim();
/*     */       }
/* 126 */       catch (IOException e) {
/* 127 */         log.debug("SummaryLog open error");
/* 128 */         log.error(e);
/*     */       } 
/*     */     }
/* 131 */     return this.summaryLog;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 136 */     this.noFiles = 0;
/* 137 */     this.noRepr = 0;
/* 138 */     this.noErr = 0;
/* 139 */     this.noSkipped = 0;
/*     */   }
/*     */   
/*     */   protected static String getTime() {
/* 143 */     SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss MM/dd/yy");
/* 144 */     return formater.format(new Timestamp(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVIT1(VIT1 vit1, Integer device) {
/* 149 */     this.vit1 = vit1;
/* 150 */     this.device = device;
/* 151 */     String name = this.testdriver.getVIT1FileName();
/* 152 */     if (!name.equals(this.vit1FileName)) {
/* 153 */       track3("File", this.testdriver.getCurrDir() + "\\" + this.testdriver.getVIT1FileName());
/* 154 */       this.noFiles++;
/* 155 */       this.vit1FileName = name;
/* 156 */       this.startTimeVIT1 = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void notify(Attribute attribute) {
/* 161 */     if (attribute.equals(CommonAttribute.FINISH)) {
/* 162 */       if (this.missingInputIndicator == null) {
/* 163 */         trackVitRes("REPROGRAMMED");
/* 164 */         this.noRepr++;
/* 165 */         sendEventNotificationSNMP(true);
/*     */       } else {
/*     */         
/* 168 */         trackVitRes("SKIPPED");
/* 169 */         this.noSkipped++;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void end() {
/* 175 */     onFinished();
/*     */     
/* 177 */     this.ui.showTestSummary(null);
/*     */     try {
/* 179 */       getSummaryLog().close();
/* 180 */       log.debug("SummaryLog closed");
/* 181 */     } catch (IOException ex) {
/* 182 */       log.error(ex);
/* 183 */       log.debug("SummaryLog close error");
/*     */     } 
/*     */     
/* 186 */     if (ExecutionMode.isLoadTest()) {
/* 187 */       File endIndication = new File(System.getProperty("file.end"));
/*     */       try {
/* 189 */         endIndication.createNewFile();
/* 190 */       } catch (IOException e) {
/* 191 */         log.error("exception: " + e, e);
/*     */       } 
/*     */ 
/*     */       
/* 195 */       System.exit(0);
/*     */     }
/*     */     else {
/*     */       
/* 199 */       sendShutdownEventNotificationSNMP();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 204 */   private static final Object SYNC_LOADTESTDATA = new Object();
/*     */ 
/*     */   
/*     */   private static class LoadTestData
/*     */   {
/*     */     public long delayMin;
/*     */     
/*     */     public long delayMax;
/*     */ 
/*     */     
/*     */     private LoadTestData() {}
/*     */   }
/* 216 */   private static LoadTestData testData = null;
/*     */   
/*     */   private LoadTestData getLoadTestData() {
/* 219 */     synchronized (SYNC_LOADTESTDATA) {
/* 220 */       if (testData == null) {
/* 221 */         testData = new LoadTestData();
/*     */         try {
/* 223 */           testData.delayMin = Long.parseLong(System.getProperty("request.handling.delay.min"));
/* 224 */           testData.delayMax = Long.parseLong(System.getProperty("request.handling.delay.max"));
/* 225 */         } catch (Exception e) {
/* 226 */           log.warn("unable to read property for 'delayed request handling', using no delay");
/* 227 */           testData.delayMax = 0L;
/* 228 */           testData.delayMin = 0L;
/*     */         } 
/*     */       } 
/* 231 */       return testData;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handle(Exception e) {
/* 236 */     if (ExecutionMode.isLoadTest()) {
/* 237 */       LoadTestData testData = getLoadTestData();
/*     */       try {
/* 239 */         Util.sleepRandom(testData.delayMin, testData.delayMax);
/* 240 */       } catch (InterruptedException e1) {
/* 241 */         throw new RuntimeException(e1);
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     if (e instanceof RequestException) {
/* 246 */       Request request = ((RequestException)e).getRequest();
/* 247 */       this.controller.execute(request);
/* 248 */     } else if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoValidVIT1Exception || e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoMoreFilesException) {
/* 249 */       end();
/* 250 */     } else if (e instanceof com.eoos.gm.tis2web.frame.export.common.InvalidSessionException) {
/*     */       
/* 252 */       track2("Test Stopped", "FATAL ERROR: Invalid Session");
/* 253 */       end();
/*     */     
/*     */     }
/* 256 */     else if (e instanceof ExceptionWrapper && ((ExceptionWrapper)e).getWrappedException() != null && ((ExceptionWrapper)e).getWrappedException() instanceof IOException) {
/* 257 */       track2("Test Stopped", "FATAL ERROR: " + ((ExceptionWrapper)e).getWrappedException().toString());
/* 258 */       end();
/*     */     } else {
/*     */       
/* 261 */       String msg = null;
/* 262 */       if (e instanceof SPSException) {
/* 263 */         String id = ((SPSException)e).getMessage();
/* 264 */         msg = ExceptionImpl.getInstance(id).getDenotation(null);
/* 265 */         if (((SPSException)e).getMessage().equals(CommonException.InvalidHardware.getID())) {
/* 266 */           msg = msg + '\n' + getHardwareInfo();
/* 267 */         } else if (((SPSException)e).getMessage().equals(CommonException.UnknownHardware.getID())) {
/* 268 */           msg = msg + '\n' + getHardwareInfo();
/*     */         } 
/*     */       } else {
/* 271 */         msg = e.getMessage();
/*     */       } 
/* 273 */       this.testdriver.writeExceptionLog(msg);
/* 274 */       this.testdriver.writeVIT1();
/* 275 */       if (!this.vinRead) {
/*     */         try {
/* 277 */           IOUtil.getNextVIT1File(null);
/* 278 */         } catch (Exception e1) {
/* 279 */           if (e1 instanceof NullPointerException) {
/*     */             try {
/* 281 */               this.testdriver.getVIN(null, null);
/* 282 */             } catch (Exception e2) {
/* 283 */               end();
/*     */               return;
/*     */             } 
/*     */           } else {
/* 287 */             end();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/* 293 */       if (this.missingInputIndicator == null) {
/*     */         
/* 295 */         trackVitRes("ERROR");
/* 296 */         this.noErr++;
/* 297 */         sendEventNotificationSNMP(false);
/*     */       } else {
/*     */         
/* 300 */         trackVitRes("SKIPPED");
/* 301 */         this.noSkipped++;
/*     */       } 
/* 303 */       Thread thread = new Thread() {
/*     */           public void run() {
/* 305 */             SPSRobot.this.controller.start();
/*     */           }
/*     */         };
/* 308 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFinished() {
/* 314 */     delim();
/* 315 */     track3("End", getTime());
/* 316 */     track2("Files", Integer.toString(this.noFiles));
/* 317 */     track2("REPROGRAMMED", Integer.toString(this.noRepr));
/* 318 */     track2("ERROR", Integer.toString(this.noErr));
/* 319 */     track2("SKIPPED", Integer.toString(this.noSkipped));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(Request request, AttributeValueMapExt data) {
/* 325 */     if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingDataDownloadContinuationRequest) {
/* 326 */       checkDownloadCompletion(data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(AssignmentRequest request, AttributeValueMapExt data) {
/* 333 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/* 334 */       setTestDriverDevice(data);
/* 335 */       this.missingInputIndicator = null; return;
/*     */     } 
/* 337 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest) {
/* 338 */       handleSelectionRequest(data, (SelectionRequest)request);
/* 339 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) {
/* 340 */       this.downloadComplete = false;
/* 341 */       handleSelectionRequest(data, (SelectionRequest)request);
/* 342 */     } else if (request instanceof SelectionRequest) {
/* 343 */       handleSelectionRequest(data, (SelectionRequest)request);
/* 344 */     } else if (!(request instanceof com.eoos.gm.tis2web.sps.common.ProgrammingDataDownloadRequest)) {
/*     */       
/* 346 */       if (request instanceof DownloadProgressDisplayRequest) {
/* 347 */         handleProgrammingDataDownload(data, (DownloadProgressDisplayRequest)request);
/* 348 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest) {
/* 349 */         data.set(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY, CommonValue.OK);
/* 350 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest) {
/* 351 */         if (request instanceof VINDisplayRequest) {
/* 352 */           handleVINDisplayRequest(data, (VINDisplayRequest)request);
/* 353 */           this.ui.setStatusBar(this.testdriver.getVITID());
/*     */         } else {
/* 355 */           throw new IllegalArgumentException();
/*     */         } 
/* 357 */       } else if (request instanceof DisplaySummaryRequest) {
/* 358 */         Summary summary = ((DisplaySummaryRequest)request).getSummary();
/* 359 */         if (summary != null) {
/* 360 */           List<History> digest = new ArrayList();
/* 361 */           digest.add(summary.getCurrentSoftware());
/* 362 */           digest.add(summary.getSelectedSoftware());
/* 363 */           data.set(CommonAttribute.SUMMARY_DIGEST, (Value)new ListValueImpl(digest));
/*     */         } 
/* 365 */         updateValue(data, CommonAttribute.SUMMARY, CommonValue.OK);
/* 366 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest) {
/* 367 */         updateValue(data, request.getAttribute(), CommonValue.OK);
/* 368 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest) {
/* 369 */         updateValue(data, request.getAttribute(), CommonValue.OK);
/*     */       } else {
/* 371 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     } 
/* 374 */     Thread thread = new Thread() {
/*     */         public void run() {
/* 376 */           SPSRobot.this.controller.triggerNextRequest();
/*     */         }
/*     */       };
/* 379 */     thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Part traverseCOP(Part part, List<COP> cop) {
/* 384 */     if (cop == null) {
/* 385 */       return part;
/*     */     }
/* 387 */     COP link = cop.get(0);
/* 388 */     if (link.getMode() == 1) {
/* 389 */       part = link.getPart();
/*     */     }
/* 391 */     return traverseCOP(part, link.getPart().getCOP());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleProgrammingDataSelection(AttributeValueMapExt data, SelectionRequest request) {
/* 396 */     ProgrammingDataSelectionRequest pdsRequest = (ProgrammingDataSelectionRequest)request;
/* 397 */     List<Part> selections = new ArrayList();
/* 398 */     if (pdsRequest.getProgrammingSequence() != null) {
/* 399 */       throw new IllegalArgumentException("Programming Sequences not supported by SPS Automatic Mode");
/*     */     }
/* 401 */     List<Module> modules = ((ProgrammingDataSelectionRequest)request).getOptions();
/* 402 */     for (int i = 0; i < modules.size(); i++) {
/* 403 */       Module module = modules.get(i);
/* 404 */       Part part = module.getSelectedPart();
/* 405 */       if (part == null) {
/* 406 */         part = module.getOriginPart();
/* 407 */         List cop = part.getCOP();
/* 408 */         part = traverseCOP(part, cop);
/*     */       } 
/* 410 */       selections.add(part);
/*     */     } 
/*     */     
/* 413 */     data.set(CommonAttribute.PROGRAMMING_DATA_SELECTION, (Value)new ListValueImpl(selections));
/*     */   }
/*     */   
/*     */   protected void handleSelectionRequest(AttributeValueMapExt data, SelectionRequest request) {
/* 417 */     if (request instanceof ProgrammingDataSelectionRequest) {
/* 418 */       handleProgrammingDataSelection(data, request);
/*     */       return;
/*     */     } 
/* 421 */     Attribute attribute = request.getAttribute();
/* 422 */     List<Value> options = request.getOptions();
/* 423 */     if (options.size() == 1) {
/* 424 */       updateValue(data, attribute, options.get(0));
/*     */     } else {
/* 426 */       Value selection = null;
/* 427 */       String label = null;
/* 428 */       if (attribute.equals(CommonAttribute.CONTROLLER)) {
/* 429 */         label = "System";
/* 430 */       } else if (attribute.equals(CommonAttribute.SEQUENCE)) {
/* 431 */         label = "Sequence";
/* 432 */       } else if (attribute.equals(CommonAttribute.FUNCTION)) {
/* 433 */         label = "Function";
/* 434 */       } else if (attribute instanceof Denotation) {
/* 435 */         label = ((Denotation)attribute).getDenotation(null);
/*     */       } 
/* 437 */       String value = null;
/* 438 */       if (label != null) {
/* 439 */         value = getOption(label);
/*     */       }
/* 441 */       if (value == null && ("engine".equalsIgnoreCase(label) || "motor".equalsIgnoreCase(label))) {
/* 442 */         value = checkSNOET(options);
/*     */       }
/* 444 */       if (value != null) {
/* 445 */         for (int i = 0; i < options.size(); i++) {
/* 446 */           Value option = options.get(i);
/* 447 */           if (option instanceof Denotation) {
/* 448 */             String display = ((Denotation)option).getDenotation(null);
/* 449 */             if (display.equalsIgnoreCase(value)) {
/* 450 */               selection = option;
/*     */               break;
/*     */             } 
/* 453 */           } else if (option instanceof ValueAdapter) {
/* 454 */             String display = ((ValueAdapter)option).getAdaptee().toString();
/* 455 */             if (display.equalsIgnoreCase(value)) {
/* 456 */               selection = option;
/*     */               break;
/*     */             } 
/* 459 */           } else if (option instanceof ControllerReference) {
/* 460 */             String display = ((ControllerReference)option).toString();
/* 461 */             if (display.equalsIgnoreCase(value)) {
/* 462 */               selection = option;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 467 */       } else if (request instanceof DefaultValueRetrieval) {
/* 468 */         selection = ((DefaultValueRetrieval)request).getDefaultValue();
/* 469 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest) {
/* 470 */         selection = options.get(0);
/* 471 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.TypeSelectionRequest) {
/* 472 */         selection = options.get(0);
/* 473 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) {
/* 474 */         selection = options.get(0);
/*     */       } 
/* 476 */       if (selection != null) {
/* 477 */         updateValue(data, attribute, selection);
/*     */       } else {
/* 479 */         for (int i = 0; i < options.size(); i++) {
/* 480 */           this.testdriver.writeExceptionLog("option=" + options.get(i));
/*     */         }
/* 482 */         this.testdriver.writeExceptionLog("no valid selection found for '" + label + "'");
/* 483 */         this.testdriver.writeExceptionLog("vit1: " + this.testdriver.getVITID());
/*     */ 
/*     */ 
/*     */         
/* 487 */         updateValue(data, attribute, options.get(0));
/* 488 */         if (this.missingInputIndicator == null) {
/* 489 */           this.missingInputIndicator = "no valid selection found for '" + label + "'";
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void checkDownloadCompletion(AttributeValueMapExt data) {
/* 496 */     if (this.downloadComplete) {
/* 497 */       data.set(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED, CommonValue.OK);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void handleProgrammingDataDownload(AttributeValueMapExt data, DownloadProgressDisplayRequest request) {
/* 502 */     if (this.downloadComplete) {
/*     */       return;
/*     */     }
/* 505 */     request.addObserver(new DownloadProgressDisplayRequest.Observer()
/*     */         {
/*     */           public synchronized void onRead(List blobs, ProgrammingDataUnit dataUnit, long byteCount) {}
/*     */           
/*     */           public synchronized void onFinished(Status status) {
/* 510 */             if (status != null) {
/* 511 */               if (status.equals(FINISHED)) {
/* 512 */                 SPSRobot.log.info("Download completed: Status - FINISHED");
/* 513 */               } else if (status.equals(ERROR)) {
/* 514 */                 SPSRobot.log.error("Download completed: Status - ERROR");
/*     */               } else {
/* 516 */                 SPSRobot.log.error("Download completed: Status - UNKNOWN");
/*     */               } 
/*     */             } else {
/* 519 */               SPSRobot.log.error("Download completed: Status - NULL");
/*     */             } 
/* 521 */             SPSRobot.this.downloadComplete = true;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected String checkSNOET(List<Value> options) {
/* 527 */     if (this.vit1 == null) {
/* 528 */       return null;
/*     */     }
/* 530 */     String snoet = null;
/* 531 */     if (this.device != null) {
/* 532 */       VIT vit = this.vit1.getControlModuleBlock(this.device);
/* 533 */       snoet = vit.getAttrValue("snoet");
/*     */     } else {
/* 535 */       List<VIT> cmbs = this.vit1.getControlModuleBlocks();
/* 536 */       if (cmbs == null) {
/* 537 */         return null;
/*     */       }
/* 539 */       for (int i = 0; i < cmbs.size(); i++) {
/* 540 */         VIT vit = cmbs.get(i);
/* 541 */         snoet = vit.getAttrValue("snoet");
/* 542 */         if (snoet != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 547 */     if (snoet != null) {
/* 548 */       if (snoet.indexOf(' ') >= 0) {
/* 549 */         snoet = snoet.substring(0, snoet.indexOf(' '));
/*     */       }
/* 551 */       for (int i = 0; i < options.size(); i++) {
/* 552 */         Value option = options.get(i);
/* 553 */         if (option instanceof Denotation) {
/* 554 */           String value = ((Denotation)option).getDenotation(null);
/* 555 */           if (snoet.equalsIgnoreCase(normalize(value))) {
/* 556 */             return value;
/*     */           }
/* 558 */         } else if (option instanceof ValueAdapter) {
/* 559 */           String value = ((ValueAdapter)option).getAdaptee().toString();
/* 560 */           if (snoet.equalsIgnoreCase(normalize(value))) {
/* 561 */             return value;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 566 */     return null;
/*     */   }
/*     */   
/*     */   protected String normalize(String value) {
/* 570 */     if (value == null) {
/* 571 */       return null;
/*     */     }
/* 573 */     StringBuffer buffer = new StringBuffer();
/* 574 */     for (int i = 0; i < value.length(); i++) {
/* 575 */       char c = value.charAt(i);
/* 576 */       if (c != ' ') {
/* 577 */         buffer.append(c);
/*     */       }
/*     */     } 
/* 580 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   protected boolean isValidVIN(Value vin) {
/* 584 */     if (vin != null && vin instanceof ValueAdapter) {
/* 585 */       return (((ValueAdapter)vin).getAdaptee().toString().length() > 0);
/*     */     }
/* 587 */     return false;
/*     */   }
/*     */   
/*     */   protected void handleVINDisplayRequest(AttributeValueMapExt data, VINDisplayRequest request) {
/* 591 */     Value vin = request.getDefaultValue();
/* 592 */     if (isValidVIN(vin)) {
/* 593 */       updateValue(data, CommonAttribute.VIN, vin);
/* 594 */       this.lastVIN = vin;
/*     */     } else {
/* 596 */       String option = getOption("VIN");
/* 597 */       if (vin != null) {
/* 598 */         updateValue(data, CommonAttribute.VIN, (Value)new ValueAdapter(option));
/* 599 */       } else if (this.lastVIN != null) {
/* 600 */         updateValue(data, CommonAttribute.VIN, this.lastVIN);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setTestDriverDevice(AttributeValueMapExt data) {
/* 606 */     updateValue(data, CommonAttribute.DEVICE, (Value)new ValueAdapter(this.testdriver.getId()));
/*     */   }
/*     */   
/*     */   protected void updateValue(AttributeValueMapExt data, Attribute attribute, Value value) {
/* 610 */     Value current = data.getValue(attribute);
/* 611 */     if (current != null) {
/* 612 */       if (!current.equals(value)) {
/* 613 */         data.set(attribute, value);
/*     */       }
/* 615 */     } else if (value != null) {
/* 616 */       data.set(attribute, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getHardwareInfo() {
/*     */     try {
/* 622 */       String hardware = null;
/* 623 */       if (this.device != null) {
/* 624 */         VIT vit = this.vit1.getControlModuleBlock(this.device);
/* 625 */         hardware = vit.getAttrValue("ssecuhn");
/*     */       } else {
/* 627 */         List<VIT> cmbs = this.vit1.getControlModuleBlocks();
/* 628 */         if (cmbs == null) {
/* 629 */           return null;
/*     */         }
/* 631 */         for (int i = 0; i < cmbs.size(); i++) {
/* 632 */           VIT vit = cmbs.get(i);
/* 633 */           hardware = vit.getAttrValue("ssecuhn");
/* 634 */           if (hardware != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 639 */       return SPSClientController.resourceProvider.getLabel(null, "summaryScreen.hardware-number") + " " + hardware;
/* 640 */     } catch (Exception x) {
/*     */       
/* 642 */       return "";
/*     */     } 
/*     */   }
/*     */   protected String getOption(String attribute) {
/* 646 */     if (this.vit1 == null) {
/* 647 */       return null;
/*     */     }
/* 649 */     VIT vit = this.vit1.getFreeOptions();
/* 650 */     return (vit == null) ? null : vit.getAttrValue(attribute);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void trackVitRes(String status) {
/* 655 */     track(status);
/*     */     try {
/* 657 */       SummaryLog sm = getSummaryLog();
/* 658 */       if (sm != null) {
/* 659 */         sm.flush();
/* 660 */         log.debug("SummaryLog flushed");
/*     */       } 
/* 662 */     } catch (IOException e) {
/* 663 */       log.debug("Error flushing SummaryLog");
/* 664 */       log.error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void track(String status) {
/* 669 */     String id = this.testdriver.getVITID();
/* 670 */     id = id.substring(id.lastIndexOf(':') + 1);
/* 671 */     track2(id, status);
/*     */   }
/*     */   
/*     */   protected void track3(String id, String status) {
/* 675 */     track2(id, "\"" + status + "\"");
/*     */   }
/*     */   
/*     */   protected void track2(String id, String status) {
/* 679 */     Vector<String> record = new Vector();
/* 680 */     record.add(id);
/* 681 */     record.add(status);
/* 682 */     writeRecord(record);
/* 683 */     this.summary.addRow(record);
/* 684 */     this.summary.fireTableRowsUpdated(this.summary.getRowCount(), this.summary.getRowCount());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void delim() {
/* 689 */     Vector<String> record = new Vector();
/* 690 */     record.add("**************************************");
/* 691 */     writeRecord(record);
/* 692 */     record.add("");
/* 693 */     this.summary.addRow(record);
/* 694 */     this.summary.fireTableRowsUpdated(this.summary.getRowCount(), this.summary.getRowCount());
/*     */   }
/*     */   
/*     */   private void writeRecord(Vector record) {
/* 698 */     SummaryLog sm = getSummaryLog();
/* 699 */     if (sm != null) {
/* 700 */       sm.writeRecord(record);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendStartEventNotificationSNMP() {
/*     */     try {
/* 707 */       if (snmp) {
/* 708 */         SnmpSupport.getSnmpService().sendStartTrap();
/*     */       }
/* 710 */     } catch (Exception e) {
/* 711 */       snmp = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendShutdownEventNotificationSNMP() {
/*     */     try {
/* 717 */       if (snmp) {
/* 718 */         SnmpSupport.getSnmpService().sendShutdownTrap();
/*     */       }
/* 720 */     } catch (Exception e) {
/* 721 */       snmp = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendEventNotificationSNMP(boolean success) {
/* 726 */     if (ExecutionMode.isLoadTest())
/*     */       return; 
/*     */     try {
/* 729 */       if (snmp) {
/* 730 */         SnmpSupport.getSnmpService().sendEventTrap(success, (int)(System.currentTimeMillis() - this.startTimeVIT1));
/*     */       }
/* 732 */     } catch (Exception e) {
/* 733 */       snmp = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVinRead(boolean vinRead) {
/* 742 */     this.vinRead = vinRead;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\SPSRobot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */