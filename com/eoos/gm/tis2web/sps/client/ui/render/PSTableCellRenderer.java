/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import javax.swing.table.TableModel;
/*    */ 
/*    */ 
/*    */ public class PSTableCellRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   protected static ImageIcon ICON_PASS = new ImageIcon(PSTableCellRenderer.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/pass.gif"));
/* 21 */   protected static ImageIcon ICON_SKIP = new ImageIcon(PSTableCellRenderer.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/skip.gif"));
/* 22 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col) {
/* 25 */     String newValue = null;
/* 26 */     if (col == 0 && value.toString().trim().length() != 0) {
/* 27 */       setIcon(getPSIconLabel(row, table));
/* 28 */       setIconTextGap(2);
/*    */     } else {
/*    */       
/* 31 */       setIcon((Icon)null);
/* 32 */       setIconTextGap(0);
/*    */     } 
/*    */     
/* 35 */     if (value.toString().indexOf("<html>") == -1) {
/* 36 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     
/*    */     }
/* 39 */     else if (value.toString().indexOf("<table>") == -1) {
/* 40 */       newValue = "<html><table><tr><td>" + value.toString() + "</td></tr></table></html>";
/*    */     } else {
/*    */       
/* 43 */       newValue = value.toString();
/*    */     } 
/*    */     
/* 46 */     setText(newValue);
/* 47 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
/* 48 */     setVerticalAlignment(1);
/* 49 */     setHorizontalAlignment(2);
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ImageIcon getPSIconLabel(int row, JTable table) {
/* 55 */     TableModel model = table.getModel();
/* 56 */     int indexFirst = table.getColumnModel().getColumnIndex(resourceProvider.getLabel(null, "summaryScreen.moduleTable.current"));
/* 57 */     int indexSecond = table.getColumnModel().getColumnIndex(resourceProvider.getLabel(null, "summaryScreen.moduleTable.selected"));
/*    */     
/* 59 */     int i = row;
/* 60 */     boolean skip = false;
/* 61 */     while (i < model.getRowCount() - 1) {
/*    */ 
/*    */       
/* 64 */       if (model.getValueAt(i, indexFirst).equals(model.getValueAt(i, indexSecond)) && !model.getValueAt(i, indexFirst).equals(resourceProvider.getLabel(null, "no-data"))) {
/* 65 */         skip = true;
/*    */       } else {
/* 67 */         skip = false;
/*    */         break;
/*    */       } 
/* 70 */       i++;
/*    */       
/* 72 */       if (isEmptyRow(i, table)) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 77 */     if (skip) {
/* 78 */       return ICON_SKIP;
/*    */     }
/* 80 */     return ICON_PASS;
/*    */   }
/*    */   
/*    */   protected boolean isEmptyRow(int row, JTable table) {
/* 84 */     boolean isEmpty = true;
/* 85 */     TableModel model = table.getModel();
/* 86 */     for (int i = 0; i < model.getColumnCount(); i++) {
/* 87 */       if (!model.getValueAt(row, i).equals(""))
/* 88 */         isEmpty = false; 
/*    */     } 
/* 90 */     return isEmpty;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\PSTableCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */