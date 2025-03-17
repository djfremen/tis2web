/*     */ package com.eoos.gm.tis2web.sps.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.DataTransferPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.DisplayPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.DoubleSelectionPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.HardwareSelectionPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.InputPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.MultipleSelectionPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.ProgrammingDataPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.SummaryPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.UIPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.page.VINPage;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TransferDataPanel;
/*     */ import com.eoos.gm.tis2web.sps.common.VCExtRequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.CustomException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.BulletinDisplayRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ExceptionImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestGroupBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.SwingUtilities;
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
/*     */ public class UIAgent
/*     */   implements IUIAgent
/*     */ {
/*  55 */   private static final Logger log = Logger.getLogger(UIAgent.class);
/*     */ 
/*     */   
/*     */   private SPSClientController controller;
/*     */ 
/*     */   
/*     */   private AttributeValueMapExt data;
/*     */   
/*     */   private SPSFrame gui;
/*     */   
/*     */   public UIPage page;
/*     */   
/*     */   private UIPage last;
/*     */   
/*     */   private UIPage pending;
/*     */   
/*     */   private String finalInstructionsID;
/*     */   
/*     */   private String finalInstructionsHTML;
/*     */   
/*     */   private volatile boolean wait = false;
/*     */   
/*     */   protected boolean blocked;
/*     */ 
/*     */   
/*     */   public void triggerNextRequest() {
/*  81 */     if (this.blocked) {
/*  82 */       unblock();
/*  83 */     } else if (this.page instanceof InputPage) {
/*  84 */       if (!((InputPage)this.page).handleInput()) {
/*  85 */         this.controller.triggerNextRequest();
/*     */       }
/*  87 */     } else if (this.pending == null) {
/*  88 */       this.last = this.page;
/*  89 */       this.page = null;
/*  90 */       this.controller.triggerNextRequest();
/*     */     } else {
/*     */       
/*  93 */       this.page = this.pending;
/*  94 */       this.pending = null;
/*  95 */       this.page.activate(this.data.getSavePoint());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blockGUI() {
/* 100 */     this.gui.blockGUI();
/*     */   }
/*     */   
/*     */   public void unblockGUI() {
/* 104 */     this.gui.unblockGUI();
/*     */   }
/*     */   
/*     */   public void lockOn() {
/* 108 */     this.gui.lockOn();
/*     */   }
/*     */   
/*     */   public void lockOff() {
/* 112 */     this.gui.lockOff();
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerRequest() {
/* 117 */     this.controller.triggerNextRequest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(AssignmentRequest request, AttributeValueMapExt data) {
/* 124 */     if (this.page == null || !this.page.handle(request, data)) {
/* 125 */       UIPage next = createPage(request, data);
/* 126 */       if (this.page == null || this.page instanceof InputPage) {
/* 127 */         this.page = next;
/* 128 */         this.wait = false;
/* 129 */         if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PROGRAMMING_INSTRUCTIONS)) {
/* 130 */           TransferDataPanel.modifyReprogrammingStatus();
/* 131 */         } else if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_POST_PROGRAMMING_INSTRUCTIONS)) {
/* 132 */           TransferDataPanel.modifyReprogrammingStatus();
/*     */         } 
/* 134 */         this.page.activate(data.getSavePoint());
/* 135 */         if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PROGRAMMING_INSTRUCTIONS)) {
/*     */ 
/*     */           
/* 138 */           TransferDataPanel.resetReprogrammingIndicator();
/*     */         }
/* 140 */         else if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 141 */           TransferDataPanel.resetSequenceReprogrammingIndicator();
/*     */         } 
/* 143 */       } else if (isFixedAttributesDisplayPending(next)) {
/* 144 */         this.page = next;
/* 145 */         this.page.activate(data.getSavePoint());
/*     */       } else {
/* 147 */         this.pending = next;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isFixedAttributesDisplayPending(UIPage next) {
/* 153 */     if (next instanceof SummaryPage && this.page instanceof MultipleSelectionPage && this.pending == null) {
/* 154 */       return (((MultipleSelectionPage)this.page).hasFixedAttributesOnly() && this.page.getRequest().getRequestGroup() instanceof VCExtRequestGroup);
/*     */     }
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   public UIAgent(SPSClientController controller, AttributeValueMapExt data) {
/* 160 */     this.blocked = false; this.controller = controller;
/*     */     this.data = data;
/*     */     this.gui = SPSFrame.getInstance();
/* 163 */     this.gui.start(this); } protected void block() { this.blocked = true;
/* 164 */     synchronized (this) {
/* 165 */       while (this.blocked) {
/*     */         try {
/* 167 */           wait();
/* 168 */         } catch (InterruptedException e) {}
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unblock() {
/* 175 */     synchronized (this) {
/* 176 */       this.blocked = false;
/* 177 */       notify();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void waitUntilUIReady() {
/* 182 */     while (this.wait);
/*     */   }
/*     */ 
/*     */   
/*     */   protected UIPage createPage(AssignmentRequest request, AttributeValueMapExt data) {
/*     */     InputPage inputPage;
/* 188 */     UIPage page = null;
/* 189 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/* 190 */       this.wait = true;
/* 191 */       DoubleSelectionPage doubleSelectionPage = new DoubleSelectionPage(this, this.gui, data, request);
/* 192 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest) {
/* 193 */       if (request instanceof com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest) {
/* 194 */         SummaryPage summaryPage = new SummaryPage(this, this.gui, data, request);
/*     */       } else {
/* 196 */         DisplayPage displayPage = new DisplayPage(this, this.gui, data, request);
/* 197 */         if (request.getAttribute().equals(CommonAttribute.FINAL_INSTRUCTIONS)) {
/* 198 */           this.page = null;
/* 199 */         } else if (request.getAttribute().equals(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 200 */           if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null) {
/* 201 */             this.page = null;
/*     */           }
/* 203 */         } else if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PROGRAMMING_INSTRUCTIONS)) {
/* 204 */           this.page = null;
/* 205 */         } else if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 206 */           this.page = null;
/* 207 */         } else if (request.getAttribute().equals(CommonAttribute.INTERMEDIATE_POST_PROGRAMMING_INSTRUCTIONS)) {
/* 208 */           this.page = null;
/*     */         } 
/*     */       } 
/* 211 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.VINDisplayRequest) {
/* 212 */       VINPage vINPage = new VINPage(this, this.gui, data, request);
/* 213 */       if (this.page instanceof MultipleSelectionPage) {
/* 214 */         this.last = this.page;
/* 215 */         this.page = null;
/*     */       } 
/* 217 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.VehicleAttributeSelectionRequest) {
/* 218 */       MultipleSelectionPage multipleSelectionPage = new MultipleSelectionPage(this, this.gui, data, request);
/* 219 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) {
/* 220 */       DoubleSelectionPage doubleSelectionPage = new DoubleSelectionPage(this, this.gui, data, request);
/*     */     }
/* 222 */     else if (request instanceof com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest) {
/* 223 */       HardwareSelectionPage hardwareSelectionPage = new HardwareSelectionPage(this, this.gui, data, request);
/* 224 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest) {
/* 225 */       ProgrammingDataPage programmingDataPage = new ProgrammingDataPage(this, this.gui, data, request);
/* 226 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest) {
/* 227 */       VCExtRequestGroup rg = makeVCOptionRequestGroup((AttributeValueMap)data);
/* 228 */       if (rg != null) {
/* 229 */         request.setRequestGroup((RequestGroup)rg);
/*     */       }
/* 231 */       MultipleSelectionPage multipleSelectionPage = new MultipleSelectionPage(this, this.gui, data, request);
/* 232 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.ProgrammingDataDownloadRequest) {
/* 233 */       DataTransferPage dataTransferPage = new DataTransferPage(this, this.gui, data, request);
/* 234 */       if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null) {
/* 235 */         this.page = null;
/*     */       }
/* 237 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest) {
/* 238 */       DataTransferPage dataTransferPage = new DataTransferPage(this, this.gui, data, request);
/* 239 */       if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null) {
/* 240 */         this.page = null;
/*     */       }
/* 242 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest) {
/* 243 */       DataTransferPage dataTransferPage = new DataTransferPage(this, this.gui, data, request);
/* 244 */       if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null) {
/* 245 */         this.page = null;
/*     */       }
/* 247 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest) {
/* 248 */       inputPage = new InputPage(this, this.gui, data, request);
/*     */     } else {
/* 250 */       throw new IllegalArgumentException();
/*     */     } 
/* 252 */     inputPage.setPredecessor((this.page == null) ? this.last : this.page);
/* 253 */     return (UIPage)inputPage;
/*     */   }
/*     */   
/*     */   public void execute(SelectionResult result) {
/* 257 */     this.controller.execute(result, (AttributeValueMapExt)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(SelectionResult result, ValueRetrieval data) {
/* 262 */     waitUntilUIReady();
/*     */     
/* 264 */     if (result.getAttribute().equals(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED)) {
/* 265 */       if (this.page != null && !(this.page instanceof DataTransferPage)) {
/* 266 */         if (this.data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) != null) {
/* 267 */           this.data.set(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED, CommonValue.OK);
/*     */         }
/*     */         return;
/*     */       } 
/* 271 */     } else if (result.getAttribute().equals(CommonAttribute.DEVICE) && 
/* 272 */       this.pending != null) {
/* 273 */       this.pending = null;
/*     */     } 
/*     */ 
/*     */     
/* 277 */     if (this.page == null || !this.page.handle(result, data)) {
/* 278 */       if (this.page instanceof HardwareSelectionPage) {
/*     */         return;
/*     */       }
/* 281 */       this.controller.execute(result, this.data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setVIT1(VIT1 vit1, Integer device) {
/* 286 */     this.gui.setVIT1(vit1, device);
/* 287 */     this.gui.setBackButtonState(true);
/*     */   }
/*     */   
/*     */   public void notify(Attribute attribute) {
/* 291 */     if (attribute.equals(CommonAttribute.TOOL_VIN)) {
/*     */ 
/*     */       
/* 294 */       this.gui.setBackButtonState(false);
/* 295 */     } else if (attribute.equals(CommonAttribute.VIT1)) {
/* 296 */       this.gui.setBackButtonState(false);
/* 297 */     } else if (attribute.equals(CommonAttribute.EXECUTION_MODE)) {
/*     */ 
/*     */       
/* 300 */       this.gui.setBackButtonState(false);
/* 301 */     } else if (attribute.equals(CommonAttribute.REPROGRAM)) {
/*     */ 
/*     */ 
/*     */       
/* 305 */       this.gui.setBackButtonState(false);
/* 306 */       this.gui.setCancelButtonState(false);
/* 307 */       this.gui.setCloseState(false);
/*     */     }
/* 309 */     else if (attribute.equals(CommonAttribute.FINISH)) {
/* 310 */       this.gui.setCancelButtonState(true);
/* 311 */       this.gui.setCloseState(true);
/* 312 */       this.gui.finish();
/*     */ 
/*     */     
/*     */     }
/* 316 */     else if (attribute.equals(CommonAttribute.FAILURE)) {
/* 317 */       this.gui.setBackButtonState(false);
/* 318 */       this.gui.setCancelButtonState(true);
/* 319 */       this.gui.setCloseState(true);
/* 320 */     } else if (!attribute.equals(CommonAttribute.PROCEED_SAME_VIN)) {
/*     */       
/* 322 */       this.gui.setCancelButtonState(true);
/* 323 */       this.gui.setCloseState(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void displayFinishButtons() {
/* 328 */     this.gui.finish();
/*     */   }
/*     */   
/*     */   public void changeStateButtons() {
/* 332 */     this.gui.changeStateButtons();
/*     */   }
/*     */   
/*     */   public void setReprogrammingController(String label) {
/* 336 */     TransferDataPanel.setReprogrammingController(label);
/*     */   }
/*     */   
/*     */   public void setReprogrammingController(String function, String position) {
/* 340 */     TransferDataPanel.setReprogrammingController(function, position);
/*     */   }
/*     */   
/*     */   public void indicateReprogrammingFailed() {
/* 344 */     TransferDataPanel.indicateReprogrammingFailed();
/*     */   }
/*     */   
/*     */   public void undo() {
/* 348 */     this.pending = null;
/* 349 */     this.last = null;
/*     */     try {
/* 351 */       this.page = this.page.undo();
/* 352 */       if (this.page instanceof DoubleSelectionPage) {
/* 353 */         Object confirmation = this.data.getValue(CommonAttribute.CONFIRMED_OPTIONS);
/* 354 */         if (confirmation != null && CommonValue.OK.equals(confirmation)) {
/* 355 */           this.data.remove(CommonAttribute.CONFIRMED_OPTIONS);
/*     */         }
/* 357 */       } else if (this.page instanceof SummaryPage) {
/* 358 */         if (this.data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) != null) {
/* 359 */           this.data.remove(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START);
/*     */         }
/* 361 */       } else if (this.page instanceof DisplayPage && this.page.getRequest().getAttribute().equals(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS) && 
/* 362 */         this.data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) != null) {
/* 363 */         this.data.remove(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START);
/*     */       }
/*     */     
/* 366 */     } catch (Exception e) {
/* 367 */       log.debug("page rollback failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*     */     try {
/* 373 */       this.page.undo();
/* 374 */     } catch (Exception e) {
/* 375 */       log.debug("page reset failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void restart() {
/* 380 */     this.pending = null;
/* 381 */     this.last = null;
/* 382 */     this.page = null;
/* 383 */     this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 384 */     this.controller.triggerNextRequest();
/*     */   }
/*     */   
/*     */   public String getFileName(String title, String filter, List fileTypes, String initialDir) {
/* 388 */     return this.gui.displayFileDialog(title, filter, fileTypes, initialDir);
/*     */   }
/*     */   
/*     */   public void requestBulletinDisplay(String bulletin) {
/* 392 */     this.controller.execute((Request)new BulletinDisplayRequestImpl(bulletin), (AttributeValueMap)null);
/*     */   }
/*     */   
/*     */   public void prepareOfflineFinalInstructions(String instructionID) {
/*     */     try {
/* 397 */       this.finalInstructionsHTML = this.controller.getInstructionHTML(instructionID, true);
/* 398 */       this.finalInstructionsID = instructionID;
/* 399 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public String requestInstructionHTML(String instructionID) throws Exception {
/* 404 */     if (this.finalInstructionsID != null && instructionID.equals(this.finalInstructionsID) && this.finalInstructionsHTML != null) {
/* 405 */       return this.controller.handleSPSLabourTime(this.finalInstructionsHTML);
/*     */     }
/* 407 */     return this.controller.getInstructionHTML(instructionID, false);
/*     */   }
/*     */   
/*     */   public byte[] requestInstructionImage(String imageID) throws Exception {
/* 411 */     return this.controller.getInstructionImage(imageID);
/*     */   }
/*     */   
/*     */   public boolean displayQuestionDialog(String message) {
/* 415 */     return this.gui.displayQuestionDialog(message);
/*     */   }
/*     */   
/*     */   public void displayMessageDialog(String message) {
/* 419 */     this.gui.displayMessageDialog(message);
/*     */   }
/*     */   
/*     */   public void removeMessageDialog() {
/* 423 */     this.gui.removeMessageDialog();
/*     */     try {
/* 425 */       this.gui.setBackButtonState(true);
/* 426 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayHTMLMessage(String message, List traceInfo) {
/* 431 */     this.gui.displayHTMLMessage(message, traceInfo);
/*     */   }
/*     */   
/*     */   public boolean displayQuestionHTMLMessage(String message, List traceInfo) {
/* 435 */     return this.gui.displayQuestionHTMLMessage(message, traceInfo);
/*     */   }
/*     */   
/*     */   public void displayInformationText(String message) {
/* 439 */     this.gui.displayMessageDialog(message);
/*     */   }
/*     */   
/*     */   public void updateInformationText(String message) {
/* 443 */     this.gui.updateMessageDialog(message);
/*     */   }
/*     */   
/*     */   public void destroyInformationText() {
/* 447 */     this.gui.removeMessageDialog();
/*     */   }
/*     */   
/*     */   public void setStatusBar(String info) {
/* 451 */     this.gui.setInfoOnBarStatus(info);
/*     */   }
/*     */   
/*     */   public void showTestSummary(final DefaultTableModel summary) {
/* 455 */     Runnable doWorkRunnable = new Runnable() {
/*     */         public void run() {
/* 457 */           UIAgent.this.gui.showTestSummary(summary);
/*     */         }
/*     */       };
/* 460 */     SwingUtilities.invokeLater(doWorkRunnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayReprogramStatusPS(List state) {
/* 465 */     this.gui.displayReprogramStatusPS(state);
/*     */   }
/*     */   
/*     */   public void handleException(Exception exception) {
/* 469 */     if (exception instanceof CustomException) {
/* 470 */       this.gui.handleException((CustomException)exception);
/*     */     } else {
/* 472 */       this.gui.handleException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleException(String exceptionID, String info) {
/* 477 */     String msg = ExceptionImpl.getInstance(exceptionID).getDenotation(null);
/* 478 */     this.gui.handleException(msg + '\n' + info);
/*     */   }
/*     */   
/*     */   public void handleException(String exceptionID) {
/* 482 */     this.gui.handleException(ExceptionImpl.getInstance(exceptionID));
/*     */   }
/*     */   
/*     */   public void displayMessage(String msg) {
/* 486 */     this.gui.displayMessage(msg);
/*     */   }
/*     */   
/*     */   public void showMessage(String severity, String title, String msg) {
/* 490 */     this.gui.displayMessage(severity, title, msg);
/*     */   }
/*     */   
/*     */   public void displayHTML(String html) {
/* 494 */     this.gui.displayHTML(html);
/*     */   }
/*     */   
/*     */   public boolean isNAO() {
/* 498 */     return this.controller.isNAO();
/*     */   }
/*     */   
/*     */   public boolean isGlobalAdapter() {
/* 502 */     return this.controller.isGlobalAdapter();
/*     */   }
/*     */   
/*     */   public boolean supportSPSFunctions() {
/* 506 */     return this.controller.supportSPSFunctions();
/*     */   }
/*     */   
/*     */   protected boolean availableVehicleAttributes(AttributeValueMap data) {
/* 510 */     return (data.getValue(CommonAttribute.SALESMAKE) != null);
/*     */   }
/*     */   
/*     */   protected void addVCAttribute(List<Attribute> attrs, Map<Attribute, Value> values, AttributeValueMap data, Attribute attribute) {
/* 514 */     Value value = data.getValue(attribute);
/* 515 */     if (value != null) {
/* 516 */       attrs.add(attribute);
/* 517 */       values.put(attribute, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCExtRequestGroup makeVCOptionRequestGroup(AttributeValueMap data) {
/* 522 */     List attrs = new ArrayList();
/* 523 */     Map<Object, Object> values = null;
/* 524 */     if (availableVehicleAttributes(data)) {
/* 525 */       values = new HashMap<Object, Object>();
/* 526 */       addVCAttribute(attrs, values, data, CommonAttribute.SALESMAKE);
/* 527 */       addVCAttribute(attrs, values, data, CommonAttribute.MODELYEAR);
/* 528 */       addVCAttribute(attrs, values, data, CommonAttribute.MODEL);
/* 529 */       addVCAttribute(attrs, values, data, CommonAttribute.CARLINE);
/* 530 */       if (data.getValue(CommonAttribute.ENGINE) != null) {
/* 531 */         addVCAttribute(attrs, values, data, CommonAttribute.ENGINE);
/*     */       }
/* 533 */       if (data.getValue(CommonAttribute.TRANSMISSION) != null) {
/* 534 */         addVCAttribute(attrs, values, data, CommonAttribute.TRANSMISSION);
/*     */       }
/* 536 */       Value vin = data.getValue(CommonAttribute.VIN);
/* 537 */       RequestGroupBuilderImpl rqBuilder = new RequestGroupBuilderImpl();
/* 538 */       return rqBuilder.makeVCExtRequestGroup((vin != null) ? ((ValueAdapter)vin).getAdaptee().toString() : null, attrs, values);
/*     */     } 
/*     */     
/* 541 */     return null;
/*     */   }
/*     */   
/* 544 */   protected static int dump = 0;
/*     */ 
/*     */   
/*     */   protected void dump() {
/* 548 */     log.debug(">>> ui-agent dump: " + dump++);
/* 549 */     if (this.page != null) {
/* 550 */       this.page.dump(0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void trace(AssignmentRequest request) {
/* 555 */     String attribute = request.getAttribute().toString();
/* 556 */     if (attribute.indexOf("[key=") >= 0) {
/* 557 */       attribute = attribute.substring(attribute.indexOf("[key="));
/*     */     }
/* 559 */     log.debug("ui-agent request attribute = " + attribute + " auto-submit=" + request.autoSubmit());
/*     */   }
/*     */   
/*     */   public boolean isFinalInstructionDisplayed() {
/* 563 */     return (this.data.getValue(CommonAttribute.FINAL_INSTRUCTIONS) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProceedPossible() {
/* 568 */     return this.controller.isProceedPossible();
/*     */   }
/*     */   
/*     */   public void proceed() {
/* 572 */     this.controller.proceed();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\UIAgent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */