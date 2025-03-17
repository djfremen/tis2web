/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ public class ComboBoxRenderer
/*    */   extends JComboBox
/*    */   implements TableCellRenderer {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ComboBoxRenderer(Object[] items) {
/* 14 */     super((E[])items);
/*    */   }
/*    */ 
/*    */   
/*    */   public ComboBoxRenderer() {}
/*    */ 
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 22 */     if (isSelected) {
/* 23 */       setForeground(table.getSelectionForeground());
/* 24 */       setBackground(table.getSelectionBackground());
/*    */     } else {
/* 26 */       setForeground(table.getForeground());
/* 27 */       setBackground(table.getBackground());
/*    */     } 
/*    */ 
/*    */     
/* 31 */     setSelectedItem(value);
/* 32 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\ComboBoxRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */