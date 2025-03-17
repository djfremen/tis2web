/*    */ package com.eoos.scsm.v2.swing.table;
/*    */ 
/*    */ import javax.swing.event.TableModelEvent;
/*    */ import javax.swing.event.TableModelListener;
/*    */ import javax.swing.table.AbstractTableModel;
/*    */ import javax.swing.table.TableModel;
/*    */ 
/*    */ class DeleteRowTableModel
/*    */   extends AbstractTableModel
/*    */   implements IDiffModel {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private TableModel delegate;
/*    */   private int index;
/*    */   
/*    */   DeleteRowTableModel(TableModel delegate, int index) {
/* 16 */     this.delegate = delegate;
/* 17 */     this.delegate.addTableModelListener(new TableModelListener()
/*    */         {
/*    */           public void tableChanged(TableModelEvent e) {
/* 20 */             TableModelEvent ee = new TableModelEvent((TableModel)e.getSource(), e.getFirstRow(), e.getLastRow(), e.getColumn(), e.getType());
/* 21 */             DeleteRowTableModel.this.fireTableChanged(ee);
/*    */           }
/*    */         });
/* 24 */     this.index = index;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getColumnClass(int columnIndex) {
/* 29 */     return this.delegate.getColumnClass(columnIndex);
/*    */   }
/*    */   
/*    */   public int getColumnCount() {
/* 33 */     return this.delegate.getColumnCount();
/*    */   }
/*    */   
/*    */   public String getColumnName(int columnIndex) {
/* 37 */     return this.delegate.getColumnName(columnIndex);
/*    */   }
/*    */   
/*    */   public int getRowCount() {
/* 41 */     return this.delegate.getRowCount() - 1;
/*    */   }
/*    */   
/*    */   private int toDelegationIndex(int rowIndex) {
/* 45 */     if (rowIndex < this.index) {
/* 46 */       return rowIndex;
/*    */     }
/* 48 */     return rowIndex + 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValueAt(int rowIndex, int columnIndex) {
/* 53 */     return this.delegate.getValueAt(toDelegationIndex(rowIndex), columnIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 58 */     return this.delegate.isCellEditable(toDelegationIndex(rowIndex), columnIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValueAt(Object value, int rowIndex, int columnIndex) {
/* 63 */     this.delegate.setValueAt(value, toDelegationIndex(rowIndex), columnIndex);
/*    */   }
/*    */   
/*    */   public TableModel getDelegate() {
/* 67 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public int getBackendIndex(int index) {
/* 71 */     if (this.delegate instanceof IDiffModel) {
/* 72 */       return ((IDiffModel)this.delegate).getBackendIndex(toDelegationIndex(index));
/*    */     }
/* 74 */     return toDelegationIndex(index);
/*    */   }
/*    */ 
/*    */   
/*    */   private IDiffModel getDiffModelDelegate() {
/*    */     try {
/* 80 */       return (IDiffModel)this.delegate;
/* 81 */     } catch (ClassCastException e) {
/* 82 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object[] getRowAddition(int rowIndex) {
/* 87 */     if (getDiffModelDelegate() != null) {
/* 88 */       return getDiffModelDelegate().getRowAddition(toDelegationIndex(rowIndex));
/*    */     }
/* 90 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\table\DeleteRowTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */