/*    */ package com.eoos.html.element.gtwo;
/*    */ 
/*    */ import com.eoos.html.element.input.HtmlInputElementBase;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SelectionElementBase
/*    */   extends HtmlInputElementBase
/*    */   implements DataRetrievalAbstraction
/*    */ {
/*    */   private boolean singleSelectionMode = true;
/*    */   protected DataRetrievalAbstraction.DataCallback optionsCallback;
/*    */   
/*    */   protected SelectionElementBase(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback) {
/* 19 */     super(parameterName);
/* 20 */     this.singleSelectionMode = singleSelectionMode;
/* 21 */     this.optionsCallback = optionsCallback;
/*    */   }
/*    */   
/*    */   protected boolean singleSelectionMode() {
/* 25 */     return this.singleSelectionMode;
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 29 */     boolean collectionType = value instanceof java.util.Collection;
/*    */     
/* 31 */     if (!this.singleSelectionMode && value == null) {
/* 32 */       value = Collections.EMPTY_SET;
/* 33 */       collectionType = true;
/*    */     } 
/*    */     
/* 36 */     if ((singleSelectionMode() && collectionType) || (!singleSelectionMode() && !collectionType)) {
/* 37 */       throw new IllegalArgumentException("value not suitable to current selection mode");
/*    */     }
/* 39 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDataCallback(DataRetrievalAbstraction.DataCallback optionsCallback) {
/* 44 */     this.optionsCallback = optionsCallback;
/*    */   }
/*    */   
/*    */   public DataRetrievalAbstraction.DataCallback getDataCallback() {
/* 48 */     return this.optionsCallback;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 52 */     return option.toString();
/*    */   }
/*    */   
/*    */   protected String getSubmitValue(Object option) {
/* 56 */     return String.valueOf(this.optionsCallback.getData().indexOf(option));
/*    */   }
/*    */   
/*    */   protected Object getOption(String submitValue) {
/*    */     try {
/* 61 */       int index = Integer.parseInt(submitValue);
/* 62 */       return this.optionsCallback.getData().get(index);
/* 63 */     } catch (Exception e) {
/* 64 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\SelectionElementBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */