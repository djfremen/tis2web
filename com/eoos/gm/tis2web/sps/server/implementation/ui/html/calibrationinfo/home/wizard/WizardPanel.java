/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HardwareNumberInputRequest;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.DatabaseInfoAccessPermission;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.CalibInfoHomePanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.AVMapReadThroughAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options.SelectedOptionsPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.ButtonContainer;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.DisplayRequestPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.SelectionRequestPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.hardware.HardwareNumberInputPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.hardware.HardwareSelectionRequestPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr.ProgrammingDataSelectionRequestPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.SummaryPanel;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WizardPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  54 */   private static final Logger log = Logger.getLogger(WizardPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  59 */       template = ApplicationContext.getInstance().loadFile(WizardPanel.class, "wizardpanel.html", null).toString();
/*  60 */     } catch (Exception e) {
/*  61 */       log.error("unable to load template - error:" + e, e);
/*  62 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private CalibInfoHomePanel homePanel;
/*     */   
/*  71 */   private RequestHandlerPanel requestHandlerPanel = null;
/*     */   
/*  73 */   private HtmlElement selectedOptionsPanel = null;
/*     */   
/*  75 */   private HtmlElement historyPanel = null;
/*     */   
/*     */   private ClickButtonElement buttonBack;
/*     */   
/*     */   private ClickButtonElement buttonNext;
/*     */   
/*     */   private ClickButtonElement buttonCancel;
/*     */   
/*  83 */   private ClickButtonElement buttonDBInfo = null;
/*     */   
/*     */   private CustomAVMap avMap;
/*     */   
/*  87 */   private Exception e = null;
/*     */   
/*     */   private boolean done = false;
/*     */   
/*  91 */   private Request request = null;
/*     */   
/*     */   private String securityCode;
/*     */   
/*     */   public WizardPanel(final ClientContext context, CalibInfoHomePanel homePanel, CustomAVMap avMap) throws Exception {
/*  96 */     this.context = context;
/*  97 */     this.homePanel = homePanel;
/*     */     
/*  99 */     this.buttonBack = new ClickButtonElement(context.createID(), "_top") {
/*     */         protected String getLabel() {
/* 101 */           return "&lt; " + context.getLabel("back");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 105 */           return WizardPanel.this.onBack();
/*     */         }
/*     */       };
/* 108 */     addElement((HtmlElement)this.buttonBack);
/*     */     
/* 110 */     this.buttonNext = new ClickButtonElement(context.createID(), "_top") {
/*     */         protected String getLabel() {
/* 112 */           return context.getLabel("next") + " &gt;";
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 117 */             return WizardPanel.this.onNext();
/* 118 */           } catch (Exception e) {
/* 119 */             WizardPanel.log.error("unable to proceed - exception:" + e, e);
/* 120 */             WizardPanel.this.e = e;
/* 121 */             WizardPanel.this.done = true;
/* 122 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/* 126 */     addElement((HtmlElement)this.buttonNext);
/*     */     
/* 128 */     this.avMap = avMap;
/* 129 */     this.selectedOptionsPanel = (HtmlElement)new SelectedOptionsPanel(context, avMap);
/* 130 */     addElement(this.selectedOptionsPanel);
/*     */     
/*     */     try {
/* 133 */       ServerWrapper.getInstance(this.context).execute((AttributeValueMap)avMap);
/* 134 */       this.done = true;
/* 135 */     } catch (RequestException e) {
/* 136 */       this.request = e.getRequest();
/* 137 */       this.requestHandlerPanel = createRequestHandlerPanel(e.getRequest());
/* 138 */       addElement((HtmlElement)this.requestHandlerPanel);
/* 139 */       if (e.getRequest() instanceof DisplayRequest) {
/* 140 */         this.done = true;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     this.buttonCancel = new ClickButtonElement(context.createID(), "_top") {
/*     */         protected String getLabel() {
/* 146 */           if (WizardPanel.this.done) {
/* 147 */             return context.getLabel("done");
/*     */           }
/* 149 */           return context.getLabel("cancel");
/*     */         }
/*     */ 
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 154 */           return WizardPanel.this.onCancel();
/*     */         }
/*     */       };
/* 157 */     addElement((HtmlElement)this.buttonCancel);
/*     */     
/* 159 */     if (DatabaseInfoAccessPermission.getInstance(this.context).check()) {
/* 160 */       this.buttonDBInfo = new ClickButtonElement(context.createID(), null) {
/*     */           protected String getLabel() {
/* 162 */             return context.getLabel("sps.calibration.info.button.database.info");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 166 */             return WizardPanel.this.onClick_DBInfo();
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 170 */             return "databaseinfo";
/*     */           }
/*     */         };
/* 173 */       addElement((HtmlElement)this.buttonDBInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object onClick_DBInfo() {
/* 179 */     Object retValue = null;
/*     */     try {
/* 181 */       if (this.request != null && this.request instanceof AssignmentRequest) {
/* 182 */         AVMapReadThroughAdapter aVMapReadThroughAdapter; CustomAVMap customAVMap = this.avMap;
/* 183 */         if (this.request instanceof ControllerSelectionRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 189 */           AVMapReadThroughAdapter aVMapReadThroughAdapter1 = new AVMapReadThroughAdapter(this.avMap);
/* 190 */           this.requestHandlerPanel.onNext((CustomAVMap)aVMapReadThroughAdapter1);
/* 191 */           aVMapReadThroughAdapter = aVMapReadThroughAdapter1;
/*     */         } 
/*     */ 
/*     */         
/* 195 */         DatabaseInfo dbInfo = SPSServer.getInstance(this.context.getSessionID()).getDatabaseInfo((AttributeValueMap)aVMapReadThroughAdapter, ((AssignmentRequest)this.request).getAttribute());
/*     */         
/* 197 */         retValue = new DatabaseInfoPopup(this.context, dbInfo);
/*     */       } 
/* 199 */     } catch (Exception e) {
/* 200 */       log.error("unable to display database info popup - exception :" + e, e);
/* 201 */       return "<html><body onLoad=\"window.close()\"></body></html>";
/*     */     } 
/* 203 */     return retValue;
/*     */   }
/*     */   
/*     */   private Object onBack() {
/* 207 */     this.homePanel.pop();
/* 208 */     return null;
/*     */   }
/*     */   
/*     */   private Object onNext() throws Exception {
/* 212 */     AVMapReadThroughAdapter aVMapReadThroughAdapter = new AVMapReadThroughAdapter(this.avMap);
/* 213 */     if (this.requestHandlerPanel != null && 
/* 214 */       this.requestHandlerPanel.onNext((CustomAVMap)aVMapReadThroughAdapter)) {
/* 215 */       WizardPanel newPanel = new WizardPanel(this.context, this.homePanel, (CustomAVMap)aVMapReadThroughAdapter);
/* 216 */       this.homePanel.push((HtmlElement)newPanel);
/*     */     } 
/*     */     
/* 219 */     return null;
/*     */   }
/*     */   
/*     */   private Object onCancel() {
/*     */     try {
/* 224 */       while (this.homePanel.peek() instanceof WizardPanel) {
/* 225 */         this.homePanel.pop();
/*     */       }
/* 227 */     } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */ 
/*     */     
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 235 */     StringBuffer tmp = new StringBuffer(template);
/* 236 */     if (this.e == null) {
/*     */       
/* 238 */       StringUtilities.replace(tmp, "{REQUEST_HANDLER_PANEL}", (this.requestHandlerPanel != null) ? this.requestHandlerPanel.getHtmlCode(params) : this.context.getMessage("sps.calibration.info.wizard.finished"));
/*     */     } else {
/*     */       
/* 241 */       String message = null;
/* 242 */       if (this.e instanceof SPSException) {
/* 243 */         message = this.context.getMessage(((SPSException)this.e).getMessageKey());
/*     */       } else {
/* 245 */         message = this.context.getMessage("sps.calibration.info.wizard.error.general");
/*     */       } 
/* 247 */       StringUtilities.replace(tmp, "{REQUEST_HANDLER_PANEL}", "<span style=\"color:red; text-align:center\">" + message + "<span>");
/*     */     } 
/* 249 */     StringUtilities.replace(tmp, "{SELECTED_OPTIONS_PANEL}", this.selectedOptionsPanel.getHtmlCode(params));
/*     */     
/* 251 */     StringUtilities.replace(tmp, "{BUTTON_BACK}", this.buttonBack.getHtmlCode(params));
/* 252 */     if (this.done) {
/* 253 */       SectionIndex index = null;
/* 254 */       while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!-- XXXXDONE -->", "<!-- /XXXXDONE -->", 0, true, false)) != null) {
/* 255 */         StringUtilities.replaceSectionContent(tmp, index, "");
/*     */       }
/*     */     } 
/* 258 */     StringUtilities.replace(tmp, "{BUTTON_NEXT}", this.buttonNext.getHtmlCode(params));
/*     */     
/* 260 */     StringUtilities.replace(tmp, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/*     */     
/* 262 */     if (this.historyPanel == null) {
/* 263 */       SectionIndex index = StringUtilities.getSectionIndex(tmp.toString(), "<!-- XXXXHISTORY -->", "<!-- /XXXXHISTORY -->", 0, true, false);
/* 264 */       StringUtilities.replaceSectionContent(tmp, index, "");
/*     */     } else {
/* 266 */       StringUtilities.replace(tmp, "{HISTORY}", this.historyPanel.getHtmlCode(params));
/*     */     } 
/*     */     
/* 269 */     String buttonCode = "&nbsp;";
/* 270 */     if (this.requestHandlerPanel instanceof ButtonContainer) {
/* 271 */       List buttons = ((ButtonContainer)this.requestHandlerPanel).getButtonElements();
/* 272 */       if (buttons != null && buttons.size() != 0) {
/* 273 */         StringBuffer table = new StringBuffer("<table><tr>{COLS}</tr></table>");
/* 274 */         for (Iterator<HtmlElement> iter = buttons.iterator(); iter.hasNext(); ) {
/* 275 */           HtmlElement element = iter.next();
/* 276 */           StringUtilities.replace(table, "{COLS}", "<td>" + element.getHtmlCode(params) + "</td>{COLS}");
/*     */         } 
/* 278 */         StringUtilities.replace(table, "{COLS}", "");
/* 279 */         buttonCode = table.toString();
/*     */       } 
/*     */     } 
/* 282 */     if (this.requestHandlerPanel instanceof SelectionRequestPanel && this.securityCode != null) {
/* 283 */       String label = this.context.getLabel("sps.calibration.info.security.code") + ": ";
/* 284 */       StringUtilities.replace(tmp, "{SECURITY_CODE}", "<tr><td align=\"right\">" + label + this.securityCode + "</td></tr>");
/*     */     } else {
/* 286 */       StringUtilities.replace(tmp, "{SECURITY_CODE}", "");
/*     */     } 
/* 288 */     StringUtilities.replace(tmp, "{ADD_BUTTONS_TABLE}", buttonCode);
/*     */     
/* 290 */     StringUtilities.replace(tmp, "{BUTTON_DBINFO}", (this.buttonDBInfo != null) ? this.buttonDBInfo.getHtmlCode(params) : "");
/*     */     
/* 292 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private RequestHandlerPanel createRequestHandlerPanel(Request request) {
/* 296 */     if (request instanceof ProgrammingDataSelectionRequest) {
/*     */       
/* 298 */       String dealerVCI = String.valueOf(AVUtil.accessValue((AttributeValueMap)this.avMap, CommonAttribute.DEALER_VCI));
/* 299 */       return (RequestHandlerPanel)new ProgrammingDataSelectionRequestPanel(this.context, (ProgrammingDataSelectionRequest)request, dealerVCI, this.avMap);
/* 300 */     }  if (request instanceof HardwareSelectionRequest)
/* 301 */       return (RequestHandlerPanel)new HardwareSelectionRequestPanel(this.context, (HardwareSelectionRequest)request); 
/* 302 */     if (request instanceof SelectionRequest) {
/* 303 */       if (request instanceof ControllerSelectionRequest) {
/* 304 */         List<?> controllerReferences = ((ControllerSelectionRequest)request).getOptions();
/* 305 */         Collections.sort(controllerReferences, SPSControllerReference.COMPARATOR_DESCRIPTION);
/* 306 */         if (UIContext.getInstance(this.context).displaySecurityCode()) {
/* 307 */           this.securityCode = String.valueOf(AVUtil.accessValue((AttributeValueMap)this.avMap, CommonAttribute.SECURITY_CODE));
/* 308 */           if (this.securityCode != null && "null".equalsIgnoreCase(this.securityCode)) {
/* 309 */             this.securityCode = null;
/*     */           }
/*     */         } 
/*     */       } 
/* 313 */       return (RequestHandlerPanel)new SelectionRequestPanel(this.context, (SelectionRequest)request);
/* 314 */     }  if (request instanceof DisplaySummaryRequest)
/* 315 */       return (RequestHandlerPanel)new SummaryPanel(this.context, (DisplaySummaryRequest)request, this.avMap, this); 
/* 316 */     if (request instanceof DisplayRequest)
/* 317 */       return (RequestHandlerPanel)new DisplayRequestPanel(this.context, (DisplayRequest)request); 
/* 318 */     if (request instanceof HardwareNumberInputRequest) {
/* 319 */       return (RequestHandlerPanel)new HardwareNumberInputPanel(this.context, (HardwareNumberInputRequest)request);
/*     */     }
/* 321 */     throw new IllegalArgumentException("unknown request type: " + String.valueOf(request));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedOptionsPanel(SelectedOptionsPanel panel) {
/* 326 */     removeElement(this.selectedOptionsPanel);
/* 327 */     this.selectedOptionsPanel = (HtmlElement)panel;
/* 328 */     addElement(this.selectedOptionsPanel);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\WizardPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */