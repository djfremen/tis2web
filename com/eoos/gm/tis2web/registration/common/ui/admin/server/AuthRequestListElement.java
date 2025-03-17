/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.dialog.AuthReqDetailDialog;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.IconElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AuthRequestListElement
/*     */   extends ListElement {
/*  32 */   private static final Logger log = Logger.getLogger(AuthRequestListElement.class);
/*     */   
/*     */   private static final int INDEX_TYPE = 0;
/*     */   
/*     */   private static final int INDEX_REQUEST_ID = 1;
/*     */   
/*     */   private static final int INDEX_DEALERSHIP_ID = 2;
/*     */   
/*     */   private static final int INDEX_DEALERSHIP = 3;
/*     */   
/*     */   private static final int INDEX_DATE = 4;
/*     */   
/*     */   private static final int INDEX_DIRECTACTION = 5;
/*     */   
/*     */   private HtmlElement headerType;
/*     */   
/*     */   private LinkElement headerRequestID;
/*     */   
/*     */   private LinkElement headerDealershipID;
/*     */   
/*     */   private LinkElement headerDealership;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   
/*     */   private HtmlElement headerDirectAction;
/*     */   
/*  58 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  62 */   private Map requestToProcessButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  64 */   private Map requestToRevokeButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  66 */   private Map requestToDeleteButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  68 */   private Map requestToEditButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  70 */   private Map requestToButtonContainer = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List requests;
/*     */   
/*     */   private IconElement iconTypeRepeat;
/*     */   
/*     */   private IconElement iconTypeMigration;
/*     */   
/*     */   private IconElement iconTypeExtension;
/*     */   
/*     */   private IconElement iconTypeRegistration;
/*     */   
/*  82 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */   
/*     */   public AuthRequestListElement(final List requests, final ClientContext context) {
/*  86 */     this.context = context;
/*  87 */     this.requests = requests;
/*  88 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  91 */             return requests;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  96 */     this.iconTypeRegistration = new IconElement("pic/registration/registration.gif", "registration");
/*  97 */     this.iconTypeMigration = new IconElement("pic/registration/migration.gif", "migration");
/*  98 */     this.iconTypeExtension = new IconElement("pic/registration/extension.gif", "extension");
/*  99 */     this.iconTypeRepeat = new IconElement("pic/registration/repeat.gif", "repeat");
/*     */     
/* 101 */     this.headerDirectAction = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/* 103 */     this.headerType = (HtmlElement)new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 107 */             if (AuthorizationRequest.COMPARATOR_TYPE.equals(AuthRequestListElement.this.comparator)) {
/* 108 */               AuthRequestListElement.this.comparator = Util.reverseComparator(AuthRequestListElement.this.comparator);
/*     */             } else {
/* 110 */               AuthRequestListElement.this.comparator = AuthorizationRequest.COMPARATOR_TYPE;
/*     */             } 
/* 112 */             Collections.sort(requests, AuthRequestListElement.this.comparator);
/* 113 */           } catch (Exception e) {
/* 114 */             AuthRequestListElement.log.error("...unable to sort by type - exception: " + e, e);
/*     */           } 
/* 116 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 121 */           return context.getLabel("type");
/*     */         }
/*     */       };
/*     */     
/* 125 */     addElement(this.headerType);
/*     */     
/* 127 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 131 */             if (AuthorizationRequest.COMPARATOR_DATE.equals(AuthRequestListElement.this.comparator)) {
/* 132 */               AuthRequestListElement.this.comparator = Util.reverseComparator(AuthRequestListElement.this.comparator);
/*     */             } else {
/* 134 */               AuthRequestListElement.this.comparator = AuthorizationRequest.COMPARATOR_DATE;
/*     */             } 
/* 136 */             Collections.sort(requests, AuthRequestListElement.this.comparator);
/* 137 */           } catch (Exception e) {
/* 138 */             AuthRequestListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 140 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 145 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 149 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 151 */     this.headerRequestID = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 155 */             if (AuthorizationRequest.COMPARATOR_REQUEST_ID.equals(AuthRequestListElement.this.comparator)) {
/* 156 */               AuthRequestListElement.this.comparator = Util.reverseComparator(AuthRequestListElement.this.comparator);
/*     */             } else {
/* 158 */               AuthRequestListElement.this.comparator = AuthorizationRequest.COMPARATOR_REQUEST_ID;
/*     */             } 
/* 160 */             Collections.sort(requests, AuthRequestListElement.this.comparator);
/* 161 */           } catch (Exception e) {
/* 162 */             AuthRequestListElement.log.error("...unable to sort by request id - exception: " + e, e);
/*     */           } 
/* 164 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 169 */           return context.getLabel("request.id");
/*     */         }
/*     */       };
/*     */     
/* 173 */     addElement((HtmlElement)this.headerRequestID);
/*     */     
/* 175 */     this.headerDealershipID = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 179 */             if (AuthorizationRequest.COMPARATOR_DEALERSHIP_ID.equals(AuthRequestListElement.this.comparator)) {
/* 180 */               AuthRequestListElement.this.comparator = Util.reverseComparator(AuthRequestListElement.this.comparator);
/*     */             } else {
/* 182 */               AuthRequestListElement.this.comparator = AuthorizationRequest.COMPARATOR_DEALERSHIP_ID;
/*     */             } 
/* 184 */             Collections.sort(requests, AuthRequestListElement.this.comparator);
/* 185 */           } catch (Exception e) {
/* 186 */             AuthRequestListElement.log.error("...unable to sort by dealership id - exception: " + e, e);
/*     */           } 
/* 188 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 193 */           return context.getLabel("dealership.id");
/*     */         }
/*     */       };
/*     */     
/* 197 */     addElement((HtmlElement)this.headerDealershipID);
/*     */     
/* 199 */     this.headerDealership = new LinkElement(context.createID(), null) {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 202 */             if (AuthorizationRequest.COMPARATOR_DEALERSHIP.equals(AuthRequestListElement.this.comparator)) {
/* 203 */               AuthRequestListElement.this.comparator = Util.reverseComparator(AuthRequestListElement.this.comparator);
/*     */             } else {
/* 205 */               AuthRequestListElement.this.comparator = AuthorizationRequest.COMPARATOR_DEALERSHIP;
/*     */             } 
/* 207 */             Collections.sort(requests, AuthRequestListElement.this.comparator);
/* 208 */           } catch (Exception e) {
/* 209 */             AuthRequestListElement.log.error("...unable to sort by dealership  - exception: " + e, e);
/*     */           } 
/* 211 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 216 */           return context.getLabel("dealership");
/*     */         }
/*     */       };
/* 219 */     addElement((HtmlElement)this.headerDealership);
/*     */     
/* 221 */     this.comparator = Util.reverseComparator(AuthorizationRequest.COMPARATOR_DATE);
/* 222 */     Collections.sort(this.requests, this.comparator);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 226 */     return 6;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 230 */     switch (columnIndex) {
/*     */       case 0:
/* 232 */         return this.headerType;
/*     */       case 1:
/* 234 */         return (HtmlElement)this.headerRequestID;
/*     */       case 2:
/* 236 */         return (HtmlElement)this.headerDealershipID;
/*     */       case 3:
/* 238 */         return (HtmlElement)this.headerDealership;
/*     */       case 4:
/* 240 */         return (HtmlElement)this.headerDate;
/*     */       case 5:
/* 242 */         return this.headerDirectAction;
/*     */     } 
/* 244 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement getProcessButton(final AuthorizationRequest request) {
/* 249 */     ClickButtonElement button = (ClickButtonElement)this.requestToProcessButton.get(request);
/* 250 */     if (button == null && request.getStatus() == AuthorizationRequest.STATUS_PENDING) {
/* 251 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 254 */             return AuthRequestListElement.this.context.getLabel("process");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 259 */               return AuthRequestListElement.this.onProcess(request);
/* 260 */             } catch (Exception e) {
/* 261 */               AuthRequestListElement.log.error("unable to process request - exception: " + e, e);
/* 262 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 267 */       this.requestToProcessButton.put(request, button);
/*     */     } 
/* 269 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getRevokeButton(final AuthorizationRequest request) {
/* 273 */     ClickButtonElement button = (ClickButtonElement)this.requestToRevokeButton.get(request);
/* 274 */     if (button == null && request.getStatus() == AuthorizationRequest.STATUS_AUTHORIZED) {
/* 275 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 278 */             return AuthRequestListElement.this.context.getLabel("revoke");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 283 */               final HtmlElementContainer retValue = getTopLevelContainer();
/* 284 */               return new SimpleConfirmationMessageBox(AuthRequestListElement.this.context, null, AuthRequestListElement.this.context.getMessage("registration.confirm.revocation"))
/*     */                 {
/*     */                   protected Object onCancel(Map params) {
/* 287 */                     return retValue;
/*     */                   }
/*     */                   
/*     */                   protected Object onOK(Map params) {
/*     */                     try {
/* 292 */                       Registry registry = RegistrationProvider.getInstance().getService();
/* 293 */                       registry.revokeAuthorization(request.getRegistration());
/* 294 */                       MainPanel.getInstance(AuthRequestListElement.this.context).update();
/* 295 */                     } catch (Exception e) {
/* 296 */                       AuthRequestListElement.log.error("unable to revoke authorization - exception: " + e, e);
/*     */                     } 
/* 298 */                     return retValue;
/*     */                   }
/*     */                 };
/*     */             
/*     */             }
/* 303 */             catch (Exception e) {
/* 304 */               AuthRequestListElement.log.error("unable to show confirmation dialog - exception: " + e, e);
/* 305 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 310 */       this.requestToRevokeButton.put(request, button);
/*     */     } 
/* 312 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getDeleteButton(final AuthorizationRequest request) {
/* 316 */     ClickButtonElement button = (ClickButtonElement)this.requestToDeleteButton.get(request);
/* 317 */     if (button == null) {
/* 318 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 321 */             return AuthRequestListElement.this.context.getLabel("delete");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 326 */               final HtmlElementContainer retValue = getTopLevelContainer();
/* 327 */               return new SimpleConfirmationMessageBox(AuthRequestListElement.this.context, null, AuthRequestListElement.this.context.getMessage("registration.confirm.deletion"))
/*     */                 {
/*     */                   protected Object onCancel(Map params) {
/* 330 */                     return retValue;
/*     */                   }
/*     */                   
/*     */                   protected Object onOK(Map params) {
/*     */                     try {
/* 335 */                       Registry registry = RegistrationProvider.getInstance().getService();
/* 336 */                       registry.deleteRegistrationRecord(request.getRegistration());
/* 337 */                       AuthRequestListElement.this.requests.remove(request);
/* 338 */                     } catch (Exception e) {
/* 339 */                       AuthRequestListElement.log.error("unable to delete request - exception: " + e, e);
/*     */                     } 
/* 341 */                     return retValue;
/*     */                   }
/*     */                 };
/*     */             
/*     */             }
/* 346 */             catch (Exception e) {
/* 347 */               AuthRequestListElement.log.error("unable to show confirmation dialog - exception: " + e, e);
/* 348 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 353 */       this.requestToDeleteButton.put(request, button);
/*     */     } 
/* 355 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getEditButton(final AuthorizationRequest request) {
/* 359 */     ClickButtonElement button = (ClickButtonElement)this.requestToEditButton.get(request);
/* 360 */     if (button == null && request.getStatus() == AuthorizationRequest.STATUS_AUTHORIZED) {
/* 361 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 364 */             return AuthRequestListElement.this.context.getLabel("edit");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 369 */               return AuthRequestListElement.this.onEdit(request);
/* 370 */             } catch (Exception e) {
/* 371 */               AuthRequestListElement.log.error("unable to edit request - exception: " + e, e);
/* 372 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 377 */       this.requestToEditButton.put(request, button);
/*     */     } 
/* 379 */     return button;
/*     */   }
/*     */   private HtmlElementContainer getButtonContainer(AuthorizationRequest request) {
/*     */     HtmlElementContainerBase htmlElementContainerBase;
/* 383 */     HtmlElementContainer buttonContainer = (HtmlElementContainer)this.requestToButtonContainer.get(request);
/* 384 */     if (buttonContainer == null) {
/* 385 */       final ClickButtonElement buttonProcess = getProcessButton(request);
/* 386 */       final ClickButtonElement buttonRevoke = getRevokeButton(request);
/* 387 */       final ClickButtonElement buttonDelete = getDeleteButton(request);
/* 388 */       final ClickButtonElement buttonEdit = getEditButton(request);
/*     */       
/* 390 */       htmlElementContainerBase = new HtmlElementContainerBase()
/*     */         {
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
/*     */           public String getHtmlCode(Map params)
/*     */           {
/* 405 */             StringBuffer tmp = new StringBuffer();
/* 406 */             tmp.append("<table cellpadding=\"0\" cellspacing=\"1\"><tr>");
/* 407 */             if (buttonProcess != null) {
/* 408 */               tmp.append("<td>");
/* 409 */               tmp.append(buttonProcess.getHtmlCode(params));
/* 410 */               tmp.append("</td>");
/*     */             } 
/*     */             
/* 413 */             tmp.append("<td>");
/* 414 */             tmp.append(buttonDelete.getHtmlCode(params));
/* 415 */             tmp.append("</td>");
/* 416 */             if (buttonRevoke != null) {
/* 417 */               tmp.append("<td>");
/* 418 */               tmp.append(buttonRevoke.getHtmlCode(params));
/* 419 */               tmp.append("</td>");
/*     */             } 
/* 421 */             if (buttonEdit != null) {
/* 422 */               tmp.append("<td>");
/* 423 */               tmp.append(buttonEdit.getHtmlCode(params));
/* 424 */               tmp.append("</td>");
/*     */             } 
/* 426 */             tmp.append("</tr></table>");
/* 427 */             return tmp.toString();
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 432 */       this.requestToButtonContainer.put(request, htmlElementContainerBase);
/*     */     } 
/* 434 */     return (HtmlElementContainer)htmlElementContainerBase;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     try {
/* 439 */       AuthorizationRequest request = (AuthorizationRequest)data;
/* 440 */       switch (columnIndex) {
/*     */         case 0:
/* 442 */           if (RequestType.STANDARD.equals(request.getType()))
/* 443 */             return (HtmlElement)this.iconTypeRegistration; 
/* 444 */           if (RequestType.HWKMIGRATION.equals(request.getType()))
/* 445 */             return (HtmlElement)this.iconTypeMigration; 
/* 446 */           if (RequestType.EXTENSION.equals(request.getType()))
/* 447 */             return (HtmlElement)this.iconTypeExtension; 
/* 448 */           if (RequestType.REPEAT.equals(request.getType())) {
/* 449 */             return (HtmlElement)this.iconTypeRepeat;
/*     */           }
/* 451 */           return (HtmlElement)new HtmlLabel("-");
/*     */ 
/*     */         
/*     */         case 4:
/* 455 */           return (HtmlElement)new HtmlLabel(DateConvert.toDateString(request.getRequestDate(), DATE_FORMAT));
/*     */         case 1:
/* 457 */           return (HtmlElement)new HtmlLabel(request.getRequestID());
/*     */         case 2:
/* 459 */           return (HtmlElement)new HtmlLabel(request.getDealershipInfo().getDealershipID());
/*     */         case 3:
/* 461 */           return (HtmlElement)new HtmlLabel(request.getDealershipInfo().getDealership());
/*     */         case 5:
/* 463 */           return (HtmlElement)getButtonContainer(request);
/*     */       } 
/* 465 */       throw new IllegalArgumentException();
/*     */     }
/* 467 */     catch (Exception e) {
/* 468 */       log.warn("unable to determine content display, showing \"-\" - exception:" + e, e);
/* 469 */       return (HtmlElement)new HtmlLabel("-");
/*     */     } 
/*     */   }
/*     */   
/* 473 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 475 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 477 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 479 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 481 */   private static final Map ATTRIBUTES_DETAIL_CELL = new HashMap<Object, Object>();
/*     */   
/* 483 */   private static final Map ATTRIBUTES_TYPE_CELL = new HashMap<Object, Object>();
/*     */   
/* 485 */   private static final Map ATTRIBUTES_DATE_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 488 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 489 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 491 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 492 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 494 */     ATTRIBUTES_HEADER.put("class", "header");
/* 495 */     ATTRIBUTES_HEADER.put("align", "center");
/*     */     
/* 497 */     ATTRIBUTES_DETAIL_CELL.put("align", "center");
/* 498 */     ATTRIBUTES_DATE_CELL.put("align", "center");
/* 499 */     ATTRIBUTES_TYPE_CELL.put("align", "center");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 504 */     if (columnIndex == 5) {
/* 505 */       map.putAll(ATTRIBUTES_DETAIL_CELL);
/* 506 */     } else if (columnIndex == 4) {
/* 507 */       map.putAll(ATTRIBUTES_DATE_CELL);
/* 508 */     } else if (columnIndex == 0) {
/* 509 */       map.putAll(ATTRIBUTES_TYPE_CELL);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 514 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 518 */     if (rowIndex % 2 == 0) {
/* 519 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 521 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 526 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   private Object onProcess(AuthorizationRequest request) {
/* 530 */     final HtmlElementContainer topLevelElement = getTopLevelContainer();
/*     */     
/* 532 */     AuthReqDetailDialog detailDlg = new AuthReqDetailDialog(this.context, request)
/*     */       {
/*     */         protected Object onClose() {
/* 535 */           MainPanel.getInstance(AuthRequestListElement.this.context).update();
/* 536 */           return topLevelElement;
/*     */         }
/*     */       };
/*     */     
/* 540 */     return detailDlg;
/*     */   }
/*     */   
/*     */   private Object onEdit(AuthorizationRequest request) {
/* 544 */     final HtmlElementContainer topLevelElement = getTopLevelContainer();
/*     */     
/* 546 */     AuthReqDetailDialog detailDlg = new AuthReqDetailDialog(this.context, request)
/*     */       {
/*     */         protected Object onClose() {
/* 549 */           MainPanel.getInstance(AuthRequestListElement.this.context).update();
/* 550 */           return topLevelElement;
/*     */         }
/*     */       };
/*     */     
/* 554 */     return detailDlg;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\server\AuthRequestListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */