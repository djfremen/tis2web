/*     */ package com.eoos.scsm.v2.swing.table;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiffTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private TableModel delegate;
/*     */   private TableModelListener delegateListener;
/*     */   private final int initialRowCount;
/*     */   
/*     */   public DiffTableModel(TableModel delegate) {
/*  20 */     this.delegate = delegate;
/*  21 */     this.initialRowCount = delegate.getRowCount();
/*  22 */     this.delegateListener = new TableModelListener()
/*     */       {
/*     */         public void tableChanged(TableModelEvent e) {
/*  25 */           TableModelEvent ee = new TableModelEvent((TableModel)e.getSource(), e.getFirstRow(), e.getLastRow(), e.getColumn(), e.getType());
/*  26 */           DiffTableModel.this.fireTableChanged(ee);
/*     */         }
/*     */       };
/*  29 */     this.delegate.addTableModelListener(this.delegateListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Class getColumnClass(int columnIndex) {
/*  34 */     return this.delegate.getColumnClass(columnIndex);
/*     */   } public static interface CommitCallback {
/*     */     void addRow(Object[] param1ArrayOfObject); void deleteIndex(int param1Int); }
/*     */   public synchronized int getColumnCount() {
/*  38 */     return this.delegate.getColumnCount();
/*     */   }
/*     */   
/*     */   public synchronized String getColumnName(int columnIndex) {
/*  42 */     return this.delegate.getColumnName(columnIndex);
/*     */   }
/*     */   
/*     */   public synchronized int getRowCount() {
/*  46 */     return this.delegate.getRowCount();
/*     */   }
/*     */   
/*     */   public synchronized Object getValueAt(int rowIndex, int columnIndex) {
/*  50 */     return this.delegate.getValueAt(rowIndex, columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isCellEditable(int rowIndex, int columnIndex) {
/*  55 */     return this.delegate.isCellEditable(rowIndex, columnIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setValueAt(Object value, int rowIndex, int columnIndex) {
/*  60 */     this.delegate.setValueAt(value, rowIndex, columnIndex);
/*     */   }
/*     */   
/*     */   public synchronized void addRow() {
/*  64 */     this.delegate.removeTableModelListener(this.delegateListener);
/*  65 */     this.delegate = new AddRowTableModel(this.delegate, getRowCount(), new Object[getColumnCount()]);
/*  66 */     this.delegate.addTableModelListener(this.delegateListener);
/*  67 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public synchronized void deleteRow(int rowIndex) {
/*  71 */     this.delegate.removeTableModelListener(this.delegateListener);
/*  72 */     this.delegate = new DeleteRowTableModel(this.delegate, rowIndex);
/*  73 */     this.delegate.addTableModelListener(this.delegateListener);
/*  74 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public synchronized void undo() {
/*  78 */     if (this.delegate instanceof IDiffModel) {
/*  79 */       this.delegate.removeTableModelListener(this.delegateListener);
/*  80 */       this.delegate = ((IDiffModel)this.delegate).getDelegate();
/*  81 */       this.delegate.addTableModelListener(this.delegateListener);
/*  82 */       fireTableDataChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void commit(CommitCallback commitCallback) {
/*  94 */     if (this.delegate instanceof IDiffModel) {
/*  95 */       IDiffModel _delegate = (IDiffModel)this.delegate;
/*     */       
/*  97 */       boolean[] deleteFlag = new boolean[this.initialRowCount];
/*  98 */       Arrays.fill(deleteFlag, true);
/*     */       int i;
/* 100 */       for (i = 0; i < getRowCount(); i++) {
/* 101 */         Object[] row = _delegate.getRowAddition(i);
/* 102 */         if (row != null) {
/* 103 */           commitCallback.addRow(row);
/*     */         } else {
/* 105 */           deleteFlag[_delegate.getBackendIndex(i)] = false;
/*     */         } 
/*     */       } 
/*     */       
/* 109 */       for (i = 0; i < deleteFlag.length; i++) {
/* 110 */         if (deleteFlag[i]) {
/* 111 */           commitCallback.deleteIndex(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hasChanged(final boolean ignoreEmptyRows) {
/* 119 */     final RuntimeException MOD_INDICATION = new RuntimeException();
/* 120 */     CommitCallback cc = new CommitCallback()
/*     */       {
/*     */         public void deleteIndex(int index) {
/* 123 */           throw MOD_INDICATION;
/*     */         }
/*     */         
/*     */         private boolean isEmpty(Object[] row) {
/* 127 */           boolean ret = true;
/* 128 */           for (int i = 0; i < row.length && ret; i++) {
/* 129 */             ret = (row[i] == null);
/*     */           }
/* 131 */           return ret;
/*     */         }
/*     */         
/*     */         public void addRow(Object[] row) {
/* 135 */           if (!ignoreEmptyRows || !isEmpty(row)) {
/* 136 */             throw MOD_INDICATION;
/*     */           }
/*     */         }
/*     */       };
/*     */     try {
/* 141 */       commit(cc);
/* 142 */       return false;
/* 143 */     } catch (RuntimeException e) {
/* 144 */       if (e == MOD_INDICATION) {
/* 145 */         return true;
/*     */       }
/* 147 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\table\DiffTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */