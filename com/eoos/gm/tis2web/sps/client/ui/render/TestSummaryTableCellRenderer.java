/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.plaf.basic.BasicHTML;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import javax.swing.text.View;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestSummaryTableCellRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col) {
/* 22 */     String newValue = null;
/*    */     
/* 24 */     if (value.toString().indexOf("<html>") == -1) {
/* 25 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     
/*    */     }
/* 28 */     else if (value.toString().indexOf("<table>") == -1) {
/* 29 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     } else {
/*    */       
/* 32 */       newValue = value.toString();
/*    */     } 
/*    */     
/* 35 */     setText(newValue);
/* 36 */     if (col == table.getColumnCount() - 1) {
/* 37 */       int lastColIndex = table.getColumnCount() - 1;
/* 38 */       int lastColWidth = table.getColumnModel().getColumn(lastColIndex).getWidth();
/* 39 */       View view = BasicHTML.createHTMLView(new JLabel(), newValue);
/* 40 */       view.setSize((lastColWidth - 5), 10000.0F);
/* 41 */       int height = (int)view.getMinimumSpan(1) + 5;
/* 42 */       if (table.getRowHeight(row) != height) {
/* 43 */         table.setRowHeight(row, height);
/*    */       }
/*    */     } 
/*    */     
/* 47 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
/* 48 */     setVerticalAlignment(1);
/* 49 */     setHorizontalAlignment(2);
/*    */     
/* 51 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\TestSummaryTableCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */