/*     */ package com.eoos.gm.tis2web.frame.dls.server.admin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.v2.Util;
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
/*     */ public class EntriesListElement
/*     */   extends ListElement {
/*  23 */   private static final Logger log = Logger.getLogger(EntriesListElement.class);
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_USER = 1;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerUsername;
/*     */   
/*  33 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  37 */   private Map entryToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List entries;
/*     */ 
/*     */   
/*     */   public EntriesListElement(final List entries, final ClientContext context) {
/*  43 */     if (entries == null || entries.size() == 0) {
/*  44 */       throw new IllegalArgumentException();
/*     */     }
/*  46 */     this.context = context;
/*  47 */     this.entries = entries;
/*  48 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  51 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  56 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */     
/*  58 */     this.headerUsername = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  62 */             EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator);
/*  63 */             Collections.sort(entries, EntriesListElement.this.comparator);
/*  64 */           } catch (Exception e) {
/*  65 */             EntriesListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/*  67 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/*  72 */           return context.getLabel("user.id");
/*     */         }
/*     */       };
/*     */     
/*  76 */     addElement((HtmlElement)this.headerUsername);
/*     */     
/*  78 */     Collections.sort(this.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/*  83 */     return 2;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  87 */     switch (columnIndex) {
/*     */       case 0:
/*  89 */         return this.headerActionMarker;
/*     */       case 1:
/*  91 */         return (HtmlElement)this.headerUsername;
/*     */     } 
/*  93 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(Object entry) {
/*  98 */     CheckBoxElement actionMarker = (CheckBoxElement)this.entryToActionMarker.get(entry);
/*  99 */     if (actionMarker == null) {
/* 100 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 101 */       this.entryToActionMarker.put(entry, actionMarker);
/*     */     } 
/* 103 */     return actionMarker;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 107 */     List<?> retValue = new LinkedList();
/* 108 */     for (Iterator<Map.Entry> iter = this.entryToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 109 */       Map.Entry entry = iter.next();
/* 110 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 111 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 112 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 115 */     Collections.sort(retValue, this.comparator);
/*     */     
/* 117 */     return retValue;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 121 */     String userID = (String)data;
/* 122 */     switch (columnIndex) {
/*     */       case 0:
/* 124 */         return (HtmlElement)getActionMarker(userID);
/*     */       
/*     */       case 1:
/* 127 */         return (HtmlElement)new HtmlLabel(userID);
/*     */     } 
/* 129 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 133 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 135 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 137 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 139 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 141 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 144 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 145 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 147 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 148 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 150 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 152 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 157 */     if (columnIndex == 0) {
/* 158 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 163 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 167 */     if (rowIndex % 2 == 0) {
/* 168 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 170 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 175 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 179 */     for (Iterator iter = this.entries.iterator(); iter.hasNext(); ) {
/* 180 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 181 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 186 */     for (Iterator iter = this.entries.iterator(); iter.hasNext(); ) {
/* 187 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 188 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 193 */     for (Iterator iter = this.entries.iterator(); iter.hasNext(); ) {
/* 194 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 195 */       if (cb.getValue() == Boolean.TRUE) {
/* 196 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 198 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\EntriesListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */