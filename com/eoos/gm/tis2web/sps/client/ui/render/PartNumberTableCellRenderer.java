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
/*    */ public class PartNumberTableCellRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col) {
/* 18 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
/* 19 */     if (value.toString().indexOf("<html>") == -1) {
/* 20 */       setText("<html> <table><tr><td>" + value.toString() + "</td></tr></table></html>");
/*    */     } else {
/* 22 */       setText(value.toString());
/* 23 */     }  setVerticalAlignment(1);
/* 24 */     setHorizontalAlignment(4);
/*    */     
/* 26 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\PartNumberTableCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */