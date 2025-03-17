/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SelectionElementBase
/*    */   extends HtmlInputElementBase
/*    */ {
/*    */   private boolean singleSelectionMode = true;
/*    */   private List options;
/*    */   
/*    */   protected SelectionElementBase(String parameterName, boolean singleSelectionMode, List<?> options) {
/* 18 */     super(parameterName);
/* 19 */     this.singleSelectionMode = singleSelectionMode;
/* 20 */     this.options = (options != null) ? new ArrayList(options) : null;
/*    */   }
/*    */   
/*    */   protected boolean singleSelectionMode() {
/* 24 */     return this.singleSelectionMode;
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 28 */     boolean collectionType = value instanceof Collection;
/*    */     
/* 30 */     if ((singleSelectionMode() && collectionType) || (!singleSelectionMode() && !collectionType)) {
/* 31 */       throw new IllegalArgumentException("value not suitable to current selection mode");
/*    */     }
/* 33 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOptions(List<?> options) {
/* 39 */     if (this.value != null) {
/* 40 */       if (singleSelectionMode()) {
/* 41 */         if (!options.contains(this.value)) {
/* 42 */           this.value = null;
/*    */         }
/*    */       } else {
/* 45 */         Iterator iter = ((Collection)this.value).iterator();
/* 46 */         while (iter.hasNext()) {
/* 47 */           Object value = iter.next();
/* 48 */           if (!options.contains(value)) {
/* 49 */             iter.remove();
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 55 */     this.options = new ArrayList(options);
/*    */   }
/*    */   
/*    */   protected List getOptions() {
/* 59 */     return this.options;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 63 */     return option.toString();
/*    */   }
/*    */   
/*    */   protected String getSubmitValue(Object option) {
/* 67 */     return String.valueOf(getOptions().indexOf(option));
/*    */   }
/*    */   
/*    */   protected Object getOption(String submitValue) {
/*    */     try {
/* 72 */       int index = Integer.parseInt(submitValue);
/* 73 */       return getOptions().get(index);
/* 74 */     } catch (Exception e) {
/* 75 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\SelectionElementBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */