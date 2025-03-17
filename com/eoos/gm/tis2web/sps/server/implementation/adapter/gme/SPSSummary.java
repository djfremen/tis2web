/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSSummary
/*    */   implements Summary
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String controller;
/*    */   protected SPSSoftware current;
/*    */   protected SPSSoftware selected;
/*    */   protected List modules;
/*    */   protected List history;
/*    */   
/*    */   SPSSummary(SPSSoftware current, SPSSoftware selected, List history, List modules) {
/* 26 */     this.current = current;
/* 27 */     this.selected = selected;
/* 28 */     this.history = history;
/* 29 */     this.modules = modules;
/* 30 */     checkIdentAttributes(current, selected);
/*    */   }
/*    */   
/*    */   SPSSummary(String controller, SPSSoftware current, SPSSoftware selected, List history, List modules) {
/* 34 */     this(current, selected, history, modules);
/* 35 */     this.controller = controller;
/*    */   }
/*    */   
/*    */   public String getControllerName() {
/* 39 */     return this.controller;
/*    */   }
/*    */   
/*    */   public History getCurrentSoftware() {
/* 43 */     return this.current;
/*    */   }
/*    */   
/*    */   public History getSelectedSoftware() {
/* 47 */     return this.selected;
/*    */   }
/*    */   
/*    */   public List getModules() {
/* 51 */     return this.modules;
/*    */   }
/*    */   
/*    */   public List getHistory() {
/* 55 */     return this.history;
/*    */   }
/*    */   
/*    */   protected void checkIdentAttributes(SPSSoftware current, SPSSoftware selected) {
/* 59 */     Set<Object> idents = new HashSet();
/* 60 */     List<Pair> attributes = selected.getAttributes();
/* 61 */     for (int i = 0; i < attributes.size(); i++) {
/* 62 */       Pair attribute = attributes.get(i);
/* 63 */       idents.add(attribute.getFirst());
/*    */     } 
/* 65 */     if (current != null && current.getAttributes() != null) {
/* 66 */       Iterator<Pair> it = current.getAttributes().iterator();
/* 67 */       while (it.hasNext()) {
/* 68 */         Pair attribute = it.next();
/* 69 */         String ident = (String)attribute.getFirst();
/* 70 */         String value = (String)attribute.getSecond();
/* 71 */         if (value != null && value.startsWith("*") && 
/* 72 */           !idents.contains(ident))
/* 73 */           it.remove(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSummary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */