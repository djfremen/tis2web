/*    */ package com.eoos.gm.tis2web.frame.dwnld.install.ui;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.util.EventObject;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.event.CellEditorListener;
/*    */ import javax.swing.table.TableCellEditor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractTableCellEditor
/*    */   implements TableCellEditor
/*    */ {
/*    */   public abstract Component getTableCellEditorComponent(JTable paramJTable, Object paramObject, boolean paramBoolean, int paramInt1, int paramInt2);
/*    */   
/*    */   public void addCellEditorListener(CellEditorListener l) {}
/*    */   
/*    */   public void cancelCellEditing() {}
/*    */   
/*    */   public abstract Object getCellEditorValue();
/*    */   
/*    */   public boolean isCellEditable(EventObject anEvent) {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeCellEditorListener(CellEditorListener l) {}
/*    */   
/*    */   public boolean shouldSelectCell(EventObject anEvent) {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public boolean stopCellEditing() {
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\instal\\ui\AbstractTableCellEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */