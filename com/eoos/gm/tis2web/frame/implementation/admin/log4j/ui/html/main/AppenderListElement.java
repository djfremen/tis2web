/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.log4j.ui.html.main;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.MultiMapWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.HtmlInputElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AppenderListElement
/*     */   extends ListElement {
/*  30 */   private static final Logger log = Logger.getLogger(AppenderListElement.class);
/*     */   
/*     */   private static final int INDEX_APPENDERNAME = 0;
/*     */   
/*     */   private static final int INDEX_LOGGERNAMES = 1;
/*     */   
/*     */   private static final int INDEX_LEVELSELECTION = 2;
/*     */   
/*     */   private static final int INDEX_PATTERN = 3;
/*     */   
/*     */   private LinkElement headerAppenderName;
/*     */   
/*     */   private LinkElement headerLoggerName;
/*     */   
/*     */   private HtmlElement headerLevelSelection;
/*     */   
/*     */   private HtmlElement headerPattern;
/*     */   
/*  48 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  52 */   private Map entryToLevelSelection = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  54 */   private Map entryToPatternInput = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */   
/*  58 */   private MultiMapWrapper entryMap = new MultiMapWrapper(new LinkedHashMap<Object, Object>());
/*     */ 
/*     */   
/*     */   public AppenderListElement(final ClientContext context) {
/*  62 */     this.context = context;
/*     */     
/*  64 */     for (Enumeration<Logger> enLoggers = Logger.getRootLogger().getLoggerRepository().getCurrentLoggers(); enLoggers.hasMoreElements(); ) {
/*  65 */       Logger logger1 = enLoggers.nextElement();
/*  66 */       for (Enumeration<Appender> enumeration = logger1.getAllAppenders(); enumeration.hasMoreElements(); ) {
/*  67 */         Appender appender = enumeration.nextElement();
/*  68 */         this.entryMap.put(appender, logger1);
/*     */       } 
/*     */     } 
/*  71 */     Logger logger = Logger.getRootLogger();
/*  72 */     for (Enumeration<Appender> enAppenders = logger.getAllAppenders(); enAppenders.hasMoreElements(); ) {
/*  73 */       Appender appender = enAppenders.nextElement();
/*  74 */       this.entryMap.put(appender, logger);
/*     */     } 
/*     */     
/*  77 */     this.entries = new ArrayList(this.entryMap.keySet());
/*     */     
/*  79 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  82 */             return AppenderListElement.this.entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  87 */     this.headerLevelSelection = (HtmlElement)new HtmlLabel(context.getLabel("level.selection"));
/*     */     
/*  89 */     this.headerPattern = (HtmlElement)new HtmlLabel(context.getLabel("pattern"));
/*     */     
/*  91 */     final Comparator comparatorAppenderName = new Comparator()
/*     */       {
/*     */         public int compare(Object o1, Object o2) {
/*  94 */           return Util.compare(((Appender)o1).getName(), ((Appender)o2).getName());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  99 */     this.headerAppenderName = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 103 */             if (comparatorAppenderName.equals(AppenderListElement.this.comparator)) {
/* 104 */               AppenderListElement.this.comparator = Util.reverseComparator(AppenderListElement.this.comparator);
/*     */             } else {
/* 106 */               AppenderListElement.this.comparator = comparatorAppenderName;
/*     */             } 
/* 108 */             Collections.sort(AppenderListElement.this.entries, AppenderListElement.this.comparator);
/* 109 */           } catch (Exception e) {
/* 110 */             AppenderListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 112 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 117 */           return context.getLabel("appender.name");
/*     */         }
/*     */       };
/*     */     
/* 121 */     addElement((HtmlElement)this.headerAppenderName);
/*     */     
/* 123 */     final Comparator comparatorLoggerName = new Comparator()
/*     */       {
/*     */         public int compare(Object o1, Object o2) {
/* 126 */           return Util.compare(AppenderListElement.this.getLoggers((Appender)o1), AppenderListElement.this.getLoggers((Appender)o2));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 131 */     this.headerLoggerName = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 135 */             if (comparatorLoggerName.equals(AppenderListElement.this.comparator)) {
/* 136 */               AppenderListElement.this.comparator = Util.reverseComparator(AppenderListElement.this.comparator);
/*     */             } else {
/* 138 */               AppenderListElement.this.comparator = comparatorLoggerName;
/*     */             } 
/* 140 */             Collections.sort(AppenderListElement.this.entries, AppenderListElement.this.comparator);
/* 141 */           } catch (Exception e) {
/* 142 */             AppenderListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 144 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 149 */           return context.getLabel("loggers");
/*     */         }
/*     */       };
/*     */     
/* 153 */     addElement((HtmlElement)this.headerLoggerName);
/*     */     
/* 155 */     this.comparator = comparatorAppenderName;
/* 156 */     Collections.sort(this.entries, this.comparator);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 160 */     return 4;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 164 */     switch (columnIndex) {
/*     */       case 2:
/* 166 */         return this.headerLevelSelection;
/*     */       case 0:
/* 168 */         return (HtmlElement)this.headerAppenderName;
/*     */       case 1:
/* 170 */         return (HtmlElement)this.headerLoggerName;
/*     */       case 3:
/* 172 */         return this.headerPattern;
/*     */     } 
/* 174 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   private HtmlInputElement getTresholdSelection(Appender appender) {
/*     */     TresholdSelectionElement tresholdSelectionElement;
/* 179 */     HtmlInputElement selectionElement = (HtmlInputElement)this.entryToLevelSelection.get(appender);
/* 180 */     if (selectionElement == null && appender instanceof AppenderSkeleton) {
/* 181 */       tresholdSelectionElement = new TresholdSelectionElement((AppenderSkeleton)appender);
/* 182 */       this.entryToLevelSelection.put(appender, tresholdSelectionElement);
/*     */     } 
/* 184 */     return (HtmlInputElement)tresholdSelectionElement;
/*     */   }
/*     */   private HtmlInputElement getPatternInput(Appender appender) {
/*     */     PatternInputElement patternInputElement;
/* 188 */     HtmlInputElement inputElement = (HtmlInputElement)this.entryToPatternInput.get(appender);
/* 189 */     if (inputElement == null && appender.getLayout() != null) {
/*     */       try {
/* 191 */         patternInputElement = new PatternInputElement(this.context, appender);
/* 192 */         this.entryToPatternInput.put(appender, patternInputElement);
/* 193 */       } catch (IllegalArgumentException e) {
/* 194 */         log.warn("unable to init input element, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/* 197 */     return (HtmlInputElement)patternInputElement;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 201 */     Appender appender = (Appender)data;
/* 202 */     switch (columnIndex) {
/*     */       case 0:
/* 204 */         return (HtmlElement)new HtmlLabel(appender.getName());
/*     */       
/*     */       case 1:
/* 207 */         return (HtmlElement)new HtmlLabel(getLoggers(appender));
/*     */       case 2:
/* 209 */         return (HtmlElement)getTresholdSelection(appender);
/*     */       case 3:
/* 211 */         return (HtmlElement)getPatternInput(appender);
/*     */     } 
/* 213 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 217 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 219 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 221 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 223 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 225 */   private static final Map ATTRIBUTES_TRESHOLD_SEL_CELL = new HashMap<Object, Object>();
/* 226 */   private static final Map ATTRIBUTES_PATTERN_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 229 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 230 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 232 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 233 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 235 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 237 */     ATTRIBUTES_TRESHOLD_SEL_CELL.put("align", "center");
/* 238 */     ATTRIBUTES_PATTERN_CELL.put("align", "center");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 243 */     if (columnIndex == 2) {
/* 244 */       map.putAll(ATTRIBUTES_TRESHOLD_SEL_CELL);
/* 245 */     } else if (columnIndex == 3) {
/* 246 */       map.putAll(ATTRIBUTES_PATTERN_CELL);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 251 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 255 */     if (rowIndex % 2 == 0) {
/* 256 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 258 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 263 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   private String getLoggers(Appender appender) {
/* 267 */     Collection loggers = (Collection)this.entryMap.get(appender);
/* 268 */     if (loggers == null || loggers.size() == 0) {
/* 269 */       return "-";
/*     */     }
/* 271 */     List<String> tmp = new LinkedList();
/* 272 */     for (Iterator<Logger> iter = loggers.iterator(); iter.hasNext();) {
/* 273 */       tmp.add(((Logger)iter.next()).getName());
/*     */     }
/*     */     
/* 276 */     Collections.sort(tmp);
/* 277 */     return CollectionUtil.toString(tmp.iterator(), new CollectionUtil.DelimiterCallback()
/*     */         {
/*     */           public String getStartDelimiter() {
/* 280 */             return null;
/*     */           }
/*     */           
/*     */           public String getSeparator() {
/* 284 */             return ", ";
/*     */           }
/*     */           
/*     */           public String getEndDelimiter() {
/* 288 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\log4\\ui\html\main\AppenderListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */