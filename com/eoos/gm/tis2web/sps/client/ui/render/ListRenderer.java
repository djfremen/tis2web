/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.CalibrationTreeModel;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.CalibrationTreePanel;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import javax.swing.DefaultListCellRenderer;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JList;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListRenderer
/*    */   extends DefaultListCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   private static final Logger log = Logger.getLogger(ListRenderer.class);
/* 23 */   protected static ImageIcon ICON_NO_MODULE_SELECTED = null;
/* 24 */   protected static ImageIcon ICON_MODULE_SELECTED = null;
/*    */   static {
/*    */     try {
/* 27 */       ICON_MODULE_SELECTED = new ImageIcon(CalibrationTreePanel.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/module-selected.gif"));
/* 28 */       ICON_NO_MODULE_SELECTED = new ImageIcon(CalibrationTreePanel.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/module-noselected.gif"));
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       log.warn("unable to initialize icons, ignoring  - exception: ", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean m_selected = false;
/*    */ 
/*    */   
/*    */   protected Color selectionColor;
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
/* 46 */     CalibrationTreeModel module = new CalibrationTreeModel((Value)((DisplayAdapter)value).getAdaptee());
/* 47 */     if (ICON_MODULE_SELECTED == null || ICON_NO_MODULE_SELECTED == null) {
/* 48 */       if (module.getSelectedNode() != null) {
/* 49 */         setForeground(list.getForeground());
/*    */       } else {
/* 51 */         setForeground(Color.red);
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 56 */     else if (module.getSelectedNode() != null) {
/* 57 */       setIcon(ICON_MODULE_SELECTED);
/*    */     } else {
/*    */       
/* 60 */       setIcon(ICON_NO_MODULE_SELECTED);
/*    */     } 
/*    */ 
/*    */     
/* 64 */     setText(value.toString());
/*    */     
/* 66 */     if (isSelected) {
/* 67 */       setBackground(list.getSelectionBackground());
/*    */     } else {
/* 69 */       setBackground(list.getBackground());
/*    */     } 
/*    */ 
/*    */     
/* 73 */     this.selectionColor = list.getSelectionBackground();
/* 74 */     this.m_selected = isSelected;
/* 75 */     setForeground(list.getForeground());
/* 76 */     setEnabled(list.isEnabled());
/* 77 */     setFont(list.getFont());
/* 78 */     setComponentOrientation(list.getComponentOrientation());
/*    */     
/* 80 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\ListRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */