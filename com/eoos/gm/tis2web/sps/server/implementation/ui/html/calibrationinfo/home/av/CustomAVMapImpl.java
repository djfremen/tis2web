/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomAVMapImpl
/*     */   implements CustomAVMap
/*     */ {
/*     */   public static class Entry
/*     */     implements CustomAVMap.Entry
/*     */   {
/*     */     private Attribute attribute;
/*     */     private Value value;
/*     */     private boolean explicit;
/*     */     
/*     */     public Entry(Attribute attribute, Value value, boolean explicit) {
/*  27 */       this.attribute = attribute;
/*  28 */       this.value = value;
/*  29 */       this.explicit = explicit;
/*     */     }
/*     */     
/*     */     public Attribute getAttribute() {
/*  33 */       return this.attribute;
/*     */     }
/*     */     
/*     */     public Value getValue() {
/*  37 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean isExplicit() {
/*  41 */       return this.explicit;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  46 */   private List entries = new LinkedList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Attribute attribute, Value value) {
/*  53 */     this.entries.add(new Entry(attribute, value, false));
/*     */   }
/*     */   
/*     */   public void remove(Attribute attribute) {
/*  57 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Collection getAttributes() {
/*  61 */     Collection<Attribute> retValue = new HashSet();
/*  62 */     for (Iterator<Entry> iter = this.entries.iterator(); iter.hasNext(); ) {
/*  63 */       Entry entry = iter.next();
/*  64 */       retValue.add(entry.getAttribute());
/*     */     } 
/*     */     
/*  67 */     return retValue;
/*     */   }
/*     */   
/*     */   public Value getValue(Attribute attribute) {
/*  71 */     Value value = null;
/*  72 */     for (Iterator<Entry> iter = this.entries.iterator(); iter.hasNext() && value == null; ) {
/*  73 */       Entry entry = iter.next();
/*  74 */       if (entry.getAttribute().equals(attribute)) {
/*  75 */         value = entry.getValue();
/*     */       }
/*     */     } 
/*  78 */     return value;
/*     */   }
/*     */   
/*     */   public List getExplicitEntries() {
/*  82 */     List retValue = new ArrayList(this.entries);
/*  83 */     Filter filter = new Filter() {
/*     */         public boolean include(Object obj) {
/*  85 */           CustomAVMapImpl.Entry entry = (CustomAVMapImpl.Entry)obj;
/*  86 */           return entry.isExplicit();
/*     */         }
/*     */       };
/*     */     
/*  90 */     CollectionUtil.filter(retValue, filter);
/*  91 */     return retValue;
/*     */   }
/*     */   
/*     */   public void explicitSet(Attribute attribute, Value value) {
/*  95 */     this.entries.add(new Entry(attribute, value, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public void exchange(Attribute attribute, Value value) {
/* 100 */     if (getValue(attribute) != null)
/* 101 */       set(attribute, value); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\av\CustomAVMapImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */