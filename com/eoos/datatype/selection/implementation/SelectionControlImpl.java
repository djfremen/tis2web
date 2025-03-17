/*    */ package com.eoos.datatype.selection.implementation;
/*    */ 
/*    */ import com.eoos.datatype.selection.SelectionControl;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionControlImpl
/*    */   implements SelectionControl
/*    */ {
/*    */   protected SelectionControlSPI spi;
/*    */   
/*    */   public SelectionControlImpl(SelectionControlSPI spi) {
/* 23 */     this.spi = spi;
/*    */   }
/*    */   
/*    */   public void setSelected(Object obj, boolean selected) {
/* 27 */     if (selected) {
/* 28 */       this.spi.addSelection(obj);
/*    */     } else {
/* 30 */       this.spi.removeSelection(obj);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isSelected(Object obj) {
/* 35 */     return getSelection().contains(obj);
/*    */   }
/*    */   
/*    */   public Collection getSelection() {
/* 39 */     return this.spi.getSelection();
/*    */   }
/*    */   
/*    */   public void toggleSelection(Object obj) {
/* 43 */     if (isSelected(obj)) {
/* 44 */       setSelected(obj, false);
/*    */     } else {
/* 46 */       setSelected(obj, true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSingleSelection(Object obj) {
/* 52 */     Iterator iter = getSelection().iterator();
/* 53 */     while (iter.hasNext()) {
/* 54 */       setSelected(iter.next(), false);
/*    */     }
/* 56 */     setSelected(obj, true);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\selection\implementation\SelectionControlImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */