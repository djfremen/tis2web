/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.config.ui.html.main;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ConfigurationListElement
/*     */   extends ListElement {
/*  26 */   private static final Logger log = Logger.getLogger(ConfigurationListElement.class);
/*     */   
/*  28 */   private static final Comparator COMPARATOR_KEY = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/*  31 */         return Util.compare(((Pair)o1).getFirst(), ((Pair)o2).getFirst());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  36 */   private static final Comparator COMPARATOR_VALUE = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/*  39 */         return Util.compare(((Pair)o1).getSecond(), ((Pair)o2).getSecond());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private LinkElement headerKey;
/*     */   
/*     */   private LinkElement headerValue;
/*     */   
/*  48 */   private Comparator comparator = null;
/*     */ 
/*     */   
/*     */   private List entries;
/*     */ 
/*     */   
/*     */   public ConfigurationListElement(Configuration configuration, final ClientContext context, Filter valueFilter, Filter keyFilter) {
/*  55 */     if (configuration == null) {
/*  56 */       this.entries = Collections.EMPTY_LIST;
/*     */     } else {
/*  58 */       this.entries = new LinkedList();
/*  59 */       for (Iterator<String> iter = configuration.getKeys().iterator(); iter.hasNext(); ) {
/*  60 */         String key = iter.next();
/*  61 */         if (keyFilter.include(key)) {
/*  62 */           String value = configuration.getProperty(key);
/*  63 */           if (valueFilter.include(value)) {
/*  64 */             this.entries.add(new PairImpl(key, value));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  69 */     this.comparator = COMPARATOR_KEY;
/*  70 */     Collections.sort(this.entries, this.comparator);
/*     */     
/*  72 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  75 */             return ConfigurationListElement.this.entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  80 */     this.headerKey = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  84 */             if (ConfigurationListElement.COMPARATOR_KEY.equals(ConfigurationListElement.this.comparator)) {
/*  85 */               ConfigurationListElement.this.comparator = Util.reverseComparator(ConfigurationListElement.this.comparator);
/*     */             } else {
/*  87 */               ConfigurationListElement.this.comparator = ConfigurationListElement.COMPARATOR_KEY;
/*     */             } 
/*  89 */             Collections.sort(ConfigurationListElement.this.entries, ConfigurationListElement.this.comparator);
/*  90 */           } catch (Exception e) {
/*  91 */             ConfigurationListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/*  93 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/*  98 */           return context.getLabel("key");
/*     */         }
/*     */       };
/*     */     
/* 102 */     addElement((HtmlElement)this.headerKey);
/*     */     
/* 104 */     this.headerValue = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 108 */             if (ConfigurationListElement.COMPARATOR_VALUE.equals(ConfigurationListElement.this.comparator)) {
/* 109 */               ConfigurationListElement.this.comparator = Util.reverseComparator(ConfigurationListElement.this.comparator);
/*     */             } else {
/* 111 */               ConfigurationListElement.this.comparator = ConfigurationListElement.COMPARATOR_VALUE;
/*     */             } 
/* 113 */             Collections.sort(ConfigurationListElement.this.entries, ConfigurationListElement.this.comparator);
/* 114 */           } catch (Exception e) {
/* 115 */             ConfigurationListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 117 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 122 */           return context.getLabel("value");
/*     */         }
/*     */       };
/*     */     
/* 126 */     addElement((HtmlElement)this.headerValue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/* 131 */     return 2;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 135 */     switch (columnIndex) {
/*     */       case 0:
/* 137 */         return (HtmlElement)this.headerKey;
/*     */       case 1:
/* 139 */         return (HtmlElement)this.headerValue;
/*     */     } 
/* 141 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 146 */     Pair entry = (Pair)data;
/* 147 */     String key = String.valueOf(entry.getFirst());
/* 148 */     String value = String.valueOf(entry.getSecond());
/* 149 */     if (ConfigurationUtil.isPossiblyPasswordKey(key)) {
/* 150 */       value = "********";
/*     */     }
/* 152 */     switch (columnIndex) {
/*     */       case 0:
/* 154 */         return (HtmlElement)new HtmlLabel(key);
/*     */       
/*     */       case 1:
/* 157 */         return (HtmlElement)new HtmlLabel(value);
/*     */     } 
/* 159 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 163 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 165 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 167 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 169 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 172 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 173 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 175 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 176 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 178 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 186 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 190 */     if (rowIndex % 2 == 0) {
/* 191 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 193 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 198 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\confi\\ui\html\main\ConfigurationListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */