/*    */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import java.awt.Component;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.ListCellRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListCellRenderer_Denotation
/*    */   extends JLabel
/*    */   implements ListCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ListCellRenderer backend;
/*    */   
/*    */   public ListCellRenderer_Denotation(ListCellRenderer backend) {
/* 21 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   public Component getListCellRendererComponent(JList<? extends String> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 25 */     Denotation denotation = (Denotation)value;
/*    */     
/* 27 */     return this.backend.getListCellRendererComponent(list, (denotation != null) ? denotation.getDenotation(getLocale()) : null, index, isSelected, cellHasFocus);
/*    */   }
/*    */   
/*    */   public Locale getLocale() {
/* 31 */     return Locale.ENGLISH;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\ListCellRenderer_Denotation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */