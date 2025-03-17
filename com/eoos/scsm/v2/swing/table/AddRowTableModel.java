/*     */ package com.eoos.scsm.v2.swing.table;
/*     */ 
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ class AddRowTableModel
/*     */   extends AbstractTableModel
/*     */   implements IDiffModel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private TableModel delegate;
/*     */   private int index;
/*     */   private Object[] row;
/*     */   
/*     */   AddRowTableModel(TableModel delegate, int index, Object[] row) {
/*  18 */     this.delegate = delegate;
/*  19 */     this.delegate.addTableModelListener(new TableModelListener()
/*     */         {
/*     */           public void tableChanged(TableModelEvent e) {
/*  22 */             TableModelEvent ee = new TableModelEvent((TableModel)e.getSource(), e.getFirstRow(), e.getLastRow(), e.getColumn(), e.getType());
/*  23 */             AddRowTableModel.this.fireTableChanged(ee);
/*     */           }
/*     */         });
/*  26 */     this.index = index;
/*  27 */     this.row = row;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getColumnClass(int columnIndex) {
/*  32 */     return this.delegate.getColumnClass(columnIndex);
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/*  36 */     return this.delegate.getColumnCount();
/*     */   }
/*     */   
/*     */   public String getColumnName(int columnIndex) {
/*  40 */     return this.delegate.getColumnName(columnIndex);
/*     */   }
/*     */   
/*     */   public int getRowCount() {
/*  44 */     return this.delegate.getRowCount() + 1;
/*     */   }
/*     */   
/*     */   private int toDelegationIndex(int rowIndex) {
/*  48 */     if (rowIndex < this.index)
/*  49 */       return rowIndex; 
/*  50 */     if (rowIndex > this.index) {
/*  51 */       return rowIndex - 1;
/*     */     }
/*  53 */     throw new NoDelegationIndexException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int rowIndex, int columnIndex) {
/*  58 */     if (rowIndex == this.index) {
/*  59 */       return this.row[columnIndex];
/*     */     }
/*  61 */     return this.delegate.getValueAt(toDelegationIndex(rowIndex), columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  66 */     if (rowIndex == this.index) {
/*  67 */       return true;
/*     */     }
/*  69 */     return this.delegate.isCellEditable(toDelegationIndex(rowIndex), columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int rowIndex, int columnIndex) {
/*  74 */     if (rowIndex == this.index) {
/*  75 */       this.row[columnIndex] = value;
/*  76 */       fireTableCellUpdated(rowIndex, columnIndex);
/*     */     } else {
/*  78 */       this.delegate.setValueAt(value, toDelegationIndex(rowIndex), columnIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TableModel getDelegate() {
/*  84 */     return this.delegate;
/*     */   }
/*     */   
/*     */   public int getBackendIndex(int index) {
/*  88 */     if (this.delegate instanceof IDiffModel) {
/*  89 */       return ((IDiffModel)this.delegate).getBackendIndex(toDelegationIndex(index));
/*     */     }
/*  91 */     return toDelegationIndex(index);
/*     */   }
/*     */ 
/*     */   
/*     */   private IDiffModel getDiffModelDelegate() {
/*     */     try {
/*  97 */       return (IDiffModel)this.delegate;
/*  98 */     } catch (ClassCastException e) {
/*  99 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object[] getRowAddition(int rowIndex) {
/* 104 */     if (rowIndex == this.index)
/* 105 */       return this.row; 
/* 106 */     if (getDiffModelDelegate() != null) {
/* 107 */       return getDiffModelDelegate().getRowAddition(toDelegationIndex(rowIndex));
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\table\AddRowTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */