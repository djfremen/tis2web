/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.login.ui.html.search.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLog;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntriesListElement
/*     */   extends ListElement
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(EntriesListElement.class);
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_DATE = 1;
/*     */   
/*     */   private static final int INDEX_USER = 2;
/*     */   
/*     */   private static final int INDEX_STATUS = 3;
/*     */   
/*     */   private static final int INDEX_SRC_ADDRESS = 4;
/*     */   
/*     */   private static final int INDEX_FREEPARAM = 5;
/*     */   
/*     */   private static final int INDEX_ORIGIN = 6;
/*     */   
/*     */   private static final int INDEX_GROUP = 7;
/*     */   
/*     */   private static final int INDEX_DEALERCODE = 8;
/*     */   
/*     */   private static final int INDEX_SERVER = 9;
/*     */   
/*     */   private static final int INDEX_DIVISIONCODE = 10;
/*     */   
/*     */   private static final int INDEX_ORGCOUNTRY = 11;
/*     */   
/*     */   private static final int INDEX_MAPPEDCOUNTRY = 12;
/*     */   
/*     */   private static final int INDEX_T2WGROUP = 13;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   private LinkElement headerUsername;
/*     */   private LinkElement headerStatus;
/*     */   private LinkElement headerSourceAddress;
/*     */   private LinkElement headerOrigin;
/*     */   private LinkElement headerUserGroup;
/*     */   private LinkElement headerDealerCode;
/*     */   private LinkElement headerServerName;
/*     */   private LinkElement headerFreeParam;
/*     */   private LinkElement headerDivisionCode;
/*     */   private LinkElement headerOrgCountry;
/*     */   private LinkElement headerMappedCountry;
/*     */   private LinkElement headerT2WGroup;
/*  75 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  79 */   private Map entryToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */   
/*  83 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public EntriesListElement(final List entries, final ClientContext context) {
/*  87 */     if (entries == null || entries.size() == 0) {
/*  88 */       throw new IllegalArgumentException();
/*     */     }
/*  90 */     this.context = context;
/*  91 */     this.entries = entries;
/*  92 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  95 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 100 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */     
/* 102 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 106 */             if (LoginLog.Entry.COMPARATOR_DATE.equals(EntriesListElement.this.comparator)) {
/* 107 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 109 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_DATE;
/*     */             } 
/* 111 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 112 */           } catch (Exception e) {
/* 113 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 115 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 120 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 124 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 126 */     this.headerUsername = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 130 */             if (LoginLog.Entry.COMPARATOR_USERNAME.equals(EntriesListElement.this.comparator)) {
/* 131 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 133 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_USERNAME;
/*     */             } 
/* 135 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 136 */           } catch (Exception e) {
/* 137 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 139 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 144 */           return context.getLabel("username");
/*     */         }
/*     */       };
/*     */     
/* 148 */     addElement((HtmlElement)this.headerUsername);
/*     */     
/* 150 */     this.headerStatus = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 154 */             if (LoginLog.Entry.COMPARATOR_SUCCESS.equals(EntriesListElement.this.comparator)) {
/* 155 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 157 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_SUCCESS;
/*     */             } 
/* 159 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 160 */           } catch (Exception e) {
/* 161 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 163 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 168 */           return context.getLabel("status");
/*     */         }
/*     */       };
/*     */     
/* 172 */     addElement((HtmlElement)this.headerStatus);
/*     */     
/* 174 */     this.headerSourceAddress = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 178 */             if (LoginLog.Entry.COMPARATOR_SRC_ADDRESS.equals(EntriesListElement.this.comparator)) {
/* 179 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 181 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_SRC_ADDRESS;
/*     */             } 
/* 183 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 184 */           } catch (Exception e) {
/* 185 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 187 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 192 */           return context.getLabel("source.address");
/*     */         }
/*     */       };
/*     */     
/* 196 */     addElement((HtmlElement)this.headerSourceAddress);
/*     */     
/* 198 */     this.headerOrigin = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 202 */             if (LoginLog.Entry.COMPARATOR_ORIGIN.equals(EntriesListElement.this.comparator)) {
/* 203 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 205 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_ORIGIN;
/*     */             } 
/* 207 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 208 */           } catch (Exception e) {
/* 209 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 211 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 216 */           return context.getLabel("origin");
/*     */         }
/*     */       };
/*     */     
/* 220 */     addElement((HtmlElement)this.headerOrigin);
/*     */     
/* 222 */     this.headerOrigin = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 226 */             if (LoginLog.Entry.COMPARATOR_ORIGIN.equals(EntriesListElement.this.comparator)) {
/* 227 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 229 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_ORIGIN;
/*     */             } 
/* 231 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 232 */           } catch (Exception e) {
/* 233 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 235 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 240 */           return context.getLabel("origin");
/*     */         }
/*     */       };
/*     */     
/* 244 */     addElement((HtmlElement)this.headerOrigin);
/*     */     
/* 246 */     this.headerUserGroup = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 250 */             if (LoginLog.Entry.COMPARATOR_USERGROUP.equals(EntriesListElement.this.comparator)) {
/* 251 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 253 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_USERGROUP;
/*     */             } 
/* 255 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 256 */           } catch (Exception e) {
/* 257 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 259 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 264 */           return context.getLabel("usergroup");
/*     */         }
/*     */       };
/*     */     
/* 268 */     addElement((HtmlElement)this.headerUserGroup);
/*     */     
/* 270 */     this.headerDealerCode = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 274 */             if (LoginLog.Entry.COMPARATOR_DEALERCODE.equals(EntriesListElement.this.comparator)) {
/* 275 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 277 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_DEALERCODE;
/*     */             } 
/* 279 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 280 */           } catch (Exception e) {
/* 281 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 283 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 288 */           return context.getLabel("dealercode");
/*     */         }
/*     */       };
/*     */     
/* 292 */     addElement((HtmlElement)this.headerDealerCode);
/*     */     
/* 294 */     this.headerServerName = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 298 */             if (LoginLog.Entry2.COMPARATOR_SERVERNAME.equals(EntriesListElement.this.comparator)) {
/* 299 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 301 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_SERVERNAME;
/*     */             } 
/* 303 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 304 */           } catch (Exception e) {
/* 305 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 307 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 312 */           return context.getLabel("server");
/*     */         }
/*     */       };
/*     */     
/* 316 */     addElement((HtmlElement)this.headerServerName);
/*     */     
/* 318 */     String tmp = ApplicationContext.getInstance().getProperty("frame.scout.nao.login.logparam");
/* 319 */     final String freeParamDisplay = (tmp != null) ? tmp : " - ";
/* 320 */     this.headerFreeParam = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 324 */             if (LoginLog.Entry2.COMPARATOR_FREEPARAM.equals(EntriesListElement.this.comparator)) {
/* 325 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 327 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_FREEPARAM;
/*     */             } 
/* 329 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 330 */           } catch (Exception e) {
/* 331 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 333 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 338 */           return freeParamDisplay;
/*     */         }
/*     */       };
/*     */     
/* 342 */     addElement((HtmlElement)this.headerFreeParam);
/*     */     
/* 344 */     this.headerDivisionCode = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 348 */             if (LoginLog.Entry2.COMPARATOR_DIVISIONCODE.equals(EntriesListElement.this.comparator)) {
/* 349 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 351 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_DIVISIONCODE;
/*     */             } 
/* 353 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 354 */           } catch (Exception e) {
/* 355 */             EntriesListElement.log.error("...unable to sort - exception: " + e, e);
/*     */           } 
/* 357 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 362 */           return context.getLabel("division.code");
/*     */         }
/*     */       };
/*     */     
/* 366 */     addElement((HtmlElement)this.headerDivisionCode);
/*     */     
/* 368 */     this.headerOrgCountry = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 372 */             if (LoginLog.Entry2.COMPARATOR_ORGCOUNTRY.equals(EntriesListElement.this.comparator)) {
/* 373 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 375 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_ORGCOUNTRY;
/*     */             } 
/* 377 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 378 */           } catch (Exception e) {
/* 379 */             EntriesListElement.log.error("...unable to sort - exception: " + e, e);
/*     */           } 
/* 381 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 386 */           return context.getLabel("org.country.code");
/*     */         }
/*     */       };
/*     */     
/* 390 */     addElement((HtmlElement)this.headerOrgCountry);
/*     */     
/* 392 */     this.headerMappedCountry = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 396 */             if (LoginLog.Entry2.COMPARATOR_MAPPEDCOUNTRY.equals(EntriesListElement.this.comparator)) {
/* 397 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 399 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_MAPPEDCOUNTRY;
/*     */             } 
/* 401 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 402 */           } catch (Exception e) {
/* 403 */             EntriesListElement.log.error("...unable to sort - exception: " + e, e);
/*     */           } 
/* 405 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 410 */           return context.getLabel("mapped.country.code");
/*     */         }
/*     */       };
/*     */     
/* 414 */     addElement((HtmlElement)this.headerMappedCountry);
/*     */     
/* 416 */     this.headerT2WGroup = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 420 */             if (LoginLog.Entry2.COMPARATOR_T2WGROUP.equals(EntriesListElement.this.comparator)) {
/* 421 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 423 */               EntriesListElement.this.comparator = (Comparator)LoginLog.Entry2.COMPARATOR_T2WGROUP;
/*     */             } 
/* 425 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 426 */           } catch (Exception e) {
/* 427 */             EntriesListElement.log.error("...unable to sort - exception: " + e, e);
/*     */           } 
/* 429 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 434 */           return context.getLabel("internal.group");
/*     */         }
/*     */       };
/*     */     
/* 438 */     addElement((HtmlElement)this.headerT2WGroup);
/*     */     
/* 440 */     this.comparator = (Comparator)LoginLog.Entry.COMPARATOR_DATE;
/* 441 */     Collections.sort(this.entries, this.comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/* 446 */     return 14;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 450 */     switch (columnIndex) {
/*     */       case 0:
/* 452 */         return this.headerActionMarker;
/*     */       case 1:
/* 454 */         return (HtmlElement)this.headerDate;
/*     */       case 2:
/* 456 */         return (HtmlElement)this.headerUsername;
/*     */       case 3:
/* 458 */         return (HtmlElement)this.headerStatus;
/*     */       case 4:
/* 460 */         return (HtmlElement)this.headerSourceAddress;
/*     */       case 6:
/* 462 */         return (HtmlElement)this.headerOrigin;
/*     */       case 7:
/* 464 */         return (HtmlElement)this.headerUserGroup;
/*     */       case 8:
/* 466 */         return (HtmlElement)this.headerDealerCode;
/*     */       case 9:
/* 468 */         return (HtmlElement)this.headerServerName;
/*     */       case 5:
/* 470 */         return (HtmlElement)this.headerFreeParam;
/*     */       case 10:
/* 472 */         return (HtmlElement)this.headerDivisionCode;
/*     */       case 11:
/* 474 */         return (HtmlElement)this.headerOrgCountry;
/*     */       case 12:
/* 476 */         return (HtmlElement)this.headerMappedCountry;
/*     */       case 13:
/* 478 */         return (HtmlElement)this.headerT2WGroup;
/*     */     } 
/* 480 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(LoginLog.Entry entry) {
/* 485 */     CheckBoxElement actionMarker = (CheckBoxElement)this.entryToActionMarker.get(entry);
/* 486 */     if (actionMarker == null) {
/* 487 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 488 */       this.entryToActionMarker.put(entry, actionMarker);
/*     */     } 
/* 490 */     return actionMarker;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 494 */     List<?> retValue = new LinkedList();
/* 495 */     for (Iterator<Map.Entry> iter = this.entryToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 496 */       Map.Entry entry = iter.next();
/* 497 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 498 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 499 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 502 */     Collections.sort(retValue, this.comparator);
/*     */     
/* 504 */     return retValue;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 508 */     LoginLog.Entry2 entry = (LoginLog.Entry2)data;
/* 509 */     switch (columnIndex) {
/*     */       case 0:
/* 511 */         return (HtmlElement)getActionMarker((LoginLog.Entry)entry);
/*     */       
/*     */       case 1:
/* 514 */         return (HtmlElement)new HtmlLabel(DateConvert.toDateString(entry.getTimestamp(), DATE_FORMAT));
/*     */       case 2:
/* 516 */         return (HtmlElement)new HtmlLabel(entry.getUsername());
/*     */       case 3:
/* 518 */         return (HtmlElement)new HtmlLabel(entry.successfulLogin() ? "allow" : "deny");
/*     */       case 4:
/* 520 */         return (HtmlElement)new HtmlLabel(entry.getSourceAddress());
/*     */       case 6:
/* 522 */         return (HtmlElement)new HtmlLabel(entry.getOrigin());
/*     */       case 7:
/* 524 */         return (HtmlElement)new HtmlLabel(entry.getUserGroup());
/*     */       case 8:
/* 526 */         return (HtmlElement)new HtmlLabel(entry.getDealerCode());
/*     */       case 9:
/* 528 */         return (HtmlElement)new HtmlLabel(entry.getServerName());
/*     */       case 5:
/* 530 */         return (HtmlElement)new HtmlLabel(entry.getFreeParameter());
/*     */       case 10:
/* 532 */         return (HtmlElement)new HtmlLabel(entry.getDivisionCode());
/*     */       case 11:
/* 534 */         return (HtmlElement)new HtmlLabel(entry.getOriginalCountryCode());
/*     */       case 12:
/* 536 */         return (HtmlElement)new HtmlLabel(entry.getMappedCountryCode());
/*     */       case 13:
/* 538 */         return (HtmlElement)new HtmlLabel(entry.getT2WGroup());
/*     */     } 
/* 540 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 544 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 546 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 548 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 550 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 552 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 555 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 556 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 558 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 559 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 561 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 563 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 568 */     if (columnIndex == 0) {
/* 569 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 574 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 578 */     if (rowIndex % 2 == 0) {
/* 579 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 581 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 586 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 590 */     for (Iterator<LoginLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 591 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 592 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 597 */     for (Iterator<LoginLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 598 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 599 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 604 */     for (Iterator<LoginLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 605 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 606 */       if (cb.getValue() == Boolean.TRUE) {
/* 607 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 609 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logi\\ui\html\search\result\EntriesListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */