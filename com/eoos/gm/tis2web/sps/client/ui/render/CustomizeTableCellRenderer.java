/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeTableCellRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col) {
/* 21 */     String newValue = null;
/*    */     
/* 23 */     if (value.toString().indexOf("<html>") == -1) {
/* 24 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     }
/* 26 */     else if (value.toString().indexOf("<table>") == -1) {
/* 27 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     } else {
/* 29 */       newValue = value.toString();
/*    */     } 
/*    */     
/* 32 */     setText(newValue);
/* 33 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
/* 34 */     setVerticalAlignment(1);
/* 35 */     setHorizontalAlignment(2);
/* 36 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\CustomizeTableCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */