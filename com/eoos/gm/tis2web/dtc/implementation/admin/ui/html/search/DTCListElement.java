/*     */ package com.eoos.gm.tis2web.dtc.implementation.admin.ui.html.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
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
/*     */ public class DTCListElement
/*     */   extends ListElement {
/*  29 */   private static final Logger log = Logger.getLogger(DTCListElement.class);
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_DATE = 1;
/*     */   
/*     */   private static final int INDEX_BACCODE = 2;
/*     */   
/*     */   private static final int INDEX_COUNTRYCODE = 3;
/*     */   
/*     */   private static final int INDEX_DETAILS = 4;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   
/*     */   private LinkElement headerBACCode;
/*     */   
/*     */   private LinkElement headerCountryCode;
/*     */   
/*     */   private HtmlElement headerGetContent;
/*     */   
/*  51 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  55 */   private Map entryToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  57 */   private Map entryToGetContentButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */   
/*  61 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public DTCListElement(final List entries, final ClientContext context) {
/*  65 */     if (entries == null || entries.size() == 0) {
/*  66 */       throw new IllegalArgumentException();
/*     */     }
/*  68 */     this.context = context;
/*  69 */     this.entries = entries;
/*  70 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  73 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  78 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/*  80 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  84 */             if (DTC.Logged.COMPARATOR_TIMESTAMP.equals(DTCListElement.this.comparator)) {
/*  85 */               DTCListElement.this.comparator = Util.reverseComparator(DTCListElement.this.comparator);
/*     */             } else {
/*  87 */               DTCListElement.this.comparator = (Comparator)DTC.Logged.COMPARATOR_TIMESTAMP;
/*     */             } 
/*  89 */             Collections.sort(entries, DTCListElement.this.comparator);
/*  90 */           } catch (Exception e) {
/*  91 */             DTCListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/*  93 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/*  98 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 102 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 104 */     this.headerBACCode = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 108 */             if (DTC.COMPARATOR_BACCode.equals(DTCListElement.this.comparator)) {
/* 109 */               DTCListElement.this.comparator = Util.reverseComparator(DTCListElement.this.comparator);
/*     */             } else {
/* 111 */               DTCListElement.this.comparator = (Comparator)DTC.COMPARATOR_BACCode;
/*     */             } 
/* 113 */             Collections.sort(entries, DTCListElement.this.comparator);
/* 114 */           } catch (Exception e) {
/* 115 */             DTCListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 117 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 122 */           return context.getLabel("dealercode");
/*     */         }
/*     */       };
/*     */     
/* 126 */     addElement((HtmlElement)this.headerBACCode);
/*     */     
/* 128 */     this.headerCountryCode = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 132 */             if (DTC.COMPARATOR_COUNTRYCode.equals(DTCListElement.this.comparator)) {
/* 133 */               DTCListElement.this.comparator = Util.reverseComparator(DTCListElement.this.comparator);
/*     */             } else {
/* 135 */               DTCListElement.this.comparator = (Comparator)DTC.COMPARATOR_COUNTRYCode;
/*     */             } 
/* 137 */             Collections.sort(entries, DTCListElement.this.comparator);
/* 138 */           } catch (Exception e) {
/* 139 */             DTCListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 141 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 146 */           return context.getLabel("countrycode");
/*     */         }
/*     */       };
/*     */     
/* 150 */     addElement((HtmlElement)this.headerCountryCode);
/*     */     
/* 152 */     this.headerGetContent = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/* 154 */     this.comparator = (Comparator)DTC.Logged.COMPARATOR_TIMESTAMP;
/* 155 */     Collections.sort(this.entries, this.comparator);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 159 */     return 5;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 163 */     switch (columnIndex) {
/*     */       case 0:
/* 165 */         return this.headerActionMarker;
/*     */       case 1:
/* 167 */         return (HtmlElement)this.headerDate;
/*     */       case 2:
/* 169 */         return (HtmlElement)this.headerBACCode;
/*     */       case 3:
/* 171 */         return (HtmlElement)this.headerCountryCode;
/*     */       case 4:
/* 173 */         return this.headerGetContent;
/*     */     } 
/* 175 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(DTC dtc) {
/* 180 */     CheckBoxElement actionMarker = (CheckBoxElement)this.entryToActionMarker.get(dtc);
/* 181 */     if (actionMarker == null) {
/* 182 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 183 */       this.entryToActionMarker.put(dtc, actionMarker);
/*     */     } 
/* 185 */     return actionMarker;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getContentButton(final DTC dtc) {
/* 189 */     ClickButtonElement button = (ClickButtonElement)this.entryToGetContentButton.get(dtc);
/* 190 */     if (button == null) {
/* 191 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 194 */             return DTCListElement.this.context.getLabel("content");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 199 */               ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 200 */               props.data = dtc.getContent();
/* 201 */               props.filename = "dtc-" + dtc.getBACCode() + ".dat";
/* 202 */               props.mime = "application/octet-stream";
/* 203 */               props.inline = false;
/* 204 */               return new ResultObject(13, false, false, props);
/* 205 */             } catch (Exception e) {
/* 206 */               DTCListElement.log.error("unable to deliver dtc content - exception: " + e, e);
/* 207 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 212 */       this.entryToGetContentButton.put(dtc, button);
/*     */     } 
/* 214 */     return button;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 218 */     List<?> retValue = new LinkedList();
/* 219 */     for (Iterator<Map.Entry> iter = this.entryToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 220 */       Map.Entry entry = iter.next();
/* 221 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 222 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 223 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 226 */     Collections.sort(retValue, this.comparator);
/* 227 */     return retValue;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 231 */     DTC dtc = (DTC)data;
/* 232 */     DTC.Logged loggedDTC = (DTC.Logged)dtc;
/* 233 */     switch (columnIndex) {
/*     */       case 0:
/* 235 */         return (HtmlElement)getActionMarker(dtc);
/*     */       
/*     */       case 1:
/* 238 */         return (HtmlElement)new HtmlLabel(DateConvert.toDateString(loggedDTC.getTimestamp(), DATE_FORMAT));
/*     */       case 2:
/* 240 */         return (HtmlElement)new HtmlLabel(dtc.getBACCode());
/*     */       case 4:
/* 242 */         return (HtmlElement)getContentButton(dtc);
/*     */       case 3:
/* 244 */         return (HtmlElement)new HtmlLabel(dtc.getCountryCode());
/*     */     } 
/* 246 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 250 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 252 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 254 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 256 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 258 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/* 260 */   private static final Map ATTRIBUTES_DETAIL_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 263 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 264 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 266 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 267 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 269 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 271 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/*     */     
/* 273 */     ATTRIBUTES_DETAIL_CELL.put("align", "center");
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 277 */     if (columnIndex == 0) {
/* 278 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/* 279 */     } else if (columnIndex == 4) {
/* 280 */       map.putAll(ATTRIBUTES_DETAIL_CELL);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 285 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 289 */     if (rowIndex % 2 == 0) {
/* 290 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 292 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 297 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 301 */     for (Iterator<DTC> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 302 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 303 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 308 */     for (Iterator<DTC> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 309 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 310 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 315 */     for (Iterator<DTC> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 316 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 317 */       if (cb.getValue() == Boolean.TRUE) {
/* 318 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 320 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\admi\\ui\html\search\DTCListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */