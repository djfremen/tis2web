/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.search.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog;
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
/*  29 */   private static final Logger log = Logger.getLogger(EntriesListElement.class);
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_DATE = 1;
/*     */   
/*     */   private static final int INDEX_DEVICE = 2;
/*     */   
/*     */   private static final int INDEX_APPLICATION = 3;
/*     */   
/*     */   private static final int INDEX_VERSION = 4;
/*     */   
/*     */   private static final int INDEX_LANGUAGE = 5;
/*     */   
/*     */   private static final int INDEX_SERVER = 6;
/*     */   
/*     */   private static final int INDEX_USERID = 7;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   
/*     */   private LinkElement headerDevice;
/*     */   
/*     */   private LinkElement headerApplication;
/*     */   
/*     */   private LinkElement headerVersion;
/*     */   
/*     */   private LinkElement headerLanguage;
/*     */   
/*     */   private LinkElement headerServerName;
/*     */   
/*     */   private LinkElement headerUserID;
/*     */   
/*  63 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  67 */   private Map entryToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */   
/*  71 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public EntriesListElement(final List entries, final ClientContext context) {
/*  75 */     if (entries == null || entries.size() == 0) {
/*  76 */       throw new IllegalArgumentException();
/*     */     }
/*  78 */     this.context = context;
/*  79 */     this.entries = entries;
/*  80 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  83 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  88 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/*  90 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  94 */             if (SWDLMetricsLog.Entry.COMPARATOR_DATE.equals(EntriesListElement.this.comparator)) {
/*  95 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/*  97 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_DATE;
/*     */             } 
/*  99 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 100 */           } catch (Exception e) {
/* 101 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 103 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 108 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 112 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 114 */     this.headerDevice = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 118 */             if (SWDLMetricsLog.Entry.COMPARATOR_DEVICE.equals(EntriesListElement.this.comparator)) {
/* 119 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 121 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_DEVICE;
/*     */             } 
/* 123 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 124 */           } catch (Exception e) {
/* 125 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 127 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 132 */           return context.getLabel("device");
/*     */         }
/*     */       };
/*     */     
/* 136 */     addElement((HtmlElement)this.headerDevice);
/*     */     
/* 138 */     this.headerApplication = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 142 */             if (SWDLMetricsLog.Entry.COMPARATOR_APPLICATION.equals(EntriesListElement.this.comparator)) {
/* 143 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 145 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_APPLICATION;
/*     */             } 
/* 147 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 148 */           } catch (Exception e) {
/* 149 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 151 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 156 */           return context.getLabel("application");
/*     */         }
/*     */       };
/*     */     
/* 160 */     addElement((HtmlElement)this.headerApplication);
/*     */     
/* 162 */     this.headerVersion = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 166 */             if (SWDLMetricsLog.Entry.COMPARATOR_VERSION.equals(EntriesListElement.this.comparator)) {
/* 167 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 169 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_VERSION;
/*     */             } 
/* 171 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 172 */           } catch (Exception e) {
/* 173 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 175 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 180 */           return context.getLabel("version");
/*     */         }
/*     */       };
/*     */     
/* 184 */     addElement((HtmlElement)this.headerVersion);
/*     */     
/* 186 */     this.headerLanguage = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 190 */             if (SWDLMetricsLog.Entry.COMPARATOR_LANGUAGE.equals(EntriesListElement.this.comparator)) {
/* 191 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 193 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_LANGUAGE;
/*     */             } 
/* 195 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 196 */           } catch (Exception e) {
/* 197 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 199 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 204 */           return context.getLabel("language");
/*     */         }
/*     */       };
/*     */     
/* 208 */     addElement((HtmlElement)this.headerLanguage);
/*     */     
/* 210 */     this.headerServerName = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 214 */             if (SWDLMetricsLog.Entry2.COMPARATOR_SERVERNAME.equals(EntriesListElement.this.comparator)) {
/* 215 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 217 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry2.COMPARATOR_SERVERNAME;
/*     */             } 
/* 219 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 220 */           } catch (Exception e) {
/* 221 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 223 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 228 */           return context.getLabel("server");
/*     */         }
/*     */       };
/*     */     
/* 232 */     addElement((HtmlElement)this.headerServerName);
/*     */     
/* 234 */     this.headerUserID = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 238 */             if (SWDLMetricsLog.Entry2.COMPARATOR_USERID.equals(EntriesListElement.this.comparator)) {
/* 239 */               EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*     */             } else {
/* 241 */               EntriesListElement.this.comparator = (Comparator)SWDLMetricsLog.Entry2.COMPARATOR_USERID;
/*     */             } 
/* 243 */             Collections.sort(entries, EntriesListElement.this.comparator);
/* 244 */           } catch (Exception e) {
/* 245 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 247 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 252 */           return context.getLabel("user.id");
/*     */         }
/*     */       };
/*     */     
/* 256 */     addElement((HtmlElement)this.headerUserID);
/*     */     
/* 258 */     this.comparator = (Comparator)SWDLMetricsLog.Entry.COMPARATOR_DATE;
/* 259 */     Collections.sort(this.entries, this.comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/* 264 */     return 8;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 268 */     switch (columnIndex) {
/*     */       case 0:
/* 270 */         return this.headerActionMarker;
/*     */       case 1:
/* 272 */         return (HtmlElement)this.headerDate;
/*     */       case 2:
/* 274 */         return (HtmlElement)this.headerDevice;
/*     */       case 3:
/* 276 */         return (HtmlElement)this.headerApplication;
/*     */       case 4:
/* 278 */         return (HtmlElement)this.headerVersion;
/*     */       case 5:
/* 280 */         return (HtmlElement)this.headerLanguage;
/*     */       case 6:
/* 282 */         return (HtmlElement)this.headerServerName;
/*     */       case 7:
/* 284 */         return (HtmlElement)this.headerUserID;
/*     */     } 
/* 286 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(SWDLMetricsLog.Entry entry) {
/* 291 */     CheckBoxElement actionMarker = (CheckBoxElement)this.entryToActionMarker.get(entry);
/* 292 */     if (actionMarker == null) {
/* 293 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 294 */       this.entryToActionMarker.put(entry, actionMarker);
/*     */     } 
/* 296 */     return actionMarker;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 300 */     List<?> retValue = new LinkedList();
/* 301 */     for (Iterator<Map.Entry> iter = this.entryToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 302 */       Map.Entry entry = iter.next();
/* 303 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 304 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 305 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 308 */     Collections.sort(retValue, this.comparator);
/* 309 */     return retValue;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 313 */     SWDLMetricsLog.Entry2 entry = (SWDLMetricsLog.Entry2)data;
/* 314 */     switch (columnIndex) {
/*     */       case 0:
/* 316 */         return (HtmlElement)getActionMarker((SWDLMetricsLog.Entry)entry);
/*     */       
/*     */       case 1:
/* 319 */         return (HtmlElement)new HtmlLabel(DateConvert.toDateString(entry.getTimestamp(), DATE_FORMAT));
/*     */       case 2:
/* 321 */         return (HtmlElement)new HtmlLabel(entry.getDevice());
/*     */       case 3:
/* 323 */         return (HtmlElement)new HtmlLabel(entry.getApplication());
/*     */       case 4:
/* 325 */         return (HtmlElement)new HtmlLabel(entry.getVersion());
/*     */       case 5:
/* 327 */         return (HtmlElement)new HtmlLabel(entry.getLanguage());
/*     */       case 6:
/* 329 */         return (HtmlElement)new HtmlLabel(entry.getServerName());
/*     */       case 7:
/* 331 */         return (HtmlElement)new HtmlLabel(entry.getUserID());
/*     */     } 
/* 333 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 337 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 339 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 341 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 343 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 345 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
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
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 361 */     if (columnIndex == 0) {
/* 362 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 367 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 371 */     if (rowIndex % 2 == 0) {
/* 372 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 374 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 379 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 383 */     for (Iterator<SWDLMetricsLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 384 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 385 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 390 */     for (Iterator<SWDLMetricsLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 391 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 392 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 397 */     for (Iterator<SWDLMetricsLog.Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/* 398 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 399 */       if (cb.getValue() == Boolean.TRUE) {
/* 400 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 402 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\admi\\ui\html\search\result\EntriesListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */