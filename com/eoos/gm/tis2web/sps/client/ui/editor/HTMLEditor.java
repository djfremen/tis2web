/*    */ package com.eoos.gm.tis2web.sps.client.ui.editor;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeHtmlRender;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ViewerHTMLDialog;
/*    */ import java.awt.Component;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.AbstractCellEditor;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellEditor;
/*    */ 
/*    */ 
/*    */ public class HTMLEditor
/*    */   extends AbstractCellEditor
/*    */   implements TableCellEditor, ActionListener
/*    */ {
/*    */   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
/* 18 */     CustomizeHtmlRender webWindowHTML = ViewerHTMLDialog.getWebWindow(null, "<html><a href=\"URL\">Link-Text</a></html>");
/*    */     
/* 20 */     return (Component)webWindowHTML;
/*    */   }
/*    */   
/*    */   public Object getCellEditorValue() {
/* 24 */     Object webWindowHTML = ViewerHTMLDialog.getWebWindow(null, "<html><a href=\"URL\">Link-Text</a></html>");
/* 25 */     return webWindowHTML;
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 29 */     System.out.println("catch");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\editor\HTMLEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */