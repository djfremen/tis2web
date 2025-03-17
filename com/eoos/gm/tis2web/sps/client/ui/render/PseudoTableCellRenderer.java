/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ public class PseudoTableCellRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col) {
/* 16 */     setText("<html><table><tr><td>&nbsp;</td></tr></table></html>");
/* 17 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
/* 18 */     setVerticalAlignment(1);
/* 19 */     setHorizontalAlignment(2);
/* 20 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\PseudoTableCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */