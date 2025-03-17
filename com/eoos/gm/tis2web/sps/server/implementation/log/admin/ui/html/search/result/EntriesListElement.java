/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.search.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Attachment;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
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
/*  36 */   private static final Logger log = Logger.getLogger(EntriesListElement.class);
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_DATE = 1;
/*     */   
/*     */   private static final int INDEX_EVENTNAME = 2;
/*     */   
/*     */   private static final int INDEX_ADAPTER = 3;
/*     */   
/*     */   private static final int INDEX_DETAILS = 4;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   
/*     */   private LinkElement headerEventname;
/*     */   
/*     */   private LinkElement headerAdapter;
/*     */   
/*     */   private HtmlElement headerDetails;
/*     */   
/*  58 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  62 */   private Map entryToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  64 */   private Map entryToDetailButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  66 */   private Map attachmentButtons = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  68 */   private Map entryToButtonContainer = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */   
/*  72 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public EntriesListElement(final List entries, final ClientContext context) {
/*  76 */     if (entries == null || entries.size() == 0) {
/*  77 */       throw new IllegalArgumentException();
/*     */     }
/*  79 */     this.context = context;
/*  80 */     this.entries = entries;
/*  81 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  84 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  89 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/*  91 */     this.headerDetails = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/*  93 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  97 */             if (SPSEventLog.LoggedEntry.COMPARATOR_DATE.equals(EntriesListElement.this.comparator)) {
/*  98 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 100 */               EntriesListElement.this.comparator = (Comparator)SPSEventLog.LoggedEntry.COMPARATOR_DATE;
/*     */             } 
/* 102 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 103 */           } catch (Exception e) {
/* 104 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 106 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 111 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 115 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 117 */     this.headerEventname = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 121 */             if (SPSEventLog.Entry.COMPARATOR_EVENTNAME.equals(EntriesListElement.this.comparator)) {
/* 122 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 124 */               EntriesListElement.this.comparator = (Comparator)SPSEventLog.Entry.COMPARATOR_EVENTNAME;
/*     */             } 
/* 126 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 127 */           } catch (Exception e) {
/* 128 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 130 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 135 */           return context.getLabel("eventname");
/*     */         }
/*     */       };
/*     */     
/* 139 */     addElement((HtmlElement)this.headerEventname);
/*     */     
/* 141 */     this.headerAdapter = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 145 */             if (SPSEventLog.Entry.COMPARATOR_ADAPTER.equals(EntriesListElement.this.comparator)) {
/* 146 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 148 */               EntriesListElement.this.comparator = (Comparator)SPSEventLog.Entry.COMPARATOR_ADAPTER;
/*     */             } 
/* 150 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 151 */           } catch (Exception e) {
/* 152 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 154 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 159 */           return context.getLabel("adapter");
/*     */         }
/*     */       };
/*     */     
/* 163 */     addElement((HtmlElement)this.headerAdapter);
/*     */     
/* 165 */     this.comparator = (Comparator)SPSEventLog.LoggedEntry.COMPARATOR_DATE;
/* 166 */     Collections.sort(this.entries, this.comparator);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 170 */     return 5;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 174 */     switch (columnIndex) {
/*     */       case 0:
/* 176 */         return this.headerActionMarker;
/*     */       case 1:
/* 178 */         return (HtmlElement)this.headerDate;
/*     */       case 2:
/* 180 */         return (HtmlElement)this.headerEventname;
/*     */       case 3:
/* 182 */         return (HtmlElement)this.headerAdapter;
/*     */       case 4:
/* 184 */         return this.headerDetails;
/*     */     } 
/* 186 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(SPSEventLog.Entry entry) {
/* 191 */     CheckBoxElement actionMarker = (CheckBoxElement)this.entryToActionMarker.get(entry);
/* 192 */     if (actionMarker == null) {
/* 193 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 194 */       this.entryToActionMarker.put(entry, actionMarker);
/*     */     } 
/* 196 */     return actionMarker;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getDetailButton(final SPSEventLog.Entry entry) {
/* 200 */     ClickButtonElement button = (ClickButtonElement)this.entryToDetailButton.get(entry);
/* 201 */     if (button == null) {
/* 202 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 205 */             return EntriesListElement.this.context.getLabel("detail");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 209 */             EntryDisplayPopup retValue = EntryDisplayPopup.getInstance(EntriesListElement.this.context);
/* 210 */             retValue.setEntry((SPSEventLog.LoggedEntry)entry);
/* 211 */             return retValue;
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 215 */             return "sps.event.log.detail";
/*     */           }
/*     */         };
/*     */       
/* 219 */       this.entryToDetailButton.put(entry, button);
/*     */     } 
/* 221 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getAttachmentButton(final SPSEventLog.Entry entry, final Attachment.Key attachmentKey) {
/* 225 */     Object key = { entry, attachmentKey };
/*     */     
/* 227 */     ClickButtonElement button = (ClickButtonElement)this.attachmentButtons.get(key);
/* 228 */     if (button == null && ((SPSEventLog.LoggedEntry)entry).getAttachmentKeys().contains(attachmentKey)) {
/* 229 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 232 */             if (attachmentKey == Attachment.KEY_VIT1)
/* 233 */               return EntriesListElement.this.context.getLabel("sps.event.log.button.get.vit1"); 
/* 234 */             if (attachmentKey == Attachment.KEY_VIT1_RNR) {
/* 235 */               return EntriesListElement.this.context.getLabel("sps.event.log.button.get.vit1.rnr");
/*     */             }
/* 237 */             return EntriesListElement.this.context.getLabel("attachment") + ": " + EntriesListElement.this.context.getLabel(attachmentKey.toString());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 243 */               Object attachment = SPSEventLogFacade.getInstance().getAttachedObject(entry, attachmentKey);
/* 244 */               ResultObject.FileProperties fp = new ResultObject.FileProperties();
/* 245 */               fp.data = String.valueOf(attachment).getBytes("UTF-8");
/* 246 */               fp.mime = "text/plain;charset=utf-8";
/* 247 */               fp.inline = true;
/* 248 */               return new ResultObject(13, fp);
/* 249 */             } catch (Exception e) {
/* 250 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 255 */             return "sps.event.log.attachment";
/*     */           }
/*     */         };
/*     */       
/* 259 */       this.attachmentButtons.put(key, button);
/*     */     } 
/* 261 */     return button;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 265 */     List<?> retValue = new LinkedList();
/* 266 */     for (Iterator<Map.Entry> iter = this.entryToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 267 */       Map.Entry entry = iter.next();
/* 268 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 269 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 270 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 273 */     Collections.sort(retValue, this.comparator);
/* 274 */     return retValue;
/*     */   }
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     String adapter;
/* 278 */     SPSEventLog.LoggedEntry entry = (SPSEventLog.LoggedEntry)data;
/* 279 */     switch (columnIndex) {
/*     */       case 0:
/* 281 */         return (HtmlElement)getActionMarker((SPSEventLog.Entry)entry);
/*     */       
/*     */       case 1:
/* 284 */         return (HtmlElement)new HtmlLabel(DateConvert.toDateString(entry.getTimestamp(), DATE_FORMAT));
/*     */       case 2:
/* 286 */         return (HtmlElement)new HtmlLabel(entry.getEventName());
/*     */       case 3:
/* 288 */         adapter = (entry.getAdapter() == Adapter.GME) ? this.context.getLabel("sps.adapter.gme") : null;
/* 289 */         if (adapter == null) {
/* 290 */           adapter = (entry.getAdapter() == Adapter.NAO) ? this.context.getLabel("sps.adapter.nao") : this.context.getLabel("sps.adapter.global");
/*     */         }
/* 292 */         return (HtmlElement)new HtmlLabel(adapter);
/*     */       case 4:
/* 294 */         return (HtmlElement)getButtonContainer((SPSEventLog.Entry)entry);
/*     */     } 
/* 296 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   private HtmlElementContainer getButtonContainer(final SPSEventLog.Entry entry) {
/*     */     HtmlElementContainerBase htmlElementContainerBase;
/* 301 */     HtmlElementContainer buttonContainer = (HtmlElementContainer)this.entryToButtonContainer.get(entry);
/* 302 */     if (buttonContainer == null) {
/*     */       
/* 304 */       htmlElementContainerBase = new HtmlElementContainerBase()
/*     */         {
/*     */           private List buttons;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public String getHtmlCode(Map params) {
/* 317 */             StringBuffer tmp = new StringBuffer();
/* 318 */             tmp.append("<table cellpadding=\"0\" cellspacing=\"1\"><tr>");
/* 319 */             for (int i = 0; i < this.buttons.size(); i++) {
/* 320 */               tmp.append("<td>");
/* 321 */               tmp.append(((HtmlElement)this.buttons.get(i)).getHtmlCode(params));
/* 322 */               tmp.append("</td>");
/*     */             } 
/* 324 */             tmp.append("</tr></table>");
/* 325 */             return tmp.toString();
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 330 */       this.entryToButtonContainer.put(entry, htmlElementContainerBase);
/*     */     } 
/* 332 */     return (HtmlElementContainer)htmlElementContainerBase;
/*     */   }
/*     */   
/* 335 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 337 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 339 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 341 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 343 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/* 345 */   private static final Map ATTRIBUTES_DETAIL_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 348 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 349 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 351 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 352 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 354 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 356 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/*     */     
/* 358 */     ATTRIBUTES_DETAIL_CELL.put("align", "center");
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 362 */     if (columnIndex == 0) {
/* 363 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/* 364 */     } else if (columnIndex == 4) {
/* 365 */       map.putAll(ATTRIBUTES_DETAIL_CELL);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 370 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 374 */     if (rowIndex % 2 == 0) {
/* 375 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 377 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 382 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 386 */     for (Iterator<SPSEventLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 387 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 388 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 393 */     for (Iterator<SPSEventLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 394 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 395 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 400 */     for (Iterator<SPSEventLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 401 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 402 */       if (cb.getValue() == Boolean.TRUE) {
/* 403 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 405 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\admi\\ui\html\search\result\EntriesListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */