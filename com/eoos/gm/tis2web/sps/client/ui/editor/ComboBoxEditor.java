/*    */ package com.eoos.gm.tis2web.sps.client.ui.editor;
/*    */ 
/*    */ import javax.swing.DefaultCellEditor;
/*    */ import javax.swing.JComboBox;
/*    */ 
/*    */ public class ComboBoxEditor extends DefaultCellEditor {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ComboBoxEditor(Object[] items) {
/* 10 */     super(new JComboBox(items));
/*    */   }
/*    */   
/*    */   public ComboBoxEditor() {
/* 14 */     super(new JComboBox());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\editor\ComboBoxEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */