/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckBoxRenderer
/*    */   extends JComboBox
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 18 */     if (isSelected) {
/* 19 */       setForeground(table.getSelectionForeground());
/* 20 */       setBackground(table.getSelectionBackground());
/*    */     } else {
/* 22 */       setForeground(table.getForeground());
/* 23 */       setBackground(table.getBackground());
/*    */     } 
/*    */ 
/*    */     
/* 27 */     setSelectedItem(value);
/* 28 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\CheckBoxRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */