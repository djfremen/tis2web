/*    */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.CalibrationTreeModel;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TreeNodeIcon;
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PROMIconCellRenderer
/*    */   extends DefaultTreeCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
/* 27 */     String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
/*    */     
/* 29 */     this.hasFocus = hasFocus;
/* 30 */     setText(stringValue);
/*    */     
/* 32 */     CalibrationTreeModel model = (CalibrationTreeModel)tree.getModel();
/*    */     
/* 34 */     if (!tree.isEnabled()) {
/* 35 */       setEnabled(false);
/* 36 */       if (leaf) {
/* 37 */         setDisabledIcon(getLeafIcon());
/* 38 */       } else if (expanded) {
/* 39 */         setDisabledIcon(getOpenIcon());
/*    */       } else {
/* 41 */         setDisabledIcon(getClosedIcon());
/*    */       } 
/*    */     } else {
/*    */       
/* 45 */       setEnabled(true);
/* 46 */       if (leaf) {
/* 47 */         setIcon(getLeafIcon());
/* 48 */       } else if (expanded) {
/* 49 */         setIcon(getOpenIcon());
/*    */       } else {
/* 51 */         setIcon(getClosedIcon());
/*    */       } 
/*    */       
/* 54 */       if (model.isSelected(value)) {
/* 55 */         setIcon(TreeNodeIcon.ICON_SELECTED);
/*    */       }
/* 57 */       else if (model.isSelectable(value)) {
/* 58 */         setIcon(TreeNodeIcon.ICON_UNSELECTED);
/*    */       } else {
/*    */         
/* 61 */         setIcon(TreeNodeIcon.ICON_NOSELECTION);
/*    */       } 
/* 63 */       setText(value.toString());
/*    */     } 
/*    */     
/* 66 */     if (sel) {
/* 67 */       setForeground(getTextSelectionColor());
/*    */     } else {
/* 69 */       setForeground(getTextNonSelectionColor());
/*    */     } 
/* 71 */     setComponentOrientation(tree.getComponentOrientation());
/* 72 */     this.selected = sel;
/*    */     
/* 74 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\PROMIconCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */