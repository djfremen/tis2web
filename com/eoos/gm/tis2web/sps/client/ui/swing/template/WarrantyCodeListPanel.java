/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.WarrantyClaimCodeStore;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ 
/*     */ public class WarrantyCodeListPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private WarrantyCodeListModel model;
/*     */   private TimeStampRenderer renderer;
/*     */   
/*     */   public WarrantyCodeListPanel(SPSFrame frame, ClientSettings clientSettings) {
/*  31 */     this.renderer = new TimeStampRenderer();
/*  32 */     getSize();
/*  33 */     this.model = new WarrantyCodeListModel(WarrantyClaimCodeStore.getWarrantyCodeList());
/*  34 */     JTable table = new JTable(this.model)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Dimension getPreferredScrollableViewportSize() {
/*  39 */           return getParentSize(getParent());
/*     */         }
/*     */         
/*     */         private Dimension getParentSize(Component parent) {
/*  43 */           if (parent == null) {
/*  44 */             return new Dimension(200, 400);
/*     */           }
/*  46 */           Dimension size = parent.getSize();
/*  47 */           if (size == null || size.height == 0 || size.width == 0) {
/*  48 */             return getParentSize(parent.getParent());
/*     */           }
/*  50 */           return parent.getSize();
/*     */         }
/*     */         
/*     */         public TableCellRenderer getCellRenderer(int row, int column) {
/*  54 */           if (column == 2) {
/*  55 */             return WarrantyCodeListPanel.this.renderer;
/*     */           }
/*  57 */           return super.getCellRenderer(row, column);
/*     */         }
/*     */       };
/*  60 */     table.setPreferredScrollableViewportSize(new Dimension(500, 70));
/*  61 */     TableCellRenderer stringRenderer = table.getDefaultRenderer(String.class);
/*  62 */     ((JLabel)stringRenderer).setHorizontalAlignment(0);
/*  63 */     table.setAutoResizeMode(4);
/*  64 */     JScrollPane scrollPane = new JScrollPane(table);
/*     */     
/*  66 */     add(scrollPane);
/*     */   }
/*     */   
/*     */   static class TimeStampRenderer
/*     */     extends DefaultTableCellRenderer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     DateFormat formatter;
/*     */     
/*     */     public TimeStampRenderer() {
/*  75 */       setHorizontalAlignment(0);
/*     */     }
/*     */     
/*     */     public void setValue(Object value) {
/*  79 */       if (value instanceof Long) {
/*  80 */         if (this.formatter == null) {
/*  81 */           this.formatter = DateFormat.getDateTimeInstance(0, 0);
/*     */         }
/*  83 */         setText((value == null) ? "" : this.formatter.format(value));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class WarrantyCodeListModel extends AbstractTableModel {
/*     */     private static final long serialVersionUID = 1L;
/*     */     List records;
/*     */     List columns;
/*     */     
/*     */     protected WarrantyCodeListModel(List records) {
/*  94 */       if (records != null) {
/*  95 */         this.records = records;
/*     */       } else {
/*  97 */         this.records = new ArrayList();
/*     */       } 
/*  99 */       LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/* 100 */       this.columns = new ArrayList();
/* 101 */       this.columns.add(resourceProvider.getLabel(null, "settingsDialog.wccTab.vin"));
/* 102 */       this.columns.add(resourceProvider.getLabel(null, "settingsDialog.wccTab.wcc"));
/* 103 */       this.columns.add(resourceProvider.getLabel(null, "settingsDialog.wccTab.timestamp"));
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 107 */       return this.columns.get(col);
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 111 */       return this.columns.size();
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 115 */       return this.records.size();
/*     */     }
/*     */     
/*     */     public Object getValueAt(int rowIndex, int columnIndex) {
/* 119 */       String[] record = this.records.get(rowIndex);
/* 120 */       if (columnIndex == 2) {
/* 121 */         return Long.valueOf(record[columnIndex]);
/*     */       }
/* 123 */       return record[columnIndex];
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\WarrantyCodeListPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */